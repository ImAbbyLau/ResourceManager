CREATE DATABASE  IF NOT EXISTS `terminal` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `terminal`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: terminal
-- ------------------------------------------------------
-- Server version	5.6.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `terrequest`
--

DROP TABLE IF EXISTS `terrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terrequest` (
  `RequestId` int(11) NOT NULL AUTO_INCREMENT,
  `TrafficType` int(11) NOT NULL,
  `TrafficImportance` int(11) NOT NULL,
  `Preference` int(11) NOT NULL,
  `VisibleNet` varchar(100) NOT NULL,
  `BandWidth` double NOT NULL,
  `Delay` double NOT NULL,
  `Jitter` double NOT NULL,
  `PacketLoss` double NOT NULL,
  `LRRMIP` char(15) DEFAULT NULL,
  `NetType` int(11) DEFAULT NULL,
  `NetID` int(11) DEFAULT NULL,
  PRIMARY KEY (`RequestId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terrequest`
--

LOCK TABLES `terrequest` WRITE;
/*!40000 ALTER TABLE `terrequest` DISABLE KEYS */;
INSERT INTO `terrequest` VALUES (1,3,12,3,'1-1,2-2,3-3',1000,50,10,1,NULL,NULL,NULL),(2,3,12,3,'1-1,2-2',1000,50,10,1,'192.168.1.2',2,2),(3,3,12,3,'1-1,2-2,3-3',1000,50,10,1,NULL,NULL,NULL),(4,2,11,2,'1-1,2-2,3-3',1230,50,11,2,NULL,NULL,NULL);
/*!40000 ALTER TABLE `terrequest` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-18 20:18:13