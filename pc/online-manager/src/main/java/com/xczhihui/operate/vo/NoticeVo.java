package com.xczhihui.operate.vo;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class NoticeVo extends OnlineBaseVo  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String noticeContent;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date showStartTime;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date showEndTime;
    private Integer status;
    private String createPersonName;

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public java.util.Date getShowStartTime() {
		return showStartTime;
	}
	public void setShowStartTime(java.util.Date showStartTime) {
		this.showStartTime = showStartTime;
	}
	public java.util.Date getShowEndTime() {
		return showEndTime;
	}
	public void setShowEndTime(java.util.Date showEndTime) {
		this.showEndTime = showEndTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreatePersonName() {
		return createPersonName;
	}
	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}
}
