package com.xczh.consumer.market.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

public class TestArray {

	
	
	//使用List
	public static boolean useList(String[] arr,String targetValue){
	    return Arrays.asList(arr).contains(targetValue);
	}
	//使用Set
	public static boolean useSet(String[] arr,String targetValue){
	    Set<String> set=new HashSet<String>(Arrays.asList(arr));
	    return set.contains(targetValue);
	}
	//使用循环判断
	public static boolean useLoop(String[] arr,String targetValue){
	    for(String s:arr){
	        if(s.indexOf(targetValue)!=-1)
	            return true;
	        }  
	        return false;
	}
	
	//查找有序数组中是否包含某个值的用法
	public static boolean useArraysBinarySearch(String[] arr,String targetValue){
	    int a=Arrays.binarySearch(arr, targetValue);
	    if(a>0)
	        return true;
	    else
	        return false;
	}
	
	//使用ArrayUtils
	public static boolean useArrayUtils(String[] arr,String targetValue){
	    return ArrayUtils.contains(arr,targetValue);
	}
	
}
