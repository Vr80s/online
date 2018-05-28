package com.xczhihui.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ReflectionUtils {

	public static Object invokeGetterMethod(Object obj, Field field) {
		String getterMethodName = "";
		if (field.getType().getName().equals((boolean.class).getName())
				|| field.getType().getName().equals((Boolean.class).getName())) {
			getterMethodName = "is" + StringUtils.capitalize(field.getName());
		} else {
			getterMethodName = "get" + StringUtils.capitalize(field.getName());
		}
		return invokeMethod(obj, getterMethodName, new Class[] {},
				new Object[] {});
	}

	/**
	 * 调用Getter方法.
	 */
	public static Object invokeGetterMethod(Object obj, String propertyName) {
		String getterMethodName = "";
		Field field = getAccessibleField(obj, propertyName);
		try {
			if (field.getType().getName().equals((boolean.class).getName())
					|| field.getType().getName()
							.equals((Boolean.class).getName())) {
				if (!"is".equals(propertyName.substring(0, 2))) {
					getterMethodName = "is"
							+ StringUtils.capitalize(propertyName);
				} else {
					getterMethodName = "get"
							+ StringUtils.capitalize(propertyName);
				}

			} else {
				getterMethodName = "get" + StringUtils.capitalize(propertyName);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return invokeMethod(obj, getterMethodName, new Class[] {},
				new Object[] {});
	}

	/**
	 * 调用Setter方法.使用value的Class来查找Setter方法.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName,
			Object value) {
		invokeSetterMethod(obj, propertyName, value, null);
	}

	/**
	 * @content 得到父类和子类的全部属性
	 * @param obj
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public static List<Field> getFieldAndSuperField(Object obj) {
		Class _class = obj.getClass();
		Class _subClass = _class.getSuperclass();
		Field[] subfields = _subClass.getDeclaredFields();
		Field[] fields = _class.getDeclaredFields();
		List<Field> list = new ArrayList<Field>();
		for (Field field : subfields) {
			list.add(field);
		}
		for (Field field : fields) {
			list.add(field);
		}
		return list;

	}

	@SuppressWarnings("rawtypes")
	public static List<Method> getMethodAndSuperMethod(Object obj) {
		Class _class = obj.getClass();
		Class _subClass = _class.getSuperclass();
		Method[] submethods = _subClass.getDeclaredMethods();
		Method[] methods = _class.getDeclaredMethods();
		List<Method> list = new ArrayList<Method>();
		for (Method method : submethods) {
			list.add(method);
		}
		for (Method method : methods) {
			list.add(method);
		}
		return list;

	}

	/**
	 * 调用Setter方法.
	 * 
	 * @param propertyType
	 *            用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName,
			Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(obj, setterMethodName, new Class[] { type },
				new Object[] { value });
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName,
			final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj,
			final String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	@SuppressWarnings("rawtypes")
	public static Field getAllField(final Class obj, final String fieldName) {
		for (Class<?> superClass = obj; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * @category 查看是否为parent的子类
	 * @param obj
	 *            需要查看的类
	 * @param parent
	 *            父类
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isInherit(final Class obj, final Class parent,
			boolean isCheckInterface) {
		if (obj == parent) {
			return true;
		}
		for (Class<?> superClass = obj; superClass != null
				&& superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			if (superClass == parent) {
				return true;
			}
		}
		if (isCheckInterface) {
			return isInterface(obj, parent);

		}
		return false;
	}

	private static boolean isInterface(final Class<?> obj, final Class<?> parent) {
		Class<?>[] interfaces = obj.getInterfaces();
		if (interfaces == null || interfaces.length == 0) {
			return false;
		}
		for (Class<?> class1 : interfaces) {
			if (class1 == parent) {
				return true;
			}
			if (isInterface(class1, parent)) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符. 用于一次性调用的情况.
	 */
	public static Object invokeMethod(final Object obj,
			final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method ["
					+ methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
	 * 
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object...
	 * args)
	 */
	public static Method getAccessibleMethod(final Object obj,
			final String methodName, final Class<?>... parameterTypes) {

		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName,
						parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {// NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. eg. public UserDao
	 * extends HibernateDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(final Class clazz,
			final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {

			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {

			return Object.class;
		}
		if (!(params[index] instanceof Class)) {

			return Object.class;
		}

		return (Class) params[index];
	}

	@SuppressWarnings("rawtypes")
	public static Class getInterFaceClassGenricType(final Class clazz,
			final int index) {
		Type[] types = clazz.getGenericInterfaces();
		for (Type genType : types) {
			if (!(genType instanceof ParameterizedType)) {

				return Object.class;
			}

			Type[] params = ((ParameterizedType) genType)
					.getActualTypeArguments();

			if (index >= params.length || index < 0) {

				continue;
			}
			if (!(params[index] instanceof Class)) {

				continue;
			}
			return (Class) params[index];
		}
		return Object.class;
	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(
			Exception e) {
		if (e instanceof IllegalAccessException
				|| e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.",
					((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
}
