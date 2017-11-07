package com.xczhihui.bxg.common.support.exception;

/**
 * 业务逻辑层异常
 * 
 * @author 李勇 create on 2015-11-06
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -3399426470936768563L;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
