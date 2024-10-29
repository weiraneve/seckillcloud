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

CREATE TABLE `permission`
(
    `id`              int         NOT NULL AUTO_INCREMENT,
    `permission`      varchar(50) NOT NULL COMMENT '权限',
    `permission_name` varchar(50) NOT NULL COMMENT '权限名称',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';

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

CREATE TABLE `role`
(
    `id`        int         NOT NULL AUTO_INCREMENT,
    `role`      varchar(50) NOT NULL COMMENT '角色',
    `role_name` varchar(50) NOT NULL COMMENT '角色名称',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

CREATE TABLE `role_permission`
(
    `id`            int         NOT NULL AUTO_INCREMENT,
    `role_id`       int         NOT NULL COMMENT '角色ID',
    `permission_id` varchar(50) NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限表';

CREATE TABLE `user_role_permission`
(
    `id`            int NOT NULL AUTO_INCREMENT,
    `user_id`       int NOT NULL COMMENT '用户ID',
    `role_id`       int NOT NULL COMMENT '角色ID',
    `permission_id` int NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色权限表';
