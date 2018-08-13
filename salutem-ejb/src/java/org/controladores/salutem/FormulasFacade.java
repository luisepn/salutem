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
import org.entidades.salutem.Formulas;

/**
 *
 * @author fernando
 */
@Stateless
public class FormulasFacade extends AbstractFacade<Formulas> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormulasFacade() {
        super(Formulas.class);
    }

    @Override
    protected String getJson(Formulas objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("lod", objeto.getLod());
        json.addProperty("loi", objeto.getLoi());
        json.addProperty("avscod", objeto.getAvscod());
        json.addProperty("avscoi", objeto.getAvscoi());
        json.addProperty("avccod", objeto.getAvccod());
        json.addProperty("avccoi", objeto.getAvccoi());
        json.addProperty("esferaod", objeto.getEsferaod());
        json.addProperty("esferaoi", objeto.getEsferaoi());
        json.addProperty("cilindrood", objeto.getCilindrood());
        json.addProperty("cilindrooi", objeto.getCilindrooi());
        json.addProperty("ejeod", objeto.getEjeod());
        json.addProperty("ejeoi", objeto.getEjeoi());
        json.addProperty("addod", objeto.getAddod());
        json.addProperty("addoi", objeto.getAddoi());
        json.addProperty("dpod", objeto.getDpod());
        json.addProperty("dpoi", objeto.getDpoi());
        json.addProperty("avod", objeto.getAvod());
        json.addProperty("avoi", objeto.getAvoi());
        json.addProperty("altura", objeto.getAltura());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("consulta", objeto.getConsulta() != null ? objeto.getConsulta().getId() : 0);
        json.addProperty("material", objeto.getMaterial() != null ? objeto.getMaterial().toString() : "");
        json.addProperty("tratamiento", objeto.getTratamiento() != null ? objeto.getTratamiento().toString() : "");
        json.addProperty("orden", objeto.getOrdenes() != null ? objeto.getOrdenes().getId() : 0);
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }

}
