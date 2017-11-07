package com.xczhihui.bxg.online.web.exception;

/** 
 * ClassName: NotSufficientFundsException.java <br>
 * Description:余额不足异常 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@SuppressWarnings("serial")
public class NotSufficientFundsException extends RuntimeException{

	public NotSufficientFundsException() {
		super("您的平台余额不足，请及时充值！");
	}
	
	public NotSufficientFundsException(String msg) {
		super(msg);
	}
}
