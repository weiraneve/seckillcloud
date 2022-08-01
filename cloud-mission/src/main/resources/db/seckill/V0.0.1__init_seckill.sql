CREATE TABLE `seckill_goods` (
                                 `goods_id` bigint NOT NULL COMMENT '商品id',
                                 `stock_count` int NOT NULL DEFAULT '0' COMMENT '剩余库存',
                                 `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4 COMMENT='库存表';
