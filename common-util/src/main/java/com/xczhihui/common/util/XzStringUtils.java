package com.xczhihui.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XzStringUtils {

    //创建数字和中文数字一一对应的映射
    private static HashMap<Integer, String> map;

    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script,
                Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern
                .compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        if (htmlStr != null && !"".equals(htmlStr)
                && !"null".equals(htmlStr)) { //过滤掉空格
            htmlStr = htmlStr.replaceAll("&nbsp;", "");
        }
        return htmlStr.trim(); // 返回文本字符串
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号
     *
     * @param phone 手机号
     * @return
     */
    public static boolean checkPhone(String phone) {
        boolean flag = false;
        try {
            // String check =
            // "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0-9]))\\d{8}$";
            String check = "^(1[345678]\\d{9})$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(phone);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证密码 英文大小写字母数字
     *
     * @param phone 手机号
     * @return
     */
    public static boolean checkPassword(String passWord) {
        boolean flag = false;
        try {
            String check = "^([A-Za-z0-9]{6,18})$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(passWord);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Description：验证呢城
     *
     * @param passWord
     * @return boolean
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    public static boolean checkNickName(String nickName) {
        boolean flag = false;
        try {
            // /^[A-Za-z0-9_\-\u4e00-\u9fa5]+$/;//支持中文、字母、数字、'-'、'_'的组合，4-20个字符
            String check = "^([_A-Za-z0-9-\u4e00-\u9fa5]{1,20})$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(nickName);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    /**
     * 校验微信号
     *
     * @param wechatNo
     * @return
     */
    public static boolean checkWechatNo(String wechatNo) {
        boolean flag = false;
        try {
            // 校验微信号正则
            String judge = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$";
            Pattern pat = Pattern.compile(judge);
            Matcher mat = pat.matcher(wechatNo);
            flag = mat.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    //将href外站的链接换为302跳转
    public static String formatA(String value) {
        if (value == null)
            return null;
        String pattern = "<a([\\w\\W]*?) href=['|\"]([\\w\\W]*?)['|\"]([\\w\\W]*?)>";
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(value);
        while (m.find()) {
            String a = m.group();
            //整段匹配不会出现错误
            String _a = "<a" + " href='javascript:void(0)' >";
            value = value.replace(a, _a);
        }
        return value;
    }


    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " days " + hours + " hours " + minutes + " minutes "
                + seconds + " seconds ";
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * You can't specify target table 'medical_doctor_department' for update in FROM clause
     * 字符串增加  每周  ***** 全天
     *
     * @param str
     * @return
     */
    public static String workTimeScreen(String line) {
        return workTimeScreen(line, true);
    }

    public static String workTimeScreen(String line, Boolean coOrdinate) {
        if (line != null && !"".equals(line)) {
            //中文的话，过滤下
            if (isContainChinese(line)) {
                String regex = ".*(下午|上午|周|点|早|晚|暂无).*";
                Pattern datePattern = Pattern.compile(regex);
                Matcher dateMatcher = datePattern.matcher(line);
                if (!dateMatcher.matches()) {
                    line = "每周" + line + "全天";
                }
                return line;
            } else {
                return workTimeConverter(line);
            }
        }
        return null;
    }


    public static String workTimeConverter(String str) {

        String[] array = str.split(",");
        List<String> day = new ArrayList<String>();
        List<String> morningArr = new ArrayList<String>();
        List<String> affternoonArr = new ArrayList<String>();

        for (int i = 0; i < array.length; i++) {
            String item = array[i];
            Integer index = item.indexOf(".");
            String itemStart = item.substring(0, index);
            String itemEnd = item.substring(index + 1, item.length());

            if (itemEnd.equals("1")) {
                morningArr.add(itemStart);
            }
            if (itemEnd.equals("2")) {
                affternoonArr.add(itemStart);
            }
        }

        for (int i = 0; i < morningArr.size(); i++) {
            for (int j = 0; j < affternoonArr.size(); j++) {
                if (morningArr.get(i).equals(affternoonArr.get(j))) {
                    day.add(morningArr.get(i));
                }
            }
        }
        morningArr.removeAll(day);
        affternoonArr.removeAll(day);

        initMap();
        String morning1 = change(morningArr, 1);
        String affternoon1 = change(affternoonArr, 2);
        String day1 = change(day, 3);

        return (morning1 != "" ? morning1 + "," : morning1) + (affternoon1 != "" ? affternoon1 + "," : affternoon1)
                + day1;
    }


    // 将全是数字的字符串转换成中文数字表示的方法  
    public static String change(List<String> list, Integer type) {
        try {
            if (list == null || list.size() <= 0) {
                return "";
            }
            String str = "";
            for (int i = 0; i < list.size(); i++) {
                str += list.get(i);
            }
            String chinese = "";
            str = String.valueOf(Integer.parseInt(str));
            // 将数字转换成中文数字  
            for (int i = 0; i < str.length(); i++) {
                int index = str.charAt(i) - 48;
                if (i == str.length() - 1) {
                    chinese += map.get(index);
                } else {
                    chinese += map.get(index) + "、";
                }
            }

            if (type.equals(1)) {
                chinese = "每周" + chinese + "上午";
            }
            if (type.equals(2)) {
                chinese = "每周" + chinese + "下午";
            }
            if (type.equals(3)) {
                chinese = "每周" + chinese + "全天";
            }
            return chinese;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // 判断输入的字符串是否全是数字
    public static boolean isNum(String str) {
        boolean flag = true;
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    // 给map赋值，使数字和中文数字意义对应  
    public static void initMap() {
        map = new HashMap<Integer, String>();
        map.put(1, "一");
        map.put(2, "二");
        map.put(3, "三");
        map.put(4, "四");
        map.put(5, "五");
        map.put(6, "六");
        map.put(7, "日");
    }

}
