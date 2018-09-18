/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.beans;

import java.io.Serializable;
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
import javax.enterprise.inject.Any;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.controladores.salutem.AtencionesFacade;
import org.controladores.salutem.FormulasFacade;
import org.controladores.salutem.OrdenesFacade;
import org.controladores.salutem.PrescripcionesFacade;
import org.entidades.salutem.Atenciones;
import org.entidades.salutem.Citas;
import org.entidades.salutem.Datos;
import org.entidades.salutem.Formulas;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Ordenes;
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
import org.utilitarios.salutem.Ojos;
import org.utilitarios.salutem.RxFinal;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 24 de Agosto de 2018, 15:46:28 PM
 */
@Named("salutemAtenciones")
@ViewScoped
public class AtencionesBean implements Serializable, IMantenimiento {

    @Inject
    private SeguridadBean seguridadBean;
    @Inject
    @Any
    private PacientesBean pacientesBean;
    @Inject
    @Any
    private CombosBean combosBean;
    @Inject
    @Any
    private DatosBean datosBean;

    private Perfiles perfil;

    private LazyDataModel<Atenciones> atenciones;
    private Formulario formulario = new Formulario();

    private List<Prescripciones> prescripciones;
    private List<RxFinal> listaRxFinal;
    private Ojos lensometria;
    private Ojos agudezavisualsincristal;
    private Ojos agudezavisualconcristal;
    private Formulas formula;
    private Ordenes orden;

    private Instituciones laboratorio;

    private Atenciones atencion;
    private Boolean conCita;
    private Citas cita;
    private Date fechaInicio;
    private Date fechaFin;

    private Atenciones ultimaAtencion;
    private List<Prescripciones> ultimasPrescripciones;
    private List<Datos> ultimosDatos;
    private Formulas ultimaFormula;
    private Ordenes ultimaOrden;
    private Ojos ultimaLensometria;
    private Ojos ultimaAgudezavisualsincristal;
    private Ojos ultimaAgudezavisualconcristal;
    private List<RxFinal> ultimaListaRxFinal;

    private Boolean verHistorico = Boolean.FALSE;
    private Integer idUltimaAtencion = 0;

    @EJB
    private AtencionesFacade ejbAtenciones;
    @EJB
    private PrescripcionesFacade ejbPrescripciones;
    @EJB
    private FormulasFacade ejbFormulas;
    @EJB
    private OrdenesFacade ejbOrdenes;

