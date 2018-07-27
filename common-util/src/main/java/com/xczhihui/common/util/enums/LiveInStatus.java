package com.xczhihui.common.util.enums;

/**
 * 分享类型
 * <p>
 * ClassName: ThirdPartyType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum LiveInStatus {

    //	normal live   直播中类型。1、正常直播 2、直播退出并没有结束(当前没有画面)
    NORMAL_LIVE(1, "正常直播"),
    EXIT_LIVE(2, "退出直播并没有结束");

    /**
     * 描述
     **/
    private Integer code;
    private String text;

    private LiveInStatus(Integer code, String text) {
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
