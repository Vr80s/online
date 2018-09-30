package com.xczhihui.bxg.online.web.controller.shop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;

import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.enums.Status;
import net.shopxx.merge.enums.Type;
import net.shopxx.merge.enums.UsersType;
import net.shopxx.merge.service.GoodsService;
import net.shopxx.merge.service.OrderOperService;
import net.shopxx.merge.vo.OrderPageParams;

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
