/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.utilitarios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author luisfernando
 */
@FacesValidator("org.salutem.utilitarios.ValidadorEmail")
public class ValidadorEmail implements Validator {

    //  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
    //  + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
    //  + "(\\.[A-Za-z]{2,})$";
    
    //  Nuevo patron de validación, permite emails con formatos como: louis.fercho@dominio-ec.com
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    private final Pattern pattern;
    private Matcher matcher;

    public ValidadorEmail() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg =
                    new FacesMessage("Falla en validaciòn de email.",
                    "Formato de email incorrecto");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}