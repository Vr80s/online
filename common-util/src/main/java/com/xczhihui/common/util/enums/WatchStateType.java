package com.xczhihui.common.util.enums;

/**
 * 
* @ClassName: WatchStateType
* @Description: 
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年8月16日
*
 */

public enum WatchStateType {


	PAY(0, "付费"),
	FREE(1, "免费"),
    PURCHASED(2, "已购买");
	
	private int code;
    private String text;

    WatchStateType(int code,String text) {
        this.code = code;
        this.text = text;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

   
    
}
