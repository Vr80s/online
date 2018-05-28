package com.xczhihui.common.util;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    public static Gson getBaseGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public static String mapToString(Map<String, String> map) {
        return new Gson().toJson(map);
    }

}
