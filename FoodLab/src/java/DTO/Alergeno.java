/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author CRIS
 */
@Entity
@Table(name = "alergeno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alergeno.findAll", query = "SELECT a FROM Alergeno a")
    , @NamedQuery(name = "Alergeno.findByCodAlergeno", query = "SELECT a FROM Alergeno a WHERE a.codAlergeno = :codAlergeno")
    , @NamedQuery(name = "Alergeno.findByNombre", query = "SELECT a FROM Alergeno a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Alergeno.findByIcono", query = "SELECT a FROM Alergeno a WHERE a.icono = :icono")})
public class Alergeno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_alergeno")
    private Integer codAlergeno;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "icono")
    private String icono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alergeno1")
    private List<AlergenosIngrediente> alergenosIngredienteList;

    public Alergeno() {
    }

    public Alergeno(Integer codAlergeno) {
        this.codAlergeno = codAlergeno;
    }

    public Alergeno(Integer codAlergeno, String nombre, String descripcion, String icono) {
        this.codAlergeno = codAlergeno;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
    }

    public Integer getCodAlergeno() {
        return codAlergeno;
    }

    public void setCodAlergeno(Integer codAlergeno) {
        this.codAlergeno = codAlergeno;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    @XmlTransient
    public List<AlergenosIngrediente> getAlergenosIngredienteList() {
        return alergenosIngredienteList;
    }

    public void setAlergenosIngredienteList(List<AlergenosIngrediente> alergenosIngredienteList) {
        this.alergenosIngredienteList = alergenosIngredienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codAlergeno != null ? codAlergeno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alergeno)) {
            return false;
        }
        Alergeno other = (Alergeno) object;
        if ((this.codAlergeno == null && other.codAlergeno != null) || (this.codAlergeno != null && !this.codAlergeno.equals(other.codAlergeno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Alergeno[ codAlergeno=" + codAlergeno + " ]";
    }
    
}
