/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entidades.salutem;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.utilitarios.salutem.Items;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "campos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campos.findAll", query = "SELECT c FROM Campos c")
    , @NamedQuery(name = "Campos.findById", query = "SELECT c FROM Campos c WHERE c.id = :id")
    , @NamedQuery(name = "Campos.findByClasificador", query = "SELECT c FROM Campos c WHERE c.clasificador = :clasificador")
    , @NamedQuery(name = "Campos.findByCodigo", query = "SELECT c FROM Campos c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Campos.findByNombre", query = "SELECT c FROM Campos c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Campos.findByDescripcion", query = "SELECT c FROM Campos c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Campos.findByCreado", query = "SELECT c FROM Campos c WHERE c.creado = :creado")
    , @NamedQuery(name = "Campos.findByCreadopor", query = "SELECT c FROM Campos c WHERE c.creadopor = :creadopor")
    , @NamedQuery(name = "Campos.findByActualizado", query = "SELECT c FROM Campos c WHERE c.actualizado = :actualizado")
    , @NamedQuery(name = "Campos.findByActualizadopor", query = "SELECT c FROM Campos c WHERE c.actualizadopor = :actualizadopor")
    , @NamedQuery(name = "Campos.findByActivo", query = "SELECT c FROM Campos c WHERE c.activo = :activo")})
public class Campos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "clasificador")
    private String clasificador;
    @Column(name = "codigo")
    private Integer codigo;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
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
    @JoinColumn(name = "institucion", referencedColumnName = "id")
    @ManyToOne
    private Instituciones institucion;
    @JoinColumn(name = "grupo", referencedColumnName = "id")
    @ManyToOne
    private Parametros grupo;
    @JoinColumn(name = "tipo", referencedColumnName = "id")
    @ManyToOne
    private Parametros tipo;

    @Transient
    private String opciones;

    public Campos() {
    }

    public Campos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClasificador() {
        return clasificador;
    }

    public void setClasificador(String clasificador) {
        this.clasificador = clasificador;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
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

    public Instituciones getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    public Parametros getGrupo() {
        return grupo;
    }

    public void setGrupo(Parametros grupo) {
        this.grupo = grupo;
    }

    public Parametros getTipo() {
        return tipo;
    }

    public void setTipo(Parametros tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof Campos)) {
            return false;
        }
        Campos other = (Campos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Campos[ id=" + id + " ]";
    }

    public JsonObject getOpcionesJson() {
        JsonParser parser = new JsonParser();
        return parser.parse(this.opciones).getAsJsonObject();
    }

    public JsonObject getOpcionesJsonFromList(List<Items> opciones) {
        JsonObject json = new JsonObject();
        for (Items item : opciones) {
            json.addProperty(item.getClave() + "", item.getValor());
        }
        return json;
    }

    public List<Items> getOpcionesList() {
        List<Items> retorno = new LinkedList<>();
        JsonObject json = getOpcionesJson();
        if (!json.isJsonNull()) {
            for (Map.Entry<String, JsonElement> e : json.entrySet()) {
                retorno.add(new Items(Integer.parseInt(e.getKey()), String.valueOf(e.getValue()).replace("\"", "")));
            }
        }
        return retorno;
    }
}
