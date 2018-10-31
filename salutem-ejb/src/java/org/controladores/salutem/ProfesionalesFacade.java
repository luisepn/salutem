/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controladores.salutem;

import com.google.gson.JsonObject;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Parametros;
import org.entidades.salutem.Personas;
import org.entidades.salutem.Profesionales;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author usuario
 */
@Stateless
public class ProfesionalesFacade extends AbstractFacade<Profesionales> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfesionalesFacade() {
        super(Profesionales.class);
    }

    public List<Profesionales> traerProfesionales(Instituciones institucion, Parametros especialidad) throws ExcepcionDeConsulta {
        try {
            Query q = em.createQuery("Select object(o) from Profesionales as o where o.activo = true and o.institucion=:institucion" + (especialidad != null ? " and o.especialidad=:especialidad" : ""));
            q.setParameter("institucion", institucion);
            if (especialidad != null) {
                q.setParameter("especialidad", especialidad);
            }
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(ProfesionalesFacade.class.getName(), e);
        }
    }

    public Profesionales traerProfesional(Personas persona, Instituciones institucion) throws ExcepcionDeConsulta {
        try {
            Query q = em.createQuery("Select object(o) from Profesionales as o where o.activo = true and o.persona=:persona and o.institucion=:institucion");
            q.setParameter("persona", persona);
            q.setParameter("institucion", institucion);
            List<Profesionales> aux = q.getResultList();
            if (!aux.isEmpty()) {
                return aux.get(0);
            }
            return null;
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(ProfesionalesFacade.class.getName(), e);
        }
    }

    @Override
    protected String getJson(Profesionales actual, Profesionales objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("persona", objeto.getPersona() != null ? objeto.getPersona().toString() : "");
        json.addProperty("especialidad", objeto.getEspecialidad() != null ? objeto.getEspecialidad().toString() : "");
        json.addProperty("institucion", objeto.getInstitucion() != null ? objeto.getInstitucion().toString() : "");
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }

}
