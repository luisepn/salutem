/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controladores.salutem;

import com.google.gson.JsonObject;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.entidades.salutem.Citas;
import org.entidades.salutem.Profesionales;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author usuario
 */
@Stateless
public class CitasFacade extends AbstractFacade<Citas> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitasFacade() {
        super(Citas.class);
    }

    public List<Citas> traerCitas(Profesionales profesional) throws ExcepcionDeConsulta {
        Query q = getEntityManager().createQuery("Select object(o) from Citas as o where o.activo = true and o.profesional=:profesional and o.fecha between :inicio and :fin");
        q.setParameter("profesional", profesional);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        q.setParameter("inicio", calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        q.setParameter("fin", calendar.getTime());
        return q.getResultList();
    }

    @Override
    protected String getJson(Citas objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("fecha", formatoFechaHora.format(objeto.getFecha()));
        json.addProperty("profesional", objeto.getProfesional() != null ? objeto.getProfesional().toString() : "");
        json.addProperty("paciente", objeto.getPaciente() != null ? objeto.getPaciente().toString() : "");
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }

}
