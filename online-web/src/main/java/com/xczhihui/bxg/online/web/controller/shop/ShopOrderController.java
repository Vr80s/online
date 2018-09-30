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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;

import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.enums.Status;
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
    		 Status status,
    		 OrderType orderType){
    	
    	BxgUser loginUser = UserLoginUtil.getLoginUser();
    	
    	LOGGER.info("orderPageParams : "+ orderPageParams.toString());
    	LOGGER.info("status : "+ status);
        return ResponseObject.newSuccessResponseObject(orderOperService.findPageXc(orderPageParams, status, null,
        		"c5f315df48c54110a8ae85ccb2e06c7b",null, UsersType.BUSINESS,orderType));
    }
    
    @InitBinder
    public void initBind(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }
    
}
