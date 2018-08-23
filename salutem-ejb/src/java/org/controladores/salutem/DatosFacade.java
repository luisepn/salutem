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
import org.entidades.salutem.Datos;

/**
 *
 * @author usuario
 */
@Stateless
public class DatosFacade extends AbstractFacade<Datos> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatosFacade() {
        super(Datos.class);
    }

    @Override
    protected String getJson(Datos objeto) {
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("clasificador", objeto.getClasificador());
        json.addProperty("identificador", objeto.getIdentificador());
        json.addProperty("ordengrupo", objeto.getOrdengrupo());
        json.addProperty("grupo", objeto.getGrupo());
        json.addProperty("codigo", objeto.getCodigo());
        json.addProperty("nombre", objeto.getNombre());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("tipo", objeto.getTipo() != null ? objeto.getTipo().toString() : "");

        if (objeto.getOpciones() != null && !objeto.getOpciones().isEmpty()) {
            json.add("opciones", objeto.getOpcionesJson());
        } else {
            json.addProperty("opciones", "");
        }

        if (objeto.getTipo() != null) {
            switch (objeto.getTipo().getCodigo()) {
                case "TEXT":
                    json.addProperty("valor", objeto.getTexto() != null ? objeto.getTexto() : "");
                    break;
                case "BOOLEAN":
                    json.addProperty("valor", objeto.getBooleano() != null ? objeto.getBooleano() ? "S" : "N" : "");
                    break;
                case "INTEGER":
                    json.addProperty("valor", objeto.getEntero() != null ? objeto.getEntero().toString() : "");
                    break;
                case "DOUBLE":
                    json.addProperty("valor", objeto.getDecimal() != null ? objeto.getDecimal().toString() : "");
                    break;
                case "DATE":
                    json.addProperty("valor", objeto.getFecha() != null ? formatoFecha.format(objeto.getFecha()) : "");
                    break;
                case "TIME":
                    json.addProperty("valor", objeto.getFecha() != null ? formatoHora.format(objeto.getFecha()) : "");
                    break;
                case "DATETIME":
                    json.addProperty("valor", objeto.getFecha() != null ? formatoFechaHora.format(objeto.getFecha()) : "");
                    break;
                case "ONE":
                case "MANY":
                    if (objeto.getSeleccion() != null && !objeto.getSeleccion().isEmpty()) {
                        json.add("valor", objeto.getSeleccionJson());
                    }
                    break;
                case "FILE":
                    json.addProperty("valor", objeto.getArchivo() != null ? objeto.getArchivo().getRuta() : "");
                    break;
            }
        }

        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json.toString();
    }
}
