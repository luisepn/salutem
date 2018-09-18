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
import org.controladores.salutem.OrdenesFacade;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Ordenes;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

@Named("salutemOrdenes")
@ViewScoped
public class OrdenesBean implements Serializable, IMantenimiento {

    @Inject
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Ordenes> ordenes;
    private Ordenes orden;
    private int laboratorio;
    private Perfiles perfil;

    private Instituciones institucion;

    private Date inicioRegistro;
    private Date finRegistro;
    private Date inicioEnvio;
    private Date finEnvio;
    private Date inicioEntrega;
    private Date finEntrega;

    @EJB
    private OrdenesFacade ejbOrdenes;

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Ordenes");
        institucion = seguridadBean.getInstitucion();
    }

    public OrdenesBean() {
        ordenes = new LazyDataModel<Ordenes>() {
            @Override
            public List<Ordenes> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                }
                return carga(i, i1, scs, map);
            }
        };
    }

    public List<Ordenes> carga(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            Map parameters = new HashMap();
            String where = " o.activo=:activo";
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
            if (institucion != null) {
                where += " and o.formula.atencion.profesional.institucion=:institucion";
                parameters.put("institucion", institucion);
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

            if (inicioRegistro != null && finRegistro != null) {
                where += " and o.registro between :inicioRegistro and :finRegistro";
                parameters.put("inicioRegistro", inicioRegistro);
                parameters.put("finRegistro", finRegistro);
            }
            if (inicioEnvio != null && finEnvio != null) {
                where += " and o.envio between :inicioEnvio and :finEnvio";
                parameters.put("inicioEnvio", inicioEnvio);
                parameters.put("finEnvio", finEnvio);
            }
            if (inicioEntrega != null && finEntrega != null) {
                where += " and o.entrega between :inicioEntrega and :finEntrega";
                parameters.put("inicioEntrega", inicioEntrega);
                parameters.put("finEntrega", finEntrega);
            }

            int total = ejbOrdenes.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            ordenes.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.formula.atencion.profesional.institucion.nombre, o.creado";
            } else {
                order = (seguridadBean.getVerAgrupado() ? "o.formula.atencion.profesional.institucion.nombre," : "") + "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbOrdenes.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(OrdenesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!perfil.getConsulta()) {
            Mensajes.advertencia("No tiene autorización para consultar");
            return null;
        }
        ordenes = new LazyDataModel<Ordenes>() {
            @Override
            public List<Ordenes> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return carga(i, i1, scs, map);
            }
        };
        return null;
    }

    public String getNombreTabla() {
        return Ordenes.class.getSimpleName();
    }

    @Override
    public String crear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String editar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insertar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String grabar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String remover() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String cancelar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     * @return the ordenes
     */
    public LazyDataModel<Ordenes> getOrdenes() {
        return ordenes;
    }

    /**
     * @param ordenes the ordenes to set
     */
    public void setOrdenes(LazyDataModel<Ordenes> ordenes) {
        this.ordenes = ordenes;
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
     * @return the institucion
     */
    public Instituciones getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the orden
     */
    public Ordenes getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Ordenes orden) {
        this.orden = orden;
    }

    /**
     * @return the laboratorio
     */
    public int getLaboratorio() {
        return laboratorio;
    }

    /**
     * @param laboratorio the laboratorio to set
     */
    public void setLaboratorio(int laboratorio) {
        this.laboratorio = laboratorio;
    }

    /**
     * @return the inicioRegistro
     */
    public Date getInicioRegistro() {
        return inicioRegistro;
    }

    /**
     * @param inicioRegistro the inicioRegistro to set
     */
    public void setInicioRegistro(Date inicioRegistro) {
        this.inicioRegistro = inicioRegistro;
    }

    /**
     * @return the finRegistro
     */
    public Date getFinRegistro() {
        return finRegistro;
    }

    /**
     * @param finRegistro the finRegistro to set
     */
    public void setFinRegistro(Date finRegistro) {
        this.finRegistro = finRegistro;
    }

    /**
     * @return the inicioEnvio
     */
    public Date getInicioEnvio() {
        return inicioEnvio;
    }

    /**
     * @param inicioEnvio the inicioEnvio to set
     */
    public void setInicioEnvio(Date inicioEnvio) {
        this.inicioEnvio = inicioEnvio;
    }

    /**
     * @return the finEnvio
     */
    public Date getFinEnvio() {
        return finEnvio;
    }

    /**
     * @param finEnvio the finEnvio to set
     */
    public void setFinEnvio(Date finEnvio) {
        this.finEnvio = finEnvio;
    }

    /**
     * @return the inicioEntrega
     */
    public Date getInicioEntrega() {
        return inicioEntrega;
    }

    /**
     * @param inicioEntrega the inicioEntrega to set
     */
    public void setInicioEntrega(Date inicioEntrega) {
        this.inicioEntrega = inicioEntrega;
    }

    /**
     * @return the finEntrega
     */
    public Date getFinEntrega() {
        return finEntrega;
    }

    /**
     * @param finEntrega the finEntrega to set
     */
    public void setFinEntrega(Date finEntrega) {
        this.finEntrega = finEntrega;
    }
}
