-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: cemsdatabase
-- ------------------------------------------------------
-- Server version	8.0.32

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
INSERT INTO `course` VALUES ('01','Advanced Java Programming','05'),('02','Data Structures and Algorithms with Java','04'),('03','Introduction to Python','05'),('04','Advanced English','05'),('05','C++ for Beginners','02'),('06','Discrete Math','02'),('07','Classical Physics ','02'),('08','Calculus I','02'),('09','Physics Fundamentals','01'),('10','Linear Algebra','02'),('11','Quantum Mechanics','01'),('12','Web Development','02'),('13','Machine Learning','02'),('14','Differential Equations','00'),('15','Probability Theory','01');
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
INSERT INTO `coursedepartment` VALUES ('01','1'),('02','1'),('03','1'),('04','1'),('05','1'),('12','1'),('13','1'),('01','2'),('02','2'),('03','2'),('04','2'),('05','2'),('12','2'),('13','2'),('04','3'),('09','4'),('04','5'),('10','5'),('04','6'),('07','6'),('11','6'),('04','7'),('04','8'),('09','8'),('04','9'),('06','9'),('08','9'),('14','9'),('15','9');
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
INSERT INTO `coursesubject` VALUES ('01','01'),('02','01'),('03','01'),('05','01'),('12','01'),('13','01'),('06','02'),('08','02'),('10','02'),('14','02'),('15','02'),('04','03'),('07','04'),('09','04'),('11','04');
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
INSERT INTO `examparticipation` VALUES ('010105','19/06/2023',180,2,6,6,0),('010204','19/06/2023',120,1,6,6,0),('011202','19/06/2023',180,0,6,4,2),('020601','19/06/2023',180,0,2,2,0),('020802','19/06/2023',180,7,6,6,0),('021001','19/06/2023',180,1,6,6,0),('030405','19/06/2023',60,3,6,6,0);
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
INSERT INTO `exams` VALUES ('010105','01','01','Class M119 has extra time.','Exam duration 3 hours.','180','Omri Sharof','01003,01014,01015,01016,01017','C001','2','206391146'),('010204','01','02','-','Exam duration 2 hours','120','Omri Sharof','01004,01018,01019,01020,01021','C002','2','206391146'),('010305','01','03','-','Exam duration 3 hours','180','Omri Sharof','01001,01002,01011,01012,01013','C003','1','206391146'),('010502','01','05','-','Exam duration 2 hours','120','Omri Sharof','01005,01022,01023,01024,01025','C004','1','206391146'),('011202','01','12','-','Class L804 has extra time.','180','Rebecca Cohen','01031,01032,01033,01034,01035','C005','2','654987321'),('011302','01','13','-','Class L838 has extra time.','180','Rebecca Cohen','01026,01027,01028,01029,01030','C006','1','654987321'),('020602','02','06','-','Exam duration 2 hours','120','Omri Sharof','02001,02004,02002,02003','M001','1','206391146'),('020802','02','08','-','Class M404 has extra time.','180','Mark Berman','02005,02006,02007,02008,02009','M002','2','315728904'),('021001','02','10','Class M202 has extra time.','3 hours duration.','180','Jacob Rosenberg','02010,02011,02012,02013,02014','M003','2','321654987'),('021002','02','10','Class L701 has extra time.','Exam duration 2 hours.','120','Sophia Harris','02010,02011,02019,02017,02016','M004','1','796428315'),('021501','02','15','-','Exam duration 3 hours.','180','Sophia Harris','02020,02021,02022,02023,02024','M005','1','796428315'),('030405','03','04','-','Exam duration 1 hour.','60','Omri Sharof','03001,03003,03004,03005,03006','E001','2','206391146'),('040702','04','07','Class L704 has additional time.','Classical Physics Exam, Duration 3 hours.','180','Jason Smith','04004,04005,04006,04007,04008','P001','1','333444555'),('040901','04','09','-','Exam duration 3 hours.','180','Sophia Harris','04009,04010,04011,04012,04013','P002','1','796428315'),('041101','04','11','-','Exam duration 2 hours','120','Elena Aharon','04014,04015,04016,04017,04018','P003','1','864297531');
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `external_users`
--

