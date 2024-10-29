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

INSERT INTO `role` (`id`, `role`, `role_name`)
VALUES (1, 'ROLE_SUPER_ADMIN', '超级管理员');
INSERT INTO `role` (`id`, `role`, `role_name`)
VALUES (2, 'NORMAL_ADMIN_USER', '普通管理员');

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
