/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.controladores;

import com.google.gson.JsonObject;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.salutem.entidades.Direcciones;

/**
 *
 * @author fernando
 */
@Stateless
public class DireccionesFacade extends AbstractFacade<Direcciones> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DireccionesFacade() {
        super(Direcciones.class);
    }

    @Override
    protected JsonObject getJson(Direcciones objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("primaria", objeto.getPrimaria());
        json.addProperty("numero", objeto.getNumero());
        json.addProperty("secundaria", objeto.getSecundaria());
        json.addProperty("piso", objeto.getPiso());
        json.addProperty("referencia", objeto.getReferencia());
        json.addProperty("fijo", objeto.getFijo());
        json.addProperty("movil", objeto.getMovil());
        json.addProperty("ciudad", objeto.getCiudad());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

}
