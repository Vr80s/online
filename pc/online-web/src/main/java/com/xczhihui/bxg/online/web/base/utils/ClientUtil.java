package com.xczhihui.bxg.online.web.base.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端工具类
 * @author majian
 * @date 2016-4-19 11:53:43
 */
public class ClientUtil {

    /**
     * 返回客户端IP
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request){
        String ip="0.0.0.0";
        if(request!=null){
            request.getHeader( "x-forwarded-for" );
            if(StringUtils.isNotEmpty(ip)&&!"unknown" .equalsIgnoreCase(ip)){
                return ip;
            }
            ip=request.getHeader( "WL-Proxy-Client-IP" );
            if(StringUtils.isNotEmpty(ip)&&!"unknown" .equalsIgnoreCase(ip)){
                return ip;
            }
            ip=request.getRemoteAddr();
        }
        return ip;
    }
}
