/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.entidades;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fernando
 */
@Entity
@Table(name = "personas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findById", query = "SELECT p FROM Personas p WHERE p.id = :id"),
    @NamedQuery(name = "Personas.findByNombres", query = "SELECT p FROM Personas p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Personas.findByApellidos", query = "SELECT p FROM Personas p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Personas.findByEmail", query = "SELECT p FROM Personas p WHERE p.email = :email"),
    @NamedQuery(name = "Personas.findByUserid", query = "SELECT p FROM Personas p WHERE p.userid = :userid"),
    @NamedQuery(name = "Personas.findByClave", query = "SELECT p FROM Personas p WHERE p.clave = :clave"),
    @NamedQuery(name = "Personas.findByCedula", query = "SELECT p FROM Personas p WHERE p.cedula = :cedula"),
    @NamedQuery(name = "Personas.findByFecha", query = "SELECT p FROM Personas p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Personas.findByRol", query = "SELECT p FROM Personas p WHERE p.rol = :rol"),
    @NamedQuery(name = "Personas.findByActivo", query = "SELECT p FROM Personas p WHERE p.activo = :activo"),
    @NamedQuery(name = "Personas.findByOcupacion", query = "SELECT p FROM Personas p WHERE p.ocupacion = :ocupacion"),
    @NamedQuery(name = "Personas.findByDescripcion", query = "SELECT p FROM Personas p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Personas.findByCreado", query = "SELECT p FROM Personas p WHERE p.creado = :creado"),
    @NamedQuery(name = "Personas.findByCreadopor", query = "SELECT p FROM Personas p WHERE p.creadopor = :creadopor"),
    @NamedQuery(name = "Personas.findByActualizado", query = "SELECT p FROM Personas p WHERE p.actualizado = :actualizado"),
    @NamedQuery(name = "Personas.findByActualizadopor", query = "SELECT p FROM Personas p WHERE p.actualizadopor = :actualizadopor")})
public class Personas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;
    @Size(max = 2147483647)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 2147483647)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @Size(max = 2147483647)
    @Column(name = "userid")
    private String userid;
    @Size(max = 2147483647)
    @Column(name = "clave")
    private String clave;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 2147483647)
    @Column(name = "cedula")
    private String cedula;
    @Size(max = 2147483647)
    @Column(name = "rol")
    private String rol;
    @Size(max = 2147483647)
    @Column(name = "ocupacion")
    private String ocupacion;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2147483647)
    @Column(name = "creadopor")
    private String creadopor;
    @Size(max = 2147483647)
    @Column(name = "actualizadopor")
    private String actualizadopor;
    @Column(name = "fotografia")
    private byte[] fotografia;
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
    @OneToMany(mappedBy = "persona")
    private List<Pacientes> pacientesList;
    @OneToMany(mappedBy = "persona")
    private List<Profesionales> profesionalesList;
    @JoinColumn(name = "genero", referencedColumnName = "id")
    @ManyToOne
    private Parametros genero;

    public Personas() {
    }

    public Personas(Integer id) {
        this.id = id;
    }

    public Personas(Integer id, String cedula) {
        this.id = id;
        this.cedula = cedula;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Parametros getGenero() {
        return genero;
    }

    public void setGenero(Parametros genero) {
        this.genero = genero;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
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

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
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
    public List<Pacientes> getPacientesList() {
        return pacientesList;
    }

    public void setPacientesList(List<Pacientes> pacientesList) {
        this.pacientesList = pacientesList;
    }

    @XmlTransient
    public List<Profesionales> getProfesionalesList() {
        return profesionalesList;
    }

    public void setProfesionalesList(List<Profesionales> profesionalesList) {
        this.profesionalesList = profesionalesList;
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
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return (apellidos != null ? apellidos : "") + " " + (nombres != null ? nombres : "");
    }

    public static LocalDate getLocalDateFromDate(Date date) {
        return LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
    }

    public String getEdad() {
        if (this.fecha == null) {
            return "";
        }
        LocalDate nacimiento = getLocalDateFromDate(this.fecha);
        LocalDate ahora = LocalDate.now();
        Period periodo = Period.between(nacimiento, ahora);
        return periodo.getYears() + " años, " + periodo.getMonths() + " meses y " + periodo.getDays() + " días.";
    }

    public String getEdad(Date fecha) {
        if (this.fecha == null) {
            return "";
        }
        LocalDate nacimiento = getLocalDateFromDate(this.fecha);
        LocalDate ahora = getLocalDateFromDate(fecha);
        Period periodo = Period.between(nacimiento, ahora);
        return periodo.getYears() + " años, " + periodo.getMonths() + " meses y " + periodo.getDays() + " días.";
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
