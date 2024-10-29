CREATE TABLE `user`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `user_name`        varchar(20) DEFAULT NULL COMMENT '用户名',
    `phone`            varchar(20) DEFAULT NULL COMMENT '登录手机号',
    `password`         varchar(80) DEFAULT NULL COMMENT '密码',
    `identity_card_id` varchar(20) DEFAULT NULL COMMENT '身份证号',
    `created_at`       datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`       datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `last_login_time`  datetime    DEFAULT NULL COMMENT '最后一次登录时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';
