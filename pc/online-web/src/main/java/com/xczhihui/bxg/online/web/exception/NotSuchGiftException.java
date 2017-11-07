package com.xczhihui.bxg.online.web.exception;

/** 
 * ClassName: NotSufficientFundsException.java <br>
 * Description:余额不足异常 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@SuppressWarnings("serial")
public class NotSuchGiftException extends RuntimeException{

	public NotSuchGiftException() {
		super("礼物不存在！");
	}
	
	public NotSuchGiftException(String msg) {
		super(msg);
	}
}
