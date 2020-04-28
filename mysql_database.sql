CREATE DATABASE  IF NOT EXISTS `swingtest`;
USE `swingtest`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
-- You can use this with the MySQL Workbench tool and a MySQL database
-- Host: localhost    Database: swingtest
-- ------------------------------------------------------
-- Server version	5.5.22

--
-- Table structure for table `people`
--

DROP TABLE IF EXISTS `people`;
CREATE TABLE `people` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `age` enum('child','adult','senior') NOT NULL,
  `employment_status` varchar(45) NOT NULL,
  `tax_id` varchar(45) DEFAULT NULL,
  `us_citizen` tinyint(1) NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `occupation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `people`
--

LOCK TABLES `people` WRITE;
INSERT INTO `people` VALUES (1,'Joe Smith','adult','employed','777',1,'male','lion tamer'),(2,'Sue','adult','other',NULL,1,'female','artist'),(3,'John','adult','selfEmployed','',0,'male','software');
UNLOCK TABLES;
