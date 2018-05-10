package com.xczhihui.bxg.online.web.base.common;

import com.xczhihui.common.util.bean.ResponseObject;

/**
 * 页面接口返回的对象。unloginedError=true表示未登录错误。
 * @see ResponseObject
 */
public class OnlineResponse extends ResponseObject {

	// 未登录错误
	private boolean unloginedError;

	public OnlineResponse(){

	}

	public OnlineResponse(Object resultObject) {
		super.setSuccess(true);
		super.setResultObject(resultObject);
	}

	public OnlineResponse(boolean loginedError, String errorMessage) {
		this.unloginedError = loginedError;
		super.setSuccess(false);
		super.setErrorMessage(errorMessage);
	}

	/**
	 * 构造一个表示错误(非未登录错误)的OnlineResponse对象。
	 * @param errorMessage
	 * @return
	 */
	public static OnlineResponse newErrorOnlineResponse(String errorMessage) {
		OnlineResponse res = new OnlineResponse(false, errorMessage);
		return res;
	}

	/**
	 * 构造一个未登录错误的OnlineResponse对象。
	 * @return
	 */
	public static OnlineResponse newUnloginErrorStudentResponse() {
		OnlineResponse res = new OnlineResponse(true, "未登录");
		return res;
	}

	/**
	 * 构造一个表示成功的OnlineResponse对象。
	 * @param resultObject
	 * @return
	 */
	public static OnlineResponse newSuccessOnlineResponse(Object resultObject) {
		OnlineResponse res = new OnlineResponse(resultObject);
		return res;
	}

	/**
	 * 构造一个表示成功的OnlineResponse对象。
	 * @param resultObject
	 * @return
	 */
	public static OnlineResponse newSuccessOnlineResponse(boolean success,String resultObject) {
		OnlineResponse res = new OnlineResponse(success,resultObject);
		return res;
	}
	public boolean isUnloginedError() {
		return unloginedError;
	}

	public void setUnloginedError(boolean unloginedError) {
		this.unloginedError = unloginedError;
	}

}
