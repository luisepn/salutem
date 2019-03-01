package org.salutem.seguridad;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import org.salutem.controladores.PersonasFacade;
import org.salutem.entidades.Instituciones;
import org.salutem.entidades.Personas;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.utilitarios.Codificador;
import org.salutem.utilitarios.CorreosFacade;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;
import org.salutemlogs.controladores.AsincronoLogFacade;

@Named("ingresoSistema")
@ViewScoped
public class IngresoSistemaBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;

    private Personas persona;

    private String usr;
    private String pwd;
    private String claveNueva;
    private String claveRetipeada;
    private String claveAnterior;
    private Instituciones institucion;
    private String estilo;

    private String cedula;
    private String email;

    private Formulario formulario = new Formulario();
    private Formulario formularioRestablecer = new Formulario();

    @EJB
    private PersonasFacade ejbPersonas;
    @EJB
    protected AsincronoLogFacade ejbLogs;
    @EJB
    protected CorreosFacade ejbCorreos;

    public IngresoSistemaBean() {
    }

    @PostConstruct
    private void iniciar() {
        seguridadBean.iniciar();
        institucion = seguridadBean.getInstitucion();
        estilo = seguridadBean.getEstilo();
    }

    public String login() {
        try {
            formulario.cancelar();
            if ((usr == null) || (usr.isEmpty())) {
                Mensajes.advertencia("Ingrese un usuario válido");
                return null;
            }
            if ((pwd == null) || (pwd.isEmpty())) {
                Mensajes.advertencia("Ingrese una clave válida");
                return null;
            }
            persona = ejbPersonas.login(usr, Codificador.getEncoded(pwd, "SHA-256"));

            if (persona == null) {
                String mensajeLog = "Usuario no registrado, o clave inválida";
                Mensajes.advertencia(mensajeLog);
                ejbLogs.log(mensajeLog, 'I', usr, seguridadBean.getCurrentClientIpAddress());
                return null;
            }
            if (!persona.getActivo()) {
                String mensajeLog = "Usuario no activo";
                Mensajes.advertencia(mensajeLog);
                ejbLogs.log(mensajeLog, 'I', persona.getUserid(), seguridadBean.getCurrentClientIpAddress());
                persona = null;
                return null;
            }

            if (persona.getClave().equals(Codificador.getEncoded(persona.getCedula(), "SHA-256"))) {
                formulario.editar();
                return null;
            }
            return seguridadBean.setCredenciales(persona);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public String cambio() {
        try {
            if ((claveAnterior == null) && (claveAnterior.isEmpty())) {
                Mensajes.advertencia("Ingrese una clave válida");
                return null;
            }
            if ((claveNueva == null) && (claveNueva.isEmpty())) {
                Mensajes.advertencia("Ingrese una clave válida");
                return null;
            }
            if ((claveRetipeada == null) && (claveRetipeada.isEmpty())) {
                Mensajes.advertencia("Ingrese una clave válida");
                return null;
            }
            String cnCodificada = Codificador.getEncoded(claveNueva, "SHA-256");
            String caCodificada = Codificador.getEncoded(claveAnterior, "SHA-256");
            String cnrCodificada = Codificador.getEncoded(claveRetipeada, "SHA-256");
            if (!caCodificada.equals(persona.getClave())) {
                Mensajes.advertencia("Ingrese una clave anterior válida");
                return null;
            }
            if (!cnCodificada.equals(cnrCodificada)) {
                Mensajes.advertencia("Ingrese una clave retipeada igual a la nueva clave");
                return null;
            }

            if (seguridadBean.validarClave(claveNueva)) {
                return null;
            }
            String where = " o.activo = true and o.userid=:userid and o.clave=:clave";

            Map parametros = new HashMap();
            parametros.put("userid", usr);
            parametros.put("clave", cnCodificada);
            try {
                List<Personas> lista = ejbPersonas.buscar(where, parametros);
                if (!lista.isEmpty()) {
                    Mensajes.advertencia("Clave no válida, intentelo de nuevo.");
                    return null;
                }
            } catch (ExcepcionDeConsulta ex) {
                Logger.getLogger(IngresoSistemaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            persona.setClave(cnCodificada);
            ejbPersonas.actualizar(persona, persona.getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String restablecerClave() {
        cedula = null;
        email = null;
        formularioRestablecer.insertar();
        return null;
    }

    public String enviar() {
        try {
            String where = "o.cedula=:cedula and o.email=:email";
            Map parameters = new HashMap();
            parameters.put("cedula", cedula);
            parameters.put("email", email);

            List<Personas> personas = ejbPersonas.buscar(where, parameters);
            if (personas.isEmpty()) {
                Mensajes.advertencia("Sus datos no fueron encontrados en nuestra base de datos, revise los datos que proporciona.");
                return null;
            } else {
                Personas p = personas.get(0);
                p.setClave(Codificador.getEncoded(p.getCedula(), "SHA-256"));
                ejbPersonas.actualizar(p, "salutem", seguridadBean.getCurrentClientIpAddress());
                String body = "";
                body += "<html>";
                body += "</br>";
                body += "<p>Estimado/a <b>" + p.toString() + ":</b></p>";
                body += "<p>Su contraseña para acceder al Sistema Médico Salutem ha sido restablecida. Su usuario es <b><i>" + p.getUserid() + "</i></b> y su contraseña ahora es su número de cédula.</p>";
                body += "<p>Si usted no ha solicitado la recuperación de su contraseña, por favor ignore éste correo electrónico y cambie inmediatamente sus credenciales.</p>";
                body += "</br></br>";
                body += "<p>Atentamente:</p>";
                body += "Salutem, Sistema Médico";
                body += "<html>";
                ejbCorreos.enviarCorreo(p.getEmail(), "Recuperación de Contraseña Salutem", body);
                Mensajes.informacion("Se enviará una notificación a " + p.getEmail());
                formularioRestablecer.cancelar();
            }
        } catch (MessagingException | UnsupportedEncodingException | ExcepcionDeConsulta | ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(IngresoSistemaBean.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return the persona
     */
    public Personas getPersona() {
        return persona;
    }

    /**
     * @return the usr
     */
    public String getUsr() {
        return usr;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @return the claveNueva
     */
    public String getClaveNueva() {
        return claveNueva;
    }

    /**
     * @return the claveRetipeada
     */
    public String getClaveRetipeada() {
        return claveRetipeada;
    }

    /**
     * @return the claveAnterior
     */
    public String getClaveAnterior() {
        return claveAnterior;
    }

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @param seguridadBean the seguridadBean to set
     */
    public void setSeguridadBean(SeguridadBean seguridadBean) {
        this.seguridadBean = seguridadBean;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    /**
     * @param usr the usr to set
     */
    public void setUsr(String usr) {
        this.usr = usr;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @param claveNueva the claveNueva to set
     */
    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    /**
     * @param claveRetipeada the claveRetipeada to set
     */
    public void setClaveRetipeada(String claveRetipeada) {
        this.claveRetipeada = claveRetipeada;
    }

    /**
     * @param claveAnterior the claveAnterior to set
     */
    public void setClaveAnterior(String claveAnterior) {
        this.claveAnterior = claveAnterior;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @return the institucion
     */
    public Instituciones getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
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
     * @return the formularioRestablecer
     */
    public Formulario getFormularioRestablecer() {
        return formularioRestablecer;
    }

    /**
     * @param formularioRestablecer the formularioRestablecer to set
     */
    public void setFormularioRestablecer(Formulario formularioRestablecer) {
        this.formularioRestablecer = formularioRestablecer;
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
