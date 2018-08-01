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
    private Perfiles perfil;

    private Formulario formulario = new Formulario();
    private List<Usuarios> listaGrupoUsuarios;

    private Profesionales profesional;
    private Pacientes paciente;

    private Horas hora;
    private List<Horas> listaHoras;
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
        perfil = seguridadBean.traerPerfil("Citas");
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
            listaHoras = new LinkedList<>();
            String where = "o.profesional=:profesional and o.dia.descripcion=:dia";
            Map parametros = new HashMap();
            parametros.put("profesional", profesional);
            Calendar f = Calendar.getInstance();
            f.setTime(fecha);
            parametros.put("dia", f.get(Calendar.DAY_OF_WEEK) + "");
            String order = "o.dia.parametros, o.hora.horainicio asc";
            List<Horarios> aux = ejbHorarios.buscar(where, parametros, order);

            for (Horarios h : aux) {
                listaHoras.add(h.getHora());
            }
            where = "o.profesional=:profesional and o.activo=true and o.fecha=:fecha";
            parametros = new HashMap();
            parametros.put("profesional", profesional);
            parametros.put("fecha", fecha);
            List<Citas> auxcitas = ejbCitas.buscar(where, parametros);

            for (Citas c : auxcitas) {
                Calendar cd = Calendar.getInstance();
                cd.setTime(c.getFecha());
                cd.set(Calendar.YEAR, 0);
                cd.set(Calendar.MONTH, 0);
                cd.set(Calendar.DAY_OF_MONTH, 0);
                for (Horas h : listaHoras) {
                    Calendar ch = Calendar.getInstance();
                    ch.setTime(h.getHorainicio());
                    if (ch.get(Calendar.HOUR_OF_DAY) == cd.get(Calendar.HOUR_OF_DAY)) {
                        listaHoras.remove(h);
                        break;
                    }
                }

            }

            return CombosBean.getSelectItems(listaHoras, "object", true);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean validar() {
//        if (seguridadBean.getPaciente() == null) {
//            Mensajes.advertencia("Seleccione un paciente");
//            return true;
//        }

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
        if (hora == null) {
            Mensajes.advertencia("Seleccione hora");
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
        h.setTime(hora.getHorainicio());

        Calendar c = Calendar.getInstance(); //Fecha de la cita
        c.setTime(fecha);
        c.set(Calendar.HOUR_OF_DAY, h.get(Calendar.HOUR_OF_DAY)); //Hora de la cita a la fecha
        c.set(Calendar.MINUTE, h.get(Calendar.MINUTE));//Minuto de la cita a la fecha

        cita.setFecha(c.getTime());
//        cita.setPaciente(seguridadBean.getPaciente());
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

    public String getColorReserva(Horas hora, Parametros dia) {
        try {
            Map parametros = new HashMap();
            String where = "o.profesional=:profesional and o.activo=true and o.fecha=:fecha and o.hora=:hora";
            parametros.put("profesional", profesional);

            Calendar h = Calendar.getInstance(); //Hora de la cita
            h.setTime(hora.getHorainicio());

            Calendar c = Calendar.getInstance(); //Fecha de la cita
            c.setTime(fecha);
            c.set(Calendar.DAY_OF_WEEK, Integer.parseInt(dia.getDescripcion()));
            c.set(Calendar.HOUR_OF_DAY, h.get(Calendar.HOUR_OF_DAY)); //Hora de la cita a la fecha
            c.set(Calendar.MINUTE, h.get(Calendar.MINUTE));//Minuto de la cita a la fecha

            parametros.put("fecha", c.getTime());
            parametros.put("hora", hora.getHorainicio());
            List<Citas> auxcitas = ejbCitas.buscar(where, parametros);
            if (!auxcitas.isEmpty()) {
                return auxcitas.get(0).getPaciente().toString();
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
}
