/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: kK0AVImV0a0/gvgLFUrZcj7Ww4i6VjXL
 */
package net.shopxx.merge.dao.impl;

import net.shopxx.dao.impl.BaseDaoImpl;
import net.shopxx.entity.Seo;
import net.shopxx.merge.dao.UsersRelationDao;
import net.shopxx.merge.entity.UsersRelation;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

/**
 * Dao - SEO设置
 * 
 * @author ixincheng
 * @version 6.1
 */
@Repository
public class UsersRelationDaoImpl extends BaseDaoImpl<UsersRelation, Long> implements UsersRelationDao {


    @Override
    public UsersRelation findByIpandatcmUserId(String ipandatcmUserId) {
        try {
            String jpql = "select usersRelation from UsersRelation usersRelation where usersRelation.ipandatcmUserId = :ipandatcmUserId";
            return entityManager.createQuery(jpql, UsersRelation.class).setParameter("ipandatcmUserId", ipandatcmUserId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

	@Override
	public UsersRelation findByUserId(Long userId) {
		try {
            String jpql = "select usersRelation from UsersRelation usersRelation where usersRelation.userId = :userId";
            return entityManager.createQuery(jpql, UsersRelation.class).setParameter("userId", userId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
}