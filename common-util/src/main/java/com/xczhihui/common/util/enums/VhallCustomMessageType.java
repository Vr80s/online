package com.xczhihui.common.util.enums;

/**
 * 
* @ClassName: VhallCustomMessageType
* @Description:微吼自定义消息类型枚举
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年8月5日
*
 */
public enum VhallCustomMessageType {

    //	10：聊天消息  11：礼物消息   12：回放生成成功  13：回放生成失败
    CHAT_MESSAGE(10, "聊天消息"),
    GIFT_MESSAGE(11, "礼物消息"),
	
	PLAYBACK_GENERATION_SECCESS(16, "回放生成成功"),
	PLAYBACK_GENERATION_FAILURE(17, "回放生成失败");

    /**
     * 描述
     **/
    private Integer code;
    private String text;

    private VhallCustomMessageType(Integer code, String text) {
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
