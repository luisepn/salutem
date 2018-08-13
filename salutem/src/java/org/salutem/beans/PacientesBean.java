package org.salutem.beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import javax.faces.event.ValueChangeEvent;
import org.controladores.salutem.ConsultasFacade;
import org.controladores.salutem.FormulasFacade;
import org.controladores.salutem.OrdenesFacade;
import org.controladores.salutem.PacientesFacade;
import org.entidades.salutem.Archivos;
import org.entidades.salutem.Consultas;
import org.entidades.salutem.Formulas;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Materiales;
import org.entidades.salutem.Ordenes;
import org.entidades.salutem.Pacientes;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.icefaces.ace.event.TextChangeEvent;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;
import org.salutem.utilitarios.Reportesds;

@ManagedBean(name = "salutemPacientes")
@ViewScoped
public class PacientesBean extends PersonasAbstractoBean implements Serializable {

    @ManagedProperty("#{salutemCombos}")
    private CombosBean combosBean;

    private LazyDataModel<Pacientes> pacientes;
    private List<Pacientes> listaPacientes;
    private Instituciones institucion;
    private Consultas consulta;
    private Formulas formula;
    private Pacientes paciente;
    private Integer nroConsulta;
    private Ordenes orden;
    private Formulario formularioConsulta = new Formulario();
    private Formulario formularioConsultas = new Formulario();
    private List<Consultas> listaConsultas;
    private Reportesds recursoPdf;
    private Reportesds ordenPdf;

    private SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    private DecimalFormat numero = new DecimalFormat("0000000.##");

