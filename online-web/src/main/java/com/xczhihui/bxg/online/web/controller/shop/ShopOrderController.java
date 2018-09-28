package com.xczhihui.bxg.online.web.controller.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;

import net.shopxx.merge.enums.Status;
import net.shopxx.merge.enums.Type;
import net.shopxx.merge.enums.UsersType;
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
public class ShopOrderController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShopOrderController.class);
	
	@Autowired
	public OrderOperService orderOperService;
	
    @RequestMapping(value = "/order/list")
    @ResponseBody
    public ResponseObject list(
    		 OrderPageParams orderPageParams,
    		 @RequestParam(required = false)Type type,
    		 @RequestParam(required = false) Status status){
    	
    	BxgUser loginUser = UserLoginUtil.getLoginUser();
    	
    	LOGGER.info("orderPageParams : "+ orderPageParams.toString());
    	LOGGER.info("type : "+ type);
    	LOGGER.info("status : "+ status);
    	
        return ResponseObject.newSuccessResponseObject(orderOperService.findPageXc(orderPageParams,type, status, null, 
        		"aa79673b899249d9a07b0f19732a1b0e",null, UsersType.BUSINESS));
    }
}
