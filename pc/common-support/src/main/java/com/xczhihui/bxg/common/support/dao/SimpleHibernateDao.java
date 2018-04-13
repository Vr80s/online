package com.xczhihui.bxg.common.support.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Table;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.domain.BasicEntity;
import com.xczhihui.bxg.common.util.bean.Page;

/**
 * 完成基本操作的DAO。使用该类前请确保已通过setDataSource和setSessionFactory方法注入必须的属性。
 * 在spring容器中注册名为simpleHibernateDao，
 * 并使用名为dataSource和sessionFactory作为数据源和hibernate的SessionFactory。
 * 如果不是请覆写方法setDataSource和setSessionFactory方法。
 *
 * @author 李勇 create on 2015-11-05
 */
@Repository("simpleHibernateDao")
public class SimpleHibernateDao {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 修改数据源
	 *
	 * @param dataSource
	 */
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		logger.debug("Inject javax.sql.DataSource by name 'dataSource'");
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * 修改hibernate的sessionFactory
	 *
	 * @param sessionFactory
	 */
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		logger.debug("Inject org.hibernate.SessionFactory by name 'sessionFactory'");
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * 保存实体，保存后实体的主键有值。
	 *
	 * @param entity
	 */
	public void save(Object entity) {
		this.hibernateTemplate.save(entity);
	}

	/**
	 * 删除实体
	 *
	 * @param entity
	 */
	public void delete(Object entity) {
		if (entity != null) {
			this.hibernateTemplate.delete(entity);
		} else {
			logger.warn("entity is null!");
		}
	}

	/**
	 * 根据ID删除实体.
	 *
	 * @param id
	 */
	public <T> T delete(Serializable id, Class<T> entityClass) {
		T entity = this.hibernateTemplate.get(entityClass, id);
		if (entity != null) {
			this.hibernateTemplate.delete(entity);
		} else {
			logger.warn("{}.{} not found!", entityClass.getName(), id);
		}
		return entity;
	}

	/**
	 * 更新实体
	 *
	 * @param entity
	 */
	public void update(Object entity) {
		this.hibernateTemplate.update(entity);
	}

	/**
	 * 保存或更新，id有值更新否则保存。
	 *
	 * @param entity
	 */
	public void saveOrUpdate(Object entity) {
		this.hibernateTemplate.saveOrUpdate(entity);
	}

	/**
	 * 批量保存或更新实体。
	 *
	 * @param entities
	 */
	public <T> void saveOrUpdate(final List<T> entities) {
		this.hibernateTemplate.executeWithNativeSession(new HibernateCallback<T>() {

			@Override
			public T doInHibernate(Session session) throws HibernateException {
				int i = 0;
				for (T t : entities) {
					session.saveOrUpdate(t);
					i++;
					if (i % 50 == 0) {
						session.flush();
						session.clear();
					}
				}
				return null;
			}
		});
	}

	/**
	 * 根据ID获取一个实体。
	 *
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T get(Serializable id, Class<T> clazz) {
		return this.hibernateTemplate.get(clazz, id);
	}

	/**
	 * 获取所有的指定实体。
	 *
	 * @param clazz
	 *            实体类型。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(Class<T> clazz) {
		return (List<T>) this.hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(clazz).list();
	}

	/**
	 * 根据查询条件查询。
	 *
	 * @param detachedCriteria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findEntities(DetachedCriteria detachedCriteria) {
		return (List<T>) this.hibernateTemplate.findByCriteria(detachedCriteria);
	}

	/**
	 * 更加查询条件获取一个实体。
	 *
	 * @param detachedCriteria
	 * @return
	 */
	public <T> T findEntity(DetachedCriteria detachedCriteria) {
		List<T> ts = this.findEntities(detachedCriteria);
		if (ts != null && ts.size() > 0) {
			return ts.get(0);
		}
		return null;
	}

	/**
	 * 分页获取符合条件的实体。
	 *
	 * @param detachedCriteria
	 * @param limit
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findEntities(DetachedCriteria detachedCriteria, final int limit, final int count) {
		return (List<T>) this.hibernateTemplate.findByCriteria(detachedCriteria, limit, count);
	}

	/**
	 * 通过HQL查询实体。
	 *
	 * @param hql
	 *            如:from User where name=? and age=?
	 * @param params
	 *            与hql中？对应的参数值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByHQL(String hql, Object... params) {
		return (List<T>) this.hibernateTemplate.find(hql, params);
	}

	/**
	 *
	 * @param hql
	 *            如:from User where name=? and age=?
	 * @param params
	 *            与hql中？对应的参数值
	 * @return
	 */
	public <T> T findByHQLOne(String hql, Object... params) {
		List<T> ts = this.findByHQL(hql, params);
		if (ts != null && ts.size() > 0) {
			return ts.get(0);
		}
		return null;
	}

