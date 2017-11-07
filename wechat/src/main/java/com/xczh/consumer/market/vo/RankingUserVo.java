package com.xczh.consumer.market.vo;

import java.io.Serializable;

/**
 * @author liutao
 * @create 2017-08-30 20:44
 **/
public class RankingUserVo  implements Serializable{

    private String userId;
    private String name;
    private String smallHeadPhoto;
    private String giftCount;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallHeadPhoto() {
        return smallHeadPhoto;
    }

    public void setSmallHeadPhoto(String smallHeadPhoto) {
        this.smallHeadPhoto = smallHeadPhoto;
    }

    public String getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(String giftCount) {
        this.giftCount = giftCount;
    }
}
