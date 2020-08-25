CREATE DATABASE  IF NOT EXISTS `bugtracker_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bugtracker_db`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: bugtracker_db
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `assignment`
--

DROP TABLE IF EXISTS `assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignment` (
  `issue_id` char(37) NOT NULL,
  `dev_id` int unsigned NOT NULL,
  `creator` int unsigned NOT NULL,
  `deadline` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`issue_id`),
  KEY `FK_ASSIGNMENT_USER_idx` (`dev_id`,`creator`),
  KEY `FK_idx` (`creator`) /*!80000 INVISIBLE */,
  CONSTRAINT `FK_ASSIGNMENT_ISSUE` FOREIGN KEY (`issue_id`) REFERENCES `issue` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ASSIGNMENT_USER` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ASSIGNMENT_USER_DEV` FOREIGN KEY (`dev_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment`
--

LOCK TABLES `assignment` WRITE;
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
INSERT INTO `assignment` VALUES ('BU001',10000,10005,'2020-08-20 10:18:02','issue 01'),('BU002',10001,10010,'2020-08-20 17:11:02','issue 01'),('BU003',10001,10009,'2020-08-20 11:00:02','issue 02'),('BU004',10003,10002,'2020-08-20 14:01:02','issue 03'),('BU005',10004,10005,'2020-08-20 09:07:02','issue 05'),('BU006',10004,10010,'2020-08-20 11:15:02','issue 04'),('BU009',10005,10000,'2020-08-31 18:07:00','quick fix');
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issue`
--

DROP TABLE IF EXISTS `issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `issue` (
  `id` char(37) NOT NULL,
  `project_id` int unsigned NOT NULL,
  `title` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ' ',
  `description` text,
  `creator` int unsigned NOT NULL,
  `date_created` datetime NOT NULL,
  `label` int unsigned DEFAULT NULL,
  `status` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_ISSUE_PROJECT_idx` (`project_id`),
  KEY `FK_ISSUE_LABEL_idx` (`label`),
  CONSTRAINT `FK_ISSUE_LABEL` FOREIGN KEY (`label`) REFERENCES `label` (`id`),
  CONSTRAINT `FK_ISSUE_PROJECT` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

LOCK TABLES `issue` WRITE;
/*!40000 ALTER TABLE `issue` DISABLE KEYS */;
INSERT INTO `issue` VALUES ('BU001',1,'project bug 1','bat loi project 01',10005,'2020-08-20 10:18:02',1,1),('BU002',3,'project app 1','loi thuc thi',10008,'2020-08-20 06:15:02',2,0),('BU003',2,'project web 1','loi tien trinh',10005,'2020-08-20 08:11:02',3,0),('BU004',5,'project bug 2','loi khoi tao moi',10006,'2020-08-20 12:11:02',4,1),('BU005',5,'project app 4','loi ky tu',10007,'2020-08-20 08:11:02',5,1),('BU006',6,'project web 5','loi cap nhat',10001,'2020-08-20 08:11:02',6,0),('BU007',4,'project bug 1','bat loi project 03',10001,'2020-08-20 08:11:02',7,1),('BU008',7,'project devsquad 6','bat loi project dev',10004,'2020-08-20 08:11:02',8,1),('BU009',8,'project app 3','loi truy xuat dong thoi',10000,'2020-08-22 20:07:37',NULL,1),('BU010',10,'project web 5','loi deadlock',10003,'2020-08-20 08:11:02',10,1),('f8c7db62-959c-4912-8a0e-cf95a88afca0',8,'test issue','problem in testing',10000,'2020-08-23 23:26:18',11,0);
/*!40000 ALTER TABLE `issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `label`
--

DROP TABLE IF EXISTS `label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `label` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int unsigned NOT NULL,
  `label_name` char(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_LABEL_PROJECT_idx` (`project_id`),
  CONSTRAINT `FK_LABEL_PROJECT` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `label`
--

LOCK TABLES `label` WRITE;
/*!40000 ALTER TABLE `label` DISABLE KEYS */;
INSERT INTO `label` VALUES (1,1,'label 01'),(2,2,'label 02'),(3,3,'label 03'),(4,4,'label 04'),(5,5,'label 05'),(6,6,'label 06'),(7,7,'label 07'),(8,8,'label 08'),(9,9,'label 09'),(10,10,'label 10'),(11,8,'Interface');
/*!40000 ALTER TABLE `label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` longtext NOT NULL,
  `manager` int unsigned NOT NULL,
  `public_mode` tinyint(1) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PROJECT_USER_idx` (`manager`),
  CONSTRAINT `FK_PROJECT_USER` FOREIGN KEY (`manager`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'project 01','project loi truy xuat dong thoi',10002,0,'2020-08-20 10:18:02'),(2,'project 02','project loi deadlock',10004,1,'2020-08-20 08:11:02'),(3,'project 03','project truy xuat',10003,1,'2020-08-20 10:18:02'),(4,'project 04','project loi truy xuat dong thoi',10002,0,'2020-08-20 10:18:02'),(5,'project 05','project debug qua han',10005,0,'2020-08-20 10:18:02'),(6,'project 06','project loi cap nhat',10004,0,'2020-08-20 08:11:02'),(7,'project 07','project loi dang ky google',10001,1,'2020-08-20 08:11:02'),(8,'Project Avenger','To gather the greatest \"Helo world\" programs the world has ever seen',10000,0,'2020-08-20 10:18:02'),(9,'project 09','project loi danh gia',10002,1,'2020-08-20 10:18:02'),(10,'project 10','project loi truy xuat dong thoi',10009,0,'2020-08-20 08:11:02'),(13,'test project','',10000,0,'2020-08-23 20:39:12');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_team`
--

DROP TABLE IF EXISTS `project_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_team` (
  `project_id` int unsigned NOT NULL,
  `user_id` int unsigned NOT NULL,
  `assign_right` tinyint(1) NOT NULL DEFAULT '1',
  `invite_right` tinyint(1) NOT NULL DEFAULT '1',
  `date_join` datetime NOT NULL,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `FK_TEAM_USER_idx` (`user_id`),
  CONSTRAINT `FK_TEAM_PROJECT` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_TEAM_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_team`
--

LOCK TABLES `project_team` WRITE;
/*!40000 ALTER TABLE `project_team` DISABLE KEYS */;
INSERT INTO `project_team` VALUES (1,10001,1,0,'2020-08-20 08:11:02'),(2,10002,0,1,'2020-08-19 10:01:03'),(3,10004,0,1,'2020-07-21 09:17:01'),(4,10003,1,1,'2020-06-19 18:18:04'),(5,10005,1,0,'2020-11-20 12:15:02'),(6,10001,0,0,'2020-08-20 08:11:02'),(7,10006,0,0,'2020-08-19 10:01:03'),(8,10000,1,1,'2020-07-27 07:30:00'),(8,10005,1,0,'2020-08-22 13:37:39'),(8,10007,0,0,'2020-06-19 18:18:04'),(9,10004,1,0,'2020-07-21 09:17:01'),(10,10005,1,1,'2020-11-20 12:15:02'),(13,10000,1,1,'2020-08-23 20:39:12');
/*!40000 ALTER TABLE `project_team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` char(15) NOT NULL,
  `password` char(30) NOT NULL,
  `email` varchar(45) NOT NULL,
  `user_type` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10012 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (10000,'user1','manager','johnny@gmail.com',0),(10001,'phamgiavu','user','phamgiavu@gmail.com',1),(10002,'lytieulong','user','lytieulong@gmail.com',1),(10003,'nguyenthilan','user','nguyenthilan@gmail.com',1),(10004,'shmily','user','shmily@gmail.com',0),(10005,'jenny','user','jenny@gmail.com',0),(10006,'Kitthy','user','Kitthy@gmail.com',1),(10007,'tommy','user','tommy@gmail.com',1),(10008,'buttdy','user','buttdy@gmail.com',0),(10009,'peter','user','peter@gmail.com',1),(10010,'oggy','user','oggy@gmail.com',0),(10011,'johnny','123456','johnnyshi@gmail.com',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'bugtracker_db'
--
/*!50003 DROP PROCEDURE IF EXISTS `SP_GetUserProjects` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_GetUserProjects`( in USER_ID int)
BEGIN
	SELECT project.* 
    FROM (project join project_team on project.id = project_team.project_id) join user on project_team.user_id = user.id
    WHERE user.id = USER_ID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-25  9:48:45
