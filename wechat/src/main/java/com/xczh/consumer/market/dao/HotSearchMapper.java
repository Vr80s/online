package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.MobileSearchVo;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/***
 * 听课
 * @author yjy
 *
 */
@Repository
public class HotSearchMapper extends BasicSimpleDao {


	/****
	 * 搜索关键字列表查询
	 * @return
	 * @throws SQLException
	 */
	public List<MobileSearchVo>  SearchList(Integer searchType) throws SQLException{
		StringBuffer sql = new StringBuffer(" ");
		sql.append(" select oms.name as name,oms.search_type as searchType from oe_mobile_search oms  ");
		sql.append(" where oms.is_delete=0 and oms.status = 1 and oms.search_type = ? ");
		sql.append(" order by oms.seq asc ");
		Object params[] = { searchType };
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(MobileSearchVo.class),params );
	}


}
