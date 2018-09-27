package net.shopxx.merge.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAccountService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;

import net.sf.json.JSONObject;
import net.shopxx.dao.ProductCategoryDao;
import net.shopxx.dao.ProductDao;
import net.shopxx.dao.StoreDao;
import net.shopxx.entity.*;
import net.shopxx.entity.SpecificationItem.Entry;
import net.shopxx.exception.ResourceNotFoundException;
import net.shopxx.merge.entity.UsersRelation;
import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.page.Page;
import net.shopxx.merge.page.Pageable;
import net.shopxx.merge.service.GoodsService;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.merge.vo.*;

/**
 * 熊猫中医与shop用户关系
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Inject
    private ProductDao productDao;

    @Inject
    private UsersRelationService usersRelationService;

    @Autowired
    private IMedicalDoctorAccountService medicalDoctorAccountService;

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

    @Override
    @Transactional
    public Object list(GoodsPageParams goodsPageParams, OrderType orderType) {

        LOGGER.info("goodsPageParams:" + goodsPageParams.toString() + ",orderType:" + (orderType != null ? orderType.toString() : null));

        List<Product> list = productDao.findPageXc(goodsPageParams, orderType);
        List<ProductVO> beanUtislist = new ArrayList<ProductVO>();

        for (Product product : list) {
            ProductVO pro = new ProductVO();
            try {
                //普通属性
                BeanUtils.copyProperties(pro, product);

                //医师推荐信息
                LOGGER.info("product.getStore().getBusiness().getDoctorId() " + product.getStore().getBusiness().getDoctorId());
                
                String key = RedisCacheKey.STORE_DOCTOR_RELEVANCE +
                        RedisCacheKey.REDIS_SPLIT_CHAR + product.getStore().getId();

                String value = redisCacheService.get(key);
                if (value != null) {
                    LOGGER.info("value :" + value);
                    JSONObject jasonObject = JSONObject.fromObject(value);
                    pro.setDoctor((Map) jasonObject);
                } else {
                    
                	String doctorId = product.getStore().getBusiness().getDoctorId();
                	
                    if (doctorId != null) {
                        
                        Map<String, Object> map = medicalDoctorBusinessService.getDoctorInfoByDoctorId(doctorId);
                        
                        LOGGER.info("map tostring " + (map != null ? map.toString() : null));
                        JSONObject jasonObject = JSONObject.fromObject(map);
                        redisCacheService.set(key, jasonObject.toString());
                        pro.setDoctor(jasonObject);
                    }
                }

                //商品图片
                pro.setProductImages(convertProductimages(product));

                beanUtislist.add(pro);

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return beanUtislist;
    }


    @Override
    @Transactional(readOnly = true)
    public Object findProductById(Long productId) {

        LOGGER.info("productId:" + productId);

        Product product = productDao.find(productId);
        if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
            throw new ResourceNotFoundException();
        }
        ProductVO pv = new ProductVO();

        //普通属性值增加
//        try {
//        	org.springframework.beans.BeanUtils.copyProperties(pv, product);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
        
        org.springframework.beans.BeanUtils.copyProperties(product,pv);

        //医师推荐
        Set<Map<String, Object>> posts = medicalDoctorPostsService.getProductPostsByProductId(productId, 0, 1);
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


        return pv;
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
                try {
                    BeanUtils.copyProperties(piv, productImage);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
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
    public Set<SpecificationItemVO> convertSpecificationItem(Product product) {
        if (product.hasSpecification()) {
            Set<SpecificationItemVO> specificationItemVOs = new HashSet<SpecificationItemVO>();
            List<SpecificationItem> specificationItems = product.getSpecificationItems();
            for (SpecificationItem specificationItem : specificationItems) {
                SpecificationItemVO specificationItemVO = new SpecificationItemVO();
                List<SpecificationItemVO.Entry> entrieVOs = new ArrayList<SpecificationItemVO.Entry>();
                List<Entry> entries = specificationItem.getEntries();
                for (Entry entry : entries) {
                    SpecificationItemVO.Entry entryVO = new SpecificationItemVO.Entry();
                    try {
                        BeanUtils.copyProperties(entryVO, entry);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
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

        if (setReview != null && setReview.iterator().hasNext()) {
            ReviewVO reviewVo = new ReviewVO();
            UsersVO usersVO = new UsersVO();
            try {
                Review review = setReview.iterator().next();

                BeanUtils.copyProperties(reviewVo, review);
                if (review.getSpecifications().size() > 0) {
                    String specifications = review.getSpecifications().stream().collect(Collectors.joining(";"));
                    reviewVo.setSpecifications(specifications);
                }

                BeanUtils.copyProperties(usersVO, review.getMember());
                reviewVo.setUser(usersVO);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            Set<ReviewVO> reviewVos = new HashSet<ReviewVO>();
            reviewVos.add(reviewVo);
            return reviewVos;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Object findIdByCategoryId(Long categoryId) {

        LOGGER.info("啦啦啦啦==========================");
        ProductCategory find = productCategoryDao.find(categoryId);
        List<Map<String, Object>> list = productDao.findIdByCategoryId(find);
        LOGGER.info("哈哈哈哈==========================");
        return list;
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
}