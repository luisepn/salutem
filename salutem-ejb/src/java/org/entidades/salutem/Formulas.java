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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "formulas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formulas.findAll", query = "SELECT f FROM Formulas f")
    , @NamedQuery(name = "Formulas.findById", query = "SELECT f FROM Formulas f WHERE f.id = :id")
    , @NamedQuery(name = "Formulas.findByLod", query = "SELECT f FROM Formulas f WHERE f.lod = :lod")
    , @NamedQuery(name = "Formulas.findByLoi", query = "SELECT f FROM Formulas f WHERE f.loi = :loi")
    , @NamedQuery(name = "Formulas.findByAvscod", query = "SELECT f FROM Formulas f WHERE f.avscod = :avscod")
    , @NamedQuery(name = "Formulas.findByAvscoi", query = "SELECT f FROM Formulas f WHERE f.avscoi = :avscoi")
    , @NamedQuery(name = "Formulas.findByAvccod", query = "SELECT f FROM Formulas f WHERE f.avccod = :avccod")
    , @NamedQuery(name = "Formulas.findByAvccoi", query = "SELECT f FROM Formulas f WHERE f.avccoi = :avccoi")
    , @NamedQuery(name = "Formulas.findByEsferaod", query = "SELECT f FROM Formulas f WHERE f.esferaod = :esferaod")
    , @NamedQuery(name = "Formulas.findByEsferaoi", query = "SELECT f FROM Formulas f WHERE f.esferaoi = :esferaoi")
    , @NamedQuery(name = "Formulas.findByCilindrood", query = "SELECT f FROM Formulas f WHERE f.cilindrood = :cilindrood")
    , @NamedQuery(name = "Formulas.findByCilindrooi", query = "SELECT f FROM Formulas f WHERE f.cilindrooi = :cilindrooi")
    , @NamedQuery(name = "Formulas.findByEjeod", query = "SELECT f FROM Formulas f WHERE f.ejeod = :ejeod")
    , @NamedQuery(name = "Formulas.findByEjeoi", query = "SELECT f FROM Formulas f WHERE f.ejeoi = :ejeoi")
    , @NamedQuery(name = "Formulas.findByAddod", query = "SELECT f FROM Formulas f WHERE f.addod = :addod")
    , @NamedQuery(name = "Formulas.findByAddoi", query = "SELECT f FROM Formulas f WHERE f.addoi = :addoi")
    , @NamedQuery(name = "Formulas.findByDpod", query = "SELECT f FROM Formulas f WHERE f.dpod = :dpod")
    , @NamedQuery(name = "Formulas.findByDpoi", query = "SELECT f FROM Formulas f WHERE f.dpoi = :dpoi")
    , @NamedQuery(name = "Formulas.findByAvod", query = "SELECT f FROM Formulas f WHERE f.avod = :avod")
    , @NamedQuery(name = "Formulas.findByAvoi", query = "SELECT f FROM Formulas f WHERE f.avoi = :avoi")
    , @NamedQuery(name = "Formulas.findByAltura", query = "SELECT f FROM Formulas f WHERE f.altura = :altura")})
