package com.xczhihui.bxg.online.web.utils;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/3/27 0027-下午 2:13<br>
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {

    private static final Pattern SCRIPT_PATTERN = Pattern.compile("<script[^>]*?>[\\s\\S]*?<\\/script>", Pattern.CASE_INSENSITIVE);
    private static final Pattern STYLE_PATTERN = Pattern.compile("<style[^>]*?>[\\s\\S]*?<\\/style>", Pattern.CASE_INSENSITIVE);
    private static final Pattern HTML_PATTERN = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
    private static final Pattern SPACE_PATTERN = Pattern.compile("\\s*|\t|\r|\n", Pattern.CASE_INSENSITIVE);

    /**
     * Description：清理html标签
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/3/27 0027 下午 2:14
     **/
    public static String delHTMLTag(String htmlStr) {
        Matcher scriptMatch = SCRIPT_PATTERN.matcher(htmlStr);
        // 过滤script标签
        htmlStr = scriptMatch.replaceAll("");

        Matcher styleMatch = STYLE_PATTERN.matcher(htmlStr);
        // 过滤style标签
        htmlStr = styleMatch.replaceAll("");

        Matcher htmlMatch = HTML_PATTERN.matcher(htmlStr);
        // 过滤html标签
        htmlStr = htmlMatch.replaceAll("");

        Matcher spaceMatch = SPACE_PATTERN.matcher(htmlStr);
        // 过滤空格回车标签
        htmlStr = spaceMatch.replaceAll("");
        return htmlStr.trim();
    }

    public static String getTextFromHtml(String htmlStr) {
        if (htmlStr == null) {
            return "";
        }
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        htmlStr = htmlStr.replaceAll("　", "");
        return htmlStr;
    }
}
