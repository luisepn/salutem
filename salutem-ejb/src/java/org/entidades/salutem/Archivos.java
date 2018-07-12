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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fernando
 */
@Entity
@Table(name = "archivos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Archivos.findAll", query = "SELECT a FROM Archivos a")
    , @NamedQuery(name = "Archivos.findById", query = "SELECT a FROM Archivos a WHERE a.id = :id")
    , @NamedQuery(name = "Archivos.findByFecha", query = "SELECT a FROM Archivos a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "Archivos.findByClasificador", query = "SELECT a FROM Archivos a WHERE a.clasificador = :clasificador")
    , @NamedQuery(name = "Archivos.findByIdentificador", query = "SELECT a FROM Archivos a WHERE a.identificador = :identificador")
    , @NamedQuery(name = "Archivos.findByNombre", query = "SELECT a FROM Archivos a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Archivos.findByTipo", query = "SELECT a FROM Archivos a WHERE a.tipo = :tipo")
    , @NamedQuery(name = "Archivos.findByRuta", query = "SELECT a FROM Archivos a WHERE a.ruta = :ruta")
    , @NamedQuery(name = "Archivos.findByDescripcion", query = "SELECT a FROM Archivos a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "Archivos.findByCreado", query = "SELECT a FROM Archivos a WHERE a.creado = :creado")
    , @NamedQuery(name = "Archivos.findByCreadopor", query = "SELECT a FROM Archivos a WHERE a.creadopor = :creadopor")
    , @NamedQuery(name = "Archivos.findByActualizado", query = "SELECT a FROM Archivos a WHERE a.actualizado = :actualizado")
    , @NamedQuery(name = "Archivos.findByActualizadopor", query = "SELECT a FROM Archivos a WHERE a.actualizadopor = :actualizadopor")
    , @NamedQuery(name = "Archivos.findByActivo", query = "SELECT a FROM Archivos a WHERE a.activo = :activo")})
public class Archivos implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "clasificador")
    private String clasificador;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 2147483647)
    @Column(name = "ruta")
    private String ruta;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2147483647)
    @Column(name = "creadopor")
    private String creadopor;
    @Size(max = 2147483647)
    @Column(name = "actualizadopor")
    private String actualizadopor;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "identificador")
    private Integer identificador;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;
    @Column(name = "activo")
    private Boolean activo;
    @OneToOne(mappedBy = "logotipo")
    private Instituciones instituciones;
    @OneToOne(mappedBy = "fotografia")
    private Personas personas;

    @Transient
    private byte[] archivo;

    public Archivos() {
    }

    public Archivos(Integer id) {
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


    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
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

    public Instituciones getInstituciones() {
        return instituciones;
    }

    public void setInstituciones(Instituciones instituciones) {
        this.instituciones = instituciones;
    }

    public Personas getPersonas() {
        return personas;
    }

    public void setPersonas(Personas personas) {
        this.personas = personas;
    }

    /**
     * @return the archivo
     */
    public byte[] getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
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
        if (!(object instanceof Archivos)) {
            return false;
        }
        Archivos other = (Archivos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Archivos[ id=" + id + " ]";
    }

    public String getClasificador() {
        return clasificador;
    }

    public void setClasificador(String clasificador) {
        this.clasificador = clasificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
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
