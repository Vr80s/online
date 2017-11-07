package com.xczh.consumer.market.wxpay.util;

import java.util.Map;
import java.util.Set;

/**
 * 参数验证
 * @author zhangshixiong
 * @date 2017/3/30
 */
public class ParamsVerifyUtils {
	/**
	 * 参数验证
	 * @param map
	 * @return 所有为空的字段
	 */
	public static String paramsVerify(Map<String,String> map){
		Set<String> keySet = map.keySet();
		String allKey = "";
		for (String key : keySet) {
			boolean contains = key.contains("ck_");
			if(contains){
				String value = map.get(key);
				if(checkIsNull(value)){
					allKey += key + ",";
				};
			}
		}
		if(allKey.length() > 0){
			allKey = allKey.substring(0, allKey.length()-1);
		}
		return allKey;
	}
	/**
	 * 做空判断
	 * @param value
	 * @return
	 */
	private static boolean checkIsNull(String value){
		if(null == value || "null".equals(value) || "".equals(value) || value.length() == 0 || "undefined".equals(value)){
			return true;
		}
		return false;
	}
}
