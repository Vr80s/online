package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.api.po.Gift;
import com.xczhihui.bxg.online.api.po.GiftStatement;
import com.xczhihui.bxg.online.api.service.GiftService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.Broadcast;
import com.xczhihui.bxg.online.web.exception.NotSufficientFundsException;


/** 
 * ClassName: GiftController.java <br>
 * Description: 礼物打赏接口<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@RestController
@RequestMapping(value = "/gift")
public class GiftController {

	@Autowired
	private GiftService giftService;
	@Autowired
	private Broadcast broadcast;

	/** 
	 * Description：赠送礼物接口（做礼物赠送余额扣减和检验）
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/getGift")
	public ResponseObject getGift() {
		List<Gift> gifts;
		try {
			gifts= giftService.getGift();
		} catch (NotSufficientFundsException e) {
			return ResponseObject.newErrorResponseObject(e.getMessage());
		}
		return ResponseObject.newSuccessResponseObject(gifts);
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
	public ResponseObject sendGift(GiftStatement giftStatement,HttpSession s) throws XMPPException, SmackException, IOException, IllegalAccessException, InvocationTargetException {
		Map<String,Object> map = new HashMap<String,Object>();
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
        if(u!=null) {
        	giftStatement.setGiver(u.getId());
        	giftStatement.setClientType(1);
        	giftStatement.setPayType(3);
//        	map = giftService.sendGiftStatement(giftStatement);
			synchronized (giftStatement.getReceiver().intern()){
				System.out.println(giftStatement.getReceiver().intern());
				map = giftService.addGiftStatement(giftStatement);
			}
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
		return ResponseObject.newSuccessResponseObject(giftService.getReceivedReward(loginUser.getId(), pageNumber, pageSize));
	}
}