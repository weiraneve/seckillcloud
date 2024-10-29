SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_users
-- ----------------------------
DROP TABLE IF EXISTS `admin_users`;
CREATE TABLE `admin_users`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `username`   varchar(255)          DEFAULT NULL COMMENT '账号',
    `name`       varchar(255)          DEFAULT NULL COMMENT '昵称',
    `avatar`     varchar(255)          DEFAULT NULL COMMENT '头像',
    `phone`      varchar(255)          DEFAULT NULL COMMENT '手机号',
    `role`       varchar(55)  NOT NULL COMMENT '角色多个角色可以使用夺标关联或者本字段用逗号分隔',
    `password`   varchar(255) NOT NULL COMMENT '用户密码',
    `is_ban`     bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否禁用',
    `created_at` datetime              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of admin_users
-- ----------------------------
BEGIN;
INSERT INTO `admin_users` (`id`, `username`, `name`, `avatar`, `phone`, `role`, `password`, `is_ban`, `created_at`,
                           `updated_at`)
VALUES (1, 'super_admin', '超级管理者一号', 'http://res.steveay.com/index/ee717dbb66f044a1b87d002b78121682.png', '',
        'ROLE_SUPER_ADMIN', '$2a$10$RO97lPxOvseFMLrbquwblupRmpu0gB..pInyi95Ocrj60APwQXKDq', b'0', '2019-12-05 16:30:01',
        '2022-02-23 15:52:11');
INSERT INTO `admin_users` (`id`, `username`, `name`, `avatar`, `phone`, `role`, `password`, `is_ban`, `created_at`,
                           `updated_at`)
