package org.salutem.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.controladores.salutem.UsuariosFacade;
import org.entidades.salutem.Parametros;
import org.entidades.salutem.Usuarios;
import org.entidades.salutem.Instituciones;
import org.excepciones.salutem.ExcepcionDeEliminacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 21 de Noviembre de 2017, 09:36:18 AM
 */
@ManagedBean(name = "salutemUsuarios")
@ViewScoped
public class UsuariosBean extends PersonasAbstractoBean implements Serializable {

    private Usuarios usuario;
    private Parametros modulo;
    private Instituciones institucion;
    private LazyDataModel<Usuarios> usuarios;

    @EJB
    private UsuariosFacade ejbUsuarios;

    public UsuariosBean() {
        usuarios = new LazyDataModel<Usuarios>() {
            @Override
            public List<Usuarios> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                return null;
            }
        };
    }

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Usuarios");
        institucion = seguridadBean.getInstitucion();
    }

    private List<Usuarios> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
        try {
            if (persona == null) {
                return null;
            }
            Map parameters = new HashMap();
            parameters.put("persona", persona);
            String where = " o.persona=:persona";
            for (Map.Entry e : map.entrySet()) {
                String clave = (String) e.getKey();
                String valor = (String) e.getValue();
                where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
                parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
            }

            int total = ejbUsuarios.contar(where, parameters);
            formulario.setTotal(total);
            int endIndex = i + pageSize;
            if (endIndex > total) {
                endIndex = total;
            }
            usuarios.setRowCount(total);
            String order;
            if (scs.length == 0) {
                order = "o.modulo.nombre";
            } else {
                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
            }
            return ejbUsuarios.buscar(where, parameters, order, i, endIndex);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        if (!IMantenimiento.validarPerfil(perfil, 'R')) {
            return null;
        }
        if (persona == null) {
            Mensajes.informacion("Es necesario un usuario válido");
            return null;
        }
        usuarios = new LazyDataModel<Usuarios>() {
            @Override
            public List<Usuarios> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
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
        if (persona == null) {
            Mensajes.informacion("Es necesario un usuario válido");
            return null;
        }
        usuario = new Usuarios();
        usuario.setPersona(persona);
        usuario.setInstitucion(institucion);
        formulario.insertar();
        return null;
    }

    @Override
    public String editar() {
        if (!IMantenimiento.validarPerfil(perfil, 'U')) {
            return null;
        }
        usuario = (Usuarios) usuarios.getRowData();
        persona = usuario.getPersona();
        claveBusqueda = usuario.getPersona().toString();

        formulario.editar();
        return null;
    }

    @Override
    public String eliminar() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        usuario = (Usuarios) usuarios.getRowData();
        formulario.eliminar();
        return null;
    }

    @Override
    public boolean validar() {
        if (persona == null) {
            Mensajes.advertencia("Seleccione un usuario");
            return true;
        }
        if (usuario.getGrupo() == null) {
            Mensajes.advertencia("Seleccione un grupo");
            return true;
        }
        Map parameters = new HashMap();
        String where = "o.persona=:persona and o.grupo=:grupo and o.modulo=:modulo";
        parameters.put("persona", usuario.getPersona());
        parameters.put("grupo", usuario.getGrupo());
        parameters.put("modulo", usuario.getModulo());

        if (usuario.getId() != null) {
            where += " and o.id!=:id";
            parameters.put("id", usuario.getId());
        }

        try {
            if (ejbUsuarios.contar(where, parameters) > 0) {
                Mensajes.advertencia("Ya existe un registro con los mismos datos");
                return true;
            }
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
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
            ejbUsuarios.crear(usuario, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeCreacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        Mensajes.informacion("Creación exitoso.\n" + persona.toString());
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
            ejbUsuarios.actualizar(usuario, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeActualizacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        Mensajes.informacion("Modificación exitosa.\n" + persona.toString());
        return null;
    }

    @Override
    public String remover() {
        if (!IMantenimiento.validarPerfil(perfil, 'D')) {
            return null;
        }
        try {
            ejbUsuarios.eliminar(usuario, seguridadBean.getLogueado().getUserid());
        } catch (ExcepcionDeEliminacion ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        Mensajes.informacion("Eliminación exitosa.\n" + persona.toString());
        return null;
    }

    @Override
    public String cancelar() {
        formulario.cancelar();
        buscar();
        return null;
    }

    /**
     * @return the usuario
     */
    public Usuarios getUsuario() {
        return usuario;
    }

    /**
     * @return the modulo
     */
    public Parametros getModulo() {
        return modulo;
    }

    /**
     * @return the institucion
     */
    public Instituciones getInstitucion() {
        return institucion;
    }

    /**
     * @return the usuarios
     */
    public LazyDataModel<Usuarios> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    /**
     * @param modulo the modulo to set
     */
    public void setModulo(Parametros modulo) {
        this.modulo = modulo;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(LazyDataModel<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

}
