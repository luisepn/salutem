package org.salutem.utilitarios;

import java.io.FileOutputStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.icefaces.ace.event.ChartImageExportEvent;
import org.salutem.beans.SeguridadBean;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @fecha Quito, 19 de diciembre de 2017
 * @hora 16:53:15
 * @descripción Este Bean administrará todos los documentos o imagenes que el
 * sistema permita ingresar y almacenadolos en un directorio del sistema de
 * archivos que tenga disponible el servidor de aplicaciones. Dicho directorio
 * puede ser parametrizado Módulo de Seguridad y Parametrización: [Maestro: PG;
 * Parámetro: DIRIMG (parametros)]
 */
@Named("salutemChartExport")
public class ChartExportBean implements Serializable {

    @Inject
    private SeguridadBean seguridadBean;

    private boolean requestOldIE;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        String ua = ec.getRequestHeaderMap().get("user-agent");
        requestOldIE = ua.contains("MSIE 7.0;") || ua.contains("MSIE 8.0;");
    }

    public boolean isRequestOldIE() {
        return requestOldIE;
    }

    public void exportHandler(ChartImageExportEvent e) {
        try {
            FileOutputStream out = new FileOutputStream("asdf1.png");
            out.write(e.getBytes());
            out.close();
        } catch (Exception ex) {

        }
    }

    /**
     * @return the seguridadBean
     */
    public SeguridadBean getSeguridadBean() {
        return seguridadBean;
    }

    /**
     * @param seguridadBean the seguridadBean to set
     */
    public void setSeguridadBean(SeguridadBean seguridadBean) {
        this.seguridadBean = seguridadBean;
    }

    /**
     * @param requestOldIE the requestOldIE to set
     */
    public void setRequestOldIE(boolean requestOldIE) {
        this.requestOldIE = requestOldIE;
    }

}
