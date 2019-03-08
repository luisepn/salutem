package org.salutem.general;

import java.io.File;
import java.io.IOException;
import org.salutem.seguridad.SeguridadBean;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.salutem.controladores.InstitucionesFacade;
import org.salutem.entidades.Instituciones;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;

@Named("salutemMiInstitucion")
@ViewScoped
public class MiInstitucionBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;

    private Instituciones institucion;
    private Formulario formulario = new Formulario();

    @EJB
    private InstitucionesFacade ejbInstituciones;

    public MiInstitucionBean() {
    }

    @PostConstruct
    private void iniciar() {
        if (seguridadBean.getLogueado() == null) {
            seguridadBean.logout();
        }
        institucion = seguridadBean.getUsuario().getInstitucion();
    }

    public String ingresar() {
        if (seguridadBean.getUsuario().getInstitucion() == null) {
            return null;
        }
        if (!seguridadBean.getUsuario().getGrupo().getCodigo().equals("GA")) {
            Mensajes.advertencia("Opción Reservada para Administradores Institucionales");
            return null;
        }
        return "MiInstitucion.salutem?faces-redirect=true&p=0";
    }

    public String editar() {
        formulario.editar();
        return null;
    }

    public String cancelar() {
        try {
            institucion = ejbInstituciones.buscar(institucion.getId());
            formulario.cancelar();
        } catch (ExcepcionDeConsulta ex) {
            Logger.getLogger(MiInstitucionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String grabar() {
        try {
            ejbInstituciones.actualizar(institucion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeActualizacion ex) {
            Logger.getLogger(MiInstitucionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean validar() {
        if ((institucion.getNombre() == null) || (institucion.getNombre().trim().isEmpty())) {
            Mensajes.advertencia("Nombre de la institución es necesario");
            return true;
        }
        if ((institucion.getPrimaria() == null) || (institucion.getPrimaria().trim().isEmpty())) {
            Mensajes.advertencia("Calle primaria es necesaria");
            return true;
        }
        if ((institucion.getNumero() == null) || (institucion.getNumero().trim().isEmpty())) {
            Mensajes.advertencia("Número es necesario");
            return true;
        }
        if ((institucion.getSecundaria() == null) || (institucion.getSecundaria().trim().isEmpty())) {
            Mensajes.advertencia("Calle secundaria es necesaria");
            return true;
        }
        if ((institucion.getFijo() == null) || (institucion.getFijo().trim().isEmpty())) {
            Mensajes.advertencia("Número telefónico es necesario");
            return true;
        }

        if (institucion.getFijo() != null && !institucion.getFijo().isEmpty()) {

            if (!institucion.getFijo().toLowerCase().contains("ext")) {
                try {
                    Long l = Long.parseLong(institucion.getFijo().trim());
                } catch (NumberFormatException e) {
                    Mensajes.advertencia("Ingrese un número de 9 dígitos para teléfono fijo");
                    return true;
                }

                String fijo = institucion.getFijo().trim();
                if (fijo.length() != 9) {
                    Mensajes.advertencia("Un teléfono fijo debe tener 9 números");
                    return true;
                }

                String primerosDigitos = fijo.substring(0, 2);

                switch (primerosDigitos) {
                    case "02":
                    case "03":
                    case "04":
                    case "06":
                    case "07":
                        break;
                    default:
                        Mensajes.advertencia("Un teléfono fijo no debe tener más de 9 números con prefijos: 02, 03, 04, 05, 06, 07");
                        return true;
                }
            }
        }
        if (institucion.getMovil() != null && !institucion.getMovil().isEmpty()) {
            try {
                Long l = Long.parseLong(institucion.getMovil().trim());
            } catch (NumberFormatException e) {
                Mensajes.advertencia("Ingrese un número de 10 dígitos para teléfono móvil");
                return true;
            }

            String movil = institucion.getMovil().trim();
            if (movil.length() != 10) {
                Mensajes.advertencia("Un teléfono móvil debe tener 10 números");
                return true;
            }

            String primerosDigitos = movil.substring(0, 2);

            switch (primerosDigitos) {
                case "09":
                    break;
                default:
                    Mensajes.advertencia("Un teléfono móvil no debe tener más de 10 números con el prefijo: 09");
                    return true;
            }
        }

        return false;
    }

    public String logotipoListener(FileEntryEvent e) {
        FileEntry fileEntry = (FileEntry) e.getComponent();

        FileEntryResults results = fileEntry.getResults();
        for (FileEntryResults.FileInfo i : results.getFiles()) {
            try {
                File file = i.getFile();
                if (!i.getContentType().contains("image/")) {
                    i.updateStatus(Mensajes.getfileEntryStatus(false, "¡Sólo se aceptan archivos de imágen!"), true);
                } else if (i.getStatus().isSuccess()) {
                    institucion.setLogotipo(Files.readAllBytes(file.toPath()));
                    i.updateStatus(Mensajes.getfileEntryStatus(true, "¡El archivo fue subido con éxito!"), true);
                } else {
                    i.updateStatus(Mensajes.getfileEntryStatus(false, "¡Se ha rechazado el requerimiento porque su tamaño excede el rango permitido: 10 MB!"), true);
                }
            } catch (IOException ex) {
                Mensajes.fatal(ex.getMessage());
                Logger.getLogger(MiInstitucionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String getNombreTabla() {
        return Instituciones.class.getSimpleName();
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