	/**
	 * 将sql查询的结果转成一个整数。
	 *
	 * @param sql
	 * @param args
	 * @return
	 */
	public int queryForInt(String sql, Object... args) {
		Number number = this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, args, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public Double queryForDouble(String sql, Object... args) {
		Number d = this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, args, Double.class);
		return (d != null ? d.doubleValue(): 0);
	}

	/**
	 * 分页查询
	 *
	 * @param criteria
	 * @param pageNumber
	 *            页码(表示第几页，从1开始)
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> findPageByCriteria(final DetachedCriteria detachedCriteria, final int pageNumber,
			final int pageSize) {
		Session session = getCurrentSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		int totalCount = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(null);
		int offset = (pageNumber - 1) * pageSize;
		offset = offset > 0 ? offset : 0;
		offset = offset < totalCount ? offset : totalCount;
		List<T> items = criteria.setFirstResult(offset).setMaxResults(pageSize).list();
		Page<T> ps = new Page<T>(items, totalCount, pageSize, pageNumber);
		return ps;
	}

	/**
	 * 分页通过HQL查询实体。
	 *
	 * @param hql
	 *            如：from TestBean where name=:name
	 * @param paramMap
	 *            查询条件变量名和值,如：paramMap.put("name","张三")
	 * @param pageNumber
	 *            页码(表示第几页，从1开始)
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> findPageByHQL(final String hql, final Map<String, Object> paramMap, final int pageNumber,
			final int pageSize) {

		// 变量转换
		final List<String> paramList = new ArrayList<String>();
		final List<Object> valueList = new ArrayList<Object>();
		Set<String> keys = paramMap.keySet();
		for (String k : keys) {
			paramList.add(k);
			valueList.add(paramMap.get(k));
		}

		final int totalCount = this.getTotal(hql, paramList, valueList);

		List<T> list = this.hibernateTemplate.executeWithNativeSession(new HibernateCallback<List<T>>() {
			@Override
            public List<T> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);

				// 设置查询条件
				for (int i = 0; i < paramList.size(); i++) {
					String paramStr = (String) paramList.get(i);
					Object valueObj = (Object) valueList.get(i);
					query.setParameter(paramStr, valueObj);
				}

				// 计算查询起始数
				int offset = (pageNumber - 1) * pageSize;
				offset = offset > 0 ? offset : 0;
				offset = offset < totalCount ? offset : totalCount;

				query.setFirstResult(offset);
				query.setMaxResults(pageSize);
				List<T> tmpList = query.list();
				return tmpList;
			}
		});

		Page<T> ps = new Page<T>(list, totalCount, pageSize, pageNumber);
		return ps;
	}

	/**
	 * 分页通过SQL查询实体。
	 *
	 * @param sql
	 *            如：select * from test_bean where name=:name
	 * @param paramMap
	 *            如：paramMap.put("name","11");
	 * @param elementType
	 *            如：TestBean.class
	 * @param pageNumber
	 *            页码(表示第几页，从1开始)
	 * @param pageSize
	 * @return
	 */
	public <T> Page<T> findPageBySQL(String sql, Map<String, Object> paramMap, Class<T> elementType, int pageNumber,
			int pageSize) {

		// 统计总页数
		String totalSql = "select count(*) from ( " + sql + "  ) as total";
		int totalCount = this.namedParameterJdbcTemplate.queryForObject(totalSql, paramMap, Integer.class);

		// 组装查询语句
		if (null == paramMap) {
			paramMap = new HashMap<String, Object>();
		}
		int offset = (pageNumber - 1) * pageSize; // 起始下标
		offset = offset > 0 ? offset : 0;
		offset = offset < totalCount ? offset : totalCount;

		paramMap.put("offset", new Integer(offset));
		paramMap.put("pageSize", new Integer(pageSize));

		String querySql = "select * from ( select pagettttt.* from (" + sql
				+ ") pagettttt ) as total limit :offset ,:pageSize";

		List<T> list = this.namedParameterJdbcTemplate.query(querySql, paramMap,
				BeanPropertyRowMapper.newInstance(elementType));

		Page<T> ps = new Page<T>(list, totalCount, pageSize, pageNumber);
		return ps;
	}

