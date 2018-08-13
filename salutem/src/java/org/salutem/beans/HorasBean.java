/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.controladores.salutem.HorasFacade;
import org.entidades.salutem.Horas;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.excepciones.salutem.ExcepcionDeEliminacion;
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
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                }
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
                order = "o.institucion.nombre, o.codigo";
            } else {
                order = (seguridadBean.getVerAgrupado() ? "o.institucion.nombre," : "") + "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbHoras.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
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
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        hora = (Horas) horas.getRowData();
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        hora = (Horas) horas.getRowData();
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        try {
            if (hora.getInstitucion() == null) {
                Mensajes.advertencia("Institución es necesaria");
                return true;
            }
            if (hora.getCodigo() == null || hora.getCodigo().isEmpty()) {
                Mensajes.advertencia("Código es necesario");
                return true;
            }
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
            String where = " o.institucion=:institucion and  o.codigo=:codigo";
            Map parameters = new HashMap();
            parameters.put("institucion", hora.getInstitucion());
            parameters.put("codigo", hora.getCodigo());
            if (hora.getId() != null) {
                where += " and o.id!=:id";
                parameters.put("id", hora.getId());
            }
            if (ejbHoras.contar(where, parameters) > 0) {
                Mensajes.advertencia("No se permiten horas con código duplicado");
                return true;
            }

        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public String insertar() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        try {
            hora.setCreado(new Date());
            hora.setCreadopor(seguridadBean.getLogueado().getUserid());
            hora.setActualizado(hora.getCreado());
            hora.setActualizadopor(hora.getCreadopor());
            ejbHoras.crear(hora, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String grabar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        try {
            hora.setActualizado(new Date());
            hora.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbHoras.actualizar(hora, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbHoras.eliminar(hora, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        buscar();
        formulario.cancelar();

        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public String getNombreTabla() {
        return Horas.class.getSimpleName();
    }

    /**
     * @return the seguridadBean
     */
    public SeguridadBean getSeguridadBean() {
        return seguridadBean;
    }

    /**
     * @param seguridadBean the seguridadBean to set
     */
    public void setSeguridadBean(SeguridadBean seguridadBean) {
        this.seguridadBean = seguridadBean;
    }

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @return the horas
     */
    public LazyDataModel<Horas> getHoras() {
        return horas;
    }

    /**
     * @param horas the horas to set
     */
    public void setHoras(LazyDataModel<Horas> horas) {
        this.horas = horas;
    }

    /**
     * @return the hora
     */
    public Horas getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(Horas hora) {
        this.hora = hora;
    }

    /**
     * @return the perfil
     */
    public Perfiles getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

}
