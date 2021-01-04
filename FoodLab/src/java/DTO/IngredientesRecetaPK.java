/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author CRIS
 */
@Embeddable
public class IngredientesRecetaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "receta")
    private int receta;
    @Basic(optional = false)
    @Column(name = "ingrediente")
    private int ingrediente;

    public IngredientesRecetaPK() {
    }

    public IngredientesRecetaPK(int receta, int ingrediente) {
        this.receta = receta;
        this.ingrediente = ingrediente;
    }

    public int getReceta() {
        return receta;
    }

    public void setReceta(int receta) {
        this.receta = receta;
    }

    public int getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(int ingrediente) {
        this.ingrediente = ingrediente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) receta;
        hash += (int) ingrediente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngredientesRecetaPK)) {
            return false;
        }
        IngredientesRecetaPK other = (IngredientesRecetaPK) object;
        if (this.receta != other.receta) {
            return false;
        }
        if (this.ingrediente != other.ingrediente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.IngredientesRecetaPK[ receta=" + receta + ", ingrediente=" + ingrediente + " ]";
    }
    
}
