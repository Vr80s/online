package com.xczhihui.bxg.online.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/4/26 0026-上午 11:10<br>
 */
public class Reg {
    public static void main(String[] args) {
        String a="/otherlink/getOtherLink";
        String b="/otherlink/getOth.*";
        String c="/otherlink/*";

        Pattern pattern = Pattern.compile(b);
        Matcher matcher = pattern.matcher(a);
        System.out.println(matcher.matches());

        pattern = Pattern.compile(c);
        matcher = pattern.matcher(a);
        System.out.println(matcher.matches());
    }
}
