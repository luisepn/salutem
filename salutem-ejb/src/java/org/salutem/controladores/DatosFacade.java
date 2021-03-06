/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.controladores;

import com.google.gson.JsonObject;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.salutem.entidades.Datos;
import org.salutem.excepciones.ExcepcionDeConsulta;
import org.salutem.utilitarios.Items;

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
    protected JsonObject getJson(Datos objeto) {
        if (objeto == null) {
            return null;
        }
        JsonObject json = new JsonObject();
        json.addProperty("id", objeto.getId());
        json.addProperty("clasificador", objeto.getClasificador());
        json.addProperty("identificador", objeto.getIdentificador());
        json.addProperty("ordengrupo", objeto.getOrdengrupo());
        json.addProperty("grupo", objeto.getGrupo());
        json.addProperty("codigo", objeto.getCodigo());
        json.addProperty("nombre", objeto.getNombre());
        json.addProperty("descripcion", objeto.getDescripcion());
        json.addProperty("tipo", objeto.getTipo() != null ? objeto.getTipo().toString() : null);
        json.addProperty("valor", "");
        if (objeto.getTipo() != null) {
            switch (objeto.getTipo().getCodigo()) {
                case "TEXT":
                    json.addProperty("valor", objeto.getTexto());
                    break;
                case "BOOLEAN":
                    json.addProperty("valor", objeto.getBooleano() != null ? objeto.getBooleano() ? "S" : "N" : null);
                    break;
                case "INTEGER":
                    json.addProperty("valor", objeto.getEntero());
                    break;
                case "DOUBLE":
                    json.addProperty("valor", objeto.getDecimal());
                    break;
                case "DATE":
                    json.addProperty("valor", objeto.getFecha() != null ? formatoFecha.format(objeto.getFecha()) : null);
                    break;
                case "TIME":
                    json.addProperty("valor", objeto.getFecha() != null ? formatoHora.format(objeto.getFecha()) : null);
                    break;
                case "DATETIME":
                    json.addProperty("valor", objeto.getFecha() != null ? formatoFechaHora.format(objeto.getFecha()) : null);
                    break;
                case "ONE":
                case "MANY":
                case "LIST":
                    if (objeto.getOpciones() != null && !objeto.getOpciones().isEmpty()) {
                        json.add("opciones", objeto.getOpcionesJson());
                    }
                    if (objeto.getSeleccion() != null && !objeto.getSeleccion().isEmpty()) {
                        json.add("valor", objeto.getSeleccionJson());
                    }
                    break;
                case "FILE":
                    json.addProperty("valor", objeto.getArchivo() != null ? objeto.getArchivo().getRuta() : null);
                    break;
            }
        }

        json.addProperty("requerido", objeto.getRequerido() ? 'S' : 'N');
        json.addProperty("activo", objeto.getActivo() ? 'S' : 'N');
        return json;
    }

    public List<Datos> traerDatos(String clasificador, String grupo, Integer identificador) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Datos as o where o.activo = true and o.clasificador=:clasificador and o.grupo=:grupo and o.identificador=:identificador ORDER BY o.grupo, o.codigo");
            q.setParameter("clasificador", clasificador);
            q.setParameter("grupo", grupo);
            q.setParameter("identificador", identificador);

            List<Datos> aux = q.getResultList();

            for (Datos d : aux) {
                switch (d.getTipo().getCodigo()) {
                    case "ONE":
                    case "MANY":
                    case "LIST":
                        d.setSeleccion(buscarJsonb("seleccion", d.getId()));
                        List<Items> seleccion = d.getItemListFromJson(true);
                        if (d.getTipo().getCodigo().equals("LIST") && !seleccion.isEmpty()) {
                            d.setOneSeleccion(seleccion.get(0).getClave() + "");
                        } else {
                            d.setManySeleccion(new LinkedList<>());
                            for (Items i : seleccion) {
                                d.getManySeleccion().add(i.getClave() + "");
                            }
                        }
                        break;
                }
            }

            return aux;
        } catch (ExcepcionDeConsulta e) {
            throw new ExcepcionDeConsulta(DatosFacade.class.getName(), e);
        }
    }

    public List<Datos> traerDatosTodos(String clasificador, String grupo, Integer identificador) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Datos as o where o.clasificador=:clasificador and o.grupo=:grupo and o.identificador=:identificador ORDER BY o.grupo, o.codigo");
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
}
