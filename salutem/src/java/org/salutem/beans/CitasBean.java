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
import org.controladores.salutem.HorariosFacade;
import org.entidades.salutem.Horas;
import org.entidades.salutem.Perfiles;
import org.entidades.salutem.Profesionales;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;

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
    private Perfiles perfil;

    private Formulario formulario = new Formulario();
    private List<Grupousuario> listaGrupoUsuarios;

    private Profesionales profesional;

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
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Citas");

    }

    @Override
    public String buscar() {
        if (fecha == null) {
            fecha = new Date();
        }
        Map parametros = new HashMap();
        parametros.put(";where", " o.fecha>=:fecha");
        parametros.put("fecha", fecha);
        try {
            listaCitas = ejbCitas.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            MensajesErrores.error(ex.getMessage() + ":" + ex.getCause());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private boolean validarFecha(Date fecha) {
        Calendar t = Calendar.getInstance();
        t.setTime(new Date());
        t.set(Calendar.HOUR_OF_DAY, 0);
        t.set(Calendar.MINUTE, 0);

        Calendar f = Calendar.getInstance();
        f.setTime(fecha);
        f.set(Calendar.HOUR_OF_DAY, 0);
        f.set(Calendar.MINUTE, 1);

        if (f.getTime().before(t.getTime())) {
            MensajesErrores.advertencia("Fecha menor a la de hoy");
            return true;
        }
        return false;
    }

    @Override
    public String eliminar() {
//        cita = (Citas) listaCitas.get(formulario.getFila().getRowIndex());
        if (validarFecha(cita.getFecha())) {
            return null;
        }
        formulario.eliminar();
        return null;
    }

    public String borrar() {
        cita.setFecha(new Date());
        cita.setActivo(Boolean.FALSE);
        cita.setUsuario(seguridadBean.getLogueado().getUserid());
        cita.setObservaciones(cita.getObservaciones() + " [Cita cancelada por: " + seguridadBean.getLogueado().getUserid() + " - " + format.format(new Date()) + "]");
        try {
            ejbCitas.edit(cita, seguridadBean.getLogueado().getUserid());
        } catch (GrabarException ex) {
            MensajesErrores.error(ex.getMessage() + ":" + ex.getCause());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        } 
        formulario.cancelar();
        cita = null;
        return null;
    }

    public String reactivar() {
//        cita = (Citas) listaCitas.get(formulario.getFila().getRowIndex());

        if (validarFecha(cita.getFecha())) {
            return null;
        }

        formulario.editar();

        return null;
    }

    public String grabarReactivar() {
        cita.setFecha(new Date());
        cita.setActivo(Boolean.TRUE);
        cita.setUsuario(seguridadBean.getLogueado().getUserid());
        cita.setObservaciones(cita.getObservaciones() + " [Cita reactivada por: " + seguridadBean.getLogueado().getUserid() + " - " + format.format(new Date()) + "]");
        try {
            ejbCitas.edit(cita, seguridadBean.getLogueado().getUserid());
        } catch (GrabarException ex) {
            MensajesErrores.error(ex.getMessage() + ":" + ex.getCause());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        cita = null;
        return null;
    }

    public SelectItem[] getHorasDisponibles() {
        try {
            listaHoras = new LinkedList<>();
            Map parametros = new HashMap();
            parametros.put(";where", "o.profesional=:profesional and o.dia.descripcion=:dia");
            parametros.put("profesional", profesional);
            Calendar f = Calendar.getInstance();
            f.setTime(fecha);
            parametros.put("dia", "" + f.get(Calendar.DAY_OF_WEEK));
            parametros.put(";orden", "o.dia.parametros, o.hora.horainicio asc");
//            List<Horarios> aux = ejbHorarios.encontarParametros(parametros);

//            for (Horarios h : aux) {
//                listaHoras.add(h.getHora());
//            }

            parametros = new HashMap();
            parametros.put(";where", "o.profesional=:profesional and o.activo=true and o.fecha=:fecha");
            parametros.put("profesional", profesional);
            parametros.put("fecha", fecha);
            List<Citas> auxcitas = ejbCitas.encontarParametros(parametros);

            for (Citas c : auxcitas) {
                Calendar cd = Calendar.getInstance();
                cd.setTime(c.getHora());
                for (Horas h : listaHoras) {
                    Calendar ch = Calendar.getInstance();
                    ch.setTime(h.getHorainicio());
                    if (ch.get(Calendar.HOUR_OF_DAY) == cd.get(Calendar.HOUR_OF_DAY)) {
                        listaHoras.remove(h);
                        break;
                    }
                }

            }

            return Combos.SelectItems(listaHoras, true);
        } catch (ConsultarException ex) {
            MensajesErrores.error(ex.getMessage() + ":" + ex.getCause());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean validar() {
//        if (seguridadBean.getPaciente() == null) {
//            MensajesErrores.advertencia("Seleccione un paciente");
//            return true;
//        }

        if (fecha == null) {
            MensajesErrores.advertencia("Seleccione una fecha");
            return true;
        }

        if (validarFecha(fecha)) {
            return true;
        }

        if (profesional == null) {
            MensajesErrores.advertencia("Seleccione un profesional médico");
            return true;
        }
        if (hora == null) {
            MensajesErrores.advertencia("Seleccione hora");
            return true;
        }
        return false;
    }

    @Override
    public String grabar() {
        if (validar()) {
            return null;
        }

        cita = new Citas();
        cita.setActivo(Boolean.TRUE);
        cita.setRegistro(new Date());

        Calendar h = Calendar.getInstance(); //Hora de la cita
        h.setTime(hora.getHorainicio());

        Calendar c = Calendar.getInstance(); //Fecha de la cita
        c.setTime(fecha);
        c.set(Calendar.HOUR_OF_DAY, h.get(Calendar.HOUR_OF_DAY)); //Hora de la cita a la fecha
        c.set(Calendar.MINUTE, h.get(Calendar.MINUTE));//Minuto de la cita a la fecha

        cita.setFecha(c.getTime());
        cita.setHora(c.getTime());
//        cita.setPaciente(seguridadBean.getPaciente());
        cita.setProfesional(profesional);
        cita.setUsuario(seguridadBean.getLogueado().getUserid());
        cita.setObservaciones("[Cita agendada por: " + seguridadBean.getLogueado().getUserid() + " - " + format.format(new Date()) + "]");
        try {
            ejbCitas.create(cita, seguridadBean.getLogueado().getUserid());
        } catch (InsertarException ex) {
            MensajesErrores.error(ex.getMessage() + ":" + ex.getCause());
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

    public String getColor(Horas hora, Codigos dia) {
        Map parametros = new HashMap();
        parametros.put(";where", "o.hora=:hora and o.dia=:dia and o.profesional=:profesional");
        parametros.put("hora", hora);
        parametros.put("dia", dia);
        parametros.put("profesional", profesional);

        try {
//            List<Horarios> aux = ejbHorarios.encontarParametros(parametros);
//            if (!aux.isEmpty()) {
//                return "#195f88";
//            }
        } catch (ConsultarException ex) {
            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "#FFFFFF";

    }

    public String getColorReserva(Horas hora, Codigos dia) {
        try {
            Map parametros = new HashMap();
            parametros.put(";where", "o.profesional=:profesional and o.activo=true and o.fecha=:fecha and o.hora=:hora");
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
            List<Citas> auxcitas = ejbCitas.encontarParametros(parametros);
            if (!auxcitas.isEmpty()) {
//                return auxcitas.get(0).getPaciente().toString();
            }

        } catch (ConsultarException ex) {
            MensajesErrores.error(ex.getMessage() + ":" + ex.getCause());
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

    public Citas traer(Integer id) throws ConsultarException {
        return ejbCitas.find(id);
    }

    public SelectItem[] getComboCitas() {
        Map parametros = new HashMap();
        parametros.put(";where", "o.fecha=:fecha and o.activo=true and o.profesional=:profesional");
        parametros.put("fecha", new Date());
//        parametros.put("profesional", seguridadBean.getProfesional());
        try {
            return Combos.SelectItems(ejbCitas.encontarParametros(parametros), true);
        } catch (ConsultarException ex) {
            MensajesErrores.error(ex.getMessage() + ":" + ex.getCause());
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

    @Override
    public String crear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String editar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insertar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String remover() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String cancelar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
