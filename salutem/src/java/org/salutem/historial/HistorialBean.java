package org.salutem.historial;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.controladores.salutemlogs.HistorialFacade;
import org.entidades.salutemlogs.Historial;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutemlogs.ExcepcionDeConsulta;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Formulario;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 18 de Noviembre de 2017, 07:23:04 AM
 *
 */
@ManagedBean(name = "salutemHistorial")
@ViewScoped
public class HistorialBean implements Serializable {

    private Formulario formulario = new Formulario();
    private LazyDataModel<Historial> lista;
    private String titulo;
    private Perfiles perfil;
    private Date fechaInicio;
    private Date fechaFin;

    @EJB
    private HistorialFacade ejbHistorial;

    public HistorialBean() {
        lista = new LazyDataModel<Historial>() {
            @Override
            public List<Historial> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return null;
            }
        };
    }

    public String buscar(String tabla, int id) {
        titulo = "Tabla = " + tabla + "; ID = " + id;
        lista = new LazyDataModel<Historial>() {
            @Override
            public List<Historial> load(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
                try {
                    Map parameters = new HashMap();
                    String where = " o.tabla=:tabla and o.objeto->>'id'=:id";
                    parameters.put("tabla", tabla);
                    parameters.put("id", id + "");
                    for (Map.Entry e : map.entrySet()) {
                        String clave = (String) e.getKey();
                        String valor = (String) e.getValue();
                        where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                        parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
                    }

                    if (fechaInicio != null && fechaFin != null) {
                        where += " and o.fecha between :fechainicio and :fechafin";
                        parameters.put("fechainicio", fechaInicio);
                        parameters.put("fechafin", fechaFin);
                    }

                    int total = (int) ejbHistorial.ejecutarQuery(where, parameters, null, null, null, true);
                    getFormulario().setTotal(total);
                    int endIndex = i + pageSize;
                    if (endIndex > total) {
                        endIndex = total;
                    }
                    getLista().setRowCount(total);
                    String order;
                    if (scs.length == 0) {
                        order = "o.fecha";
                    } else {
                        order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
                    }
                    return (List<Historial>) ejbHistorial.ejecutarQuery(where, parameters, order, i, endIndex, false);
                } catch (ExcepcionDeConsulta ex) {
                    Logger.getLogger(HistorialBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        };
        formulario.insertar();
        return null;
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
     * @return the lista
     */
    public LazyDataModel<Historial> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(LazyDataModel<Historial> lista) {
        this.lista = lista;
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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}