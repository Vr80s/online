package com.xczh.consumer.market.vo;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/8/23 16:37
 */

/**
 * @author liutao
 * @create 2017-08-23 16:37
 **/
public class PayRecordVo {

    private String outTradeNo;
    private String subject;
    private String gmtCreate;
    private String totalAmount;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
