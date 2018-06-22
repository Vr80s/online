package com.xczh.consumer.market.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.xczhihui.course.consts.MultiUrlHelper;

/**
 * @author hejiwei
 */
public class APPUtil {

    public static boolean isAPP(HttpServletRequest request) {
        return StringUtils.isNotBlank(request.getParameter("appUniqueId"));
    }

    public static String getMobileSource(HttpServletRequest request) {
        return isAPP(request) ? MultiUrlHelper.URL_TYPE_APP : MultiUrlHelper.URL_TYPE_MOBILE;
    }
}
