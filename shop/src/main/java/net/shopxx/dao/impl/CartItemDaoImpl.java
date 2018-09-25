/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: I+eGZQ9hoMD3LCY4HqJ/jiEuY2eSYb/v
 */
package net.shopxx.dao.impl;

import net.shopxx.entity.Area;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.shopxx.dao.CartItemDao;
import net.shopxx.entity.CartItem;

import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

/**
 * Dao - 购物车项
 *
 * @author SHOP++ Team
 * @version 6.1
 */
@Repository
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, Long> implements CartItemDao {

    @Override
    public List<CartItem> getCartItemListByIds(Long[] ids){
        TypedQuery<CartItem> query;
        String jpql = "select cartItem from CartItem cartItem where cartItem.id in (:ids) ";
        query = entityManager.createQuery(jpql, CartItem.class).setParameter("ids", Arrays.asList(ids));
        return query.getResultList();
    }

    public CartItem findFetchSku(Long id) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<CartItem> query = criteriaBuilder.createQuery(CartItem.class);
        Root<CartItem> from = query.from(CartItem.class);
        from.fetch("sku");
        query.select(from);
        query.where(criteriaBuilder.equal(from.get("id"), id));
        return this.entityManager.createQuery(query).getSingleResult();
    }
}