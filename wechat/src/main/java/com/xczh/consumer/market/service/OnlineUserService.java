package com.xczh.consumer.market.service;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.ResponseObject;

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
     * 添加用户
     *
     * @param user
     * @throws SQLException
     */
    void addOnlineUser(OnlineUser user) throws SQLException;

    /**
     * 往本地库写用户
     *
     * @param mobile
     * @param userName
     * @param shareCode 上级的分享码
     * @return
     * @throws Exception
     */
    OnlineUser addUser(String mobile, String userName, String shareCode, String password) throws Exception;

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
     * 通过用户id更改 用户所对应的微吼信息
     *
     * @param weihouId
     * @param password
     * @param userName
     * @param id
     * @throws SQLException
     */
    void updateVhallIdOnlineUser(String weihouId, String password, String userName,
                                 String id) throws SQLException;

    /**
     * Description：更新登录名（手机号）
     *
     * @param o
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    void updateUserLoginName(OnlineUser o) throws SQLException;


    void updateOnlineUserAddPwdAndUserName(OnlineUser ou) throws Exception;

    /**
     * Description：查找主播信息
     *
     * @param lecturerId
     * @return Map<String,Object>
     * @throws SQLException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    Map<String, Object> findHostById(String lecturerId) throws SQLException;

    void emptyAccount(String userName) throws SQLException;

    /**
     * Description：验证手机号是否已经注册
     *
     * @param username
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    void verifyPhone(String username) throws SQLException;
}
