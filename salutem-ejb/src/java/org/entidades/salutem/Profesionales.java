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
 * @author fernando
 */
@Entity
@Table(name = "profesionales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesionales.findAll", query = "SELECT p FROM Profesionales p"),
    @NamedQuery(name = "Profesionales.findById", query = "SELECT p FROM Profesionales p WHERE p.id = :id"),
    @NamedQuery(name = "Profesionales.findByActivo", query = "SELECT p FROM Profesionales p WHERE p.activo = :activo"),
    @NamedQuery(name = "Profesionales.findByDescripcion", query = "SELECT p FROM Profesionales p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Profesionales.findByCreado", query = "SELECT p FROM Profesionales p WHERE p.creado = :creado"),
    @NamedQuery(name = "Profesionales.findByCreadopor", query = "SELECT p FROM Profesionales p WHERE p.creadopor = :creadopor"),
    @NamedQuery(name = "Profesionales.findByActualizado", query = "SELECT p FROM Profesionales p WHERE p.actualizado = :actualizado"),
    @NamedQuery(name = "Profesionales.findByActualizadopor", query = "SELECT p FROM Profesionales p WHERE p.actualizadopor = :actualizadopor")})
public class Profesionales implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2147483647)
    @Column(name = "creadopor")
    private String creadopor;
    @Size(max = 2147483647)
    @Column(name = "actualizadopor")
    private String actualizadopor;
    @OneToMany(mappedBy = "profesional")
    private List<Citas> citasList;
    @OneToMany(mappedBy = "profesional")
    private List<Atenciones> atencionesList;

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
    @JoinColumn(name = "fotografia", referencedColumnName = "id")
    @OneToOne
    private Archivos fotografia;
    @JoinColumn(name = "institucion", referencedColumnName = "id")
    @ManyToOne
    private Instituciones institucion;
    @JoinColumn(name = "especialidad", referencedColumnName = "id")
    @ManyToOne
    private Parametros especialidad;
    @JoinColumn(name = "persona", referencedColumnName = "id")
    @ManyToOne
    private Personas persona;

    public Profesionales() {
    }

    public Profesionales(Integer id) {
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


    public Archivos getFotografia() {
        return fotografia;
    }

    public void setFotografia(Archivos fotografia) {
        this.fotografia = fotografia;
    }

    public Instituciones getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    public Parametros getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Parametros especialidad) {
        this.especialidad = especialidad;
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
        if (!(object instanceof Profesionales)) {
            return false;
        }
        Profesionales other = (Profesionales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return persona != null ? persona.toString() : "[" + id + "]";
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
