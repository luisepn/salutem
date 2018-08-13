package org.salutem.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.entidades.salutem.Personas;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 20 de Noviembre de 2017, 07:38:01 AM
 */
@ManagedBean(name = "salutemPersonas")
@ViewScoped
public class PersonasBean extends PersonasAbstractoBean implements Serializable {

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Personas");
    }

    public String getNombreTabla() {
        return Personas.class.getSimpleName();
    }
}
