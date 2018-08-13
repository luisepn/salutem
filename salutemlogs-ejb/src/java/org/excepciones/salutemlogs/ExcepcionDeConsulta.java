package org.excepciones.salutemlogs;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
public class ExcepcionDeConsulta extends Exception {

    /**
     *
     * @param message Ha ocurrido un error al tratar de consultar en la base de datos
     * @param cause Causa del error
     */
    public ExcepcionDeConsulta(String message, Throwable cause) {
        super("Ha ocurrido un error al tratar de consultar en la base de datos:\n" + message + " \n " + cause.getMessage(), cause);
    }

    /**
     *
     * @param message Ha ocurrido un error al tratar de consultar en la base de datos
     */
    public ExcepcionDeConsulta(String message) {
        super("Ha ocurrido un error al tratar de consultar en la base de datos:\n" + message);
    }
}
