package com.xczhihui.common.support.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xczhihui.common.support.service.DictionaryService;
import org.springframework.stereotype.Service;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.DictionaryVo;

@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
	
	private SimpleHibernateDao simpleDao;

	@Override
	public List<DictionaryVo> list(String parentValue) {
		List<DictionaryVo> returnlist = new ArrayList<DictionaryVo>();
		String sql = "select c.`name`,c.`value`,p.`value` as parent_value,c.display_order "
				+ "from system_variate c,system_variate p "
				+ "where c.parent_id=p.id and p.`value`=:parentValue and p.parent_id is null;";
		Map<String, String> params = new HashMap<String, String>();
		params.put("parentValue", parentValue);
		List<Map<String, Object>> datas = simpleDao.getNamedParameterJdbcTemplate().queryForList(sql, params);
		for (Map<String, Object> map : datas) {
			returnlist.add(new DictionaryVo(String.valueOf(map.get("name")), 
					String.valueOf(map.get("value")), String.valueOf(map.get("parent_value"))));
		}
		return returnlist;
	}

	@Override
	public String name(String parentValue,String value) {
		String sql = "select c.name as name from system_variate c,system_variate p where c.parent_id=p.id and p.`value`=:parentValue and c.`value`=:value";
		Map<String, String> params = new HashMap<String, String>();
		params.put("parentValue", parentValue);
		params.put("value", value);
		List<Map<String, Object>> queryForList = simpleDao.getNamedParameterJdbcTemplate().queryForList(sql, params);
		if (queryForList != null && queryForList.size() > 0) {
			return String.valueOf(queryForList.get(0).get("name"));
		}
		return null;
	}
	
	@Resource(name = "simpleHibernateDao")
	public void setSimpleHibernateDao(SimpleHibernateDao simpleDao) {
		this.simpleDao = simpleDao;
	}
	
}
