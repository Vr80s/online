package com.xczh.consumer.market.service.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.dao.WxcpClientUserWxMappingMapper;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.SLEmojiFilter;
import com.xczhihui.common.util.enums.UserOrigin;
import com.xczhihui.online.api.service.UserCoinService;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.CookieUtil;
import com.xczhihui.user.center.vo.OeUserVO;
import com.xczhihui.user.center.vo.ThirdFlag;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

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
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;

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

    /**
     * 手机号注册
     */
    @Override
    public OnlineUser addPhoneRegistByAppH5(HttpServletRequest req, String password,
                                            String mobile)
            throws Exception {
        OnlineUser user = onlineUserDao.findUserByLoginName(mobile);
        if (user == null) {
            //向用户中心注册
            userCenterService.regist(mobile, password, mobile, UserOrigin.PC);
            String shareCode = CookieUtil.getCookieValue(req, "_usercode_");
            user = onlineUserDao.findUserByLoginName(mobile);
            if (StringUtils.isNotBlank(shareCode)) {
                onlineUserDao.updateShareCode(user.getId(), shareCode);
            }
        }
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

    @Override
    public WxcpClientUserWxMapping saveWxInfo(String code) {
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
            String nickname = SLEmojiFilter.filterEmoji(wxMpUser.getNickname());
            String openId = wxMpUser.getOpenId();
            WxcpClientUserWxMapping m = wxcpClientUserWxMappingMapper.getWxcpClientUserByUnionId(wxMpUser.getUnionId());

            if (null == m) {
                WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
                wxcpClientUserWxMapping.setWx_id(CodeUtil.getRandomUUID());
                wxcpClientUserWxMapping.setWx_public_id(wxMpConfigStorage.getAppId());
                wxcpClientUserWxMapping.setWx_public_name(WxPayConst.appid4name);
                wxcpClientUserWxMapping.setOpenid(openId);
                wxcpClientUserWxMapping.setNickname(nickname);
                wxcpClientUserWxMapping.setSex(String.valueOf(wxMpUser.getSex()));
                wxcpClientUserWxMapping.setLanguage(wxMpUser.getLanguage());
                wxcpClientUserWxMapping.setCity(wxMpUser.getCity());
                wxcpClientUserWxMapping.setProvince(wxMpUser.getProvince());
                wxcpClientUserWxMapping.setCountry(wxMpUser.getCountry());
                String headImgUrl = wxMpUser.getHeadImgUrl();
                if (!StringUtils.isNotBlank(headImgUrl)) {
                    headImgUrl = webdomain + "/web/images/defaultHead/18.png";
                }
                wxcpClientUserWxMapping.setHeadimgurl(headImgUrl);
                wxcpClientUserWxMapping.setUnionid(wxMpUser.getUnionId());
                wxcpClientUserWxMappingMapper.insert(wxcpClientUserWxMapping);
                return wxcpClientUserWxMapping;
            } else {
                return m;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ThirdFlag buildThirdFlag(WxcpClientUserWxMapping wxw) {
        ThirdFlag tf = new ThirdFlag();
        tf.setOpenId(wxw.getOpenid());
        tf.setUnionId(wxw.getUnionid());
        tf.setNickName(StringUtils.isNotBlank(wxw.getNickname()) ? wxw.getNickname() : "熊猫中医");
        String defaultHeadImg = webdomain + "/web/images/defaultHead/18.png";
        tf.setHeadImg(StringUtils.isNotBlank(wxw.getHeadimgurl()) ? wxw.getHeadimgurl() : defaultHeadImg);
        return tf;
    }
}
