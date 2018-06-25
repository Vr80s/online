package com.xczh.consumer.market.controller.threeparties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.RandomStatusGenerator;
import com.qq.connect.utils.http.HttpClient;
import com.qq.connect.utils.http.PostParameter;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.enums.ThirdPartyType;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.common.util.enums.UserUnitedStateType;
import com.xczhihui.course.model.QQClientUserMapping;
import com.xczhihui.course.service.IThreePartiesLoginService;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.vo.Token;

/**
 * 用户controller
 *
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/qq")
public class QQThirdPartyController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(QQThirdPartyController.class);

    private static final Pattern UNION_ID_PATTERN = Pattern.compile("\"unionid\"\\s*:\\s*\"(\\w+)\"");

    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private IThreePartiesLoginService threePartiesLoginService;
    @Autowired
    private UserCenterService userCenterAPI;
    protected HttpClient client = new HttpClient();

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    /**
     * Description：h5 -- > 回调接口  -- 回调了接口后需要请求  。本期先不做
     *
     * @param request
     * @param res
     * @return ResponseObject
     * @throws IOException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "evokeQQRedirect")
    public void evokeQQRedirect(HttpServletRequest request,
                                HttpServletResponse res) throws IOException {
        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            String accessToken, openID;
            Map<String, String> mapRequest = new HashMap<String, String>();
            LOGGER.info("accessTokenObj" + accessTokenObj.toString());
            if (accessTokenObj.getAccessToken().equals("")) {

                LOGGER.info("获取accessToken信息有误：");

                res.sendRedirect(returnOpenidUri + "/xcview/html/enter.html");
            } else {
                accessToken = accessTokenObj.getAccessToken();
                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj = new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();

                String unionId = this.getQQUnionIdByOpenIdAndAccessToken(accessToken);

                QQClientUserMapping qqUser = threePartiesLoginService.selectQQClientUserMappingByUnionId(unionId);

                //保存qq用户
                if (qqUser == null) {
                    LOGGER.info("第一次存入qq用户信息");
                    // 利用获取到的accessToken 去获取当前用户的openid --------- end
                    UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                    UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                    
                    QQClientUserMapping qq = new QQClientUserMapping(userInfoBean,openID);

                    threePartiesLoginService.saveQQClientUserMapping(qq);

                    //直接重定向到完善信息
                    res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?openId=" + openID + "&type=" + ThirdPartyType.QQ.getCode());

                    //绑定了用户信息
                } else if (StringUtils.isNotBlank(qqUser.getUserId())) {

                    LOGGER.info("熊猫中医用户id   ============已绑定用户信息" + qqUser.getUserId());

                    OnlineUser ou = onlineUserService.findUserById(qqUser.getUserId());

                    Token t = userCenterAPI.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
                    //把用户中心的数据给他  --这些数据是IM的
                    ou.setUserCenterId(ou.getId());
                    ou.setTicket(t.getTicket());
                    //重定向到推荐首页
                    res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html");
                } else if (StringUtils.isNotBlank(qqUser.getUserId())) {
                    LOGGER.info("熊猫中医用户id 没有绑定用户信息" + qqUser.getUserId());
                    mapRequest.put("code", UserUnitedStateType.UNBOUNDED.getCode() + "");
                    mapRequest.put("openId", openID);
                    //直接重定向到完善信息
                    res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?openId=" + openID + "&type=" + ThirdPartyType.QQ.getCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //重定向到推荐首页
            res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html");
        }
    }

    /**
     * Description：app -- QQ回调接口  -- 回调了接口后需要请求
     *
     * @param request
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "appEvokeQQRedirect")
    @ResponseBody
    public ResponseObject appEvokeQQRedirect(HttpServletRequest request,
             HttpServletResponse res,@RequestParam("accessToken") String accessToken,
             @RequestParam("openId") String openId,@RequestParam("model") String model) throws Exception {
    	
        String userId = request.getParameter("userId");
        if (StringUtils.isNotBlank(userId)) {
            OnlineUser ou = onlineUserService.findUserById(userId);
            if (ou == null) {
                return ResponseObject.newErrorResponseObject("获取用户信息有误");
            }
            QQClientUserMapping  qqUser =threePartiesLoginService.selectQQClientUserMappingByUserId(userId);
            if(qqUser!=null) {
            	return ResponseObject.newErrorResponseObject("用户已绑定过QQ号");
            }
        }

        Map<String, String> mapRequest = new HashMap<>();
        mapRequest.put("type", ThirdPartyType.QQ.getCode() + "");

        if (accessToken == null) {
            return ResponseObject.newErrorResponseObject("获取QQ：accessToken 有误");
        } else {
            QQClientUserMapping qqUser = threePartiesLoginService.selectQQClientUserMappingByOpenId(openId);
            if (qqUser == null) {   //保存qq用户
                UserInfoBean userInfoBean = this.getUserInfo(accessToken, openId);

                QQClientUserMapping qq = new QQClientUserMapping(userInfoBean,openId);
                
                //用户id不等于null时，就判定用户第三方登录是通过手机号来绑定 第三方登录信息的
                if (StringUtils.isNotBlank(userId)) {  // 绑定成功
                    qq.setUserId(userId);
                    mapRequest.put("code", UserUnitedStateType.MOBILE_BINDING.getCode() + "");
                } else {
                    mapRequest.put("code", UserUnitedStateType.UNBOUNDED.getCode() + "");
                }
                mapRequest.put("unionId", openId);

                threePartiesLoginService.saveQQClientUserMapping(qq);

                return ResponseObject.newSuccessResponseObject(mapRequest, Integer.parseInt(mapRequest.get("code")));
                //绑定了用户信息
            } else if (StringUtils.isNotBlank(qqUser.getUserId())) {
                if (StringUtils.isNotBlank(userId)) {  // 这里说明人家这个已经绑定过其他信息了。
                    mapRequest.put("code", UserUnitedStateType.MOBILE_UNBOUNDED.getCode() + "");
                    return ResponseObject.newSuccessResponseObject(mapRequest, UserUnitedStateType.MOBILE_UNBOUNDED.getCode());
                }
                OnlineUser ou = onlineUserService.findUserById(qqUser.getUserId());

                Token t = userCenterAPI.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
                //把用户中心的数据给他  --这些数据是IM的
                ou.setUserCenterId(ou.getId());
                ou.setTicket(t.getTicket());
                return ResponseObject.newSuccessResponseObject(ou, UserUnitedStateType.BINDING.getCode());
            } else if (!StringUtils.isNotBlank(qqUser.getUserId())) {

                LOGGER.info("熊猫中医用户id 没有绑定用户信息" + qqUser.getUserId());

                if (StringUtils.isNotBlank(userId)) {  // 绑定成功
                    mapRequest.put("code", UserUnitedStateType.MOBILE_BINDING.getCode() + "");
                    qqUser.setUserId(userId);
                    threePartiesLoginService.updateQQInfoAddUserId(qqUser);
                } else {
                    mapRequest.put("code", UserUnitedStateType.UNBOUNDED.getCode() + "");
                }
                mapRequest.put("unionId", openId);
                return ResponseObject.newSuccessResponseObject(mapRequest, Integer.parseInt(mapRequest.get("code")));
            }
            return ResponseObject.newSuccessResponseObject("");
        }
    }

    /**
     * Description：获取ios的配置信息啦
     *
     * @param openid
     * @return UserInfoBean
     * @throws QQConnectException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    private UserInfoBean getUserInfo(String token, String openid) throws QQConnectException {
        return new UserInfoBean(this.client.get(QQConnectConfig.getValue("getUserInfoURL"), new PostParameter[]{
                new PostParameter("openid", openid),
                new PostParameter("oauth_consumer_key", "1106447696"),
                new PostParameter("access_token", token), new PostParameter("format", "json")}).asJSONObject());
    }


    /**
     * Description：h5 --》 唤起qq第三方登录授权页面
     *
     * @param request
     * @param response
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "evokeQQOuth")
    public void evokeQQOuth(HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            LOGGER.info("进入唤起qq第三方登录方法============");
            /**
             * client端的状态值。
             * 用于第三方应用防止CSRF攻击(跨站请求伪造)
             */
            String state = RandomStatusGenerator.getUniqueState();
            request.getSession().setAttribute("qq_connect_state", state);
            String scope = QQConnectConfig.getValue("scope");
            String state1 = (String) request.getSession().getAttribute("qq_connect_state");
            /**
             * 请求转发
             */
            String redirectUrl = getCustomAuthorizeURL("code", state, scope, "mobile");
            LOGGER.info("打印的从定向地址：+" + redirectUrl);
            response.sendRedirect(redirectUrl);
        } catch (IOException | QQConnectException e) {
            e.printStackTrace();
        }
    }

    /**
     * Description：获取第一步  得到code
     *
     * @param responseType 默认值code
     * @param state        state
     * @param scope
     * @param display
     * @return String
     * @throws QQConnectException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    private String getCustomAuthorizeURL(String responseType, String state, String scope, String display) throws QQConnectException {
        return QQConnectConfig.getValue("authorizeURL").trim() + "?client_id=" + QQConnectConfig.getValue("app_ID").trim() +
                "&redirect_uri=" + QQConnectConfig.getValue("redirect_URI").trim() +
                "&response_type=" + responseType + "&state=" + state + "&scope=" + scope;
    }

    private String getQQUnionIdByOpenIdAndAccessToken(String accessToken) throws QQConnectException {
        String jsonp = client.get(QQConnectConfig.getValue("getOpenIDURL").trim() + "?access_token=" + accessToken + "&unionid=1").asString();
        Matcher m = UNION_ID_PATTERN.matcher(jsonp);
        if (m.find()) {
            return m.group(1);
        } else {
            throw new QQConnectException("server error!");
        }
    }
}
