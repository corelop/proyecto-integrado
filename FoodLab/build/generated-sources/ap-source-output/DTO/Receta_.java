package DTO;

import DTO.Comentario;
import DTO.Dieta;
import DTO.IngredientesReceta;
import DTO.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-11T17:59:12")
@StaticMetamodel(Receta.class)
public class Receta_ { 

    public static volatile SingularAttribute<Receta, String> contenido;
    public static volatile SingularAttribute<Receta, Boolean> aceptada;
    public static volatile ListAttribute<Receta, Usuario> usuarioList;
    public static volatile ListAttribute<Receta, IngredientesReceta> ingredientesRecetaList;
    public static volatile SingularAttribute<Receta, Integer> codReceta;
    public static volatile SingularAttribute<Receta, String> nombre;
    public static volatile SingularAttribute<Receta, Usuario> autor;
    public static volatile ListAttribute<Receta, Dieta> dietaList;
    public static volatile SingularAttribute<Receta, String> foto;
    public static volatile ListAttribute<Receta, Comentario> comentarioList;
    public static volatile SingularAttribute<Receta, String> tipoPlato;
    public static volatile SingularAttribute<Receta, Date> fechaInsercion;
    public static volatile SingularAttribute<Receta, String> dificultad;

}