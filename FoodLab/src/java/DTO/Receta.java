/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author CRIS
 */
@Entity
@Table(name = "receta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receta.findAll", query = "SELECT r FROM Receta r")
    , @NamedQuery(name = "Receta.findByCodReceta", query = "SELECT r FROM Receta r WHERE r.codReceta = :codReceta")
    , @NamedQuery(name = "Receta.findByNombre", query = "SELECT r FROM Receta r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "Receta.findByFechaInsercion", query = "SELECT r FROM Receta r WHERE r.fechaInsercion = :fechaInsercion")
    , @NamedQuery(name = "Receta.findByFoto", query = "SELECT r FROM Receta r WHERE r.foto = :foto")
    , @NamedQuery(name = "Receta.findByTipoPlato", query = "SELECT r FROM Receta r WHERE r.tipoPlato = :tipoPlato")
    , @NamedQuery(name = "Receta.findByDificultad", query = "SELECT r FROM Receta r WHERE r.dificultad = :dificultad")
    , @NamedQuery(name = "Receta.findByAceptada", query = "SELECT r FROM Receta r WHERE r.aceptada = :aceptada")})
public class Receta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_receta")
    private Integer codReceta;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "contenido")
    private String contenido;
    @Basic(optional = false)
    @Column(name = "fecha_insercion")
    @Temporal(TemporalType.DATE)
    private Date fechaInsercion;
    @Basic(optional = false)
    @Column(name = "foto")
    private String foto;
    @Column(name = "tipo_plato")
    private String tipoPlato;
    @Column(name = "dificultad")
    private String dificultad;
    @Basic(optional = false)
    @Column(name = "aceptada")
    private boolean aceptada;
    @JoinTable(name = "dietas_receta", joinColumns = {
        @JoinColumn(name = "receta", referencedColumnName = "cod_receta")}, inverseJoinColumns = {
        @JoinColumn(name = "dieta", referencedColumnName = "cod_dieta")})
    @ManyToMany
    private List<Dieta> dietaList;
    @JoinTable(name = "recetas_fav", joinColumns = {
        @JoinColumn(name = "receta", referencedColumnName = "cod_receta")}, inverseJoinColumns = {
        @JoinColumn(name = "usuario", referencedColumnName = "nick")})
    @ManyToMany
    private List<Usuario> usuarioList;
    @JoinColumn(name = "autor", referencedColumnName = "nick")
    @ManyToOne(optional = false)
    private Usuario autor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receta")
    private List<SolicitudIngredientes> solicitudIngredientesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receta")
    private List<Comentario> comentarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receta1")
    private List<IngredientesReceta> ingredientesRecetaList;

    public Receta() {
    }

    public Receta(Integer codReceta) {
        this.codReceta = codReceta;
    }

    public Receta(Integer codReceta, String nombre, String contenido, Date fechaInsercion, String foto, boolean aceptada) {
        this.codReceta = codReceta;
        this.nombre = nombre;
        this.contenido = contenido;
        this.fechaInsercion = fechaInsercion;
        this.foto = foto;
        this.aceptada = aceptada;
    }

    public Integer getCodReceta() {
        return codReceta;
    }

    public void setCodReceta(Integer codReceta) {
        this.codReceta = codReceta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTipoPlato() {
        return tipoPlato;
    }

    public void setTipoPlato(String tipoPlato) {
        this.tipoPlato = tipoPlato;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public boolean getAceptada() {
        return aceptada;
    }

    public void setAceptada(boolean aceptada) {
        this.aceptada = aceptada;
    }

    @XmlTransient
    public List<Dieta> getDietaList() {
        return dietaList;
    }

    public void setDietaList(List<Dieta> dietaList) {
        this.dietaList = dietaList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    @XmlTransient
    public List<SolicitudIngredientes> getSolicitudIngredientesList() {
        return solicitudIngredientesList;
    }

    public void setSolicitudIngredientesList(List<SolicitudIngredientes> solicitudIngredientesList) {
        this.solicitudIngredientesList = solicitudIngredientesList;
    }

    @XmlTransient
    public List<Comentario> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
    }

    @XmlTransient
    public List<IngredientesReceta> getIngredientesRecetaList() {
        return ingredientesRecetaList;
    }

    public void setIngredientesRecetaList(List<IngredientesReceta> ingredientesRecetaList) {
        this.ingredientesRecetaList = ingredientesRecetaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codReceta != null ? codReceta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receta)) {
            return false;
        }
        Receta other = (Receta) object;
        if ((this.codReceta == null && other.codReceta != null) || (this.codReceta != null && !this.codReceta.equals(other.codReceta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Receta[ codReceta=" + codReceta + " ]";
    }
    
}
