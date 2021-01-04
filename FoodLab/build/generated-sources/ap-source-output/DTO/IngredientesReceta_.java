package DTO;

import DTO.Ingrediente;
import DTO.IngredientesRecetaPK;
import DTO.Receta;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-11T17:59:12")
@StaticMetamodel(IngredientesReceta.class)
public class IngredientesReceta_ { 

    public static volatile SingularAttribute<IngredientesReceta, Ingrediente> ingrediente1;
    public static volatile SingularAttribute<IngredientesReceta, String> unidadMedida;
    public static volatile SingularAttribute<IngredientesReceta, String> cantidad;
    public static volatile SingularAttribute<IngredientesReceta, IngredientesRecetaPK> ingredientesRecetaPK;
    public static volatile SingularAttribute<IngredientesReceta, Receta> receta1;

}