	/**
	 * 根据实体的ID获取一批实体
	 *
	 * @param clazz
	 * @param entityIds
	 * @return
	 */
	public <T> List<T> findEntities(Class<T> clazz, Set<? extends Serializable> entityIds) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		dc.add(Restrictions.in("id", entityIds));
		return this.findEntities(dc);
	}

	/**
	 * 根据单个属性查一批实体
	 *
	 * @param clazz
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public <T> List<T> findEntitiesByProperty(Class<T> clazz, String propertyName, Object propertyValue) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		dc.add(Restrictions.eq(propertyName, propertyValue));
		return this.findEntities(dc);
	}

	/**
	 * 根据单个属性查一个实体
	 *
	 * @param clazz
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public <T> T findOneEntitiyByProperty(Class<T> clazz, String propertyName, Object propertyValue) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		dc.add(Restrictions.eq(propertyName, propertyValue));
		return this.findEntity(dc);
	}

	/**
	 * 命名参数查询。
	 *
	 * @param clazz
	 *            返回list中元素的类型
	 * @param sql
	 *            如:select * from user where name=:name and age=:age
	 * @param paramMap
	 *            参数，key为sql中的参数名如:name,age
	 * @return
	 */
	public <T> List<T> findEntitiesByJdbc(Class<T> clazz, String sql, Map<String, ?> paramMap) {
		return this.namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(clazz));
	}
	public <T> T findEntityByJdbc(Class<T> clazz, String sql, Map<String, ?> paramMap) {
		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(clazz));
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return this.namedParameterJdbcTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	/**
	 * 当前上下文中得session
	 *
	 * @return
	 */
	protected Session getCurrentSession() {
		return this.hibernateTemplate.getSessionFactory().getCurrentSession();
	}

	// 获取总记录数，带参数列表
	protected int getTotal(String queryStr, final List<String> paramList, final List<Object> valueList) {
		int start = queryStr.indexOf("from");
		String from = queryStr.substring(start, queryStr.length());
		if (from.indexOf("order") > -1) {
			from = from.substring(0, from.indexOf("order"));
		}
		String countStr = "select count(*) " + from;
		@SuppressWarnings("rawtypes")
		List result = this.hibernateTemplate.findByNamedParam(countStr, list2Array(paramList), valueList.toArray());
		if (null != result && !result.isEmpty()) {
			return ((Long) result.get(0)).intValue();
		} else {
			return 0;
		}
	}

	/**
	 * 将字符串列表转换成字符串数组
	 *
	 * @param list
	 *            List 字符串列表
	 * @return String[]
	 */
	private static String[] list2Array(List<String> list) {
		String[] strs = new String[list.size()];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = list.get(i);
		}
		return strs;
	}

	/**
	 * 逻辑删除一批实体。
	 *
	 * @param clazz
	 * @param ids
	 * @return
	 */
	public <T extends BasicEntity> int deleteLogics(Class<T> clazz, List<String> ids) {
		return this.updateDeletes(clazz, ids, true);
	}

	/**
	 * 修改is_delete字段状态。
	 *
	 * @param domainClass
	 *            实体类
	 * @param ids
	 * @param isDelete
	 *            是否删除
	 * @return
	 */
	public <T extends BasicEntity> int updateDeletes(Class<T> clazz, List<String> ids, boolean isDelete) {
		if (clazz == null || ids == null || ids.size() < 1) {
			return 0;
		}
		Table table = clazz.getAnnotation(Table.class);
		String tabName = table.name();
		String sql = String.format("update %s set is_delete=%d where id in (:ids)", tabName, isDelete ? 1 : 0);
		logger.debug("updateDelete:{}", sql);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("ids", ids);
		return this.namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 逻辑删除一个实体
	 *
	 * @param id
	 * @param clazz
	 */
	public <T extends BasicEntity> T deleteLogic(Serializable id, Class<T> clazz) {
		return this.updateDelete(id, clazz, true);
	}

	/**
	 * 逻辑删除一个实体
	 *
	 * @param id
	 * @param clazz
	 */
	public <T extends BasicEntity> void deleteLogic(BasicEntity entity) {
		this.updateDelete(entity, true);
	}

	/**
	 * 修改实体的删除属性
	 *
	 * @param id
	 * @param clazz
	 * @param isDelete
	 */
	public <T extends BasicEntity> T updateDelete(Serializable id, Class<T> clazz, boolean isDelete) {
		T e = this.get(id, clazz);
		this.updateDelete(e, isDelete);
		return e;
	}

	/**
	 * 修改实体的删除属性
	 *
	 * @param entity
	 * @param isDelete
	 */
	public <T extends BasicEntity> void updateDelete(BasicEntity entity, boolean isDelete) {
		if (entity.isDelete() != isDelete) {
			entity.setDelete(isDelete);
			this.update(entity);
		}
	}

	/**
	 * 获取有效（未逻辑删除）的实体。
	 *
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T extends BasicEntity> T getValid(Serializable id, Class<T> clazz) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		dc.add(Restrictions.eq("id", id));
		dc.add(Restrictions.eq("isDelete", false));
		return this.findEntity(dc);
	}
}
