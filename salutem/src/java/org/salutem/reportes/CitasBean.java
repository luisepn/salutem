package org.salutem.reportes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.controladores.salutem.CitasFacade;
import org.entidades.salutem.Parametros;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.chart.CartesianSeries.CartesianType;
import org.salutem.beans.CombosBean;
import org.salutem.beans.SeguridadBean;
import org.salutem.utilitarios.Mensajes;
import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.component.chart.AxisType;
import org.icefaces.ace.model.chart.SectorSeries;
import org.salutem.utilitarios.GraficoCombinado;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 18 de Noviembre de 2018, 14:39:25 AM
 *
 */
@Named("salutemReporteCitas")
@ViewScoped
public class CitasBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;
    @Inject
    @Any
    private CombosBean combosBean;

    private List<Parametros> especialidades;

    private GraficoCombinado graficoCitasPorDia;
    private GraficoCombinado graficoCitasPorMes;

    @EJB
    private CitasFacade ejbCitas;

    @PostConstruct
    public void iniciar() {
        especialidades = combosBean.getListaEspecialidades();
        graficoCitasPorDia = citasPorDia();
        graficoCitasPorMes = citasPorMes();
    }

    private GraficoCombinado citasPorDia() {
        List<Parametros> dias = new LinkedList<>();
        dias.add(new Parametros("Lunes", 2));
        dias.add(new Parametros("Martes", 3));
        dias.add(new Parametros("Miércoles", 4));
        dias.add(new Parametros("Jueves", 5));
        dias.add(new Parametros("Viernes", 6));
        dias.add(new Parametros("Sabado", 7));
        dias.add(new Parametros("Domingo", 1));

        //Inicio gráfico: configuración y primer llenado
        List<CartesianSeries> grafico = new ArrayList<>();
        CartesianSeries citasPorDia = new CartesianSeries();
        citasPorDia.setPointLabels(Boolean.TRUE);
        citasPorDia.setPointLabelStacked(Boolean.TRUE);
        citasPorDia.setType(CartesianType.LINE);
        citasPorDia.setLabel("Citas");
        citasPorDia.setYAxis(2);
        citasPorDia.setXAxis(2);
        for (Parametros d : dias) {
            citasPorDia.add(d.getNombre(), getFrecuencia('D', d.getId(), null));
        }
        grafico.add(citasPorDia);
        //Fin gráfico: configuración y primer llenado

        //Inicio gráfico: llenado con subgráficos
        for (Parametros e : especialidades) {
            CartesianSeries subgrafico = new CartesianSeries();
            for (Parametros d : dias) {
                subgrafico.add(getFrecuencia('D', d.getId(), e));
                subgrafico.setLabel(e.getNombre());
                subgrafico.setPointLabels(Boolean.TRUE);
                subgrafico.setPointLabelStacked(Boolean.TRUE);
            }
            grafico.add(subgrafico);

        }
        //Fin gráfico: llenado con subgráficos

        //Inicio configuración subgráfico
        CartesianSeries subgraficoConfig = new CartesianSeries();
        subgraficoConfig.setType(CartesianType.BAR);
        subgraficoConfig.setFillToZero(false);
        //Fin configuración gráfico

        //Inicio configuración de ordenadas arriba y abajo
        Axis ordenadasGeneralConfig = new Axis();
        ordenadasGeneralConfig.setTickAngle(-45);
        //Fin configuración de ordenadas

        Axis yLeft = new Axis();
        yLeft.setAutoscale(true);
        yLeft.setTickInterval("5");
        yLeft.setLabel("Citas");

        Axis yRight = new Axis();
        yRight.setAutoscale(true);
        yRight.setTickInterval("5");
        yRight.setLabel("Citas\nPor\nEspecialidad");

        Axis[] ordenadasConfigSpecific = new Axis[2];
        ordenadasConfigSpecific[0] = yLeft;
        ordenadasConfigSpecific[1] = yRight;

        String[] ticks = new String[7];
        int i = 0;
        for (Parametros d : dias) {
            ticks[i++] = d.getNombre();
        }

        Axis abscisaBottom = new Axis();
        //abscisaBottom.setTicks(ticks);
        abscisaBottom.setType(AxisType.CATEGORY);

        Axis abscisaTop = new Axis();
        abscisaTop.setTicks(ticks);
        abscisaTop.setType(AxisType.CATEGORY);

        return new GraficoCombinado(grafico, subgraficoConfig, abscisaBottom, abscisaTop, ordenadasGeneralConfig, ordenadasConfigSpecific);
    }

    private GraficoCombinado citasPorMes() {
        List<Parametros> meses = new LinkedList<>();
        meses.add(new Parametros("Enero", 0));
        meses.add(new Parametros("Febrero", 1));
        meses.add(new Parametros("Marzo", 2));
        meses.add(new Parametros("Abril", 3));
        meses.add(new Parametros("Mayo", 4));
        meses.add(new Parametros("Junio", 5));
        meses.add(new Parametros("Julio", 6));
        meses.add(new Parametros("Agosto", 7));
        meses.add(new Parametros("Septiembre", 8));
        meses.add(new Parametros("Octubre", 9));
        meses.add(new Parametros("Noviembre", 10));
        meses.add(new Parametros("Diciembre", 11));

        //Inicio gráfico: configuración y primer llenado
        List<CartesianSeries> grafico = new ArrayList<>();
        CartesianSeries citasPorMes = new CartesianSeries();
        citasPorMes.setPointLabels(Boolean.TRUE);
        citasPorMes.setPointLabelStacked(Boolean.TRUE);
        citasPorMes.setType(CartesianType.LINE);
        citasPorMes.setLabel("Citas");
        citasPorMes.setYAxis(2);
        citasPorMes.setXAxis(2);
        for (Parametros m : meses) {
            citasPorMes.add(m.getNombre(), getFrecuencia('M', m.getId(), null));
        }
        grafico.add(citasPorMes);
        //Fin gráfico: configuración y primer llenado

        //Inicio gráfico: llenado con subgráficos
        for (Parametros e : especialidades) {
            CartesianSeries subgrafico = new CartesianSeries();
            for (Parametros d : meses) {
                subgrafico.add(getFrecuencia('M', d.getId(), e));
                subgrafico.setLabel(e.getNombre());
                subgrafico.setPointLabels(Boolean.TRUE);
                subgrafico.setPointLabelStacked(Boolean.TRUE);
            }
            grafico.add(subgrafico);

        }
        //Fin gráfico: llenado con subgráficos

        //Inicio configuración subgráfico
        CartesianSeries subgraficoConfig = new CartesianSeries();
        subgraficoConfig.setType(CartesianType.BAR);
        subgraficoConfig.setFillToZero(false);
        //Fin configuración gráfico

        //Inicio configuración de ordenadas arriba y abajo
        Axis ordenadasGeneralConfig = new Axis();
        ordenadasGeneralConfig.setTickAngle(-45);
        //Fin configuración de ordenadas

        Axis yLeft = new Axis();
        yLeft.setAutoscale(true);
        yLeft.setTickInterval("10");
        yLeft.setLabel("Citas");

        Axis yRight = new Axis();
        yRight.setAutoscale(true);
        yRight.setTickInterval("10");
        yRight.setLabel("Citas\nPor\nEspecialidad");

        Axis[] ordenadasConfigSpecific = new Axis[2];
        ordenadasConfigSpecific[0] = yLeft;
        ordenadasConfigSpecific[1] = yRight;

        String[] ticks = new String[12];
        int i = 0;
        for (Parametros d : meses) {
            ticks[i++] = d.getNombre();
        }

        Axis abscisaBottom = new Axis();
        //abscisaBottom.setTicks(ticks);
        abscisaBottom.setType(AxisType.CATEGORY);

        Axis abscisaTop = new Axis();
        abscisaTop.setTicks(ticks);
        abscisaTop.setType(AxisType.CATEGORY);

        return new GraficoCombinado(grafico, subgraficoConfig, abscisaBottom, abscisaTop, ordenadasGeneralConfig, ordenadasConfigSpecific);
    }

    private int getFrecuencia(char tipo, int unidad, Parametros especialidad) {
        try {

            Date inicio = null;
            Date fin = null;
            Calendar c;

            switch (tipo) {
                case 'D':
                    c = Calendar.getInstance();
                    c.set(Calendar.DAY_OF_WEEK, unidad);
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    inicio = c.getTime();

                    c.add(Calendar.DAY_OF_WEEK, 1);
                    c.add(Calendar.MILLISECOND, -1);
                    fin = c.getTime();
                    break;
                case 'S':
                    c = Calendar.getInstance();
                    c.set(Calendar.DAY_OF_WEEK, unidad);
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    inicio = c.getTime();

                    c.add(Calendar.DAY_OF_WEEK, 7);
                    c.add(Calendar.MILLISECOND, -1);
                    fin = c.getTime();
                    break;
                case 'M':
                    c = Calendar.getInstance();
                    c.set(Calendar.MONTH, unidad);
                    c.set(Calendar.DAY_OF_MONTH, 1);
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    inicio = c.getTime();

                    c.add(Calendar.MONTH, 1);
                    c.add(Calendar.MILLISECOND, -1);
                    fin = c.getTime();
                    break;
                case 'A':
                    break;
                default:
                    inicio = new Date();
                    fin = new Date();
                    break;
            }

            String where = " o.activo=true and o.fecha between :inicio and :fin ";
            Map parameters = new HashMap();
            parameters.put("inicio", inicio);
            parameters.put("fin", fin);
//            if (combosBean.getProfesional() != null) {
//                where += " and o.profesional=:profesional";
//                parameters.put("profesional", combosBean.getProfesional());
//            }

            if (combosBean.getInstitucion() != null) {
                where += " and o.profesional.institucion=:institucion";
                parameters.put("institucion", combosBean.getInstitucion());
            }
            if (especialidad != null) {
                where += " and o.profesional.especialidad=:especialidad";
                parameters.put("especialidad", especialidad);
            }
            return ejbCitas.contar(where, parameters);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private List<SectorSeries> antencionesPorCita() {
        List<SectorSeries> retorno = new ArrayList<>();
        for (Parametros e : especialidades) {
            SectorSeries sector = new SectorSeries();
            sector.add(e.getNombre(), getFrecuencia('S', 0, e));
            sector.setShowDataLabels(true);
            sector.setDataLabelFormatString("%.2f%%");
            sector.setSliceMargin(4);
            sector.setFill(false);
            retorno.add(sector);
        }
        return retorno;
    }

    private List<SectorSeries> pieData = new ArrayList<SectorSeries>() {
        {
            add(new SectorSeries() {
                {
                    add("Heavy Industry", 12);
                    add("Retail", 9);
                    add("Light Industry", 14);
                    add("Out of Home", 16);
                    add("Commuting", 7);
                    add("Orientation", 9);
                    setShowDataLabels(true);
                    setDataLabelFormatString("%.2f%%");
                    setSliceMargin(4);
                    setFill(false);
                }
            });
        }
    };

    /**
     * @return the seguridadBean
     */
    public SeguridadBean getSeguridadBean() {
        return seguridadBean;
    }

    /**
     * @param seguridadBean the seguridadBean to set
     */
    public void setSeguridadBean(SeguridadBean seguridadBean) {
        this.seguridadBean = seguridadBean;
    }

    /**
     * @return the combosBean
     */
    public CombosBean getCombosBean() {
        return combosBean;
    }

    /**
     * @param combosBean the combosBean to set
     */
    public void setCombosBean(CombosBean combosBean) {
        this.combosBean = combosBean;
    }

    /**
     * @return the especialidades
     */
    public List<Parametros> getEspecialidades() {
        return especialidades;
    }

    /**
     * @param especialidades the especialidades to set
     */
    public void setEspecialidades(List<Parametros> especialidades) {
        this.especialidades = especialidades;
    }

    /**
     * @return the graficoCitasPorDia
     */
    public GraficoCombinado getGraficoCitasPorDia() {
        return graficoCitasPorDia;
    }

    /**
     * @param graficoCitasPorDia the graficoCitasPorDia to set
     */
    public void setGraficoCitasPorDia(GraficoCombinado graficoCitasPorDia) {
        this.graficoCitasPorDia = graficoCitasPorDia;
    }

    /**
     * @return the graficoCitasPorMes
     */
    public GraficoCombinado getGraficoCitasPorMes() {
        return graficoCitasPorMes;
    }

    /**
     * @param graficoCitasPorMes the graficoCitasPorMes to set
     */
    public void setGraficoCitasPorMes(GraficoCombinado graficoCitasPorMes) {
        this.graficoCitasPorMes = graficoCitasPorMes;
    }
}
