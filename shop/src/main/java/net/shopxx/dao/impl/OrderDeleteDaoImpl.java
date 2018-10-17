/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: NKTq9GxRVdN5o2+dHeZjafoWH6EWkjKQ
 */
package net.shopxx.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.OrderDeleteDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order.Status;
import net.shopxx.entity.Order.Type;
import net.shopxx.entity.OrderDelete;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Product;
import net.shopxx.entity.Sku;
import net.shopxx.entity.Store;
import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.vo.OrderPageParams;

/**
 * Dao - 订单
 * 
 * @author SHOP++ Team
 * @version 6.1
 */
@Repository
public class OrderDeleteDaoImpl extends BaseDaoImpl<OrderDelete, Long> implements OrderDeleteDao {

	
	@Override
	public Page<OrderDelete> findPageXc(OrderPageParams orderPageParams, 
			Type type,Status status,
			List<Store> stores, Member member,
			Product product,
			Pageable pageable, 
			OrderType orderType) {
		
	
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<OrderDelete> criteriaQuery = criteriaBuilder.createQuery(OrderDelete.class);
		Root<OrderDelete> root = criteriaQuery.from(OrderDelete.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (stores != null && stores.size()>0) {
			
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("store")).value(stores));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		
		if (orderPageParams.getStartDate() != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), orderPageParams.getStartDate()));
		}
		if (orderPageParams.getEndDate() != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), orderPageParams.getEndDate()));
		}
		
		if (orderPageParams.getProductName() != null) {
			Subquery<OrderItem> subquery = criteriaQuery.subquery(OrderItem.class);
			Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.like(subqueryRoot.<String>get("name"),
					"%" + orderPageParams.getProductName() + "%"));
			
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.or(
					criteriaBuilder.in(root.join("orderItems")).value(subquery)));
		}
		
		if (orderPageParams.getSn() != null) {
			 restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(
		    		 criteriaBuilder.like(root.<String>get("sn"), "%" + orderPageParams.getSn() + "%")));
		}
		
		if (product != null) {
			Subquery<Sku> skuSubquery = criteriaQuery.subquery(Sku.class);
			Root<Sku> skuSubqueryRoot = skuSubquery.from(Sku.class);
			skuSubquery.select(skuSubqueryRoot);
			skuSubquery.where(criteriaBuilder.equal(skuSubqueryRoot.get("product"), product));

			Subquery<OrderItem> orderItemSubquery = criteriaQuery.subquery(OrderItem.class);
			Root<OrderItem> orderItemSubqueryRoot = orderItemSubquery.from(OrderItem.class);
			orderItemSubquery.select(orderItemSubqueryRoot);
			orderItemSubquery.where(criteriaBuilder.equal(orderItemSubqueryRoot.get("order"), root), criteriaBuilder.in(orderItemSubqueryRoot.get("sku")).value(skuSubquery));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(orderItemSubquery));
		 }
		 criteriaQuery.where(restrictions);
		
		 if (orderType != null) {
            switch (orderType) {
                case DATE_ASC:
                	criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createdDate")));
                    break;
                case DATE_DESC:
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
                    break;
			default:
				break;
            }
	     }else{
	    	  criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
	     }
		 return super.findPage(criteriaQuery, pageable);
	}


}