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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "prescripciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prescripciones.findAll", query = "SELECT p FROM Prescripciones p")
    , @NamedQuery(name = "Prescripciones.findById", query = "SELECT p FROM Prescripciones p WHERE p.id = :id")
    , @NamedQuery(name = "Prescripciones.findByMedicamento", query = "SELECT p FROM Prescripciones p WHERE p.medicamento = :medicamento")
    , @NamedQuery(name = "Prescripciones.findByDosis", query = "SELECT p FROM Prescripciones p WHERE p.dosis = :dosis")
    , @NamedQuery(name = "Prescripciones.findByFrecuencia", query = "SELECT p FROM Prescripciones p WHERE p.frecuencia = :frecuencia")
    , @NamedQuery(name = "Prescripciones.findByDuracion", query = "SELECT p FROM Prescripciones p WHERE p.duracion = :duracion")
    , @NamedQuery(name = "Prescripciones.findByAdvertencias", query = "SELECT p FROM Prescripciones p WHERE p.advertencias = :advertencias")})
public class Prescripciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "medicamento")
    private String medicamento;
    @Size(max = 2147483647)
    @Column(name = "dosis")
    private String dosis;
    @Size(max = 2147483647)
    @Column(name = "frecuencia")
    private String frecuencia;
    @Size(max = 2147483647)
    @Column(name = "duracion")
    private String duracion;
    @Size(max = 2147483647)
    @Column(name = "advertencias")
    private String advertencias;
    @JoinColumn(name = "atencion", referencedColumnName = "id")
    @ManyToOne
    private Atenciones atencion;

    public Prescripciones() {
    }

    public Prescripciones(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getAdvertencias() {
        return advertencias;
    }

    public void setAdvertencias(String advertencias) {
        this.advertencias = advertencias;
    }

    public Atenciones getAtencion() {
        return atencion;
    }

    public void setAtencion(Atenciones atencion) {
        this.atencion = atencion;
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
        if (!(object instanceof Prescripciones)) {
            return false;
        }
        Prescripciones other = (Prescripciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Prescripciones[ id=" + id + " ]";
    }
    
}
