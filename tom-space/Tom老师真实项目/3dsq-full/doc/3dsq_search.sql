/*
Navicat MySQL Data Transfer

Source Server         : 192.168.94.13_3306
Source Server Version : 50713
Source Host           : 192.168.94.13:3306
Source Database       : 3dsq_search

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_hot_city
-- ----------------------------
DROP TABLE IF EXISTS `t_hot_city`;
CREATE TABLE `t_hot_city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cityId` varchar(255) DEFAULT NULL,
  `cityPath` varchar(255) DEFAULT NULL COMMENT '城市xpath',
  `hotStore` int(11) DEFAULT NULL COMMENT '热度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='热门城市列表';

-- ----------------------------
-- Records of t_hot_city
-- ----------------------------

-- ----------------------------
-- Table structure for t_keyword
-- ----------------------------
DROP TABLE IF EXISTS `t_keyword`;
CREATE TABLE `t_keyword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(255) DEFAULT NULL,
  `hotStore` int(11) DEFAULT NULL COMMENT '热度指数',
  `createTime` bigint(20) DEFAULT NULL,
  `lastSearchLocation` varchar(255) DEFAULT NULL,
  `lastSearchIp` varchar(255) DEFAULT NULL,
  `lastSearchTime` bigint(20) DEFAULT NULL,
  `state` int(1) DEFAULT NULL COMMENT '状态码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='热门关键词';

-- ----------------------------
-- Records of t_keyword
-- ----------------------------

-- ----------------------------
-- Table structure for t_search_log
-- ----------------------------
DROP TABLE IF EXISTS `t_search_log`;
CREATE TABLE `t_search_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(255) DEFAULT NULL COMMENT '搜搜关键词',
  `lat` float(11,0) DEFAULT NULL COMMENT '纬度',
  `lon` float(20,0) DEFAULT NULL COMMENT '经度',
  `ip` varchar(255) DEFAULT NULL COMMENT 'IP',
  `searchTime` bigint(20) DEFAULT NULL COMMENT '搜索时间',
  `memberId` bigint(20) DEFAULT NULL COMMENT '搜索人',
  `stype` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='搜索日志表';

-- ----------------------------
-- Records of t_search_log
-- ----------------------------
