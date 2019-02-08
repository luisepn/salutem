/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.controladores;

import com.google.gson.JsonObject;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.salutem.entidades.Instituciones;
import org.salutem.entidades.Pacientes;
import org.salutem.entidades.Personas;
import org.salutem.excepciones.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class PacientesFacade extends AbstractFacade<Pacientes> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PacientesFacade() {
        super(Pacientes.class);
    }

    @Override
    protected JsonObject getJson(Pacientes objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("persona", objeto.getPersona() != null ? objeto.getPersona().toString() : null);
        json.addProperty("institucion", objeto.getInstitucion() != null ? objeto.getInstitucion().toString() : null);
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

    public Pacientes traerPaciente(Personas persona, Instituciones institucion) throws ExcepcionDeConsulta {
        try {
            Query q = em.createQuery("Select object(o) from Pacientes as o where o.activo = true and o.persona=:persona and o.institucion=:institucion");
            q.setParameter("persona", persona);
            q.setParameter("institucion", institucion);
            List<Pacientes> aux = q.getResultList();
            if (!aux.isEmpty()) {
                return aux.get(0);
            }
            return null;
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(PacientesFacade.class.getName(), e);
        }
    }
}
