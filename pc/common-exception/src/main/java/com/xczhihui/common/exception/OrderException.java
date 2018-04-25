package com.xczhihui.common.exception;

import java.io.Serializable;

/**
 * Description：订单相关异常
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/25 0025 上午 9:06
 **/
public class OrderException extends IpandaTcmException implements Serializable {

    public OrderException(String msg, boolean alarm) {
        super(msg);
        this.msg = msg;
        this.alarm = alarm;
    }


    public OrderException(String msg) {
        super(msg);
        this.msg = msg;
//        订单异常发送告警邮件
        this.alarm = true;
//        this.alarm = false;
    }

}
