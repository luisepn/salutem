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
import org.entidades.salutem.Formulas;
import org.entidades.salutem.Ordenes;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class OrdenesFacade extends AbstractFacade<Ordenes> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdenesFacade() {
        super(Ordenes.class);
    }

    public List<Ordenes> traerOrdenesTodas(Formulas formula) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Ordenes as o where o.formula=:formula");
            q.setParameter("formula", formula);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(OrdenesFacade.class.getName(), e);
        }
    }

    @Override
    protected JsonObject getJson(Ordenes objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

}
