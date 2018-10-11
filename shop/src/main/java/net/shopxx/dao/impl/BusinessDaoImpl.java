/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: dNNDgP3dH/afb6CWsck1rQs4XpDlTH8C
 */
package net.shopxx.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.shopxx.dao.BusinessDao;
import net.shopxx.entity.Business;

/**
 * Dao - 商家
 *
 * @author SHOP++ Team
 * @version 6.1
 */
@Repository
public class BusinessDaoImpl extends BaseDaoImpl<Business, Long> implements BusinessDao {

    @Override
    public List<Business> search(String keyword, Integer count) {
        if (StringUtils.isEmpty(keyword)) {
            return Collections.emptyList();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Business> criteriaQuery = criteriaBuilder.createQuery(Business.class);
        Root<Business> root = criteriaQuery.from(Business.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("username"), "%" + keyword + "%")));
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, count, null, null);
    }

    @Override
    public BigDecimal totalBalance() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Business> root = criteriaQuery.from(Business.class);
        criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("balance")));
        BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal frozenTotalAmount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Business> root = criteriaQuery.from(Business.class);
        criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("frozenAmount")));
        BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public List<String> findUsernameByDoctorId(String doctorId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Business> query = criteriaBuilder.createQuery(Business.class);
        Root<Business> root = query.from(Business.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get("doctorId"), doctorId));
        List<Business> businessList = entityManager.createQuery(query).getResultList();
        if (businessList != null && !businessList.isEmpty()) {
            return businessList.stream().map(Business::getUsername).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Long count(Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Business> criteriaQuery = criteriaBuilder.createQuery(Business.class);
        Root<Business> root = criteriaQuery.from(Business.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.count(criteriaQuery, null);
    }
    
    @Override
    public List<Business> findBusinessByDoctorId(String doctorId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Business> query = criteriaBuilder.createQuery(Business.class);
        Root<Business> root = query.from(Business.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get("doctorId"), doctorId));
        List<Business> businessList = entityManager.createQuery(query).getResultList();
        return businessList;
    }

}