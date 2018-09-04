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
import org.entidades.salutem.Atenciones;

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
    protected String getJson(Atenciones objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("fecha", formatoFechaHora.format(objeto.getFecha()));
        json.addProperty("cita", objeto.getCita() != null ? objeto.getCita().getId().toString() : "");
        json.addProperty("motivo", objeto.getMotivo());
        json.addProperty("diganosticp", objeto.getDiagnostico());
        json.addProperty("observaciones", objeto.getObservaciones());
        json.addProperty("formula", objeto.getFormula()!= null ? objeto.getFormula().getId() : 0);
        json.addProperty("paciente", objeto.getPaciente() != null ? objeto.getPaciente().toString() : "");
        json.addProperty("especialidad", objeto.getEspecialidad() != null ? objeto.getEspecialidad().toString() : "");
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }

}
