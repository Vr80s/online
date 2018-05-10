package com.xczhihui.common.util.enums;

/**
 * 
 * 回放状态类型
 * 
 * ClassName: ThirdPartyType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum PlayBackType {

		//0表示生成中，1表示生成成功，2表示生成失败
		GENERATION(0, "生成中"),
		GENERATION_SUCCESS(1, "生成成功"),
		GENERATION_FAILURE(2, "生成失败");
		
		/**
	     * 描述
	     **/
		private int code;
		private String text;
			
	    private PlayBackType(int code,String text) {
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
		
		
		public static String getTypeText(int code){
	        for (MyCourseType e : MyCourseType.values()) {
	            if(e.getCode() == code){
	                return e.getText();
	            }
	        }
	        return null;
	    }
	
}
