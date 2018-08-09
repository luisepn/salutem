package org.salutem.historial;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.controladores.salutem.HistorialFacade;
import org.controladores.salutem.MaestrosFacade;
import org.entidades.salutem.Historial;
import org.entidades.salutem.Perfiles;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.icefaces.ace.model.table.LazyDataModel;
import org.icefaces.ace.model.table.SortCriteria;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.IMantenimiento;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 18 de Noviembre de 2017, 07:23:04 AM
 *
 */
@ManagedBean(name = "salutemHistorial")
@ViewScoped
public class HistorialBean implements Serializable {

    private Formulario formulario = new Formulario();
    private LazyDataModel<Historial> lista;
    private Perfiles perfil;

    @EJB
    private HistorialFacade ejbHistorial;

    public HistorialBean() {
    }

    private List<Historial> cargar(int i, int pageSize, SortCriteria[] scs, Map<String, String> map) {
//        try {
//            Map parameters = new HashMap();
//            String where = " o.activo=:activo ";
//            parameters.put("activo", seguridadBean.getVerActivos());
//            for (Map.Entry e : map.entrySet()) {
//                String clave = (String) e.getKey();
//                String valor = (String) e.getValue();
//                where += " and upper(o." + clave + ") like :" + clave.replaceAll("\\.", "");
//                parameters.put(clave.replaceAll("\\.", ""), valor.toUpperCase() + "%");
//            }
//
//            if (seguridadBean.getInicioCreado() != null && seguridadBean.getFinCreado() != null) {
//                where += " and o.creado between :iniciocreado and :fincreado";
//                parameters.put("iniciocreado", seguridadBean.getInicioCreado());
//                parameters.put("fincreado", seguridadBean.getFinCreado());
//            }
//            if (seguridadBean.getInicioActualizado() != null && seguridadBean.getFinActualizado() != null) {
//                where += " and o.actualizado between :inicioactualizado and :finactualizado";
//                parameters.put("inicioactualizado", seguridadBean.getInicioActualizado());
//                parameters.put("finactualizado", seguridadBean.getFinActualizado());
//            }
//
//            int total = ejbMaestros.contar(where, parameters);
//            formulario.setTotal(total);
//            int endIndex = i + pageSize;
//            if (endIndex > total) {
//                endIndex = total;
//            }
//            maestros.setRowCount(total);
//            String order;
//            if (scs.length == 0) {
//                order = "o.codigo";
//            } else {
//                order = "o." + scs[0].getPropertyName() + (scs[0].isAscending() ? " ASC" : " DESC");
//            }
//            return ejbMaestros.buscar(where, parameters, order, i, endIndex);
//        } catch (ExcepcionDeConsulta ex) {
//            Mensajes.fatal(ex.getMessage());
//            Logger.getLogger(HistorialBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
//        }
    }

    public String buscar(String tabla, int id) {
        lista = new LazyDataModel<Historial>() {
            @Override
            public List<Historial> load(int i, int i1, SortCriteria[] scs, Map<String, String> map) {
                if (!IMantenimiento.validarPerfil(perfil, 'R')) {
                    return null;
                }
                return cargar(i, i1, scs, map);
            }
        };
        return null;
    }

}
