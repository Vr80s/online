package com.xczhihui.bxg.online.manager.common.dao;

import com.xczhihui.bxg.online.manager.common.vo.KeyValVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.PageVo;
import com.xczhihui.bxg.online.manager.utils.PropertyFilter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于查询OE_COMMON表的数据
 * 
 * @author yuanziyang
 * @date 2016年4月5日 下午3:27:55
 */
@Repository
public class OeCommonDao extends HibernateDao {

	/**
	 * 查询指定分组的数据，查出id 和val列，装入KeyValVo类对象
	 * 
	 * @param group
	 *            对应OE_GROUP表的group列数据
	 * @return
	 */
	public List<KeyValVo> getCommonDataList(String group) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT c.id AS id,c.`val` AS name,c.key as _key,c.val as _value FROM oe_common c ");
		sql.append(" WHERE c.is_delete = 0");
		sql.append(" AND c.group =:group ");
		sql.append(" ORDER BY c.sort ");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("group", group);
		return this.getNamedParameterJdbcTemplate().query(sql.toString(), paramMap, new RowMapper<KeyValVo>() {

			@Override
			public KeyValVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				KeyValVo keyValVo = new KeyValVo();
				keyValVo.setId(rs.getString("id"));
				keyValVo.setName(rs.getString("name"));
				keyValVo.set_key(rs.getString("_key"));
				keyValVo.set_value(rs.getString("_value"));
				return keyValVo;
			}

		});
	}

	/**
	 * 查询一页常量分组数据
	 * 
	 * @param groups
	 * @param page
	 * @return
	 */
	public PageVo findCommonGroupPage(Groups groups, PageVo page) {
		StringBuilder sql = new StringBuilder();
		// 增加unuser列，可以在findPageByGroups方法中成功提取出列名为groupName的数据，否则会提取出列名为groupName到第二个from之间的所有字符
		sql.append(" select oec.groupName as groupName,'unuse' as unuse from ");
		StringBuilder innerSql = new StringBuilder();
		innerSql.append("select distinct(`group`) as groupName from oe_common ");
		if (groups != null && groups.getGroupList() != null) {
			innerSql.append("where 1=1 ");
			for (Group group : groups.getGroupList()) {
				if (group.getMatchType() == PropertyFilter.MatchType.EQ) {
                    innerSql.append(" and " + group.getPropertyName() + "=" + group.getPropertyValue1());
                } else if (group.getMatchType() == PropertyFilter.MatchType.LIKE) {
                    innerSql.append(" and " + group.getPropertyName() + " like '%" + group.getPropertyValue1() + "%'");
                }
			}
		}
		sql.append(" (").append(innerSql).append(") oec ");
		groups.setGroupList(null);
		groups.setOrderby("oec.groupName");
		groups.setOrder(true);
		// TODO 这个查询还不能实现通过Groups来搜索，需要修改findPageByGroups方法
		return this.findPageByGroups(groups, page, sql.toString());
	}

	/**
	 * 查询一页常量数据
	 * 
	 * @param groups
	 * @param page
	 * @return
	 */
	public PageVo findCommonPage(Groups groups, PageVo page) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT oec.id as id,oec.`key` as keyId,oec.val as val,oec.`group` as groupName,");
		sql.append("oec.is_delete as isDelete,oec.status as status,oec.create_time as createTime,oec.sort as sort");
		sql.append(" from oe_common oec");
		groups.setOrderbys(new String[] { "oec.`group`", "oec.sort" });
		groups.setOrders(new boolean[] { true, true });
		return this.findPageByGroups(groups, page, sql.toString());
	}

	/**
	 * 更新一个分组的所有数据的指定列
	 * 
	 * @param colName
	 * @param value
	 * @param groupVal
	 */
	public void updateOneColByGroup(String colName, Object value, String groupVal) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update oe_common oec set " + colName + "=:" + colName);
		sql.append(" where oec.`group`=:groupVal");
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(colName, value);
		paramMap.put("groupVal", groupVal);
		this.getNamedParameterJdbcTemplate().update(sql.toString(), paramMap);
	}


}
