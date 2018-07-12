package com.xczhihui.course.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hejiwei
 */
public class RouteUrlUtil {

    public static Map<String, Object> handleUrl(String url) {
        String prefix = "xczh://ipandatcm.com/";
        String pageType = null;//跳转页面对应的类型
        //跳转页面请求接口需要的参数(当前最多一个)
        Map<String, Object> params = new HashMap<>(4);
        if (url.startsWith(prefix)) {
            String queryPath = url.replace(prefix, "");
            if (queryPath.length() > 1) {
                //带参数
                if (queryPath.contains("?")) {
                    String[] pathParams = queryPath.split("\\?");
                    pageType = pathParams[0];
                    String paramStr = pathParams[1].replace("?", "");
                    String[] paramArr = paramStr.split("&");
                    for (String param : paramArr) {
                        if (param.contains("=")) {
                            String[] kv = param.split("=");
                            params.put(kv[0], kv[1]);
                        }
                    }
                } else {//没有参数,
                    pageType = queryPath;
                }
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("pageType", pageType);
        result.put("params", params);
        return result;
    }

}
