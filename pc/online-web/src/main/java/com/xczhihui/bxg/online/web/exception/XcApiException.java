package com.xczhihui.bxg.online.web.exception;

/**
 * api异常
 *
 * @author liutao 【jvmtar@gmail.com】
 * @create 2017-08-23 10:56
 **/
public class XcApiException extends Exception{

    private static final long serialVersionUID = -238091758285157331L;
    private String errCode;
    private String errMsg;

    public XcApiException() {
    }

    public XcApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public XcApiException(String message) {
        super(message);
    }

    public XcApiException(Throwable cause) {
        super(cause);
    }

    public XcApiException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
