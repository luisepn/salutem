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
 * @author usuario
 */
@Entity
@Table(name = "horas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horas.findAll", query = "SELECT h FROM Horas h")
    , @NamedQuery(name = "Horas.findById", query = "SELECT h FROM Horas h WHERE h.id = :id")
    , @NamedQuery(name = "Horas.findByNombre", query = "SELECT h FROM Horas h WHERE h.nombre = :nombre")
    , @NamedQuery(name = "Horas.findByHorainicio", query = "SELECT h FROM Horas h WHERE h.horainicio = :horainicio")
    , @NamedQuery(name = "Horas.findByHorafin", query = "SELECT h FROM Horas h WHERE h.horafin = :horafin")
    , @NamedQuery(name = "Horas.findByActivo", query = "SELECT h FROM Horas h WHERE h.activo = :activo")
    , @NamedQuery(name = "Horas.findByDescripcion", query = "SELECT h FROM Horas h WHERE h.descripcion = :descripcion")
    , @NamedQuery(name = "Horas.findByCreado", query = "SELECT h FROM Horas h WHERE h.creado = :creado")
    , @NamedQuery(name = "Horas.findByCreadopor", query = "SELECT h FROM Horas h WHERE h.creadopor = :creadopor")
    , @NamedQuery(name = "Horas.findByActualizado", query = "SELECT h FROM Horas h WHERE h.actualizado = :actualizado")
    , @NamedQuery(name = "Horas.findByActualizadopor", query = "SELECT h FROM Horas h WHERE h.actualizadopor = :actualizadopor")})
public class Horas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "horainicio")
    @Temporal(TemporalType.TIME)
    private Date horainicio;
    @Column(name = "horafin")
    @Temporal(TemporalType.TIME)
    private Date horafin;
    @Column(name = "activo")
    private Boolean activo;
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
    @JoinColumn(name = "institucion", referencedColumnName = "id")
    @ManyToOne
    private Instituciones institucion;
    @OneToMany(mappedBy = "hora")
    private List<Horarios> horariosList;

    public Horas() {
    }

    public Horas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(Date horainicio) {
        this.horainicio = horainicio;
    }

    public Date getHorafin() {
        return horafin;
    }

    public void setHorafin(Date horafin) {
        this.horafin = horafin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Instituciones getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    @XmlTransient
    public List<Horarios> getHorariosList() {
        return horariosList;
    }

    public void setHorariosList(List<Horarios> horariosList) {
        this.horariosList = horariosList;
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
        if (!(object instanceof Horas)) {
            return false;
        }
        Horas other = (Horas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Horas[ id=" + id + " ]";
    }
    
}
