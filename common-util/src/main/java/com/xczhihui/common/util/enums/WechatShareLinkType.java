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
public enum WechatShareLinkType {

	
	    //首页
		HOME_PAGE("/xcview/html/home_page.html", "首页"),
	  
		/*************    课程的分享页面  开始       ***************/
		
		SCHOOL_AUDIO("/xcview/html/school_audio.html?shareBack=1&course_id=", "视频音频展示页"),
		SCHOOL_PLAY("/xcview/html/school_play.html?shareBack=1&course_id=", "直播展示页"),
		SCHOOL_CLASS("/xcview/html/school_class.html?shareBack=1&course_id=", "线下课展示页"),
		
		LIVE_AUDIO("/xcview/html/live_audio.html?shareBack=1&my_study=", "视频/音频"),
		LIVE_PLAY("/xcview/html/live_play.html?shareBack=1&my_study=", "直播"),
		LIVE_CLASS("/xcview/html/live_class.html?shareBack=1&my_study=", "线下课"),
		LIVE_SELECT_ALBUM("/xcview/html/live_select_album.html?shareBack=1&course_id=", "专辑播放页"),
		
		
		/*************    课程的分享页面  结束       ***************/
		
		UNSHELVE("/xcview/html/unshelve.html", "课程下架页面"),
		LIVE_PERSONAL("/xcview/html/live_personal.html?shareBack=1&userLecturerId=", "主播"),
		APPRENTICE("/xcview/html/apprentice/inherited_introduction.html?merId=","师承"),
		
		
		DOCDOT_SHARE("/xcview/html/home_page.html", "医师页面"),
		ACTICLE_SHARE("/xcview/html/home_page.html", "文章"),
		MEDICAL_CASES("/xcview/html/home_page.html","医案");
		
		/**
	     * 描述
	     **/
		private String link;
		private String text;
		
		private WechatShareLinkType(String link, String text) {
			this.link = link;
			this.text = text;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
			
	   
	
}
