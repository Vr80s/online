package com.xczhihui.bxg.online.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChapterVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	//节
	private List<ChapterVo> chapterSons = new ArrayList<ChapterVo>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ChapterVo> getChapterSons() {
		return chapterSons;
	}

	public void setChapterSons(List<ChapterVo> chapterSons) {
		this.chapterSons = chapterSons;
	}

}
