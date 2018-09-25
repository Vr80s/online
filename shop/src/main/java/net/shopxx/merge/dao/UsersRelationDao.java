package net.shopxx.merge.dao;

import net.shopxx.dao.BaseDao;
import net.shopxx.entity.Seo;
import net.shopxx.merge.entity.UsersRelation;

/**
 * 熊猫中医与shop用户关系
 */
public interface UsersRelationDao extends BaseDao<UsersRelation, Long> {


    UsersRelation findByIpandatcmUserId(String ipandatcmUserId);

	/**  
	 * <p>Title: findByUserId</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @return  
	 */ 
	UsersRelation findByUserId(Long userId);
}