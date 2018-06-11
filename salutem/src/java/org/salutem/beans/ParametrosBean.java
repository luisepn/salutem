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
import org.controladores.salutem.ParametrosFacade;
import org.entidades.salutem.Parametros;
import org.entidades.salutem.Maestros;
import org.entidades.salutem.Perfiles;
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
 * @since 19 de Noviembre de 2017, 07:08:01 AM
 */
@ManagedBean(name = "salutemParametros")
@ViewScoped
public class ParametrosBean implements Serializable, IMantenimiento {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Parametros> parametros;
    private Parametros parametro;
    private Maestros maestro;
    private Perfiles perfil;

    @EJB
    private ParametrosFacade ejbParametros;

    public ParametrosBean() {
        parametros = new LazyDataModel<Parametros>() {
            @Override
            public List<Parametros> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return null;
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
            if (maestro == null) {
                parametros.setRowCount(0);
                formulario.setTotal(0);
                return null;
            }
            Map parameters = new HashMap();
            parameters.put("maestro", maestro);
            String where = " o.activo=true and o.maestro=:maestro";
            for (Map.Entry e : map.entrySet()) {
                String clave = (String) e.getKey();
                String valor = (String) e.getValue();
                where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
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
                order = "o.nombre";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
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
        if (maestro == null) {
            Mensajes.advertencia("Seleccione un maestro primero");
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
        if (maestro == null) {
            Mensajes.advertencia("Seleccione una tabla maestra primero");
            return null;
        }
        parametro = new Parametros();
        parametro.setMaestro(maestro);
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
        if (formulario.isCrear()) {
            try {
                Map parameters = new HashMap();
                parameters.put("codigo", maestro.getCodigo());
                if (ejbParametros.contar("o.activo=true and o.codigo=:codigo", parameters) > 0) {
                    Mensajes.advertencia("No se permiten parámetros con código duplicado");
                    return true;
                }
            } catch (ExcepcionDeConsulta ex) {
                Logger.getLogger(ParametrosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            Map parameters = new HashMap();
            parameters.put("codigo", parametro.getCodigo());
            List<Parametros> lp = ejbParametros.buscar("o.codigo=:codigo", parameters);
            if (lp.isEmpty()) {
                ejbParametros.crear(parametro, seguridadBean.getLogueado().getUserid());
            } else {
                Parametros p = lp.get(0);
                p.setActivo(Boolean.TRUE);
                p.setNombre(parametro.getNombre());
                p.setMaestro(parametro.getMaestro());
                p.setDescripcion(parametro.getDescripcion());
                ejbParametros.actualizar(p, seguridadBean.getLogueado().getUserid());
            }

        } catch (ExcepcionDeCreacion | ExcepcionDeConsulta | ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ParametrosBean.class.getName()).log(Level.SEVERE, null, ex);
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
            ejbParametros.actualizar(parametro, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ParametrosBean.class.getName()).log(Level.SEVERE, null, ex);
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
            parametro.setActivo(Boolean.FALSE);
            ejbParametros.actualizar(parametro, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ParametrosBean.class.getName()).log(Level.SEVERE, null, ex);
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
    public Maestros getMaestro() {
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
    public void setMaestro(Maestros maestro) {
        this.maestro = maestro;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

}
