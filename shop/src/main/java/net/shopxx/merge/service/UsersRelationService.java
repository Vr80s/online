package net.shopxx.merge.service;

import net.shopxx.entity.Member;
import net.shopxx.merge.entity.UsersRelation;
import net.shopxx.service.BaseService;

public interface UsersRelationService extends BaseService<UsersRelation, Long> {


    UsersRelation findByIpandatcmUserId(String ipandatcmUserId);

    /**
     * <p>Title: findByUserId</p>  
     * <p>Description: 通过shop服务用户id</p>  
     * @param userId
     * @return
     */
    UsersRelation findByUserId(Long userId);

    Member getMemberByIpandatcmUserId(String ipandatcmUserId);

    UsersRelation saveUserRelation(String ipandatcmUserId);
}