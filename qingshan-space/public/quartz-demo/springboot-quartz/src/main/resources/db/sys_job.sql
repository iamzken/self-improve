CREATE database gupao;

CREATE TABLE `sys_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `job_name` varchar(512) NOT NULL COMMENT '任务名称',
  `job_group` varchar(512) NOT NULL COMMENT '任务组名',
  `job_cron` varchar(512) NOT NULL COMMENT '时间表达式',
  `job_class_path` varchar(1024) NOT NULL COMMENT '类路径,全类型',
  `job_data_map` varchar(1024) DEFAULT NULL COMMENT '传递map参数',
  `job_status` int(2) NOT NULL COMMENT '状态:1启用 0停用',
  `job_describe` varchar(1024) DEFAULT NULL COMMENT '任务功能描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- 插入3条数据，3个任务
-- 注意第三条，是一个发送邮件的任务，需要改成你自己的QQ和授权码。不知道什么是授权码的自己百度。
INSERT INTO `sys_job` (`id`, `job_name`, `job_group`, `job_cron`, `job_class_path`, `job_data_map`, `job_status`, `job_describe`) VALUES (22, 'test', 'test', '*/20 * * * * ?', 'com.gupaoedu.demo.task.TestTask1', NULL, 1, 'a job a');
INSERT INTO `sys_job` (`id`, `job_name`, `job_group`, `job_cron`, `job_class_path`, `job_data_map`, `job_status`, `job_describe`) VALUES (23, 'test2', 'test', '*/30 * * * * ?', 'com.gupaoedu.demo.task.TestTask2', NULL, 1, 'another job');
INSERT INTO `sys_job` (`id`, `job_name`, `job_group`, `job_cron`, `job_class_path`, `job_data_map`, `job_status`, `job_describe`) VALUES (24, 'test3', 'mail', '*/10 * * * * ?', 'com.gupaoedu.demo.task.TestTask3', '{\"data\":{\"loginAccount\":\"改成你的QQ邮箱\",\"loginAuthCode\":\"改成你的邮箱授权码\",\"sender\":\"改成你的QQ邮箱\",\"emailContent\":\"&nbsp;&nbsp;&nbsp;&nbsp;你好，我是蒋介石的私生子，我在台湾有2000亿新台币冻结了。我现在在古交，又回不了台湾。所以没办法，只要你给我转1000块钱帮我解冻我的账号，我在台湾有我自己的部队。要是你今天帮了我，等我回到台湾给你留一个三军统帅的位置，另外再给你200亿人民币，我建行账号158158745745110蒋宽。这是我女秘书的账号，打了钱通知我，我给你安排专机接你来台。\",\"emailContentType\":\"text/html;charset=utf-8\",\"emailSubject\":\"十万火急\",\"recipients\":\"改成你要的收件人邮箱，可以有多个，英文逗号隔开\"}}', 1, 'fdsafdfds');
