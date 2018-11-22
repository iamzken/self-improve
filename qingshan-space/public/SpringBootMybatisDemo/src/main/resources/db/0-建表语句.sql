CREATE TABLE `customer` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `order_detail` (
  `order_id` int(11) NOT NULL COMMENT '订单号',
  `id` int(11) NOT NULL COMMENT '订单详情',
  `goods_id` int(11) DEFAULT NULL COMMENT '货品ID',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `is_pay` int(2) DEFAULT NULL COMMENT '支付状态',
  `is_ship` int(2) DEFAULT NULL COMMENT '是否发货',
  `status` int(2) DEFAULT NULL COMMENT '订单详情状态',
  PRIMARY KEY (`order_id`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_info` (
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `nums` int(11) DEFAULT NULL COMMENT '商品数量',
  `state` int(2) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

