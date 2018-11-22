CREATE TABLE `merchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商户号',
  `name` varchar(64) NOT NULL COMMENT '商户名称',
  `address` varchar(128) DEFAULT NULL COMMENT '商户地址',
  `accountNo` varchar(32) DEFAULT NULL COMMENT '账号',
  `accountName` varchar(128) DEFAULT NULL COMMENT '户名',
  `state` char(1) DEFAULT '1' COMMENT '商户状态 1激活 0关闭',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
