package com.xczhihui.common.exception;

import java.io.Serializable;

/**
 * Description：登录注册相关异常异常
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/25 0025 上午 9:06
 **/
public class LoginRegException extends IpandaTcmException implements Serializable {

    /**
     * Token 验证不通过
     */
    public static final LoginRegException TOKEN_IS_ILLICIT = new LoginRegException("Token 验证非法");

    /**
     * 未登录
     */
    public static final LoginRegException NOT_LOGGED_IN = new LoginRegException("未登录",true);



    public LoginRegException(String msg, boolean alarm) {
        super(msg);
        this.msg = msg;
        this.alarm = alarm;
    }


    public LoginRegException(String msg) {
        super(msg);
        this.msg = msg;
        this.alarm = false;
    }

}
