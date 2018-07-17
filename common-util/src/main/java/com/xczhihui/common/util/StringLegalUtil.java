package com.xczhihui.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringLegalUtil {
    /**
     * Description：验证手机号码11位数，中间四位加*
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/7/17 15:50
     * 此方法中前三位格式有：13+任意数,15+除4的任意数,18+除1和4的任意数,17+除9的任意数,147,16+任意数
     **/
    public static String isPhoneLegal(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147)|(16[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        String str2;
        if(m.matches()){
            str2 = str.substring(0,3)+"****"+str.substring(7);
        } else {
            str2 =str;
        }
        return str2;
    }

}
