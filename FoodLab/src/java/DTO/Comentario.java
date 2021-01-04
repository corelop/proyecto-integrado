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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "comentario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comentario.findAll", query = "SELECT c FROM Comentario c")
    , @NamedQuery(name = "Comentario.findByCodReceta", query = "SELECT c FROM Comentario c WHERE c.comentarioPK.codReceta = :codReceta")
    , @NamedQuery(name = "Comentario.findByUsuario", query = "SELECT c FROM Comentario c WHERE c.comentarioPK.usuario = :usuario")
    , @NamedQuery(name = "Comentario.findByFechaComentario", query = "SELECT c FROM Comentario c WHERE c.comentarioPK.fechaComentario = :fechaComentario")
    , @NamedQuery(name = "Comentario.findByPuntuacion", query = "SELECT c FROM Comentario c WHERE c.puntuacion = :puntuacion")})
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComentarioPK comentarioPK;
    @Lob
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @Column(name = "puntuacion")
    private int puntuacion;
    @JoinColumn(name = "cod_receta", referencedColumnName = "cod_receta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Receta receta;
    @JoinColumn(name = "usuario", referencedColumnName = "nick", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;

    public Comentario() {
    }

    public Comentario(ComentarioPK comentarioPK) {
        this.comentarioPK = comentarioPK;
    }

    public Comentario(ComentarioPK comentarioPK, int puntuacion) {
        this.comentarioPK = comentarioPK;
        this.puntuacion = puntuacion;
    }

    public Comentario(int codReceta, String usuario, Date fechaComentario) {
        this.comentarioPK = new ComentarioPK(codReceta, usuario, fechaComentario);
    }

    public ComentarioPK getComentarioPK() {
        return comentarioPK;
    }

    public void setComentarioPK(ComentarioPK comentarioPK) {
        this.comentarioPK = comentarioPK;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comentarioPK != null ? comentarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comentario)) {
            return false;
        }
        Comentario other = (Comentario) object;
        if ((this.comentarioPK == null && other.comentarioPK != null) || (this.comentarioPK != null && !this.comentarioPK.equals(other.comentarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Comentario[ comentarioPK=" + comentarioPK + " ]";
    }
    
}
