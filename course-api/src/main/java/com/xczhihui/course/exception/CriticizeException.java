package com.xczhihui.course.exception;

import java.io.Serializable;

/**
 * Description：评论相关异常
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/25 0025 上午 11:27
 **/
public class CriticizeException extends IpandaTcmException implements Serializable {

    public CriticizeException(String msg, boolean alarm) {
        super(msg);
        this.alarm = alarm;
    }

    public CriticizeException(String msg) {
        super(msg);
        this.alarm = false;
    }

}
