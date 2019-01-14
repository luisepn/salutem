package org.salutem.excepciones;

import javax.ejb.ApplicationException;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
@ApplicationException(rollback = true)
public class ExcepcionDeActualizacion extends Exception {

    /**
     *
     * @param message Ha ocurrido un error al tratar de actualizar el registro
     * @param cause Causa del error
     */
    public ExcepcionDeActualizacion(String message, Throwable cause) {
        super("Ha ocurrido un error al tratar de actualizar el registro:\n" + message + " \n " + cause.getMessage(), cause);
    }

    /**
     *
     * @param message Ha ocurrido un error al tratar de actualizar el registro
     */
    public ExcepcionDeActualizacion(String message) {
        super("Ha ocurrido un error al tratar de actualizar el registro:\n" + message);
    }
}