VALUES (2, 'admin', '普通管理者一号', 'http://res.steveay.com/index/ee717dbb66f044a1b87d002b78121682.png',
        '088-6668888', 'NORMAL_ADMIN_USER', '$2a$10$RO97lPxOvseFMLrbquwblupRmpu0gB..pInyi95Ocrj60APwQXKDq', b'0',
        '2020-03-14 13:39:53', '2022-03-02 18:11:16');
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`
(
    `id`              int         NOT NULL AUTO_INCREMENT,
    `permission`      varchar(50) NOT NULL COMMENT '权限',
    `permission_name` varchar(50) NOT NULL COMMENT '权限名称',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (1, 'INDEX_ADMIN_USER', '首页');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (2, 'GOODS_ADMIN_USER', '商品列表');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (3, 'SECKILL_ADMIN_USER', '秒杀库存');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (4, 'ORDER_ADMIN_USER', '订单管理');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (5, 'SETTING_ADMIN_USER', '系统设置');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (6, 'MENU_ADMIN_USER', '菜单设置');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (7, 'ROLE_ADMIN_USER', '角色管理');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (8, 'ACCOUNT_ADMIN_USER', '账号管理');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (9, 'PERMISSION_ADMIN_USER', '权限设置');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (10, 'SETTING_UPDATE', '系统设置修改操作');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (11, 'SETTING_ADD', '系统设置添加操作');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (12, 'SRTTING_DELETE', '系统设置删除操作');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (13, 'SETTING_SELECT', '系统设置查询操作');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (14, 'SETTING_NORMAL_DELETE_USER', '正常模块删除权限');
INSERT INTO `permission` (`id`, `permission`, `permission_name`)
VALUES (15, 'SETTING_NORMAL_UPDATE_USER', '正常模块修改权限');
COMMIT;

-- ----------------------------
-- Table structure for permission_menu
-- ----------------------------
DROP TABLE IF EXISTS `permission_menu`;
CREATE TABLE `permission_menu`
(
    `id`            int NOT NULL AUTO_INCREMENT,
    `level`         int          DEFAULT '1' COMMENT '一级还是二级菜单(目录/菜单)',
    `sort`          int          DEFAULT '0' COMMENT '排序',
    `permission_id` int NOT NULL COMMENT '权限Id',
    `parent_id`     varchar(50)  DEFAULT NULL COMMENT '父级ID',
    `key`           varchar(255) DEFAULT NULL COMMENT '组建页面',
    `name`          varchar(255) DEFAULT NULL COMMENT '名称',
    `icon`          varchar(255) DEFAULT NULL COMMENT '图标 设置该路由的图标',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限菜单表';

-- ----------------------------
-- Records of permission_menu
-- ----------------------------
BEGIN;
INSERT INTO `permission_menu` (`id`, `level`, `sort`, `permission_id`, `parent_id`, `key`, `name`, `icon`)
VALUES (1, 1, 1, 1, NULL, 'Home', '首页', 'home');
INSERT INTO `permission_menu` (`id`, `level`, `sort`, `permission_id`, `parent_id`, `key`, `name`, `icon`)
VALUES (2, 1, 2, 2, NULL, 'Goods', '商品列表', 'shopping');
INSERT INTO `permission_menu` (`id`, `level`, `sort`, `permission_id`, `parent_id`, `key`, `name`, `icon`)
VALUES (3, 1, 3, 3, NULL, 'Seckill', '秒杀库存', 'apple');
INSERT INTO `permission_menu` (`id`, `level`, `sort`, `permission_id`, `parent_id`, `key`, `name`, `icon`)
VALUES (4, 1, 4, 4, NULL, 'Order', '订单管理', 'amazon');
INSERT INTO `permission_menu` (`id`, `level`, `sort`, `permission_id`, `parent_id`, `key`, `name`, `icon`)
VALUES (5, 1, 5, 5, NULL, 'Setting', '系统设置', 'setting');
INSERT INTO `permission_menu` (`id`, `level`, `sort`, `permission_id`, `parent_id`, `key`, `name`, `icon`)
VALUES (6, 2, 6, 6, '5', 'Menu', '菜单设置', 'menu');
INSERT INTO `permission_menu` (`id`, `level`, `sort`, `permission_id`, `parent_id`, `key`, `name`, `icon`)
VALUES (7, 2, 7, 7, '5', 'Role', '角色管理', 'robot');
INSERT INTO `permission_menu` (`id`, `level`, `sort`, `permission_id`, `parent_id`, `key`, `name`, `icon`)
VALUES (8, 2, 8, 8, '5', 'Account', '账号管理', 'contacts');
INSERT INTO `permission_menu` (`id`, `level`, `sort`, `permission_id`, `parent_id`, `key`, `name`, `icon`)
VALUES (9, 2, 9, 9, '5', 'Permission', '权限设置', 'paper-clip');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`        int         NOT NULL AUTO_INCREMENT,
    `role`      varchar(50) NOT NULL COMMENT '角色',
    `role_name` varchar(50) NOT NULL COMMENT '角色名称',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` (`id`, `role`, `role_name`)
VALUES (1, 'ROLE_SUPER_ADMIN', '超级管理员');
INSERT INTO `role` (`id`, `role`, `role_name`)
VALUES (2, 'NORMAL_ADMIN_USER', '普通管理员');
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`
(
    `id`            int         NOT NULL AUTO_INCREMENT,
    `role_id`       int         NOT NULL COMMENT '角色ID',
    `permission_id` varchar(50) NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (1, 1, '1');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (2, 1, '2');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (3, 1, '3');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (4, 1, '4');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (5, 1, '5');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (6, 1, '6');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (7, 1, '7');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (8, 1, '8');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (9, 1, '9');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (10, 1, '10');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (11, 1, '11');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (12, 1, '12');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (13, 1, '13');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (14, 1, '14');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (15, 1, '15');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (16, 2, '1');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (17, 2, '2');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (18, 2, '3');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (19, 2, '4');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (20, 2, '5');
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`)
VALUES (21, 2, '6');
COMMIT;

-- ----------------------------
-- Table structure for user_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `user_role_permission`;
CREATE TABLE `user_role_permission`
(
    `id`            int NOT NULL AUTO_INCREMENT,
    `user_id`       int NOT NULL COMMENT '用户ID',
    `role_id`       int NOT NULL COMMENT '角色ID',
    `permission_id` int NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色权限表';

-- ----------------------------
-- Records of user_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (1, 1, 1, 1);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (2, 1, 1, 2);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (3, 1, 1, 3);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (4, 1, 1, 4);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (5, 1, 1, 5);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (6, 1, 1, 6);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (7, 1, 1, 7);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (8, 1, 1, 8);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (9, 1, 1, 9);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (10, 1, 1, 10);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (11, 1, 1, 11);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (12, 1, 1, 12);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (13, 1, 1, 13);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (14, 1, 1, 14);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (15, 1, 1, 15);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (16, 2, 1, 1);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (17, 2, 1, 2);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (18, 2, 1, 3);
INSERT INTO `user_role_permission` (`id`, `user_id`, `role_id`, `permission_id`)
VALUES (19, 2, 1, 4);
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;
