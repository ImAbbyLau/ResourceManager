-- MySQL dump 10.13
--
-- Host: localhost    Database: PR
-- Server version	5.0.95

--
-- Table structure for table `LAPEP`
--

DROP TABLE IF EXISTS `LAPEP`;
CREATE TABLE `LAPEP` (
  `LAPEP` varchar(32) NOT NULL ,
  `sindex` int(11) NOT NULL,
  `Domain` varchar(15) default NULL,
  `Address` varchar(15) default NULL,
  `status` varchar(5) default NULL,
  PRIMARY KEY  (`LAPEP`,`sindex`)
) ;

--
-- Dumping data for table `LAPEP`
--

INSERT INTO `LAPEP` VALUES ('1A',1,'192.168.10.0','192.168.0.10','ON');
INSERT INTO `LAPEP` VALUES ('2A',2,'192.168.10.0','192.168.0.10','ON');
INSERT INTO `LAPEP` VALUES ('3A',3,'192.168.10.0','192.168.0.10','ON');


--
-- Table structure for table `LBPEP`
--

DROP TABLE IF EXISTS `LBPEP`;
CREATE TABLE `LBPEP` (
  `LBPEP` varchar(32) NOT NULL,
  `sindex` int(11) NOT NULL,
  `Domain` varchar(15) default NULL,
  `Address` varchar(15) default NULL,
  `status` varchar(5) default NULL,
  PRIMARY KEY  (`LBPEP`,`sindex`)
) ;

--
-- Dumping data for table `LBPEP`
--

INSERT INTO `LBPEP` VALUES ('1B',1,'192.168.20.0','192.168.0.20','ON');
INSERT INTO `LBPEP` VALUES ('2B',2,'192.168.20.0','192.168.0.20','ON');
INSERT INTO `LBPEP` VALUES ('3B',3,'192.168.20.0','192.168.0.20','ON');


--
-- Table structure for table `LPDP`
--

DROP TABLE IF EXISTS LPDP;
CREATE TABLE LPDP (
  `PDP_ID` int(11) NOT NULL,
  `Address` varchar(15) default NULL,
  `Domain` varchar(15) default NULL,
  `status` varchar(10) default NULL,
  `pdpPort` int(11) default NULL,
  `GatewayPEPID`varchar(32) default NULL,
  PRIMARY KEY  (`GatewayPEPID`)
) ;

--
-- Dumping data for table `LPDP`
--


INSERT INTO `LPDP` VALUES (1,'192.168.0.10','192.168.0.10','ON',3188,'1A');
INSERT INTO `LPDP` VALUES (2,'192.168.0.20','192.168.0.20','ON',3188,'1B');







