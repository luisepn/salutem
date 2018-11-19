package org.salutem.reportes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.controladores.salutem.CitasFacade;
import org.controladores.salutem.PersonasFacade;
import org.entidades.salutem.Instituciones;
import org.entidades.salutemlogs.Historial;
import org.entidades.salutem.Perfiles;
import org.icefaces.ace.model.table.LazyDataModel;
import org.salutem.beans.CombosBean;
import org.salutem.beans.SeguridadBean;
import org.salutem.utilitarios.Formulario;

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

    private Instituciones institucion;
    private Calendar fecha = Calendar.getInstance();

    private Formulario formulario = new Formulario();
    private LazyDataModel<Historial> lista;
    private Perfiles perfil;
    private String titulo;

    private Date fechaInicio;
    private Date fechaFin;
    private String usuario;
    private Character operacion;

    private String tabla;
    private Integer registro;

    private String tablaAuxiliar = "A";

    @EJB
    private CitasFacade ejbCitas;
    @EJB
    private PersonasFacade ejbTransacciones;

    public TransaccionalBean() {

    }

    @PostConstruct
    public void activar() {
    }

    public void iniciar() {
        //Semanal
        
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(fecha.getTime());
        inicio.setFirstDayOfWeek(fecha.getActualMinimum(Calendar.DAY_OF_WEEK));
        
        
        
        String where = " o.paciente.institucion=:institucion and o.fecha between :inicio and :fin";
        Map parameters = new HashMap();
        parameters.put("institucion", institucion);
        parameters.put("inicio", institucion);
        
    }

}
