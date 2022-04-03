/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : cloud-uaa

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/03/2022 21:01:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `exceed_year` int DEFAULT NULL COMMENT '规定逾期年份',
  `exceed_count` int DEFAULT NULL COMMENT '规定逾期次数',
  `exceed_money` int DEFAULT NULL COMMENT '规定逾期金额',
  `exceed_day` int DEFAULT NULL COMMENT '规定逾期天数之内还清',
  `limit_age` int DEFAULT NULL COMMENT '限定客户年龄',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='筛选规则表';

-- ----------------------------
-- Records of rule
-- ----------------------------
BEGIN;
INSERT INTO `rule` VALUES (1, 3, 2, 1000, 3, 18, '2022-03-02 18:27:32', '2022-03-17 10:31:10');
COMMIT;

-- ----------------------------
-- Table structure for sift
-- ----------------------------
DROP TABLE IF EXISTS `sift`;
CREATE TABLE `sift` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `sift_pass` int DEFAULT NULL COMMENT '初筛是否通过(0未通过 1通过)',
  `identity_card_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证号',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='初筛表';

-- ----------------------------
-- Records of sift
-- ----------------------------
BEGIN;
INSERT INTO `sift` VALUES (1, 1, 1, '510302200010300531', '2022-03-02 18:27:46', '2022-03-15 11:39:40');
INSERT INTO `sift` VALUES (2, 2, 1, '510302199910310531', '2022-03-15 11:40:29', '2022-03-15 11:43:33');
COMMIT;

-- ----------------------------
-- Table structure for status
-- ----------------------------
DROP TABLE IF EXISTS `status`;
CREATE TABLE `status` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `identity_card_id` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `exceed_record` int DEFAULT NULL COMMENT '贷款逾期记录(拒绝3年内逾期2次以上，金额小于 1000 元，3 天内还清的除外)',
  `work_status` varchar(20) DEFAULT NULL COMMENT '客户工作状态(拒绝无业、失业)',
  `dishonest` tinyint DEFAULT NULL COMMENT '客户是否被列入失信名单(拒绝被列入名单)',
  `age` int DEFAULT NULL COMMENT '客户年龄(拒绝小于18岁)',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='状态表';

-- ----------------------------
-- Records of status
-- ----------------------------
BEGIN;
INSERT INTO `status` VALUES (1, 1, '510302200010300534', 0, '0', 0, 0, '2022-03-02 18:27:54', '2022-03-10 22:58:02');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录手机号',
  `password` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `identity_card_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证号',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '孙念', '18077200000', '9508534c15e39a3f5a586aec9be941f6c249e646e06857ea0c3b0c33545dc679', '510302200010300531', '2022-02-26 16:22:19', '2022-03-25 20:59:09', '2022-03-25 20:59:31');
INSERT INTO `user` VALUES (2, 'sun', '18077200001', '9508534c15e39a3f5a586aec9be941f6c249e646e06857ea0c3b0c33545dc679', '510302199910310531', '2022-03-15 11:40:17', '2022-03-25 20:51:07', '2022-03-21 02:36:03');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
