package com.xczhihui.bxg.online.web.controller.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.common.util.bean.ResponseObject;

import net.shopxx.merge.service.GoodsService;

/**
 * 
* @ClassName: ShopOrderController
* @Description: 医师订单管理
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年9月26日
*
 */
@RestController
@RequestMapping(value = "/xczh/shop")
public class ShopGoodsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShopGoodsController.class);
	
	@Autowired
	public GoodsService goodsService;
	
    @RequestMapping(value = "/good/detail")
    @ResponseBody
    public ResponseObject detail(Long productId){
    	LOGGER.info("productId:"+productId);
        return ResponseObject.newSuccessResponseObject(goodsService.findProductById(productId));
    }
}