    @EJB
    private PacientesFacade ejbPacientes;
    @EJB
    private ConsultasFacade ejbConsultas;
    @EJB
    private FormulasFacade ejbFormulas;
    @EJB
    private OrdenesFacade ejbOrdenes;

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("CitasConsultasPacientes");
        institucion = getSeguridadBean().getInstitucion();
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
            String where = " o.activo=true and o.institucion=:institucion";
            parameters.put("institucion", institucion);
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
            int total = ejbPacientes.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            pacientes.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = " o.institucion.nombre, o.persona.apellidos";
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
        if (institucion == null) {
            Mensajes.advertencia("Seleccione una institución primero");
            return null;
        }
        paciente = new Pacientes();
        paciente.setActivo(Boolean.TRUE);
        paciente.setInstitucion(institucion);
        crear();
        direccion.setCiudad(institucion.getDireccion() != null ? institucion.getDireccion().getCiudad() : null);
        return null;
    }

    private void existe() {
        String where = " o.persona=:persona and o.institucion=:institucion";
        Map parametros = new HashMap();
        parametros.put("persona", persona);
        parametros.put("institucion", institucion);
        try {
            List<Pacientes> lista = ejbPacientes.buscar(where, parametros);
            if (!lista.isEmpty()) {
                paciente = lista.get(0);
            } else {
                paciente = new Pacientes();
                paciente.setActivo(Boolean.TRUE);
                paciente.setInstitucion(institucion);
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String editarPaciente() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        paciente = (Pacientes) pacientes.getRowData();
        institucion = paciente.getInstitucion();
        persona = paciente.getPersona();
        imagenesBean.setArchivo(persona.getFotografia() != null ? persona.getFotografia() : new Archivos());
        editar();
        formulario.editar();
        return null;
    }

    public String borrarPaciente() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        paciente = ((Pacientes) pacientes.getRowData());
        persona = paciente.getPersona();
        imagenesBean.setArchivo(persona.getFotografia() != null ? persona.getFotografia() : new Archivos());
        institucion = paciente.getInstitucion();
        formulario.eliminar();
        return null;
    }

    public String grabarPaciente() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        if (persona.getId() == null) {
            insertar();
        } else {
            grabar();
        }
        existe();
        try {
            paciente.setPersona(persona);
            if (paciente.getId() == null) {
                ejbPacientes.crear(paciente, getSeguridadBean().getLogueado().getUserid());
            } else {
                ejbPacientes.actualizar(paciente, getSeguridadBean().getLogueado().getUserid());
            }
        } catch (ExcepcionDeCreacion | ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String removerPaciente() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        paciente.setActivo(Boolean.FALSE);
        try {
            ejbPacientes.actualizar(paciente, getSeguridadBean().getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    public String cancelarConsulta() {
        formularioConsulta.cancelar();
        formularioConsultas.insertar();
        buscarPacientes();
        return null;
    }

    public String nuevaConsulta() {
        paciente = ((Pacientes) pacientes.getRowData());
        consulta = new Consultas();
        formula = new Formulas();
        consulta.setPaciente(paciente);
        orden = new Ordenes();
        formularioConsulta.insertar();
        return null;
    }

    public String insertarConsulta() {
        try {
            consulta.setFecha(new Date());
            ejbConsultas.crear(consulta, getSeguridadBean().getLogueado().getUserid());
            formula.setConsulta(consulta);
            ejbFormulas.crear(formula, seguridadBean.getLogueado().getUserid());
            orden.setFormula(formula);
            ejbOrdenes.crear(orden, seguridadBean.getLogueado().getUserid());

        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        //generarConsulta();
        formularioConsulta.cancelar();
        formularioConsulta.setMostrar(true);
        formularioConsulta.editar();
        return null;
    }

    public void generarConsulta() {

    }

    public void generarOrden() {

    }

    public String formatearNumero(Integer id) {
        return numero.format(id);
    }

    public String verHistorial() {
        paciente = ((Pacientes) pacientes.getRowData());

        buscarConsulta();

        formularioConsultas.insertar();
        return null;
    }

    public String buscarConsulta() {
        String where = "o.paciente=:paciente";
        String order = " o.id desc";
        Map parametros = new HashMap();
        parametros.put("paciente", paciente);
        if (nroConsulta != null) {
            where += " and o.id=:nroConsulta";
            parametros.put("nroConsulta", nroConsulta);
        }
        try {
            listaConsultas = ejbConsultas.buscar(where, parametros, order);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String modificarConsulta() {
        consulta = ((Consultas) listaConsultas.get(formularioConsulta.getFila().getRowIndex()));
        formula = consulta.getFormula();
        orden = formula.getOrdenes();
        if (formula.getMaterial() != null) {
            combosBean.setFoco(formula.getMaterial().getFoco());
            combosBean.setTipo(formula.getMaterial().getTipo());
        }
        formularioConsulta.editar();
        formularioConsultas.cancelar();
        return null;
    }

    public String grabarConsulta() {
        try {
            ejbConsultas.actualizar(consulta, getSeguridadBean().getLogueado().getUserid());
            ejbFormulas.actualizar(formula, seguridadBean.getLogueado().getUserid());
            ejbOrdenes.actualizar(orden, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        generarConsulta();
        return null;
    }

    private boolean validarOrden() {
        if ((orden.getFactura() == null) || (orden.getFactura().isEmpty())) {
            Mensajes.advertencia("Ingrese número de factura");
            return true;
        }
        return false;
    }

    public String grabarOrden() {
        if (validarOrden()) {
            return null;
        }
        orden.setFormula(formula);
        orden.setRegistro(new Date());
        orden.setUsuario(getSeguridadBean().getLogueado().toString());
        try {
            if (orden.getId() == null) {
                ejbOrdenes.crear(orden, getSeguridadBean().getLogueado().getUserid());
            } else {
                ejbOrdenes.actualizar(orden, getSeguridadBean().getLogueado().getUserid());
            }
        } catch (ExcepcionDeCreacion | ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        generarOrden();
        return null;
    }

    public String traerIndicaciones() {
        String m = formula.getMaterial() != null ? formula.getMaterial().getFoco().getNombre() + " - " + formula.getMaterial().getTipo().getNombre() + " - " + formula.getMaterial().getNombre() : "";
        String t = formula.getTratamiento() != null ? formula.getTratamiento().getNombre() : "";
        return m + " " + t + "\n" + consulta.getIndicaciones();
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

    public String getNombreTabla() {
        return Pacientes.class.getSimpleName();
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
     * @return the consulta
     */
    public Consultas getConsulta() {
        return consulta;
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
     * @return the nroConsulta
     */
    public Integer getNroConsulta() {
        return nroConsulta;
    }

    /**
     * @return the orden
     */
    public Ordenes getOrden() {
        return orden;
    }

    /**
     * @return the formularioConsulta
     */
    public Formulario getFormularioConsulta() {
        return formularioConsulta;
    }

    /**
     * @return the formularioConsultas
     */
    public Formulario getFormularioConsultas() {
        return formularioConsultas;
    }

    /**
     * @return the listaConsultas
     */
    public List<Consultas> getListaConsultas() {
        return listaConsultas;
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
     * @param consulta the consulta to set
     */
    public void setConsulta(Consultas consulta) {
        this.consulta = consulta;
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
     * @param nroConsulta the nroConsulta to set
     */
    public void setNroConsulta(Integer nroConsulta) {
        this.nroConsulta = nroConsulta;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Ordenes orden) {
        this.orden = orden;
    }

    /**
     * @param formularioConsulta the formularioConsulta to set
     */
    public void setFormularioConsulta(Formulario formularioConsulta) {
        this.formularioConsulta = formularioConsulta;
    }

    /**
     * @param formularioConsultas the formularioConsultas to set
     */
    public void setFormularioConsultas(Formulario formularioConsultas) {
        this.formularioConsultas = formularioConsultas;
    }

    /**
     * @param listaConsultas the listaConsultas to set
     */
    public void setListaConsultas(List<Consultas> listaConsultas) {
        this.listaConsultas = listaConsultas;
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
