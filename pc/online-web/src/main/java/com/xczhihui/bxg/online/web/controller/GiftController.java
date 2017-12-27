package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
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
//	@Autowired
//	private Broadcast broadcast;
	private RedissonClient redisson;

	public GiftController(){
		//Redisson连接配置文件
		Config config = new Config();
		config.useSingleServer().setAddress("127.0.0.1:6379");
		redisson = Redisson.create(config);
	}
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
	public ResponseObject sendGift(GiftStatement giftStatement,HttpSession s) throws XMPPException, SmackException, IOException, IllegalAccessException, InvocationTargetException, InterruptedException {
		Map<String,Object> map = new HashMap<String,Object>();
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
        if(u!=null) {
        	giftStatement.setGiver(u.getId());
        	giftStatement.setClientType(1);
        	giftStatement.setPayType(3);
//			System.out.println(giftStatement.getLiveId());
			RLock redissonLock = redisson.getLock("liveId"+giftStatement.getLiveId()); // 1.获得锁对象实例
			boolean res = false;
			try {
				res = redissonLock.tryLock(10, 5, TimeUnit.SECONDS);//等待十秒。有效期五秒
				map = giftService.addGiftStatement(giftStatement);
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				if(res){
					redissonLock.unlock();
				}
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
		return ResponseObject.newSuccessResponseObject(giftService.getLiveCourseUsersById(id,loginUser.getId(), pageNumber, pageSize));
	}
}