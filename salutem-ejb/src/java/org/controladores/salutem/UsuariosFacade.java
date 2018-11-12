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
import org.entidades.salutem.Usuarios;

/**
 *
 * @author fernando
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }

    @Override
    protected JsonObject getJson(Usuarios objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("modulo", objeto.getModulo() != null ? objeto.getModulo().toString() : null);
        json.addProperty("grupo", objeto.getGrupo() != null ? objeto.getGrupo().toString() : null);
        json.addProperty("institucion", objeto.getInstitucion() != null ? objeto.getInstitucion().toString() : null);
        json.addProperty("persona", objeto.getPersona() != null ? objeto.getPersona().toString() : null);
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }
}
