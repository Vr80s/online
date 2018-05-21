package com.xczhihui.config;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.xczhihui.common.support.dao.SimpleHibernateDao;

/**
 * @author hejiwei
 */
@Configuration
public class MysqlConfig {

    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean(name = "hibernateTemplate")
    public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate hibernateTemplate = new HibernateTemplate();
        hibernateTemplate.setSessionFactory(sessionFactory);
        return hibernateTemplate;
    }

    @Bean(name = "simpleHibernateDao")
    public SimpleHibernateDao simpleHibernateDao() {
        return new SimpleHibernateDao();
    }
}
