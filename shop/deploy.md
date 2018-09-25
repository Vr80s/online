根据生成原型在shop++ 基础上增加的数据字段


1、商品表增加医师推荐数字段


	ALTER TABLE `product` ADD `recommends` int(11) DEFAULT NULL COMMENT '医师推荐数';
	
2、用户表中增加用户头像字段	

    ALTER TABLE `users` ADD `headPhoto` varchar(255) DEFAULT NULL COMMENT '用户头像' AFTER `username`;
		

    ALTER TABLE `Users` add column `doctor_id` VARCHAR(50) NULL  DEFAULT NULL  COMMENT '医师id';

