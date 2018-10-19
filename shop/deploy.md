根据生成原型在shop++ 基础上增加的数据字段


1、商品表增加医师推荐数字段

	ALTER TABLE `product` ADD `doctorRecommends` int(11) DEFAULT NULL COMMENT '医师推荐数';

	ALTER TABLE `product` ADD `recommends` int(11) DEFAULT NULL COMMENT '医师推荐星级级别';

	ALTER TABLE `product` ADD `recommentContent` int(11) DEFAULT NULL COMMENT '医师推荐内容';
	
	ALTER TABLE `product` ADD `defaultSales` int(11) DEFAULT 0 COMMENT '默认销量'  AFTER `sales`;

	ALTER TABLE `product` ADD `totalSales` int(11) DEFAULT 0 COMMENT '总销量'  AFTER `sales`;
	
2、用户表中增加用户头像字段	

    ALTER TABLE `users` ADD `headPhoto` varchar(255) DEFAULT NULL COMMENT '用户头像' AFTER `username`;
		

    ALTER TABLE `Users` add column `doctor_id` VARCHAR(50) NULL  DEFAULT NULL  COMMENT '医师id';
    
    
3、商品评论表中增加 卖家服务、物流服务 评价字段

	ALTER TABLE `review` ADD `seller` int(11) DEFAULT NULL COMMENT '卖家服务'  AFTER `score`;
	
	ALTER TABLE `review` ADD `logistics` int(11) DEFAULT NULL COMMENT '物流服务'  AFTER `score`;    
	
    ALTER TABLE `review` ADD `reviewImage` VARCHAR(4000) DEFAULT NULL COMMENT '评价的图片'  AFTER `content`;    
    
4、购物车cartitem字段加入 是否选中
	
	    
    ALTER TABLE `cartitem` ADD `isChecked` bit(1) COMMENT '条目是否被选中'  AFTER `sku_id`;
    
5、删除订单的记录表里面增加一个 下单时间字段

    ALTER TABLE `orders_delete` ADD `createOrderDate` datetime  COMMENT '订单创建时间'  AFTER `expire`;	
	
    

