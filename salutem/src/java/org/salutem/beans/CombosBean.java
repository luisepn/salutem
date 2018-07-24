package org.salutem.beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.controladores.salutem.HorasFacade;
import org.controladores.salutem.InstitucionesFacade;
import org.controladores.salutem.MaestrosFacade;
import org.controladores.salutem.MaterialesFacade;
import org.controladores.salutem.MenusFacade;
import org.controladores.salutem.ParametrosFacade;
import org.controladores.salutem.UsuariosFacade;
import org.entidades.salutem.Horas;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Parametros;
import org.entidades.salutem.Maestros;
import org.entidades.salutem.Materiales;
import org.entidades.salutem.Menus;
import org.entidades.salutem.Usuarios;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.salutem.utilitarios.Mensajes;

@ManagedBean(name = "salutemCombos")
@ViewScoped
public class CombosBean implements Serializable {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;

    private final String TIPO_DE_MATERIAL = "TMT";
    private final String TIPO_DE_FOCO = "TF";
    private final String TIPO_DE_TRATAMIENTO = "TTR";
    private final String MODULOS_DE_SISTEMA = "MDS";
    private final String GENERO_HUMANO = "GHU";
    private final String GRUPO_DE_USUARIO = "GRPUSR";

    private Parametros modulo;
    private Parametros grupo;
    private Menus menu;
    private Instituciones institucion;

    private Parametros foco;
    private Parametros tipo;

    @EJB
    private InstitucionesFacade ejbInstituciones;
    @EJB
    private MaestrosFacade ejbMaestros;
    @EJB
    private MaterialesFacade ejbMateriales;
    @EJB
    private MenusFacade ejbMenus;
    @EJB
    private ParametrosFacade ejbParametros;
    @EJB
    private UsuariosFacade ejbUsuarios;
    @EJB
    private HorasFacade ejbHoras;

    private SelectItem[] getSelectItems(List<?> entities, String clave, boolean selectOne) {
        if (entities == null) {
            return null;
        }
        int size = selectOne ? entities.size() + 1 : entities.size();
        int i = 0;
        SelectItem[] items = new SelectItem[size];
        if (selectOne) {

            switch (clave) {
                case "object":
                    items[i++] = new SelectItem(null, "--Seleccione uno--");
                    break;
                case "id":
                    items[i++] = new SelectItem(0, "--Seleccione uno--");
                    break;
                case "toString":
                    items[i++] = new SelectItem("", "--Seleccione uno--");
                    break;

            }

        }

        for (Object x : entities) {

            switch (clave) {
                case "object":
                    items[i++] = new SelectItem(x, x.toString());
                    break;
                case "id":
                    items[i++] = new SelectItem(x.hashCode(), x.toString());
                    break;
                case "toString":
                    items[i++] = new SelectItem(x.toString(), x.toString());
                    break;

            }

        }
        return items;
    }

    public SelectItem[] getInstituciones() {
        if (seguridadBean.getGrupo().getCodigo().equals("GSA")) {
            return getSelectItems(traerInstituciones(Boolean.FALSE), "object", true);
        }
        if (seguridadBean.getInstitucion() != null) {
            List<Instituciones> lista = new LinkedList<>();
            lista.add(seguridadBean.getInstitucion());
            return getSelectItems(lista, "object", false);
        }
        return getSelectItems(traerInstituciones(Boolean.FALSE), "object", true);
    }

    public SelectItem[] getInstitucionesId() {
        if (seguridadBean.getGrupo().getCodigo().equals("GSA")) {
            return getSelectItems(traerInstituciones(Boolean.FALSE), "id", true);
        }
        if (seguridadBean.getInstitucion() != null) {
            List<Instituciones> lista = new LinkedList<>();
            lista.add(seguridadBean.getInstitucion());
            return getSelectItems(lista, "id", false);
        }
        return getSelectItems(traerInstituciones(Boolean.FALSE), "object", true);

    }

    public SelectItem[] getLaboratorios() {
        return getSelectItems(traerInstituciones(Boolean.TRUE), "object", true);
    }

    public SelectItem[] getMaestros() {
        return getSelectItems(traerMaestros(), "object", true);
    }

    public SelectItem[] getMaestrosId() {
        return getSelectItems(traerMaestros(), "id", true);
    }

    public SelectItem[] getMateriales() {
        return getSelectItems(traerMateriales(), "object", true);
    }

    public SelectItem[] getMenus() {
        return getSelectItems(traerMenus(), "object", true);
    }

    public SelectItem[] getMenusId() {
        return getSelectItems(traerMenus(), "id", true);
    }

    public SelectItem[] getSubMenus() {
        return getSelectItems(traerSubMenus(), "object", true);
    }

