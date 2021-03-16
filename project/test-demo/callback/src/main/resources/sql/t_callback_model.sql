CREATE TABLE `t_callback_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(128) NOT NULL DEFAULT '',
  `product` varchar(32) NOT NULL DEFAULT '',
  `method` varchar(32) NOT NULL DEFAULT '',
  `event` varchar(64) NOT NULL DEFAULT '',
  `data` varchar(512) DEFAULT NULL,
  `partner` varchar(128) NOT NULL DEFAULT 'MAIN',
  PRIMARY KEY (`app_id`,`product`,`event`),
  KEY `callback_key_index` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;