/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "ingrediente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingrediente.findAll", query = "SELECT i FROM Ingrediente i")
    , @NamedQuery(name = "Ingrediente.findByCodIngrediente", query = "SELECT i FROM Ingrediente i WHERE i.codIngrediente = :codIngrediente")
    , @NamedQuery(name = "Ingrediente.findByNombre", query = "SELECT i FROM Ingrediente i WHERE i.nombre = :nombre")
    , @NamedQuery(name = "Ingrediente.findByKcal", query = "SELECT i FROM Ingrediente i WHERE i.kcal = :kcal")})
public class Ingrediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_ingrediente")
    private Integer codIngrediente;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "kcal")
    private float kcal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ingrediente1")
    private List<AlergenosIngrediente> alergenosIngredienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ingrediente1")
    private List<ListaCompra> listaCompraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ingrediente1")
    private List<IngredientesReceta> ingredientesRecetaList;

    public Ingrediente() {
    }

    public Ingrediente(Integer codIngrediente) {
        this.codIngrediente = codIngrediente;
    }

    public Ingrediente(Integer codIngrediente, String nombre, float kcal) {
        this.codIngrediente = codIngrediente;
        this.nombre = nombre;
        this.kcal = kcal;
    }

    public Integer getCodIngrediente() {
        return codIngrediente;
    }

    public void setCodIngrediente(Integer codIngrediente) {
        this.codIngrediente = codIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getKcal() {
        return kcal;
    }

    public void setKcal(float kcal) {
        this.kcal = kcal;
    }

    @XmlTransient
    public List<AlergenosIngrediente> getAlergenosIngredienteList() {
        return alergenosIngredienteList;
    }

    public void setAlergenosIngredienteList(List<AlergenosIngrediente> alergenosIngredienteList) {
        this.alergenosIngredienteList = alergenosIngredienteList;
    }

    @XmlTransient
    public List<ListaCompra> getListaCompraList() {
        return listaCompraList;
    }

    public void setListaCompraList(List<ListaCompra> listaCompraList) {
        this.listaCompraList = listaCompraList;
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
        hash += (codIngrediente != null ? codIngrediente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingrediente)) {
            return false;
        }
        Ingrediente other = (Ingrediente) object;
        if ((this.codIngrediente == null && other.codIngrediente != null) || (this.codIngrediente != null && !this.codIngrediente.equals(other.codIngrediente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Ingrediente[ codIngrediente=" + codIngrediente + " ]";
    }
    
}
