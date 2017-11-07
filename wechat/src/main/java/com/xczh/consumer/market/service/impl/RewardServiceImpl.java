package com.xczh.consumer.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.Reward;
import com.xczh.consumer.market.bean.RewardStatement;
import com.xczh.consumer.market.dao.RewardMapper;
import com.xczh.consumer.market.dao.RewardStatementMapper;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.RewardService;
import com.xczh.consumer.market.utils.Broadcast;
import org.apache.commons.dbutils.DbUtils;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2017-08-22 10:48
 **/
@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardStatementMapper rewardStatementMapper;

    @Autowired
    private RewardMapper rewardMapper;

    @Autowired
    private OnlineUserService onlineUserService;

    @Autowired
    private Broadcast broadcast;

    @Override
    public void insert(RewardStatement rewardStatement) {
        try {
            rewardStatement.setChannel(1);
            rewardStatementMapper.insert(rewardStatement);
            rewardBroadcast(rewardStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("打赏记录插入失败!");
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reward> listAll() {
        try {
            return rewardMapper.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Reward findById(String id) {
        return rewardMapper.get(id);
    }

    public void rewardBroadcast(RewardStatement rewardStatement) throws XMPPException, SmackException, IOException {
        Map<String,Object> mapSenderInfo = new HashMap<String,Object>();
        Map<String,Object> mapGiftInfo = new HashMap<String,Object>();
        OnlineUser u=null;
        try {
            u=  onlineUserService.findUserById(rewardStatement.getGiver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mapSenderInfo.put("avatar", u.getSmallHeadPhoto());
        mapSenderInfo.put("userId", u.getId());
        mapSenderInfo.put("userName", u.getName());

        mapGiftInfo.put("price", rewardStatement.getPrice());
        mapGiftInfo.put("time", rewardStatement.getCreateTime());
        mapGiftInfo.put("giftId", rewardStatement.getRewardId());

//    	Map<String,Object> onlineCount = new HashMap<String,Object>();
//    	onlineCount.put("messageType", 3);
//    	onlineCount.put("giftCount", giftService.findByUserId(giftStatement.getReceiver()));
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("senderInfo", mapSenderInfo);
        map.put("rewardInfo", mapGiftInfo);
        map.put("messageType",0);//打赏类型为0
        String rewardStatementStr = JSONObject.toJSONString(map);
        broadcast.loginAndSend(rewardStatement.getLiveId(), rewardStatementStr);
    }


}
