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
import org.entidades.salutem.Atenciones;
import org.entidades.salutem.Formulas;
import org.excepciones.salutem.ExcepcionDeConsulta;

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

    public List<Formulas> traerFormulasTodas(Atenciones atencion) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Formulas as o where o.atencion=:atencion");
            q.setParameter("atencion", atencion);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(FormulasFacade.class.getName(), e);
        }
    }

    @Override
    protected JsonObject getJson(Formulas objeto) {
        if (objeto == null) {
            return null;
        }
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
        json.addProperty("atencion", objeto.getAtencion() != null ? objeto.getAtencion().getId() : null);
        json.addProperty("material", objeto.getMaterial() != null ? objeto.getMaterial().toString() : null);
        json.addProperty("tratamiento", objeto.getTratamiento() != null ? objeto.getTratamiento().toString() : null);
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

}
