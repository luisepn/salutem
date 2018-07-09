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
import org.controladores.salutem.PerfilesFacade;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutem.ExcepcionDeEliminacion;
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
 * @since 22 de Noviembre de 2017, 13:57:21 AM
 */
@ManagedBean(name = "salutemPerfiles")
@ViewScoped
public class PerfilesBean implements Serializable, IMantenimiento {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;
    @ManagedProperty("#{salutemCombos}")
    private CombosBean combosBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Perfiles> perfiles;
    private Perfiles perfil;
    private Perfiles perfilSistema;

    @EJB
    private PerfilesFacade ejbPerfiles;

    public PerfilesBean() {
        perfiles = new LazyDataModel<Perfiles>() {
            @Override
            public List<Perfiles> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return null;
            }
        };
    }

    @PostConstruct
    @Override
    public void activar() {
        perfilSistema = seguridadBean.traerPerfil("Perfiles");
    }

    private List<Perfiles> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            if (combosBean.getGrupo() == null) {
                return null;
            }
            if (combosBean.getMenu() == null) {
                return null;
            }
            Map parameters = new HashMap();
            parameters.put("grupo", combosBean.getGrupo());
            parameters.put("menu", combosBean.getMenu());
            String where = " o.grupo=:grupo and o.menu.menupadre=:menu";
            for (Map.Entry e : map.entrySet()) {
                String clave = (String) e.getKey();
                String valor = (String) e.getValue();
                where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
            }

            int total = ejbPerfiles.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            perfiles.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.menu.nombre";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbPerfiles.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfilSistema, 'R')) {
            return null;
        }
        if (combosBean.getGrupo() == null) {
            Mensajes.advertencia("Por favor seleccione un grupo");
            return null;
        }
        if (combosBean.getMenu() == null) {
            Mensajes.advertencia("Seleccione un menú primero");
            return null;
        }
        perfiles = new LazyDataModel<Perfiles>() {
            @Override
            public List<Perfiles> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return cargar(i, i1, scs, map);
            }
        };
        return null;
    }

    @Override
    public String crear() {
        if (!IMantenimiento.validarPerfil(perfilSistema, 'C')) {
            return null;
        }
        if (combosBean.getMenu() == null) {
            Mensajes.advertencia("Seleccione un menú primero");
            return null;
        }
        if (combosBean.getGrupo() == null) {
            Mensajes.advertencia("Por favor seleccione un grupo");
            return null;
        }
        perfil = new Perfiles();
        perfil.setGrupo(combosBean.getGrupo());

        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfilSistema, 'U')) {
            return null;
        }
        perfil = (Perfiles) perfiles.getRowData();
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfilSistema, 'D')) {
            return null;
        }
        perfil = (Perfiles) perfiles.getRowData();
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        if (perfil.getGrupo() == null) {
            Mensajes.advertencia("Es necesario grupo");
            return true;
        }
        if (perfil.getMenu() == null) {
            Mensajes.advertencia("Es necesario seleccionar un  Menú");
            return true;
        }
        return false;
    }

    @Override
    public String insertar() {
        if (!IMantenimiento.validarPerfil(perfilSistema, 'C')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        try {
            ejbPerfiles.crear(perfil, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();
        return null;
    }

    @Override
    public String grabar() {
        if (!IMantenimiento.validarPerfil(perfilSistema, 'U')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        try {
            ejbPerfiles.actualizar(perfil, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();
        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfilSistema, 'D')) {
            return null;
        }
        if (!perfilSistema.getBorrado()) {
            Mensajes.advertencia("No tiene autorización para borrar un registro");
        }
        try {
            ejbPerfiles.eliminar(perfil, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return the combosBean
     */
    public CombosBean getCombosBean() {
        return combosBean;
    }

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @return the perfiles
     */
    public LazyDataModel<Perfiles> getPerfiles() {
        return perfiles;
    }

    /**
     * @return the perfil
     */
    public Perfiles getPerfil() {
        return perfil;
    }

    /**
     * @return the perfilSistema
     */
    public Perfiles getPerfilSistema() {
        return perfilSistema;
    }

    /**
     * @param seguridadBean the seguridadBean to set
     */
    public void setSeguridadBean(SeguridadBean seguridadBean) {
        this.seguridadBean = seguridadBean;
    }

    /**
     * @param combosBean the combosBean to set
     */
    public void setCombosBean(CombosBean combosBean) {
        this.combosBean = combosBean;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @param perfiles the perfiles to set
     */
    public void setPerfiles(LazyDataModel<Perfiles> perfiles) {
        this.perfiles = perfiles;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    /**
     * @param perfilSistema the perfilSistema to set
     */
    public void setPerfilSistema(Perfiles perfilSistema) {
        this.perfilSistema = perfilSistema;
    }
}
