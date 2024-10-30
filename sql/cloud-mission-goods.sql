SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `goods_name`  varchar(16)                                            DEFAULT NULL COMMENT '商品名称',
    `goods_img`   varchar(255)                                           DEFAULT NULL COMMENT '商品图片',
    `is_using`    bit(1)                                                 DEFAULT b'1' COMMENT '是否启用',
    `goods_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品标题',
    `goods_price` decimal(10, 0)                                         DEFAULT NULL COMMENT '商品价格',
    `goods_stock` int                                                    DEFAULT '0' COMMENT '商品库存',
    `start_time`  datetime                                               DEFAULT NULL COMMENT '秒杀开始时间',
    `end_time`    datetime                                               DEFAULT NULL COMMENT '秒杀结束时间',
    `created_at`  datetime                                               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  datetime                                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=UTF8MB4 COMMENT='商品表';

-- ----------------------------
-- Records of goods
-- ----------------------------
BEGIN;
INSERT INTO `goods` (`id`, `goods_name`, `goods_img`, `is_using`, `goods_title`, `goods_price`, `goods_stock`,
                     `start_time`, `end_time`, `created_at`, `updated_at`)
VALUES (1, '商品1', 'http://res.steveay.com/goods/36ba7b77b5994d04abfc662c80d86930.png', b'1', '商品1业务', 100, 200,
        '2024-04-01 00:00:00', '2025-01-01 00:00:00', '2022-03-02 18:21:14', '2022-06-28 23:39:42');
INSERT INTO `goods` (`id`, `goods_name`, `goods_img`, `is_using`, `goods_title`, `goods_price`, `goods_stock`,
                     `start_time`, `end_time`, `created_at`, `updated_at`)
VALUES (2, '商品2', 'http://res.steveay.com/goods/36ba7b77b5994d04abfc662c80d86930.png', b'1', '商品2业务', 30, 100,
        '2022-01-08 00:00:00', '2022-01-09 00:00:00', '2022-03-02 18:21:14', '2022-03-21 15:57:37');
INSERT INTO `goods` (`id`, `goods_name`, `goods_img`, `is_using`, `goods_title`, `goods_price`, `goods_stock`,
                     `start_time`, `end_time`, `created_at`, `updated_at`)
VALUES (3, '商品3', 'http://res.steveay.com/goods/36ba7b77b5994d04abfc662c80d86930.png', b'1', '商品3业务', 30, 100,
        '2022-03-08 00:00:00', '2022-03-09 00:00:00', '2022-03-02 18:21:14', '2022-03-21 15:57:41');
INSERT INTO `goods` (`id`, `goods_name`, `goods_img`, `is_using`, `goods_title`, `goods_price`, `goods_stock`,
                     `start_time`, `end_time`, `created_at`, `updated_at`)
VALUES (4, '商品4', 'http://res.steveay.com/goods/36ba7b77b5994d04abfc662c80d86930.png', b'1', '商品4业务', 30, 100,
        '2022-02-28 00:00:00', '2022-03-28 00:00:00', '2022-03-02 18:21:14', '2022-03-21 15:57:46');
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for seckill_notification
-- ----------------------------
DROP TABLE IF EXISTS `seckill_notification`;
CREATE TABLE `seckill_notification`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     bigint   DEFAULT NULL COMMENT '用户id',
    `goods_id`    bigint   DEFAULT NULL COMMENT '商品id',
    `is_notified` tinyint(1) DEFAULT '0' COMMENT '是否进行通知 0-未通知 1-已通知',
    `created_at`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=UTF8MB4 COMMENT='秒杀通知表';