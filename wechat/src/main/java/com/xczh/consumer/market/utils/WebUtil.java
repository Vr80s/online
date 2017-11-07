package com.xczh.consumer.market.utils;

public class WebUtil {

    
    /** 
     * Description：判断数字是否为整数
     * @param obj
     * @return
     * @return boolean
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    public static boolean isIntegerForDouble(double obj) {  
        double eps = 1e-10;  // 精度范围  
        return obj-Math.floor(obj) < eps;  
    }
}
