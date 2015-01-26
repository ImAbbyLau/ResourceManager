CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `test`;
-- MySQL dump 10.13  Distrib 5.6.11, for Win32 (x86)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.6.13-log

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
-- Table structure for table `missiontype`
--

DROP TABLE IF EXISTS `missiontype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `missiontype` (
  `idmissiontype` int(11) NOT NULL,
  `bandwidth` double DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `reliability` double DEFAULT NULL,
  `responseTime` double DEFAULT NULL,
  `missiontitle` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idmissiontype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `missiontype`
--

LOCK TABLES `missiontype` WRITE;
/*!40000 ALTER TABLE `missiontype` DISABLE KEYS */;
INSERT INTO `missiontype` VALUES (1,100,1,1,5,'打击'),(2,200,2,2,10,'监控'),(3,150,2,2.5,2,'防御');
/*!40000 ALTER TABLE `missiontype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trafficinfo`
--

DROP TABLE IF EXISTS `trafficinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trafficinfo` (
  `idtrafficinfo` int(11) NOT NULL AUTO_INCREMENT,
  `traffictype` int(11) DEFAULT NULL,
  `usertype` int(11) DEFAULT NULL,
  `missiontype` int(11) DEFAULT NULL,
  `2Deva` int(11) DEFAULT NULL,
  `3Deva` int(11) DEFAULT NULL,
  PRIMARY KEY (`idtrafficinfo`),
  KEY `traffictype_idx` (`traffictype`),
  KEY `usertype_idx` (`usertype`),
  KEY `missiontype_idx` (`missiontype`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trafficinfo`
--

LOCK TABLES `trafficinfo` WRITE;
/*!40000 ALTER TABLE `trafficinfo` DISABLE KEYS */;
INSERT INTO `trafficinfo` VALUES (1,2,2,1,5,9),(2,4,1,2,7,33),(3,1,3,2,6,13),(4,4,4,2,25,109),(7,1,1,1,1,1);
/*!40000 ALTER TABLE `trafficinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trafficinfo2`
--

DROP TABLE IF EXISTS `trafficinfo2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trafficinfo2` (
  `idtrafficinfo` int(11) NOT NULL AUTO_INCREMENT,
  `traffictype` int(11) DEFAULT NULL,
  `usertype` int(11) DEFAULT NULL,
  `missiontype` int(11) DEFAULT NULL,
  `evatype` int(11) DEFAULT NULL,
  `evavalue` int(11) DEFAULT NULL,
  PRIMARY KEY (`idtrafficinfo`),
  KEY `traffictype_idx` (`traffictype`),
  KEY `usertype_idx` (`usertype`),
  KEY `missiontype_idx` (`missiontype`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trafficinfo2`
--

LOCK TABLES `trafficinfo2` WRITE;
/*!40000 ALTER TABLE `trafficinfo2` DISABLE KEYS */;
INSERT INTO `trafficinfo2` VALUES (1,2,4,2,1,14),(2,1,2,1,1,3),(3,4,3,1,1,18),(4,2,1,1,1,2),(5,4,4,2,1,25),(6,2,2,1,2,9),(7,4,4,2,2,109),(8,1,1,1,2,1);
/*!40000 ALTER TABLE `trafficinfo2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `traffictype`
--

DROP TABLE IF EXISTS `traffictype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `traffictype` (
  `idtraffictype` int(11) NOT NULL,
  `latency` double DEFAULT NULL,
  `jitter` double DEFAULT NULL,
  `packetlossratio` double DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `traffictitle` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idtraffictype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traffictype`
--

LOCK TABLES `traffictype` WRITE;
/*!40000 ALTER TABLE `traffictype` DISABLE KEYS */;
INSERT INTO `traffictype` VALUES (1,150,1,0.01,1,'会话类'),(2,200,1,0.03,2,'流类'),(3,1000,100,0.1,3,'交互类'),(4,1000,100,0.1,4,'背景类'),(5,100,2000,0.05,5,'测试类'),(6,200,1000,0.01,10,'新增类'),(7,300,100,0.055,7,'控制流类');
/*!40000 ALTER TABLE `traffictype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertype`
--

DROP TABLE IF EXISTS `usertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertype` (
  `idusertype` int(11) NOT NULL,
  `classification` int(11) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `securitypolicy` int(11) DEFAULT NULL,
  `usertitle` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idusertype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertype`
--

LOCK TABLES `usertype` WRITE;
/*!40000 ALTER TABLE `usertype` DISABLE KEYS */;
INSERT INTO `usertype` VALUES (1,1,1,1,'高级'),(2,2,2,2,'次高级'),(3,2,3,2,'中级'),(4,3,4,3,'低级'),(5,1,3,5,'中高级');
/*!40000 ALTER TABLE `usertype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
--add by qx
DROP TABLE IF EXISTS `evaluateresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluateresult` (
  `idresult` int(11) NOT NULL,
  `trafficname` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `missionname` varchar(45) DEFAULT NULL,
  `twodevimp` int(11) DEFAULT NULL,
  `threedevimp` int(11) DEFAULT NULL,
  `madmdevimp` double DEFAULT NULL,
  PRIMARY KEY (`idresult`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
-- Dump completed on 2013-11-04 10:44:00
