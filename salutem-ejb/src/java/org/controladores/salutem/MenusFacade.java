/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controladores.salutem;

import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.entidades.salutem.Menus;
import org.entidades.salutem.Parametros;
import org.excepciones.salutem.ExcepcionDeConsulta;

/**
 *
 * @author fernando
 */
@Stateless
public class MenusFacade extends AbstractFacade<Menus> {

    @PersistenceContext(unitName = "salutem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenusFacade() {
        super(Menus.class);
    }

    public List<Menus> traerMenus(Parametros modulo) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Menus AS o WHERE o.modulo=:modulo ORDER BY o.nombre");
            q.setParameter("modulo", modulo);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(MenusFacade.class.getName(), e);
        }
    }

    public List<Menus> traerSubMenus(Menus menu) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createQuery("Select object(o) from Menus AS o WHERE o.menupadre =:menu ORDER BY o.codigo");
            q.setParameter("menu", menu);
            return q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(MenusFacade.class.getName(), e);
        }
    }

    public List<Menus> traerSubMenusDisponibles(Menus menu, Parametros grupo) throws ExcepcionDeConsulta {

        List<Menus> retorno = new LinkedList<>();
        List<Menus> submenus = traerSubMenus(menu);

        for (Menus submenu : submenus) {
            try {
                Query q = getEntityManager().createQuery("Select count(o) from Perfiles AS o WHERE o.grupo=:grupo and o.menu=:submenu");
                q.setParameter("grupo", grupo);
                q.setParameter("submenu", submenu);
                if (((Long) q.getSingleResult()).intValue() == 0) {
                    retorno.add(submenu);
                }
            } catch (Exception e) {
                throw new ExcepcionDeConsulta(MenusFacade.class.getName(), e);
            }
        }
        return retorno;

    }
}
