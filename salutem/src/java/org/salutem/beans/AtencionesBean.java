/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.beans;

import java.io.Serializable;
import java.util.Calendar;
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
import org.controladores.salutem.AtencionesFacade;
import org.controladores.salutem.PrescripcionesFacade;
import org.entidades.salutem.Atenciones;
import org.entidades.salutem.Citas;
import org.entidades.salutem.Datos;
import org.entidades.salutem.Formulas;
import org.entidades.salutem.Perfiles;
import org.entidades.salutem.Prescripciones;
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
    @ManagedProperty(value = "#{salutemCombos}")
    private CombosBean combosBean;
    @ManagedProperty(value = "#{salutemDatos}")
    private DatosBean datosBean;

    private Perfiles perfil;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Atenciones> atenciones;
    private Formulas formula;
    private List<Prescripciones> prescripciones;

    private Atenciones atencion;
    private Boolean conCita;
    private Citas cita;

    private Date fechaInicio;
    private Date fechaFin;

    @EJB
    private AtencionesFacade ejbAtenciones;
    @EJB
    private PrescripcionesFacade ejbPrescripciones;

    public AtencionesBean() {
        conCita = true;
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
            Map parameters = new HashMap();
            String where = " o.activo=:activo";
            parameters.put("activo", seguridadBean.getVerActivos());
            for (Map.Entry e : map.entrySet()) {
                String clave = (String) e.getKey();
                String valor = (String) e.getValue();
                where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
            }
            if (combosBean.getProfesional() != null) {
                where += " and o.profesional=:profesional";
                parameters.put("profesional", combosBean.getProfesional());
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
                order = "o.fecha desc";
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
        try {
            prescripciones = ejbPrescripciones.traerPrescripciones(atencion);
            datosBean.iniciar(getNombreTabla(), combosBean.getProfesional().getEspecialidad(), atencion.getId());
            datosBean.crear();
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            prescripciones = ejbPrescripciones.traerPrescripciones(atencion);
            datosBean.iniciar(getNombreTabla(), combosBean.getProfesional().getEspecialidad(), atencion.getId());
            datosBean.buscar();
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
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
            if (combosBean.getProfesional() == null) {
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
        try {
            atencion = new Atenciones();
            atencion.setFecha(new Date());
            Map parameters = new HashMap();
            if (conCita) {
                parameters.put("cita", cita);
                if (ejbAtenciones.contar("o.cita=:cita", parameters) > 0) {
                    Mensajes.advertencia("Ya existe una atención generada para la cita seleccionada");
                    return null;
                }
                atencion.setCita(cita);
                atencion.setPaciente(cita.getPaciente());
                atencion.setProfesional(cita.getProfesional());
            } else {
                parameters.put("profesional", combosBean.getProfesional());
                parameters.put("paciente", pacientesBean.getPaciente());
                parameters.put("paciente", pacientesBean.getPaciente());
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                parameters.put("inicio", calendar.getTime());
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                parameters.put("fin", calendar.getTime());
                if (ejbAtenciones.contar("o.profesional=:profesional and o.paciente=:paciente and o.fecha between :inicio and :fin", parameters) > 0) {
                    Mensajes.advertencia("Ya existe una atención generada para el paciente seleccionado");
                    return null;
                }
                atencion.setPaciente(pacientesBean.getPaciente());
                atencion.setProfesional(combosBean.getProfesional());
            }
            atencion.setEspecialidad(atencion.getProfesional().getEspecialidad());

            atencion.setCreado(new Date());
            atencion.setCreadopor(seguridadBean.getLogueado().getUserid());
            atencion.setActualizado(atencion.getCreado());
            atencion.setActualizadopor(atencion.getCreadopor());
            atencion.setActivo(Boolean.TRUE);

            ejbAtenciones.crear(atencion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            datosBean.iniciar(getNombreTabla(), combosBean.getProfesional().getEspecialidad(), atencion.getId());
            datosBean.crear();
        } catch (ExcepcionDeCreacion | ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.editar();
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

        return f.getTime().before(t.getTime());
    }

    @Override
    public String grabar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        atencion.setActualizado(new Date());
        atencion.setActualizadopor(seguridadBean.getLogueado().getUserid());
        try {
            datosBean.grabar();
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

    public String crearPrescripcion() {
        Prescripciones prescripcion = new Prescripciones();
        prescripcion.setAtencion(atencion);
        prescripcion.setCreado(new Date());
        prescripcion.setCreadopor(seguridadBean.getLogueado().getUserid());
        prescripcion.setActualizado(prescripcion.getCreado());
        prescripcion.setActualizadopor(prescripcion.getCreadopor());
        prescripcion.setActivo(Boolean.TRUE);
        try {
            ejbPrescripciones.crear(prescripcion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            prescripciones = ejbPrescripciones.traerPrescripciones(atencion);
        } catch (ExcepcionDeCreacion | ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String grabarPrescripciones() {
        try {
            for (Prescripciones p : prescripciones) {
                p.setActualizado(new Date());
                p.setActualizadopor(seguridadBean.getLogueado().getUserid());
                ejbPrescripciones.actualizar(p, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }
            prescripciones = ejbPrescripciones.traerPrescripciones(atencion);
            Mensajes.informacion("¡Prescripciones grabadas con éxito!");
        } catch (ExcepcionDeActualizacion | ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String eliminarPrescripcion(int index) {
        try {
            ejbPrescripciones.eliminar(prescripciones.get(index), seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            prescripciones = ejbPrescripciones.traerPrescripciones(atencion);
        } catch (ExcepcionDeEliminacion | ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
     * @return the datosBean
     */
    public DatosBean getDatosBean() {
        return datosBean;
    }

    /**
     * @param datosBean the datosBean to set
     */
    public void setDatosBean(DatosBean datosBean) {
        this.datosBean = datosBean;
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
     * @return the atenciones
     */
    public LazyDataModel<Atenciones> getAtenciones() {
        return atenciones;
    }

    /**
     * @param atenciones the atenciones to set
     */
    public void setAtenciones(LazyDataModel<Atenciones> atenciones) {
        this.atenciones = atenciones;
    }

    /**
     * @return the atencion
     */
    public Atenciones getAtencion() {
        return atencion;
    }

    /**
     * @param atencion the atencion to set
     */
    public void setAtencion(Atenciones atencion) {
        this.atencion = atencion;
    }

    /**
     * @return the conCita
     */
    public Boolean getConCita() {
        return conCita;
    }

    /**
     * @param conCita the conCita to set
     */
    public void setConCita(Boolean conCita) {
        this.conCita = conCita;
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
     * @return the formula
     */
    public Formulas getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    /**
     * @return the prescripciones
     */
    public List<Prescripciones> getPrescripciones() {
        return prescripciones;
    }

    /**
     * @param prescripciones the prescripciones to set
     */
    public void setPrescripciones(List<Prescripciones> prescripciones) {
        this.prescripciones = prescripciones;
    }
}
