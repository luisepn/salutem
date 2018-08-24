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
import javax.persistence.Query;
import org.entidades.salutem.Campos;

/**
 *
 * @author usuario
 */
@Stateless
public class CamposFacade extends AbstractFacade<Campos> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CamposFacade() {
        super(Campos.class);
    }

    @Override
    protected String getJson(Campos objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("clasificador", objeto.getClasificador());
        json.addProperty("codigo", objeto.getCodigo());
        json.addProperty("grupo", objeto.getGrupo() != null ? objeto.getGrupo().toString() : "");
        json.addProperty("nombre", objeto.getNombre());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("tipo", objeto.getTipo() != null ? objeto.getTipo().toString() : "");
        if (objeto.getTipo().getCodigo().equals("ONE") || objeto.getTipo().getCodigo().equals("MANY")) {
            if (objeto.getOpciones() != null && !objeto.getOpciones().isEmpty()) {
                json.add("opciones", objeto.getOpcionesJson());
            } else {
                json.addProperty("opciones", "");
            }
        }
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }

    public String traerOpciones(Integer id) {
        Query q = getEntityManager().createNativeQuery("SELECT jsonb_pretty(o.opciones) as opciones from Campos o WHERE o.id=:id");
        q.setParameter("id", id);
        return (String) q.getSingleResult();
    }

    public void insertarOpciones(String opciones, Integer id) {
        if (opciones == null) {
            em.createNativeQuery("UPDATE Campos SET opciones = null WHERE id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
        } else {
            em.createNativeQuery("UPDATE Campos SET opciones = '" + opciones + "' WHERE id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
        }
    }

}
