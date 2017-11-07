package com.xczhihui.bxg.online.common.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.base.service.OnlineBaseService;
/**
 * 所有service均继承于此
 * @author Haicheng Jiang
 */
@Service
public class OnlineBaseServiceImpl implements OnlineBaseService {
	public SimpleHibernateDao dao;

	public SimpleHibernateDao getDao() {
		return dao;
	}

	@Resource(name="simpleHibernateDao")
	public void setDao(SimpleHibernateDao dao) {
		this.dao = dao;
	}
}
