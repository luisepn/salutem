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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.controladores.salutem.ProfesionalesFacade;
import org.entidades.salutem.Archivos;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Parametros;
import org.entidades.salutem.Profesionales;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.excepciones.salutem.ExcepcionDeEliminacion;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

@ManagedBean(name = "salutemProfesionales")
@ViewScoped
public class ProfesionalesBean extends PersonasAbstractoBean implements Serializable {

    private LazyDataModel<Profesionales> profesionales;
    private Profesionales profesional;

    private int especialidad;

    @EJB
    private ProfesionalesFacade ejbProfesionales;

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Profesionales");
    }

    public ProfesionalesBean() {
        profesionales = new LazyDataModel<Profesionales>() {
            @Override
            public List<Profesionales> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                }
                return cargar(i, i1, scs, map);
            }
        };
    }

    public List<Profesionales> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            Map parameters = new HashMap();
            String where = " o.activo=:activo ";
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
            int total = ejbProfesionales.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            profesionales.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = " o.institucion.nombre, o.persona.apellidos, o.persona.nombres";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbProfesionales.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ProfesionalesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String buscarProfesionales() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        profesionales = new LazyDataModel<Profesionales>() {
            @Override
            public List<Profesionales> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return cargar(i, i1, scs, map);
            }
        };
        return null;
    }

    public String crearProfesional() {
        if (!IMantenimiento.validarPerfil(perfil, 'C')) {
            return null;
        }
        profesional = new Profesionales();
        profesional.setActivo(Boolean.TRUE);
        crear();
        if (seguridadBean.getInstitucion() != null) {
            direccion.setCiudad(seguridadBean.getInstitucion().getDireccion() != null ? seguridadBean.getInstitucion().getDireccion().getCiudad() : null);
        }
        return null;
    }

    private void existe() throws ExcepcionDeConsulta {
        Instituciones institucion = profesional.getInstitucion();
        Parametros especialidad = profesional.getEspecialidad();
        String where = " o.persona=:persona and o.institucion=:institucion";
        Map parametros = new HashMap();
        parametros.put("persona", persona);
        parametros.put("institucion", institucion);

        List<Profesionales> lista = ejbProfesionales.buscar(where, parametros);
        if (!lista.isEmpty()) {
            profesional.setId(lista.get(0).getId());
        } else {
            profesional.setId(null);
        }
    }

    public String editarProfesional() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        profesional = (Profesionales) profesionales.getRowData();
        persona = profesional.getPersona();
        imagenesBean.setArchivo(persona.getFotografia() != null ? persona.getFotografia() : new Archivos());
        editar();
        formulario.editar();
        return null;
    }

    public String borrarProfesional() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        profesional = ((Profesionales) profesionales.getRowData());
        persona = profesional.getPersona();
        imagenesBean.setArchivo(persona.getFotografia() != null ? persona.getFotografia() : new Archivos());
        formulario.eliminar();
        return null;
    }

    public String grabarProfesional() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        if (validar()) {
            return null;
        }
        if (profesional.getInstitucion() == null) {
            Mensajes.advertencia("Seleccione una institución");
            return null;
        }
        if (profesional.getEspecialidad() == null) {
            Mensajes.advertencia("Seleccione una especialidad");
            return null;
        }
        if (persona.getId() == null) {
            insertar();
        } else {
            grabar();
        }
        try {
            existe();
            profesional.setPersona(persona);
            if (profesional.getId() == null) {
                profesional.setCreado(new Date());
                profesional.setCreadopor(seguridadBean.getLogueado().getUserid());
                profesional.setActualizado(profesional.getCreado());
                profesional.setActualizadopor(profesional.getCreadopor());
                ejbProfesionales.crear(profesional, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            } else {
                profesional.setActualizado(new Date());
                profesional.setActualizadopor(seguridadBean.getLogueado().getUserid());
                ejbProfesionales.actualizar(profesional, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
            }
        } catch (ExcepcionDeCreacion | ExcepcionDeActualizacion | ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ProfesionalesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String removerProfesional() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbProfesionales.eliminar(profesional, seguridadBean.getLogueado().getUserid(), seguridadBean.getCurrentClientIpAddress());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ProfesionalesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }

    public String getNombreTabla() {
        return Profesionales.class.getSimpleName();
    }

    /**
     * @return the profesionales
     */
    public LazyDataModel<Profesionales> getProfesionales() {
        return profesionales;
    }

    /**
     * @param profesionales the profesionales to set
     */
    public void setProfesionales(LazyDataModel<Profesionales> profesionales) {
        this.profesionales = profesionales;
    }

    /**
     * @return the profesional
     */
    public Profesionales getProfesional() {
        return profesional;
    }

    /**
     * @param profesional the profesional to set
     */
    public void setProfesional(Profesionales profesional) {
        this.profesional = profesional;
    }

    /**
     * @return the especialidad
     */
    public int getEspecialidad() {
        return especialidad;
    }

    /**
     * @param especialidad the especialidad to set
     */
    public void setEspecialidad(int especialidad) {
        this.especialidad = especialidad;
    }

}
