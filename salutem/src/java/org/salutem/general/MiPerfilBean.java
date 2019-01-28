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
import org.salutem.controladores.PersonasFacade;
import org.salutem.entidades.Personas;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;

@Named("salutemMiPerfil")
@ViewScoped
public class MiPerfilBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;

    private Personas persona;
    private Formulario formulario = new Formulario();

    @EJB
    private PersonasFacade ejbPersonas;

    public MiPerfilBean() {
    }

    @PostConstruct
    private void iniciar() {
        if (seguridadBean.getLogueado() == null) {
            seguridadBean.logout();
        }
        persona = seguridadBean.getLogueado();
    }

    public String ingresar() {
        return "MiPerfil.salutem?faces-redirect=true&p=0";
    }

    public String editar() {
        formulario.editar();
        return null;
    }

    public String cancelar() {
        try {
            persona = ejbPersonas.buscar(persona.getId());
            formulario.cancelar();
        } catch (ExcepcionDeConsulta ex) {
            Logger.getLogger(MiPerfilBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String grabar() {
        try {
            ejbPersonas.actualizar(persona, persona.getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
        } catch (ExcepcionDeActualizacion ex) {
            Logger.getLogger(MiPerfilBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean validar() {
        if ((persona.getCedula() == null) || (persona.getCedula().trim().isEmpty())) {
            Mensajes.advertencia("CI o RUC es obligatorio");
            return true;
        }

        try {
            String where = "o.cedula=:cedula";
            Map parametros = new HashMap();
            parametros.put("cedula", persona.getCedula());
            if (persona.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", persona.getId());
            }
            List<Personas> l = ejbPersonas.buscar(where, parametros);
            if (!l.isEmpty()) {
                Mensajes.error("Otra persona con la cédula " + l.get(0).getCedula() + " ya se encuentra registrada en el sistema.");
                return true;
            }

            if ((persona.getUserid() == null) || (persona.getUserid().trim().isEmpty())) {
                Mensajes.advertencia("CI o RUC es obligatorio");
                return true;
            }
            where = "o.userid=:userid";
            parametros = new HashMap();
            parametros.put("userid", persona.getUserid());
            if (persona.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", persona.getId());
            }
            l = ejbPersonas.buscar(where, parametros);
            if (!l.isEmpty()) {
                Mensajes.error("Nombre de usuario no disponible, por favor, elija uno nuevo");
                return true;
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PersonasAbstractoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if ((persona.getNombres() == null) || (persona.getNombres().trim().isEmpty())) {
            Mensajes.advertencia("Nombres son obligatorios");
            return true;
        }
        if ((persona.getApellidos() == null) || (persona.getApellidos().trim().isEmpty())) {
            Mensajes.advertencia("Apellidos son obligatorios");
            return true;
        }

        if (persona.getFecha() != null && persona.getFecha().after(new Date())) {
            Mensajes.advertencia("La fecha de nacimiento no puede ser mayor a la fecha actual");
            return true;
        }
        return false;
    }

    public String fotografiaListener(FileEntryEvent e) {
        FileEntry fileEntry = (FileEntry) e.getComponent();

        FileEntryResults results = fileEntry.getResults();
        for (FileEntryResults.FileInfo i : results.getFiles()) {
            try {
                File file = i.getFile();
                if (!i.getContentType().contains("image/")) {
                    i.updateStatus(Mensajes.getfileEntryStatus(false, "¡Sólo se aceptan archivos de imágen!"), true);
                } else if (i.getStatus().isSuccess()) {
                    persona.setFotografia(Files.readAllBytes(file.toPath()));
                    i.updateStatus(Mensajes.getfileEntryStatus(true, "¡El archivo fue subido con éxito!"), true);
                } else {
                    i.updateStatus(Mensajes.getfileEntryStatus(false, "¡Se ha rechazado el requerimiento porque su tamaño excede el rango permitido: 10 MB!"), true);
                }
            } catch (IOException ex) {
                Mensajes.fatal(ex.getMessage());
                Logger.getLogger(MiPerfilBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String getNombreTabla() {
        return Personas.class.getSimpleName();
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
     * @return the persona
     */
    public Personas getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Personas persona) {
        this.persona = persona;
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
