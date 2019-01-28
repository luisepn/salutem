package org.salutem.historial;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.salutem.controladores.PersonasFacade;
import org.salutemlogs.controladores.HistorialFacade;
import org.salutemlogs.entidades.Historial;
import org.salutem.entidades.Perfiles;
import org.salutemlogs.excepciones.ExcepcionDeConsulta;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.general.CombosBean;
import org.salutem.seguridad.SeguridadBean;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 18 de Noviembre de 2017, 07:23:04 AM
 *
 */
@Named("salutemHistorial")
@ViewScoped
public class HistorialBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;
    @Inject
    @Any
    private CombosBean combosBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Historial> lista;
    private Perfiles perfil;
    private String titulo;

    private Date fechaInicio;
    private Date fechaFin;
    private String usuario;
    private Character operacion;

    private String tabla;
    private Integer registro;

    private String tablaAuxiliar = "A";

    @EJB
    private HistorialFacade ejbHistorial;
    @EJB
    private PersonasFacade ejbTransacciones;

    public HistorialBean() {
        lista = new LazyDataModel<Historial>() {
            @Override
            public List<Historial> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return null;
            }
        };
    }

    @PostConstruct
    public void activar() {
        perfil = seguridadBean.traerPerfil("Historial");
    }

    public List<Historial> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            Integer registroAuxiliar = null;
            Map parameters = new HashMap();
            String where = " o.id is not null";

            for (Map.Entry e : map.entrySet()) {
                String clave = (String) e.getKey();
                String valor = (String) e.getValue();

                if (clave.contains("registro")) {
                    try {
                        registroAuxiliar = Integer.parseInt(valor);
                    } catch (NumberFormatException ex) {
                        Mensajes.error("En la columna ID ingrese únicamente números " + ex.getMessage());
                        registroAuxiliar = null;
                    }
                } else if (clave.contains("operacion")) {
                    if (valor.equals("A")) {
                        where += " and o.operacion is not null";
                    } else {
                        where += " and o.operacion=:operacion";
                        parameters.put("operacion", valor);
                    }
                } else if (clave.contains("tabla")) {
                    tablaAuxiliar = valor;
                } else if (clave.contains("anterior") || clave.contains("nuevo")) {
                    if (valor.trim().contains(" in ")) {
                        if (valor.trim().matches("'[^ ]*'\\ [><!=^like^ilike^not like^not ilike^in^not in]*\\ \\([^ŋ]*\\)")) {
                            where += " and " + clave + "->>" + valor;
                        }
                    } else {
                        if (valor.trim().matches("'[^ ]*'\\ [><!=^like^ilike^not like^not ilike^in^not in]*\\ '[^ŋ]*'")) {
                            where += " and " + clave + "->>" + valor;
                        }
                    }
                } else {
                    where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                    parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
                }
            }
            if (tabla != null && registro != null) {

                Map param;

                switch (tabla) {
                    case "Atenciones":
                        param = new HashMap();
                        param.put("clasificador", tabla);
                        param.put("identificador", registro);
                        List<Integer> datos = (List<Integer>) ejbTransacciones.buscar("id", "Datos", "clasificador=:clasificador and identificador=:identificador", param, false);

                        param = new HashMap();
                        param.put("atencion", registro);
                        Integer formula = (int) ejbTransacciones.buscar("id", "Formulas", "atencion=:atencion", param, true);
                        List<Integer> prescripciones = (List<Integer>) ejbTransacciones.buscar("id", "Prescripciones", "atencion=:atencion", param, false);

                        param = new HashMap();
                        param.put("formula", formula);
                        Integer orden = (int) ejbTransacciones.buscar("id", "Ordenes", "formula=:formula", param, true);

                        switch (tablaAuxiliar) {
                            case "Atenciones":
                                where += " and o.tabla=:tabla and o.registro=:registro";
                                parameters.put("tabla", tablaAuxiliar);
                                parameters.put("registro", registro);
                                break;
                            case "Datos":
                                where += " and o.tabla=:tabla and o.registro in :datos";
                                parameters.put("tabla", tablaAuxiliar);
                                parameters.put("datos", datos);
                                break;
                            case "Formulas":
                                where += " and o.tabla=:tabla and o.registro=:formula";
                                parameters.put("tabla", tablaAuxiliar);
                                parameters.put("formula", formula);
                                break;
                            case "Ordenes":
                                where += " and o.tabla=:tabla and o.registro=:orden";
                                parameters.put("tabla", tablaAuxiliar);
                                parameters.put("orden", orden);
                                break;
                            case "Prescripciones":
                                where += " and o.tabla=:tabla and o.registro in :prescripciones";
                                parameters.put("tabla", tablaAuxiliar);
                                parameters.put("prescripciones", prescripciones);
                                break;
                            case "A":
                                where += " and ("
                                        + "(o.tabla=:tabla and o.registro=:registro) or "
                                        + "(o.tabla='Datos' and o.registro in :datos) or "
                                        + "(o.tabla='Formulas' and o.registro=:formula) or "
                                        + "(o.tabla='Ordenes' and o.registro=:orden) or "
                                        + "(o.tabla='Prescripciones' and o.registro in :prescripciones)"
                                        + ")";
                                parameters.put("tabla", tabla);
                                parameters.put("registro", registro);
                                parameters.put("datos", datos);
                                parameters.put("formula", formula);
                                parameters.put("orden", orden);
                                parameters.put("prescripciones", prescripciones);
                                break;
                        }
                        break;
                    case "Pacientes":
                    case "Profesionales":
                        param = new HashMap();
                        param.put("id", registro);
                        int persona = (int) ejbTransacciones.buscar("persona", tabla, "id=:id", param, true);
                        param.put("id", persona);

                        switch (tablaAuxiliar) {
                            case "Pacientes":
                            case "Profesionales":
                                where += " and o.tabla=:tabla and o.registro=:registro";
                                parameters.put("tabla", tabla);
                                parameters.put("registro", registro);
                                break;
                            case "Personas":
                                where += " and o.tabla=:tabla and o.registro=:registro";
                                parameters.put("tabla", tablaAuxiliar);
                                parameters.put("registro", persona);
                                break;
                            case "A":
                                where += " and ("
                                        + "(o.tabla=:tabla and o.registro=:registro) or "
                                        + "(o.tabla='Personas' and o.registro=:persona)"
                                        + ")";
                                parameters.put("tabla", tabla);
                                parameters.put("registro", registro);
                                parameters.put("persona", persona);
                                break;
                        }

                        break;
                    default:
                        where += " and o.tabla=:tabla and o.registro=:registro";
                        parameters.put("tabla", tabla);
                        parameters.put("registro", registro);
                        break;
                }

            } else {
                if (tablaAuxiliar.equals("A")) {
                    where += " and o.tabla is not null";
                } else {
                    where += " and o.tabla=:tabla";
                    parameters.put("tabla", tablaAuxiliar);
                }
            }

            if (registroAuxiliar != null) {
                where += " and o.registro=:registroAuxiliar";
                parameters.put("registroAuxiliar", registroAuxiliar);
            }

            if (fechaInicio != null && fechaFin != null) {
                where += " and o.fecha between :fechainicio and :fechafin";
                parameters.put("fechainicio", fechaInicio);
                parameters.put("fechafin", fechaFin);
            }

            int total = (int) ejbHistorial.buscar(where, parameters, null, null, null, true);
            getFormulario().setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            lista.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.fecha desc";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return (List<Historial>) ejbHistorial.buscar(where, parameters, order, i, endIndex, false);
        } catch (ExcepcionDeConsulta | org.salutem.excepciones.ExcepcionDeConsulta ex) {
            Logger.getLogger(HistorialBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String buscar() {
        if (fechaInicio == null || fechaFin == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            fechaInicio = calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            fechaFin = calendar.getTime();
        }
        combosBean.setTabla(null);
        lista = new LazyDataModel<Historial>() {
            @Override
            public List<Historial> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return cargar(i, i1, scs, map);
            }
        };
        return null;
    }

    public String buscar(String tabla, Integer registro) {
        titulo = "Tabla = " + tabla + "; ID = " + registro;
        this.tabla = tabla;
        combosBean.setTabla(tabla);
        this.registro = registro;
        lista = new LazyDataModel<Historial>() {
            @Override
            public List<Historial> load(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
                return cargar(i, pageSize, scs, map);
            }
        };
        formulario.insertar();
        return null;
    }

    public String getAyudaCampos() {
        return "Campos (Puede utilizar los operadores: '=', '!=', '>', '>=', '<='; y las instrucciones: like, ilike, in, not in)";
    }

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @return the lista
     */
    public LazyDataModel<Historial> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(LazyDataModel<Historial> lista) {
        this.lista = lista;
    }

    /**
     * @return the perfil
     */
    public Perfiles getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the operacion
     */
    public Character getOperacion() {
        return operacion;
    }

    /**
     * @param operacion the operacion to set
     */
    public void setOperacion(Character operacion) {
        this.operacion = operacion;
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

    /**
     * @return the combosBean
     */
    public CombosBean getCombosBean() {
        return combosBean;
    }

    /**
     * @param combosBean the combosBean to set
     */
    public void setCombosBean(CombosBean combosBean) {
        this.combosBean = combosBean;
    }

    /**
     * @return the tablaAuxiliar
     */
    public String getTablaAuxiliar() {
        return tablaAuxiliar;
    }

    /**
     * @param tablaAuxiliar the tablaAuxiliar to set
     */
    public void setTablaAuxiliar(String tablaAuxiliar) {
        this.tablaAuxiliar = tablaAuxiliar;
    }

}
