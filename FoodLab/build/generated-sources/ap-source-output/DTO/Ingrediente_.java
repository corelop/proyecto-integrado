package DTO;

import DTO.AlergenosIngrediente;
import DTO.IngredientesReceta;
import DTO.ListaCompra;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-11T17:59:12")
@StaticMetamodel(Ingrediente.class)
public class Ingrediente_ { 

    public static volatile SingularAttribute<Ingrediente, BigDecimal> kcal;
    public static volatile SingularAttribute<Ingrediente, Integer> codIngrediente;
    public static volatile ListAttribute<Ingrediente, ListaCompra> listaCompraList;
    public static volatile ListAttribute<Ingrediente, IngredientesReceta> ingredientesRecetaList;
    public static volatile SingularAttribute<Ingrediente, String> imagen;
    public static volatile ListAttribute<Ingrediente, AlergenosIngrediente> alergenosIngredienteList;
    public static volatile SingularAttribute<Ingrediente, String> nombre;

}