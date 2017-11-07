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
    public static void main(String[] args) {

        Map<String, String> m = new HashMap<>();
        m.put("于某人","5");
        m.put("刘某人","5");
        m.put("杨某人","5");

        BigDecimal total = new BigDecimal("15");
        BigDecimal actualTotal = new BigDecimal("12");
        calculate(m,total,actualTotal);
    }

    public static void calculate(Map<String,String> m,BigDecimal total,BigDecimal actualTotal){
        for (Map.Entry<String, String> entry : m.entrySet()) {
//            System.out.println("name = " + entry.getKey() + ", price = " + entry.getValue());
            String personalActualTotal = (new BigDecimal(entry.getValue()).divide(total,2,BigDecimal.ROUND_UP)).multiply(actualTotal).setScale(2,BigDecimal.ROUND_UP).toString();
            System.out.println(entry.getKey()+"外卖金额："+entry.getValue()+"实际支付金额："+personalActualTotal);
        }
    }
}
