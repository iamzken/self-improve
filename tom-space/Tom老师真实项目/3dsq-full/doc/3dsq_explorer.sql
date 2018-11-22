/*
Navicat MySQL Data Transfer

Source Server         : 192.168.94.13_3306
Source Server Version : 50713
Source Host           : 192.168.94.13:3306
Source Database       : 3dsq_explorer

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_short_url
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url`;
CREATE TABLE `t_short_url` (
  `surl` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '短链接',
  `url` text COLLATE utf8_unicode_ci COMMENT '实际链接',
  PRIMARY KEY (`surl`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='短链接表';

-- ----------------------------
-- Records of t_short_url
-- ----------------------------

-- ----------------------------
-- Table structure for t_ufile
-- ----------------------------
DROP TABLE IF EXISTS `t_ufile`;
CREATE TABLE `t_ufile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `objectId` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `xpath` text COLLATE utf8_unicode_ci COMMENT 'xpath',
  `xcode` text COLLATE utf8_unicode_ci COMMENT 'xcode',
  `fname` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件名',
  `alias` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件别名',
  `ftype` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件类型',
  `ext` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件后缀',
  `ico` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '显示图标',
  `lastModified` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `fsize` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `fgroup` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件分组',
  `recycle` int(11) DEFAULT NULL COMMENT '是否已放入回收站',
  `createId` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='文件表';

-- ----------------------------
-- Records of t_ufile
-- ----------------------------
