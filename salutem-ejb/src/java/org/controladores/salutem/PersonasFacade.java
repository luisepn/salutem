/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controladores.salutem;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.entidades.salutem.Personas;
import org.excepciones.salutem.ExcepcionDeConsulta;

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
    protected String getJson(Personas actual, Personas objeto) {
        JsonObject json = new JsonObject();

        if (!Objects.equals(objeto.getId(), actual.getId())) {
            json.addProperty("id", objeto.getId());
        }
        if (!Objects.equals(objeto.getApellidos(), actual.getApellidos())) {
            json.addProperty("apellidos", objeto.getApellidos());
        }
        if (!Objects.equals(objeto.getEmail(), actual.getEmail())) {
            json.addProperty("email", objeto.getEmail());
        }
        if (!Objects.equals(objeto.getUserid(), actual.getUserid())) {
            json.addProperty("userid", objeto.getUserid());
        }
//        json.addProperty("clave", objeto.getClave());
        if (!Objects.equals(objeto.getCedula(), actual.getCedula())) {
            json.addProperty("cedula", objeto.getCedula());
        }
        if (!Objects.equals(objeto.getRol(), actual.getRol())) {
            json.addProperty("rol", objeto.getRol());
        }
        if (!Objects.equals(objeto.getOcupacion(), actual.getOcupacion())) {
            json.addProperty("ocupacion", objeto.getOcupacion());
        }
        if (!Objects.equals(objeto.getOcupacion(), actual.getOcupacion())) {
            json.addProperty("descripcion", objeto.getOcupacion());
        }
        if (!Objects.equals(objeto.getFecha(), actual.getFecha())) {
            json.addProperty("fecha", formatoFechaHora.format(objeto.getFecha()));
        }
        if (!Objects.equals(objeto.getFotografia().getRuta(), actual.getFotografia().getRuta())) {
            json.addProperty("fotografia", objeto.getFotografia() != null ? objeto.getFotografia().getRuta().replace("*", objeto.getFotografia().getId().toString()) : "");
        }
        if (!Objects.equals(objeto.getDireccion().toString(), actual.getDireccion().toString())) {
            json.addProperty("direccion", objeto.getDireccion() != null ? objeto.getDireccion().toString() : "");
        }
        if (!Objects.equals(objeto.getGenero().toString(), actual.getGenero().toString())) {
            json.addProperty("genero", objeto.getGenero() != null ? objeto.getGenero().toString() : "");
        }
        if (!Objects.equals(objeto.getActivo(), actual.getActivo())) {
            json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        }
        return json.toString();
    }
}
