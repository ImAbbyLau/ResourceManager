-- MySQL dump 10.13
--
-- Host: localhost    Database: test_BB
---------------------------------------------------------
-- Server version	5.1.56

--
-- Table structure for table 'Allow'
--


IF EXISTS Allow DROP TABLE;
CREATE TABLE Allow (
  SLA int(11) NOT NULL default '0',
  Domain   varchar(15) NOT NULL default '',
  PRIMARY KEY  (SLA,Domain)
) TYPE=MyISAM;

--
-- Dumping data for table 'Allow'
--


INSERT INTO Allow VALUES (1,'127.0.1.0');
INSERT INTO Allow VALUES (1,'127.0.2.0');
INSERT INTO Allow VALUES (1,'127.0.3.0');
INSERT INTO Allow VALUES (1,'129.94.231.0');
INSERT INTO Allow VALUES (1,'129.94.232.0');
INSERT INTO Allow VALUES (1,'67.167.1.0');
INSERT INTO Allow VALUES (5,'127.0.1.0');
INSERT INTO Allow VALUES (5,'127.0.2.0');
INSERT INTO Allow VALUES (5,'127.0.3.0');
INSERT INTO Allow VALUES (5,'129.94.231.0');
INSERT INTO Allow VALUES (5,'129.94.232.0');

--
-- Table structure for table 'BBPeer'
--

DROP TABLE IF EXISTS BBPeer;
CREATE TABLE BBPeer (
  BB_ID int(11) NOT NULL auto_increment,
  Address varchar(15) default NULL,
  Domain varchar(15) default NULL,
  status varchar(10) default NULL,
  port int(11) default NULL,
  pdpPort int(11) default NULL,
  GatewayPEPID varchar(32) default NULL,
  PRIMARY KEY  (BB_ID)
) TYPE=MyISAM;

--
-- Dumping data for table 'BBPeer'
--


INSERT INTO BBPeer VALUES (1,'127.0.0.1','127.0.1.0','OFF',4444,3288,'1A');
INSERT INTO BBPeer VALUES (3,'127.0.0.1','192.2.0.0','OFF',4446,3388,'3A');
INSERT INTO BBPeer VALUES (2,'127.0.0.1','67.167.1.0','OFF',4445,3188,'2A');
INSERT INTO BBPeer VALUES (4,'127.0.0.1','127.0.4.0','OFF',4000,3388,'4A');
INSERT INTO BBPeer VALUES (5,'127.0.0.1','127.0.2.0','OFF',5444,3289,'5A');
INSERT INTO BBPeer VALUES (6,'127.0.0.1','127.0.3.0','OFF',6444,3290,'6A');
INSERT INTO BBPeer VALUES (7,'127.0.0.1','127.0.5.0','OFF',4555,3388,'7A');
INSERT INTO BBPeer VALUES (8,'127.0.0.1','127.8.0.0','OFF',55555,3388,'8A');
INSERT INTO BBPeer VALUES (9,'129.94.232.27','129.94.231.0','ON',7777,3298,'9A');
INSERT INTO BBPeer VALUES (10,'129.94.232.110','129.94.232.0','ON',7777,3288,'10A');

--
-- Table structure for table 'Capacity'
--

DROP TABLE IF EXISTS Capacity;
CREATE TABLE Capacity (
  Domain varchar(15) NOT NULL default '',
  Capacity int(11) default NULL,
  availCapacity int(11) default NULL,
  PRIMARY KEY  (Domain)
) TYPE=MyISAM;

--
-- Dumping data for table 'Capacity'
--


INSERT INTO Capacity VALUES ('127.0.1.0',10000,8200);
INSERT INTO Capacity VALUES ('129.94.232.0',10000,8950);
INSERT INTO Capacity VALUES ('129.94.0.0',100000,100000);
INSERT INTO Capacity VALUES ('129.94.231.0',6000,4900);
INSERT INTO Capacity VALUES ('127.0.2.0',10000,9100);
INSERT INTO Capacity VALUES ('127.0.3.0',10000,10000);
INSERT INTO Capacity VALUES ('67.167.1.0',5000,5000);

