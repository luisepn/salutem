/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.beans;

import java.io.Serializable;
import java.util.Date;
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
import javax.faces.model.SelectItem;
import org.controladores.salutem.HorariosFacade;
import org.entidades.salutem.Horarios;
import org.entidades.salutem.Horas;
import org.entidades.salutem.Parametros;
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
 * @since 22 de Julio de 2018, 23:07:05 AM
 *
 *
 */
@ManagedBean(name = "salutemHorarios")
@ViewScoped
public class HorariosBean implements Serializable, IMantenimiento {

    @ManagedProperty(value = "#{salutemSeguridad}")
    private SeguridadBean seguridadBean;
    @ManagedProperty(value = "#{salutemCombos}")
    private CombosBean combosBean;

    private Perfiles perfil;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Horarios> horarios;
    private List<Parametros> dias;
    private Horarios horario;
    private int profesional;

    @EJB
    private HorariosFacade ejbHorarios;

    public HorariosBean() {
        horarios = new LazyDataModel<Horarios>() {
            @Override
            public List<Horarios> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        perfil = seguridadBean.traerPerfil("Horarios");
        dias = combosBean.traerParametros(CombosBean.DIAS_SEMANA, "o.parametros");
    }

    private List<Horarios> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
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
            int total = ejbHorarios.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            horarios.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.profesional.id, o.dia.parametros, o.hora.horainicio asc";
            } else {
                order = (seguridadBean.getVerAgrupado() ? "o.profesional.id," : "") + "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbHorarios.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }

        horarios = new LazyDataModel<Horarios>() {
            @Override
            public List<Horarios> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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

        horario = new Horarios();
        horario.setActivo(Boolean.TRUE);
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        horario = (Horarios) horarios.getRowData();
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        horario = (Horarios) horarios.getRowData();
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        try {
            if (horario.getDia() == null) {
                Mensajes.advertencia("Día es necesario");
                return true;
            }
            if (horario.getHora() == null) {
                Mensajes.advertencia("Hora es necesaria");
                return true;
            }

            String where = "o.dia=:dia and o.hora=:hora and o.profesional=:profesional";
            Map parameters = new HashMap();
            parameters.put("dia", horario.getDia());
            parameters.put("hora", horario.getHora());
            parameters.put("profesional", horario.getProfesional());
            if (horario.getId() != null) {
                where += " and o.id!=:id";
                parameters.put("id", horario.getId());
            }
            if (ejbHorarios.contar(where, parameters) > 0) {
                Mensajes.advertencia("Registro duplicado");
                return true;
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
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
            horario.setCreado(new Date());
            horario.setCreadopor(seguridadBean.getLogueado().getUserid());
            horario.setActualizado(horario.getCreado());
            horario.setActualizadopor(horario.getCreadopor());
            ejbHorarios.crear(horario, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String grabar() {
        if (validar()) {
            return null;
        }
        try {
            horario.setActualizado(new Date());
            horario.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbHorarios.actualizar(horario, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String remover() {
        try {
            ejbHorarios.eliminar(horario, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        buscar();
        formulario.cancelar();

        return null;
    }

    public SelectItem[] getComboHorariosPaquete() {
        List<Horarios> li = new LinkedList<>();
        String where = "o.activo = true and o.institucion=:institucion and o.paquete = true";
        Map parametros = new HashMap();
        parametros.put(";where", "o.activo = true and o.institucion=:institucion and o.paquete = true");
        parametros.put("institucion", seguridadBean.getInstitucion());

        try {
            li = ejbHorarios.buscar(where, parametros);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        //return Combos.SelectItems(li, true);
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public String getProfesionales(Horas hora, Parametros dia) {
        String retorno = "";
        Map parametros = new HashMap();
        String where = "o.hora=:hora and o.dia=:dia";
        parametros.put("hora", hora);
        parametros.put("dia", dia);
        if (profesional != 0) {
            where += " and o.profesional.id=:profesional";
            parametros.put("profesional", profesional);
        }

        try {
            List<Horarios> aux = ejbHorarios.buscar(where, parametros);
            for (Horarios h : aux) {
                retorno += " - " + h.getProfesional().toString() + "\n";
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public String getColor(Horas hora, Parametros dia) {
        Map parametros = new HashMap();
        String where = "o.hora=:hora and o.dia=:dia and o.profesional.id=:profesional";
        parametros.put("hora", hora);
        parametros.put("dia", dia);
        parametros.put("profesional", profesional);

        try {
            if (ejbHorarios.contar(where, parametros) > 0) {
                return "#195f88";
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "#FFFFFF";
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
     * @return the horarios
     */
    public LazyDataModel<Horarios> getHorarios() {
        return horarios;
    }

    /**
     * @param horarios the horarios to set
     */
    public void setHorarios(LazyDataModel<Horarios> horarios) {
        this.horarios = horarios;
    }

    /**
     * @return the dias
     */
    public List<Parametros> getDias() {
        return dias;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(List<Parametros> dias) {
        this.dias = dias;
    }

    /**
     * @return the horario
     */
    public Horarios getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(Horarios horario) {
        this.horario = horario;
    }

    /**
     * @return the profesional
     */
    public int getProfesional() {
        return profesional;
    }

    /**
     * @param profesional the profesional to set
     */
    public void setProfesional(int profesional) {
        this.profesional = profesional;
    }

    /**
     * @return the combosBean
     */
    public CombosBean getCombosBean() {
        return combosBean;
    }

    /**
     * @param combosBean the combosBean to set
     */
    public void setCombosBean(CombosBean combosBean) {
        this.combosBean = combosBean;
    }

}
