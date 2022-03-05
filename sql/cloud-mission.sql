/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : cloud-mission

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 05/03/2022 20:28:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片',
  `is_using` bit(1) DEFAULT b'1' COMMENT '是否启用',
  `goods_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品标题',
  `goods_price` decimal(10,0) DEFAULT NULL COMMENT '商品价格',
  `goods_stock` int DEFAULT '0' COMMENT '商品库存',
  `start_time` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='商品表\n';

-- ----------------------------
-- Records of goods
-- ----------------------------
BEGIN;
INSERT INTO `goods` VALUES (1, '比特币', 'http://rel.weiran.ltd/goods/ab3aa8ad9145404caa4709d37e5f990b.jpg', b'1', '银行比特币业务', 100, 200, '2022-02-28 00:00:00', '2022-03-28 00:00:00', '2022-03-02 18:21:14', '2022-03-05 19:26:35');
INSERT INTO `goods` VALUES (2, '狗狗币', 'http://rel.weiran.ltd/goods/9cada8ddcd8b47a1b45edf1b16156103.jpg', b'1', '银行狗狗币业务', 30, 100, '2022-01-08 00:00:00', '2022-01-09 00:00:00', '2022-03-02 18:21:14', '2022-03-02 18:21:14');
INSERT INTO `goods` VALUES (3, '以太坊币', 'http://rel.weiran.ltd/goods/0ae53d1d33104f2c8294ecdd8f8c38cc.jpg', b'1', '银行以太坊币业务', 30, 100, '2022-03-08 00:00:00', '2022-03-09 00:00:00', '2022-03-02 18:21:14', '2022-03-02 18:21:14');
INSERT INTO `goods` VALUES (4, 'shi币', 'http://rel.weiran.ltd/goods/e5a4c197883041f2a12bda5caafca978.jpg', b'1', '银行shi币业务', 30, 100, '2022-02-28 00:00:00', '2022-03-08 00:00:00', '2022-03-02 18:21:14', '2022-03-02 18:21:14');
COMMIT;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of order_info
-- ----------------------------
BEGIN;
INSERT INTO `order_info` VALUES (1, 1, 1, '2022-03-02 18:20:39', '2022-03-05 20:27:12');
COMMIT;

-- ----------------------------
-- Table structure for seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `stock_count` int DEFAULT '0' COMMENT '剩余库存',
  `version` int DEFAULT NULL COMMENT '版本号',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='库存表';

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
BEGIN;
INSERT INTO `seckill_goods` VALUES (1, 1, 200, 12, '2022-03-02 18:21:36', '2022-03-05 19:26:35');
INSERT INTO `seckill_goods` VALUES (2, 2, 100, 0, '2022-03-02 18:21:36', '2022-03-02 18:21:36');
INSERT INTO `seckill_goods` VALUES (3, 3, 100, 0, '2022-03-02 18:21:36', '2022-03-02 18:21:36');
INSERT INTO `seckill_goods` VALUES (4, 4, 99, 1, '2022-03-02 18:21:36', '2022-03-02 18:21:36');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
