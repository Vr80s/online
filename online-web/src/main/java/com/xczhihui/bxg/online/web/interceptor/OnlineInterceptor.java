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
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
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
public class OnlineInterceptor implements HandlerInterceptor {

    private SimpleHibernateDao dao;

    @Autowired
    private UserCenterService userCenterService;

    @Autowired
    private UserService userService;

    private List<String> checkuris = new ArrayList<String>();
    private List<String> checkanchoruris = new ArrayList<String>();
    private List<String> checkanonuris = new ArrayList<String>();
    private List<String> checkanonanchoruris = new ArrayList<String>();

    public OnlineInterceptor() {
        try {
            URL controller = this.getClass().getClassLoader().getResource("auth.xml");
            Document doc = new SAXReader().read(controller.openStream());
            List<Element> uris = doc.selectNodes("//checkuris/uri");
            for (Element e : uris) {
                checkuris.add(e.getStringValue());
            }
            //新增主播校验2018-02-06
            List<Element> anchoruris = doc.selectNodes("//checkuris/anchoruri");
            for (Element e : anchoruris) {
                checkuris.add(e.getStringValue());
                checkanchoruris.add(e.getStringValue());
            }
            //新增无需登录鉴权2018-02-06
            List<Element> anonuris = doc.selectNodes("//checkuris/anonuri");
            for (Element e : anonuris) {
                checkanonuris.add(e.getStringValue());
            }
            //新增无需主播鉴权2018-02-06
            List<Element> anonanchoruris = doc.selectNodes("//checkuris/anonanchoruri");
            for (Element e : anonuris) {
                checkanonanchoruris.add(e.getStringValue());
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
        boolean b = (token == null) && checkUris(request.getRequestURI());
        if (b) {
            Gson gson = new GsonBuilder().create();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(ResponseObject.newErrorResponseObject("请登录！")));
            return false;
        } else {
            OnlineUser user = getUser(token);
            //设置当前用户
            com.xczhihui.bxg.online.web.controller.AbstractController.setCurrentUser(user);
        }

        //已经登录，但是调用了主播角色才可以调的接口
        if (checkAnchoruris(request.getRequestURI())) {
            BxgUser user = UserLoginUtil.getLoginUser();
            Boolean anchor = userService.isAnchor(user.getLoginName());
            if (!anchor) {
                response.sendRedirect("/");
                return false;
                /*Gson gson = new GsonBuilder().create();
                response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(ResponseObject.newErrorResponseObject("不具有主播权限或权限被禁用！")));
				return false;*/
            }
        }

        return true;
    }

    private boolean checkUris(String uri) {
        if (!checkanonUris(uri)) {
            for (int i = 0; i < checkuris.size(); i++) {
                String checkuri = checkuris.get(i);
                Pattern pattern = Pattern.compile(checkuri);
                Matcher matcher = pattern.matcher(uri);
                if (matcher.matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkAnchoruris(String uri) {
        if (!checkanonanchorUris(uri)) {
            for (int i = 0; i < checkanchoruris.size(); i++) {
                String checkuri = checkanchoruris.get(i);
                Pattern pattern = Pattern.compile(checkuri);
                Matcher matcher = pattern.matcher(uri);
                if (matcher.matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkanonUris(String uri) {
        for (int i = 0; i < checkanonuris.size(); i++) {
            String checkanonuri = checkanonuris.get(i);
            Pattern pattern = Pattern.compile(checkanonuri);
            Matcher matcher = pattern.matcher(uri);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkanonanchorUris(String uri) {
        for (int i = 0; i < checkanonanchoruris.size(); i++) {
            String checkanonanchoruri = checkanonanchoruris.get(i);
            Pattern pattern = Pattern.compile(checkanonanchoruri);
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
}
