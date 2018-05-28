package com.xczhihui.bxg.online.web.base.utils;


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * mysql工具类
 * @author majian
 * @date 2016-5-17
 */
public class MysqlUtils {

    /**
     * 转移字符集合.
     */
    private static List<String> escs=new ArrayList<String>();

    static{
        escs.add("\\");
        escs.add("_");
        escs.add("%");
        escs.add("'");
        escs.add("[");
        escs.add("^");
        escs.add("\"");
    }

    /**
     * 替换专业字符,搜索值，针对特殊字符处理
     * @param str
     * @return
     */
    public static String replaceESC(String str){
        if(StringUtils.isNotEmpty(str)){
            if(escs.size()>0) {
                for(String esc:escs) {
                    if(str.indexOf(esc)!=-1&&str.indexOf("\\" + esc)==-1) {
                        str=str.replace(esc, "\\" + esc);
                    }
                }
            }
        }
        return str;
    }
}
