/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.controladores;

import com.google.gson.JsonObject;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.salutem.entidades.Instituciones;
import org.salutem.entidades.Usuariosactivos;
import org.salutem.excepciones.ExcepcionDeConsulta;

/**
 *
 * @author luis
 */
@Stateless
public class UsuariosactivosFacade extends AbstractFacade<Usuariosactivos> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosactivosFacade() {
        super(Usuariosactivos.class);
    }

    @Override
    protected JsonObject getJson(Usuariosactivos objeto) {
        return null;
    }

    public List<Usuariosactivos> traerUsuariosActivos(Integer institucion, String userid) throws ExcepcionDeConsulta {
        try {
            String sql = "Select object(o) from Usuariosactivos as o where o.id is not null";
            if (institucion != null) {
                sql += " and o.institucion=:institucion";
            }
            if (userid != null) {
                sql += " and o.institucion=:institucion";
            }

            Query q = getEntityManager().createQuery(sql);
            if (institucion != null) {
                q.setParameter("institucion", institucion);
            }
            if (userid != null) {
                q.setParameter("userid", userid);
            }
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(UsuariosactivosFacade.class.getName(), e);
        }
    }
}
