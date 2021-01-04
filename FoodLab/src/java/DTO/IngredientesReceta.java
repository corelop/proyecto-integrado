/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "ingredientes_receta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IngredientesReceta.findAll", query = "SELECT i FROM IngredientesReceta i")
    , @NamedQuery(name = "IngredientesReceta.findByReceta", query = "SELECT i FROM IngredientesReceta i WHERE i.ingredientesRecetaPK.receta = :receta")
    , @NamedQuery(name = "IngredientesReceta.findByIngrediente", query = "SELECT i FROM IngredientesReceta i WHERE i.ingredientesRecetaPK.ingrediente = :ingrediente")
    , @NamedQuery(name = "IngredientesReceta.findByCantidad", query = "SELECT i FROM IngredientesReceta i WHERE i.cantidad = :cantidad")
    , @NamedQuery(name = "IngredientesReceta.findByUnidadMedida", query = "SELECT i FROM IngredientesReceta i WHERE i.unidadMedida = :unidadMedida")})
public class IngredientesReceta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IngredientesRecetaPK ingredientesRecetaPK;
    @Column(name = "cantidad")
    private String cantidad;
    @Column(name = "unidad_medida")
    private String unidadMedida;
    @JoinColumn(name = "ingrediente", referencedColumnName = "cod_ingrediente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ingrediente ingrediente1;
    @JoinColumn(name = "receta", referencedColumnName = "cod_receta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Receta receta1;

    public IngredientesReceta() {
    }

    public IngredientesReceta(IngredientesRecetaPK ingredientesRecetaPK) {
        this.ingredientesRecetaPK = ingredientesRecetaPK;
    }

    public IngredientesReceta(int receta, int ingrediente) {
        this.ingredientesRecetaPK = new IngredientesRecetaPK(receta, ingrediente);
    }

    public IngredientesRecetaPK getIngredientesRecetaPK() {
        return ingredientesRecetaPK;
    }

    public void setIngredientesRecetaPK(IngredientesRecetaPK ingredientesRecetaPK) {
        this.ingredientesRecetaPK = ingredientesRecetaPK;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Ingrediente getIngrediente1() {
        return ingrediente1;
    }

    public void setIngrediente1(Ingrediente ingrediente1) {
        this.ingrediente1 = ingrediente1;
    }

    public Receta getReceta1() {
        return receta1;
    }

    public void setReceta1(Receta receta1) {
        this.receta1 = receta1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ingredientesRecetaPK != null ? ingredientesRecetaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngredientesReceta)) {
            return false;
        }
        IngredientesReceta other = (IngredientesReceta) object;
        if ((this.ingredientesRecetaPK == null && other.ingredientesRecetaPK != null) || (this.ingredientesRecetaPK != null && !this.ingredientesRecetaPK.equals(other.ingredientesRecetaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.IngredientesReceta[ ingredientesRecetaPK=" + ingredientesRecetaPK + " ]";
    }
    
}
