-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: freedb.tech    Database: freedbtech_FinalProject
-- ------------------------------------------------------
-- Server version	5.7.34-0ubuntu0.18.04.1

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
-- Table structure for table `diagnoseds`
--

DROP TABLE IF EXISTS `diagnoseds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnoseds` (
  `diagnosed` varchar(45) NOT NULL,
  `diagnostic` varchar(45) NOT NULL,
  `lasttrainmotorlevel` int(11) NOT NULL DEFAULT '1',
  `lasttrainmotorstage` int(11) NOT NULL DEFAULT '1',
  `lastdiagnosismotorimg` int(11) NOT NULL DEFAULT '1',
  `lastdiagnosisnum` int(11) NOT NULL DEFAULT '1',
  `lasttrainsynclevel` varchar(45) NOT NULL DEFAULT '1',
  `lasttrainsyncstage` varchar(45) NOT NULL DEFAULT '1',
  `lastdiagnosissyncimg` int(11) NOT NULL DEFAULT '1',
  `lastdiagnosissyncnum` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`diagnosed`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnoseds`
--

LOCK TABLES `diagnoseds` WRITE;
/*!40000 ALTER TABLE `diagnoseds` DISABLE KEYS */;
INSERT INTO `diagnoseds` VALUES ('3','admin',1,1,1,1,'1','1',0,0),('a','mhemmad',1,1,1,1,'1','1',0,0),('ahmad','admin',1,1,1,1,'1','1',0,0),('ayman','mhemmad',3,3,5,2,'3','2',3,12),('d','admin',1,1,1,1,'1','1',1,1),('m','mhemmad',1,2,1,1,'1','1',1,2),('x','d',1,1,1,1,'1','1',2,1);
/*!40000 ALTER TABLE `diagnoseds` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-26 21:41:04
