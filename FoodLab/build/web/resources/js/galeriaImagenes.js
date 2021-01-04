/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function galeria(img, recetas) {

    var carrusel = {};
    carrusel.img = img;
    carrusel.pos = 0;
    var input = $("#formOculto input[type='hidden']:nth-child(2)");
    var imgDiapo = $("#formOculto input[type='image']");
    console.log(imgDiapo);

    function añadirLi() {
        $("#miniaturas ul").empty();
        for (var i in img) {
            //console.log(i);
            var li = $("<li><div>" + i + "</div><img src=" + img[i] + " id=" + i + "></li>");

            li.data("datos", recetas[i]);
            $("#miniaturas ul").append(li);
            //  console.log(li.data("datos"));

            if (i == 0) {
                //diapositiva inicial

                imgDiapo.attr("src", carrusel.img[0]);
                var receta = li.data("datos");

                //console.log(receta);

                li.addClass("seleccionada");
                $("#rotulo").append("<h3>" + receta.nombre + "</h3>" +
                        "<span>añadido el " + receta.fecha_insercion + " por " + receta.autor + "</span>");

                input.attr("value", receta.cod_receta);

                //console.log(receta.cod_receta);
            }

        }
    }


    $(function () {
        // creamos las miniaturas....................
        var $divMin = $("<div id='miniaturas' class='col-12'><ul class='row' ></ul></div>");

        $("#ultimasAdd").append($divMin);

        añadirLi();

        $("#ultimasAdd").on("click", "li", function () {
            clearInterval(autoPlay);

            $("#" + carrusel.pos).parent().removeClass("seleccionada");
            var img = $(this).find("img")[0];

            imgDiapo.attr("src", img.src);

            carrusel.pos = img.id;

            $("#" + img.id).parent().addClass("seleccionada");

            var receta = $(this).data("datos");

            $("#rotulo").empty();
            $("#rotulo").append("<h3>" + receta.nombre + "</h3>" +
                    "<span>añadido el " + receta.fecha_insercion + " por " + receta.autor + "</span>");
            input.attr("value", receta.cod_receta);
           
        });

        //botones foto galeria
        $("#prev").on("click", function () {

            clearInterval(autoPlay);

            if (carrusel.pos == 0) {

                $("#" + carrusel.pos).parent().removeClass("seleccionada");
                carrusel.pos = Object.keys(carrusel.img).length - 1;
                imgDiapo.attr("src", carrusel.img[carrusel.pos]);
                $("#" + carrusel.pos).parent().addClass("seleccionada");

            } else {

                $("#" + carrusel.pos).parent().removeClass("seleccionada");
                carrusel.pos--;
                imgDiapo.attr("src", carrusel.img[carrusel.pos]);
                $("#" + carrusel.pos).parent().addClass("seleccionada");

            }

            var receta = $("#" + carrusel.pos).parent().data("datos");
            //  console.log(receta);
            $("#rotulo").empty();
            $("#rotulo").append("<h3>" + receta.nombre + "</h3>" +
                    "<span>añadido el " + receta.fecha_insercion + " por " + receta.autor + "</span>");
            input.attr("value", receta.cod_receta);
          
        });


        $("#next").on("click", function () {

            clearInterval(autoPlay);

            if (carrusel.pos == Object.keys(carrusel.img).length - 1) {

                $("#" + carrusel.pos).parent().removeClass("seleccionada");
               imgDiapo.attr("src", carrusel.img[0]);
                carrusel.pos = 0;
                $("#" + carrusel.pos).parent().addClass("seleccionada");

            } else {

                $("#" + carrusel.pos).parent().removeClass("seleccionada");
                carrusel.pos++;
                imgDiapo.attr("src", carrusel.img[carrusel.pos]);
                $("#" + carrusel.pos).parent().addClass("seleccionada");

            }

            var receta = $("#" + carrusel.pos).parent().data("datos");
            $("#rotulo").empty();
            $("#rotulo").append("<h3>" + receta.nombre + "</h3>" +
                    "<span>añadido el " + receta.fecha_insercion + " por " + receta.autor + "</span>");
            input.attr("value", receta.cod_receta);
            

        });



        //auto play
        function transicion() {


            if (carrusel.pos == Object.keys(carrusel.img).length - 1) {

                //$("#diapo").fadeToggle(2000, function () {
                $("#" + carrusel.pos).parent().removeClass("seleccionada");
                carrusel.pos = 0;
                imgDiapo.attr("src", carrusel.img[carrusel.pos]);
                $("#" + carrusel.pos).parent().addClass("seleccionada");
                //});
            } else {
                //$("#diapo").fadeToggle(2000, function () {
                $("#" + carrusel.pos).parent().removeClass("seleccionada");
                var pos = carrusel.pos + 1;
                imgDiapo.attr("src", carrusel.img[pos]);
                carrusel.pos = pos;
                $("#" + carrusel.pos).parent().addClass("seleccionada");
                //});
            }
            //$("#" + carrusel.pos).parent().addClass("seleccionada");

            var receta = $("#" + carrusel.pos).parent().data("datos");
            //  console.log($("#" + carrusel.pos).parent());
            $("#rotulo").empty();
            $("#rotulo").append("<h3>" + receta.nombre + "</h3>" +
                    "<span>añadido el " + receta.fecha_insercion + " por " + receta.autor + "</span>");
            input.attr("value", receta.cod_receta);

        }

        var autoPlay = setInterval(transicion, 15000);


    });
}