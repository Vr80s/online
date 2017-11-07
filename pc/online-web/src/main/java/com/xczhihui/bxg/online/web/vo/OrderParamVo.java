package com.xczhihui.bxg.online.web.vo;


import java.io.Serializable;

/**
 * @author liutao 【jvmtar@gmail.com】
 * @create 2017-08-23 14:45
 **/
public class OrderParamVo implements Serializable{

    private String userId;
    private String subject;
    //回传参数业务类型 1:打赏 2 普通订单
    private String passbackParamBizType="2";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassbackParamBizType() {
        return passbackParamBizType;
    }

    public void setPassbackParamBizType(String passbackParamBizType) {
        this.passbackParamBizType = passbackParamBizType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
