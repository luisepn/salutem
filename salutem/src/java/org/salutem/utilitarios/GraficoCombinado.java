/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.utilitarios;

import java.util.List;
import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.model.chart.CartesianSeries;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
public class GraficoCombinado {

    private List<CartesianSeries> grafico; //Gráfico de lineas
    private CartesianSeries subgraficoConfig; //Subgráfico de barras

    private Axis abscisaBottom; //Nombres de las categorías de abajo del gráfico
    private Axis abscisaTop; //Nombres de las categorías de arriba del gráfico

    private Axis ordenadasGeneralConfig; //Configuración general de los nombres de las categorías de arriba y abajo del gráfico
    private Axis[] ordenadasConfigSpecific; //Configuracion específica de los valores izquierdo y derecho del gráfico

    public GraficoCombinado(List<CartesianSeries> grafico, CartesianSeries subgraficoConfig, Axis abscisaBottom, Axis abscisaTop, Axis ordenadasGeneralConfig, Axis[] ordenadasConfigSpecific) {
        this.grafico = grafico;
        this.subgraficoConfig = subgraficoConfig;
        this.abscisaBottom = abscisaBottom;
        this.abscisaTop = abscisaTop;
        this.ordenadasGeneralConfig = ordenadasGeneralConfig;
        this.ordenadasConfigSpecific = ordenadasConfigSpecific;
    }

    /**
     * @return the grafico
     */
    public List<CartesianSeries> getGrafico() {
        return grafico;
    }

    /**
     * @param grafico the grafico to set
     */
    public void setGrafico(List<CartesianSeries> grafico) {
        this.grafico = grafico;
    }

    /**
     * @return the subgraficoConfig
     */
    public CartesianSeries getSubgraficoConfig() {
        return subgraficoConfig;
    }

    /**
     * @param subgraficoConfig the subgraficoConfig to set
     */
    public void setSubgraficoConfig(CartesianSeries subgraficoConfig) {
        this.subgraficoConfig = subgraficoConfig;
    }

    /**
     * @return the abscisaBottom
     */
    public Axis getAbscisaBottom() {
        return abscisaBottom;
    }

    /**
     * @param abscisaBottom the abscisaBottom to set
     */
    public void setAbscisaBottom(Axis abscisaBottom) {
        this.abscisaBottom = abscisaBottom;
    }

    /**
     * @return the abscisaTop
     */
    public Axis getAbscisaTop() {
        return abscisaTop;
    }

    /**
     * @param abscisaTop the abscisaTop to set
     */
    public void setAbscisaTop(Axis abscisaTop) {
        this.abscisaTop = abscisaTop;
    }

    /**
     * @return the ordenadasGeneralConfig
     */
    public Axis getOrdenadasGeneralConfig() {
        return ordenadasGeneralConfig;
    }

    /**
     * @param ordenadasGeneralConfig the ordenadasGeneralConfig to set
     */
    public void setOrdenadasGeneralConfig(Axis ordenadasGeneralConfig) {
        this.ordenadasGeneralConfig = ordenadasGeneralConfig;
    }

    /**
     * @return the ordenadasConfigSpecific
     */
    public Axis[] getOrdenadasConfigSpecific() {
        return ordenadasConfigSpecific;
    }

    /**
     * @param ordenadasConfigSpecific the ordenadasConfigSpecific to set
     */
    public void setOrdenadasConfigSpecific(Axis[] ordenadasConfigSpecific) {
        this.ordenadasConfigSpecific = ordenadasConfigSpecific;
    }

}
