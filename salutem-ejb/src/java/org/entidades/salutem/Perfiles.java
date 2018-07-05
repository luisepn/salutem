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
 * @author fernando
 */
@Entity
@Table(name = "perfiles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfiles.findAll", query = "SELECT p FROM Perfiles p")
    , @NamedQuery(name = "Perfiles.findById", query = "SELECT p FROM Perfiles p WHERE p.id = :id")
    , @NamedQuery(name = "Perfiles.findByConsulta", query = "SELECT p FROM Perfiles p WHERE p.consulta = :consulta")
    , @NamedQuery(name = "Perfiles.findByModificacion", query = "SELECT p FROM Perfiles p WHERE p.modificacion = :modificacion")
    , @NamedQuery(name = "Perfiles.findByBorrado", query = "SELECT p FROM Perfiles p WHERE p.borrado = :borrado")
    , @NamedQuery(name = "Perfiles.findByNuevo", query = "SELECT p FROM Perfiles p WHERE p.nuevo = :nuevo")
    , @NamedQuery(name = "Perfiles.findByActivo", query = "SELECT p FROM Perfiles p WHERE p.activo = :activo")
    , @NamedQuery(name = "Perfiles.findByDescripcion", query = "SELECT p FROM Perfiles p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Perfiles.findByCreado", query = "SELECT p FROM Perfiles p WHERE p.creado = :creado")
    , @NamedQuery(name = "Perfiles.findByCreadopor", query = "SELECT p FROM Perfiles p WHERE p.creadopor = :creadopor")
    , @NamedQuery(name = "Perfiles.findByActualizado", query = "SELECT p FROM Perfiles p WHERE p.actualizado = :actualizado")
    , @NamedQuery(name = "Perfiles.findByActualizadopor", query = "SELECT p FROM Perfiles p WHERE p.actualizadopor = :actualizadopor")})
public class Perfiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "consulta")
    private Boolean consulta;
    @Column(name = "modificacion")
    private Boolean modificacion;
    @Column(name = "borrado")
    private Boolean borrado;
    @Column(name = "nuevo")
    private Boolean nuevo;
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
    @JoinColumn(name = "menu", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Menus menu;
    @JoinColumn(name = "grupo", referencedColumnName = "id")
    @ManyToOne
    private Parametros grupo;

    public Perfiles() {
    }

    public Perfiles(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getConsulta() {
        return consulta;
    }

    public void setConsulta(Boolean consulta) {
        this.consulta = consulta;
    }

    public Boolean getModificacion() {
        return modificacion;
    }

    public void setModificacion(Boolean modificacion) {
        this.modificacion = modificacion;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    public Boolean getNuevo() {
        return nuevo;
    }

    public void setNuevo(Boolean nuevo) {
        this.nuevo = nuevo;
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

    public Menus getMenu() {
        return menu;
    }

    public void setMenu(Menus menu) {
        this.menu = menu;
    }

    public Parametros getGrupo() {
        return grupo;
    }

    public void setGrupo(Parametros grupo) {
        this.grupo = grupo;
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
        if (!(object instanceof Perfiles)) {
            return false;
        }
        Perfiles other = (Perfiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Perfiles[ id=" + id + " ]";
    }
    
}