DROP TABLE IF EXISTS `external_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `external_users` (
  `ID` varchar(45) NOT NULL,
  `UserName` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Department` varchar(45) DEFAULT NULL,
  `DepartmentID` varchar(45) DEFAULT NULL,
  `Role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `external_users`
--

LOCK TABLES `external_users` WRITE;
/*!40000 ALTER TABLE `external_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `external_users` ENABLE KEYS */;
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
  PRIMARY KEY (`examID`,`studentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finishedexam`
--

LOCK TABLES `finishedexam` WRITE;
/*!40000 ALTER TABLE `finishedexam` DISABLE KEYS */;
INSERT INTO `finishedexam` VALUES ('010105','098765432','A stack is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed. Elements are added and removed from only one end of the stack.|An abstract class can have method implementations and member variables, while an interface only has method signatures (without implementations) and constants.|by using the \'implements\' keyword.|the \'static\' keyword is used to make a class immutable.|Same name, different parameters.','Omri Sharof',60,1,1),('010105','123456789','A stack is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed. Elements are added and removed from only one end of the stack.|An abstract class can have method implementations and member variables, while an interface only has method signatures (without implementations) and constants.|By implementing multiple interfaces.|The \'static\' keyword is used to define variables and methods that belong to the class itself, rather than instances of the class.|Same name, different parameters.','Omri Sharof',100,1,1),('010105','206392246','A stack is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed. Elements are added and removed from only one end of the stack.|an interface can have member variables.|by using the \'extend\' keyword.|The \'static\' keyword is used to define variables and methods that belong to the class itself, rather than instances of the class.|Method overloading occurs when a subclass provides a different implementation of a method.','Omri Sharof',80,1,1),('010105','316350768','A stack is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed. Elements are added and removed from only one end of the stack.|an abstract class and an interface are the same.|By implementing multiple interfaces.|the \'static\' keyword is used to make a class immutable.|Method overloading occurs when a method has multiple return types, while method overriding occurs when a method has different access modifiers.','Omri Sharof',90,1,1),('010105','456765422','A queue is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.|an interface can have member variables.|By implementing multiple interfaces.|The \'static\' keyword is used to prevent inheritance.|Method overloading occurs when a subclass provides a different implementation of a method.','Omri Sharof',20,1,1),('010105','987654321','A stack is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed. Elements are added and removed from only one end of the stack.|an interface can have member variables.|By implementing multiple interfaces.|The \'static\' keyword is used to prevent inheritance.|Method overloading occurs when a method has multiple return types, while method overriding occurs when a method has different access modifiers.','Omri Sharof',40,1,1),('010204','098765432','O(log n)|O(1) (constant time)|Stack|O(1) (constant time)|Insertion Sort','Omri Sharof',60,1,1),('010204','123456789','O(log n)|O(1) (constant time)|Stack|O(log n) (logarithmic time)|Merge Sort','Omri Sharof',100,1,1),('010204','206392246','O(log n)|O(1) (constant time)|Queue|O(n^2) (quadratic time)|Merge Sort','Omri Sharof',80,1,1),('010204','316350768','O(log n)|O(1) (constant time)|Queue|O(n^2) (quadratic time)|Merge Sort','Omri Sharof',77,1,1),('010204','456765422','O(log n)|O(1) (constant time)|Queue|O(n^2) (quadratic time)|Merge Sort','Omri Sharof',94,1,1),('010204','987654321','O(log n)|O(1) (constant time)|Stack|O(n) (linear time)|Bubble Sort','Omri Sharof',44,1,1),('011202','098765432','HTML is for structuring web content, while CSS is for styling and formatting.|JavaScript adds interactivity and dynamic behavior to web pages.| | | ','Rebecca Cohen',40,1,1),('011202','123456789','HTML is for structuring web content, while CSS is for styling and formatting.| | | | ','Rebecca Cohen',20,1,1),('011202','206392246','HTML is for styling, while CSS is for structuring content.|JavaScript creates databases in web development.|A server is only required for static web pages.|A database is only necessary for static web pages.|Back-end is only concerned with visual elements.','Rebecca Cohen',0,1,1),('011202','316350768','HTML is for structuring web content, while CSS is for styling and formatting.|JavaScript is primarily used for server-side processing.|A server is used for client-side scripting.|A database is used for styling web pages.|Back-end is only concerned with visual elements.','Rebecca Cohen',20,1,1),('011202','456765422','HTML is for styling, while CSS is for structuring content.|JavaScript creates databases in web development.|A server hosts web applications and processes requests from clients.|A database is only necessary for static web pages.|Front-end and back-end are interchangeable terms.','Rebecca Cohen',20,1,1),('011202','987654321','HTML and CSS can be used interchangeably.|JavaScript is primarily used for server-side processing.|A server hosts web applications and processes requests from clients.|A database stores and manages structured data for web applications.|Front-end and back-end are interchangeable terms.','Rebecca Cohen',40,1,1),('020802','098765432','The derivative of f(x) is f\'(x) = 6x + 2.|The indefinite integral of g(x) is G(x) = 2x^4 - 5x^3 + 2x^2 + 7x.|The limit of h(x) as x approaches 2 is 2.|The critical point of f(x) is x = 2.|The definite integral of f(x) over the interval [0, 3] is 8.','Mark Berman',20,1,1),('020802','123456789','The derivative of f(x) is f\'(x) = 6x + 2.|The indefinite integral of g(x) is G(x) = 2x^4 - 5x^3 + 2x^2 + 7x.|The limit of h(x) as x approaches 2 is undefined.|The critical points of f(x) are x = -√3 and x = √3.|The definite integral of f(x) over the interval [0, 3] is 6.','Mark Berman',40,1,1),('020802','206392246','The derivative of f(x) is f\'(x) = 3x^3 + 2.|The indefinite integral of g(x) is G(x) = (1/2)x^4 - (5/3)x^3 + 2x^2 + 7x.|The limit of h(x) as x approaches 2 is 0.|The critical point of f(x) is x = -1.|The definite integral of f(x) over the interval [0, 3] is 12.','Mark Berman',20,1,1),('020802','316350768','The derivative of f(x) is f\'(x) = 6x + 2.|The indefinite integral of g(x) is G(x) = 2x^4 - 5x^3 + 2x^2 + 7x.|The limit of h(x) as x approaches 2 is undefined.|The critical points of f(x) are x = -√3 and x = √3.|The definite integral of f(x) over the interval [0, 3] is 9.','Mark Berman',60,1,1),('020802','456765422','The derivative of f(x) is f\'(x) = 9x + 2.|The indefinite integral of g(x) is G(x) = 6x^3 - 5x^2 + 2x + 7.|The limit of h(x) as x approaches 2 is 4.|The critical point of f(x) is x = -1.|The definite integral of f(x) over the interval [0, 3] is 9.','Mark Berman',40,1,1),('020802','987654321','The derivative of f(x) is f\'(x) = 3x + 2.|The indefinite integral of g(x) is G(x) = (1/2)x^4 - (5/3)x^3 + 2x^2 + 7x.|The limit of h(x) as x approaches 2 is 4.|The critical points of f(x) are x = -√3 and x = √3.|The definite integral of f(x) over the interval [0, 3] is 6.','Mark Berman',90,1,1),('021001','098765432','The dot product of vectors u and v is u · v = u₁v₁ + u₂v₂ + ... + uₙvₙ.|The determinant of a 2x2 matrix A is det(A) = ad - bc|The cross product of vectors u and v in three dimensions is u × v = u + v.|The eigenvalues of a square matrix A are found by solving the characteristic equation: det(A - λI) = 0.|The inverse of a square matrix A is A⁻¹ = A^-1.','Jacob Rosenberg',20,1,1),('021001','123456789','The dot product of vectors u and v is u · v = u₁v₁ + u₂v₂ + ... + uₙvₙ.|The determinant of a 2x2 matrix A is det(A) = ad - bc|The cross product of vectors u and v in three dimensions is u × v = u - v.|The eigenvalues of a square matrix A are found by solving the characteristic equation: det(A - λI) = 0.|The inverse of a square matrix A is A⁻¹ = A + I.','Jacob Rosenberg',0,1,1),('021001','206392246','The dot product of vectors u and v is u · v = u₁v₁ + u₂v₂ + ... + uₙvₙ.|The determinant of a 2x2 matrix A is det(A) = ad - bc|The cross product of vectors u and v in three dimensions is u × v = |u| |v| sinθ.|The eigenvalues of a square matrix A are found by solving the characteristic equation: det(A - λI) = 0.|The inverse of a square matrix A is A⁻¹ = A^T.','Jacob Rosenberg',20,1,1),('021001','316350768','The dot product of vectors u and v is u · v = u₁v₁ + u₂v₂ + ... + uₙvₙ.|The determinant of a 2x2 matrix A is det(A) = ad - bc|The cross product of vectors u and v in three dimensions is u × v = |u| |v| sinθ.|The eigenvalues of a square matrix A are found by solving the characteristic equation: det(A - λI) = 0.|The inverse of a square matrix A is A⁻¹ = A^T.','Jacob Rosenberg',20,1,1),('021001','456765422','The dot product of vectors u and v is u · v = u₁v₁ + u₂v₂ + ... + uₙvₙ.|The determinant of a 2x2 matrix A is det(A) = ad - bc|The cross product of vectors u and v in three dimensions is u × v = u - v.|The eigenvalues of a square matrix A are eigenvalues(A) = |A|.|The inverse of a square matrix A is A⁻¹ = A + I.','Jacob Rosenberg',80,1,1),('021001','987654321','The dot product of vectors u and v is u · v = u₁v₁ + u₂v₂ + ... + uₙvₙ.|The determinant of a 2x2 matrix A is det(A) = a/b + c/d.|u × v = (u₂v₃ - u₃v₂)i - (u₁v₃ - u₃v₁)j + (u₁v₂ - u₂v₁)k.|The eigenvalues of a square matrix A are found by solving the characteristic equation: det(A - λI) = 0.|The inverse of a square matrix A is A⁻¹ = A^T.','Jacob Rosenberg',60,1,1),('030405','098765432','English facilitates global communication and understanding across diverse cultures.|Eaten|Cold|He|Childern','Omri Sharof',70,1,1),('030405','123456789','English\'s significance is tied to its status as the official language of the United States.|Eats|Cold|He|Childern','Omri Sharof',30,1,1),('030405','206392246','English has a rich literary tradition with influential works.|Eat|Cold|Blue|Childes','Omri Sharof',10,1,1),('030405','316350768','English facilitates global communication and understanding across diverse cultures.|Eaten|Cold|He|Childs','Omri Sharof',70,1,1),('030405','456765422','English facilitates global communication and understanding across diverse cultures.|Ate|Cold|He|Childern','Omri Sharof',90,1,1),('030405','987654321','English facilitates global communication and understanding across diverse cultures.|Eat|Cold|He|Children','Omri Sharof',80,1,1);
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
INSERT INTO `headofdepartment` VALUES ('1111','Miriam.Levy','123456','Miriam.Levy','Miriam.Levy@e.braude.ac.il','Applied Mathematics','9',0),('1212','Daniel.Rosenberg','090909','Daniel Rosenberg','Daniel.Rosenberg@e.braude.ac.il','Civil Engineering','6',0),('2222','David.Katz','123123','David.Katz','David.Katz@e.braude.ac.il','Mechanical Engineering','4',0),('2233','Yossi.Ohayon','123123','Yossi Ohayion','Yossi.Ohayon@e.braude.ac.il','Software Engineering','2',0),('3344','Avraham.Cohen','123456','Avraham.Cohen','Avraham.Cohen@e.braude.ac.il','Information Systems Engineering','1',0),('3737','Sarah.Cohen','888888','Sarah.Cohen','Sarah.Cohen@e.braude.ac.il','Biotechnology Engineering','7',0),('4455','Leah.Weiss','222222','Leah Weiss','Leah.Weiss@e.braude.ac.il','Optical Engineering','5',0),('5555','Rachel.Goldstein','000000','Rachel.Goldstein','Rachel.Goldstein@e.braude.ac.il','Electrical Engineering','8',0),('6666','Eli.Cohen','111111','Eli.Cohen','Eli.Cohen@e.braude.ac.il','Industrial Engineering','3',0);
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
INSERT INTO `lecturer` VALUES ('206391146','Omri.Sharof','111111','Omri Sharof','Omri.Sharof@e.braude.ac.il',0),('315728904','Mark.Berman','334433','Mark Berman','Mark.Berman@e.braude.ac.il',0),('321654987','Jacob.Rosenberg','321789','Jacob Rosenberg','Jacob.Rosenberg@e.braude.ac.il',0),('333444555','Jason.Smith','123456','Jason Smith','Jason.Smith@e.braude.ac.il',0),('654987321','Rebecca.Cohen','987321','Rebecca Cohen','Rebecca.Cohen@e.braude.ac.il',0),('796428315','Sophia.Harris','315204','Sophia Harris','Sophia.Harris@e.braude.ac.il',0),('864297531','Elena.Aharon','123123','Elena Aharon','Elena.Aharon@e.braude.ac.il',0);
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
INSERT INTO `lecturercourse` VALUES ('206391146','01'),('206391146','02'),('206391146','03'),('206391146','04'),('206391146','05'),('206391146','06'),('206391146','14'),('315728904','08'),('321654987','10'),('333444555','01'),('333444555','02'),('333444555','03'),('333444555','07'),('654987321','12'),('654987321','13'),('796428315','09'),('796428315','10'),('796428315','15'),('864297531','11');
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
INSERT INTO `lecturerdepartment` VALUES ('206391146','1'),('206391146','2'),('796428315','3'),('864297531','4'),('315728904','5'),('864297531','5'),('333444555','6'),('315728904','7'),('321654987','7'),('654987321','8'),('315728904','9'),('333444555','9');
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
INSERT INTO `lecturersubject` VALUES ('206391146','01'),('654987321','01'),('206391146','02'),('315728904','02'),('321654987','02'),('333444555','02'),('796428315','02'),('206391146','03'),('333444555','04'),('796428315','04'),('864297531','04');
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
  `filePath` varchar(1000) DEFAULT NULL,
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
INSERT INTO `manualexams` VALUES ('123456','01','02','potato.txt','C:/TestFiles','Manual Exam','I like potatoes',180,'001','Ben Dover','6942042069','1');
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
INSERT INTO `question` VALUES ('01001','01','What is the correct syntax for a loop in Python?.','001','for i in range(10)','while(i != 1)','i++','if(i==1)','Omri Sharof','206391146'),('01002','01','What is the correct syntax for a pointer? ','002','x = 10','x = *10','*x = 10','x = &10','Jason Smith','333444555'),('01003','01','What is the difference between a stack and a queue?','003','A stack is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed. Elements are added and removed from only one end of the stack.','A stack is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.','A queue is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed.','A queue is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.','Jason Smith','333444555'),('01004','01','What is the time complexity of binary search?','004','O(log n)','O(n)','O(n^2)','O(1)','Jason Smith','333444555'),('01005','01','What is the difference between pass by value and pass by reference?','005','Pass by value: Value copied, no modification affects original.','Pass by value: Memory address passed, modifications affect original.','Pass by reference: Value copied, modifications affect original.','Pass by reference: Memory address passed, no modification affects original.','Jason Smith','333444555'),('01011','01','Which keyword is used to define a function in Python?','011','def','for','if','while','Omri Sharof','206391146'),('01012','01','How do you check the length of a list in Python?','012','len()','size()','count()','length()','Omri Sharof','206391146'),('01013','01','Which data type is used to store a sequence of characters in Python?','013','str','int','float','bool','Omri Sharof','206391146'),('01014','01','In Java, what is the difference between an abstract class and an interface?','014','An abstract class can have method implementations and member variables, while an interface only has method signatures (without implementations) and constants.','An abstract class cannot have any methods.','an interface can have member variables.','an abstract class and an interface are the same.','Omri Sharof','206391146'),('01015','01','How can you achieve multiple inheritance in Java?','015','By implementing multiple interfaces.',' By extending multiple classes.','by using the \'implements\' keyword.','by using the \'extend\' keyword.','Omri Sharof','206391146'),('01016','01','What is the purpose of the \'static\' keyword in Java?','016','The \'static\' keyword is used to define variables and methods that belong to the class itself, rather than instances of the class.','The \'static\' keyword is used to prevent inheritance.','the \'static\' keyword is used to make a class immutable.','the \'static\' keyword is used to define variables and methods that can only be accessed within the same package.','Omri Sharof','206391146'),('01017','01','What is the difference between method overloading and method overriding in Java?','017','Same name, different parameters.','Method overloading occurs when a method has multiple return types, while method overriding occurs when a method has different access modifiers.','Method overloading occurs when a subclass provides a different implementation of a method.','while method overriding occurs when multiple methods in the same class have the same name.','Omri Sharof','206391146'),('01018','01','What is the time complexity of inserting an element into an ArrayList in Java?','018','O(1) (constant time)','O(log n) (logarithmic time)','O(n) (linear time)','O(n^2) (quadratic time)','Omri Sharof','206391146'),('01019','01','Which data structure follows the Last-In-First-Out (LIFO) principle?','019','Stack','Queue','LinkedList','HashMap','Omri Sharof','206391146'),('01020','01','What is the worst-case time complexity of searching for an element in a binary search tree (BST)?','020','O(log n) (logarithmic time)','O(1) (constant time)','O(n) (linear time)','O(n^2) (quadratic time)','Omri Sharof','206391146'),('01021','01','What sorting algorithm has an average time complexity of O(n log n)?','021','Merge Sort','Bubble Sort','Insertion Sort','Selection Sort','Omri Sharof','206391146'),('01022','01','What is the difference between the pre-increment (++x) and post-increment (x++) operators in C++?','022','The pre-increment operator increments the value of a variable before it is used, while the post-increment operator increments the value after it is used.','They have the same effect.',' they both increment the value after it is used.','they both increment the value before it is used.','Omri Sharof','206391146'),('01023','01','What is the purpose of the \'new\' keyword in C++?','023','The \'new\' keyword is used to dynamically allocate memory for an object at runtime.','t is used to deallocate memory.','it is used to define a new variable.','it is used to initialize an object.','Omri Sharof','206391146'),('01024','01','How do you define a constant variable in C++?','024','By using the \'const\' keyword before the variable declaration.',' By using the \'constant\' keyword.','by using the \'var\' keyword.','by using the \'final\' keyword.','Omri Sharof','206391146'),('01025','01','What is the difference between \'cout\' and \'cin\' in C++?','025','\'cout\' is used to output data to the console, while \'cin\' is used to read input data from the console.',' They are used interchangeably',' \'cout\' is used to read input data','\'cin\' is used to output data.','Omri Sharof','206391146'),('01026','01','What is the purpose of training and testing data in machine learning?','026','The purpose of training and testing data in machine learning is to evaluate the model\'s performance and generalization ability.','The purpose of training and testing data in machine learning is to increase the model complexity.','The purpose of training and testing data in machine learning is to determine the accuracy of input features.','The purpose of training and testing data in machine learning is to calculate the loss function.','Rebecca Cohen','654987321'),('01027','01','What is overfitting in machine learning?','027','Overfitting in machine learning occurs when a model performs well on the training data but fails to generalize to new data.','Overfitting in machine learning occurs when a model performs poorly on the training data.','Overfitting in machine learning occurs when the model is too simple.','Overfitting in machine learning occurs when the model has too few training examples.','Rebecca Cohen','654987321'),('01028','01','What is the purpose of regularization in machine learning?','028','Regularization in machine learning prevents overfitting by adding a penalty term to the loss function.','Regularization in machine learning increases the model complexity.','Regularization in machine learning decreases the model\'s ability to generalize.','Regularization in machine learning makes the model more sensitive to noise.','Rebecca Cohen','654987321'),('01029','01','What is the difference between supervised and unsupervised learning?','029','Supervised learning uses labeled data, while unsupervised learning uses unlabeled data.','Supervised learning is for classification tasks, while unsupervised learning is for regression tasks.','Supervised learning requires fewer computational resources than unsupervised learning.','The difference between supervised and unsupervised learning lies in the use of feature extraction techniques.','Rebecca Cohen','654987321'),('01030','01','What is the purpose of cross-validation in machine learning?','030','Cross-validation in machine learning assesses the performance and generalization ability of a model using multiple subsets of data.','Cross-validation in machine learning increases the complexity of the model.','Cross-validation in machine learning tunes the hyperparameters of the model.','Cross-validation in machine learning minimizes the training time of the model.','Rebecca Cohen','654987321'),('01031','01','What is the difference between HTML and CSS?','031','HTML is for structuring web content, while CSS is for styling and formatting.','HTML and CSS are both programming languages.','HTML is for styling, while CSS is for structuring content.','HTML and CSS can be used interchangeably.','Rebecca Cohen','654987321'),('01032','01','What is the purpose of JavaScript in web development?','032','JavaScript adds interactivity and dynamic behavior to web pages.','JavaScript is primarily used for server-side processing.','JavaScript defines the structure of web pages.','JavaScript creates databases in web development.','Rebecca Cohen','654987321'),('01033','01','What is the role of a server in web development?','033','A server hosts web applications and processes requests from clients.','A server creates web page layouts.','A server is used for client-side scripting.','A server is only required for static web pages.','Rebecca Cohen','654987321'),('01034','01','What is the purpose of a database in web development?','034','A database stores and manages structured data for web applications.','A database is used for styling web pages.','A database handles user interactions.','A database is only necessary for static web pages.','Rebecca Cohen','654987321'),('01035','01','What is the difference between front-end and back-end development?','035','Front-end focuses on the client-side, while back-end handles server-side operations.','Front-end is responsible for server-side processing.','Back-end is only concerned with visual elements.','Front-end and back-end are interchangeable terms.','Rebecca Cohen','654987321'),('02001','02','If a set B has n elements, then what is the total number of subsets of B?','001','2^n','n!','n^2','n','Omri Sharof','206391146'),('02002','02','What is the negation of the statement \"For all x, P(x)\"?','002','There exists an x for which not P(x)','There does not exist an x for which P(x)','For some x, P(x)','P(x) is true for all x','Omri Sharof','206391146'),('02003','02','What is the result of the logical operation (p ∧ q) ∨ ¬q, given that p is true and q is false?','003','True','False','Undefined','Depends on other factors','Omri Sharof','206391146'),('02004','02','In combinatorics, how many ways can you arrange n distinct objects in a row?','004','n!','n^n','2^n','n+1','Omri Sharof','206391146'),('02005','02','What is the derivative of f(x) = 3x^2 + 2x - 1?','005','The derivative of f(x) is f\'(x) = 6x + 2.','The derivative of f(x) is f\'(x) = 9x + 2.','The derivative of f(x) is f\'(x) = 3x^3 + 2.','The derivative of f(x) is f\'(x) = 3x + 2.','Mark Berman','315728904'),('02006','02','Find the indefinite integral of g(x) = 2x^3 - 5x^2 + 4x + 7.','006','The indefinite integral of g(x) is G(x) = (1/2)x^4 - (5/3)x^3 + 2x^2 + 7x.','The indefinite integral of g(x) is G(x) = 6x^3 - 5x^2 + 2x + 7.','The indefinite integral of g(x) is G(x) = x^4 - x^2 + 4x + 7.','The indefinite integral of g(x) is G(x) = 2x^4 - 5x^3 + 2x^2 + 7x.','Mark Berman','315728904'),('02007','02','Determine the limit of h(x) as x approaches 2, where h(x) = (x^2 - 4)/(x - 2).','007','The limit of h(x) as x approaches 2 is 4.','The limit of h(x) as x approaches 2 is 0.','The limit of h(x) as x approaches 2 is 2.','The limit of h(x) as x approaches 2 is undefined.','Mark Berman','315728904'),('02008','02','Find the critical points of the function f(x) = x^3 - 12x + 1.','008','The critical points of f(x) are x = -√3 and x = √3.','The critical point of f(x) is x = 0.','The critical point of f(x) is x = -1.','The critical point of f(x) is x = 2.','Mark Berman','315728904'),('02009','02','Evaluate the definite integral of f(x) = 2x over the interval [0, 3].','009','The definite integral of f(x) over the interval [0, 3] is 9.','The definite integral of f(x) over the interval [0, 3] is 6.','The definite integral of f(x) over the interval [0, 3] is 8.','The definite integral of f(x) over the interval [0, 3] is 12.','Mark Berman','315728904'),('02010','02','What is the formula for calculating the dot product of two vectors u and v?','010','The dot product of vectors u and v is u · v = u₁v₁ + u₂v₂ + ... + uₙvₙ.','The dot product of vectors u and v is u · v = |u| |v| cosθ.','The dot product of vectors u and v is u · v = u + v.','The dot product of vectors u and v is u · v = u - v.','Jacob Rosenberg','321654987'),('02011','02','What is the formula for calculating the determinant of a 2x2 matrix A?','011','The determinant of a 2x2 matrix A is det(A) = ad - bc','The determinant of a 2x2 matrix A is det(A) = a + b + c + d.','The determinant of a 2x2 matrix A is det(A) = a/b + c/d.','The determinant of a 2x2 matrix A is det(A) = (a - b)(c - d).','Jacob Rosenberg','321654987'),('02012','02','What is the formula for calculating the cross product of two vectors u and v in three dimensions?','012','u × v = (u₂v₃ - u₃v₂)i - (u₁v₃ - u₃v₁)j + (u₁v₂ - u₂v₁)k.','The cross product of vectors u and v in three dimensions is u × v = |u| |v| sinθ.','The cross product of vectors u and v in three dimensions is u × v = u + v.','The cross product of vectors u and v in three dimensions is u × v = u - v.','Jacob Rosenberg','321654987'),('02013','02','What is the formula for calculating the eigenvalues of a square matrix A?','013','The eigenvalues of a square matrix A are found by solving the characteristic equation: det(A - λI) = 0.','The eigenvalues of a square matrix A are eigenvalues(A) = tr(A).','The eigenvalues of a square matrix A are eigenvalues(A) = |A|.','The eigenvalues of a square matrix A are eigenvalues(A) = A^2.','Jacob Rosenberg','321654987'),('02014','02','What is the formula for calculating the inverse of a square matrix A?','014','The inverse of a square matrix A is A⁻¹ = (1/det(A)) adj(A), where det(A) is the determinant of A and adj(A) is the adjugate of A.','The inverse of a square matrix A is A⁻¹ = A^-1.','The inverse of a square matrix A is A⁻¹ = A + I.','The inverse of a square matrix A is A⁻¹ = A^T.','Jacob Rosenberg','321654987'),('02015','02','What is a vector in linear algebra?','015','A vector in linear algebra is an ordered collection of numbers or elements that represents a magnitude and direction in space.','A vector in linear algebra is a matrix with a single row or column.','A vector in linear algebra is a scalar value.','A vector in linear algebra is a function that maps vectors to real numbers.','Sophia Harris','796428315'),('02016','02','What is the dot product of two vectors in linear algebra?','016','The dot product of two vectors in linear algebra is the scalar value obtained by multiplying corresponding components of the vectors and summing them up.','The dot product of two vectors in linear algebra is the cross product of the vectors.','The dot product of two vectors in linear algebra is the determinant of the vectors.','The dot product of two vectors in linear algebra is the sum of the vectors.','Sophia Harris','796428315'),('02017','02','What is a matrix in linear algebra?','017','A matrix in linear algebra is a rectangular array of numbers or elements, arranged in rows and columns.','A matrix in linear algebra is a single number or scalar value.','A matrix in linear algebra is a vector with a single row or column.','A matrix in linear algebra is a function that maps vectors to vectors.','Sophia Harris','796428315'),('02018','02','What is the determinant of a matrix in linear algebra?','018','The determinant of a matrix in linear algebra is a scalar value that represents certain properties of the matrix, such as invertibility and scaling factor.','The determinant of a matrix in linear algebra is the sum of its diagonal elements.','The determinant of a matrix in linear algebra is the product of its diagonal elements.','The determinant of a matrix in linear algebra is the transpose of the matrix.','Sophia Harris','796428315'),('02019','02','What is matrix multiplication in linear algebra?','019','Matrix multiplication in linear algebra is the operation of multiplying two matrices to produce a new matrix. It involves multiplying corresponding elements and summing them up.','Matrix multiplication in linear algebra is the element-wise multiplication of two matrices.','Matrix multiplication in linear algebra is the division of two matrices.','Matrix multiplication in linear algebra is the subtraction of two matrices.','Sophia Harris','796428315'),('02020','02','What is the definition of probability in probability theory?','020','Probability is a numerical measure that quantifies the likelihood of an event occurring.','Probability is a measure of the total number of possible outcomes.','Probability is a measure of the frequency of an event occurring.','Probability is a measure of the certainty of an event occurring.','Sophia Harris','796428315'),('02021','02','What is the difference between independent and dependent events in probability theory?','021','Independent events do not influence each other, while dependent events do.','Independent events always have the same outcome.','Dependent events have no relationship with each other.','Independent events have a higher probability of occurring.','Sophia Harris','796428315'),('02022','02','What is the definition of conditional probability in probability theory?','022','Conditional probability is the probability of an event occurring given another event.','Conditional probability is the probability of two events occurring simultaneously.','Conditional probability is the probability of an event occurring without any conditions.','Conditional probability is the probability of an event occurring in the future.','Sophia Harris','796428315'),('02023','02','What is the union of two events in probability theory?','023','The union of two events is the event that either one or both occur.','The union of two events is the event that neither occur.','The union of two events is the event that both occur simultaneously.','The union of two events is the event that the first occurs before the second.','Sophia Harris','796428315'),('02024','02','What is the complement of an event in probability theory?','024','The complement of an event is the event that it does not occur.','The complement of an event is the event that it occurs more than once.','The complement of an event is the event that it occurs with certainty.','The complement of an event is the event that it is impossible to occur.','Sophia Harris','796428315'),('03001','03','Why is English so important?','001','English facilitates global communication and understanding across diverse cultures.','English is challenging due to its irregular grammar rules and exceptions.','English has a rich literary tradition with influential works.','English\'s significance is tied to its status as the official language of the United States.','Omri Sharof','206391146'),('03003','03','What is the past tense of the verb \"eat\"?','003','Ate','Eaten','Eats','Eat','Omri Sharof','206391146'),('03004','03','What is the opposite of the word \"hot\"?','004','Cold','Warm','Cool','Spicy','Omri Sharof','206391146'),('03005','03','Which word is a pronoun? ','005','He','Table','Walk','Blue','Omri Sharof','206391146'),('03006','03','What is the plural form of the word \"child\"?','006','Children','Childs','Childes','Childern','Omri Sharof','206391146'),('04004','04','What is the equation for calculating work?','004','W = Fd (work is equal to force multiplied by displacement).','W = F + d (work is equal to force plus displacement).','W = F/d (work is equal to force divided by displacement).','W = F^2d (work is equal to force squared multiplied by displacement).','Jason Smith','333444555'),('04005','04','What is the formula for calculating gravitational potential energy?','005','PE = mgh (potential energy is equal to mass multiplied by acceleration due to gravity multiplied by height).','PE = mg/h (potential energy is equal to mass multiplied by acceleration due to gravity divided by height).','PE = mgh^2 (potential energy is equal to mass multiplied by acceleration due to gravity squared multiplied by height).','PE = m + gh (potential energy is equal to mass plus acceleration due to gravity multiplied by height).','Jason Smith','333444555'),('04006','04','What is the equation for calculating power?','006','P = W/t (power is equal to work divided by time).','P = W + t (power is equal to work plus time).','P = W * t (power is equal to work multiplied by time).','P = W - t (power is equal to work minus time).','Jason Smith','333444555'),('04007','04','What is the formula for calculating momentum?','007','p = mv (momentum is equal to mass multiplied by velocity).','p = m + v (momentum is equal to mass plus velocity).','p = m/v (momentum is equal to mass divided by velocity).','p = m^2v (momentum is equal to mass squared multiplied by velocity).','Jason Smith','333444555'),('04008','04','What is the relationship between voltage (V), current (I), and resistance (R) in an electrical circuit?','008','V = IR (voltage is equal to current multiplied by resistance).','V = I + R (voltage is equal to current plus resistance).','V = I/R (voltage is equal to current divided by resistance).','V = I - R (voltage is equal to current minus resistance).','Jason Smith','333444555'),('04009','04','What is the fundamental unit of charge in physics?','009','The fundamental unit of charge in physics is the elementary charge (e).','The fundamental unit of charge in physics is the coulomb (C).','The fundamental unit of charge in physics is the ampere (A).','The fundamental unit of charge in physics is the electronvolt (eV).','Sophia Harris','796428315'),('04010','04','What is Newton\'s first law of motion?','010','Newton\'s first law of motion states that an object at rest stays at rest and an object in motion stays in motion with constant velocity, unless acted upon by an external force.','Newton\'s first law of motion states that force is directly proportional to acceleration.','Newton\'s first law of motion states that velocity is directly proportional to net force.','Newton\'s first law of motion states that momentum is conserved in the absence of external forces.','Sophia Harris','796428315'),('04011','04','What is the law of conservation of energy?','011','The law of conservation of energy states that energy cannot be created or destroyed but can only change form or transfer between objects.','The law of conservation of energy states that energy is directly proportional to object mass.','The law of conservation of energy states that total energy of a closed system remains constant.','The law of conservation of energy states that kinetic energy is conserved during collisions.','Sophia Harris','796428315'),('04012','04','What is the formula for calculating velocity in physics?','012','The formula for calculating velocity in physics is velocity = distance / time.','The formula for calculating velocity in physics is velocity = mass × acceleration.','The formula for calculating velocity in physics is velocity = force / mass.','The formula for calculating velocity in physics is velocity = energy × time.','Sophia Harris','796428315'),('04013','04','What is the relationship between frequency and wavelength in wave physics?','013','Frequency and wavelength in wave physics are inversely proportional. As frequency increases, wavelength decreases, and vice versa.','Frequency and wavelength in wave physics are directly proportional.','Frequency and wavelength in wave physics are not related to each other.','Frequency and wavelength in wave physics have a linear relationship.','Sophia Harris','796428315'),('04014','04','What is the concept of superposition in quantum physics?','014','Superposition is the ability of quantum systems to exist in multiple states simultaneously.','Superposition refers to the collapse of a wavefunction into a single state.','Superposition refers to the instantaneous transfer of information between particles.','Superposition refers to the complete randomness of quantum systems.','Elena Aharon','864297531'),('04015','04','What is the Heisenberg uncertainty principle in quantum physics?','015','The Heisenberg uncertainty principle states that there is a limit to the precision with which certain pairs of properties can be known simultaneously.','The Heisenberg uncertainty principle states that all physical properties of a quantum system are fixed and deterministic.','The Heisenberg uncertainty principle states that particles have fixed and well-defined positions and momenta.','The Heisenberg uncertainty principle states that the behavior of quantum systems is completely predictable.','Elena Aharon','864297531'),('04016','04','What is quantum entanglement in quantum physics?','016','Quantum entanglement is the correlation between particles, where the state of one particle cannot be described independently of the other.','Quantum entanglement refers to the separation of particles into distinct energy levels.','Quantum entanglement refers to the decay of unstable particles.','Quantum entanglement refers to the interaction between classical and quantum systems.','Elena Aharon','864297531'),('04017','04','What is the wave-particle duality in quantum physics?','017','Wave-particle duality is the concept that particles can exhibit both wave-like and particle-like properties.','Wave-particle duality refers to the transformation of waves into particles.','Wave-particle duality refers to the complete separation of waves and particles in quantum systems.','Wave-particle duality refers to the inability to observe both wave and particle properties simultaneously.','Elena Aharon','864297531'),('04018','04','What is quantum tunneling in quantum physics?','018','Quantum tunneling is the phenomenon where particles can pass through energy barriers, even without enough energy to overcome them classically.','Quantum tunneling refers to the confinement of particles within a well-defined region.','Quantum tunneling refers to the reflection of particles off potential energy barriers.','Quantum tunneling refers to the predictable behavior of particles within a potential energy field.','Elena Aharon','864297531');
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
INSERT INTO `questioncourse` VALUES ('01001','03'),('01002','03'),('01003','01'),('01004','02'),('01005','05'),('01006','01'),('01006','02'),('01006','03'),('01008','01'),('01008','02'),('01008','03'),('01008','05'),('01010','01'),('01010','02'),('01010','03'),('01010','05'),('01011','03'),('01012','03'),('01013','03'),('01014','01'),('01015','01'),('01016','01'),('01017','01'),('01018','02'),('01019','02'),('01020','02'),('01021','02'),('01022','05'),('01023','05'),('01024','05'),('01025','05'),('01026','13'),('01027','13'),('01028','13'),('01029','13'),('01030','13'),('01031','12'),('01032','12'),('01033','12'),('01034','12'),('01035','12'),('02001','06'),('02002','06'),('02003','06'),('02004','06'),('02005','08'),('02006','08'),('02007','08'),('02008','08'),('02009','08'),('02010','10'),('02011','10'),('02012','10'),('02013','10'),('02014','10'),('02015','10'),('02016','10'),('02017','10'),('02018','10'),('02019','10'),('02020','15'),('02021','15'),('02022','15'),('02023','15'),('02024','15'),('03001','04'),('03003','04'),('03004','04'),('03005','04'),('03006','04'),('04004','07'),('04005','07'),('04006','07'),('04007','07'),('04008','07'),('04009','09'),('04010','09'),('04011','09'),('04012','09'),('04013','09'),('04014','11'),('04015','11'),('04016','11'),('04017','11'),('04018','11');
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
INSERT INTO `questionsexam` VALUES ('01001','010304','What is the correct syntax for a loop in Python?','i++','while(i != 1)','for i in range(10)','if(i==1)','20.0'),('01001','010305','What is the correct syntax for a loop in Python?.','for i in range(10)','while(i != 1)','i++','if(i==1)','20.0'),('01002','010304','What is the correct syntax for a pointer? ','x = *10','x = 10','*x = 10','x = &10','20.0'),('01002','010305','What is the correct syntax for a pointer? ','x = 10','x = *10','*x = 10','x = &10','20.0'),('01003','010104','What is the difference between a stack and a queue?','A stack is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.','A stack is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed. Elements are added and removed from only one end of the stack.','A queue is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed.','A queue is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.','20.0'),('01003','010105','What is the difference between a stack and a queue?','A stack is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed. Elements are added and removed from only one end of the stack.','A stack is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.','A queue is a data structure that follows the Last-In-First-Out (LIFO) principle, where the last element added is the first one to be removed.','A queue is a data structure that follows the First-In-First-Out (FIFO) principle, where the first element added is the first one to be removed.','20.0'),('01004','010203','What is the time complexity of binary search?','O(n^2)','O(n)','O(1)','O(log n)','20.0'),('01004','010204','What is the time complexity of binary search?','O(log n)','O(n)','O(n^2)','O(1)','20.0'),('01005','010501','What is the difference between pass by value and pass by reference?','Pass by value: Memory address passed, modifications affect original.','Pass by value: Value copied, no modification affects original.','Pass by reference: Value copied, modifications affect original.','Pass by reference: Memory address passed, no modification affects original.','40.0'),('01005','010502','What is the difference between pass by value and pass by reference?','Pass by value: Value copied, no modification affects original.','Pass by value: Memory address passed, modifications affect original.','Pass by reference: Value copied, modifications affect original.','Pass by reference: Memory address passed, no modification affects original.','20.0'),('01011','010304','Which keyword is used to define a function in Python?','for','def','if','while','20.0'),('01011','010305','Which keyword is used to define a function in Python?','def','for','if','while','20.0'),('01012','010304','How do you check the length of a list in Python?','length()','size()','count()','len()','20.0'),('01012','010305','How do you check the length of a list in Python?','len()','size()','count()','length()','20.0'),('01013','010304','Which data type is used to store a sequence of characters in Python?','float','int','str','bool','20.0'),('01013','010305','Which data type is used to store a sequence of characters in Python?','str','int','float','bool','20.0'),('01014','010104','In Java, what is the difference between an abstract class and an interface?','An abstract class cannot have any methods.','An abstract class can have method implementations and member variables, while an interface only has method signatures (without implementations) and constants.','an interface can have member variables.','an abstract class and an interface are the same.','30.0'),('01014','010105','In Java, what is the difference between an abstract class and an interface?','An abstract class can have method implementations and member variables, while an interface only has method signatures (without implementations) and constants.','An abstract class cannot have any methods.','an interface can have member variables.','an abstract class and an interface are the same.','20.0'),('01015','010104','How can you achieve multiple inheritance in Java?',' By extending multiple classes.','By implementing multiple interfaces.','by using the \'implements\' keyword.','by using the \'extend\' keyword.','20.0'),('01015','010105','How can you achieve multiple inheritance in Java?','By implementing multiple interfaces.',' By extending multiple classes.','by using the \'implements\' keyword.','by using the \'extend\' keyword.','20.0'),('01016','010104','What is the purpose of the \'static\' keyword in Java?','The \'static\' keyword is used to prevent inheritance.','The \'static\' keyword is used to define variables and methods that belong to the class itself, rather than instances of the class.','the \'static\' keyword is used to make a class immutable.','the \'static\' keyword is used to define variables and methods that can only be accessed within the same package.','10.0'),('01016','010105','What is the purpose of the \'static\' keyword in Java?','The \'static\' keyword is used to define variables and methods that belong to the class itself, rather than instances of the class.','The \'static\' keyword is used to prevent inheritance.','the \'static\' keyword is used to make a class immutable.','the \'static\' keyword is used to define variables and methods that can only be accessed within the same package.','20.0'),('01017','010104','What is the difference between method overloading and method overriding in Java?','Method overloading occurs when a subclass provides a different implementation of a method.','Method overloading occurs when a method has multiple return types, while method overriding occurs when a method has different access modifiers.','Same name, different parameters.','while method overriding occurs when multiple methods in the same class have the same name.','20.0'),('01017','010105','What is the difference between method overloading and method overriding in Java?','Same name, different parameters.','Method overloading occurs when a method has multiple return types, while method overriding occurs when a method has different access modifiers.','Method overloading occurs when a subclass provides a different implementation of a method.','while method overriding occurs when multiple methods in the same class have the same name.','20.0'),('01018','010203','What is the time complexity of inserting an element into an ArrayList in Java?','O(log n) (logarithmic time)','O(n) (linear time)','O(1) (constant time)','O(n^2) (quadratic time)','20.0'),('01018','010204','What is the time complexity of inserting an element into an ArrayList in Java?','O(1) (constant time)','O(log n) (logarithmic time)','O(n) (linear time)','O(n^2) (quadratic time)','20.0'),('01019','010203','Which data structure follows the Last-In-First-Out (LIFO) principle?','LinkedList','Queue','HashMap','Stack','20.0'),('01019','010204','Which data structure follows the Last-In-First-Out (LIFO) principle?','Stack','Queue','LinkedList','HashMap','20.0'),('01020','010203','What is the worst-case time complexity of searching for an element in a binary search tree (BST)?','O(n) (linear time)','O(log n) (logarithmic time)','O(1) (constant time)','O(n^2) (quadratic time)','20.0'),('01020','010204','What is the worst-case time complexity of searching for an element in a binary search tree (BST)?','O(log n) (logarithmic time)','O(1) (constant time)','O(n) (linear time)','O(n^2) (quadratic time)','20.0'),('01021','010203','What sorting algorithm has an average time complexity of O(n log n)?','Selection Sort','Merge Sort','Insertion Sort','Bubble Sort','20.0'),('01021','010204','What sorting algorithm has an average time complexity of O(n log n)?','Merge Sort','Bubble Sort','Insertion Sort','Selection Sort','20.0'),('01022','010501','What is the difference between the pre-increment (++x) and post-increment (x++) operators in C++?','they both increment the value before it is used.','They have the same effect.',' they both increment the value after it is used.','The pre-increment operator increments the value of a variable before it is used, while the post-increment operator increments the value after it is used.','10.0'),('01022','010502','What is the difference between the pre-increment (++x) and post-increment (x++) operators in C++?','The pre-increment operator increments the value of a variable before it is used, while the post-increment operator increments the value after it is used.','They have the same effect.',' they both increment the value after it is used.','they both increment the value before it is used.','20.0'),('01023','010501','What is the purpose of the \'new\' keyword in C++?','it is used to define a new variable.','t is used to deallocate memory.','The \'new\' keyword is used to dynamically allocate memory for an object at runtime.','it is used to initialize an object.','10.0'),('01023','010502','What is the purpose of the \'new\' keyword in C++?','The \'new\' keyword is used to dynamically allocate memory for an object at runtime.','t is used to deallocate memory.','it is used to define a new variable.','it is used to initialize an object.','20.0'),('01024','010501','How do you define a constant variable in C++?','by using the \'final\' keyword.',' By using the \'constant\' keyword.','by using the \'var\' keyword.','By using the \'const\' keyword before the variable declaration.','10.0'),('01024','010502','How do you define a constant variable in C++?','By using the \'const\' keyword before the variable declaration.',' By using the \'constant\' keyword.','by using the \'var\' keyword.','by using the \'final\' keyword.','20.0'),('01025','010501','What is the difference between \'cout\' and \'cin\' in C++?','\'cin\' is used to output data.',' They are used interchangeably',' \'cout\' is used to read input data','\'cout\' is used to output data to the console, while \'cin\' is used to read input data from the console.','30.0'),('01025','010502','What is the difference between \'cout\' and \'cin\' in C++?','\'cout\' is used to output data to the console, while \'cin\' is used to read input data from the console.',' They are used interchangeably',' \'cout\' is used to read input data','\'cin\' is used to output data.','20.0'),('01026','011301','What is the purpose of training and testing data in machine learning?','The purpose of training and testing data in machine learning is to calculate the loss function.','The purpose of training and testing data in machine learning is to increase the model complexity.','The purpose of training and testing data in machine learning is to determine the accuracy of input features.','The purpose of training and testing data in machine learning is to evaluate the model\'s performance and generalization ability.','20.0'),('01026','011302','What is the purpose of training and testing data in machine learning?','The purpose of training and testing data in machine learning is to evaluate the model\'s performance and generalization ability.','The purpose of training and testing data in machine learning is to increase the model complexity.','The purpose of training and testing data in machine learning is to determine the accuracy of input features.','The purpose of training and testing data in machine learning is to calculate the loss function.','20.0'),('01027','011301','What is overfitting in machine learning?','Overfitting in machine learning occurs when the model has too few training examples.','Overfitting in machine learning occurs when a model performs poorly on the training data.','Overfitting in machine learning occurs when the model is too simple.','Overfitting in machine learning occurs when a model performs well on the training data but fails to generalize to new data.','20.0'),('01027','011302','What is overfitting in machine learning?','Overfitting in machine learning occurs when a model performs well on the training data but fails to generalize to new data.','Overfitting in machine learning occurs when a model performs poorly on the training data.','Overfitting in machine learning occurs when the model is too simple.','Overfitting in machine learning occurs when the model has too few training examples.','20.0'),('01028','011301','What is the purpose of regularization in machine learning?','Regularization in machine learning makes the model more sensitive to noise.','Regularization in machine learning increases the model complexity.','Regularization in machine learning decreases the model\'s ability to generalize.','Regularization in machine learning prevents overfitting by adding a penalty term to the loss function.','20.0'),('01028','011302','What is the purpose of regularization in machine learning?','Regularization in machine learning prevents overfitting by adding a penalty term to the loss function.','Regularization in machine learning increases the model complexity.','Regularization in machine learning decreases the model\'s ability to generalize.','Regularization in machine learning makes the model more sensitive to noise.','20.0'),('01029','011301','What is the difference between supervised and unsupervised learning?','The difference between supervised and unsupervised learning lies in the use of feature extraction techniques.','Supervised learning is for classification tasks, while unsupervised learning is for regression tasks.','Supervised learning requires fewer computational resources than unsupervised learning.','Supervised learning uses labeled data, while unsupervised learning uses unlabeled data.','20.0'),('01029','011302','What is the difference between supervised and unsupervised learning?','Supervised learning uses labeled data, while unsupervised learning uses unlabeled data.','Supervised learning is for classification tasks, while unsupervised learning is for regression tasks.','Supervised learning requires fewer computational resources than unsupervised learning.','The difference between supervised and unsupervised learning lies in the use of feature extraction techniques.','20.0'),('01030','011301','What is the purpose of cross-validation in machine learning?','Cross-validation in machine learning increases the complexity of the model.','Cross-validation in machine learning assesses the performance and generalization ability of a model using multiple subsets of data.','Cross-validation in machine learning tunes the hyperparameters of the model.','Cross-validation in machine learning minimizes the training time of the model.','20.0'),('01030','011302','What is the purpose of cross-validation in machine learning?','Cross-validation in machine learning assesses the performance and generalization ability of a model using multiple subsets of data.','Cross-validation in machine learning increases the complexity of the model.','Cross-validation in machine learning tunes the hyperparameters of the model.','Cross-validation in machine learning minimizes the training time of the model.','20.0'),('01031','011201','What is the difference between HTML and CSS?','HTML is for styling, while CSS is for structuring content.','HTML and CSS are both programming languages.','HTML is for structuring web content, while CSS is for styling and formatting.','HTML and CSS can be used interchangeably.','20.0'),('01031','011202','What is the difference between HTML and CSS?','HTML is for structuring web content, while CSS is for styling and formatting.','HTML and CSS are both programming languages.','HTML is for styling, while CSS is for structuring content.','HTML and CSS can be used interchangeably.','20.0'),('01032','011201','What is the purpose of JavaScript in web development?','JavaScript is primarily used for server-side processing.','JavaScript adds interactivity and dynamic behavior to web pages.','JavaScript defines the structure of web pages.','JavaScript creates databases in web development.','20.0'),('01032','011202','What is the purpose of JavaScript in web development?','JavaScript adds interactivity and dynamic behavior to web pages.','JavaScript is primarily used for server-side processing.','JavaScript defines the structure of web pages.','JavaScript creates databases in web development.','20.0'),('01033','011201','What is the role of a server in web development?','A server creates web page layouts.','A server hosts web applications and processes requests from clients.','A server is used for client-side scripting.','A server is only required for static web pages.','20.0'),('01033','011202','What is the role of a server in web development?','A server hosts web applications and processes requests from clients.','A server creates web page layouts.','A server is used for client-side scripting.','A server is only required for static web pages.','20.0'),('01034','011201','What is the purpose of a database in web development?','A database handles user interactions.','A database is used for styling web pages.','A database stores and manages structured data for web applications.','A database is only necessary for static web pages.','20.0'),('01034','011202','What is the purpose of a database in web development?','A database stores and manages structured data for web applications.','A database is used for styling web pages.','A database handles user interactions.','A database is only necessary for static web pages.','20.0'),('01035','011201','What is the difference between front-end and back-end development?','Back-end is only concerned with visual elements.','Front-end is responsible for server-side processing.','Front-end focuses on the client-side, while back-end handles server-side operations.','Front-end and back-end are interchangeable terms.','20.0'),('01035','011202','What is the difference between front-end and back-end development?','Front-end focuses on the client-side, while back-end handles server-side operations.','Front-end is responsible for server-side processing.','Back-end is only concerned with visual elements.','Front-end and back-end are interchangeable terms.','20.0'),('02001','020601','If a set B has n elements, then what is the total number of subsets of B?','n','n!','n^2','2^n','30.0'),('02001','020602','If a set B has n elements, then what is the total number of subsets of B?','2^n','n!','n^2','n','30.0'),('02002','020601','What is the negation of the statement \"For all x, P(x)\"?','P(x) is true for all x','There does not exist an x for which P(x)','For some x, P(x)','There exists an x for which not P(x)','20.0'),('02002','020602','What is the negation of the statement \"For all x, P(x)\"?','There exists an x for which not P(x)','There does not exist an x for which P(x)','For some x, P(x)','P(x) is true for all x','20.0'),('02003','020601','What is the result of the logical operation (p ∧ q) ∨ ¬q, given that p is true and q is false?','False','True','Undefined','Depends on other factors','30.0'),('02003','020602','What is the result of the logical operation (p ∧ q) ∨ ¬q, given that p is true and q is false?','True','False','Undefined','Depends on other factors','30.0'),('02004','020601','In combinatorics, how many ways can you arrange n distinct objects in a row?','n+1','n^n','2^n','n!','20.0'),('02004','020602','In combinatorics, how many ways can you arrange n distinct objects in a row?','n!','n^n','2^n','n+1','20.0'),('02005','020801','What is the derivative of f(x) = 3x^2 + 2x - 1?','The derivative of f(x) is f\'(x) = 3x + 2.','The derivative of f(x) is f\'(x) = 9x + 2.','The derivative of f(x) is f\'(x) = 3x^3 + 2.','The derivative of f(x) is f\'(x) = 6x + 2.','20.0'),('02005','020802','What is the derivative of f(x) = 3x^2 + 2x - 1?','The derivative of f(x) is f\'(x) = 6x + 2.','The derivative of f(x) is f\'(x) = 9x + 2.','The derivative of f(x) is f\'(x) = 3x^3 + 2.','The derivative of f(x) is f\'(x) = 3x + 2.','20.0'),('02006','020801','Find the indefinite integral of g(x) = 2x^3 - 5x^2 + 4x + 7.','The indefinite integral of g(x) is G(x) = 2x^4 - 5x^3 + 2x^2 + 7x.','The indefinite integral of g(x) is G(x) = 6x^3 - 5x^2 + 2x + 7.','The indefinite integral of g(x) is G(x) = x^4 - x^2 + 4x + 7.','The indefinite integral of g(x) is G(x) = (1/2)x^4 - (5/3)x^3 + 2x^2 + 7x.','20.0'),('02006','020802','Find the indefinite integral of g(x) = 2x^3 - 5x^2 + 4x + 7.','The indefinite integral of g(x) is G(x) = (1/2)x^4 - (5/3)x^3 + 2x^2 + 7x.','The indefinite integral of g(x) is G(x) = 6x^3 - 5x^2 + 2x + 7.','The indefinite integral of g(x) is G(x) = x^4 - x^2 + 4x + 7.','The indefinite integral of g(x) is G(x) = 2x^4 - 5x^3 + 2x^2 + 7x.','20.0'),('02007','020801','Determine the limit of h(x) as x approaches 2, where h(x) = (x^2 - 4)/(x - 2).','The limit of h(x) as x approaches 2 is 0.','The limit of h(x) as x approaches 2 is 4.','The limit of h(x) as x approaches 2 is 2.','The limit of h(x) as x approaches 2 is undefined.','20.0'),('02007','020802','Determine the limit of h(x) as x approaches 2, where h(x) = (x^2 - 4)/(x - 2).','The limit of h(x) as x approaches 2 is 4.','The limit of h(x) as x approaches 2 is 0.','The limit of h(x) as x approaches 2 is 2.','The limit of h(x) as x approaches 2 is undefined.','20.0'),('02008','020801','Find the critical points of the function f(x) = x^3 - 12x + 1.','The critical point of f(x) is x = 0.','The critical points of f(x) are x = -√3 and x = √3.','The critical point of f(x) is x = -1.','The critical point of f(x) is x = 2.','20.0'),('02008','020802','Find the critical points of the function f(x) = x^3 - 12x + 1.','The critical points of f(x) are x = -√3 and x = √3.','The critical point of f(x) is x = 0.','The critical point of f(x) is x = -1.','The critical point of f(x) is x = 2.','20.0'),('02009','020801','Evaluate the definite integral of f(x) = 2x over the interval [0, 3].','The definite integral of f(x) over the interval [0, 3] is 6.','The definite integral of f(x) over the interval [0, 3] is 9.','The definite integral of f(x) over the interval [0, 3] is 8.','The definite integral of f(x) over the interval [0, 3] is 12.','20.0'),('02009','020802','Evaluate the definite integral of f(x) = 2x over the interval [0, 3].','The definite integral of f(x) over the interval [0, 3] is 9.','The definite integral of f(x) over the interval [0, 3] is 6.','The definite integral of f(x) over the interval [0, 3] is 8.','The definite integral of f(x) over the interval [0, 3] is 12.','20.0'),('02010','021001','What is the formula for calculating the dot product of two vectors u and v?','The dot product of vectors u and v is u · v = |u| |v| cosθ.','The dot product of vectors u and v is u · v = u₁v₁ + u₂v₂ + ... + uₙvₙ.','The dot product of vectors u and v is u · v = u + v.','The dot product of vectors u and v is u · v = u - v.','20.0'),('02010','021002','What is the formula for calculating the dot product of two vectors u and v?','The dot product of vectors u and v is u · v = |u| |v| cosθ.','The dot product of vectors u and v is u · v = u₁v₁ + u₂v₂ + ... + uₙvₙ.','The dot product of vectors u and v is u · v = u + v.','The dot product of vectors u and v is u · v = u - v.','20.0'),('02011','021001','What is the formula for calculating the determinant of a 2x2 matrix A?','The determinant of a 2x2 matrix A is det(A) = a/b + c/d.','The determinant of a 2x2 matrix A is det(A) = a + b + c + d.','The determinant of a 2x2 matrix A is det(A) = ad - bc','The determinant of a 2x2 matrix A is det(A) = (a - b)(c - d).','20.0'),('02011','021002','What is the formula for calculating the determinant of a 2x2 matrix A?','The determinant of a 2x2 matrix A is det(A) = a + b + c + d.','The determinant of a 2x2 matrix A is det(A) = ad - bc','The determinant of a 2x2 matrix A is det(A) = a/b + c/d.','The determinant of a 2x2 matrix A is det(A) = (a - b)(c - d).','20.0'),('02012','021001','What is the formula for calculating the cross product of two vectors u and v in three dimensions?','The cross product of vectors u and v in three dimensions is u × v = u + v.','The cross product of vectors u and v in three dimensions is u × v = |u| |v| sinθ.','u × v = (u₂v₃ - u₃v₂)i - (u₁v₃ - u₃v₁)j + (u₁v₂ - u₂v₁)k.','The cross product of vectors u and v in three dimensions is u × v = u - v.','20.0'),('02013','021001','What is the formula for calculating the eigenvalues of a square matrix A?','The eigenvalues of a square matrix A are eigenvalues(A) = tr(A).','The eigenvalues of a square matrix A are found by solving the characteristic equation: det(A - λI) = 0.','The eigenvalues of a square matrix A are eigenvalues(A) = |A|.','The eigenvalues of a square matrix A are eigenvalues(A) = A^2.','20.0'),('02014','021001','What is the formula for calculating the inverse of a square matrix A?','The inverse of a square matrix A is A⁻¹ = A^T.','The inverse of a square matrix A is A⁻¹ = A^-1.','The inverse of a square matrix A is A⁻¹ = A + I.','The inverse of a square matrix A is A⁻¹ = (1/det(A)) adj(A), where det(A) is the determinant of A and adj(A) is the adjugate of A.','20.0'),('02016','021002','What is the dot product of two vectors in linear algebra?','The dot product of two vectors in linear algebra is the cross product of the vectors.','The dot product of two vectors in linear algebra is the scalar value obtained by multiplying corresponding components of the vectors and summing them up.','The dot product of two vectors in linear algebra is the determinant of the vectors.','The dot product of two vectors in linear algebra is the sum of the vectors.','20.0'),('02017','021002','What is a matrix in linear algebra?','A matrix in linear algebra is a single number or scalar value.','A matrix in linear algebra is a rectangular array of numbers or elements, arranged in rows and columns.','A matrix in linear algebra is a vector with a single row or column.','A matrix in linear algebra is a function that maps vectors to vectors.','20.0'),('02019','021002','What is matrix multiplication in linear algebra?','Matrix multiplication in linear algebra is the division of two matrices.','Matrix multiplication in linear algebra is the element-wise multiplication of two matrices.','Matrix multiplication in linear algebra is the operation of multiplying two matrices to produce a new matrix. It involves multiplying corresponding elements and summing them up.','Matrix multiplication in linear algebra is the subtraction of two matrices.','20.0'),('02020','021501','What is the definition of probability in probability theory?','Probability is a measure of the frequency of an event occurring.','Probability is a measure of the total number of possible outcomes.','Probability is a numerical measure that quantifies the likelihood of an event occurring.','Probability is a measure of the certainty of an event occurring.','20.0'),('02021','021501','What is the difference between independent and dependent events in probability theory?','Dependent events have no relationship with each other.','Independent events always have the same outcome.','Independent events do not influence each other, while dependent events do.','Independent events have a higher probability of occurring.','20.0'),('02022','021501','What is the definition of conditional probability in probability theory?','Conditional probability is the probability of an event occurring without any conditions.','Conditional probability is the probability of two events occurring simultaneously.','Conditional probability is the probability of an event occurring given another event.','Conditional probability is the probability of an event occurring in the future.','20.0'),('02023','021501','What is the union of two events in probability theory?','The union of two events is the event that the first occurs before the second.','The union of two events is the event that neither occur.','The union of two events is the event that both occur simultaneously.','The union of two events is the event that either one or both occur.','20.0'),('02024','021501','What is the complement of an event in probability theory?','The complement of an event is the event that it occurs more than once.','The complement of an event is the event that it does not occur.','The complement of an event is the event that it occurs with certainty.','The complement of an event is the event that it is impossible to occur.','20.0'),('03001','030404','Why is English so important?','English is challenging due to its irregular grammar rules and exceptions.','English facilitates global communication and understanding across diverse cultures.','English has a rich literary tradition with influential works.','English\'s significance is tied to its status as the official language of the United States.','20.0'),('03001','030405','Why is English so important?','English facilitates global communication and understanding across diverse cultures.','English is challenging due to its irregular grammar rules and exceptions.','English has a rich literary tradition with influential works.','English\'s significance is tied to its status as the official language of the United States.','40.0'),('03003','030404','What is the past tense of the verb \"eat\"?','Eats','Eaten','Ate','Eat','20.0'),('03003','030405','What is the past tense of the verb \"eat\"?','Ate','Eaten','Eats','Eat','20.0'),('03004','030404','What is the opposite of the word \"hot\"?','Cool','Warm','Cold','Spicy','20.0'),('03004','030405','What is the opposite of the word \"hot\"?','Cold','Warm','Cool','Spicy','10.0'),('03005','030404','Which word is a pronoun? ','Blue','Table','Walk','He','20.0'),('03005','030405','Which word is a pronoun? ','He','Table','Walk','Blue','20.0'),('03006','030404','What is the plural form of the word \"child\"?','Childs','Children','Childes','Childern','20.0'),('03006','030405','What is the plural form of the word \"child\"?','Children','Childs','Childes','Childern','10.0'),('04004','040702','What is the equation for calculating work?','W = F + d (work is equal to force plus displacement).','W = Fd (work is equal to force multiplied by displacement).','W = F/d (work is equal to force divided by displacement).','W = F^2d (work is equal to force squared multiplied by displacement).','20.0'),('04005','040702','What is the formula for calculating gravitational potential energy?','PE = m + gh (potential energy is equal to mass plus acceleration due to gravity multiplied by height).','PE = mg/h (potential energy is equal to mass multiplied by acceleration due to gravity divided by height).','PE = mgh^2 (potential energy is equal to mass multiplied by acceleration due to gravity squared multiplied by height).','PE = mgh (potential energy is equal to mass multiplied by acceleration due to gravity multiplied by height).','20.0'),('04006','040702','What is the equation for calculating power?','P = W + t (power is equal to work plus time).','P = W/t (power is equal to work divided by time).','P = W * t (power is equal to work multiplied by time).','P = W - t (power is equal to work minus time).','20.0'),('04007','040702','What is the formula for calculating momentum?','p = m^2v (momentum is equal to mass squared multiplied by velocity).','p = m + v (momentum is equal to mass plus velocity).','p = m/v (momentum is equal to mass divided by velocity).','p = mv (momentum is equal to mass multiplied by velocity).','20.0'),('04008','040702','What is the relationship between voltage (V), current (I), and resistance (R) in an electrical circuit?','V = I + R (voltage is equal to current plus resistance).','V = IR (voltage is equal to current multiplied by resistance).','V = I/R (voltage is equal to current divided by resistance).','V = I - R (voltage is equal to current minus resistance).','20.0'),('04009','040901','What is the fundamental unit of charge in physics?','The fundamental unit of charge in physics is the coulomb (C).','The fundamental unit of charge in physics is the elementary charge (e).','The fundamental unit of charge in physics is the ampere (A).','The fundamental unit of charge in physics is the electronvolt (eV).','20.0'),('04010','040901','What is Newton\'s first law of motion?','Newton\'s first law of motion states that force is directly proportional to acceleration.','Newton\'s first law of motion states that an object at rest stays at rest and an object in motion stays in motion with constant velocity, unless acted upon by an external force.','Newton\'s first law of motion states that velocity is directly proportional to net force.','Newton\'s first law of motion states that momentum is conserved in the absence of external forces.','20.0'),('04011','040901','What is the law of conservation of energy?','The law of conservation of energy states that energy is directly proportional to object mass.','The law of conservation of energy states that energy cannot be created or destroyed but can only change form or transfer between objects.','The law of conservation of energy states that total energy of a closed system remains constant.','The law of conservation of energy states that kinetic energy is conserved during collisions.','20.0'),('04012','040901','What is the formula for calculating velocity in physics?','The formula for calculating velocity in physics is velocity = energy × time.','The formula for calculating velocity in physics is velocity = mass × acceleration.','The formula for calculating velocity in physics is velocity = force / mass.','The formula for calculating velocity in physics is velocity = distance / time.','20.0'),('04013','040901','What is the relationship between frequency and wavelength in wave physics?','Frequency and wavelength in wave physics have a linear relationship.','Frequency and wavelength in wave physics are directly proportional.','Frequency and wavelength in wave physics are not related to each other.','Frequency and wavelength in wave physics are inversely proportional. As frequency increases, wavelength decreases, and vice versa.','20.0'),('04014','041101','What is the concept of superposition in quantum physics?','Superposition refers to the collapse of a wavefunction into a single state.','Superposition is the ability of quantum systems to exist in multiple states simultaneously.','Superposition refers to the instantaneous transfer of information between particles.','Superposition refers to the complete randomness of quantum systems.','20.0'),('04015','041101','What is the Heisenberg uncertainty principle in quantum physics?','The Heisenberg uncertainty principle states that the behavior of quantum systems is completely predictable.','The Heisenberg uncertainty principle states that all physical properties of a quantum system are fixed and deterministic.','The Heisenberg uncertainty principle states that particles have fixed and well-defined positions and momenta.','The Heisenberg uncertainty principle states that there is a limit to the precision with which certain pairs of properties can be known simultaneously.','20.0'),('04016','041101','What is quantum entanglement in quantum physics?','Quantum entanglement refers to the interaction between classical and quantum systems.','Quantum entanglement refers to the separation of particles into distinct energy levels.','Quantum entanglement refers to the decay of unstable particles.','Quantum entanglement is the correlation between particles, where the state of one particle cannot be described independently of the other.','20.0'),('04017','041101','What is the wave-particle duality in quantum physics?','Wave-particle duality refers to the transformation of waves into particles.','Wave-particle duality is the concept that particles can exhibit both wave-like and particle-like properties.','Wave-particle duality refers to the complete separation of waves and particles in quantum systems.','Wave-particle duality refers to the inability to observe both wave and particle properties simultaneously.','20.0'),('04018','041101','What is quantum tunneling in quantum physics?','Quantum tunneling refers to the predictable behavior of particles within a potential energy field.','Quantum tunneling refers to the confinement of particles within a well-defined region.','Quantum tunneling refers to the reflection of particles off potential energy barriers.','Quantum tunneling is the phenomenon where particles can pass through energy barriers, even without enough energy to overcome them classically.','20.0');
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
INSERT INTO `student` VALUES ('098765432','Boris.Shostin','123123','Boris Shostin','Boris.Shostin@e.braude.ac.il','Software Engineering','2',0),('123456789','Alon.Ternerider','112233','Alon Ternerider','Alon.Ternerider@e.braude.ac.il','Software Engineering','2',0),('206392246','Aleksander.Pitkin','123456','Aleksander Pitkin','Aleksander.Pitkin@e.braude.ac.il','Information Systems Engineering','1',0),('316350768','Nadav.Goldin','123456','Nadav Goldin','Nadav.Goldin@e.braude.ac.il','Software Engineering','2',0),('456765422','Abed.Zuzu','123456','Abed Zuzu','Abed.Zuzu@e.braude.ac.il','Information Systems Engineering','1',0),('456789123','Ilya.Vor','778899','Ilya Vor','Ilya.Vor@e.braude.ac.il','Software Engineering','2',0),('876543211','Tom.B','345345','Tom B','Tom.B@e.braude.ac.il','Mechanical Engineering','4',0),('987654321','Kfir.Sharoni','445566','Kfir Sharoni','Kfir.Sharoni@e.braude.ac.il','Information Systems Engineering','1',0);
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
INSERT INTO `subjects` VALUES ('01','Coding','035'),('02','Math','024'),('03','Language','006'),('04','Physics','018');
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

-- Dump completed on 2023-06-19 10:56:25
