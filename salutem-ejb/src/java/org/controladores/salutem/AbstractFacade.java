/**
 *
 * Hacer commit en GitHub:
 * 1: Clic derecho sobre todos los archivos del proyecto
 * 2: Seleccionar Git/Commit
 * 3: En la ventana hacer clic en confirmar
 * 4: Seleccionar Git/Commit/Remote/Push
 *
 * Hacer Update desde GitHub
 * 1: Clic derecho sobre todos los archivos del proyecto
 * 2: Seleccionar Git/Commit/Remote/Pull
 *
 */
package org.controladores.salutem;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.controladores.salutemlogs.AsincronoLogFacade;
import org.entidades.salutem.Campos;
import org.entidades.salutem.Datos;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeCreacion;
import org.excepciones.salutem.ExcepcionDeEliminacion;

/**
 *
 * @author fernando
 * @param <T>
 */
public abstract class AbstractFacade<T> implements Serializable {

    protected SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    protected SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    protected SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

    protected abstract EntityManager getEntityManager();

    protected abstract JsonObject getJson(T objeto);

    private final Class<T> entityClass;

    @EJB
    private AsincronoLogFacade ejbLogs;

    protected AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    private String[] getCambios(JsonObject jsonAnterior, JsonObject jsonNuevo) {
        if (jsonAnterior == null) {
            return new String[]{null, jsonNuevo.toString()};
        }

        if (jsonNuevo == null) {
            return new String[]{jsonAnterior.toString(), null};
        }

        JsonObject anterior = new JsonObject();
        JsonObject nuevo = new JsonObject();

        for (String n : jsonNuevo.keySet()) {
            JsonElement a = jsonAnterior.get(n);
            if (!Objects.equals(a, jsonNuevo.get(n))) {
                anterior.add(n, a);
                nuevo.add(n, jsonNuevo.get(n));
            }
        }

        return new String[]{anterior.toString(), nuevo.toString()};
    }

