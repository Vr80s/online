package com.xczhihui.bxg.online.web.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;

import net.shopxx.merge.enums.Status;
import net.shopxx.merge.enums.Type;
import net.shopxx.merge.service.OrderOperService;

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
	
	///   xczh/order/order/list
	
	@Autowired
	public OrderOperService orderOperService;
	
    @RequestMapping(value = "/order/list")
    @ResponseBody
    public ResponseObject list( @RequestParam(required = false)Type type,
    		 @RequestParam(required = false) Status status, 
    		 @RequestParam(required = false) Boolean isPendingReceive
            , @RequestParam(required = false) Boolean isPendingRefunds, 
              @RequestParam(required = false) Boolean isUseCouponCode, 
              @RequestParam(required = false) Boolean isExchangePoint
            , @RequestParam(required = false) Boolean isAllocatedStock,
              @RequestParam(required = false) Boolean hasExpired
            , @RequestParam(defaultValue = "1") int pageNumber,
              @RequestParam(defaultValue = "10") int pageSize){
    	
    	BxgUser loginUser = UserLoginUtil.getLoginUser();
    	
        return ResponseObject.newSuccessResponseObject(orderOperService.findPageXc(type, status, null, "aa79673b899249d9a07b0f19732a1b0e", 
        		null, isPendingReceive, isPendingRefunds, isUseCouponCode, 
        		isExchangePoint, isAllocatedStock, hasExpired, pageNumber, pageSize));
    }
}
