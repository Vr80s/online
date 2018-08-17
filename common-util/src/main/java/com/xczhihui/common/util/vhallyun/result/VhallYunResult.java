package com.xczhihui.common.util.vhallyun.result;

import java.io.Serializable;

/**
 * 微吼云接口返回结果接收实体
 *
 * @author hejiwei
 */
public class VhallYunResult implements Serializable {

    private String code;

    private String msg;

    private Object data;

    @Override
    public String toString() {
        return "VhallYunResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isOk() {
        return "200".equals(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
