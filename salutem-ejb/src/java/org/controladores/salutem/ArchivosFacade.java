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
import org.entidades.salutem.Archivos;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class ArchivosFacade extends AbstractFacade<Archivos> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivosFacade() {
        super(Archivos.class);
    }

    public Archivos traerArchivo(String clasificador, Integer identificador) throws ExcepcionDeConsulta {
        if (clasificador == null || identificador == null) {
            return null;
        }
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Archivos as o where o.clasificador=:clasificador and o.identificador=:identificador");
            q.setParameter("clasificador", clasificador);
            q.setParameter("identificador", identificador);
            return (Archivos) q.getSingleResult();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(ArchivosFacade.class.getName(), e);
        }
    }

    @Override
    protected String getJson(Archivos objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("clasificador", objeto.getClasificador());
        json.addProperty("identificador", objeto.getIdentificador());
        json.addProperty("nombre", objeto.getNombre());
        json.addProperty("tipo", objeto.getTipo());
        json.addProperty("ruta", objeto.getRuta());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }

}
