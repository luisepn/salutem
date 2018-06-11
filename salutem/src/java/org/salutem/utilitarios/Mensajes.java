/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.utilitarios;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
public class Mensajes {
    public static void error(String mensaje){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new  FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        message.setSummary(mensaje);
        message.setDetail(mensaje);
        context.addMessage("¡Error!", message);
    }
    public static void fatal(String mensaje){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new  FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_FATAL);
        message.setSummary(mensaje);
        message.setDetail(mensaje);
        context.addMessage("¡Fatal!", message);
    }
    public static void informacion(String mensaje){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new  FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary(mensaje);
        message.setDetail(mensaje);
        context.addMessage("¡Información!", message);
    }
    public static void advertencia(String mensaje){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new  FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_WARN);
        message.setSummary(mensaje);
        message.setDetail(mensaje);
        context.addMessage("¡Advertencia!", message);
    }
}
