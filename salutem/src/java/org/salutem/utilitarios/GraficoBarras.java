/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.utilitarios;

import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.component.chart.AxisType;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.chart.CartesianSeries.CartesianType;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
public class GraficoBarras {

    private CartesianSeries grafico;
    private Axis barDefaultAxis;
    private Axis barXAxis;
    private Axis[] barYAxes;

    public GraficoBarras(String nombre) {
        grafico = new CartesianSeries();
        grafico.setLabel(nombre);
        grafico.setType(CartesianType.BAR);
        barDefaultAxis = new Axis();
        barDefaultAxis.setTickAngle(-30);

        barXAxis = new Axis();
        barXAxis.setType(AxisType.CATEGORY);

        barYAxes = new Axis[1];
        Axis a = new Axis();
        a.setLabel("");
        barYAxes[0] = a;
    }

    /**
     * @return the grafico
     */
    public CartesianSeries getGrafico() {
        return grafico;
    }

    /**
     * @param grafico the grafico to set
     */
    public void setGrafico(CartesianSeries grafico) {
        this.grafico = grafico;
    }

    /**
     * @return the barDefaultAxis
     */
    public Axis getBarDefaultAxis() {
        return barDefaultAxis;
    }

    /**
     * @param barDefaultAxis the barDefaultAxis to set
     */
    public void setBarDefaultAxis(Axis barDefaultAxis) {
        this.barDefaultAxis = barDefaultAxis;
    }

    /**
     * @return the barXAxis
     */
    public Axis getBarXAxis() {
        return barXAxis;
    }

    /**
     * @param barXAxis the barXAxis to set
     */
    public void setBarXAxis(Axis barXAxis) {
        this.barXAxis = barXAxis;
    }

    /**
     * @return the barYAxes
     */
    public Axis[] getBarYAxes() {
        return barYAxes;
    }

    /**
     * @param barYAxes the barYAxes to set
     */
    public void setBarYAxes(Axis[] barYAxes) {
        this.barYAxes = barYAxes;
    }

}
