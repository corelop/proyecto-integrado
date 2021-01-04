/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
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
@Table(name = "alergenos_ingrediente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlergenosIngrediente.findAll", query = "SELECT a FROM AlergenosIngrediente a")
    , @NamedQuery(name = "AlergenosIngrediente.findByAlergeno", query = "SELECT a FROM AlergenosIngrediente a WHERE a.alergenosIngredientePK.alergeno = :alergeno")
    , @NamedQuery(name = "AlergenosIngrediente.findByIngrediente", query = "SELECT a FROM AlergenosIngrediente a WHERE a.alergenosIngredientePK.ingrediente = :ingrediente")})
public class AlergenosIngrediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AlergenosIngredientePK alergenosIngredientePK;
    @JoinColumn(name = "alergeno", referencedColumnName = "cod_alergeno", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alergeno alergeno1;
    @JoinColumn(name = "ingrediente", referencedColumnName = "cod_ingrediente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ingrediente ingrediente1;

    public AlergenosIngrediente() {
    }

    public AlergenosIngrediente(AlergenosIngredientePK alergenosIngredientePK) {
        this.alergenosIngredientePK = alergenosIngredientePK;
    }

    public AlergenosIngrediente(int alergeno, int ingrediente) {
        this.alergenosIngredientePK = new AlergenosIngredientePK(alergeno, ingrediente);
    }

    public AlergenosIngredientePK getAlergenosIngredientePK() {
        return alergenosIngredientePK;
    }

    public void setAlergenosIngredientePK(AlergenosIngredientePK alergenosIngredientePK) {
        this.alergenosIngredientePK = alergenosIngredientePK;
    }

    public Alergeno getAlergeno1() {
        return alergeno1;
    }

    public void setAlergeno1(Alergeno alergeno1) {
        this.alergeno1 = alergeno1;
    }

    public Ingrediente getIngrediente1() {
        return ingrediente1;
    }

    public void setIngrediente1(Ingrediente ingrediente1) {
        this.ingrediente1 = ingrediente1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alergenosIngredientePK != null ? alergenosIngredientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlergenosIngrediente)) {
            return false;
        }
        AlergenosIngrediente other = (AlergenosIngrediente) object;
        if ((this.alergenosIngredientePK == null && other.alergenosIngredientePK != null) || (this.alergenosIngredientePK != null && !this.alergenosIngredientePK.equals(other.alergenosIngredientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.AlergenosIngrediente[ alergenosIngredientePK=" + alergenosIngredientePK + " ]";
    }
    
}
