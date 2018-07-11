/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controladores.salutem;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.entidades.salutem.Parametros;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class ParametrosFacade extends AbstractFacade<Parametros> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametrosFacade() {
        super(Parametros.class);
    }

    public Parametros traerParametro(String maestro, String parametro) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Parametros as o where o.maestro.codigo=:maestro and o.codigo=:codigo");
            q.setParameter("maestro", maestro);
            q.setParameter("codigo", parametro);
            List<Parametros> codigos = q.getResultList();
            if (!codigos.isEmpty()) {
                return codigos.get(0);
            }
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(ParametrosFacade.class.getName(), e);
        }
        return null;
    }

    public List<Parametros> traerParametros(String maestro) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Parametros as o where o.maestro.codigo=:maestro order by o.codigo");
            q.setParameter("maestro", maestro);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(ParametrosFacade.class.getName(), e);
        }
    }
}
