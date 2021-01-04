/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author CRIS
 */
@Embeddable
public class ComentarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_receta")
    private int codReceta;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "fecha_comentario")
    @Temporal(TemporalType.DATE)
    private Date fechaComentario;

    public ComentarioPK() {
    }

    public ComentarioPK(int codReceta, String usuario, Date fechaComentario) {
        this.codReceta = codReceta;
        this.usuario = usuario;
        this.fechaComentario = fechaComentario;
    }

    public int getCodReceta() {
        return codReceta;
    }

    public void setCodReceta(int codReceta) {
        this.codReceta = codReceta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codReceta;
        hash += (usuario != null ? usuario.hashCode() : 0);
        hash += (fechaComentario != null ? fechaComentario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComentarioPK)) {
            return false;
        }
        ComentarioPK other = (ComentarioPK) object;
        if (this.codReceta != other.codReceta) {
            return false;
        }
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        if ((this.fechaComentario == null && other.fechaComentario != null) || (this.fechaComentario != null && !this.fechaComentario.equals(other.fechaComentario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.ComentarioPK[ codReceta=" + codReceta + ", usuario=" + usuario + ", fechaComentario=" + fechaComentario + " ]";
    }
    
}
