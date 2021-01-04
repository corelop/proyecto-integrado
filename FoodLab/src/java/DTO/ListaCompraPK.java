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
public class ListaCompraPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "ingrediente")
    private int ingrediente;

    public ListaCompraPK() {
    }

    public ListaCompraPK(String usuario, int ingrediente) {
        this.usuario = usuario;
        this.ingrediente = ingrediente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
        hash += (usuario != null ? usuario.hashCode() : 0);
        hash += (int) ingrediente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaCompraPK)) {
            return false;
        }
        ListaCompraPK other = (ListaCompraPK) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        if (this.ingrediente != other.ingrediente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.ListaCompraPK[ usuario=" + usuario + ", ingrediente=" + ingrediente + " ]";
    }
    
}
