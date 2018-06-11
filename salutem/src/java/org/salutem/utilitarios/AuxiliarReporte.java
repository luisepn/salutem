/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.utilitarios;

import com.lowagie.text.Element;

/**
 *
 * @author luis
 */
public class AuxiliarReporte {

    public static int ALIGN_LEFT = Element.ALIGN_LEFT;
    public static int ALIGN_RIGHT = Element.ALIGN_RIGHT;
    public static int ALIGN_CENTER = Element.ALIGN_CENTER;
    
    private String dato;
    private Object valor;
    private int alineacion;
    private boolean negrilla;
    private int columnas;
    private int tamanio;

    public AuxiliarReporte(String dato, int tamanio, int alineacion, int columnas, boolean negrilla, Object valor) {
        this.dato = dato;
        this.valor = valor;
        this.alineacion = alineacion;
        this.negrilla = negrilla;
        this.columnas = columnas;
        this.tamanio = tamanio;
    }

    public AuxiliarReporte(String dato, Object valor) {
        this.dato = dato;
        this.valor = valor;
    }

    /**
     * @return the dato
     */
    public String getDato() {
        return dato;
    }

    /**
     * @param dato the dato to set
     */
    public void setDato(String dato) {
        this.dato = dato;
    }

    /**
     * @return the valor
     */
    public Object getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Object valor) {
        this.valor = valor;
    }

    /**
     * @return the alineacion
     */
    public int getAlineacion() {
        return alineacion;
    }

    /**
     * @param alineacion the alineacion to set
     */
    public void setAlineacion(int alineacion) {
        this.alineacion = alineacion;
    }

    /**
     * @return the negrilla
     */
    public boolean isNegrilla() {
        return negrilla;
    }

    /**
     * @param negrilla the negrilla to set
     */
    public void setNegrilla(boolean negrilla) {
        this.negrilla = negrilla;
    }

    /**
     * @return the columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * @param columnas the columnas to set
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    /**
     * @return the tamanio
     */
    public int getTamanio() {
        return tamanio;
    }

    /**
     * @param tamanio the tamanio to set
     */
    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

}
