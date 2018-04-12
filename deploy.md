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