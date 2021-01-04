-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-06-2020 a las 11:48:31
-- Versión del servidor: 10.4.8-MariaDB
-- Versión de PHP: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `recetas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alergeno`
--

CREATE TABLE `alergeno` (
  `cod_alergeno` int(2) NOT NULL,
  `nombre` varchar(15) NOT NULL,
  `descripcion` text NOT NULL,
  `icono` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `alergeno`
--

INSERT INTO `alergeno` (`cod_alergeno`, `nombre`, `descripcion`, `icono`) VALUES
(1, 'pescado', 'Pescado o productos a base de pescado.', 'pescado.png'),
(2, 'frutos secos', 'Frutos de cáscara, es decir: almendras, avellanas, nueces, anacardos, pacanas, nueces de Brasil, alfóncigos, nueces macadamia o nueces de Australia o productos derivados.', 'frutos-secos.png'),
(3, 'lácteos', 'Leche o sus derivados (incluida la lactosa).', 'lacteos.png'),
(4, 'moluscos', 'Moluscos o productos a base de moluscos.', 'moluscos.png'),
(5, 'gluten', 'Cereales que contengan gluten, como trigo, centeno, cebada, avena, espelta, kamut o sus variedades híbridas y productos derivados.', 'gluten.png'),
(6, 'crustáceos', 'Crustáceos o productos a base de crustáceos.', 'crustaceos.png'),
(7, 'huevos', 'Huevos o productos a base de huevo.', 'huevo.png'),
(8, 'cacahuetes', 'Cacahuetes o productos a base de cacahuetes.', 'cacahuete.png'),
(9, 'soja', 'Soja o productos a base de soja.', 'soja.png'),
(10, 'apio', 'Apio o productos derivados.', 'apio.png'),
(11, 'mostaza', 'Mostaza o productos derivados.', 'mostaza.png'),
(12, 'sésamo', 'Granos de sésamo o productos a base de granos de sésamo.', 'sesamo.png'),
(13, 'sulfitos', 'Dióxido de azufre y sulfitos en concentraciones superiores a 10 mg/kg o 10 mg/litro en términos de SO2 total, para los productos listos para el consumo o reconstituidos conforme a las instrucciones del fabricante.', 'sulfitos.png');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alergenos_ingrediente`
--

CREATE TABLE `alergenos_ingrediente` (
  `alergeno` int(2) NOT NULL,
  `ingrediente` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `alergenos_ingrediente`
--

INSERT INTO `alergenos_ingrediente` (`alergeno`, `ingrediente`) VALUES
(1, 54),
(3, 4),
(5, 12),
(7, 23),
(7, 52),
(11, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentario`
--

CREATE TABLE `comentario` (
  `cod_receta` int(4) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `fecha_comentario` date NOT NULL,
  `comentario` text DEFAULT NULL,
  `puntuacion` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `comentario`
--

INSERT INTO `comentario` (`cod_receta`, `usuario`, `fecha_comentario`, `comentario`, `puntuacion`) VALUES
(1, 'xPepe', '2020-06-19', '¡Me la guardo!', 8),
(2, 'FoodLab', '2020-06-18', 'Opción muy saludable para merendar, me ha encantado.', 9),
(3, 'cris', '2020-06-01', 'Fácil y saludable.', 10),
(4, 'ana', '2020-05-29', '¡Que buena pinta!', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dieta`
--

CREATE TABLE `dieta` (
  `cod_dieta` int(2) NOT NULL,
  `tipo` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `dieta`
--

INSERT INTO `dieta` (`cod_dieta`, `tipo`) VALUES
(1, 'Mediterránea'),
(2, 'Vegetariana'),
(3, 'Vegana'),
(4, 'Sin Gluten'),
(5, 'Paleo'),
(6, 'General');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dietas_receta`
--

CREATE TABLE `dietas_receta` (
  `receta` int(4) NOT NULL,
  `dieta` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `dietas_receta`
--

INSERT INTO `dietas_receta` (`receta`, `dieta`) VALUES
(1, 1),
(2, 2),
(2, 6),
(3, 2),
(3, 3),
(3, 5),
(3, 6),
(4, 4),
(4, 6),
(5, 2),
(5, 6),
(25, 1),
(25, 6),
(26, 1),
(26, 5),
(26, 6),
(31, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingrediente`
--

CREATE TABLE `ingrediente` (
  `cod_ingrediente` int(3) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `kcal` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ingrediente`
--

INSERT INTO `ingrediente` (`cod_ingrediente`, `nombre`, `kcal`) VALUES
(1, 'Cebolla', '32.00'),
(2, 'Pimiento rojo', '32.90'),
(3, 'Lomo de cerdo', '311.00'),
(4, 'Queso de untar', '342.00'),
(5, 'Agua', '0.00'),
(6, 'Mostaza', '125.00'),
(7, 'Sal', '0.00'),
(8, 'Pimienta negra', '280.00'),
(9, 'Romero', '131.00'),
(10, 'Aceite de oliva', '899.00'),
(11, 'Plátano', '95.03'),
(12, 'Avena', '353.00'),
(13, 'Canela', '255.00'),
(14, 'Calabaza', '28.37'),
(15, 'Manzana', '54.08'),
(16, 'Pechuga de pollo', '145.00'),
(17, 'Ajo', '119.00'),
(18, 'Arroz', '364.00'),
(19, 'Leche de coco', '230.00'),
(20, 'Curry', '325.00'),
(21, 'Patata', '77.00'),
(22, 'Tomate', '22.17'),
(23, 'Huevo', '52.00'),
(36, 'Hierbabuena', '12.00'),
(37, 'Hierbabuena', '12.00'),
(38, 'Hierbabuena', '12.00'),
(39, 'Hierbabuena', '12.00'),
(40, 'Hierbabuena', '12.00'),
(41, 'Hierbabuena', '12.00'),
(42, 'Hierbabuena', '12.00'),
(43, 'Hierbabuena', '12.00'),
(44, ' Hierbabuenma', '12.00'),
(45, ' Hierbabuenma', '12.00'),
(46, 'Hierbabuena', '12.00'),
(47, 'Hierbabuena', '12.00'),
(48, 'Hierbabuena', '12.00'),
(49, 'Hierbabuena', '12.00'),
(50, 'Hierbabuena', '12.00'),
(51, 'Hierbabuena', '12.00'),
(52, 'Hierbabuena', '12.00'),
(53, 'Merluza', '65.00'),
(54, 'Merluza', '65.00'),
(55, 'Hierbabuena', '0.00'),
(56, 'Guisante', '15.00'),
(57, 'Guisante', '15.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingredientes_receta`
--

CREATE TABLE `ingredientes_receta` (
  `receta` int(4) NOT NULL,
  `ingrediente` int(3) NOT NULL,
  `cantidad` varchar(5) DEFAULT NULL,
  `unidad_medida` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ingredientes_receta`
--

INSERT INTO `ingredientes_receta` (`receta`, `ingrediente`, `cantidad`, `unidad_medida`) VALUES
(1, 3, '2', 'filetes'),
(1, 4, '2', 'cucharadas'),
(1, 5, '1/3', 'taza'),
(1, 6, '1', 'cucharada'),
(1, 7, '1', 'pizca'),
(1, 8, '1', 'pizca'),
(1, 9, '1', 'pizca'),
(1, 10, '1', 'cucharada'),
(2, 11, '1/2', 'unidad'),
(2, 12, '1/4', 'taza'),
(2, 13, '1/2', 'cucharadita'),
(3, 1, '1', 'unidad'),
(3, 14, '350', 'gr'),
(3, 15, '175', 'gr'),
(4, 1, '1/2', 'unidad'),
(4, 7, '1/2', 'cucharadita'),
(4, 10, '2', 'cucharadas'),
(4, 16, '1', 'unidad'),
(4, 17, '1', 'diente'),
(4, 18, '1/2', 'taza'),
(4, 19, '1', 'taza'),
(4, 20, '1/2', 'cucharada'),
(5, 1, '1/4', 'unidad'),
(5, 7, '1', 'pizca'),
(5, 10, '1', 'cucharada'),
(5, 21, '1/2', 'unidad'),
(5, 22, '1', 'unidad'),
(5, 23, '3/2', 'unidades'),
(25, 7, '1', 'cucharadita'),
(25, 10, '1', 'cucharada'),
(25, 17, '1', 'diente'),
(25, 55, '1', 'ramita'),
(26, 1, '1/2', 'unidad'),
(26, 7, '1', 'pizca'),
(26, 8, '1/2', 'pizca'),
(26, 10, '1', ' cucharada'),
(26, 21, '1', 'unidad'),
(26, 53, '175', 'gr'),
(26, 54, '175', 'gr'),
(31, 1, '1/4', 'unidad'),
(31, 7, '1', 'pellizco'),
(31, 10, '1', 'cucharada'),
(31, 23, '2', 'unidad'),
(31, 56, '1', 'bote'),
(31, 57, '1', 'bote'),
(32, 1, '1', 'unidad'),
(32, 2, '1', 'unidad');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lista_compra`
--

CREATE TABLE `lista_compra` (
  `usuario` varchar(15) NOT NULL,
  `ingrediente` int(11) NOT NULL,
  `cantidad` varchar(11) NOT NULL,
  `unidad` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `lista_compra`
--

INSERT INTO `lista_compra` (`usuario`, `ingrediente`, `cantidad`, `unidad`) VALUES
('ana', 1, '1', 'unidad'),
('ana', 14, '350', 'gr'),
('ana', 15, '175', 'gr'),
('cris', 10, '2', 'garrafa'),
('cris', 23, '3', 'unidades'),
('FoodLab', 14, '350', 'gr'),
('FoodLab', 15, '175', 'gr'),
('FoodLab', 17, '1', 'diente'),
('xPepe', 3, '2', 'filetes');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `receta`
--

CREATE TABLE `receta` (
  `cod_receta` int(4) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `contenido` text NOT NULL,
  `fecha_insercion` date NOT NULL,
  `autor` varchar(15) NOT NULL,
  `foto` varchar(45) NOT NULL,
  `tipo_plato` varchar(15) DEFAULT NULL,
  `dificultad` varchar(10) DEFAULT NULL,
  `aceptada` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `receta`
--

INSERT INTO `receta` (`cod_receta`, `nombre`, `contenido`, `fecha_insercion`, `autor`, `foto`, `tipo_plato`, `dificultad`, `aceptada`) VALUES
(1, 'Lomo de cerdo a la mostaza', '\r\nEn una sartén, con un chorrito de aceite, fríe los filetes de lomo de cerdo, previamente salpimentados.\r\n\r\nMientras, en un cazo vierte el queso de untar, la mostaza, el romero, una pizca de sal y el agua. Ponlo a fuego suave y revuelve de vez en cuando para que se mezcle bien el queso con el resto de ingredientes.\r\n\r\nVierte la salsa sobre los lomos de cerdo, deja que la salsa se integre, baja el fuego al mínimo, y cocina 2-3 minutos. A continuación, retira y sírvelos en la mesa.', '2020-06-02', 'cris', 'lomocerdo.jpg', 'carne', 'fácil', 1),
(2, 'Galletas de Avena, Plátano y Canela', 'Pela el plátano y, con un tenedor, tritúralo hasta lograr un puré.\r\n\r\nEn un recipiente, vierte el puré de plátano, añade la avena y la canela. Mezcla bien todos los ingredientes.\r\n\r\nCon tus manos forma bolitas y colócalas sobre papel de horno en una bandeja apta para horno.\r\n\r\nAplasta un poquito las bolitas (te quedarán con forma de galleta) y hornéalas a 200°C durante 20 minutos.\r\n\r\nRetira del horno, deja que se enfríen y sírvelas en un plato. ¡Ya puedes disfrutar de tus galletas de avena!', '2020-06-02', 'cris', 'galletas-de-avena-platano-y-canela.jpg', 'postre', 'fácil', 1),
(3, 'Crema de calabaza y manzana', 'Quita la piel de la calabaza y córtala en cuadrados\r\nPonla en una olla y tápala. Cuece en agua durante 10-15 minutos hasta que esté blandita al pincharla con un tenedor.\r\nMientras, pon un poco de aceite de oliva en una sartén a fuego lento.\r\nPica la cebolla en tiras finas y ponlas en la sartén hasta que esté casi transparente. Añade entonces la manzana, ya pelada y cortada en cuadraditos. Remueve para mezclarlas\r\nCocina la manzana y la cebolla durante 3-5 minutos\r\nSaca la calabaza cocinada y ponlas en un procesador de alimentos o batidora junto con la cebolla y la manzana.\r\nBate hasta que no queden brumos.\r\nYo no he añadido el agua de la cocción, pero porque me gusta que la crema de verduras quede espesa, pero puedes añadir un poco de caldo de la cocción para que quede más líquida la sopa.', '2020-05-11', 'cris', 'crema-de-calabaza-y-manzana.jpg', 'principal', 'media', 1),
(4, 'Arroz con Pollo al Curry', 'Pela y pica los ajos y la cebolla en cubos pequeños.\r\n\r\nPon una sartén amplia, a fuego medio, con 4 cucharadas de aceite, añade el ajo y la cebolla con una pizca de sal hasta que la cebolla esté blanda y transparente.\r\n\r\nMientras corta el pollo en dados y échale un poco de curry. Sube el fuego y añade el pollo. Dóralos por fuera y sácalos de la sartén.\r\n\r\nAñade el arroz y mezcla con la cebolla. Remueve 2 minutos.\r\n\r\nVierte la leche de coco, mezcla, y deja hacer a fuego medio. Cuando quede un dedo de leche de coco, echa el curry, el pollo y la sal.\r\n\r\nDeja hacer hasta que absorba el líquido. Apaga el fuego, tapa con un paño y deja reposar 5 minutos antes de servir.', '2020-05-12', 'cris', 'arroz-con-pollo-al-curry.jpg', 'principal', 'media', 1),
(5, 'Sartén de patatas y huevos con tomate', 'Lava las patatas y córtalas en trozos pequeños.\r\n\r\nEn una cazuela pon agua y una cucharadita de sal. Cuando el agua comience a hervir vierte los trozos de patata y cuécelos durante 10-12 minutos. Si las patatas las has cortado en trozos tamaño medio o pequeños estarán listas en este tiempo, sino cocina varios minutos más hasta que estén tiernas.\r\n\r\nLava los tomates y córtalos en trozos tamaño medio.\r\n\r\nEn una sartén echa un chorrito de aceite de oliva. Cuando esté caliente vierte la cebolla y saltéala durante un par de minutos. Añade las patatas cocidas y saltéalas junto con la cebolla apenas 1 minuto. Añade el tomate a la sartén, un poco de sal, y deja todo a fuego a fuego medio/bajo durante 5-6 minutos . Poco a poco el tomate se irá ablandando. Si ves que se reseca echa un poquito de agua a los ingredientes.\r\n\r\nHaz dos huecos en la sartén y vierte en ellos los huevos con una pizca de sal. Pon una tapa y deja que se cuajen, te llevará varios minutos. A continuación sirve.', '2020-05-12', 'cris', 'sarten-de-patatas-y-huevos-con-tomate.jpg', 'principal', 'fácil', 1),
(25, 'Tzatziki griego (salsa de yogur y pepino)', 'Quitamos las pepitas al pepino y lo picamos muy fino, lo salamos y dejamos reposar 20 min. para que suelte agua.\r\nEn un mortero majamos los ajos con la sal, mezclamos con el yogur, las hierbas y el aceite de oliva.\r\nPodemos triturar con el pasapurés (perderá consistencia) o aplastar con el tenedor para que queden algunos trozos de pepino.\r\nSe sirve como acompañamiento de muchos platos. Es ideal para acompañar el tabulé y cualquier ensalada de cuscús, con pollo frito marinado o alitas de pollo en adobo, con bastoncitos de verduras fritos o buñuelos de verduras, con kebabs o dados de queso frito. También es una salsa perfecta para carnes frías y embutidos.\r\n', '2020-06-11', 'ana', 'tzatziki-griego-detalle-aceite.jpg', 'entrante', 'facil', 1),
(26, 'Merluza al Horno con Patatas', 'Pela la patata y la cebolla, y corta en rodajas finas.\r\n\r\nPrecalienta el horno a 150°C. En una bandeja apta para horno, coloca las verduras, sazónalas al gusto y riégalas con un chorrito de aceite. Deja que se cocinen durante 15 minutos a 180°C.\r\n\r\nPasado ese tiempo abre el horno, da la vuelta a las verduras y coloca la merluza (o lomos de merluza en su caso) sobre las verduras. Echa un poco de sal y pimienta al pescado y sube el horno a 200°C. Cocina el pescado unos 10-15 minutos (dependerá del tamaño de tu pieza).\r\n\r\nAntes de sacar la merluza, con un tenedor comprueba si está cocinada por dentro. Una vez esté en el punto de cocción que te gusta, retira y sirve la merluza al horno en la mesa.', '2020-06-11', '2daw', 'merluza-al-horno-con-patatas.jpg', 'pescado', 'media', 1),
(31, 'Tortilla de Guisante', 'Corta la cebolla en tiras.\r\n\r\nEn una sartén echa el aceite de oliva. Cuando se caliente añade la cebolla y saltéala durante un par de minutos.\r\n\r\nA continuación añade los guisantes, previamente escurridos de su bote, y saltéalos a fuego medio-bajo junto con la cebolla y una pizca de sal durante 5-7 minutos. Es importante que el fuego no esté muy fuerte porque sino los guisantes saltarán, se explotarán.\r\n\r\nBate los huevos y viértelos en un recipiente. Añade la cebolla y los guisantes, y revuelve para que se integre todo.\r\n\r\nEn la misma sartén donde has estado cocinado, con ese aceite que te ha sobrado (si no pon un poquito más), añade la mezcla. Cuando la mezcla por ese lado esté cuajada, dale la vuelta para que se termine de cuajar por el otro lado (como si fuera una tortilla de patata). Apaga el fuego y sirve en la mesa.', '2020-06-18', 'xPepe', 'tortilla-de-guisantes.jpg', 'entrante', 'media', 1),
(32, 'Calabacines Rellenos de Atún y Pimientos', 'Lava el calabacín (cuanto más grande mejor, sino emplea dos) y córtalo por la mitad. A su vez cada mitad córtalo a lo largo de manera que te queden cuatro trozos grandes, serán los trozos que posteriormente rellenarás.\r\n\r\nEn una cazuela con suficiente agua pon a cocer los trozos de calabacín. Pasados 15 minutos retíralos, deja que se enfríen un poco y, con una cuchara, retira la pulpa o carne de su interior. Pica la carne del calabacín y resérvala.\r\n\r\nLava el pimiento y córtalo en tiras o trozos pequeños. También corta la cebolla en trozos pequeños.\r\n\r\nEn una sartén echa un poco de aceite de oliva y sofríe a fuego medio durante 5-6 minutos el pimiento, la cebolla y la carne del calabacín que tenías reservada.\r\n\r\nCuando las verduras estén tiernas vierte en la sartén el atún, previamente escurrido de sus latas, y la salsa de tomate. Mezcla bien los ingredientes.\r\n\r\nRellena los trozos de calabacín con el contenido de la sartén y ¡directo a la mesa!', '2020-06-19', 'xPepe', 'calabacines-rellenos-de-atun-y-pimientos.jpg', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recetas_fav`
--

CREATE TABLE `recetas_fav` (
  `usuario` varchar(15) NOT NULL,
  `receta` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `recetas_fav`
--

INSERT INTO `recetas_fav` (`usuario`, `receta`) VALUES
('ana', 5),
('cris', 3),
('FoodLab', 3),
('xPepe', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitud_ingredientes`
--

CREATE TABLE `solicitud_ingredientes` (
  `cod_solicitud` int(2) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(30) DEFAULT NULL,
  `cantidad` varchar(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `receta` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `solicitud_ingredientes`
--

INSERT INTO `solicitud_ingredientes` (`cod_solicitud`, `nombre`, `descripcion`, `cantidad`, `usuario`, `receta`) VALUES
(5, 'Atún', NULL, '1 lata', 'xPepe', 32);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `nick` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `correo` varchar(40) NOT NULL,
  `apellidos` varchar(35) NOT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`nick`, `password`, `nombre`, `correo`, `apellidos`, `admin`) VALUES
('2daw', '2daw', 'Ana', 'prueba2daw@gmail.com', 'Garcia', 0),
('ana', 'ana', 'Ana', 'ana_s@gmail.com', 'Sanchez', 0),
('cris', 'cris', 'Cristina', 'prueba2daw@gmail.com', 'Orellana', 1),
('FoodLab', 'FoodLab', 'Food', 'admin@gmail.com', 'Lab', 1),
('juancho', 'juancho', 'Juan', 'juanito@gmail.com', 'Martin', 0),
('xPepe', 'xpepe', 'Pepe', 'p_rodrigues@gmail.com', 'Rodriguez Santos', 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alergeno`
--
ALTER TABLE `alergeno`
  ADD PRIMARY KEY (`cod_alergeno`);

--
-- Indices de la tabla `alergenos_ingrediente`
--
ALTER TABLE `alergenos_ingrediente`
  ADD PRIMARY KEY (`alergeno`,`ingrediente`),
  ADD KEY `ingrediente` (`ingrediente`),
  ADD KEY `alergeno` (`alergeno`);

--
-- Indices de la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD PRIMARY KEY (`cod_receta`,`usuario`,`fecha_comentario`),
  ADD KEY `usuario` (`usuario`);

--
-- Indices de la tabla `dieta`
--
ALTER TABLE `dieta`
  ADD PRIMARY KEY (`cod_dieta`);

--
-- Indices de la tabla `dietas_receta`
--
ALTER TABLE `dietas_receta`
  ADD PRIMARY KEY (`receta`,`dieta`),
  ADD KEY `dieta` (`dieta`);

--
-- Indices de la tabla `ingrediente`
--
ALTER TABLE `ingrediente`
  ADD PRIMARY KEY (`cod_ingrediente`);

--
-- Indices de la tabla `ingredientes_receta`
--
ALTER TABLE `ingredientes_receta`
  ADD PRIMARY KEY (`receta`,`ingrediente`),
  ADD KEY `ingrediente` (`ingrediente`);

--
-- Indices de la tabla `lista_compra`
--
ALTER TABLE `lista_compra`
  ADD PRIMARY KEY (`usuario`,`ingrediente`),
  ADD KEY `ingrediente` (`ingrediente`);

--
-- Indices de la tabla `receta`
--
ALTER TABLE `receta`
  ADD PRIMARY KEY (`cod_receta`),
  ADD KEY `autor` (`autor`);

--
-- Indices de la tabla `recetas_fav`
--
ALTER TABLE `recetas_fav`
  ADD PRIMARY KEY (`usuario`,`receta`),
  ADD KEY `receta` (`receta`);

--
-- Indices de la tabla `solicitud_ingredientes`
--
ALTER TABLE `solicitud_ingredientes`
  ADD PRIMARY KEY (`cod_solicitud`),
  ADD KEY `usuario` (`usuario`),
  ADD KEY `receta` (`receta`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`nick`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alergeno`
--
ALTER TABLE `alergeno`
  MODIFY `cod_alergeno` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `dieta`
--
ALTER TABLE `dieta`
  MODIFY `cod_dieta` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `ingrediente`
--
ALTER TABLE `ingrediente`
  MODIFY `cod_ingrediente` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT de la tabla `receta`
--
ALTER TABLE `receta`
  MODIFY `cod_receta` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `solicitud_ingredientes`
--
ALTER TABLE `solicitud_ingredientes`
  MODIFY `cod_solicitud` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alergenos_ingrediente`
--
ALTER TABLE `alergenos_ingrediente`
  ADD CONSTRAINT `alergenos_ingrediente_ibfk_1` FOREIGN KEY (`alergeno`) REFERENCES `alergeno` (`cod_alergeno`),
  ADD CONSTRAINT `alergenos_ingrediente_ibfk_2` FOREIGN KEY (`ingrediente`) REFERENCES `ingrediente` (`cod_ingrediente`),
  ADD CONSTRAINT `alergenos_ingrediente_ibfk_3` FOREIGN KEY (`alergeno`) REFERENCES `alergeno` (`cod_alergeno`),
  ADD CONSTRAINT `alergenos_ingrediente_ibfk_4` FOREIGN KEY (`alergeno`) REFERENCES `alergeno` (`cod_alergeno`);

--
-- Filtros para la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD CONSTRAINT `comentario_ibfk_1` FOREIGN KEY (`cod_receta`) REFERENCES `receta` (`cod_receta`),
  ADD CONSTRAINT `comentario_ibfk_2` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`nick`);

--
-- Filtros para la tabla `dietas_receta`
--
ALTER TABLE `dietas_receta`
  ADD CONSTRAINT `dietas_receta_ibfk_1` FOREIGN KEY (`receta`) REFERENCES `receta` (`cod_receta`),
  ADD CONSTRAINT `dietas_receta_ibfk_2` FOREIGN KEY (`dieta`) REFERENCES `dieta` (`cod_dieta`);

--
-- Filtros para la tabla `ingredientes_receta`
--
ALTER TABLE `ingredientes_receta`
  ADD CONSTRAINT `ingredientes_receta_ibfk_1` FOREIGN KEY (`ingrediente`) REFERENCES `ingrediente` (`cod_ingrediente`),
  ADD CONSTRAINT `ingredientes_receta_ibfk_2` FOREIGN KEY (`receta`) REFERENCES `receta` (`cod_receta`);

--
-- Filtros para la tabla `lista_compra`
--
ALTER TABLE `lista_compra`
  ADD CONSTRAINT `lista_compra_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`nick`),
  ADD CONSTRAINT `lista_compra_ibfk_2` FOREIGN KEY (`ingrediente`) REFERENCES `ingrediente` (`cod_ingrediente`);

--
-- Filtros para la tabla `receta`
--
ALTER TABLE `receta`
  ADD CONSTRAINT `receta_ibfk_1` FOREIGN KEY (`autor`) REFERENCES `usuario` (`nick`);

--
-- Filtros para la tabla `recetas_fav`
--
ALTER TABLE `recetas_fav`
  ADD CONSTRAINT `recetas_fav_ibfk_1` FOREIGN KEY (`receta`) REFERENCES `receta` (`cod_receta`),
  ADD CONSTRAINT `recetas_fav_ibfk_2` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`nick`);

--
-- Filtros para la tabla `solicitud_ingredientes`
--
ALTER TABLE `solicitud_ingredientes`
  ADD CONSTRAINT `solicitud_ingredientes_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`nick`),
  ADD CONSTRAINT `solicitud_ingredientes_ibfk_2` FOREIGN KEY (`receta`) REFERENCES `receta` (`cod_receta`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
