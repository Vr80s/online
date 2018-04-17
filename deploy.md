附件中心改版部署
==

1. 从attachment库中的attachment表数据导入至online库中

2. 部署涉及附件上传的项目（原因:所依赖的代码修改与增加七牛配置） online-web online-manager wechat

3. 线下脚本同步attachment表中数据至七牛

dev-2.0
==

bbs后台整合部署


    1. 重新部署online-manager, 废弃老的bbs后台服务

    2. 进入online-manager，权限管理 -> 资源管理，增加菜单资源。如下:

    名称|图标|菜单级别|权限代码|资源地址
    ---|----|-------|-------|-------
    BBS管理 | fas fa-at  |   二级  |   bbs:menu    |
    标签管理 |            |   三级  |   bbs:menu:label | /bbs/label/index
    帖子管理 |            |   三级  |   bbs:menu:post |  /bbs/post/index
    回复管理 |            |   三级  |    bbs:menu:reply | /bbs/reply/index
    拉黑/禁言|            |   三级  |    bbs:menu:restriction | /bbs/restriction/index

    3.修改相关角色对以上新增资源的权限

管理后台用户登录逻辑修改

    1.alter sql

            ALTER TABLE `user` ADD `salt` varchar(50) NULL COMMENT '盐';

            更新prod_online 库中的user表中的password 与 salt为 user-center 库中的password 与 salt

bbs pc端整合

    1.拷贝online-web/src/main/resources/sensitiveWord.txt 至服务器 /data 目录下

医馆管理->公告管理

    CREATE TABLE `medical_hospital_announcement` (
      `id` varchar(32) NOT NULL DEFAULT '',
      `hospital_id` varchar(32) NOT NULL DEFAULT '' COMMENT '医馆id',
      `content` varchar(255) NOT NULL DEFAULT '' COMMENT '公告内容',
      `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
      `create_person` varchar(32) NOT NULL DEFAULT '' COMMENT '创建的用户id',
      `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

医师管理->著作管理

    ALTER TABLE `medical_writings` ADD `img_path` VARCHAR(255)  NULL  DEFAULT NULL  AFTER `sort`;

医馆管理->招聘管理

    ALTER TABLE `medical_hospital_recruit` ADD `public_time` DATETIME DEFAULT NULL COMMENT '发布时间';

医馆管理->专栏管理与媒体报道

    ALTER TABLE `oe_bxs_article` ADD `url` VARCHAR(255)  NULL  DEFAULT NULL COMMENT '文章来源的url';

    CREATE TABLE `medical_doctor_special_column` (
      `id` varchar(32) NOT NULL DEFAULT '' COMMENT '医师专栏关系表',
      `doctor_id` varchar(32) NOT NULL DEFAULT '' COMMENT '医师id',
      `article_id` varchar(32) NOT NULL DEFAULT '' COMMENT '文章id',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    ALTER TABLE `oe_bxs_article` ADD `update_time` DATETIME  NULL DEFAULT NULL COMMENT '更新时间';
