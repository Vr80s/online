package com.xczhihui.user.center.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.user.center.mapper.ItcastUserMapper;
import com.xczhihui.user.center.mapper.OeUserMapper;
import com.xczhihui.user.center.model.ItcastUser;
import com.xczhihui.user.center.model.OeUser;
import com.xczhihui.user.center.service.IOeUserService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-05-14
 */
@Service
public class OeUserServiceImpl extends ServiceImpl<OeUserMapper, OeUser> implements IOeUserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ItcastUserMapper itcastUserMapper;

    @Override
    public void updateUsersInit() throws Exception {
        List<ItcastUser> itcastUsers = itcastUserMapper.selectAll();
        itcastUsers.forEach(itcastUser -> {
            logger.info("更新用户{}", itcastUser.getLoginName());
            updateUserInit(itcastUser);
        });
    }

    @Override
    public void updateUserInit(ItcastUser itcastUser) {
        OeUser ou = this.baseMapper.selectByLoginName(itcastUser.getLoginName());
        if (ou == null) {
            logger.error("用户{}无对应信息,", itcastUser.getLoginName());
        } else {
            ou.setPassword(itcastUser.getPassword());
            ou.setSalt(itcastUser.getSalt());
            this.baseMapper.updateById(ou);
        }
    }

    @Override
    public void addOeUser(OeUser oeUser) {
        this.baseMapper.insert(oeUser);
    }

    @Override
    public void updateOeUser(OeUser user) {
        user.setChangeTime(new Date());
        this.baseMapper.updateById(user);
    }

    @Override
    public void updateStatus(String userId, int status) {
        OeUser oeUser = new OeUser();
        oeUser.setStatus(status);
        oeUser.setId(userId);
        this.baseMapper.updateById(oeUser);
    }

    @Override
    public void updatePassword(String id, String password) {
        OeUser oeUser = new OeUser();
        oeUser.setId(id);
        oeUser.setPassword(password);
        this.baseMapper.updateById(oeUser);
    }

    @Override
    public void updateLoginName(String oldLoginName, String newLoginName) {
        this.baseMapper.updateLoginName(oldLoginName, newLoginName);
    }
}
