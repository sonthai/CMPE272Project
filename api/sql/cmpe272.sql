-- MySQL dump 10.13  Distrib 5.7.16, for Linux (x86_64)
--
-- Host: localhost    Database: cmpe272
-- ------------------------------------------------------
-- Server version	5.7.16-0ubuntu0.16.04.1

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
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job` (
  `detailUrl` varchar(150) NOT NULL,
  `joborder_id` int(11) NOT NULL AUTO_INCREMENT,
  `jobTitle` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `skills` varchar(100) NOT NULL,
  `areacode` varchar(10) NOT NULL,
  `state` varchar(10) NOT NULL,
  `city` varchar(15) NOT NULL,
  `recname` varchar(40) NOT NULL,
  `recemail` varchar(50) NOT NULL,
  `openningCnt` int(5) NOT NULL,
  `detail` blob NOT NULL,
  `companyID` varchar(30) NOT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `isDeleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`joborder_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES ('http://google.com',1,'Software Engineer','2016-12-06','java','98987','CA','San Francisco','ABC','abc@gmail.com',4,'great skill','Non label',1,0),('http://google.com',2,'Software Engineer II','2016-12-06','c++, java, perl','98987','CA','San Francisco','CDF','abc@yahoo.com',4,'great skill','Non label 1',1,0),('http://google.com',3,'Software Engineer II','2016-12-06','c++, java, perl','98987','CA','San Francisco','CDF','abc@yahoo.com',4,'great skill','Non label 1',1,0),('http://google.com',4,'Software Engineer III','2016-12-06','c++, java, perl','98987','CA','San Francisco','CDF','abc@yahoo.com',4,'great skill','Non label 1',1,0);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_applied`
--

DROP TABLE IF EXISTS `job_applied`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_applied` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `companyID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `userName` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `jobTitle` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `date` date NOT NULL,
  `location` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `detailUrl` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`,`companyID`,`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_applied`
--

LOCK TABLES `job_applied` WRITE;
/*!40000 ALTER TABLE `job_applied` DISABLE KEYS */;
INSERT INTO `job_applied` VALUES (1,'Google','sdthai','Software Engineer','2016-12-06','Mountin View','http://google.com'),(2,'Netapp','sdthai','Software Engineer','2016-12-06','Mountin View','http://netapp.com');
/*!40000 ALTER TABLE `job_applied` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruiter_profile`
--

DROP TABLE IF EXISTS `recruiter_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recruiter_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `companyInfo` blob NOT NULL,
  `note` blob NOT NULL,
  `email` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `userName` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`,`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recruiter_profile`
--

LOCK TABLES `recruiter_profile` WRITE;
/*!40000 ALTER TABLE `recruiter_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `recruiter_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) CHARACTER SET utf8 NOT NULL,
  `userName` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phoneNo` varchar(20) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`id`,`firstName`,`lastName`,`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Son','Thai','sdthai','81dc9bdb52d04dc2036dbd8313ed055','7654689876',1),(2,'David','Lion','david','81dc9bdb52d04dc2036dbd8313ed055','7654689876',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `skills` blob,
  `work_experience` blob,
  `education` blob,
  `userName` varchar(20) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (3,'java, c++','10 years of software development','Calpoly SLO','sdthai',NULL),(4,'java, c++, perl','5 years of software development','Calpoly SLO','david',NULL);
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-06 21:43:40
