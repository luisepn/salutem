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
import org.entidades.salutem.Consultas;

/**
 *
 * @author fernando
 */
@Stateless
public class ConsultasFacade extends AbstractFacade<Consultas> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsultasFacade() {
        super(Consultas.class);
    }

    @Override
    protected String getJson(Consultas objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("fecha", formatoFecha.format(objeto.getFecha()));
        json.addProperty("motivo", objeto.getMotivo());
        json.addProperty("observaciones", objeto.getObservaciones());
        json.addProperty("indicaciones", objeto.getIndicaciones());
        json.addProperty("usuario", objeto.getUsuario());
        json.addProperty("formula", objeto.getFormula() != null ? objeto.getFormula().getId() : 0);
        json.addProperty("paciente", objeto.getPaciente() != null ? objeto.getPaciente().toString() : "");
        json.addProperty("especialidad", objeto.getEspecialidad() != null ? objeto.getEspecialidad().toString() : "");
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }

}
