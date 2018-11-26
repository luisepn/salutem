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
import org.icefaces.ace.model.chart.DragConstraintAxis;
import org.salutem.beans.CombosBean;
import org.salutem.beans.SeguridadBean;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;
import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.component.chart.AxisType;

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
    private List<CartesianSeries> barData;
    private CartesianSeries modelFill;

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
        barData = new ArrayList<>();
        CartesianSeries categoriaDias = new CartesianSeries();

        categoriaDias.setType(CartesianType.LINE);
        categoriaDias.setDragConstraintAxis(DragConstraintAxis.Y);
        categoriaDias.setLabel("Citas");
        categoriaDias.setYAxis(2);
        categoriaDias.setXAxis(2);

        for (Parametros d : dias) {
            categoriaDias.add(d.getNombre(), getFrecuenciaDiaria(d.toInteger(), null));
        }

        barData.add(categoriaDias);

        modelFill = new CartesianSeries();
        modelFill.setType(CartesianType.BAR);
        modelFill.setFillToZero(true);

        especialidades = combosBean.getListaEspecialidades();
        for (Parametros esp : especialidades) {
            CartesianSeries especialidad = new CartesianSeries();
            for (Parametros d : dias) {
                especialidad.add(getFrecuenciaDiaria(d.toInteger(), esp));
                especialidad.setLabel(esp.getNombre());
            }

            barData.add(especialidad);
        }

    }

    private Axis barDemoDefaultAxis = new Axis() {
        {
            setTickAngle(0);
        }
    };

    private Axis[] barDemoYAxes = new Axis[]{
        new Axis() {
            {
                setAutoscale(true);
                setTickInterval("5");
                setLabel("Citas");
            }
        },
        new Axis() {
            {
                setAutoscale(true);
                setTickInterval("5");
                setLabel("Citas Por Especialidad");
            }
        }
    };
    private Axis barDemoXOneAxis = new Axis() {
        {
            setTicks(new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"});
            setType(AxisType.CATEGORY);
        }
    };
    private Axis barDemoXTwoAxis = new Axis() {
        {
            setTicks(new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"});
            setType(AxisType.CATEGORY);
        }
    };

    private int getFrecuenciaDiaria(int dia, Parametros especialidad) {
        try {
            dia = dia < 7 ? dia + 1 : 1;
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, dia);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date inicio = c.getTime();

            c.set(Calendar.HOUR, 23);
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

    /**
     * @return the barData
     */
    public List<CartesianSeries> getBarData() {
        return barData;
    }

    /**
     * @param barData the barData to set
     */
    public void setBarData(List<CartesianSeries> barData) {
        this.barData = barData;
    }

    /**
     * @return the barDemoDefaultAxis
     */
    public Axis getBarDemoDefaultAxis() {
        return barDemoDefaultAxis;
    }

    /**
     * @param barDemoDefaultAxis the barDemoDefaultAxis to set
     */
    public void setBarDemoDefaultAxis(Axis barDemoDefaultAxis) {
        this.barDemoDefaultAxis = barDemoDefaultAxis;
    }

    /**
     * @return the barDemoXOneAxis
     */
    public Axis getBarDemoXOneAxis() {
        return barDemoXOneAxis;
    }

    /**
     * @param barDemoXOneAxis the barDemoXOneAxis to set
     */
    public void setBarDemoXOneAxis(Axis barDemoXOneAxis) {
        this.barDemoXOneAxis = barDemoXOneAxis;
    }

    /**
     * @return the barDemoYAxes
     */
    public Axis[] getBarDemoYAxes() {
        return barDemoYAxes;
    }

    /**
     * @param barDemoYAxes the barDemoYAxes to set
     */
    public void setBarDemoYAxes(Axis[] barDemoYAxes) {
        this.barDemoYAxes = barDemoYAxes;
    }

    /**
     * @return the barDemoXTwoAxis
     */
    public Axis getBarDemoXTwoAxis() {
        return barDemoXTwoAxis;
    }

    /**
     * @param barDemoXTwoAxis the barDemoXTwoAxis to set
     */
    public void setBarDemoXTwoAxis(Axis barDemoXTwoAxis) {
        this.barDemoXTwoAxis = barDemoXTwoAxis;
    }

    /**
     * @return the modelFill
     */
    public CartesianSeries getModelFill() {
        return modelFill;
    }

    /**
     * @param modelFill the modelFill to set
     */
    public void setModelFill(CartesianSeries modelFill) {
        this.modelFill = modelFill;
    }

}
