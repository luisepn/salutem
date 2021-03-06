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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "instituciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instituciones.findAll", query = "SELECT i FROM Instituciones i"),
    @NamedQuery(name = "Instituciones.findById", query = "SELECT i FROM Instituciones i WHERE i.id = :id"),
    @NamedQuery(name = "Instituciones.findByNombre", query = "SELECT i FROM Instituciones i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Instituciones.findByFecha", query = "SELECT i FROM Instituciones i WHERE i.fecha = :fecha"),
    @NamedQuery(name = "Instituciones.findByEmail", query = "SELECT i FROM Instituciones i WHERE i.email = :email"),
    @NamedQuery(name = "Instituciones.findByWeb", query = "SELECT i FROM Instituciones i WHERE i.web = :web"),
    @NamedQuery(name = "Instituciones.findByLaboratorio", query = "SELECT i FROM Instituciones i WHERE i.laboratorio = :laboratorio"),
    @NamedQuery(name = "Instituciones.findByActivo", query = "SELECT i FROM Instituciones i WHERE i.activo = :activo"),
    @NamedQuery(name = "Instituciones.findByDescripcion", query = "SELECT i FROM Instituciones i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Instituciones.findByCreado", query = "SELECT i FROM Instituciones i WHERE i.creado = :creado"),
    @NamedQuery(name = "Instituciones.findByCreadopor", query = "SELECT i FROM Instituciones i WHERE i.creadopor = :creadopor"),
    @NamedQuery(name = "Instituciones.findByActualizado", query = "SELECT i FROM Instituciones i WHERE i.actualizado = :actualizado"),
    @NamedQuery(name = "Instituciones.findByActualizadopor", query = "SELECT i FROM Instituciones i WHERE i.actualizadopor = :actualizadopor")})
public class Instituciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "laboratorio")
    private Boolean laboratorio;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;

    @Size(max = 2147483647)
    @Column(name = "ruc")
    private String ruc;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @Size(max = 2147483647)
    @Column(name = "web")
    private String web;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "logotipo")
    private byte[] logotipo;
    @Size(max = 2147483647)
    @Column(name = "primaria")
    private String primaria;
    @Size(max = 2147483647)
    @Column(name = "numero")
    private String numero;
    @Size(max = 2147483647)
    @Column(name = "secundaria")
    private String secundaria;
    @Size(max = 2147483647)
    @Column(name = "referencia")
    private String referencia;
    @Size(max = 2147483647)
    @Column(name = "fijo")
    private String fijo;
    @Size(max = 2147483647)
    @Column(name = "movil")
    private String movil;
    @Size(max = 2147483647)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 2147483647)
    @Column(name = "creadopor")
    private String creadopor;
    @Size(max = 2147483647)
    @Column(name = "actualizadopor")
    private String actualizadopor;
    @OneToMany(mappedBy = "institucion")
    private List<Campos> camposList;
    @OneToMany(mappedBy = "institucion")
    private List<Horas> horasList;
    @OneToMany(mappedBy = "institucion")
    private List<Profesionales> profesionalesList;
    @OneToMany(mappedBy = "institucion")
    private List<Usuarios> usuariosList;
    @OneToMany(mappedBy = "laboratorio")
    private List<Ordenes> ordenesList;
    @OneToMany(mappedBy = "institucion")
    private List<Pacientes> pacientesList;

    public Instituciones() {
    }

    public Instituciones(Integer id) {
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
    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
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

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getPrimaria() {
        return primaria;
    }

    public void setPrimaria(String primaria) {
        this.primaria = primaria;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSecundaria() {
        return secundaria;
    }

    public void setSecundaria(String secundaria) {
        this.secundaria = secundaria;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getFijo() {
        return fijo;
    }

    public void setFijo(String fijo) {
        this.fijo = fijo;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Boolean laboratorio) {
        this.laboratorio = laboratorio;
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

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    @XmlTransient
    public List<Ordenes> getOrdenesList() {
        return ordenesList;
    }

    public void setOrdenesList(List<Ordenes> ordenesList) {
        this.ordenesList = ordenesList;
    }

    @XmlTransient
    public List<Pacientes> getPacientesList() {
        return pacientesList;
    }

    public void setPacientesList(List<Pacientes> pacientesList) {
        this.pacientesList = pacientesList;
    }

    @XmlTransient
    public List<Horas> getHorasList() {
        return horasList;
    }

    public void setHorasList(List<Horas> horasList) {
        this.horasList = horasList;
    }

    @XmlTransient
    public List<Profesionales> getProfesionalesList() {
        return profesionalesList;
    }

    public void setProfesionalesList(List<Profesionales> profesionalesList) {
        this.profesionalesList = profesionalesList;
    }

    @XmlTransient
    public List<Campos> getCamposList() {
        return camposList;
    }

    public void setCamposList(List<Campos> camposList) {
        this.camposList = camposList;
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
        if (!(object instanceof Instituciones)) {
            return false;
        }
        Instituciones other = (Instituciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getDireccion() {
        return (primaria != null && !primaria.isEmpty() && numero != null && secundaria != null && !secundaria.isEmpty())
                ? primaria + " " + numero + " y " + secundaria
                : (primaria != null ? primaria : "") + " "
                + (numero != null ? numero : "")
                + (secundaria != null ? secundaria : "");
    }

    public String getTelefonos() {
        return ((fijo != null && movil != null)
                ? (fijo + " - " + movil)
                : fijo != null
                        ? fijo
                        : movil != null ? movil : "");
    }
}
