/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entidades.salutem;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "atenciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atenciones.findAll", query = "SELECT a FROM Atenciones a")
    , @NamedQuery(name = "Atenciones.findById", query = "SELECT a FROM Atenciones a WHERE a.id = :id")
    , @NamedQuery(name = "Atenciones.findByFecha", query = "SELECT a FROM Atenciones a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "Atenciones.findByMotivo", query = "SELECT a FROM Atenciones a WHERE a.motivo = :motivo")
    , @NamedQuery(name = "Atenciones.findByObservaciones", query = "SELECT a FROM Atenciones a WHERE a.observaciones = :observaciones")
    , @NamedQuery(name = "Atenciones.findByIndicaciones", query = "SELECT a FROM Atenciones a WHERE a.indicaciones = :indicaciones")
    , @NamedQuery(name = "Atenciones.findByActivo", query = "SELECT a FROM Atenciones a WHERE a.activo = :activo")
    , @NamedQuery(name = "Atenciones.findByCreado", query = "SELECT a FROM Atenciones a WHERE a.creado = :creado")
    , @NamedQuery(name = "Atenciones.findByCreadopor", query = "SELECT a FROM Atenciones a WHERE a.creadopor = :creadopor")
    , @NamedQuery(name = "Atenciones.findByActualizado", query = "SELECT a FROM Atenciones a WHERE a.actualizado = :actualizado")
    , @NamedQuery(name = "Atenciones.findByActualizadopor", query = "SELECT a FROM Atenciones a WHERE a.actualizadopor = :actualizadopor")})
public class Atenciones implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "motivo")
    private String motivo;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 2147483647)
    @Column(name = "indicaciones")
    private String indicaciones;
    @Size(max = 2147483647)
    @Column(name = "creadopor")
    private String creadopor;
    @Size(max = 2147483647)
    @Column(name = "actualizadopor")
    private String actualizadopor;
    @OneToMany(mappedBy = "atencion")
    private List<Prescripciones> prescripcionesList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;
    @OneToOne(mappedBy = "atencion")
    private Formulas formula;
    @JoinColumn(name = "cita", referencedColumnName = "id")
    @OneToOne
    private Citas cita;
    @JoinColumn(name = "paciente", referencedColumnName = "id")
    @ManyToOne
    private Pacientes paciente;
    @JoinColumn(name = "especialidad", referencedColumnName = "id")
    @ManyToOne
    private Parametros especialidad;
    @JoinColumn(name = "profesional", referencedColumnName = "id")
    @ManyToOne
    private Profesionales profesional;

    public Atenciones() {
    }

    public Atenciones(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }


    public Date getActualizado() {
        return actualizado;
    }

    public void setActualizado(Date actualizado) {
        this.actualizado = actualizado;
    }


    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    public Citas getCita() {
        return cita;
    }

    public void setCita(Citas cita) {
        this.cita = cita;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    public Parametros getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Parametros especialidad) {
        this.especialidad = especialidad;
    }

    public Profesionales getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesionales profesional) {
        this.profesional = profesional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atenciones)) {
            return false;
        }
        Atenciones other = (Atenciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Atenciones[ id=" + id + " ]";
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getCreadopor() {
        return creadopor;
    }

    public void setCreadopor(String creadopor) {
        this.creadopor = creadopor;
    }

    public String getActualizadopor() {
        return actualizadopor;
    }

    public void setActualizadopor(String actualizadopor) {
        this.actualizadopor = actualizadopor;
    }

    @XmlTransient
    public List<Prescripciones> getPrescripcionesList() {
        return prescripcionesList;
    }

    public void setPrescripcionesList(List<Prescripciones> prescripcionesList) {
        this.prescripcionesList = prescripcionesList;
    }
    
}
