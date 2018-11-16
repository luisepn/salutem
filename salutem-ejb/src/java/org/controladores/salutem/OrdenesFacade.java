/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controladores.salutem;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.entidades.salutem.Formulas;
import org.entidades.salutem.Instituciones;
import org.entidades.salutem.Ordenes;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class OrdenesFacade extends AbstractFacade<Ordenes> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdenesFacade() {
        super(Ordenes.class);
    }

    public List<Ordenes> traerOrdenesTodas(Formulas formula) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Ordenes as o where o.formula=:formula");
            q.setParameter("formula", formula);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(OrdenesFacade.class.getName(), e);
        }
    }

    @Override
    protected JsonObject getJson(Ordenes objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("formula", objeto.getFormula() != null ? objeto.getFormula().getId() : null);
        json.addProperty("factura", objeto.getFactura());
        json.addProperty("laboratorio", objeto.getLaboratorio() != null ? objeto.getLaboratorio().toString() : null);
        json.addProperty("registro", objeto.getRegistro() != null ? formatoFechaHora.format(objeto.getRegistro()) : null);
        json.addProperty("recepcion", objeto.getRecepcion() != null ? formatoFechaHora.format(objeto.getRecepcion()) : null);
        json.addProperty("envio", objeto.getEnvio() != null ? formatoFechaHora.format(objeto.getEnvio()) : null);
        json.addProperty("entrega", objeto.getEntrega() != null ? formatoFechaHora.format(objeto.getEntrega()) : null);
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

    public int contarSeleccionados(Map parameters) throws ExcepcionDeConsulta, ExcepcionDeActualizacion {
        String where = "o.seleccionado=true and " + (String) parameters.get("where");
        Map parametros = (Map) parameters.get("parameters");
        return this.contar(where, parametros);
    }

    public Instituciones traerLaboratorio(Map parameters) throws ExcepcionDeConsulta, ExcepcionDeActualizacion {
        String where = "o.seleccionado=true and o.laboratorio is not null and " + (String) parameters.get("where");
        Map parametros = (Map) parameters.get("parameters");
        List<Ordenes> lista = this.buscar(where, parametros, 0, 1);
        if (!lista.isEmpty()) {
            return lista.get(0).getLaboratorio();
        }
        return null;
    }

}
