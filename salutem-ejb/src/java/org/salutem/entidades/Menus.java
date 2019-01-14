/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "menus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menus.findAll", query = "SELECT m FROM Menus m")
    , @NamedQuery(name = "Menus.findById", query = "SELECT m FROM Menus m WHERE m.id = :id")
    , @NamedQuery(name = "Menus.findByNombre", query = "SELECT m FROM Menus m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Menus.findByFormulario", query = "SELECT m FROM Menus m WHERE m.formulario = :formulario")
    , @NamedQuery(name = "Menus.findByDescripcion", query = "SELECT m FROM Menus m WHERE m.descripcion = :descripcion")
    , @NamedQuery(name = "Menus.findByCreado", query = "SELECT m FROM Menus m WHERE m.creado = :creado")
    , @NamedQuery(name = "Menus.findByCreadopor", query = "SELECT m FROM Menus m WHERE m.creadopor = :creadopor")
    , @NamedQuery(name = "Menus.findByActualizado", query = "SELECT m FROM Menus m WHERE m.actualizado = :actualizado")
    , @NamedQuery(name = "Menus.findByActualizadopor", query = "SELECT m FROM Menus m WHERE m.actualizadopor = :actualizadopor")
    , @NamedQuery(name = "Menus.findByActivo", query = "SELECT m FROM Menus m WHERE m.activo = :activo")
    , @NamedQuery(name = "Menus.findByCodigo", query = "SELECT m FROM Menus m WHERE m.codigo = :codigo")})
public class Menus implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "formulario")
    private String formulario;
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
    @Size(max = 2147483647)
    @Column(name = "icono")
    private String icono;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
    private List<Perfiles> perfilesList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(mappedBy = "menupadre")
    private List<Menus> menusList;
    @JoinColumn(name = "menupadre", referencedColumnName = "id")
    @ManyToOne
    private Menus menupadre;
    @JoinColumn(name = "modulo", referencedColumnName = "id")
    @ManyToOne
    private Parametros modulo;

    public Menus() {
    }

    public Menus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }


    @XmlTransient
    public List<Menus> getMenusList() {
        return menusList;
    }

    public void setMenusList(List<Menus> menusList) {
        this.menusList = menusList;
    }

    public Menus getMenupadre() {
        return menupadre;
    }

    public void setMenupadre(Menus menupadre) {
        this.menupadre = menupadre;
    }

    public Parametros getModulo() {
        return modulo;
    }

    public void setModulo(Parametros modulo) {
        this.modulo = modulo;
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
        if (!(object instanceof Menus)) {
            return false;
        }
        Menus other = (Menus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @XmlTransient
    public List<Perfiles> getPerfilesList() {
        return perfilesList;
    }

    public void setPerfilesList(List<Perfiles> perfilesList) {
        this.perfilesList = perfilesList;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

}
