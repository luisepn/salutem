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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.salutem.entidades.Parametros;
import org.salutem.entidades.Perfiles;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.chart.CartesianSeries.CartesianType;
import org.salutem.seguridad.SeguridadBean;
import org.salutem.utilitarios.Mensajes;
import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.component.chart.AxisType;
import org.salutem.utilitarios.GraficoCombinado;
import org.salutemlogs.controladores.HistorialFacade;
import org.salutemlogs.excepciones.ExcepcionDeConsulta;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 18 de Noviembre de 2018, 14:39:25 AM
 *
 */
@Named("salutemReporteUsuarios")
@ViewScoped
public class IngresosUsuariosBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;

    private Perfiles perfil;

    private Calendar calendar;
    private Date fecha;

    private GraficoCombinado barrasLogsSemana;
    private GraficoCombinado barrasLogsPorAnio;

    @EJB
    private HistorialFacade ejbHistorial;

    @PostConstruct
    public void iniciar() {
        perfil = seguridadBean.traerPerfil("DashBoard");
        buscarTodo();
    }

    private void buscarTodo() {
        if (fecha == null) {
            fecha = new Date();
        }

        calendar = Calendar.getInstance();
        barrasLogsSemana = logsSemanal();
        barrasLogsPorAnio = logsAnio();
    }

    private GraficoCombinado logsSemanal() {
        List<Parametros> dias = new LinkedList<>();
        dias.add(new Parametros("lun", 2));
        dias.add(new Parametros("mar", 3));
        dias.add(new Parametros("mié", 4));
        dias.add(new Parametros("jue", 5));
        dias.add(new Parametros("vie", 6));
        dias.add(new Parametros("sab", 7));
        dias.add(new Parametros("dom", 1));

        //Inicio gráfico: configuración y primer llenado
        List<CartesianSeries> grafico = new ArrayList<>();
        CartesianSeries logsInOut = new CartesianSeries();
        logsInOut.setPointLabels(Boolean.TRUE);
        logsInOut.setPointLabelStacked(Boolean.TRUE);
        logsInOut.setType(CartesianType.BAR);
        logsInOut.setLabel("Log Out");
        logsInOut.setYAxis(2);
        logsInOut.setXAxis(2);
        for (Parametros d : dias) {
            logsInOut.add(d.getNombre(), getFrecuencia('O', 'D', d.getId()));
        }
        grafico.add(logsInOut);
        //Fin gráfico: configuración y primer llenado

        //Inicio gráfico: llenado con subgráficos
        CartesianSeries subgrafico = new CartesianSeries();
        for (Parametros d : dias) {
            subgrafico.add(getFrecuencia('I', 'D', d.getId()));
            subgrafico.setLabel("Log In");
            subgrafico.setPointLabels(Boolean.TRUE);
            subgrafico.setPointLabelStacked(Boolean.TRUE);
        }
        grafico.add(subgrafico);

        //Fin gráfico: llenado con subgráficos
        //Inicio configuración subgráfico
        CartesianSeries subgraficoConfig = new CartesianSeries();
        subgraficoConfig.setType(CartesianType.LINE);
        subgraficoConfig.setFillToZero(false);
        //Fin configuración gráfico

        //Inicio configuración de ordenadas arriba y abajo
        Axis ordenadasGeneralConfig = new Axis();
        ordenadasGeneralConfig.setTickAngle(0);
        //Fin configuración de ordenadas

        Axis yLeft = new Axis();
        yLeft.setAutoscale(true);
        yLeft.setTickInterval("2");
        yLeft.setLabel("Logs");

        Axis yRight = new Axis();
        yRight.setAutoscale(true);
        yRight.setTickInterval("3");
        yRight.setLabel("");

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

    private GraficoCombinado logsAnio() {
        List<Parametros> meses = new LinkedList<>();
        meses.add(new Parametros("en", 0));
        meses.add(new Parametros("feb", 1));
        meses.add(new Parametros("mar", 2));
        meses.add(new Parametros("abr", 3));
        meses.add(new Parametros("may", 4));
        meses.add(new Parametros("jun", 5));
        meses.add(new Parametros("jul", 6));
        meses.add(new Parametros("ago", 7));
        meses.add(new Parametros("sep", 8));
        meses.add(new Parametros("oct", 9));
        meses.add(new Parametros("nov", 10));
        meses.add(new Parametros("dic", 11));

        //Inicio gráfico: configuración y primer llenado
        List<CartesianSeries> grafico = new ArrayList<>();
        CartesianSeries logsInOut = new CartesianSeries();
        logsInOut.setPointLabels(Boolean.TRUE);
        logsInOut.setPointLabelStacked(Boolean.TRUE);
        logsInOut.setType(CartesianType.BAR);
        logsInOut.setLabel("Log Out");
        logsInOut.setYAxis(2);
        logsInOut.setXAxis(2);
        for (Parametros m : meses) {
            logsInOut.add(m.getNombre(), getFrecuencia('O', 'M', m.getId()));
        }
        grafico.add(logsInOut);
        //Fin gráfico: configuración y primer llenado

        //Inicio gráfico: llenado con subgráficos
        CartesianSeries subgrafico = new CartesianSeries();
        for (Parametros m : meses) {
            subgrafico.add(getFrecuencia('I', 'M', m.getId()));
            subgrafico.setLabel("Log In");
            subgrafico.setPointLabels(Boolean.TRUE);
            subgrafico.setPointLabelStacked(Boolean.TRUE);
        }
        grafico.add(subgrafico);

        //Fin gráfico: llenado con subgráficos
        //Inicio configuración subgráfico
        CartesianSeries subgraficoConfig = new CartesianSeries();
        subgraficoConfig.setType(CartesianType.LINE);
        subgraficoConfig.setFillToZero(false);
        //Fin configuración gráfico

        //Inicio configuración de ordenadas arriba y abajo
        Axis ordenadasGeneralConfig = new Axis();
        ordenadasGeneralConfig.setTickAngle(0);
        //Fin configuración de ordenadas

        Axis yLeft = new Axis();
        yLeft.setAutoscale(true);
        yLeft.setTickInterval("2");
        yLeft.setLabel("Logs");

        Axis yRight = new Axis();
        yRight.setAutoscale(true);
        yRight.setTickInterval("3");
        yRight.setLabel("");

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

    private int getFrecuencia(char tipo, char tipoUnidad, int unidad) {
        try {
            calendar.setTime(fecha);

            Date inicio;
            Date fin;

            switch (tipoUnidad) {
                case 'D'://Diario
                    calendar.set(Calendar.DAY_OF_WEEK, unidad);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    inicio = calendar.getTime();

                    calendar.add(Calendar.DAY_OF_WEEK, 1);
                    calendar.add(Calendar.MILLISECOND, -1);
                    fin = calendar.getTime();
                    break;
                case 'S'://Semanal
                    calendar.set(Calendar.DAY_OF_WEEK, unidad);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    inicio = calendar.getTime();

                    calendar.add(Calendar.DAY_OF_WEEK, 7);
                    calendar.add(Calendar.MILLISECOND, -1);
                    fin = calendar.getTime();
                    break;
                case 'M'://Mensual
                    calendar.set(Calendar.MONTH, unidad);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    inicio = calendar.getTime();

                    calendar.add(Calendar.MONTH, 1);
                    calendar.add(Calendar.MILLISECOND, -1);
                    fin = calendar.getTime();
                    break;
                case 'A'://Anual
                    calendar.set(Calendar.MONTH, 0);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    inicio = calendar.getTime();

                    calendar.add(Calendar.YEAR, 1);
                    calendar.add(Calendar.MILLISECOND, -1);
                    fin = calendar.getTime();
                    break;
                default:
                    inicio = new Date();
                    fin = new Date();
                    break;
            }

            String where = "o.operacion='" + tipo + "' and o.tabla='Logs' and o.fecha between :inicio and :fin";
            Map parameters = new HashMap();
            parameters.put("inicio", inicio);
            parameters.put("fin", fin);
            return (int) ejbHistorial.buscar(where, parameters, null, null, null, true);

        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(IngresosUsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return 0;
    }

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
     * @return the perfil
     */
    public Perfiles getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * @param calendar the calendar to set
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the barrasLogsSemana
     */
    public GraficoCombinado getBarrasLogsSemana() {
        return barrasLogsSemana;
    }

    /**
     * @param barrasLogsSemana the barrasLogsSemana to set
     */
    public void setBarrasLogsSemana(GraficoCombinado barrasLogsSemana) {
        this.barrasLogsSemana = barrasLogsSemana;
    }

    /**
     * @return the barrasLogsPorAnio
     */
    public GraficoCombinado getBarrasLogsPorAnio() {
        return barrasLogsPorAnio;
    }

    /**
     * @param barrasLogsPorAnio the barrasLogsPorAnio to set
     */
    public void setBarrasLogsPorAnio(GraficoCombinado barrasLogsPorAnio) {
        this.barrasLogsPorAnio = barrasLogsPorAnio;
    }
}
