package com.xczhihui.bxg.online.common.utils;

/**
 * ClassName: UserCoin.java <br>
 * Description:用户-代币余额表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 8:23 2018/1/30 0030<br>
 */
public class OrderNoUtil {

    /**
     * Description：
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 8:26 2018/1/30 0030
     **/
    public static String getEnchashmentOrderNo(){
        return TimeUtil.getSystemTime() + "TX" +RandomUtil.getCharAndNumr(12);
    }
}