public class Formulas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "lod")
    private String lod;
    @Size(max = 2147483647)
    @Column(name = "loi")
    private String loi;
    @Size(max = 2147483647)
    @Column(name = "avscod")
    private String avscod;
    @Size(max = 2147483647)
    @Column(name = "avscoi")
    private String avscoi;
    @Size(max = 2147483647)
    @Column(name = "avccod")
    private String avccod;
    @Size(max = 2147483647)
    @Column(name = "avccoi")
    private String avccoi;
    @Size(max = 2147483647)
    @Column(name = "esferaod")
    private String esferaod;
    @Size(max = 2147483647)
    @Column(name = "esferaoi")
    private String esferaoi;
    @Size(max = 2147483647)
    @Column(name = "cilindrood")
    private String cilindrood;
    @Size(max = 2147483647)
    @Column(name = "cilindrooi")
    private String cilindrooi;
    @Size(max = 2147483647)
    @Column(name = "ejeod")
    private String ejeod;
    @Size(max = 2147483647)
    @Column(name = "ejeoi")
    private String ejeoi;
    @Size(max = 2147483647)
    @Column(name = "addod")
    private String addod;
    @Size(max = 2147483647)
    @Column(name = "addoi")
    private String addoi;
    @Size(max = 2147483647)
    @Column(name = "dpod")
    private String dpod;
    @Size(max = 2147483647)
    @Column(name = "dpoi")
    private String dpoi;
    @Size(max = 2147483647)
    @Column(name = "avod")
    private String avod;
    @Size(max = 2147483647)
    @Column(name = "avoi")
    private String avoi;
    @Size(max = 2147483647)
    @Column(name = "altura")
    private String altura;
    @OneToOne(mappedBy = "formula")
    private Ordenes ordenes;
    @JoinColumn(name = "consulta", referencedColumnName = "id")
    @OneToOne
    private Consultas consulta;
    @JoinColumn(name = "material", referencedColumnName = "id")
    @ManyToOne
    private Materiales material;
    @JoinColumn(name = "tratamiento", referencedColumnName = "id")
    @ManyToOne
    private Parametros tratamiento;

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

    public String getLod() {
        return lod;
    }

    public void setLod(String lod) {
        this.lod = lod;
    }

    public String getLoi() {
        return loi;
    }

    public void setLoi(String loi) {
        this.loi = loi;
    }

    public String getAvscod() {
        return avscod;
    }

    public void setAvscod(String avscod) {
        this.avscod = avscod;
    }

    public String getAvscoi() {
        return avscoi;
    }

    public void setAvscoi(String avscoi) {
        this.avscoi = avscoi;
    }

    public String getAvccod() {
        return avccod;
    }

    public void setAvccod(String avccod) {
        this.avccod = avccod;
    }

    public String getAvccoi() {
        return avccoi;
    }

    public void setAvccoi(String avccoi) {
        this.avccoi = avccoi;
    }

    public String getEsferaod() {
        return esferaod;
    }

    public void setEsferaod(String esferaod) {
        this.esferaod = esferaod;
    }

    public String getEsferaoi() {
        return esferaoi;
    }

    public void setEsferaoi(String esferaoi) {
        this.esferaoi = esferaoi;
    }

    public String getCilindrood() {
        return cilindrood;
    }

    public void setCilindrood(String cilindrood) {
        this.cilindrood = cilindrood;
    }

    public String getCilindrooi() {
        return cilindrooi;
    }

    public void setCilindrooi(String cilindrooi) {
        this.cilindrooi = cilindrooi;
    }

    public String getEjeod() {
        return ejeod;
    }

    public void setEjeod(String ejeod) {
        this.ejeod = ejeod;
    }

    public String getEjeoi() {
        return ejeoi;
    }

    public void setEjeoi(String ejeoi) {
        this.ejeoi = ejeoi;
    }

    public String getAddod() {
        return addod;
    }

    public void setAddod(String addod) {
        this.addod = addod;
    }

    public String getAddoi() {
        return addoi;
    }

    public void setAddoi(String addoi) {
        this.addoi = addoi;
    }

    public String getDpod() {
        return dpod;
    }

    public void setDpod(String dpod) {
        this.dpod = dpod;
    }

    public String getDpoi() {
        return dpoi;
    }

    public void setDpoi(String dpoi) {
        this.dpoi = dpoi;
    }

    public String getAvod() {
        return avod;
    }

    public void setAvod(String avod) {
        this.avod = avod;
    }

    public String getAvoi() {
        return avoi;
    }

    public void setAvoi(String avoi) {
        this.avoi = avoi;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public Ordenes getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(Ordenes ordenes) {
        this.ordenes = ordenes;
    }

    public Consultas getConsulta() {
        return consulta;
    }

    public void setConsulta(Consultas consulta) {
        this.consulta = consulta;
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
    
}
