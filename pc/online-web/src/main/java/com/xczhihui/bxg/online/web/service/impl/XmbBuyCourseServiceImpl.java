package com.xczhihui.bxg.online.web.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xczhihui.bxg.online.api.service.OrderPayService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.api.service.XmbBuyCouserService;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.common.util.enums.Payment;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.bxg.online.web.service.OrderService;
import com.xczhihui.bxg.online.web.vo.OrderVo;

@Service
public class XmbBuyCourseServiceImpl implements XmbBuyCouserService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserCoinService userCoinService;
	
	@Autowired
	private OrderPayService orderPayService;

	@Value("${rate}")
	private  Integer rate;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Lock(lockName = "xmbBuyCourse",waitTime = 5,effectiveTime = 8)
	public void xmbBuyCourseLock(String orderNo){
		OrderVo ov =orderService.findOrderByOrderNoAndStatus(orderNo,null);
		//订单是否存在
		if(ov == null){
			throw new RuntimeException("订单信息有误!");
		}
		//是否支付
		if(ov.getOrder_status() == 1){
			throw new RuntimeException("订单已经购买哦!");
		}
		//实际支付金额
		double actual_pay =Double.parseDouble(ov.getActual_pay());
		//实际要扣减的熊猫币
		BigDecimal  xmb = BigDecimal.valueOf( actual_pay * rate);
		String userCoinConsumptionId = userCoinService.updateBalanceForBuyCourse(ov.getUser_id(),
				OrderFrom.valueOf(ov.getOrder_from()),
				xmb, orderNo);
		/*
		 * 更改订单状态，增加课程学习人数
		 */
		orderPayService.addPaySuccess(orderNo,Payment.COINPAY,userCoinConsumptionId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Lock(lockName = "xmbBuyCourse",waitTime = 5,effectiveTime = 8)
	public void focusHostLock(String lecturerId, String userid, Integer type) {
		
//		try {
//			Focus f = focusMapper.findFoursByUserIdAndlecturerId(userid,lecturerId);
//			if(type !=null && type == 1){//增加关注
//				if(f!=null){
//					//return "你已经关注过了";
//					throw new RuntimeException("你已经关注过了!");
//				}
//				f= new Focus();
//				f.setId(UUID.randomUUID().toString().replace("-", ""));
//				f.setUserId(userid);
//				f.setLecturerId(lecturerId);
//				f.setCreateTime(new Date());
//				focusMapper.insert(f);
//			}else if(type !=null && type == 2){
//				if(f==null){
//					//return "你还未关注此主播";
//					throw new RuntimeException("你还未关注此主播!");
//				}
//				focusMapper.deleteById(f.getId());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("操作失败!");
//		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	


}
