-- MySQL dump 10.13  Distrib 8.0.23, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: prova_db
-- ------------------------------------------------------
-- Server version	8.0.23-0ubuntu0.20.04.1

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
-- Table structure for table `asta_aperta`
--

DROP TABLE IF EXISTS `asta_aperta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asta_aperta` (
  `asta_id` int NOT NULL AUTO_INCREMENT,
  `id_utente` int NOT NULL DEFAULT '999',
  `nome` varchar(45) DEFAULT NULL,
  `descrizione` varchar(200) DEFAULT NULL,
  `immagine` varchar(45) DEFAULT NULL,
  `prezzo_iniziale` float DEFAULT NULL,
  `minimo_rialzo` float DEFAULT NULL,
  `dataApertura` varchar(45) DEFAULT NULL,
  `scadenza` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`asta_id`),
  KEY `id_utente_idx` (`id_utente`),
  CONSTRAINT `id_utente2` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`) ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asta_aperta`
--

LOCK TABLES `asta_aperta` WRITE;
/*!40000 ALTER TABLE `asta_aperta` DISABLE KEYS */;
INSERT INTO `asta_aperta` VALUES (1,1,'domenico','bici','dsd',10,10,NULL,NULL),(2,2,'matteo','flessibile','asd',5,5,NULL,NULL),(3,3,'pietro','passamontagna','asd',5,5,NULL,NULL),(4,4,'michele','punteruolo','asd',12,12,NULL,NULL);
/*!40000 ALTER TABLE `asta_aperta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asta_chiusa`
--

DROP TABLE IF EXISTS `asta_chiusa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asta_chiusa` (
  `asta_id` int NOT NULL AUTO_INCREMENT,
  `id_utente` int NOT NULL,
  `nome` varchar(45) NOT NULL,
  `descrizione` varchar(45) NOT NULL,
  `immagine` varchar(45) NOT NULL,
  `aggiudicatario` int DEFAULT NULL,
  `prezzo` float DEFAULT NULL,
  `spedizione` varchar(45) DEFAULT NULL,
  `ora_apertura` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`asta_id`),
  KEY `id_utente_idx` (`id_utente`),
  CONSTRAINT `id_utente1` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asta_chiusa`
--

LOCK TABLES `asta_chiusa` WRITE;
/*!40000 ALTER TABLE `asta_chiusa` DISABLE KEYS */;
/*!40000 ALTER TABLE `asta_chiusa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dettaglio_asta`
--

DROP TABLE IF EXISTS `dettaglio_asta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dettaglio_asta` (
  `offerente` int NOT NULL,
  `data_offerta` varchar(45) NOT NULL,
  `importo` float DEFAULT NULL,
  `id_asta` int DEFAULT NULL,
  PRIMARY KEY (`offerente`,`data_offerta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dettaglio_asta`
--

LOCK TABLES `dettaglio_asta` WRITE;
/*!40000 ALTER TABLE `dettaglio_asta` DISABLE KEYS */;
INSERT INTO `dettaglio_asta` VALUES (1123,'23/6/66',7.54,8),(1123,'23/7/66',7.54,8),(1123,'24/7/66',7.54,8),(3333,'aaaaaa',5,9),(4444,'s',8,9);
/*!40000 ALTER TABLE `dettaglio_asta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id_utente` int NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `indirizzo_spedizione` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_utente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'domenico','1234','via tali '),(2,'matteo','1234','via tali '),(3,'pietro','1234','via tali '),(4,'michele','1234','via tali ');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-07 15:28:38
