/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.beans;

import com.evmedical.entidades.Codigos;
import com.evmedical.entidades.Perfil;
import com.evmedical.excepciones.BorrarException;
import com.evmedical.excepciones.ConsultarException;
import com.evmedical.excepciones.GrabarException;
import com.evmedical.excepciones.InsertarException;
import com.evmedical.seguridad.SeguridadBean;
import com.evmedical.utilitarios.Combos;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.controladores.salutem.HorariosFacade;
import org.entidades.salutem.Horarios;
import org.entidades.salutem.Horas;
import org.entidades.salutem.Profesionales;
import org.salutem.utilitarios.Formulario;
import org.salutem.utilitarios.Mensajes;

/**
 *
 * @author Luis Fernando Ordóñez Armijos
 * @since 22 de Julio de 2018, 23:07:05 AM
 *
 *
 */
@ManagedBean(name = "horarios")
@ViewScoped
public class HorariosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{seguridadMedical}")
    private SeguridadBean seguridadBean;
    private Perfil perfil;

    private Formulario formulario = new Formulario();
    private List<Horarios> listaHorarios;
    private List<Codigos> dias;
    private Horarios horario;
    private Profesionales profesional;

    @EJB
    private HorariosFacade ejbHorarios;

    @PostConstruct
    private void activar() {
        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String redirect = (String) params.get("faces-redirect");
        if (redirect == null) {
            return;
        }

        String p = (String) params.get("p");
        String nombreForma = "HorariosVista";

        if (p == null) {
            Mensajes.fatal("Sin perfil válido");
            seguridadBean.cerraSession();
        }
        perfil = seguridadBean.traerPerfil((String) params.get("p"));

        if (this.perfil == null) {
            Mensajes.fatal("Sin perfil válido");
            seguridadBean.cerraSession();
        }

        if (nombreForma.contains(perfil.getMenu().getFormulario())) {
            if (!this.perfil.getGrupo().equals(seguridadBean.getGrupo().getGrupo())) {
                Mensajes.fatal("Sin perfil válido, grupo invalido");
                seguridadBean.cerraSession();
            }
        }

        llenarDias();

    }

    public HorariosBean() {
    }

    public String buscar() {

        if (profesional == null) {
            Mensajes.advertencia("Seleccione un profesional");
            return null;
        }
        Map parametros = new HashMap();
        parametros.put(";where", "o.profesional=:profesional");
        parametros.put("profesional", profesional);

        parametros.put(";orden", "o.dia.parametros, o.hora.horainicio asc");
        try {
            listaHorarios = ejbHorarios.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String crear() {
        if (!perfil.getNuevo()) {
            Mensajes.advertencia("No tiene autorización para crear un registro");
            return null;
        }

        if (profesional == null) {
            Mensajes.advertencia("Seleccione un profesional");
            return null;
        }

        horario = new Horarios();
        horario.setProfesional(profesional);
        formulario.insertar();
        return null;
    }

    public String modificar() {
        if (!perfil.getModificacion()) {
            Mensajes.advertencia("No tiene autorización para modificar un registro");
            return null;
        }
        horario = (Horarios) listaHorarios.get(formulario.getFila().getRowIndex());
        formulario.editar();
        return null;
    }

    public String eliminar() {
        if (!perfil.getBorrado()) {
            Mensajes.advertencia("No tiene autorización para borrar un registro");
            return null;
        }
        horario = (Horarios) listaHorarios.get(formulario.getFila().getRowIndex());
        formulario.eliminar();
        return null;
    }

    private boolean validar() {
        if (horario.getDia() == null) {
            Mensajes.advertencia("Día es necesario");
            return true;
        }
        if (horario.getHora() == null) {
            Mensajes.advertencia("Hora es necesaria");
            return true;
        }

        Map parametros = new HashMap();
        if (formulario.isNuevo()) {
            parametros.put(";where", "o.dia=:dia and o.hora=:hora and o.profesional=:profesional");
        } else {
            parametros.put(";where", "o.dia=:dia and o.hora=:hora and o.id!=:id");
            parametros.put("id", horario.getId());
        }

        parametros.put("dia", horario.getDia());
        parametros.put("hora", horario.getHora());
        parametros.put("profesional", profesional);

        int aux;
        try {
            aux = ejbHorarios.contar(parametros);
            if (aux > 0) {
                Mensajes.advertencia("Registro duplicado");
                return true;
            }
        } catch (ConsultarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public String insertar() {
        if (validar()) {
            return null;
        }
        try {
            ejbHorarios.create(horario, seguridadBean.getEntidad().getUserid());
        } catch (InsertarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();
        return null;
    }

    public String grabar() {
        if (validar()) {
            return null;
        }
        try {
            ejbHorarios.edit(horario, seguridadBean.getEntidad().getUserid());
        } catch (GrabarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();

        return null;
    }

    public String borrar() {
        try {
            ejbHorarios.remove(horario, seguridadBean.getEntidad().getUserid());
        } catch (BorrarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        buscar();
        formulario.cancelar();

        return null;
    }

    public Horarios traer(Integer id) throws ConsultarException {
        return ejbHorarios.find(id);
    }

    public SelectItem[] getComboHorarios() {
        List<Horarios> li = new LinkedList<>();
        Map parametros = new HashMap();
        parametros.put(";where", "o.activo = true and o.centro=:centro");
        parametros.put("centro", seguridadBean.getCentro());

        try {
            li = ejbHorarios.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Combos.SelectItems(li, true);
    }

    public SelectItem[] getComboHorariosPaquete() {
        List<Horarios> li = new LinkedList<>();
        Map parametros = new HashMap();
        parametros.put(";where", "o.activo = true and o.centro=:centro and o.paquete = true");
        parametros.put("centro", seguridadBean.getCentro());

        try {
            li = ejbHorarios.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Combos.SelectItems(li, true);
    }

    public String cancelar() {
        formulario.cancelar();
        buscar();
        return null;
    }

    private void llenarDias() {
        try {
            dias = ejbHorarios.traerCodigos("DSM", null);

            if (dias.size() > 0) {
                Collections.sort(dias, new Comparator<Codigos>() {
                    @Override
                    public int compare(Codigos t, Codigos t1) {
                        return t.getParametros().compareTo(t1.getParametros());
                    }
                });
            }

        } catch (ConsultarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getColor(Horas hora, Codigos dia) {
        Map parametros = new HashMap();
        parametros.put(";where", "o.hora=:hora and o.dia=:dia and o.profesional=:profesional");
        parametros.put("hora", hora);
        parametros.put("dia", dia);
        parametros.put("profesional", profesional);

        try {
            List<Horarios> aux = ejbHorarios.encontarParametros(parametros);
            if (!aux.isEmpty()) {
                return "#195f88";
            }
        } catch (ConsultarException ex) {
            Mensajes.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "#FFFFFF";

    }

}
