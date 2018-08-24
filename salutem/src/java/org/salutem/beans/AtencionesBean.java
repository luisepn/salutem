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
import org.controladores.salutem.AtencionesFacade;
import org.controladores.salutem.HorariosFacade;
import org.entidades.salutem.Atenciones;
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
 * @since 24 de Agosto de 2018, 15:46:28 PM
 */
@ManagedBean(name = "salutemAtenciones")
@ViewScoped
public class AtencionesBean implements Serializable, IMantenimiento {

    @ManagedProperty(value = "#{salutemSeguridad}")
    private SeguridadBean seguridadBean;
    @ManagedProperty(value = "#{salutemPacientes}")
    private PacientesBean pacientesBean;

    private Perfiles perfil;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Atenciones> atenciones;

    private Profesionales profesional;
    private Pacientes paciente;

    private Horarios horario;
    private List<Horarios> listaHorarios;
    private Date fecha = new Date();
    private Atenciones atencion;
    private String observaciones;

    private boolean ver = false;
    private Date inicio;
    private Date fin;

    private Date fechaInicio;
    private Date fechaFin;

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @EJB
    private HorariosFacade ejbHorarios;
    @EJB
    private AtencionesFacade ejbAtenciones;

    public AtencionesBean() {
        fecha = new Date();
        atenciones = new LazyDataModel<Atenciones>() {
            @Override
            public List<Atenciones> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        perfil = seguridadBean.traerPerfil("AtencionesConsultasPacientes");
    }

    private List<Atenciones> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
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
            if (fechaInicio != null && fechaFin != null) {
                where += " and o.fecha between :fechainicio and :fechafin";
                parameters.put("fechainicio", fechaInicio);
                parameters.put("fechafin", fechaFin);
            }
            int total = ejbAtenciones.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            atenciones.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.fecha";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbAtenciones.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
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
        atenciones = new LazyDataModel<Atenciones>() {
            @Override
            public List<Atenciones> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        atencion = (Atenciones) atenciones.getRowData();
        if (validarFecha(atencion.getFecha())) {
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
        atencion = (Atenciones) atenciones.getRowData();
        if (validarFecha(atencion.getFecha())) {
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

        atencion = new Atenciones();
        atencion.setActivo(Boolean.TRUE);
        atencion.setCreado(new Date());
        atencion.setCreadopor(seguridadBean.getLogueado().getUserid());
        atencion.setActualizado(atencion.getCreado());
        atencion.setActualizadopor(atencion.getCreadopor());

        Calendar h = Calendar.getInstance(); //Hora de la cita
        h.setTime(horario.getHora().getHorainicio());

        Calendar c = Calendar.getInstance(); //Fecha de la cita
        c.setTime(fecha);
        c.set(Calendar.HOUR_OF_DAY, h.get(Calendar.HOUR_OF_DAY)); //Hora de la cita a la fecha
        c.set(Calendar.MINUTE, h.get(Calendar.MINUTE));//Minuto de la cita a la fecha

        atencion.setFecha(c.getTime());
        atencion.setPaciente(pacientesBean.getPaciente());
        atencion.setProfesional(profesional);
        atencion.setCreado(new Date());
        atencion.setCreadopor(seguridadBean.getLogueado().getUserid());
        atencion.setActualizado(atencion.getCreado());
        atencion.setActualizadopor(atencion.getCreadopor());

        try {
            ejbAtenciones.crear(atencion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        observaciones = null;
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
        atencion.setActualizado(new Date());
        atencion.setActualizadopor(seguridadBean.getLogueado().getUserid());
        try {
            ejbAtenciones.actualizar(atencion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
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
            ejbAtenciones.eliminar(atencion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
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

            List<Atenciones> auxcitas = ejbAtenciones.buscar(where, parametros);

            for (Atenciones c : auxcitas) {
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
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String verAgenda() {
        ver = Boolean.TRUE;

        Calendar c = Calendar.getInstance();//Fecha de la cita
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
            int diaReferencia = Integer.parseInt(dia.getParametros()) + 1; //Día del que se quiere averiguar si tuvo atenciones

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

            List<Atenciones> auxcitas = ejbAtenciones.buscar(where, parametros);
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
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public SelectItem[] getComboAtenciones() {
        Map parametros = new HashMap();
        String where = "o.fecha=:fecha and o.activo=true and o.profesional=:profesional";
        parametros.put("fecha", new Date());
//        parametros.put("profesional", seguridadBean.getProfesional());
        try {
            return CombosBean.getSelectItems(ejbAtenciones.buscar(where, parametros), "object", true);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<String> separar(String texto) {
        List<String> lista = new LinkedList<>();
        String[] aux = texto.split("\\]");
        lista.addAll(Arrays.asList(aux));
        return lista;
    }

    public String getNombreTabla() {
        return Atenciones.class.getSimpleName();
    }

}
