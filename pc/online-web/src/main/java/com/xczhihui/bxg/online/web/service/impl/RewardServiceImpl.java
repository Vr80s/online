package com.xczhihui.bxg.online.web.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xczhihui.bxg.online.api.po.RewardStatement;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.Reward;
import com.xczhihui.bxg.online.web.base.common.Broadcast;
import com.xczhihui.bxg.online.web.dao.RewardDao;
import com.xczhihui.bxg.online.web.service.RewardService;

/**
 * @author liutao 【jvmtar@gmail.com】
 * @create 2017-09-13 16:43
 **/
@Service
public class RewardServiceImpl implements RewardService {

	@Autowired
	private RewardDao rewardDao;
	@Autowired
	private Broadcast broadcast;

    @Override
    public void insert(RewardStatement rewardStatement) throws XMPPException, SmackException, IOException {
    	rewardDao.save(rewardStatement);
    	rewardBroadcast(rewardStatement);
    }

    @Override
    public List<Reward> findAll() {
        return rewardDao.findAll();
    }

	@Override
	public Reward findRewardById(String rewardId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Reward.class);
		dc.add(Restrictions.eq("id", Integer.valueOf(rewardId)));
		return rewardDao.findEntity(dc);
	}
	
	public void rewardBroadcast(RewardStatement rewardStatement) throws XMPPException, SmackException, IOException{
		Map<String,Object> mapSenderInfo = new HashMap<String,Object>();
    	Map<String,Object> maprewardInfo = new HashMap<String,Object>();
    	DetachedCriteria dc = DetachedCriteria.forClass(OnlineUser.class);
		dc.add(Restrictions.eq("id", rewardStatement.getGiver()));
		OnlineUser u = rewardDao.findEntity(dc);
		
    	mapSenderInfo.put("avatar", u.getSmallHeadPhoto());
    	mapSenderInfo.put("userId", u.getId());
    	mapSenderInfo.put("userName", u.getName());
    	
    	maprewardInfo.put("price", rewardStatement.getPrice());
    	maprewardInfo.put("time", rewardStatement.getCreateTime());
    	maprewardInfo.put("rewardId", rewardStatement.getRewardId());

    	String rewardTotal = rewardDao.getRewardTotal(rewardStatement.getLiveId());
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("senderInfo", mapSenderInfo);
    	map.put("rewardInfo", maprewardInfo);
    	map.put("messageType",0);//打赏类型为0
    	map.put("rewardTotal",rewardTotal==null?0:rewardTotal);//打赏总金额

    	String rewardStatementStr = JSONObject.toJSONString(map);
		broadcast.loginAndSend(rewardStatement.getLiveId(), rewardStatementStr);
	}
}