    public SelectItem[] getSubMenusDisponibles() {
        return getSelectItems(traerSubMenusDisponibles(), "object", true);
    }

    public SelectItem[] getGrupoUsuarios() {
        return getSelectItems(traerParametros(GRUPO_DE_USUARIO), "object", true);
    }

    public SelectItem[] getGrupoUsuariosId() {
        return getSelectItems(traerParametros(GRUPO_DE_USUARIO), "id", true);
    }

    public SelectItem[] getModulos() {
        return getSelectItems(traerParametros(MODULOS_DE_SISTEMA), "object", true);
    }

    public SelectItem[] getModulosId() {
        return getSelectItems(traerParametros(MODULOS_DE_SISTEMA), "id", true);
    }

    public SelectItem[] getGenero() {
        return getSelectItems(traerParametros(GENERO_HUMANO), "object", true);
    }

    public SelectItem[] getTipoMaterial() {
        return getSelectItems(traerParametros(TIPO_DE_MATERIAL), "object", true);
    }

    public SelectItem[] getTipoMaterialId() {
        return getSelectItems(traerParametros(TIPO_DE_MATERIAL), "id", true);
    }

    public SelectItem[] getFocos() {
        return getSelectItems(traerParametros(TIPO_DE_FOCO), "object", true);
    }

    public SelectItem[] getFocosId() {
        return getSelectItems(traerParametros(TIPO_DE_FOCO), "id", true);
    }

    public SelectItem[] getTratamientos() {
        return getSelectItems(traerParametros(TIPO_DE_TRATAMIENTO), "object", true);
    }

    public SelectItem[] getComboHoras() {
        return getSelectItems(traerHoras(), "object", true);
    }

    public SelectItem[] getComboHorasId() {
        return getSelectItems(traerHoras(), "id", true);
    }

    private List<Instituciones> traerInstituciones(Boolean tipo) {
        try {
            return ejbInstituciones.traerInstituciones(tipo);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Maestros> traerMaestros() {
        try {
            return ejbMaestros.traerMaestros();
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Materiales> traerMateriales() {
        try {
            return ejbMateriales.traerMateriales(foco, tipo);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Menus> traerMenus() {
        try {
            return ejbMenus.traerMenus(modulo);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Menus> traerSubMenus() {
        try {
            return ejbMenus.traerSubMenus(menu);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Menus> traerSubMenusDisponibles() {
        try {
            return ejbMenus.traerSubMenusDisponibles(menu, grupo);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Parametros> traerParametros(String maestro) {
        try {
            return ejbParametros.traerParametros(maestro);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Horas> traerHoras() {
        try {
            return ejbHoras.traerHoras(institucion);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Instituciones traerInstitucion(Integer id) throws ExcepcionDeConsulta {
        return (Instituciones) ejbInstituciones.buscar(id);
    }

    public Maestros traerMaestro(Integer id) throws ExcepcionDeConsulta {
        return (Maestros) ejbMaestros.buscar(id);
    }

    public Materiales traerMaterial(Integer id) throws ExcepcionDeConsulta {
        return (Materiales) ejbMateriales.buscar(id);
    }

    public Menus traerMenu(Integer id) throws ExcepcionDeConsulta {
        return (Menus) ejbMenus.buscar(id);
    }

    public Parametros traerParametro(Integer id) throws ExcepcionDeConsulta {
        return (Parametros) ejbParametros.buscar(id);
    }

    public Usuarios traerUsuario(Integer id) throws ExcepcionDeConsulta {
        return (Usuarios) ejbUsuarios.buscar(id);
    }

    public Horas traerHora(Integer id) throws ExcepcionDeConsulta {
        return (Horas) ejbHoras.buscar(id);
    }

    /**
     * @return the modulo
     */
    public Parametros getModulo() {
        return modulo;
    }

    /**
     * @return the grupo
     */
    public Parametros getGrupo() {
        return grupo;
    }

    /**
     * @return the menu
     */
    public Menus getMenu() {
        return menu;
    }

    /**
     * @param modulo the modulo to set
     */
    public void setModulo(Parametros modulo) {
        this.modulo = modulo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Parametros grupo) {
        this.grupo = grupo;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(Menus menu) {
        this.menu = menu;
    }

    /**
     * @return the foco
     */
    public Parametros getFoco() {
        return foco;
    }

    /**
     * @return the tipo
     */
    public Parametros getTipo() {
        return tipo;
    }

    /**
     * @param foco the foco to set
     */
    public void setFoco(Parametros foco) {
        this.foco = foco;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Parametros tipo) {
        this.tipo = tipo;
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

}
