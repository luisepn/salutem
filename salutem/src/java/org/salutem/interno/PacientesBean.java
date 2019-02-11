package org.salutem.interno;

import org.salutem.general.CombosBean;
import org.salutem.general.PersonasAbstractoBean;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.faces.view.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.salutem.controladores.PacientesFacade;
import org.salutem.entidades.Atenciones;
import org.salutem.entidades.Formulas;
import org.salutem.entidades.Instituciones;
import org.salutem.entidades.Materiales;
import org.salutem.entidades.Ordenes;
import org.salutem.entidades.Pacientes;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.excepciones.ExcepcionDeCreacion;
import org.icefaces.ace.event.TextChangeEvent;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.entidades.Personas;
import org.salutem.excepciones.ExcepcionDeEliminacion;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;
import org.salutem.utilitarios.Reportesds;

@Named("salutemPacientes")
@ViewScoped
public class PacientesBean extends PersonasAbstractoBean implements Serializable {

    @Inject
    @Any
    private CombosBean combosBean;

    private LazyDataModel<Pacientes> pacientes;
    private List<Pacientes> listaPacientes;
    private Instituciones institucion;
    private Atenciones atencion;
    private Formulas formula;
    private Pacientes paciente;
    private Integer nroAtencion;
    private Ordenes orden;
    private Formulario formularioAtencion = new Formulario();
    private Formulario formularioAtenciones = new Formulario();
    private List<Atenciones> listaAtenciones;
    private Reportesds recursoPdf;
    private Reportesds ordenPdf;

    private DecimalFormat numero = new DecimalFormat("0000000.##");

