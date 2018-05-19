package com.xczhihui.course.enums;

/**
 * @author hejiwei
 */
public enum MessageTypeEnum {

    SYSYTEM(0),
    COURSE(1),
    FEEDBACK(2),
    ASK_QUESTION(3),
    REPLY(4);

    MessageTypeEnum(int val) {
        this.val = val;
    }

    private int val;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
