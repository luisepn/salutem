/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controladores.salutemlogs;

import java.util.Date;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fernando
 */
@Stateless
@LocalBean
public class AsincronoLogFacade {

    @PersistenceContext(unitName = "salutemlogs-ejbPU")
    private EntityManager em;

    @Asynchronous
    public void log(String json, String tabla, char operacion, String userid) {

        String query = "INSERT INTO "
                + "Historial(fecha, tabla, objeto, operacion, userid) "
                + "VALUES (:fecha, :tabla, '" + json + "', :operacion, :userid);";
        em.createNativeQuery(query)
                .setParameter("fecha", new Date())
                .setParameter("tabla", tabla)
                .setParameter("operacion", operacion)
                .setParameter("userid", userid)
                .executeUpdate();
    }
}
