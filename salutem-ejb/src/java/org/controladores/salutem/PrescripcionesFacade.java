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
import org.entidades.salutem.Atenciones;
import org.entidades.salutem.Prescripciones;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author usuario
 */
@Stateless
public class PrescripcionesFacade extends AbstractFacade<Prescripciones> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrescripcionesFacade() {
        super(Prescripciones.class);
    }

    public List<Prescripciones> traerPrescripciones(Atenciones atencion) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Prescripciones as o where o.activo = true and o.atencion=:atencion");
            q.setParameter("atencion", atencion);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(PrescripcionesFacade.class.getName(), e);
        }
    }

    public List<Prescripciones> traerPrescripcionesTodas(Atenciones atencion) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Prescripciones as o where o.atencion=:atencion");
            q.setParameter("atencion", atencion);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(PrescripcionesFacade.class.getName(), e);
        }
    }

    @Override
    protected JsonObject getJson(Prescripciones objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("atencion", objeto.getAtencion() != null ? objeto.getAtencion().getId().toString() : "");
        json.addProperty("medicamento", objeto.getMedicamento());
        json.addProperty("dosis", objeto.getDosis());
        json.addProperty("frecuencia", objeto.getFrecuencia());
        json.addProperty("duracion", objeto.getDuracion());
        json.addProperty("advertencias", objeto.getAdvertencias());
        return json;
    }

}
