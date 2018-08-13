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
import org.controladores.salutem.MenusFacade;
import org.entidades.salutem.Menus;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutem.ExcepcionDeEliminacion;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 19 de Noviembre de 2017, 04:49:59 AM
 */
@ManagedBean(name = "salutemMenus")
@ViewScoped
public class MenusBean implements Serializable, IMantenimiento {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Menus> menus;
    private Menus menu;
    private int modulo;
    private Perfiles perfil;

    @EJB
    private MenusFacade ejbMenus;

    public MenusBean() {
        menus = new LazyDataModel<Menus>() {
            @Override
            public List<Menus> load(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                }
                return cargar(i, pageSize, scs, map);
            }
        };
    }

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Menus");
    }

    private List<Menus> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            String where = " o.activo=:activo and o.menupadre is null and o.modulo.activo=true";
            Map parameters = new HashMap();
            parameters.put("activo", seguridadBean.getVerActivos());

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

            if (seguridadBean.getInicioCreado() != null && seguridadBean.getFinCreado() != null) {
                where += " and o.creado between :iniciocreado and :fincreado";
                parameters.put("iniciocreado", seguridadBean.getInicioCreado());
                parameters.put("fincreado", seguridadBean.getFinCreado());
            }
            if (seguridadBean.getInicioActualizado() != null && seguridadBean.getFinActualizado() != null) {
                where += " and o.actualizado between :inicioactualizado and :finactualizado";
                parameters.put("inicioactualizado", seguridadBean.getInicioActualizado());
                parameters.put("finactualizado", seguridadBean.getFinActualizado());
            }

            int total = ejbMenus.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            menus.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.modulo.nombre, o.codigo";
            } else {
                order = (seguridadBean.getVerAgrupado() ? "o.modulo.nombre," : "") + "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbMenus.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MenusBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        menus = new LazyDataModel<Menus>() {
            @Override
            public List<Menus> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        menu = new Menus();
        menu.setActivo(Boolean.TRUE);
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        menu = (Menus) menus.getRowData();
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        menu = (Menus) menus.getRowData();
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        if (menu.getModulo() == null) {
            Mensajes.advertencia("Es necesario módulo");
            return true;
        }
        if ((menu.getCodigo() == null) || (menu.getCodigo().isEmpty())) {
            Mensajes.advertencia("Es necesario código");
            return true;
        }
        if ((menu.getNombre() == null) || (menu.getNombre().isEmpty())) {
            Mensajes.advertencia("Es necesario nombre");
            return true;
        }
        try {
            String where = "o.codigo=:codigo and o.modulo=:modulo";
            Map parametros = new HashMap();
            parametros.put("codigo", menu.getCodigo());
            parametros.put("modulo", menu.getModulo());
            if (menu.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", menu.getId());
            }
            if (ejbMenus.contar(where, parametros) > 0) {
                Mensajes.advertencia("No se permiten menús con código duplicado");
                return true;
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MenusBean.class.getName()).log(Level.SEVERE, null, ex);
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
            menu.setCreado(new Date());
            menu.setCreadopor(seguridadBean.getLogueado().getUserid());
            menu.setActualizado(menu.getCreado());
            menu.setActualizadopor(menu.getCreadopor());
            ejbMenus.crear(menu, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MenusBean.class.getName()).log(Level.SEVERE, null, ex);
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
            menu.setActualizado(new Date());
            menu.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbMenus.actualizar(menu, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MenusBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbMenus.eliminar(menu, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MenusBean.class.getName()).log(Level.SEVERE, null, ex);
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
        return Menus.class.getSimpleName();
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
     * @return the menus
     */
    public LazyDataModel<Menus> getMenus() {
        return menus;
    }

    /**
     * @return the menu
     */
    public Menus getMenu() {
        return menu;
    }

    /**
     * @return the modulo
     */
    public int getModulo() {
        return modulo;
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
     * @param menus the menus to set
     */
    public void setMenus(LazyDataModel<Menus> menus) {
        this.menus = menus;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(Menus menu) {
        this.menu = menu;
    }

    /**
     * @param modulo the modulo to set
     */
    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }
}
