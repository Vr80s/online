package com.xczhihui.bxg.online.web.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Description：外卖结算
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 上午 11:16 2017/10/11 0011
 **/
public class Takeaway {

    public static void calculate(Map<String,String> m,BigDecimal total,BigDecimal actualTotal){
        for (Map.Entry<String, String> entry : m.entrySet()) {
            String personalActualTotal = (new BigDecimal(entry.getValue()).divide(total,2,BigDecimal.ROUND_UP)).multiply(actualTotal).setScale(2,BigDecimal.ROUND_UP).toString();
        }
    }
}
