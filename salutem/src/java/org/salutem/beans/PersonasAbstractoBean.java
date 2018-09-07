package org.salutem.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import org.controladores.salutem.DireccionesFacade;
import org.controladores.salutem.PersonasFacade;
import org.entidades.salutem.Archivos;
import org.entidades.salutem.Direcciones;
import org.entidades.salutem.Perfiles;
import org.entidades.salutem.Personas;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.excepciones.salutem.ExcepcionDeEliminacion;
import org.icefaces.ace.event.TextChangeEvent;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Codificador;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

@ManagedBean(name = "salutemPersonasAbstracto")
@ViewScoped
public abstract class PersonasAbstractoBean implements Serializable, IMantenimiento {

    @ManagedProperty("#{salutemSeguridad}")
    protected SeguridadBean seguridadBean;
    @ManagedProperty("#{salutemArchivos}")
    protected ArchivosBean archivosBean;

    protected Formulario formulario = new Formulario();
    protected LazyDataModel<Personas> personas;
    protected Personas persona;
    protected Direcciones direccion;
    protected Perfiles perfil;

    protected List<Personas> listaPersonas;
    protected String parametroBusqueda = "o.apellidos";
    protected String claveBusqueda;

    protected Date fechaInicio;
    protected Date fechaFin;

    @EJB
    protected PersonasFacade ejbPersonas;
    @EJB
    protected DireccionesFacade ejbDirecciones;

