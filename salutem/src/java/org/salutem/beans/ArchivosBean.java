package org.salutem.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.salutem.controladores.ArchivosFacade;
import org.salutem.entidades.Archivos;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.excepciones.ExcepcionDeCreacion;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.icefaces.ace.component.fileentry.FileEntryStatus;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;
import org.salutem.utilitarios.Recurso;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @fecha Quito, 19 de diciembre de 2017
 * @hora 16:53:15
 * @descripción Este Bean administrará todos los documentos o imagenes que el
 * sistema permita ingresar y almacenadolos en un directorio del sistema de
 * archivos que tenga disponible el servidor de aplicaciones. Dicho directorio
 * puede ser parametrizado Módulo de Seguridad y Parametrización: [Maestro: PG;
 * Parámetro: DIRIMG (parametros)]
 */
@Named("salutemArchivos")
@ViewScoped
public class ArchivosBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;

    private Archivos archivo;
    private String clasificador;
    private Integer identificador;
    private List<Archivos> archivos;
    private Formulario formulario = new Formulario();

    private final String absolutePath = "/tmp/salutem";

    @EJB
    private ArchivosFacade ejbArchivos;

    public ArchivosBean() {
    }

    public void iniciar(String clasificador, Integer identificador) {
        this.clasificador = clasificador;
        this.identificador = identificador;
    }

    public void iniciar(String clasificador, Integer identificador, Formulario formulario) {
        this.clasificador = clasificador;
        this.identificador = identificador;
        this.formulario = formulario;
        buscar();
    }

    public void iniciar(String clasificador, Integer identificador, Archivos archivo) {
        this.clasificador = clasificador;
        this.identificador = identificador;
        this.archivo = archivo;
        if (archivo.existeFichero()) {
            this.archivo.setArchivo(archivo.getArchivo());
        } else {
            if (archivo.getRuta() != null) {
                //Mensajes.fatal("El archivo no existe en la ruta " + archivo.getRuta());
            }
        }
        buscar();
    }

    public String ficheroListener(FileEntryEvent e) {
        FileEntry fileEntry = (FileEntry) e.getComponent();
        fileEntry.setAbsolutePath(absolutePath);
        fileEntry.setMaxFileSize(4 * 1024 * 1024); //4 Mb
        fileEntry.setUseOriginalFilename(true);

        FileEntryResults results = fileEntry.getResults();
        for (FileEntryResults.FileInfo i : results.getFiles()) {
            try {
                File file = i.getFile();
                if (!i.getContentType().contains("image/")) {
                    i.updateStatus(getfileEntryStatus(false, "¡Sólo se aceptan archivos de imágen!"), true);
                } else if (i.getStatus().isSuccess()) {
                    archivo.setNombre(i.getFileName());
                    archivo.setTipo(i.getContentType());
                    archivo.setArchivo(Files.readAllBytes(file.toPath()));
                    archivo.setRuta(file.getAbsolutePath());
                    archivo.setIdentificador(identificador);
                    i.updateStatus(getfileEntryStatus(true, "¡El archivo fue subido con éxito!"), true);
                } else {
                    i.updateStatus(getfileEntryStatus(true, "¡El archivo supera el tamaño máximo definido (4 Mb)!"), true);
                }
            } catch (IOException ex) {
                Mensajes.fatal(ex.getMessage());
                Logger.getLogger(ArchivosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String colocarFichero(FileEntryEvent e) {
        FileEntry fileEntry = (FileEntry) e.getComponent();
        fileEntry.setAbsolutePath(absolutePath);
        fileEntry.setMaxFileSize(100 * 1024 * 1024);//100 Megas
        fileEntry.setUseOriginalFilename(true);

        FileEntryResults results = fileEntry.getResults();
        for (FileEntryResults.FileInfo i : results.getFiles()) {
            try {
                if (i.getStatus().isSuccess()) {
                    File file = i.getFile();
                    archivo.setNombre(i.getFileName());
                    archivo.setTipo(i.getContentType());
                    archivo.setArchivo(Files.readAllBytes(file.toPath()));
                    archivo.setIdentificador(identificador);
                    grabar();
                    i.updateStatus(getfileEntryStatus(true, "¡El archivo fue subido con éxito!"), true);
                } else {
                    i.updateStatus(getfileEntryStatus(true, "¡El archivo supera el tamaño máximo definido (100 Mb)!"), true);
                }
            } catch (IOException ex) {
                Mensajes.fatal(ex.getMessage());
                Logger.getLogger(ArchivosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void actualizarIdentificador(String identificador) {
        try {
            if (archivo.getId() == null) {
                return;
            }
            ejbArchivos.actualizarCampo("identificador", identificador, archivo.getId());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ArchivosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscar() {
        try {
            archivos = ejbArchivos.traerArchivos(clasificador, identificador);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ArchivosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabar() {
        try {
            if (archivo.getId() == null) {
                if (archivo.getArchivo() != null) {
                    archivo.setClasificador(clasificador);
                    archivo.setActivo(Boolean.TRUE);
                    archivo.setCreado(new Date());
                    archivo.setCreadopor(seguridadBean.getLogueado().getUserid());
                    archivo.setActualizado(archivo.getCreado());
                    archivo.setActualizadopor(archivo.getCreadopor());
                    archivo.setRuta(seguridadBean.getDirectorioArchivos() + "/" + clasificador + "/*");
                    ejbArchivos.crear(archivo, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                    ejbArchivos.actualizarCampo("ruta", crearFichero(archivo.getId(), archivo.getArchivo(), clasificador), archivo.getId());
                    archivo.setRuta(archivo.getRuta().replace("*", archivo.getId().toString()));
                }
            } else {
                if (archivo.getArchivo() != null) {
                    archivo.setActualizado(archivo.getCreado());
                    archivo.setActualizadopor(seguridadBean.getLogueado().getUserid());
                    archivo.setRuta(crearFichero(archivo.getId(), archivo.getArchivo(), clasificador));
                    ejbArchivos.actualizar(archivo, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
                }
            }
        } catch (ExcepcionDeCreacion | ExcepcionDeActualizacion | ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ArchivosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String crearFichero(Integer id, byte[] archivo, String directorio) throws ExcepcionDeConsulta {
        if (archivo == null) {
            return null;
        }

        if (seguridadBean.getDirectorioArchivos() != null) {
            try {
                File folder = new File(seguridadBean.getDirectorioArchivos() + "/" + directorio);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                File fichero = new File(folder.getAbsolutePath() + "/" + id);
                fichero.createNewFile();

                try (OutputStream out = new FileOutputStream(fichero.getCanonicalPath())) {
                    out.write(archivo);
                }
                return fichero.getCanonicalPath();
            } catch (IOException ex) {
                Mensajes.fatal(ex.getMessage());
                Logger.getLogger(ArchivosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Mensajes.error("Se necesita un parámetro de sistema:\nMaestro = [PG] Parámetros Generales\nParámetro = [DARCH] Directorio de Archivos\n Especifique en Parámetros la ruta absoluta");
        }
        return null;
    }

    public void borrarFichero(String path) {
        if (path != null) {
            File fichero = new File(path);
            fichero.delete();
        }
    }

    public Recurso traerRecurso(Archivos archivo) {
        if (archivo.existeFichero()) {
            try {
                return new Recurso(Files.readAllBytes(Paths.get(archivo.getRuta() != null ? archivo.getRuta() : "")));
            } catch (IOException ex) {
                Logger.getLogger(ArchivosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Mensajes.fatal("El archivo no existe en la ruta " + (archivo.getRuta() != null ? archivo.getRuta() : ""));
        }
        return null;
    }

    private FileEntryStatus getfileEntryStatus(boolean status, String mensaje) {

        FileEntryStatus retorno = new FileEntryStatus() {
            @Override
            public boolean isSuccess() {
                return status;
            }

            @Override
            public FacesMessage getFacesMessage(FacesContext fc, UIComponent uic, FileEntryResults.FileInfo fi) {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage();

                message.setSeverity(status ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR);
                message.setSummary("!Atención!");
                message.setDetail(mensaje);
                context.addMessage(seguridadBean.getLogueado().getUserid(), message);

                return message;
            }
        };
        return retorno;
    }

    public String traerTextoMilisegundos() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
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
     * @return the archivo
     */
    public Archivos getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(Archivos archivo) {
        this.archivo = archivo;
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
     * @return the archivos
     */
    public List<Archivos> getArchivos() {
        return archivos;
    }

    /**
     * @param archivos the archivos to set
     */
    public void setArchivos(List<Archivos> archivos) {
        this.archivos = archivos;
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

}
