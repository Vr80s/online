package com.xczhihui.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author hejiwei
 */
public class HtmlUtils {

    public static String escapeContent(String content) {
        if (StringUtils.isNotBlank(content)) {
            return content.replaceAll("\'", "&acute;");
        }
        return content;
    }
}
