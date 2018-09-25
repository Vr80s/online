package net.shopxx.merge.service.impl;

import net.shopxx.entity.Member;
import net.shopxx.entity.User;
import net.shopxx.merge.dao.UsersRelationDao;
import net.shopxx.merge.entity.UsersRelation;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.service.UserService;
import net.shopxx.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
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


	@Override
	public UsersRelation findByIpandatcmUserId(String ipandatcmUserId) {
		return usersRelationDao.findByIpandatcmUserId(ipandatcmUserId);
	}

	@Override
	@Transactional(readOnly = true)
	public Member getMemberByIpandatcmUserId(String ipandatcmUserId){
		UsersRelation usersRelation = this.findByIpandatcmUserId(ipandatcmUserId);
		User user = userService.find(usersRelation.getUserId());
		return (Member) user;
	}

	@Override
	public UsersRelation findByUserId(Long userId) {
		return usersRelationDao.findByUserId(userId);
	}
}