package net.shopxx.merge.service.impl;

import net.shopxx.entity.Member;
import net.shopxx.entity.User;
import net.shopxx.merge.dao.UsersRelationDao;
import net.shopxx.merge.entity.UsersRelation;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.merge.service.UsersService;
import net.shopxx.service.UserService;
import net.shopxx.service.impl.BaseServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.redis.key.RedisCacheKey;

import javax.inject.Inject;

/**
 * 熊猫中医与shop用户关系
 */
@Service
public class UsersRelationServiceImpl extends BaseServiceImpl<UsersRelation, Long> implements UsersRelationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsersRelationServiceImpl.class);
	 
	@Inject
	private UsersRelationDao usersRelationDao;
	@Inject
	private UserService userService;
	@Inject
	private UsersService usersService;


	@Autowired
	private CacheService redisCacheService;
	
	@Override
	@Transactional
	public UsersRelation findByIpandatcmUserId(String ipandatcmUserId) {
		
		UsersRelation usersRelation = redisCacheService.get(RedisCacheKey.SHOP_USERS_RELATION
	    		+RedisCacheKey.REDIS_SPLIT_CHAR
	    		+ipandatcmUserId);
		
		LOGGER.info("ipandatcmUserId:"+ipandatcmUserId);
		LOGGER.info("usersRelation:"+usersRelation);
		if(usersRelation == null){
		    usersRelation = usersRelationDao.findByIpandatcmUserId(ipandatcmUserId);
		    if(usersRelation == null) {
		    	usersRelation = saveUserRelation(ipandatcmUserId);
		    }
		    
		    redisCacheService.set(RedisCacheKey.SHOP_USERS_RELATION
		    		+RedisCacheKey.REDIS_SPLIT_CHAR
		    		+ipandatcmUserId, usersRelation);
		    
		    redisCacheService.set(RedisCacheKey.SHOP_USERS_RELATION
		    		+RedisCacheKey.REDIS_SPLIT_CHAR
		    		+usersRelation.getUserId(), usersRelation);
		}
		return usersRelation;
	}

	@Override
	@Transactional
//	@Transactional(propagation= Propagation.REQUIRES_NEW)
	public Member getMemberByIpandatcmUserId(String ipandatcmUserId){
		System.out.println("用户关联查询---id："+ipandatcmUserId);
		UsersRelation usersRelation = this.findByIpandatcmUserId(ipandatcmUserId);
		User user = userService.find(usersRelation.getUserId());
		return (Member) user;
	}

	@Override
	public UsersRelation findByUserId(Long userId) {
		
		UsersRelation usersRelation = redisCacheService.get(RedisCacheKey.SHOP_USERS_RELATION
	    		+RedisCacheKey.REDIS_SPLIT_CHAR
	    		+userId);
		if(usersRelation == null){
		    usersRelation = usersRelationDao.findByUserId(userId);
		    
		    redisCacheService.set(RedisCacheKey.SHOP_USERS_RELATION
		    		+RedisCacheKey.REDIS_SPLIT_CHAR
		    		+userId, usersRelation);
		}
		return usersRelation;
	}

	@Override
	@Transactional
	public synchronized UsersRelation saveUserRelation(String ipandatcmUserId) {
		UsersRelation usersRelation = usersRelationDao.findByIpandatcmUserId(ipandatcmUserId);
		if(usersRelation != null){
			return usersRelation;
		}
		usersRelation = new UsersRelation();
		usersRelation.setIpandatcmUserId(ipandatcmUserId);
		Long userId = usersService.register(ipandatcmUserId, ipandatcmUserId, ipandatcmUserId, ipandatcmUserId);
		usersRelation.setUserId(userId);
		this.save(usersRelation);
		System.out.println("创建用户："+ipandatcmUserId+"==="+usersRelation.getUserId());
		return usersRelation;
	}
}