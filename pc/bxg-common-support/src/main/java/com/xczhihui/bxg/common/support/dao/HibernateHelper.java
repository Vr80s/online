package com.xczhihui.bxg.common.support.dao;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

/**
 * 构造查询条件的助手类
 * 
 * @author liyong
 *
 */
public final class HibernateHelper {

	/**
	 * 给map增加查询条件
	 * 
	 * @param criteriaMap
	 * @param paramName
	 *            查询参数名
	 * @param paramValue
	 *            查询参数值
	 */
	public static void putCriteria(Map<String, Object> criteriaMap, String paramName, Object paramValue) {
		if (StringUtils.hasText(paramName) && paramValue != null) {
			criteriaMap.put(paramName, paramValue);
		}
	}

	/**
	 * 添加条件
	 * 
	 * @param dc
	 * @param field
	 * @param value
	 * @param criteriaRelation
	 */
	public static DetachedCriteria addCriteria(DetachedCriteria dc, String field, Object value,
			CriteriaRelation criteriaRelation) {
		if (value == null || (StringUtils.isEmpty(value.toString()))) {
			return dc;
		}
		switch (criteriaRelation) {
		case EQ:
			dc.add(Restrictions.eq(field, value));
			break;
		case GT:
			dc.add(Restrictions.gt(field, value));
			break;
		case GE:
			dc.add(Restrictions.ge(field, value));
			break;
		case LT:
			dc.add(Restrictions.lt(field, value));
			break;
		case LE:
			dc.add(Restrictions.le(field, value));
			break;
		case NE:
			dc.add(Restrictions.ne(field, value));
			break;
		case LIKE:
			dc.add(Restrictions.like(field, value.toString().trim(), MatchMode.ANYWHERE));
			break;
		default:
			break;
		}
		return dc;
	}
}
