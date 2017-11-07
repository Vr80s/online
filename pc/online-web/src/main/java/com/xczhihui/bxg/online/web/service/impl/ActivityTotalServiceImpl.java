package com.xczhihui.bxg.online.web.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.web.service.ActivityTotalService;

/**
 * @author Haicheng Jiang
 */
@Service
public class ActivityTotalServiceImpl implements ActivityTotalService {
	
	@Resource(name = "simpleHibernateDao")
	private SimpleHibernateDao dao;
	
	@Override
	public void addTotal(String fromCode) {
		add(fromCode);
		dao.getNamedParameterJdbcTemplate().getJdbcOperations()
			.update("update oe_activity_total set from_sum=(from_sum+1) where from_code=?",fromCode);
	}

	@Override
	public void addTotalDetail4Reg(String fromCode,String loginName, String nikeName) {
		try {
			String id = UUID.randomUUID().toString().replace("-", "");
			dao.getNamedParameterJdbcTemplate().getJdbcOperations()
			.update("insert into oe_activity_total_detail(id,from_code,login_name,nike_name) "
					+ " values ('"+id+"','"+fromCode+"','"+loginName+"','"+nikeName+"')");
			add(fromCode);
			dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update("update oe_activity_total set reg_sum=(reg_sum+1) where from_code=?",fromCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void add(String fromCode) {
		if (dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(
				"select count(*) from oe_activity_total where from_code=?", Integer.class,fromCode) <= 0) {
			String id = UUID.randomUUID().toString().replace("-", "");
			dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update("insert into oe_activity_total(id,from_code) values ('"+id+"','"+fromCode+"')");
		}
	}
}
