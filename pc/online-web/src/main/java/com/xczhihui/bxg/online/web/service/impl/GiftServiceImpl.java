package com.xczhihui.bxg.online.web.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.api.po.Gift;
import com.xczhihui.bxg.online.api.po.GiftStatement;
import com.xczhihui.bxg.online.api.service.GiftService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.GiftDao;
import com.xczhihui.bxg.online.web.exception.NotSuchGiftException;
import com.xczhihui.bxg.online.web.service.OnlineUserCenterService;

@Service
public class GiftServiceImpl implements GiftService {
	
	@Autowired
	private GiftDao giftDao;
	@Autowired
	private UserCoinService userCoinService;
	@Autowired
	private OnlineUserCenterService onlineUserCenterService;

	/* (non-Javadoc)
	 * @see com.xczhihui.bxg.online.web.service.GiftService#addGiftStatement(com.xczhihui.bxg.online.common.domain.GiftStatement)
	 */
	@Override
	public Map<String,Object> addGiftStatement(GiftStatement giftStatement){
		if(giftStatement.getCount()<1){
			throw new RuntimeException("送礼物数量最少为1！");
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(Gift.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("id", Integer.valueOf(giftStatement.getGiftId())));
		Gift gift = giftDao.findEntity(dc);
		if(gift == null) {
            throw new NotSuchGiftException();
        }
		giftStatement.setCreateTime(new Date());
		giftStatement.setGiftId(gift.getId()+"");
		giftStatement.setGiftName(gift.getName());
		giftStatement.setPrice(gift.getPrice());
		giftStatement.setCreateTime(new Date());
		giftStatement.setGiftImg(gift.getSmallimgPath());
		giftDao.addGiftStatement(giftStatement);
		
		if(gift.getPrice()>0)
//			throw new RuntimeException("");
        {
            userCoinService.updateBalanceForGift(giftStatement, gift);//扣除用户相应的代币数量,主播增加相应代币
        }
		OnlineUser u = onlineUserCenterService.getUser(giftStatement.getGiver());
		if(u==null) {
            throw new RuntimeException(giftStatement.getGiver() + "--用户不存在");//20171227-yuxin
        }
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
    	
    	mapGiftInfo.put("continuousCount", gs.getCount());
    	mapGiftInfo.put("count", gs.getCount());
    	mapGiftInfo.put("time", new Date().getTime()+"");
    	mapGiftInfo.put("giftId", gs.getGiftId());
    	mapGiftInfo.put("name", gs.getGiftName());
    	mapGiftInfo.put("smallimgPath", gs.getGiftImg());
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("senderInfo", mapSenderInfo);
    	map.put("giftInfo", mapGiftInfo);
    	map.put("giftCount",findByUserId(gs.getReceiver()));
    	map.put("messageType",1);
    	map.put("balanceTotal",userCoinService.getBalanceByUserId(u.getId()).get("balanceTotal"));
		return map;
	}

	/* (non-Javadoc)
	 * @see com.xczhihui.bxg.online.web.service.GiftService#getGift()
	 */
	@Override
	public List<Gift> getGift() {
		return giftDao.getGift();
	}

    /* (non-Javadoc)
     * @see com.xczhihui.bxg.online.web.service.GiftService#findByUserId(java.lang.String)
     */
    @Override
    public int findByUserId(String userId) {
        return giftDao.findByUserId(userId);
    }

	@Override
	public Object getReceivedGift(String id, Integer pageNumber,
			Integer pageSize) {
		return giftDao.getReceivedGift(id,pageNumber,pageSize);
	}

	@Override
	public Object getReceivedReward(String id, Integer pageNumber,
			Integer pageSize) {
		return giftDao.getReceivedReward(id,pageNumber,pageSize);
	}

	@Override
	public Object getLiveCourseByUserId(String userId, Integer pageNumber, Integer pageSize) {
		return giftDao.getLiveCourseByUserId(userId,pageNumber,pageSize);
	}

	@Override
	public Object getLiveCourseUsersById(String id, String userId, Integer pageNumber, Integer pageSize) {
		return giftDao.getLiveCourseUsersById(id,userId,pageNumber,pageSize);
	}
}
