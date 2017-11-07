/**
 * 
 */
package com.xczhihui.bxg.common.util.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李勇 create on 2015-11-04
 */
public class Page<T> implements Serializable{

	/**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = -2420827387931675697L;

	public static final int DEFAULT_PAGE_SIZE = 15;

	private List<T> items;

	private int totalCount;// 总记录数

	private int totalPageCount;// 总页数

	private int pageSize;// 每页记录个数

	private int currentPage;// 当前页数

	/**
	 * 
	 * @param items
	 *            包含一页的数据。
	 * @param totalCount
	 *            总数据数
	 * @param pageSize
	 *            每页个数
	 * @param currentPage
	 *            当前页码 从1开始。
	 */
	public Page(List<T> items, int totalCount, int pageSize, int currentPage) {
		this.items = items;
		this.pageSize = pageSize > 1 ? pageSize : 1;
		this.currentPage = currentPage > 0 ? currentPage : 1;
		this.totalCount = totalCount;
		this.calProperties();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("totalCount:" + totalCount);
		sb.append(" totalPageCount:" + totalPageCount);
		sb.append(" pageSize:" + pageSize);
		sb.append(" currentPage:" + currentPage);
		sb.append(" items.size:" + items.size());
		return sb.toString();
	}

	/**
	 * 获取上一页页码，不会小于1。
	 * 
	 * @return
	 */
	public int getPrevPage() {
		int pre = this.currentPage - 1;
		pre = pre < 1 ? 1 : pre;
		pre = pre < totalPageCount ? pre : totalPageCount;
		return pre;
	}

	/**
	 * 获取下一页页码，不会超过总页数。
	 * 
	 * @return
	 */
	public int getNextPage() {
		int next = this.currentPage + 1;
		next = next > 1 ? next : 1;
		next = next < totalPageCount ? next : totalPageCount;
		return next;
	}

	/**
	 * 当前页码
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	/**
	 * 每页个数
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.calProperties();
	}

	/**
	 * 总记录数
	 * 
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		this.calProperties();
	}

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public int getTotalPageCount() {
		return totalPageCount;
	}

	private void calProperties() {
		if (this.totalCount > 0) {
			this.totalPageCount = this.totalCount / this.pageSize;
			if (this.totalCount % this.pageSize > 0)
				this.totalPageCount++;
		} else {
			this.totalCount = 0;
			this.totalPageCount = 0;
		}
	}

}
