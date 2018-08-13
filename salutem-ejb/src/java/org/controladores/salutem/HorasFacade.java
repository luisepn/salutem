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
import org.entidades.salutem.Horas;
import org.entidades.salutem.Instituciones;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author usuario
 */
@Stateless
public class HorasFacade extends AbstractFacade<Horas> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HorasFacade() {
        super(Horas.class);
    }

    public List<Horas> traerHoras(Instituciones institucion) throws ExcepcionDeConsulta {
        Query q = getEntityManager().createQuery("Select object(o) from Horas as o where o.activo = true and o.institucion=:institucion");
        q.setParameter("institucion", institucion);
        return q.getResultList();
    }

    @Override
    protected String getJson(Horas objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("codigo", objeto.getCodigo());
        json.addProperty("nombre", objeto.getNombre());
        json.addProperty("horainicio", formatoHora.format(objeto.getHorainicio()));
        json.addProperty("horafin", formatoHora.format(objeto.getHorafin()));
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("institucion", objeto.getInstitucion() != null ? objeto.getInstitucion().toString() : "");
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }
}
