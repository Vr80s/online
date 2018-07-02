package com.xczhihui.medical.exception;

import java.io.Serializable;

import com.xczhihui.common.exception.IpandaTcmException;

/**
 * Description：主播工作台相关异常
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/25 0025 上午 11:27
 **/
public class AnchorWorkException extends IpandaTcmException implements Serializable {

    public AnchorWorkException(String msg, boolean alarm) {
        super(msg);
        this.alarm = alarm;
    }

    public AnchorWorkException(String msg) {
        super(msg);
        this.alarm = false;
    }

}
