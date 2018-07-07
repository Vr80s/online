package com.xczhihui.online.api.exception;

import java.io.Serializable;

import com.xczhihui.common.exception.IpandaTcmException;

/**
 * Description：礼物相关异常
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/7/7 0025 上午 11:27
 **/
public class GiftException extends IpandaTcmException implements Serializable {

    public GiftException(String msg, boolean alarm) {
        super(msg);
        this.alarm = alarm;
    }

    public GiftException(String msg) {
        super(msg);
        this.alarm = false;
    }

}
