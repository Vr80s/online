package com.xczhihui.bxg.user.center.exception;

import com.xczhihui.common.exception.IpandaTcmException;

import java.io.Serializable;

/**
 * Description：登录注册相关异常异常
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/25 0025 上午 9:06
 **/
public class LoginRegException extends IpandaTcmException implements Serializable {

    public LoginRegException(String msg, boolean alarm) {
        super(msg);
        this.alarm = alarm;
    }


    public LoginRegException(String msg) {
        super(msg);
        this.alarm = false;
    }

}
