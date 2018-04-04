package com.xczhihui.bxg.online.common.utils;

/**
 * Description: 身份证号码校验工具类<br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/3/1 0001-下午 3:36<br>
 */
public class IDCard {

    /**
     * 我国公民的身份证号码特点如下
     * 1.长度18位
     * 2.第1-17号只能为数字
     * 3.第18位只能是数字或者x
     * 4.第7-14位表示特有人的年月日信息
     * 请实现身份证号码合法性判断的函数，函数返回值：
     * 1.如果身份证合法返回0
     * 2.如果身份证长度不合法返回1
     * 3.如果第1-17位含有非数字的字符返回2
     * 4.如果第18位不是数字也不是x返回3
     * 5.如果身份证号的出生日期非法返回4
     *
     * @since 0.0.1
     */
    public static boolean validator(String no) {
        // 对身份证号进行长度等简单判断
        if (no == null || no.length() != 18 || !no.matches("\\d{17}[0-9X]")){
            return false;
        }
        // 1-17位相乘因子数组
        int[] factor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        // 18位随机码数组
        char[] random = "10X98765432".toCharArray();
        // 计算1-17位与相应因子乘积之和
        int total = 0;
        for (int i = 0; i < 17; i++){
            total += Character.getNumericValue(no.charAt(i)) * factor[i];
        }
        // 判断随机码是否相等
        return random[total % 11] == no.charAt(17);
    }

}
