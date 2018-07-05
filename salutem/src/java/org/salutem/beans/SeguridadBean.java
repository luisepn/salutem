package org.salutem.beans;

import java.io.IOException;
import java.io.Serializable;
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
import org.controladores.salutem.UsuariosFacade;
import org.controladores.salutem.MenusFacade;
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
    private List<Usuarios> usuariosPorGrupo;

    private String clave;
    private String claveNueva;
    private String claveNuevaRetipeada;
    private Boolean activo = true;

    @EJB
    private UsuariosFacade ejbUsuarios;
    @EJB
    protected PersonasFacade ejbPersonas;
    @EJB
    private PerfilesFacade ejbPerfiles;
    @EJB
    private MenusFacade ejbMenus;

    public String setCredenciales(Personas logueado) {
        try {
            if (logueado != null) {
                Map parameters = new HashMap();
                parameters.put("persona", logueado);
                usuariosPorGrupo = ejbUsuarios.buscar("o.persona=:persona", parameters);
                if (!usuariosPorGrupo.isEmpty()) {
                    seleccionarGrupo(usuariosPorGrupo.get(0));
                }
                this.logueado = logueado;
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario.getModulo().getParametros().trim() + ".jsf?faces-redirect=true";
    }

    public SelectItem[] getComboUsuariosPorGrupo() {
        if (usuariosPorGrupo == null) {
            return null;
        }
        SelectItem[] items = new SelectItem[usuariosPorGrupo.size()];
        int i = 0;
        for (Usuarios x : usuariosPorGrupo) {
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
        String order = " o.texto";
        Map parameters = new HashMap();
        parameters.put("modulo", usuario.getModulo());
        List<Menus> ml = ejbMenus.buscar(where, parameters, order);

        menu = new DefaultMenuModel();
        for (Menus menusistema : ml) {
            Submenu nuevoSubmenu = new Submenu();
            nuevoSubmenu.setId("sm_" + menusistema.getId());
            nuevoSubmenu.setLabel(menusistema.getNombre());

            where = " o.menu.menupadre=:menu and o.grupo=:grupo";
            order = " o.menu.texto";
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
                ctx.redirect(ctxPath + "?m=Sin perfil v&aacute;lido");
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
                ctx.redirect(ctxPath + "?m=Usuario logueado no est&aacute; en el grupo correcto");
            }
            titulo = perfil.getMenu().getNombre();

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
     * @return the usuariosPorGrupo
     */
    public List<Usuarios> getUsuariosPorGrupo() {
        return usuariosPorGrupo;
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
     * @param usuariosPorGrupo the usuariosPorGrupo to set
     */
    public void setUsuariosPorGrupo(List<Usuarios> usuariosPorGrupo) {
        this.usuariosPorGrupo = usuariosPorGrupo;
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
     * @return the activo
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
