package com.xczh.consumer.market.vo;

import java.io.Serializable;
import java.util.List;

public class ChapterVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String chapter_id;
	private String chapter_name;
	private List<VideoVo> videos;
	
	
	public String getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(String chapter_id) {
		this.chapter_id = chapter_id;
	}
	
	public String getChapter_name() {
		return chapter_name;
	}
	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}
	public List<VideoVo> getVideos() {
		return videos;
	}
	public void setVideos(List<VideoVo> videos) {
		this.videos = videos;
	}
	

}
