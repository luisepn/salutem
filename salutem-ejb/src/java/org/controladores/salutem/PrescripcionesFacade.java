/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controladores.salutem;

import com.google.gson.JsonObject;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.entidades.salutem.Prescripciones;

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

    @Override
    protected String getJson(Prescripciones objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("atencion", objeto.getAtencion() != null ? objeto.getAtencion().getId().toString() : "");
        json.addProperty("medicamento", objeto.getMedicamento());
        json.addProperty("dosis", objeto.getDosis());
        json.addProperty("frecuencia", objeto.getFrecuencia());
        json.addProperty("duracion", objeto.getDuracion());
        json.addProperty("advertencias", objeto.getAdvertencias());
        return json.toString();
    }

}
