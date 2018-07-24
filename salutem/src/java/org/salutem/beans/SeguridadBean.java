package org.salutem.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.controladores.salutem.InstitucionesFacade;
import org.controladores.salutem.UsuariosFacade;
import org.controladores.salutem.MenusFacade;
import org.controladores.salutem.ParametrosFacade;
import org.controladores.salutem.PerfilesFacade;
import org.controladores.salutem.PersonasFacade;
import org.entidades.salutem.Parametros;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Menus;
import org.entidades.salutem.Perfiles;
import org.entidades.salutem.Personas;
import org.entidades.salutem.Usuarios;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.icefaces.ace.component.menuitem.MenuItem;
import org.icefaces.ace.component.submenu.Submenu;
import org.icefaces.ace.model.DefaultMenuModel;
import org.icefaces.ace.model.MenuModel;
import org.salutem.utilitarios.Codificador;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 24 de Noviembre de 2017, 10:20:09 AM
 */
@ManagedBean(name = "salutemSeguridad")
@SessionScoped
public class SeguridadBean implements Serializable {

    private Personas logueado;
    private Formulario formulario = new Formulario();
    private Usuarios usuario;
    private MenuModel menu;
    private Parametros grupo;
    private Instituciones institucion;
    private String titulo;
    private List<Usuarios> usuarios;

    private String clave;
    private String claveNueva;
    private String claveNuevaRetipeada;

    //Parámetros iniciales
    private Boolean verActivos = true;
    private Boolean verAgrupado = true;
    private Date inicioCreado;
    private Date finCreado;
    private Date inicioActualizado;
    private Date finActualizado;

    private String formatoFecha;
    private String formatoFechaHora;
    private String directorioArchivos;

    @EJB
    private UsuariosFacade ejbUsuarios;
    @EJB
    protected PersonasFacade ejbPersonas;
    @EJB
    private PerfilesFacade ejbPerfiles;
    @EJB
    private MenusFacade ejbMenus;
    @EJB
    private ParametrosFacade ejbParametros;
    @EJB
    private InstitucionesFacade ejbInstituciones;

    public SeguridadBean() {
    }

