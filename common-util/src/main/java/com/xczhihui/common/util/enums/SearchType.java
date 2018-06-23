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
public enum SearchType {

	
	
	   //1 默认检索提示   2 热门搜索
	   DEFAULT_SEARCH(1, "默认检索提示"),
	   HOT_SEARCH(2, "热门搜索");
	
		/**
	     * 描述
	     **/
		private int code;
		private String text;
			
	    private SearchType(int code,String text) {
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