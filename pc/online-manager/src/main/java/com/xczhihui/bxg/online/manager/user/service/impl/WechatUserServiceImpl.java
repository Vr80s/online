package com.xczhihui.bxg.online.manager.user.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.WechatUser;
import com.xczhihui.bxg.online.manager.user.dao.WechatUserDao;
import com.xczhihui.bxg.online.manager.user.service.WechatUserService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;

@Service("WechatUserService")
public class WechatUserServiceImpl implements WechatUserService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatUserDao dao;
    @Autowired
    private UserCenterAPI api;

    @Override
    public Page<WechatUser> findUserPage(String nickname, String subscribeTime, String qr_scene, int pageNumber,
                                         int pageSize) {
        return dao.findUserPage(nickname, subscribeTime, qr_scene, pageNumber, pageSize);
    }

    @Override
    public void updateUserStatus(String loginName, int status) {
        WechatUser u = dao.findOneEntitiyByProperty(WechatUser.class, "loginName", loginName);
    }

    @Override
    public void updateMenuForUser(WechatUser entity) {
        WechatUser temp = dao.findOneEntitiyByProperty(WechatUser.class, "id", entity.getWxId());
        dao.update(temp);

    }

    //查询所有的讲师	
    @Override
    public List<Map<String, Object>> getAllUserLecturer() {
        return dao.getAllUserLecturer();
    }

    @Override
    public void updateUserLecturer(String userId, int isLecturer, String description) {

    	
    }

    @Override
    public WechatUser getWechatUserByUserId(String userId) {
        WechatUser ou = dao.getWechatUserByUserId(userId);
        return ou;
    }

    @Override
    public List<Map<String, Object>> getAllCourseName() {
        return dao.getAllCourseName();
    }

    @Override
    public void updateUserLogin(String userId, String loginName) {
        
    }

}
