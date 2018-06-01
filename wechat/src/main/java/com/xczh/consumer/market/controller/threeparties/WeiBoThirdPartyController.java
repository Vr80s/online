package com.xczh.consumer.market.controller.threeparties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.SLEmojiFilter;
import com.xczhihui.common.util.enums.ThirdPartyType;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.common.util.enums.UserUnitedStateType;
import com.xczhihui.course.model.WeiboClientUserMapping;
import com.xczhihui.course.service.IThreePartiesLoginService;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.Token;

import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.http.HttpClient;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

/**
 * 用户controller
 *
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/weibo")
public class WeiBoThirdPartyController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WeiBoThirdPartyController.class);

    public HttpClient client = new HttpClient();
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private IThreePartiesLoginService threePartiesLoginService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserCenterService userCenterService;

    //手机端登录使用
    @Value("${mobile.authorizeURL}")
    public String weiboMobileAuthorizeURL;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    /**
     * Description：h5 --》微博回调接口  -- 回调了接口后需要请求
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "evokeWeiBoRedirect")
    public void evokeWeiBoRedirect(HttpServletRequest req,
                                   HttpServletResponse res) {

        String code = req.getParameter("code");
        String state = (String) req.getSession().getAttribute("weibo_connect_state");
        Oauth oauth = new Oauth();
        try {
            /**
             * 通过code获取认证 微博唯一票据
             */
            AccessToken at = oauth.getAccessTokenByCode(code);
            String uId = at.getUid();
            if (StringUtils.isBlank(state)) {
                LOGGER.info("获取accessToken信息有误：");
                res.sendRedirect(returnOpenidUri + "/xcview/html/enter.html");
            }
            WeiboClientUserMapping wbuser = new WeiboClientUserMapping();
            try {
                Users um = new Users();
                um.client.setToken(at.getAccessToken());

                WeiboClientUserMapping wcum = threePartiesLoginService.selectWeiboClientUserMappingByUid(at.getUid());
                LOGGER.info("是否存在此微博号--------:" + wcum);
                if (wcum == null) {
                    JSONObject job = um.client.get(WeiboConfig.getValue("baseURL") + "users/show.json",
                            new PostParameter[]{new PostParameter("uid", at.getUid())}).asJSONObject();

                    wbuser = new WeiboClientUserMapping(job);
                    wbuser.setId(CodeUtil.getRandomUUID());
                    //用户昵称
                    String screenName = wbuser.getScreenName();
                    screenName = SLEmojiFilter.filterEmoji(screenName);
                    wbuser.setScreenName(screenName);
                    //友好显示名称
                    String name = wbuser.getName();
                    name = SLEmojiFilter.filterEmoji(name);
                    wbuser.setName(name);
                    threePartiesLoginService.saveWeiboClientUserMapping(wbuser);

                    //直接重定向到完善信息
                    res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?uId=" + uId + "&type=" + ThirdPartyType.WEIBO.getCode());

                } else if (StringUtils.isNotBlank(wcum.getUserId())) { //绑定了用户信息了

                    LOGGER.info("绑定了用户信息了-wcum.getUserId()-------:" + wcum.getUserId());

                    OnlineUser ou = onlineUserService.findUserById(wcum.getUserId());
                    Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
                    //把用户中心的数据给他  --这些数据是IM的
                    ou.setUserCenterId(ou.getId());
                    ou.setTicket(t.getTicket());

                    UCCookieUtil.writeTokenCookie(res, t);
                    //重定向到推荐首页
                    res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html");

                } else if (!StringUtils.isNotBlank(wcum.getUserId())) {

                    LOGGER.info("没有绑定了用户信息了" + wcum.getUserId());

                    //直接重定向到完善信息
                    res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?uId=" + uId + "&type=" + ThirdPartyType.WEIBO.getCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("==============");
            }
        } catch (WeiboException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Description：APP ---> 微博回调接口  -- 回调了接口后需要请求
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "appEvokeWeiBoRedirect")
    @ResponseBody
    public ResponseObject appEvokeWeiBoRedirect(HttpServletRequest req,
                                                HttpServletResponse res,
                                                @RequestParam("accessToken") String accessToken,
                                                @RequestParam("uId") String uId,
                                                @RequestParam("model") String model) {
        try {
            String code = req.getParameter("code");
            LOGGER.info("微博用户授权登录成功code" + code);

            //判断是绑定呢，还是解除绑定呢
            String userId = req.getParameter("userId");
            if (StringUtils.isNotBlank(userId)) {
                OnlineUser ou = onlineUserService.findUserById(userId);
                if (ou == null) {
                    return ResponseObject.newErrorResponseObject("获取用户信息有误");
                }
                WeiboClientUserMapping wbcm =  threePartiesLoginService.selectWeiboClientUserMappingByUserId(userId);
                if (wbcm!=null) {
                    return ResponseObject.newErrorResponseObject("用户已绑定过微博号");
                }
            }

            Map<String, String> mapRequest = new HashMap<>();
            mapRequest.put("type", ThirdPartyType.WEIBO.getCode() + "");
            LOGGER.info("微博唯一票据--------》认证AccessToken成功:" + accessToken);
            LOGGER.info("微博用户唯一uid--------:" + uId);
            try {
                Users um = new Users();
                um.client.setToken(accessToken);
                WeiboClientUserMapping wcum = threePartiesLoginService.selectWeiboClientUserMappingByUid(uId);
                LOGGER.info("是否存在此微博号--------:" + wcum);
                if (wcum == null) {
                    JSONObject job = um.client.get(WeiboConfig.getValue("baseURL") + "users/show.json",
                            new PostParameter[]{new PostParameter("uid", uId)}).asJSONObject();

                    WeiboClientUserMapping wbuser = new WeiboClientUserMapping(job);
                    wbuser.setId(CodeUtil.getRandomUUID());
                    //用户昵称
                    String screenName = wbuser.getScreenName();
                    screenName = SLEmojiFilter.filterEmoji(screenName);
                    wbuser.setScreenName(screenName);
                    //友好显示名称
                    String name = wbuser.getName();
                    name = SLEmojiFilter.filterEmoji(name);
                    wbuser.setName(name);

                    if (StringUtils.isNotBlank(userId)) {  // 绑定成功
                        wbuser.setUserId(userId);
                        mapRequest.put("code", UserUnitedStateType.MOBILE_BINDING.getCode() + "");
                    } else {
                        mapRequest.put("code", UserUnitedStateType.UNBOUNDED.getCode() + "");
                    }
                    mapRequest.put("unionId", uId + "");

                    threePartiesLoginService.saveWeiboClientUserMapping(wbuser);

                    return ResponseObject.newSuccessResponseObject(mapRequest, Integer.parseInt(mapRequest.get("code")));
                } else if (StringUtils.isNotBlank(wcum.getUserId())) { //绑定了用户信息了
                    LOGGER.info("绑定了用户信息了-wcum.getUserId()-------:" + wcum.getUserId());
                    if (StringUtils.isNotBlank(userId)) {  // 这里说明人家这个已经绑定过其他信息了。
                        mapRequest.put("code", UserUnitedStateType.MOBILE_UNBOUNDED.getCode() + "");
                        return ResponseObject.newSuccessResponseObject(mapRequest, UserUnitedStateType.MOBILE_UNBOUNDED.getCode());
                    }

                    OnlineUser ou = onlineUserService.findUserById(wcum.getUserId());
                    Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);

                    //把用户中心的数据给他  --这些数据是IM的
                    ou.setUserCenterId(ou.getId());
                    ou.setTicket(t.getTicket());
                    UCCookieUtil.writeTokenCookie(res, t);
                    return ResponseObject.newSuccessResponseObject(ou, UserUnitedStateType.BINDING.getCode());
                } else if (!StringUtils.isNotBlank(wcum.getUserId())) {

                    LOGGER.info("没有绑定了用户信息了" + wcum.getUserId());
                    if (StringUtils.isNotBlank(userId)) {  // 绑定成功
                        mapRequest.put("code", UserUnitedStateType.MOBILE_BINDING.getCode() + "");
                        wcum.setUserId(userId);
                        threePartiesLoginService.updateWeiboInfoAddUserId(wcum);
                    } else {
                        mapRequest.put("code", UserUnitedStateType.UNBOUNDED.getCode() + "");
                    }
                    mapRequest.put("unionId", uId + "");
                    return ResponseObject.newSuccessResponseObject(mapRequest, Integer.parseInt(mapRequest.get("code")));
                }
            } catch (Exception e) {
                e.printStackTrace();
                mapRequest.put("code", UserUnitedStateType.DATA_IS_WRONG.getCode() + "");
                mapRequest.put("unionId", uId + "");
                return ResponseObject.newSuccessResponseObject(mapRequest, UserUnitedStateType.DATA_IS_WRONG.getCode());
            }
            return ResponseObject.newSuccessResponseObject("");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Description：唤起微博第三方登录授权页面   --获取code
     *
     * @param req
     * @param response
     * @return ResponseObject
     * @throws WeiboException
     * @throws IOException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "evokeWeiBoOuth")
    public void evokeWeiBoOuth(HttpServletRequest req,
                               HttpServletResponse response) throws IOException, WeiboException {
        /**
         * state 用户防止其他人乱调用
         * mobile 手机浏览器调用
         */
        String state = CodeUtil.getRandomUUID();
        req.getSession().setAttribute("weibo_connect_state", state);

        response.sendRedirect(authorize("code", state, "mobile"));
    }

    /**
     * Description：微博接口
     *
     * @param response_type 响应类型 默认code
     * @param state
     * @param display       default 默认的授权页面，适用于web浏览器。
     *                      mobile  移动终端的授权页面，适用于支持html5的手机,使用此版授权页请用 https://open.weibo.cn/oauth2/authorize 授权接口
     *                      client 客户端版本授权页面，适用于PC桌面应用
     *                      wap 	wap版授权页面，适用于非智能手机。
     *                      apponweibo 默认的站内应用授权页，授权后不返回access_token，只刷新站内应用父框架。
     * @return String
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public String authorize(String response_type, String state, String display) throws WeiboException {
        return weiboMobileAuthorizeURL.trim() +
                "?client_id=" + WeiboConfig.getValue("client_ID").trim() +
                "&redirect_uri=" + WeiboConfig.getValue("redirect_URI").trim() +
                "&response_type=" + response_type +
                "&state=" + state +
                "&display=" + display;
    }
}
