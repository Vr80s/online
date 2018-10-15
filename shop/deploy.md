根据生成原型在shop++ 基础上增加的数据字段


1、商品表增加医师推荐数字段

	ALTER TABLE `product` ADD `doctorRecommends` int(11) DEFAULT NULL COMMENT '医师推荐数';

	ALTER TABLE `product` ADD `recommends` int(11) DEFAULT NULL COMMENT '医师推荐星级级别';

	ALTER TABLE `product` ADD `recommentContent` int(11) DEFAULT NULL COMMENT '医师推荐内容';
	
2、用户表中增加用户头像字段	

    ALTER TABLE `users` ADD `headPhoto` varchar(255) DEFAULT NULL COMMENT '用户头像' AFTER `username`;
		

    ALTER TABLE `Users` add column `doctor_id` VARCHAR(50) NULL  DEFAULT NULL  COMMENT '医师id';
    
    
3、商品评论表中增加 卖家服务、物流服务 评价字段

	ALTER TABLE `review` ADD `seller` int(11) DEFAULT NULL COMMENT '卖家服务'  AFTER `score`;
	
	ALTER TABLE `review` ADD `logistics` int(11) DEFAULT NULL COMMENT '物流服务'  AFTER `score`;    
	
    ALTER TABLE `review` ADD `reviewImage` VARCHAR(4000) DEFAULT NULL COMMENT '评价的图片'  AFTER `content`;    

