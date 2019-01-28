package org.salutem.seguridad;

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
import org.salutem.controladores.MaestrosFacade;
import org.salutem.entidades.Maestros;
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
 * @since 18 de Noviembre de 2017, 07:23:04 AM
 *
 */
@Named("salutemMaestros")
@ViewScoped
public class MaestrosBean implements Serializable, IMantenimiento {

    @Inject
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Maestros> maestros;
    private Maestros maestro;
    private Perfiles perfil;

    @EJB
    private MaestrosFacade ejbMaestros;

    public MaestrosBean() {
        maestros = new LazyDataModel<Maestros>() {
            @Override
            public List<Maestros> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        perfil = seguridadBean.traerPerfil("Maestros");
    }

    private List<Maestros> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            Map parameters = new HashMap();
            String where = " o.activo=:activo ";
            parameters.put("activo", seguridadBean.getVerActivos());
            for (Map.Entry e : map.entrySet()) {
                String clave = (String) e.getKey();
                String valor = (String) e.getValue();
                where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
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

            int total = ejbMaestros.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            maestros.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.codigo";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbMaestros.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }

        maestros = new LazyDataModel<Maestros>() {
            @Override
            public List<Maestros> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        maestro = new Maestros();
        maestro.setActivo(Boolean.TRUE);
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        maestro = (Maestros) maestros.getRowData();
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        maestro = (Maestros) maestros.getRowData();
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        if ((maestro.getCodigo() == null) || (maestro.getCodigo().isEmpty())) {
            Mensajes.advertencia("Es necesario código");
            return true;
        }
        if ((maestro.getNombre() == null) || (maestro.getNombre().isEmpty())) {
            Mensajes.advertencia("Es necesario nombre");
            return true;
        }
        try {
            String where = "o.codigo=:codigo";
            Map parametros = new HashMap();
            parametros.put("codigo", maestro.getCodigo());
            if (maestro.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", maestro.getId());
            }
            if (ejbMaestros.contar(where, parametros) > 0) {
                Mensajes.advertencia("No se permiten maestros con código duplicado");
                return true;
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
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
            maestro.setCreado(new Date());
            maestro.setCreadopor(seguridadBean.getLogueado().getUserid());
            maestro.setActualizado(maestro.getCreado());
            maestro.setActualizadopor(maestro.getCreadopor());
            ejbMaestros.crear(maestro, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            maestro.setActualizado(new Date());
            maestro.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbMaestros.actualizar(maestro, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbMaestros.eliminar(maestro, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public String getNombreTabla() {
        return Maestros.class.getSimpleName();
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
     * @return the maestros
     */
    public LazyDataModel<Maestros> getMaestros() {
        return maestros;
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
     * @param maestros the maestros to set
     */
    public void setMaestros(LazyDataModel<Maestros> maestros) {
        this.maestros = maestros;
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
