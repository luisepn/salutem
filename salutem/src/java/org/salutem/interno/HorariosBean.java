/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.interno;

import org.salutem.general.CombosBean;
import org.salutem.seguridad.SeguridadBean;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.salutem.controladores.HorariosFacade;
import org.salutem.entidades.Horarios;
import org.salutem.entidades.Horas;
import org.salutem.entidades.Parametros;
import org.salutem.entidades.Perfiles;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.excepciones.ExcepcionDeCreacion;
import org.salutem.excepciones.ExcepcionDeEliminacion;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.controladores.HorasFacade;
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
@Named("salutemHorarios")
@ViewScoped
public class HorariosBean implements Serializable, IMantenimiento {

    @Inject
    private SeguridadBean seguridadBean;
    @Inject
    @Any
    private CombosBean combosBean;

    private Perfiles perfil;

    private Formulario formulario = new Formulario();
    private Formulario formularioAutomatico = new Formulario();
    private LazyDataModel<Horarios> horarios;
    private List<Horas> horas;
    private Horarios horario;
    private int profesional;
    private int dia;
    private boolean seleccionarTodo = false;

    @EJB
    private HorasFacade ejbHoras;
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

    public String automatico() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        horario = new Horarios();
        formularioAutomatico.insertar();
        return null;
    }

    public List<Horas> getHorarioProfesional() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        try {
            horas = ejbHoras.traerHoras(combosBean.getInstitucion());
            if (horario.getProfesional() == null || horario.getDia() == null) {
                for (Horas h : horas) {
                    h.setSeleccionado(Boolean.FALSE);
                }
            } else {
                for (Horas hora : horas) {
                    String where = "o.dia=:dia and o.hora=:hora and o.profesional=:profesional";
                    Map parameters = new HashMap();
                    parameters.put("dia", horario.getDia());
                    parameters.put("hora", hora);
                    parameters.put("profesional", horario.getProfesional());
                    if (ejbHorarios.contar(where, parameters) > 0) {
                        hora.setSeleccionado(Boolean.TRUE);
                    }
                }
            }
            return horas;
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void seleccionar(ValueChangeEvent event) {
        boolean seleccionarTodoNada = (Boolean) event.getNewValue();
        for (Horas h : horas) {
            h.setSeleccionado(seleccionarTodoNada);
        }
    }

    public String grabarAutomatico() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        try {
            for (Horas hora : horas) {

                String where = "o.dia=:dia and o.hora=:hora and o.profesional=:profesional";
                Map parameters = new HashMap();
                parameters.put("dia", horario.getDia());
                parameters.put("hora", hora);
                parameters.put("profesional", horario.getProfesional());
                List<Horarios> lista = ejbHorarios.buscar(where, parameters);
                Horarios h;
                if (lista.isEmpty()) {
                    if (hora.isSeleccionado()) {
                        h = new Horarios();
                        h.setProfesional(horario.getProfesional());
                        h.setDia(horario.getDia());
                        h.setActivo(Boolean.TRUE);
                        h.setHora(hora);
                        h.setCreado(new Date());
                        h.setCreadopor(seguridadBean.getLogueado().getUserid());
                        h.setActualizado(h.getCreado());
                        h.setActualizadopor(h.getCreadopor());
                        ejbHorarios.crear(h, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                    }
                } else {
                    h = lista.get(0);
                    if (hora.isSeleccionado()) {
                        h.setActivo(Boolean.TRUE);
                        h.setActualizado(new Date());
                        h.setActualizadopor(seguridadBean.getLogueado().getUserid());
                        ejbHorarios.actualizar(h, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                    } else {
                        ejbHorarios.eliminar(h, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                    }
                }
            }
            profesional = horario.getProfesional().getId();
            formularioAutomatico.cancelar();
            Mensajes.informacion("Horario generado para el día " + horario.getDia().getNombre());
        } catch (ExcepcionDeActualizacion | ExcepcionDeConsulta | ExcepcionDeEliminacion | ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            ejbHorarios.crear(horario, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
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
            ejbHorarios.actualizar(horario, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String remover() {
        try {
            ejbHorarios.eliminar(horario, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            buscar();
            formulario.cancelar();
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public String getProfesionales(Horas hora, Parametros dia) {
        if (profesional == 0) {
            return null;
        }

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

    public String getNombreTabla() {
        return Horarios.class.getSimpleName();
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

    /**
     * @return the dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * @return the formularioAutomatico
     */
    public Formulario getFormularioAutomatico() {
        return formularioAutomatico;
    }

    /**
     * @param formularioAutomatico the formularioAutomatico to set
     */
    public void setFormularioAutomatico(Formulario formularioAutomatico) {
        this.formularioAutomatico = formularioAutomatico;
    }

    /**
     * @return the horas
     */
    public List<Horas> getHoras() {
        return horas;
    }

    /**
     * @param horas the horas to set
     */
    public void setHoras(List<Horas> horas) {
        this.horas = horas;
    }

    /**
     * @return the seleccionarTodo
     */
    public boolean isSeleccionarTodo() {
        return seleccionarTodo;
    }

    /**
     * @param seleccionarTodo the seleccionarTodo to set
     */
    public void setSeleccionarTodo(boolean seleccionarTodo) {
        this.seleccionarTodo = seleccionarTodo;
    }

}
