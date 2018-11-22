/*
Navicat MySQL Data Transfer

Source Server         : 192.168.8.126
Source Server Version : 50722
Source Host           : 192.168.8.126:3306
Source Database       : tcc_order

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-10-24 14:01:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ORD_ORDER
-- ----------------------------
DROP TABLE IF EXISTS `ORD_ORDER`;
CREATE TABLE `ORD_ORDER` (
  `ORDER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PAYER_USER_ID` int(11) DEFAULT NULL,
  `PAYEE_USER_ID` int(11) DEFAULT NULL,
  `RED_PACKET_PAY_AMOUNT` decimal(10,0) DEFAULT NULL,
  `CAPITAL_PAY_AMOUNT` decimal(10,0) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `MERCHANT_ORDER_NO` varchar(45) NOT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ORDER_ID`),
  UNIQUE KEY `MERCHANT_ORDER_NO_UNIQUE` (`MERCHANT_ORDER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=1198 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ORD_ORDER
-- ----------------------------
INSERT INTO `ORD_ORDER` VALUES ('1188', '1000', '2000', null, '4000', 'PAYING', '7c9076f0-b8df-4fda-a76e-0faa0ad5e1d4', '2');
INSERT INTO `ORD_ORDER` VALUES ('1197', '1000', '2000', null, '4000', 'PAYING', 'f6418561-315a-42ea-ab18-9a29797c5c0f', '2');
