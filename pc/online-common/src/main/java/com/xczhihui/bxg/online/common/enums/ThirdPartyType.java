package com.xczhihui.bxg.online.common.enums;

/**
 * 
 * 第三发登录类型
 * 
 * ClassName: ThirdPartyType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum ThirdPartyType {

		WECHAT(1, "微信"),
		QQ(2, "QQ"),
		WEIBO(3, "微博");
		
		/**
	     * 描述
	     **/
		private int code;
		private String text;
			
	    private ThirdPartyType(int code,String text) {
			this.text = text;
			this.code = code;
		}
		 
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		
	
}
