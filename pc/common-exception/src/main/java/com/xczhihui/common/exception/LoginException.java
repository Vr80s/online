package com.xczhihui.common.exception;

import java.io.Serializable;

/**
 * Description：登录相关异常异常
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/25 0025 上午 9:06
 **/
public class LoginException extends IpandaTcmException implements Serializable {

    /**
     * Token 验证不通过
     */
    public static final LoginException TOKEN_IS_ILLICIT = new LoginException("Token 验证非法");

    /**
     * 未登录
     */
    public static final LoginException NOT_LOGGED_IN = new LoginException("未登录",true);



    public LoginException(String msg, boolean alarm) {
        super(msg);
        this.msg = msg;
        this.alarm = alarm;
    }


    public LoginException(String msg) {
        super(msg);
        this.msg = msg;
        this.alarm = false;
    }

}