    public PersonasAbstractoBean() {
        personas = new LazyDataModel<Personas>() {
            @Override
            public List<Personas> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                }
                return cargar(i, i1, scs, map);
            }
        };
    }

    private List<Personas> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            Map parameters = new HashMap();
            String where = " o.activo=:activo ";
            parameters.put("activo", seguridadBean.getVerActivos());
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

            int total = ejbPersonas.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            personas.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.apellidos";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbPersonas.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        personas = new LazyDataModel<Personas>() {
            @Override
            public List<Personas> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        persona = new Personas();
        persona.setActivo(Boolean.TRUE);
        direccion = new Direcciones();
        direccion.setActivo(Boolean.TRUE);
        archivosBean.iniciar(getNombreTabla(), persona.getId(), new Archivos());
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        if (personas.getRowIndex() > -1) {
            //Por defecto una tabla lazy devuelve -1 cuando no hay seleccionada ninguna línea
            persona = (Personas) personas.getRowData();
        }
        direccion = persona.getDireccion() != null ? persona.getDireccion() : new Direcciones();
        direccion.setActivo(Boolean.TRUE);
        archivosBean.setArchivo(persona.getFotografia() != null ? persona.getFotografia() : new Archivos());
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        persona = (Personas) personas.getRowData();
        direccion = persona.getDireccion() != null ? persona.getDireccion() : new Direcciones();
        archivosBean.setArchivo(persona.getFotografia() != null ? persona.getFotografia() : new Archivos());
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        if ((persona.getCedula() == null) || (persona.getCedula().trim().isEmpty())) {
            Mensajes.advertencia("CI o RUC es obligatorio");
            return true;
        }
        if ((persona.getNombres() == null) || (persona.getNombres().trim().isEmpty())) {
            Mensajes.advertencia("Nombres son obligatorios");
            return true;
        }
        if ((persona.getApellidos() == null) || (persona.getApellidos().trim().isEmpty())) {
            Mensajes.advertencia("Apellidos son obligatorios");
            return true;
        }
        if (persona.getFecha() != null && persona.getFecha().after(new Date())) {
            Mensajes.advertencia("La fecha de nacimiento no puede ser mayor a la fecha actual");
            return true;
        }
        if (formulario.isEditar()) {
            Personas p = traerPersona(persona.getCedula());
            if (p != null && !Objects.equals(p.getId(), persona.getId())) {
                Mensajes.advertencia("Otra persona con la cédula " + p.getCedula() + " ya se encuentra registrada en el sistema."
                        + (p.getActivo() == null || !p.getActivo() ? " Busque en los registros inactivos." : ""));
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
            ejbDirecciones.crear(direccion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            persona.setDireccion(direccion);

            archivosBean.grabar();
            persona.setFotografia(archivosBean.getArchivo());

            persona.setUserid(persona.getCedula());
            persona.setClave(Codificador.getEncoded(persona.getCedula(), "MD5"));
            persona.setCreado(new Date());
            persona.setCreadopor(seguridadBean.getLogueado().getUserid());
            persona.setActualizado(persona.getCreado());
            persona.setActualizadopor(persona.getActualizadopor());
            ejbPersonas.crear(persona, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            archivosBean.actualizarIdentificador(persona.getId().toString());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        Mensajes.informacion("Creación exitoso.\n" + persona.toString());
        return null;
    }

    @Override
    public String grabar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        try {
            if (direccion.getId() == null) {
                ejbDirecciones.crear(direccion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            } else {
                ejbDirecciones.actualizar(direccion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }
            persona.setDireccion(direccion);

            archivosBean.grabar();
            persona.setFotografia(archivosBean.getArchivo());
            persona.setActualizado(new Date());
            persona.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbPersonas.actualizar(persona, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } catch (ExcepcionDeCreacion | ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        Mensajes.informacion("Modificación exitosa.\n" + persona.toString());
        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbPersonas.eliminar(persona, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        formulario.cancelar();
        Mensajes.informacion("Eliminación exitosa.\n" + persona.toString());
        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public void cambiaCedula(ValueChangeEvent event) {
        String nuevaCedula = (String) event.getNewValue();
        Personas p = traerPersona(nuevaCedula);
        if (p != null) {
            persona = p;
            editar();
        }
    }

    public void apellidosChangeEventHandler(TextChangeEvent event) {
        persona = null;
        listaPersonas = null;
        String newWord = event.getNewValue() != null ? (String) event.getNewValue() : "";
        if ((newWord == null) || (newWord.isEmpty())) {
            return;
        }
        String where = "upper(o.apellidos) like :apellidos and o.activo=true";
        Map parametros = new HashMap();
        parametros.put("apellidos", newWord.toUpperCase() + "%");
        try {
            listaPersonas = ejbPersonas.buscar(where, parametros, 0, 10);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambiaApellidos(ValueChangeEvent event) {
        if (listaPersonas == null) {
            return;
        }
        String newWord = (String) event.getNewValue();
        for (Personas p : listaPersonas) {
            String aComparar = p.toString();
            if (aComparar.compareToIgnoreCase(newWord) == 0) {
                persona = p;
            }
        }
    }

    /**
     *
     * @param cedula Una cadena de texto
     * @return Una persona que coincida con la cédula enviada com parámetro
     */
    public Personas traerPersona(String cedula) {
        String where = "upper(o.cedula)=:cedula";
        Map parametros = new HashMap();
        parametros.put("cedula", cedula);
        try {
            List<Personas> lista = ejbPersonas.buscar(where, parametros);
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.error(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getNombreTabla() {
        return Personas.class.getSimpleName();
    }

    /**
     * @return the seguridadBean
     */
    public SeguridadBean getSeguridadBean() {
        return seguridadBean;
    }

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @return the personas
     */
    public LazyDataModel<Personas> getPersonas() {
        return personas;
    }

    /**
     * @return the persona
     */
    public Personas getPersona() {
        return persona;
    }

    /**
     * @return the direccion
     */
    public Direcciones getDireccion() {
        return direccion;
    }

    /**
     * @return the perfil
     */
    public Perfiles getPerfil() {
        return perfil;
    }

    /**
     * @return the listaPersonas
     */
    public List<Personas> getListaPersonas() {
        return listaPersonas;
    }

    /**
     * @return the claveBusqueda
     */
    public String getClaveBusqueda() {
        return claveBusqueda;
    }

    /**
     * @param seguridadBean the seguridadBean to set
     */
    public void setSeguridadBean(SeguridadBean seguridadBean) {
        this.seguridadBean = seguridadBean;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @param personas the personas to set
     */
    public void setPersonas(LazyDataModel<Personas> personas) {
        this.personas = personas;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(Direcciones direccion) {
        this.direccion = direccion;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    /**
     * @param listaPersonas the listaPersonas to set
     */
    public void setListaPersonas(List<Personas> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    /**
     * @param claveBusqueda the claveBusqueda to set
     */
    public void setClaveBusqueda(String claveBusqueda) {
        this.claveBusqueda = claveBusqueda;
    }

    /**
     * @return the archivosBean
     */
    public ArchivosBean getArchivosBean() {
        return archivosBean;
    }

    /**
     * @param archivosBean the archivosBean to set
     */
    public void setArchivosBean(ArchivosBean archivosBean) {
        this.archivosBean = archivosBean;
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
     * @return the parametroBusqueda
     */
    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    /**
     * @param parametroBusqueda the parametroBusqueda to set
     */
    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

}
