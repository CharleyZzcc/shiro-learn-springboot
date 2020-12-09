CREATE DATABASE `shiro_learn`;
use `shiro_learn`;

-- -------------------------
-- JdbcRealm默认使用的表和数据
-- -------------------------

CREATE TABLE `roles_permissions`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `permission` varchar(255) DEFAULT NULL,
    `role_name`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8 COMMENT ='JdbcRealm默认角色权限表';

INSERT INTO `roles_permissions`
VALUES (1, 'user:select', 'admin');
INSERT INTO `roles_permissions`
VALUES (2, 'user:delete', 'admin');
INSERT INTO `roles_permissions`
VALUES (3, 'user:update', 'admin');

CREATE TABLE `user_roles`
(
    `id`        int NOT NULL AUTO_INCREMENT,
    `role_name` varchar(255) DEFAULT NULL,
    `username`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8 COMMENT ='JdbcRealm默认用户角色表';

INSERT INTO `user_roles`
VALUES (1, 'admin', 'lzc');
INSERT INTO `user_roles`
VALUES (2, 'user', 'lzc');

CREATE TABLE `users`
(
    `id`       int NOT NULL AUTO_INCREMENT,
    `username` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8 COMMENT ='JdbcRealm默认用户表';

INSERT INTO `users`
VALUES (1, 'lzc', '123456');

-- -----------------------
-- 自定义Realm使用的表和数据
-- -----------------------

CREATE TABLE `permission`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `permission` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_permission` (`permission`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8 COMMENT ='自定义权限表';

INSERT INTO `permission`
VALUES (1, 'user:select');
INSERT INTO `permission`
VALUES (2, 'user:delete');
INSERT INTO `permission`
VALUES (3, 'user:update');

CREATE TABLE `role`
(
    `id`        int NOT NULL AUTO_INCREMENT,
    `role_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_role_name` (`role_name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8 COMMENT ='自定义角色表';

INSERT INTO `role`
VALUES (1, 'user');
INSERT INTO `role`
VALUES (2, 'admin');

CREATE TABLE `role_permission`
(
    `id`            int NOT NULL AUTO_INCREMENT,
    `role_id`       int DEFAULT NULL,
    `permission_id` int DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8 COMMENT ='自定义角色权限中间表';

INSERT INTO `role_permission`
VALUES (1, 2, 1);
INSERT INTO `role_permission`
VALUES (2, 2, 2);
INSERT INTO `role_permission`
VALUES (3, 2, 3);
INSERT INTO `role_permission`
VALUES (4, 1, 1);

CREATE TABLE `user`
(
    `id`       int NOT NULL AUTO_INCREMENT,
    `username` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8 COMMENT ='自定义用户表';

INSERT INTO `user`
VALUES (1, 'lzc', '123456');
INSERT INTO `user`
VALUES (2, 'zhangsan', '9bad41710724cf6511abde2a52416881');
INSERT INTO `user`
VALUES (3, 'lisi', '1b539b60601b934441308049a9526e7d');

CREATE TABLE `user_role`
(
    `id`      int NOT NULL AUTO_INCREMENT,
    `user_id` int DEFAULT NULL,
    `role_id` int DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8 COMMENT ='自定义用户角色中间表';

INSERT INTO `user_role`
VALUES (1, 1, 1);
INSERT INTO `user_role`
VALUES (2, 1, 2);
INSERT INTO `user_role`
VALUES (3, 2, 2);
INSERT INTO `user_role`
VALUES (4, 3, 1);