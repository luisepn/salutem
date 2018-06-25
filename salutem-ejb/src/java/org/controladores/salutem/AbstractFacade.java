/**
 *Hacer commint en GitHub:
 * 1: Clic derecho sobre todos los archivos del proyecto
 * 2: Seleccionar Git/Commit
 * 3: En la ventana hacer clic en confirmar
 * 4: Seleccionar Git/Commit/Remote/Push
 */
package org.controladores.salutem;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.excepciones.salutem.ExcepcionDeEliminacion;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.excepciones.salutem.ExcepcionDeActualizacion;
import org.excepciones.salutem.ExcepcionDeCreacion;

/**
 *
 * @author fernando
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     *
     * @param entity Entidad a ser creada
     * @param usuario Usuario que ejecuta la creación de la entidad
     * @throws org.excepciones.salutem.ExcepcionDeCreacion
     */
    public void crear(T entity, String usuario) throws ExcepcionDeCreacion {
        try {
            getEntityManager().persist(entity);
        } catch (Exception e) {
            throw new ExcepcionDeCreacion(entity.toString(), e);
        } finally {
            Logger.getLogger(this.entityClass.getName()).log(Level.INFO, "Entidad Creada: {0}", entity.hashCode() + " " + entity.toString());
        }
    }

    /**
     *
     * @param entity Entidad a ser actualizada
     * @param usuario Usuario que ejecuta la actualización de la entidad
     * @throws org.excepciones.salutem.ExcepcionDeActualizacion
     */
    public void actualizar(T entity, String usuario) throws ExcepcionDeActualizacion {
        try {
            getEntityManager().merge(entity);
        } catch (Exception e) {
            throw new ExcepcionDeActualizacion(entity.toString(), e);
        } finally {
            Logger.getLogger(this.entityClass.getName()).log(Level.INFO, "Entidad Actualizada: {0}", entity.hashCode() + " " + entity.toString());
        }
    }

    /**
     *
     * @param entity Entidad a ser eliminada
     * @param usuario Usuario que ejecuta la eliminación de la entidad
     * @throws org.excepciones.salutem.ExcepcionDeEliminacion
     */
    public void eliminar(T entity, String usuario) throws ExcepcionDeEliminacion {
        try {
            getEntityManager().remove(entity);
        } catch (Exception e) {
            throw new ExcepcionDeEliminacion(entity.toString(), e);
        } finally {
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

    private Object ejecutarQuery(String where, Map parameters, String order, Integer firstResult, Integer maxResults, Boolean contar) throws ExcepcionDeConsulta {
        try {
            String sql
                    = (contar ? "Select count(o) from " : "Select object(o) from ")
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

            return contar ? ((Long) q.getSingleResult()).intValue() : (List<T>) q.getResultList();
        } catch (Exception e) {
            throw new ExcepcionDeConsulta(this.entityClass.toString(), e);
        }
    }
}
