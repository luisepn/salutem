package org.salutem.beans;

import java.io.Serializable;
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
import javax.faces.event.ValueChangeEvent;
import org.controladores.salutem.OrdenesFacade;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Ordenes;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Mensajes;

@ManagedBean(name = "ordenes")
@ViewScoped
public class OrdenesBean implements Serializable {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;

    private LazyDataModel<Ordenes> listaOrdenes;
    private Instituciones institucion;
    private Instituciones laboratorio;
    private Perfiles perfil;

    @EJB
    private OrdenesFacade ejbOrdenes;

    @PostConstruct
    private void activar() {
       perfil = seguridadBean.traerPerfil("Ordenes");
    }

    public void cambiaInstitucion(ValueChangeEvent event) {
        institucion = ((Instituciones) event.getNewValue());
    }

    public OrdenesBean() {
        listaOrdenes = new LazyDataModel<Ordenes>() {
            public List<Ordenes> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return carga(i, i1, scs, map);
            }
        };
    }

    public List<Ordenes> carga(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {

        Map parametros = new HashMap();
        String where = " o.activo=true ";
        for (Map.Entry e : map.entrySet()) {
            String clave = (String) e.getKey();
            String valor = (String) e.getValue();
            where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
            parametros.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
        }
        if (institucion != null) {
            where += " and o.consulta.paciente.institucion=:institucion";
            parametros.put("institucion", institucion);
        }
        if (laboratorio != null) {
            where += " and o.laboratorio=:laboratorio";
            parametros.put("laboratorio", laboratorio);
        }
        try {
            int total = ejbOrdenes.contar(where, parametros);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            listaOrdenes.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.consulta.paciente.institucion.nombre, o.fecha desc";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbOrdenes.buscar(where, parametros, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger("").log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String buscarOrdenes() {
        if (!perfil.getConsulta()) {
            Mensajes.advertencia("No tiene autorización para consultar");
            return null;
        }
        listaOrdenes = new LazyDataModel<Ordenes>() {
            @Override
            public List<Ordenes> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return carga(i, i1, scs, map);
            }
        };
        return null;
    }

    /**
     * @return the seguridadBean
     */
    public SeguridadBean getSeguridadBean() {
        return seguridadBean;
    }

    /**
     * @return the listaOrdenes
     */
    public LazyDataModel<Ordenes> getListaOrdenes() {
        return listaOrdenes;
    }

    /**
     * @return the institucion
     */
    public Instituciones getInstitucion() {
        return institucion;
    }

    /**
     * @return the laboratorio
     */
    public Instituciones getLaboratorio() {
        return laboratorio;
    }

    /**
     * @return the perfil
     */
    public Perfiles getPerfil() {
        return perfil;
    }

    /**
     * @param seguridadBean the seguridadBean to set
     */
    public void setSeguridadBean(SeguridadBean seguridadBean) {
        this.seguridadBean = seguridadBean;
    }

    /**
     * @param listaOrdenes the listaOrdenes to set
     */
    public void setListaOrdenes(LazyDataModel<Ordenes> listaOrdenes) {
        this.listaOrdenes = listaOrdenes;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    /**
     * @param laboratorio the laboratorio to set
     */
    public void setLaboratorio(Instituciones laboratorio) {
        this.laboratorio = laboratorio;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }
}
