package com.xczhihui.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

/**
 * @author hejiwei
 */
public class ShortUrlUtil {

    private static final String SHORT_URL_API = "http://api.ft12.com/api.php?url={0}";

    public static String getShortUrl(String longUrl) {
        try {
            return HttpUtil.sendGetRequest(MessageFormat.format(SHORT_URL_API, URLEncoder.encode(longUrl, "utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
