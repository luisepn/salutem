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
import org.controladores.salutem.ParametrosFacade;
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

    private int grupo;
    private int modulo;
    private int menu;

    @EJB
    private PerfilesFacade ejbPerfiles;
    @EJB
    private ParametrosFacade ejbParametros;
    @EJB
    private MenusFacade ejbMenus;

    public PerfilesBean() {
        perfiles = new LazyDataModel<Perfiles>() {
            @Override
            public List<Perfiles> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        perfilSistema = seguridadBean.traerPerfil("Perfiles");
    }

    private List<Perfiles> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            Map parameters = new HashMap();
            String where = " o.activo=:activo ";
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

            if (!formulario.isMostrar()) {
                if (modulo != 0) {
                    combosBean.setModulo(ejbParametros.buscar(modulo));
                }
                if (menu != 0) {
                    combosBean.setMenu(ejbMenus.buscar(menu));
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
        perfil = new Perfiles();
        perfil.setActivo(Boolean.TRUE);
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfilSistema, 'U')) {
            return null;
        }
        perfil = (Perfiles) perfiles.getRowData();
        combosBean.setMenu(perfil.getMenu() != null ? perfil.getMenu().getMenupadre() : null);
        combosBean.setModulo(combosBean.getMenu() != null ? combosBean.getMenu().getModulo() : null);
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfilSistema, 'D')) {
            return null;
        }
        perfil = (Perfiles) perfiles.getRowData();
        combosBean.setMenu(perfil.getMenu() != null ? perfil.getMenu().getMenupadre() : null);
        combosBean.setModulo(combosBean.getMenu() != null ? combosBean.getMenu().getModulo() : null);
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
        try {
            String where = "o.menu=:menu and o.grupo=:grupo";
            Map parametros = new HashMap();
            parametros.put("menu", perfil.getMenu());
            parametros.put("grupo", perfil.getGrupo());
            if (perfil.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", perfil.getId());
            }
            if (ejbPerfiles.contar(where, parametros) > 0) {
                Mensajes.advertencia("No se permiten perfiles con submenú y grupo duplicados");
                return true;
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
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
            perfil.setCreado(new Date());
            perfil.setCreadopor(seguridadBean.getLogueado().getUserid());
            perfil.setActualizado(perfil.getCreado());
            perfil.setActualizadopor(perfil.getCreadopor());
            ejbPerfiles.crear(perfil, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
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
            perfil.setActualizado(new Date());
            perfil.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbPerfiles.actualizar(perfil, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfilSistema, 'D')) {
            return null;
        }
        try {
            ejbPerfiles.eliminar(perfil, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
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

    /**
     * @return the grupo
     */
    public int getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the modulo
     */
    public int getModulo() {
        return modulo;
    }

    /**
     * @param modulo the modulo to set
     */
    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    /**
     * @return the menu
     */
    public int getMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(int menu) {
        this.menu = menu;
    }
}
