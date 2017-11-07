package com.xczh.consumer.market.utils;

import java.io.Serializable;

/**
 * 请求的返回结果。 
 * 如果:success=true resultObject=处理结果 
 * 如果:success=false errorMessage=错误信息
 * 
 * @author Alex Wang
 */
public class ResponseObject implements Serializable {

	private boolean success;

	private String errorMessage;

	private Object resultObject;
	
	private Integer code;
	
	/**
	 * 构造一个错误响应对象
	 * @param errorMessage
	 * @return
	 */
	public static ResponseObject newErrorResponseObject(String errorMessage) {
		ResponseObject res = new ResponseObject();
		res.setSuccess(false);
		res.setErrorMessage(errorMessage);
		return res;
	}
	
	/**
	 * 构造一个错误响应对象带有code的
	 * @param errorMessage
	 * @return
	 */
	public static ResponseObject newErrorResponseObject(String errorMessage,Integer code) {
		ResponseObject res = new ResponseObject();
		res.setSuccess(false);
		res.setErrorMessage(errorMessage);
		res.setCode(code);
		return res;
	}

	/**
	 * 构造一个成功响应对象
	 * @param resultObject
	 * @return
	 */
	public static ResponseObject newSuccessResponseObject(Object resultObject) {
		ResponseObject res = new ResponseObject();
		res.setSuccess(true);
		res.setResultObject(resultObject);
		return res;
	}
	/**
	 * 构造一个成功响应对象   带有 code 值的
	 * @param resultObject
	 * @return
	 */
	public static ResponseObject newSuccessResponseObject(Object resultObject,Integer code) {
		ResponseObject res = new ResponseObject();
		res.setSuccess(true);
		res.setResultObject(resultObject);
		res.setCode(code);
		return res;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
