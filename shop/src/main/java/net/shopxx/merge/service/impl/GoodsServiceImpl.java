package net.shopxx.merge.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.bean.ShareInfoVo;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.vo.OeUserVO;

import net.sf.json.JSONObject;
import net.shopxx.dao.ProductCategoryDao;
import net.shopxx.dao.ProductDao;
import net.shopxx.dao.ReviewDao;
import net.shopxx.dao.StoreDao;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.ProductImage;
import net.shopxx.entity.Review;
import net.shopxx.entity.Sku;
import net.shopxx.entity.SpecificationItem;
import net.shopxx.entity.Store;
import net.shopxx.entity.SpecificationItem.Entry;
import net.shopxx.exception.ResourceNotFoundException;
import net.shopxx.merge.entity.UsersRelation;
import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.page.Page;
import net.shopxx.merge.page.Pageable;
import net.shopxx.merge.service.GoodsService;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.merge.vo.GoodsPageParams;
import net.shopxx.merge.vo.ProductImageVO;
import net.shopxx.merge.vo.ProductQueryParam;
import net.shopxx.merge.vo.ProductVO;
import net.shopxx.merge.vo.ReviewVO;
import net.shopxx.merge.vo.SkuVO;
import net.shopxx.merge.vo.SpecificationItemVO;
import net.shopxx.merge.vo.UsersVO;

