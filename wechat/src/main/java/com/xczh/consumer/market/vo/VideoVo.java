package com.xczh.consumer.market.vo;

import java.io.Serializable;

public class VideoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String v_id;
	private String video_id;
	private String video_name;
	private String chapter_id;
	public String getVideo_id() {
		return video_id;
	}
	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}
	public String getVideo_name() {
		return video_name;
	}
	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}
	public String getV_id() {
		return v_id;
	}
	public void setV_id(String v_id) {
		this.v_id = v_id;
	}
	public String getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(String chapter_id) {
		this.chapter_id = chapter_id;
	}
	
	

}