--
-- Table structure for table 'Domains'
--

DROP TABLE IF EXISTS Domains;
CREATE TABLE Domains (
  Domain varchar(15) NOT NULL default '',
  Neighbour varchar(15) NOT NULL default '',
  PRIMARY KEY  (Domain,Neighbour)
) TYPE=MyISAM;

--
-- Dumping data for table 'Domains'
--


INSERT INTO Domains VALUES ('127.0.1.0','127.0.2.0');
INSERT INTO Domains VALUES ('127.0.1.0','129.94.231.0');
INSERT INTO Domains VALUES ('127.0.2.0','127.0.1.0');
INSERT INTO Domains VALUES ('127.0.2.0','127.0.3.0');
INSERT INTO Domains VALUES ('127.0.3.0','127.0.1.0');
INSERT INTO Domains VALUES ('127.0.3.0','127.0.2.0');
INSERT INTO Domains VALUES ('127.0.3.0','129.94.232.0');
INSERT INTO Domains VALUES ('129.94.231.0','127.0.1.0');
INSERT INTO Domains VALUES ('129.94.231.0','129.94.232.0');
INSERT INTO Domains VALUES ('129.94.232.0','127.3.0.0');
INSERT INTO Domains VALUES ('129.94.232.0','129.94.231.0');
INSERT INTO Domains VALUES ('127.0.1.0','67.167.1.0');
INSERT INTO Domains VALUES ('67.167.1.0','127.0.1.0');

--
-- Table structure for table 'Flows'
--

DROP TABLE IF EXISTS Flows;
CREATE TABLE Flows (
  RAR int(11) NOT NULL default '0',
  Domain varchar(15) NOT NULL default '',
  bandwidth int(11) default NULL,
  starttime time default NULL,
  startdate date default NULL,
  endtime time default NULL,
  enddate date default NULL,
  PRIMARY KEY  (RAR,Domain)
) TYPE=MyISAM;

--
-- Dumping data for table 'Flows'
--


INSERT INTO Flows VALUES (60,'127.0.1.0',100,'11:11:11','2003-04-11','11:11:11','00:20:03');
INSERT INTO Flows VALUES (59,'127.0.1.0',100,'11:11:11','2003-04-10','11:11:11','00:20:03');
INSERT INTO Flows VALUES (57,'127.0.2.0',300,'12:12:12','2003-04-10','10:10:10','00:20:03');
INSERT INTO Flows VALUES (57,'127.0.1.0',300,'12:12:12','2003-04-10','10:10:10','00:20:03');
INSERT INTO Flows VALUES (57,'129.94.231.0',300,'12:12:12','2003-04-10','10:10:10','00:20:03');

--
-- Table structure for table 'PEP'
--

DROP TABLE IF EXISTS PEP;
CREATE TABLE PEP (
  pepID varchar(32) NOT NULL default '',
  sindex int(11) NOT NULL,
  Domain varchar(15) default NULL,
  Address varchar(15) default NULL,
  Neighbour varchar(15) default NULL,
  status varchar(5) default NULL,
  PRIMARY KEY  (pepID,sindex)
) TYPE=MyISAM;

--
-- Dumping data for table 'PEP'
--


INSERT INTO PEP VALUES ('1A',1,'127.0.1.0','127.0.0.1','127.0.2.0','OFF');
INSERT INTO PEP VALUES ('1B',0,'127.0.1.0','127.0.0.1','129.94.231.0','OFF');
INSERT INTO PEP VALUES ('5A',2,'127.0.2.0','127.0.0.1','127.0.1.0','OFF');
INSERT INTO PEP VALUES ('5B',5,'127.0.2.0','127.0.0.1','127.0.3.0','OFF');
INSERT INTO PEP VALUES ('9A',3,'129.94.231.0','129.94.232.27','127.0.1.0','OFF');
INSERT INTO PEP VALUES ('10A',4,'129.94.232.0','129.94.232.110','129.94.231.0','OFF');
INSERT INTO PEP VALUES ('9B',6,'129.94.231.0','129.94.232.27','129.94.232.0','OFF');
INSERT INTO PEP VALUES ('6A',7,'127.0.3.0','127.0.0.1','127.0.2.0','OFF');
INSERT INTO PEP VALUES ('2A',8,'67.167.1.0','127.0.0.1','127.0.1.','OFF');

