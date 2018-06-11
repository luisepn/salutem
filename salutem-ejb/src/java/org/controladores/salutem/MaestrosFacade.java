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
import org.entidades.salutem.Maestros;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class MaestrosFacade extends AbstractFacade<Maestros> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaestrosFacade() {
        super(Maestros.class);
    }

    public List<Maestros> traerMaestros() throws ExcepcionDeConsulta {
        Query q = getEntityManager().createQuery("Select object(o) from Maestros as o where o.activo = true");
        return q.getResultList();
    }
}
