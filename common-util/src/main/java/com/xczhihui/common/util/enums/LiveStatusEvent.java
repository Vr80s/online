package com.xczhihui.common.util.enums;

/**
 * 直播课状态更新事件
 *
 * @author hejiwei
 */
public enum LiveStatusEvent {

    START("start"),
    STOP("stop");

    private String name;

    LiveStatusEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
