package com.xczhihui.bxg.online.web.utils;

import java.math.BigDecimal;

public class CountUtils {

	
	/**  
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指  
	* 定精度，以后的数字四舍五入。  
	* @param v1 被除数  
	* @param v2 除数  
	* @param scale 表示表示需要精确到小数点以后几位。  
	* @return 两个参数的商  
	*/  
	public static BigDecimal div(BigDecimal b1,double v2,int scale){   
		if(scale<0){   
			throw new IllegalArgumentException(   
			"The scale must be a positive integer or zero");   
		}   
		//BigDecimal b1 = new BigDecimal(Double.toString(v1));   
		BigDecimal b2 = new BigDecimal(Double.toString(v2));   
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);   
	}   
	
	/**  
	 * 提供精确的减法运算。  
	 * @param v1 被减数  
	 * @param v2 减数  
	 * @return 两个参数的差  
	*/  
	public static double sub(double v1,double v2){   
		BigDecimal b1 = new BigDecimal(Double.toString(v1));   
		BigDecimal b2 = new BigDecimal(Double.toString(v2));   
		return b1.subtract(b2).doubleValue();   
	} 

	public static void main(String[] args) {
		BigDecimal b1 = new BigDecimal("0.0222");   
		BigDecimal b2 = new BigDecimal("0.0222");  
		System.out.println("你好"+b1.subtract(b2));
	}
	
	
}
