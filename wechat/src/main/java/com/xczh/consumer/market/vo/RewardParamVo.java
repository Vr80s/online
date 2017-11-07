package com.xczh.consumer.market.vo;

import java.io.Serializable;

/**
 * 打赏需要传的参数
 * @author
 * @create 2017-08-22 9:52
 **/
public class RewardParamVo implements Serializable{

    private  String rewardId;
    private  String giver;
    private  String receiver;
    private  String liveId;
    private  String clientType;
    //回传参数业务类型 1:打赏 2 普通订单 3 充值代币
    private String t;
    private String userId;
    private String subject;
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getGiver() {
        return giver;
    }

    public void setGiver(String giver) {
        this.giver = giver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

}
