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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.salutem.controladores.ParametrosFacade;
import org.salutem.entidades.Parametros;
import org.salutem.entidades.Perfiles;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.excepciones.ExcepcionDeCreacion;
import org.salutem.excepciones.ExcepcionDeEliminacion;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 19 de Noviembre de 2017, 07:08:01 AM
 */
@Named("salutemParametros")
@ViewScoped
public class ParametrosBean implements Serializable, IMantenimiento {

    @Inject
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Parametros> parametros;
    private Parametros parametro;
    private int maestro;
    private Perfiles perfil;

    @EJB
    private ParametrosFacade ejbParametros;

    public ParametrosBean() {
        parametros = new LazyDataModel<Parametros>() {
            @Override
            public List<Parametros> load(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
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
        perfil = seguridadBean.traerPerfil("Parametros");
    }

    private List<Parametros> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            Map parameters = new HashMap();
            String where = " o.activo=:activo and o.maestro.activo=true";
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
            int total = ejbParametros.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            parametros.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.maestro.nombre, o.codigo";
            } else {
                order = (seguridadBean.getVerAgrupado() ? "o.maestro.nombre," : "") + "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbParametros.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ParametrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        parametros = new LazyDataModel<Parametros>() {
            @Override
            public List<Parametros> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        parametro = new Parametros();
        parametro.setActivo(Boolean.TRUE);
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        parametro = (Parametros) parametros.getRowData();
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        parametro = (Parametros) parametros.getRowData();
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        try {
            if (parametro.getMaestro() == null) {
                Mensajes.advertencia("Es necesario maestro");
                return true;
            }
            if ((parametro.getCodigo() == null) || (parametro.getCodigo().isEmpty())) {
                Mensajes.advertencia("Es necesario código");
                return true;
            }
            if ((parametro.getNombre() == null) || (parametro.getNombre().isEmpty())) {
                Mensajes.advertencia("Es necesario nombre");
                return true;
            }
            if ((parametro.getDescripcion() == null) || (parametro.getDescripcion().isEmpty())) {
                Mensajes.advertencia("Es necesario descripción");
                return true;
            }

            String where = " o.maestro=:maestro and o.codigo=:codigo";
            Map parameters = new HashMap();
            parameters.put("maestro", parametro.getMaestro());
            parameters.put("codigo", parametro.getCodigo());
            if (parametro.getId() != null) {
                where += " and o.id!=:id";
                parameters.put("id", parametro.getId());
            }
            if (ejbParametros.contar(where, parameters) > 0) {
                Mensajes.advertencia("No se permiten maestros con código duplicado");
                return true;
            }

        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ParametrosBean.class.getName()).log(Level.SEVERE, null, ex);
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
            parametro.setCreado(new Date());
            parametro.setCreadopor(seguridadBean.getLogueado().getUserid());
            parametro.setActualizado(parametro.getCreado());
            parametro.setActualizadopor(parametro.getCreadopor());
            ejbParametros.crear(parametro, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ParametrosBean.class.getName()).log(Level.SEVERE, null, ex);
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
            parametro.setActualizado(new Date());
            parametro.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbParametros.actualizar(parametro, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ParametrosBean.class.getName()).log(Level.SEVERE, null, ex);
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
            ejbParametros.eliminar(parametro, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ParametrosBean.class.getName()).log(Level.SEVERE, null, ex);
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
        return Parametros.class.getSimpleName();
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
    public LazyDataModel<Parametros> getParametros() {
        return parametros;
    }

    /**
     * @return the parametro
     */
    public Parametros getParametro() {
        return parametro;
    }

    /**
     * @return the maestro
     */
    public int getMaestro() {
        return maestro;
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
    public void setParametros(LazyDataModel<Parametros> codigos) {
        this.parametros = codigos;
    }

    /**
     * @param parametro the parametro to set
     */
    public void setParametro(Parametros parametro) {
        this.parametro = parametro;
    }

    /**
     * @param maestro the maestro to set
     */
    public void setMaestro(int maestro) {
        this.maestro = maestro;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

}
