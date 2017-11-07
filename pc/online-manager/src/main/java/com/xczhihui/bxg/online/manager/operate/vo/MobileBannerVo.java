package com.xczhihui.bxg.online.manager.operate.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class MobileBannerVo extends OnlineBaseVo  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
    private String url;
    private String imgPath;
    private Integer clickSum;
    private String createPerson;
    private java.util.Date createTime;
    private Integer status;
    private Integer seq;
    private String createPersonName;

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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getClickSum() {
		return clickSum;
	}
	public void setClickSum(Integer clickSum) {
		this.clickSum = clickSum;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getCreatePersonName() {
		return createPersonName;
	}
	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}
}