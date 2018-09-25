///**  
//* <p>Title: Test.java</p>  
//* <p>Description: </p>  
//* @author yangxuan@ixincheng.com  
//* @date 2018年9月14日 
//*/  
//package com.xczh.test.jsonswitch;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.xczh.test.vo.*;
//
///**
//* @ClassName: Test
//* @Description: TODO(这里用一句话描述这个类的作用)
//* @author yangxuan
//* @email yangxuan@ixincheng.com
//* @date 2018年9月14日
//*
//*/
//
//public class Test {
//
//	@org.junit.Test
//	public void test() {
//		
//		
//        List<Admin> list = new ArrayList<>();  
//        Admin admin = new Admin(); 
//        admin.setId(1);
//        admin.setName("haha"); 
//        list.add(admin);  
//        list.add(admin);
//        String s = JSON.toJSONString(list);
//        System.out.println(s);
//		
//	}
//	
//	
//	 /**
//     * 循环引用
//     */
//	@org.junit.Test
//    public void RecyleRef(){
//        Group group = new Group();  
//        group.setId(1);  
//        group.setName("group");  
//
//        User user1 = new User();  
//        user1.setId(2);  
//        user1.setName("user1");  
//        user1.setGroup(group);
//        User user2 = new User();  
//        user2.setId(3);  
//        user2.setName("user2");  
//        user2.setGroup(group);
//
//        group.getList().add(user1);  
//        group.getList().add(user2); 
//        JSON.toJSONString(group, SerializerFeature.DisableCircularReferenceDetect); 
//        //java.lang.StackOverflowError
//    }
//	
//	
//	@org.junit.Test
//    public void methodAccess(){
//
//		
//		
//    }
//	
//
//}
