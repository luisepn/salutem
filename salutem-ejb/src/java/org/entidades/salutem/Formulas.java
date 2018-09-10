/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entidades.salutem;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Type;
import org.utilitarios.salutem.Ojos;
import org.utilitarios.salutem.RxFinal;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "formulas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formulas.findAll", query = "SELECT f FROM Formulas f")
    , @NamedQuery(name = "Formulas.findById", query = "SELECT f FROM Formulas f WHERE f.id = :id")
    , @NamedQuery(name = "Formulas.findByAltura", query = "SELECT f FROM Formulas f WHERE f.altura = :altura")
    , @NamedQuery(name = "Formulas.findByDescripcion", query = "SELECT f FROM Formulas f WHERE f.descripcion = :descripcion")
    , @NamedQuery(name = "Formulas.findByCreado", query = "SELECT f FROM Formulas f WHERE f.creado = :creado")
    , @NamedQuery(name = "Formulas.findByCreadopor", query = "SELECT f FROM Formulas f WHERE f.creadopor = :creadopor")
    , @NamedQuery(name = "Formulas.findByActualizado", query = "SELECT f FROM Formulas f WHERE f.actualizado = :actualizado")
    , @NamedQuery(name = "Formulas.findByActualizadopor", query = "SELECT f FROM Formulas f WHERE f.actualizadopor = :actualizadopor")
    , @NamedQuery(name = "Formulas.findByActivo", query = "SELECT f FROM Formulas f WHERE f.activo = :activo")})
public class Formulas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Type(type = "jsonb")
    @Column(columnDefinition = "lensometria")
    private Ojos lensometria;
    @Type(type = "jsonb")
    @Column(columnDefinition = "agudezavisualsincristal")
    private Ojos agudezavisualsincristal;
    @Type(type = "jsonb")
    @Column(columnDefinition = "agudezavisualconcristal")
    private Ojos agudezavisualconcristal;
    @Type(type = "jsonb")
    @Column(columnDefinition = "esfera")
    private Ojos esfera;
    @Type(type = "jsonb")
    @Column(columnDefinition = "cilindro")
    private Ojos cilindro;
    @Type(type = "jsonb")
    @Column(columnDefinition = "eje")
    private Ojos eje;
    @Type(type = "jsonb")
    @Column(columnDefinition = "adicion")
    private Ojos adicion;
    @Type(type = "jsonb")
    @Column(columnDefinition = "distanciapupilar")
    private Ojos distanciapupilar;
    @Type(type = "jsonb")
    @Column(columnDefinition = "agudezavisual")
    private Ojos agudezavisual;
    @Size(max = 2147483647)
    @Column(name = "altura")
    private String altura;
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
    @JoinColumn(name = "atencion", referencedColumnName = "id")
    @OneToOne
    private Atenciones atencion;
    @JoinColumn(name = "material", referencedColumnName = "id")
    @ManyToOne
    private Materiales material;
    @JoinColumn(name = "tratamiento", referencedColumnName = "id")
    @ManyToOne
    private Parametros tratamiento;
    @OneToOne(mappedBy = "formula")
    private Ordenes orden;

    @Transient
    private List<RxFinal> listaRxFinal;

    public Formulas() {
    }

    public Formulas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ojos getLensometria() {
        return lensometria;
    }

    public void setLensometria(Ojos lensometria) {
        this.lensometria = lensometria;
    }

    public Ojos getAgudezavisualsincristal() {
        return agudezavisualsincristal;
    }

    public void setAgudezavisualsincristal(Ojos agudezavisualsincristal) {
        this.agudezavisualsincristal = agudezavisualsincristal;
    }

    public Ojos getAgudezavisualconcristal() {
        return agudezavisualconcristal;
    }

    public void setAgudezavisualconcristal(Ojos agudezavisualconcristal) {
        this.agudezavisualconcristal = agudezavisualconcristal;
    }

    public Ojos getEsfera() {
        return esfera;
    }

    public void setEsfera(Ojos esfera) {
        this.esfera = esfera;
    }

    public Ojos getCilindro() {
        return cilindro;
    }

    public void setCilindro(Ojos cilindro) {
        this.cilindro = cilindro;
    }

    public Ojos getEje() {
        return eje;
    }

    public void setEje(Ojos eje) {
        this.eje = eje;
    }

    public Ojos getAdicion() {
        return adicion;
    }

    public void setAdicion(Ojos adicion) {
        this.adicion = adicion;
    }

    public Ojos getDistanciapupilar() {
        return distanciapupilar;
    }

    public void setDistanciapupilar(Ojos distanciapupilar) {
        this.distanciapupilar = distanciapupilar;
    }

    public Ojos getAgudezavisual() {
        return agudezavisual;
    }

    public void setAgudezavisual(Ojos agudezavisual) {
        this.agudezavisual = agudezavisual;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
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

    public Atenciones getAtencion() {
        return atencion;
    }

    public void setAtencion(Atenciones atencion) {
        this.atencion = atencion;
    }

    public Materiales getMaterial() {
        return material;
    }

    public void setMaterial(Materiales material) {
        this.material = material;
    }

    public Parametros getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Parametros tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Ordenes getOrden() {
        return orden;
    }

    public void setOrden(Ordenes orden) {
        this.orden = orden;
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
        if (!(object instanceof Formulas)) {
            return false;
        }
        Formulas other = (Formulas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Formulas[ id=" + id + " ]";
    }

    /**
     * @return the listaRxFinal
     */
    public List<RxFinal> getListaRxFinal() {
        listaRxFinal = new LinkedList<>();
        listaRxFinal.add(new RxFinal("OD", esfera.getD(), cilindro.getD(), eje.getD(), adicion.getD(), distanciapupilar.getD(), agudezavisual.getD()));
        listaRxFinal.add(new RxFinal("OI", esfera.getI(), cilindro.getI(), eje.getI(), adicion.getI(), distanciapupilar.getI(), agudezavisual.getI()));
        return this.listaRxFinal;
    }

    /**
     * @param listaRxFinal the listaRxFinal to set
     */
    public void setListaRxFinal(List<RxFinal> listaRxFinal) {
        this.listaRxFinal = listaRxFinal;
    }

}
