/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.excepciones.salutemlogs;

import javax.ejb.ApplicationException;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
@ApplicationException(rollback = true)
public class ExcepcionDeCreacion extends Exception {

    /**
     *
     * @param message Ha ocurrido un error al tratar de crear el registro
     * @param cause Causa del error
     */
    public ExcepcionDeCreacion(String message, Throwable cause) {
        super("Ha ocurrido un error al tratar de crear el registro:\n" + message + " \n " + cause.getMessage(), cause);
    }

    /**
     *
     * @param message Ha ocurrido un error al tratar de crear el registro
     */
    public ExcepcionDeCreacion(String message) {
        super("Ha ocurrido un error al tratar de crear el registro:\n" + message);
    }
}
