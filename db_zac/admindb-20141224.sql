CREATE DATABASE  IF NOT EXISTS `admindb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `admindb`;
-- MySQL dump 10.13  Distrib 5.6.11, for Win32 (x86)
--
-- Host: localhost    Database: admindb
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
-- Table structure for table `evaluateresult`
--

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
  `madmdevimp` int(11) DEFAULT NULL,
  `madmvalue` double DEFAULT NULL,
  PRIMARY KEY (`idresult`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluateresult`
--

LOCK TABLES `evaluateresult` WRITE;
/*!40000 ALTER TABLE `evaluateresult` DISABLE KEYS */;
INSERT INTO `evaluateresult` VALUES (1,'会话类','高级','打击',1,1,1,0.75),(2,'会话类','高级','监控',1,2,4,0.6482142857142857),(3,'会话类','次高级','打击',3,4,8,0.55),(4,'会话类','次高级','监控',3,8,15,0.44821428571428573),(5,'会话类','中级','打击',6,10,10,0.5277777777777777),(6,'会话类','中级','监控',6,18,19,0.4259920634920635),(7,'会话类','低级','打击',10,20,11,0.5055555555555555),(8,'会话类','低级','监控',10,33,22,0.4037698412698413),(9,'流类','高级','打击',2,3,2,0.6785132549133545),(10,'流类','高级','监控',2,6,6,0.5767275406276402),(11,'流类','次高级','打击',5,9,12,0.47851325491335445),(12,'流类','次高级','监控',5,16,24,0.3767275406276402),(13,'流类','中级','打击',9,19,14,0.4562910326911322),(14,'流类','中级','监控',9,31,26,0.35450531840541794),(15,'流类','低级','打击',14,34,18,0.43406881046891),(16,'流类','低级','监控',14,52,29,0.33228309618319574),(17,'交互类','高级','打击',4,7,3,0.6666666666666666),(18,'交互类','高级','监控',4,13,7,0.5648809523809524),(19,'交互类','次高级','打击',8,17,13,0.4666666666666667),(20,'交互类','次高级','监控',8,28,25,0.3648809523809524),(21,'交互类','中级','打击',13,32,17,0.4444444444444444),(22,'交互类','中级','监控',13,49,28,0.34265873015873016),(23,'交互类','低级','打击',19,53,21,0.42222222222222217),(24,'交互类','低级','监控',19,77,31,0.3204365079365079),(25,'背景类','高级','打击',7,14,5,0.6458333333333333),(26,'背景类','高级','监控',7,24,9,0.5440476190476191),(27,'背景类','次高级','打击',12,29,16,0.4458333333333333),(28,'背景类','次高级','监控',12,45,27,0.34404761904761905),(29,'背景类','中级','打击',18,50,20,0.4236111111111111),(30,'背景类','中级','监控',18,73,30,0.32182539682539685),(31,'背景类','低级','打击',25,78,23,0.4013888888888889),(32,'背景类','低级','监控',25,109,32,0.29960317460317465);
/*!40000 ALTER TABLE `evaluateresult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logininfo`
--

DROP TABLE IF EXISTS `logininfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logininfo` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `usertype` varchar(45) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logininfo`
--

LOCK TABLES `logininfo` WRITE;
/*!40000 ALTER TABLE `logininfo` DISABLE KEYS */;
INSERT INTO `logininfo` VALUES ('user1','111111','高级'),('user2','111111','次高级'),('user3','111111','中级'),('user4','111111','低级');
/*!40000 ALTER TABLE `logininfo` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `missiontype` VALUES (1,800,1,1,5,'打击'),(2,300,2,2,10,'监控');
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
INSERT INTO `traffictype` VALUES (1,150,1,0.01,1,'会话类'),(2,200,1,0.03,2,'流类'),(3,1000,100,0.1,3,'交互类'),(4,1000,100,0.1,4,'背景类');
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
INSERT INTO `usertype` VALUES (1,1,1,1,'高级'),(2,2,2,2,'次高级'),(3,3,3,2,'中级'),(4,4,4,3,'低级');
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

-- Dump completed on 2014-12-24 15:23:10
