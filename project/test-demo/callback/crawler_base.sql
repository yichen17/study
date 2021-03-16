/*
 Navicat Premium Data Transfer

 Source Server         : 本机 子系统 unbutu
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : 127.0.0.1:3306
 Source Schema         : crawler_base

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 15/03/2021 15:55:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_callback_config
-- ----------------------------
DROP TABLE IF EXISTS `t_callback_config`;
CREATE TABLE `t_callback_config`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `partner` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `headers` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `notify_event` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `app_id`(`app_id`, `product`, `notify_event`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 217 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_callback_config
-- ----------------------------
INSERT INTO `t_callback_config` VALUES (3, 'e5f34af6fc3f461cad551bb990a85844', 'MAIN', 'QHPL', '{\r\n\r\n\"abc\":\"12323\"\r\n\r\n}', 'task.success', 'http://localhost:8080/show');

-- ----------------------------
-- Table structure for t_callback_log
-- ----------------------------
DROP TABLE IF EXISTS `t_callback_log`;
CREATE TABLE `t_callback_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商户唯一号',
  `task_key` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务号',
  `notify_event` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '回调时间，task，bill，report等',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `success` tinyint(0) NULL DEFAULT -1 COMMENT '-1: 初始状态， 0：回调成功， 1：回调失败',
  `callback_times` tinyint(0) NULL DEFAULT 0,
  `step` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '回调当前阶段',
  `from_channel` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调渠道来源',
  `trans_no` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '流水号',
  `event` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调时间记录',
  `is_manual` tinyint(1) NULL DEFAULT NULL COMMENT '是否为商户自服务重推',
  `callback_data` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调给业务方数据',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '开始回调时间',
  `invoke_time` datetime(0) NULL DEFAULT NULL COMMENT '回调触发时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '回调结束时间',
  `status_code` smallint(0) NULL DEFAULT NULL COMMENT '回调返回码',
  `partner` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `product` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'NONE',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_key_step_idx`(`id`) USING BTREE,
  INDEX `task_key`(`task_key`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE,
  INDEX `202369`(`app_id`, `product`) USING BTREE,
  INDEX `trans_no`(`trans_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13623 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact PARTITION BY KEY (`id`)
PARTITIONS 32
(PARTITION `p0` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p1` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p10` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p11` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p12` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p13` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p14` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p15` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p16` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p17` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p18` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p19` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p20` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p21` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p22` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p23` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p24` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p25` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p26` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p27` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p28` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p29` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p3` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p30` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p31` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p4` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p5` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p6` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p7` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p8` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p9` ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 )
;

-- ----------------------------
-- Records of t_callback_log
-- ----------------------------
INSERT INTO `t_callback_log` VALUES (13625, 'e5f34af6fc3f461cad551bb990a85844', 'yichen gogogo', 'task.success', '2021-03-15 15:04:22', -1, 0, 'START', NULL, '0c4401a0-6441-49b9-ad92-bef128723a47', NULL, NULL, NULL, '2021-03-15 15:04:22', NULL, NULL, NULL, 'MAIN', 'QHPL');
INSERT INTO `t_callback_log` VALUES (13628, 'e5f34af6fc3f461cad551bb990a85844', 'yichen gogogo', 'task.success', '2021-03-15 15:53:02', 1, 0, 'FAILED', NULL, '4f35b57a-fd2a-48c6-b00f-a2c888b014f5', NULL, NULL, '{\"notify_event\":\"task.success\",\"product\":\"QHPL\",\"task_key\":\"yichen gogogo\",\"name\":\"yichen\",\"app_id\":\"e5f34af6fc3f461cad551bb990a85844\",\"timestamp\":\"1615794781948\"}', '2021-03-15 15:53:02', NULL, '2021-03-15 15:53:02', 200, 'MAIN', 'QHPL');
INSERT INTO `t_callback_log` VALUES (13626, 'e5f34af6fc3f461cad551bb990a85844', 'yichen gogogo', 'task.success', '2021-03-15 15:06:49', 1, 0, 'FAILED', NULL, '614a4646-3687-4343-a793-feff25c989c5', NULL, NULL, '{\"notify_event\":\"task.success\",\"product\":\"QHPL\",\"task_key\":\"yichen gogogo\",\"app_id\":\"e5f34af6fc3f461cad551bb990a85844\",\"timestamp\":\"1615792008888\"}', '2021-03-15 15:06:49', NULL, '2021-03-15 15:06:49', 200, 'MAIN', 'QHPL');
INSERT INTO `t_callback_log` VALUES (13627, 'e5f34af6fc3f461cad551bb990a85844', 'yichen gogogo', 'task.success', '2021-03-15 15:16:14', 1, 0, 'FAILED', NULL, 'ffc0e6e4-eb9f-4ba0-8f91-d6f164206e0c', NULL, NULL, '{\"notify_event\":\"task.success\",\"product\":\"QHPL\",\"task_key\":\"yichen gogogo\",\"name\":\"yichen\",\"app_id\":\"e5f34af6fc3f461cad551bb990a85844\",\"timestamp\":\"1615792574158\"}', '2021-03-15 15:16:14', NULL, '2021-03-15 15:16:15', 200, 'MAIN', 'QHPL');
INSERT INTO `t_callback_log` VALUES (13624, 'e5f34af6fc3f461cad551bb990a85844', 'yichen gogogo', 'task.success', '2021-03-15 15:01:29', -1, 0, 'START', NULL, '02fe91d6-3d2b-418d-98b2-849504e4d701', NULL, NULL, NULL, '2021-03-15 15:01:29', NULL, NULL, NULL, 'MAIN', 'QHPL');
INSERT INTO `t_callback_log` VALUES (13623, 'e5f34af6fc3f461cad551bb990a85844', 'yichen gogogo', 'task.success', '2021-03-15 14:57:09', -1, 0, 'START', NULL, 'b74705cb-da15-46be-bbec-5686e11cffd1', NULL, NULL, NULL, '2021-03-15 14:57:09', NULL, NULL, NULL, 'MAIN', 'QHPL');

-- ----------------------------
-- Table structure for t_callback_model
-- ----------------------------
DROP TABLE IF EXISTS `t_callback_model`;
CREATE TABLE `t_callback_model`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `product` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `method` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `event` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `data` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `partner` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'MAIN',
  PRIMARY KEY (`app_id`, `product`, `event`, `partner`) USING BTREE,
  INDEX `callback_key_index`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 205 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_callback_model
-- ----------------------------
INSERT INTO `t_callback_model` VALUES (3, 'e5f34af6fc3f461cad551bb990a85844', 'QHPL', 'form', 'task.success', '{\r\n\r\n	\"task_key\":{\r\n		\"fixed\":false,\r\n		\"pairKey\":\"task_key\"\r\n		\r\n	},\r\n		\"status_code\":{\r\n		\"fixed\":true,\r\n		\"pairKey\":\"status_code\",\r\n		\"fixedValue\":\"360\"\r\n		\r\n	},\r\n		\"product\":{\r\n		\"fixed\":false,\r\n		\"pairKey\":\"product\"\r\n		\r\n	},\r\n		\"timestamp\":{\r\n		\"fixed\":false,\r\n		\"pairKey\":\"timestamp\"\r\n		\r\n	}\r\n}', 'MAIN');

SET FOREIGN_KEY_CHECKS = 1;
