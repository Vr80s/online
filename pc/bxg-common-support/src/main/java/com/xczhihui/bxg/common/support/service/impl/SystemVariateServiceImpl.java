package com.xczhihui.bxg.common.support.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.common.util.bean.Page;

@Service("systemVariateService")
public class SystemVariateServiceImpl implements SystemVariateService {
	/**
	 * 变量类型
	 */
	private static final String VAR_TYPE = "var_type";

	private SimpleHibernateDao simpleDao;

	@Override
	public List<SystemVariate> getSystemVariateTypes() {
		return this.getSystemVariatesByName(VAR_TYPE);
	}

	@Override
	public void addSystemVariate(SystemVariate systemVariate) {
		systemVariate.setCreateTime(new Date());
		this.simpleDao.save(systemVariate);
	}

	@Override
	public void deleteSystemVariateLogic(String id) {
		this.simpleDao.deleteLogic(id, SystemVariate.class);
	}

	@Override
	public void deleteSystemVariate(String id) {
		this.simpleDao.delete(id, SystemVariate.class);
	}

	@Override
	public SystemVariate getSystemVariateById(String id) {
		return this.simpleDao.get(id, SystemVariate.class);
	}

	@Override
	public SystemVariate getSystemVariateByName(String name) {
		List<SystemVariate> vars = this.getSystemVariatesByName(name);
		if (vars != null && vars.size() > 0) {
			return vars.get(0);
		}
		return null;
	}

	@Override
	public List<SystemVariate> getSystemVariatesByName(String name) {
		List<SystemVariate> svs = this.simpleDao.findEntitiesByProperty(SystemVariate.class, "name", name);
		Collections.sort(svs, new SystemVariateComparator());
		return svs;
	}

	@Override
	public List<SystemVariate> getSystemVariatesByParentId(String parentId) {
		List<SystemVariate> svs = this.simpleDao.findEntitiesByProperty(SystemVariate.class, "parentId", parentId);
		Collections.sort(svs, new SystemVariateComparator());
		return svs;
	}

	@Resource(name = "simpleHibernateDao")
	public void setSimpleHibernateDao(SimpleHibernateDao simpleDao) {
		this.simpleDao = simpleDao;
	}

	@Override
	public List<SystemVariate> getSystemVariatesByParentValue(String parentValue) {
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("parentValue", parentValue);
		String sql = "select t1.id, t1.name,t1.value,t2.id as parent_id from system_variate t1,"
				+ "system_variate t2 where t1.parent_id=t2.id and t2.value=:parentValue order by t1.display_order ";
		List<SystemVariate> svs = this.simpleDao.getNamedParameterJdbcTemplate()
				.query(sql,ps,new BeanPropertyRowMapper<SystemVariate>(SystemVariate.class));
		return svs;
	}

	@Override
	public String getNameByValue(String parentValue, String value) {
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("parentValue", parentValue);
		ps.put("value", value);
		String sql = "select t1.name,t1.value from system_variate t1,"
				+ "system_variate t2 where t1.parent_id=t2.id and t2.value=:parentValue and t1.value=:value  order by t1.display_order ";
		List<SystemVariate> svs = this.simpleDao.getNamedParameterJdbcTemplate()
				.query(sql,ps,new BeanPropertyRowMapper<SystemVariate>(SystemVariate.class));
		if (svs != null && svs.size() > 0) {
			return svs.get(0).getName();
		}
		return null;
	}

	@Override
	public Page<SystemVariate> getSystemVariatesList(Object object,
			int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
}

class SystemVariateComparator implements Comparator<SystemVariate> {

	@Override
	public int compare(SystemVariate o1, SystemVariate o2) {
		return o2.getDisplayOrder() - o1.getDisplayOrder();
	}
}