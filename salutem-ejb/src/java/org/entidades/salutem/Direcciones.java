/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entidades.salutem;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fernando
 */
@Entity
@Table(name = "direcciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direcciones.findAll", query = "SELECT d FROM Direcciones d")
    , @NamedQuery(name = "Direcciones.findById", query = "SELECT d FROM Direcciones d WHERE d.id = :id")
    , @NamedQuery(name = "Direcciones.findByPrimaria", query = "SELECT d FROM Direcciones d WHERE d.primaria = :primaria")
    , @NamedQuery(name = "Direcciones.findByNumero", query = "SELECT d FROM Direcciones d WHERE d.numero = :numero")
    , @NamedQuery(name = "Direcciones.findBySecundaria", query = "SELECT d FROM Direcciones d WHERE d.secundaria = :secundaria")
    , @NamedQuery(name = "Direcciones.findByPiso", query = "SELECT d FROM Direcciones d WHERE d.piso = :piso")
    , @NamedQuery(name = "Direcciones.findByReferencia", query = "SELECT d FROM Direcciones d WHERE d.referencia = :referencia")
    , @NamedQuery(name = "Direcciones.findByFijo", query = "SELECT d FROM Direcciones d WHERE d.fijo = :fijo")
    , @NamedQuery(name = "Direcciones.findByMovil", query = "SELECT d FROM Direcciones d WHERE d.movil = :movil")
    , @NamedQuery(name = "Direcciones.findByCiudad", query = "SELECT d FROM Direcciones d WHERE d.ciudad = :ciudad")})
public class Direcciones implements Serializable {

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
    @Column(name = "piso")
    private String piso;
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
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToOne(mappedBy = "direccion")
    private Instituciones instituciones;
    @OneToOne(mappedBy = "direccion")
    private Personas personas;

    public Direcciones() {
    }

    public Direcciones(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Direcciones)) {
            return false;
        }
        Direcciones other = (Direcciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return (primaria != null && numero != null && secundaria != null)
                ? primaria + " " + numero + " y " + secundaria
                : (primaria != null ? primaria : "") + " "
                + (numero != null ? numero : "")
                + (secundaria != null ? secundaria : "");
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

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
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

}
