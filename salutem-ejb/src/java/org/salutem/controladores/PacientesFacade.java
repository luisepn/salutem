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
import org.salutem.entidades.Pacientes;

/**
 *
 * @author fernando
 */
@Stateless
public class PacientesFacade extends AbstractFacade<Pacientes> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PacientesFacade() {
        super(Pacientes.class);
    }

    @Override
    protected JsonObject getJson(Pacientes objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("persona", objeto.getPersona() != null ? objeto.getPersona().toString() : null);
        json.addProperty("fotografia", objeto.getFotografia() != null ? objeto.getFotografia().getNombre() : null);
        json.addProperty("institucion", objeto.getInstitucion() != null ? objeto.getInstitucion().toString() : null);
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

}
