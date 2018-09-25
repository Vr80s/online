/**  
* <p>Title: ReflectASM.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年9月17日 
*/  
package com.xczh.test.beanwitch;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.xczh.test.vo.Group;
import com.xczh.test.vo.User;
import com.xczh.test.vo.UserVo;

/**
* @ClassName: ReflectASM
* @Description: TODO(这里用一句话描述这个类的作用)
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年9月17日
*
*/

public class ReflectASMDemo {

	
	private static Map<Class, MethodAccess> methodMap = new HashMap<Class, MethodAccess>();
	 
	private static Map<String, Integer> methodIndexMap = new HashMap<String, Integer>();
 
	private static Map<Class, List<String>> fieldMap = new HashMap<Class, List<String>>();
 
	static String[] types = {"java.lang.Integer",
	        "java.lang.Double",
	        "java.lang.Float",
	        "java.lang.Long",
	        "java.lang.Short",
	        "java.lang.Byte",
	        "java.lang.Boolean",
	        "java.lang.Character",
	        "java.lang.String",
	        "java.util.Date",
	        "java.math.BigDecimal",
	        "int","double","long","short","byte","boolean","char","float"};

	
	/**
	 * 
	 * <p>Title: copyProperties</p>  
	 * <p>Description: </p>  
	 * @param desc  vo对象（空对象）
	 * @param orgi  映射就像（数据对象） 
	 */
	public static void copyProperties(Object desc, Object orgi) {
		MethodAccess descMethodAccess = methodMap.get(desc.getClass());
		if (descMethodAccess == null) {
			descMethodAccess = cache(desc);
		}
		MethodAccess orgiMethodAccess = methodMap.get(orgi.getClass());
		if (orgiMethodAccess == null) {
			orgiMethodAccess = cache(orgi);
		}
 
		List<String> fieldList = fieldMap.get(orgi.getClass());
		for (String field : fieldList) {
			String getKey = orgi.getClass().getName() + "." + "get" + field;
			String setkey = desc.getClass().getName() + "." + "set" + field;
			Integer setIndex = methodIndexMap.get(setkey);
			if (setIndex != null) {
				int getIndex = methodIndexMap.get(getKey);
				// 参数一需要反射的对象
				// 参数二class.getDeclaredMethods 对应方法的index
				// 参数对三象集合
				descMethodAccess.invoke(desc, setIndex.intValue(),
						orgiMethodAccess.invoke(orgi, getIndex));
			}
		}
	}
 
	// 单例模式
	private static MethodAccess cache(Object orgi) {
		synchronized (orgi.getClass()) {
			MethodAccess methodAccess = MethodAccess.get(orgi.getClass());
			Field[] fields = orgi.getClass().getDeclaredFields();
			List<String> fieldList = new ArrayList<String>(fields.length);
			
			/**
			 * 合并数组
			 */
			Field[] fields1 = orgi.getClass().getSuperclass().getDeclaredFields();
			
			Field[] fields2 = new Field[fields.length + fields1.length];
			System.arraycopy(fields, 0, fields2, 0, fields.length);
			System.arraycopy(fields1, 0, fields2, fields.length, fields1.length);
			
			for (Field field : fields2) {
				
//			    String str =  field.getName();
//				if(str.equals("type")) {
//					System.out.println(str + "========"+ field.isEnumConstant()+" ----- "+ field.getAnnotatedType().getType().getTypeName());
//					if(str instanceof String) {
//						System.out.println("123");	
//					}	
//					//判断是否是枚举
//					if(field.getType().isEnum()) {
//						System.out.println("456");	
//					}
//					Annotation [] aa = field.getDeclaredAnnotations();
//					for (Annotation annotation : aa) {
//						System.out.println(annotation.annotationType().getName());
//					}
//				}
				
				if (Modifier.isPrivate(field.getModifiers())        // 是否是私有的
						&& !Modifier.isStatic(field.getModifiers()) // 是否是静态的
						&& !field.getType().isEnum()				// 是否枚举  
							) { 		// 确定指定 类对象表示一个基本类型 ，&& field.getType().isPrimitive()
					
					for (String str : types) {
						if(field.getType().getName().equals(str)) {
							// 非公共私有变量
							String fieldName = StringUtils.capitalize(field.getName()); // 获取属性名称
							int getIndex = methodAccess.getIndex("get" + fieldName); // 获取get方法的下标
							int setIndex = methodAccess.getIndex("set" + fieldName); // 获取set方法的下标
							methodIndexMap.put(orgi.getClass().getName() + "." + "get"
									+ fieldName, getIndex); // 将类名get方法名，方法下标注册到map中
							methodIndexMap.put(orgi.getClass().getName() + "." + "set"
									+ fieldName, setIndex); // 将类名set方法名，方法下标注册到map中
							fieldList.add(fieldName); // 将属性名称放入集合里
						}
					}
				}
			}
			fieldMap.put(orgi.getClass(), fieldList); // 将类名，属性名称注册到map中
			methodMap.put(orgi.getClass(), methodAccess);
			return methodAccess;
		}
	}

	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {  

        
		User  user = new User();  
        user.setName("轩哥哥");
        user.setId(1);
        
        user.setAge(18);
        user.setBirthDay(new Date());
        user.setSex(false);
        
        Group group = new Group();
        group.setId(2);
        group.setName("心承智慧-研发");
        user.setGroup(group);
        
        UserVo userv = new UserVo(); 
        ReflectASMDemo.copyProperties(userv, user);   
        
        //BeanUtils.copyProperties(userv, user);
        System.out.println("user tostring:" + user.toString());
        System.out.println("user v tostring :" + userv.toString());
        System.out.println(userv instanceof Object);
        //System.out.println(userv instanceof String);
//        System.out.println("========= 啦啦啦啦啦啦   ==============");
//        Product pro = new Product();
//        ProductVO proVO = new ProductVO();
//        ReflectASMDemo.copyProperties(proVO, pro);   
//        System.out.println("========= HEHEHEHE   ==============");
        
	}  
	
}
