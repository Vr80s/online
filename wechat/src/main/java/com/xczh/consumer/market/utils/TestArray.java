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
	
	public static void main(String[] args) {
	    String[] arr=new String[]{"CD","BC","EF","DE","AB","JK"};
	    System.out.println(useLoop(arr, "A"));
	    
	    //use list
//	    long startTime=System.nanoTime();
//	    for(int i=0;i<100000;i++){
//	        useList(arr, "A");
//	    }
//	    long endTime=System.nanoTime();
//	    long duration=endTime-startTime;
//	    System.out.println("useList:"+duration/1000000);
	    //use set
//        long startTime2=System.nanoTime();
//        for(int i=0;i<100000;i++){
//            useSet(arr, "A");
//        }
//        long endTime2=System.nanoTime();
//        long duration2=endTime2-startTime2;
//        System.out.println("useSet:"+duration2/1000000);
//        //use loop
//        long startTime3=System.nanoTime();
//        for(int i=0;i<100000;i++){
//            useLoop(arr, "A");
//        }
//        long endTime3=System.nanoTime();
//        long duration3=endTime3-startTime3;
//        System.out.println("useLoop:"+duration3/1000000);
//        //use Arrays.binarySearch()
//        long startTime4=System.nanoTime();
//        for(int i=0;i<100000;i++){
//            useArraysBinarySearch(arr, "A");
//        }
//        long endTime4=System.nanoTime();
//        long duration4=endTime4-startTime4;
//        System.out.println("useArraysBinarySearch:"+duration4/1000000);
	}
}
