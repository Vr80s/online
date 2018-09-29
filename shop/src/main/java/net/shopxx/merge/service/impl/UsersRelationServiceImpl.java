package net.shopxx.merge.service.impl;

import net.shopxx.entity.Member;
import net.shopxx.entity.User;
import net.shopxx.merge.dao.UsersRelationDao;
import net.shopxx.merge.entity.UsersRelation;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.merge.service.UsersService;
import net.shopxx.service.UserService;
import net.shopxx.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * 熊猫中医与shop用户关系
 */
@Service
public class UsersRelationServiceImpl extends BaseServiceImpl<UsersRelation, Long> implements UsersRelationService {

	@Inject
	private UsersRelationDao usersRelationDao;
	@Inject
	private UserService userService;
	@Inject
	private UsersService usersService;


	@Override
	@Transactional
	public UsersRelation findByIpandatcmUserId(String ipandatcmUserId) {
		UsersRelation usersRelation = usersRelationDao.findByIpandatcmUserId(ipandatcmUserId);
		if(usersRelation == null){
			usersRelation = saveUserRelation(ipandatcmUserId);
		}
		return usersRelation;
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRES_NEW)
	public Member getMemberByIpandatcmUserId(String ipandatcmUserId){
		System.out.println("用户关联查询---id："+ipandatcmUserId);
		UsersRelation usersRelation = this.findByIpandatcmUserId(ipandatcmUserId);
		User user = userService.find(usersRelation.getUserId());
		return (Member) user;
	}

	@Override
	public UsersRelation findByUserId(Long userId) {
		return usersRelationDao.findByUserId(userId);
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