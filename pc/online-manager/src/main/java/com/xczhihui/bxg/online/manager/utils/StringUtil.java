package com.xczhihui.bxg.online.manager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static boolean isNull(String str) {
	    boolean isTrue = false;
		if (str == null || "".equals(str)) {
			isTrue = true;
			throw new NullPointerException("请检查输入是否为空!");
		}
		return isTrue;
	}
	
	@SuppressWarnings("rawtypes")
	public static String ForIn(List ids){
		String str = "(";
		if(ids==null||ids.size()==0){
			return "";
		}else{
			for (Object object : ids) {
				str+="'"+object.toString()+"',";
			}
		}
		str=str.substring(0, str.length()-1);
		str+=")";
		return str;
	}
	
	/**
	 * @category 去空格
	 * @param str 字符串
	 * @return
	 */
	public static String trim(String str){
		if(str==null||str.equals("")){
			return "";
		}else{
			return str.replaceAll(" ", "");
		}
	}

	/**
	 * 为空返回true 非空返回 false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkNull(String str) {
		boolean isTrue = false;
		if (null == str || "".equals(str.trim())) {
            isTrue = true;
        } else {
            isTrue = false;
        }
		return isTrue;
	}

	public static boolean isNull(Object... args) {
		boolean isTrue = false;
		try {
			if (args == null || args.length == 0) {
				isTrue = true;
				throw new NullPointerException("请检查输入是否为空!");
			}
			for (Object obj : args) {
				isNull(convertToString(obj));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isTrue;
	}

	public static String convertToString(Object obj) {

		try {
			if (obj == null || "".equals(obj)) {
				throw new NullPointerException("请检查输入是否为空!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(obj);
	}

	@SuppressWarnings("null")
	public static String[] convertToStrings(Object... args) {
		boolean isTrue = false;
		String[] str = null;
		try {
			if (args == null || args.length == 0) {
				isTrue = true;
				throw new NullPointerException("请检查输入是否为空!");
			}
			for (int i = 0; i < args.length; i++) {
				String strtemp = convertToString(args[i]);
				isNull(strtemp);
				str[i] = convertToString(args[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 整数转换编号 - 长度不够   添零 -暂定长度为6位
	 * @param Integer i 传入整数
	 */
	public static String formatToCode(Integer i){
		return String.format("%06d", i); //remark_lizeyuan_20111020
	}
	
	public static boolean isNotNull(Object... args) {
		boolean isNotNull = false;
		try {
			if (args == null || args.length == 0) {
				throw new NullPointerException("请检查输入是否为空!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		isNotNull = true;
		return isNotNull;
	}

	/**
	 * 
	 * 数组转字符串，返回用逗号分隔的字符串
	 * @param strArr 
	 * @return
	 */
	public static String arrToString(String[] strArr) {
		return arrToString(strArr,",");
    }

	/**
	 * 
	 * 数组转字符串
	 * @param strArr 数组
	 * @param spacer 分隔符
	 * @return
	 */
	public static String arrToString(String[] strArr,String spacerStr) {
		if(strArr == null || strArr.length == 0){//为空或数组为0 返回空字符串
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0;i < strArr.length;i++) {
			if(i == strArr.length - 1){
				sb.append(strArr[i]);
			}else{
				sb.append(strArr[i]);
				sb.append(spacerStr);
			}
		}  
		return sb.toString();  
	}

	public static java.lang.Long format2Long(String str) {
		if (isNull(str)) {
			return null;
		} else {
			return Long.valueOf(str);
		}
	}


	/**
	 * Split the given String into tokens.
	 * <P>
	 * This method is meant to be similar to the split function in other
	 * programming languages but it does not use regular expressions. Rather the
	 * String is split on a single String literal.
	 * <P>
	 * Unlike java.util.StringTokenizer which accepts multiple character tokens
	 * as delimiters, the delimiter here is a single String literal.
	 * <P>
	 * Each null token is returned as an empty String. Delimiters are never
	 * returned as tokens.
	 * <P>
	 * If there is no delimiter because it is either empty or null, the only
	 * element in the result is the original String.
	 * <P>
	 * StringHelper.split("1-2-3", "-");<br>
	 * result: {"1","2","3"}<br>
	 * StringHelper.split("-1--2-", "-");<br>
	 * result: {"","1","","2",""}<br>
	 * StringHelper.split("123", "");<br>
	 * result: {"123"}<br>
	 * StringHelper.split("1-2---3----4", "--");<br>
	 * result: {"1-2","-3","","4"}<br>
	 * 
	 * @param s
	 *            String to be split.
	 * @param delimiter
	 *            String literal on which to split.
	 * @return an array of tokens.
	 * @throws NullPointerException
	 *             if s is null.
	 * 
	 * @since ostermillerutils 1.00.00
	 */
	public static String[] split(String s, String delimiter) {
		int delimiterLength;
		int stringLength = s.length();
		if (delimiter == null || (delimiterLength = delimiter.length()) == 0) {		
			return new String[] { s };
		}

		int count;
		int start;
		int end;

		// Scan s and count the tokens.
		count = 0;
		start = 0;
		while ((end = s.indexOf(delimiter, start)) != -1) {
			count++;
			start = end + delimiterLength;
		}
		count++;

		String[] result = new String[count];
		count = 0;
		start = 0;
		while ((end = s.indexOf(delimiter, start)) != -1) {
			result[count] = (s.substring(start, end));
			count++;
			start = end + delimiterLength;
		}
		end = stringLength;
		result[count] = s.substring(start, end);

		return (result);
	}

	/**
	 * Split the given String into tokens. Delimiters will be returned as
	 * tokens.
	 * <P>
	 * This method is meant to be similar to the split function in other
	 * programming languages but it does not use regular expressions. Rather the
	 * String is split on a single String literal.
	 * <P>
	 * Unlike java.util.StringTokenizer which accepts multiple character tokens
	 * as delimiters, the delimiter here is a single String literal.
	 * <P>
	 * Each null token is returned as an empty String. Delimiters are never
	 * returned as tokens.
	 * <P>
	 * If there is no delimiter because it is either empty or null, the only
	 * element in the result is the original String.
	 * <P>
	 * StringHelper.split("1-2-3", "-");<br>
	 * result: {"1","-","2","-","3"}<br>
	 * StringHelper.split("-1--2-", "-");<br>
	 * result: {"","-","1","-","","-","2","-",""}<br>
	 * StringHelper.split("123", "");<br>
	 * result: {"123"}<br>
	 * StringHelper.split("1-2--3---4----5", "--");<br>
	 * result: {"1-2","--","3","--","-4","--","","--","5"}<br>
	 * 
	 * @param s
	 *            String to be split.
	 * @param delimiter
	 *            String literal on which to split.
	 * @return an array of tokens.
	 * @throws NullPointerException
	 *             if s is null.
	 * 
	 * @since ostermillerutils 1.05.00
	 */
	public static String[] splitIncludeDelimiters(String s, String delimiter) {
		int delimiterLength;
		// the next statement has the side effect of throwing a null pointer
		// exception if s is null.
		int stringLength = s.length();
		if (delimiter == null || (delimiterLength = delimiter.length()) == 0) {
			// it is not inherently clear what to do if there is no delimiter
			// On one hand it would make sense to return each character because
			// the null String can be found between each pair of characters in
			// a String. However, it can be found many times there and we don'
			// want to be returning multiple null tokens.
			// returning the whole String will be defined as the correct
			// behavior
			// in this instance.
			return new String[] { s };
		}

		// a two pass solution is used because a one pass solution would
		// require the possible resizing and copying of memory structures
		// In the worst case it would have to be resized n times with each
		// resize having a O(n) copy leading to an O(n^2) algorithm.

		int count;
		int start;
		int end;

		// Scan s and count the tokens.
		count = 0;
		start = 0;
		while ((end = s.indexOf(delimiter, start)) != -1) {
			count += 2;
			start = end + delimiterLength;
		}
		count++;

		// allocate an array to return the tokens,
		// we now know how big it should be
		String[] result = new String[count];

		// Scan s again, but this time pick out the tokens
		count = 0;
		start = 0;
		while ((end = s.indexOf(delimiter, start)) != -1) {
			result[count] = (s.substring(start, end));
			count++;
			result[count] = delimiter;
			count++;
			start = end + delimiterLength;
		}
		end = stringLength;
		result[count] = s.substring(start, end);

		return (result);
	}

	/**
	 * @content 得到不同的时间类型
	 * @author liuxd
	 * @time 2011/07/24 22:00
	 */
	public static String getNow(int type) {
		SimpleDateFormat sdf = null;
		switch (type) {
		case 0:
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			break;
		case 1:
			sdf = new SimpleDateFormat("yyyyMMdd");
			break;
		case 2:
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 3:
			sdf = new SimpleDateFormat("yyyyMM");
			break;
		case 4:
			sdf = new SimpleDateFormat("yyyy年MM月dd日");
			break;
		case 5:
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 6:
			sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); //取到毫秒
			break;
		case 7:
			sdf = new SimpleDateFormat("yyyyMMddHHmmss"); //取到秒
			break;
		default:
			sdf = new SimpleDateFormat("yyyy-M-D");
			break;
		}

		return sdf.format(new Date());
	}

	/**
	 * 返回子串在　母串中的个数
	 * 
	 * @param srcStr
	 * @param subStr
	 * @return
	 */
	public static int getSubCount(String srcStr, String subStr) {
		int count = 0;
		try {
			Matcher m = Pattern.compile(subStr).matcher(srcStr);

			while (m.find()) {
				count++;
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();

			return count;
		}

	}

	/**
	 * 判断字符串是否全是数字字符.
	 * 
	 * @param input
	 *            输入的字符串
	 * @return 判断结果, true 为全数字, false 为还有非数字字符
	 */
	public static boolean isNumeric(String input) {
		if (checkNull(input)) {
            return false;
        }

		for (int i = 0; i < input.length(); i++) {
			char charAt = input.charAt(i);

			if (!Character.isDigit(charAt)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串　是否能转换成整数
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isNumber(String input) {
		try {
			Integer.valueOf(input).intValue();

			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isExcelData(String input){
		if(input.indexOf(".")!=-1){
			String decimals = input.substring(input.indexOf(".")+1);
			if(decimals.length()==1 && decimals.equals("0")){
				String integers = input.substring(0,input.indexOf("."));
				if(Integer.parseInt(integers)<0){
					return false;
				}else{
					return true;
				}				
			}else{
				return false;
			}
		}else{
			try {
				Integer.valueOf(input).intValue();

				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}
	public static String retIntegerpart(String input){
		if(input.indexOf(".")!=-1){
			return input.substring(0,input.indexOf("."));
		}else{
			return input;
		}
		
	}

	/**
	 * 判断字符串是否是正数
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isSortNumber(String input) {
		try {
			int temp = Integer.valueOf(input).intValue();
			if (temp < 0) {
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串　是否能转换成float
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isFloat(String input) {
		try {
			Float.valueOf(input).floatValue();

			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是正数
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isSortFloat(String input) {
		
		if (null == input || input.trim().equals("")) {
            return false;
        }
		
		try {
			float f = Float.valueOf(input).floatValue();
			if (f < 0) {
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否全是字母
	 * 
	 * @param
	 * @return
	 */
	public static boolean checkOnlyChar(String s) {
		if (s == null || s.equals("")) {
            return false;
        }

		for (int j = 0; j < s.length(); j++) {
			char c = s.charAt(j);
			int i = (int) c;
			if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
                return true;
            }
		}

		return false;
	}

	/**
	 * 判断首字符是否为大写
	 * 
	 * @param
	 * @return
	 */
	public static boolean isOnecharLarge(String s) {
		char c = s.charAt(0);
		int i = (int) c;
		if ((i >= 65 && i <= 90)/* || i >= 97 && i <= 122*/) {
			return true;
		} else {
			return false;
		}
	}
	
	  /**
     * 功能：判断字符串是否为日期格式
     * 
     * @param str
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
	 * 作者拼音编码规则
	 */	
	  public String AuthorNumberMapping(String authorNameSpell)
      {
          String authorNumber = "";
          HashMap<String, String> mappingDict = new HashMap<String, String>();
          mappingDict.put("A-B-C", "1");
          mappingDict.put("D-E-F", "2");
          mappingDict.put("G-H-I", "3");
          mappingDict.put("J-K-L", "4");
          mappingDict.put("M-N", "5");
          mappingDict.put("O-P-Q", "6");
          mappingDict.put("R-S-T", "7");
          mappingDict.put("U-V-W", "8");
          mappingDict.put("X-Y-Z", "9");
          mappingDict.put("ZH-CH-SH", "0");
          Set<Map.Entry<String,String>> entrySet = mappingDict.entrySet();
          for(Map.Entry<String,String> entry:entrySet){
        	  String[] tempArray = entry.getKey().split("-");
        	  if (isKey(authorNameSpell, tempArray))
              {
                  authorNumber =  entry.getValue();
              }
           
          }
          return authorNumber;
      }

	  /**
	   * 作者拼音字母是否在map的key当中
	   * @param authorNameSpell
	   * @param tempArray
	   * @return
	   */
	private boolean isKey(String authorNameSpell, String[] tempArray) {
		for(int i=0;i<tempArray.length;i++){
			  if(authorNameSpell.equals(tempArray[i])){
				  return true;
			  }
		  }
		return false;
	}

	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date StrToDate(String str,String formatStr) {
	  
	   SimpleDateFormat format = new SimpleDateFormat(formatStr);
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
	
}
