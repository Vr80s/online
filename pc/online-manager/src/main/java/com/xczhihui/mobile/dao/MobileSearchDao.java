package com.xczhihui.mobile.dao;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.mobile.vo.MobileSearchVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("mobileSearchDao")
public class MobileSearchDao extends HibernateDao<MobileSearchVo> {

	public Page<MobileSearchVo> findMobileSearchPage(
			MobileSearchVo mobileSearchVo, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select * from oe_mobile_search where is_delete=0 ");
		if (mobileSearchVo.getSearchType() != null
				&& !"".equals(mobileSearchVo.getSearchType())) {
			sql.append(" and search_type = :type ");
			paramMap.put("type", mobileSearchVo.getSearchType());
		}
		sql.append(" order by  status desc,seq asc ");
		return this.findPageBySQL(sql.toString(), paramMap,
				MobileSearchVo.class, pageNumber, pageSize);
	}

	public int getMaxSort() {
		String sql = " select ifnull(max(seq),0) as maxSort from oe_mobile_search where  is_delete=0  ";
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, new HashMap<String, Object>());
		return Integer.parseInt(result.get("maxSort") != null ? String
				.valueOf(result.get("maxSort")) : "0");
	}

	public MobileSearchVo findById(String parseInt) {
		StringBuilder sql = new StringBuilder(
				"select * from oe_mobile_search where id=:id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", parseInt);
		List<MobileSearchVo> menus = this
				.getNamedParameterJdbcTemplate()
				.query(sql.toString(), params,
						BeanPropertyRowMapper.newInstance(MobileSearchVo.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	public String deleteById(String id) {
		String s = "";
		String deleteSql = " update oe_mobile_search set is_delete=1 where  id = :id ";
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("id", id);
		this.getNamedParameterJdbcTemplate().update(deleteSql, params2);
		s = "删除成功";
		return s;
	}

}
