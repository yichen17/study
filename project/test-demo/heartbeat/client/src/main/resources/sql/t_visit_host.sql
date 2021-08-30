/*
 Navicat Premium Data Transfer

 Source Server         : mysql7 3307端口
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3307
 Source Schema         : netty

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 30/08/2021 14:42:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_visit_host
-- ----------------------------
DROP TABLE IF EXISTS `t_visit_host`;
CREATE TABLE `t_visit_host`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ipv4请求地址',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '该ip地址首次创建时间',
  `pre_reject_time` timestamp(0) NULL DEFAULT NULL COMMENT '前一次拒绝时间',
  `status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y' COMMENT '启用状态 Y启动 N禁用',
  `reject_times` tinyint(1) NOT NULL DEFAULT 0 COMMENT '拒绝次数，到3次禁用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_ip`(`ip`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '访问主机的ip信息列表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
