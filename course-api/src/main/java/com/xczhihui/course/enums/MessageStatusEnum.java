package com.xczhihui.course.enums;

/**
 * @author hejiwei
 */
public enum MessageStatusEnum {

    //启用
    ENABLE((short) 1),
    DISABLE((short) 0);

    private short val;

    MessageStatusEnum(short val) {
        this.val = val;
    }

    public short getVal() {
        return val;
    }

    public void setVal(short val) {
        this.val = val;
    }
}
