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
public class AlergenosIngredientePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "alergeno")
    private int alergeno;
    @Basic(optional = false)
    @Column(name = "ingrediente")
    private int ingrediente;

    public AlergenosIngredientePK() {
    }

    public AlergenosIngredientePK(int alergeno, int ingrediente) {
        this.alergeno = alergeno;
        this.ingrediente = ingrediente;
    }

    public int getAlergeno() {
        return alergeno;
    }

    public void setAlergeno(int alergeno) {
        this.alergeno = alergeno;
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
        hash += (int) alergeno;
        hash += (int) ingrediente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlergenosIngredientePK)) {
            return false;
        }
        AlergenosIngredientePK other = (AlergenosIngredientePK) object;
        if (this.alergeno != other.alergeno) {
            return false;
        }
        if (this.ingrediente != other.ingrediente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.AlergenosIngredientePK[ alergeno=" + alergeno + ", ingrediente=" + ingrediente + " ]";
    }
    
}
