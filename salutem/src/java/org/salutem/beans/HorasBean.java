/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.controladores.salutem.HorasFacade;
import org.entidades.salutem.Horas;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 22 de Julio de 2018, 23:06:05 AM
 *
 */
@ManagedBean(name = "salutemHoras")
@ViewScoped
public class HorasBean implements Serializable, IMantenimiento {

    @ManagedProperty(value = "#{salutemSeguridad}")
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Horas> horas;
    private Horas hora;
    private Perfiles perfil;

    @EJB
    private HorasFacade ejbHoras;

    public HorasBean() {
        horas = new LazyDataModel<Horas>() {
            @Override
            public List<Horas> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return cargar(i, i1, scs, map);
            }
        };
    }

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Horas");
    }

    private List<Horas> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            Map parameters = new HashMap();
            String where = " o.activo=:activo";
            parameters.put("activo", seguridadBean.getVerActivos());

            for (Map.Entry e : map.entrySet()) {
                String clave = (String) e.getKey();
                String valor = (String) e.getValue();
                if (clave.contains(".id")) {
                    Integer id = Integer.parseInt(valor);
                    if (id != 0) {
                        where += " and o." + clave + "=:" + clave.replaceAll("\\.", "");
                        parameters.put(clave.replaceAll("\\.", ""), id);
                    }
                } else {
                    where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                    parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
                }
            }
            if (seguridadBean.getInicioCreado() != null && seguridadBean.getFinCreado() != null) {
                where += " and o.creado between :iniciocreado and :fincreado";
                parameters.put("iniciocreado", seguridadBean.getInicioCreado());
                parameters.put("fincreado", seguridadBean.getFinCreado());
            }
            if (seguridadBean.getInicioActualizado() != null && seguridadBean.getFinActualizado() != null) {
                where += " and o.actualizado between :inicioactualizado and :finactualizado";
                parameters.put("inicioactualizado", seguridadBean.getInicioActualizado());
                parameters.put("finactualizado", seguridadBean.getFinActualizado());
            }
            int total = ejbHoras.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            horas.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.maestro.nombre, o.codigo";
            } else {
                order = (seguridadBean.getVerAgrupado() ? "o.maestro.nombre," : "") + "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbHoras.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ParametrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        horas = new LazyDataModel<Horas>() {
            @Override
            public List<Horas> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return cargar(i, i1, scs, map);
            }
        };
        return null;
    }

    @Override
    public String crear() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        hora = new Horas();
        hora.setActivo(Boolean.TRUE);
        formulario.insertar();
        formulario.insertar();
        return null;
    }

    public String modificar() {
        if (!perfil.getModificacion()) {
            Mensajes.advertencia("No tiene autorización para modificar un registro");
            return null;
        }
        hora = (Horas) horas.get(formulario.getFila().getRowIndex());
        formulario.editar();
        return null;
    }

    public String eliminar() {
        if (!perfil.getBorrado()) {
            Mensajes.advertencia("No tiene autorización para borrar un registro");
            return null;
        }
        hora = (Horas) horas.get(formulario.getFila().getRowIndex());
        formulario.eliminar();
        return null;
    }

    private boolean validar() {
        if (hora.getNombre() == null || hora.getNombre().isEmpty()) {
            Mensajes.advertencia("Nombre es necesario");
            return true;
        }
        if (hora.getHorainicio() == null) {
            Mensajes.advertencia("Hora de inicio es necesaria");
            return true;
        }
        if (hora.getHorafin() == null) {
            Mensajes.advertencia("Hora de fin es necesaria");
            return true;
        }
        return false;
    }

    public String insertar() {
        if (validar()) {
            return null;
        }
        try {
            ejbHoras.create(hora, seguridadBean.getEntidad().getUserid());
        } catch (InsertarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();
        return null;
    }

    public String grabar() {
        if (validar()) {
            return null;
        }
        try {
            ejbHoras.edit(hora, seguridadBean.getEntidad().getUserid());
        } catch (GrabarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();

        return null;
    }

    public String borrar() {
        hora.setActivo(Boolean.FALSE);
        try {
            ejbHoras.edit(hora, seguridadBean.getEntidad().getUserid());
        } catch (GrabarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        buscar();
        formulario.cancelar();

        return null;
    }

    public Horas traer(Integer id) throws ConsultarException {
        return ejbHoras.find(id);
    }

    public SelectItem[] getComboHoras() {
        List<Horas> li = new LinkedList<>();
        Map parametros = new HashMap();
        parametros.put(";where", "o.activo = true and o.centro=:centro");
        parametros.put("centro", seguridadBean.getCentro());

        try {
            li = ejbHoras.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Combos.SelectItems(li, true);
    }

    public SelectItem[] getComboHorasPaquete() {
        List<Horas> li = new LinkedList<>();
        Map parametros = new HashMap();
        parametros.put(";where", "o.activo = true and o.centro=:centro and o.paquete = true");
        parametros.put("centro", seguridadBean.getCentro());

        try {
            li = ejbHoras.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Combos.SelectItems(li, true);
    }

    public String cancelar() {
        formulario.cancelar();
        buscar();
        return null;
    }

    @Override
    public String editar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String remover() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
