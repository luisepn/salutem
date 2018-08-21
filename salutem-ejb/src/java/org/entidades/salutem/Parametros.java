/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entidades.salutem;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fernando
 */
@Entity
@Table(name = "parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p")
    , @NamedQuery(name = "Parametros.findById", query = "SELECT p FROM Parametros p WHERE p.id = :id")
    , @NamedQuery(name = "Parametros.findByNombre", query = "SELECT p FROM Parametros p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Parametros.findByCodigo", query = "SELECT p FROM Parametros p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Parametros.findByDescripcion", query = "SELECT p FROM Parametros p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Parametros.findByParametros", query = "SELECT p FROM Parametros p WHERE p.parametros = :parametros")
    , @NamedQuery(name = "Parametros.findByActivo", query = "SELECT p FROM Parametros p WHERE p.activo = :activo")
    , @NamedQuery(name = "Parametros.findByCreado", query = "SELECT p FROM Parametros p WHERE p.creado = :creado")
    , @NamedQuery(name = "Parametros.findByCreadopor", query = "SELECT p FROM Parametros p WHERE p.creadopor = :creadopor")
    , @NamedQuery(name = "Parametros.findByActualizado", query = "SELECT p FROM Parametros p WHERE p.actualizado = :actualizado")
    , @NamedQuery(name = "Parametros.findByActualizadopor", query = "SELECT p FROM Parametros p WHERE p.actualizadopor = :actualizadopor")})
public class Parametros implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2147483647)
    @Column(name = "parametros")
    private String parametros;
    @Size(max = 2147483647)
    @Column(name = "creadopor")
    private String creadopor;
    @Size(max = 2147483647)
    @Column(name = "actualizadopor")
    private String actualizadopor;
    @OneToMany(mappedBy = "tipo")
    private List<Datos> datosList;
    @OneToMany(mappedBy = "tipo")
    private List<Campos> camposList;
    @OneToMany(mappedBy = "especialidad")
    private List<Atenciones> atencionesList;
    @OneToMany(mappedBy = "dia")
    private List<Horarios> horariosList;
    @OneToMany(mappedBy = "especialidad")
    private List<Profesionales> profesionalesList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;
    @OneToMany(mappedBy = "tratamiento")
    private List<Formulas> formulasList;
    @OneToMany(mappedBy = "grupo")
    private List<Perfiles> perfilesList;
    @OneToMany(mappedBy = "grupo")
    private List<Usuarios> usuariosList;
    @OneToMany(mappedBy = "modulo")
    private List<Usuarios> usuariosList1;
    @JoinColumn(name = "maestro", referencedColumnName = "id")
    @ManyToOne
    private Maestros maestro;
    @OneToMany(mappedBy = "genero")
    private List<Personas> personasList;
    @OneToMany(mappedBy = "foco")
    private List<Materiales> materialesList;
    @OneToMany(mappedBy = "tipo")
    private List<Materiales> materialesList1;
    @OneToMany(mappedBy = "modulo")
    private List<Menus> menusList;

    public Parametros() {
    }

    public Parametros(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public List<Formulas> getFormulasList() {
        return formulasList;
    }

    public void setFormulasList(List<Formulas> formulasList) {
        this.formulasList = formulasList;
    }

    @XmlTransient
    public List<Perfiles> getPerfilesList() {
        return perfilesList;
    }

    public void setPerfilesList(List<Perfiles> perfilesList) {
        this.perfilesList = perfilesList;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList1() {
        return usuariosList1;
    }

    public void setUsuariosList1(List<Usuarios> usuariosList1) {
        this.usuariosList1 = usuariosList1;
    }

    public Maestros getMaestro() {
        return maestro;
    }

    public void setMaestro(Maestros maestro) {
        this.maestro = maestro;
    }

    @XmlTransient
    public List<Personas> getPersonasList() {
        return personasList;
    }

    public void setPersonasList(List<Personas> personasList) {
        this.personasList = personasList;
    }

    @XmlTransient
    public List<Materiales> getMaterialesList() {
        return materialesList;
    }

    public void setMaterialesList(List<Materiales> materialesList) {
        this.materialesList = materialesList;
    }

    @XmlTransient
    public List<Materiales> getMaterialesList1() {
        return materialesList1;
    }

    public void setMaterialesList1(List<Materiales> materialesList1) {
        this.materialesList1 = materialesList1;
    }

    @XmlTransient
    public List<Menus> getMenusList() {
        return menusList;
    }

    public void setMenusList(List<Menus> menusList) {
        this.menusList = menusList;
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
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @XmlTransient
    public List<Horarios> getHorariosList() {
        return horariosList;
    }

    public void setHorariosList(List<Horarios> horariosList) {
        this.horariosList = horariosList;
    }

    @XmlTransient
    public List<Profesionales> getProfesionalesList() {
        return profesionalesList;
    }

    public void setProfesionalesList(List<Profesionales> profesionalesList) {
        this.profesionalesList = profesionalesList;
    }
    @XmlTransient
    public List<Atenciones> getAtencionesList() {
        return atencionesList;
    }
    public void setAtencionesList(List<Atenciones> atencionesList) {
        this.atencionesList = atencionesList;
    }


    @XmlTransient
    public List<Datos> getDatosList() {
        return datosList;
    }

    public void setDatosList(List<Datos> datosList) {
        this.datosList = datosList;
    }

    @XmlTransient
    public List<Campos> getCamposList() {
        return camposList;
    }

    public void setCamposList(List<Campos> camposList) {
        this.camposList = camposList;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
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
