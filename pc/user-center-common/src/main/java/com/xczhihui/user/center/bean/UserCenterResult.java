package com.xczhihui.user.center.bean;

import java.io.Serializable;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 用户中心rest接口返回的对象。
 * 
 * @author liyong
 */
public class UserCenterResult implements Serializable {

	private static final long serialVersionUID = 1L;

	// 处理成功
	private static final int RESULT_CODE_SUCCESS = 0;

	// 处理失败
	private static final int RESULT_CODE_FAIL = -1;

	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private int resultCode = RESULT_CODE_SUCCESS;

	// 处理失败时的错误信息。
	private String errorMessage;
	/**
	 * 由处理结果生成的json字符串。接口返回的值不同该值不同。如：login接口返回是Token对象生成的json字符串；
	 * getUser接口返回的是ItcastUser对象生成的json字符串。
	 */
	private String resultObjectJson;

	/**
	 * 创建一个成功结果。
	 * 
	 * @param resultObject
	 *            业务接口返回值。
	 * @return
	 */
	public static UserCenterResult successResult(Object resultObject) {
		UserCenterResult result = new UserCenterResult();
		String json = "";
		if (resultObject != null) {
			Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_PATTERN)
			        .create();
			json = gson.toJson(resultObject);
		}
		result.setResultObjectJson(json);
		return result;
	}

	/**
	 * 创建一个失败结果。
	 * 
	 * @param errorMessage
	 *            错误信息。
	 * @return
	 */
	public static UserCenterResult failResult(String errorMessage) {
		UserCenterResult result = new UserCenterResult();
		result.setResultCode(RESULT_CODE_FAIL);
		result.setErrorMessage(errorMessage);
		return result;
	}

	/**
	 * 将resultObjectJson属性转成Java对象；服务器返回的结果是空这该方法返回null，服务器返回错误将抛异常。
	 * 参考属性resultObjectJson
	 * 
	 * @param resultType
	 * @return
	 */
	public <T> T parseResult(Type resultType) {
		if (this.isSuccess()) {
			String json = this.getResultObjectJson();
			if (json == null || json.length() < 1
			        || "null".equalsIgnoreCase(json)) {
				return null;
			}
			Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_PATTERN)
			        .create();
			return gson.fromJson(json, resultType);
		} else {
			throw new RuntimeException(this.getErrorMessage());
		}
	}

	public boolean isSuccess() {
		return this.resultCode == RESULT_CODE_SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getResultObjectJson() {
		return resultObjectJson;
	}

	public void setResultObjectJson(String resultObjectJson) {
		this.resultObjectJson = resultObjectJson;
	}

}
