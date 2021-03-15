CREATE TABLE `t_callback_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(128) NOT NULL,
  `partner` varchar(128) NOT NULL,
  `product` varchar(32) NOT NULL DEFAULT '',
  `headers` text,
  `notify_event` varchar(128) DEFAULT NULL,
  `url` text,
  PRIMARY KEY (`id`),
  KEY `app_id` (`app_id`,`product`,`notify_event`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;