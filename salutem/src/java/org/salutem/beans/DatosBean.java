package org.salutem.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.faces.view.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.controladores.salutem.CamposFacade;
import org.controladores.salutem.DatosFacade;
import org.entidades.salutem.Archivos;
import org.entidades.salutem.Campos;
import org.entidades.salutem.Datos;
import org.entidades.salutem.Parametros;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.excepciones.salutem.ExcepcionDeEliminacion;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;
import org.utilitarios.salutem.Items;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @fecha Quito, 27 de agosto de 2018
 * @hora 15:03:23
 */
@Named("salutemDatos")
@ViewScoped
public class DatosBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;
    @Inject
    @Any
    private ArchivosBean archivosBean;

    private Formulario formulario = new Formulario();
    private Formulario formularioConfirmacion = new Formulario();

    private String clasificador;
    private Parametros grupo;
    private Integer identificador;
    private List<Datos> datos;

    @EJB
    private CamposFacade ejbCampos;
    @EJB
    private DatosFacade ejbDatos;

    public DatosBean() {
    }

    public void iniciar(String clasificador, Parametros grupo, Integer identificador, Formulario formulario) {
        this.clasificador = clasificador;
        this.grupo = grupo;
        this.identificador = identificador;
        this.formulario = formulario;
        buscar();
        crear();
    }

    public String crear() {
        try {
            if (!datos.isEmpty()) {
                return null;
            }

            List<Campos> campos = ejbCampos.traerCampos(clasificador, grupo);
            if (campos == null || campos.isEmpty()) {
//                Mensajes.advertencia("No existen campos registrados para el clasificador y grupo seleccionados ");
                return null;
            }

            for (Campos c : campos) {
                grabarDato(c, new Datos(), true);
            }
            buscar();
        } catch (ExcepcionDeConsulta | ExcepcionDeCreacion | ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private void grabarDato(Campos c, Datos d, Boolean nuevo) throws ExcepcionDeCreacion, ExcepcionDeConsulta, ExcepcionDeActualizacion {
        if (nuevo) {
            d = new Datos();
        }
        d.setClasificador(clasificador);
        d.setIdentificador(identificador);
        d.setCodigo(c.getCodigo());
        d.setNombre(c.getNombre());
        d.setDescripcion(c.getDescripcion());
        d.setOrdengrupo(c.getGrupo().getId());
        d.setGrupo(c.getGrupo().getNombre());
        d.setTipo(c.getTipo());
        d.setOpciones(ejbCampos.buscarJsonb("opciones", c.getId()));
        d.setRequerido(c.getRequerido());
        d.setActivo(c.getActivo());
        if (nuevo) {
            d.setCreado(new Date());
            d.setCreadopor(seguridadBean.getLogueado().getUserid());
            d.setActualizado(d.getCreado());
            d.setActualizadopor(d.getCreadopor());
            ejbDatos.crear(d, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } else {
            d.setActualizado(new Date());
            d.setActualizadopor(seguridadBean.getLogueado().getUserid());
            ejbDatos.actualizar(d, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        }
        if (c.getTipo().getCodigo().equals("ONE")
                || c.getTipo().getCodigo().equals("MANY")
                || c.getTipo().getCodigo().equals("LIST")) {
            ejbDatos.actualizarJsonb("opciones", d.getOpciones(), d.getId());
        }
    }

    public void buscar() {
        try {
            datos = ejbDatos.traerDatos(clasificador, grupo.getNombre(), identificador);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Datos> traerDatos(String clasificador, String grupo, Integer identificador) throws ExcepcionDeConsulta {
        return ejbDatos.traerDatos(clasificador, grupo, identificador);
    }

    public String pedirActualizacion() {
        formularioConfirmacion.insertar();
        return null;
    }

    public String actualizar(Boolean borrar) {
        try {
            if (borrar) {
                List<Datos> ld = ejbDatos.traerDatos(clasificador, grupo.getNombre(), identificador);
                for (Datos d : ld) {
                    ejbDatos.eliminar(d, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                }
                crear();
            } else {
                List<Campos> lc = ejbCampos.traerCampos(clasificador, grupo);
                for (Campos c : lc) {
                    Datos d = ejbDatos.traerDato(clasificador, grupo.getNombre(), identificador, c.getCodigo());
                    grabarDato(c, d, d == null);
                }
            }
            datos = ejbDatos.traerDatos(clasificador, grupo.getNombre(), identificador);
            buscar();
            formularioConfirmacion.cancelar();
        } catch (ExcepcionDeEliminacion | ExcepcionDeConsulta | ExcepcionDeCreacion | ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private boolean validar() {
        for (Datos d : datos) {
            if (d.getRequerido() != null && d.getRequerido()) {
                switch (d.getTipo().getCodigo()) {
                    case "INTEGER":
                        if (d.getEntero() == null) {
                            Mensajes.advertencia("Item " + d.getCodigo() + " es requerido");
                            return true;
                        }
                        break;
                    case "DOUBLE":
                        if (d.getDecimal() == null) {
                            Mensajes.advertencia("Item " + d.getCodigo() + " es requerido");
                            return true;
                        }
                        break;
                    case "TEXT":
                        if (d.getTexto() == null) {
                            Mensajes.advertencia("Item " + d.getCodigo() + " es requerido");
                            return true;
                        }
                        break;
                    case "DATE":
                        if (d.getFecha() == null) {
                            Mensajes.advertencia("Item " + d.getCodigo() + " es requerido");
                            return true;
                        }
                        break;
                    case "TIME":
                        if (d.getHora() == null) {
                            Mensajes.advertencia("Item " + d.getCodigo() + " es requerido");
                            return true;
                        }
                        break;
                    case "DATETIME":
                        if (d.getFechahora() == null) {
                            Mensajes.advertencia("Item " + d.getCodigo() + " es requerido");
                            return true;
                        }
                        break;
                    case "LIST":
                        if (d.getOneSeleccion() == null) {
                            Mensajes.advertencia("Item " + d.getCodigo() + " es requerido");
                            return true;
                        }
                        break;
                    case "FILE":
                        if (d.getArchivo() == null) {
                            Mensajes.advertencia("Item " + d.getCodigo() + " es requerido");
                            return true;
                        }
                        break;
                    case "ONE":
                    case "MANY":
                        if (d.getManySeleccion() == null || d.getManySeleccion().isEmpty()) {
                            Mensajes.advertencia("Item " + d.getCodigo() + " es requerido");
                            return true;
                        }
                        break;
                }
            }
        }
        return false;
    }

    public String grabar() {
        if (validar()) {
            return null;
        }
        try {
            for (Datos d : datos) {
                switch (d.getTipo().getCodigo()) {
                    case "ONE":
                    case "MANY":
                    case "LIST":
                        List<Items> opciones = d.getItemListFromJson(false);
                        if (!opciones.isEmpty()) {
                            List<Items> seleccion = new LinkedList<>();
                            try {
                                if (d.getTipo().getCodigo().equals("LIST")) {
                                    if (d.getOneSeleccion() != null) {
                                        seleccion.add(opciones.get(Integer.parseInt(d.getOneSeleccion())));
                                    }
                                } else {
                                    for (String s : d.getManySeleccion()) {
                                        seleccion.add(opciones.get(Integer.parseInt(s)));
                                    }
                                }
                            } catch (NumberFormatException ex) {
                            }
                            ejbDatos.actualizarJsonb("seleccion", seleccion.isEmpty() ? null : d.getJsonFromList(seleccion).toString(), d.getId());
                        }
                        d.setSeleccion(ejbDatos.buscarJsonb("seleccion", d.getId()));
                        ejbDatos.actualizar(d, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                        break;
                    case "FILE":
                        break;
                    default:
                        ejbDatos.actualizar(d, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                        break;
                }
            }
            Mensajes.informacion("¡Datos grabados con éxito!");
        } catch (ExcepcionDeActualizacion | ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ok";
    }

    public String borrar() {
        try {
            List<Datos> datosBorrar = ejbDatos.traerDatosTodos(clasificador, grupo.getNombre(), identificador);
            for (Datos d : datosBorrar) {
                ejbDatos.eliminar(d, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }
            Mensajes.informacion("¡Datos removidos con éxito!");
        } catch (ExcepcionDeConsulta | ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public SelectItem[] traerOpciones(Datos d) {
        try {
            d.setOpciones(ejbDatos.buscarJsonb("opciones", d.getId()));
            return CombosBean.getSelectItems(d.getItemListFromJson(false), "op", d.getTipo().getCodigo().equals("LIST"));
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String traerSeleccion(Datos d) {
        String retorno = "";
        try {
            d.setOpciones(ejbDatos.buscarJsonb("seleccion", d.getId()));
            List<Items> seleccion = d.getItemListFromJson(true);
            for (Items i : seleccion) {
                retorno += " - " + i.getValor() + "\n";
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public String getMensajeActualizacion() {
        return "Seleccione una opción cuidadosamente.\n"
                + "Si desea sincronizar la lista de datos actual con los campos parametrizados presione 'Copiar nuevos'.\n"
                + "Si desea eliminar la lista de datos y volver a copiar los campos parametrizados presione 'Eliminar y copiar'.";
    }

    public void colocarFichero(FileEntryEvent e) {
        int index = formulario.getFila().getRowIndex();
        Datos d = datos.get(index);
        d.setVerSubir(false);
        Archivos a = d.getArchivo();
        archivosBean.iniciar(getNombreTabla(), d.getId(), a != null ? a : new Archivos());
        archivosBean.colocarFichero(e);
        d.setArchivo(archivosBean.getArchivo());
        try {
            ejbDatos.actualizar(d, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            datos.set(index, d);
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(DatosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNombreTabla() {
        return Datos.class.getSimpleName();
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
     * @return the archivosBean
     */
    public ArchivosBean getArchivosBean() {
        return archivosBean;
    }

    /**
     * @param archivosBean the archivosBean to set
     */
    public void setArchivosBean(ArchivosBean archivosBean) {
        this.archivosBean = archivosBean;
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
     * @return the formularioConfirmacion
     */
    public Formulario getFormularioConfirmacion() {
        return formularioConfirmacion;
    }

    /**
     * @param formularioConfirmacion the formularioConfirmacion to set
     */
    public void setFormularioConfirmacion(Formulario formularioConfirmacion) {
        this.formularioConfirmacion = formularioConfirmacion;
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
     * @return the grupo
     */
    public Parametros getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Parametros grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the identificador
     */
    public Integer getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the datos
     */
    public List<Datos> getDatos() {
        return datos;
    }

    /**
     * @param datos the datos to set
     */
    public void setDatos(List<Datos> datos) {
        this.datos = datos;
    }

}
