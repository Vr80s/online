package com.xczhihui.common.util.bean;

/**
 * 微吼聊天列表放回的数据
 * @author yangxuan
 *
 */
public class VhallMessageVo {

    private String data; // 聊天内容
    private String dataTime; //聊天时间
    private String thirdPartyUserId; //第三方用户ID
    
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getDataTime() {
        return dataTime;
    }
    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
    public String getThirdPartyUserId() {
        return thirdPartyUserId;
    }
    public void setThirdPartyUserId(String thirdPartyUserId) {
        this.thirdPartyUserId = thirdPartyUserId;
    }
    
}
