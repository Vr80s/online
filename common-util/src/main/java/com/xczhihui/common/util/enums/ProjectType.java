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
public enum ProjectType {

    	//1 推荐 2 线下课程 3 直播 4 听课 
	
		PROJECT(1, "专题--专题"),
		PROJECT_CATEGORY(2, "专题--分类");
		/**
	     * 描述
	     **/
		private int code;
		private String text;
			
	    private ProjectType(int code,String text) {
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
