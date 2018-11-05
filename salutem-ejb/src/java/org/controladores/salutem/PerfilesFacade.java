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
import org.entidades.salutem.Perfiles;

/**
 *
 * @author fernando
 */
@Stateless
public class PerfilesFacade extends AbstractFacade<Perfiles> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PerfilesFacade() {
        super(Perfiles.class);
    }

    @Override
    protected JsonObject getJson(Perfiles objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("menu", objeto.getMenu() != null ? objeto.getMenu().toString() : "");
        json.addProperty("grupo", objeto.getGrupo() != null ? objeto.getGrupo().toString() : "");
        json.addProperty("consulta", objeto.getConsulta() ? 'S' : 'N');
        json.addProperty("modificacion", objeto.getModificacion() ? 'S' : 'N');
        json.addProperty("borrado", objeto.getBorrado() ? 'S' : 'N');
        json.addProperty("nuevo", objeto.getNuevo() ? 'S' : 'N');
        json.addProperty("auditoria", objeto.getAuditoria() ? 'S' : 'N');
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }
}
