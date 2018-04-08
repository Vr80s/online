package com.xczhihui.ask.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

/**
 * 标签web端调用的结果封装类
 * @author 王高伟
 */
public class TagVo extends OnlineBaseVo implements Comparable<TagVo> {
    private String id;
    /**
     * 学科id
     */
    private String menuId;
    /**
     * 标签名称
     */
    private String name;
    
    /**
     * 问题数
     */
    private Integer quesCount;
    /**
     * 引用数
     */
    private Integer citesCount;
    
    /**
     * 问题数排序
     */
    private String quesSort;
    
    
    /**
     * 引用数月查询
     */
    private String monthSort;
    
    /**
     * 禁用状态
     */
    private Integer status;
    /**
     * 引用数排序
     */
    private String citesSort;
    
    private String sortType;
    
    
    public String getCitesSort() {
		return citesSort;
	}
	public void setCitesSort(String citesSort) {
		this.citesSort = citesSort;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuesCount() {
		return quesCount;
	}
	public void setQuesCount(Integer quesCount) {
		this.quesCount = quesCount;
	}
	public Integer getCitesCount() {
		return citesCount;
	}
	public void setCitesCount(Integer citesCount) {
		this.citesCount = citesCount;
	}
	public String getQuesSort() {
		return quesSort;
	}
	public void setQuesSort(String quesSort) {
		this.quesSort = quesSort;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getMonthSort() {
		return monthSort;
	}
	public void setMonthSort(String monthSort) {
		this.monthSort = monthSort;
	}
	
	@Override
	public int compareTo(TagVo o) {
		if ("question".equals(this.sortType)) {
			if(this.quesCount > o.getQuesCount()){
				return -1;
			}
			if(this.quesCount < o.getQuesCount()){
				return 1;
			}
		} else if ("use".equals(this.sortType)) {
			if(this.citesCount > o.getCitesCount()){
				return -1;
			}
			if(this.citesCount < o.getCitesCount()){
				return 1;
			}
		}
		return 0;
	}
	
	
}
