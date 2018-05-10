package com.xczhihui.bxg.online.web.dao;/**
 * Created by admin on 2016/9/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;

/**
 * 第三方系统接口提供
 * @author Haicheng Jiang
 *
 */
@Repository
public class ThirdSystemDao extends SimpleHibernateDao {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
