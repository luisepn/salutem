/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.convertidores;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.entidades.salutem.Usuarios;
import org.excepciones.salutem.ExcepcionDeConsulta;
import org.salutem.beans.CombosBean;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 */
@FacesConverter(forClass = Usuarios.class)
public class ConvertidorUsuarios implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Usuarios codigo = null;
        try {
            if (value == null) {
                return null;
            }
            CombosBean bean = (CombosBean) context.getELContext().getELResolver().
                    getValue(context.getELContext(), null, "salutemCombos");
            Integer id = Integer.parseInt(value);
            codigo = bean.traerUsuario(id);
        } catch (ExcepcionDeConsulta ex) {
            Mensajes.fatal(ex.getMessage());
            Logger.getLogger(ConvertidorUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Usuarios codigo = (Usuarios) value;
        if (codigo.getId() == null) {
            return "0";
        }
        return ((Usuarios) value).getId().toString();
    }
}
