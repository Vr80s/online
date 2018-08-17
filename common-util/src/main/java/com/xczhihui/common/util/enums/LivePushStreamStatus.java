package com.xczhihui.common.util.enums;

/**
 * @author hejiwei
 */
public enum LivePushStreamStatus {

    /**
     * 没有推流
     */
    NON_PUSH_STREAM(0),
    /**
     * 正在推流
     */
    PUSH_STREAM_ING(1);

    private int code;

    LivePushStreamStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
