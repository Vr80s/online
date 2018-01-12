package com.xczh.consumer.market.wxpay.typeutil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DoubleUtils {
	
    public static Double getDouble(Double n){
        if (n != null) {
            BigDecimal b = new BigDecimal(n);
            return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return n;
    }

    public static String formatDouble(Object n){
        if (n != null) {
            DecimalFormat df=new DecimalFormat("#.00");
            String str=df.format(n);
            if(str.indexOf(".")==0){
            	str="0"+str;
            }
            return str;
        }
        return null;
    }

    public static String double2String(Double n){
        return formatDouble(getDouble(n));
    }
    
    /**
     * getDouble
     * @param str
     * @return
     */
    public static double getDouble(String str){
    	if(StringUtil.isEmpty(str)) {
            return 0d;
        }
    	return Double.parseDouble(str);
    }
    
    /**
     * getDouble
     * @param str
     * @return
     */
    public static double getDouble(Object obj){
    	return getDouble(StringUtil.getString(obj));
    }
    
    /**
     * 金额取整
     * @param doublePrice
     * @return
     */
	public static String filterDecimal(Double doublePrice) {
		if(doublePrice!=null){
			Integer intN=doublePrice.intValue();
			if(doublePrice>intN){
				return doublePrice.toString();
			}else{
				return intN.toString();
			}
		}
		
		return null;
	}
	
}
