package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczhihui.user.center.vo.ThirdFlag;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OnlineUserService {
    /**
     * 根据登录名查找用户
     *
     * @param loginName
     * @return
     */
    OnlineUser findUserByLoginName(String loginName) throws SQLException;

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     * @throws SQLException
     */
    OnlineUser findUserById(String id) throws SQLException;

    /**
     * Description：h5、app注册功能
     *
     * @param req
     * @param password
     * @param mobile
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    OnlineUser addPhoneRegistByAppH5(HttpServletRequest req,
                                     String password, String mobile) throws Exception;

    /**
     * Description：修改用户中心数据接口
     *
     * @param user
     * @return void
     * @throws SQLException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    void updateUserCenterData(OnlineUser user, Map<String, String> map) throws SQLException;


    /**
     * Description：更新登录名（手机号）
     *
     * @param o
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    void updateUserLoginName(OnlineUser o) throws SQLException;


    /**
     * Description：验证手机号是否已经注册
     *
     * @param username
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    void verifyPhone(String username) throws SQLException;

    /**
     * 保存微信信息
     *
     * @param code
     * @return
     */
    WxcpClientUserWxMapping saveWxInfo(String code);

    /**
     * 授权登陆后调用，用于将微信相关信息,设置在cookie中便于前端获取
     *
     * @param wxw
     * @return
     */
    ThirdFlag buildThirdFlag(WxcpClientUserWxMapping wxw);

	List<OnlineUser> selectAllUser()throws SQLException;

}
