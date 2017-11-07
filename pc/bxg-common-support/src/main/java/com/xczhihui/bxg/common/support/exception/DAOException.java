package com.xczhihui.bxg.common.support.exception;

/**
 * 数据访问层异常
 * 
 * @author 李勇 create on 2015-11-06
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = -3399426470936768563L;

	public DAOException() {
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

}
