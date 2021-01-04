/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CRIS
 */
@Entity
@Table(name = "solicitud_ingredientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudIngredientes.findAll", query = "SELECT s FROM SolicitudIngredientes s")
    , @NamedQuery(name = "SolicitudIngredientes.findByCodSolicitud", query = "SELECT s FROM SolicitudIngredientes s WHERE s.codSolicitud = :codSolicitud")
    , @NamedQuery(name = "SolicitudIngredientes.findByNombre", query = "SELECT s FROM SolicitudIngredientes s WHERE s.nombre = :nombre")
    , @NamedQuery(name = "SolicitudIngredientes.findByDescripcion", query = "SELECT s FROM SolicitudIngredientes s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "SolicitudIngredientes.findByCantidad", query = "SELECT s FROM SolicitudIngredientes s WHERE s.cantidad = :cantidad")})
public class SolicitudIngredientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_solicitud")
    private Integer codSolicitud;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private String cantidad;
    @JoinColumn(name = "usuario", referencedColumnName = "nick")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "receta", referencedColumnName = "cod_receta")
    @ManyToOne(optional = false)
    private Receta receta;

    public SolicitudIngredientes() {
    }

    public SolicitudIngredientes(Integer codSolicitud) {
        this.codSolicitud = codSolicitud;
    }

    public SolicitudIngredientes(Integer codSolicitud, String nombre, String cantidad) {
        this.codSolicitud = codSolicitud;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public Integer getCodSolicitud() {
        return codSolicitud;
    }

    public void setCodSolicitud(Integer codSolicitud) {
        this.codSolicitud = codSolicitud;
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSolicitud != null ? codSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudIngredientes)) {
            return false;
        }
        SolicitudIngredientes other = (SolicitudIngredientes) object;
        if ((this.codSolicitud == null && other.codSolicitud != null) || (this.codSolicitud != null && !this.codSolicitud.equals(other.codSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.SolicitudIngredientes[ codSolicitud=" + codSolicitud + " ]";
    }
    
}
