package com.xczhihui.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xczhihui.bxg.common.util.bean.Page;

/**
 * 用于将po对象中的字符串类型的属性值从Null值设置成长度为0的字符串
 * 如某一个用户的name为null，则调用convert方法后此对用户的name为"";
 * 
 * @author yuanziyang
 * @date 2016年4月6日 下午4:53:11
 */
public class ConvertNullToEmptyStringUtil {

	protected static Logger logger = LoggerFactory
			.getLogger(ConvertNullToEmptyStringUtil.class);

	/**
	 * 将一页的所有对象中属性值为Null的字符串属性设置为""
	 * 
	 * @param page
	 * @return
	 * @author yuanziyang
	 */
	public static <T> Page<T> convert(Page<T> page) {
		if (page.getItems() != null && page.getItems().size() > 0) {
			Class<? extends Object> tClass = page.getItems().get(0).getClass();
			Field[] fileds = tClass.getDeclaredFields();
			ArrayList<Field> setValueFields = new ArrayList<Field>();
			for (Field field : fileds) {
				if (field.getType().isAssignableFrom(String.class)) {
					setValueFields.add(field);
				}
			}
			if (setValueFields.size() == 0) {
				return page;
			}
			for (T item : page.getItems()) {
				for (Field field : setValueFields) {
					String methodName = field.getName().substring(0, 1)
							.toUpperCase()
							+ field.getName().substring(1);
					try {
						Object fieldValue = tClass
								.getMethod("get" + methodName).invoke(item);
						if (fieldValue == null) {
							tClass.getMethod("set" + methodName, String.class)
									.invoke(item, "");
						}
					} catch (SecurityException | IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
		return page;
	}

}
