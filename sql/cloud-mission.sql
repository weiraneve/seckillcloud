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

 Date: 28/12/2021 19:44:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片',
  `goods_detail` longtext COMMENT '商品介绍详情',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
  `create_date` datetime DEFAULT NULL COMMENT '生成时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新商品的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
BEGIN;
INSERT INTO `goods` VALUES (1, '比特币', '银行比特币业务', '/img/bitcoin.jpg', '加密货币始祖', 100.00, 100, '2021-12-01 00:00:00', '2021-12-01 00:00:00');
INSERT INTO `goods` VALUES (2, '狗狗币', '银行狗狗币业务', '/img/dogecoin.jpg', '货币流通性强大无比', 100.00, 100, '2021-12-01 00:00:00', '2021-12-01 00:00:00');
INSERT INTO `goods` VALUES (3, '以太坊币', '银行以太坊币业务', '/img/ethereum.jpg', '加密货币二大爷', 100.00, 100, '2021-12-01 00:00:00', '2021-12-01 00:00:00');
INSERT INTO `goods` VALUES (4, 'shi币', '银行shi币业务', '/img/shiba.jpg', '创意无限', 100.00, 100, '2021-12-01 00:00:00', '2021-12-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `addr_id` bigint DEFAULT NULL COMMENT '收货地址id',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int DEFAULT NULL COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `order_channel` int DEFAULT '0' COMMENT '支付通道：1 PC、2 Android、3 ios',
  `status` int DEFAULT NULL COMMENT '订单状态：0 未支付，1已支付，2 已发货，3 已收货，4 已退款，‘5 已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单生成的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `id` bigint NOT NULL AUTO_INCREMENT,
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `seckill_price` decimal(10,2) DEFAULT NULL COMMENT '秒杀价',
  `stock_count` int DEFAULT NULL COMMENT '秒杀剩余数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  `version` int DEFAULT NULL COMMENT '版本号',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_date` datetime DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
BEGIN;
INSERT INTO `seckill_goods` VALUES (1, 1, 99.00, 100, '2021-12-01 00:00:00', '2021-12-31 00:00:00', 0, '2021-12-01 00:00:00', '2021-12-01 00:00:00');
INSERT INTO `seckill_goods` VALUES (2, 2, 99.00, 100, '2021-12-01 00:00:00', '2021-12-31 00:00:00', 0, '2021-12-01 00:00:00', '2021-12-01 00:00:00');
INSERT INTO `seckill_goods` VALUES (3, 3, 99.00, 100, '2021-12-01 00:00:00', '2021-12-31 00:00:00', 0, '2021-12-01 00:00:00', '2021-12-01 00:00:00');
INSERT INTO `seckill_goods` VALUES (4, 4, 99.00, 100, '2021-12-01 00:00:00', '2021-12-31 00:00:00', 0, '2021-12-01 00:00:00', '2021-12-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `seckill_order`;
CREATE TABLE `seckill_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `order_id` bigint DEFAULT NULL COMMENT '订单id',
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `create_date` datetime DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_userid_goodsid` (`user_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill_order
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
