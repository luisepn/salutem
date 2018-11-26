package org.salutem.reportes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.entidades.salutem.Perfiles;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.chart.CartesianSeries.CartesianType;
import org.salutem.beans.CombosBean;
import org.salutem.beans.SeguridadBean;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;
import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.component.chart.AxisType;
import org.icefaces.ace.model.chart.DragConstraintAxis;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 18 de Noviembre de 2018, 14:39:25 AM
 *
 */
@Named("dashboardTransaccional")
@ViewScoped
public class TransaccionalBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;
    @Inject
    @Any
    private CombosBean combosBean;

    private List<Parametros> especialidades;
    private List<Parametros> dias;

    private List<CartesianSeries> grafico; //Gráfico de lineas
    private CartesianSeries subgraficoConfig; //Subgráfico de barras

    private Axis abscisaBottom; //Nombres de las categorías de abajo del gráfico
    private Axis abscisaTop; //Nombres de las categorías de arriba del gráfico

    private Axis ordenadasGeneralConfig; //Configuración general de los nombres de las categorías de arriba y abajo del gráfico
    private Axis[] ordenadasConfigSpecific; //Configuracion específica de los valores izquierdo y derecho del gráfico

    private Formulario formulario = new Formulario();
    private Perfiles perfil;

    @EJB
    private CitasFacade ejbCitas;

    public TransaccionalBean() {
//        
    }

    @PostConstruct
    public void iniciar() {

        dias = combosBean.getListaDias();
        especialidades = combosBean.getListaEspecialidades();

        //Inicio gráfico: configuración y primer llenado
        grafico = new ArrayList<>();
        CartesianSeries citasPorDia = new CartesianSeries();
        citasPorDia.setType(CartesianType.LINE);
        citasPorDia.setLabel("Citas");
        citasPorDia.setYAxis(2);
        citasPorDia.setXAxis(2);
        for (Parametros d : dias) {
            citasPorDia.add(d.getNombre(), getFrecuenciaDiaria(d.toInteger(), null));
        }
        grafico.add(citasPorDia);
        //Fin gráfico: configuración y primer llenado

        //Inicio gráfico: llenado con subgráficos
        for (Parametros e : especialidades) {
            CartesianSeries subgrafico = new CartesianSeries();
            for (Parametros d : dias) {
                subgrafico.add(getFrecuenciaDiaria(d.toInteger(), e));
                subgrafico.setLabel(e.getNombre());

            }
            subgrafico.setPointLabels(Boolean.TRUE);
            subgrafico.setPointLabelStacked(Boolean.TRUE);
            grafico.add(subgrafico);

        }
        //Fin gráfico: llenado con subgráficos

        //Inicio configuración subgráfico
        subgraficoConfig = new CartesianSeries();
        subgraficoConfig.setType(CartesianType.BAR);
        subgraficoConfig.setFillToZero(true);

        String[] ticks = new String[especialidades.size() * dias.size()];
        int i = 0;
//        for (int j = 0; j < dias.size(); j++) {
//            for (Parametros e : especialidades) {
//                ticks[i++] = e.getNombre();
//            }
//        }
//        subgraficoConfig.setPointLabelList(ticks);
        //Fin configuración gráfico
        //Inicio configuración de ordenadas arriba y abajo
        ordenadasGeneralConfig = new Axis();
        ordenadasGeneralConfig.setTickAngle(-10);
        //Fin configuración de ordenadas

        Axis yLeft = new Axis();
        yLeft.setAutoscale(true);
        yLeft.setTickInterval("2");
        yLeft.setLabel("Citas");

        Axis yRight = new Axis();
        yRight.setAutoscale(true);
        yRight.setTickInterval("2");
        yRight.setLabel("Citas Por Especialidad");

        ordenadasConfigSpecific = new Axis[2];
        ordenadasConfigSpecific[0] = yLeft;
        ordenadasConfigSpecific[1] = yRight;

        ticks = new String[7];
        i = 0;
        for (Parametros d : dias) {
            ticks[i++] = d.getNombre();
        }

        abscisaBottom = new Axis();
        abscisaBottom.setTicks(ticks);
        abscisaBottom.setType(AxisType.CATEGORY);

        abscisaTop = new Axis();
        abscisaTop.setTicks(ticks);
        abscisaTop.setType(AxisType.CATEGORY);
    }

    private int getFrecuenciaDiaria(int dia, Parametros especialidad) {
        try {
            dia = dia == 7 ? 1 : dia + 1;
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, dia);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date inicio = c.getTime();

            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
            Date fin = c.getTime();

            String where = " o.paciente.institucion=:institucion and o.fecha between :inicio and :fin " + (especialidad != null ? "and o.profesional.especialidad=:especialidad" : "");
            Map parameters = new HashMap();
            parameters.put("inicio", inicio);
            parameters.put("fin", fin);
            parameters.put("institucion", seguridadBean.getInstitucion());
            if (especialidad != null) {
                parameters.put("especialidad", especialidad);
            }
            return ejbCitas.contar(where, parameters);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(TransaccionalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void exportHandler(org.icefaces.ace.event.ChartImageExportEvent e) {
        try {
            java.io.FileOutputStream out = new java.io.FileOutputStream("asdf1.png");
            out.write(e.getBytes());
            out.close();
        } catch (Exception ex) {

        }
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
     * @return the dias
     */
    public List<Parametros> getDias() {
        return dias;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(List<Parametros> dias) {
        this.dias = dias;
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
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
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

}
