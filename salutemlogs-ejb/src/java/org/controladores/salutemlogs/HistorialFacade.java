/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controladores.salutemlogs;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.entidades.salutemlogs.Historial;
import org.excepciones.salutemlogs.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class HistorialFacade extends AbstractFacade<Historial> {

    @PersistenceContext(unitName = "salutemlogs-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistorialFacade() {
        super(Historial.class);
    }

    public Object buscar(String where, Map parameters, String order, Integer firstResult, Integer maxResults, Boolean contar) throws ExcepcionDeConsulta {
        try {
            String sql
                    = (contar ? "Select count(o.id) from Historial o" : "Select "
                            + "o.id, "
                            + "o.fecha, "
                            + "o.usuario, "
                            + "o.ip, "
                            + "o.operacion, "
                            + "o.tabla, "
                            + "o.registro, "
                            + "jsonb_pretty(o.anterior) as anterior,"
                            + "jsonb_pretty(o.nuevo) as nuevo "
                            + "from Historial o");
            if (where != null) {
                sql += " where " + where;
            }
            if (order != null) {
                sql += " order by " + order;
            }

            Query q;
            if (contar) {
                q = getEntityManager().createNativeQuery(sql);
            } else {
                q = getEntityManager().createNativeQuery(sql, Historial.class);
            }
            if (parameters != null) {
                Iterator it = parameters.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    String clave = (String) e.getKey();
                    q.setParameter(clave, e.getValue());
                }
            }
            if (firstResult != null && maxResults != null) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

            return contar ? ((BigInteger) q.getSingleResult()).intValue() : (List<Historial>) q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(Historial.class.toString(), e);
        }
    }
}
