/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.utilitarios;

import org.entidades.salutem.Perfiles;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
public interface IMantenimiento {

    public abstract void activar();

    public abstract String buscar();

    public abstract String crear();

    public abstract String editar();

    public abstract String eliminar();

    public abstract boolean validar();

    public abstract String insertar();

    public abstract String grabar();

    public abstract String remover();

    public abstract String cancelar();

    public static boolean validarPerfil(Perfiles perfil, Character operacion) {
        if (perfil == null) {
            return false;
        }
        switch (operacion) {
            case 'C': //create
                if (!perfil.getNuevo()) {
                    Mensajes.advertencia("¡No tiene autorización para Crear!");
                }
                return perfil.getNuevo();
            case 'R': //read
                if (!perfil.getConsulta()) {
                    Mensajes.advertencia("¡No tiene autorización para Leer!");
                }
                return perfil.getConsulta();
            case 'U': //update
                if (!perfil.getModificacion()) {
                    Mensajes.advertencia("¡No tiene autorización para Actualizar!");
                }
                return perfil.getModificacion();
            case 'D': //delete
                if (!perfil.getBorrado()) {
                    Mensajes.advertencia("¡No tiene autorización para Eliminar!");
                }
                return perfil.getBorrado();
            default:
                Mensajes.advertencia("¡No puede ejecutar ninguna Acción!");
                return false;
        }

    }

}
