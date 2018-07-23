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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "horarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horarios.findAll", query = "SELECT h FROM Horarios h")
    , @NamedQuery(name = "Horarios.findById", query = "SELECT h FROM Horarios h WHERE h.id = :id")
    , @NamedQuery(name = "Horarios.findByDescripcion", query = "SELECT h FROM Horarios h WHERE h.descripcion = :descripcion")
    , @NamedQuery(name = "Horarios.findByCreado", query = "SELECT h FROM Horarios h WHERE h.creado = :creado")
    , @NamedQuery(name = "Horarios.findByCreadopor", query = "SELECT h FROM Horarios h WHERE h.creadopor = :creadopor")
    , @NamedQuery(name = "Horarios.findByActualizado", query = "SELECT h FROM Horarios h WHERE h.actualizado = :actualizado")
    , @NamedQuery(name = "Horarios.findByActualizadopor", query = "SELECT h FROM Horarios h WHERE h.actualizadopor = :actualizadopor")
    , @NamedQuery(name = "Horarios.findByActivo", query = "SELECT h FROM Horarios h WHERE h.activo = :activo")})
public class Horarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Size(max = 2147483647)
    @Column(name = "creadopor")
    private String creadopor;
    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;
    @Size(max = 2147483647)
    @Column(name = "actualizadopor")
    private String actualizadopor;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "hora", referencedColumnName = "id")
    @ManyToOne
    private Horas hora;
    @JoinColumn(name = "dia", referencedColumnName = "id")
    @ManyToOne
    private Parametros dia;
    @JoinColumn(name = "profesional", referencedColumnName = "id")
    @ManyToOne
    private Profesionales profesional;

    public Horarios() {
    }

    public Horarios(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public String getCreadopor() {
        return creadopor;
    }

    public void setCreadopor(String creadopor) {
        this.creadopor = creadopor;
    }

    public Date getActualizado() {
        return actualizado;
    }

    public void setActualizado(Date actualizado) {
        this.actualizado = actualizado;
    }

    public String getActualizadopor() {
        return actualizadopor;
    }

    public void setActualizadopor(String actualizadopor) {
        this.actualizadopor = actualizadopor;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Horas getHora() {
        return hora;
    }

    public void setHora(Horas hora) {
        this.hora = hora;
    }

    public Parametros getDia() {
        return dia;
    }

    public void setDia(Parametros dia) {
        this.dia = dia;
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
        if (!(object instanceof Horarios)) {
            return false;
        }
        Horarios other = (Horarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Horarios[ id=" + id + " ]";
    }
    
}
