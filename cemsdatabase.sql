-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cemsdatabase
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `CourseID` varchar(2) NOT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `maxExamNumber` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CourseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES ('01','Advanced Java Programming','04'),('02','Data Structures and Algorithms with Java','03'),('03','Introduction to Python','04'),('04','Advanced English','04'),('05','C++ for Beginners','01'),('06','Discrete Math','01'),('07','Classical Physics ','00');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coursedepartment`
--

DROP TABLE IF EXISTS `coursedepartment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coursedepartment` (
  `CourseID` varchar(50) NOT NULL,
  `DepartmentID` varchar(50) NOT NULL,
  PRIMARY KEY (`CourseID`,`DepartmentID`),
  KEY `did_idx` (`DepartmentID`),
  CONSTRAINT `CID` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  CONSTRAINT `DeparID` FOREIGN KEY (`DepartmentID`) REFERENCES `department` (`DepartmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coursedepartment`
--

LOCK TABLES `coursedepartment` WRITE;
/*!40000 ALTER TABLE `coursedepartment` DISABLE KEYS */;
INSERT INTO `coursedepartment` VALUES ('01','1'),('02','1'),('03','1'),('04','1'),('05','1'),('01','2'),('02','2'),('03','2'),('04','2'),('05','2'),('04','3'),('04','5'),('04','6'),('07','6'),('04','7'),('04','8'),('04','9'),('06','9');
/*!40000 ALTER TABLE `coursedepartment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coursesubject`
--

DROP TABLE IF EXISTS `coursesubject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coursesubject` (
  `CourseID` varchar(50) NOT NULL,
  `SubjectID` varchar(50) NOT NULL,
  PRIMARY KEY (`CourseID`,`SubjectID`),
  KEY `SubjID_idx` (`SubjectID`),
  CONSTRAINT `coursID` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  CONSTRAINT `SubjID` FOREIGN KEY (`SubjectID`) REFERENCES `subjects` (`SubjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coursesubject`
--

LOCK TABLES `coursesubject` WRITE;
/*!40000 ALTER TABLE `coursesubject` DISABLE KEYS */;
INSERT INTO `coursesubject` VALUES ('01','01'),('02','01'),('03','01'),('05','01'),('06','02'),('04','03'),('07','04');
/*!40000 ALTER TABLE `coursesubject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `DepartmentID` varchar(50) NOT NULL,
  `Name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`DepartmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES ('1','Information Systems Engineering'),('2','Software Engineering'),('3','Industrial Engineering'),('4','Mechanical Engineering'),('5','Optical Engineering'),('6','Civil Engineering'),('7','Biotechnology Engineering'),('8','Electrical Engineering'),('9','Applied Mathematics');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examparticipation`
--

DROP TABLE IF EXISTS `examparticipation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examparticipation` (
  `examID` varchar(45) NOT NULL,
  `date` varchar(45) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `actualDuration` int DEFAULT NULL,
  `totalStudents` int DEFAULT NULL,
  `completedStudents` int DEFAULT NULL,
  `incompletedStudents` int DEFAULT NULL,
  PRIMARY KEY (`examID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examparticipation`
--

LOCK TABLES `examparticipation` WRITE;
/*!40000 ALTER TABLE `examparticipation` DISABLE KEYS */;
/*!40000 ALTER TABLE `examparticipation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exams` (
  `ID` varchar(45) NOT NULL,
  `subjectID` varchar(45) DEFAULT NULL,
  `courseID` varchar(45) DEFAULT NULL,
  `commentsLecturer` varchar(1000) DEFAULT NULL,
  `commentsStudents` varchar(1000) DEFAULT NULL,
  `duration` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  `questionsInExam` varchar(100) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `isActive` varchar(45) DEFAULT NULL,
  `authorID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
