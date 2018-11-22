/*
Navicat MySQL Data Transfer

Source Server         : 192.168.94.13_3306
Source Server Version : 50713
Source Host           : 192.168.94.13:3306
Source Database       : 3dsq_sns

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_bbs_forum
-- ----------------------------
DROP TABLE IF EXISTS `t_bbs_forum`;
CREATE TABLE `t_bbs_forum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `parentId` bigint(20) NOT NULL COMMENT '父级节点',
  `xpath` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'xpath',
  `cityPath` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所在城市',
  `residentialId` bigint(20) DEFAULT NULL COMMENT '小区ID',
  `title` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '版块名称',
  `creatorId` bigint(20) DEFAULT NULL COMMENT '版主',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `alias` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '版块别名',
  `level` int(11) DEFAULT NULL COMMENT '层级',
  `state` int(11) DEFAULT '0' COMMENT '状态码',
  PRIMARY KEY (`id`),
  KEY `idx_parentId` (`parentId`) USING BTREE,
  KEY `idx_xpath` (`xpath`) USING BTREE,
  KEY `idx_residentialId` (`residentialId`) USING BTREE,
  KEY `idx_creatorId` (`creatorId`) USING BTREE,
  KEY `idx_level` (`level`) USING BTREE,
  KEY `idx_state` (`state`) USING BTREE,
  KEY `idx_cityPath` (`cityPath`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='论坛版块表';

-- ----------------------------
-- Records of t_bbs_forum
-- ----------------------------
INSERT INTO `t_bbs_forum` VALUES ('1', '0', '/0/1/', null, '1', '测试模块', null, '1469864540358', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('2', '0', '/0/2/', null, '1', '小区', null, '1469868951709', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('3', '0', '/0/3/', null, '1', '物业', null, '1469868958834', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('4', '0', '/0/4/', null, '745', '小区', null, '1469936916542', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('5', '0', '/0/5/', null, '745', '物业', null, '1469936916549', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('6', '0', '/0/6/', null, '746', '小区', null, '1470196322056', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('9', '0', '/0/9/', null, '746', '物业', null, '1470196322072', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('10', '0', '/0/10/', null, '123', '小区', null, '1470206735359', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('11', '0', '/0/11/', null, '123', '物业', null, '1470206735366', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('12', '0', '/0/12/', null, '187', '小区', null, '1470467331344', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('13', '0', '/0/13/', null, '187', '物业', null, '1470467331351', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('14', '0', '/0/14/', null, '722', '小区', null, '1471674626716', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('15', '0', '/0/15/', null, '722', '物业', null, '1471674626724', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('16', '0', '/0/16/', null, '753', '小区', null, '1471693100586', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('17', '0', '/0/17/', null, '753', '物业', null, '1471693100593', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('18', '0', '/0/18/', null, '754', '小区', null, '1471695062226', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('19', '0', '/0/19/', null, '754', '物业', null, '1471695062231', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('20', '0', '/0/20/', null, '26', '小区', null, '1471695402422', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('21', '0', '/0/21/', null, '26', '物业', null, '1471695402428', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('22', '0', '/0/22/', null, '768', '小区', null, '1471735352731', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('23', '0', '/0/23/', null, '768', '物业', null, '1471735352738', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('24', '0', '/0/24/', null, '773', '小区', null, '1471742943299', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('25', '0', '/0/25/', null, '773', '物业', null, '1471742943305', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('26', '0', '/0/26/', null, '620', '小区', null, '1471754669069', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('27', '0', '/0/27/', null, '620', '物业', null, '1471754669074', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('28', '0', '/0/28/', null, '789', '小区', null, '1471829902307', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('29', '0', '/0/29/', null, '789', '物业', null, '1471829902313', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('30', '0', '/0/30/', null, '772', '小区', null, '1471842385791', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('31', '0', '/0/31/', null, '772', '物业', null, '1471842385797', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('32', '0', '/0/32/', null, '770', '小区', null, '1471913042701', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('33', '0', '/0/33/', null, '770', '物业', null, '1471913042716', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('34', '0', '/0/34/', null, '872', '小区', null, '1471951964125', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('35', '0', '/0/35/', null, '872', '物业', null, '1471951964131', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('36', '0', '/0/36/', null, '787', '小区', null, '1471956952860', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('37', '0', '/0/37/', null, '787', '物业', null, '1471956952864', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('38', '0', '/0/38/', null, '855', '小区', null, '1471995317405', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('39', '0', '/0/39/', null, '855', '物业', null, '1471995317412', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('40', '0', '/0/40/', null, '875', '小区', null, '1472244371155', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('41', '0', '/0/41/', null, '875', '物业', null, '1472244371165', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('42', '0', '/0/42/', null, '801', '小区', null, '1472361965654', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('43', '0', '/0/43/', null, '801', '物业', null, '1472361965657', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('44', '0', '/0/44/', null, '769', '小区', null, '1472385784661', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('45', '0', '/0/45/', null, '769', '物业', null, '1472385784666', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('46', '0', '/0/46/', null, '828', '小区', null, '1472431814137', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('47', '0', '/0/47/', null, '828', '物业', null, '1472431814141', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('48', '0', '/0/48/', null, '853', '小区', null, '1472546427784', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('49', '0', '/0/49/', null, '853', '物业', null, '1472546427792', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('50', '0', '/0/50/', null, '792', '小区', null, '1472705234527', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('51', '0', '/0/51/', null, '792', '物业', null, '1472705234532', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('52', '0', '/0/52/', null, '867', '小区', null, '1472965252890', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('53', '0', '/0/53/', null, '867', '物业', null, '1472965252896', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('54', '0', '/0/54/', null, '794', '小区', null, '1473160559055', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('55', '0', '/0/55/', null, '794', '物业', null, '1473160559060', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('56', '0', '/0/56/', null, '791', '小区', null, '1473209277695', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('57', '0', '/0/57/', null, '791', '物业', null, '1473209277702', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('58', '0', '/0/58/', null, '775', '小区', null, '1473676819876', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('59', '0', '/0/59/', null, '775', '物业', null, '1473676819881', 'PUB_PROPERTY', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('60', '0', '/0/60/', null, '819', '小区', null, '1474689330981', 'PUB_RESIDENTIAL', null, '1');
INSERT INTO `t_bbs_forum` VALUES ('61', '0', '/0/61/', null, '819', '物业', null, '1474689330988', 'PUB_PROPERTY', null, '1');

-- ----------------------------
-- Table structure for t_bbs_praise
-- ----------------------------
DROP TABLE IF EXISTS `t_bbs_praise`;
CREATE TABLE `t_bbs_praise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `topicId` bigint(20) DEFAULT NULL COMMENT '话题ID',
  `replyId` bigint(20) DEFAULT NULL COMMENT '回复ID',
  `type` int(11) NOT NULL COMMENT '点赞类型',
  `createTime` bigint(20) DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_type` (`type`),
  KEY `idx_topicId` (`topicId`),
  KEY `idx_replyId` (`replyId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户点赞日志表';

-- ----------------------------
-- Records of t_bbs_praise
-- ----------------------------
INSERT INTO `t_bbs_praise` VALUES ('7', '2', null, '11', '2', '1469949275736');

-- ----------------------------
-- Table structure for t_bbs_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_bbs_reply`;
CREATE TABLE `t_bbs_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `parentId` bigint(20) DEFAULT '0' COMMENT '父级ID',
  `level` int(11) DEFAULT NULL COMMENT '回复层级',
  `topicId` bigint(20) NOT NULL COMMENT '话题ID',
  `topicCreatorId` bigint(20) DEFAULT NULL COMMENT '话题创建人',
  `creatorId` bigint(20) NOT NULL COMMENT '回复人ID',
  `content` text COMMENT '回复内容',
  `contentImgs` text COMMENT '回复内容图片列表',
  `xpath` varchar(500) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL COMMENT '发表回复时间',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '最后修改回复内容时间',
  `updateUid` bigint(20) DEFAULT NULL COMMENT '最后修改回复内容用户的id',
  `rootReplyCount` int(20) DEFAULT '0' COMMENT '一级回复的数量',
  `replyToUid` bigint(20) DEFAULT NULL COMMENT '被回复人的id',
  `praiseUser` text COMMENT '点赞用户名',
  `praiseCount` int(11) DEFAULT '0' COMMENT '点赞用户数量',
  PRIMARY KEY (`id`),
  KEY `idx_topicId` (`topicId`),
  KEY `idx_creatorId` (`creatorId`),
  KEY `idx_pid` (`parentId`),
  KEY `idx_level` (`level`),
  KEY `idx_xpath` (`xpath`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='话题回复表';

-- ----------------------------
-- Records of t_bbs_reply
-- ----------------------------
INSERT INTO `t_bbs_reply` VALUES ('11', '0', '1', '6', '2', '2', '回去测试话题一级回复', '', '/0/11/', '1469853668970', null, null, '2', '0', '2,', '1');
INSERT INTO `t_bbs_reply` VALUES ('13', '11', '2', '6', '2', '2', '二级级回复1', '', '/0/11/13/', '1469853942711', null, null, '0', '2', '2,', '1');
INSERT INTO `t_bbs_reply` VALUES ('14', '13', '3', '6', '2', '2', '三级级级回复1', '', '/0/11/13/14/', '1469853990671', null, null, '0', '2', null, null);

-- ----------------------------
-- Table structure for t_bbs_topic
-- ----------------------------
DROP TABLE IF EXISTS `t_bbs_topic`;
CREATE TABLE `t_bbs_topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `title` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发表话题标题',
  `content` text COLLATE utf8_unicode_ci COMMENT '话题内容',
  `creatorId` bigint(20) DEFAULT NULL COMMENT '发起人',
  `contentImgs` text COLLATE utf8_unicode_ci COMMENT '图片列表（JSON格式）',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `updateUid` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `readPersonCount` bigint(20) DEFAULT '0' COMMENT '点击量',
  `lastReplyUid` bigint(20) DEFAULT NULL COMMENT '最后回复人的ID',
  `lastReplyName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最后回复人名称',
  `lastReplyContent` text COLLATE utf8_unicode_ci COMMENT '最后回复的内容',
  `lastReplyContentImgs` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最后回复内容中包含的图片',
  `lastReplyTime` bigint(20) DEFAULT NULL COMMENT '最后回复时间',
  `top` int(11) DEFAULT '0' COMMENT '是否置顶',
  `choice` int(11) DEFAULT '0' COMMENT '是否加精',
  `prohibit` int(11) DEFAULT '0' COMMENT '审核状态',
  `stick` int(11) DEFAULT '0' COMMENT '结贴表示',
  `residentialId` bigint(20) DEFAULT NULL COMMENT '小区ID',
  `forumId` bigint(20) DEFAULT NULL COMMENT '版块ID',
  `cityPath` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所在城市',
  `forumPath` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所在版块',
  `lat` float DEFAULT NULL COMMENT '纬度',
  `lon` float DEFAULT NULL COMMENT '经度',
  `replyCount` bigint(20) DEFAULT '0' COMMENT '回复数',
  `praiseUser` text COLLATE utf8_unicode_ci COMMENT '点赞用户',
  `praiseCount` int(11) DEFAULT '0' COMMENT '点赞数量',
  PRIMARY KEY (`id`),
  KEY `idx_residentialId` (`residentialId`),
  KEY `idx_top` (`top`),
  KEY `idx_choice` (`choice`),
  KEY `idx_creatorId` (`creatorId`) USING BTREE,
  KEY `idx_forumId` (`forumId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='讨论话题表';

-- ----------------------------
-- Records of t_bbs_topic
-- ----------------------------
INSERT INTO `t_bbs_topic` VALUES ('6', 'asdadas', 'asdasasda', '2', null, '1111111111111111111', null, null, '0', null, null, null, null, null, '0', '0', '0', '0', '1', '2', null, null, null, null, '0', null, '0');
INSERT INTO `t_bbs_topic` VALUES ('7', '吧', '吧', '4', '', '1470196460439', '1470196460439', null, '0', null, null, null, null, '1470196460439', '0', '0', '0', '0', '745', '4', null, null, null, null, '0', null, '0');
INSERT INTO `t_bbs_topic` VALUES ('8', '吧', '吧', '4', '', '1470197001507', '1470197001507', null, '0', null, null, null, null, '1470197001507', '0', '0', '0', '0', '745', '4', null, null, null, null, '0', null, '0');
INSERT INTO `t_bbs_topic` VALUES ('9', '喝酒', '哦啦噜啦', '4', '', '1470197698115', '1470197698115', null, '0', null, null, null, null, '1470197698115', '0', '0', '0', '0', '745', '5', null, null, null, null, '0', null, '0');
INSERT INTO `t_bbs_topic` VALUES ('10', '家', '咯好', '4', '', '1470197747309', '1470197747309', null, '0', null, null, null, null, '1470197747309', '0', '0', '0', '0', '745', '5', null, null, null, null, '0', null, '0');
INSERT INTO `t_bbs_topic` VALUES ('11', '吧', 'OK你', '4', '', '1470209974461', '1470209974461', null, '0', null, null, null, null, '1470209974461', '0', '0', '0', '0', '745', '4', null, null, null, null, '0', null, '0');
INSERT INTO `t_bbs_topic` VALUES ('12', '测试', '测试', '12', '', '1470236014313', '1470236014313', null, '0', null, null, null, null, '1470236014313', '0', '0', '0', '0', '745', '4', null, null, null, null, '0', null, '0');
INSERT INTO `t_bbs_topic` VALUES ('13', '测试话题', '测试话题', '12', '', '1470236101548', '1470236101548', null, '0', null, null, null, null, '1470236101548', '0', '0', '0', '0', '745', '4', null, null, null, null, '0', null, '0');
INSERT INTO `t_bbs_topic` VALUES ('14', '来', '蜡笔', '4', '', '1470366991741', '1470366991741', null, '0', null, null, null, null, '1470366991741', '0', '0', '0', '0', '745', '4', null, null, null, null, '0', null, '0');
INSERT INTO `t_bbs_topic` VALUES ('15', '来OK', '看了喇叭', '4', '', '1470367018283', '1470367018283', null, '0', null, null, null, null, '1470367018283', '0', '0', '0', '0', '745', '4', null, null, null, null, '0', null, '0');

-- ----------------------------
-- Table structure for t_sns_friend
-- ----------------------------
DROP TABLE IF EXISTS `t_sns_friend`;
CREATE TABLE `t_sns_friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `memberId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `groupId` bigint(20) DEFAULT NULL COMMENT '分组ID',
  `friendId` bigint(20) DEFAULT NULL COMMENT '好友ID',
  `blackFlag` int(11) DEFAULT NULL COMMENT '是否黑名单',
  `alias` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '好友备注名称',
  `createTime` bigint(20) DEFAULT NULL COMMENT '加为好友 时间',
  `state` int(11) DEFAULT NULL COMMENT '状态(1:有效，0：已删)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='好友表';

-- ----------------------------
-- Records of t_sns_friend
-- ----------------------------

-- ----------------------------
-- Table structure for t_sns_group
-- ----------------------------
DROP TABLE IF EXISTS `t_sns_group`;
CREATE TABLE `t_sns_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `memberId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分组名称',
  `gtype` int(11) DEFAULT NULL COMMENT '分组类型(1：家庭组，2：好友组)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='好友分组表';

-- ----------------------------
-- Records of t_sns_group
-- ----------------------------
