package test.com.xczhihui;

import com.xczhihui.user.center.mapper.ItcastUserMapper;
import com.xczhihui.user.center.model.ItcastUser;
import com.xczhihui.user.center.service.IOeUserService;
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
        System.out.println(itcastUsers.size());
        System.out.println(itcastUsers.size());
        itcastUsers.forEach(itcastUser -> {
            System.out.println(itcastUser.getLoginName());
            oeUserService.updateUserInit(itcastUser);
        });
    }

}