INSERT INTO `exams` VALUES ('010104','01','01','Class EM203 has additional time','Java Exam - Duration 3 Hours','180','Omri Sharof','01003,01014,01015,01017,01016','C001','1','206391146'),('010203','01','02','check for additional time.','Exam duration is 2 hours','120','Omri Sharof','01004,01018,01019,01020,01021','C002','1','206391146'),('010304','01','03','Class EM101 has additional time.','Exam duration is 3 hours, formula sheet is allowed.','180','Omri Sharof','01001,01002,01011,01012,01013','C003','1','206391146'),('010501','01','05','Class M303 has additional time','Exam in C++, duration 3 hours','180','Omri Sharof','01005,01022,01023,01024,01025','C004','2','206391146'),('020601','02','06','-','Exam in discrete math','180','Omri Sharof','02001,02002,02003,02004','M001','2','206391146'),('030404','03','04','Class M208 has additonal Time','English Exam - 3 Hours Duration','180','Omri Sharof','03001,03003,03004,03005,03006','E001','1','206391146');
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finishedexam`
--

DROP TABLE IF EXISTS `finishedexam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `finishedexam` (
  `examID` varchar(45) NOT NULL,
  `studentID` varchar(45) NOT NULL,
  `answers` varchar(1000) DEFAULT NULL,
  `lecturer` varchar(45) DEFAULT NULL,
  `grade` double DEFAULT NULL,
  `approved` int DEFAULT NULL,
  `checkExam` int DEFAULT NULL,
  PRIMARY KEY (`examID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finishedexam`
--

LOCK TABLES `finishedexam` WRITE;
/*!40000 ALTER TABLE `finishedexam` DISABLE KEYS */;
INSERT INTO `finishedexam` VALUES ('010203','316350768',' | | | |','Omri Sharof',0,0,1),('010304','316350768',' | | | |','Omri Sharof',0,0,1),('010501','316350768','Pass by value: Memory address passed, modifications affect original.|they both increment the value before it is used.|it is used to define a new variable.|by using the \'final\' keyword.| They are used interchangeably','Omri Sharof',70,0,1),('030404','316350768','English is challenging due to its irregular grammar rules and exceptions.|Ate,Cool| | ','Omri Sharof',40,0,1);
/*!40000 ALTER TABLE `finishedexam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `headofdepartment`
--

DROP TABLE IF EXISTS `headofdepartment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `headofdepartment` (
  `HeadOfDepartmentID` varchar(50) NOT NULL,
  `UserName` varchar(50) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Department` varchar(50) DEFAULT NULL,
  `DepartmentID` varchar(45) DEFAULT NULL,
  `isLogged` int DEFAULT NULL,
  PRIMARY KEY (`HeadOfDepartmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `headofdepartment`
--

LOCK TABLES `headofdepartment` WRITE;
/*!40000 ALTER TABLE `headofdepartment` DISABLE KEYS */;
INSERT INTO `headofdepartment` VALUES ('2233','Yossi.Ohayon','123123','Yossi Ohayion','Yossi.Ohayon@e.braude.ac.il','Software Engineering','2',0);
/*!40000 ALTER TABLE `headofdepartment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `headofdepartmentrequests`
--

DROP TABLE IF EXISTS `headofdepartmentrequests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `headofdepartmentrequests` (
  `HeadOfDepartmentID` varchar(45) DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `course` varchar(45) DEFAULT NULL,
  `lecturerID` varchar(45) DEFAULT NULL,
  `examID` varchar(45) DEFAULT NULL,
  `lecturerName` varchar(45) DEFAULT NULL,
  `explanation` varchar(45) DEFAULT NULL,
  `examDurationAdd` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `headofdepartmentrequests`
--

LOCK TABLES `headofdepartmentrequests` WRITE;
/*!40000 ALTER TABLE `headofdepartmentrequests` DISABLE KEYS */;
INSERT INTO `headofdepartmentrequests` VALUES ('aaa2','Coding','Advanced Java Programming','206391146','010103','Omri Sharof','fds','23'),('aaa3','Coding','Advanced Java Programming','206391146','010103','Omri Sharof','sdfdfdfd','234'),('aaa3','Coding','Advanced Java Programming','206391146','010103','Omri Sharof','sdfdfdfd','234'),('aaa2','Coding','Advanced Java Programming','206391146','010103','Omri Sharof','sdfdfdfd','234'),('yossi ohayon','Coding','Advanced Java Programming','206391146','010103','Omri Sharof','d','sd'),('2233','Language','Advanced English','206391146','030404','Omri Sharof','asd','12');
/*!40000 ALTER TABLE `headofdepartmentrequests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturer`
--

DROP TABLE IF EXISTS `lecturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecturer` (
  `LecturerID` varchar(50) NOT NULL,
  `UserName` varchar(50) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `isLogged` int DEFAULT NULL,
  PRIMARY KEY (`LecturerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturer`
--

LOCK TABLES `lecturer` WRITE;
/*!40000 ALTER TABLE `lecturer` DISABLE KEYS */;
INSERT INTO `lecturer` VALUES ('206391146','Omri.Sharof','111111','Omri Sharof','Omri.Sharof@e.braude.ac.il',0),('333444555','Jason.Smith','123456','Jason Smith','Jason.Smith@e.braude.ac.il',0);
/*!40000 ALTER TABLE `lecturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturercourse`
--

DROP TABLE IF EXISTS `lecturercourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecturercourse` (
  `LecturerID` varchar(50) NOT NULL,
  `CourseID` varchar(50) NOT NULL,
  PRIMARY KEY (`LecturerID`,`CourseID`),
  KEY `LID_idx` (`LecturerID`),
  KEY `CorID_idx` (`CourseID`),
  CONSTRAINT `CorID` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  CONSTRAINT `LID` FOREIGN KEY (`LecturerID`) REFERENCES `lecturer` (`LecturerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturercourse`
--

LOCK TABLES `lecturercourse` WRITE;
/*!40000 ALTER TABLE `lecturercourse` DISABLE KEYS */;
INSERT INTO `lecturercourse` VALUES ('206391146','01'),('206391146','02'),('206391146','03'),('206391146','04'),('206391146','05'),('206391146','06'),('333444555','01'),('333444555','02'),('333444555','03'),('333444555','07');
/*!40000 ALTER TABLE `lecturercourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturerdepartment`
--

DROP TABLE IF EXISTS `lecturerdepartment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecturerdepartment` (
  `LecturerID` varchar(50) NOT NULL,
  `DepartmentID` varchar(50) NOT NULL,
  PRIMARY KEY (`LecturerID`,`DepartmentID`),
  KEY `DID_idx` (`DepartmentID`),
  CONSTRAINT `DID` FOREIGN KEY (`DepartmentID`) REFERENCES `department` (`DepartmentID`),
  CONSTRAINT `LecID` FOREIGN KEY (`LecturerID`) REFERENCES `lecturer` (`LecturerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturerdepartment`
--

LOCK TABLES `lecturerdepartment` WRITE;
/*!40000 ALTER TABLE `lecturerdepartment` DISABLE KEYS */;
INSERT INTO `lecturerdepartment` VALUES ('206391146','1'),('206391146','2'),('333444555','6'),('333444555','9');
/*!40000 ALTER TABLE `lecturerdepartment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturersubject`
--

DROP TABLE IF EXISTS `lecturersubject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecturersubject` (
  `LecturerID` varchar(50) NOT NULL,
  `SubjectID` varchar(50) NOT NULL,
  PRIMARY KEY (`LecturerID`,`SubjectID`),
  KEY `sbid_idx` (`SubjectID`),
  CONSTRAINT `lectid` FOREIGN KEY (`LecturerID`) REFERENCES `lecturer` (`LecturerID`),
  CONSTRAINT `sbid` FOREIGN KEY (`SubjectID`) REFERENCES `subjects` (`SubjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturersubject`
--

LOCK TABLES `lecturersubject` WRITE;
/*!40000 ALTER TABLE `lecturersubject` DISABLE KEYS */;
INSERT INTO `lecturersubject` VALUES ('206391146','01'),('206391146','02'),('333444555','02'),('206391146','03'),('333444555','04');
/*!40000 ALTER TABLE `lecturersubject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manualexams`
--

DROP TABLE IF EXISTS `manualexams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manualexams` (
  `examId` varchar(45) NOT NULL,
  `subjectID` varchar(45) DEFAULT NULL,
  `courseID` varchar(45) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `file` mediumblob,
  `commentsStudent` varchar(1000) DEFAULT NULL,
  `commentsLecturer` varchar(1000) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  `authorID` varchar(45) DEFAULT NULL,
  `isActive` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`examId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manualexams`
--

LOCK TABLES `manualexams` WRITE;
/*!40000 ALTER TABLE `manualexams` DISABLE KEYS */;
INSERT INTO `manualexams` VALUES ('123456','01','02','potato.txt',NULL,'Manual Exam','I like potatoes',180,'001','Ben Dover','6942042069','1');
/*!40000 ALTER TABLE `manualexams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` varchar(5) NOT NULL,
  `subjectID` varchar(45) DEFAULT NULL,
  `questionText` varchar(1000) DEFAULT NULL,
  `questionNumber` varchar(45) DEFAULT NULL,
  `answerCorrect` varchar(450) DEFAULT NULL,
  `answerWrong1` varchar(450) DEFAULT NULL,
  `answerWrong2` varchar(450) DEFAULT NULL,
  `answerWrong3` varchar(450) DEFAULT NULL,
  `lecturer` varchar(45) DEFAULT NULL,
  `LecturerID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `LectuID_idx` (`LecturerID`),
  KEY `sbjID_idx` (`subjectID`),
  CONSTRAINT `subjeID` FOREIGN KEY (`subjectID`) REFERENCES `subjects` (`SubjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES ('01001','01','What is the correct syntax for a loop in Python?.','001','for i in range(10)','while(i != 1)','i++','if(i==1)','Omri Sharof','206391146'),('01002','01','What is the correct syntax for a pointer? ','002','x = 10','x = *10','*x = 10','x = &10','Jason Smith','333444555'),('01003','01','What is the difference between a stack and a queue?','003','A stack is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed. Elements are added and removed from only one end of the stack.','A stack is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.','A queue is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed.','A queue is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.','Jason Smith','333444555'),('01004','01','What is the time complexity of binary search?','004','O(log n)','O(n)','O(n^2)','O(1)','Jason Smith','333444555'),('01005','01','What is the difference between pass by value and pass by reference?','005','Pass by value: Value copied, no modification affects original.','Pass by value: Memory address passed, modifications affect original.','Pass by reference: Value copied, modifications affect original.','Pass by reference: Memory address passed, no modification affects original.','Jason Smith','333444555'),('01011','01','Which keyword is used to define a function in Python?','011','def','for','if','while','Omri Sharof','206391146'),('01012','01','How do you check the length of a list in Python?','012','len()','size()','count()','length()','Omri Sharof','206391146'),('01013','01','Which data type is used to store a sequence of characters in Python?','013','str','int','float','bool','Omri Sharof','206391146'),('01014','01','In Java, what is the difference between an abstract class and an interface?','014','An abstract class can have method implementations and member variables, while an interface only has method signatures (without implementations) and constants.','An abstract class cannot have any methods.','an interface can have member variables.','an abstract class and an interface are the same.','Omri Sharof','206391146'),('01015','01','How can you achieve multiple inheritance in Java?','015','By implementing multiple interfaces.',' By extending multiple classes.','by using the \'implements\' keyword.','by using the \'extend\' keyword.','Omri Sharof','206391146'),('01016','01','What is the purpose of the \'static\' keyword in Java?','016','The \'static\' keyword is used to define variables and methods that belong to the class itself, rather than instances of the class.','The \'static\' keyword is used to prevent inheritance.','the \'static\' keyword is used to make a class immutable.','the \'static\' keyword is used to define variables and methods that can only be accessed within the same package.','Omri Sharof','206391146'),('01017','01','What is the difference between method overloading and method overriding in Java?','017','Same name, different parameters.','Method overloading occurs when a method has multiple return types, while method overriding occurs when a method has different access modifiers.','Method overloading occurs when a subclass provides a different implementation of a method.','while method overriding occurs when multiple methods in the same class have the same name.','Omri Sharof','206391146'),('01018','01','What is the time complexity of inserting an element into an ArrayList in Java?','018','O(1) (constant time)','O(log n) (logarithmic time)','O(n) (linear time)','O(n^2) (quadratic time)','Omri Sharof','206391146'),('01019','01','Which data structure follows the Last-In-First-Out (LIFO) principle?','019','Stack','Queue','LinkedList','HashMap','Omri Sharof','206391146'),('01020','01','What is the worst-case time complexity of searching for an element in a binary search tree (BST)?','020','O(log n) (logarithmic time)','O(1) (constant time)','O(n) (linear time)','O(n^2) (quadratic time)','Omri Sharof','206391146'),('01021','01','What sorting algorithm has an average time complexity of O(n log n)?','021','Merge Sort','Bubble Sort','Insertion Sort','Selection Sort','Omri Sharof','206391146'),('01022','01','What is the difference between the pre-increment (++x) and post-increment (x++) operators in C++?','022','The pre-increment operator increments the value of a variable before it is used, while the post-increment operator increments the value after it is used.','They have the same effect.',' they both increment the value after it is used.','they both increment the value before it is used.','Omri Sharof','206391146'),('01023','01','What is the purpose of the \'new\' keyword in C++?','023','The \'new\' keyword is used to dynamically allocate memory for an object at runtime.','t is used to deallocate memory.','it is used to define a new variable.','it is used to initialize an object.','Omri Sharof','206391146'),('01024','01','How do you define a constant variable in C++?','024','By using the \'const\' keyword before the variable declaration.',' By using the \'constant\' keyword.','by using the \'var\' keyword.','by using the \'final\' keyword.','Omri Sharof','206391146'),('01025','01','What is the difference between \'cout\' and \'cin\' in C++?','025','\'cout\' is used to output data to the console, while \'cin\' is used to read input data from the console.',' They are used interchangeably',' \'cout\' is used to read input data','\'cin\' is used to output data.','Omri Sharof','206391146'),('02001','02','If a set B has n elements, then what is the total number of subsets of B?','001','2^n','n!','n^2','n','Omri Sharof','206391146'),('02002','02','What is the negation of the statement \"For all x, P(x)\"?','002','There exists an x for which not P(x)','There does not exist an x for which P(x)','For some x, P(x)','P(x) is true for all x','Omri Sharof','206391146'),('02003','02','What is the result of the logical operation (p ∧ q) ∨ ¬q, given that p is true and q is false?','003','True','False','Undefined','Depends on other factors','Omri Sharof','206391146'),('02004','02','In combinatorics, how many ways can you arrange n distinct objects in a row?','004','n!','n^n','2^n','n+1','Omri Sharof','206391146'),('03001','03','Why is English so important?','001','English facilitates global communication and understanding across diverse cultures.','English is challenging due to its irregular grammar rules and exceptions.','English has a rich literary tradition with influential works.','English\'s significance is tied to its status as the official language of the United States.','Omri Sharof','206391146'),('03003','03','What is the past tense of the verb \"eat\"?','003','Ate','Eaten','Eats','Eat','Omri Sharof','206391146'),('03004','03','What is the opposite of the word \"hot\"?','004','Cold','Warm','Cool','Spicy','Omri Sharof','206391146'),('03005','03','Which word is a pronoun? ','005','He','Table','Walk','Blue','Omri Sharof','206391146'),('03006','03','What is the plural form of the word \"child\"?','006','Children','Childs','Childes','Childern','Omri Sharof','206391146'),('04001','04','In the context of electric circuits, what is the role of a resistor?','001','Resistor decreases the current flowing through the circuit','Resistor stores electrical energy.','Resistor increases the voltage of the circuit','Resistor acts as a switch, turning the circuit on and off','Jason Smith','333444555');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questioncourse`
--

DROP TABLE IF EXISTS `questioncourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questioncourse` (
  `questionID` varchar(45) NOT NULL,
  `courseID` varchar(45) NOT NULL,
  PRIMARY KEY (`questionID`,`courseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questioncourse`
--

LOCK TABLES `questioncourse` WRITE;
/*!40000 ALTER TABLE `questioncourse` DISABLE KEYS */;
INSERT INTO `questioncourse` VALUES ('01001','03'),('01002','03'),('01003','01'),('01004','02'),('01005','05'),('01006','01'),('01006','02'),('01006','03'),('01008','01'),('01008','02'),('01008','03'),('01008','05'),('01010','01'),('01010','02'),('01010','03'),('01010','05'),('01011','03'),('01012','03'),('01013','03'),('01014','01'),('01015','01'),('01016','01'),('01017','01'),('01018','02'),('01019','02'),('01020','02'),('01021','02'),('01022','05'),('01023','05'),('01024','05'),('01025','05'),('02001','06'),('02002','06'),('02003','06'),('02004','06'),('03001','04'),('03003','04'),('03004','04'),('03005','04'),('03006','04'),('04001','07');
/*!40000 ALTER TABLE `questioncourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionsexam`
--

DROP TABLE IF EXISTS `questionsexam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionsexam` (
  `questionID` varchar(45) NOT NULL,
  `examID` varchar(45) NOT NULL,
  `questionText` varchar(256) DEFAULT NULL,
  `answerCorrect` varchar(450) DEFAULT NULL,
  `answerWrong1` varchar(450) DEFAULT NULL,
  `answerWrong2` varchar(450) DEFAULT NULL,
  `answerWrong3` varchar(450) DEFAULT NULL,
  `points` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`questionID`,`examID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionsexam`
--

LOCK TABLES `questionsexam` WRITE;
/*!40000 ALTER TABLE `questionsexam` DISABLE KEYS */;
INSERT INTO `questionsexam` VALUES ('01001','010304','What is the correct syntax for a loop in Python?','i++','while(i != 1)','for i in range(10)','if(i==1)','20.0'),('01002','010304','What is the correct syntax for a pointer? ','x = *10','x = 10','*x = 10','x = &10','20.0'),('01003','010104','What is the difference between a stack and a queue?','A stack is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.','A stack is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed. Elements are added and removed from only one end of the stack.','A queue is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed.','A queue is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.','20.0'),('01004','010203','What is the time complexity of binary search?','O(n^2)','O(n)','O(1)','O(log n)','20.0'),('01005','010501','What is the difference between pass by value and pass by reference?','Pass by value: Memory address passed, modifications affect original.','Pass by value: Value copied, no modification affects original.','Pass by reference: Value copied, modifications affect original.','Pass by reference: Memory address passed, no modification affects original.','40.0'),('01011','010304','Which keyword is used to define a function in Python?','for','def','if','while','20.0'),('01012','010304','How do you check the length of a list in Python?','length()','size()','count()','len()','20.0'),('01013','010304','Which data type is used to store a sequence of characters in Python?','float','int','str','bool','20.0'),('01014','010104','In Java, what is the difference between an abstract class and an interface?','An abstract class cannot have any methods.','An abstract class can have method implementations and member variables, while an interface only has method signatures (without implementations) and constants.','an interface can have member variables.','an abstract class and an interface are the same.','30.0'),('01015','010104','How can you achieve multiple inheritance in Java?',' By extending multiple classes.','By implementing multiple interfaces.','by using the \'implements\' keyword.','by using the \'extend\' keyword.','20.0'),('01016','010104','What is the purpose of the \'static\' keyword in Java?','The \'static\' keyword is used to prevent inheritance.','The \'static\' keyword is used to define variables and methods that belong to the class itself, rather than instances of the class.','the \'static\' keyword is used to make a class immutable.','the \'static\' keyword is used to define variables and methods that can only be accessed within the same package.','10.0'),('01017','010104','What is the difference between method overloading and method overriding in Java?','Method overloading occurs when a subclass provides a different implementation of a method.','Method overloading occurs when a method has multiple return types, while method overriding occurs when a method has different access modifiers.','Same name, different parameters.','while method overriding occurs when multiple methods in the same class have the same name.','20.0'),('01018','010203','What is the time complexity of inserting an element into an ArrayList in Java?','O(log n) (logarithmic time)','O(n) (linear time)','O(1) (constant time)','O(n^2) (quadratic time)','20.0'),('01019','010203','Which data structure follows the Last-In-First-Out (LIFO) principle?','LinkedList','Queue','HashMap','Stack','20.0'),('01020','010203','What is the worst-case time complexity of searching for an element in a binary search tree (BST)?','O(n) (linear time)','O(log n) (logarithmic time)','O(1) (constant time)','O(n^2) (quadratic time)','20.0'),('01021','010203','What sorting algorithm has an average time complexity of O(n log n)?','Selection Sort','Merge Sort','Insertion Sort','Bubble Sort','20.0'),('01022','010501','What is the difference between the pre-increment (++x) and post-increment (x++) operators in C++?','they both increment the value before it is used.','They have the same effect.',' they both increment the value after it is used.','The pre-increment operator increments the value of a variable before it is used, while the post-increment operator increments the value after it is used.','10.0'),('01023','010501','What is the purpose of the \'new\' keyword in C++?','it is used to define a new variable.','t is used to deallocate memory.','The \'new\' keyword is used to dynamically allocate memory for an object at runtime.','it is used to initialize an object.','10.0'),('01024','010501','How do you define a constant variable in C++?','by using the \'final\' keyword.',' By using the \'constant\' keyword.','by using the \'var\' keyword.','By using the \'const\' keyword before the variable declaration.','10.0'),('01025','010501','What is the difference between \'cout\' and \'cin\' in C++?','\'cin\' is used to output data.',' They are used interchangeably',' \'cout\' is used to read input data','\'cout\' is used to output data to the console, while \'cin\' is used to read input data from the console.','30.0'),('02001','020601','If a set B has n elements, then what is the total number of subsets of B?','n','n!','n^2','2^n','30.0'),('02002','020601','What is the negation of the statement \"For all x, P(x)\"?','P(x) is true for all x','There does not exist an x for which P(x)','For some x, P(x)','There exists an x for which not P(x)','20.0'),('02003','020601','What is the result of the logical operation (p ∧ q) ∨ ¬q, given that p is true and q is false?','False','True','Undefined','Depends on other factors','30.0'),('02004','020601','In combinatorics, how many ways can you arrange n distinct objects in a row?','n+1','n^n','2^n','n!','20.0'),('03001','030404','Why is English so important?','English is challenging due to its irregular grammar rules and exceptions.','English facilitates global communication and understanding across diverse cultures.','English has a rich literary tradition with influential works.','English\'s significance is tied to its status as the official language of the United States.','20.0'),('03003','030404','What is the past tense of the verb \"eat\"?','Eats','Eaten','Ate','Eat','20.0'),('03004','030404','What is the opposite of the word \"hot\"?','Cool','Warm','Cold','Spicy','20.0'),('03005','030404','Which word is a pronoun? ','Blue','Table','Walk','He','20.0'),('03006','030404','What is the plural form of the word \"child\"?','Childs','Children','Childes','Childern','20.0');
/*!40000 ALTER TABLE `questionsexam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `StudentID` varchar(50) NOT NULL,
  `UserName` varchar(50) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Department` varchar(50) DEFAULT NULL,
  `DepartmentID` varchar(50) DEFAULT NULL,
  `isLogged` int DEFAULT NULL,
  PRIMARY KEY (`StudentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('206392246','Aleksander.Pitkin','123456','Aleksander Pitkin','Aleksander.Pitkin@e.braude.ac.il','GayClub','2',0),('316350768','Nadav.Goldin','123456','Nadav Goldin','Nadav.Goldin@e.braude.ac.il','Software Engineering','1',0);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentcourse`
--

DROP TABLE IF EXISTS `studentcourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentcourse` (
  `StudentID` varchar(50) NOT NULL,
  `CourseID` varchar(50) NOT NULL,
  PRIMARY KEY (`StudentID`,`CourseID`),
  KEY `CourseID_idx` (`CourseID`),
  CONSTRAINT `CourseID` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`),
  CONSTRAINT `SID` FOREIGN KEY (`StudentID`) REFERENCES `student` (`StudentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentcourse`
--

LOCK TABLES `studentcourse` WRITE;
/*!40000 ALTER TABLE `studentcourse` DISABLE KEYS */;
INSERT INTO `studentcourse` VALUES ('206392246','01'),('316350768','01'),('206392246','02'),('316350768','02'),('206392246','03'),('316350768','03'),('206392246','04'),('316350768','04'),('206392246','05'),('316350768','05'),('206392246','06'),('316350768','06'),('206392246','07'),('316350768','07');
/*!40000 ALTER TABLE `studentcourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjects` (
  `SubjectID` varchar(2) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `MaxQuestionNumber` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`SubjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES ('01','Coding','025'),('02','Math','004'),('03','Language','006'),('04','Physics','000');
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-16 22:17:40
