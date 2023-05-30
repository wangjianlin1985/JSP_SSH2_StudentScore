/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : selectcoursedb

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-04-02 21:38:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `classinfo`
-- ----------------------------
DROP TABLE IF EXISTS `classinfo`;
CREATE TABLE `classinfo` (
  `classNumber` varchar(20) NOT NULL,
  `className` varchar(20) default NULL,
  `classSpecialFieldNumber` varchar(20) default NULL,
  `classBirthDate` datetime default NULL,
  `classTeacherCharge` varchar(12) default NULL,
  `classTelephone` varchar(20) default NULL,
  `classMemo` varchar(100) default NULL,
  PRIMARY KEY  (`classNumber`),
  KEY `FK76D92626A924E03B` (`classSpecialFieldNumber`),
  CONSTRAINT `FK76D92626A924E03B` FOREIGN KEY (`classSpecialFieldNumber`) REFERENCES `specialfieldinfo` (`specialFieldNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classinfo
-- ----------------------------
INSERT INTO `classinfo` VALUES ('2017050303', '2017级计算机3班', '0503', '2017-08-31 00:00:00', '宋大侠', '13839431943', '测试班级');
INSERT INTO `classinfo` VALUES ('2017050304', '2017级计算机4班', '0503', '2017-09-13 00:00:00', '刘实', '13859343502', '测试班级');
INSERT INTO `classinfo` VALUES ('2017050403', '2017级电子技术3班', '0504', '2017-09-13 00:00:00', '王德阳', '15394213432', '测试班级');

-- ----------------------------
-- Table structure for `collegeinfo`
-- ----------------------------
DROP TABLE IF EXISTS `collegeinfo`;
CREATE TABLE `collegeinfo` (
  `collegeNumber` varchar(20) NOT NULL,
  `collegeName` varchar(20) default NULL,
  `collegeBirthDate` datetime default NULL,
  `collegeMan` varchar(10) default NULL,
  `collegeTelephone` varchar(20) default NULL,
  `collegeMemo` varchar(100) default NULL,
  PRIMARY KEY  (`collegeNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collegeinfo
-- ----------------------------
INSERT INTO `collegeinfo` VALUES ('05', '信息工程学院', '2017-12-08 00:00:00', '高飞', '13583949833', '测试学院');

-- ----------------------------
-- Table structure for `courseinfo`
-- ----------------------------
DROP TABLE IF EXISTS `courseinfo`;
CREATE TABLE `courseinfo` (
  `courseNumber` varchar(20) NOT NULL,
  `courseName` varchar(20) default NULL,
  `courseTeacher` varchar(20) default NULL,
  `courseTime` varchar(40) default NULL,
  `coursePlace` varchar(40) default NULL,
  `courseScore` float default NULL,
  `courseMemo` varchar(100) default NULL,
  PRIMARY KEY  (`courseNumber`),
  KEY `FKB440F89E14459BF` (`courseTeacher`),
  CONSTRAINT `FKB440F89E14459BF` FOREIGN KEY (`courseTeacher`) REFERENCES `teacher` (`teacherNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of courseinfo
-- ----------------------------
INSERT INTO `courseinfo` VALUES ('KC001', '安卓Android程序设计', 'TH001', '每周星期2下午3,4节', '9教学楼304', '3.5', '测试课程信息');
INSERT INTO `courseinfo` VALUES ('KC002', 'ios程序设计', 'TH002', '每周5下午1,2节', '6A-301', '3', '苹果手机程序');
INSERT INTO `courseinfo` VALUES ('KC003', 'jsp网站设计入门', 'TH002', '每周4上午3,4节', '7C-101', '3.5', '测试课程');
INSERT INTO `courseinfo` VALUES ('KC004', '高等数学', 'TH001', '每周2下午3,4节', '8C-103', '3', '测试信息');
INSERT INTO `courseinfo` VALUES ('KC005', '计算机编译原理', 'TH001', '每周3上午1,2节', '6B-203', '3.5', '测试课程');

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `newsId` int(11) NOT NULL auto_increment,
  `newsTitle` varchar(50) default NULL,
  `newsContent` longtext,
  `newsDate` datetime default NULL,
  `newsPhoto` varchar(50) default NULL,
  PRIMARY KEY  (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('1', '2018年清明节放假安排', '除夕节不放假了！', '2018-03-31 00:00:00', 'upload/a1099a4b-2ebc-4ddc-9803-ef3376f2a2e3.jpg');
INSERT INTO `news` VALUES ('2', '嫦娥三号将于14日落月', '据中国之声《央广新闻》报道，嫦娥三号即将于14日落月，面对从未去过的陌生土地，黑色750秒、时不时会扬起的月尘……嫦娥三号携带了哪些制胜法宝，又将如何闯关？\r\n\r\n　　嫦娥三号要降落在一个从未去过的这么一片土地，这里面的环境温度白天达到100多度，同时在地面上还会有时时扬起的跟我们地面上不一样的月尘，而这种月尘其实在地面上我们是无法测到的，它的成分相当于火山灰。还有，它在下降的过程中还有一系列包括角度等等的风险。\r\n\r\n　　正是因为嫦娥三号探测器会面对这么多的风险，因此它现在设计也是非常合理，它们是由着陆器和巡视器两部分组成，当着陆器降落到月球之后将释放巡视器，也就是说只要三姑娘能轻盈落月，此次任务就已经取得了一大半的成功。这样的话在落月的过程之中最关键的莫过于推进系统，推进系统现在是由一台7500牛的变推力轨控发动机，还有若干台150牛的大姿控发动机，还有若干台10牛的小姿控发动机组成，这些发动机可以共同作用来承担地月转移、近月制动、环月飞行以及最后的分系统在15公里到100米的高度精确完成逐减速任务，还要在100米-4米高度开始稳定和缓慢地下降。（记者张棉棉）\r\n', '2018-03-31 00:00:00', 'upload/d750c7df-85a1-4e82-a7e3-447ad1ab7b90.jpg');

-- ----------------------------
-- Table structure for `scoreinfo`
-- ----------------------------
DROP TABLE IF EXISTS `scoreinfo`;
CREATE TABLE `scoreinfo` (
  `scoreId` int(11) NOT NULL auto_increment,
  `studentNumber` varchar(20) default NULL,
  `courseNumber` varchar(20) default NULL,
  `scoreValue` float default NULL,
  `studentEvaluate` varchar(30) default NULL,
  PRIMARY KEY  (`scoreId`),
  KEY `FKF6529E40155BEDF5` (`studentNumber`),
  KEY `FKF6529E405EB22C57` (`courseNumber`),
  CONSTRAINT `FKF6529E40155BEDF5` FOREIGN KEY (`studentNumber`) REFERENCES `student` (`studentNumber`),
  CONSTRAINT `FKF6529E405EB22C57` FOREIGN KEY (`courseNumber`) REFERENCES `courseinfo` (`courseNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of scoreinfo
-- ----------------------------
INSERT INTO `scoreinfo` VALUES ('1', '201705030317', 'KC001', '95', '表现不错');
INSERT INTO `scoreinfo` VALUES ('2', '201705030317', 'KC002', '95', '这个学生很努力！');
INSERT INTO `scoreinfo` VALUES ('3', '201705030318', 'KC002', '85', '表现不错');
INSERT INTO `scoreinfo` VALUES ('4', '201705030318', 'KC003', '78', '有待努力');

-- ----------------------------
-- Table structure for `specialfieldinfo`
-- ----------------------------
DROP TABLE IF EXISTS `specialfieldinfo`;
CREATE TABLE `specialfieldinfo` (
  `specialFieldNumber` varchar(20) NOT NULL,
  `specialFieldName` varchar(20) default NULL,
  `specialCollegeNumber` varchar(20) default NULL,
  `specialBirthDate` datetime default NULL,
  `specialMan` varchar(10) default NULL,
  `specialTelephone` varchar(20) default NULL,
  `specialMemo` varchar(100) default NULL,
  PRIMARY KEY  (`specialFieldNumber`),
  KEY `FK2FCC502F6AE335E2` (`specialCollegeNumber`),
  CONSTRAINT `FK2FCC502F6AE335E2` FOREIGN KEY (`specialCollegeNumber`) REFERENCES `collegeinfo` (`collegeNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of specialfieldinfo
-- ----------------------------
INSERT INTO `specialfieldinfo` VALUES ('0503', '计算机科学与技术', '05', '2017-12-31 00:00:00', '小明', '13859383943', '测试专业');
INSERT INTO `specialfieldinfo` VALUES ('0504', '电子信息技术', '05', '2017-11-21 00:00:00', '黄石', '13569384953', '测试信息');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `studentNumber` varchar(20) NOT NULL,
  `studentName` varchar(12) default NULL,
  `studentPassword` varchar(30) default NULL,
  `studentSex` varchar(2) default NULL,
  `studentClassNumber` varchar(20) default NULL,
  `studentBirthday` datetime default NULL,
  `studentState` varchar(20) default NULL,
  `studentPhoto` varchar(50) default NULL,
  `studentTelephone` varchar(20) default NULL,
  `studentEmail` varchar(30) default NULL,
  `studentQQ` varchar(20) default NULL,
  `studentAddress` varchar(100) default NULL,
  `studentMemo` varchar(100) default NULL,
  PRIMARY KEY  (`studentNumber`),
  KEY `FKF3371A1B97DC0CE2` (`studentClassNumber`),
  CONSTRAINT `FKF3371A1B97DC0CE2` FOREIGN KEY (`studentClassNumber`) REFERENCES `classinfo` (`classNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('201705030317', '双鱼林', '123456', '男', '2017050303', '2018-03-31 00:00:00', '团员', 'upload/e2e275ef-404d-42a5-8280-ba591ac23601.jpg', '13849384212', 'xiaomiao@126.com', '287307421', '四川成都红星路10号', '测试学生信息');
INSERT INTO `student` VALUES ('201705030318', '雷小夏', '123456', '女', '2017050303', '2018-03-31 00:00:00', '团员', 'upload/de664348-89e0-4cee-a9c4-15cc3cb05f08.jpg', '15934214312', 'xiaoxia@126.com', '4398412', '福建福州', '测试学生');

-- ----------------------------
-- Table structure for `studentselectcourseinfo`
-- ----------------------------
DROP TABLE IF EXISTS `studentselectcourseinfo`;
CREATE TABLE `studentselectcourseinfo` (
  `selectId` int(11) NOT NULL auto_increment,
  `studentNumber` varchar(20) default NULL,
  `courseNumber` varchar(20) default NULL,
  PRIMARY KEY  (`selectId`),
  KEY `FKDD6401C0155BEDF5` (`studentNumber`),
  KEY `FKDD6401C05EB22C57` (`courseNumber`),
  CONSTRAINT `FKDD6401C0155BEDF5` FOREIGN KEY (`studentNumber`) REFERENCES `student` (`studentNumber`),
  CONSTRAINT `FKDD6401C05EB22C57` FOREIGN KEY (`courseNumber`) REFERENCES `courseinfo` (`courseNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of studentselectcourseinfo
-- ----------------------------
INSERT INTO `studentselectcourseinfo` VALUES ('1', '201705030317', 'KC001');
INSERT INTO `studentselectcourseinfo` VALUES ('2', '201705030317', 'KC004');
INSERT INTO `studentselectcourseinfo` VALUES ('3', '201705030317', 'KC002');
INSERT INTO `studentselectcourseinfo` VALUES ('4', '201705030318', 'KC002');
INSERT INTO `studentselectcourseinfo` VALUES ('5', '201705030318', 'KC003');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `teacherNumber` varchar(20) NOT NULL,
  `teacherName` varchar(12) default NULL,
  `teacherPassword` varchar(30) default NULL,
  `teacherSex` varchar(2) default NULL,
  `teacherBirthday` datetime default NULL,
  `teacherArriveDate` datetime default NULL,
  `teacherCardNumber` varchar(20) default NULL,
  `teacherPhone` varchar(20) default NULL,
  `teacherPhoto` varchar(50) default NULL,
  `teacherAddress` varchar(100) default NULL,
  `teacherMemo` varchar(100) default NULL,
  PRIMARY KEY  (`teacherNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('TH001', '张无忌', '123456', '男', '1999-12-06 00:00:00', '2017-12-06 00:00:00', '513030198912064123', '13959349312', 'upload/426ea669-9bf3-43ef-92f6-eec1ef786875.jpg', '测试老师地址', '测试附加信息');
INSERT INTO `teacher` VALUES ('TH002', '王小燕', '123456', '女', '1998-12-09 00:00:00', '2017-09-14 00:00:00', '513030199312064133', '13953539315', 'upload/8ffa110e-35bb-4bd3-b988-3cb7da868f08.jpg', '四川成都二仙桥', '测试信息');
