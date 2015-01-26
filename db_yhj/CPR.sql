-- MySQL dump 10.13
--
-- Host: localhost    Database: CPR
-- Server version	5.0.95

--
-- Table structure for table `CPEP`
--

DROP TABLE IF EXISTS `CPEP`;
CREATE TABLE `CPEP` (
  `CPEP` varchar(32) NOT NULL,
  `sindex` int(11) NOT NULL,
  `Domain` varchar(15) default NULL,
  `Address` varchar(15) default NULL,
  `status` varchar(5) default NULL,
  PRIMARY KEY  (`CPEP`,`sindex`)
) ;

--
-- Dumping data for table `CPEP`
--

INSERT INTO `CPEP` VALUES ('LA',1,'192.168.0.10','192.168.0.1','ON');
INSERT INTO `CPEP` VALUES ('LB',2,'192.168.0.20','192.168.0.1','ON');
INSERT INTO `CPEP` VALUES ('LC',3,'192.168.0.30','192.168.0.1','ON');



--
-- Table structure for table `CPEP`
--

DROP TABLE IF EXISTS `CPEP`;
CREATE TABLE `CPEP` (
  `PDP_ID` int(11) NOT NULL,
  `Address` varchar(15) default NULL,
  `Domain` varchar(15) default NULL,
  `status` varchar(10) default NULL,
  `pdpPort` int(11) default NULL,
  `GatewayCPEPID` varchar(32) default NULL,
  PRIMARY KEY  (`GatewayCPEPID`)
);

--
-- Dumping data for table `CPEP`
--


INSERT INTO `CPEP` VALUES (1,'192.168.0.1','192.168.0.10','ON',3288,'LA');
INSERT INTO `CPEP` VALUES (1,'192.168.0.1','192.168.0.20','ON',3288,'LB');
INSERT INTO `CPEP` VALUES (1,'192.168.0.1','192.168.0.30','ON',3288,'LC');





--
-- Table structure for table `CPDP`
--

DROP TABLE IF EXISTS `CPDP`;
CREATE TABLE `CPDP` (
  `PDP_ID` int(11) NOT NULL,
  `Address` varchar(15) default NULL,
  `Domain` varchar(15) default NULL,
  `status` varchar(10) default NULL,
  `pdpPort` int(11) default NULL,
  `GatewayCPEPID` varchar(32) default NULL,
  PRIMARY KEY  (`GatewayCPEPID`)
);

--
-- Dumping data for table `CPDP`
--


INSERT INTO `CPDP` VALUES (1,'192.168.0.1','192.168.0.10','ON',3288,'LA');
INSERT INTO `CPDP` VALUES (1,'192.168.0.1','192.168.0.20','ON',3288,'LB');
INSERT INTO `CPDP` VALUES (1,'192.168.0.1','192.168.0.30','ON',3288,'LC');