    private T traerAnterior(Integer id) {
        if (id == 0) {
            return null;
        }
        T anterior = getEntityManager().find(entityClass, id);
        try {
            switch (entityClass.getSimpleName()) {
                case "Campos":
                    Campos c = (Campos) anterior;
                    c.setOpciones(buscarJsonb("opciones", c.getId()));
                    return (T) c;
                case "Datos":
                    Datos d = (Datos) anterior;
                    d.setOpciones(buscarJsonb("opciones", d.getId()));
                    d.setSeleccion(buscarJsonb("seleccion", d.getId()));
                    return (T) d;
                default:
                    return anterior;
            }
        } catch (ExcepcionDeConsulta ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param entity Entidad a ser creada
     * @param usuario Usuario que ejecuta la creación de la entidad
     * @param ip Dirección IP del cliente de la aplicación
     * @throws org.excepciones.salutem.ExcepcionDeCreacion
     */
    public void crear(T entity, String usuario, String ip) throws ExcepcionDeCreacion {
        String cambios[] = getCambios(null, getJson(entity));
        try {
            getEntityManager().persist(entity);
            getEntityManager().flush();
        } catch (Exception e) {
            throw new ExcepcionDeCreacion(entity.toString(), e);
        } finally {
            ejbLogs.log(entity.hashCode(), cambios, entity.getClass().getSimpleName(), 'C', usuario, ip);
            Logger.getLogger(this.entityClass.getName()).log(Level.INFO, "Entidad Creada: {0}", entity.hashCode() + " " + entity.toString());
        }
    }

    /**
     *
     * @param entity Entidad a ser actualizada
     * @param usuario Usuario que ejecuta la actualización de la entidad
     * @param ip Dirección IP del cliente de la aplicación
     * @throws org.excepciones.salutem.ExcepcionDeActualizacion
     */
    public void actualizar(T entity, String usuario, String ip) throws ExcepcionDeActualizacion {
        T anterior = traerAnterior(entity.hashCode());
        String cambios[] = getCambios(getJson(anterior), getJson(entity));
        try {
            getEntityManager().merge(entity);
            getEntityManager().flush();
        } catch (Exception e) {
            throw new ExcepcionDeActualizacion(entity.toString(), e);
        } finally {

            ejbLogs.log(entity.hashCode(), cambios, entity.getClass().getSimpleName(), 'U', usuario, ip);
            Logger.getLogger(this.entityClass.getName()).log(Level.INFO, "Entidad Actualizada: {0}", entity.hashCode() + " " + entity.toString());
        }
    }

    /**
     *
     * @param entity Entidad a ser eliminada
     * @param usuario Usuario que ejecuta la eliminación de la entidad
     * @param ip Dirección IP del cliente de la aplicación
     * @throws org.excepciones.salutem.ExcepcionDeEliminacion
     */
    public void eliminar(T entity, String usuario, String ip) throws ExcepcionDeEliminacion {
        T anterior = traerAnterior(entity.hashCode());
        String cambios[] = getCambios(getJson(anterior), null);
        try {
            entity = getEntityManager().merge(entity);
            getEntityManager().remove(entity);
            getEntityManager().flush();
        } catch (Exception e) {
            throw new ExcepcionDeEliminacion(entity.toString(), e);
        } finally {

            ejbLogs.log(entity.hashCode(), cambios, entity.getClass().getSimpleName(), 'D', usuario, ip);
            Logger.getLogger(this.entityClass.getName()).log(Level.INFO, "Entidad Eliminada: {0}", entity.hashCode() + " " + entity.toString());
        }
    }

    /**
     *
     * @return Cantidad de entidades consultados
     * @throws ExcepcionDeConsulta
     */
    public int contar() throws ExcepcionDeConsulta {
        return (int) ejecutarQuery(null, null, null, null, null, true);
    }

    /**
     *
     * @param where condiciones de consulta
     * @return Cantidad de entidades consultados
     * @throws ExcepcionDeConsulta
     */
    public int contar(String where) throws ExcepcionDeConsulta {
        return (int) ejecutarQuery(where, null, null, null, null, true);
    }

    /**
     *
     * @param where condiciones de consulta
     * @param parameters parámetros de consulta
     * @return Cantidad de entidades consultados
     * @throws ExcepcionDeConsulta
     */
    public int contar(String where, Map parameters) throws ExcepcionDeConsulta {
        return (int) ejecutarQuery(where, parameters, null, null, null, true);
    }

    /**
     *
     * @param id Indentificador de objeto
     * @return Entidad consultada
     * @throws ExcepcionDeConsulta
     */
    public T buscar(Object id) throws ExcepcionDeConsulta {
        try {
            return getEntityManager().find(entityClass, id);
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(entityClass.toString(), e);
        }
    }

    /**
     *
     * @param where condiciones de consulta
     * @return Lista de entidades consultadas
     * @throws ExcepcionDeConsulta
     */
    public List<T> buscar(String where) throws ExcepcionDeConsulta {
        return (List<T>) ejecutarQuery(where, null, null, null, null, false);
    }

    /**
     *
     * @param where condiciones de consulta
     * @param parameters parámetros de consulta
     * @return Lista de entidades consultadas
     * @throws ExcepcionDeConsulta
     */
    public List<T> buscar(String where, Map parameters) throws ExcepcionDeConsulta {
        return (List<T>) ejecutarQuery(where, parameters, null, null, null, false);
    }

    /**
     *
     * @param where condiciones de consulta
     * @param order orden de consulta
     * @return Lista de entidades consultadas
     * @throws ExcepcionDeConsulta
     */
    public List<T> buscar(String where, String order) throws ExcepcionDeConsulta {
        return (List<T>) ejecutarQuery(where, null, order, null, null, false);
    }

    /**
     *
     * @param where condiciones de consulta
     * @param parameters parámetros de consulta
     * @param order orden de consulta
     * @return Lista de entidades consultadas
     * @throws ExcepcionDeConsulta
     */
    public List<T> buscar(String where, Map parameters, String order) throws ExcepcionDeConsulta {
        return (List<T>) ejecutarQuery(where, parameters, order, null, null, false);
    }

    /**
     *
     * @param where condiciones de consulta
     * @param parameters parámetros de consulta
     * @param order orden de consulta
     * @param firstResult rango inicial de consulta
     * @param maxResults rango final de consulta
     * @return Lista de entidades consultadas
     * @throws ExcepcionDeConsulta
     */
    public List<T> buscar(String where, Map parameters, String order, Integer firstResult, Integer maxResults) throws ExcepcionDeConsulta {
        return (List<T>) ejecutarQuery(where, parameters, order, firstResult, maxResults, false);
    }

    /**
     *
     * @param where condiciones de consulta
     * @param parameters parámetros de consulta
     * @param firstResult rango inicial de consulta
     * @param maxResults rango final de consulta
     * @return Lista de entidades consultadas
     * @throws ExcepcionDeConsulta
     */
    public List<T> buscar(String where, Map parameters, Integer firstResult, Integer maxResults) throws ExcepcionDeConsulta {
        return (List<T>) ejecutarQuery(where, parameters, null, firstResult, maxResults, false);
    }

    private Object ejecutarQuery(String where, Map parameters, String order, Integer firstResult, Integer maxResults, Boolean count) throws ExcepcionDeConsulta {
        try {
            String sql
                    = (count ? "Select count(o) from " : "Select object(o) from ")
                    + this.entityClass.getSimpleName();
            if (where != null) {
                sql += " as o where " + where;
            }
            if (order != null) {
                sql += " order by " + order;
            }

            Query q = getEntityManager().createQuery(sql);
            if (parameters != null) {
                Iterator it = parameters.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    String clave = (String) e.getKey();
                    q.setParameter(clave, e.getValue());
                }
            }
            if (firstResult != null && maxResults != null) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

            return count ? ((Long) q.getSingleResult()).intValue() : (List<T>) q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(this.entityClass.toString(), e);
        }
    }

    public Object buscar(String field, String table, String where, Map parameters, boolean one) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createNativeQuery("SELECT " + field + " from " + table + " WHERE " + where);
            if (parameters != null) {
                Iterator it = parameters.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    String clave = (String) e.getKey();
                    q.setParameter(clave, e.getValue());
                }
            }
            List<Integer> lista = q.getResultList();

