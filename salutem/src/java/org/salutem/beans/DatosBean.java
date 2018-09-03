package org.salutem.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.controladores.salutem.CamposFacade;
import org.controladores.salutem.DatosFacade;
import org.entidades.salutem.Campos;
import org.entidades.salutem.Datos;
import org.entidades.salutem.Parametros;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.excepciones.salutem.ExcepcionDeEliminacion;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @fecha Quito, 27 de agosto de 2018
 * @hora 15:03:23
 */
@ManagedBean(name = "salutemDatos")
@ViewScoped
public class DatosBean implements Serializable {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;

    private List<Datos> datos;

    @EJB
    private CamposFacade ejbCampos;
    @EJB
    private DatosFacade ejbDatos;

    public DatosBean() {
    }

    public String crear(String clasificador, Parametros grupo, Integer identificador) {
        try {
            datos = ejbDatos.traerDatos(clasificador, grupo.getNombre(), identificador);
            if (!datos.isEmpty()) {
                return null;
            }

            List<Campos> campos = ejbCampos.traerCampos(clasificador, grupo);
            if (campos == null || campos.isEmpty()) {
                Mensajes.advertencia("No existen campos registrados para el clasificador y grupo seleccionados ");
                return null;
            }

            for (Campos c : campos) {
                Datos d = new Datos();
                d.setCreado(new Date());
                d.setCreadopor(seguridadBean.getLogueado().getUserid());
                d.setActualizado(d.getCreado());
                d.setActualizadopor(d.getCreadopor());

                d.setClasificador(clasificador);
                d.setIdentificador(identificador);
                d.setCodigo(c.getCodigo());
                d.setNombre(c.getNombre());
                d.setDescripcion(c.getDescripcion());
                d.setOrdengrupo(c.getGrupo().getId());
                d.setGrupo(c.getGrupo().getNombre());
                d.setTipo(c.getTipo());
                d.setActivo(c.getActivo());

                ejbDatos.crear(d, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());

                if (c.getTipo().getCodigo().equals("ONE") || c.getTipo().getCodigo().equals("MANY")) {
                    ejbDatos.actualizarJsonb("opciones", ejbCampos.buscarJsonb(c.getId()), d.getId());
                }
            }
            datos = ejbDatos.traerDatos(clasificador, grupo.getNombre(), identificador);
        } catch (ExcepcionDeConsulta | ExcepcionDeCreacion | ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String actualizar(String clasificador, Parametros grupo, Integer identificador) {
        try {
            datos = ejbDatos.traerDatos(clasificador, grupo.getNombre(), identificador);
            for (Datos d : datos) {
                ejbDatos.eliminar(d, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }
            crear(clasificador, grupo, identificador);
        } catch (ExcepcionDeEliminacion | ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String actualizar() {
        try {
            for (Datos d : datos) {
                ejbDatos.actualizar(d, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                if (d.getTipo().getCodigo().equals("ONE") || d.getTipo().getCodigo().equals("MANY")) {
                    ejbDatos.actualizarJsonb("opciones", ejbCampos.buscarJsonb(d.getId()), d.getId());
                }
            }
        } catch (ExcepcionDeActualizacion | ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public SelectItem[] traerOpciones(Datos d) {
        try {
            d.setOpciones(ejbDatos.buscarJsonb("opciones",d.getId()));
            return CombosBean.getSelectItems(d.getOpcionesList(), "toString", false);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CamposBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
     * @return the datos
     */
    public List<Datos> getDatos() {
        return datos;
    }

    /**
     * @param datos the datos to set
     */
    public void setDatos(List<Datos> datos) {
        this.datos = datos;
    }

}
