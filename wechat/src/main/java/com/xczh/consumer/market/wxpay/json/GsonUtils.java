package com.xczh.consumer.market.wxpay.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

import java.lang.reflect.Type;

public class GsonUtils {
	private static Gson instance = new Gson();
	public static Gson getInstance() {
		return instance;
	}
	
    /**
     * @Title: toJson
     * @param bean
     * @return String 返回类型
     * @throws：
     */
    public static String toJson(Object bean){
        Gson gson=new GsonBuilder()
                .registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
                .setDateFormat("yyyyMMddhhmmss")
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .create();
        return gson.toJson(bean);
    }

    public static String toJson(Object bean,boolean hasLong){
    	GsonBuilder gsonBuilder=new GsonBuilder();
    	if(hasLong){
    		gsonBuilder.registerTypeAdapter(String.class, new LongDeserializer()).setLongSerializationPolicy(LongSerializationPolicy.DEFAULT);
    	}
    	return gsonBuilder.create().toJson(bean);
    }
    
    public static String toJson(Object bean,Type type){
        Gson gson=new GsonBuilder()
                .registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
                .setDateFormat("yyyyMMddhhmmss")
                .create();
        return gson.toJson(bean, type);
    }
    
    /**
     * @Title: fromJson
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param <T>
     * @param json
     * @param type
     * @return T 返回类型
     * @throws：
     */
    public static Object fromJson(String json,Type type){
        Gson gson=new GsonBuilder()
                .registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
                .setDateFormat("yyyyMMddhhmmss")
                .create();
        return gson.fromJson(json, type);
    }
    
    /**
     * @Title: fromJson
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param <T>
     * @param json
     * @param classOfT
     * @return T 返回类型
     * @throws：
     */
    public  static <T>T fromJson(String json,Class<T> classOfT){
        Gson gson=new GsonBuilder()
                .registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
                .setDateFormat("yyyyMMddhhmmss")
                .create();
        return gson.fromJson(json, classOfT);
    }
    
}