            if (one) {
                if (!lista.isEmpty()) {
                    return lista.get(0);
                } else {
                    return 0;
                }
            } else {
                if (lista.isEmpty()) {
                    lista.add(0);
                }
                return lista;
            }
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(table, e);
        }
    }

    public String buscarJsonb(String campo, Integer id) throws ExcepcionDeConsulta {
        try {
            Query q = getEntityManager().createNativeQuery("SELECT jsonb_pretty(o." + campo + ") as json from " + entityClass.getSimpleName() + " o WHERE o.id=:id");
            q.setParameter("id", id);
            List<String> aux = q.getResultList();
            if (!aux.isEmpty()) {
                return aux.get(0);
            }
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(AbstractFacade.class.getName(), e);
        }
        return null;
    }

    public void actualizarJsonb(String campo, String opciones, Integer id) throws ExcepcionDeActualizacion {
        try {
            if (opciones == null) {
                getEntityManager().createNativeQuery("UPDATE " + entityClass.getSimpleName() + " SET " + campo + " = null WHERE id=:id")
                        .setParameter("id", id)
                        .executeUpdate();
            } else {
                getEntityManager().createNativeQuery("UPDATE " + entityClass.getSimpleName() + " SET " + campo + " = '" + opciones + "' WHERE id=:id")
                        .setParameter("id", id)
                        .executeUpdate();
            }
        } catch (Exception e) {
            throw new ExcepcionDeActualizacion(CamposFacade.class.getName(), e);
        }
    }
}
