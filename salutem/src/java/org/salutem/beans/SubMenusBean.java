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
import org.controladores.salutem.MenusFacade;
import org.entidades.salutem.Menus;
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
 * @since 19 de Noviembre de 2017, 22:58:09 AM
 */
@ManagedBean(name = "salutemSubmenus")
@ViewScoped
public class SubMenusBean implements Serializable, IMantenimiento {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Menus> menus;
    private Menus menu;
    private Menus menuPadre;
    private Perfiles perfil;

    @EJB
    private MenusFacade ejbMenus;

    public SubMenusBean() {
        menus = new LazyDataModel<Menus>() {
            @Override
            public List<Menus> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return null;
            }
        };
    }

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Submenus");
    }

    private List<Menus> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            if (menuPadre == null) {
                return null;
            }
            Map parameters = new HashMap();
            parameters.put("padre", menuPadre);
            String where = "o.menupadre=:padre";
            for (Map.Entry e : map.entrySet()) {
                String clave = (String) e.getKey();
                String valor = (String) e.getValue();
                where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
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
                order = "o.texto";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbMenus.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SubMenusBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        if (menuPadre == null) {
            Mensajes.advertencia("Seleccione un menú primero");
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
        menu.setMenupadre(menuPadre);
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
        if ((menu.getTexto() == null) || (menu.getTexto().isEmpty())) {
            Mensajes.advertencia("Es necesario Texto a desplegar");
            return true;
        }
        if ((menu.getFormulario() == null) || (menu.getFormulario().isEmpty())) {
            Mensajes.advertencia("Es necesario nombre de formulario");
            return true;
        }
        if (menu.getMenupadre() == null) {
            Mensajes.advertencia("Es necesario seleccionar un  Menú");
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
            ejbMenus.crear(menu, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SubMenusBean.class.getName()).log(Level.SEVERE, null, ex);
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
            ejbMenus.actualizar(menu, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SubMenusBean.class.getName()).log(Level.SEVERE, null, ex);
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
            ejbMenus.eliminar(menu, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(SubMenusBean.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return the menuPadre
     */
    public Menus getMenuPadre() {
        return menuPadre;
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
     * @param menuPadre the menuPadre to set
     */
    public void setMenuPadre(Menus menuPadre) {
        this.menuPadre = menuPadre;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }
}
