package DTO;

import DTO.Comentario;
import DTO.ListaCompra;
import DTO.Receta;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-11T17:59:12")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> nick;
    public static volatile SingularAttribute<Usuario, String> apellidos;
    public static volatile SingularAttribute<Usuario, String> password;
    public static volatile ListAttribute<Usuario, ListaCompra> listaCompraList;
    public static volatile ListAttribute<Usuario, Comentario> comentarioList;
    public static volatile SingularAttribute<Usuario, String> correo;
    public static volatile SingularAttribute<Usuario, Boolean> admin;
    public static volatile ListAttribute<Usuario, Receta> recetaList1;
    public static volatile ListAttribute<Usuario, Receta> recetaList;
    public static volatile SingularAttribute<Usuario, String> nombre;

}