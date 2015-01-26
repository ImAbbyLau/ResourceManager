CREATE DATABASE  IF NOT EXISTS `jrrm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jrrm`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: jrrm
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
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request` (
  `Address` char(15) NOT NULL,
  `PackageID` int(11) NOT NULL,
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
  `NetID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` VALUES ('127.0.0.1',1,4,55,3,'2-3,1-2',2000,50,15,10,'192.168.1.3',1,2),('127.0.0.1',2,3,12,3,'1-1,2-2,3-3',1000,50,10,1,'0',0,0),('127.0.0.1',3,3,12,3,'1-1,2-2',1000,50,10,1,'192.168.1.2',2,2),('127.0.0.1',4,3,12,3,'1-1,2-2,3-3',1000,50,10,1,'0',0,0);
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `Address` char(15) NOT NULL,
  `NetworkType` int(11) NOT NULL,
  `NetworkID` int(11) NOT NULL,
  `BandWidth` double NOT NULL,
  `Delay` double NOT NULL,
  `Jitter` double NOT NULL,
  `PacketLoss` double NOT NULL,
  `LoadRate` double NOT NULL,
  PRIMARY KEY (`Address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES ('127.0.0.1',2,5,2000,100,10,15,30),('192.168.1.1',3,2,3000,80,20,5,40),('192.168.1.2',2,2,4000,120,5,3,30),('192.168.1.3',1,1,5000,40,10,10,30),('192.168.1.4',2,3,4000,60,15,8,30),('192.168.1.5',1,2,4000,70,5,20,30),('192.168.1.6',1,3,2000,30,20,6,30),('192.168.1.7',3,1,3000,50,50,4,30),('192.168.1.8',2,1,3000,90,5,6,30);
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-18 20:17:15
