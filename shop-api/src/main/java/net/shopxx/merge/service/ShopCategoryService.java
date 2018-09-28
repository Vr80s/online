package net.shopxx.merge.service;

import java.util.List;

import net.shopxx.merge.vo.ProductCategoryVO;

public interface ShopCategoryService {


	Object list();

	/**  
	 * <p>Title: details</p>  
	 * <p>Description: </p>  
	 * @param productCateId
	 * @return  
	 */ 
	Object details(Long productCateId);
	
}
