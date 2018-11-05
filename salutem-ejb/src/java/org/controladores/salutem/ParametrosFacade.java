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
import org.entidades.salutem.Parametros;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class ParametrosFacade extends AbstractFacade<Parametros> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametrosFacade() {
        super(Parametros.class);
    }

    public Parametros traerParametro(String maestro, String parametro) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Parametros as o where o.maestro.codigo=:maestro and o.codigo=:codigo");
            q.setParameter("maestro", maestro);
            q.setParameter("codigo", parametro);
            List<Parametros> aux = q.getResultList();
            if (!aux.isEmpty()) {
                return aux.get(0);
            }
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(ParametrosFacade.class.getName(), e);
        }
        return null;
    }

    public List<Parametros> traerParametros(String maestro, String orden) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Parametros as o where o.maestro.codigo=:maestro order by " + orden);
            q.setParameter("maestro", maestro);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(ParametrosFacade.class.getName(), e);
        }
    }

    public List<Parametros> traerParametros(String maestro, String parametros, String orden) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Parametros as o where o.maestro.codigo=:maestro "
                    + (parametros == null ? " and (o.parametros is null or o.parametros = '')" : "and o.parametros =:parametros") + " order by " + orden
            );
            q.setParameter("maestro", maestro);
            if (parametros != null) {
                q.setParameter("parametros", parametros);
            }
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(ParametrosFacade.class.getName(), e);
        }
    }

    @Override
    protected JsonObject getJson(Parametros objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("codigo", objeto.getCodigo());
        json.addProperty("nombre", objeto.getNombre());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("parametros", objeto.getParametros());
        json.addProperty("maestro", objeto.getMaestro() != null ? objeto.getMaestro().toString() : "");
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }
}
