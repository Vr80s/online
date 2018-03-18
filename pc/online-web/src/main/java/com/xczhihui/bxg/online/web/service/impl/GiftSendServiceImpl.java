package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.support.service.impl.RedisCacheService;
import com.xczhihui.bxg.online.api.po.Gift;
import com.xczhihui.bxg.online.api.po.GiftStatement;
import com.xczhihui.bxg.online.api.service.GiftSendService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import com.xczhihui.bxg.online.common.utils.lock.Lock;
import com.xczhihui.bxg.online.web.dao.GiftDao;
import com.xczhihui.bxg.online.web.service.OnlineUserCenterService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class GiftSendServiceImpl implements GiftSendService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftDao giftDao;
	@Autowired
	private UserCoinService userCoinService;
	@Autowired
	private OnlineUserCenterService onlineUserCenterService;
	@Autowired
	private RedisCacheService cacheService;

	private static String giftCache = "gift_";

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Lock(lockName = "addGiftStatement",waitTime = 5,effectiveTime = 8)
	public Map<String,Object> addGiftStatement4Lock(String lockKey, String giverId, String receiverId, String giftId, OrderFrom orderFrom, int count, String liveId){
		if(count<1){
			throw new RuntimeException("送礼物数量最少为1！");
		}
		if(StringUtils.isBlank(giftId)){
			throw new RuntimeException("礼物id不为空！");
		}

		GiftStatement giftStatement = new GiftStatement();
		giftStatement.setGiftId(giftId);
		giftStatement.setGiver(giverId);
		giftStatement.setReceiver(receiverId);
		giftStatement.setLiveId(liveId);
		giftStatement.setCount(count);

		Gift gift = cacheService.get(giftCache+giftStatement.getGiftId());
		if(gift == null){
			DetachedCriteria dc = DetachedCriteria.forClass(Gift.class);
			dc.add(Restrictions.eq("isDelete", false));
			dc.add(Restrictions.eq("id", Integer.valueOf(giftStatement.getGiftId())));
			gift = giftDao.findEntity(dc);
			if(gift == null) {
				throw new RuntimeException("无对应礼物");
			}else{
				//缓存礼物数据10分钟
				cacheService.set(giftCache+giftStatement.getGiftId(),gift,60*10);
			}
		}else{
			logger.info("取到礼物缓存数据");
		}

		giftStatement.setGiftName(gift.getName());
		giftStatement.setPrice(gift.getPrice());
		giftStatement.setCreateTime(new Date());
		giftStatement.setGiftImg(gift.getSmallimgPath());
		giftStatement.setClientType(orderFrom.getCode());
		giftDao.addGiftStatement(giftStatement);
		
		if(gift.getPrice()>0){
			//扣除用户相应的代币数量,主播增加相应代币
            userCoinService.updateBalanceForGift(giftStatement, gift);
        }
		OnlineUser u = onlineUserCenterService.getUser(giftStatement.getGiver());
		if(u==null) {
            throw new RuntimeException(giftStatement.getGiver() + "--用户不存在");
        }
        //业务流程处理完成，开始准备广播数据
		int continuousCount = getContinuousCount(u.getId(),gift.getId(),liveId);
		logger.info("{}连击数{}",u.getId(),continuousCount);

		GiftStatement gs = new GiftStatement();
		try {
			BeanUtils.copyProperties(gs, giftStatement);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		gs.setGiver(u.getName());
    	
		//封装广播参数
    	Map<String,Object> mapSenderInfo = new HashMap<String,Object>();
    	Map<String,Object> mapGiftInfo = new HashMap<String,Object>();
    	
    	mapSenderInfo.put("avatar", u.getSmallHeadPhoto());
    	mapSenderInfo.put("userId", u.getId());
    	mapSenderInfo.put("userName", u.getName());
    	
    	mapGiftInfo.put("continuousCount", continuousCount);
    	mapGiftInfo.put("count", gs.getCount());
    	mapGiftInfo.put("time", new Date().getTime()+"");
    	mapGiftInfo.put("giftId", gs.getGiftId());
    	mapGiftInfo.put("name", gs.getGiftName());
    	mapGiftInfo.put("smallimgPath", gs.getGiftImg());
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("senderInfo", mapSenderInfo);
    	map.put("giftInfo", mapGiftInfo);
    	map.put("giftCount",findByUserId(liveId));
    	map.put("messageType",1);

		return map;
	}

	private int getContinuousCount(String id, Integer giftId, String liveId) {
		//用户id+视频Id+礼物id
		String giftShowTicket = id+"_"+giftId+"_"+liveId;
		Integer continuousCount = cacheService.get(giftShowTicket);
		if(continuousCount == null){
			continuousCount = 1;
			//将礼物数目存入redis 有效期三秒
			cacheService.set(giftShowTicket,continuousCount,3);
		}else{
			continuousCount++;
			cacheService.set(giftShowTicket,continuousCount,3);
		}
		return continuousCount.intValue();
	}

	@Override
	public int findByUserId(String userId) {
		return giftDao.findByUserId(userId);
	}

}
