package com.xczh.consumer.market.controller.live;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.GiftService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.api.po.GiftStatement;
import com.xczhihui.bxg.online.api.service.UserCoinService;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.TimeUnit;


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

	private RedissonClient redisson;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GiftController.class);
	
	
	public GiftController(){
		//Redisson连接配置文件
		Config config = new Config();
		config.useSingleServer().setAddress("redis-server:6379");
		redisson = Redisson.create(config);
	}

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
		LOGGER.info("====================="+user.getId());
		
		GiftStatement giftStatement=new GiftStatement();
		giftStatement.setCreateTime(new Date());
		giftStatement.setGiver(user.getId());
		giftStatement.setGiftId(req.getParameter("giftId"));
		giftStatement.setLiveId(req.getParameter("liveId"));
		giftStatement.setReceiver(req.getParameter("receiverId"));
		//giftStatement.setCount(Integer.valueOf(req.getParameter()("continuousCount")));
		LOGGER.info("c:"+req.getParameter("continuousCount"));
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
//		synchronized (giftStatement.getReceiver().intern()){
//			map=remoteGiftService.addGiftStatement(giftStatement);
//				}

		RLock redissonLock = redisson.getLock("liveId"+giftStatement.getLiveId()); // 1.获得锁对象实例
		boolean resl = false;
		try {
			resl = redissonLock.tryLock(10, 5, TimeUnit.SECONDS);//等待十秒。有效期五秒
//			LOGGER.info(giftStatement.getLiveId()+":"+resl);
			map=remoteGiftService.addGiftStatement(giftStatement);
		}catch (Exception e){
			e.printStackTrace();
			LOGGER.info(e.getMessage());
			return ResponseObject.newErrorResponseObject(e.getMessage());
		}finally {
			if(resl){
				redissonLock.unlock();
			}
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
