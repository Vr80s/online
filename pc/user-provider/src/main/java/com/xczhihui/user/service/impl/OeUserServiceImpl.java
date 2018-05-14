package com.xczhihui.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.user.mapper.OeUserMapper;
import com.xczhihui.user.model.ItcastUser;
import com.xczhihui.user.model.OeUser;
import com.xczhihui.user.service.IOeUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-05-14
 */
@Service
public class OeUserServiceImpl extends ServiceImpl<OeUserMapper, OeUser> implements IOeUserService {

    @Override
    public void updateUserInit(ItcastUser itcastUser) {
        OeUser ou = this.baseMapper.selectByLoginName(itcastUser.getLoginName());
        if(ou==null){
            System.err.println(itcastUser.getLoginName());
        }else{
            ou.setPassword(itcastUser.getPassword());
            ou.setSalt(itcastUser.getSalt());
            this.baseMapper.updateById(ou);
        }
    }
}
