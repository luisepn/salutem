package org.excepciones.salutem;

import javax.ejb.ApplicationException;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
@ApplicationException(rollback = true)
public class ExcepcionDeEliminacion
        extends Exception {

    /**
     *
     * @param message Ha ocurrido un error al tratar de eliminar el registro
     * @param cause Causa del error
     */
    public ExcepcionDeEliminacion(String message, Throwable cause) {
        super("Ha ocurrido un error al tratar de eliminar el registro:\n" + message + " - " + cause.getMessage(), cause);
    }

    /**
     *
     * @param message Ha ocurrido un error al tratar de eliminar el registro
     */
    public ExcepcionDeEliminacion(String message) {
        super("Ha ocurrido un error al tratar de eliminar el registro:\n" + message);
    }
}
