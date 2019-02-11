package org.salutem.general;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.salutem.controladores.PersonasFacade;
import org.salutem.entidades.Perfiles;
import org.salutem.entidades.Personas;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.excepciones.ExcepcionDeCreacion;
import org.salutem.excepciones.ExcepcionDeEliminacion;
import org.icefaces.ace.event.TextChangeEvent;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.controladores.ParametrosFacade;
import org.salutem.controladores.UsuariosFacade;
import org.salutem.entidades.Instituciones;
import org.salutem.entidades.Parametros;
import org.salutem.entidades.Usuarios;
import org.salutem.seguridad.SeguridadBean;
import org.salutem.utilitarios.Codificador;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

public abstract class PersonasAbstractoBean implements Serializable, IMantenimiento {

    @Inject
    protected SeguridadBean seguridadBean;

    protected Formulario formulario = new Formulario();
    private Formulario formularioClave = new Formulario();
    protected LazyDataModel<Personas> personas;
    protected Personas persona;
    protected Perfiles perfil;

    protected List<Personas> listaPersonas;
    protected String parametroBusqueda = "o.apellidos";
    protected String claveBusqueda;

    protected Date fechaInicio;
    protected Date fechaFin;

    protected boolean modificarDatos = false;

    @EJB
    protected PersonasFacade ejbPersonas;
    @EJB
    protected ParametrosFacade ejbParametros;
    @EJB
    protected UsuariosFacade ejbUsuarios;

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
        formulario.editar();
        return null;
    }

    public String editarClave() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        persona = (Personas) personas.getRowData();
        persona.setClave(null);
        formularioClave.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        persona = (Personas) personas.getRowData();
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        if ((persona.getCedula() == null) || (persona.getCedula().trim().isEmpty())) {
            Mensajes.advertencia("CI o RUC es obligatorio");
            return true;
        }

        try {
            String where = "o.cedula=:cedula";
            Map parametros = new HashMap();
            parametros.put("cedula", persona.getCedula());
            if (persona.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", persona.getId());
            }
            List<Personas> l = ejbPersonas.buscar(where, parametros);
            if (!l.isEmpty()) {
                Mensajes.error("Otra persona con la cédula " + l.get(0).getCedula() + " ya se encuentra registrada en el sistema."
                        + (l.get(0).getActivo() == null || !l.get(0).getActivo() ? " Busque en los registros inactivos." : ""));
                return true;
            }

            if ((persona.getUserid() == null) || (persona.getUserid().trim().isEmpty())) {
                Mensajes.advertencia("CI o RUC es obligatorio");
                return true;
            }
            where = "o.userid=:userid";
            parametros = new HashMap();
            parametros.put("userid", persona.getUserid());
            if (persona.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", persona.getId());
            }
            l = ejbPersonas.buscar(where, parametros);
            if (!l.isEmpty()) {
                Mensajes.error("Nombre de usuario no disponible, por favor, elija uno nuevo");
                return true;
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
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
            persona.setUserid(persona.getCedula());
            persona.setClave(Codificador.getEncoded(persona.getCedula(), "MD5"));
            persona.setCreado(new Date());
            persona.setCreadopor(seguridadBean.getLogueado().getUserid());
            persona.setActualizado(persona.getCreado());
            persona.setActualizadopor(persona.getActualizadopor());
            ejbPersonas.crear(persona, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
            Mensajes.informacion("Creación exitoso. " + persona.toString());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

            if (persona.getClave() == null) {
                persona.setClave(Codificador.getEncoded(persona.getCedula(), "MD5"));
            }

            persona.setActualizado(new Date());
            persona.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbPersonas.actualizar(persona, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
            formularioClave.cancelar();
            Mensajes.informacion("Modificación exitosa. " + persona.toString());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String grabarClave() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        try {

            if (persona.getClave() == null) {
                persona.setClave(Codificador.getEncoded(persona.getCedula(), "MD5"));
            }
            persona.setClave(Codificador.getEncoded(persona.getClave(), "MD5"));
            persona.setActualizado(new Date());
            persona.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbPersonas.actualizar(persona, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
            formularioClave.cancelar();
            Mensajes.informacion("Clave atualizada" + persona.toString());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbPersonas.eliminar(persona, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
            Mensajes.informacion("Eliminación exitosa. " + persona.toString());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public void cambiaCedula(ValueChangeEvent event) {
        String nuevaCedula = (String) event.getNewValue();
        try {
            String where = "o.cedula=:cedula";
            Map parametros = new HashMap();
            parametros.put("cedula", nuevaCedula);
            if (persona.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", persona.getId());
            }
            List<Personas> l = ejbPersonas.buscar(where, parametros);
            if (!l.isEmpty()) {
                Mensajes.error("Otra persona con la cédula " + l.get(0).getCedula() + " ya se encuentra registrada en el sistema."
                        + (l.get(0).getActivo() == null || !l.get(0).getActivo() ? " Busque en los registros inactivos." : ""));
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public String fotografiaListener(FileEntryEvent e) {
        FileEntry fileEntry = (FileEntry) e.getComponent();

        FileEntryResults results = fileEntry.getResults();
        for (FileEntryResults.FileInfo i : results.getFiles()) {
            try {
                File file = i.getFile();
                if (!i.getContentType().contains("image/")) {
                    i.updateStatus(Mensajes.getfileEntryStatus(false, "¡Sólo se aceptan archivos de imágen!"), true);
                } else if (i.getStatus().isSuccess()) {
                    persona.setFotografia(Files.readAllBytes(file.toPath()));
                    i.updateStatus(Mensajes.getfileEntryStatus(true, "¡El archivo fue subido con éxito!"), true);
                } else {
                    i.updateStatus(Mensajes.getfileEntryStatus(false, "¡Se ha rechazado el requerimiento porque su tamaño excede el rango permitido: 10 MB!"), true);
                }
            } catch (IOException ex) {
                Mensajes.fatal(ex.getMessage());
                Logger.getLogger(ArchivosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void crearUsuarios(Personas persona, Instituciones institucion, String grupo, String modulo) {
        try {

            if (persona == null || institucion == null) {
                return;
            }

            Usuarios usuario = new Usuarios();
            usuario.setActivo(Boolean.TRUE);
            usuario.setCreado(new Date());
            usuario.setActualizado(usuario.getCreado());
            usuario.setCreadopor(seguridadBean.getLogueado().getUserid());
            usuario.setInstitucion(institucion);
            usuario.setPersona(persona);
            usuario.setGrupo(ejbParametros.traerParametro(CombosBean.GRUPO_DE_USUARIO, grupo));
            usuario.setModulo(ejbParametros.traerParametro(CombosBean.MODULOS_DE_SISTEMA, modulo));
            usuario.setDescripcion(grupo.equals("GP") ? "Paciente" : "Profesional");

            Map parameters = new HashMap();
            String where = "o.persona=:persona and o.grupo=:grupo and o.modulo=:modulo and o.institucion=:institucion";
            parameters.put("persona", usuario.getPersona());
            parameters.put("grupo", usuario.getGrupo());
            parameters.put("modulo", usuario.getModulo());
            parameters.put("institucion", usuario.getInstitucion());

            if (ejbUsuarios.contar(where, parameters) == 0) {
                ejbUsuarios.crear(usuario, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }
        } catch (ExcepcionDeConsulta | ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    /**
     * @return the modificarDatos
     */
    public boolean isModificarDatos() {
        return modificarDatos;
    }

    /**
     * @param modificarDatos the modificarDatos to set
     */
    public void setModificarDatos(boolean modificarDatos) {
        this.modificarDatos = modificarDatos;
    }

    /**
     * @return the formularioClave
     */
    public Formulario getFormularioClave() {
        return formularioClave;
    }

    /**
     * @param formularioClave the formularioClave to set
     */
    public void setFormularioClave(Formulario formularioClave) {
        this.formularioClave = formularioClave;
    }
}
