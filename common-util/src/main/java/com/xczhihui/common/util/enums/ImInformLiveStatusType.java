package com.xczhihui.common.util.enums;

/**
 * 
 * IM 通知直播间 直播状态枚举
 * 
 * ClassName: ImInformLiveStatusType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum ImInformLiveStatusType {

		LIVE_START(2, "直播开始"),
		LIVE_END(3, "直播结束"),
		PLAYBACK_SUCCES(4, "回放生成成功"),
		PLAYBACK_FAILURE(5, "回放生产失败");
		
		/**
	     * 描述
	     **/
		private int code;
		private String text;
			
	    private ImInformLiveStatusType(int code,String text) {
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
