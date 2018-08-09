/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.beans;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.controladores.salutem.HistorialFacade;
import org.controladores.salutem.HorariosFacade;
import org.entidades.salutem.Citas;
import org.entidades.salutem.Historial;
import org.entidades.salutem.Horarios;
import org.entidades.salutem.Horas;
import org.entidades.salutem.Pacientes;
import org.entidades.salutem.Parametros;
import org.entidades.salutem.Perfiles;
import org.entidades.salutem.Profesionales;
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
 * @since 25 de Julio de 2018, 11:13:55 AM
 */
@ManagedBean(name = "salutemCitas")
@ViewScoped
public class CitasBean implements Serializable, IMantenimiento {

    @ManagedProperty(value = "#{salutemSeguridad}")
    private SeguridadBean seguridadBean;
    @ManagedProperty(value = "#{salutemPacientes}")
    private PacientesBean pacientesBean;

    private Perfiles perfil;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Citas> citas;

    private Profesionales profesional;
    private Pacientes paciente;

    private Horarios horario;
    private List<Horarios> listaHorarios;
    private Date fecha = new Date();
    private Citas cita;

    private boolean ver = false;
    private Date inicio;
    private Date fin;

    private Date fechaInicio;
    private Date fechaFin;

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @EJB
    private HorariosFacade ejbHorarios;
    @EJB
    private CitasFacade ejbCitas;
    @EJB
    private HistorialFacade ejbHistorial;

    public CitasBean() {
        fecha = new Date();
        citas = new LazyDataModel<Citas>() {
            @Override
            public List<Citas> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                } else {
                    return cargar(i, i1, scs, map);
                }
            }
        };
    }

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("CitasConsultasPacientes");
    }

    private List<Citas> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            if (fecha == null) {
                fecha = new Date();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Map parameters = new HashMap();
            String where = " o.activo=:activo and o.fecha>=:fecha";
            parameters.put("activo", seguridadBean.getVerActivos());
            parameters.put("fecha", calendar.getTime());
            for (Map.Entry e : map.entrySet()) {
                String clave = (String) e.getKey();
                String valor = (String) e.getValue();
                where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
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

            int total = ejbCitas.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            citas.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.fecha";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbCitas.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        if (fecha == null) {
            fecha = new Date();
        }
        citas = new LazyDataModel<Citas>() {
            @Override
            public List<Citas> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        cita = (Citas) citas.getRowData();
        if (validarFecha(cita.getFecha())) {
            Mensajes.advertencia("No se puede editar citas con fechas menores a la de hoy");
            return null;
        }
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        cita = (Citas) citas.getRowData();
        if (validarFecha(cita.getFecha())) {
            Mensajes.advertencia("No se puede eliminar citas con fechas menores a la de hoy");
            return null;
        }
        formulario.eliminar();
        return null;
    }

    @Override
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
            Mensajes.advertencia("No se puede realizar citas con fechas menores a la de hoy");
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

    @Override
    public String insertar() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
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
            return true;
        }
        return false;
    }

    @Override
    public String grabar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        return null;
    }

    public String cancelarCita() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        if (validarFecha(cita.getFecha())) {
            Mensajes.advertencia("No se puede modificar citas con fechas menores a la de hoy");
            return null;
        }
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
        return null;
    }

    public String reactivarCita() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        if (validarFecha(cita.getFecha())) {
            Mensajes.advertencia("No se puede modificar citas con fechas menores a la de hoy");
            return null;
        }
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
        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbCitas.eliminar(cita, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public SelectItem[] getHorasDisponibles() {
        try {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            if (fecha.before(calendar.getTime())) {
                return null;
            }

            calendar.setTime(fecha);

            String where = "o.profesional=:profesional and o.dia.parametros=:dia and o.hora.horainicio>=:hora and o.activo=true";
            Map parametros = new HashMap();
            parametros.put("profesional", profesional);
            /**
             * De la fecha seleccionada se extrae el numero del día de la semana
             * Entoces se busca el horario del profesional médico seleccionado
             */
            parametros.put("dia", calendar.get(Calendar.DAY_OF_WEEK) == 1 ? "7" : (calendar.get(Calendar.DAY_OF_WEEK) - 1) + "");

            calendar = Calendar.getInstance();
            if (fecha.after(calendar.getTime())) {
                calendar.set(Calendar.HOUR_OF_DAY, 0);
            }

            calendar.set(Calendar.YEAR, 1970);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            parametros.put("hora", calendar.getTime());
            String order = "o.dia.parametros, o.hora.horainicio asc";
            listaHorarios = ejbHorarios.buscar(where, parametros, order);

            where = "o.profesional=:profesional and o.activo=true and o.fecha between :inicio and :fin";
            parametros = new HashMap();
            parametros.put("profesional", profesional);

            calendar.setTime(fecha);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            parametros.put("inicio", calendar.getTime());

            calendar.setTime(fecha);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
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

    public String verAgenda() {
        ver = Boolean.TRUE;

        Calendar c = Calendar.getInstance(); //Fecha de la cita
        c.setTime(fecha);
        c.set(Calendar.DAY_OF_WEEK, 2);//Dia Lunes

        inicio = c.getTime();

        c.setTime(fecha);
        c.set(Calendar.DAY_OF_WEEK, 1);//Día Domingo

        fin = c.getTime();

        return null;
    }

    public String ocultarAgenda() {
        ver = Boolean.FALSE;
        return null;
    }

    public String getColor(Horas hora, Parametros dia) {
        Map parametros = new HashMap();
        String where = "o.hora=:hora and o.dia=:dia and o.activo=true and o.profesional=:profesional";
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
            String where = "o.profesional=:profesional and o.activo=true and o.fecha>=:inicio and o.fecha<:fin";
            Map parametros = new HashMap();
            parametros.put("profesional", profesional);

            Calendar calendar = Calendar.getInstance();
            Calendar calendarHora = Calendar.getInstance();
            int diaActual = calendar.get(Calendar.DAY_OF_WEEK); //Día de la fecha actual
            int diaReferencia = Integer.parseInt(dia.getParametros()) + 1; //Día del que se quiere averiguar si tuvo citas

            calendar = Calendar.getInstance();
            calendarHora.setTime(hora.getHorainicio());
            calendar.add(Calendar.DAY_OF_YEAR, (diaActual - diaReferencia) * -1);
            calendar.set(Calendar.HOUR_OF_DAY, calendarHora.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendarHora.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendarHora.get(Calendar.SECOND));
            calendar.set(Calendar.MILLISECOND, calendarHora.get(Calendar.MILLISECOND));
            parametros.put("inicio", calendar.getTime());

            calendar = Calendar.getInstance();
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
     * @return the citas
     */
    public LazyDataModel<Citas> getCitas() {
        return citas;
    }

    /**
     * @param citas the citas to set
     */
    public void setCitas(LazyDataModel<Citas> citas) {
        this.citas = citas;
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
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}
