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
import org.entidades.salutem.Parametros;
import org.entidades.salutem.Profesionales;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author usuario
 */
@Stateless
public class ProfesionalesFacade extends AbstractFacade<Profesionales> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfesionalesFacade() {
        super(Profesionales.class);
    }

    public List<Profesionales> traerProfesionales(Instituciones institucion, Parametros especialidad) throws ExcepcionDeConsulta {
        Query q = getEntityManager().createQuery("Select object(o) from Profesionales as o where o.activo = true and o.institucion=:institucion" + (especialidad != null ? " and o.especialidad=:especialidad" : ""));
        q.setParameter("institucion", institucion);
        if (especialidad != null) {
            q.setParameter("especialidad", especialidad);
        }
        return q.getResultList();
    }

}