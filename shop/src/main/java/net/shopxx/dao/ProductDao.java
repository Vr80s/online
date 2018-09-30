/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: t/Rx/lp+kdI5A9JihoBz/oqnZKu33Qbd
 */
package net.shopxx.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xczhihui.common.util.bean.ShareInfoVo;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.*;
import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.vo.GoodsPageParams;
import net.shopxx.merge.vo.ProductQueryParam;
import net.shopxx.merge.vo.ProductVO;

/**
 * Dao - 商品
 *
 * @author SHOP++ Team
 * @version 6.1
 */
public interface ProductDao extends BaseDao<Product, Long> {

    /**
     * 查找商品
     *
     * @param type                 类型
     * @param store                店铺
     * @param productCategory      商品分类
     * @param storeProductCategory 店铺商品分类
     * @param brand                品牌
     * @param promotion            促销
     * @param productTag           商品标签
     * @param storeProductTag      店铺商品标签
     * @param attributeValueMap    属性值Map
     * @param startPrice           最低价格
     * @param endPrice             最高价格
     * @param isMarketable         是否上架
     * @param isList               是否列出
     * @param isTop                是否置顶
     * @param isActive             是否有效
     * @param isOutOfStock         是否缺货
     * @param isStockAlert         是否库存警告
     * @param hasPromotion         是否存在促销
     * @param orderType            排序类型
     * @param count                数量
     * @param filters              筛选
     * @param orders               排序
     * @return 商品
     */
    List<Product> findList(Product.Type type, Store store, ProductCategory productCategory, StoreProductCategory storeProductCategory, Brand brand, Promotion promotion, ProductTag productTag, StoreProductTag storeProductTag, Map<Attribute, String> attributeValueMap, BigDecimal startPrice,
                           BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType, Integer count, List<Filter> filters, List<Order> orders);

    /**
     * 查找商品
     *
     * @param productCategory      商品分类
     * @param storeProductCategory 店铺商品分类
     * @param isMarketable         是否上架
     * @param isActive             是否有效
     * @param beginDate            起始日期
     * @param endDate              结束日期
     * @param first                起始记录
     * @param count                数量
     * @return 商品
     */
    List<Product> findList(ProductCategory productCategory, StoreProductCategory storeProductCategory, Boolean isMarketable, Boolean isActive, Date beginDate, Date endDate, Integer first, Integer count);

    /**
     * 查找商品
     *
     * @param rankingType 排名类型
     * @param store       店铺
     * @param count       数量
     * @return 商品
     */
    List<Product> findList(Product.RankingType rankingType, Store store, Integer count);

    /**
     * 查找商品分页
     *
     * @param type                 类型
     * @param storeType            店铺类型
     * @param store                店铺
     * @param productCategory      商品分类
     * @param storeProductCategory 店铺商品分类
     * @param brand                品牌
     * @param promotion            促销
     * @param productTag           商品标签
     * @param storeProductTag      店铺商品标签
     * @param attributeValueMap    属性值Map
     * @param startPrice           最低价格
     * @param endPrice             最高价格
     * @param isMarketable         是否上架
     * @param isList               是否列出
     * @param isTop                是否置顶
     * @param isActive             是否有效
     * @param isOutOfStock         是否缺货
     * @param isStockAlert         是否库存警告
     * @param hasPromotion         是否存在促销
     * @param orderType            排序类型
     * @param pageable             分页信息
     * @return 商品分页
     */
    Page<Product> findPage(Product.Type type, Store.Type storeType, Store store, ProductCategory productCategory, StoreProductCategory storeProductCategory, Brand brand, Promotion promotion, ProductTag productTag, StoreProductTag storeProductTag, Map<Attribute, String> attributeValueMap,
                           BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType, Pageable pageable);

    /**
     * 查询商品数量
     *
     * @param type         类型
     * @param store        店铺
     * @param isMarketable 是否上架
     * @param isList       是否列出
     * @param isTop        是否置顶
     * @param isActive     是否有效
     * @param isOutOfStock 是否缺货
     * @param isStockAlert 是否库存警告
     * @return 商品数量
     */
    Long count(Product.Type type, Store store, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert);

    /**
     * 清除商品属性值
     *
     * @param attribute 属性
     */
    void clearAttributeValue(Attribute attribute);

    /**
     * 刷新过期店铺商品有效状态
     */
    void refreshExpiredStoreProductActive();

    /**
     * 刷新商品有效状态
     *
     * @param store 店铺
     */
    void refreshActive(Store store);

    /**
     * 上架商品
     *
     * @param ids ID
     */
    void shelves(Long[] ids);

    /**
     * 下架商品
     *
     * @param ids ID
     */
    void shelf(Long[] ids);

    /**
     * <p>Title: findPage</p>
     * <p>Description: </p>
     *
     * @param goodsPageParams
     * @return
     */
    List<Product> findPageXc(GoodsPageParams goodsPageParams, OrderType orderType);

    /**
     * 查询商品信息，饥饿加载店铺
     *
     * @param id id
     * @return Product
     */
    Product findFetchStore(Long id);

	/**
	 * <p>Title: findByProductNameAndId</p>  
	 * <p>Description: </p>  
	 * @param productCategoryId
	 * @return  
	 */ 
    List<Map<String,Object>> findIdByCategoryId(ProductCategory productCategory);

	/**  
	 * <p>Title: findPageKayWordXc</p>  
	 * <p>Description: </p>  
	 * @param goodsPageParams
	 * @param orderType
	 * @return  
	 */ 
	List<Product> findPageKayWordXc(GoodsPageParams goodsPageParams, OrderType orderType);

    /**
     * 医师id查询医师商品列表
     *
     * @param storeIds          storeIds
     * @param productQueryParam productQueryParam
     * @return
     */
    net.shopxx.merge.page.Page<ProductVO> listByStoreId(List<Long> storeIds, ProductQueryParam productQueryParam);

	/**  
	 * <p>Title: 通过商品id获取商品分享信息findIdByShareInfo</p>  
	 * <p>Description: </p>  
	 * @param productId
	 * @return  
	 */ 
	ShareInfoVo findIdByShareInfo(Long productId);
}