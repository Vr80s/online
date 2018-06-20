package com.wetcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/6/20 0020-上午 11:25<br>
 */
public class TestRegx {

    public static void main(String[] args) {
        System.out.println(openUris("/"));
        System.out.println(openUris("/bbs/2"));
        System.out.println(openUris("/bbs?a=1"));
        System.out.println(openUris("/headline/3.css"));
    }
    static boolean openUris(String uri) {
//            String openuri = "/headline/1";
            Pattern pattern = Pattern.compile("/bbs[?].*?");
            Matcher matcher = pattern.matcher(uri);
            if (matcher.matches()) {
                return true;
            }
        return false;
    }
}
