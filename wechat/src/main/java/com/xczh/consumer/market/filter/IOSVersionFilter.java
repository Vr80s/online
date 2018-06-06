package com.xczh.consumer.market.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;


/**
 * Description：ios版本过滤器
 * creed: Talk is cheap,show me the code
 * @author name：yuxin
 * @Date: 2018/6/6 0006 上午 11:26
 **/
public class IOSVersionFilter implements Filter {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IOSVersionFilter.class);
    private static String IVERSION = "iversion";
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String iversion = request.getHeader(IVERSION);
        if(StringUtils.isBlank(iversion)){
            iversion = request.getParameter(IVERSION);
        }
        threadLocal.set(iversion);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
