/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author CRIS
 */
@Entity
@Table(name = "dieta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dieta.findAll", query = "SELECT d FROM Dieta d")
    , @NamedQuery(name = "Dieta.findByCodDieta", query = "SELECT d FROM Dieta d WHERE d.codDieta = :codDieta")
    , @NamedQuery(name = "Dieta.findByTipo", query = "SELECT d FROM Dieta d WHERE d.tipo = :tipo")})
public class Dieta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_dieta")
    private Integer codDieta;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @ManyToMany(mappedBy = "dietaList")
    private List<Receta> recetaList;

    public Dieta() {
    }

    public Dieta(Integer codDieta) {
        this.codDieta = codDieta;
    }

    public Dieta(Integer codDieta, String tipo) {
        this.codDieta = codDieta;
        this.tipo = tipo;
    }

    public Integer getCodDieta() {
        return codDieta;
    }

    public void setCodDieta(Integer codDieta) {
        this.codDieta = codDieta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Receta> getRecetaList() {
        return recetaList;
    }

    public void setRecetaList(List<Receta> recetaList) {
        this.recetaList = recetaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDieta != null ? codDieta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dieta)) {
            return false;
        }
        Dieta other = (Dieta) object;
        if ((this.codDieta == null && other.codDieta != null) || (this.codDieta != null && !this.codDieta.equals(other.codDieta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Dieta[ codDieta=" + codDieta + " ]";
    }
    
}
