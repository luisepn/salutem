/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.controladores;

import com.google.gson.JsonObject;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.salutem.entidades.Atenciones;

/**
 *
 * @author fernando
 */
@Stateless
public class AtencionesFacade extends AbstractFacade<Atenciones> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtencionesFacade() {
        super(Atenciones.class);
    }

    @Override
    protected JsonObject getJson(Atenciones objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("fecha", formatoFechaHora.format(objeto.getFecha()));
        json.addProperty("cita", objeto.getCita() != null ? objeto.getCita().getId().toString() : null);
        json.addProperty("motivo", objeto.getMotivo() != null ? objeto.getMotivo() : null);
        json.addProperty("diagnostico", objeto.getDiagnostico() != null ? objeto.getDiagnostico() : null);
        json.addProperty("observaciones", objeto.getObservaciones() != null ? objeto.getObservaciones() : null);
        json.addProperty("paciente", objeto.getPaciente() != null ? objeto.getPaciente().toString() : null);
        json.addProperty("especialidad", objeto.getEspecialidad() != null ? objeto.getEspecialidad().toString() : null);
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

}
