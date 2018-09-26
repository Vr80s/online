package net.shopxx.merge.service;

public interface ShopReviewService {

	/**
	 * 查询评论列表
	 * <p>Title: list</p>  
	 * <p>Description: </p>  
	 * @param goodsPageParams
	 * @param orderType
	 * @return
	 */
	Object list(Long productId, Integer pageNumber, Integer pageSize);

	/**  
	 * <p>Title: addReview</p>  
	 * <p>Description: </p>  
	 * @param json
	 * @return  
	 */ 
	Object addReview(Long orderId,Object json,String accountId,String ip)throws Exception;

	
}
