/*
Navicat MySQL Data Transfer

Source Server         : 192.168.94.13_3306
Source Server Version : 50713
Source Host           : 192.168.94.13:3306
Source Database       : 3dsq_property

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_build
-- ----------------------------
DROP TABLE IF EXISTS `t_build`;
CREATE TABLE `t_build` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `residentialId` bigint(20) DEFAULT NULL,
  `xpath` varchar(255) DEFAULT NULL COMMENT 'xpath',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `levelType` int(11) DEFAULT NULL COMMENT '层级',
  `floorCount` int(11) DEFAULT NULL COMMENT '楼层数量',
  `floorInfo` text COMMENT '楼层信息',
  `state` int(11) DEFAULT NULL COMMENT '状态码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小区楼栋、单元、房间信息登记表';

-- ----------------------------
-- Records of t_build
-- ----------------------------

-- ----------------------------
-- Table structure for t_owner
-- ----------------------------
DROP TABLE IF EXISTS `t_owner`;
CREATE TABLE `t_owner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `roomId` bigint(20) DEFAULT NULL COMMENT '房间ID',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `propertyNum` varchar(255) DEFAULT NULL COMMENT '物业服务卡号',
  `idCardImg` varchar(255) DEFAULT NULL COMMENT '身份证电子版',
  `idCardNum` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `tel` varchar(255) DEFAULT NULL COMMENT '手机号',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `holderFlag` int(11) DEFAULT NULL COMMENT '是否为户主',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `buyTime` bigint(20) DEFAULT NULL COMMENT '购房日期',
  `handoverTime` bigint(20) DEFAULT NULL COMMENT '交房日期',
  `checkinTime` bigint(20) DEFAULT NULL COMMENT '入住日期',
  `state` int(11) DEFAULT NULL COMMENT '状态码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业主信息表';

-- ----------------------------
-- Records of t_owner
-- ----------------------------

-- ----------------------------
-- Table structure for t_power_fees
-- ----------------------------
DROP TABLE IF EXISTS `t_power_fees`;
CREATE TABLE `t_power_fees` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `residentialId` bigint(20) DEFAULT NULL,
  `roomId` bigint(20) DEFAULT NULL,
  `orderNum` varchar(255) DEFAULT NULL,
  `fees` float DEFAULT NULL,
  `price` float DEFAULT NULL COMMENT '单价',
  `account` float DEFAULT NULL COMMENT '用量',
  `discount` float DEFAULT NULL COMMENT '折扣',
  `startTime` bigint(20) DEFAULT NULL,
  `endTime` bigint(20) DEFAULT NULL,
  `finishFlag` int(11) DEFAULT NULL,
  `finishTime` bigint(20) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电费缴纳订单';

-- ----------------------------
-- Records of t_power_fees
-- ----------------------------

-- ----------------------------
-- Table structure for t_power_grid
-- ----------------------------
DROP TABLE IF EXISTS `t_power_grid`;
CREATE TABLE `t_power_grid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) DEFAULT NULL,
  `xpath` varchar(255) DEFAULT NULL,
  `cityPath` varchar(255) DEFAULT NULL,
  `num` varchar(255) DEFAULT NULL COMMENT '电网公司编号',
  `name` varchar(255) DEFAULT NULL,
  `shortName` varchar(255) DEFAULT NULL,
  `lat` float DEFAULT NULL,
  `lon` float DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电网公司信息表';

-- ----------------------------
-- Records of t_power_grid
-- ----------------------------

-- ----------------------------
-- Table structure for t_power_grid_residential
-- ----------------------------
DROP TABLE IF EXISTS `t_power_grid_residential`;
CREATE TABLE `t_power_grid_residential` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `powerId` bigint(20) DEFAULT NULL,
  `residentialId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电网公司和小区关系表';

-- ----------------------------
-- Records of t_power_grid_residential
-- ----------------------------

-- ----------------------------
-- Table structure for t_property
-- ----------------------------
DROP TABLE IF EXISTS `t_property`;
CREATE TABLE `t_property` (
  `id` bigint(20) NOT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `xpath` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '物业公司名称',
  `shortName` varchar(255) DEFAULT NULL COMMENT '物业公司简称',
  `pinyin` varchar(255) DEFAULT NULL COMMENT '中文全拼',
  `lat` float DEFAULT NULL COMMENT '纬度',
  `lon` float DEFAULT NULL COMMENT '经度',
  `cityPath` varchar(255) DEFAULT NULL COMMENT '所在城市路径',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `concacts` varchar(255) DEFAULT NULL COMMENT '联系人',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `state` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物业公司信息表';

-- ----------------------------
-- Records of t_property
-- ----------------------------

-- ----------------------------
-- Table structure for t_property_fees
-- ----------------------------
DROP TABLE IF EXISTS `t_property_fees`;
CREATE TABLE `t_property_fees` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `residentialId` bigint(20) DEFAULT NULL,
  `propertyId` bigint(20) DEFAULT NULL COMMENT '物业公司ID',
  `roomId` bigint(20) DEFAULT NULL COMMENT '房间号',
  `orderNum` varchar(255) DEFAULT NULL COMMENT '订单号',
  `fees` float DEFAULT NULL COMMENT '物业费用',
  `price` float DEFAULT NULL COMMENT '单价',
  `areaSize` float DEFAULT NULL COMMENT '面积大小',
  `discount` float DEFAULT NULL COMMENT '折扣',
  `startTime` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `endTime` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `finishFlag` int(11) DEFAULT NULL COMMENT '是否已完成缴费',
  `finishTime` bigint(20) DEFAULT NULL COMMENT '完成缴费日期',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `holderName` varchar(255) DEFAULT NULL COMMENT '业主姓名',
  `state` int(11) DEFAULT NULL COMMENT '状态码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物业管理缴费订单';

-- ----------------------------
-- Records of t_property_fees
-- ----------------------------

-- ----------------------------
-- Table structure for t_property_residential
-- ----------------------------
DROP TABLE IF EXISTS `t_property_residential`;
CREATE TABLE `t_property_residential` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `propertyId` bigint(20) DEFAULT NULL COMMENT '物业公司ID',
  `residentialId` bigint(20) DEFAULT NULL COMMENT '小区ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物业公司与小区关系表';

-- ----------------------------
-- Records of t_property_residential
-- ----------------------------

-- ----------------------------
-- Table structure for t_repa_order
-- ----------------------------
DROP TABLE IF EXISTS `t_repa_order`;
CREATE TABLE `t_repa_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` varchar(255) DEFAULT NULL COMMENT '订单号',
  `residentialId` bigint(20) DEFAULT NULL COMMENT '小区ID',
  `roomId` bigint(20) DEFAULT NULL COMMENT '房间ID',
  `propertyId` bigint(20) DEFAULT NULL COMMENT '物业公司ID',
  `roomName` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `holderName` varchar(255) DEFAULT NULL COMMENT '联系人',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `content` text COMMENT '保修内容',
  `appointTime` bigint(20) DEFAULT NULL COMMENT '预约维修时间',
  `nowFlag` int(11) DEFAULT NULL COMMENT '是否立即上门',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `state` int(11) DEFAULT NULL COMMENT '状态码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物业报修订单';

-- ----------------------------
-- Records of t_repa_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_room
-- ----------------------------
DROP TABLE IF EXISTS `t_room`;
CREATE TABLE `t_room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `buildPath` varchar(255) DEFAULT NULL,
  `residentialId` bigint(20) DEFAULT NULL COMMENT '小区ID',
  `buildName` varchar(255) DEFAULT NULL COMMENT '楼栋号',
  `unitName` varchar(255) DEFAULT NULL COMMENT '单元号',
  `floorName` varchar(255) DEFAULT NULL COMMENT '楼层号',
  `name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `state` int(11) DEFAULT NULL COMMENT '状态码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小区住房信息表';

-- ----------------------------
-- Records of t_room
-- ----------------------------
