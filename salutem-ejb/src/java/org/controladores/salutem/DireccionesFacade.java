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
import org.entidades.salutem.Direcciones;

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
    protected String getJson(Direcciones objeto) {
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
        json.addProperty("institucion", objeto.getInstitucion() != null ? objeto.getInstitucion().toString() : "");
        json.addProperty("persona", objeto.getPersona() != null ? objeto.getPersona().toString() : "");
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }

}
