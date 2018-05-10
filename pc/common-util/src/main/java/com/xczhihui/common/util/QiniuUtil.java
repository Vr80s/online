package com.xczhihui.common.util;

public class QiniuUtil {

    private static final String QINIU_SLIM_PARAM = "imageslim";
    private static final String URL_PARAM_START_MARK = "?";
    private static final String URL_PARAM_AND_MARK = "&";

    /**
     * 七牛图片瘦身处理
     * @param url 七牛图片url
     * @return 处理后的url
     */
    public static String slim(String url) {
        if (url != null) {
            if (url.contains(URL_PARAM_START_MARK)) {
                if (url.endsWith(URL_PARAM_START_MARK)) {
                    url = url + QINIU_SLIM_PARAM;
                } else {
                    url = url + URL_PARAM_AND_MARK + QINIU_SLIM_PARAM;
                }
            } else {
                url = url + URL_PARAM_START_MARK + QINIU_SLIM_PARAM;
            }
        }
        return url;
    }
}
