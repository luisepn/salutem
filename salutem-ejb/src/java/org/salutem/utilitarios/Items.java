/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.utilitarios;

/**
 *
 * @author usuario
 */
public class Items {

    private int clave;
    private String valor;

    public Items(int clave, String valor) {
        this.clave = clave;
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        return clave;
    }

    /**
     * @return the clave
     */
    public int getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(int clave) {
        this.clave = clave;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor;
    }

}
