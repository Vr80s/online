package com.xczhihui;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xczhihui.user.mapper.ItcastUserMapper;
import com.xczhihui.user.mapper.OeUserMapper;
import com.xczhihui.user.model.ItcastUser;
import com.xczhihui.user.model.OeUser;
import com.xczhihui.user.service.IOeUserService;
import com.xczhihui.user.service.impl.ItcastUserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

import java.util.List;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/3/19 0019-下午 8:11<br>
 */
public class UserServiceImplTest extends BaseJunit4Test {

    @Autowired
    private ItcastUserMapper itcastUserMapper;
    @Autowired
    private IOeUserService oeUserService;

    @Test
    public void updateCourseApplyResource() throws Exception {
        List<ItcastUser> itcastUsers = itcastUserMapper.selectAll();
        itcastUsers.forEach(itcastUser -> {
            System.out.println(itcastUser.getLoginName());
            oeUserService.updateUserInit(itcastUser);
        });
    }

}