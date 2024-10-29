SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`              bigint NOT NULL AUTO_INCREMENT,
    `phone`           varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录手机号',
    `password`        varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
    `created_at`      datetime                                               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`      datetime                                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `last_login_time` datetime                                               DEFAULT NULL COMMENT '最后一次登录时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `phone`, `password`, `created_at`, `updated_at`, `last_login_time`)
VALUES (1, '13870035320', '9508534c15e39a3f5a586aec9be941f6c249e646e06857ea0c3b0c33545dc679', '2022-02-26 16:22:19',
        '2022-03-25 20:59:09', '2022-05-24 20:36:30');
INSERT INTO `user` (`id`, `phone`, `password`, `created_at`, `updated_at`, `last_login_time`)
VALUES (2, '13870035321', '9508534c15e39a3f5a586aec9be941f6c249e646e06857ea0c3b0c33545dc679', '2022-03-15 11:40:17',
        '2022-03-25 20:51:07', '2022-03-21 02:36:03');
INSERT INTO `user` (`id`, `phone`, `password`, `created_at`, `updated_at`, `last_login_time`)
VALUES (3, '12345678910', '123', '2022-03-15 11:40:17', '2022-03-25 20:51:07', '2022-03-21 02:36:03');
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;
