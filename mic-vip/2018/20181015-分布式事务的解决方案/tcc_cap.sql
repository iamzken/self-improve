/*
Navicat MySQL Data Transfer

Source Server         : 192.168.8.126
Source Server Version : 50722
Source Host           : 192.168.8.126:3306
Source Database       : tcc_cap

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-10-24 14:01:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for CAP_CAPITAL_ACCOUNT
-- ----------------------------
DROP TABLE IF EXISTS `CAP_CAPITAL_ACCOUNT`;
CREATE TABLE `CAP_CAPITAL_ACCOUNT` (
  `CAPITAL_ACCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BALANCE_AMOUNT` decimal(10,0) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CAPITAL_ACCOUNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of CAP_CAPITAL_ACCOUNT
-- ----------------------------
INSERT INTO `CAP_CAPITAL_ACCOUNT` VALUES ('1', '10000', '1000');
INSERT INTO `CAP_CAPITAL_ACCOUNT` VALUES ('2', '10000', '2000');

-- ----------------------------
-- Table structure for CAP_TRADE_ORDER
-- ----------------------------
DROP TABLE IF EXISTS `CAP_TRADE_ORDER`;
CREATE TABLE `CAP_TRADE_ORDER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SELF_USER_ID` bigint(11) DEFAULT NULL,
  `OPPOSITE_USER_ID` bigint(11) DEFAULT NULL,
  `MERCHANT_ORDER_NO` varchar(45) NOT NULL,
  `AMOUNT` decimal(10,0) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UX_MERCHANT_ORDER_NO` (`MERCHANT_ORDER_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of CAP_TRADE_ORDER
-- ----------------------------
