package com.xczhihui.common.util.enums;

/**
 * 
* @ClassName: LiveCaseType
* @Description: 直播中的状况
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年8月9日
*
 */
public enum LiveCaseType {

    NORMAL_LIVE(1, "正常直播"),
    EXIT_BUT_NOT_END(2, "退出但不结束");

    /**
     * 描述
     **/
    private Integer code;
    private String text;

    private LiveCaseType(Integer code, String text) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
