/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.utilitarios;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.icefaces.ace.component.fileentry.FileEntryStatus;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
public class Mensajes {

    public static void error(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        message.setSummary("¡Error!");
        message.setDetail(mensaje.replaceAll("\n", " ").replaceAll("\\.", "\\. "));
        context.addMessage("¡Error!", message);
    }

    public static void fatal(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_FATAL);
        message.setSummary("¡Fatal!");
        message.setDetail(mensaje.replaceAll("\n", " ").replaceAll("\\.", "\\. "));
        context.addMessage("¡Fatal!", message);
    }

    public static void informacion(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("¡Información!");
        message.setDetail(mensaje.replaceAll("\n", " ").replaceAll("\\.", ""));
        context.addMessage("¡Información!", message);
    }

    public static void advertencia(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_WARN);
        message.setSummary("¡Advertencia!");
        message.setDetail(mensaje.replaceAll("\n", " ").replaceAll("\\.", "\\. "));
        context.addMessage("¡Advertencia!", message);
    }

    public static FileEntryStatus getfileEntryStatus(boolean status, String mensaje) {

        FileEntryStatus retorno = new FileEntryStatus() {
            @Override
            public boolean isSuccess() {
                return status;
            }

            @Override
            public FacesMessage getFacesMessage(FacesContext fc, UIComponent uic, FileEntryResults.FileInfo fi) {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage();

                message.setSeverity(status ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR);
                message.setSummary(status ? "¡Información!" : "¡Error!");
                message.setDetail(mensaje.replaceAll("\n", " ").replaceAll("\\.", "\\. "));
                context.addMessage(message.getSummary(), message);

                return message;
            }
        };
        return retorno;
    }
}
