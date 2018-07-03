package com.xczh.consumer.market.interceptor;

import static com.xczh.consumer.market.utils.ResponseObject.tokenBlankError;
import static com.xczh.consumer.market.utils.ResponseObject.tokenExpired;
import static com.xczhihui.common.util.enums.UserUnitedStateType.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.user.center.exception.LoginRegException;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.ThirdFlag;
import com.xczhihui.user.center.vo.Token;

/**
 * @author hejiwei
 */
@Component
public class AuthInterceptor implements HandlerInterceptor, HandlerMethodArgumentResolver {

    private static final String TOKEN_PARAM_NAME = "token";
    private static final String APP_UNIQUE_ID_PARAM_NAME = "appUniqueId";
    private static final String ENTER_HTML_URL = "/xcview/html/enter.html";
    private static final String EVPI_HTML_URL = "/xcview/html/evpi.html";
    private static final String WEIXIN_AUTH_CALLBACK = "/xczh/wxlogin/middle?url=";
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private static final String X_REQUEST_WITH_HEADER_NAME = "x-requested-with";
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String MICRO_MESSENGER = "micromessenger";
    private static List<String> noNeedAuthPaths = Arrays.asList("/xczh/user/**", "/xczh/share/**", "/xczh/qq/**",
            "/xczh/wxlogin/**", "/xczh/weibo/**", "/xczh/third/**", "/xczh/wxpublic/**", "/xczh/alipay/alipayNotifyUrl",
            "/bxg/wxpay/wxNotify", "/xczh/alipay/pay", "/xczh/alipay/rechargePay", "/xczh/criticize/getCriticizeList",
            "/xczh/ccvideo/palyCode", "/xczh/wechatJssdk/certificationSign", "/xczh/manager/home",
            "/xczh/common/getProblems", "/xczh/common/verifyLoginStatus", "/xczh/common/getProblemAnswer",
            "/xczh/common/checkUpdate", "/xczh/common/addOpinion", "/xczh/gift/rankingList", "/xczh/common/richTextDetails", "/xczh/common/getDomain",
            "/xczh/gift/list", "/xczh/common/checkToken", "/xczh/message", "/xczh/pay/pay_notify", "/xczh/set/isLogined",
            "/xczh/recommend/**", "/xczh/classify/**", "/xczh/bunch/**", "/xczh/live/**", "/xczh/host/**", "/xczh/host/doctor/v2", "/xczh/course/**", "/xczh/doctors/**",
            "/xczh/enrol/enrollmentRegulations", "/xczh/myinfo/showWallet", "/xczh/page/course/*", "/xczh/article/view", "/xczh/article/appraise/list",
            "/doctor/posts/**");
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PathMatcher pathMatcher;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private CommonsMultipartResolver multipartResolver;
    @Autowired
    private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!isExcludePath(request)) {
            ServletOutputStream outputStream = response.getOutputStream();
            if (isAppRequest(request)) {
                String token = getParam(request, TOKEN_PARAM_NAME);
                ResponseObject responseObject = null;
                if (StringUtils.isBlank(token)) {
                    responseObject = tokenBlankError();
                } else {
                    Token redisToken = userCenterService.getToken(token);
                    if (redisToken == null) {
                        responseObject = tokenExpired();
                    } else if (userCenterService.isDisabled(redisToken.getUserId())) {
                        responseObject = tokenExpired();
                        userCenterService.deleteToken(redisToken.getTicket());
                    }
                }
                if (responseObject != null) {
                    writeData(response, outputStream, responseObject);
                    return false;
                }
                return true;
            } else {
                Token token = UCCookieUtil.readTokenCookie(request);
                ResponseObject responseObject;
                String redirectUrl;
                Token redisToken = null;
                if (token != null) {
                    redisToken = userCenterService.getToken(token.getTicket());
                }
                boolean isAjax = isAjax(request);
                boolean isWeixin = isWeixin(request);
                if (token == null || redisToken == null) {
                    if (isWeixin) {
                        boolean isNeedBind = isNeedBind(request);
                        if (isNeedBind) {
                            redirectUrl = request.getContextPath() + EVPI_HTML_URL;
                            responseObject = ResponseObject.newErrorResponseObject(null, GO_TO_BIND.getCode());
                        } else {
                            redirectUrl = request.getContextPath() + WEIXIN_AUTH_CALLBACK + URLEncoder.encode(request.getServletPath(), "UTF-8");
                            responseObject = ResponseObject.newErrorResponseObject(null, WEIXIN_AUTH.getCode());
                        }
                        logger.info("wechat的ajax请求：{}", responseObject.toString());
                    } else {
                        redirectUrl = request.getContextPath() + ENTER_HTML_URL;
                        responseObject = ResponseObject.newErrorResponseObject(null, OVERDUE.getCode());
                    }
                } else {
                    if (!userCenterService.isDisabled(token.getUserId())) {
                        return true;
                    } else {
                        userCenterService.deleteToken(token.getTicket());
                        redirectUrl = request.getContextPath() + ENTER_HTML_URL;
                        responseObject = ResponseObject.newErrorResponseObject(null, OVERDUE.getCode());
                    }
                }
                if (isAjax) {
                    writeData(response, outputStream, responseObject);
                } else {
                    response.sendRedirect(redirectUrl);
                }
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 判断如果存在第三方token，并且未绑定手机号，先去绑定手机号
     *
     * @param request
     * @return
     * @throws SQLException
     */
    private boolean isNeedBind(HttpServletRequest request) throws SQLException {
        ThirdFlag thirdFlag = UCCookieUtil.readThirdPartyCookie(request);
        if (thirdFlag != null) {
            String unionId = thirdFlag.getUnionId();
            if (StringUtils.isNotBlank(unionId)) {
                WxcpClientUserWxMapping wxcpClientUser = wxcpClientUserWxMappingService.getWxcpClientUserByUnionId(unionId);
                //有第三方登陆token,但是没有绑定手机号时，需要去完善信息
                if (wxcpClientUser != null && StringUtils.isBlank(wxcpClientUser.getClient_id())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {

    }

    private boolean isAjax(HttpServletRequest request) {
        return request.getHeader(X_REQUEST_WITH_HEADER_NAME) != null
                && XML_HTTP_REQUEST.equalsIgnoreCase(request.getHeader(X_REQUEST_WITH_HEADER_NAME));
    }

    private boolean isWeixin(HttpServletRequest request) {
        String header = request.getHeader(USER_AGENT_HEADER_NAME).toLowerCase();
        return header.contains(MICRO_MESSENGER);
    }

    private boolean isExcludePath(HttpServletRequest request) {
        String path = request.getServletPath();
        boolean isExclude = false;
        logger.info("url:{}", path);
        for (String publicPath : noNeedAuthPaths) {
            if (pathMatcher.match(publicPath, path)) {
                isExclude = true;
                break;
            }
        }
        return isExclude;
    }

    private boolean isAppRequest(HttpServletRequest request) {
        return StringUtils.isNotBlank(getParam(request, APP_UNIQUE_ID_PARAM_NAME));
    }

    private String getParam(HttpServletRequest request, String paramName) {
        String param;
        String contentType = request.getContentType();
        if (StringUtils.isNotBlank(contentType) && contentType.contains("multipart/form-data")) {
            MultipartHttpServletRequest multiReq = multipartResolver.resolveMultipart(request);
            param = multiReq.getParameter(paramName);
        } else {
            param = request.getParameter(paramName);
        }
        return param;
    }

    /**
     * 这里没有选用PrintWriter 而选用 ServletOutputStream ,是因为在同一个response中只能选其一，spring 底层使用的是ServletOutputStream，
     * 故此处与spring统一
     * 此处的responseObject 不能包含中文, ServletOutputStream输出不支持中文
     *
     * @param response
     * @param outputStream
     * @param responseObject
     * @throws IOException
     */
    private void writeData(HttpServletResponse response, ServletOutputStream outputStream, ResponseObject responseObject) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        outputStream.print(JSON.toJSONString(responseObject));
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Account.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token;
        String str = request.getServletPath();
        if (isAppRequest(request)) {
            token = getParam(request, TOKEN_PARAM_NAME);
        } else {
            Token tokenData = UCCookieUtil.readTokenCookie(request);
            token = tokenData != null ? tokenData.getTicket() : null;
        }
        Account annotation = parameter.getParameterAnnotation(Account.class);
        Class<?> clazz = parameter.getParameterType();

        Token tokenData = userCenterService.getToken(token);
        String userId = tokenData != null ? tokenData.getUserId() : null;
        if (annotation.optional()) {
            if (Optional.class.isAssignableFrom(clazz)) {
                return Optional.ofNullable(userId);
            } else {
                return userId;
            }
        } else {
            if (tokenData == null) {
                throw new LoginRegException("token 无效" + str);
            }
            if (OnlineUser.class.isAssignableFrom(clazz)) {
                return onlineUserService.findUserById(userId);
            } else {
                return userId;
            }
        }
    }
}
