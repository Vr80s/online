package net.shopxx.merge.service;

public interface ShopReviewService {

	/**
	 * 
	 * <p>Title: list</p>  
	 * <p>Description: 商品列表</p>  
	 * @param productId  商品id
	 * @param pageNumber 当前第几页
	 * @param pageSize	  每页多少条
	 * @return
	 */
	Object list(Long productId, Integer pageNumber, Integer pageSize);

	/**
	 * 
	 * <p>Title: addReview</p>  
	 * <p>Description:增加评论 </p>  
	 * @param orderId  订单id
	 * @param json     评论内容封装的json数据
	 * @param accountId 熊猫中医用户id
	 * @param ip	   ip地址	
	 * @return
	 * @throws Exception
	 */
	Object addReview(Long orderId,Object json,String accountId,String ip)throws Exception;

	
}
