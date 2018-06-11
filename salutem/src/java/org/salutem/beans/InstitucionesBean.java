package org.salutem.beans;

import java.io.Serializable;
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
import org.controladores.salutem.DireccionesFacade;
import org.controladores.salutem.InstitucionesFacade;
import org.entidades.salutem.Archivos;
import org.entidades.salutem.Direcciones;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 23 de Noviembre de 2017, 07:46:08 AM
 */
@ManagedBean(name = "salutemInstituciones")
@ViewScoped
public class InstitucionesBean implements Serializable, IMantenimiento {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;
    @ManagedProperty("#{salutemImagenes}")
    private ImagenesBean imagenesBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Instituciones> instituciones;
    private Instituciones institucion;
    private Direcciones direccion;
    private Perfiles perfil;

    @EJB
    private InstitucionesFacade ejbInstituciones;
    @EJB
    private DireccionesFacade ejbDirecciones;

    public InstitucionesBean() {
        instituciones = new LazyDataModel<Instituciones>() {
            @Override
            public List<Instituciones> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return cargar(i, i1, scs, map);
            }
        };
    }

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Instituciones");
    }

    private List<Instituciones> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        Map parametros = new HashMap();
        String where = " o.activo=true ";
        for (Map.Entry e : map.entrySet()) {
            String clave = (String) e.getKey();
            String valor = (String) e.getValue();
            where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
            parametros.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
        }
        try {
            int total = ejbInstituciones.contar(where, parametros);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            instituciones.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.nombre";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbInstituciones.buscar(where, parametros, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(InstitucionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        instituciones = new LazyDataModel<Instituciones>() {
            @Override
            public List<Instituciones> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return cargar(i, i1, scs, map);
            }
        };
        return null;
    }

    @Override
    public String crear() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        institucion = new Instituciones();
        institucion.setActivo(Boolean.TRUE);
        institucion.setFecha(new Date());
        direccion = new Direcciones();
        imagenesBean.setArchivo(new Archivos());
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        institucion = ((Instituciones) instituciones.getRowData());
        imagenesBean.setArchivo(institucion.getLogotipo() != null ? institucion.getLogotipo() : new Archivos());
        direccion = institucion.getDireccion() != null ? institucion.getDireccion() : new Direcciones();
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        institucion = ((Instituciones) instituciones.getRowData());
        imagenesBean.setArchivo(institucion.getLogotipo() != null ? institucion.getLogotipo() : new Archivos());
        direccion = institucion.getDireccion() != null ? institucion.getDireccion() : new Direcciones();
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        if ((institucion.getNombre() == null) || (institucion.getNombre().trim().isEmpty())) {
            Mensajes.advertencia("Nombre de la institución es necesario");
            return true;
        }
        if ((direccion.getPrimaria() == null) || (direccion.getPrimaria().trim().isEmpty())) {
            Mensajes.advertencia("Calle primaria es necesaria");
            return true;
        }
        if ((direccion.getNumero() == null) || (direccion.getNumero().trim().isEmpty())) {
            Mensajes.advertencia("Número es necesario");
            return true;
        }
        if ((direccion.getSecundaria() == null) || (direccion.getSecundaria().trim().isEmpty())) {
            Mensajes.advertencia("Calle secundaria es necesaria");
            return true;
        }
        if ((direccion.getFijo() == null) || (direccion.getFijo().trim().isEmpty())) {
            Mensajes.advertencia("Número telefónico es necesario");
            return true;
        }
        return false;
    }

    @Override
    public String insertar() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        try {
            ejbDirecciones.crear(direccion, seguridadBean.getLogueado().getUserid());
            imagenesBean.grabarImagen(seguridadBean.getLogueado().getUserid(), "Logotipos", null);
            institucion.setLogotipo(imagenesBean.getArchivo());
            institucion.setDireccion(direccion);
            ejbInstituciones.crear(institucion, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(InstitucionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();
        return null;
    }

    @Override
    public String grabar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        try {
            ejbDirecciones.actualizar(direccion, seguridadBean.getLogueado().getUserid());
            imagenesBean.grabarImagen(seguridadBean.getLogueado().getUserid(), "Logotipos", null);
            institucion.setLogotipo(imagenesBean.getArchivo());
            ejbInstituciones.actualizar(institucion, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(InstitucionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();
        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            institucion.setActivo(Boolean.FALSE);
            ejbInstituciones.actualizar(institucion, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(InstitucionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();
        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        buscar();
        return null;
    }

    /**
     * @return the seguridadBean
     */
    public SeguridadBean getSeguridadBean() {
        return seguridadBean;
    }

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @return the instituciones
     */
    public LazyDataModel<Instituciones> getInstituciones() {
        return instituciones;
    }

    /**
     * @return the institucion
     */
    public Instituciones getInstitucion() {
        return institucion;
    }

    /**
     * @return the direccion
     */
    public Direcciones getDireccion() {
        return direccion;
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
     * @param formulario the formulario to set
     */
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @param instituciones the instituciones to set
     */
    public void setInstituciones(LazyDataModel<Instituciones> instituciones) {
        this.instituciones = instituciones;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(Direcciones direccion) {
        this.direccion = direccion;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the imagenesBean
     */
    public ImagenesBean getImagenesBean() {
        return imagenesBean;
    }

    /**
     * @param imagenesBean the imagenesBean to set
     */
    public void setImagenesBean(ImagenesBean imagenesBean) {
        this.imagenesBean = imagenesBean;
    }

}
