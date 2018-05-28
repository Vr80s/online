package com.xczhihui.common.util.enums;

/**
 * 
 * 第三发登录类型
 * 
 * ClassName: CommonEnumsType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum CommonEnumsType {

		WECHAT_USERINFO_NOFOUND(1, "获取微信信息错误"),
		QQ_USERINFO_NOFOUND(2, "获取QQ信息错误"),
		WEIBO_USERINFO_NOFOUND(3, "获取微博信息错误");
		
		/**
	     * 描述
	     **/
		private int code;
		private String text;
			
	    private CommonEnumsType(int code,String text) {
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
