package com.xczhihui.bxg.online.manager.boxueshe.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class TagVo extends OnlineBaseVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private String name;
	
	private Integer articleCnt;
	
	private Integer articleCntLately;
	
	private Integer status;

	private Integer sortType;
	
	private Integer latelyNum;
	
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
	public Integer getArticleCnt() {
		return articleCnt;
	}
	public void setArticleCnt(Integer articleCnt) {
		this.articleCnt = articleCnt;
	}
	public Integer getArticleCntLately() {
		return articleCntLately;
	}
	public void setArticleCntLately(Integer articleCntLately) {
		this.articleCntLately = articleCntLately;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortType() {
		return sortType;
	}
	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}
	public Integer getLatelyNum() {
		return latelyNum;
	}
	public void setLatelyNum(Integer latelyNum) {
		this.latelyNum = latelyNum;
	}
}
