package com.xczhihui.common.util;

import java.lang.reflect.Method;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bean工具。
 * 
 * @author liyong
 *
 */
public class BeanUtil {

	protected static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	/**
	 * 以可读的形式，输出bean的属性和值。
	 * 
	 * @param bean
	 * @return
	 */
	public static String beanToString(Object bean) {
		Method[] ms = bean.getClass().getMethods();
		StringBuilder sb = new StringBuilder();
		sb.append(bean.getClass().getName() + ":{\n");
		for (Method m : ms) {
			String name = m.getName();
			if (m.getParameterTypes().length > 0) {
				continue;
			}
			if (name.startsWith("get") && !"getClass".equals(name)) {
				try {
					Object ret = m.invoke(bean);
					String prop = name.substring(3, name.length());
					String val = "null";
					if (ret != null) {
						val = ret.toString();
					}
					sb.append("  " + prop + ":" + val + "\n");
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
		sb.append("}\n");
		return sb.toString();
	}

	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
