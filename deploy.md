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
    ALTER TABLE `oe_bxs_article` ADD `user_created` BIT(1)  NULL DEFAULT 0 COMMENT '是否是用户创建的';
    UPDATE article_type SET status = 1,sort = 1 WHERE id = 4;
    ALTER TABLE `oe_bxs_article` ADD `create_person` VARCHAR(32) NULL DEFAULT NULL COMMENT '创建者id';

dev-2.1.0

    正式环境微信模板配置，替换配置文件里的模板id

    后台管理系统新增菜单 在 主播管理 下 新增 学员管理  菜单路径为anchor/student/index

2.2.0
===

    处理banner中老的数据，登陆后台管理后，使用postman 以post方式调用:
    测试环境:
            http://test-www.xczhihui.com:28080/operate/mobileBanner/clean
            http://test-www.xczhihui.com:28080/operate/banner2/updateOldData

    正式环境环境:
                http://www.ipandatcm.com:28080/operate/mobileBanner/clean
                http://www.ipandatcm.com:28080/operate/banner2/updateOldData

    CREATE TABLE `doctor_banner` (
      `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
      `img_url` varchar(255) DEFAULT NULL COMMENT '封面图url',
      `type` int(255) DEFAULT NULL COMMENT '1:课程 2: 直播 3: 医案 4:专栏',
      `route_type` varchar(255) DEFAULT NULL COMMENT '路由类型',
      `link_param` varchar(255) DEFAULT NULL,
      `start_time` datetime DEFAULT NULL COMMENT '开始时间',
      `end_time` datetime DEFAULT NULL COMMENT '结束时间',
      `create_time` datetime DEFAULT NULL COMMENT '结束时间',
      `status` bit(1) DEFAULT b'0' COMMENT '是否上架',
      `user_id` varchar(32) NOT NULL COMMENT '用户id',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    处理老的反馈数据中的回复
    update oe_message o1, oe_message o2 set o1.`replytext` = o2.`context`where o1.type = 2 and o1.id = o2.pid;

    update oe_bxs_article set type_id = 9 where type_id is null;

    update oe_bxs_article oba join medical_writings mw on oba.id = mw.`article_id` set oba.create_person = mw.create_person where oba.type_id = 9;

    INSERT INTO `article_type` (`id`, `name`, `create_person`, `create_time`, `sort`, `status`)
    VALUES
        ('9', '名医著作', NULL, '2018-07-06 11:38:46', 16, 0),
        ('8', '医案', NULL, '2018-07-06 11:38:53', 17, 0);

dev-2.3
===

    alter table medical_entry_information add column doctor_id varchar(32)  null;

    alter table medical_entry_information add column type int(1) default 1;

    alter table medical_entry_information add column applied bit(1) default 0;

    update medical_entry_information mei  set mei.doctor_id = (select mer.doctor_id from medical_enrollment_regulations mer where mer.id =  mei.mer_id);

    update medical_entry_information set type = 1;

    ALTER TABLE medical_entry_information DROP INDEX mer_id;
    ALTER TABLE `medical_entry_information` ADD UNIQUE INDEX (`doctor_id`, `type`, `mer_id`, `user_id`);

    update medical_entry_information set applied =1 where apprentice = 1;

    update oe_user set origin = 2 where origin = 'weixin';

    update oe_user set origin = 1 where origin = 'online';

    update oe_user set origin = 4,  visitor = 1 where origin = 'apple_yk';

    update oe_user set origin = -1 where origin is null;

dev-2.4
==
    
    CREATE TABLE `oe_user_document` (
      `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
      `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户id',
      `document_id` varchar(32) NOT NULL DEFAULT '' COMMENT '文档id',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `document_name` varchar(500) DEFAULT NULL COMMENT '文档名称',
      `trans_status` int(1) DEFAULT NULL COMMENT '文档转码状态',
      PRIMARY KEY (`id`),
      UNIQUE KEY `unique_index` (`user_id`,`document_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    ALTER TABLE `oe_course` ADD COLUMN `channel_id` VARCHAR(32) NULL COMMENT '频道ID（文档、IM）' AFTER `direct_id`; 
    ALTER TABLE `oe_course` ADD COLUMN `record_id` VARCHAR(100) NULL COMMENT '回放ID' AFTER `direct_id`; 

dev-2.5
==
    新增医师分类表  
    DROP TABLE IF EXISTS `doctor_type`;
	CREATE TABLE `doctor_type`  (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标题',
	  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标',
	  `sort` int(11) DEFAULT NULL COMMENT '排序',
	  `create_person` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
	  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
	  `is_delete` bit(1) DEFAULT NULL COMMENT '是否删除,1已删除0未删除',
	  `status` int(2) NOT NULL DEFAULT 1 COMMENT '是否启用, 1 禁用 0 启用',
	  `remark` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
	  PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
	
	-- ----------------------------
	-- Records of doctor_type
	-- ----------------------------
	INSERT INTO `doctor_type` VALUES (1, '名青年中医1', NULL, 1, 'admin', '2018-08-07 11:58:28', b'0', 1, NULL);
	INSERT INTO `doctor_type` VALUES (2, '名老中医', NULL, 2, 'admin', '2018-08-07 14:58:12', b'0', 1, NULL);
	INSERT INTO `doctor_type` VALUES (3, '少数名族中医', NULL, 3, 'admin', '2018-08-07 15:01:46', b'0', 1, NULL);
	INSERT INTO `doctor_type` VALUES (4, '国医大师', NULL, 4, 'admin', '2018-08-07 15:03:41', b'0', 1, NULL);
	INSERT INTO `doctor_type` VALUES (5, '家传中医', NULL, 5, 'admin', '2018-08-07 15:04:05', b'0', 1, NULL);

    增加直播状况字段 
    ALTER TABLE `oe_course` ADD COLUMN `live_case` int(1) DEFAULT '1' COMMENT '直播状况。1.正常直播 2.退出但不结束' AFTER `live_status`; 

    ALTER TABLE `medical_treatment_appointment_info` ADD `status` INT(11)  NULL  DEFAULT NULL  COMMENT '预约状态'  AFTER `apprentice_id`;
    ALTER TABLE `medical_treatment_appointment_info` ADD `deleted` BIT(1)  NULL  DEFAULT 0  COMMENT '是否删除'  AFTER `apprentice_id`;
    ALTER TABLE `medical_treatment` ADD `course_id` INT(11)  NULL  DEFAULT NULL  COMMENT '课程id';
    update `medical_treatment_appointment_info` info set info.`status` = (select mt.status from medical_treatment mt where mt.`info_id` = info.id);
    update medical_treatment_appointment_info set deleted = 0 where deleted is null;
    update `medical_treatment_appointment_info` set status = 6  where id in (select info_id from medical_treatment where deleted = 1 and info_id is not null);
    update medical_treatment_appointment_info set status = 6 where status is null;

    ALTER TABLE `medical_treatment_appointment_info` ADD `date` DATE  NULL  AFTER `deleted`;
    ALTER TABLE `medical_treatment_appointment_info` ADD `start_time` TIME  NULL  AFTER `date`;
    ALTER TABLE `medical_treatment_appointment_info` ADD `end_time` TIME  NULL  AFTER `start_time`;

    update `medical_treatment_appointment_info` info set info.date = (select mt.date from medical_treatment mt where info.`treatment_id` = mt.id);

    update `medical_treatment_appointment_info` info set info.start_time = (select mt.start_time from medical_treatment mt where info.`treatment_id` = mt.id);

    update `medical_treatment_appointment_info` info set info.end_time = (select mt.end_time from medical_treatment mt where info.`treatment_id` = mt.id);

    课程表新增互动id，和预约审核详细信息id
    ALTER TABLE `oe_course` ADD COLUMN `inav_id` varchar(100) null COMMENT '微吼互动id' AFTER `is_record`;
    ALTER TABLE `oe_course` ADD COLUMN `appointment_info_id` int(11) null COMMENT '预约信息id' AFTER `is_teaching`;

    ALTER TABLE `medical_treatment` ADD `treatment_start_time` DATETIME  NULL AFTER `deleted`;
    ALTER TABLE `medical_treatment_appointment_info` ADD `treatment_start_time` DATETIME  NULL AFTER `deleted`;

    POST 方式调用: 
        测试环境: https://cs.xczhihui.com/doctor/treatment/reset/startTime
        正式环境: http://www.ipandatcm.com/doctor/treatment/reset/startTime 
    
     替换掉医馆图片信息中的图片后缀    
    update 	medical_hospital_picture	set  picture =  left(picture,LOCATE("?",picture)-1) where picture is not null     
        
        