package com.xczhihui.common.exception;

import java.io.Serializable;

/**
 * Description：粉丝相关异常
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/25 0025 上午 11:27
 **/
public class FansException extends IpandaTcmException implements Serializable {

    public FansException(String msg, boolean alarm) {
        super(msg);
        this.msg = msg;
        this.alarm = alarm;
    }

    public FansException(String msg) {
        super(msg);
        this.msg = msg;
        this.alarm = false;
    }

}
