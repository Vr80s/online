package net.shopxx.merge.service;

import net.shopxx.merge.vo.GoodsPageParams;

public interface GoodsService {

	/**
	 * 查询商品列表
	 * <p>Title: list</p>  
	 * <p>Description: </p>  
	 * @param goodsPageParams
	 * @param orderType
	 * @return
	 */
	Object list(GoodsPageParams goodsPageParams,GoodsPageParams.OrderType orderType);
	
	/**
	 * 查找商品详情，根据商品id
	 * <p>Title: findProductById</p>  
	 * <p>Description: </p>  
	 * @param productId
	 * @return
	 */
	Object findProductById(Long productId);
	
	
	Object findIdByCategoryId(Long categoryId);
}
