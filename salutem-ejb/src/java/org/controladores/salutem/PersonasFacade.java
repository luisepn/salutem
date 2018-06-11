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
import org.entidades.salutem.Personas;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class PersonasFacade extends AbstractFacade<Personas> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonasFacade() {
        super(Personas.class);
    }

    public Personas getPersona(String cedula) throws ExcepcionDeConsulta {
        try {
            Query q = em.createQuery("SELECT OBJECT(o) from Personas as o WHERE o.cedula=:cedula");
            q.setParameter("cedula", cedula);
            List<Personas> lista = q.getResultList();
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(PersonasFacade.class.getName(), e);
        }
        return null;
    }

    public Personas login(String usuario, String clave) throws ExcepcionDeConsulta {
        try {
            Query q = em.createQuery("SELECT OBJECT(o) from Personas as o WHERE o.userid=:usuario and o.clave=:clave");
            q.setParameter("usuario", usuario);
            q.setParameter("clave", clave);
            List<Personas> lista = q.getResultList();
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(PersonasFacade.class.getName(), e);
        }
        return null;
    }
}
