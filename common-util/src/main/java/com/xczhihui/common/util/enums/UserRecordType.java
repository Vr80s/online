package com.xczhihui.common.util.enums;

/**
 * 记录用户操作信息类型
 * @author yangxuan
 *
 */
public enum UserRecordType {

	
	    //1 增加学习记录 2 增加观看记录
	    LEARN_RECORD(1, "增加学习记录"),
		WATCH_RECORD(2, "增加观看记录");
		
		/**
	     * 描述
	     **/
		private int code;
		private String text;
			
	    private UserRecordType(int code,String text) {
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
	
		
		public static String getAllToString() {
			String all = "";
			for (MyCourseType e : MyCourseType.values()) {
				all+=e.toString()+",";
		    }
			return all;
		}
		
		// 覆盖方法
        @Override
        public String toString() {
            return this.code + ":" + this.text;
        }
	
}
