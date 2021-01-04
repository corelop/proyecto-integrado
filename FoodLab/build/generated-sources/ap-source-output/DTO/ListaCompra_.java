package DTO;

import DTO.Ingrediente;
import DTO.ListaCompraPK;
import DTO.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-11T17:59:12")
@StaticMetamodel(ListaCompra.class)
public class ListaCompra_ { 

    public static volatile SingularAttribute<ListaCompra, Ingrediente> ingrediente1;
    public static volatile SingularAttribute<ListaCompra, String> unidad;
    public static volatile SingularAttribute<ListaCompra, Usuario> usuario1;
    public static volatile SingularAttribute<ListaCompra, ListaCompraPK> listaCompraPK;
    public static volatile SingularAttribute<ListaCompra, String> cantidad;

}