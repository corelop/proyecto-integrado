/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByNick", query = "SELECT u FROM Usuario u WHERE u.nick = :nick")
    , @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo")
    , @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos")
    , @NamedQuery(name = "Usuario.findByAdmin", query = "SELECT u FROM Usuario u WHERE u.admin = :admin")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nick")
    private String nick;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "admin")
    private boolean admin;
    @ManyToMany(mappedBy = "usuarioList")
    private List<Receta> recetaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autor")
    private List<Receta> recetaList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<SolicitudIngredientes> solicitudIngredientesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario1")
    private List<ListaCompra> listaCompraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario1")
    private List<Comentario> comentarioList;

    public Usuario() {
    }

    public Usuario(String nick) {
        this.nick = nick;
    }

    public Usuario(String nick, String password, String nombre, String correo, String apellidos, boolean admin) {
        this.nick = nick;
        this.password = password;
        this.nombre = nombre;
        this.correo = correo;
        this.apellidos = apellidos;
        this.admin = admin;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @XmlTransient
    public List<Receta> getRecetaList() {
        return recetaList;
    }

    public void setRecetaList(List<Receta> recetaList) {
        this.recetaList = recetaList;
    }

    @XmlTransient
    public List<Receta> getRecetaList1() {
        return recetaList1;
    }

    public void setRecetaList1(List<Receta> recetaList1) {
        this.recetaList1 = recetaList1;
    }

    @XmlTransient
    public List<SolicitudIngredientes> getSolicitudIngredientesList() {
        return solicitudIngredientesList;
    }

    public void setSolicitudIngredientesList(List<SolicitudIngredientes> solicitudIngredientesList) {
        this.solicitudIngredientesList = solicitudIngredientesList;
    }

    @XmlTransient
    public List<ListaCompra> getListaCompraList() {
        return listaCompraList;
    }

    public void setListaCompraList(List<ListaCompra> listaCompraList) {
        this.listaCompraList = listaCompraList;
    }

    @XmlTransient
    public List<Comentario> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nick != null ? nick.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.nick == null && other.nick != null) || (this.nick != null && !this.nick.equals(other.nick))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Usuario[ nick=" + nick + " ]";
    }
    
}
