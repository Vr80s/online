package net.shopxx.merge.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.shopxx.Pageable;
import net.shopxx.dao.ReviewDao;
import net.shopxx.entity.Product;
import net.shopxx.entity.Review;
import net.shopxx.merge.service.ShopReviewService;
import net.shopxx.merge.vo.ReviewVO;
import net.shopxx.merge.vo.UsersVO;

/**
 * 熊猫中医与shop用户关系
 */
@Service
public class ShopReviewServiceImpl implements ShopReviewService {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShopReviewServiceImpl.class);
	
	@Inject
	private ReviewDao reviewDao;

	
	@Override
	public Object list(Long productId, Integer pageNumber, Integer pageSize) {
		Product product = new Product();
		product.setId(productId);
		
		List<Review> content = reviewDao.findPage(null, product, null, null, true, 
				new Pageable(pageNumber,pageSize)).getContent();
		
		List<ReviewVO> convertReviewVos = convertReviewVos(content);
		
		LOGGER.info("convertReviewVos size " + convertReviewVos.size());
		return convertReviewVos;
	}
	
	public List<ReviewVO> convertReviewVos(List<Review> content){
		if(content!=null && content.size()>0) {
			List<ReviewVO> reviewVos = new ArrayList<ReviewVO>();
			for (Review review : content) {
				ReviewVO reviewVo = new ReviewVO();
				UsersVO usersVO = new UsersVO();
				try {
					BeanUtils.copyProperties(reviewVo,review);
					if(review.getSpecifications().size()>0) {
						String specifications = review.getSpecifications().stream().collect(Collectors.joining(";"));
						reviewVo.setSpecifications(specifications);
					}
					BeanUtils.copyProperties(usersVO, review.getMember());
					reviewVo.setUser(usersVO);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				reviewVos.add(reviewVo);
			}
			return reviewVos;
		}
		return null;
	}
	
}