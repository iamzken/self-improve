/*
Navicat MySQL Data Transfer

Source Server         : 192.168.94.13_3306
Source Server Version : 50713
Source Host           : 192.168.94.13:3306
Source Database       : 3dsq_admin

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_lng_settings
-- ----------------------------
DROP TABLE IF EXISTS `t_lng_settings`;
CREATE TABLE `t_lng_settings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
  `keyCode` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '键',
  `keyVal` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '值',
  `local` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '语言',
  `mode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所属模块',
  `platform` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '子平台名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='多语言配置表';

-- ----------------------------
-- Records of t_lng_settings
-- ----------------------------

-- ----------------------------
-- Table structure for t_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `t_menu_role`;
CREATE TABLE `t_menu_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menuId` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='菜单角色关系表';

-- ----------------------------
-- Records of t_menu_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `discription` text COLLATE utf8_unicode_ci COMMENT '角色描述',
  `state` int(11) DEFAULT NULL COMMENT '角色状态(0：无效；1：有效)',
  `rootFlag` int(11) DEFAULT NULL COMMENT '是否超级管理员',
  `creatorId` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `options` text COLLATE utf8_unicode_ci COMMENT '角色权限',
  `fromSite` bigint(20) DEFAULT NULL COMMENT '所属站点',
  `platform` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所属平台',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_tree_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_tree_menu`;
CREATE TABLE `t_tree_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `parentId` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '父级ID',
  `xpath` bigint(20) DEFAULT NULL COMMENT 'xpath',
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜单名称',
  `orderNum` int(11) DEFAULT NULL COMMENT '排序',
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '功能地址',
  `icon` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '功能图标',
  `attributes` text COLLATE utf8_unicode_ci COMMENT '自定义属性',
  `actions` text COLLATE utf8_unicode_ci COMMENT '权限代码',
  `platform` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所属平台',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建日期',
  `creatorId` bigint(20) DEFAULT NULL COMMENT '创建人',
  `state` int(11) DEFAULT NULL COMMENT '状态(1:可用，0：禁用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='功能菜单表';

-- ----------------------------
-- Records of t_tree_menu
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `loginName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `loginPass` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户密码(MD5加密长32位)',
  `realName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `sex` int(11) DEFAULT NULL COMMENT '性别（0：女；1：男；2：保密）',
  `province` varchar(8) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所属省份',
  `city` varchar(8) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所属城市',
  `area` varchar(8) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所属区县',
  `companyId` bigint(20) DEFAULT NULL COMMENT '所属企业',
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '电子邮箱',
  `loginCount` bigint(20) DEFAULT NULL COMMENT '登录次数',
  `lastLoginIp` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最后登录IP',
  `lastlogintime` bigint(20) DEFAULT NULL COMMENT '最后登录时间',
  `lastLoginAddr` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最后登录地址',
  `remark` text COLLATE utf8_unicode_ci COMMENT '备注',
  `state` int(11) DEFAULT NULL COMMENT '用户状态（0：不可用；1：可用；2：已禁用；-1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='后台管理用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色关系表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_settings
-- ----------------------------
DROP TABLE IF EXISTS `t_user_settings`;
CREATE TABLE `t_user_settings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `memberId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `settings` text COLLATE utf8_unicode_ci COMMENT '用户配置(JSON 对象)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户个性化设置表';

-- ----------------------------
-- Records of t_user_settings
-- ----------------------------
