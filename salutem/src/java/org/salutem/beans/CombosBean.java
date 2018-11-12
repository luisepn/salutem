package org.salutem.beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.controladores.salutem.CitasFacade;
import org.controladores.salutem.HorariosFacade;
import org.controladores.salutem.HorasFacade;
import org.controladores.salutem.InstitucionesFacade;
import org.controladores.salutem.MaestrosFacade;
import org.controladores.salutem.MaterialesFacade;
import org.controladores.salutem.MenusFacade;
import org.controladores.salutem.ParametrosFacade;
import org.controladores.salutem.ProfesionalesFacade;
import org.controladores.salutem.UsuariosFacade;
import org.entidades.salutem.Citas;
import org.entidades.salutem.Horarios;
import org.entidades.salutem.Horas;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Parametros;
import org.entidades.salutem.Maestros;
import org.entidades.salutem.Materiales;
import org.entidades.salutem.Menus;
import org.entidades.salutem.Profesionales;
import org.entidades.salutem.Usuarios;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.salutem.utilitarios.Mensajes;

@Named("salutemCombos")
@ViewScoped
public class CombosBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;

    public static String PARAMETROS_GENERALES = "PG";
    public static String TIPO_DE_MATERIAL = "TMT";
    public static String TIPO_DE_FOCO = "TF";
    public static String TIPO_DE_TRATAMIENTO = "TTR";
    public static String MODULOS_DE_SISTEMA = "MDS";
    public static String GENERO_HUMANO = "GHU";
    public static String GRUPO_DE_USUARIO = "GRPUSR";
    public static String DIAS_SEMANA = "DS";
    public static String ESPECIALIDADES = "ESP";
    public static String TIPO_DE_DATO = "TD";
    public static String GRUPOS_DE_DATOS = "GD";

    private Parametros modulo;
    private Parametros grupo;
    private Menus menu;
    private Instituciones institucion;
    private Parametros especialidad;

    private Parametros foco;
    private Parametros tipo;
    private Profesionales profesional;

    private String clasificador;
    private String tabla;

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
    @EJB
    private HorariosFacade ejbhHorarios;
    @EJB
    private ProfesionalesFacade ejbProfesionales;
    @EJB
    private CitasFacade ejbCitas;

    public CombosBean() {
    }

    @PostConstruct
    private void iniciar() {
        institucion = seguridadBean.getInstitucion();
        profesional = seguridadBean.getProfesional();
    }

    public static SelectItem[] getSelectItems(List<?> entities, String clave, boolean selectOne) {
        if (entities == null) {
            return null;
        }
        int size = selectOne ? entities.size() + 1 : entities.size();
        int i = 0;
        SelectItem[] items = new SelectItem[size];
        if (selectOne) {

            switch (clave) {
                case "object":
                case "op":
                    items[i++] = new SelectItem(null, "--Seleccione uno--");
                    break;
                case "id":
                    items[i++] = new SelectItem(0, "--Seleccione uno--");
                    break;
                case "toString":
                case "parameters":
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
                case "op":
                    items[i++] = new SelectItem(x.hashCode(), x.toString());
                    break;
                case "toString":
                    items[i++] = new SelectItem(x.toString(), x.toString());
                    break;
                case "parameters":
                    items[i++] = new SelectItem(((Parametros) x).getParametros(), x.toString());
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

    public SelectItem[] getLaboratoriosId() {
        return getSelectItems(traerInstituciones(Boolean.TRUE), "id", true);
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
        return getSelectItems(traerParametros(GRUPO_DE_USUARIO, "o.codigo"), "object", true);
    }

    public SelectItem[] getGrupoUsuariosId() {
        return getSelectItems(traerParametros(GRUPO_DE_USUARIO, "o.codigo"), "id", true);
    }

    public SelectItem[] getModulos() {
        return getSelectItems(traerParametros(MODULOS_DE_SISTEMA, "o.codigo"), "object", true);
    }

    public SelectItem[] getModulosId() {
        return getSelectItems(traerParametros(MODULOS_DE_SISTEMA, "o.codigo"), "id", true);
    }

    public SelectItem[] getGenero() {
        return getSelectItems(traerParametros(GENERO_HUMANO, "o.codigo"), "object", true);
    }

    public SelectItem[] getTipoMaterial() {
        return getSelectItems(traerParametros(TIPO_DE_MATERIAL, "o.codigo"), "object", true);
    }

    public SelectItem[] getTipoMaterialId() {
        return getSelectItems(traerParametros(TIPO_DE_MATERIAL, "o.codigo"), "id", true);
    }

    public SelectItem[] getFocos() {
        return getSelectItems(traerParametros(TIPO_DE_FOCO, "o.codigo"), "object", true);
    }

    public SelectItem[] getFocosId() {
        return getSelectItems(traerParametros(TIPO_DE_FOCO, "o.codigo"), "id", true);
    }

    public SelectItem[] getTratamientos() {
        return getSelectItems(traerParametros(TIPO_DE_TRATAMIENTO, "o.codigo"), "object", true);
    }

    public SelectItem[] getDias() {
        return getSelectItems(traerParametros(DIAS_SEMANA, "o.parametros"), "object", true);
    }

    public SelectItem[] getDiasId() {
        return getSelectItems(traerParametros(DIAS_SEMANA, "o.parametros"), "id", true);
    }

    public SelectItem[] getEspecialidades() {
        return getSelectItems(traerParametros(ESPECIALIDADES, "o.codigo"), "object", true);
    }

    public SelectItem[] getEspecialidadesId() {
        return getSelectItems(traerParametros(ESPECIALIDADES, "o.codigo"), "id", true);
    }

    public SelectItem[] getTiposDeDato() {
        return getSelectItems(traerParametros(TIPO_DE_DATO, "o.codigo"), "object", true);
    }

    public SelectItem[] getTiposDeDatoId() {
        return getSelectItems(traerParametros(TIPO_DE_DATO, "o.codigo"), "id", true);
    }

    public SelectItem[] getGruposDeDatos() {
        List<Parametros> lista = traerParametros(GRUPOS_DE_DATOS, clasificador, "o.codigo");
        if (lista != null && !lista.isEmpty()) {
            return getSelectItems(traerParametros(GRUPOS_DE_DATOS, clasificador, "o.codigo"), "object", true);
        } else {
            return getSelectItems(traerParametros(GRUPOS_DE_DATOS, null, "o.codigo"), "object", true);
        }
    }

    public SelectItem[] getGruposDeDatosId() {
        List<Parametros> lista = traerParametros(GRUPOS_DE_DATOS, clasificador, "o.codigo");
        if (lista != null && !lista.isEmpty()) {
            return getSelectItems(traerParametros(GRUPOS_DE_DATOS, clasificador, "o.codigo"), "id", true);
        } else {
            return getSelectItems(traerParametros(GRUPOS_DE_DATOS, null, "o.codigo"), "id", true);
        }
    }

    public SelectItem[] getHoras() {
        return getSelectItems(getListaHoras(), "object", true);
    }

    public SelectItem[] getHorasId() {
        return getSelectItems(getListaHoras(), "id", true);
    }

    public SelectItem[] getProfesionales() {
        return getSelectItems(traerProfesionales(), "object", true);
    }

    public SelectItem[] getProfesionalesId() {
        return getSelectItems(traerProfesionales(), "id", true);
    }

    public SelectItem[] getCitas() {
        return getSelectItems(traerCitas(), "object", true);
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

    private List<Parametros> traerParametros(String maestro, String orden) {
        try {
            return ejbParametros.traerParametros(maestro, orden);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Parametros> traerParametros(String maestro, String parametros, String orden) {
        try {
            return ejbParametros.traerParametros(maestro, parametros, orden);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Parametros> getListaDias() {
        return traerParametros(CombosBean.DIAS_SEMANA, "o.parametros");
    }

    public List<Horas> getListaHoras() {
        try {
            return ejbHoras.traerHoras(institucion);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Profesionales> traerProfesionales() {
        try {
            return ejbProfesionales.traerProfesionales(institucion, especialidad);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CombosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Citas> traerCitas() {
        try {
            return ejbCitas.traerCitas(profesional);
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

    public Horarios traerHorario(Integer id) throws ExcepcionDeConsulta {
        return (Horarios) ejbhHorarios.buscar(id);
    }

    public Profesionales traerProfesional(Integer id) throws ExcepcionDeConsulta {
        return (Profesionales) ejbProfesionales.buscar(id);
    }

    public Citas traerCita(Integer id) throws ExcepcionDeConsulta {
        return (Citas) ejbCitas.buscar(id);
    }

    public SelectItem[] getIconos() {
        SelectItem[] items = new SelectItem[174];
        items[0] = new SelectItem(null, "Ninguno");
        items[1] = new SelectItem("ui-icon ui-icon-carat-1-n", "carat-1-n");
        items[2] = new SelectItem("ui-icon ui-icon-carat-1-ne", "carat-1-ne");
        items[3] = new SelectItem("ui-icon ui-icon-carat-1-e", "carat-1-e");
        items[4] = new SelectItem("ui-icon ui-icon-carat-1-se", "carat-1-se");
        items[5] = new SelectItem("ui-icon ui-icon-carat-1-s", "carat-1-s");
        items[6] = new SelectItem("ui-icon ui-icon-carat-1-sw", "carat-1-sw");
        items[7] = new SelectItem("ui-icon ui-icon-carat-1-w", "carat-1-w");
        items[8] = new SelectItem("ui-icon ui-icon-carat-1-nw", "carat-1-nw");
        items[9] = new SelectItem("ui-icon ui-icon-carat-2-n-s", "carat-2-n-s");
        items[10] = new SelectItem("ui-icon ui-icon-carat-2-e-w", "carat-2-e-w");
        items[11] = new SelectItem("ui-icon ui-icon-triangle-1-n", "triangle-1-n");
        items[12] = new SelectItem("ui-icon ui-icon-triangle-1-ne", "triangle-1-ne");
        items[13] = new SelectItem("ui-icon ui-icon-triangle-1-e", "triangle-1-e");
        items[14] = new SelectItem("ui-icon ui-icon-triangle-1-se", "triangle-1-se");
        items[15] = new SelectItem("ui-icon ui-icon-triangle-1-s", "triangle-1-s");
        items[16] = new SelectItem("ui-icon ui-icon-triangle-1-sw", "triangle-1-sw");
        items[17] = new SelectItem("ui-icon ui-icon-triangle-1-w", "triangle-1-w");
        items[18] = new SelectItem("ui-icon ui-icon-triangle-1-nw", "triangle-1-nw");
        items[19] = new SelectItem("ui-icon ui-icon-triangle-2-n-s", "triangle-2-n-s");
        items[20] = new SelectItem("ui-icon ui-icon-triangle-2-e-w", "triangle-2-e-w");
        items[21] = new SelectItem("ui-icon ui-icon-arrow-1-n", "arrow-1-n");
        items[22] = new SelectItem("ui-icon ui-icon-arrow-1-ne", "arrow-1-ne");
        items[23] = new SelectItem("ui-icon ui-icon-arrow-1-e", "arrow-1-e");
        items[24] = new SelectItem("ui-icon ui-icon-arrow-1-se", "arrow-1-se");
        items[25] = new SelectItem("ui-icon ui-icon-arrow-1-s", "arrow-1-s");
        items[26] = new SelectItem("ui-icon ui-icon-arrow-1-sw", "arrow-1-sw");
        items[27] = new SelectItem("ui-icon ui-icon-arrow-1-w", "arrow-1-w");
        items[28] = new SelectItem("ui-icon ui-icon-arrow-1-nw", "arrow-1-nw");
        items[29] = new SelectItem("ui-icon ui-icon-arrow-2-n-s", "arrow-2-n-s");
        items[30] = new SelectItem("ui-icon ui-icon-arrow-2-ne-sw", "arrow-2-ne-sw");
        items[31] = new SelectItem("ui-icon ui-icon-arrow-2-e-w", "arrow-2-e-w");
        items[32] = new SelectItem("ui-icon ui-icon-arrow-2-se-nw", "arrow-2-se-nw");
        items[33] = new SelectItem("ui-icon ui-icon-arrowstop-1-n", "arrowstop-1-n");
        items[34] = new SelectItem("ui-icon ui-icon-arrowstop-1-e", "arrowstop-1-e");
        items[35] = new SelectItem("ui-icon ui-icon-arrowstop-1-s", "arrowstop-1-s");
        items[36] = new SelectItem("ui-icon ui-icon-arrowstop-1-w", "arrowstop-1-w");
        items[37] = new SelectItem("ui-icon ui-icon-arrowthick-1-n", "arrowthick-1-n");
        items[38] = new SelectItem("ui-icon ui-icon-arrowthick-1-ne", "arrowthick-1-ne");
        items[39] = new SelectItem("ui-icon ui-icon-arrowthick-1-e", "arrowthick-1-e");
        items[40] = new SelectItem("ui-icon ui-icon-arrowthick-1-se", "arrowthick-1-se");
        items[41] = new SelectItem("ui-icon ui-icon-arrowthick-1-s", "arrowthick-1-s");
        items[42] = new SelectItem("ui-icon ui-icon-arrowthick-1-sw", "arrowthick-1-sw");
        items[43] = new SelectItem("ui-icon ui-icon-arrowthick-1-w", "arrowthick-1-w");
        items[44] = new SelectItem("ui-icon ui-icon-arrowthick-1-nw", "arrowthick-1-nw");
        items[45] = new SelectItem("ui-icon ui-icon-arrowthick-2-n-s", "arrowthick-2-n-s");
        items[46] = new SelectItem("ui-icon ui-icon-arrowthick-2-ne-sw", "arrowthick-2-ne-sw");
        items[47] = new SelectItem("ui-icon ui-icon-arrowthick-2-e-w", "arrowthick-2-e-w");
        items[48] = new SelectItem("ui-icon ui-icon-arrowthick-2-se-nw", "arrowthick-2-se-nw");
        items[49] = new SelectItem("ui-icon ui-icon-arrowthickstop-1-n", "arrowthickstop-1-n");
        items[50] = new SelectItem("ui-icon ui-icon-arrowthickstop-1-e", "arrowthickstop-1-e");
        items[51] = new SelectItem("ui-icon ui-icon-arrowthickstop-1-s", "arrowthickstop-1-s");
        items[52] = new SelectItem("ui-icon ui-icon-arrowthickstop-1-w", "arrowthickstop-1-w");
        items[53] = new SelectItem("ui-icon ui-icon-arrowreturnthick-1-w", "arrowreturnthick-1-w");
        items[54] = new SelectItem("ui-icon ui-icon-arrowreturnthick-1-n", "arrowreturnthick-1-n");
        items[55] = new SelectItem("ui-icon ui-icon-arrowreturnthick-1-e", "arrowreturnthick-1-e");
        items[56] = new SelectItem("ui-icon ui-icon-arrowreturnthick-1-s", "arrowreturnthick-1-s");
        items[57] = new SelectItem("ui-icon ui-icon-arrowreturn-1-w", "arrowreturn-1-w");
        items[58] = new SelectItem("ui-icon ui-icon-arrowreturn-1-n", "arrowreturn-1-n");
        items[59] = new SelectItem("ui-icon ui-icon-arrowreturn-1-e", "arrowreturn-1-e");
        items[60] = new SelectItem("ui-icon ui-icon-arrowreturn-1-s", "arrowreturn-1-s");
        items[61] = new SelectItem("ui-icon ui-icon-arrowrefresh-1-w", "arrowrefresh-1-w");
        items[62] = new SelectItem("ui-icon ui-icon-arrowrefresh-1-n", "arrowrefresh-1-n");
        items[63] = new SelectItem("ui-icon ui-icon-arrowrefresh-1-e", "arrowrefresh-1-e");
        items[64] = new SelectItem("ui-icon ui-icon-arrowrefresh-1-s", "arrowrefresh-1-s");
        items[65] = new SelectItem("ui-icon ui-icon-arrow-4", "arrow-4");
        items[66] = new SelectItem("ui-icon ui-icon-arrow-4-diag", "arrow-4-diag");
        items[67] = new SelectItem("ui-icon ui-icon-extlink", "extlink");
        items[68] = new SelectItem("ui-icon ui-icon-newwin", "newwin");
        items[69] = new SelectItem("ui-icon ui-icon-refresh", "refresh");
        items[70] = new SelectItem("ui-icon ui-icon-shuffle", "shuffle");
        items[71] = new SelectItem("ui-icon ui-icon-transfer-e-w", "transfer-e-w");
        items[72] = new SelectItem("ui-icon ui-icon-transferthick-e-w", "transferthick-e-w");
        items[73] = new SelectItem("ui-icon ui-icon-folder-collapsed", "folder-collapsed");
        items[74] = new SelectItem("ui-icon ui-icon-folder-open", "folder-open");
        items[75] = new SelectItem("ui-icon ui-icon-document", "document");
        items[76] = new SelectItem("ui-icon ui-icon-document-b", "document-b");
        items[77] = new SelectItem("ui-icon ui-icon-note", "note");
        items[78] = new SelectItem("ui-icon ui-icon-mail-closed", "mail-closed");
        items[79] = new SelectItem("ui-icon ui-icon-mail-open", "mail-open");
        items[80] = new SelectItem("ui-icon ui-icon-suitcase", "suitcase");
        items[81] = new SelectItem("ui-icon ui-icon-comment", "comment");
        items[82] = new SelectItem("ui-icon ui-icon-person", "person");
        items[83] = new SelectItem("ui-icon ui-icon-print", "print");
        items[84] = new SelectItem("ui-icon ui-icon-trash", "trash");
        items[85] = new SelectItem("ui-icon ui-icon-locked", "locked");
        items[86] = new SelectItem("ui-icon ui-icon-unlocked", "unlocked");
        items[87] = new SelectItem("ui-icon ui-icon-bookmark", "bookmark");
        items[88] = new SelectItem("ui-icon ui-icon-tag", "tag");
        items[89] = new SelectItem("ui-icon ui-icon-home", "home");
        items[90] = new SelectItem("ui-icon ui-icon-flag", "flag");
        items[91] = new SelectItem("ui-icon ui-icon-calendar", "calendar");
        items[92] = new SelectItem("ui-icon ui-icon-cart", "cart");
        items[93] = new SelectItem("ui-icon ui-icon-pencil", "pencil");
        items[94] = new SelectItem("ui-icon ui-icon-clock", "clock");
        items[95] = new SelectItem("ui-icon ui-icon-disk", "disk");
        items[96] = new SelectItem("ui-icon ui-icon-calculator", "calculator");
        items[97] = new SelectItem("ui-icon ui-icon-zoomin", "zoomin");
        items[98] = new SelectItem("ui-icon ui-icon-zoomout", "zoomout");
        items[99] = new SelectItem("ui-icon ui-icon-search", "search");
        items[100] = new SelectItem("ui-icon ui-icon-wrench", "wrench");
        items[101] = new SelectItem("ui-icon ui-icon-gear", "gear");
        items[102] = new SelectItem("ui-icon ui-icon-heart", "heart");
        items[103] = new SelectItem("ui-icon ui-icon-star", "star");
        items[104] = new SelectItem("ui-icon ui-icon-link", "link");
        items[105] = new SelectItem("ui-icon ui-icon-cancel", "cancel");
        items[106] = new SelectItem("ui-icon ui-icon-plus", "plus");
        items[107] = new SelectItem("ui-icon ui-icon-plusthick", "plusthick");
        items[108] = new SelectItem("ui-icon ui-icon-minus", "minus");
        items[109] = new SelectItem("ui-icon ui-icon-minusthick", "minusthick");
        items[110] = new SelectItem("ui-icon ui-icon-close", "close");
        items[111] = new SelectItem("ui-icon ui-icon-closethick", "closethick");
        items[112] = new SelectItem("ui-icon ui-icon-key", "key");
        items[113] = new SelectItem("ui-icon ui-icon-lightbulb", "lightbulb");
        items[114] = new SelectItem("ui-icon ui-icon-scissors", "scissors");
        items[115] = new SelectItem("ui-icon ui-icon-clipboard", "clipboard");
        items[116] = new SelectItem("ui-icon ui-icon-copy", "copy");
        items[117] = new SelectItem("ui-icon ui-icon-contact", "contact");
        items[118] = new SelectItem("ui-icon ui-icon-image", "image");
        items[119] = new SelectItem("ui-icon ui-icon-video", "video");
        items[120] = new SelectItem("ui-icon ui-icon-script", "script");
        items[121] = new SelectItem("ui-icon ui-icon-alert", "alert");
        items[122] = new SelectItem("ui-icon ui-icon-info", "info");
        items[123] = new SelectItem("ui-icon ui-icon-notice", "notice");
        items[124] = new SelectItem("ui-icon ui-icon-help", "help");
        items[125] = new SelectItem("ui-icon ui-icon-check", "check");
        items[126] = new SelectItem("ui-icon ui-icon-bullet", "bullet");
        items[127] = new SelectItem("ui-icon ui-icon-radio-off", "radio-off");
        items[128] = new SelectItem("ui-icon ui-icon-radio-on", "radio-on");
        items[129] = new SelectItem("ui-icon ui-icon-pin-w", "pin-w");
        items[130] = new SelectItem("ui-icon ui-icon-pin-s", "pin-s");
        items[131] = new SelectItem("ui-icon ui-icon-play", "play");
        items[132] = new SelectItem("ui-icon ui-icon-pause", "pause");
        items[133] = new SelectItem("ui-icon ui-icon-seek-next", "seek-next");
        items[134] = new SelectItem("ui-icon ui-icon-seek-prev", "seek-prev");
        items[135] = new SelectItem("ui-icon ui-icon-seek-end", "seek-end");
        items[136] = new SelectItem("ui-icon ui-icon-seek-start", "seek-start");
        items[137] = new SelectItem("ui-icon ui-icon-stop", "stop");
        items[138] = new SelectItem("ui-icon ui-icon-eject", "eject");
        items[139] = new SelectItem("ui-icon ui-icon-volume-off", "volume-off");
        items[140] = new SelectItem("ui-icon ui-icon-volume-on", "volume-on");
        items[141] = new SelectItem("ui-icon ui-icon-power", "power");
        items[142] = new SelectItem("ui-icon ui-icon-signal-diag", "signal-diag");
        items[143] = new SelectItem("ui-icon ui-icon-signal", "signal");
        items[144] = new SelectItem("ui-icon ui-icon-battery-0", "battery-0");
        items[145] = new SelectItem("ui-icon ui-icon-battery-1", "battery-1");
        items[146] = new SelectItem("ui-icon ui-icon-battery-2", "battery-2");
        items[147] = new SelectItem("ui-icon ui-icon-battery-3", "battery-3");
        items[148] = new SelectItem("ui-icon ui-icon-circle-plus", "circle-plus");
        items[149] = new SelectItem("ui-icon ui-icon-circle-minus", "circle-minus");
        items[150] = new SelectItem("ui-icon ui-icon-circle-close", "circle-close");
        items[151] = new SelectItem("ui-icon ui-icon-circle-triangle-e", "circle-triangle-e");
        items[152] = new SelectItem("ui-icon ui-icon-circle-triangle-s", "circle-triangle-s");
        items[153] = new SelectItem("ui-icon ui-icon-circle-triangle-w", "circle-triangle-w");
        items[154] = new SelectItem("ui-icon ui-icon-circle-triangle-n", "circle-triangle-n");
        items[155] = new SelectItem("ui-icon ui-icon-circle-arrow-e", "circle-arrow-e");
        items[156] = new SelectItem("ui-icon ui-icon-circle-arrow-s", "circle-arrow-s");
        items[157] = new SelectItem("ui-icon ui-icon-circle-arrow-w", "circle-arrow-w");
        items[158] = new SelectItem("ui-icon ui-icon-circle-arrow-n", "circle-arrow-n");
        items[159] = new SelectItem("ui-icon ui-icon-circle-zoomin", "circle-zoomin");
        items[160] = new SelectItem("ui-icon ui-icon-circle-zoomout", "circle-zoomout");
        items[161] = new SelectItem("ui-icon ui-icon-circle-check", "circle-check");
        items[162] = new SelectItem("ui-icon ui-icon-circlesmall-plus", "circlesmall-plus");
        items[163] = new SelectItem("ui-icon ui-icon-circlesmall-minus", "circlesmall-minus");
        items[164] = new SelectItem("ui-icon ui-icon-circlesmall-close", "circlesmall-close");
        items[165] = new SelectItem("ui-icon ui-icon-squaresmall-plus", "squaresmall-plus");
        items[166] = new SelectItem("ui-icon ui-icon-squaresmall-minus", "squaresmall-minus");
        items[167] = new SelectItem("ui-icon ui-icon-squaresmall-close", "squaresmall-close");
        items[168] = new SelectItem("ui-icon ui-icon-grip-dotted-vertical", "grip-dotted-vertical");
        items[169] = new SelectItem("ui-icon ui-icon-grip-dotted-horizontal", "grip-dotted-horizontal");
        items[170] = new SelectItem("ui-icon ui-icon-grip-solid-vertical", "grip-solid-vertical");
        items[171] = new SelectItem("ui-icon ui-icon-grip-solid-horizontal", "grip-solid-horizontal");
        items[172] = new SelectItem("ui-icon ui-icon-gripsmall-diagonal-se", "gripsmall-diagonal-se");
        items[173] = new SelectItem("ui-icon ui-icon-grip-diagonal-se", "grip-diagonal-se");
        return items;
    }

    public SelectItem[] getOperaciones() {
        SelectItem[] items = new SelectItem[6];
        items[0] = new SelectItem("A", "--Seleccione uno--");
        items[1] = new SelectItem("C", "[C] Crear");
        items[2] = new SelectItem("U", "[U] Actualizar");
        items[3] = new SelectItem("D", "[D] Borrar");
        items[4] = new SelectItem("I", "[I] Log IN");
        items[5] = new SelectItem("O", "[O] Log OUT");
        return items;
    }

    public SelectItem[] getClasificadores() {
        SelectItem[] items;
        items = new SelectItem[24];
        items[0] = new SelectItem("A", "--Seleccione uno--");
        items[1] = new SelectItem("Archivos", "Archivos");
        items[2] = new SelectItem("Atenciones", "Atenciones");
        items[3] = new SelectItem("Campos", "Campos");
        items[4] = new SelectItem("Citas", "Citas");
        items[5] = new SelectItem("Consultas", "Consultas");
        items[6] = new SelectItem("Datos", "Datos");
        items[7] = new SelectItem("Direcciones", "Direcciones");
        items[8] = new SelectItem("Formulas", "Formulas");
        items[9] = new SelectItem("Horarios", "Horarios");
        items[10] = new SelectItem("Horas", "Horas");
        items[11] = new SelectItem("Instituciones", "Instituciones");
        items[12] = new SelectItem("Maestros", "Maestros");
        items[13] = new SelectItem("Materiales", "Materiales");
        items[14] = new SelectItem("Menus", "Menus");
        items[15] = new SelectItem("Ordenes", "Ordenes");
        items[16] = new SelectItem("Pacientes", "Pacientes");
        items[17] = new SelectItem("Parametros", "Parametros");
        items[18] = new SelectItem("Perfiles", "Perfiles");
        items[19] = new SelectItem("Personas", "Personas");
        items[20] = new SelectItem("Prescripciones", "Prescripciones");
        items[21] = new SelectItem("Profesionales", "Profesionales");
        items[22] = new SelectItem("Usuarios", "Usuarios");
        items[23] = new SelectItem("Logs", "Logs");
        return items;
    }

    public SelectItem[] getTablas() {
        SelectItem[] items;
        if (tabla == null) {
            items = new SelectItem[24];
            items[0] = new SelectItem("A", "--Seleccione uno--");
            items[1] = new SelectItem("Archivos", "Archivos");
            items[2] = new SelectItem("Atenciones", "Atenciones");
            items[3] = new SelectItem("Campos", "Campos");
            items[4] = new SelectItem("Citas", "Citas");
            items[5] = new SelectItem("Consultas", "Consultas");
            items[6] = new SelectItem("Datos", "Datos");
            items[7] = new SelectItem("Direcciones", "Direcciones");
            items[8] = new SelectItem("Formulas", "Formulas");
            items[9] = new SelectItem("Horarios", "Horarios");
            items[10] = new SelectItem("Horas", "Horas");
            items[11] = new SelectItem("Instituciones", "Instituciones");
            items[12] = new SelectItem("Maestros", "Maestros");
            items[13] = new SelectItem("Materiales", "Materiales");
            items[14] = new SelectItem("Menus", "Menus");
            items[15] = new SelectItem("Ordenes", "Ordenes");
            items[16] = new SelectItem("Pacientes", "Pacientes");
            items[17] = new SelectItem("Parametros", "Parametros");
            items[18] = new SelectItem("Perfiles", "Perfiles");
            items[19] = new SelectItem("Personas", "Personas");
            items[20] = new SelectItem("Prescripciones", "Prescripciones");
            items[21] = new SelectItem("Profesionales", "Profesionales");
            items[22] = new SelectItem("Usuarios", "Usuarios");
            items[23] = new SelectItem("Logs", "Logs");
        } else {
            switch (tabla) {
                case "Atenciones":
                    items = new SelectItem[6];
                    items[0] = new SelectItem("A", "--Seleccione uno--");
                    items[1] = new SelectItem("Atenciones", tabla);
                    items[2] = new SelectItem("Datos", "Datos");
                    items[3] = new SelectItem("Formulas", "Formulas");
                    items[4] = new SelectItem("Ordenes", "Ordenes");
                    items[5] = new SelectItem("Prescripciones", "Prescripciones");
                    break;
                case "Pacientes":
                    items = new SelectItem[5];
                    items[0] = new SelectItem("A", "--Seleccione uno--");
                    items[1] = new SelectItem("Archivos", "Archivos");
                    items[2] = new SelectItem("Direcciones", "Direcciones");
                    items[3] = new SelectItem("Personas", "Personas");
                    items[4] = new SelectItem("Pacientes", tabla);
                    break;
                case "Personas":
                    items = new SelectItem[4];
                    items[0] = new SelectItem("A", "--Seleccione uno--");
                    items[1] = new SelectItem("Archivos", "Archivos");
                    items[2] = new SelectItem("Direcciones", "Direcciones");
                    items[3] = new SelectItem("Personas", tabla);
                    break;

                case "Profesionales":
                    items = new SelectItem[5];
                    items[0] = new SelectItem("A", "--Seleccione uno--");
                    items[1] = new SelectItem("Archivos", "Archivos");
                    items[2] = new SelectItem("Direcciones", "Direcciones");
                    items[3] = new SelectItem("Personas", "Personas");
                    items[4] = new SelectItem("Profesionales", tabla);
                    break;
                default:
                    items = new SelectItem[1];
                    items[0] = new SelectItem(tabla, tabla);
                    break;
            }
        }
        return items;
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

    /**
     * @return the especialidad
     */
    public Parametros getEspecialidad() {
        return especialidad;
    }

    /**
     * @param especialidad the especialidad to set
     */
    public void setEspecialidad(Parametros especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * @return the clasificador
     */
    public String getClasificador() {
        return clasificador;
    }

    /**
     * @param clasificador the clasificador to set
     */
    public void setClasificador(String clasificador) {
        this.clasificador = clasificador;
    }

    /**
     * @return the profesional
     */
    public Profesionales getProfesional() {
        return profesional;
    }

    /**
     * @param profesional the profesional to set
     */
    public void setProfesional(Profesionales profesional) {
        this.profesional = profesional;
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
}
