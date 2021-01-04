package DTO;

import DTO.ComentarioPK;
import DTO.Receta;
import DTO.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-11T17:59:12")
@StaticMetamodel(Comentario.class)
public class Comentario_ { 

    public static volatile SingularAttribute<Comentario, Integer> puntuacion;
    public static volatile SingularAttribute<Comentario, Receta> receta;
    public static volatile SingularAttribute<Comentario, Usuario> usuario1;
    public static volatile SingularAttribute<Comentario, String> comentario;
    public static volatile SingularAttribute<Comentario, ComentarioPK> comentarioPK;

}