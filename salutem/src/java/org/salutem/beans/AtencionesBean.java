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
import org.entidades.salutem.Citas;
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
    @ManagedProperty(value = "#{salutemDatos}")
    private DatosBean datosBean;

    private Perfiles perfil;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Atenciones> atenciones;

    private Profesionales profesional;
    private Atenciones atencion;
    private Boolean conCita;
    private Citas cita;

    private Date fechaInicio;
    private Date fechaFin;

    @EJB
    private AtencionesFacade ejbAtenciones;

    public AtencionesBean() {
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
            Calendar calendar = Calendar.getInstance();
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
        insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        atencion = (Atenciones) atenciones.getRowData();
        if (validarFecha(atencion.getFecha())) {
            Mensajes.advertencia("No se puede editar atenciones con fechas menores a la de hoy");
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
            Mensajes.advertencia("No se puede eliminar atenciones con fechas menores a la de hoy");
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
        if (conCita) {
            if (cita == null) {
                Mensajes.advertencia("Seleccione una cita");
                return true;
            }
        } else {
            if (pacientesBean.getPaciente() == null) {
                Mensajes.advertencia("Seleccione un paciente");
                return true;
            }
            if (profesional == null) {
                Mensajes.advertencia("Seleccione un profesional médico");
                return true;
            }
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

        atencion.setFecha(new Date());
        if (conCita) {
            if (cita == null) {
                atencion.setPaciente(cita.getPaciente());
                atencion.setProfesional(cita.getProfesional());
            }
        } else {
            atencion.setPaciente(pacientesBean.getPaciente());
            atencion.setProfesional(profesional);
        }

        atencion.setCreado(new Date());
        atencion.setCreadopor(seguridadBean.getLogueado().getUserid());
        atencion.setActualizado(atencion.getCreado());
        atencion.setActualizadopor(atencion.getCreadopor());

        try {
            ejbAtenciones.crear(atencion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            datosBean.crear(getNombreTabla(), profesional.getEspecialidad(), atencion.getId());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getNombreTabla() {
        return Atenciones.class.getSimpleName();
    }

}
