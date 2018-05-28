package com.xczhihui.common.util.enums;

/**
 * 课程状态
 *
 * @author hejwiei
 */

public enum CourseStatus {

    ENABLE("1"),
    DISABLE("0");

    private String code;

    CourseStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
