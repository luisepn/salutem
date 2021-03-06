package org.salutem.seguridad;

import java.io.File;
import java.io.IOException;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.salutem.controladores.InstitucionesFacade;
import org.salutem.entidades.Instituciones;
import org.salutem.entidades.Perfiles;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.excepciones.ExcepcionDeCreacion;
import org.salutem.excepciones.ExcepcionDeEliminacion;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.general.ArchivosBean;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 23 de Noviembre de 2017, 07:46:08 AM
 */
@Named("salutemInstituciones")
@ViewScoped
public class InstitucionesBean implements Serializable, IMantenimiento {

    @Inject
    private SeguridadBean seguridadBean;

    private Formulario formulario = new Formulario();
    private LazyDataModel<Instituciones> instituciones;
    private Instituciones institucion;
    private Perfiles perfil;
    private Boolean activo = true;

    @EJB
    private InstitucionesFacade ejbInstituciones;

    public InstitucionesBean() {
        instituciones = new LazyDataModel<Instituciones>() {
            @Override
            public List<Instituciones> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        perfil = seguridadBean.traerPerfil("Instituciones");
    }

    private List<Instituciones> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        Map parameters = new HashMap();
        String where = " o.activo=:activo ";
        parameters.put("activo", getActivo());
        for (Map.Entry e : map.entrySet()) {
            String clave = (String) e.getKey();
            String valor = (String) e.getValue();
            where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
            parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
        }
        try {
            int total = ejbInstituciones.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            instituciones.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.nombre";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbInstituciones.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(InstitucionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        instituciones = new LazyDataModel<Instituciones>() {
            @Override
            public List<Instituciones> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        institucion = new Instituciones();
        institucion.setActivo(Boolean.TRUE);
        institucion.setFecha(new Date());
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        institucion = ((Instituciones) instituciones.getRowData());
        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        institucion = ((Instituciones) instituciones.getRowData());
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        if ((institucion.getRuc() == null) || (institucion.getRuc().trim().isEmpty())) {
            Mensajes.advertencia("R.U.C. es necesario");
            return true;
        }
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

    @Override
    public String insertar() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        try {
            ejbInstituciones.crear(institucion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
            Mensajes.informacion("Creación exitosa. " + institucion.toString());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(InstitucionesBean.class.getName()).log(Level.SEVERE, null, ex);
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
            ejbInstituciones.actualizar(institucion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
            Mensajes.informacion("Modificación exitosa. " + institucion.toString());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(InstitucionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbInstituciones.eliminar(institucion, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            formulario.cancelar();
            Mensajes.informacion("Eliminación exitosa. " + institucion.toString());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(InstitucionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
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
                Logger.getLogger(ArchivosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        buscar();
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
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @return the instituciones
     */
    public LazyDataModel<Instituciones> getInstituciones() {
        return instituciones;
    }

    /**
     * @return the institucion
     */
    public Instituciones getInstitucion() {
        return institucion;
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
     * @param instituciones the instituciones to set
     */
    public void setInstituciones(LazyDataModel<Instituciones> instituciones) {
        this.instituciones = instituciones;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the activo
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
