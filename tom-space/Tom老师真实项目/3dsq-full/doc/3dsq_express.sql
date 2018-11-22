/*
Navicat MySQL Data Transfer

Source Server         : 192.168.94.13_3306
Source Server Version : 50713
Source Host           : 192.168.94.13:3306
Source Database       : 3dsq_express

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_courier
-- ----------------------------
DROP TABLE IF EXISTS `t_courier`;
CREATE TABLE `t_courier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` bigint(20) DEFAULT NULL COMMENT '快递员姓名',
  `store` int(11) DEFAULT NULL COMMENT '评分等级',
  `servicdCount` int(11) DEFAULT NULL COMMENT '服务次数',
  `tel` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `creatorId` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快递员信息表';

-- ----------------------------
-- Records of t_courier
-- ----------------------------

-- ----------------------------
-- Table structure for t_ex_order
-- ----------------------------
DROP TABLE IF EXISTS `t_ex_order`;
CREATE TABLE `t_ex_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID,自增',
  `fromAddrId` bigint(20) DEFAULT NULL COMMENT '寄件地址ID',
  `toAddrId` bigint(20) DEFAULT NULL COMMENT '收件地址ID',
  `sender` varchar(50) DEFAULT NULL COMMENT '寄件人',
  `receiver` varchar(50) DEFAULT NULL COMMENT '收件人',
  `fromAddr` varchar(255) DEFAULT NULL COMMENT '寄件人地址',
  `toAddr` varchar(255) DEFAULT NULL COMMENT '收件人地址',
  `fromZip` varchar(10) DEFAULT NULL COMMENT '寄件人邮编',
  `toZip` varchar(10) DEFAULT NULL COMMENT '收件人邮编',
  `senderTel` varchar(18) DEFAULT NULL COMMENT '寄件人电话',
  `receiverTel` varchar(18) DEFAULT NULL COMMENT '收件人电话',
  `expressId` bigint(20) DEFAULT NULL COMMENT '快递公司ID',
  `expressName` varchar(50) DEFAULT NULL COMMENT '快递公司名称',
  `expressNum` varchar(50) DEFAULT NULL COMMENT '快递单号',
  `itemKindId` bigint(20) DEFAULT NULL,
  `itemName` varchar(50) DEFAULT NULL COMMENT '物品名称',
  `itemWeight` float DEFAULT NULL COMMENT '物品重量',
  `itemLong` float DEFAULT NULL COMMENT '物品长',
  `itemWide` float DEFAULT NULL COMMENT '物品宽',
  `itemHigh` float DEFAULT NULL COMMENT '物品高',
  `feesFlag` int(11) DEFAULT NULL COMMENT '计费方式',
  `itemCount` int(11) DEFAULT NULL COMMENT '件内数量',
  `finish` int(11) DEFAULT '0' COMMENT '是否已经完成（1：已完成，0：未完成）',
  `postState` int(11) DEFAULT '1' COMMENT '订单状态（1：取件中，2：已打包，3：运送中，4：到达目的地，5：派件中，6：已完成）',
  `accessFlag` int(11) DEFAULT '1' COMMENT '取件方式(1:上门取件，2：自助送件)',
  `tomorrowFlag` int(11) DEFAULT '0' COMMENT '是否次日送达(0：否，1：是)',
  `payRecvFlag` int(11) DEFAULT '1' COMMENT '是否到付(1:自付,2:到付)',
  `creatorId` bigint(20) NOT NULL COMMENT '下单人',
  `createTime` bigint(20) DEFAULT NULL COMMENT '下单时间',
  `orderAmount` float DEFAULT NULL,
  `payAmount` float DEFAULT NULL,
  `process` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='快递订单信息表';

-- ----------------------------
-- Records of t_ex_order
-- ----------------------------
INSERT INTO `t_ex_order` VALUES ('1', null, null, '胡乾坤', '宋哲', '湖北省武汉市东湖高新区万科魅力之城', '银河系火星', null, null, '1882625166', '110', null, null, null, '1', '地雷', null, null, null, null, null, null, '0', '1', '1', '0', '1', '2', '1472657363404', null, null, null);

-- ----------------------------
-- Table structure for t_express
-- ----------------------------
DROP TABLE IF EXISTS `t_express`;
CREATE TABLE `t_express` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `xpath` varchar(255) DEFAULT NULL COMMENT 'xpath',
  `num` varchar(255) DEFAULT NULL COMMENT '快递公司统一编码',
  `name` varchar(255) DEFAULT NULL COMMENT '快递公司名称',
  `shortName` varchar(255) DEFAULT NULL COMMENT '快递公司全称',
  `pinyin` varchar(255) DEFAULT NULL COMMENT '拼音',
  `logoImg` varchar(255) DEFAULT NULL COMMENT '企业logo',
  `homePage` varchar(255) DEFAULT NULL COMMENT '官方网站',
  `queryAPI` varchar(255) DEFAULT NULL COMMENT '单号查询网址',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `cityPath` text COMMENT '省级ID',
  `contacts` varchar(255) DEFAULT NULL COMMENT '联系人',
  `lon` float DEFAULT NULL COMMENT '经度',
  `lat` float DEFAULT NULL COMMENT '纬度',
  `creatorId` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `hotStore` int(11) DEFAULT NULL COMMENT '快递公司热度',
  `state` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 COMMENT='快递公司信息表';

-- ----------------------------
-- Records of t_express
-- ----------------------------
INSERT INTO `t_express` VALUES ('1', '1', '/1/', null, 'AAE快递', 'AAE快递', 'AAEkuaidi', null, null, null, null, '400-610-0400', null, null, null, null, null, '1468574473121', '0', '1');
INSERT INTO `t_express` VALUES ('2', '2', '/2/', null, '安信达', '安信达', 'anshenda', null, null, null, null, '021-54224681', null, null, null, null, null, '1468574473462', '0', '1');
INSERT INTO `t_express` VALUES ('3', '3', '/3/', null, '安能物流', '安能物流', 'annengwuliu', null, null, null, null, '400-104-0088', null, null, null, null, null, '1468574473486', '0', '1');
INSERT INTO `t_express` VALUES ('4', '4', '/4/', null, '安迅物流', '安迅物流', 'anxunwuliu', null, null, null, null, '010-59288730', null, null, null, null, null, '1468574473511', '0', '1');
INSERT INTO `t_express` VALUES ('5', '5', '/5/', null, 'BHT快递', 'BHT快递', 'BHTkuaidi', null, null, null, null, '010-58633508', null, null, null, null, null, '1468574473536', '0', '1');
INSERT INTO `t_express` VALUES ('6', '6', '/6/', null, '巴伦支', '巴伦支', 'balunzhi', null, null, null, null, '400-636-1516', null, null, null, null, null, '1468574473562', '0', '1');
INSERT INTO `t_express` VALUES ('7', '7', '/7/', null, '邦送物流', '邦送物流', 'bangsongwuliu', null, null, null, null, '021-20965696', null, null, null, null, null, '1468574473586', '0', '1');
INSERT INTO `t_express` VALUES ('8', '8', '/8/', null, '百福东方快递', '百福东方快递', 'baifudongfangkuaidi', null, null, null, null, '010-57169000', null, null, null, null, null, '1468574473611', '0', '1');
INSERT INTO `t_express` VALUES ('9', '9', '/9/', null, 'COE快递', 'COE快递', 'COEkuaidi', null, null, null, null, '0755-83575000', null, null, null, null, null, '1468574473636', '0', '1');
INSERT INTO `t_express` VALUES ('10', '10', '/10/', null, 'City-Link', 'City-Link', 'City-Link', null, null, null, null, '603-55658399', null, null, null, null, null, '1468574473661', '0', '1');
INSERT INTO `t_express` VALUES ('11', '11', '/11/', null, '传喜物流', '传喜物流', 'zhuanxiwuliu', null, null, null, null, '400-777-5656', null, null, null, null, null, '1468574473686', '0', '1');
INSERT INTO `t_express` VALUES ('12', '12', '/12/', null, 'DHL中国快递', 'DHL中国快递', 'DHLzhongguokuaidi', null, null, null, null, '800-810-8000', null, null, null, null, null, '1468574473710', '0', '1');
INSERT INTO `t_express` VALUES ('13', '13', '/13/', null, 'DPEX快递', 'DPEX快递', 'DPEXkuaidi', null, null, null, null, '021-64659883', null, null, null, null, null, '1468574473735', '0', '1');
INSERT INTO `t_express` VALUES ('14', '14', '/14/', null, 'D速快递', 'D速快递', 'Dsukuaidi', null, null, null, null, '0531-88636363', null, null, null, null, null, '1468574473772', '0', '1');
INSERT INTO `t_express` VALUES ('15', '15', '/15/', null, '大田物流', '大田物流', 'daitianwuliu', null, null, null, null, '400-626-1166', null, null, null, null, null, '1468574473797', '0', '1');
INSERT INTO `t_express` VALUES ('16', '16', '/16/', null, '递达速运', '递达速运', 'didasuyun', null, null, null, null, '400-687-8123', null, null, null, null, null, '1468574473822', '0', '1');
INSERT INTO `t_express` VALUES ('17', '17', '/17/', null, '递四方', '递四方', 'disifang', null, null, null, null, '0755-33933895', null, null, null, null, null, '1468574473847', '0', '1');
INSERT INTO `t_express` VALUES ('18', '18', '/18/', null, '大洋物流', '大洋物流', 'daiyangwuliu', null, null, null, null, '400-820-0088', null, null, null, null, null, '1468574473872', '0', '1');
INSERT INTO `t_express` VALUES ('19', '19', '/19/', null, 'EMS快递', 'EMS快递', 'EMSkuaidi', null, null, null, null, '11183', null, null, null, null, null, '1468574473897', '0', '1');
INSERT INTO `t_express` VALUES ('20', '20', '/20/', null, 'FedEx国际', 'FedEx国际', 'FedExguoji', null, null, null, null, '400-886-1888', null, null, null, null, null, '1468574473922', '0', '1');
INSERT INTO `t_express` VALUES ('21', '21', '/21/', null, 'FedEx美国件', 'FedEx美国件', 'FedExmeiguojian', null, null, null, null, '800-463-3339', null, null, null, null, null, '1468574473959', '0', '1');
INSERT INTO `t_express` VALUES ('22', '22', '/22/', null, '飞豹快递', '飞豹快递', 'feibaokuaidi', null, null, null, null, '400-000-5566', null, null, null, null, null, '1468574473984', '0', '1');
INSERT INTO `t_express` VALUES ('23', '23', '/23/', null, '风行天下快递', '风行天下快递', 'fengxingtianxiakuaidi', null, null, null, null, '400-040-4909', null, null, null, null, null, '1468574474009', '0', '1');
INSERT INTO `t_express` VALUES ('24', '24', '/24/', null, '凡宇快递', '凡宇快递', 'fanyukuaidi', null, null, null, null, '400-658-0358', null, null, null, null, null, '1468574474034', '0', '1');
INSERT INTO `t_express` VALUES ('25', '25', '/25/', null, '凡客配送', '凡客配送', 'fankepeisong', null, null, null, null, '400-010-6660', null, null, null, null, null, '1468574474059', '0', '1');
INSERT INTO `t_express` VALUES ('26', '26', '/26/', null, 'GLS快递', 'GLS快递', 'GLSkuaidi', null, null, null, null, '877-914-5465', null, null, null, null, null, '1468574474095', '0', '1');
INSERT INTO `t_express` VALUES ('27', '27', '/27/', null, 'GSM快递', 'GSM快递', 'GSMkuaidi', null, null, null, null, '021-64656011', null, null, null, null, null, '1468574474132', '0', '1');
INSERT INTO `t_express` VALUES ('28', '28', '/28/', null, '国通快递', '国通快递', 'guotongkuaidi', null, null, null, null, '400-111-1123', null, null, null, null, null, '1468574474157', '0', '1');
INSERT INTO `t_express` VALUES ('29', '29', '/29/', null, '共速达', '共速达', 'gongsuda', null, null, null, null, '400-111-0005', null, null, null, null, null, '1468574474182', '0', '1');
INSERT INTO `t_express` VALUES ('30', '30', '/30/', null, '百世快递快递', '百世快递快递', 'baishikuaidikuaidi', null, null, null, null, '400-956-5656', null, null, null, null, null, '1468574474232', '0', '1');
INSERT INTO `t_express` VALUES ('31', '31', '/31/', null, '海盟速递', '海盟速递', 'haimengsudi', null, null, null, null, '400-080-6369', null, null, null, null, null, '1468574474257', '0', '1');
INSERT INTO `t_express` VALUES ('32', '32', '/32/', null, '河北建华', '河北建华', 'hebeijianhua', null, null, null, null, '0311-86123186', null, null, null, null, null, '1468574474281', '0', '1');
INSERT INTO `t_express` VALUES ('33', '33', '/33/', null, '华企快运', '华企快运', 'huaqikuaiyun', null, null, null, null, '400-626-2356', null, null, null, null, null, '1468574474306', '0', '1');
INSERT INTO `t_express` VALUES ('34', '34', '/34/', null, '华夏龙', '华夏龙', 'huaxialong', null, null, null, null, '0755-61211999', null, null, null, null, null, '1468574474331', '0', '1');
INSERT INTO `t_express` VALUES ('35', '35', '/35/', null, '汇强快递', '汇强快递', 'huijiangkuaidi', null, null, null, null, '400-000-0177', null, null, null, null, null, '1468574474356', '0', '1');
INSERT INTO `t_express` VALUES ('36', '36', '/36/', null, '昊盛物流', '昊盛物流', 'haochengwuliu', null, null, null, null, '400-186-5566', null, null, null, null, null, '1468574474381', '0', '1');
INSERT INTO `t_express` VALUES ('37', '37', '/37/', null, '恒路物流', '恒路物流', 'hengluwuliu', null, null, null, null, '400-182-6666', null, null, null, null, null, '1468574474406', '0', '1');
INSERT INTO `t_express` VALUES ('38', '38', '/38/', null, '急先达', '急先达', 'jixianda', null, null, null, null, '400-694-1256', null, null, null, null, null, '1468574474446', '0', '1');
INSERT INTO `t_express` VALUES ('39', '39', '/39/', null, '捷特快递', '捷特快递', 'jietekuaidi', null, null, null, null, '400-820-8585', null, null, null, null, null, '1468574474484', '0', '1');
INSERT INTO `t_express` VALUES ('40', '40', '/40/', null, '佳吉快运', '佳吉快运', 'jiajikuaiyun', null, null, null, null, '400-820-5566', null, null, null, null, null, '1468574474509', '0', '1');
INSERT INTO `t_express` VALUES ('41', '41', '/41/', null, '京广速递', '京广速递', 'jingansudi', null, null, null, null, '0769-83660666', null, null, null, null, null, '1468574474534', '0', '1');
INSERT INTO `t_express` VALUES ('42', '42', '/42/', null, '嘉里大通', '嘉里大通', 'jialidaitong', null, null, null, null, '400-610-3188', null, null, null, null, null, '1468574474559', '0', '1');
INSERT INTO `t_express` VALUES ('43', '43', '/43/', null, '晋越快递', '晋越快递', 'jinyuekuaidi', null, null, null, null, '0769-85158039', null, null, null, null, null, '1468574474584', '0', '1');
INSERT INTO `t_express` VALUES ('44', '44', '/44/', null, '加运美', '加运美', 'jiayunmei', null, null, null, null, '0769-85515555', null, null, null, null, null, '1468574474609', '0', '1');
INSERT INTO `t_express` VALUES ('45', '45', '/45/', null, '佳怡物流', '佳怡物流', 'jiayiwuliu', null, null, null, null, '400-631-9999', null, null, null, null, null, '1468574474633', '0', '1');
INSERT INTO `t_express` VALUES ('46', '46', '/46/', null, '金大物流', '金大物流', 'jindaiwuliu', null, null, null, null, '0755-82262209', null, null, null, null, null, '1468574474658', '0', '1');
INSERT INTO `t_express` VALUES ('47', '47', '/47/', null, 'KCS快递', 'KCS快递', 'KCSkuaidi', null, null, null, null, '800-858-5590', null, null, null, null, null, '1468574474683', '0', '1');
INSERT INTO `t_express` VALUES ('48', '48', '/48/', null, '快捷速递', '快捷速递', 'kuaijiesudi', null, null, null, null, '4008333666', null, null, null, null, null, '1468574474721', '0', '1');
INSERT INTO `t_express` VALUES ('49', '49', '/49/', null, '跨越速运', '跨越速运', 'kuayuesuyun', null, null, null, null, '400-809-8098', null, null, null, null, null, '1468574474746', '0', '1');
INSERT INTO `t_express` VALUES ('50', '50', '/50/', null, '康力物流', '康力物流', 'kangliwuliu', null, null, null, null, '400-156-5156', null, null, null, null, null, '1468574474771', '0', '1');
INSERT INTO `t_express` VALUES ('51', '51', '/51/', null, '林道国际快递', '林道国际快递', 'lindaoguojikuaidi', null, null, null, null, '400-820-0112', null, null, null, null, null, '1468574474795', '0', '1');
INSERT INTO `t_express` VALUES ('52', '52', '/52/', null, '联邦快递', '联邦快递', 'lianbangkuaidi', null, null, null, null, '400-889-1888', null, null, null, null, null, '1468574474820', '0', '1');
INSERT INTO `t_express` VALUES ('53', '53', '/53/', null, '立即送', '立即送', 'lijisong', null, null, null, null, '400-028-5666', null, null, null, null, null, '1468574474845', '0', '1');
INSERT INTO `t_express` VALUES ('54', '54', '/54/', null, '乐捷递', '乐捷递', 'yuejiedi', null, null, null, null, '400-618-1400', null, null, null, null, null, '1468574474870', '0', '1');
INSERT INTO `t_express` VALUES ('55', '55', '/55/', null, '龙邦速递', '龙邦速递', 'longbangsudi', null, null, null, null, '021-39283333', null, null, null, null, null, '1468574474894', '0', '1');
INSERT INTO `t_express` VALUES ('56', '56', '/56/', null, '联昊通', '联昊通', 'lianhaotong', null, null, null, null, '0769-88620000', null, null, null, null, null, '1468574474932', '0', '1');
INSERT INTO `t_express` VALUES ('57', '57', '/57/', null, '民邦速递', '民邦速递', 'minbangsudi', null, null, null, null, '0769-81515303', null, null, null, null, null, '1468574474957', '0', '1');
INSERT INTO `t_express` VALUES ('58', '58', '/58/', null, '民航快递', '民航快递', 'minhangkuaidi', null, null, null, null, '400-817-4008', null, null, null, null, null, '1468574474982', '0', '1');
INSERT INTO `t_express` VALUES ('59', '59', '/59/', null, '明亮物流', '明亮物流', 'mingliangwuliu', null, null, null, null, '400-035-6568', null, null, null, null, null, '1468574475007', '0', '1');
INSERT INTO `t_express` VALUES ('60', '60', '/60/', null, '闽盛快递', '闽盛快递', 'minchengkuaidi', null, null, null, null, '0592-3725988', null, null, null, null, null, '1468574475032', '0', '1');
INSERT INTO `t_express` VALUES ('61', '61', '/61/', null, '门对门', '门对门', 'menduimen', null, null, null, null, '400-700-7676', null, null, null, null, null, '1468574475056', '0', '1');
INSERT INTO `t_express` VALUES ('62', '62', '/62/', null, '能达速递', '能达速递', 'nengdasudi', null, null, null, null, '400-6886-765', null, null, null, null, null, '1468574475081', '0', '1');
INSERT INTO `t_express` VALUES ('63', '63', '/63/', null, '偌亚奥快递', '偌亚奥快递', 'ruoyaaokuaidi', null, null, null, null, '400-887-1871', null, null, null, null, null, '1468574475106', '0', '1');
INSERT INTO `t_express` VALUES ('64', '64', '/64/', null, 'OCS快递', 'OCS快递', 'OCSkuaidi', null, null, null, null, '400-118-8588', null, null, null, null, null, '1468574475144', '0', '1');
INSERT INTO `t_express` VALUES ('65', '65', '/65/', null, 'OnTrac快递', 'OnTrac快递', 'OnTrackuaidi', null, null, null, null, '800-334-5000', null, null, null, null, null, '1468574475168', '0', '1');
INSERT INTO `t_express` VALUES ('66', '66', '/66/', null, '平安达腾飞', '平安达腾飞', 'pingandatengfei', null, null, null, null, '4006-230-009', null, null, null, null, null, '1468574475193', '0', '1');
INSERT INTO `t_express` VALUES ('67', '67', '/67/', null, '品速心达快递', '品速心达快递', 'pinsuxindakuaidi', null, null, null, null, '400-800-3693', null, null, null, null, null, '1468574475218', '0', '1');
INSERT INTO `t_express` VALUES ('68', '68', '/68/', null, '全一快递', '全一快递', 'quanyikuaidi', null, null, null, null, '400-663-1111', null, null, null, null, null, '1468574475243', '0', '1');
INSERT INTO `t_express` VALUES ('69', '69', '/69/', null, '全峰快递', '全峰快递', 'quanfengkuaidi', null, null, null, null, '400-100-0001', null, null, null, null, null, '1468574475268', '0', '1');
INSERT INTO `t_express` VALUES ('70', '70', '/70/', null, '全日通', '全日通', 'quanritong', null, null, null, null, '020-86298999', null, null, null, null, null, '1468574475293', '0', '1');
INSERT INTO `t_express` VALUES ('71', '71', '/71/', null, '全际通', '全际通', 'quanjitong', null, null, null, null, '400-0179-888', null, null, null, null, null, '1468574475368', '0', '1');
INSERT INTO `t_express` VALUES ('72', '72', '/72/', null, '全晨快递', '全晨快递', 'quanchenkuaidi', null, null, null, null, '0769-82026703', null, null, null, null, null, '1468574475393', '0', '1');
INSERT INTO `t_express` VALUES ('73', '73', '/73/', null, '七天连锁快递', '七天连锁快递', 'qitianliansuokuaidi', null, null, null, null, '400-882-1202', null, null, null, null, null, '1468574475418', '0', '1');
INSERT INTO `t_express` VALUES ('74', '74', '/74/', null, '如风达', '如风达', 'rufengda', null, null, null, null, '400-010-6660', null, null, null, null, null, '1468574475442', '0', '1');
INSERT INTO `t_express` VALUES ('75', '75', '/75/', null, '顺丰速运', '顺丰速运', 'shunfengsuyun', null, null, null, null, '95338', null, null, null, null, null, '1468574475467', '0', '1');
INSERT INTO `t_express` VALUES ('76', '76', '/76/', null, '申通快递', '申通快递', 'shentongkuaidi', null, null, null, null, '95543', null, null, null, null, null, '1468574475492', '0', '1');
INSERT INTO `t_express` VALUES ('77', '77', '/77/', null, '速尔快递', '速尔快递', 'suerkuaidi', null, null, null, null, '400-882-2168', null, null, null, null, null, '1468574475516', '0', '1');
INSERT INTO `t_express` VALUES ('78', '78', '/78/', null, '圣安物流', '圣安物流', 'shenganwuliu', null, null, null, null, '400-661-8169', null, null, null, null, null, '1468574475542', '0', '1');
INSERT INTO `t_express` VALUES ('79', '79', '/79/', null, '盛丰物流', '盛丰物流', 'chengfengwuliu', null, null, null, null, '0591-83621111', null, null, null, null, null, '1468574475580', '0', '1');
INSERT INTO `t_express` VALUES ('80', '80', '/80/', null, '三态速递', '三态速递', 'santaisudi', null, null, null, null, '400-881-8106', null, null, null, null, null, '1468574475604', '0', '1');
INSERT INTO `t_express` VALUES ('81', '81', '/81/', null, '盛辉物流', '盛辉物流', 'chenghuiwuliu', null, null, null, null, '400-822-2222', null, null, null, null, null, '1468574475629', '0', '1');
INSERT INTO `t_express` VALUES ('82', '82', '/82/', null, '上大物流', '上大物流', 'shangdawuliu', null, null, null, null, '021-54477891', null, null, null, null, null, '1468574475654', '0', '1');
INSERT INTO `t_express` VALUES ('83', '83', '/83/', null, '赛澳递快递', '赛澳递快递', 'saiaodikuaidi', null, null, null, null, '400-034-5888', null, null, null, null, null, '1468574475679', '0', '1');
INSERT INTO `t_express` VALUES ('84', '84', '/84/', null, '穗佳物流', '穗佳物流', 'suijiawuliu', null, null, null, null, '400-880-9771', null, null, null, null, null, '1468574475704', '0', '1');
INSERT INTO `t_express` VALUES ('85', '85', '/85/', null, 'TNT快递', 'TNT快递', 'TNTkuaidi', null, null, null, null, '800-820-9868', null, null, null, null, null, '1468574475728', '0', '1');
INSERT INTO `t_express` VALUES ('86', '86', '/86/', null, '天地华宇快递', '天地华宇快递', 'tiandehuayukuaidi', null, null, null, null, '400-808-6666', null, null, null, null, null, '1468574475754', '0', '1');
INSERT INTO `t_express` VALUES ('87', '87', '/87/', null, '通和天下快递', '通和天下快递', 'tonghaitianxiakuaidi', null, null, null, null, '400-0056-516', null, null, null, null, null, '1468574475779', '0', '1');
INSERT INTO `t_express` VALUES ('88', '88', '/88/', null, 'UPS快递', 'UPS快递', 'UPSkuaidi', null, null, null, null, '400-820-8388', null, null, null, null, null, '1468574475803', '0', '1');
INSERT INTO `t_express` VALUES ('89', '89', '/89/', null, 'USPS快递', 'USPS快递', 'USPSkuaidi', null, null, null, null, '800-275-8777', null, null, null, null, null, '1468574475828', '0', '1');
INSERT INTO `t_express` VALUES ('90', '90', '/90/', null, '微特派', '微特派', 'weitepai', null, null, null, null, '400-6363-000', null, null, null, null, null, '1468574475853', '0', '1');
INSERT INTO `t_express` VALUES ('91', '91', '/91/', null, '万象物流', '万象物流', 'wanxiangwuliu', null, null, null, null, '400-820-8088', null, null, null, null, null, '1468574475878', '0', '1');
INSERT INTO `t_express` VALUES ('92', '92', '/92/', null, '万家物流', '万家物流', 'mojiawuliu', null, null, null, null, '021-5193 7018', null, null, null, null, null, '1468574475903', '0', '1');
INSERT INTO `t_express` VALUES ('93', '93', '/93/', null, '新邦物流', '新邦物流', 'xinbangwuliu', null, null, null, null, '400-800-0222', null, null, null, null, null, '1468574475928', '0', '1');
INSERT INTO `t_express` VALUES ('94', '94', '/94/', null, '信丰物流', '信丰物流', 'xinfengwuliu', null, null, null, null, '400-830-6333', null, null, null, null, null, '1468574475953', '0', '1');
INSERT INTO `t_express` VALUES ('95', '95', '/95/', null, '新蛋物流', '新蛋物流', 'xindanwuliu', null, null, null, null, '400-820-4400', null, null, null, null, null, '1468574475977', '0', '1');
INSERT INTO `t_express` VALUES ('96', '96', '/96/', null, '祥龙运通', '祥龙运通', 'xianglongyuntong', null, null, null, null, '0755-88888908', null, null, null, null, null, '1468574476002', '0', '1');
INSERT INTO `t_express` VALUES ('97', '97', '/97/', null, '希优特', '希优特', 'xiyoute', null, null, null, null, '400-840-0365', null, null, null, null, null, '1468574476027', '0', '1');
INSERT INTO `t_express` VALUES ('98', '98', '/98/', null, '圆通速递', '圆通速递', 'yuantongsudi', null, null, null, null, '95554', null, null, null, null, null, '1468574476064', '0', '1');
INSERT INTO `t_express` VALUES ('99', '99', '/99/', null, '韵达快递', '韵达快递', 'yundakuaidi', null, null, null, null, '95546', null, null, null, null, null, '1468574476089', '0', '1');
INSERT INTO `t_express` VALUES ('100', '100', '/100/', null, '优速物流', '优速物流', 'yousuwuliu', null, null, null, null, '400-1111-119', null, null, null, null, null, '1468574476114', '0', '1');
INSERT INTO `t_express` VALUES ('101', '101', '/101/', null, '远成物流', '远成物流', 'yuanchengwuliu', null, null, null, null, '400-820-1646', null, null, null, null, null, '1468574476138', '0', '1');
INSERT INTO `t_express` VALUES ('102', '102', '/102/', null, '亿领速运', '亿领速运', 'yilingsuyun', null, null, null, null, '400-611-1892', null, null, null, null, null, '1468574476188', '0', '1');
INSERT INTO `t_express` VALUES ('103', '103', '/103/', null, '亿顺航', '亿顺航', 'yishunhang', null, null, null, null, '4006-018-268', null, null, null, null, null, '1468574476213', '0', '1');
INSERT INTO `t_express` VALUES ('104', '104', '/104/', null, '邮必佳', '邮必佳', 'youbijia', null, null, null, null, '400-687-8123', null, null, null, null, null, '1468574476238', '0', '1');
INSERT INTO `t_express` VALUES ('105', '105', '/105/', null, '易通达', '易通达', 'yitongda', null, null, null, null, '0898-65339299', null, null, null, null, null, '1468574476263', '0', '1');
INSERT INTO `t_express` VALUES ('106', '106', '/106/', null, '银捷速递', '银捷速递', 'yinjiesudi', null, null, null, null, '0755-88250666', null, null, null, null, null, '1468574476288', '0', '1');
INSERT INTO `t_express` VALUES ('107', '107', '/107/', null, '宇鑫物流', '宇鑫物流', 'yuxinwuliu', null, null, null, null, '0371-66368798', null, null, null, null, null, '1468574476313', '0', '1');
INSERT INTO `t_express` VALUES ('108', '108', '/108/', null, '源安达', '源安达', 'yuananda', null, null, null, null, '0769-85021875', null, null, null, null, null, '1468574476337', '0', '1');
INSERT INTO `t_express` VALUES ('109', '109', '/109/', null, '元智捷诚', '元智捷诚', 'yuanzhijiecheng', null, null, null, null, '400-081-2345', null, null, null, null, null, '1468574476363', '0', '1');
INSERT INTO `t_express` VALUES ('110', '110', '/110/', null, '一邦速递', '一邦速递', 'yibangsudi', null, null, null, null, '400-800-0666', null, null, null, null, null, '1468574476400', '0', '1');
INSERT INTO `t_express` VALUES ('111', '111', '/111/', null, '亚风速递', '亚风速递', 'yafengsudi', null, null, null, null, '400-628-0018', null, null, null, null, null, '1468574476425', '0', '1');
INSERT INTO `t_express` VALUES ('112', '112', '/112/', null, '源伟丰', '源伟丰', 'yuanweifeng', null, null, null, null, '400-601-2228', null, null, null, null, null, '1468574476450', '0', '1');
INSERT INTO `t_express` VALUES ('113', '113', '/113/', null, '越丰物流', '越丰物流', 'yuefengwuliu', null, null, null, null, '852-23909969', null, null, null, null, null, '1468574476487', '0', '1');
INSERT INTO `t_express` VALUES ('114', '114', '/114/', null, '原飞航快递', '原飞航快递', 'yuanfeihangkuaidi', null, null, null, null, '0755-29778899', null, null, null, null, null, '1468574476512', '0', '1');
INSERT INTO `t_express` VALUES ('115', '115', '/115/', null, '运通中港', '运通中港', 'yuntongzhonggang', null, null, null, null, '0769-81156999', null, null, null, null, null, '1468574476537', '0', '1');
INSERT INTO `t_express` VALUES ('116', '116', '/116/', null, '中通速递', '中通速递', 'zhongtongsudi', null, null, null, null, '95311', null, null, null, null, null, '1468574476574', '0', '1');
INSERT INTO `t_express` VALUES ('117', '117', '/117/', null, '中速快递', '中速快递', 'zhongsukuaidi', null, null, null, null, '11183', null, null, null, null, null, '1468574476612', '0', '1');
INSERT INTO `t_express` VALUES ('118', '118', '/118/', null, '中睿速递', '中睿速递', 'zhongruisudi', null, null, null, null, '400-0375-888', null, null, null, null, null, '1468574476686', '0', '1');
INSERT INTO `t_express` VALUES ('119', '119', '/119/', null, '中外运速递', '中外运速递', 'zhongwaiyunsudi', null, null, null, null, '010-8041 8611', null, null, null, null, null, '1468574476711', '0', '1');
INSERT INTO `t_express` VALUES ('120', '120', '/120/', null, '中外运速递', '中外运速递', 'zhongwaiyunsudi', null, null, null, null, '010-80418611', null, null, null, null, null, '1468574476736', '0', '1');
INSERT INTO `t_express` VALUES ('121', '121', '/121/', null, '中铁快运', '中铁快运', 'zhongtiekuaiyun', null, null, null, null, '95572', null, null, null, null, null, '1468574476760', '0', '1');
INSERT INTO `t_express` VALUES ('122', '122', '/122/', null, '中铁物流', '中铁物流', 'zhongtiewuliu', null, null, null, null, '400-000-5566', null, null, null, null, null, '1468574476786', '0', '1');
INSERT INTO `t_express` VALUES ('123', '123', '/123/', null, '郑州建华', '郑州建华', 'zhengzhoujianhua', null, null, null, null, '0371-65995266', null, null, null, null, null, '1468574476810', '0', '1');
INSERT INTO `t_express` VALUES ('124', '124', '/124/', null, '忠信达', '忠信达', 'zhongxinda', null, null, null, null, '400-646-6665', null, null, null, null, null, '1468574476835', '0', '1');
INSERT INTO `t_express` VALUES ('125', '125', '/125/', null, '中邮物流', '中邮物流', 'zhongyouwuliu', null, null, null, null, '11183', null, null, null, null, null, '1468574476860', '0', '1');

-- ----------------------------
-- Table structure for t_express_courier
-- ----------------------------
DROP TABLE IF EXISTS `t_express_courier`;
CREATE TABLE `t_express_courier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expressId` bigint(20) DEFAULT NULL COMMENT '快递单号',
  `courierId` bigint(20) DEFAULT NULL COMMENT '快递员编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快递公司与快递员关系表';

-- ----------------------------
-- Records of t_express_courier
-- ----------------------------
