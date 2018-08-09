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
import org.entidades.salutem.Citas;
import org.jsonb.salutem.BaseEntity;

/**
 *
 * @author usuario
 */
@Stateless
public class CitasFacade extends AbstractFacade<Citas> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitasFacade() {
        super(Citas.class);
    }

    @Override
    protected String getJson(Citas objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("fecha", BaseEntity.format.format(objeto.getFecha()));
        json.addProperty("profesional", objeto.getProfesional() != null ? objeto.getProfesional().toString() : "");
        json.addProperty("paciente", objeto.getPaciente() != null ? objeto.getPaciente().toString() : "");
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("activo", objeto.getActivo() ? "s" : "n");
        return json.toString();
    }

}
