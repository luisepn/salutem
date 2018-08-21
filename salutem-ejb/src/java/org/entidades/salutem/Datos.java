/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entidades.salutem;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "datos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Datos.findAll", query = "SELECT d FROM Datos d")
    , @NamedQuery(name = "Datos.findById", query = "SELECT d FROM Datos d WHERE d.id = :id")
    , @NamedQuery(name = "Datos.findByClasificador", query = "SELECT d FROM Datos d WHERE d.clasificador = :clasificador")
    , @NamedQuery(name = "Datos.findByIdentificador", query = "SELECT d FROM Datos d WHERE d.identificador = :identificador")
    , @NamedQuery(name = "Datos.findByOrdengrupo", query = "SELECT d FROM Datos d WHERE d.ordengrupo = :ordengrupo")
    , @NamedQuery(name = "Datos.findByGrupo", query = "SELECT d FROM Datos d WHERE d.grupo = :grupo")
    , @NamedQuery(name = "Datos.findByOrden", query = "SELECT d FROM Datos d WHERE d.orden = :orden")
    , @NamedQuery(name = "Datos.findByNombre", query = "SELECT d FROM Datos d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "Datos.findByTexto", query = "SELECT d FROM Datos d WHERE d.texto = :texto")
    , @NamedQuery(name = "Datos.findByBooleano", query = "SELECT d FROM Datos d WHERE d.booleano = :booleano")
    , @NamedQuery(name = "Datos.findByEntero", query = "SELECT d FROM Datos d WHERE d.entero = :entero")
    , @NamedQuery(name = "Datos.findByDecimal", query = "SELECT d FROM Datos d WHERE d.decimal = :decimal")
    , @NamedQuery(name = "Datos.findByFecha", query = "SELECT d FROM Datos d WHERE d.fecha = :fecha")
    , @NamedQuery(name = "Datos.findByHora", query = "SELECT d FROM Datos d WHERE d.hora = :hora")
    , @NamedQuery(name = "Datos.findByFechahora", query = "SELECT d FROM Datos d WHERE d.fechahora = :fechahora")
    , @NamedQuery(name = "Datos.findByCreado", query = "SELECT d FROM Datos d WHERE d.creado = :creado")
    , @NamedQuery(name = "Datos.findByCreadopor", query = "SELECT d FROM Datos d WHERE d.creadopor = :creadopor")
    , @NamedQuery(name = "Datos.findByActualizado", query = "SELECT d FROM Datos d WHERE d.actualizado = :actualizado")
    , @NamedQuery(name = "Datos.findByActualizadopor", query = "SELECT d FROM Datos d WHERE d.actualizadopor = :actualizadopor")
    , @NamedQuery(name = "Datos.findByActivo", query = "SELECT d FROM Datos d WHERE d.activo = :activo")})
public class Datos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "clasificador")
    private String clasificador;
    @Column(name = "identificador")
    private Integer identificador;
    @Column(name = "ordengrupo")
    private Integer ordengrupo;
    @Size(max = 2147483647)
    @Column(name = "grupo")
    private String grupo;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "opciones")
    private String opciones;
    @Size(max = 2147483647)
    @Column(name = "texto")
    private String texto;
    @Column(name = "booleano")
    private Boolean booleano;
    @Column(name = "entero")
    private Integer entero;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "decimal")
    private Double decimal;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Column(name = "fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Size(max = 2147483647)
    @Column(name = "seleccion")
    private String seleccion;
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
    @JoinColumn(name = "archivo", referencedColumnName = "id")
    @ManyToOne
    private Archivos archivo;
    @JoinColumn(name = "tipo", referencedColumnName = "id")
    @ManyToOne
    private Parametros tipo;

    public Datos() {
    }

    public Datos(Integer id) {
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

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public Integer getOrdengrupo() {
        return ordengrupo;
    }

    public void setOrdengrupo(Integer ordengrupo) {
        this.ordengrupo = ordengrupo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Boolean getBooleano() {
        return booleano;
    }

    public void setBooleano(Boolean booleano) {
        this.booleano = booleano;
    }

    public Integer getEntero() {
        return entero;
    }

    public void setEntero(Integer entero) {
        this.entero = entero;
    }

    public Double getDecimal() {
        return decimal;
    }

    public void setDecimal(Double decimal) {
        this.decimal = decimal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public String getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
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

    public Archivos getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivos archivo) {
        this.archivo = archivo;
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
        if (!(object instanceof Datos)) {
            return false;
        }
        Datos other = (Datos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.entidades.salutem.Datos[ id=" + id + " ]";
    }

    public JsonObject getOpcionesJson() {
        JsonParser parser = new JsonParser();
        return parser.parse(this.opciones).getAsJsonObject();
    }

    public JsonObject getSeleccionJson() {
        JsonParser parser = new JsonParser();
        return parser.parse(this.seleccion).getAsJsonObject();
    }

}
