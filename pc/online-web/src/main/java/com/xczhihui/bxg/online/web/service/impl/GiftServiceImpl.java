package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.online.api.po.Gift;
import com.xczhihui.bxg.online.api.service.GiftSendService;
import com.xczhihui.bxg.online.api.service.GiftService;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import com.xczhihui.bxg.online.web.dao.GiftDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GiftServiceImpl implements GiftService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftDao giftDao;
	@Autowired
	private GiftSendService giftSendService;

	@Override
	public Map<String,Object> addGiftStatement(String giverId, String receiverId, String giftId, OrderFrom orderFrom,int count,String liveId) {
		//每次送一个  20180305 yuxin
		count=1;
		Map<String, Object> stringObjectMap = giftSendService.addGiftStatement4Lock(liveId, giverId, receiverId, giftId, orderFrom, count, liveId);
		return stringObjectMap;
	}

	@Override
	public List<Gift> getGift() {
		return giftDao.getGift();
	}

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
