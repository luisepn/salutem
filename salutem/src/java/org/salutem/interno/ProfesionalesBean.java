package org.salutem.interno;

import org.salutem.general.PersonasAbstractoBean;
import java.io.Serializable;
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
import javax.inject.Named;
import org.salutem.controladores.ProfesionalesFacade;
import org.salutem.entidades.Instituciones;
import org.salutem.entidades.Profesionales;
import org.salutem.excepciones.ExcepcionDeActualizacion;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.excepciones.ExcepcionDeCreacion;
import org.salutem.excepciones.ExcepcionDeEliminacion;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.entidades.Personas;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

@Named("salutemProfesionales")
@ViewScoped
public class ProfesionalesBean extends PersonasAbstractoBean implements Serializable {

    private LazyDataModel<Profesionales> profesionales;
    private Profesionales profesional;

    private int especialidad;

    private Instituciones institucion;

    @EJB
    private ProfesionalesFacade ejbProfesionales;

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Profesionales");
        institucion = seguridadBean.getInstitucion();
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
            if (institucion != null) {
                where += " and o.institucion=:institucion";
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
        modificarDatos = false;
        profesional = new Profesionales();
        profesional.setActivo(Boolean.TRUE);
        profesional.setInstitucion(institucion);
        crear();
        if (institucion != null) {
            persona.setCiudad(institucion.getCiudad());
        }
        return null;
    }

    public String editarProfesional() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        modificarDatos = false;
        profesional = (Profesionales) profesionales.getRowData();
        institucion = profesional.getInstitucion();
        persona = profesional.getPersona();
        editar();
        formulario.editar();
        return null;
    }

    public String borrarProfesional() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        modificarDatos = false;
        profesional = ((Profesionales) profesionales.getRowData());
        persona = profesional.getPersona();
        formulario.eliminar();
        return null;
    }

    private boolean validarProfesional() throws ExcepcionDeConsulta {
        if (profesional.getInstitucion() == null) {
            Mensajes.advertencia("Seleccione una institución");
            return true;
        }

        String where = "o.persona.cedula=:cedula and o.institucion=:institucion";
        Map parametros = new HashMap();
        parametros.put("cedula", persona.getCedula());
        parametros.put("institucion", profesional.getInstitucion());
        if (profesional.getId() != null) {
            where += " and o.id!=:id";
            parametros.put("id", profesional.getId());
        }
        List<Profesionales> l = ejbProfesionales.buscar(where, parametros);
        if (!l.isEmpty()) {
            Mensajes.error("Otra persona con la cédula " + l.get(0).getPersona().getCedula()
                    + " ya se encuentra registrada como profesional en la Institución: "
                    + institucion.getNombre() + "."
                    + (l.get(0).getActivo() == null || !l.get(0).getActivo() ? " Busque en los registros inactivos." : ""));
            return true;
        }

        if (profesional.getEspecialidad() == null) {
            Mensajes.advertencia("Seleccione una especialidad");
            return true;
        }
        return false;
    }

    public String grabarProfesional() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        modificarDatos = false;

        if (validar()) {
            return null;
        }
        try {
            if (validarProfesional()) {
                return null;
            }
            if (validar()) {
                return null;
            }
            if (persona.getId() == null) {
                crear();
            } else {
                profesional.setActivo(Boolean.TRUE);
                grabar();
            }
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

    @Override
    public String getNombreTabla() {
        return Profesionales.class.getSimpleName();
    }

    @Override
    public void cambiaCedula(ValueChangeEvent event) {
        modificarDatos = false;
        String nuevaCedula = (String) event.getNewValue();
        try {

            String where = "o.cedula=:cedula";
            Map parametros = new HashMap();
            parametros.put("cedula", nuevaCedula);
            if (persona.getId() != null) {
                where += " and o.id!=:id";
                parametros.put("id", persona.getId());
            }
            List<Personas> lp = ejbPersonas.buscar(where, parametros);
            if (!lp.isEmpty()) {
                persona = lp.get(0);
            } else {
                modificarDatos = true;
            }

            validarProfesional();
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(PacientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
