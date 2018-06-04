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
public enum PagingFixedType {

	
		/************  客户端分页展示类别   *****************/
	
		RECOMMENDATION_PAGETYPE_UP(1, "客户端-->推荐->精品课程、最新课程-->固定展示6个",6),
		RECOMMENDATION_PAGETYPE_DOWN(2, "客户端-->推荐->分类课程-->固定展示4个",4),
		
		REAL_PAGETYPE_UP(3, "客户端-->线下课->全国课程-->固定展示6个",6),
		REAL_PAGETYPE_DOWN(4, "客户端-->线下课->其他城市-->固定展示4个",4),
	
		LIVE_PAGETYPE_UP(5, "客户端-->直播课->正在直播-->固定展示12个",12),
		LIVE_PAGETYPE_DOWN(6, "客户端-->直播课->其他状态-->固定展示4个",4),
	
		LISTEN(7, "听课-->固定展示4个",12),
	
		/************  PC客户端分页展示类别   *****************/
		
		PC_RECOMMENDATION(8, "PC端-推荐-->固定展示3个",3),
		
		PC_REAL_PAGETYPE_UP(9, "PC端-->线下课->全国课程-->固定展示12个",12),
		PC_REAL_PAGETYPE_DOWN(10, "PC端-->线下课->其他城市-->固定展示6个",6),
	
		PC_LIVE_PAGETYPE(11, "PC端-->直播课--》固定展示3个",3),
	
		PC_LISTEN(12, "PC端-->固定展示12个",12);
	
		
		/************  PC客户端分页展示类别   *****************/
		
	
	
	
		/**
	     * 描述
	     **/
		private int code;
		private String text;
		private Integer value;
			
	    private PagingFixedType(int code,String text,Integer value) {
			this.text = text;
			this.code = code;
			this.value = value;
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

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
		
		
	
}
