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

        JsonObject jsone = new JsonObject();

        if (objeto.getLensometria() != null) {
            jsone.addProperty("izquierdo", objeto.getLensometria().getI());
            jsone.addProperty("derecho", objeto.getLensometria().getD());
            json.add("lensometria", jsone);
        } else {
            json.addProperty("lensometria", "");
        }
        if (objeto.getAgudezavisualsincristal() != null) {
            jsone.addProperty("izquierdo", objeto.getAgudezavisualsincristal().getI());
            jsone.addProperty("derecho", objeto.getAgudezavisualsincristal().getD());
            json.add("agudezavisualsincristal", jsone);
        } else {
            json.addProperty("agudezavisualsincristal", "");
        }
        if (objeto.getAgudezavisualsincristal() != null) {
            jsone.addProperty("izquierdo", objeto.getAgudezavisualconcristal().getI());
            jsone.addProperty("derecho", objeto.getAgudezavisualconcristal().getD());
            json.add("agudezavisualconcristal", jsone);
        } else {
            json.addProperty("agudezavisualconcristal", "");
        }
        if (objeto.getEsfera() != null) {
            jsone.addProperty("izquierdo", objeto.getEsfera().getI());
            jsone.addProperty("derecho", objeto.getEsfera().getD());
            json.add("esfera", jsone);
        } else {
            json.addProperty("esfera", "");
        }
        if (objeto.getCilindro() != null) {
            jsone.addProperty("izquierdo", objeto.getCilindro().getI());
            jsone.addProperty("derecho", objeto.getCilindro().getD());
            json.add("cilindro", jsone);
        } else {
            json.addProperty("cilindro", "");
        }
        if (objeto.getEje() != null) {
            jsone.addProperty("izquierdo", objeto.getEje().getI());
            jsone.addProperty("derecho", objeto.getEje().getD());
            json.add("eje", jsone);
        } else {
            json.addProperty("eje", "");
        }
        if (objeto.getAdicion() != null) {
            jsone.addProperty("izquierdo", objeto.getAdicion().getI());
            jsone.addProperty("derecho", objeto.getAdicion().getD());
            json.add("adicion", jsone);
        } else {
            json.addProperty("adicion", "");
        }
        if (objeto.getDistanciapupilar() != null) {
            jsone.addProperty("izquierdo", objeto.getDistanciapupilar().getI());
            jsone.addProperty("derecho", objeto.getDistanciapupilar().getD());
            json.add("distanciapupilar", jsone);
        } else {
            json.addProperty("distanciapupilar", "");
        }
        if (objeto.getAgudezavisual() != null) {
            jsone.addProperty("izquierdo", objeto.getAgudezavisual().getI());
            jsone.addProperty("derecho", objeto.getAgudezavisual().getD());
            json.add("agudezavisual", jsone);
        } else {
            json.addProperty("agudezavisual", "");
        }

        json.addProperty("altura", objeto.getAltura());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("consulta", objeto.getAtencion() != null ? objeto.getAtencion().getId() : 0);
        json.addProperty("material", objeto.getMaterial() != null ? objeto.getMaterial().toString() : "");
        json.addProperty("tratamiento", objeto.getTratamiento() != null ? objeto.getTratamiento().toString() : "");
        json.addProperty("orden", objeto.getOrden() != null ? objeto.getOrden().getId() : 0);
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }

}
