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
import org.entidades.salutem.Instituciones;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class InstitucionesFacade extends AbstractFacade<Instituciones> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstitucionesFacade() {
        super(Instituciones.class);
    }

    public List<Instituciones> traerInstituciones(Boolean tipo) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Instituciones as o where o.activo = true and o.laboratorio = :tipo");
            q.setParameter("tipo", tipo);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(InstitucionesFacade.class.getName(), e);
        }
    }

}
