package org.salutem.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.controladores.salutem.CamposFacade;
import org.entidades.salutem.Campos;
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
import org.utilitarios.salutem.Items;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 21 de Agosto de 2018, 16:57:35 AM
 */
@ManagedBean(name = "salutemCampos")
@ViewScoped
public class CamposBean implements Serializable, IMantenimiento {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;
    @ManagedProperty("#{salutemCombos}")
    private CombosBean combosBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Campos> parametros;
    private Campos campo;
    private List<Items> opciones;
    private String clasificador;
    private int grupo;
    private int tipo;
    private Perfiles perfil;

    @EJB
    private CamposFacade ejbCampos;

    public CamposBean() {
        parametros = new LazyDataModel<Campos>() {
            @Override
            public List<Campos> load(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
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
        perfil = seguridadBean.traerPerfil("Campos");
    }

    private List<Campos> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            Map parameters = new HashMap();
            String where = " o.activo=:activo and o.grupo.activo=true";
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
                if (clasificador != null) {
                    combosBean.setClasificador(clasificador);
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
            int total = ejbCampos.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            parametros.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.clasificador, o.grupo.codigo, o.codigo";
            } else {
                order = (seguridadBean.getVerAgrupado() ? "o.clasificador, o.grupo.codigo," : "") + "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbCampos.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CamposBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        parametros = new LazyDataModel<Campos>() {
            @Override
            public List<Campos> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        campo = new Campos();
        campo.setActivo(Boolean.TRUE);
        opciones = new LinkedList<>();
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        campo = (Campos) parametros.getRowData();
        campo.setOpciones(ejbCampos.traerOpciones(campo.getId()));
        opciones = campo.getOpcionesList();
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        campo = (Campos) parametros.getRowData();
        campo.setOpciones(ejbCampos.traerOpciones(campo.getId()));
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        try {
            if (campo.getClasificador() == null) {
                return true;
            }
            if (campo.getGrupo() == null) {
                Mensajes.advertencia("Es necesario grupo");
                return true;
            }
            if (campo.getTipo() == null) {
                Mensajes.advertencia("Es necesario tipo de dato");
                return true;
            }
            if (campo.getCodigo() == null) {
                Mensajes.advertencia("Es necesario código");
                return true;
            }
            if ((campo.getNombre() == null) || (campo.getNombre().isEmpty())) {
                Mensajes.advertencia("Es necesario nombre");
                return true;
            }
            if (campo.getTipo().getCodigo().equals("ONE") || campo.getTipo().getCodigo().equals("MANY")) {
                if (opciones.isEmpty()) {
                    Mensajes.advertencia("Es necesario opciones");
                    return true;
                }
            }

            String where = " o.grupo=:grupo and o.codigo=:codigo";
            Map parameters = new HashMap();
            parameters.put("grupo", campo.getGrupo());
            parameters.put("codigo", campo.getCodigo());
            if (campo.getId() != null) {
                where += " and o.id!=:id";
                parameters.put("id", campo.getId());
            }
            if (ejbCampos.contar(where, parameters) > 0) {
                Mensajes.advertencia("No se campos con orden duplicado");
                return true;
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CamposBean.class.getName()).log(Level.SEVERE, null, ex);
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
            campo.setCreado(new Date());
            campo.setCreadopor(seguridadBean.getLogueado().getUserid());
            campo.setActualizado(campo.getCreado());
            campo.setActualizadopor(campo.getCreadopor());
            campo.setOpciones(campo.getOpcionesJsonFromList(opciones) != null ? campo.getOpcionesJsonFromList(opciones).toString() : null);
            ejbCampos.crear(campo, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            if (campo.getTipo().getCodigo().equals("ONE") || campo.getTipo().getCodigo().equals("MANY")) {
                ejbCampos.insertarOpciones(campo.getOpciones(), campo.getId());
            }
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CamposBean.class.getName()).log(Level.SEVERE, null, ex);
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
            campo.setActualizado(new Date());
            campo.setActualizadopor(seguridadBean.getLogueado().getUserid());
            campo.setOpciones(campo.getOpcionesJsonFromList(opciones) != null ? campo.getOpcionesJsonFromList(opciones).toString() : null);
            ejbCampos.actualizar(campo, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            if (campo.getTipo().getCodigo().equals("ONE") || campo.getTipo().getCodigo().equals("MANY")) {
                ejbCampos.insertarOpciones(campo.getOpciones(), campo.getId());
            }
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CamposBean.class.getName()).log(Level.SEVERE, null, ex);
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
            ejbCampos.eliminar(campo, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(CamposBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public String nuevaOpcion() {
        int i = 0;
        for (Items item : opciones) {
            item.setClave(i++);
        }
        opciones.add(new Items(opciones.size(), null));
        return null;
    }

    public String borrarOpcion(int index) {
        opciones.remove(index);
        int i = 0;
        for (Items item : opciones) {
            item.setClave(i++);
        }
        return null;
    }

    public SelectItem[] traerOpciones() {
        Campos c = (Campos) parametros.getRowData();
        c.setOpciones(ejbCampos.traerOpciones(c.getId()));
        return CombosBean.getSelectItems(c.getOpcionesList(), "toString", false);
    }

    public String getNombreTabla() {
        return Campos.class.getSimpleName();
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
     * @return the codigos
     */
    public LazyDataModel<Campos> getCampos() {
        return parametros;
    }

    /**
     * @return the campo
     */
    public Campos getCampo() {
        return campo;
    }

    /**
     * @return the grupo
     */
    public int getGrupo() {
        return grupo;
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
     * @param codigos the codigos to set
     */
    public void setCampos(LazyDataModel<Campos> codigos) {
        this.parametros = codigos;
    }

    /**
     * @param campo the campo to set
     */
    public void setCampo(Campos campo) {
        this.campo = campo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the opciones
     */
    public List<Items> getOpciones() {
        return opciones;
    }

    /**
     * @param opciones the opciones to set
     */
    public void setOpciones(List<Items> opciones) {
        this.opciones = opciones;
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

}
