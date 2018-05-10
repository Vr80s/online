package com.xczhihui.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xczhihui.common.util.bean.Page;

public class TableVo {

	private int iTotalRecords;

	private int iTotalDisplayRecords;

	// 每页显示数量
	private int iDisplayLength = 10;

	// 分页时每页跨度数量
	private int iDisplayStart = 10;

	private List aaData;

	// DataTable请求服务器端次数
	private String sEcho;

	private String sSortDir_0;

	private int iSortCol_0;

	private String sColumns;

	private int iSortingCols;

	// 列数
	private int iColumns;

	// 搜索条件
	private String sSearch;

	private Long unreadNum;

	@JsonIgnore
	private int currentPage;

	public int getCurrentPage() {
		return iDisplayStart / iDisplayLength + 1;
	}

	public TableVo fetch(Page<?> page) {
		int totalCount = page.getTotalCount();
		this.setAaData(page.getItems());
		this.setiTotalDisplayRecords(totalCount);
		this.setiTotalRecords(totalCount);
		return this;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public int getiColumns() {
		return iColumns;
	}

	public void setiColumns(int iColumns) {
		this.iColumns = iColumns;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List getAaData() {
		return aaData;
	}

	public void setAaData(List aaData) {
		this.aaData = aaData;
	}

	public String getsColumns() {
		return sColumns;
	}

	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}

	public int getiSortingCols() {
		return iSortingCols;
	}

	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	public String getsSortDir_0() {
		return sSortDir_0;
	}

	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}

	public int getiSortCol_0() {
		return iSortCol_0;
	}

	public void setiSortCol_0(int iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	public Long getUnreadNum() {
		return unreadNum;
	}

	public void setUnreadNum(Long unreadNum) {
		this.unreadNum = unreadNum;
	}

}
