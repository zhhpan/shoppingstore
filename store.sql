-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: store_cart
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Current Database: `store_cart`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `store_cart` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `store_cart`;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `num` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `table_name_product_product_id_fk` (`product_id`),
  KEY `table_name_user_user_id_fk` (`user_id`),
  CONSTRAINT `table_name_product_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `store_product`.`product` (`product_id`),
  CONSTRAINT `table_name_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `store_user`.`user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `store_category`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `store_category` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `store_category`;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL,
  `category_name` char(10) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'手机'),(2,'电脑');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `store_collect`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `store_collect` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `store_collect`;

--
-- Table structure for table `collect`
--

DROP TABLE IF EXISTS `collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collect` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `collect_time` bigint NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `collect_product_product_id_fk` (`product_id`),
  KEY `collect_user_user_id_fk` (`user_id`),
  CONSTRAINT `collect_product_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `store_product`.`product` (`product_id`),
  CONSTRAINT `collect_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `store_user`.`user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collect`
--

LOCK TABLES `collect` WRITE;
/*!40000 ALTER TABLE `collect` DISABLE KEYS */;
/*!40000 ALTER TABLE `collect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `store_order`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `store_order` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `store_order`;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `product_num` int NOT NULL,
  `product_price` double NOT NULL,
  `order_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orders_product_product_id_fk` (`product_id`),
  KEY `orders_user_user_id_fk` (`user_id`),
  CONSTRAINT `orders_product_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `store_product`.`product` (`product_id`),
  CONSTRAINT `orders_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `store_user`.`user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (3,1709992940752,1,2,56,2200,1709992940752),(4,1709992940752,1,3,1,2300,1709992940752);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `store_product`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `store_product` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `store_product`;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` char(100) NOT NULL,
  `category_id` int NOT NULL,
  `product_title` char(30) NOT NULL,
  `product_intro` text NOT NULL,
  `product_picture` char(200) DEFAULT NULL,
  `product_price` double NOT NULL,
  `product_selling_price` double NOT NULL,
  `product_num` int NOT NULL,
  `product_sales` int NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_id` (`product_id`),
  KEY `product_st__fk` (`category_id`),
  FULLTEXT KEY `full_text_index` (`product_name`,`product_intro`,`product_title`),
  CONSTRAINT `product_st__fk` FOREIGN KEY (`category_id`) REFERENCES `store_category`.`category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'iPhone 12',1,'Apple/苹果 iPhone 12','Apple/苹果 iPhone 12苹果12promax双卡双待iphone12promax5G手机','https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp',2500,2190,20,8),(2,'iPhone 12 max',1,'12max','12m','https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp',2600,2200,-56,7),(3,'iPhone 13',1,'13','13','https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp',2700,2300,18,3),(4,'iPhone 13 max',1,'13max','13m','https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp',2800,2400,20,5),(5,'iPhone 14',1,'14','14','https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp',2900,2500,20,1),(6,'iPhone 14 max',1,'14max','14m','https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp',3000,2600,20,6),(7,'iPhone 15',1,'15','15','https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp',3100,2700,20,2),(8,'iPhone 15 max',1,'15max','15m','https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp',3200,2800,20,4),(9,'华硕',2,'天选2','华硕天选2','https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp',5000,4500,10,3);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `store_user`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `store_user` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `store_user`;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '地址id',
  `linkman` varchar(30) NOT NULL COMMENT '联系人',
  `phone` varchar(30) NOT NULL COMMENT '手机号',
  `address` varchar(30) DEFAULT NULL COMMENT '地址',
  `user_id` int NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'张三','123','北京',1),(12,'张三','1243','北京',1);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `user_phonenumber` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin123','123456','12346578984'),(2,'admin','E9B6D7A15DCA0624DBAB8970F2FE3623','18605462315');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-14 22:56:18
