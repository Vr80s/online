package com.xczh.consumer.market.controller.live;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.GiftService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.api.po.GiftStatement;
import com.xczhihui.bxg.online.api.service.UserCoinService;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/** 
 * ClassName: GiftController.java <br>
 * Description: 礼物打赏接口<br>
 * Create by: name：lituao <br>email: jvmtar@gmail.com <br>
 * Create Time: 2017年8月21日<br>
 */
@Controller
@RequestMapping(value = "/bxg/gift")
public class GiftController {


	@Autowired
	@Qualifier("giftServiceLocal")
	private GiftService giftService;

	@Autowired()
	@Qualifier("giftServiceImpl")
	private com.xczhihui.bxg.online.api.service.GiftService remoteGiftService;

	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private UserCoinService userCoinService;

	/**
	 * Description：赠送礼物接口（做礼物赠送余额扣减和检验）
	 * @return
	 * @return ResponseObject
	 * @author name：liutao <br>email: gvmtar@gmail.com
	 * @throws IOException 
	 * @throws SmackException 
	 * @throws XMPPException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 **/
	@ResponseBody
	@RequestMapping(value = "/sendGift")
	public ResponseObject sendGift(HttpServletRequest req,
								   HttpServletResponse res) throws SQLException, XMPPException, SmackException, IOException, IllegalAccessException, InvocationTargetException {

		Map<String, String> params=new HashMap<>();
		params.put("token",req.getParameter("token"));
		OnlineUser user =appBrowserService.getOnlineUserByReq(req, params); // onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000");
		if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		System.out.println("====================="+user.getId());
				GiftStatement giftStatement=new GiftStatement();
				giftStatement.setCreateTime(new Date());
	        	giftStatement.setGiver(user.getId());
				giftStatement.setGiftId(req.getParameter("giftId"));
				giftStatement.setLiveId(req.getParameter("liveId"));
				giftStatement.setReceiver(req.getParameter("receiverId"));
				//giftStatement.setCount(Integer.valueOf(req.getParameter()("continuousCount")));
				System.out.println("c:"+req.getParameter("continuousCount"));
				try {
					giftStatement.setCount(Integer.valueOf(req.getParameter("count")));
					if(giftStatement.getCount()<1){
						throw  new RuntimeException("非法的礼物数量!");
					}
				}catch (Exception e){
					throw  new RuntimeException("非法的礼物数量!");
				}

				giftStatement.setContinuousCount(Integer.valueOf(req.getParameter("continuousCount")));
				giftStatement.setChannel(1);
				giftStatement.setClientType(Integer.valueOf(req.getParameter("clientType")));
				giftStatement.setPayType(3);
	        //	giftStatement = giftService.addGiftStatement(giftStatement);
	        	//giftStatement.setGiver(user.getName());
	        	/*Broadcast bd = new Broadcast();
	        	
	        	Map<String,Object> map = new HashMap<String,Object>();
	        	Map<String,Object> mapSenderInfo = new HashMap<String,Object>();
	        	Map<String,Object> mapGiftInfo = new HashMap<String,Object>();
	        	
	        	mapSenderInfo.put("avatar", user.getSmallHeadPhoto());
	        	mapSenderInfo.put("userId", user.getId());
	        	mapSenderInfo.put("userName", user.getName());

	        	mapGiftInfo.put("continuousCount", giftStatement.getContinuousCount());
	        	mapGiftInfo.put("giftId", giftStatement.getGiftId());
				mapGiftInfo.put("time",giftStatement.getCreateTime());
		mapGiftInfo.put("count",giftStatement.getContinuousCount());
	        	mapGiftInfo.put("name", giftStatement.getGiftName());
	        	mapGiftInfo.put("smallimgPath", giftStatement.getGiftImg());

	        	map.put("senderInfo", mapSenderInfo);
	        	map.put("giftInfo", mapGiftInfo);
	        	map.put("messageType",1);
				map.put("giftCount",giftService.findByUserId(giftStatement.getReceiver()));

		map.put("balanceTotal",userCoinService.getBalanceByUserId(user.getId()).get("balanceTotal"));*/
		Map<String,Object> map=null;
		synchronized (giftStatement.getReceiver().intern()){
			map=remoteGiftService.addGiftStatement(giftStatement);
				}
		return ResponseObject.newSuccessResponseObject(map);
	}
	
	/**
	 * 礼物列表
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public ResponseObject list(HttpServletRequest req,
								   HttpServletResponse res, Map<String, String> params) throws SQLException {

//		OnlineUser user =appBrowserService.getOnlineUserByReq(req, params); // onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000");
//		if(user==null){
//			return ResponseObject.newErrorResponseObject("获取用户信息异常");
//		}
		int pageNumber = 0;
		if(null != req.getParameter("pageNumber")){
			pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
		}
		int pageSize = 0;
		if(null != req.getParameter("pageSize")){
			pageSize = Integer.valueOf(req.getParameter("pageSize"));
		}
		return ResponseObject.newSuccessResponseObject(giftService.listAll(pageNumber,pageSize));
	}


	/**
	 * 礼物榜单（直播间）
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value = "/rankingList")
	public ResponseObject rankingList(HttpServletRequest req,
							   HttpServletResponse res) throws SQLException {
		int pageNumber = 0;
		if(null != req.getParameter("pageNumber")){
			pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
		}
		int pageSize = 0;
		if(null != req.getParameter("pageSize")){
			pageSize = Integer.valueOf(req.getParameter("pageSize"));
		}
		return ResponseObject.newSuccessResponseObject(giftService.rankingList(req.getParameter("liveId"),Integer.valueOf(req.getParameter("type")),pageNumber,pageSize));
	}

	/**
	 * 礼物榜单（个人主页）
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value = "/userRankingList")
	public ResponseObject userRankingList(HttpServletRequest req,
									  HttpServletResponse res,String userId) throws SQLException {

		return ResponseObject.newSuccessResponseObject(giftService.userRankingList(userId));
	}


}
