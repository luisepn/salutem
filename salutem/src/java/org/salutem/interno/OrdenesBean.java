package org.salutem.interno;

import org.salutem.seguridad.SeguridadBean;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.salutem.controladores.OrdenesFacade;
import org.salutem.entidades.Instituciones;
import org.salutem.entidades.Ordenes;
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

@Named("salutemOrdenes")
@ViewScoped
public class OrdenesBean implements Serializable, IMantenimiento {

    @Inject
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private Formulario formularioSeleccion = new Formulario();
    private LazyDataModel<Ordenes> ordenes;
    private Ordenes orden;
    private int laboratorioId;
    private int estado = -1;
    private Perfiles perfil;

    private Instituciones institucion;

    private Date inicioRegistro;
    private Date finRegistro;
    private Date inicioEnvio;
    private Date finEnvio;
    private Date inicioRecepcion;
    private Date finRecepcion;
    private Date inicioEntrega;
    private Date finEntrega;
    private int totalSeleccionados;

    private Instituciones laboratorio;
    private String descripcion;
    private int estadoSiguiente = 0;
    private Map parameters;
    private String where;

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
            parameters = new HashMap();
            where = " o.activo=:activo";
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
                } else if (clave.contains("toString")) {
                    estado = Integer.parseInt(valor);
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
            if (inicioRecepcion != null && finRecepcion != null) {
                where += " and o.recepcion between :inicioRecepcion and :finRecepcion";
                parameters.put("inicioRecepcion", inicioRecepcion);
                parameters.put("finRecepcion", finRecepcion);
            }
            if (inicioEntrega != null && finEntrega != null) {
                where += " and o.entrega between :inicioEntrega and :finEntrega";
                parameters.put("inicioEntrega", inicioEntrega);
                parameters.put("finEntrega", finEntrega);
            }

            switch (estado) {
                case 0:
                    where += " and o.registro is not null and o.envio is null and o.recepcion is null and o.entrega is null";
                    break;
                case 1:
                    where += " and o.registro is not null and o.envio is not null and o.recepcion is null and o.entrega is null";
                    break;
                case 2:
                    where += " and o.registro is not null and o.envio is not null and o.recepcion is not null and o.entrega is null";
                    break;
                case 3:
                    where += " and o.registro is not null and o.envio is not null and o.recepcion is not null and o.entrega is not null";
                    break;
            }

            int total = ejbOrdenes.contar(where, parameters);

            Map p = new HashMap();
            p.put("where", where);
            p.put("parameters", parameters);

            totalSeleccionados = ejbOrdenes.contarSeleccionados(p);

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
        } catch (ExcepcionDeConsulta | ExcepcionDeActualizacion ex) {
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
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        orden = new Ordenes();
        orden.setActivo(Boolean.TRUE);
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        orden = (Ordenes) ordenes.getRowData();

//        Date fecha = null;
//
//        switch (orden.getEstado()) {
//            case 0:
//                fecha = orden.getRegistro();
//                break;
//            case 1:
//                fecha = orden.getEnvio();
//                break;
//            case 2:
//                fecha = orden.getRecepcion();
//                break;
//            case 3:
//                fecha = orden.getEntrega();
//                break;
//        }
//
//        if (fecha != null && validarFecha(fecha)) {
//            Mensajes.advertencia("No se puede editar ordenes con fechas menores a la de hoy");
//            return null;
//        }
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        orden = (Ordenes) ordenes.getRowData();

        Date fecha = null;

        switch (orden.getEstado()) {
            case 0:
                fecha = orden.getRegistro();
                break;
            case 1:
                fecha = orden.getEnvio();
                break;
            case 2:
                fecha = orden.getRecepcion();
                break;
            case 3:
                fecha = orden.getEntrega();
                break;
        }

        if (fecha != null && validarFecha(fecha)) {
            Mensajes.advertencia("No se puede eliminar ordenes con fechas menores a la de hoy");
            return null;
        }

        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
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
            orden.setCreado(new Date());
            orden.setCreadopor(seguridadBean.getLogueado().getUserid());
            orden.setActualizado(orden.getCreado());
            orden.setActualizadopor(orden.getCreadopor());
            ejbOrdenes.crear(orden, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(OrdenesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private boolean validarFecha(Date fecha) {
        Calendar t = Calendar.getInstance();
        t.setTime(new Date());
        t.set(Calendar.HOUR_OF_DAY, 0);
        t.set(Calendar.MINUTE, 0);
        t.set(Calendar.SECOND, 0);
        t.set(Calendar.MILLISECOND, 0);

        Calendar f = Calendar.getInstance();
        f.setTime(fecha);
        f.set(Calendar.HOUR_OF_DAY, 0);
        f.set(Calendar.MINUTE, 0);
        f.set(Calendar.SECOND, 0);
        f.set(Calendar.MILLISECOND, 1);

        return f.getTime().before(t.getTime());
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
            orden.setActualizado(new Date());
            orden.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbOrdenes.actualizar(orden, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(OrdenesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbOrdenes.eliminar(orden, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(OrdenesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    public String cancelarSeleccion() {
        formularioSeleccion.cancelar();
        descripcion = null;
        return null;
    }

    public void seleccionar(ValueChangeEvent event) {
        Ordenes o = (Ordenes) ordenes.getRowData();
        o.setSeleccionado((Boolean) event.getNewValue());
        try {
            ejbOrdenes.actualizar(o, null, null);
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(OrdenesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String actualizar(boolean siguiente) {
        switch (estado) {
            case 0:
                estadoSiguiente = 1;
                break;
            case 1:
                estadoSiguiente = siguiente ? 2 : 0;
                break;
            case 2:
                estadoSiguiente = siguiente ? 3 : 1;
                break;
        }

        try {
            Map p = new HashMap();
            p.put("where", where);
            p.put("parameters", parameters);
            laboratorio = ejbOrdenes.traerLaboratorio(p);
        } catch (ExcepcionDeConsulta | ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(OrdenesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formularioSeleccion.insertar();
        return null;
    }

    public String actualizar() {
        if (laboratorio == null) {
            Mensajes.advertencia("Por favor, seleccione un laboratorio");
            return null;
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            Mensajes.advertencia("Por favor, ponga una descripción para la trasacción");
            return null;
        }

        try {
            actualizarEstado();
        } catch (ExcepcionDeConsulta | ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(OrdenesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        cancelarSeleccion();
        return null;
    }

    private void actualizarEstado() throws ExcepcionDeConsulta, ExcepcionDeActualizacion {

        while (ejbOrdenes.contar("o.seleccionado=true and " + where, parameters) > 0) {
            List<Ordenes> lista = ejbOrdenes.buscar("o.seleccionado=true and " + where, parameters, 0, 100);
            for (Ordenes o : lista) {
                o.setSeleccionado(Boolean.FALSE);
                o.setActualizado(new Date());
                o.setActualizadopor(seguridadBean.getLogueado().getUserid());
                switch (estadoSiguiente) {
                    case 0:
                        o.setEnvio(null);
                        o.setRecepcion(null);
                        o.setEntrega(null);
                        break;
                    case 1:
                        o.setEnvio(new Date());
                        o.setRecepcion(null);
                        o.setEntrega(null);
                        break;
                    case 2:
                        o.setRecepcion(new Date());
                        o.setEntrega(null);
                        break;
                    case 3:
                        o.setEntrega(new Date());
                        break;
                }
                o.setDescripcion(descripcion);
                o.setLaboratorio(laboratorio);
                ejbOrdenes.actualizar(o, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }
        }
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
     * @return the formularioSeleccion
     */
    public Formulario getFormularioSeleccion() {
        return formularioSeleccion;
    }

    /**
     * @param formularioSeleccion the formularioSeleccion to set
     */
    public void setFormularioSeleccion(Formulario formularioSeleccion) {
        this.formularioSeleccion = formularioSeleccion;
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
     * @return the laboratorioId
     */
    public int getLaboratorioId() {
        return laboratorioId;
    }

    /**
     * @param laboratorioId the laboratorioId to set
     */
    public void setLaboratorioId(int laboratorioId) {
        this.laboratorioId = laboratorioId;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
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
     * @return the inicioRecepcion
     */
    public Date getInicioRecepcion() {
        return inicioRecepcion;
    }

    /**
     * @param inicioRecepcion the inicioRecepcion to set
     */
    public void setInicioRecepcion(Date inicioRecepcion) {
        this.inicioRecepcion = inicioRecepcion;
    }

    /**
     * @return the finRecepcion
     */
    public Date getFinRecepcion() {
        return finRecepcion;
    }

    /**
     * @param finRecepcion the finRecepcion to set
     */
    public void setFinRecepcion(Date finRecepcion) {
        this.finRecepcion = finRecepcion;
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

    /**
     * @return the laboratorio
     */
    public Instituciones getLaboratorio() {
        return laboratorio;
    }

    /**
     * @param laboratorio the laboratorio to set
     */
    public void setLaboratorio(Instituciones laboratorio) {
        this.laboratorio = laboratorio;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the estadoSiguiente
     */
    public int getEstadoSiguiente() {
        return estadoSiguiente;
    }

    /**
     * @param estadoSiguiente the estadoSiguiente to set
     */
    public void setEstadoSiguiente(int estadoSiguiente) {
        this.estadoSiguiente = estadoSiguiente;
    }

    /**
     * @return the totalSeleccionados
     */
    public int getTotalSeleccionados() {
        return totalSeleccionados;
    }

    /**
     * @param totalSeleccionados the totalSeleccionados to set
     */
    public void setTotalSeleccionados(int totalSeleccionados) {
        this.totalSeleccionados = totalSeleccionados;
    }
}
