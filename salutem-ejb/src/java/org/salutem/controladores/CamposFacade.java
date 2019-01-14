/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.controladores;

import com.google.gson.JsonObject;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.salutem.entidades.Campos;
import org.salutem.entidades.Parametros;
import org.salutem.excepciones.ExcepcionDeConsulta;

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
    protected JsonObject getJson(Campos objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("clasificador", objeto.getClasificador());
        json.addProperty("codigo", objeto.getCodigo());
        json.addProperty("grupo", objeto.getGrupo() != null ? objeto.getGrupo().toString() : null);
        json.addProperty("nombre", objeto.getNombre());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("tipo", objeto.getTipo() != null ? objeto.getTipo().toString() : null);
        if (objeto.getTipo().getCodigo().equals("ONE") || objeto.getTipo().getCodigo().equals("MANY") || objeto.getTipo().getCodigo().equals("LIST")) {
            if (objeto.getOpciones() != null && !objeto.getOpciones().isEmpty()) {
                json.add("opciones", objeto.getOpcionesJson());
            }
        }
        json.addProperty("requerido", objeto.getRequerido() ? 'S' : 'N');
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

    public List<Campos> traerCampos(String clasificador, Parametros grupo) throws ExcepcionDeConsulta {
        if (clasificador == null) {
            return null;
        }
        try {
            String sql = "Select object(o) from Campos as o where o.clasificador=:clasificador "
                    + (grupo != null ? " and o.grupo=:grupo" : "") + " ORDER BY o.grupo.codigo, o.codigo";
            Query q = getEntityManager().createQuery(sql);
            q.setParameter("clasificador", clasificador);
            if (grupo != null) {
                q.setParameter("grupo", grupo);
            }
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(CamposFacade.class.getName(), e);
        }
    }

}
