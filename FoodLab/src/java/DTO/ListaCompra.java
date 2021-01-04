/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "lista_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaCompra.findAll", query = "SELECT l FROM ListaCompra l")
    , @NamedQuery(name = "ListaCompra.findByUsuario", query = "SELECT l FROM ListaCompra l WHERE l.listaCompraPK.usuario = :usuario")
    , @NamedQuery(name = "ListaCompra.findByIngrediente", query = "SELECT l FROM ListaCompra l WHERE l.listaCompraPK.ingrediente = :ingrediente")
    , @NamedQuery(name = "ListaCompra.findByCantidad", query = "SELECT l FROM ListaCompra l WHERE l.cantidad = :cantidad")
    , @NamedQuery(name = "ListaCompra.findByUnidad", query = "SELECT l FROM ListaCompra l WHERE l.unidad = :unidad")})
public class ListaCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListaCompraPK listaCompraPK;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private String cantidad;
    @Basic(optional = false)
    @Column(name = "unidad")
    private String unidad;
    @JoinColumn(name = "usuario", referencedColumnName = "nick", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;
    @JoinColumn(name = "ingrediente", referencedColumnName = "cod_ingrediente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ingrediente ingrediente1;

    public ListaCompra() {
    }

    public ListaCompra(ListaCompraPK listaCompraPK) {
        this.listaCompraPK = listaCompraPK;
    }

    public ListaCompra(ListaCompraPK listaCompraPK, String cantidad, String unidad) {
        this.listaCompraPK = listaCompraPK;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public ListaCompra(String usuario, int ingrediente) {
        this.listaCompraPK = new ListaCompraPK(usuario, ingrediente);
    }

    public ListaCompraPK getListaCompraPK() {
        return listaCompraPK;
    }

    public void setListaCompraPK(ListaCompraPK listaCompraPK) {
        this.listaCompraPK = listaCompraPK;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
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
        hash += (listaCompraPK != null ? listaCompraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaCompra)) {
            return false;
        }
        ListaCompra other = (ListaCompra) object;
        if ((this.listaCompraPK == null && other.listaCompraPK != null) || (this.listaCompraPK != null && !this.listaCompraPK.equals(other.listaCompraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.ListaCompra[ listaCompraPK=" + listaCompraPK + " ]";
    }
    
}
