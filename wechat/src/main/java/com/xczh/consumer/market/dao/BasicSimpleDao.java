package com.xczh.consumer.market.dao;


import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * 基础dao，所有的dao继承于此类
 * 
 * @author Alex Wang
 */
@Repository
public class BasicSimpleDao extends QueryRunner {

	/**
	 * 分页查询
	 * @param c 数据库连接
	 * @param sql
	 * @param pageNumber 当前是第几页
	 * @param pageSize 每页显示多少行
	 * @param clz 转换的对象（vo）
	 * @param params 参数
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> queryPage(Connection c, String sql, int pageNumber, int pageSize, Class<T> clz, Object... params)
			throws SQLException {
		if (sql != null) {
			int num = (pageNumber - 1) * pageSize;
			num = num < 0 ? 0 : num;
			sql += " limit "+num+","+pageSize;
		}
		if (params == null || params.length < 0) {
			return query(JdbcUtil.getCurrentConnection(), sql, new BeanListHandler<T>(clz));
		}
		return query(JdbcUtil.getCurrentConnection(), sql, new BeanListHandler<T>(clz), params);
	}
	
	/**
	 * 获得sql语句中的in参数
	 * 
	 * @param str 字符串
	 * @param split 字符串中是以什么分隔的
	 * @param hasSingleQuote 生成的in字符串是否带带引号，如'1','2','3'或1,2,3
	 * @return
	 */
	public String getInParamter(String str, String split, boolean hasSingleQuote) {
		if (!hasSingleQuote) {
			return str.replace(split, ",");
		}

		String returnstr = "";
		String[] os = str.split(split);
		for (String s : os) {
			returnstr += ("','" + s);
		}
		if (returnstr.length() > 0) {
			returnstr = returnstr.substring(2) + "'";
		}
		return returnstr;
	}

	/**
	 * 获得sql语句中的in参数
	 * 
	 * @param obj
	 * @param hasSingleQuote 生成的in字符串是否带带引号，如'1','2','3'或1,2,3
	 * @return
	 */
	public String getInParamter(Object[] obj, boolean hasSingleQuote) {
		String returnstr = "";
		for (Object object : obj) {
			if (hasSingleQuote) {
				returnstr += ("','" + object.toString());
			} else {
				returnstr += ("," + object.toString());
			}
		}
		if (returnstr.length() > 0) {
			if (hasSingleQuote) {
				returnstr = returnstr.substring(2) + "'";
			} else {
				returnstr = returnstr.substring(1);
			}
		}
		return returnstr;
	}

	/**
	 * 获得sql语句中的in参数
	 * 
	 * @param collection
	 * @param hasSingleQuote 生成的in字符串是否带带引号，如'1','2','3'或1,2,3
	 * @return
	 */
	public String getInParamter(Collection<? extends Object> collection, boolean hasSingleQuote) {
		String returnstr = "";
		for (Object object : collection) {
			if (hasSingleQuote) {
				returnstr += ("','" + object.toString());
			} else {
				returnstr += ("," + object.toString());
			}
		}
		if (returnstr.length() > 0) {
			if (hasSingleQuote) {
				returnstr = returnstr.substring(2) + "'";
			} else {
				returnstr = returnstr.substring(1);
			}
		}
		return returnstr;
	}
}
