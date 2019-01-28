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
import org.salutem.entidades.Horas;
import org.salutem.entidades.Instituciones;
import org.salutem.excepciones.ExcepcionDeConsulta;

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
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Horas as o where o.activo = true and o.institucion=:institucion ORDER BY o.codigo");
            q.setParameter("institucion", institucion);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(HorariosFacade.class.getName(), e);
        }
    }

    @Override
    protected JsonObject getJson(Horas objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("codigo", objeto.getCodigo());
        json.addProperty("nombre", objeto.getNombre());
        json.addProperty("horainicio", formatoHora.format(objeto.getHorainicio()));
        json.addProperty("horafin", formatoHora.format(objeto.getHorafin()));
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("institucion", objeto.getInstitucion() != null ? objeto.getInstitucion().toString() : null);
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }
}
