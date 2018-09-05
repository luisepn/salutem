package org.salutem.historial;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.controladores.salutemlogs.HistorialFacade;
import org.entidades.salutemlogs.Historial;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutemlogs.ExcepcionDeConsulta;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.beans.SeguridadBean;
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

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Historial> lista;
    private Perfiles perfil;
    private String titulo;
    private Boolean verColumnaTabla = false;

    private Date fechaInicio;
    private Date fechaFin;
    private String usuario;
    private Character operacion;
    private String tabla;
    private String campo;
    private String busqueda;

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

    @PostConstruct
    public void activar() {
        perfil = seguridadBean.traerPerfil("Historial");
    }

    public List<Historial> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            Map parameters = new HashMap();
            String where = " o.id is not null";

            for (Map.Entry e : map.entrySet()) {
                String clave = (String) e.getKey();
                String valor = (String) e.getValue();

                if (clave.contains("operacion")) {
                    if (valor.equals("A")) {
                        where += " and o.operacion is not null";
                    } else {
                        where += " and o.operacion=:operacion";
                        parameters.put("operacion", valor);
                    }
                } else if (clave.contains("tabla")) {
                    if (valor.equals("A")) {
                        where += " and o.tabla is not null";
                    } else {
                        where += " and o.tabla=:tabla";
                        parameters.put("tabla", valor);
                    }
                } else if (clave.contains("objeto")) {
                    if (valor.trim().contains(" in ")) {
                        if (valor.trim().matches("'[^ ]*'\\ [><!=^like^ilike^not like^not ilike^in^not in]*\\ \\([^ŋ]*\\)")) {
                            where += " and o.objeto->>" + valor;
                        }
                    } else {
                        if (valor.trim().matches("'[^ ]*'\\ [><!=^like^ilike^not like^not ilike^in^not in]*\\ '[^ŋ]*'")) {
                            where += " and o.objeto->>" + valor;
                        }
                    }
                } else {
                    where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                    parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
                }
            }

            if (campo != null && busqueda != null) {
                where += " and o.objeto->>'" + campo + "'='" + busqueda + "'";
            }

            if (fechaInicio != null && fechaFin != null) {
                where += " and o.fecha between :fechainicio and :fechafin";
                parameters.put("fechainicio", fechaInicio);
                parameters.put("fechafin", fechaFin);
            }

            int total = (int) ejbHistorial.buscar(where, parameters, null, null, null, true);
            getFormulario().setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            getLista().setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.fecha desc";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return (List<Historial>) ejbHistorial.buscar(where, parameters, order, i, endIndex, false);
        } catch (ExcepcionDeConsulta ex) {
            Logger.getLogger(HistorialBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String buscar() {
        if (fechaInicio == null || fechaFin == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            fechaInicio = calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            fechaFin = calendar.getTime();
        }
        verColumnaTabla = true;
        lista = new LazyDataModel<Historial>() {
            @Override
            public List<Historial> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return cargar(i, i1, scs, map);
            }
        };
        return null;
    }

    public String buscar(String tabla, int id) {
        titulo = "Tabla = " + tabla + "; ID = " + id;
        this.tabla = tabla;
        this.campo = "id";
        this.busqueda = id + "";
        verColumnaTabla = false;
        lista = new LazyDataModel<Historial>() {
            @Override
            public List<Historial> load(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
                if (map.isEmpty()) {
                    map.put("o.tabla", tabla);
                }
                return cargar(i, pageSize, scs, map);
            }
        };
        formulario.insertar();
        return null;
    }

    public String getAyudaCampos() {
        return "Campos (Puede utilizar los operadores: '=', '!=', '>', '>=', '<='; y las instrucciones: like, ilike, in, not in)";
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

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the operacion
     */
    public Character getOperacion() {
        return operacion;
    }

    /**
     * @param operacion the operacion to set
     */
    public void setOperacion(Character operacion) {
        this.operacion = operacion;
    }

    /**
     * @return the tabla
     */
    public String getTabla() {
        return tabla;
    }

    /**
     * @param tabla the tabla to set
     */
    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    /**
     * @return the campo
     */
    public String getCampo() {
        return campo;
    }

    /**
     * @param campo the campo to set
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }

    /**
     * @return the busqueda
     */
    public String getBusqueda() {
        return busqueda;
    }

    /**
     * @param busqueda the busqueda to set
     */
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
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
     * @return the verColumnaTabla
     */
    public Boolean getVerColumnaTabla() {
        return verColumnaTabla;
    }

    /**
     * @param verColumnaTabla the verColumnaTabla to set
     */
    public void setVerColumnaTabla(Boolean verColumnaTabla) {
        this.verColumnaTabla = verColumnaTabla;
    }

}
