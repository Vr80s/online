package com.xczhihui.bxg.online.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by admin on 2016/7/29.
 */
public class NowTimeVo<T> {

    private boolean success;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private T data;

    private String error;

    public NowTimeVo(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public NowTimeVo(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
