package com.xczh.consumer.market.vo;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/12 23:39
 */

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liutao
 * @create 2017-09-12 23:39
 **/
public class RechargeParamVo implements Serializable {
	
    private  String clientType;
    private String t; //回传参数业务类型 1:打赏 2 普通订单 3 充值代币
    private String userId;
    private String subject;
    
    private BigDecimal value;  //充值的金额
    private Integer orderForm; // //订单来源
    private Integer payType;   ////0:支付宝 1:微信 2:网银	
    


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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Integer getOrderForm() {
		return orderForm;
	}

	public void setOrderForm(Integer orderForm) {
		this.orderForm = orderForm;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
    
    
}
