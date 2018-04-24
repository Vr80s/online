package com.xczhihui.common.util.enums;


/**
 * 用户统一状态枚举类。包括登录状态、包括第三方绑定用户状态等
 * ClassName: UserUnitedStateType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum UserUnitedStateType {

	
	
	
	
	//code:1000 的 是有效  1002 是过期了  1003 是其他设备登录了  1001 token不能为空
	//app登录状态
	EFFECTIVE(1000, "有效"),
	TOKEN_ISNULL(1001, "token不能为空"),
	OVERDUE(1002, "过期"),
	WITHDRAWAL(1003, "其他设备登录"),
	
	
	//三方登录状态
	//200 已经绑定过，201 未绑定 202 数据异常
	BINDING(200, "第三方信息已绑定手机号。直接登录！"),
	UNBOUNDED(201, " 第三方信息没有绑定手机号。需要完善信息！"),
	
	MOBILE_BINDING(203, "手机号绑定第三方信息成功。"),
	MOBILE_UNBOUNDED(204, "手机号绑定第三方信息，第三方登录信息已经绑定了其他手机号了"),
	
	DATA_IS_WRONG(202, "数据异常"),
	
	
	//认证手机号是否绑定过了  
	PNHONE_NOT_THERE_ARE(400, "未注册手机号,可进行判断操作"),
	PNHONE_IS_WRONG(401, "已注册手机号,但是未绑定,可进行判断操作"),
	PNHONE_BINDING(402, "已经绑定过了,直接使用");
	
	
	/**
     * 描述
     **/
	private int code;
	private String text;
		
    private UserUnitedStateType(int code,String text) {
		this.text = text;
		this.code = code;
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
    
	
    public static UserUnitedStateType valueOf(int value) {
    	
        switch (value) {
        case 1000:
            return UserUnitedStateType.EFFECTIVE;
        case 1001:
            return UserUnitedStateType.TOKEN_ISNULL;
        case 1002:
            return UserUnitedStateType.OVERDUE;
        case 1003:
            return UserUnitedStateType.WITHDRAWAL;
        case 200:
            return UserUnitedStateType.BINDING;
        case 201:
            return UserUnitedStateType.UNBOUNDED;
        case 202:
            return UserUnitedStateType.DATA_IS_WRONG;
        case 400:
            return UserUnitedStateType.PNHONE_NOT_THERE_ARE;
        case 401:
            return UserUnitedStateType.PNHONE_IS_WRONG;
        case 402:
            return UserUnitedStateType.PNHONE_BINDING;
        default:
            return null;
        }
    }
	
    
    
}
