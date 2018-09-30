package net.shopxx.merge.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.Pageable;
import net.shopxx.Results;
import net.shopxx.Setting;
import net.shopxx.dao.ProductDao;
import net.shopxx.dao.ReviewDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Product;
import net.shopxx.entity.Review;
import net.shopxx.entity.Sku;
import net.shopxx.entity.Review.Entry;
import net.shopxx.merge.entity.UsersRelation;
import net.shopxx.merge.service.ShopReviewService;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.merge.vo.ReviewVO;
import net.shopxx.merge.vo.UsersVO;
import net.shopxx.service.OrderItemService;
import net.shopxx.service.OrderService;
import net.shopxx.util.SystemUtils;

/**
 * 熊猫中医与shop用户关系
 */
@Service
public class ShopReviewServiceImpl implements ShopReviewService {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShopReviewServiceImpl.class);
	
	@Inject
	private ReviewDao reviewDao;
	
	@Inject
	private ProductDao productDao;
	
	@Inject
	private OrderService orderService;
	
	@Inject
	private UsersRelationService usersRelationService;
	
	@Inject
	private OrderItemService orderItemService;

	
	@Override
	@Transactional
	public Object list(Long productId, Integer pageNumber, Integer pageSize) {
		Product find = productDao.find(productId);
		List<Review> content = reviewDao.findPage(null, find, null, null, true, 
				new Pageable(pageNumber,pageSize)).getContent();
		List<ReviewVO> convertReviewVos = convertReviewVos(content);
		return convertReviewVos;
	}
	
	public List<ReviewVO> convertReviewVos(List<Review> content){
		if(content!=null && content.size()>0) {
			List<ReviewVO> reviewVos = new ArrayList<ReviewVO>();
			for (Review review : content) {
				ReviewVO reviewVo = new ReviewVO();
				UsersVO usersVO = new UsersVO();
				BeanUtils.copyProperties(review,reviewVo);
				if(review.getSpecifications().size()>0) {
					String specifications = review.getSpecifications().stream().collect(Collectors.joining(";"));
					reviewVo.setSpecifications(specifications);
				}
				BeanUtils.copyProperties(review.getMember(),usersVO);
				
				reviewVo.setUser(usersVO);
				
				reviewVos.add(reviewVo);
			}
			return reviewVos;
		}
		return null;
	}

	@Override
	@Transactional
	public Object addReview(Long orderId,Object obj,String accountId,String ip) throws Exception{

		/**
		 * 数据检验
		 */
		try {
			
			Setting setting = SystemUtils.getSetting();
			if (!setting.getIsReviewEnabled()) {
				return Results.unprocessableEntity("member.review.disabled");
			}
			Order order = orderService.find(orderId);
			
			Member currentUser = usersRelationService.getMemberByIpandatcmUserId(accountId);
			
			if (order == null || !currentUser.equals(order.getMember()) || order.getIsReviewed() || CollectionUtils.isEmpty(order.getOrderItems())) {
				return Results.UNPROCESSABLE_ENTITY;
			}
			if (!Order.Status.RECEIVED.equals(order.getStatus()) && !Order.Status.COMPLETED.equals(order.getStatus())) {
				return Results.UNPROCESSABLE_ENTITY;
			}
			
			
			org.json.JSONObject json = new org.json.JSONObject(obj);
	    	
			LOGGER.info("物流服务"+json.getInt("logistics"));
			LOGGER.info("卖家服务"+json.getInt("seller"));
			
			if(json.get("logistics") ==null || json.get("seller")==null) {
				throw new Exception("请填写必要的评价信息");
			}
			
			JSONArray jsonArray = json.getJSONArray("reviewEntryList");
			if(jsonArray.length()<=0) {
				throw new Exception("请填写必要的评价信息");
			}
			
			for (int i = 0; i < jsonArray.length(); i++) {
				
				JSONObject jsonI = (JSONObject) jsonArray.get(i);
				 
				Object long1 = jsonI.get("orderItemId");
				Object int1 = jsonI.get("score");
				Object string = jsonI.get("content");
				
				if(long1==null || int1 ==null || string ==null) {
					throw new Exception("请填写必要的评价信息");
				}
				
				OrderItem pOrderItem = orderItemService.find(jsonI.getLong("orderItemId"));
				if (pOrderItem == null || pOrderItem == null) {
					throw new Exception("评论条目有误");
				}
				Sku sku = pOrderItem.getSku();
				if (sku == null) {
					continue;
				}
				if (!order.equals(pOrderItem.getOrder())) {
					return Results.UNPROCESSABLE_ENTITY;
				}
				
				Review pReview = new Review();
				pReview.setScore(jsonI.getInt("score"));
				pReview.setContent(jsonI.getString("content"));
				pReview.setIp(ip);
				pReview.setMember(currentUser);
				pReview.setProduct(sku.getProduct());
				pReview.setStore(sku.getProduct().getStore());
				pReview.setReplyReviews(null);
				pReview.setForReview(null);
				pReview.setSpecifications(pOrderItem.getSpecifications());
				pReview.setIsShow(setting.getIsReviewCheck() ? false : true);
				reviewDao.persist(pReview);
				
			}
			order.setIsReviewed(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存课程信息有误");
		}
		return null;
	}
	
}