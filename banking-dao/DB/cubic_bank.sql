-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cubic_bank
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
-- Table structure for table `account_status`
--

DROP TABLE IF EXISTS `account_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_status`
--

LOCK TABLES `account_status` WRITE;
/*!40000 ALTER TABLE `account_status` DISABLE KEYS */;
INSERT INTO `account_status` VALUES (1,'AS01','PENDING','PENDING'),(2,'AS02','PROCESSING','PROCESSING'),(3,'AS03','DORMANT','DORMANT'),(4,'AS04','APPROVED','APPROVED'),(5,'AS05','ACTIVE','ACTIVE'),(6,'AS06','REGISTERED','REGISTERED');
/*!40000 ALTER TABLE `account_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_type`
--

DROP TABLE IF EXISTS `account_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_type`
--

LOCK TABLES `account_type` WRITE;
/*!40000 ALTER TABLE `account_type` DISABLE KEYS */;
INSERT INTO `account_type` VALUES (1,'AC001','SAVING','SAVING'),(2,'AC002','SAVING','CURRENT'),(3,'AC003','SAVING','CORPORATE'),(4,'AC004','SAVING','CHECKING');
/*!40000 ALTER TABLE `account_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address_tbl`
--

DROP TABLE IF EXISTS `address_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address_tbl` (
  `id` int NOT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mobile` bigint DEFAULT NULL,
  `zipcode` varchar(255) DEFAULT NULL,
  `userid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address_tbl`
--

LOCK TABLES `address_tbl` WRITE;
/*!40000 ALTER TABLE `address_tbl` DISABLE KEYS */;
INSERT INTO `address_tbl` VALUES (64,'123 this st','1','thisCity','country','bmb','bmb',123,'12345','shrumsowen1@gmail.com');
/*!40000 ALTER TABLE `address_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_account_information_tbl`
--

DROP TABLE IF EXISTS `customer_account_information_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_account_information_tbl` (
  `id` bigint NOT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `av_balance` float NOT NULL,
  `branch` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `status_as_of` datetime DEFAULT NULL,
  `tav_balance` float NOT NULL,
  `account_type` int NOT NULL,
  `customer_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2hgpp4qx0tshne8ymled7owj7` (`account_type`),
  KEY `FKoe67kqqpqn5pftc5p4eunmy69` (`customer_id`),
  CONSTRAINT `FK2hgpp4qx0tshne8ymled7owj7` FOREIGN KEY (`account_type`) REFERENCES `account_type` (`id`),
  CONSTRAINT `FKoe67kqqpqn5pftc5p4eunmy69` FOREIGN KEY (`customer_id`) REFERENCES `user_login_tbl` (`loginid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_account_information_tbl`
--

LOCK TABLES `customer_account_information_tbl` WRITE;
/*!40000 ALTER TABLE `customer_account_information_tbl` DISABLE KEYS */;
INSERT INTO `customer_account_information_tbl` VALUES (13,'00-1658993842',1000,'California','$','2020-06-19 14:35:28',1000,1,'gpsprogramys@gmail.com'),(21,'00-1863012206',875,'California','$','2020-06-29 19:29:46',875,1,'shrumsowen1@gmail.com'),(23,'001528354860',1125,'Fremont','$','2020-07-02 17:44:11',1125,1,'shrumsowen11@gmail.com');
/*!40000 ALTER TABLE `customer_account_information_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_payee_funding_tbl`
--

DROP TABLE IF EXISTS `customer_payee_funding_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_payee_funding_tbl` (
  `id` int NOT NULL,
  `amount` float NOT NULL,
  `debit_account_no` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `transaction_date` datetime DEFAULT NULL,
  `payee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg8xptl7rqar2o5c8cvl0p9oyt` (`payee_id`),
  CONSTRAINT `FKg8xptl7rqar2o5c8cvl0p9oyt` FOREIGN KEY (`payee_id`) REFERENCES `payee_informations_tbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_payee_funding_tbl`
--

LOCK TABLES `customer_payee_funding_tbl` WRITE;
/*!40000 ALTER TABLE `customer_payee_funding_tbl` DISABLE KEYS */;
INSERT INTO `customer_payee_funding_tbl` VALUES (66,100,'00-1863012206','rent','2020-08-10 19:42:02',36),(68,50,'001528354860','jpt','2020-08-10 19:50:50',62),(69,25,'00-1863012206','ave','2020-08-10 23:09:15',36);
/*!40000 ALTER TABLE `customer_payee_funding_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_question_answer_tbl`
--

DROP TABLE IF EXISTS `customer_question_answer_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_question_answer_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `doe` datetime DEFAULT NULL,
  `dom` datetime DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `userid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdg3ajx7k8dd9lpesbxhtiih61` (`userid`),
  CONSTRAINT `FKdg3ajx7k8dd9lpesbxhtiih61` FOREIGN KEY (`userid`) REFERENCES `user_login_tbl` (`loginid`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_question_answer_tbl`
--

LOCK TABLES `customer_question_answer_tbl` WRITE;
/*!40000 ALTER TABLE `customer_question_answer_tbl` DISABLE KEYS */;
INSERT INTO `customer_question_answer_tbl` VALUES (9,'BABA','2020-06-13 12:12:55','2020-06-13 12:12:55','What was the name of your first school?','coddybugk@gmail.com'),(10,'r34534','2020-06-13 12:12:56','2020-06-13 12:12:56','What is your mother\'s maiden name?','coddybugk@gmail.com'),(12,'amogh','2020-06-13 12:56:17','2020-06-13 12:56:17','What is your favourite vacation spot?','coddybugk@gmail.com'),(13,'Varanasi','2020-06-13 12:56:17','2020-06-13 12:56:17','What is your birth place?','coddybugk@gmail.com'),(14,'Singh','2020-06-15 12:23:46','2020-06-15 12:23:46','What is your mother\'s maiden name?','gpsprogramys@gmail.com'),(15,'Ghaziabad','2020-06-15 12:23:46','2020-06-15 12:23:46','What is your birth place?','gpsprogramys@gmail.com'),(16,'234324#','2020-06-16 13:12:35','2020-06-16 13:12:35','What is your father\'s middle name?','javahunk100@gmail.com'),(17,'Varanasi','2020-06-16 13:12:35','2020-06-16 13:12:35','What is the name of your first crush?','javahunk100@gmail.com'),(22,'red','2020-06-29 18:02:53','2020-06-29 18:02:53','What is your color?','shrumsowen1@gmail.com'),(23,'VedV','2020-06-29 18:02:53','2020-06-29 18:02:53','What is your favourite author\'s name?','shrumsowen1@gmail.com'),(24,'bmb','2020-07-02 17:43:49','2020-07-02 17:43:49','What is your pet name?','shrumsowen11@gmail.com'),(25,'bmb','2020-07-02 17:43:49','2020-07-02 17:43:49','What is your favourite vacation spot?','shrumsowen11@gmail.com');
/*!40000 ALTER TABLE `customer_question_answer_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_saving_enquiry_approved_tbl`
--

DROP TABLE IF EXISTS `customer_saving_enquiry_approved_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_saving_enquiry_approved_tbl` (
  `csaid` int NOT NULL AUTO_INCREMENT,
  `appref` varchar(30) DEFAULT NULL,
  `doa` datetime DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `acc_type` int NOT NULL,
  `status` int NOT NULL,
  `ucrid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`csaid`),
  KEY `FKk0h66b6nud374jpnghy9guo07` (`acc_type`),
  KEY `FKhyffum0o9nond8rr78oc36q4l` (`status`),
  CONSTRAINT `customer_saving_enquiry_approved_tbl_ibfk_1` FOREIGN KEY (`status`) REFERENCES `account_status` (`id`),
  CONSTRAINT `customer_saving_enquiry_approved_tbl_ibfk_2` FOREIGN KEY (`acc_type`) REFERENCES `account_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_saving_enquiry_approved_tbl`
--

LOCK TABLES `customer_saving_enquiry_approved_tbl` WRITE;
/*!40000 ALTER TABLE `customer_saving_enquiry_approved_tbl` DISABLE KEYS */;
INSERT INTO `customer_saving_enquiry_approved_tbl` VALUES (12,'AS-170100-045V6','2020-06-15 12:17:56','gpsprogramys@gmail.com','California','9873003702','Amit Kumar',1,6,'1592fc3886bf-db1b-40d9-a2c6-db373361bf2a238028431'),(14,'AS-736288-8G1QY','2020-06-29 18:00:49','shrumsowen1@gmail.com','California','123','bvm',1,6,'15935c17f084-dc9a-46cf-8069-09975a26c410468066008'),(15,'AS-363662-HZAB7','2020-07-02 17:42:07','shrumsowen11@gmail.com','Fremont','4694716012','bmb',1,6,'159378a35b5b-0663-445d-8a70-16881695fdc5726157631');
/*!40000 ALTER TABLE `customer_saving_enquiry_approved_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_saving_enquiry_tbl`
--

DROP TABLE IF EXISTS `customer_saving_enquiry_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_saving_enquiry_tbl` (
  `csaid` int NOT NULL AUTO_INCREMENT,
  `appref` varchar(30) DEFAULT NULL,
  `doa` datetime DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `acc_type` int NOT NULL,
  `status` int NOT NULL,
  `ucrid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`csaid`),
  KEY `FKk0h66b6nud374jpnghy9guo07` (`acc_type`),
  KEY `FKhyffum0o9nond8rr78oc36q4l` (`status`),
  CONSTRAINT `FKhyffum0o9nond8rr78oc36q4l` FOREIGN KEY (`status`) REFERENCES `account_status` (`id`),
  CONSTRAINT `FKk0h66b6nud374jpnghy9guo07` FOREIGN KEY (`acc_type`) REFERENCES `account_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_saving_enquiry_tbl`
--

LOCK TABLES `customer_saving_enquiry_tbl` WRITE;
/*!40000 ALTER TABLE `customer_saving_enquiry_tbl` DISABLE KEYS */;
INSERT INTO `customer_saving_enquiry_tbl` VALUES (3,'AS-447758-6NIRP','2020-03-30 18:48:44','synergisticit2020@gmail.com','GHAZIABAD','0938737373','Nagendra Kumar',1,4,'1592e55e9bfc-c814-450b-abbb-b32193a83a69238077296'),(7,'AS-419592-0JBH7','2020-04-06 17:46:08','javahunk100@gmail.com','Fremont','08700134973','javahunk100@gmail.com',2,4,'158562afeb35-0ee1-496b-a5a0-1f7d7a61dd06951804778'),(8,'AS-956553-HJQYL','2020-04-06 19:34:33','javahunk2020@gmail.com','Fremont','08700134973','JavaHunk Technologies',1,6,'1586ff7a0796-8a2d-4b3e-957f-835479ef1170216128890'),(9,'AS-570688-FM3F8','2020-06-09 10:38:19','nagendra.yadav.niit@gmail.com','Fremont','9873003702','Nagendra',1,4,'1591ceb76334-e6fe-421f-89c4-2b35070c6222713643786'),(10,'AS-275986-JUBMG','2020-06-11 10:12:57','coddybugk@gmail.com','California','9227272772','Omega',1,4,'1591ea35d84a-3478-4cbc-94d5-86a2178445f4885148873');
/*!40000 ALTER TABLE `customer_saving_enquiry_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_security_questions_tbl`
--

DROP TABLE IF EXISTS `customer_security_questions_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_security_questions_tbl` (
  `qid` int NOT NULL,
  `createdate` datetime DEFAULT NULL,
  `owner` varchar(100) DEFAULT NULL,
  `questions` varchar(255) DEFAULT NULL,
  `status` varchar(3) DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  PRIMARY KEY (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_security_questions_tbl`
--

LOCK TABLES `customer_security_questions_tbl` WRITE;
/*!40000 ALTER TABLE `customer_security_questions_tbl` DISABLE KEYS */;
INSERT INTO `customer_security_questions_tbl` VALUES (1,'2020-06-15 13:03:05','admin100','What is your birth place?','yes','2020-06-15 13:03:05'),(2,'2020-06-15 13:03:05','admin100','What is your mother\'s maiden name?','no','2020-06-15 13:03:05'),(3,'2020-06-15 13:03:05','admin100','What is your favourite author\'s name?','yes','2020-06-15 13:03:05'),(4,'2020-06-15 13:03:05','admin100','What is your pet name?','no','2020-06-15 13:03:05'),(5,'2020-06-15 13:03:05','admin100','What is your favourite soccer team?','no','2020-06-15 13:03:05'),(6,'2020-06-15 13:03:05','admin100','What is the name of your childhood hero?','no','2020-06-15 13:03:05'),(7,'2020-06-15 13:03:05','admin100','What is your father\'s middle name?','no','2020-06-15 13:03:05'),(8,'2020-06-15 13:03:05','admin100','What is the name of your first crush?','yes','2020-06-15 13:03:05'),(9,'2020-06-15 13:03:05','admin100','What was the name of your first school?','yes','2020-06-15 13:03:05'),(10,'2020-06-15 13:03:05','admin100','What is your favourite vacation spot?','no','2020-06-15 13:03:05'),(11,'2020-06-16 13:02:33','coddybugk@gmail.com','What is your color?','yes','2020-06-16 13:02:33'),(12,'2020-06-16 13:02:59','coddybugk@gmail.com','What is your last degree?','no','2020-06-16 13:02:59');
/*!40000 ALTER TABLE `customer_security_questions_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_tbl`
--

DROP TABLE IF EXISTS `customer_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int NOT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `doe` datetime DEFAULT NULL,
  `dom` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `father` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `image` longblob,
  `job_title` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photo_name` varchar(255) DEFAULT NULL,
  `qualification` varchar(255) DEFAULT NULL,
  `ssn` varchar(255) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlji6xcqvy9ayc80syoj41pr8t` (`userid`),
  CONSTRAINT `FKlji6xcqvy9ayc80syoj41pr8t` FOREIGN KEY (`userid`) REFERENCES `user_login_tbl` (`loginid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_tbl`
--

LOCK TABLES `customer_tbl` WRITE;
/*!40000 ALTER TABLE `customer_tbl` DISABLE KEYS */;
INSERT INTO `customer_tbl` VALUES (2,'Fremont',0,'2016-10-03',NULL,NULL,'javahunk100@gmail.com','NA','Male',NULL,'CONSULTANT','08700134973','javahunk100@gmail.com',NULL,'B.TECH','92982828','javahunk100@gmail.com'),(3,'Fremont',0,'12-03-2020','2020-04-06 17:51:40','2020-04-06 17:51:40','javatech1000@gmail.com','Mr. Jack','Male',NULL,'Bank Employee','320432043','James Robert',NULL,'NA','23432','javatech1000@gmail.com'),(4,'Fremont',0,'03-03-2019',NULL,NULL,'javahunk2020@gmail.com','Mr .JK','Male',NULL,'CASHIER','08700134973','JavaHunk Technologies',NULL,'B.TECH','42324234','javahunk2020@gmail.com'),(5,'California',0,'03-03-2019',NULL,NULL,'coddybugk@gmail.com','Mr .JK','Male',NULL,'ACCOUNTANT','9227272772','Omega',NULL,'B.TECH','938938','coddybugk@gmail.com'),(6,'California',0,'03-03-2019',NULL,NULL,'gpsprogramys@gmail.com','Mr .JK','Male',NULL,'ACCOUNTANT','9873003702','Amit Kumar',NULL,'M.TECH','93939393','gpsprogramys@gmail.com'),(10,'California',0,'09/06/2000',NULL,NULL,'shrumsowen1@gmail.com','fathername','Male',NULL,'Picker','123','bhask',NULL,'B.TECH','1','shrumsowen1@gmail.com'),(11,'Fremont',0,'09/05/1994',NULL,NULL,'shrumsowen11@gmail.com','fathername','Male',NULL,'Picker','4694716012','bmb',NULL,'B.TECH','34544','shrumsowen11@gmail.com');
/*!40000 ALTER TABLE `customer_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (70);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payee_informations_tbl`
--

DROP TABLE IF EXISTS `payee_informations_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payee_informations_tbl` (
  `id` int NOT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  `doe` datetime DEFAULT NULL,
  `dom` datetime DEFAULT NULL,
  `payee_account_no` varchar(30) DEFAULT NULL,
  `payee_name` varchar(100) DEFAULT NULL,
  `payee_nick_name` varchar(100) DEFAULT NULL,
  `remarks` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `urn` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payee_informations_tbl`
--

LOCK TABLES `payee_informations_tbl` WRITE;
/*!40000 ALTER TABLE `payee_informations_tbl` DISABLE KEYS */;
INSERT INTO `payee_informations_tbl` VALUES (1,'DummyHardCoded@gmail.com',NULL,NULL,'111','dummyHardCoded','dummyHardCoded','a',NULL,1),(36,'shrumsowen1@gmail.com','2020-07-07 11:59:37','2020-08-13 13:20:29','001528354860','RealBMB','RealBMB','edit RealBMB',NULL,0),(52,'shrumsowen1@gmail.com','2020-07-07 11:40:37','2020-08-13 14:43:49','00-1658993842','Krishna','krishh','edit krish',NULL,0),(62,'shrumsowen11@gmail.com','2020-07-17 19:35:53','2020-07-17 19:35:53','00-1863012206','shrums','shrums','add shrums',NULL,0);
/*!40000 ALTER TABLE `payee_informations_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons_tbl`
--

DROP TABLE IF EXISTS `persons_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persons_tbl` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(100) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `mobile` bigint DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `ssn` int DEFAULT NULL,
  `createdate` timestamp NULL DEFAULT NULL,
  `updatedate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`pid`,`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons_tbl`
--

LOCK TABLES `persons_tbl` WRITE;
/*!40000 ALTER TABLE `persons_tbl` DISABLE KEYS */;
INSERT INTO `persons_tbl` VALUES (1,'javahunk','Nagendra','javahunk100@gmail.com','2020-04-20',9873003702,33433,34535,'2020-04-20 13:40:26','2020-04-20 13:40:26');
/*!40000 ALTER TABLE `persons_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `raw_report`
--

DROP TABLE IF EXISTS `raw_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `raw_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `DATE` varchar(100) DEFAULT NULL,
  `IMPRESSIONS` varchar(200) DEFAULT NULL,
  `CLICKS` varchar(200) DEFAULT NULL,
  `EARNING` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raw_report`
--

LOCK TABLES `raw_report` WRITE;
/*!40000 ALTER TABLE `raw_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `raw_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_status`
--

DROP TABLE IF EXISTS `request_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_status`
--

LOCK TABLES `request_status` WRITE;
/*!40000 ALTER TABLE `request_status` DISABLE KEYS */;
INSERT INTO `request_status` VALUES (1,'RS001','COMPLETE','COMPLETE'),(2,'RS002','SEND/PENDING','SEND/PENDING'),(3,'RS003','NEW_STATUS','NEW_STATUS');
/*!40000 ALTER TABLE `request_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_tbl`
--

DROP TABLE IF EXISTS `request_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request_tbl` (
  `id` int NOT NULL,
  `customer_account_no` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `request_date` datetime DEFAULT NULL,
  `request_status` int NOT NULL,
  `request_type` int NOT NULL,
  `user_address` int NOT NULL,
  `userid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7ie7q0l1rp0vdcdl0m6v9cwp1` (`request_status`),
  KEY `FK2ctddh9snxanc6l4bxwae5lxy` (`request_type`),
  KEY `FKsajg41eu6g2gihsjnlgb55m5n` (`user_address`),
  CONSTRAINT `FK2ctddh9snxanc6l4bxwae5lxy` FOREIGN KEY (`request_type`) REFERENCES `request_type` (`id`),
  CONSTRAINT `FK7ie7q0l1rp0vdcdl0m6v9cwp1` FOREIGN KEY (`request_status`) REFERENCES `request_status` (`id`),
  CONSTRAINT `FKsajg41eu6g2gihsjnlgb55m5n` FOREIGN KEY (`user_address`) REFERENCES `address_tbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_tbl`
--

LOCK TABLES `request_tbl` WRITE;
/*!40000 ALTER TABLE `request_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `request_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_type`
--

DROP TABLE IF EXISTS `request_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_type`
--

LOCK TABLES `request_type` WRITE;
/*!40000 ALTER TABLE `request_type` DISABLE KEYS */;
INSERT INTO `request_type` VALUES (1,'RT001','CheckBook','CheckBook'),(2,'RT002','AddressChange','AddressChange'),(3,'RT003','PhoneNumber','PhoneNumber'),(4,'RT004','Misc.','Misc.');
/*!40000 ALTER TABLE `request_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_tbl`
--

DROP TABLE IF EXISTS `roles_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_tbl` (
  `rid` int NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_tbl`
--

LOCK TABLES `roles_tbl` WRITE;
/*!40000 ALTER TABLE `roles_tbl` DISABLE KEYS */;
INSERT INTO `roles_tbl` VALUES (1,'ADMIN','ADMIN'),(2,'EMPLOYEE','EMPLOYEE'),(3,'CUSTOMER','CUSTOMER'),(4,'MANAGER','MANAGER');
/*!40000 ALTER TABLE `roles_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_login_tbl`
--

DROP TABLE IF EXISTS `user_login_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_login_tbl` (
  `loginid` varchar(255) NOT NULL,
  `llt` datetime DEFAULT NULL,
  `locked` varchar(255) DEFAULT NULL,
  `lwap` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `no_of_attempt` int DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_expire` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`loginid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login_tbl`
--

LOCK TABLES `user_login_tbl` WRITE;
/*!40000 ALTER TABLE `user_login_tbl` DISABLE KEYS */;
INSERT INTO `user_login_tbl` VALUES ('coddybugk@gmail.com',NULL,'no',NULL,'Omega',3,'$2a$10$2WQKucsQIGZjzOd1fzKtY.JACM.Q6avII.Vt0UknA08DcYCjHqxpO',NULL,NULL),('gpsprogramys@gmail.com','2020-06-15 12:24:22','no',NULL,'Amit Kumar',3,'$2a$10$zn.V63aim4LterEwZvSViuKi2DObtMz7L5Izt3wF.bFlKTUJPFGry',NULL,NULL),('javahunk100@gmail.com','2020-06-16 13:12:50','no',NULL,'javahunk100@gmail.com',3,'$2a$10$oIQ3aqUcO/zEQD4nrT0IkeJSyII5/xs22pL6Z0/lI0gEY3n4IMwp2',NULL,NULL),('javahunk2020@gmail.com',NULL,'no',NULL,'JavaHunk Technologies',3,'$2a$10$2WQKucsQIGZjzOd1fzKtY.JACM.Q6avII.Vt0UknA08DcYCjHqxpO',NULL,NULL),('javatech1000@gmail.com',NULL,'no',NULL,'James Robert',3,'$2a$10$2WQKucsQIGZjzOd1fzKtY.JACM.Q6avII.Vt0UknA08DcYCjHqxpO',NULL,'2230303'),('mockcj@gmaill.com','2020-04-20 09:34:34','no','2020-04-20 09:34:09','Nagendra',2,'wer','2020-04-20 09:35:10',NULL),('shrumsowen11@gmail.com','2020-07-02 17:43:55','no',NULL,'BMB',3,'$2a$10$VwsJss6X.s2BzGodpIeZ4eTROTeNJn7GbeNx1AO5NisQWGqDLlhaO',NULL,NULL),('shrumsowen1@gmail.com','2020-07-06 23:32:12','no',NULL,'shrums',3,'$2a$10$IPSF71t9KeGhVNfrdsRAzecoda45jhXsna0cADcS8hRhFetMyraYG',NULL,NULL);
/*!40000 ALTER TABLE `user_login_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `rid` int NOT NULL,
  `loginid` varchar(255) NOT NULL,
  PRIMARY KEY (`loginid`,`rid`),
  KEY `FKl9iid9occsp431s68de3q056e` (`rid`),
  CONSTRAINT `FKdggj6erbbsld9rktydn2c78wv` FOREIGN KEY (`loginid`) REFERENCES `user_login_tbl` (`loginid`),
  CONSTRAINT `FKl9iid9occsp431s68de3q056e` FOREIGN KEY (`rid`) REFERENCES `roles_tbl` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'coddybugk@gmail.com'),(1,'gpsprogramys@gmail.com'),(2,'javatech1000@gmail.com'),(3,'javahunk100@gmail.com'),(3,'javahunk2020@gmail.com'),(3,'shrumsowen11@gmail.com'),(3,'shrumsowen1@gmail.com');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-26 18:47:57
