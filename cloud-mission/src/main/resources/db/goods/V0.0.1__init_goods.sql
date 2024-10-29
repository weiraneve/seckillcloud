CREATE TABLE `goods`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `goods_name`  varchar(16)    DEFAULT NULL COMMENT '商品名称',
    `goods_img`   varchar(255)   DEFAULT NULL COMMENT '商品图片',
    `is_using`    bit(1)         DEFAULT b'1' COMMENT '是否启用',
    `goods_title` varchar(64)    DEFAULT NULL COMMENT '商品标题',
    `goods_price` decimal(10, 0) DEFAULT NULL COMMENT '商品价格',
    `goods_stock` int            DEFAULT '0' COMMENT '商品库存',
    `start_time`  datetime       DEFAULT NULL COMMENT '秒杀开始时间',
    `end_time`    datetime       DEFAULT NULL COMMENT '秒杀结束时间',
    `created_at`  datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=UTF8MB4 COMMENT='商品表';
