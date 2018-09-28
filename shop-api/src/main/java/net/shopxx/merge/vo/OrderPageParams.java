/**
 * <p>Title: GoodsPageParams.java</p>
 * <p>Description: </p>
 *
 * @author yangxuan@ixincheng.com
 * @date 2018年9月13日
 */
package net.shopxx.merge.vo;

import java.io.Serializable;
import java.util.Date;

/*
 * @author yangxuan
 * @ClassName: GoodsPageParams
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @email yangxuan@ixincheng.com
 * @date 2018年9月13日
 */
public class OrderPageParams implements Serializable {

	
	
	
	private Integer pageNumber = 1;

	private Integer pageSize = 10;

	private String sn; // 订单编号

	private String productName; // 商品名

	private Date startDate;

	private Date endDate;
	
	
	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {

		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "OrderPageParams [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", sn=" + sn + ", productName="
				+ productName + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
