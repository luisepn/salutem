/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entidades.salutemlogs;

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
@Table(name = "historial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historial.findAll", query = "SELECT h FROM Historial h"),
    @NamedQuery(name = "Historial.findById", query = "SELECT h FROM Historial h WHERE h.id = :id"),
    @NamedQuery(name = "Historial.findByFecha", query = "SELECT h FROM Historial h WHERE h.fecha = :fecha"),
    @NamedQuery(name = "Historial.findByUsuario", query = "SELECT h FROM Historial h WHERE h.usuario = :usuario"),
    @NamedQuery(name = "Historial.findByIp", query = "SELECT h FROM Historial h WHERE h.ip = :ip"),
    @NamedQuery(name = "Historial.findByOperacion", query = "SELECT h FROM Historial h WHERE h.operacion = :operacion"),
    @NamedQuery(name = "Historial.findByTabla", query = "SELECT h FROM Historial h WHERE h.tabla = :tabla"),
    @NamedQuery(name = "Historial.findByRegistro", query = "SELECT h FROM Historial h WHERE h.registro = :registro")})
public class Historial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 2147483647)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 2147483647)
    @Column(name = "ip")
    private String ip;
    @Column(name = "operacion")
    private Character operacion;
    @Size(max = 2147483647)
    @Column(name = "tabla")
    private String tabla;
    @Column(name = "registro")
    private Integer registro;
    @Column(name = "anterior")
    private String anterior;
    @Column(name = "nuevo")
    private String nuevo;

    public Historial() {
    }

    public Historial(Integer id) {
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Character getOperacion() {
        return operacion;
    }

    public void setOperacion(Character operacion) {
        this.operacion = operacion;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public Integer getRegistro() {
        return registro;
    }

    public void setRegistro(Integer registro) {
        this.registro = registro;
    }

    public String getAnterior() {
        return anterior;
    }

    public void setAnterior(String anterior) {
        this.anterior = anterior;
    }

    public String getNuevo() {
        return nuevo;
    }

    public void setNuevo(String nuevo) {
        this.nuevo = nuevo;
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
        if (!(object instanceof Historial)) {
            return false;
        }
        Historial other = (Historial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutemlogs.Historial[ id=" + id + " ]";
    }

    public String getAnteriorSinFormato() {
        return quitarLlaves(anterior);
    }

    public String getNuevoSinFormato() {
        return quitarLlaves(nuevo);
    }

    private String quitarLlaves(String texto) {
        if (texto != null && texto.length() > 3) {
            texto = texto.substring(2, texto.length() - 2);
            return texto.replace("\":", "' =").replace("\"", "'");
        }
        return null;
    }

}
