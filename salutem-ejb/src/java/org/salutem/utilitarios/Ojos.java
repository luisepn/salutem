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
public class Ojos {

    private String i;
    private String d;

    public Ojos() {
    }

    public Ojos(String izquierdo, String derecho) {
        this.i = izquierdo;
        this.d = derecho;
    }

    /**
     * @return the i
     */
    public String getI() {
        return i;
    }

    /**
     * @param i the i to set
     */
    public void setI(String i) {
        this.i = i;
    }

    /**
     * @return the d
     */
    public String getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(String d) {
        this.d = d;
    }

}
