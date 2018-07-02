package com.xczh.consumer.market.controller.threeparties;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.ThirdPartyType;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.common.util.enums.UserUnitedStateType;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.Token;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
@RequestMapping(value = "/xczh/wxlogin")
public class WeChatThirdPartyController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(WeChatThirdPartyController.class);

    @Autowired
    private OnlineUserService onlineUserService;

    @Autowired
    private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;

    @Autowired
    private UserCenterService userCenterService;

    @Autowired
    private WxMpService wxMpService;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    @Value("${webdomain}")
    private String webdomain;

    /**
     * 公众号和手机号在h5中的关系 Description： 1、h5
     * 微信第三方登录（如果没有绑定手机号，在进行非游客操作时需要进行晚上信息，如果绑定了手机号就直接登录）--》也就是微信号绑定手机账户
     * 2、h5绑定微信号 -->也就是通过手机号去绑定微信号
     *
     * @param req
     * @param res
     * @param params
     * @return void
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("publicWechatAndMobile")
    public void publicWechatAndMobile(@RequestParam(required = false) String userId,
                                      HttpServletResponse res) throws Exception {

        String redirectUri = "/xczh/wxlogin/publicWechatAndMobileCallback";
        if (StringUtils.isNotBlank(userId)) {
            OnlineUser ou = onlineUserService.findUserById(userId);
            if (ou == null) {
                res.sendRedirect(returnOpenidUri + "/xcview/html/enter.html");
                return;
            }
            redirectUri += "?userId=" + userId;
        }
        String strLinkHome = wxMpService.oauth2buildAuthorizationUrl(returnOpenidUri + redirectUri, "snsapi_userinfo", "STATE");
        res.sendRedirect(strLinkHome);
    }

    /**
     * 公众号和手机号在h5中的关系 --》微信回调
     * <p>
     * Description： 1、h5
     * 微信第三方登录（如果没有绑定手机号，在进行非游客操作时需要进行晚上信息，如果绑定了手机号就直接登录）--》也就是微信号绑定手机账户
     * 2、h5绑定微信号 -->也就是通过手机号去绑定微信号
     *
     * @param req
     * @param res
     * @param params
     * @return void
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("publicWechatAndMobileCallback")
    public RedirectView publicWechatAndMobileCallback(HttpServletRequest req,
                                                      HttpServletResponse res, RedirectAttributes attrs)
            throws Exception {
        try {
            String userId = req.getParameter("userId");
            String code = req.getParameter("code");
            WxcpClientUserWxMapping wxw = onlineUserService.saveWxInfo(code);
            if (wxw == null) {
                throw new IllegalStateException("微信授权失败");
            }
            String openId = wxw.getOpenid();
            UCCookieUtil.writeThirdPartyCookie(res, onlineUserService.buildThirdFlag(wxw));
            if (StringUtils.isNotBlank(wxw.getClient_id())) {
                if (StringUtils.isNotBlank(userId)) { // type=1 说明这个已经绑定了
                    attrs.addAttribute("type", 1);
                    return new RedirectView(returnOpenidUri + "/xcview/html/lickacc_mobile.html");
                } else { // 直接登录
                    OnlineUser ou = onlineUserService.findUserById(wxw.getClient_id());
                    Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
                    // 把用户中心的数据给他 这里im都要用到
                    ou.setUserCenterId(ou.getId());
                    ou.setTicket(t.getTicket());
                    UCCookieUtil.writeTokenCookie(res, t);
                    return new RedirectView(returnOpenidUri + "/xcview/html/home_page.html");
                }
            } else {
                // 手机号绑定微信号
                if (StringUtils.isNotBlank(userId)) {
                    wxw.setClient_id(userId);
                    wxcpClientUserWxMappingService.update(wxw);
                    attrs.addAttribute("type", 2);
                    return new RedirectView(returnOpenidUri + "/xcview/html/lickacc_mobile.html");
                } else {
                    attrs.addAttribute("openId", openId).addAttribute("unionId", wxw.getUnionid())
                            .addAttribute("jump_type", 1);
                    return new RedirectView(returnOpenidUri + "/xcview/html/home_page.html");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView(returnOpenidUri + "/xcview/html/enter.html");
        }
    }

    /**
     * Description：app端第三方登录 ---绑定手机号使用
     *
     * @param req
     * @param res
     * @param accessToken
     * @param openId
     * @param model
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "appThirdPartyLogin")
    @ResponseBody
    @Transactional
    public ResponseObject addAppThirdparty(HttpServletRequest req,
                                           HttpServletResponse res,
                                           @RequestParam("accessToken") String accessToken,
                                           @RequestParam("openId") String openId,
                                           @RequestParam("model") String model) throws Exception {
        String userId = req.getParameter("userId");
        if (StringUtils.isNotBlank(userId)) {
            OnlineUser ou = onlineUserService.findUserById(userId);
            if (ou == null) {
                return ResponseObject.newErrorResponseObject("获取用户信息有误");
            }
            /**
             * 判断这个用户是否已经判断了其他微信号了
             */
            WxcpClientUserWxMapping wcwm = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByUserId(userId);
            if (wcwm != null) {
                return ResponseObject.newErrorResponseObject("用户已绑定过微信号");
            }
        }


        Map<String, String> mapRequest = new HashMap<String, String>();
        mapRequest.put("type", ThirdPartyType.WECHAT.getCode() + "");
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
            wxMpOAuth2AccessToken.setOpenId(openId);
            wxMpOAuth2AccessToken.setAccessToken(accessToken);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");

            String unionid = wxMpUser.getUnionId();
            WxcpClientUserWxMapping m = wxcpClientUserWxMappingService
                    .getWxcpClientUserByUnionId(unionid);
            if (null == m) {

                WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping(wxMpUser);

                wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);

                if (StringUtils.isNotBlank(userId)) { // 绑定成功
                    wxcpClientUserWxMapping.setClient_id(userId);
                    mapRequest.put("code",
                            UserUnitedStateType.MOBILE_BINDING.getCode() + "");
                } else {
                    mapRequest.put("code",
                            UserUnitedStateType.UNBOUNDED.getCode() + "");
                }
                mapRequest.put("unionId", unionid);

                return ResponseObject.newSuccessResponseObject(mapRequest,
                        Integer.parseInt(mapRequest.get("code")));
            } else if (StringUtils.isNotBlank(m.getClient_id())) { // 绑定了用户信息

                if (StringUtils.isNotBlank(userId)) { // 这里说明人家这个已经绑定过其他信息了。我的天
                    mapRequest.put("code", UserUnitedStateType.MOBILE_UNBOUNDED.getCode() + "");
                    return ResponseObject.newSuccessResponseObject(mapRequest, UserUnitedStateType.MOBILE_UNBOUNDED.getCode());
                }

                OnlineUser ou = onlineUserService
                        .findUserById(m.getClient_id());
                Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
                // 把用户中心的数据给他 --这些数据是IM的
                ou.setUserCenterId(ou.getId());
                ou.setTicket(t.getTicket());
                return ResponseObject.newSuccessResponseObject(ou,
                        UserUnitedStateType.BINDING.getCode());

            } else if (!StringUtils.isNotBlank(m.getClient_id())) {

                if (StringUtils.isNotBlank(userId)) { // 绑定成功
                    mapRequest.put("code",
                            UserUnitedStateType.MOBILE_BINDING.getCode() + "");
                    m.setClient_id(userId);
                    wxcpClientUserWxMappingService.update(m);
                } else {
                    mapRequest.put("code",
                            UserUnitedStateType.UNBOUNDED.getCode() + "");
                }
                mapRequest.put("unionId", unionid);
                return ResponseObject.newSuccessResponseObject(mapRequest,
                        Integer.parseInt(mapRequest.get("code")));
            }
            return ResponseObject.newSuccessResponseObject("");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 当用户在微信中打开，并且h5没有还未获微信信息时，用于中转将微信信息写到h5
     *
     * @param url
     * @throws Exception
     */
    @RequestMapping("middle")
    public void getCurrentWechatOpenId(@RequestParam String url, HttpServletResponse response) throws Exception {
        String strLinkHome = wxMpService.oauth2buildAuthorizationUrl(returnOpenidUri + "/xczh/wxlogin/middle/callback?url=" + url,
                "snsapi_userinfo", "STATE");
        LOGGER.warn("strLinkHome:" + strLinkHome);
        response.sendRedirect(strLinkHome);
    }

    /**
     * 微信授权成功后返回原先的地址
     *
     * @param req
     * @param res
     * @param code
     * @param url
     * @throws Exception
     */
    @RequestMapping("middle/callback")
    public void getCurrentWechatOpenIdCallback(HttpServletResponse res, @RequestParam String code, @RequestParam String url)
            throws Exception {
        LOGGER.warn("middle/callback:code={}", code);
        WxcpClientUserWxMapping wxw = onlineUserService.saveWxInfo(code);
        if (StringUtils.isNotBlank(wxw.getClient_id())) {
            OnlineUser ou = onlineUserService.findUserById(wxw.getClient_id());
            if (ou != null) {
                Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
                // 把用户中心的数据给他 这里im都要用到
                ou.setUserCenterId(ou.getId());
                ou.setTicket(t.getTicket());
                UCCookieUtil.writeTokenCookie(res, t);
            } else { //之前的bug引起的,需要用户在完善一次信息
                wxw.setClient_id(null);
                wxcpClientUserWxMappingService.update(wxw);
            }
        }
        UCCookieUtil.writeThirdPartyCookie(res, onlineUserService.buildThirdFlag(wxw));
        res.sendRedirect(url);
    }
}
