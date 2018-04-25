package com.xczhihui.common.exception;

import java.io.Serializable;

/**
 * Description：用户信息相关异常
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/25 0025 上午 11:27
 **/
public class UserInfoException extends IpandaTcmException implements Serializable {

    public UserInfoException(String msg, boolean alarm) {
        super(msg);
        this.msg = msg;
        this.alarm = alarm;
    }

    public UserInfoException(String msg) {
        super(msg);
        this.msg = msg;
        this.alarm = false;
    }

}
