package com.xczhihui.common.util.enums;

/**
 * 
 * banner图类型
 * 
 * ClassName: ThirdPartyType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum BannerType {

    	//1 推荐 2 线下课程 3 直播 4 听课 
	
		RECOMMENDATION(1, "推荐"),
		REAL(2, "线下课"),
		LIVE(3, "直播"),
		LISTEN(3, "听课");
		/**
	     * 描述
	     **/
		private int code;
		private String text;
			
	    private BannerType(int code,String text) {
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
