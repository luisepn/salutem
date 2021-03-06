/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutemlogs.controladores;

import java.util.Date;
import java.util.Objects;
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
    public void log(Integer registro, String json[], String tabla, char operacion, String userid, String ip) {

        if (json == null
                || (Objects.equals(json[0], "{}") && Objects.equals(json[1], "{}"))) {
            return;
        }

        String anterior = json[0] != null ? json[0].replaceAll("'", "''") : null;
        String nuevo = json[1] != null
                ? (operacion == 'C'
                        ? json[1].replaceAll("'", "''")
                                .replaceAll("\"id\":null", "\"id\":" + registro)
                        : json[1].replaceAll("'", "''")) : null;

        String query = "INSERT INTO Historial("
                + "fecha, "
                + "usuario, "
                + "ip, "
                + "operacion, "
                + "tabla, "
                + "registro, "
                + "anterior, "
                + "nuevo) "
                + ""
                + "VALUES ("
                + ":fecha, "
                + ":usuario, "
                + ":ip, "
                + ":operacion, "
                + ":tabla, "
                + ":registro, "
                + "'" + anterior + "', "
                + "'" + nuevo + "');";
        em.createNativeQuery(query)
                .setParameter("fecha", new Date())
                .setParameter("usuario", userid)
                .setParameter("ip", ip)
                .setParameter("tabla", tabla)
                .setParameter("registro", registro)
                .setParameter("operacion", operacion)
                .executeUpdate();
    }

    @Asynchronous
    public void log(String mensaje, char operacion, String usuario, String ip) {
        String query = "INSERT INTO Historial("
                + "fecha, "
                + "usuario, "
                + "ip, "
                + "operacion, "
                + "tabla, "
                + "registro, "
                + "nuevo) "
                + ""
                + "VALUES ("
                + ":fecha, "
                + ":usuario, "
                + ":ip, "
                + ":operacion, "
                + "'Logs',"
                + "'0', "
                + "'{\"" + (operacion == 'I' ? "login" : "logout") + "\":\"" + mensaje + "\"}');";
        em.createNativeQuery(query)
                .setParameter("fecha", new Date())
                .setParameter("usuario", usuario)
                .setParameter("ip", ip)
                .setParameter("operacion", operacion)
                .executeUpdate();
    }
}
