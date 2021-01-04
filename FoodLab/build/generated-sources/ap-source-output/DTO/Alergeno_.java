package DTO;

import DTO.AlergenosIngrediente;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-11T17:59:12")
@StaticMetamodel(Alergeno.class)
public class Alergeno_ { 

    public static volatile SingularAttribute<Alergeno, String> descripcion;
    public static volatile SingularAttribute<Alergeno, String> icono;
    public static volatile ListAttribute<Alergeno, AlergenosIngrediente> alergenosIngredienteList;
    public static volatile SingularAttribute<Alergeno, Integer> codAlergeno;
    public static volatile SingularAttribute<Alergeno, String> nombre;

}