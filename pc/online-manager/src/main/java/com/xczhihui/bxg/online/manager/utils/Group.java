package com.xczhihui.bxg.online.manager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import com.xczhihui.bxg.online.manager.utils.PropertyFilter.MatchType;
import com.xczhihui.bxg.online.manager.utils.PropertyFilter.Type;


public class Group {
	private boolean isDate = false;
	// 与前一条件的关系
	private MatchType relation = MatchType.AND;

	private Type type = Type.STRING;

	private String tempType = "";

	// 本身字段的比较
	private MatchType matchType = MatchType.EQ;

	private String tempMatchType = "";

	// 待比较字段,级联关系字段直接.写法,如father.child.grandson
	private String propertyName = null;

	/**
	 * 注意　这里没有对 数组对象作处理,　既暂时不支持组对象
	 */
	// 比较的值
	private Object propertyValue1 = null;

	// 为between 准备
	private Object propertyValue2 = null;

	public Group() {

	}

	public Group(String propertyName, Object propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue1 = propertyValue;
	}

	public Group(String propertyName, Object propertyValue, MatchType matchType) {
		this.propertyName = propertyName;
		this.propertyValue1 = propertyValue;
		this.matchType = matchType;
	}

	public Group(String propertyName, MatchType matchType, MatchType relation) {
		this.propertyName = propertyName;
		this.matchType = matchType;
		this.relation = relation;
	}

	public Group(String propertyName, Object propertyValue,
			MatchType matchType, MatchType relation) {
		this.propertyName = propertyName;
		this.propertyValue1 = propertyValue;
		this.matchType = matchType;
		this.relation = relation;
	}

	public Group(String propertyName, Object propertyValue1,
			Object propertyValue2, MatchType matchType, MatchType relation) {
		this.propertyName = propertyName;
		this.propertyValue1 = propertyValue1;
		this.propertyValue2 = propertyValue2;
		this.matchType = matchType;
		this.relation = relation;
	}

	public MatchType getRelation() {
		return relation;
	}

	public void setRelation(MatchType relation) {
		this.relation = relation;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getPropertyValue1() {
		return propertyValue1;
	}

	public void setPropertyValue1(Object propertyValue1) {
		this.propertyValue1 = propertyValue1;
	}

	public Object getPropertyValue2() {
		return propertyValue2;
	}

	public void setPropertyValue2(Object propertyValue2) {
		this.propertyValue2 = propertyValue2;
	}

	public String getTempMatchType() {
		return tempMatchType;
	}

	public void setTempMatchType(String tempMatchType) {
		this.tempMatchType = tempMatchType;
		/*setPropertyValue1(((String[]) propertyValue1)[0].trim());
		setPropertyValue2(((String[]) propertyValue2)[0].trim());*/
		if ("4".equals(tempMatchType)){
			matchType = MatchType.EQ;
		} else if ("5".equals(tempMatchType)) {
			matchType = MatchType.LIKE;
		} else if ("6".equals(tempMatchType)) {
			matchType = MatchType.LT;//小于
		} else if ("7".equals(tempMatchType)) {
			matchType = MatchType.GT;//大于
		} else if ("8".equals(tempMatchType)) {
			matchType = MatchType.LE;//小于等于
		} else if ("9".equals(tempMatchType)) {
			matchType = MatchType.GE;//大于等于
		} else if ("10".equals(tempMatchType)) {
			matchType = MatchType.BETWEEN;
		} else if ("11".equals(tempMatchType)) {
			matchType = MatchType.IN;
		}
		if (getPropertyValue1() == null) {
			matchType = MatchType.NULL;
		}
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getTempType() {
		return tempType;
	}

	public void setTempType(String tempType) throws ParseException {
		this.tempType = tempType;
		
		if (tempType.toString().toUpperCase().equals("INTEGER")) {
			Float obj = Float.valueOf(propertyValue1.toString());
			propertyValue1 = obj.intValue();
			if (propertyValue2 != null) {
				propertyValue2 = Integer.valueOf(propertyValue1.toString());
			}
		} else if (tempType.toString().toUpperCase().equals("FLOAT")) {
			propertyValue1 = Float.valueOf(propertyValue1.toString());
			if (propertyValue2 != null) {
				propertyValue2 = Float.valueOf(propertyValue1.toString());
			}
		} else if (tempType.toString().toUpperCase().equals("DOUBLE")) {
			propertyValue1 = Double.valueOf(propertyValue1.toString());
			if (propertyValue2 != null) {
				propertyValue2 = Double.valueOf(propertyValue1.toString());
			} else if (tempType.toString().toUpperCase().equals("YYDATE")) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				propertyValue1 = simpleDateFormat.parse(propertyValue1.toString());
				if (propertyValue2 != null) {
					propertyValue2 = simpleDateFormat.parseObject(propertyValue2.toString());
				}
			} else if (tempType.toString().toUpperCase().equals("LONG")) {
				propertyValue1 = Long.valueOf(propertyValue1.toString());
				if (propertyValue2 != null) {
					propertyValue2 = Long.valueOf(propertyValue1.toString());
				}
			} else if (tempType.toString().toUpperCase().equals("BOOLEAN")) {
				propertyValue1 = Boolean.valueOf(propertyValue1.toString());
				if (propertyValue2 != null) {
					propertyValue2 = Boolean.valueOf(propertyValue1.toString());
				}
			} else if (tempType.toString().toUpperCase().equals("DOUBLE")) {
				propertyValue1 = Double.valueOf(propertyValue1.toString());
				if (propertyValue2 != null) {
					propertyValue2 = Double.valueOf(propertyValue1.toString());
				}
			}
		} else if (tempType.toString().toUpperCase().equals("BOOLEAN")) {
			propertyValue1 = Boolean.valueOf(propertyValue1.toString());
			if (propertyValue2 != null) {
				propertyValue2 = Boolean.valueOf(propertyValue1.toString());
			}
		} else if (tempType.toString().toUpperCase().equals("DATE")) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			propertyValue1 = simpleDateFormat.parse(propertyValue1.toString());
			if (propertyValue2 != null) {
				propertyValue2 = simpleDateFormat.parseObject(propertyValue2.toString());
			}
		}
		else if("LIST".equals(tempType.toString().toUpperCase()))
		{
			String values = propertyValue1.toString();
			
			String[] datas = values.split(",");
			List<String> list = Arrays.asList(datas);
			propertyValue1 = list;			
		}
	}

	public boolean isDate() {
		return isDate;
	}

	public void setDate(boolean isDate) {
		this.isDate = isDate;
	}

}
