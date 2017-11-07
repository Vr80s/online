package com.xczh.consumer.market.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Map;

/**
 * 请求控制相关工具类
 * 
 * @author Alex Wang
 */
public class ControllerUtil {
	/**
	 * 统一返回json
	 * 
	 * @param o
	 * @param response
	 */
	public static void writeResponseObject(Object o, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		Gson g = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		try {
			response.getWriter().write(g.toJson(o));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 页面跳转
	 * 
	 * @param location
	 * @param req
	 * @param res
	 */
	public static void forward(String location, HttpServletRequest req, HttpServletResponse res) {
		try {
			req.getRequestDispatcher(location).forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 页面跳转
	 * 
	 * @param location
	 * @param res
	 */
	public static void redirect(String location, HttpServletResponse res) {
		try {
			res.sendRedirect(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将map里面的值设置到bean的属性里面
	 * @param params
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static  <T> T params2bean(Map<String, String> params,Class<T> clazz) throws Exception {
		T obj = clazz.newInstance();
		if (!params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String name = entry.getKey();
				String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
				Method[] ms = clazz.getMethods();
				for (Method method : ms) {
					if (method.getName().equals(setMethodName)) {
						Class<?> c = method.getParameterTypes()[0];
						ControllerUtil.setParam(c, setMethodName, entry.getValue(), obj);
						break;
					}
				}
			}
		}
		return obj;
	}

	public static void setParam(Class<?> paramType, String methodName, Object value, Object obj)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ParseException {

		Method m = obj.getClass().getMethod(methodName, paramType);

		String type = paramType.getName();
		Object valueObj = null;

		if ("java.lang.Integer".equals(type) || "int".equals(type)) {
			if (value != null) {
				valueObj = Integer.valueOf(value.toString());
			} else {
				valueObj = 0;
			}
		} else if ("java.lang.String".equals(type)) {
			valueObj = value;
		} else if ("java.lang.Double".equals(type) || "double".equals(type)) {
			if (value != null) {
				valueObj = Double.valueOf(value.toString());
			} else {
				valueObj = 0.00;
			}
		} else if ("java.lang.Float".equals(type) || "float".equals(type)) {
			if (value != null) {
				valueObj = Float.valueOf(value.toString());
			} else {
				valueObj = 0.00;
			}
		} else if ("java.lang.Long".equals(type) || "long".equals(type)) {
			if (value != null) {
				valueObj = Long.valueOf(value.toString());
			} else {
				valueObj = 0;
			}
		} else if ("java.lang.Boolean".equals(type) || "boolean".equals(type)) {
			if (value != null) {
				valueObj = Boolean.valueOf(value.toString());
			} else {
				valueObj = false;
			}
		} else if ("java.util.Date".equals(type)) {
			if (value != null) {
				int length = value.toString().trim().length();
				if (length == 10) {
					value = DateUtil.parseDate(value.toString().trim(), "yyyy-MM-dd");
				} else if (length == 19){
					value = DateUtil.parseDate(value.toString().trim(), "yyyy-MM-dd HH:mm:ss");
				} else {
					valueObj = value;
				}
			}
		} else if ("java.util.Short".equals(type) || "short".equals(type)) {
			if (value != null) {
				valueObj = Short.valueOf(value.toString());
			}
		} else if ("java.lang.Character".equals(type) || "char".equals(type)) {
			if (value != null) {
				valueObj = (Character)value;
			}
		}
		m.invoke(obj, valueObj);
	}
}
