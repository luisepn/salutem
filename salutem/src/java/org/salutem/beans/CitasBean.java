/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
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
import org.controladores.salutem.CitasFacade;
import org.controladores.salutem.HorariosFacade;
import org.entidades.salutem.Citas;
import org.entidades.salutem.Horarios;
import org.entidades.salutem.Horas;
import org.entidades.salutem.Pacientes;
import org.entidades.salutem.Parametros;
import org.entidades.salutem.Perfiles;
import org.entidades.salutem.Profesionales;
import org.entidades.salutem.Usuarios;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 25 de Julio de 2018, 11:13:55 AM
 */
@ManagedBean(name = "salutemCitas")
@ViewScoped
public class CitasBean implements Serializable {

    @ManagedProperty(value = "#{salutemSeguridad}")
    private SeguridadBean seguridadBean;
    @ManagedProperty(value = "#{salutemPacientes}")
    private PacientesBean pacientesBean;
    private Perfiles perfil;

    private Formulario formulario = new Formulario();
    private List<Usuarios> listaGrupoUsuarios;

    private Profesionales profesional;
    private Pacientes paciente;

    private Horarios horario;
    private List<Horarios> listaHorarios;
    private Date fecha = new Date();
    private Citas cita;

    private boolean ver = false;
    private Date inicio;
    private Date fin;

    private List<Citas> listaCitas;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @EJB
    private HorariosFacade ejbHorarios;
    @EJB
    private CitasFacade ejbCitas;

    public CitasBean() {
    }

    @PostConstruct
    public void activar() {
        perfil = seguridadBean.traerPerfil("CitasConsultasPacientes");
    }

