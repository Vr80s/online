package com.xczh.consumer.market.wxpay.typeutil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;

/**
 * @ClassName:  PrecisionUtil      
 */
public  class PrecisionUtil {

	private PrecisionUtil() {
		
	}

	/**
	 * 使用BigDecimal，保留小数点后两位
	 */
	public static String format1(double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.toString();
	}

	/**
	 * 使用DecimalFormat,保留小数点后两位
	 */
	public static String format2(double value) {

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(value);
	}

	/**
	 * 使用NumberFormat,保留小数点后两位
	 */
	public static String format3(double value) {

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		/*
		 * setMinimumFractionDigits设置成2
		 * 
		 * 如果不这么做，那么当value的值是100.00的时候返回100
		 * 
		 * 而不是100.00
		 */
		nf.setMinimumFractionDigits(2);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		/*
		 * 如果想输出的格式用逗号隔开，可以设置成true
		 */
		nf.setGroupingUsed(false);
		return nf.format(value);
	}

	/**
	 * 使用java.util.Formatter,保留小数点后两位
	 */
	public static String format4(double value) {
		/*
		 * %.2f % 表示 小数点前任意位数 2 表示两位小数 格式后的结果为 f 表示浮点型
		 */
		return new Formatter().format("%.2f", value).toString();
	}

	/**
	 * 使用String.format来实现。
	 * 
	 * 这个方法其实和format4是一样的
	 */
	public static String format5(double value) {

		return String.format("%.2f", value).toString();
	}
}
