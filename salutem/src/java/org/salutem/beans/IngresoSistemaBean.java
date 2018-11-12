package org.salutem.beans;

import java.io.Serializable;
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
import org.controladores.salutem.PersonasFacade;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Personas;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.salutem.utilitarios.Codificador;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;

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
    private Formulario formulario = new Formulario();

    @EJB
    private PersonasFacade ejbPersonas;

    public IngresoSistemaBean() {
    }

    @PostConstruct
    private void iniciar() {
        seguridadBean.iniciar();
        institucion = seguridadBean.getInstitucion();
        estilo = seguridadBean.getEstilo();
    }

//    @PostConstruct
//    private void iniciar() {
//
//    }
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
            persona = ejbPersonas.login(usr, Codificador.getEncoded(pwd, "MD5"));

            if (persona == null) {
                String mensajeLog = "Usuario no registrado, o clave inválida";
                Mensajes.advertencia(mensajeLog);
                seguridadBean.ejbLogs.log(mensajeLog, 'I', usr, seguridadBean.getCurrentClientIpAddress());
                return null;
            }
            if (!persona.getActivo()) {
                String mensajeLog = "Usuario no activo";
                Mensajes.advertencia(mensajeLog);
                seguridadBean.ejbLogs.log(mensajeLog, 'I', persona.getUserid(), seguridadBean.getCurrentClientIpAddress());
                persona = null;
                return null;
            }

            if (persona.getClave().equals(Codificador.getEncoded(persona.getCedula(), "MD5"))) {
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
            String cnCodificada = Codificador.getEncoded(claveNueva, "MD5");
            String caCodificada = Codificador.getEncoded(claveAnterior, "MD5");
            String cnrCodificada = Codificador.getEncoded(claveRetipeada, "MD5");
            if (!caCodificada.equals(persona.getClave())) {
                Mensajes.advertencia("Ingrese una clave anterior válida");
                return null;
            }
            if (!cnCodificada.equals(cnrCodificada)) {
                Mensajes.advertencia("Ingrese una clave retipeada igual a la nueva clave");
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
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
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

}
