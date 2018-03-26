package com.xczhihui.bxg.common.util;

public class QiniuUtil {

    private static final String QINIU_SLIM_PARAM = "imageslim";

    /**
     * 七牛图片瘦身处理
     * @param url 七牛图片url
     * @return
     */
    public static String slim(String url) {
        if (url != null) {
            if (url.contains("?")) {
                if (url.endsWith("?")) {
                    url = url + QINIU_SLIM_PARAM;
                } else {
                    url = url + "&" + QINIU_SLIM_PARAM;
                }
            } else {
                url = url + "?" + QINIU_SLIM_PARAM;
            }
        }
        return url;
    }
}
