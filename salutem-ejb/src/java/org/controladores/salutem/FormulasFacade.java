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
import org.entidades.salutem.Formulas;

/**
 *
 * @author fernando
 */
@Stateless
public class FormulasFacade extends AbstractFacade<Formulas> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormulasFacade() {
        super(Formulas.class);
    }

    @Override
    protected String getJson(Formulas objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        return json.getAsString();
    }

}
