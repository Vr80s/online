package com.xczhihui.bxg.common.support.service;

import java.util.ArrayList;
import java.util.List;

import com.xczhihui.bxg.common.support.domain.SystemVariate;

/**
 * 转换系统变量的值。
 * 
 * @author liyong
 */
public final class SystemVariateHelper {

	/**
	 * 将系统变量转成Integer
	 * 
	 * @param vars
	 * @return
	 */
	public static List<Integer> transformIntList(List<SystemVariate> vars) {
		List<Integer> ints = new ArrayList<>();
		for (SystemVariate var : vars) {
			ints.add(var.getValueAsInt());
		}
		return ints;
	}

	/**
	 * 将系统变量转成String
	 * 
	 * @param vars
	 * @return
	 */
	public static List<String> transformStringList(List<SystemVariate> vars) {
		List<String> ints = new ArrayList<>();
		for (SystemVariate var : vars) {
			ints.add(var.getValue());
		}
		return ints;
	}
}
