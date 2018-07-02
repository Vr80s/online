package com.xczhihui.bxg.online.web.interceptor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.Token;

/**
 * Description：权限拦截
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/26 0026 上午 10:56
 **/
@Component
public class OnlineInterceptor implements HandlerInterceptor {

    private static final String AJAX_REQUEST_FLAG = "XMLHttpRequest";
    private static final String AJAX_REQUEST_HEADER_FLAG = "X-Requested-With";
    private SimpleHibernateDao dao;

    @Autowired
    private UserCenterService userCenterService;

    private List<String> openuris = new ArrayList<String>();

    public OnlineInterceptor() {
        try {
            URL controller = this.getClass().getClassLoader().getResource("auth.xml");
            Document doc = new SAXReader().read(controller.openStream());
            List<Element> uris = doc.selectNodes("//openuris/uri");
            for (Element e : uris) {
                openuris.add(e.getStringValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Token t = UCCookieUtil.readTokenCookie(request);
        Token token = null;
        if (t != null) {
            token = userCenterService.getToken(t.getTicket());
        }

        //没登录，但是调用了必须登录才可以调的接口
        boolean isOpen = openUris(request.getRequestURI());
        boolean b = (token == null) && !isOpen;
        if (b) {
            if (isAjax(request)) {
                Gson gson = new GsonBuilder().create();
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(ResponseObject.newErrorResponseObject("请登录！")));
            } else {
                response.sendRedirect("/web/html/login.html");
            }
            return false;
        }
        OnlineUser user = null;
        if (token != null) {
            user = getUser(token);
        }
        //设置当前用户
        com.xczhihui.bxg.online.web.controller.AbstractController.setCurrentUser(user);
        return true;
    }

    private boolean openUris(String uri) {
        for (int i = 0; i < openuris.size(); i++) {
            String openuri = openuris.get(i);
            Pattern pattern = Pattern.compile(openuri);
            Matcher matcher = pattern.matcher(uri);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }


    private OnlineUser getUser(Token t) {
        if (t == null) {
            return null;
        }
        OnlineUser ou = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", t.getLoginName());
        return ou;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    @Resource(name = "simpleHibernateDao")
    public void setDao(SimpleHibernateDao dao) {
        this.dao = dao;
    }

    private boolean isAjax(HttpServletRequest request) {
        return AJAX_REQUEST_FLAG.equalsIgnoreCase(request
                .getHeader(AJAX_REQUEST_HEADER_FLAG));
    }
}
