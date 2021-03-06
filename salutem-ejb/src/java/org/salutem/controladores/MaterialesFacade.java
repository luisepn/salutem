/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.controladores;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.salutem.entidades.Materiales;
import org.salutem.entidades.Parametros;
import org.salutem.excepciones.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class MaterialesFacade extends AbstractFacade<Materiales> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterialesFacade() {
        super(Materiales.class);
    }

    public List<Materiales> traerMateriales(Parametros foco, Parametros tipo) throws ExcepcionDeConsulta {
        try {
            Map parameters = new HashMap();
            String sql = "Select object(o) from Materiales as o where o.activo = true ";
            if (foco != null) {
                sql += " and o.foco=:foco";
                parameters.put("foco", foco);
            }
            if (tipo != null) {
                sql += " and o.tipo=:tipo";
                parameters.put("tipo", tipo);
            }
            Query q = getEntityManager().createQuery(sql);
            Iterator it = parameters.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                q.setParameter((String) e.getKey(), e.getValue());
            }
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(MaterialesFacade.class.getName(), e);
        }
    }

    @Override
    protected JsonObject getJson(Materiales objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("codigo", objeto.getCodigo());
        json.addProperty("nombre", objeto.getNombre());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("foco", objeto.getFoco() != null ? objeto.getFoco().toString() : null);
        json.addProperty("tipo", objeto.getTipo() != null ? objeto.getTipo().toString() : null);
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

}
