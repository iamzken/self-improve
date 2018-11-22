

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(36) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `age` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '萧峰', '35');
INSERT INTO `user` VALUES ('2', '段誉', '22');
INSERT INTO `user` VALUES ('3', '虚竹', '21');
INSERT INTO `user` VALUES ('4', '慕容复', '28');
INSERT INTO `user` VALUES ('5', '玄慈', '66');
INSERT INTO `user` VALUES ('6', '钟灵', '19');
INSERT INTO `user` VALUES ('7', '宋长老', '56');
INSERT INTO `user` VALUES ('8', '李秋水', '90');
INSERT INTO `user` VALUES ('9', '木婉清', '20');
INSERT INTO `user` VALUES ('10', '慧方', '71');
INSERT INTO `user` VALUES ('11', '竹剑', '23');
INSERT INTO `user` VALUES ('12', '玄悲', '77');
INSERT INTO `user` VALUES ('13', '鸠摩智', '42');
