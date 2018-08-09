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
import org.entidades.salutem.Consultas;

/**
 *
 * @author fernando
 */
@Stateless
public class ConsultasFacade extends AbstractFacade<Consultas> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsultasFacade() {
        super(Consultas.class);
    }

    @Override
    protected String getJson(Consultas objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        return json.getAsString();
    }

}
