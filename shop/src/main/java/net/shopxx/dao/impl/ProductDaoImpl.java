/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: h/Mf1r32rYP9i6awpROKpuM+PXcuXESs
 */
package net.shopxx.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xczhihui.common.util.bean.ShareInfoVo;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.dao.ProductDao;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.ProductTag;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Sku;
import net.shopxx.entity.Store;
import net.shopxx.entity.StoreProductCategory;
import net.shopxx.entity.StoreProductTag;
import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.vo.GoodsPageParams;
import net.shopxx.merge.vo.ProductImageVO;
import net.shopxx.merge.vo.ProductQueryParam;
import net.shopxx.merge.vo.ProductVO;
import net.shopxx.util.SystemUtils;

/**
 * Dao - 商品
 *
 * @author SHOP++ Team
 * @version 6.1
 */
@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoImpl.class);

    @Override
    public List<Product> findList(Product.Type type, Store store, ProductCategory productCategory,
                                  StoreProductCategory storeProductCategory, Brand brand, Promotion promotion, ProductTag productTag,
                                  StoreProductTag storeProductTag, Map<Attribute, String> attributeValueMap, BigDecimal startPrice,
                                  BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive,
                                  Boolean isOutOfStock, Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType,
                                  Integer count, List<Filter> filters, List<Order> orders) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (productCategory != null) {
            Subquery<ProductCategory> subquery = criteriaQuery.subquery(ProductCategory.class);
            Root<ProductCategory> subqueryRoot = subquery.from(ProductCategory.class);
            subquery.select(subqueryRoot);
            subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, productCategory),
                    criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR
                            + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.in(root.get("productCategory")).value(subquery));
        }
        if (storeProductCategory != null) {
            Subquery<StoreProductCategory> subquery = criteriaQuery.subquery(StoreProductCategory.class);
            Root<StoreProductCategory> subqueryRoot = subquery.from(StoreProductCategory.class);
            subquery.select(subqueryRoot);
            subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, storeProductCategory),
                    criteriaBuilder.like(subqueryRoot.<String>get("treePath"),
                            "%" + StoreProductCategory.TREE_PATH_SEPARATOR + storeProductCategory.getId()
                                    + StoreProductCategory.TREE_PATH_SEPARATOR + "%")));
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.in(root.get("storeProductCategory")).value(subquery));
        }
        if (brand != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
        }
        if (promotion != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("promotions"), promotion));
        }
        if (productTag != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.join("productTags"), productTag));
        }
        if (storeProductTag != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.join("storeProductTags"), storeProductTag));
        }
        if (attributeValueMap != null) {
            for (Map.Entry<Attribute, String> entry : attributeValueMap.entrySet()) {
                String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
                restrictions = criteriaBuilder.and(restrictions,
                        criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
            }
        }
        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal temp = startPrice;
            startPrice = endPrice;
            endPrice = temp;
        }
        if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("price"), startPrice));
        }
        if (endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("price"), endPrice));
        }
        if (isMarketable != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
        }
        if (isList != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
        }
        if (isTop != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
        }
        if (isActive != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isActive"), isActive));
        }
        if (isOutOfStock != null) {
            Subquery<Sku> subquery = criteriaQuery.subquery(Sku.class);
            Root<Sku> subqueryRoot = subquery.from(Sku.class);
            subquery.select(subqueryRoot);
            Path<Integer> stock = subqueryRoot.get("stock");
            Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
            if (isOutOfStock) {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root),
                        criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
            } else {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root),
                        criteriaBuilder.greaterThan(stock, allocatedStock));
            }
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
        }
        if (isStockAlert != null) {
            Subquery<Sku> subquery = criteriaQuery.subquery(Sku.class);
            Root<Sku> subqueryRoot = subquery.from(Sku.class);
            subquery.select(subqueryRoot);
            Path<Integer> stock = subqueryRoot.get("stock");
            Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
            Setting setting = SystemUtils.getSetting();
            if (isStockAlert) {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root), criteriaBuilder
                        .lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
            } else {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root), criteriaBuilder
                        .greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
            }
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
        }
        if (hasPromotion != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(root.join("promotions")));
        }
        criteriaQuery.where(restrictions);
        if (orderType != null) {
            switch (orderType) {
                case TOP_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case PRICE_ASC:
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case PRICE_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case SALES_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case SCORE_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case DATE_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
                    break;
            }
        } else if (CollectionUtils.isEmpty(orders)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")),
                    criteriaBuilder.desc(root.get("createdDate")));
        }
        return super.findList(criteriaQuery, null, count, filters, orders);
    }

    @Override
    public List<Product> findList(ProductCategory productCategory, StoreProductCategory storeProductCategory,
                                  Boolean isMarketable, Boolean isActive, Date beginDate, Date endDate, Integer first, Integer count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (productCategory != null) {
            Subquery<ProductCategory> subquery = criteriaQuery.subquery(ProductCategory.class);
            Root<ProductCategory> subqueryRoot = subquery.from(ProductCategory.class);
            subquery.select(subqueryRoot);
            subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, productCategory),
                    criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR
                            + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.in(root.get("productCategory")).value(subquery));
        }
        if (storeProductCategory != null) {
            Subquery<StoreProductCategory> subquery = criteriaQuery.subquery(StoreProductCategory.class);
            Root<StoreProductCategory> subqueryRoot = subquery.from(StoreProductCategory.class);
            subquery.select(subqueryRoot);
            subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, storeProductCategory),
                    criteriaBuilder.like(subqueryRoot.<String>get("treePath"),
                            "%" + StoreProductCategory.TREE_PATH_SEPARATOR + storeProductCategory.getId()
                                    + StoreProductCategory.TREE_PATH_SEPARATOR + "%")));
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.in(root.get("storeProductCategory")).value(subquery));
        }
        if (isMarketable != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
        }
        if (isActive != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isActive"), isActive));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, first, count);
    }

    @Override
    public List<Product> findList(Product.RankingType rankingType, Store store, Integer count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        if (rankingType != null) {
            switch (rankingType) {
                case SCORE:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")),
                            criteriaBuilder.desc(root.get("scoreCount")));
                    break;
                case SCORE_COUNT:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreCount")),
                            criteriaBuilder.desc(root.get("score")));
                    break;
                case WEEK_HITS:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("weekHits")));
                    break;
                case MONTH_HITS:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("monthHits")));
                    break;
                case HITS:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("hits")));
                    break;
                case WEEK_SALES:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("weekSales")));
                    break;
                case MONTH_SALES:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("monthSales")));
                    break;
                case SALES:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")));
                    break;
            }
        }
        if (store != null) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("store"), store));
        }
        return super.findList(criteriaQuery, null, count);
    }

    @Override
    public Page<Product> findPage(Product.Type type, Store.Type storeType, Store store, ProductCategory productCategory,
                                  StoreProductCategory storeProductCategory, Brand brand, Promotion promotion, ProductTag productTag,
                                  StoreProductTag storeProductTag, Map<Attribute, String> attributeValueMap, BigDecimal startPrice,
                                  BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive,
                                  Boolean isOutOfStock, Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType,
                                  Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (storeType != null) {
            Subquery<Store> subquery = criteriaQuery.subquery(Store.class);
            Root<Store> subqueryRoot = subquery.from(Store.class);
            subquery.select(subqueryRoot);
            subquery.where(criteriaBuilder.equal(subqueryRoot.get("type"), storeType));
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("store")).value(subquery));
        }
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (productCategory != null) {
            Subquery<ProductCategory> subquery = criteriaQuery.subquery(ProductCategory.class);
            Root<ProductCategory> subqueryRoot = subquery.from(ProductCategory.class);
            subquery.select(subqueryRoot);
            subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, productCategory),
                    criteriaBuilder.like(subqueryRoot.<String>get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR
                            + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.in(root.get("productCategory")).value(subquery));
        }
        if (storeProductCategory != null) {
            Subquery<StoreProductCategory> subquery = criteriaQuery.subquery(StoreProductCategory.class);
            Root<StoreProductCategory> subqueryRoot = subquery.from(StoreProductCategory.class);
            subquery.select(subqueryRoot);
            subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subqueryRoot, storeProductCategory),
                    criteriaBuilder.like(subqueryRoot.<String>get("treePath"),
                            "%" + StoreProductCategory.TREE_PATH_SEPARATOR + storeProductCategory.getId()
                                    + StoreProductCategory.TREE_PATH_SEPARATOR + "%")));
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.in(root.get("storeProductCategory")).value(subquery));
        }
        if (brand != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
        }
        if (promotion != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("promotions"), promotion));
        }
        if (productTag != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.join("productTags"), productTag));
        }
        if (storeProductTag != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.join("storeProductTags"), storeProductTag));
        }
        if (attributeValueMap != null) {
            for (Map.Entry<Attribute, String> entry : attributeValueMap.entrySet()) {
                String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
                restrictions = criteriaBuilder.and(restrictions,
                        criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
            }
        }
        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal temp = startPrice;
            startPrice = endPrice;
            endPrice = temp;
        }
        if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("price"), startPrice));
        }
        if (endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("price"), endPrice));
        }
        if (isMarketable != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
        }
        if (isList != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
        }
        if (isTop != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
        }
        if (isActive != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isActive"), isActive));
        }
        if (isOutOfStock != null) {
            Subquery<Sku> subquery = criteriaQuery.subquery(Sku.class);
            Root<Sku> subqueryRoot = subquery.from(Sku.class);
            subquery.select(subqueryRoot);
            Path<Integer> stock = subqueryRoot.get("stock");
            Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
            if (isOutOfStock) {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root),
                        criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
            } else {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root),
                        criteriaBuilder.greaterThan(stock, allocatedStock));
            }
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
        }
        if (isStockAlert != null) {
            Subquery<Sku> subquery = criteriaQuery.subquery(Sku.class);
            Root<Sku> subqueryRoot = subquery.from(Sku.class);
            subquery.select(subqueryRoot);
            Path<Integer> stock = subqueryRoot.get("stock");
            Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
            Setting setting = SystemUtils.getSetting();
            if (isStockAlert) {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root), criteriaBuilder
                        .lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
            } else {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root), criteriaBuilder
                        .greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
            }
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
        }
        if (hasPromotion != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(root.join("promotions")));
        }
        criteriaQuery.where(restrictions);
        if (orderType != null) {
            switch (orderType) {
                case TOP_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case PRICE_ASC:
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case PRICE_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case SALES_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case SCORE_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case DATE_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
                    break;
            }
        } else if (pageable == null
                || ((StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection() == null)
                && (CollectionUtils.isEmpty(pageable.getOrders())))) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")),
                    criteriaBuilder.desc(root.get("createdDate")));
        }
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public Long count(Product.Type type, Store store, Boolean isMarketable, Boolean isList, Boolean isTop,
                      Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (isMarketable != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
        }
        if (isList != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
        }
        if (isTop != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
        }
        if (isActive != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isActive"), isActive));
        }
        if (isOutOfStock != null) {
            Subquery<Sku> subquery = criteriaQuery.subquery(Sku.class);
            Root<Sku> subqueryRoot = subquery.from(Sku.class);
            subquery.select(subqueryRoot);
            Path<Integer> stock = subqueryRoot.get("stock");
            Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
            if (isOutOfStock) {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root),
                        criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
            } else {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root),
                        criteriaBuilder.greaterThan(stock, allocatedStock));
            }
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
        }
        if (isStockAlert != null) {
            Subquery<Sku> subquery = criteriaQuery.subquery(Sku.class);
            Root<Sku> subqueryRoot = subquery.from(Sku.class);
            subquery.select(subqueryRoot);
            Path<Integer> stock = subqueryRoot.get("stock");
            Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
            Setting setting = SystemUtils.getSetting();
            if (isStockAlert) {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root), criteriaBuilder
                        .lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
            } else {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("product"), root), criteriaBuilder
                        .greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
            }
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
        }
        criteriaQuery.where(restrictions);
        return super.count(criteriaQuery, null);
    }

    @Override
    public void clearAttributeValue(Attribute attribute) {
        if (attribute == null || attribute.getPropertyIndex() == null || attribute.getProductCategory() == null) {
            return;
        }

        String jpql = "update Product product set product." + Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
                + attribute.getPropertyIndex() + " = null where product.productCategory = :productCategory";
        entityManager.createQuery(jpql).setParameter("productCategory", attribute.getProductCategory()).executeUpdate();
    }

    @Override
    public void refreshExpiredStoreProductActive() {
        String jpql = "update Product product set product.isActive = false where product.isActive = true and product.store in (select store from Store store where store.endDate is null or store.endDate <= :now)";
        entityManager.createQuery(jpql).setParameter("now", new Date()).executeUpdate();
    }

    @Override
    public void refreshActive(Store store) {
        Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

        setActive(store);
        setInactive(store);
    }

    @Override
    public void shelves(Long[] ids) {
        Assert.notEmpty(ids, "[Assertion failed] - ids must not be empty: it must contain at least 1 element");

        String jpql = "update Product product set product.isMarketable = true where product.isMarketable = false and product.id in (:ids)";
        entityManager.createQuery(jpql).setParameter("ids", Arrays.asList(ids)).executeUpdate();
    }

    @Override
    public void shelf(Long[] ids) {
        Assert.notEmpty(ids, "[Assertion failed] - ids must not be empty: it must contain at least 1 element");

        String jpql = "update Product product set product.isMarketable = false where product.isMarketable = true and product.id in (:ids)";
        entityManager.createQuery(jpql).setParameter("ids", Arrays.asList(ids)).executeUpdate();
    }

    /**
     * 设置商品有效状态
     *
     * @param store 店铺
     */
    private void setActive(Store store) {
        Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

        String jpql = "update Product product set product.isActive = true where product.isActive = false and product.store = :store and product.store in (select store from Store store where store.isEnabled = true and store.status = :status and store.endDate is not null and store.endDate > :now) and product.productCategory in(select productCategory from ProductCategory productCategory join productCategory.stores productCategoryStore where productCategoryStore = product.store)";
        entityManager.createQuery(jpql).setParameter("store", store).setParameter("status", Store.Status.SUCCESS)
                .setParameter("now", new Date()).executeUpdate();
    }

    /**
     * 设置商品无效状态
     *
     * @param store 店铺
     */
    private void setInactive(Store store) {
        Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

        String jpql = "update Product product set product.isActive = false where product.isActive = true and product.store = :store and product.store in (select store from Store store where store.isEnabled != true or store.status != :status or store.endDate is null or store.endDate <= :now) or product.productCategory not in(select productCategory from ProductCategory productCategory join productCategory.stores productCategoryStore where productCategoryStore = product.store)";
        entityManager.createQuery(jpql).setParameter("store", store).setParameter("status", Store.Status.SUCCESS)
                .setParameter("now", new Date()).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> findPageXc(GoodsPageParams goodsPageParams, OrderType orderType) {

        if (!StringUtils.isEmpty(goodsPageParams.getKeyWord())) {
            return findPageKayWordXc(goodsPageParams, orderType);
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        
        
        restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isMarketable"), true));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), true));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isActive"), true));
        
        
        criteriaQuery.where(restrictions);
        if (orderType != null) {
            switch (orderType) {
                case RECOMMEND_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case SALES_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case DATE_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
                    break;

                case PRICE_ASC:
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
                case PRICE_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")),
                            criteriaBuilder.desc(root.get("createdDate")));
                    break;
            }
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")),
                    criteriaBuilder.desc(root.get("createdDate")));
        }
        return super.findList(criteriaQuery, goodsPageParams.getPageNumber(), goodsPageParams.getPageSize(), null, null);

    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Product> findPageKayWordXc(GoodsPageParams goodsPageParams, OrderType orderType) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(Product.class).get();

        
        org.apache.lucene.search.Query snPhraseQuery = queryBuilder.phrase().onField("sn").sentence(goodsPageParams.getKeyWord()).createQuery();
        BooleanJunction<?> subJunction = queryBuilder.bool().should(snPhraseQuery);

        org.apache.lucene.search.Query namePhraseQuery = queryBuilder.phrase().withSlop(3).onField("name").sentence(goodsPageParams.getKeyWord()).createQuery();
        org.apache.lucene.search.Query keywordFuzzyQuery = queryBuilder.keyword().fuzzy().onField("keyword").matching(goodsPageParams.getKeyWord()).createQuery();

        
        org.apache.lucene.search.Query isMarketablePhraseQuery = queryBuilder.phrase().onField("isMarketable").sentence("true").createQuery();
        org.apache.lucene.search.Query isListPhraseQuery = queryBuilder.phrase().onField("isList").sentence("true").createQuery();
        org.apache.lucene.search.Query isActivePhraseQuery = queryBuilder.phrase().onField("isActive").sentence("true").createQuery();
        
        BooleanJunction<?> junction = queryBuilder.bool().must(isMarketablePhraseQuery).must(isListPhraseQuery).must(isActivePhraseQuery);
        subJunction.should(namePhraseQuery).should(keywordFuzzyQuery);
        junction.must(subJunction.createQuery());
        
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(junction.createQuery(), Product.class);
        
        SortField[] sortFields = null;
        if (orderType != null) {
            switch (orderType) {
                case RECOMMEND_DESC:
                    sortFields = new SortField[]{new SortField("recommends", SortField.Type.INT, true)};
                    break;
                case SALES_DESC:
                    sortFields = new SortField[]{new SortField("sales", SortField.Type.LONG, true)};
                    break;
                case DATE_DESC:
                    sortFields = new SortField[]{new SortField("createdDate", SortField.Type.LONG, true)};
                    break;
                case PRICE_ASC:
                    sortFields = new SortField[]{new SortField("price", SortField.Type.DOUBLE, false)};
                    break;
                case PRICE_DESC:
                    sortFields = new SortField[]{new SortField("price", SortField.Type.DOUBLE, true)};
                    break;
            }
        } else {
            sortFields = new SortField[]{new SortField("recommends", SortField.Type.INT, true)};
        }

        fullTextQuery.setSort(new Sort(sortFields));
        fullTextQuery.setFirstResult(goodsPageParams.getPageNumber());
        fullTextQuery.setMaxResults(goodsPageParams.getPageSize());
        return fullTextQuery.getResultList();
    }

    @Override
    public Product findFetchStore(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        root.fetch("store");
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Map<String, Object>> findIdByCategoryId(ProductCategory productCategory) {

        String jpql = " SELECT id FROM productcategory WHERE treePath LIKE :categoryId";
        List<BigInteger> resultList  = entityManager.createNativeQuery(jpql)
                .setParameter("categoryId", "%" + productCategory.getId() + "%").getResultList();
        if(resultList!=null) {
            resultList.add(new BigInteger(productCategory.getId().toString()));
        }else {
        	resultList = new ArrayList<BigInteger>();
        	resultList.add(new BigInteger(productCategory.getId().toString()));
        }
        LOGGER.info("resultList size()" + resultList.size());	
        if (resultList != null && resultList.size() > 0) {
            String jpq2 = " SELECT p.id,p.name FROM product AS p WHERE  "
            		+ " p.isActive =1 and p.isList=1 and p.isMarketable =1 and  "
            		+ " p.productCategory_id IN (:categoryIds)";
            List rows = entityManager.createNativeQuery(jpq2).setParameter("categoryIds", resultList).getResultList();

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (Object row : rows) {
                Object[] cells = (Object[]) row;
                LOGGER.info("id = " + cells[0] + "," + "name = " + cells[1]);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", cells[0]);
                map.put("name", cells[1]);
                list.add(map);

            }
            return list;
        }
        return null;
    }

    @Override
    public net.shopxx.merge.page.Page<ProductVO> listByStoreId(List<Long> storeIds, ProductQueryParam productQueryParam) {
        String selectField = "SELECT p.id, p.name, p.createdDate, p.hits, p.sales, p.productImages, p.sn,p.price, " +
                "skuSum.productStock AS inventory, p.uv ";
        String countField = "select count(p.id) ";
        StringBuilder sqlBuilder = new StringBuilder("                FROM Product p " +
                "                 LEFT JOIN " +
                "                   (SELECT sum(sku.stock) AS productStock, sku.product_id" +
                "                FROM Sku GROUP BY sku.product_id ) AS skuSum ON p.id = skuSum.product_id" +
                "                   LEFT JOIN Store store ON p.`store_id` = store.`id` " +
                "                 WHERE 1=1 ");
        if (!storeIds.isEmpty()) {
            sqlBuilder.append(" and store.id in (:storeIds)");
        }
        if (StringUtils.isNotBlank(productQueryParam.getKeyword())) {
            sqlBuilder.append(" and (p.name like :keyword OR p.sn like :keyword)");
        }
        sqlBuilder.append(" and p.price between :minPrice AND :maxPrice");
        sqlBuilder.append(" and p.sales between :minSales AND :maxSales");
        Query countQuery = entityManager.createNativeQuery(countField + sqlBuilder.toString());
        if (!storeIds.isEmpty()) {
            countQuery.setParameter("storeIds", storeIds);
        }
        if (!storeIds.isEmpty()) {
            countQuery.setParameter("storeIds", storeIds);
        }
        if (StringUtils.isNotBlank(productQueryParam.getKeyword())) {
            countQuery.setParameter("keyword", productQueryParam.getKeyword() + "%");
        }
        countQuery.setParameter("minPrice", productQueryParam.getMinPrice()).setParameter("maxPrice", productQueryParam.getMaxPrice())
                .setParameter("minSales", productQueryParam.getMinSales()).setParameter("maxSales", productQueryParam.getMaxSales());
        BigInteger total = (BigInteger) countQuery.getSingleResult();
        OrderType orderType = productQueryParam.getOrderType();
        if (orderType != null) {
            switch (orderType) {
                case DATE_ASC:
                    sqlBuilder.append(" order by p.createdDate asc");
                    break;
                case DATE_DESC:
                    sqlBuilder.append(" order by p.createdDate desc");
                    break;
                case PRICE_ASC:
                    sqlBuilder.append(" order by p.price asc");
                    break;
                case PRICE_DESC:
                    sqlBuilder.append(" order by p.price desc");
                    break;
                case SALES_ASC:
                    sqlBuilder.append(" order by p.sales asc");
                    break;
                case SALES_DESC:
                    sqlBuilder.append(" order by p.sales desc");
                    break;
                case INVENTORY_ASC:
                    sqlBuilder.append(" order by inventory asc");
                    break;
                case INVENTORY_DESC:
                    sqlBuilder.append(" order by inventory desc");
                    break;
                default:
                    sqlBuilder.append(" order by p.createdDate desc");
                    break;
            }
        }
        String sql = sqlBuilder.toString();
        Query selectQuery = entityManager.createNativeQuery(selectField + sql);
        if (!storeIds.isEmpty()) {
            selectQuery.setParameter("storeIds", storeIds);
        }
        if (StringUtils.isNotBlank(productQueryParam.getKeyword())) {
            selectQuery.setParameter("keyword", productQueryParam.getKeyword() + "%");
        }
        selectQuery.setParameter("minPrice", productQueryParam.getMinPrice()).setParameter("maxPrice", productQueryParam.getMaxPrice())
                .setParameter("minSales", productQueryParam.getMinSales()).setParameter("maxSales", productQueryParam.getMaxSales());
        Integer pageNumber = productQueryParam.getPageNumber();
        selectQuery.setFirstResult((pageNumber - 1) * productQueryParam.getPageSize());
        selectQuery.setMaxResults(productQueryParam.getPageSize());
        List<ProductVO> productVOList = ((List<Object[]>) selectQuery.getResultList()).stream().map(this::getProductVO).collect(Collectors.toList());
        net.shopxx.merge.page.Pageable pageable = new net.shopxx.merge.page.Pageable(pageNumber, productQueryParam.getPageSize());

        return new net.shopxx.merge.page.Page<ProductVO>(productVOList, total.intValue(), pageable);
    }

    private ProductVO getProductVO(Object[] objects) {
        ProductVO productVO = new ProductVO();
        productVO.setId(((BigInteger) objects[0]).longValue());
        productVO.setName(objects[1] != null ? (String) objects[1] : "");
        productVO.setCreateddate((Date) objects[2]);
        productVO.setHits(((BigInteger) objects[3]).longValue());
        productVO.setSales(((BigInteger) objects[4]).longValue());

        productVO.setProductImages(objects[5] != null ? JSONObject.parseArray((String) objects[5], ProductImageVO.class) : null);
        productVO.setSn((String) objects[6]);
        productVO.setPrice(objects[7] != null ? (BigDecimal) objects[7] : BigDecimal.ZERO);
        productVO.setInventory(objects[8] != null ? ((BigDecimal) objects[8]).intValue() : 0);
        productVO.setUv(objects[9] != null ? ((BigInteger) objects[9]).longValue() : 0);
        return productVO;
    }
    
    
    
    @Override
    public ShareInfoVo findIdByShareInfo(Long productId) {

        String jpq = " SELECT p.id,p.name,p.productImages,p.introduction  FROM product AS p "
        		+ " WHERE p.id =:categoryIds";
        
        Object singleResult = entityManager.createNativeQuery(jpq).setParameter("categoryIds", productId).getSingleResult();
        if(singleResult!=null) {
        	Object[] cells = (Object[]) singleResult;
        	LOGGER.info(cells.toString());
        	ShareInfoVo sinfo = new ShareInfoVo();
        	sinfo.setShareId(cells[0].toString());
        	sinfo.setName(cells[1]!=null ? cells[1].toString() : null);
        	String img = getShareImg(cells[2]!=null ? cells[2].toString() : null);
        	sinfo.setHeadImg(img);
        	sinfo.setDescription(cells[3]!=null ? cells[3].toString() : null);
        	return sinfo;
        }
        return null;
    }
    
    public String getShareImg(String imgs) {
    	if(imgs!=null){
    		try {
        		JSONArray jsonArray = (JSONArray) JSONObject.parse(imgs);
        		String img = null;
        		for (Object object : jsonArray) {
        			JSONObject jsonObj =  (JSONObject) object;
        			if(jsonObj!=null && jsonObj.get("thumbnail")!=null) {
        				img = jsonObj.get("thumbnail").toString();
        				break;
        			}
    			}
        		return img;
    		} catch (Exception e) {
    			e.printStackTrace();
    			return null;
    		}
    	}else {
    		return null;
    	}
    }
}