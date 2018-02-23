package com.xczhihui.medical.anchor.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserCoinIncreaseVO implements Serializable {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 课程名
     */
    private String gradeName;

    /**
     * 课程类型
     */
    private Integer type;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付人
     */
    private String name;

    /**
     * 实际支付的价格
     */
    private String actualPay;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 苹果扣除的总数
     */
    private BigDecimal iosBrokerageValue;

    /**
     * 课程获得总熊猫币
     */
    private BigDecimal value;


    /**
     * 直播开始时间
     */
    private Date startTime;

    /**
     * 礼物总价
     */
    private BigDecimal giftTotalPrice;

    /**
     * 直播id
     */
    private String liveId;

    /**
     * 礼物赠送人id
     */
    private String giver;

    /**
     * 分成比例
     */
    private String percent;

}
