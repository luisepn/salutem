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
import org.entidades.salutem.Datos;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;

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
                    if (objeto.getSeleccion()!= null && !objeto.getSeleccion().isEmpty()) {
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

    public List<Datos> traerDatos(String clasificador, String grupo, Integer identificador) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Datos as o where o.activo = true and o.clasificador=:clasificador and o.grupo=:grupo and o.identificador=:identificador ORDER BY o.grupo, o.codigo");
            q.setParameter("clasificador", clasificador);
            q.setParameter("grupo", grupo);
            q.setParameter("identificador", identificador);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(DatosFacade.class.getName(), e);
        }
    }

    public Datos traerDato(String clasificador, String grupo, Integer identificador, Integer codigo) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Datos as o where o.activo = true and o.clasificador=:clasificador and o.grupo=:grupo and o.identificador=:identificador and o.codigo=:codigo");
            q.setParameter("clasificador", clasificador);
            q.setParameter("grupo", grupo);
            q.setParameter("identificador", identificador);
            q.setParameter("codigo", codigo);
            List<Datos> aux = q.getResultList();
            if (!aux.isEmpty()) {
                return aux.get(0);
            }
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(DatosFacade.class.getName(), e);
        }
        return null;
    }

    public String buscarJsonb(String campo, Integer id) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createNativeQuery("SELECT jsonb_pretty(o." + campo + ") as opciones from Datos o WHERE o.id=:id");
            q.setParameter("id", id);
            List<String> aux = q.getResultList();
            if (!aux.isEmpty()) {
                return aux.get(0);
            }
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(DatosFacade.class.getName(), e);
        }
        return null;
    }

    public void actualizarJsonb(String campo, String valor, Integer id) throws ExcepcionDeActualizacion {
        try {
            if (valor == null) {
                em.createNativeQuery("UPDATE Datos SET " + campo + " = null WHERE id=:id")
                        .setParameter("id", id)
                        .executeUpdate();
            } else {
                em.createNativeQuery("UPDATE Datos SET " + campo + " = '" + valor + "' WHERE id=:id")
                        .setParameter("id", id)
                        .executeUpdate();
            }
        } catch (Exception e) {
            throw new ExcepcionDeActualizacion(DatosFacade.class.getName(), e);
        }
    }
}
