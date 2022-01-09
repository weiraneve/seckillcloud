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

 Date: 03/01/2022 17:06:12
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
  `goods_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品标题',
  `goods_price` decimal(10,0) DEFAULT NULL COMMENT '商品价价',
  `goods_stock` int DEFAULT '0' COMMENT '商品库存',
  `start_time` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '生成时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新商品的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='商品表\n';

-- ----------------------------
-- Records of goods
-- ----------------------------
BEGIN;
INSERT INTO `goods` VALUES (1, '比特币', '/img/bitcoin.jpg', '银行比特币业务', 99, 100, '2022-01-01 00:00:00', '2022-06-01 00:00:00', '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO `goods` VALUES (2, '狗狗币', '/img/dogecoin.jpg', '银行狗狗币业务', 99, 100, '2022-01-01 00:00:00', '2022-06-01 00:00:00', '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO `goods` VALUES (3, '以太坊币', '/img/ethereum.jpg', '银行以太坊币业务', 99, 100, '2022-01-01 00:00:00', '2022-06-01 00:00:00', '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO `goods` VALUES (4, 'shi币', '/img/shiba.jpg', '银行shi币业务', 99, 100, '2022-01-01 00:00:00', '2022-06-01 00:00:00', '2022-01-01 00:00:00', '2022-01-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `create_time` datetime DEFAULT NULL COMMENT '订单生成的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of order_info
-- ----------------------------
BEGIN;
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
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='库存表';

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
BEGIN;
INSERT INTO `seckill_goods` VALUES (1, 1, 100, 0, '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO `seckill_goods` VALUES (2, 2, 100, 0, '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO `seckill_goods` VALUES (3, 3, 100, 0, '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO `seckill_goods` VALUES (4, 4, 100, 0, '2022-01-01 00:00:00', '2022-01-01 00:00:00');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
