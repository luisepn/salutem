/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.controladores;

import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.salutem.entidades.Personas;
import org.salutem.excepciones.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class PersonasFacade extends AbstractFacade<Personas> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonasFacade() {
        super(Personas.class);
    }

    public Personas getPersona(String cedula) throws ExcepcionDeConsulta {
        try {
            Query q = em.createQuery("SELECT OBJECT(o) from Personas as o WHERE o.cedula=:cedula");
            q.setParameter("cedula", cedula);
            List<Personas> lista = q.getResultList();
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(PersonasFacade.class.getName(), e);
        }
        return null;
    }

    public Personas login(String usuario, String clave) throws ExcepcionDeConsulta {
        if (usuario == null || clave == null) {
            return null;
        }
        try {
            Query q = em.createQuery("SELECT OBJECT(o) from Personas as o WHERE o.userid=:usuario and o.clave=:clave");
            q.setParameter("usuario", usuario);
            q.setParameter("clave", clave);
            List<Personas> aux = q.getResultList();
            if (!aux.isEmpty()) {
                return aux.get(0);
            }
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(PersonasFacade.class.getName(), e);
        }
        return null;
    }

    @Override
    protected JsonObject getJson(Personas objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("nombres", objeto.getNombres());
        json.addProperty("apellidos", objeto.getApellidos());
        json.addProperty("email", objeto.getEmail());
        json.addProperty("userid", objeto.getUserid());
        json.addProperty("clave", "^*" + Integer.toHexString(objeto.getClave().hashCode()).substring(2, 5) + "*$");
        json.addProperty("cedula", objeto.getCedula());
        json.addProperty("rol", objeto.getRol());
        json.addProperty("ocupacion", objeto.getOcupacion());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("fecha", objeto.getFecha() != null ? formatoFecha.format(objeto.getFecha()) : "");
        json.addProperty("genero", objeto.getGenero() != null ? objeto.getGenero().toString() : null);
        json.addProperty("fotografia", Arrays.hashCode(objeto.getFotografia()));
        json.addProperty("primaria", objeto.getPrimaria());
        json.addProperty("numero", objeto.getNumero());
        json.addProperty("secundaria", objeto.getSecundaria());
        json.addProperty("referencia", objeto.getReferencia());
        json.addProperty("fijo", objeto.getFijo());
        json.addProperty("movil", objeto.getMovil());
        json.addProperty("ciudad", objeto.getCiudad());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

}
