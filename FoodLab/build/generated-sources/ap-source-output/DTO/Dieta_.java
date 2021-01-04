package DTO;

import DTO.Receta;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-11T17:59:12")
@StaticMetamodel(Dieta.class)
public class Dieta_ { 

    public static volatile SingularAttribute<Dieta, String> tipo;
    public static volatile SingularAttribute<Dieta, Integer> codDieta;
    public static volatile ListAttribute<Dieta, Receta> recetaList;

}