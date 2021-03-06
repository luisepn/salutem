package org.salutem.seguridad;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.salutem.controladores.InstitucionesFacade;
import org.salutem.controladores.UsuariosFacade;
import org.salutem.controladores.MenusFacade;
import org.salutem.controladores.ParametrosFacade;
import org.salutem.controladores.PerfilesFacade;
import org.salutem.controladores.PersonasFacade;
import org.salutem.controladores.ProfesionalesFacade;
import org.salutemlogs.controladores.AsincronoLogFacade;
import org.salutem.entidades.Parametros;
import org.salutem.entidades.Instituciones;
import org.salutem.entidades.Menus;
import org.salutem.entidades.Perfiles;
import org.salutem.entidades.Personas;
import org.salutem.entidades.Profesionales;
import org.salutem.entidades.Usuarios;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.icefaces.ace.component.menuitem.MenuItem;
import org.icefaces.ace.component.submenu.Submenu;
import org.icefaces.ace.model.DefaultMenuModel;
import org.icefaces.ace.model.MenuModel;
import org.salutem.controladores.PacientesFacade;
import org.salutem.entidades.Pacientes;
import org.salutem.general.CombosBean;
import org.salutem.utilitarios.Codificador;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 24 de Noviembre de 2017, 10:20:09 AM
 */
@SessionScoped
@Named("salutemSeguridad")
public class SeguridadBean implements Serializable {

    private Personas logueado;
    private Formulario formulario = new Formulario();
    private Usuarios usuario;
    private MenuModel menu;
    private Parametros grupo;
    private Instituciones institucion;
    private Profesionales profesional;
    private Pacientes paciente;
    private String titulo;
    private List<Usuarios> usuarios;
    private String idPerfil = "0";

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
    private String estilo;

