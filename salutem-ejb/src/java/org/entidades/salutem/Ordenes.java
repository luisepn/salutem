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
import javax.persistence.OneToOne;
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
@Table(name = "ordenes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordenes.findAll", query = "SELECT o FROM Ordenes o")
    , @NamedQuery(name = "Ordenes.findById", query = "SELECT o FROM Ordenes o WHERE o.id = :id")
    , @NamedQuery(name = "Ordenes.findByFactura", query = "SELECT o FROM Ordenes o WHERE o.factura = :factura")
    , @NamedQuery(name = "Ordenes.findByUsuario", query = "SELECT o FROM Ordenes o WHERE o.usuario = :usuario")
    , @NamedQuery(name = "Ordenes.findByRegistro", query = "SELECT o FROM Ordenes o WHERE o.registro = :registro")
    , @NamedQuery(name = "Ordenes.findByEnvio", query = "SELECT o FROM Ordenes o WHERE o.envio = :envio")
    , @NamedQuery(name = "Ordenes.findByEntrega", query = "SELECT o FROM Ordenes o WHERE o.entrega = :entrega")
    , @NamedQuery(name = "Ordenes.findByDescripcion", query = "SELECT o FROM Ordenes o WHERE o.descripcion = :descripcion")
    , @NamedQuery(name = "Ordenes.findByCreado", query = "SELECT o FROM Ordenes o WHERE o.creado = :creado")
    , @NamedQuery(name = "Ordenes.findByCreadopor", query = "SELECT o FROM Ordenes o WHERE o.creadopor = :creadopor")
    , @NamedQuery(name = "Ordenes.findByActualizado", query = "SELECT o FROM Ordenes o WHERE o.actualizado = :actualizado")
    , @NamedQuery(name = "Ordenes.findByActualizadopor", query = "SELECT o FROM Ordenes o WHERE o.actualizadopor = :actualizadopor")
    , @NamedQuery(name = "Ordenes.findByActivo", query = "SELECT o FROM Ordenes o WHERE o.activo = :activo")})
public class Ordenes implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "factura")
    private String factura;
    @Size(max = 2147483647)
    @Column(name = "usuario")
    private String usuario;
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
    @Column(name = "registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registro;
    @Column(name = "envio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date envio;
    @Column(name = "entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrega;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "formula", referencedColumnName = "id")
    @OneToOne
    private Formulas formula;
    @JoinColumn(name = "laboratorio", referencedColumnName = "id")
    @ManyToOne
    private Instituciones laboratorio;

    public Ordenes() {
    }

    public Ordenes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getRegistro() {
        return registro;
    }

    public void setRegistro(Date registro) {
        this.registro = registro;
    }

    public Date getEnvio() {
        return envio;
    }

    public void setEnvio(Date envio) {
        this.envio = envio;
    }

    public Date getEntrega() {
        return entrega;
    }

    public void setEntrega(Date entrega) {
        this.entrega = entrega;
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

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    public Instituciones getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Instituciones laboratorio) {
        this.laboratorio = laboratorio;
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
        if (!(object instanceof Ordenes)) {
            return false;
        }
        Ordenes other = (Ordenes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Ordenes[ id=" + id + " ]";
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
