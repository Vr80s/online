package com.xczhihui.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class Group {
    private boolean isDate = false;
    // 与前一条件的关系
    private PropertyFilter.MatchType relation = PropertyFilter.MatchType.AND;

    private PropertyFilter.Type type = PropertyFilter.Type.STRING;

    private String tempType = "";

    // 本身字段的比较
    private PropertyFilter.MatchType matchType = PropertyFilter.MatchType.EQ;

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

    public Group(String propertyName, Object propertyValue,
                 PropertyFilter.MatchType matchType) {
        this.propertyName = propertyName;
        this.propertyValue1 = propertyValue;
        this.matchType = matchType;
    }

    public Group(String propertyName, PropertyFilter.MatchType matchType,
                 PropertyFilter.MatchType relation) {
        this.propertyName = propertyName;
        this.matchType = matchType;
        this.relation = relation;
    }

    public Group(String propertyName, Object propertyValue,
                 PropertyFilter.MatchType matchType,
                 PropertyFilter.MatchType relation) {
        this.propertyName = propertyName;
        this.propertyValue1 = propertyValue;
        this.matchType = matchType;
        this.relation = relation;
    }

    public Group(String propertyName, Object propertyValue1,
                 Object propertyValue2, PropertyFilter.MatchType matchType,
                 PropertyFilter.MatchType relation) {
        this.propertyName = propertyName;
        this.propertyValue1 = propertyValue1;
        this.propertyValue2 = propertyValue2;
        this.matchType = matchType;
        this.relation = relation;
    }

    public PropertyFilter.MatchType getRelation() {
        return relation;
    }

    public void setRelation(PropertyFilter.MatchType relation) {
        this.relation = relation;
    }

    public PropertyFilter.MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(PropertyFilter.MatchType matchType) {
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
        /*
		 * setPropertyValue1(((String[]) propertyValue1)[0].trim());
		 * setPropertyValue2(((String[]) propertyValue2)[0].trim());
		 */
        if ("4".equals(tempMatchType)) {
            matchType = PropertyFilter.MatchType.EQ;
        } else if ("5".equals(tempMatchType)) {
            matchType = PropertyFilter.MatchType.LIKE;
        } else if ("6".equals(tempMatchType)) {
            matchType = PropertyFilter.MatchType.LT;// 小于
        } else if ("7".equals(tempMatchType)) {
            matchType = PropertyFilter.MatchType.GT;// 大于
        } else if ("8".equals(tempMatchType)) {
            matchType = PropertyFilter.MatchType.LE;// 小于等于
        } else if ("9".equals(tempMatchType)) {
            matchType = PropertyFilter.MatchType.GE;// 大于等于
        } else if ("10".equals(tempMatchType)) {
            matchType = PropertyFilter.MatchType.BETWEEN;
        } else if ("11".equals(tempMatchType)) {
            matchType = PropertyFilter.MatchType.IN;
        }
        if (getPropertyValue1() == null) {
            matchType = PropertyFilter.MatchType.NULL;
        }
    }

    public PropertyFilter.Type getType() {
        return type;
    }

    public void setType(PropertyFilter.Type type) {
        this.type = type;
    }

    public String getTempType() {
        return tempType;
    }

    public void setTempType(String tempType) throws ParseException {
        this.tempType = tempType;

        if ("INTEGER".equals(tempType.toString().toUpperCase())) {
            Float obj = Float.valueOf(propertyValue1.toString());
            propertyValue1 = obj.intValue();
            if (propertyValue2 != null) {
                propertyValue2 = Integer.valueOf(propertyValue1.toString());
            }
        } else if ("FLOAT".equals(tempType.toString().toUpperCase())) {
            propertyValue1 = Float.valueOf(propertyValue1.toString());
            if (propertyValue2 != null) {
                propertyValue2 = Float.valueOf(propertyValue1.toString());
            }
        } else if ("DOUBLE".equals(tempType.toString().toUpperCase())) {
            propertyValue1 = Double.valueOf(propertyValue1.toString());
            if (propertyValue2 != null) {
                propertyValue2 = Double.valueOf(propertyValue1.toString());
            } else if ("YYDATE".equals(tempType.toString().toUpperCase())) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd");
                propertyValue1 = simpleDateFormat.parse(propertyValue1
                        .toString());
                if (propertyValue2 != null) {
                    propertyValue2 = simpleDateFormat
                            .parseObject(propertyValue2.toString());
                }
            } else if ("LONG".equals(tempType.toString().toUpperCase())) {
                propertyValue1 = Long.valueOf(propertyValue1.toString());
                if (propertyValue2 != null) {
                    propertyValue2 = Long.valueOf(propertyValue1.toString());
                }
            } else if ("BOOLEAN".equals(tempType.toString().toUpperCase())) {
                propertyValue1 = Boolean.valueOf(propertyValue1.toString());
                if (propertyValue2 != null) {
                    propertyValue2 = Boolean.valueOf(propertyValue1.toString());
                }
            } else if ("DOUBLE".equals(tempType.toString().toUpperCase())) {
                propertyValue1 = Double.valueOf(propertyValue1.toString());
                if (propertyValue2 != null) {
                    propertyValue2 = Double.valueOf(propertyValue1.toString());
                }
            }
        } else if ("BOOLEAN".equals(tempType.toString().toUpperCase())) {
            propertyValue1 = Boolean.valueOf(propertyValue1.toString());
            if (propertyValue2 != null) {
                propertyValue2 = Boolean.valueOf(propertyValue1.toString());
            }
        } else if ("DATE".equals(tempType.toString().toUpperCase())) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            propertyValue1 = simpleDateFormat.parse(propertyValue1.toString());
            if (propertyValue2 != null) {
                propertyValue2 = simpleDateFormat.parseObject(propertyValue2
                        .toString());
            }
        } else if ("LIST".equals(tempType.toString().toUpperCase())) {
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
