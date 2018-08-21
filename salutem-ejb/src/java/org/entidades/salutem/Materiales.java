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
@Table(name = "materiales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materiales.findAll", query = "SELECT m FROM Materiales m")
    , @NamedQuery(name = "Materiales.findById", query = "SELECT m FROM Materiales m WHERE m.id = :id")
    , @NamedQuery(name = "Materiales.findByNombre", query = "SELECT m FROM Materiales m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Materiales.findByActivo", query = "SELECT m FROM Materiales m WHERE m.activo = :activo")
    , @NamedQuery(name = "Materiales.findByDescripcion", query = "SELECT m FROM Materiales m WHERE m.descripcion = :descripcion")
    , @NamedQuery(name = "Materiales.findByCreado", query = "SELECT m FROM Materiales m WHERE m.creado = :creado")
    , @NamedQuery(name = "Materiales.findByCreadopor", query = "SELECT m FROM Materiales m WHERE m.creadopor = :creadopor")
    , @NamedQuery(name = "Materiales.findByActualizado", query = "SELECT m FROM Materiales m WHERE m.actualizado = :actualizado")
    , @NamedQuery(name = "Materiales.findByActualizadopor", query = "SELECT m FROM Materiales m WHERE m.actualizadopor = :actualizadopor")
    , @NamedQuery(name = "Materiales.findByCodigo", query = "SELECT m FROM Materiales m WHERE m.codigo = :codigo")})
public class Materiales implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2147483647)
    @Column(name = "creadopor")
    private String creadopor;
    @Size(max = 2147483647)
    @Column(name = "actualizadopor")
    private String actualizadopor;
    @Size(max = 2147483647)
    @Column(name = "codigo")
    private String codigo;

    @OneToMany(mappedBy = "material")
    private List<Formulas> formulasList;

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
    @JoinColumn(name = "foco", referencedColumnName = "id")
    @ManyToOne
    private Parametros foco;
    @JoinColumn(name = "tipo", referencedColumnName = "id")
    @ManyToOne
    private Parametros tipo;

    public Materiales() {
    }

    public Materiales(Integer id) {
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


    public Parametros getFoco() {
        return foco;
    }

    public void setFoco(Parametros foco) {
        this.foco = foco;
    }

    public Parametros getTipo() {
        return tipo;
    }

    public void setTipo(Parametros tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof Materiales)) {
            return false;
        }
        Materiales other = (Materiales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Materiales[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Formulas> getFormulasList() {
        return formulasList;
    }

    public void setFormulasList(List<Formulas> formulasList) {
        this.formulasList = formulasList;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
}
