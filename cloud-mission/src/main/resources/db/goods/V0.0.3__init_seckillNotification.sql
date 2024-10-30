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