    public AtencionesBean() {
        conCita = true;
        atenciones = new LazyDataModel<Atenciones>() {
            @Override
            public List<Atenciones> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                } else {
                    if (getCombosBean().getProfesional() != null) {
                        map.put("profesional.id", getCombosBean().getProfesional().getId().toString());
                    }
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
                if (getCombosBean().getProfesional() != null) {
                    map.put("profesional.id", getCombosBean().getProfesional().getId().toString());
                }
                return cargar(i, i1, scs, map);
            }
        };
        return null;
    }

    public String buscarHistorico() {
        atenciones = new LazyDataModel<Atenciones>() {
            @Override
            public List<Atenciones> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                map.put("profesional.institucion.id", getAtencion().getProfesional().getInstitucion().getId().toString());
                map.put("especialidad.id", getAtencion().getEspecialidad().getId().toString());
                map.put("paciente.id", getAtencion().getPaciente().getId().toString());
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
            crearFormula();
            prescripciones = ejbPrescripciones.traerPrescripciones(atencion);

        } catch (ExcepcionDeConsulta | ExcepcionDeCreacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.editar();
        datosBean.iniciar(getNombreTabla(), combosBean.getProfesional().getEspecialidad(), atencion.getId(), formulario);
        buscarUltimaAtencion();
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
            crearFormula();
            prescripciones = ejbPrescripciones.traerPrescripciones(atencion);
        } catch (ExcepcionDeConsulta | ExcepcionDeCreacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.eliminar();
        datosBean.iniciar(getNombreTabla(), combosBean.getProfesional().getEspecialidad(), atencion.getId(), formulario);
        buscarUltimaAtencion();
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
                parameters.put("especialidad", combosBean.getProfesional().getEspecialidad());
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
                if (ejbAtenciones.contar("o.profesional=:profesional and o.paciente=:paciente and o.especialidad=:especialidad and o.fecha between :inicio and :fin", parameters) > 0) {
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

            crearFormula();

        } catch (ExcepcionDeCreacion | ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.editar();
        datosBean.iniciar(getNombreTabla(), combosBean.getProfesional().getEspecialidad(), atencion.getId(), formulario);
        buscarUltimaAtencion();
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
            if (datosBean.grabar() == null) {
                return null;
            }
            grabarPrescripciones();
            ejbAtenciones.actualizar(atencion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            grabarFormula();
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        cancelar();
        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            List<Prescripciones> prescripcionesBorrar = ejbPrescripciones.traerPrescripcionesTodas(atencion);
            for (Prescripciones p : prescripcionesBorrar) {
                ejbPrescripciones.eliminar(p, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }
            List<Formulas> formulasBorrar = ejbFormulas.traerFormulasTodas(atencion);
            for (Formulas f : formulasBorrar) {
                List<Ordenes> ordenesBorrar = ejbOrdenes.traerOrdenesTodas(f);
                for (Ordenes o : ordenesBorrar) {
                    ejbOrdenes.eliminar(o, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                }
                ejbFormulas.eliminar(f, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }

            ejbAtenciones.eliminar(atencion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            datosBean.borrar();
        } catch (ExcepcionDeConsulta | ExcepcionDeEliminacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        cancelar();
        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        buscar();
        formula = null;
        orden = null;
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
            if (!prescripciones.isEmpty()) {
                Mensajes.informacion("¡Prescripciones grabadas con éxito!");
            }
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

    public String buscarUltimaAtencion() {
        try {
            this.idUltimaAtencion = 0;
            this.ultimaAtencion = null;
            this.ultimasPrescripciones = null;
            this.ultimosDatos = null;
            String where = "o.profesional.institucion=:institucion and o.especialidad=:especialidad and o.paciente=:paciente and o.id!=:id";
            Map parametros = new HashMap();
            parametros.put("institucion", this.atencion.getProfesional().getInstitucion());
            parametros.put("especialidad", this.atencion.getEspecialidad());
            parametros.put("paciente", this.atencion.getPaciente());
            parametros.put("id", this.atencion.getId());
            String order = "o.fecha desc";
            List<Atenciones> aux = ejbAtenciones.buscar(where, parametros, order, 0, 1);
            if (!aux.isEmpty()) {
                colocarUltimaAtencion(aux.get(0));
                idUltimaAtencion = ultimaAtencion.getId();
                buscarHistorico();
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void colocarUltimaAtencion(Atenciones ultimaAtencion) {
        try {
            this.ultimaAtencion = ultimaAtencion;
            this.ultimasPrescripciones = ejbPrescripciones.traerPrescripciones(ultimaAtencion);
            this.ultimosDatos = datosBean.traerDatos(getNombreTabla(), ultimaAtencion.getEspecialidad().getNombre(), ultimaAtencion.getId());

            this.ultimaFormula = ultimaAtencion.getFormula();
            this.ultimaOrden = ultimaFormula != null ? ultimaFormula.getOrden() : null;
            this.ultimaListaRxFinal = ultimaFormula != null ? ultimaFormula.getListaRxFinal() : new LinkedList<>();
            this.ultimaLensometria = ultimaFormula != null ? ultimaFormula.getLensometria() : null;
            this.ultimaAgudezavisualsincristal = ultimaFormula != null ? ultimaFormula.getAgudezavisualsincristal() : null;
            this.ultimaAgudezavisualconcristal = ultimaFormula != null ? ultimaFormula.getAgudezavisualconcristal() : null;

            this.verHistorico = Boolean.FALSE;
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearFormula() throws ExcepcionDeCreacion {
        if (atencion.getEspecialidad().getCodigo().equals("OPT")) {

            formula = atencion.getFormula();

            if (formula == null) {
                formula = new Formulas();

                formula.setLensometria(new Ojos());
                formula.setAgudezavisualsincristal(new Ojos());
                formula.setAgudezavisualconcristal(new Ojos());

                formula.setEsfera(new Ojos());
                formula.setCilindro(new Ojos());
                formula.setEje(new Ojos());
                formula.setAdicion(new Ojos());
                formula.setDistanciapupilar(new Ojos());
                formula.setAgudezavisual(new Ojos());

                formula.setAtencion(atencion);
                formula.setCreado(new Date());
                formula.setCreadopor(seguridadBean.getLogueado().getUserid());
                formula.setActualizado(atencion.getCreado());
                formula.setActualizadopor(atencion.getCreadopor());
                formula.setActivo(Boolean.TRUE);
                ejbFormulas.crear(formula, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }
            lensometria = formula.getLensometria();
            agudezavisualsincristal = formula.getAgudezavisualsincristal();
            agudezavisualconcristal = formula.getAgudezavisualconcristal();

            listaRxFinal = formula.getListaRxFinal();
            orden = formula.getOrden();
            laboratorio = orden != null ? orden.getLaboratorio() : null;
        }
    }

    public String grabarFormula() throws ExcepcionDeActualizacion {
        if (atencion.getEspecialidad().getCodigo().equals("OPT")) {
            formula.setEsfera(new Ojos(listaRxFinal.get(0).getEsfera(), listaRxFinal.get(1).getEsfera()));
            formula.setCilindro(new Ojos(listaRxFinal.get(0).getCilindro(), listaRxFinal.get(1).getCilindro()));
            formula.setEje(new Ojos(listaRxFinal.get(0).getEje(), listaRxFinal.get(1).getEje()));
            formula.setAdicion(new Ojos(listaRxFinal.get(0).getAdicion(), listaRxFinal.get(1).getAdicion()));
            formula.setDistanciapupilar(new Ojos(listaRxFinal.get(0).getDistanciapupilar(), listaRxFinal.get(1).getDistanciapupilar()));
            formula.setAgudezavisual(new Ojos(listaRxFinal.get(0).getAgudezavisual(), listaRxFinal.get(1).getAgudezavisual()));

            formula.setActualizado(new Date());
            formula.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbFormulas.actualizar(formula, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            Mensajes.informacion("¡Datos de optometría grabados con éxito!");
        }
        return null;
    }

    public String crearOrden() {
        if (formula == null || formula.getId() == null) {
            Mensajes.advertencia("Primero debe ser generada y grabada una receta optalmológica");
            return null;
        }
        if (laboratorio == null) {
            Mensajes.advertencia("Seleccione un laboratorio");
            return null;
        }
        if (orden == null) {
            orden = new Ordenes();
        }
        try {
            orden.setLaboratorio(laboratorio);
            orden.setFormula(formula);
            orden.setCreado(new Date());
            orden.setCreadopor(seguridadBean.getLogueado().getUserid());
            orden.setActualizado(atencion.getCreado());
            orden.setActualizadopor(atencion.getCreadopor());
            orden.setActivo(Boolean.TRUE);
            orden.setRegistro(orden.getCreado());

            ejbOrdenes.crear(orden, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            Mensajes.informacion("¡Orden de Laboratorio grabado con éxito!");
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String grabarOrden() {
        if (formula == null || formula.getId() == null) {
            Mensajes.advertencia("Primero debe ser generada y grabada una receta optalmológica");
            return null;
        }
        if (laboratorio == null) {
            Mensajes.advertencia("Seleccione un laboratorio");
            return null;
        }
        try {
            orden.setLaboratorio(laboratorio);
            orden.setFormula(formula);
            orden.setActualizado(atencion.getCreado());
            orden.setActualizadopor(atencion.getCreadopor());

            ejbOrdenes.actualizar(orden, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            Mensajes.informacion("¡Orden de Laboratorio actualizado con éxito!");
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
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

    /**
     * @return the listaRxFinal
     */
    public List<RxFinal> getListaRxFinal() {
        return listaRxFinal;
    }

    /**
     * @param listaRxFinal the listaRxFinal to set
     */
    public void setListaRxFinal(List<RxFinal> listaRxFinal) {
        this.listaRxFinal = listaRxFinal;
    }

    /**
     * @return the lensometria
     */
    public Ojos getLensometria() {
        return lensometria;
    }

    /**
     * @param lensometria the lensometria to set
     */
    public void setLensometria(Ojos lensometria) {
        this.lensometria = lensometria;
    }

    /**
     * @return the agudezavisualsincristal
     */
    public Ojos getAgudezavisualsincristal() {
        return agudezavisualsincristal;
    }

    /**
     * @param agudezavisualsincristal the agudezavisualsincristal to set
     */
    public void setAgudezavisualsincristal(Ojos agudezavisualsincristal) {
        this.agudezavisualsincristal = agudezavisualsincristal;
    }

    /**
     * @return the agudezavisualconcristal
     */
    public Ojos getAgudezavisualconcristal() {
        return agudezavisualconcristal;
    }

    /**
     * @param agudezavisualconcristal the agudezavisualconcristal to set
     */
    public void setAgudezavisualconcristal(Ojos agudezavisualconcristal) {
        this.agudezavisualconcristal = agudezavisualconcristal;
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
     * @return the orden
     */
    public Ordenes getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Ordenes orden) {
        this.orden = orden;
    }

    /**
     * @return the laboratorio
     */
    public Instituciones getLaboratorio() {
        return laboratorio;
    }

    /**
     * @param laboratorio the laboratorio to set
     */
    public void setLaboratorio(Instituciones laboratorio) {
        this.laboratorio = laboratorio;
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
     * @return the ultimaAtencion
     */
    public Atenciones getUltimaAtencion() {
        return ultimaAtencion;
    }

    /**
     * @param ultimaAtencion the ultimaAtencion to set
     */
    public void setUltimaAtencion(Atenciones ultimaAtencion) {
        this.ultimaAtencion = ultimaAtencion;
    }

    /**
     * @return the ultimasPrescripciones
     */
    public List<Prescripciones> getUltimasPrescripciones() {
        return ultimasPrescripciones;
    }

    /**
     * @param ultimasPrescripciones the ultimasPrescripciones to set
     */
    public void setUltimasPrescripciones(List<Prescripciones> ultimasPrescripciones) {
        this.ultimasPrescripciones = ultimasPrescripciones;
    }

    /**
     * @return the ultimosDatos
     */
    public List<Datos> getUltimosDatos() {
        return ultimosDatos;
    }

    /**
     * @param ultimosDatos the ultimosDatos to set
     */
    public void setUltimosDatos(List<Datos> ultimosDatos) {
        this.ultimosDatos = ultimosDatos;
    }

    /**
     * @return the ultimaFormula
     */
    public Formulas getUltimaFormula() {
        return ultimaFormula;
    }

    /**
     * @param ultimaFormula the ultimaFormula to set
     */
    public void setUltimaFormula(Formulas ultimaFormula) {
        this.ultimaFormula = ultimaFormula;
    }

    /**
     * @return the ultimaOrden
     */
    public Ordenes getUltimaOrden() {
        return ultimaOrden;
    }

    /**
     * @param ultimaOrden the ultimaOrden to set
     */
    public void setUltimaOrden(Ordenes ultimaOrden) {
        this.ultimaOrden = ultimaOrden;
    }

    /**
     * @return the ultimaLensometria
     */
    public Ojos getUltimaLensometria() {
        return ultimaLensometria;
    }

    /**
     * @param ultimaLensometria the ultimaLensometria to set
     */
    public void setUltimaLensometria(Ojos ultimaLensometria) {
        this.ultimaLensometria = ultimaLensometria;
    }

    /**
     * @return the ultimaAgudezavisualsincristal
     */
    public Ojos getUltimaAgudezavisualsincristal() {
        return ultimaAgudezavisualsincristal;
    }

    /**
     * @param ultimaAgudezavisualsincristal the ultimaAgudezavisualsincristal to
     * set
     */
    public void setUltimaAgudezavisualsincristal(Ojos ultimaAgudezavisualsincristal) {
        this.ultimaAgudezavisualsincristal = ultimaAgudezavisualsincristal;
    }

    /**
     * @return the ultimaAgudezavisualconcristal
     */
    public Ojos getUltimaAgudezavisualconcristal() {
        return ultimaAgudezavisualconcristal;
    }

    /**
     * @param ultimaAgudezavisualconcristal the ultimaAgudezavisualconcristal to
     * set
     */
    public void setUltimaAgudezavisualconcristal(Ojos ultimaAgudezavisualconcristal) {
        this.ultimaAgudezavisualconcristal = ultimaAgudezavisualconcristal;
    }

    /**
     * @return the ultimaListaRxFinal
     */
    public List<RxFinal> getUltimaListaRxFinal() {
        return ultimaListaRxFinal;
    }

    /**
     * @param ultimaListaRxFinal the ultimaListaRxFinal to set
     */
    public void setUltimaListaRxFinal(List<RxFinal> ultimaListaRxFinal) {
        this.ultimaListaRxFinal = ultimaListaRxFinal;
    }

    /**
     * @return the verHistorico
     */
    public Boolean getVerHistorico() {
        return verHistorico;
    }

    /**
     * @param verHistorico the verHistorico to set
     */
    public void setVerHistorico(Boolean verHistorico) {
        this.verHistorico = verHistorico;
    }

    /**
     * @return the idUltimaAtencion
     */
    public Integer getIdUltimaAtencion() {
        return idUltimaAtencion;
    }

    /**
     * @param idUltimaAtencion the idUltimaAtencion to set
     */
    public void setIdUltimaAtencion(Integer idUltimaAtencion) {
        this.idUltimaAtencion = idUltimaAtencion;
    }

}