    private ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
    private String rutaDeContexto = ((ServletContext) contexto.getContext()).getContextPath();
    private String ultimaRutaVisitada;

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
    @EJB
    private ProfesionalesFacade ejbProfesionales;
    @EJB
    private PacientesFacade ejbPacientesFacade;
    @EJB
    private AsincronoLogFacade ejbLogs;

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
                    case "STYLE":
                        estilo = p.getParametros() != null && !p.getParametros().isEmpty() ? p.getParametros().trim() : "base.css";
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
                    ejbLogs.log("Ingreso exitoso", 'I', logueado.getUserid(), getCurrentClientIpAddress());
                    ultimaRutaVisitada = usuario.getModulo().getParametros().trim() + ".salutem?faces-redirect=true";
                    return ultimaRutaVisitada;
                } else {
                    String mensajeLog = "Usuario no tiene perfil asignado";
                    Mensajes.advertencia(mensajeLog);
                    ejbLogs.log(mensajeLog, 'I', logueado.getUserid(), getCurrentClientIpAddress());
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
            contexto.redirect(rutaDeContexto + usuario.getModulo().getParametros().trim() + ".salutem?faces-redirect=true");
        } catch (ExcepcionDeConsulta | IOException ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambiarUsuarioPorGrupo(ActionEvent event) {
        try {
            seleccionarGrupo(ejbUsuarios.buscar(Integer.parseInt(event.getComponent().getId().replaceAll("_", ""))));
            ultimaRutaVisitada = usuario.getModulo().getParametros().trim() + ".salutem?faces-redirect=true";
            contexto.redirect(rutaDeContexto + ultimaRutaVisitada);
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

        this.profesional = ejbProfesionales.traerProfesional(logueado, institucion);
        this.paciente = ejbPacientesFacade.traerPaciente(logueado, institucion);

        String where = " o.modulo=:modulo";
        String order = " o.codigo";
        Map parameters = new HashMap();

        parameters.put("modulo", usuario.getModulo());
        List<Menus> ml = ejbMenus.buscar(where, parameters, order);

        menu = new DefaultMenuModel();

        Submenu submenu;

        for (Menus menusistema : ml) {
            submenu = new Submenu();
            submenu.setId("sm_" + menusistema.getId());
            submenu.setLabel(menusistema.getNombre());
            submenu.setIcon(menusistema.getIcono());

            where = " o.menu.menupadre=:menu and o.grupo=:grupo";
            order = " o.menu.codigo";
            parameters = new HashMap();
            parameters.put("menu", menusistema);
            parameters.put("grupo", grupo);
            List<Perfiles> pl = ejbPerfiles.buscar(where, parameters, order);
            for (Perfiles p : pl) {
                MenuItem item = new MenuItem();
                item.setId(submenu.getId() + "_mmi_" + p.getId());
                item.setValue(p.getMenu().getNombre());
                item.setUrl(p.getMenu().getFormulario().trim() + ".salutem?faces-redirect=true&p=" + p.getId());
                item.setIcon(p.getMenu().getIcono());
                submenu.getChildren().add(item);
            }
            menu.addSubmenu(submenu);
        }

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ExpressionFactory expressionFactory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
        submenu = new Submenu();

        submenu.setId("sm_0000");
        submenu.setLabel("Módulo: " + usuario.getModulo().getNombre() + " >> " + "Grupo: " + usuario.getGrupo().getNombre());
        submenu.setIcon("ui-icon ui-icon-comment");
        for (Usuarios u : usuarios) {
            MenuItem item = new MenuItem();
            item.setId("_" + u.getId());
            item.setValue(u.getModulo().getNombre() + " >> " + u.getGrupo().getNombre());
            item.setIcon("ui-icon ui-icon-comment");
            item.addActionListener(new MethodExpressionActionListener(expressionFactory.createMethodExpression(elContext,
                    "#{salutemSeguridad.cambiarUsuarioPorGrupo}", null, new Class[]{ActionEvent.class})));
            submenu.getChildren().add(item);
        }

        menu.addSubmenu(submenu);

    }

    public void logout() {
        try {
            if (logueado != null) {
                ejbLogs.log("Salida exitosa", 'O', logueado.getUserid(), getCurrentClientIpAddress());
            }
            logueado = null;
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
            if (formulario.equals("DashBoard")) {
                return null;
            }
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String p = (String) params.get("p");
            if (p == null) {
                p = idPerfil;
            }
            if (p == null) {
                ctx.redirect(ctxPath + "?m=Sin perfil valido");
                return null;
            }
            idPerfil = p;
            verActivos = true;
            verAgrupado = true;
            inicioCreado = null;
            finCreado = null;
            inicioActualizado = null;
            finActualizado = null;

            if (!p.equals("0")) {
                Perfiles perfil = ejbPerfiles.buscar(Integer.parseInt(p));
                if (perfil == null) {
                    logout();
                    return null;
                }
                if (!formulario.equals("Historial")) {
                    if (!formulario.contains(perfil.getMenu().getFormulario())) {
                        ctx.redirect(ctxPath + "?m=Perfil no valido");
                    }
                    if (!perfil.getGrupo().equals(usuario.getGrupo())) {
                        ctx.redirect(ctxPath + "?m=Usuario logueado no esta en el grupo correcto");
                    }
                    titulo = perfil.getMenu().getNombre();
                }
                ultimaRutaVisitada = usuario.getModulo().getNombre() + "/" + perfil.getMenu().getFormulario().trim() + ".salutem?faces-redirect=true&p=" + perfil.getId();
                return perfil;
            }

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

    public boolean validarClave(String password) {

        if (password.length() < 8) {
            Mensajes.error("La contraseña debe tener al menos 8 caracteres");
            return true;
        }

        char pwd;
        byte contNumero = 0;
        byte contLetraMay = 0;
        byte contLetraMin = 0;
        byte contCaracEsp = 0;
        for (byte i = 0; i < password.length(); i++) {
            pwd = password.charAt(i);
            String passValue = String.valueOf(pwd);
            if (passValue.matches("[A-Z]")) {
                contLetraMay++;
            } else if (passValue.matches("[a-z]")) {
                contLetraMin++;
            } else if (passValue.matches("[0-9]")) {
                contNumero++;
            } else if (passValue.matches("[@#$%^&+=]")) {
                contCaracEsp++;
            }
        }

        if (contLetraMay == 0) {
            Mensajes.error("La contraseña debe tener al menos una letra mayúscula");
            return true;
        }
        if (contLetraMin == 0) {
            Mensajes.error("La contraseña debe tener al menos una letra minúscula");
            return true;
        }
        if (contNumero == 0) {
            Mensajes.error("La contraseña debe tener al menos un número");
            return true;
        }
        if (contCaracEsp == 0) {
            Mensajes.error("La contraseña debe tener al menos uno de los siguientes caracteres especiales: @ # $ % ^ & + =");
            return true;
        }
        return false;
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
        String claveCodificada = Codificador.getEncoded(clave, "SHA-256");
        if (!logueado.getClave().equals(claveCodificada)) {
            Mensajes.advertencia("Clave anterior no es la correcta");
            return null;
        }
        if (validarClave(claveNueva)) {
            return null;
        }
        claveCodificada = Codificador.getEncoded(claveNueva, "SHA-256");
        logueado.setClave(claveCodificada);

        try {
            ejbPersonas.actualizar(logueado, logueado.getUserid(), getCurrentClientIpAddress());
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

    public String getCurrentClientIpAddress() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public Date getFechaActual() {
        return new Date();
    }

    public String getFechaConFormato(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat(this.formatoFechaHora);
        return format.format(fecha);
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

    /**
     * @return the profesional
     */
    public Profesionales getProfesional() {

        try {
            if (profesional != null) {
                profesional = ejbProfesionales.buscar(profesional.getId());
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return profesional;
    }

    /**
     * @param profesional the profesional to set
     */
    public void setProfesional(Profesionales profesional) {
        this.profesional = profesional;
    }

    /**
     * @return the estilo
     */
    public String getEstilo() {
        return estilo;
    }

    /**
     * @param estilo the estilo to set
     */
    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    /**
     * @return the contexto
     */
    public ExternalContext getContexto() {
        return contexto;
    }

    /**
     * @param contexto the contexto to set
     */
    public void setContexto(ExternalContext contexto) {
        this.contexto = contexto;
    }

    /**
     * @return the rutaDeContexto
     */
    public String getRutaDeContexto() {
        return rutaDeContexto;
    }

    /**
     * @param rutaDeContexto the rutaDeContexto to set
     */
    public void setRutaDeContexto(String rutaDeContexto) {
        this.rutaDeContexto = rutaDeContexto;
    }

    /**
     * @return the paciente
     */
    public Pacientes getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the ultimaRutaVisitada
     */
    public String getUltimaRutaVisitada() {
        return ultimaRutaVisitada;
    }

    /**
     * @param ultimaRutaVisitada the ultimaRutaVisitada to set
     */
    public void setUltimaRutaVisitada(String ultimaRutaVisitada) {
        this.ultimaRutaVisitada = ultimaRutaVisitada;
    }

}
