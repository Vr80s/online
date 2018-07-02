package com.xczh.consumer.market.wxpay.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

public class GsonUtils {
    private static Gson instance = new Gson();

    public static Gson getInstance() {
        return instance;
    }

    /**
     * @param bean
     * @return String 返回类型
     * @Title: toJson
     * @throws：
     */
    public static String toJson(Object bean) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
                .setDateFormat("yyyyMMddhhmmss")
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .create();
        return gson.toJson(bean);
    }

    public static String toJson(Object bean, boolean hasLong) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (hasLong) {
            gsonBuilder.registerTypeAdapter(String.class, new LongDeserializer()).setLongSerializationPolicy(LongSerializationPolicy.DEFAULT);
        }
        return gsonBuilder.create().toJson(bean);
    }

    public static String toJson(Object bean, Type type) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
                .setDateFormat("yyyyMMddhhmmss")
                .create();
        return gson.toJson(bean, type);
    }

    /**
     * @param <T>
     * @param json
     * @param type
     * @return T 返回类型
     * @Title: fromJson
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @throws：
     */
    public static Object fromJson(String json, Type type) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
                .setDateFormat("yyyyMMddhhmmss")
                .create();
        return gson.fromJson(json, type);
    }

    /**
     * @param <T>
     * @param json
     * @param classOfT
     * @return T 返回类型
     * @Title: fromJson
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @throws：
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
                .setDateFormat("yyyyMMddhhmmss")
                .create();
        return gson.fromJson(json, classOfT);
    }

}