    public String buscar() {
        if (fecha == null) {
            fecha = new Date();
        }
        String where = " o.fecha>=:fecha";
        Map parametros = new HashMap();
        parametros.put("fecha", fecha);
        try {
            listaCitas = ejbCitas.buscar(where, parametros);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private boolean validarFecha(Date fecha) {
        Calendar t = Calendar.getInstance();
        t.setTime(new Date());
        t.set(Calendar.HOUR_OF_DAY, 0);
        t.set(Calendar.MINUTE, 0);
        t.set(Calendar.SECOND, 0);
        t.set(Calendar.MILLISECOND, 0);

        Calendar f = Calendar.getInstance();
        f.setTime(fecha);
        f.set(Calendar.HOUR_OF_DAY, 0);
        f.set(Calendar.MINUTE, 0);
        f.set(Calendar.SECOND, 0);
        f.set(Calendar.MILLISECOND, 1);

        if (f.getTime().before(t.getTime())) {
            Mensajes.advertencia("Fecha menor a la de hoy");
            return true;
        }
        return false;
    }

    public String eliminar() {
        cita = (Citas) listaCitas.get(formulario.getFila().getRowIndex());
        if (validarFecha(cita.getFecha())) {
            return null;
        }
        formulario.eliminar();
        return null;
    }

    public String cancelar() {
        cita.setFecha(new Date());
        cita.setActivo(Boolean.FALSE);
        cita.setActualizado(new Date());
        cita.setActualizadopor(seguridadBean.getLogueado().getUserid());
        cita.setDescripcion(cita.getDescripcion() + " [Cita cancelada por: " + seguridadBean.getLogueado().getUserid() + " - " + format.format(new Date()) + "]");
        try {
            ejbCitas.actualizar(cita, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        cita = null;
        return null;
    }

    public String reactivar() {
        cita = (Citas) listaCitas.get(formulario.getFila().getRowIndex());
        if (validarFecha(cita.getFecha())) {
            return null;
        }
        formulario.editar();
        return null;
    }

    public String grabarReactivar() {
        cita.setFecha(new Date());
        cita.setActivo(Boolean.TRUE);
        cita.setActualizado(new Date());
        cita.setActualizadopor(seguridadBean.getLogueado().getUserid());
        cita.setDescripcion(cita.getDescripcion() + " [Cita reactivada por: " + seguridadBean.getLogueado().getUserid() + " - " + format.format(new Date()) + "]");
        try {
            ejbCitas.actualizar(cita, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        cita = null;
        return null;
    }

    public SelectItem[] getHorasDisponibles() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);

            String where = "o.profesional=:profesional and o.dia.parametros=:dia and o.activo=true";
            Map parametros = new HashMap();
            parametros.put("profesional", profesional);
            /**
             * De la fecha seleccionada se extrae el numero del día de la semana
             * Entoces se busca el horario del profesional médico seleccionado
             */
            parametros.put("dia", calendar.get(Calendar.DAY_OF_WEEK) - 1 + "");
            String order = "o.dia.parametros, o.hora.horainicio asc";
            listaHorarios = ejbHorarios.buscar(where, parametros, order);

            where = "o.profesional=:profesional and o.activo=true and o.fecha between :inicio and :fin";
            parametros = new HashMap();
            parametros.put("profesional", profesional);

            calendar.setTime(fecha);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            parametros.put("inicio", calendar.getTime());

            calendar.setTime(fecha);
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            parametros.put("fin", calendar.getTime());

            List<Citas> auxcitas = ejbCitas.buscar(where, parametros);

            for (Citas c : auxcitas) {
                Calendar horasOcupadas = Calendar.getInstance();
                horasOcupadas.setTime(c.getFecha());
                horasOcupadas.set(Calendar.YEAR, 0);
                horasOcupadas.set(Calendar.MONTH, 0);
                horasOcupadas.set(Calendar.DAY_OF_MONTH, 0);
                for (Horarios h : listaHorarios) {
                    Calendar horaSeleccionada = Calendar.getInstance();
                    horaSeleccionada.setTime(h.getHora().getHorainicio());
                    if (horaSeleccionada.get(Calendar.HOUR_OF_DAY) == horasOcupadas.get(Calendar.HOUR_OF_DAY)) {
                        listaHorarios.remove(h);
                        break;
                    }
                }

            }

            return CombosBean.getSelectItems(listaHorarios, "object", true);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean validar() {
        if (pacientesBean.getPaciente() == null) {
            Mensajes.advertencia("Seleccione un paciente");
            return true;
        }

        if (fecha == null) {
            Mensajes.advertencia("Seleccione una fecha");
            return true;
        }

        if (validarFecha(fecha)) {
            return true;
        }

        if (profesional == null) {
            Mensajes.advertencia("Seleccione un profesional médico");
            return true;
        }
        if (horario == null) {
            Mensajes.advertencia("Seleccione hora de atención");
            return true;
        }
        return false;
    }

    public String grabar() {
        if (validar()) {
            return null;
        }

        cita = new Citas();
        cita.setActivo(Boolean.TRUE);
        cita.setCreado(new Date());
        cita.setCreadopor(seguridadBean.getLogueado().getUserid());
        cita.setActualizado(cita.getCreado());
        cita.setActualizadopor(cita.getCreadopor());

        Calendar h = Calendar.getInstance(); //Hora de la cita
        h.setTime(horario.getHora().getHorainicio());

        Calendar c = Calendar.getInstance(); //Fecha de la cita
        c.setTime(fecha);
        c.set(Calendar.HOUR_OF_DAY, h.get(Calendar.HOUR_OF_DAY)); //Hora de la cita a la fecha
        c.set(Calendar.MINUTE, h.get(Calendar.MINUTE));//Minuto de la cita a la fecha

        cita.setFecha(c.getTime());
        cita.setPaciente(pacientesBean.getPaciente());
        cita.setProfesional(profesional);
        cita.setCreado(new Date());
        cita.setCreadopor(seguridadBean.getLogueado().getUserid());
        cita.setActualizado(cita.getCreado());
        cita.setActualizadopor(cita.getCreadopor());
        cita.setDescripcion("[Cita agendada por: " + seguridadBean.getLogueado().getUserid() + " - " + format.format(new Date()) + "]");
        try {
            ejbCitas.crear(cita, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String verAgenda() {
        ver = Boolean.TRUE;

        Calendar c = Calendar.getInstance(); //Fecha de la cita
        c.setTime(fecha);
        c.set(Calendar.DAY_OF_WEEK, 2);

        inicio = c.getTime();

        c.setTime(fecha);
        c.set(Calendar.DAY_OF_WEEK, 1);

        fin = c.getTime();

        return null;
    }

    public String ocultarAgenda() {
        ver = Boolean.FALSE;
        return null;
    }

    public String getColor(Horas hora, Parametros dia) {
        Map parametros = new HashMap();
        String where = "o.hora=:hora and o.dia=:dia and o.profesional=:profesional";
        parametros.put("hora", hora);
        parametros.put("dia", dia);
        parametros.put("profesional", profesional);

        try {
            List<Horarios> aux = ejbHorarios.buscar(where, parametros);
            if (!aux.isEmpty()) {
                return "#195f88";
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "#FFFFFF";

    }

    public String getReservado(Horas hora, Parametros dia) {
        try {
            String where = "o.profesional=:profesional and o.activo=true and o.fecha between :inicio and :fin";
            Map parametros = new HashMap();
            parametros.put("profesional", profesional);

            Calendar calendar = Calendar.getInstance();
            Calendar calendarHora = Calendar.getInstance();
            int diaActual = calendar.get(Calendar.DAY_OF_WEEK); //Día de la fecha actual
            int diaReferencia = Integer.parseInt(dia.getParametros()) + 1; //Día del que se quiere averiguar si tuvo citas

            calendarHora.setTime(hora.getHorainicio());
            calendar.add(Calendar.DAY_OF_YEAR, (diaActual - diaReferencia) * -1);
            calendar.set(Calendar.HOUR_OF_DAY, calendarHora.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendarHora.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendarHora.get(Calendar.SECOND));
            calendar.set(Calendar.MILLISECOND, calendarHora.get(Calendar.MILLISECOND));
            parametros.put("inicio", calendar.getTime());

            calendarHora.setTime(hora.getHorafin());
            calendar.add(Calendar.DAY_OF_YEAR, (diaActual - diaReferencia) * -1);
            calendar.set(Calendar.HOUR_OF_DAY, calendarHora.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendarHora.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendarHora.get(Calendar.SECOND));
            calendar.set(Calendar.MILLISECOND, calendarHora.get(Calendar.MILLISECOND));
            parametros.put("fin", calendar.getTime());

            List<Citas> auxcitas = ejbCitas.buscar(where, parametros);
            if (!auxcitas.isEmpty()) {
                return auxcitas.get(0).getPaciente().toString();
            } else {
                parametros = new HashMap();
                where = " o.activo = true and o.hora=:hora and o.dia=:dia and o.profesional=:profesional";
                parametros.put("hora", hora);
                parametros.put("dia", dia);
                parametros.put("profesional", profesional);

                if (ejbHorarios.contar(where, parametros) > 0) {
                    return "-";
                }
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public String header() {
        if (profesional == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        return "Agenda del " + f.format(inicio) + " al " + f.format(fin) + " - " + profesional.toString();
    }

    public SelectItem[] getComboCitas() {
        Map parametros = new HashMap();
        String where = "o.fecha=:fecha and o.activo=true and o.profesional=:profesional";
        parametros.put("fecha", new Date());
//        parametros.put("profesional", seguridadBean.getProfesional());
        try {
            return CombosBean.getSelectItems(ejbCitas.buscar(where, parametros), "object", true);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<String> separar(String texto) {
        List<String> lista = new LinkedList<>();
        String[] aux = texto.split("\\]");
        lista.addAll(Arrays.asList(aux));
        return lista;
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
     * @return the listaGrupoUsuarios
     */
    public List<Usuarios> getListaGrupoUsuarios() {
        return listaGrupoUsuarios;
    }

    /**
     * @param listaGrupoUsuarios the listaGrupoUsuarios to set
     */
    public void setListaGrupoUsuarios(List<Usuarios> listaGrupoUsuarios) {
        this.listaGrupoUsuarios = listaGrupoUsuarios;
    }

    /**
     * @return the profesional
     */
    public Profesionales getProfesional() {
        return profesional;
    }

    /**
     * @param profesional the profesional to set
     */
    public void setProfesional(Profesionales profesional) {
        this.profesional = profesional;
    }

    /**
     * @return the paciente
     */
    public Pacientes getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
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
     * @return the listaHorarios
     */
    public List<Horarios> getListaHorarios() {
        return listaHorarios;
    }

    /**
     * @param listaHorarios the listaHorarios to set
     */
    public void setListaHorarios(List<Horarios> listaHorarios) {
        this.listaHorarios = listaHorarios;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the cita
     */
    public Citas getCita() {
        return cita;
    }

    /**
     * @param cita the cita to set
     */
    public void setCita(Citas cita) {
        this.cita = cita;
    }

    /**
     * @return the ver
     */
    public boolean isVer() {
        return ver;
    }

    /**
     * @param ver the ver to set
     */
    public void setVer(boolean ver) {
        this.ver = ver;
    }

    /**
     * @return the inicio
     */
    public Date getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fin
     */
    public Date getFin() {
        return fin;
    }

    /**
     * @param fin the fin to set
     */
    public void setFin(Date fin) {
        this.fin = fin;
    }

    /**
     * @return the listaCitas
     */
    public List<Citas> getListaCitas() {
        return listaCitas;
    }

    /**
     * @param listaCitas the listaCitas to set
     */
    public void setListaCitas(List<Citas> listaCitas) {
        this.listaCitas = listaCitas;
    }

    /**
     * @return the pacientesBean
     */
    public PacientesBean getPacientesBean() {
        return pacientesBean;
    }

    /**
     * @param pacientesBean the pacientesBean to set
     */
    public void setPacientesBean(PacientesBean pacientesBean) {
        this.pacientesBean = pacientesBean;
    }
}
