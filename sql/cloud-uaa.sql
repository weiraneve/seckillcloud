/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : cloud-uaa

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 31/12/2021 15:59:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for loan
-- ----------------------------
DROP TABLE IF EXISTS `loan`;
CREATE TABLE `loan` (
  `id` bigint NOT NULL COMMENT '主键',
  `identity_card_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '身份证号',
  `create_date` datetime DEFAULT NULL COMMENT '生成时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户贷款表';

-- ----------------------------
-- Records of loan
-- ----------------------------
BEGIN;
INSERT INTO `loan` VALUES (1, '510302200010300534', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sift
-- ----------------------------
DROP TABLE IF EXISTS `sift`;
CREATE TABLE `sift` (
  `id` bigint NOT NULL,
  `sift_pass` tinyint DEFAULT NULL COMMENT '初筛是否通过',
  `identity_card_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证号',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `create_date` datetime DEFAULT NULL COMMENT '生成时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='秒杀初筛表';

-- ----------------------------
-- Records of sift
-- ----------------------------
BEGIN;
INSERT INTO `sift` VALUES (1, 1, '510302200010300534', '孙念', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for status
-- ----------------------------
DROP TABLE IF EXISTS `status`;
CREATE TABLE `status` (
  `id` bigint NOT NULL,
  `identity_card_id` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `exceed_record` int DEFAULT NULL COMMENT '贷款逾期记录(拒绝3年内逾期2次以上，金额小于 1000 元，3 天内还清的除外)',
  `work_status` varchar(20) DEFAULT NULL COMMENT '客户工作状态(拒绝无业、失业)',
  `dishonest` tinyint DEFAULT NULL COMMENT '客户是否被列入失信名单(拒绝被列入名单)',
  `age` int DEFAULT NULL COMMENT '客户年龄(拒绝小于18岁)',
  `create_date` datetime DEFAULT NULL COMMENT '生成时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户状态表';

-- ----------------------------
-- Records of status
-- ----------------------------
BEGIN;
INSERT INTO `status` VALUES (1, '510302200010300534', 1, '就业', 0, 22, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登陆手机号',
  `password` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `identity_card_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证号',
  `login_count` int DEFAULT NULL COMMENT '登陆记录',
  `create_date` datetime DEFAULT NULL COMMENT '生成时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后一次登陆时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '孙念', '18077200000', 'ae2fe40a6242ef07a35a30da2232e10a', '510302200010300534', 1, '2021-12-01 00:00:00', '2021-12-01 00:00:00');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
