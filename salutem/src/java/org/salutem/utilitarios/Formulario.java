/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.utilitarios;

import javax.faces.component.UIData;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
public class Formulario {

    private boolean mostrar;
    private boolean crear;
    private boolean editar;
    private boolean eliminar;
    private boolean imprimir;

    //exportar
    private String tipo = "xlsx";
    private Integer total;

    //bindig
    private UIData fila;
    private int filaIndice;

    public Formulario() {
        mostrar = false;
        crear = false;
        editar = false;
        eliminar = false;
        imprimir = false;
    }

    public void insertar() {
        mostrar = true;
        crear = true;
        editar = false;
        eliminar = false;
        imprimir = false;
    }

    public void editar() {
        mostrar = true;
        crear = false;
        editar = true;
        eliminar = false;
        imprimir = false;
    }

    public void eliminar() {
        mostrar = true;
        crear = false;
        editar = false;
        eliminar = true;
        imprimir = false;
    }

    public void imprimir() {
        mostrar = true;
        crear = false;
        editar = false;
        eliminar = false;
        imprimir = true;
    }

    public void cancelar() {
        mostrar = false;
        crear = false;
        editar = false;
        eliminar = false;
        imprimir = false;
    }

    public String getRowsPerPageTemplate() {
        if (total == null) {
            return "0";
        } else if (total > 100) {
            return "5,10,20,30,40,50,100," + total.toString();
        } else if (total > 50) {
            return "5,10,20,30,40,50," + total.toString();
        } else if (total > 10) {
            return "5,10," + total.toString();
        } else if (total > 5) {
            return "5," + total.toString();
        } else if (total > 2) {
            return "2," + total.toString();
        } else {
            return total.toString();
        }
    }

    /**
     * @return the mostrar
     */
    public boolean isMostrar() {
        return mostrar;
    }

    /**
     * @return the crear
     */
    public boolean isCrear() {
        return crear;
    }

    /**
     * @return the editar
     */
    public boolean isEditar() {
        return editar;
    }

    /**
     * @return the eliminar
     */
    public boolean isEliminar() {
        return eliminar;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        if (total == null) {
            return 0;
        }
        return total;
    }

    /**
     * @return the fila
     */
    public UIData getFila() {
        return fila;
    }

    /**
     * @return the filaIndice
     */
    public int getFilaIndice() {
        return filaIndice;
    }

    /**
     * @param mostrar the mostrar to set
     */
    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    /**
     * @param crear the crear to set
     */
    public void setCrear(boolean crear) {
        this.crear = crear;
    }

    /**
     * @param editar the editar to set
     */
    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    /**
     * @param eliminar the eliminar to set
     */
    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(UIData fila) {
        this.fila = fila;
    }

    /**
     * @param filaIndice the filaIndice to set
     */
    public void setFilaIndice(int filaIndice) {
        this.filaIndice = filaIndice;
    }

    /**
     * @return the imprimir
     */
    public boolean isImprimir() {
        return imprimir;
    }

    /**
     * @param imprimir the imprimir to set
     */
    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }
}
