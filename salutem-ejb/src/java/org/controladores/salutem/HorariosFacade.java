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
import org.entidades.salutem.Horarios;

/**
 *
 * @author usuario
 */
@Stateless
public class HorariosFacade extends AbstractFacade<Horarios> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HorariosFacade() {
        super(Horarios.class);
    }

    @Override
    protected JsonObject getJson(Horarios objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("hora", objeto.getHora() != null ? objeto.getHora().toString() : null);
        json.addProperty("dia", objeto.getDia() != null ? objeto.getDia().toString() : null);
        json.addProperty("profesional", objeto.getProfesional() != null ? objeto.getProfesional().toString() : null);
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

}
