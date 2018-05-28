package com.xczhihui.common.util.enums;

/**
 * 
 * 阿里短信服务  返回的信息提示   只拿到几个有用的。
 * 	  参考官网：https://help.aliyun.com/knowledge_detail/57717.html?spm=a2c4g.11186623.2.9.RgRRSp
 * ClassName: AliSMSServiceMessageType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum AliSMSServiceMessageType {

	
	
	    //isp.SYSTEM_ERROR	isp.SYSTEM_ERROR	请重试接口调用，如仍存在此情况请创建工单反馈工程师查看
	    //isv.BUSINESS_LIMIT_CONTROL  将短信发送频率限制在正常的业务流控范围内，默认流控：短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天。
		// 触发天级流控Permits:10   
	
	//Minutes, hours, days.
		SYSTEM_ERROR("isp.SYSTEM_ERROR", "请重试接口调用,如仍存在此情况请创建工单反馈工程师查看"),
		BUSINESS_LIMIT_CONTROL("isv.BUSINESS_LIMIT_CONTROL", "业务限流，支持1条/分钟，5条/小时 ，累计10条/天"),
		BUSINESS_LIMIT_CONTROL_HOURS("触发小时级流控", "业务限流，支持5条/小时"),
		BUSINESS_LIMIT_CONTROL_DAYS("触发天级流控", "业务限流，支持累计10条/天"),
		
		
		OK("OK", "请求成功");
		/**
	     * 描述
	     **/
		private String code;
		private String message;
		
		private AliSMSServiceMessageType(String code, String message) {
			this.code = code;
			this.message = message;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		
	
}
