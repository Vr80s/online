package com.xczhihui.bxg.online.web.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xczhihui.online.api.service.LiveCallbackService;
import com.xczhihui.bxg.online.web.base.common.Broadcast;

@Service
public class LiveCallbackServiceImpl implements LiveCallbackService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Broadcast broadcast;
	
	@Override
	public void liveCallbackImRadio(String liveId,Integer Type) {
		
		System.out.println("{}{}{}{}{}{}-----》调用im广播的方法---》"+ liveId + ",type:" + Type);
		
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("senderInfo","微吼系统--》manager --》web ");
    	map.put("rewardInfo", "直播间"+liveId);
    	map.put("messageType",Type);			//0 打赏 1 礼物  2 直播开始通知   3 直播结束通知   ImInformLiveStatusType
    	map.put("sendTime",System.currentTimeMillis());  //发送时间
    	map.put("rewardTotal","0");//打赏总金额
    	String rewardStatementStr = JSONObject.toJSONString(map);

    	//直播刚刚结束后，提示用户生产回放的时间    待定
    	
    	try {
			logger.info("{}{}{}{}{}{}{}--广播的参数--》"+map.toString());
			broadcast.loginAndSend(liveId, rewardStatementStr);
		} catch (XMPPException | SmackException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


//	@Override
//	public Map<String,Object> addGiftStatement(String giverId, String receiverId, String giftId, OrderFrom orderFrom,int count,String liveId) {
//		if(giverId.equals(receiverId)){
//			throw new RuntimeException("不可以给自己送礼物哦~");
//		}
//		//每次送一个  20180305 yuxin
//		count=1;
//		Map<String, Object> stringObjectMap = giftSendService.addGiftStatement4Lock(liveId, giverId, receiverId, giftId, orderFrom, count, liveId);
//		return stringObjectMap;
//	}
}
