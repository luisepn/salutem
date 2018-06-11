/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controladores.salutem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.entidades.salutem.Materiales;
import org.entidades.salutem.Parametros;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class MaterialesFacade extends AbstractFacade<Materiales> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterialesFacade() {
        super(Materiales.class);
    }

    public List<Materiales> traerMateriales(Parametros foco, Parametros tipo) throws ExcepcionDeConsulta {
        try {
            Map parameters = new HashMap();
            String sql = "Select object(o) from Materiales as o where o.activo = true ";
            if (foco != null) {
                sql += " and o.foco=:foco";
                parameters.put("foco", foco);
            }
            if (tipo != null) {
                sql += " and o.tipo=:tipo";
                parameters.put("tipo", tipo);
            }
            Query q = getEntityManager().createQuery(sql);
            Iterator it = parameters.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                q.setParameter((String) e.getKey(), e.getValue());
            }
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(MaterialesFacade.class.getName(), e);
        }
    }

}
