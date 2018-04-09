package com.xczhihui.bxg.common.util.enums;

/**
 * 发送短信类型           枚举  1注册，2重置密码, 3 原来手机号获取验证码 ,4 新的手机号获取验证码
 * ClassName: SMSCode.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月29日<br>
 */
public enum SMSCode {
	
	//注册，忘记秘密使用
	RETISTERED(1, "注册"),
	FORGOT_PASSWORD(2, "重置密码"),
	
	
	//更换手机号使用
	OLD_PHONE(3, "原来手机号获取验证码"),
	NEW_PHONE(4, "新的手机号获取验证码"),
	
	//提现 短信验证
	WITHDRAWAL(5, "提现短信验证");
	
	/**
     * 描述
     **/
	private int code;
	private String text;
		
    private SMSCode(int code,String text) {
		this.text = text;
		this.code = code;
	}
	 
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
    public static boolean contains(Integer type){  
        for(SMSCode typeEnum : SMSCode.values()){  
            if(typeEnum.getCode() == type){  
                return true;  
            }  
        }  
        return false;  
    }  
}
