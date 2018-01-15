package com.xczh.consumer.market.wxpay.typeutil;

public class LongUtil {
	
	public static long toLong(Long src){
		if(src==null) {
            return 0L;
        }
		return src.longValue();
	}
	
	/**
	 * 判断两个Long是否相等
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static boolean isEq(Long l1,Long l2){
		return toLong(l1)==toLong(l2);
	}
	
	public static Long String2Long(String str){
		try {
			Long l=null==str?null:Long.valueOf(str);
			return l;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
