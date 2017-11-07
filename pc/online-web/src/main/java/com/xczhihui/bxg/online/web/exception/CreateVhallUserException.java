package com.xczhihui.bxg.online.web.exception;

/** 
 * ClassName: NotSufficientFundsException.java <br>
 * Description:余额不足异常 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@SuppressWarnings("serial")
public class CreateVhallUserException extends RuntimeException{

	public CreateVhallUserException() {
		super("创建微吼用户失败！");
	}
	
	public CreateVhallUserException(String msg) {
		super(msg);
	}
}
