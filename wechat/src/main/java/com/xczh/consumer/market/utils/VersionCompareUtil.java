package com.xczh.consumer.market.utils;

/**
 * liutao
 */
public class VersionCompareUtil {
    /**
     *  int diff = VersionCompareUtil.compareVersion(最新版本号, 用户的版本号);
     if (diff <= 0) {
     	return 已是最新版本
     }
     Map<String, Object> dataMap = new HashMap<String, Object>();
     dataMap.put("newVersion", newVersion);
     dataMap.put("downloadUrl", downloadUrl);
     * 版本对比
     * @param version1
     * @param version2
     * @return diff >= 0 ? version1 >= version2 : version1 < version2
     */
    public static int compareVersion(String version1, String version2) {

        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
        	
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
    
    public static void main(String[] args) {
		
    	System.out.println(compareVersion("1.1.1","1.1.2"));
	}
}
