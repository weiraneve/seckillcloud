SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods`
(
    `goods_id`    bigint NOT NULL COMMENT '商品id',
    `stock_count` int    NOT NULL DEFAULT '0' COMMENT '剩余库存',
    `created_at`  datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  datetime        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4 COMMENT='库存表';

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
BEGIN;
INSERT INTO `seckill_goods` (`goods_id`, `stock_count`, `created_at`, `updated_at`)
VALUES (1, 200, '2022-03-02 18:21:36', '2022-07-07 13:03:30');
INSERT INTO `seckill_goods` (`goods_id`, `stock_count`, `created_at`, `updated_at`)
VALUES (2, 100, '2022-03-02 18:21:36', '2022-03-25 21:01:11');
INSERT INTO `seckill_goods` (`goods_id`, `stock_count`, `created_at`, `updated_at`)
VALUES (3, 100, '2022-03-02 18:21:36', '2022-03-25 21:01:14');
INSERT INTO `seckill_goods` (`goods_id`, `stock_count`, `created_at`, `updated_at`)
VALUES (4, 100, '2022-03-02 18:21:36', '2022-03-25 21:01:16');
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;
