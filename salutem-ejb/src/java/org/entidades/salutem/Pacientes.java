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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fernando
 */
@Entity
@Table(name = "pacientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pacientes.findAll", query = "SELECT p FROM Pacientes p")
    , @NamedQuery(name = "Pacientes.findById", query = "SELECT p FROM Pacientes p WHERE p.id = :id")
    , @NamedQuery(name = "Pacientes.findByActivo", query = "SELECT p FROM Pacientes p WHERE p.activo = :activo")
    , @NamedQuery(name = "Pacientes.findByDescripcion", query = "SELECT p FROM Pacientes p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Pacientes.findByCreado", query = "SELECT p FROM Pacientes p WHERE p.creado = :creado")
    , @NamedQuery(name = "Pacientes.findByCreadopor", query = "SELECT p FROM Pacientes p WHERE p.creadopor = :creadopor")
    , @NamedQuery(name = "Pacientes.findByActualizado", query = "SELECT p FROM Pacientes p WHERE p.actualizado = :actualizado")
    , @NamedQuery(name = "Pacientes.findByActualizadopor", query = "SELECT p FROM Pacientes p WHERE p.actualizadopor = :actualizadopor")})
public class Pacientes implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2147483647)
    @Column(name = "creadopor")
    private String creadopor;
    @Size(max = 2147483647)
    @Column(name = "actualizadopor")
    private String actualizadopor;
    @OneToMany(mappedBy = "paciente")
    private List<Atenciones> atencionesList;
    @OneToMany(mappedBy = "paciente")
    private List<Citas> citasList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;
    @JoinColumn(name = "institucion", referencedColumnName = "id")
    @ManyToOne
    private Instituciones institucion;
    @JoinColumn(name = "persona", referencedColumnName = "id")
    @ManyToOne
    private Personas persona;

    public Pacientes() {
    }

    public Pacientes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Instituciones getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
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
        if (!(object instanceof Pacientes)) {
            return false;
        }
        Pacientes other = (Pacientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return persona != null ? persona.toString() : "[" + id + "]";
    }

    public String toStringApellidos() {
        return persona != null ? persona.getApellidos() + " " + persona.getNombres() + " " + persona.getCedula() : "";
    }

    public String toStringNombres() {
        return persona != null ? persona.getNombres() + " " + persona.getApellidos() + " " + persona.getCedula() : "";
    }

    public String toStringCedula() {
        return persona != null ? persona.getCedula() + " " + persona.getNombres() + " " + persona.getApellidos() : "";
    }

    @XmlTransient
    public List<Citas> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<Citas> citasList) {
        this.citasList = citasList;
    }


    @XmlTransient
    public List<Atenciones> getAtencionesList() {
        return atencionesList;
    }

    public void setAtencionesList(List<Atenciones> atencionesList) {
        this.atencionesList = atencionesList;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

}
