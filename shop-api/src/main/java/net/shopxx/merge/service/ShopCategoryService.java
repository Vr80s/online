package net.shopxx.merge.service;

public interface ShopCategoryService {


	/**
	 * 
	 * <p>Title: list</p>  
	 * <p>Description:查询一级分类 </p>  
	 * @return
	 */
	Object list();

	/**  
	 * <p>Title: details</p>  
	 * <p>Description: 查询分类详情</p>  
	 * @param productCateId 分类id
	 * @return  
	 */ 
	Object details(Long productCateId);
	
}
