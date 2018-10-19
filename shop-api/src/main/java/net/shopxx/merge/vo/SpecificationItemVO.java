/**  
* <p>Title: SpecificationItem.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年9月19日 
*/  
package net.shopxx.merge.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* @ClassName: SpecificationItem
* @Description: 规格条目
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年9月19日
*
*/

public class SpecificationItemVO implements Serializable {

	
	private static final long serialVersionUID = 7623999885848444842L;
	
	
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 条目
	 */
	private List<SpecificationItemVO.Entry> entries = new ArrayList<>();
	
	
	
	
	
	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public List<SpecificationItemVO.Entry> getEntries() {
		return entries;
	}





	public void setEntries(List<SpecificationItemVO.Entry> entries) {
		this.entries = entries;
	}





	/**
	 * Entity - 条目
	 * 
	 * @author ixincheng
	 * @version 6.1
	 */
	public static class Entry implements Serializable {

		private static final long serialVersionUID = 4793372555875531705L;

		/**
		 * ID
		 */
		private Integer id;

		/**
		 * 值
		 */
		private String value;

		/**
		 * 是否已选
		 */
		private Boolean isSelected;

		/**
		 * 获取ID
		 * 
		 * @return ID
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * 设置ID
		 * 
		 * @param id
		 *            ID
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * 获取值
		 * 
		 * @return 值
		 */
		public String getValue() {
			return value;
		}

		/**
		 * 设置值
		 * 
		 * @param value
		 *            值
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * 获取是否已选
		 * 
		 * @return 是否已选
		 */
		public Boolean getIsSelected() {
			return isSelected;
		}

		/**
		 * 设置是否已选
		 * 
		 * @param isSelected
		 *            是否已选
		 */
		public void setIsSelected(Boolean isSelected) {
			this.isSelected = isSelected;
		}
	}
	
	
	
	
}
