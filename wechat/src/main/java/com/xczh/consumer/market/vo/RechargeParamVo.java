package com.xczh.consumer.market.vo;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/12 23:39
 */

import java.io.Serializable;

/**
 * @author liutao
 * @create 2017-09-12 23:39
 **/
public class RechargeParamVo implements Serializable {
    private  String clientType;
    //回传参数业务类型 1:打赏 2 普通订单 3 充值代币
    private String t="3";
    private String userId;
    private String subject;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