/**
 * 熊猫中医与shop用户关系
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Inject
    private ProductDao productDao;

    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;


    @Autowired
    private CacheService redisCacheService;

    @Inject
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private StoreDao storeDao;

    @Inject
    private UsersRelationService usersRelationService;

    @Autowired
    private UserCenterService userCenterService;
    
    @Autowired
    private ReviewDao  reviewDao;
    
    @Autowired
    private  CommonService commonService;


    @Value("${defaultHead}")
    private String defaultHead;

    @Override
    @Transactional
    public Object list(GoodsPageParams goodsPageParams, OrderType orderType) {

        LOGGER.info("goodsPageParams:" + goodsPageParams.toString() + ",orderType:" + (orderType != null ? orderType.toString() : null));

        List<Product> list = productDao.findPageXc(goodsPageParams, orderType);
        List<ProductVO> beanUtislist = new ArrayList<ProductVO>();

        for (Product product : list) {
            ProductVO pro = new ProductVO();
            //普通属性
            BeanUtils.copyProperties(product, pro);

            pro.setId(product.getId());

            Map<String, Object> doctorInfoByStore = commonService.getDoctorInfoByStore(product.getStore());
            
            
            pro.setDoctor(doctorInfoByStore);
            
            //商品图片
            pro.setProductImages(convertProductimages(product));

            beanUtislist.add(pro);
        }
        return beanUtislist;
    }


    @Override
    @Transactional
    public Object findProductById(Long productId){

        LOGGER.info("productId:" + productId);

        Product product = productDao.find(productId);
        if (product == null) {
            throw new RuntimeException("商品找不到。productId："+productId);
        }
        
        ProductVO pv = new ProductVO();

        org.springframework.beans.BeanUtils.copyProperties(product, pv);

        pv.setId(product.getId());

        String doctorId = product.getStore().getBusiness().getDoctorId();
        
        LOGGER.info("doctorId:==================" + doctorId);
        
        //医师推荐
        List<Map<String, Object>> posts = medicalDoctorPostsService.getProductPostsByProductIdAndDoctorId(productId,doctorId,0, 1);
        pv.setPosts(posts);

        //评论
        pv.setReviewvs(convertReview(product));

        //商品规格
        pv.setSpecificationItemvs(convertSpecificationItem(product));

        //商品图片
        pv.setProductImages(convertProductimages(product));

        //是否缺货
        pv.setIsOutOfStock(product.getIsOutOfStock());

        //市场价
        pv.setMarketprice(product.getDefaultSku().getMarketPrice());

        //分类id
        pv.setProductcategoryId(product.getProductCategory().getId());

        //库存转换
        pv.setSkuVOs(convertProductSku(product));
        
        //此商品评论总数
        long reviewvCount = reviewDao.calculateScoreCount(product);
        pv.setReviewvCount(reviewvCount);
        
        
        return pv;
    }


    /**
     * <p>Title: convertProductSku</p>
     * <p>Description: </p>
     *
     * @param product
     * @return
     */
    private Set<SkuVO> convertProductSku(Product product) {
        if (product.getSkus().size() > 0) {
            Set<Sku> skus = product.getSkus();
            Set<SkuVO> skuVos = new HashSet<SkuVO>();
            for (Sku sku : skus) {
                SkuVO skuVo = new SkuVO();
                org.springframework.beans.BeanUtils.copyProperties(sku, skuVo);
                skuVo.setSpecificationValueIds(sku.getSpecificationValueIds());
                skuVo.setIsOutOfStock(sku.getIsOutOfStock());
                skuVo.setId(sku.getId());
                skuVos.add(skuVo);
            }
            return skuVos;
        }
        return null;

    }


    /**
     * <p>Title: convertProductimages</p>
     * <p>Description: 图片转换</p>
     *
     * @param product
     * @return
     */
    public List<ProductImageVO> convertProductimages(Product product) {
        if (product.getProductImages().size() > 0) {
            List<ProductImageVO> productVoImages = new ArrayList<ProductImageVO>();
            List<ProductImage> productImages = product.getProductImages();
            for (ProductImage productImage : productImages) {
                ProductImageVO piv = new ProductImageVO();
                BeanUtils.copyProperties(productImage, piv);
                productVoImages.add(piv);
            }
            return productVoImages;
        }
        return null;
    }


    /**
     * <p>Title: convertReview</p>
     * <p>Description: 评论转换</p>
     *
     * @param setReview
     * @return
     */
    public List<SpecificationItemVO> convertSpecificationItem(Product product) {
        if (product.hasSpecification()) {
        	List<SpecificationItemVO> specificationItemVOs = new ArrayList<SpecificationItemVO>();
            List<SpecificationItem> specificationItems = product.getSpecificationItems();
            for (SpecificationItem specificationItem : specificationItems) {
                SpecificationItemVO specificationItemVO = new SpecificationItemVO();
                List<SpecificationItemVO.Entry> entrieVOs = new ArrayList<SpecificationItemVO.Entry>();
                List<Entry> entries = specificationItem.getEntries();
                for (Entry entry : entries) {
                    SpecificationItemVO.Entry entryVO = new SpecificationItemVO.Entry();
                    BeanUtils.copyProperties(entry, entryVO);
                    entrieVOs.add(entryVO);
                }
                specificationItemVO.setName(specificationItem.getName());
                specificationItemVO.setEntries(entrieVOs);
                specificationItemVOs.add(specificationItemVO);
            }
            return specificationItemVOs;
        }
        return null;
    }


    public Set<ReviewVO> convertReview(Product product) {
        Set<Review> setReview = product.getReviews();
        
        for (Review review : setReview) {
        	if(review.getIsShow()) {
            	
        		ReviewVO reviewVo = new ReviewVO();
    			UsersVO usersVO = new UsersVO();
    			BeanUtils.copyProperties(review,reviewVo);
    			if(review.getSpecifications().size()>0) {
    				String specifications = review.getSpecifications().stream().collect(Collectors.joining(";"));
    				reviewVo.setSpecifications(specifications);
    			}
    			BeanUtils.copyProperties(review.getMember(),usersVO);
    			usersVO.setId(review.getMember().getId());
    			
    			/**
    			 * 存放redis里面吧
    			 */
    			UsersRelation usersRelation =  usersRelationService.findByUserId(review.getMember().getId());
    			
    			LOGGER.warn("usersRelation:"+usersRelation);
    			
    			if(usersRelation!=null) {
    				OeUserVO oeUserVO = redisCacheService.get(RedisCacheKey.OE_USER_INFO+RedisCacheKey.REDIS_SPLIT_CHAR
    			    		+usersRelation.getIpandatcmUserId());
    				
    				LOGGER.warn("oeUserVO:"+oeUserVO);
    				
    				if(oeUserVO==null) {
    					oeUserVO = userCenterService.getUserVOById(usersRelation.getIpandatcmUserId());
    					redisCacheService.set(RedisCacheKey.OE_USER_INFO+RedisCacheKey.REDIS_SPLIT_CHAR
    				    		+usersRelation.getIpandatcmUserId(), oeUserVO);
    				}
    				LOGGER.warn("oeUserVO:"+oeUserVO.getSmallHeadPhoto());
    				usersVO.setHeadPhoto(oeUserVO.getSmallHeadPhoto());
    				usersVO.setName(oeUserVO.getName());
    				usersVO.setUsername(oeUserVO.getName());
    			}else {
    				usersVO.setHeadPhoto(defaultHead);
    				//usersVO.setName(oeUserVO.getName());
    			}
    			
    			reviewVo.setUser(usersVO);
                
                Set<ReviewVO> reviewVos = new HashSet<ReviewVO>();
                reviewVos.add(reviewVo);
                return reviewVos;
        	}
		}
        return null;
    }
    
    
    @Override
    @Transactional
    public Object findIdByCategoryId(Long categoryId) {
        ProductCategory find = productCategoryDao.find(categoryId);
        try {
            List<Map<String, Object>> list = productDao.findIdByCategoryId(find);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public Object listDoctorProduct(ProductQueryParam productQueryParam, String doctorId) {
        if (productQueryParam.isAll()) {
            return productDao.listByStoreId(Collections.EMPTY_LIST, productQueryParam);
        } else {
            List<Long> storeIds = storeDao.findByDoctorId(doctorId);
            if (storeIds.isEmpty()) {
                return Page.empty(new Pageable(productQueryParam.getPageNumber(), productQueryParam.getPageSize()));
            }
            return productDao.listByStoreId(storeIds, productQueryParam);
        }
    }

    @Override
    public ProductVO getProductById(Long id) {
        Product product = productDao.find(id);
        if (product == null) {
            throw new IllegalArgumentException("商品id参数错误");
        }
        ProductVO productVO = new ProductVO();
        productVO.setId(id);
        productVO.setSpecificationItemvs(convertSpecificationItem(product));
        Set<SkuVO> skuVOs = product.getSkus().stream().map(sku -> {
            SkuVO skuVO = new SkuVO();
            org.springframework.beans.BeanUtils.copyProperties(sku, skuVO);
            skuVO.setId(sku.getId());
            skuVO.setSpecifications(sku.getSpecifications() != null ? sku.getSpecifications().stream().collect(Collectors.joining(",")) : null);
            return skuVO;
        }).collect(Collectors.toSet());
        productVO.setSkuVOs(skuVOs);
        
        productVO.setProductImages(convertProductimages(product));
        
        return productVO;
    }

    @Override
    public ShareInfoVo findIdByShareInfo(String shareId) {
        return productDao.findIdByShareInfo(Long.parseLong(shareId));
    }

    @Transactional
    @Override
    public void updateClick(String userId, Long id) {
        try {
            Product product = productDao.find(id);
            product.setHits(product.getHits() + 1);
            if (!redisCacheService.sismenber(Product.USER_VIEW_CACHE_NAME + ":" + id, userId)) {
                redisCacheService.sadd(Product.USER_VIEW_CACHE_NAME + ":" + id, userId);
            }
            Set<String> smembers = redisCacheService.smembers(Product.USER_VIEW_CACHE_NAME + ":" + id);
            product.setUv(smembers != null ? smembers.size() : 0L);
            productDao.persist(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Transactional
    @Override
    public void modifyAddDoctorRecommends(Long id){
    	Product product = productDao.find(id);
		if(product!=null) {
			Integer doctorRecommends = product.getDoctorRecommends();
			product.setDoctorRecommends(doctorRecommends!=null ?  doctorRecommends+1 : 1);
			productDao.flush();
		}
    }
    
}