--
-- Table structure for table 'RAR'
--

DROP TABLE IF EXISTS RAR;
CREATE TABLE RAR (
  rar_id int(11) NOT NULL auto_increment,
  startdate date default NULL,
  starttime time default NULL,
  enddate date default NULL,
  endtime time default NULL,
  givenBW int(11) default NULL,
  source varchar(15) default NULL,
  destination varchar(15) default NULL,
  sla_id int(11) default NULL,
  PRIMARY KEY  (rar_id)
) TYPE=MyISAM;

--
-- Dumping data for table 'RAR'
--


INSERT INTO RAR VALUES (30,'2005-10-10','11:11:11','2003-10-10','11:11:11',500,'129.94.232.4','129.94.232.10',5);
INSERT INTO RAR VALUES (33,'2003-04-10','11:11:11','2003-06-10','10:10:10',100,'129.94.231.22','129.94.231.33',5);
INSERT INTO RAR VALUES (48,'2003-04-11','11:11:11','2003-06-03','22:22:22',150,'129.94.231.123','129.94.232.110',5);
INSERT INTO RAR VALUES (41,'2003-05-11','11:11:11','2003-06-03','11:11:11',100,'129.94.231.100','129.94.232.101',5);
INSERT INTO RAR VALUES (58,'2003-05-01','10:00:00','2003-06-05','20:00:00',100,'127.0.2.33','129.94.231.100',5);
INSERT INTO RAR VALUES (57,'2003-04-10','12:12:12','2003-06-04','10:10:10',300,'129.94.231.22','127.0.2.33',5);

--
-- Table structure for table 'SLA'
--

DROP TABLE IF EXISTS SLA;
CREATE TABLE SLA (
  sla_id int(11) NOT NULL default '0',
  service_type varchar(10) default NULL,
  startdate date default NULL,
  starttime time default NULL,
  enddate date default NULL,
  endtime time default NULL,
  rate int(11) default NULL,
  availBW int(11) default NULL,
  PRIMARY KEY  (sla_id)
) TYPE=MyISAM;

--
-- Dumping data for table 'SLA'
--


INSERT INTO SLA VALUES (1,'null','0000-00-00','00:00:00','0000-00-00','00:00:00',NULL,NULL);
INSERT INTO SLA VALUES (2,'EF','2003-04-10','00:00:01','2003-12-31','23:59:59',20000,20000);
INSERT INTO SLA VALUES (5,'BE','2003-01-11','11:11:11','2003-10-10','00:00:01',10000,9250);
INSERT INTO SLA VALUES (99,'EF','2003-01-01','00:00:01','2004-12-20','11:11:11',1000000,1000000);

--
-- Table structure for table 'codepoint'
--

DROP TABLE IF EXISTS codepoint;
CREATE TABLE codepoint (
  service_type varchar(10) default NULL,
  dscp varchar(6) default NULL
) TYPE=MyISAM;

--
-- Dumping data for table 'codepoint'
--


INSERT INTO codepoint VALUES ('EF','101110');
INSERT INTO codepoint VALUES ('BE','000000');

--
-- Table structure for table 'passwords'
--

DROP TABLE IF EXISTS passwords;
CREATE TABLE passwords (
  sla_id int(11) default NULL,
  password varchar(32) default NULL
) TYPE=MyISAM;

--
-- Dumping data for table 'passwords'
--


INSERT INTO passwords VALUES (1,'sla001');
INSERT INTO passwords VALUES (0,'BBPeer');
INSERT INTO passwords VALUES (2,'sla002');
INSERT INTO passwords VALUES (5,'sla005');
INSERT INTO passwords VALUES (99,'sla099');

