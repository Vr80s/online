package com.xczh.consumer.market.wxpay.json;

import net.sf.json.util.PropertyFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 忽略JAVABEAN的指定属性、是否忽略集合类属性
 */
public class IgnoreFieldProcessorImpl implements PropertyFilter {

	Log log = LogFactory.getLog(this.getClass());

	/**
	 * 忽略的属性名称
	 */
	private String[] fields;

	/**
	 * 是否忽略集合
	 */
	private boolean ignoreColl = false;

	/**
	 * 空参构造方法
	 * 
	 * 默认不忽略集合
	 */
	public IgnoreFieldProcessorImpl() {
		// empty
	}

	/**
	 * 构造方法
	 * 
	 * @param fields
	 *            忽略属性名称数组
	 */
	public IgnoreFieldProcessorImpl(String[] fields) {
		this.fields = fields;
	}

	/**
	 * 构造方法
	 * 
	 * @param ignoreColl
	 *            是否忽略集合
	 * @param fields
	 *            忽略属性名称数组
	 */
	public IgnoreFieldProcessorImpl(boolean ignoreColl, String[] fields) {
		this.fields = fields;
		this.ignoreColl = ignoreColl;
	}

	/**
	 * 构造方法
	 * 
	 * @param ignoreColl
	 *            是否忽略集合
	 */
	public IgnoreFieldProcessorImpl(boolean ignoreColl) {
		this.ignoreColl = ignoreColl;
	}

	@Override
    public boolean apply(Object source, String name, Object value) {
		Field declaredField = null;
		try {
			if (value!=null && source.getClass() == value.getClass()) {
				declaredField = source.getClass().getDeclaredField(name);
			}
		} catch (NoSuchFieldException e) {
			log.equals("没有找到属性" + name);
			e.printStackTrace();
		}

		// 忽略集合
		if (declaredField != null) {
			if (ignoreColl) {
				if (Collection.class.isAssignableFrom(declaredField.getType())) {
					return true;
				}
			}
		}

		// 忽略设定的属性
		if (fields != null && fields.length > 0) {
			if (contains(fields, name)) {
				return true;
			}
		}
		return false;
	}

	public String[] getFields() {
		return fields;
	}

	/**
	 * 设置忽略的属性
	 * 
	 * @param fields
	 */
	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public boolean isIgnoreColl() {
		return ignoreColl;
	}

	/**
	 * 设置是否忽略集合类
	 * 
	 * @param ignoreColl
	 */
	public void setIgnoreColl(boolean ignoreColl) {
		this.ignoreColl = ignoreColl;
	}

	/**
	 * 判断某个字符串是否存在于数组中
	 * 
	 * @param stringArray
	 *            原数组
	 * @param source
	 *            查找的字符串
	 * @return 是否找到
	 */
	public static boolean contains(String[] stringArray, String source) {
		// 转换为list
		List<String> tempList = Arrays.asList(stringArray);

		// 利用list的包含方法,进行判断
		if (tempList.contains(source)) {
			return true;
		} else {
			return false;
		}
	}
}
