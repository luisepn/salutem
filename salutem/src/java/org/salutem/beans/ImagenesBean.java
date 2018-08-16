package org.salutem.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.controladores.salutem.ArchivosFacade;
import org.entidades.salutem.Archivos;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
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
@ManagedBean(name = "salutemImagenes")
@ViewScoped
public class ImagenesBean implements Serializable {

    @ManagedProperty("#{salutemSeguridad}")
    private SeguridadBean seguridadBean;

    private Archivos archivo;

    @EJB
    private ArchivosFacade ejbArchivos;

    public ImagenesBean() {
    }

    public String ficheroDocumento(FileEntryEvent e) {
        FileEntry fe = (FileEntry) e.getComponent();
        FileEntryResults results = fe.getResults();
        for (FileEntryResults.FileInfo i : results.getFiles()) {
            try {
                File file = i.getFile();
                archivo.setNombre(i.getFileName());
                archivo.setTipo(i.getContentType());
                archivo.setArchivo(Files.readAllBytes(file.toPath()));
            } catch (IOException ex) {
                Mensajes.fatal(ex.getMessage());
                Logger.getLogger(ImagenesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void grabarImagen(String userid, String ip, String clasificador, Integer identificador) {
        try {
            if (archivo.getId() == null) {
                archivo.setActivo(Boolean.TRUE);
                archivo.setCreado(new Date());
                archivo.setCreadopor(userid);
                archivo.setActualizado(archivo.getCreado());
                archivo.setActualizadopor(archivo.getActualizadopor());
                ejbArchivos.crear(archivo, userid, ip);
            }
            archivo.setIdentificador(identificador);
            archivo.setClasificador(clasificador);
            archivo.setRuta(crearFichero(archivo.getId(), archivo.getArchivo(), clasificador));
            archivo.setActualizado(new Date());
            archivo.setActualizadopor(userid);
            ejbArchivos.actualizar(archivo, userid, ip);
        } catch (ExcepcionDeCreacion | ExcepcionDeActualizacion | ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ImagenesBean.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ImagenesBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public Recurso traerImagen() {
        try {
            return new Recurso(Files.readAllBytes(Paths.get(archivo.getRuta() != null ? archivo.getRuta() : "")));
        } catch (IOException ex) {
            Mensajes.fatal("El archivo no existe en la ruta " + (archivo.getRuta() != null ? archivo.getRuta() : ""));
            return null;
        }
    }

    public byte[] traerImagen(String ruta) {
        try {
            if (ruta == null) {
                return null;
            }
            return Files.readAllBytes(Paths.get(ruta));
        } catch (IOException ex) {
            Mensajes.fatal("El archivo no existe en la ruta " + ruta);
            return null;
        }
    }

    public Archivos getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivos archivo) {
        this.archivo = archivo;
        this.archivo.setArchivo(traerImagen(archivo.getRuta()));
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

}
