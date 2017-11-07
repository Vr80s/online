package com.xczhihui.bxg.online.common.utils;

import com.google.gson.Gson;

/**
 * JSON工具类
 * @author majian
 * @date 2016-4-18
 */
public class JsonUtil {
    private static final Gson gson=new Gson();

    /**
     * 对象转JSON
     * @param object
     * @return
     */
    public static String toJson(Object object){
        if(object!=null)
           return gson.toJson(object);
        return "{}";
    }
}