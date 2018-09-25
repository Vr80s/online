/**  
* <p>Title: GoodsPageParams.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年9月13日 
*/  
package net.shopxx.merge.vo;

import java.io.Serializable;

/**
* @ClassName: GoodsPageParams
* @Description: TODO(这里用一句话描述这个类的作用)
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年9月13日
*
*/

public class GoodsPageParams implements Serializable{

	/**
	 * 排序类型
	 * 	推荐，按照医师动态推荐次数由多到少排序；     -->加个字段，表示医师推荐个数。医师推荐个数
	 *	最热，按照销售数量由多到少排序；		   -->按照销量排序	
	 *	最新，按照编辑时间由近到远排序；		   -->最后修改时间排序	
	 *	价格，按照价格由高到低、低到高排序；            -->按照价格排序
	 */
	public enum OrderType {
		/**
		 * 推荐，按照医师动态推荐次数由多到少排序
		 */
		RECOMMEND_DESC,
		
		/**
		 * 销量降序
		 */
		SALES_DESC,
	
		/**
		 * 日期降序
		 */
		DATE_DESC,

		
		/**
		 * 价格升序
		 */
		PRICE_ASC,
		
		/**
		 * 价格降序
		 */
		PRICE_DESC

	}

	private Integer pageNumber = 1;
	
	
	private Integer pageSize = 10;

	
	public Integer getPageNumber() {
        int num = (pageNumber - 1) * pageSize;
        num = num < 0 ? 0 : num;
		return num;
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

	@Override
	public String toString() {
		return "GoodsPageParams [pageNumber=" + pageNumber + ", pageSize=" + pageSize + "]";
	}
	
}
