/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.interno;

import org.salutem.general.DatosBean;
import org.salutem.general.CombosBean;
import org.salutem.seguridad.SeguridadBean;
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
import org.salutem.controladores.AtencionesFacade;
import org.salutem.controladores.FormulasFacade;
import org.salutem.controladores.OrdenesFacade;
import org.salutem.controladores.PrescripcionesFacade;
import org.salutem.entidades.Atenciones;
import org.salutem.entidades.Citas;
import org.salutem.entidades.Datos;
import org.salutem.entidades.Formulas;
import org.salutem.entidades.Ordenes;
import org.salutem.entidades.Perfiles;
import org.salutem.entidades.Prescripciones;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.excepciones.ExcepcionDeCreacion;
import org.salutem.excepciones.ExcepcionDeEliminacion;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;
import org.salutem.utilitarios.Items;
import org.salutem.utilitarios.Ojos;
import org.salutem.utilitarios.PDFCampo;
import org.salutem.utilitarios.PDFDocument;
import org.salutem.utilitarios.Recurso;
import org.salutem.utilitarios.RxFinal;

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
        conCita = false;
        atenciones = new LazyDataModel<Atenciones>() {
            @Override
            public List<Atenciones> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                } else {
                    if (combosBean.getProfesional() != null) {
                        map.put("profesional.id", combosBean.getProfesional().getId().toString());
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
            if (seguridadBean.getGrupo().getCodigo().equals("GP") && seguridadBean.getPaciente() != null) {
                where += " and o.paciente=:paciente";
                parameters.put("paciente", seguridadBean.getPaciente());
            }
            if (conCita && cita != null) {
                where += " and o.cita=:cita";
                parameters.put("cita", cita);
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
                if (combosBean.getProfesional() != null) {
                    map.put("profesional.id", combosBean.getProfesional().getId().toString());
                }
                if (combosBean.getEspecialidad() != null) {
                    map.put("especialidad.id", combosBean.getEspecialidad().getId().toString());
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
//        if (validarFecha(atencion.getFecha())) {
//            Mensajes.advertencia("No se puede editar atenciones con fechas menores a la de hoy");
//            return null;
//        }
        try {
            crearFormula();
            prescripciones = ejbPrescripciones.traerPrescripciones(atencion);
            formulario.editar();
            datosBean.iniciar(getNombreTabla(), atencion.getEspecialidad(), atencion.getId(), formulario);
            buscarUltimaAtencion();
        } catch (ExcepcionDeConsulta | ExcepcionDeCreacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        atencion = (Atenciones) atenciones.getRowData();
//        if (validarFecha(atencion.getFecha())) {
//            Mensajes.advertencia("No se puede eliminar atenciones con fechas menores a la de hoy");
//            return null;
//        }
        try {
            crearFormula();
            prescripciones = ejbPrescripciones.traerPrescripciones(atencion);
            formulario.eliminar();
            datosBean.iniciar(getNombreTabla(), combosBean.getProfesional().getEspecialidad(), atencion.getId(), formulario);
            buscarUltimaAtencion();
        } catch (ExcepcionDeConsulta | ExcepcionDeCreacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
//                if (ejbAtenciones.contar("o.profesional=:profesional and o.paciente=:paciente and o.especialidad=:especialidad and o.fecha between :inicio and :fin", parameters) > 0) {
//                    Mensajes.advertencia("Ya existe una atención generada para el paciente seleccionado");
//                    return null;
//                }
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
            formulario.editar();
            datosBean.iniciar(getNombreTabla(), combosBean.getProfesional().getEspecialidad(), atencion.getId(), formulario);
            buscarUltimaAtencion();
        } catch (ExcepcionDeCreacion | ExcepcionDeConsulta ex) {
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
            cancelar();
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            cancelar();

        } catch (ExcepcionDeConsulta | ExcepcionDeEliminacion ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(AtencionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        grabarPrescripciones();
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
            if (prescripciones != null) {
                for (Prescripciones p : prescripciones) {
                    p.setActualizado(new Date());
                    p.setActualizadopor(seguridadBean.getLogueado().getUserid());
                    ejbPrescripciones.actualizar(p, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                }
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

            combosBean.setFoco(formula.getMaterial() != null ? formula.getMaterial().getFoco() : null);
            combosBean.setTipo(formula.getMaterial() != null ? formula.getMaterial().getTipo() : null);

            lensometria = formula.getLensometria();
            agudezavisualsincristal = formula.getAgudezavisualsincristal();
            agudezavisualconcristal = formula.getAgudezavisualconcristal();

            listaRxFinal = formula.getListaRxFinal();
            orden = formula.getOrden();

            if (orden == null) {
                crearOrden();
            }
        }
    }

    public String grabarFormula() throws ExcepcionDeActualizacion {
        if (atencion.getEspecialidad().getCodigo().equals("OPT")) {
            formula.setEsfera(new Ojos(listaRxFinal.get(1).getEsfera(), listaRxFinal.get(0).getEsfera()));
            formula.setCilindro(new Ojos(listaRxFinal.get(1).getCilindro(), listaRxFinal.get(0).getCilindro()));
            formula.setEje(new Ojos(listaRxFinal.get(1).getEje(), listaRxFinal.get(0).getEje()));
            formula.setAdicion(new Ojos(listaRxFinal.get(1).getAdicion(), listaRxFinal.get(0).getAdicion()));
            formula.setDistanciapupilar(new Ojos(listaRxFinal.get(1).getDistanciapupilar(), listaRxFinal.get(0).getDistanciapupilar()));
            formula.setAgudezavisual(new Ojos(listaRxFinal.get(1).getAgudezavisual(), listaRxFinal.get(0).getAgudezavisual()));

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
        try {
            orden = new Ordenes();
            orden.setFormula(formula);
            orden.setCreado(new Date());
            orden.setCreadopor(seguridadBean.getLogueado().getUserid());
            orden.setActualizado(atencion.getCreado());
            orden.setActualizadopor(atencion.getCreadopor());
            orden.setActivo(Boolean.TRUE);
            orden.setSeleccionado(Boolean.FALSE);
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
        if (orden.getLaboratorio() == null) {
            Mensajes.advertencia("Seleccione un laboratorio");
            return null;
        }
        try {
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

    public String imprimir() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }

        atencion = null;
        colocarUltimaAtencion((Atenciones) atenciones.getRowData());
        formulario.imprimir();
        datosBean.iniciar(getNombreTabla(), ((Atenciones) atenciones.getRowData()).getEspecialidad(), ultimaAtencion.getId(), formulario);

        return null;
    }

    public Recurso generarPdf() {
        if (atencion != null) {
            return generarPdf(atencion, datosBean.getDatos(), prescripciones);
        }
        return null;
    }

    public Recurso generarUltimoPdf() {
        if (ultimaAtencion != null) {
            return generarPdf(ultimaAtencion, ultimosDatos, ultimasPrescripciones);
        }
        return null;
    }

    public Recurso generarOrdenPdf() {
        if (atencion != null) {
            return generarOrdenPdf(atencion, orden);
        }
        return null;
    }

    public Recurso generarUltimoOrdenPdf() {
        if (ultimaAtencion != null) {
            return generarOrdenPdf(ultimaAtencion, ultimaOrden);
        }
        return null;
    }

    private Recurso generarPdf(Atenciones atencion, List<Datos> datos, List<Prescripciones> prescripciones) {

        PDFDocument pdf = new PDFDocument(atencion.getPaciente().toString(), "A5", false);

        if (atencion.getProfesional().getInstitucion().getLogotipo() != null) {
            pdf.agregarImagen(atencion.getProfesional().getInstitucion().getLogotipo(), 40, 'C');
        }

        List<PDFCampo> titulos;
        List<PDFCampo> campos;

        if (atencion.getPaciente() != null) {
            campos = new LinkedList<>();
            campos.add(new PDFCampo("Nombres:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getPaciente().getPersona().getNombres(), 'L'));
            campos.add(new PDFCampo("Apellidos:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getPaciente().getPersona().getApellidos(), 'L'));
            campos.add(new PDFCampo("C.I.:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getPaciente().getPersona().getCedula(), 'L'));
            campos.add(new PDFCampo("Dirección:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getPaciente().getPersona().getDireccion(), 'L'));
            campos.add(new PDFCampo("Teléfono:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getPaciente().getPersona().getTelefonos(), 'L'));
            campos.add(new PDFCampo("E-mail:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getPaciente().getPersona().getEmail(), 'L'));
            campos.add(new PDFCampo("Fecha de nacimiento:", 'L', "IB"));
            if (atencion.getPaciente().getPersona().getFecha() != null) {
                campos.add(new PDFCampo(PDFDocument.formatoFecha.format(atencion.getPaciente().getPersona().getFecha()), 'L'));
            } else {
                campos.add(new PDFCampo("", 'L'));
            }

            campos.add(new PDFCampo("Edad:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getPaciente().getPersona().getEdad(atencion.getFecha()), 'L'));
            campos.add(new PDFCampo("Ocupación:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getPaciente().getPersona().getOcupacion(), 'L'));
            campos.add(new PDFCampo("Género:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getPaciente().getPersona().getGenero() != null ? atencion.getPaciente().getPersona().getGenero().getNombre() : "", 'L'));
            pdf.agregarTabla(null, campos, new float[]{10, 40, 10, 40}, 'C', new PDFCampo("Datos del Paciente", 'L', "IB", 4, 1, 8, "B"), false);
        }
        campos = new LinkedList<>();
        campos.add(new PDFCampo("Fecha de Atención:", 'L', "IB", 1, 1, 8, "T"));
        campos.add(new PDFCampo(PDFDocument.formatoFechaHora.format(atencion.getFecha()), 'L', "", 1, 1, 8, "T"));
        campos.add(new PDFCampo("Motivo de Consulta:", 'L', "IB"));
        campos.add(new PDFCampo(atencion.getMotivo(), 'L'));
        pdf.agregarTabla(null, campos, new float[]{10, 90}, 'C', null, false);

        if (datos != null) {
            titulos = new LinkedList<>();
            campos = new LinkedList<>();
            for (Datos d : datos) {
                campos.add(new PDFCampo("Integer", d.getCodigo(), 'R', "IB", 1, 1, 8, "RLTB"));
                campos.add(new PDFCampo("String", d.getNombre(), 'R', "IB", 1, 1, 8, "RLTB"));
                if (d.getTipo() != null) {
                    switch (d.getTipo().getCodigo()) {
                        case "TEXT":
                            campos.add(new PDFCampo("String", d.getTexto(), 'L', "RLTB"));
                            break;
                        case "BOOLEAN":
                            campos.add(new PDFCampo("Boolean", d.getBooleano(), 'L', "RLTB"));
                            break;
                        case "INTEGER":
                            campos.add(new PDFCampo("Integer", d.getEntero(), 'L', "RLTB"));
                            break;
                        case "DOUBLE":
                            campos.add(new PDFCampo("Double", d.getDecimal(), 'L', "RLTB"));
                            break;
                        case "DATE":
                            campos.add(new PDFCampo("Date", d.getFecha(), 'L', "RLTB"));
                            break;
                        case "TIME":
                            campos.add(new PDFCampo("Time", d.getHora(), 'L', "RLTB"));
                            break;
                        case "DATETIME":
                            campos.add(new PDFCampo("DateTime", d.getFechahora(), 'L', "RLTB"));
                            break;
                        case "ONE":
                        case "MANY":
                        case "LIST":
                            if (d.getSeleccion() != null && !d.getSeleccion().isEmpty()) {

                                List<Items> lista = d.getItemListFromJson(true);
                                String valor = "";

                                for (Items i : lista) {
                                    valor += i.getValor() + "\n";
                                }

                                campos.add(new PDFCampo("Selection", valor, 'L', "RLTB"));
                            } else {
                                campos.add(new PDFCampo("String", "", 'L', "RLTB"));
                            }
                            break;
                        case "FILE":
                            campos.add(new PDFCampo("File", d.getArchivo(), 'L', "RLTB"));
                            break;
                        default:
                            campos.add(new PDFCampo("String", "", 'L', "RLTB"));
                            break;
                    }
                }

            }
            pdf.agregarTabla(titulos, campos, new float[]{10, 30, 60}, 'C', new PDFCampo(atencion.getEspecialidad().getNombre(), 'L', "IB", 3, 1, 8, "TB"), false);

        }
        //Receta Optometría Formulas
        Formulas f = atencion.getFormula();
        if (atencion.getEspecialidad().getCodigo().equals("OPT") && f != null) {

            Ojos l = f.getLensometria();
            Ojos avsc = f.getAgudezavisualsincristal();
            Ojos avcc = f.getAgudezavisualconcristal();
            List<RxFinal> lrx = f.getListaRxFinal();

            if (l != null && avsc != null && avcc != null) {

                titulos = new LinkedList<>();
                titulos.add(new PDFCampo("LENSOMETRÍA", 'C', "IB", 2, 1, 8, "RLTB"));
                titulos.add(new PDFCampo("AV SC", 'C', "IB", 2, 1, 8, "RLTB"));
                titulos.add(new PDFCampo("AV CC", 'C', "IB", 2, 1, 8, "RLTB"));

                campos = new LinkedList<>();

                campos.add(new PDFCampo("OD", "IB", "L"));
                campos.add(new PDFCampo(l.getD(), "", "R"));

                campos.add(new PDFCampo("OD", "IB", "L"));
                campos.add(new PDFCampo(avsc.getD(), "", "R"));

                campos.add(new PDFCampo("OD", "IB", "L"));
                campos.add(new PDFCampo(avcc.getD(), "", "R"));

                campos.add(new PDFCampo("OI", "IB", "LB"));
                campos.add(new PDFCampo(l.getI(), "", "RB"));

                campos.add(new PDFCampo("OI", "IB", "B"));
                campos.add(new PDFCampo(avsc.getI(), "", "B"));

                campos.add(new PDFCampo("OI", "IB", "LB"));
                campos.add(new PDFCampo(avcc.getI(), "", "RB"));

                pdf.agregarTabla(titulos, campos, new float[]{new Float(16.66), new Float(16.66), new Float(16.66), new Float(16.66), new Float(16.66), new Float(16.66)}, 'C', new PDFCampo("Rx", 'L', "IB", 6, 1, 8, "B"), false);

            }

            if (lrx != null) {
                titulos = new LinkedList<>();
                titulos.add(new PDFCampo("", "IB", "RLTB"));
                titulos.add(new PDFCampo("Esfera", "IB", "RLTB"));
                titulos.add(new PDFCampo("Cilindro", "IB", "RLTB"));
                titulos.add(new PDFCampo("Eje", "IB", "RLTB"));
                titulos.add(new PDFCampo("ADD", "IB", "RLTB"));
                titulos.add(new PDFCampo("DP", "IB", "RLTB"));
                titulos.add(new PDFCampo("AV", "IB", "RLTB"));

                campos = new LinkedList<>();
                for (RxFinal rx : lrx) {
                    campos.add(new PDFCampo(rx.getOjo(), "IB", "RLTB"));
                    campos.add(new PDFCampo(rx.getEsfera(), "", "RLTB"));
                    campos.add(new PDFCampo(rx.getCilindro(), "", "RLTB"));
                    campos.add(new PDFCampo(rx.getEje(), "", "RLTB"));
                    campos.add(new PDFCampo(rx.getAdicion(), "", "RLTB"));
                    campos.add(new PDFCampo(rx.getDistanciapupilar(), "", "RLTB"));
                    campos.add(new PDFCampo(rx.getAgudezavisual(), "", "RLTB"));
                }
                pdf.agregarTabla(titulos, campos, new float[]{10, 15, 15, 15, 15, 15, 15}, 'C', new PDFCampo("Rx Final", 'L', "IB", titulos.size(), 1, 8, "B"), false);

                campos = new LinkedList<>();

                campos.add(new PDFCampo("Foco:", 'L', "IB"));
                campos.add(new PDFCampo(f.getMaterial() != null ? f.getMaterial().getFoco().getNombre() : "", 'L'));
                campos.add(new PDFCampo("Tipo Material:", 'L', "IB"));
                campos.add(new PDFCampo(f.getMaterial() != null ? f.getMaterial().getTipo().getNombre() : "", 'L'));
                campos.add(new PDFCampo("Material:", 'L', "IB"));
                campos.add(new PDFCampo(f.getMaterial() != null ? f.getMaterial().getNombre() : "", 'L'));
                campos.add(new PDFCampo("Tratamiento:", 'L', "IB"));
                campos.add(new PDFCampo(f.getTratamiento() != null ? f.getTratamiento().getNombre() : "", 'L'));
                campos.add(new PDFCampo("Altura:", 'L', "IB"));
                campos.add(new PDFCampo(f.getAltura() != null ? f.getAltura() : "", 'L'));
                pdf.agregarTabla(null, campos, new float[]{20, 30, 20, 30}, 'C', null, false);

            }
        } else {
            if (prescripciones != null) {
                titulos = new LinkedList<>();
                campos = new LinkedList<>();

                titulos.add(new PDFCampo("Medicamento", "IB", "RLTB"));
                titulos.add(new PDFCampo("Dosis", "IB", "RLTB"));
                titulos.add(new PDFCampo("Frecuencia", "IB", "RLTB"));
                titulos.add(new PDFCampo("Duración", "IB", "RLTB"));
                titulos.add(new PDFCampo("Administración", "IB", "RLTB"));

                for (Prescripciones p : prescripciones) {
                    if ((p.getMedicamento() != null && !p.getMedicamento().trim().isEmpty())
                            || (p.getDosis() != null && !p.getDosis().trim().isEmpty())
                            || (p.getFrecuencia() != null && !p.getFrecuencia().trim().isEmpty())
                            || (p.getDuracion() != null && !p.getDuracion().trim().isEmpty())
                            || (p.getAdministracion() != null && !p.getAdministracion().trim().isEmpty())) {
                        campos.add(new PDFCampo(p.getMedicamento(), "", "RLTB"));
                        campos.add(new PDFCampo(p.getDosis(), "", "RLTB"));
                        campos.add(new PDFCampo(p.getFrecuencia(), "", "RLTB"));
                        campos.add(new PDFCampo(p.getDuracion(), "", "RLTB"));
                        campos.add(new PDFCampo(p.getAdministracion(), "", "RLTB"));

                    }
                }
                pdf.agregarTabla(titulos, campos, new float[]{20, 20, 20, 20, 20}, 'C', new PDFCampo("Prescripciones", 'L', "IB", 5, 1, 8, "B"), false);
            }
        }
        campos = new LinkedList<>();

        if (atencion.getEspecialidad().getCodigo().equals("OPT")) {
            campos.add(new PDFCampo("Indicaciones:", 'L', "IB"));
            campos.add(new PDFCampo(f != null ? atencion.getFormula().getIndicaciones() : "", 'L'));
        } else {
            campos.add(new PDFCampo("Diagnóstico:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getDiagnostico(), 'L'));
            campos.add(new PDFCampo("Observaciones:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getObservaciones(), 'L'));
        }
        pdf.agregarTabla(null, campos, new float[]{10, 90}, 'C', null, false);

        return pdf.traerRecurso();
    }

    private Recurso generarOrdenPdf(Atenciones atencion, Ordenes orden) {

        PDFDocument pdf = new PDFDocument(atencion.getPaciente().toString(), "A5", false);

        if (atencion.getProfesional().getInstitucion().getLogotipo() != null) {
            pdf.agregarImagen(atencion.getProfesional().getInstitucion().getLogotipo(), 40, 'C');
        }

        pdf.agregarParrafo(new PDFCampo("ORDEN DE LABORATORIO", 'C', "B"), false);
        pdf.agregarParrafo(new PDFCampo(orden.getLaboratorio() != null ? orden.getLaboratorio().getNombre() : "", 'C', "B"), false);

        List<PDFCampo> titulos;
        List<PDFCampo> campos;

        if (atencion.getPaciente() != null) {
            campos = new LinkedList<>();
            campos.add(new PDFCampo("Nombres:", 'L', "IB"));
            campos.add(new PDFCampo(atencion.getPaciente().getPersona().getNombres(), 'L'));
            campos.add(new PDFCampo("Fecha:", 'L', "IB"));
            if (orden.getRegistro() != null) {
                campos.add(new PDFCampo(PDFDocument.formatoFecha.format(orden.getRegistro()), 'L'));
            } else {
                campos.add(new PDFCampo("", "L"));
            }
            campos.add(new PDFCampo("Factura:", 'L', "IB"));
            campos.add(new PDFCampo(orden.getFactura(), 'L'));
            pdf.agregarTabla(null, campos, new float[]{new Float(2.5), new Float(2.5), new Float(2.5), new Float(2.5), new Float(2.5), new Float(2.5)}, 'C', null, false);

        }
        Formulas f = atencion.getFormula();
        if (atencion.getEspecialidad().getCodigo().equals("OPT") && f != null) {
            List<RxFinal> lrx = f.getListaRxFinal();
            if (lrx != null) {
                titulos = new LinkedList<>();
                titulos.add(new PDFCampo("", "IB", "RLTB"));
                titulos.add(new PDFCampo("Esfera", "IB", "RLTB"));
                titulos.add(new PDFCampo("Cilindro", "IB", "RLTB"));
                titulos.add(new PDFCampo("Eje", "IB", "RLTB"));
                titulos.add(new PDFCampo("ADD", "IB", "RLTB"));
                titulos.add(new PDFCampo("DP", "IB", "RLTB"));
                titulos.add(new PDFCampo("AV", "IB", "RLTB"));

                campos = new LinkedList<>();
                for (RxFinal rx : lrx) {
                    campos.add(new PDFCampo(rx.getOjo(), "IB", "RLTB"));
                    campos.add(new PDFCampo(rx.getEsfera(), "", "RLTB"));
                    campos.add(new PDFCampo(rx.getCilindro(), "", "RLTB"));
                    campos.add(new PDFCampo(rx.getEje(), "", "RLTB"));
                    campos.add(new PDFCampo(rx.getAdicion(), "", "RLTB"));
                    campos.add(new PDFCampo(rx.getDistanciapupilar(), "", "RLTB"));
                    campos.add(new PDFCampo(rx.getAgudezavisual(), "", "RLTB"));
                }
                pdf.agregarTabla(titulos, campos, new float[]{10, 15, 15, 15, 15, 15, 15}, 'C', null, false);

                campos = new LinkedList<>();

                campos.add(new PDFCampo("Foco:", 'L', "IB"));
                campos.add(new PDFCampo(f.getMaterial() != null ? f.getMaterial().getFoco().getNombre() : "", 'L'));
                campos.add(new PDFCampo("Tipo Material:", 'L', "IB"));
                campos.add(new PDFCampo(f.getMaterial() != null ? f.getMaterial().getTipo().getNombre() : "", 'L'));
                campos.add(new PDFCampo("Material:", 'L', "IB"));
                campos.add(new PDFCampo(f.getMaterial() != null ? f.getMaterial().getNombre() : "", 'L'));
                campos.add(new PDFCampo("Tratamiento:", 'L', "IB"));
                campos.add(new PDFCampo(f.getTratamiento() != null ? f.getTratamiento().getNombre() : "", 'L'));
                campos.add(new PDFCampo("Altura:", 'L', "IB"));
                campos.add(new PDFCampo(f.getAltura() != null ? f.getAltura() : "", 'L'));
                pdf.agregarTabla(null, campos, new float[]{20, 30, 20, 30}, 'C', null, false);
            }
        }

        campos = new LinkedList<>();
        campos.add(new PDFCampo("Indicaciones:", 'L', "IB"));
        campos.add(new PDFCampo(atencion.getFormula() != null ? atencion.getFormula().getIndicaciones() : "", 'L'));
        pdf.agregarTabla(null, campos, new float[]{10, 90}, 'C', null, false);

        pdf.agregarParrafo(new PDFCampo("____________________", 'R'), false);
        pdf.agregarParrafo(new PDFCampo("FIRMA RESPONSABLE      ", 'R'), true);

        return pdf.traerRecurso();
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
