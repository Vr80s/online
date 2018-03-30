package com.xczhihui.bxg.online.manager.support;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.manager.user.service.UserService;

@Component
public class ManagerAuthFilter extends FormAuthenticationFilter {

    private static final String AJAX_REQUEST_FLAG = "XMLHttpRequest";
    private static final String AJAX_REQUEST_HEADER_FLAG = "X-Requested-With";

    @Autowired
    private UserService userService;

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //ajax请求
        if (isAjax(httpServletRequest)) {
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            httpServletResponse.getWriter().write(new Gson().toJson(ResponseObject.newSuccessResponseObject(null)));
            return false;
        } else {//非ajax请求
            return super.onLoginSuccess(token, subject, request, response);
        }
    }

    @Override
    protected AuthenticationToken createToken(String username, String password, boolean rememberMe, String host) {
        return new UsernamePasswordToken(username, password);
    }

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException e) {
        request.setAttribute("shiroLoginException", e);
        super.setFailureAttribute(request, e);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //ajax请求
        if (isAjax(httpServletRequest)) {
            try {
                httpServletResponse.setContentType("application/json; charset=utf-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.getWriter().write(new Gson().toJson(ResponseObject.newErrorResponseObject("用户名密码错误！")));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        } else {//非ajax请求
            return super.onLoginFailure(token, e, request, response);
        }
    }

    private boolean isAjax(HttpServletRequest request) {
        return AJAX_REQUEST_FLAG.equalsIgnoreCase(request.getHeader(AJAX_REQUEST_HEADER_FLAG));
    }
}
