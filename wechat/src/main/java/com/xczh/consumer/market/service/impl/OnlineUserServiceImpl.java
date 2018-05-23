package com.xczh.consumer.market.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.dao.WxcpClientUserWxMappingMapper;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.CookieUtil;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.WeihouInterfacesListUtil;
import com.xczhihui.common.util.enums.UserOrigin;
import com.xczhihui.online.api.service.UserCoinService;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.vo.OeUserVO;

@Service
public class OnlineUserServiceImpl implements OnlineUserService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OnlineUserServiceImpl.class);

    @Autowired
    public OnlineUserMapper onlineUserDao;
    @Autowired
    public WxcpClientUserWxMappingMapper wxcpClientUserWxMappingMapper;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private UserCoinService userCoinService;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    @Value("${webdomain}")
    private String webdomain;

    @Override
    public OnlineUser findUserById(String id) throws SQLException {
        return onlineUserDao.findUserById(id);
    }

    @Override
    public OnlineUser findUserByLoginName(String loginName) throws SQLException {
        return onlineUserDao.findUserByLoginName(loginName);
    }

    @Override
    public void addOnlineUser(OnlineUser user) throws SQLException {
        onlineUserDao.addOnlineUser(user);
    }

    @Override
    public OnlineUser addUser(String mobile, String userName,
                              String origin, String password) throws Exception {

        OnlineUser u = new OnlineUser();
        //保存本地库
        u.setId(CodeUtil.getRandomUUID());
        u.setLoginName(mobile);
        u.setMobile(mobile);
        u.setStatus(0);
        u.setCreateTime(new Date());
        u.setDelete(false);
        u.setName(mobile);   //初次登录设置默认名为：手机号
        u.setSmallHeadPhoto(webdomain + "/web/images/defaultHead/18.png");
        u.setVisitSum(0);
        u.setStayTime(0);
        u.setUserType(0);
        u.setOrigin(origin);
        u.setMenuId(-1);
        u.setPassword(password);
        u.setSex(OnlineUser.SEX_UNKNOWN);
        u.setCreateTime(new Date());
        u.setType(1);

        /*
         * 创建微吼信息
         */
        String weihouUserId = WeihouInterfacesListUtil.createUser(u.getId(),
                WeihouInterfacesListUtil.MOREN, mobile,
                u.getSmallHeadPhoto(), mobile);
        if (StringUtils.isNotBlank(weihouUserId)) {
            u.setVhallId(weihouUserId);  //微吼id
            u.setVhallName(mobile);
            u.setVhallPass(WeihouInterfacesListUtil.MOREN);    //微吼密码
        }
        onlineUserDao.addOnlineUser(u);
        return u;
    }

    /**
     * 微信端注册
     */
    @Override
    public OnlineUser addPhoneRegistByAppH5(HttpServletRequest req, String password,
                                            String mobile)
            throws Exception {
        //手机
        OeUserVO iu = userCenterService.getUserVO(mobile);
        OnlineUser user = onlineUserDao.findUserByLoginName(mobile);
        if (iu == null) {
            //向用户中心注册
            userCenterService.regist(mobile, password, mobile, UserOrigin.PC);
        }
        if (null == user) {
            String shareCode = CookieUtil.getCookieValue(req, "_usercode_");
            user = this.addUser(mobile, "", shareCode, password);
        }
        /**
         * 注册成功后初始化
         * 为用户初始化一条代币记录
         */
        userCoinService.saveUserCoin(user.getId());
        return user;
    }

    @Override
    public void updateUserCenterData(OnlineUser user, Map<String, String> map) throws SQLException {
        onlineUserDao.updateUserCenter(user, map);
    }

    @Override
    public void updateVhallIdOnlineUser(String weihouId, String password, String userName,
                                        String id) throws SQLException {
        onlineUserDao.updateVhallIdOnlineUser(weihouId, password, userName, id);
    }

    @Override
    public void updateUserLoginName(OnlineUser o) throws SQLException {
        onlineUserDao.updateUserLoginName(o);
    }


    @Override
    public void updateOnlineUserAddPwdAndUserName(OnlineUser ou) throws Exception {
        onlineUserDao.updateOnlineUserAddPwdAndUserName(ou);
    }

    @Override
    public Map<String, Object> findHostById(String lecturerId)
            throws SQLException {
        return onlineUserDao.findHostById(lecturerId);
    }

    @Override
    public void emptyAccount(String userName) throws SQLException {
        onlineUserDao.emptyAccount(userName);
    }

    @Override
    public void verifyPhone(String username) throws SQLException {
        //在用户重新获取登录对象
        OeUserVO iu = userCenterService.getUserVO(username);
        if (iu == null) {
            throw new RuntimeException("用户不存在！");
        }
        OnlineUser o = onlineUserDao.findUserByLoginName(username);
        if (o != null) {
            if (o.isDelete() || o.getStatus() == -1) {
                throw new RuntimeException("用户已禁用！");
            }
        }
    }
}
