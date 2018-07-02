package com.xczhihui.course.vo;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public class PayMessage {

    private static final long serialVersionUID = 1L;

    private String type;

    private String userId;

    private BigDecimal value;

    private Integer from;

    public static PayMessage getPayMessage(String payMessage) {
        return JSONObject.parseObject(payMessage.replace("|", "\""), PayMessage.class);
    }

    public static String getPayMessage(PayMessage payMessage) {
        return JSONObject.toJSON(payMessage).toString().replaceAll("\"", "|");
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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


}
