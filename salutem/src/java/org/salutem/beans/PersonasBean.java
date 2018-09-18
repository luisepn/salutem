package org.salutem.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.entidades.salutem.Personas;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 20 de Noviembre de 2017, 07:38:01 AM
 */
@Named("salutemPersonas")
@ViewScoped
public class PersonasBean extends PersonasAbstractoBean implements Serializable {

    @PostConstruct
    @Override
    public void activar() {
        perfil = seguridadBean.traerPerfil("Personas");
    }

    @Override
    public String getNombreTabla() {
        return Personas.class.getSimpleName();
    }
}
