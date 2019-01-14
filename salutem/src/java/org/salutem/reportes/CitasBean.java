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
import org.salutem.controladores.AtencionesFacade;
import org.salutem.controladores.CitasFacade;
import org.salutem.entidades.Parametros;
import org.salutem.entidades.Perfiles;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.chart.CartesianSeries.CartesianType;
import org.salutem.beans.CombosBean;
import org.salutem.beans.SeguridadBean;
import org.salutem.utilitarios.Mensajes;
import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.component.chart.AxisType;
import org.icefaces.ace.model.chart.SectorSeries;
import org.icefaces.ace.model.chart.SectorSeries.SectorType;
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

    private Perfiles perfil;

    private List<Parametros> especialidades;
    private Calendar calendar;
    private Date fecha;

    private GraficoCombinado barrasCitasPorSemana;
    private GraficoCombinado barrasCitasPorMes;
    private GraficoCombinado barrasCitasPorAnio;

    private SectorSeries model = new SectorSeries();

    private List<SectorSeries> pieCitasPorSemana;
    private List<SectorSeries> pieCitasPorMes;
    private List<SectorSeries> pieCitasPorAnio;
    private List<SectorSeries> pieAtencionesMes;

    @EJB
    private CitasFacade ejbCitas;
    @EJB
    private AtencionesFacade ejbAtenciones;

    @PostConstruct
    public void iniciar() {
        perfil = seguridadBean.traerPerfil("DashBoard");
        especialidades = combosBean.getListaEspecialidades();
        buscarTodo();
    }

    private void buscarTodo() {
        if (fecha == null) {
            fecha = new Date();
        }

        model.setType(SectorType.DONUT);

        calendar = Calendar.getInstance();
        barrasCitasPorSemana = citasBarSemana();
        barrasCitasPorMes = citasBarMes();
        barrasCitasPorAnio = citasBarAnio();

        pieCitasPorSemana = citasPieSemana();
        pieCitasPorMes = citasPieMes();
        pieCitasPorAnio = citasPieAnio();

        pieAtencionesMes = atencionesPieMes();

    }

    private GraficoCombinado citasBarSemana() {
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
        CartesianSeries citasPorDia = new CartesianSeries();
        citasPorDia.setPointLabels(Boolean.TRUE);
        citasPorDia.setPointLabelStacked(Boolean.TRUE);
        citasPorDia.setType(CartesianType.LINE);
        citasPorDia.setLabel("Citas");
        citasPorDia.setYAxis(2);
        citasPorDia.setXAxis(2);
        for (Parametros d : dias) {
            citasPorDia.add(d.getNombre(), getFrecuencia('C', 'D', d.getId(), null));
        }
        grafico.add(citasPorDia);
        //Fin gráfico: configuración y primer llenado

        //Inicio gráfico: llenado con subgráficos
        for (Parametros e : especialidades) {
            CartesianSeries subgrafico = new CartesianSeries();
            for (Parametros d : dias) {
                subgrafico.add(getFrecuencia('C', 'D', d.getId(), e));
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
        ordenadasGeneralConfig.setTickAngle(0);
        //Fin configuración de ordenadas

        Axis yLeft = new Axis();
        yLeft.setAutoscale(true);
        yLeft.setTickInterval("5");
        yLeft.setLabel("Citas");

        Axis yRight = new Axis();
        yRight.setAutoscale(true);
        yRight.setTickInterval("5");
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

    private GraficoCombinado citasBarMes() {
        //Inicio gráfico: configuración y primer llenado
        List<CartesianSeries> grafico = new ArrayList<>();
        CartesianSeries citas = new CartesianSeries();
        citas.setPointLabels(Boolean.TRUE);
        citas.setPointLabelStacked(Boolean.TRUE);
        citas.setType(CartesianType.LINE);
        citas.setLabel("Citas");
        citas.setYAxis(2);
        citas.setXAxis(2);

        calendar.setTime(fecha);

        for (Parametros e : especialidades) {
            citas.add(e.getNombre(), getFrecuencia('C', 'M', calendar.get(Calendar.MONTH), e));
        }
        grafico.add(citas);
        //Fin gráfico: configuración y primer llenado

        //Inicio gráfico: llenado con subgráficos
        CartesianSeries atenciones = new CartesianSeries();
        atenciones.setBarWidth(10);
        atenciones.setPointLabels(Boolean.TRUE);
        atenciones.setPointLabelStacked(Boolean.TRUE);
        atenciones.setType(CartesianType.BAR);
        atenciones.setLabel("Atenciones");

        calendar.setTime(fecha);
        for (Parametros e : especialidades) {
            atenciones.add(e.getNombre(), getFrecuencia('A', 'M', calendar.get(Calendar.MONTH), e));
        }
        //Fin gráfico: llenado con subgráficos
        grafico.add(atenciones);

        //Inicio configuración de ordenadas arriba y abajo
        Axis ordenadasGeneralConfig = new Axis();
        ordenadasGeneralConfig.setTickAngle(0);
        //Fin configuración de ordenadas

        Axis yLeft = new Axis();
        yLeft.setAutoscale(true);
        yLeft.setTickInterval("5");
        yLeft.setLabel("Citas");

        Axis yRight = new Axis();
        yRight.setAutoscale(true);
        yRight.setTickInterval("5");
        yRight.setLabel("Atenciones");

        Axis[] ordenadasConfigSpecific = new Axis[2];
        ordenadasConfigSpecific[0] = yLeft;
        ordenadasConfigSpecific[1] = yRight;

        String[] ticks = new String[especialidades.size()];
        int i = 0;
        for (Parametros d : especialidades) {
            ticks[i++] = d.getNombre();
        }

        Axis abscisaBottom = new Axis();
        //abscisaBottom.setTicks(ticks);
        abscisaBottom.setType(AxisType.CATEGORY);

        Axis abscisaTop = new Axis();
//        abscisaTop.setTicks(ticks);
        abscisaTop.setType(AxisType.CATEGORY);

        return new GraficoCombinado(grafico, null, abscisaBottom, abscisaTop, ordenadasGeneralConfig, ordenadasConfigSpecific);
    }

    private GraficoCombinado citasBarAnio() {
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
        CartesianSeries citasPorMes = new CartesianSeries();
        citasPorMes.setPointLabels(Boolean.TRUE);
        citasPorMes.setPointLabelStacked(Boolean.TRUE);
        citasPorMes.setType(CartesianType.LINE);
        citasPorMes.setLabel("Citas");
        citasPorMes.setYAxis(2);
        citasPorMes.setXAxis(2);
        for (Parametros m : meses) {
            citasPorMes.add(m.getNombre(), getFrecuencia('C', 'M', m.getId(), null));
        }
        grafico.add(citasPorMes);
        //Fin gráfico: configuración y primer llenado

        //Inicio gráfico: llenado con subgráficos
        for (Parametros e : especialidades) {
            CartesianSeries subgrafico = new CartesianSeries();
            for (Parametros d : meses) {
                subgrafico.add(getFrecuencia('C', 'M', d.getId(), e));
                subgrafico.setLabel(e.getNombre());
                subgrafico.setPointLabels(Boolean.TRUE);
                subgrafico.setPointLabelStacked(Boolean.TRUE);
                subgrafico.setBarWidth(8);
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
        ordenadasGeneralConfig.setTickAngle(0);
        //Fin configuración de ordenadas

        Axis yLeft = new Axis();
        yLeft.setAutoscale(true);
        yLeft.setTickInterval("10");
        yLeft.setLabel("Citas");

        Axis yRight = new Axis();
        yRight.setAutoscale(true);
        yRight.setTickInterval("10");
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

    private List<SectorSeries> citasPieSemana() {
        List<SectorSeries> retorno = new ArrayList<>();
        SectorSeries sector = new SectorSeries();
        for (Parametros e : especialidades) {
            sector.add(e.getNombre(), getFrecuencia('C', 'S', 2, e));
        }
        sector.setType(SectorType.PIE);
        sector.setShowDataLabels(true);
        sector.setDataLabelFormatString("%.2f%%");
        sector.setSliceMargin(4);
        retorno.add(sector);
        return retorno;
    }

    private List<SectorSeries> citasPieMes() {
        List<SectorSeries> retorno = new ArrayList<>();
        SectorSeries sector = new SectorSeries();
        calendar.setTime(fecha);
        for (Parametros e : especialidades) {
            sector.add(e.getNombre(), getFrecuencia('C', 'M', calendar.get(Calendar.MONTH), e));
        }
        sector.setType(SectorType.PIE);
        sector.setShowDataLabels(true);
        sector.setDataLabelFormatString("%.2f%%");
        sector.setSliceMargin(4);
        retorno.add(sector);
        return retorno;
    }

    private List<SectorSeries> atencionesPieMes() {
        calendar.setTime(fecha);
        List<SectorSeries> retorno = new ArrayList<>();
        SectorSeries citas = new SectorSeries();

        int totalCitas = 0;

        for (Parametros e : especialidades) {
            int c = getFrecuencia('C', 'M', calendar.get(Calendar.MONTH), e);
            citas.add(e.getNombre(), c);
            totalCitas += c;
        }
        citas.add("Citas sin atención", 0);

        citas.setShowDataLabels(true);
        citas.setDataLabelFormatString("%.2f%%");
        citas.setSliceMargin(4);
        retorno.add(citas);

        SectorSeries atenciones = new SectorSeries();

        int totalAtenciones = 0;

        for (Parametros e : especialidades) {
            int a = getFrecuencia('A', 'M', calendar.get(Calendar.MONTH), e);
            atenciones.add(e.getNombre(), a);
            totalAtenciones += a;
        }

        atenciones.add("Citas sin atención", totalCitas - totalAtenciones);

        atenciones.setShowDataLabels(true);
        atenciones.setDataLabelFormatString("%.2f%%");
        atenciones.setSliceMargin(4);
        retorno.add(atenciones);

        return retorno;
    }

    private List<SectorSeries> citasPieAnio() {
        List<SectorSeries> retorno = new ArrayList<>();
        SectorSeries sector = new SectorSeries();
        for (Parametros e : especialidades) {
            sector.add(e.getNombre(), getFrecuencia('C', 'A', 0, e));
        }
        sector.setType(SectorType.PIE);
        sector.setShowDataLabels(true);
        sector.setDataLabelFormatString("%.2f%%");
        sector.setSliceMargin(4);
        retorno.add(sector);
        return retorno;
    }

    private int getFrecuencia(char tabla, char tipoUnidad, int unidad, Parametros especialidad) {
        try {
            calendar.setTime(fecha);

            Date inicio;
            Date fin;

            switch (tipoUnidad) {
                case 'D':
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
                case 'S':
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
                case 'M':
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
                case 'A':
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

            String where;
            Map parameters;
            switch (tabla) {
                case 'C': //Citas
                    where = " o.activo=true and o.fecha between :inicio and :fin ";
                    parameters = new HashMap();
                    parameters.put("inicio", inicio);
                    parameters.put("fin", fin);
                    //            if (!seguridadBean.getGrupo().getCodigo().equals("GSA")
                    //                    || !seguridadBean.getGrupo().getCodigo().equals("GA")) {
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
                case 'A': //Atenciones
                    where = " o.cita.activo=true and o.cita.fecha between :inicio and :fin ";
                    parameters = new HashMap();
                    parameters.put("inicio", inicio);
                    parameters.put("fin", fin);
                    //            if (!seguridadBean.getGrupo().getCodigo().equals("GSA")
                    //                    || !seguridadBean.getGrupo().getCodigo().equals("GA")) {
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
                    return ejbAtenciones.contar(where, parameters);
            }

        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CitasBean.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return the barrasCitasPorSemana
     */
    public GraficoCombinado getBarrasCitasPorSemana() {
        return barrasCitasPorSemana;
    }

    /**
     * @param barrasCitasPorSemana the barrasCitasPorSemana to set
     */
    public void setBarrasCitasPorSemana(GraficoCombinado barrasCitasPorSemana) {
        this.barrasCitasPorSemana = barrasCitasPorSemana;
    }

    /**
     * @return the barrasCitasPorAnio
     */
    public GraficoCombinado getBarrasCitasPorAnio() {
        return barrasCitasPorAnio;
    }

    /**
     * @param barrasCitasPorAnio the barrasCitasPorAnio to set
     */
    public void setBarrasCitasPorAnio(GraficoCombinado barrasCitasPorAnio) {
        this.barrasCitasPorAnio = barrasCitasPorAnio;
    }

    /**
     * @return the pieCitasPorSemana
     */
    public List<SectorSeries> getPieCitasPorSemana() {
        return pieCitasPorSemana;
    }

    /**
     * @param pieCitasPorSemana the pieCitasPorSemana to set
     */
    public void setPieCitasPorSemana(List<SectorSeries> pieCitasPorSemana) {
        this.pieCitasPorSemana = pieCitasPorSemana;
    }

    /**
     * @return the pieCitasPorAnio
     */
    public List<SectorSeries> getPieCitasPorAnio() {
        return pieCitasPorAnio;
    }

    /**
     * @param pieCitasPorAnio the pieCitasPorAnio to set
     */
    public void setPieCitasPorAnio(List<SectorSeries> pieCitasPorAnio) {
        this.pieCitasPorAnio = pieCitasPorAnio;
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
     * @return the barrasCitasPorMes
     */
    public GraficoCombinado getBarrasCitasPorMes() {
        return barrasCitasPorMes;
    }

    /**
     * @param barrasCitasPorMes the barrasCitasPorMes to set
     */
    public void setBarrasCitasPorMes(GraficoCombinado barrasCitasPorMes) {
        this.barrasCitasPorMes = barrasCitasPorMes;
    }

    /**
     * @return the pieCitasPorMes
     */
    public List<SectorSeries> getPieCitasPorMes() {
        return pieCitasPorMes;
    }

    /**
     * @param pieCitasPorMes the pieCitasPorMes to set
     */
    public void setPieCitasPorMes(List<SectorSeries> pieCitasPorMes) {
        this.pieCitasPorMes = pieCitasPorMes;
    }

    /**
     * @return the pieAtencionesMes
     */
    public List<SectorSeries> getPieAtencionesMes() {
        return pieAtencionesMes;
    }

    /**
     * @param pieAtencionesMes the pieAtencionesMes to set
     */
    public void setPieAtencionesMes(List<SectorSeries> pieAtencionesMes) {
        this.pieAtencionesMes = pieAtencionesMes;
    }

    /**
     * @return the model
     */
    public SectorSeries getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(SectorSeries model) {
        this.model = model;
    }

}
