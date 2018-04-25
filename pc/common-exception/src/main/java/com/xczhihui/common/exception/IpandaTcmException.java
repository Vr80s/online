package com.xczhihui.common.exception;

import java.io.Serializable;

/**
 * Description：业务异常基类，所有业务异常都必须继承于此异常
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/25 0025 上午 9:06
 **/
public class IpandaTcmException extends RuntimeException implements Serializable{

    protected String msg;
    protected boolean alarm;

    public IpandaTcmException() {
        super();
    }

    public IpandaTcmException(String msg) {
        super(msg);
        this.msg = msg;
        this.alarm = false;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 实例化异常
     * 
     * @param msgFormat
     * @return
     */
    public IpandaTcmException newInstance(String msgFormat) {
        return new IpandaTcmException(msgFormat);
    }

}
