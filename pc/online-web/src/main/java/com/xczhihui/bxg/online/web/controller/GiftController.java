package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.GiftStatement;
import com.xczhihui.online.api.service.GiftService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.common.util.enums.Payment;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


/** 
 * ClassName: GiftController.java <br>
 * Description: 礼物打赏接口<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@RestController
@RequestMapping(value = "/gift")
public class GiftController extends AbstractController{

	@Autowired
	private GiftService giftService;

	/**
	 * Description：赠送礼物接口（做礼物赠送余额扣减和检验）
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/getGift")
	public ResponseObject getGift() {
		return ResponseObject.newSuccessResponseObject(giftService.getGift());
	}
	
	/** 
	 * Description：赠送礼物接口（做礼物赠送余额扣减和检验）
	 * @param giftStatement
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @throws IOException 
	 * @throws SmackException 
	 * @throws XMPPException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 **/
	@RequestMapping(value = "/sendGift")
	public ResponseObject sendGift(GiftStatement giftStatement,
			HttpServletRequest request) throws XMPPException, SmackException, IOException, IllegalAccessException, InvocationTargetException, InterruptedException {
		Map<String,Object> map = new HashMap<String,Object>();
		OnlineUser u = getCurrentUser();
        if(u!=null) {
        	giftStatement.setGiver(u.getId());
        	giftStatement.setClientType(OrderFrom.PC.getCode());
        	giftStatement.setPayType(Payment.COINPAY.getCode());
			map = giftService.addGiftStatement(u.getId(),giftStatement.getReceiver(),
					giftStatement.getGiftId(),OrderFrom.PC,giftStatement.getCount(),giftStatement.getLiveId());
		}
		return ResponseObject.newSuccessResponseObject(map);
	}
	
	/** 
	 * Description：接收到的礼物
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/receivedGift")
	@ResponseBody
	public ResponseObject receivedGift(HttpServletRequest request,Integer pageNumber,Integer pageSize) throws Exception {
		//获取登录用户
		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
		if(loginUser==null) {
            return ResponseObject.newErrorResponseObject("用户未登录");//20171227-yuxin
        }
		return ResponseObject.newSuccessResponseObject(giftService.getReceivedGift(loginUser.getId(), pageNumber, pageSize));
	}
	
	/** 
	 * Description：接收到的打赏
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/receivedReward")
	@ResponseBody
	public ResponseObject receivedReward(HttpServletRequest request,Integer pageNumber,Integer pageSize) throws Exception {
		//获取登录用户
		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
		if(loginUser==null) {
            return ResponseObject.newErrorResponseObject("用户未登录");//20171227-yuxin
        }
		return ResponseObject.newSuccessResponseObject(giftService.getReceivedReward(loginUser.getId(), pageNumber, pageSize));
	}

	/**
	 * Description：分页获取直播课程列表
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/getLiveCourseByUserId")
	@ResponseBody
	public ResponseObject getLiveCourseByUserId(HttpServletRequest request,Integer pageNumber,Integer pageSize) throws Exception {
		//获取登录用户
		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
		if(loginUser==null) {
            return ResponseObject.newErrorResponseObject("用户未登录");//20171227-yuxin
        }
		return ResponseObject.newSuccessResponseObject(giftService.getLiveCourseByUserId(loginUser.getId(), pageNumber, pageSize));
	}
	/**
	 * Description：获取直播课程对应的课程报名情况
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/getLiveCourseUsersById")
	@ResponseBody
	public ResponseObject getLiveCourseUsersById(HttpServletRequest request,String id,Integer pageNumber,Integer pageSize) throws Exception {
		//获取登录用户
		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
		if(loginUser==null) {
            return ResponseObject.newErrorResponseObject("用户未登录");//20171227-yuxin
        }
		return ResponseObject.newSuccessResponseObject(giftService.getLiveCourseUsersById(id,loginUser.getId(), pageNumber, pageSize));
	}
	
	
	/** 
	 * Description：接收到的礼物
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/getRankingListByLiveId")
	@ResponseBody
	public ResponseObject getRankingListByLiveId(HttpServletRequest request,
			String liveId,
			Integer pageNumber,Integer pageSize) throws Exception {
		//获取登录用户
		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
		if(loginUser==null) {
            return ResponseObject.newErrorResponseObject("用户未登录");//20171227-yuxin
        }
		return ResponseObject.newSuccessResponseObject(giftService.getRankingListByLiveId(liveId, pageNumber, pageSize));
	}
	
	
}