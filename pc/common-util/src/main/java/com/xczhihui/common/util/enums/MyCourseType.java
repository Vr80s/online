package com.xczhihui.common.util.enums;

/**
 * 
 * 第三发登录类型
 * 
 * ClassName: ThirdPartyType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum MyCourseType {

		ALL_COURSE(1, "所有课程"),
		OVER_COURSE(2, "已结束");
		
		/**
	     * 描述
	     **/
		private int code;
		private String text;
			
	    private MyCourseType(int code,String text) {
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
		public static void main(String[] args) {
			System.out.println(getTypeText(3));
			System.out.println(MyCourseType.getAllToString());
		}
		
}
