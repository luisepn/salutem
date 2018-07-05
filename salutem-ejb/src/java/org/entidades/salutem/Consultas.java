/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entidades.salutem;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fernando
 */
@Entity
@Table(name = "consultas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consultas.findAll", query = "SELECT c FROM Consultas c")
    , @NamedQuery(name = "Consultas.findById", query = "SELECT c FROM Consultas c WHERE c.id = :id")
    , @NamedQuery(name = "Consultas.findByFecha", query = "SELECT c FROM Consultas c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "Consultas.findByMotivo", query = "SELECT c FROM Consultas c WHERE c.motivo = :motivo")
    , @NamedQuery(name = "Consultas.findByObservaciones", query = "SELECT c FROM Consultas c WHERE c.observaciones = :observaciones")
    , @NamedQuery(name = "Consultas.findByIndicaciones", query = "SELECT c FROM Consultas c WHERE c.indicaciones = :indicaciones")
    , @NamedQuery(name = "Consultas.findByUsuario", query = "SELECT c FROM Consultas c WHERE c.usuario = :usuario")})
public class Consultas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
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
    @Column(name = "usuario")
    private String usuario;
    @OneToOne(mappedBy = "consulta")
    private Formulas formulas;
    @JoinColumn(name = "paciente", referencedColumnName = "id")
    @ManyToOne
    private Pacientes paciente;
    @JoinColumn(name = "especialidad", referencedColumnName = "id")
    @ManyToOne
    private Parametros especialidad;

    public Consultas() {
    }

    public Consultas(Integer id) {
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Formulas getFormulas() {
        return formulas;
    }

    public void setFormulas(Formulas formulas) {
        this.formulas = formulas;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consultas)) {
            return false;
        }
        Consultas other = (Consultas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Consultas[ id=" + id + " ]";
    }
    
}