    public void iniciar() {
        try {
            Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String mensaje = (String) params.get("m");
            if (mensaje != null && !mensaje.isEmpty()) {
                Mensajes.informacion(mensaje);
            }
            List<Parametros> lista = ejbParametros.traerParametros(CombosBean.PARAMETROS_GENERALES, "o.codigo");
            for (Parametros p : lista) {
                switch (p.getCodigo()) {
                    case "INSP":
                        institucion = ejbInstituciones.buscar(p.getParametros() != null && !p.getParametros().isEmpty() ? Integer.parseInt(p.getParametros().trim()) : 0);
                        break;
                    case "FTF":
                        formatoFecha = p.getParametros() != null && !p.getParametros().isEmpty() ? p.getParametros().trim() : null;
                        break;
                    case "FFH":
                        formatoFechaHora = p.getParametros() != null && !p.getParametros().isEmpty() ? p.getParametros().trim() : null;
                        break;
                    case "DARCH":
                        directorioArchivos = p.getParametros() != null && !p.getParametros().isEmpty() ? p.getParametros().trim() : null;
                        break;
                }
            }

            if (formatoFecha == null) {
                formatoFecha = "dd/MM/yyyy";
            }
            if (formatoFechaHora == null) {
                formatoFechaHora = "dd/MM/yyyy hh:mm:ss";
            }

        } catch (NumberFormatException | ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(IngresoSistemaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String setCredenciales(Personas logueado) {
        try {
            if (logueado != null) {
                this.logueado = logueado;
                Map parameters = new HashMap();
                parameters.put("persona", logueado);
                usuarios = ejbUsuarios.buscar("o.persona=:persona", parameters);
                if (!usuarios.isEmpty()) {
                    seleccionarGrupo(usuarios.get(0));
                    return usuario.getModulo().getParametros().trim() + ".jsf?faces-redirect=true";
                } else {
                    Mensajes.advertencia("Usuario no tiene perfil asignado");
                }
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public SelectItem[] getComboUsuariosPorGrupo() {
        if (usuarios == null) {
            return null;
        }
        SelectItem[] items = new SelectItem[usuarios.size()];
        int i = 0;
        for (Usuarios x : usuarios) {
            items[i++] = new SelectItem(x, x.getModulo() != null ? x.getModulo().getNombre() + " - " + x.getGrupo().getNombre() : x.getId().toString());
        }
        return items;
    }

    public void cambiarUsuarioPorGrupo(ValueChangeEvent event) {
        try {
            seleccionarGrupo((Usuarios) event.getNewValue());
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            ctx.redirect(ctxPath + usuario.getModulo().getParametros().trim() + ".jsf?faces-redirect=true");
        } catch (ExcepcionDeConsulta | IOException ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void seleccionarGrupo(Usuarios usuario) throws ExcepcionDeConsulta {
        this.usuario = usuario;
        this.titulo = usuario.getModulo().getNombre();
        this.grupo = usuario.getGrupo();
        this.institucion = usuario.getInstitucion();

        String where = " o.modulo=:modulo";
        String order = " o.nombre";
        Map parameters = new HashMap();
        parameters.put("modulo", usuario.getModulo());
        List<Menus> ml = ejbMenus.buscar(where, parameters, order);

        menu = new DefaultMenuModel();
        for (Menus menusistema : ml) {
            Submenu nuevoSubmenu = new Submenu();
            nuevoSubmenu.setId("sm_" + menusistema.getId());
            nuevoSubmenu.setLabel(menusistema.getNombre());

            where = " o.menu.menupadre=:menu and o.grupo=:grupo";
            order = " o.menu.nombre";
            parameters = new HashMap();
            parameters.put("menu", menusistema);
            parameters.put("grupo", grupo);
            List<Perfiles> pl = ejbPerfiles.buscar(where, parameters, order);
            for (Perfiles p : pl) {
                MenuItem nuevo = new MenuItem();
                nuevo.setId(nuevoSubmenu.getId() + "_mmi_" + p.getId());
                nuevo.setValue(p.getMenu().getNombre());
                nuevo.setUrl(p.getMenu().getFormulario().trim() + ".jsf?faces-redirect=true&p=" + p.getId());
                nuevoSubmenu.getChildren().add(nuevo);
            }
            menu.addSubmenu(nuevoSubmenu);
        }
    }

    public void logout() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            ctx.redirect(ctxPath);

        } catch (IOException ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Perfiles traerPerfil(String formulario) {
        try {
            if (logueado == null) {
                logout();
            }
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String p = (String) params.get("p");
            if (p == null) {
                ctx.redirect(ctxPath + "?m=Sin perfil valido");
                return null;
            }
            Perfiles perfil = ejbPerfiles.buscar(Integer.parseInt(p));
            if (perfil == null) {
                logout();
                return null;
            }

            if (!perfil.getMenu().getFormulario().contains(formulario)) {
                ctx.redirect(ctxPath + "?m=Perfil no v&aacute;do");
            }
            if (!perfil.getGrupo().equals(usuario.getGrupo())) {
                ctx.redirect(ctxPath + "?m=Usuario logueado no esta en el grupo correcto");
            }
            titulo = perfil.getMenu().getNombre();
            verActivos = true;
            verAgrupado = true;
            inicioCreado = null;
            finCreado = null;
            inicioActualizado = null;
            finActualizado = null;
            return perfil;

        } catch (ExcepcionDeConsulta | IOException ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String cambiarClave() {
        formulario.insertar();
        return null;
    }

    public String cancelarCambiarClave() {
        formulario.cancelar();
        clave = null;
        claveNueva = null;
        claveNuevaRetipeada = null;
        return null;
    }

    public String grabarClave() {
        if ((clave == null) || (clave.trim().isEmpty())) {
            Mensajes.advertencia("Ingrese la clave actual");
            return null;
        }
        if ((claveNueva == null) || (claveNueva.trim().isEmpty())) {
            Mensajes.advertencia("Ingrese la clave nueva");
            return null;
        }
        if ((claveNuevaRetipeada == null) || (claveNuevaRetipeada.trim().isEmpty())) {
            Mensajes.advertencia("retipee la clave nueva");
            return null;
        }
        if (!claveNueva.equals(claveNuevaRetipeada)) {
            Mensajes.advertencia("Clave nueva debe ser igual a la clave retipeada");
            return null;
        }
        String claveCodificada = Codificador.getEncoded(clave, "MD5");
        if (!logueado.getClave().equals(claveCodificada)) {
            Mensajes.advertencia("Clave anterior no es la correcta");
            return null;
        }
        claveCodificada = Codificador.getEncoded(claveNueva, "MD5");
        logueado.setClave(claveCodificada);

        try {
            ejbPersonas.actualizar(logueado, null);
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        Mensajes.advertencia("Clave se cambio correctamente");
        formulario.cancelar();
        clave = null;
        claveNueva = null;
        claveNuevaRetipeada = null;
        return null;
    }

    /**
     * @return the logueado
     */
    public Personas getLogueado() {
        return logueado;
    }

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @return the usuario
     */
    public Usuarios getUsuario() {
        return usuario;
    }

    /**
     * @return the menu
     */
    public MenuModel getMenu() {
        return menu;
    }

    /**
     * @return the grupo
     */
    public Parametros getGrupo() {
        return grupo;
    }

    /**
     * @return the institucion
     */
    public Instituciones getInstitucion() {
        return institucion;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return the usuarios
     */
    public List<Usuarios> getUsuarios() {
        return usuarios;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @return the claveNueva
     */
    public String getClaveNueva() {
        return claveNueva;
    }

    /**
     * @return the claveNuevaRetipeada
     */
    public String getClaveNuevaRetipeada() {
        return claveNuevaRetipeada;
    }

    /**
     * @param logueado the logueado to set
     */
    public void setLogueado(Personas logueado) {
        this.logueado = logueado;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(MenuModel menu) {
        this.menu = menu;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Parametros grupo) {
        this.grupo = grupo;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @param claveNueva the claveNueva to set
     */
    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    /**
     * @param claveNuevaRetipeada the claveNuevaRetipeada to set
     */
    public void setClaveNuevaRetipeada(String claveNuevaRetipeada) {
        this.claveNuevaRetipeada = claveNuevaRetipeada;
    }

    /**
     * @return the verActivos
     */
    public Boolean getVerActivos() {
        return verActivos;
    }

    /**
     * @param verActivos the verActivos to set
     */
    public void setVerActivos(Boolean verActivos) {
        this.verActivos = verActivos;
    }

    /**
     * @return the verAgrupado
     */
    public Boolean getVerAgrupado() {
        return verAgrupado;
    }

    /**
     * @param verAgrupado the verAgrupado to set
     */
    public void setVerAgrupado(Boolean verAgrupado) {
        this.verAgrupado = verAgrupado;
    }

    /**
     * @return the formatoFecha
     */
    public String getFormatoFecha() {
        return formatoFecha;
    }

    /**
     * @param formatoFecha the formatoFecha to set
     */
    public void setFormatoFecha(String formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    /**
     * @return the formatoFechaHora
     */
    public String getFormatoFechaHora() {
        return formatoFechaHora;
    }

    /**
     * @param formatoFechaHora the formatoFechaHora to set
     */
    public void setFormatoFechaHora(String formatoFechaHora) {
        this.formatoFechaHora = formatoFechaHora;
    }

    /**
     * @return the directorioArchivos
     */
    public String getDirectorioArchivos() {
        return directorioArchivos;
    }

    /**
     * @param directorioArchivos the directorioArchivos to set
     */
    public void setDirectorioArchivos(String directorioArchivos) {
        this.directorioArchivos = directorioArchivos;
    }

    /**
     * @return the inicioCreado
     */
    public Date getInicioCreado() {
        return inicioCreado;
    }

    /**
     * @param inicioCreado the inicioCreado to set
     */
    public void setInicioCreado(Date inicioCreado) {
        this.inicioCreado = inicioCreado;
    }

    /**
     * @return the finCreado
     */
    public Date getFinCreado() {
        return finCreado;
    }

    /**
     * @param finCreado the finCreado to set
     */
    public void setFinCreado(Date finCreado) {
        this.finCreado = finCreado;
    }

    /**
     * @return the inicioActualizado
     */
    public Date getInicioActualizado() {
        return inicioActualizado;
    }

    /**
     * @param inicioActualizado the inicioActualizado to set
     */
    public void setInicioActualizado(Date inicioActualizado) {
        this.inicioActualizado = inicioActualizado;
    }

    /**
     * @return the finActualizado
     */
    public Date getFinActualizado() {
        return finActualizado;
    }

    /**
     * @param finActualizado the finActualizado to set
     */
    public void setFinActualizado(Date finActualizado) {
        this.finActualizado = finActualizado;
    }

}
