package com.xczhihui.common.util.enums;

/**
 * @author hejiwei
 */
public enum ResourceCheck {

    COURSE(1),
    ARTICLE(2);

    private int code;

    ResourceCheck(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
