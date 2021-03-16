CREATE TABLE `t_callback_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(64) DEFAULT NULL COMMENT '商户唯一号',
  `task_key` varchar(128) NOT NULL COMMENT '任务号',
  `notify_event` varchar(32) NOT NULL DEFAULT '' COMMENT '回调时间，task，bill，report等',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `success` tinyint(11) DEFAULT '-1' COMMENT '-1: 初始状态， 0：回调成功， 1：回调失败',
  `callback_times` tinyint(11) DEFAULT '0',
  `step` varchar(32) NOT NULL DEFAULT '' COMMENT '回调当前阶段',
  `from_channel` varchar(16) DEFAULT NULL COMMENT '回调渠道来源',
  `trans_no` varchar(36) NOT NULL DEFAULT '' COMMENT '流水号',
  `event` varchar(512) DEFAULT NULL COMMENT '回调时间记录',
  `is_manual` tinyint(1) DEFAULT NULL COMMENT '是否为商户自服务重推',
  `callback_data` varchar(1024) DEFAULT NULL COMMENT '回调给业务方数据',
  `create_time` datetime DEFAULT NULL COMMENT '开始回调时间',
  `invoke_time` datetime DEFAULT NULL COMMENT '回调触发时间',
  `end_time` datetime DEFAULT NULL COMMENT '回调结束时间',
  `status_code` smallint(2) DEFAULT NULL COMMENT '回调返回码',
  `partner` varchar(32) NOT NULL DEFAULT '',
  `product` varchar(16) DEFAULT 'NONE',
  PRIMARY KEY (`trans_no`),
  KEY `task_key_step_idx` (`id`) USING BTREE,
  KEY `task_key` (`task_key`),
  KEY `app_id` (`app_id`,`product`),
  KEY `create_time` (`create_time`),
  KEY `update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8
PARTITION BY KEY (trans_no)
PARTITIONS 32;

