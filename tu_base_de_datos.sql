-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: tu_base_de_datos
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id_producto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `precio` double NOT NULL,
  `stock` int NOT NULL,
  `precio_compra` double DEFAULT NULL,
  PRIMARY KEY (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (12,'Papas Rancheras',8,99,11.25),(13,'Papas de Camote',18.5,25,12.75),(14,'Papas Fritas de Calabacín',16.75,20,11.5),(15,'Papas con Sabor a Barbacoa',17.99,50,13.25),(16,'Papas Dulces',20.5,50,15),(17,'Papas con Chile y Limón',14.99,10,10.25),(19,'Papas de Trufa',22.5,10,18),(20,'Papas con Sabor a Pizza',16.99,50,12.75),(21,'Papas de Zanahoria',17,48,11.25),(23,'Papas con Chile Jalapeño',14.25,25,10.5),(24,'Papas con Queso y Chile',15.99,15,12.5),(25,'Papas al Horno',14.5,75,11.25),(26,'Papas con Salsa de Tomate',16.75,38,13),(27,'Papas de Kale',18.99,60,15.75),(28,'Papas con Sabor a Cebolla',14.25,85,10),(29,'Papas Gratinadas',20.5,50,17.25),(30,'Papas con Aderezo de Mostaza y Miel',17.75,75,14.5),(31,'Papas de Batata',19.99,55,16.25),(32,'Papas con Sabor a Barbacoa y Queso',22.5,55,18.75),(33,'Papas Rústicas',16.99,85,13.5),(34,'Papas con Tocino y Queso Cheddar',21.5,60,17.75),(35,'Papas con Sabor a Chile Verde',15.25,94,12),(36,'Papas con Romero y Aceite de Oliva',19.75,50,16.5),(37,'Papas Hasselback',17.5,70,14.25),(38,'Papas con Sabor a Salsa BBQ',14.99,80,11.75),(39,'Papas con Albahaca y Queso Feta',20.25,55,16.75),(40,'Papas con Sabor a Ajo y Hierbas',18.5,65,15.25),(41,'Papas con Sabor a Limón y Pimienta',16.5,75,13.25),(42,'Papas con Salsa de Queso Azul',19.25,60,15.5),(43,'Papas con Guacamole',17.99,70,14),(44,'Papas con Sabor a Tocino y Cebolla Caramelizada',21.75,55,18.25),(45,'Papas con Sazonador Ranch',15.5,85,12.75),(46,'Papas con Sabor a Chile Chipotle',18.5,65,14.5),(47,'Papas con Queso Parmesano',20.99,50,17),(48,'Papas con Sabor a Queso Gouda',19.5,60,15.75),(49,'Papas con Sabor a Jalapeño y Queso Crema',22.25,45,19),(50,'Papas con Sabor a Mantequilla y Perejil',17.25,70,13.75),(51,'Papas con Aderezo de Yogur y Hierbas',18.99,55,15.25),(52,'Papas con Sabor a Salsa de Queso Cheddar',15.75,75,12.5),(53,'Papas con Sabor a Salsa de Queso Jalapeño',19.5,50,16),(54,'Papas con Sabor a Crema de Ajo',16.25,70,13),(55,'Papas con Sabor a Mostaza y Eneldo',20.75,50,17.25),(56,'Papas con Aderezo de Miel y Mostaza',17.5,65,14),(57,'Papas con Sabor a Chile Habanero',14.99,75,12.25),(58,'Papas con Sabor a Salsa de Queso Azul y Tocino',21.99,35,18.5),(59,'Papas con Sabor a Guayaba y Chile',19.25,55,16),(60,'Galletas de Avena',12.99,90,8.5),(61,'Galletas de Chocolate',14.5,80,9.75),(62,'Galletas Rellenas de Vainilla',13.75,80,10),(63,'Galletas de Mantequilla',15.99,90,11.25),(64,'Galletas de Coco',18.5,70,12.75),(65,'Galletas Integrales',16.75,80,11.5),(66,'Galletas de Frutas',17.99,95,13.25),(67,'Galletas de Almendra',20.5,36,15),(68,'Galletas de Avena y Pasas',14.99,85,10.25),(69,'Galletas de Miel',19.75,75,14),(70,'Galletas de Limón',22.5,50,18),(71,'Galletas de Nuez',16.99,65,12.75),(72,'Galletas de Chispas de Chocolate',15.5,110,11.25),(73,'Galletas de Jengibre',18.75,80,14.5),(75,'Galletas de Vainilla',20.99,65,16.75),(76,'Galletas de Mocha',18.5,75,15.25),(77,'Galletas de Avena y Manzana',21.5,60,17.75),(78,'Galletas de Canela',14.25,95,10.5),(79,'Galletas de Avellana',19.25,55,16.25),(80,'Galletas de Avena y Miel',16.5,75,13.25),(81,'Galletas de Choco-Canela',19.25,60,15.5),(82,'Galletas de Arándanos',17.99,70,14),(83,'Galletas de Mantequilla de Maní',21.75,45,18.25),(84,'Galletas Rellenas de Chocolate',15.5,80,12.75),(85,'Galletas de Matcha',18.5,65,14.5),(86,'Galletas de Naranja',20.99,50,17),(87,'Galletas de Cacahuate y Chocolate',19.5,60,15.75),(88,'Galletas de Vainilla y Fresa',22.25,35,19),(89,'Galletas de Jalea de Frambuesa',17.25,70,13.75),(90,'Galletas de Mantequilla de Almendra',18.99,55,15.25),(91,'Galletas de Cacao Nibs',15.75,75,12.5),(92,'Galletas de Piña y Coco',19.5,50,16),(93,'Galletas de Cerveza y Pretzel',16.25,70,13),(94,'Galletas de Trigo Sarraceno',20.75,60,17.25),(95,'Galletas de Café',17.5,65,14),(96,'Galletas de Mora',14.99,85,12.25),(97,'Galletas de Tiramisú',21.99,45,18.5),(98,'Galletas de Plátano y Chocolate',19.25,55,16),(99,'Galletas de Limón y Almendra',16.5,75,13.25),(100,'Galletas de Matcha y Coco',19.25,50,15.5),(101,'Galletas de Almendra y Arándanos',17.99,70,14),(102,'Galletas de Chocolate Blanco y Frambuesa',21.75,45,18.25),(103,'Galletas Rellenas de Crema de Avellana',15.5,75,12.75),(104,'Galletas de Té Chai',18.5,65,14.5),(105,'Galletas de Nuez y Maple',20.99,50,17),(106,'Galletas de Pistacho',19.5,60,15.75),(107,'Galletas de Cacao y Avellana',22.25,45,19),(108,'Galletas de Mantequilla y Frutas Tropicales',17.25,80,13.75),(109,'Galletas de Chocolate Amargo',18.99,55,15.25),(110,'Galletas de Vainilla y Canela',15.75,75,12.5),(111,'Galletas de Arroz Inflado y Chocolate',19.5,50,16),(112,'Galletas de Avena y Pasas con Canela',16.25,70,13),(113,'Galletas de Sésamo y Miel',20.75,60,17.25),(114,'Galletas de Frutos del Bosque',17.5,65,14),(115,'Galletas de Fresa y Chocolate Blanco',14.99,85,12.25),(116,'Galletas de Coco y Mango',21.99,45,18.5),(117,'Galletas de Cacahuate y Caramelo',19.25,50,16),(118,'Papas Sabritas Tradicionales',17.5,120,13.25),(119,'Galletas Ricolino Bubu Lubu',22.99,90,18.5),(120,'Papas Ruffles Original',19.75,110,15),(121,'Galletas Marinela Polvorones',16.99,100,12.75),(122,'Papas Barcel Takis Fuego',21.5,140,17.25),(123,'Galletas Oreo Double Stuf',25.99,70,20.5),(124,'Papas Sabritas Chiles Rellenos',18.25,130,14),(125,'Galletas Marías Gamesa',14.5,95,11.75),(126,'Papas Ruffles Queso y Jalapeño',23.5,120,19),(127,'Galletas Diamante',15.75,85,12.25),(128,'Papas Sabritas Sazonadas',20.99,110,16.5),(129,'Galletas Tía Rosa Linaza',17.25,80,14),(130,'Papas Barcel Chetos Poffs',19.5,100,15.75),(131,'Galletas Mamut',18.99,90,14.25),(132,'Papas Ruffles Queso Ranchero',22.75,130,18.5),(133,'Galletas Marias Choco Roles',16.5,105,12.75),(134,'Papas Sabritas Adobadas Clásicas',21.25,115,17.5),(135,'Galletas Barritas Marinela',19.99,75,16.25),(136,'Papas Takis Nitro',15.5,100,11.75),(137,'Galletas Triki Trakes',23.99,80,20),(138,'Papas Barcel Piquitos',18.75,120,15.25),(139,'Refresco Coca-Cola 600ml',12.5,50,8),(140,'Tortillas de Maíz (kg)',15.99,30,10.5),(141,'Papas Sabritas Clásicas',18.75,40,15),(142,'Pan Blanco Bimbo',22.5,20,18),(143,'Huevo (docena)',28.99,10,25.5),(144,'Leche Lala Entera 1L',25.75,15,20),(145,'Frijoles en Lata La Costeña',15.99,25,12.5),(146,'Agua embotellada Bonafont 500ml',8.5,60,5.75),(147,'Jabón Zote',9.99,20,7.5),(148,'Cereal Kellogg\'s Corn Flakes',28.5,15,23),(149,'Atún en Agua Calvo',19.25,30,15.75),(150,'Café Soluble Nescafé 100g',32.99,10,28),(151,'Azúcar Estándar (kg)',12.75,25,9.5),(152,'Sopa Instantánea Maruchan',7.5,50,5.25),(153,'Chocolate Abuelita 90g',15.5,15,12),(154,'Cepillo de Dientes Colgate',18.99,20,14.5),(155,'Papel Higiénico Suave',24.5,10,20),(156,'Aceite Vegetal 1L',22.99,15,18.5),(157,'Detergente en Polvo Ariel',30.75,10,25),(158,'Sal Refisal (kg)',8.99,30,6.5),(159,'Galletas Gamesa Emperador',16.99,45,12.5),(160,'Cerveza Modelo 355ml',18.5,60,15),(161,'Papel Aluminio Reynolds',12.75,25,9.5),(162,'Aceitunas La Española 200g',14.99,40,11.5),(163,'Botana Cheetos 100g',10.5,55,7.75),(164,'Chiles enlatados La Costeña',22.25,30,18),(165,'Pañuelos Kleenex',9.99,15,7.5),(166,'Jugo de Naranja Del Valle 1L',20.5,20,15.75),(167,'Pescado enlatado Sardimar',15.75,35,12),(168,'Salsa Picante Valentina 370ml',8.99,50,6.5),(169,'Botella de Vino Tinto 750ml',45.99,10,38),(170,'Paletas de Hielo Sabor Fresa',5.5,75,3.75),(171,'Pipas G La Morena',12.25,30,9),(172,'Harina de Maíz Maseca 1kg',18.75,25,14.5),(173,'Caja de Té Manzanilla',14.5,20,11.25),(174,'Desodorante Axe',25.99,15,20.5),(175,'Caja de Cigarros Marlboro',50.75,54,45),(176,'Bolsa de Hielo 3kg',8.25,12,5.75),(177,'Shampoo Pantene 400ml',19.5,18,16),(178,'Yogurt Yoplait Natural 125g',7.99,30,5.5),(179,'Pilas AA Duracell (paquete de 4)',24.99,20,20.5),(180,'Papel Toalla Suave 2 Rollos',15.5,25,12),(181,'Lata de Chiles Jalapeños',10.75,40,8.5),(182,'Dulces de Tamarindo Vero',8.99,50,6.75),(183,'Salchichas Viena Fud',17.25,30,14),(184,'Queso Oaxaca 200g',22.5,15,18.5),(185,'Cereal Nestlé Nesquik',28.75,18,24),(186,'Cepillo y Pasta de Dientes Colgate',19.99,22,15.75),(187,'Gelatina Royal de Fresa',12.5,35,9.75),(188,'Paquete de Pañuelos de Papel',7.25,40,5.5),(189,'Caja de Cigarros Camel',52.75,10,47),(190,'Aceite de Oliva Extra Virgen 500ml',34.5,12,29),(191,'Champú y Acondicionador Herbal Essences',27.99,18,22.5),(192,'Caja de Cereal Trix',19.75,20,15.5),(193,'Té Verde Lipton 20 Bolsitas',15.99,25,12.5),(194,'Caja de Pañuelos de Papel Kleenex',9.25,30,6.75),(195,'Pasta de Dientes Sensodyne',22.5,15,18),(196,'Lápiz Labial Maybelline',16.25,10,12.5),(197,'Caja de Té de Hierbabuena',14.99,25,11.75),(198,'Pilas AAA Energizer (paquete de 4)',21.5,18,17),(199,'Galletas Marías Gamesa',14.5,40,11.25),(200,'Pasta Alfredo 500g',18.99,20,15.5),(201,'Shampoo Clear Men 2 en 1',25.5,15,21),(202,'Lata de Maíz Dulce',12.25,30,9.5),(203,'Bolsa de Dulces Vero Mango',9.75,45,7.5),(204,'Caja de Cereal Special K',26.99,25,22.5),(205,'Desodorante Secret',19.5,20,15.75),(206,'Queso Panela 200g',20.75,15,17.5),(207,'Sopa Maruchan Sabor Pollo',7.99,55,5.75),(208,'Paquete de Pilas AAA (paquete de 8)',32.99,10,28),(209,'Detergente Liquido Ariel 1L',24.75,12,20),(210,'Caja de Cigarros Winston',50.25,8,45),(211,'Salsa de Tomate La Costeña',15.5,25,12.75),(212,'Paquete de Pañuelos Kleenex',8.5,40,6.25),(213,'Crema de Cacahuate Skippy',18.25,30,14),(214,'Cereal Quaker Avena',22.99,20,18.5),(215,'Botella de Vino Blanco 750ml',48.5,10,42),(216,'Chiles enlatados Embasa',11.99,35,9.75),(217,'Caja de Té de Manzanilla Celestial',14.75,25,11.5),(218,'Paquete de Pañuelos de Papel Puffs',9.25,30,6.75),(219,'Refresco Coca-Cola 2L',20.99,30,15),(220,'Agua embotellada Ciel 1.5L',12.5,40,8.75),(221,'Jugo de Naranja Del Valle 1L',18.75,25,14),(222,'Refresco Pepsi 2L',19.5,35,14.5),(223,'Agua Mineral Topo Chico 600ml',8.99,50,5.75),(224,'Jugo de Manzana Boing 1L',15.99,30,11.5),(225,'Refresco Sprite 2L',20.75,28,16.5),(226,'Agua embotellada Bonafont 500ml',7.5,60,4.75),(227,'Té Lipton Citrus 500ml',14.25,45,10.5),(228,'Refresco Fanta 2L',18.5,32,14),(229,'Refresco Sidral Mundet 600ml',10.99,38,7.5),(230,'Agua de Coco Vita 500ml',22.5,20,18),(231,'Jugo de Mango Jumex 1L',13.75,42,10),(232,'Refresco Dr Pepper 2L',21.99,25,17.5),(233,'Té Verde Arizona 680ml',16.5,30,13.25),(234,'Refresco Sangría Señorial 355ml',9.25,63,6.75),(235,'Agua embotellada Electropura 1L',11.5,38,8.25),(236,'Jugo de Uva Del Valle 500ml',14.99,40,11.75),(237,'Refresco Squirt 600ml',12.75,6,9.5),(238,'Agua de Horchata Santa Clara 1L',17.5,22,13.75),(239,'pan tostado',45,10,NULL),(240,'popotes de menta',5,50,NULL),(241,'Cigarro de menta-Malvoro',6,50,NULL),(242,'baso de agua de cafe',15,50,NULL),(245,'pepinos de sal',1,0,NULL),(246,'concha',7,8,NULL),(247,'pepito',30,30,NULL),(248,'pozole del rico con chilito',67,100,NULL),(249,'sal y pimienta',19,190,NULL);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `id_ticket` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `contenido` text,
  PRIMARY KEY (`id_ticket`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (51,'2023-12-01 11:23:52','Papas Rancheras - Cantidad: 39\n'),(52,'2023-12-07 15:40:53','Papas de Camote - Cantidad: 2\n'),(53,'2023-12-07 15:52:07','Papas con Ajo y Parmesano - Cantidad: 3\n\n\nTotal de la compra: $56.25'),(54,'2023-12-07 17:43:30','Papas al Horno - Cantidad: 5\n\n\nTotal de la compra: $72.5'),(56,'2024-03-25 13:48:59','Galletas de Almendra - Cantidad: 12\n\n\nTotal de la compra: $246.0'),(57,'2024-03-25 14:53:33','Pato a la naranja - Cantidad: 23\nTónico facial - Cantidad: 10\n\n\nTotal de la compra: $0.0');
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(255) NOT NULL,
  `contraseña` varchar(70) DEFAULT NULL,
  `estado` enum('activo','inactivo') DEFAULT 'activo',
  `puesto` varchar(255) DEFAULT 'null',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (14,'pepin','f10d91a7596bf5a6773579ff1306afdc363b0be08602c768907c09261cad3a56','inactivo','null'),(15,'admin','f10d91a7596bf5a6773579ff1306afdc363b0be08602c768907c09261cad3a56','activo','null'),(16,'pe','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','activo','null'),(17,'jose','f10d91a7596bf5a6773579ff1306afdc363b0be08602c768907c09261cad3a56','inactivo','null'),(18,'jesus','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','inactivo','null'),(19,'jesus','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','activo','null');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `nombre_producto` varchar(60) NOT NULL,
  `cantidad` int DEFAULT NULL,
  `ganancias` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`nombre_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES ('agua de sabor',18,0.00),('Galletas de Almendra',12,20.50),('Papas al Horno',5,14.50),('Papas con Ajo y Parmesano',3,18.75),('Papas con Chile Jalapeño',7,42.75),('Papas de Camote',4,55.50),('Papas de Zanahoria',1,17.00),('Papas Fritas de Calabacín',5,16.75),('Papas Rancheras',79,647.60);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-11 20:13:53
