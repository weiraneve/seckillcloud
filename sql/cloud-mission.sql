/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : cloud-mission

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 30/04/2022 11:10:04
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COMMENT='商品表\n';

-- ----------------------------
-- Records of goods
-- ----------------------------
BEGIN;
INSERT INTO `goods` (`id`, `goods_name`, `goods_img`, `is_using`, `goods_title`, `goods_price`, `goods_stock`, `start_time`, `end_time`, `created_at`, `updated_at`) VALUES (1, '商品1', 'http://rel.weiran.ltd/goods/97d9ce37a3e8402aa56cd753c2d01b1f.jpg', b'1', '商品1业务', 100, 200, '2022-04-01 00:00:00', '2022-12-01 00:00:00', '2022-03-02 18:21:14', '2022-03-25 14:30:44');
INSERT INTO `goods` (`id`, `goods_name`, `goods_img`, `is_using`, `goods_title`, `goods_price`, `goods_stock`, `start_time`, `end_time`, `created_at`, `updated_at`) VALUES (2, '商品2', 'http://rel.weiran.ltd/goods/11b1512bf69d405b8cef35ee70dfba1f.jpg', b'1', '商品2业务', 30, 100, '2022-01-08 00:00:00', '2022-01-09 00:00:00', '2022-03-02 18:21:14', '2022-03-21 15:57:37');
INSERT INTO `goods` (`id`, `goods_name`, `goods_img`, `is_using`, `goods_title`, `goods_price`, `goods_stock`, `start_time`, `end_time`, `created_at`, `updated_at`) VALUES (3, '商品3', 'http://rel.weiran.ltd/goods/02213bd5574b4648b44764d51a75fb87.jpg', b'1', '商品3业务', 30, 100, '2022-03-08 00:00:00', '2022-03-09 00:00:00', '2022-03-02 18:21:14', '2022-03-21 15:57:41');
INSERT INTO `goods` (`id`, `goods_name`, `goods_img`, `is_using`, `goods_title`, `goods_price`, `goods_stock`, `start_time`, `end_time`, `created_at`, `updated_at`) VALUES (4, '商品4', 'http://rel.weiran.ltd/goods/030f9b57692a46b8a0bbca557aca2653.jpg', b'1', '商品4业务', 30, 100, '2022-02-28 00:00:00', '2022-03-28 00:00:00', '2022-03-02 18:21:14', '2022-03-21 15:57:46');
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
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_goods_id` (`goods_id`),
  CONSTRAINT `order_goods_id` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='订单表';

-- ----------------------------
-- Records of order_info
-- ----------------------------
BEGIN;
INSERT INTO `order_info` (`id`, `user_id`, `goods_id`, `created_at`, `updated_at`) VALUES (1000001, 1, 1, '2022-04-30 11:09:54', '2022-04-30 11:09:54');
COMMIT;

-- ----------------------------
-- Table structure for seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `stock_count` int DEFAULT '0' COMMENT '剩余库存',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `seckill_goods_id` (`goods_id`),
  CONSTRAINT `seckill_goods_id` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COMMENT='库存表';

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
BEGIN;
INSERT INTO `seckill_goods` (`id`, `goods_id`, `stock_count`, `created_at`, `updated_at`) VALUES (1, 1, 200, '2022-03-02 18:21:36', '2022-03-25 21:01:08');
INSERT INTO `seckill_goods` (`id`, `goods_id`, `stock_count`, `created_at`, `updated_at`) VALUES (2, 2, 100, '2022-03-02 18:21:36', '2022-03-25 21:01:11');
INSERT INTO `seckill_goods` (`id`, `goods_id`, `stock_count`, `created_at`, `updated_at`) VALUES (3, 3, 100, '2022-03-02 18:21:36', '2022-03-25 21:01:14');
INSERT INTO `seckill_goods` (`id`, `goods_id`, `stock_count`, `created_at`, `updated_at`) VALUES (4, 4, 100, '2022-03-02 18:21:36', '2022-03-25 21:01:16');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
