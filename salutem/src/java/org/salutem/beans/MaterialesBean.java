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
import org.controladores.salutem.MaterialesFacade;
import org.entidades.salutem.Materiales;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.excepciones.salutem.ExcepcionDeEliminacion;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 24 de Noviembre de 2017, 09:45:07 AM
 *
 *
 */
@ManagedBean(name = "salutemMateriales")
@ViewScoped
public class MaterialesBean implements Serializable, IMantenimiento {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Materiales> materiales;
    private Materiales material;
    private int foco;
    private int tipo;
    private Perfiles perfil;

    @EJB
    private MaterialesFacade ejbMateriales;

    public MaterialesBean() {
        materiales = new LazyDataModel<Materiales>() {
            @Override
            public List<Materiales> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                }
                return cargar(i, i1, scs, map);
            }
        };
    }

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Materiales");
    }

    private List<Materiales> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {

        Map parameters = new HashMap();
        String where = " o.activo=true ";
        for (Map.Entry e : map.entrySet()) {
            String clave = (String) e.getKey();
            String valor = (String) e.getValue();
            if (clave.contains(".id")) {
                Integer id = Integer.parseInt(valor);
                if (id != 0) {
                    where += " and o." + clave + "=:" + clave.replaceAll("\\.", "");
                    parameters.put(clave.replaceAll("\\.", ""), id);
                }
            } else {
                where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
            }
        }
        try {
            int total = ejbMateriales.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            materiales.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.nombre";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbMateriales.buscar(where, parameters, order, i, endIndex);
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
        materiales = new LazyDataModel<Materiales>() {
            @Override
            public List<Materiales> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        material = new Materiales();
        material.setActivo(Boolean.TRUE);
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        material = (Materiales) materiales.getRowData();
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        material = (Materiales) materiales.getRowData();
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        if (material.getFoco() == null) {
            Mensajes.advertencia("Tipo de foco es necesario");
            return true;
        }
        if (material.getTipo() == null) {
            Mensajes.advertencia("Tipo del material es necesario");
            return true;
        }
        if ((material.getCodigo() == null) || (material.getCodigo().isEmpty())) {
            Mensajes.advertencia("Código del material es necesario");
            return true;
        }
        if ((material.getNombre() == null) || (material.getNombre().isEmpty())) {
            Mensajes.advertencia("Nombre del material es necesario");
            return true;
        }
        try {
            String where = "o.codigo=:codigo";
            Map parametros = new HashMap();
            parametros.put("codigo", material.getCodigo());
            if (material.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", material.getId());
            }
            if (ejbMateriales.contar(where, parametros) > 0) {
                Mensajes.advertencia("No se permiten materiales con código duplicado");
                return true;
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MaterialesBean.class.getName()).log(Level.SEVERE, null, ex);
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
            material.setCreado(new Date());
            material.setCreadopor(seguridadBean.getLogueado().getUserid());
            material.setActualizado(material.getCreado());
            material.setActualizadopor(material.getCreadopor());
            ejbMateriales.crear(material, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MaterialesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
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
            material.setActualizado(new Date());
            material.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbMateriales.actualizar(material, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MaterialesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        try {
            ejbMateriales.eliminar(material, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MaterialesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public String getNombreTabla() {
        return Materiales.class.getSimpleName();
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
     * @return the materiales
     */
    public LazyDataModel<Materiales> getMateriales() {
        return materiales;
    }

    /**
     * @return the material
     */
    public Materiales getMaterial() {
        return material;
    }

    /**
     * @return the foco
     */
    public int getFoco() {
        return foco;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
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
     * @param materiales the materiales to set
     */
    public void setMateriales(LazyDataModel<Materiales> materiales) {
        this.materiales = materiales;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(Materiales material) {
        this.material = material;
    }

    /**
     * @param foco the foco to set
     */
    public void setFoco(int foco) {
        this.foco = foco;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }
}