    @EJB
    private PacientesFacade ejbPacientes;

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("CitasAtencionesPacientes");
        institucion = seguridadBean.getInstitucion();
        paciente = seguridadBean.getPaciente();
        claveBusqueda = paciente != null ? paciente.toStringApellidos() : null;
    }

    public void cambiaMaterial(ValueChangeEvent event) {
        Materiales material = ((Materiales) event.getNewValue());
        if (material != null) {
            combosBean.setFoco(material.getFoco());
            combosBean.setTipo(material.getTipo());
        }
    }

    public PacientesBean() {
        parametroBusqueda = "o.persona.apellidos";
        pacientes = new LazyDataModel<Pacientes>() {
            @Override
            public List<Pacientes> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                }
                return carga(i, i1, scs, map);
            }
        };
    }

    public List<Pacientes> carga(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {

        if (institucion == null) {
            pacientes.setRowCount(0);
            return null;
        }
        try {
            Map parameters = new HashMap();
            String where = " o.activo=:activo ";
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
            if (institucion != null) {
                where += " and o.institucion=:institucion";
                parameters.put("institucion", institucion);
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
            int total = ejbPacientes.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            pacientes.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = " o.institucion.nombre, o.persona.apellidos, o.persona.nombres";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbPacientes.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String buscarPacientes() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        if (institucion == null) {
            Mensajes.advertencia("Seleccione una instituación");
            return null;
        }
        pacientes = new LazyDataModel<Pacientes>() {
            @Override
            public List<Pacientes> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return carga(i, i1, scs, map);
            }
        };
        return null;
    }

    public String crearPaciente() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        modificarDatos = false;
        paciente = new Pacientes();
        paciente.setActivo(Boolean.TRUE);
        paciente.setInstitucion(institucion);
        crear();
        if (institucion != null) {
            persona.setCiudad(institucion.getCiudad());
        }
        return null;
    }

    public String editarPaciente() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        modificarDatos = false;
        paciente = (Pacientes) pacientes.getRowData();
        institucion = paciente.getInstitucion();
        persona = paciente.getPersona();
        editar();
        formulario.editar();
        return null;
    }

    public String borrarPaciente() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        modificarDatos = false;
        paciente = ((Pacientes) pacientes.getRowData());
        persona = paciente.getPersona();
        institucion = paciente.getInstitucion();
        formulario.eliminar();
        return null;
    }

    private boolean validarPaciente() throws ExcepcionDeConsulta {
        if (paciente.getInstitucion() == null) {
            Mensajes.advertencia("Seleccione una institución");
            return true;
        }

        String where = "o.persona.cedula=:cedula and o.institucion=:institucion";
        Map parametros = new HashMap();
        parametros.put("cedula", persona.getCedula());
        parametros.put("institucion", paciente.getInstitucion());
        if (paciente.getId() != null) {
            where += " and o.id!=:id";
            parametros.put("id", paciente.getId());
        }
        List<Pacientes> l = ejbPacientes.buscar(where, parametros);
        if (!l.isEmpty()) {
            Mensajes.error("Otra persona con la cédula " + l.get(0).getPersona().getCedula()
                    + " ya se encuentra registrada como paciente en la Institución: "
                    + institucion.getNombre() + "."
                    + (l.get(0).getActivo() == null || !l.get(0).getActivo() ? " Busque en los registros inactivos." : ""));
            return true;
        }
        return false;
    }

    public String grabarPaciente() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        modificarDatos = false;
        try {
            if (validarPaciente()) {
                return null;
            }
            if (validar()) {
                return null;
            }
            if (persona.getId() == null) {
                crear();
            } else {
                paciente.setActivo(Boolean.TRUE);
                grabar();
            }
            paciente.setPersona(persona);
            if (paciente.getId() == null) {
                paciente.setCreado(new Date());
                paciente.setCreadopor(seguridadBean.getLogueado().getUserid());
                paciente.setActualizado(paciente.getCreado());
                paciente.setActualizadopor(paciente.getCreadopor());
                ejbPacientes.crear(paciente, getSeguridadBean().getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            } else {
                paciente.setActualizado(new Date());
                paciente.setActualizadopor(seguridadBean.getLogueado().getUserid());
                ejbPacientes.actualizar(paciente, getSeguridadBean().getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }
            crearUsuarios(persona, institucion, "GP", "MTI");
            formulario.cancelar();
        } catch (ExcepcionDeCreacion | ExcepcionDeActualizacion | ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String removerPaciente() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbPacientes.eliminar(paciente, getSeguridadBean().getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String formatearNumero(Integer id) {
        return numero.format(id);
    }

    public void pacientesChangeEventHandler(TextChangeEvent event) {
        paciente = null;
        listaPacientes = null;
        String claveBuqueda = event.getNewValue() != null ? (String) event.getNewValue() : "";
        if ((claveBuqueda == null) || (claveBuqueda.isEmpty())) {
            return;
        }
        String where = "upper(" + parametroBusqueda + ") like :parametro and o.activo=true";
        Map parametros = new HashMap();
        parametros.put("parametro", claveBuqueda.toUpperCase() + "%");
        try {
            listaPacientes = ejbPacientes.buscar(where, parametros, parametroBusqueda, 0, 10);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambiaPacientes(ValueChangeEvent event) {
        if (listaPacientes == null) {
            return;
        }
        String newWord = (String) event.getNewValue();
        for (Pacientes p : listaPacientes) {
            String aComparar;
            switch (parametroBusqueda) {
                case "o.persona.apellidos":
                    aComparar = p.toStringApellidos();
                    break;
                case "o.persona.nombres":
                    aComparar = p.toStringNombres();
                    break;
                case "o.persona.cedula":
                    aComparar = p.toStringCedula();
                    break;
                default:
                    aComparar = p.toString();
                    break;
            }

            if (aComparar.compareToIgnoreCase(newWord) == 0) {
                paciente = p;
            }
        }
    }

    @Override
    public String getNombreTabla() {
        return Pacientes.class.getSimpleName();
    }

    @Override
    public void cambiaCedula(ValueChangeEvent event) {
        modificarDatos = false;
        String nuevaCedula = (String) event.getNewValue();
        try {

            String where = "o.cedula=:cedula";
            Map parametros = new HashMap();
            parametros.put("cedula", nuevaCedula);
            if (persona.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", persona.getId());
            }
            List<Personas> lp = ejbPersonas.buscar(where, parametros);
            if (!lp.isEmpty()) {
                persona = lp.get(0);
            } else {
                modificarDatos = true;
            }

            validarPaciente();
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the combosBean
     */
    public CombosBean getCombosBean() {
        return combosBean;
    }

    /**
     * @return the pacientes
     */
    public LazyDataModel<Pacientes> getPacientes() {
        return pacientes;
    }

    /**
     * @return the institucion
     */
    public Instituciones getInstitucion() {
        return institucion;
    }

    /**
     * @return the atencion
     */
    public Atenciones getAtencion() {
        return atencion;
    }

    /**
     * @return the formula
     */
    public Formulas getFormula() {
        return formula;
    }

    /**
     * @return the paciente
     */
    public Pacientes getPaciente() {
        return paciente;
    }

    /**
     * @return the nroAtencion
     */
    public Integer getNroAtencion() {
        return nroAtencion;
    }

    /**
     * @return the orden
     */
    public Ordenes getOrden() {
        return orden;
    }

    /**
     * @return the formularioAtencion
     */
    public Formulario getFormularioAtencion() {
        return formularioAtencion;
    }

    /**
     * @return the formularioAtenciones
     */
    public Formulario getFormularioAtenciones() {
        return formularioAtenciones;
    }

    /**
     * @return the listaAtenciones
     */
    public List<Atenciones> getListaAtenciones() {
        return listaAtenciones;
    }

    /**
     * @return the recursoPdf
     */
    public Reportesds getRecursoPdf() {
        return recursoPdf;
    }

    /**
     * @return the ordenPdf
     */
    public Reportesds getOrdenPdf() {
        return ordenPdf;
    }

    /**
     * @param combosBean the combosBean to set
     */
    public void setCombosBean(CombosBean combosBean) {
        this.combosBean = combosBean;
    }

    /**
     * @param pacientes the pacientes to set
     */
    public void setPacientes(LazyDataModel<Pacientes> pacientes) {
        this.pacientes = pacientes;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    /**
     * @param atencion the atencion to set
     */
    public void setAtencion(Atenciones atencion) {
        this.atencion = atencion;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    /**
     * @param nroAtencion the nroAtencion to set
     */
    public void setNroAtencion(Integer nroAtencion) {
        this.nroAtencion = nroAtencion;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Ordenes orden) {
        this.orden = orden;
    }

    /**
     * @param formularioAtencion the formularioAtencion to set
     */
    public void setFormularioAtencion(Formulario formularioAtencion) {
        this.formularioAtencion = formularioAtencion;
    }

    /**
     * @param formularioAtenciones the formularioAtenciones to set
     */
    public void setFormularioAtenciones(Formulario formularioAtenciones) {
        this.formularioAtenciones = formularioAtenciones;
    }

    /**
     * @param listaAtenciones the listaAtenciones to set
     */
    public void setListaAtenciones(List<Atenciones> listaAtenciones) {
        this.listaAtenciones = listaAtenciones;
    }

    /**
     * @param recursoPdf the recursoPdf to set
     */
    public void setRecursoPdf(Reportesds recursoPdf) {
        this.recursoPdf = recursoPdf;
    }

    /**
     * @param ordenPdf the ordenPdf to set
     */
    public void setOrdenPdf(Reportesds ordenPdf) {
        this.ordenPdf = ordenPdf;
    }

    /**
     * @return the listaPacientes
     */
    public List<Pacientes> getListaPacientes() {
        return listaPacientes;
    }

    /**
     * @param listaPacientes the listaPacientes to set
     */
    public void setListaPacientes(List<Pacientes> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }
}
