package com.xczhihui.bxg.online.manager.common.dao;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.BasicEntity;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.Order;
import com.xczhihui.bxg.online.manager.utils.Order.SortType;
import com.xczhihui.bxg.online.manager.utils.PageVo;
import com.xczhihui.bxg.online.manager.utils.PropertyFilter.MatchType;
import com.xczhihui.bxg.online.manager.utils.ReflectionUtils;
import com.xczhihui.bxg.online.manager.utils.SearchAnnotation;
import com.xczhihui.bxg.online.manager.utils.StringUtil;


/**
 * 公共DAO
 * @author liuxiaodong
 *
 */
public class HibernateDao<T> extends SimpleHibernateDao{
	
	protected Class<T> entityClass;
	
	public HibernateDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}
	
	public List findByGroups(Groups groups, String sql){
		List<Object> values = new LinkedList<Object>();
		sql = createSqlByGroupsAll(sql,groups, values);
		Query q = createSqlQuery(sql, values);
		return q.list();
	}
	
	public PageVo findPageByGroups(Groups groups, PageVo page, String sql){
		
		List<Object> values = new LinkedList<Object>();
		sql = createSqlByGroupsAll(sql,groups, values);
		page = findPageBySql(page, sql, values);
		List list = page.getItems();
		List<Object> items = new ArrayList<Object>();
		String fields = sql.substring(sql.indexOf("select")+6,sql.lastIndexOf("from"));
		String[] names = fields.split(",");
		for(Object object : list)
		{
			Map map = new HashMap();
			boolean bb = object instanceof Object[];
			if(bb)
			{
				Object[] objs = (Object[]) object;
				for(int i=0;i<objs.length;i++)
				{
					String name = names[i].trim();
					if(name.contains(" as "))
					{
						name = name.substring(name.indexOf(" as ")+4).trim();
					}
					if(objs[i] != null)
					{
						map.put(name, objs[i].toString());
					}
					else
					{
						map.put(name, "");
					}
				}
			}
			else
			{
				String name = names[0].trim();
				if(name.contains(" as "))
				{
					name = name.substring(name.indexOf(" as ")+4).trim();
				}
				if(object != null)
				{
					map.put(name, object.toString());
				}
				else
				{
					map.put(name, "");
				}
			}			
			items.add(map);
		}
		page.setItems(items);
		
		return page;
	}
	
	public PageVo findEntityPageByGroups(Groups groups, PageVo page){
		
		List<Object> values = new LinkedList<Object>();
		
		String hql = createHqlByGroupsAll("",groups, values);
		
		page = findPage(page, hql, values);
		
		return page;
	}
	
	public List<T> findEntityByGroups(Groups groups){

		List<Object> values = new LinkedList<Object>();
		
		String hql = createHqlByGroupsAll("",groups, values);
		
		return findListByHql(hql, values);
	}
		
	
	public PageVo findPageBySql(final PageVo page, final String sql, final List<Object> parameter) {

		Query q = createSqlQuery(sql, parameter);
		Integer totalCount  = countSqlResult(sql, parameter);
		page.setTotalCount(totalCount);
		
		int pageNo = page.getCurrentPage();
		int pageSize = page.getPageSize();
		int first = ((pageNo - 1) * pageSize) + 1;
		int totalPageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		page.setTotalPageCount(totalPageCount);
		q.setFirstResult(first - 1);
		q.setMaxResults(pageSize);
		page.setItems(q.list());
		return page;
	}
	
	@SuppressWarnings("rawtypes")
	public List findListBySql(String sql, List<Object> parameter){
		Query q = createSqlQuery(sql, parameter);
		return q.list();
	}
	
	@SuppressWarnings("rawtypes")
	public List findListByHql(String hql, List<Object> parameter){
		Query q = createQuery(hql, parameter);
		return q.list();
	}
	
	public PageVo findPage(final PageVo page, final String hql, final List<Object> parameter) {

		Query q = createQuery(hql, parameter);
		Long totalCount  = countResult(hql, parameter);
		Integer total = totalCount.intValue();
		page.setTotalCount(total);
		
		int pageNo = page.getCurrentPage();
		int pageSize = page.getPageSize();
		int first = ((pageNo - 1) * pageSize) + 1;
		q.setFirstResult(first - 1);
		q.setMaxResults(pageSize);
		int totalPageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		page.setTotalPageCount(totalPageCount);
		page.setItems(q.list());
		return page;
	}
	
	public List<String> findByGroupsAlias(Groups groups, String field){
		List<Object> objs = findByHql(groups, new String[]{field}, new String[]{field});
		List<String> result = new ArrayList<String>();
		for(Object obj : objs){
			result.add(String.valueOf(obj));
		}
		return result;
	}
	
	public List<Object> findByHql(Groups groups, String[] selectStr, String[] groupStr){
		String hql = "";
		if(groups.getOrderby()!=null){
			groups.setOrderby("");
		}
		List<Object> objects = new LinkedList<Object>();
		hql = composeString(groups, null, selectStr, groupStr, objects);
		Query q = createQuery(hql, objects);
		
		return q.list();
	}
	
	private String composeString(final Groups groups, PageVo page, String[] selectStr, String[] groupStr, List<Object> values) {
		// from段
		StringBuffer fromBuffer = new StringBuffer(" ");
		// where 段
		StringBuffer whereBufferQian = new StringBuffer(" where 1=1 ");
		StringBuffer whereBufferHou = new StringBuffer("");		
	
		// 存取相同前缀
		List<String> Alias1 = new LinkedList<String>();
		Class<T> tempclass = ReflectionUtils.getSuperClassGenricType(this.getClass());
		fromBuffer.append(" from " + tempclass.getSimpleName() + " as "+ tempclass.getSimpleName());		

		// 有条件组内容
		appendGroups(groups, fromBuffer, whereBufferQian, tempclass,Alias1, whereBufferHou, values);
		String hql = "";
		List<Order> orders = new ArrayList<Order>();
		if ((groups.getOrderbys() == null || groups.getOrderbys().length <= 0) && 
				groups.getOrderby()!=null && !groups.getOrderby().equals("")) {
			Order order = new Order();
			order.setField(groups.getOrderby());
			if (groups.isOrder()) {
				order.setSortType(SortType.ASC);
			} else {
				order.setSortType(SortType.DESC);
			}
			orders.add(order);
		}
		if (groups.getOrderbys() != null && groups.getOrderbys().length > 0) {
			for (int i = 0; i < groups.getOrderbys().length; i++) {
				Order order = new Order();
				order.setField(groups.getOrderbys()[i]);
				if (groups.getOrders()[i]) {
					order.setSortType(SortType.ASC);
				} else {
					order.setSortType(SortType.DESC);
				}
				orders.add(order);
			}
		}

		StringBuffer orderBuffer = new StringBuffer();
		if (orders.size() > 0) {
			orderBuffer = new StringBuffer(" order by ");
			appendOrder(orders, orderBuffer, tempclass, Alias1,fromBuffer, whereBufferQian);

		}
		StringBuffer slectBuffer = new StringBuffer(" select ");
		if (selectStr != null && selectStr.length != 0) {
			appendSelect(selectStr, slectBuffer, tempclass, Alias1,fromBuffer, whereBufferQian);
			if (!slectBuffer.toString().trim().equals("select")) {
				hql += slectBuffer.toString() + " ";
			}
		}
		if (whereBufferQian.toString().trim().equals("where 1=1")) {
			hql += fromBuffer.append(whereBufferQian).append("  ")
					.append(whereBufferHou).append(" ").toString();
		} else {
			hql += fromBuffer.append(whereBufferQian).append(" and ")
					.append(whereBufferHou).append(" ").toString();
		}
		if (groupStr != null && groupStr.length != 0) {
			StringBuffer groupBuffer = new StringBuffer(" group by  ");
			appendGroup(groupStr, groupBuffer, tempclass, Alias1,
					fromBuffer, whereBufferQian);
			if (!groupBuffer.toString().trim().equals("groupby")) {
				hql += groupBuffer.toString() + " ";
			}
		}
		if (orderBuffer.toString().length() != 0) {
			hql += orderBuffer;
		}
		return hql;
	}
	private void appendSelect(String[] selectStrings, StringBuffer slectBuffer,
			Class<?> baseClass,  List<String> Alias1,
			StringBuffer fromBuffer, StringBuffer whereBufferQian){
		if (selectStrings == null || selectStrings.length == 0) {

		} else {
			for (String selectStr : selectStrings) {
				String tem = "";
				if (selectStr.contains("sum")) {
					tem = selectStr.substring(selectStr.indexOf("(") + 1,selectStr.indexOf(")"));
				} else if (selectStr.contains("count")) {
					tem = selectStr.substring(selectStr.indexOf("(") + 1,selectStr.indexOf(")"));
				} else {
					tem = selectStr;
				}
				String[] strings = StringUtil.split(tem, ".");
				if (strings == null || strings.length == 0) {
					System.out.println("查询参数错误，请查证！");
				}
				Class<?> temClass = baseClass;
				Field value = null;
				StringBuffer temBuffer = new StringBuffer();
				String temName = baseClass.getSimpleName();
				for (String string : strings) {

					value = ReflectionUtils.getAllField(temClass, string);

					try {
						temClass = value.getType();
					} catch (Exception e) {
						slectBuffer.append(" '");
						slectBuffer.append(string);
						slectBuffer.append(" ' ");
						slectBuffer.append(",");
						continue;
					}
					temBuffer.append(string + ".");
					// 如果是集合
					if (ReflectionUtils.isInherit(temClass, List.class, true)) {
						SearchAnnotation searchAnnotation = value.getAnnotation(SearchAnnotation.class);
						if (searchAnnotation != null) {
							
							if (!Alias1.contains(temBuffer.subSequence(0,temBuffer.length() - 1))) {
								Alias1.add(temBuffer.subSequence(0,temBuffer.length() - 1).toString());
								fromBuffer.append(" left join ").append(temName).append(".")
										.append(string).append(" ").append(" as ").append(string);

							}
							temName = string;
							temClass = searchAnnotation.Class();
						} else {
							System.out.println("多对多关系必须要配置好注解searchAnnotation的别名");
						}
					}
					// 如果是程序
					else if (ReflectionUtils.isInherit(temClass, BasicEntity.class, false)) {
						
						if (!Alias1.contains(temBuffer.subSequence(0, temBuffer.length() - 1))) {
							Alias1.add(temBuffer.subSequence(0,temBuffer.length() - 1).toString());
							fromBuffer.append(" left join ").append(temName).append(".").append(string)
							.append(" ").append(" as ").append(string);
						}
						temName = string;
					} else {
						if (selectStr.contains("sum")) {
							slectBuffer.append(" sum( ");
						} else if (selectStr.contains("count")) {
							slectBuffer.append(" count( ");
						}
						slectBuffer.append(temName).append(".").append(string);
						if (selectStr.contains("sum") || selectStr.contains("count")) {
							slectBuffer.append(" ) ");
						}
						slectBuffer.append(",");

					}
				}
			}
			slectBuffer = slectBuffer.delete(slectBuffer.length() - 1,slectBuffer.length());
		}
	}
	
	private void appendOrder(List<Order> orders,StringBuffer orderBuffer, Class<?> baseClass, 
			List<String> Alias1, StringBuffer fromBuffer,StringBuffer whereBufferQian){

		for (Order order : orders) {
			String tem = "";
			tem = order.getField();
			String[] strings = StringUtil.split(tem, ".");
			if (strings == null || strings.length == 0) {
				System.out.println("查询参数错误，请查证！");
			}
			Class<?> temClass = baseClass;
			Field value = null;
			StringBuffer temBuffer = new StringBuffer();
			String temName = baseClass.getSimpleName();
			for (String string : strings) {
				value = ReflectionUtils.getAllField(temClass, string);
				temClass = value.getType();
				temBuffer.append(string + ".");
				// 如果是集合
				try {
					if (ReflectionUtils.isInherit(temClass, List.class, true)) {
						SearchAnnotation searchAnnotation = value.getAnnotation(SearchAnnotation.class);
						if (searchAnnotation != null) {
							
							if (!Alias1.contains(temBuffer.subSequence(0,temBuffer.length() - 1))) {
								Alias1.add(temBuffer.subSequence(0,temBuffer.length() - 1).toString());
								fromBuffer.append(" left join ").append(temName).append(".")
										.append(string).append(" ").append(" as ").append(string);
							}
							temClass = searchAnnotation.Class();
							temName = string;
						} else {
							System.out.println("多对多关系必须要配置好注解searchAnnotation的别名");
						}
					}
					// 如果是程序
					else if (ReflectionUtils.isInherit(temClass,BasicEntity.class, false)) {						
						if (!Alias1.contains(temBuffer.subSequence(0,temBuffer.length() - 1))) {
							Alias1.add(temBuffer.subSequence(0,temBuffer.length() - 1).toString());
							fromBuffer.append(" left join ").append(temName)
									.append(".").append(string).append(" ").append(" as ").append(string);
						}
						temName = string;
					} else {
						orderBuffer.append(temName).append(".").append(string);
						if (order.getSortType() == SortType.ASC) {
							orderBuffer.append(" asc ");
						} else {
							orderBuffer.append(" desc ");
						}
						orderBuffer.append(",");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		orderBuffer = orderBuffer.delete(orderBuffer.length() - 1,orderBuffer.length());
	}
	private void appendGroup(String[] groupStrings, StringBuffer groupBuffer,
			Class<?> baseClass,  List<String> Alias1,StringBuffer fromBuffer, StringBuffer whereBufferQian){
		if (groupStrings == null || groupStrings.length == 0) {

		} else {
			for (String groupStr : groupStrings) {
				String tem = "";
				tem = groupStr;
				if (tem.contains("sum") || tem.contains("count")) {
					continue;
				}
				String[] strings = StringUtil.split(tem, ".");

				if (strings == null || strings.length == 0) {
					System.out.println("查询参数错误，请查证！");
				}
				Class<?> temClass = baseClass;
				Field value = null;
				StringBuffer temBuffer = new StringBuffer();
				String temName = baseClass.getSimpleName();
				for (String string : strings) {
					value = ReflectionUtils.getAllField(temClass, string);
					temClass = value.getType();
					temBuffer.append(string + ".");
					// 如果是集合
					if (ReflectionUtils.isInherit(temClass, List.class, true)) {
						SearchAnnotation searchAnnotation = value.getAnnotation(SearchAnnotation.class);
						if (searchAnnotation != null) {
							
							if (!Alias1.contains(temBuffer.subSequence(0,temBuffer.length() - 1))) {
								Alias1.add(temBuffer.subSequence(0,temBuffer.length() - 1).toString());
								fromBuffer.append(" left join ").append(temName).append(".")
										.append(string).append(" ").append(" as ").append(string);

							}
							temClass = searchAnnotation.Class();
							temName = string;
						} else {
							System.out.println("多对多关系必须要配置好注解searchAnnotation的别名");
						}
					}else if (ReflectionUtils.isInherit(temClass,BasicEntity.class, false)) {						
						if (!Alias1.contains(temBuffer.subSequence(0,temBuffer.length() - 1))) {
							Alias1.add(temBuffer.subSequence(0,temBuffer.length() - 1).toString());
							fromBuffer.append(" left join ").append(temName)
									.append(".").append(string).append(" ").append(" as ").append(string);
						}
						temName = string;
					} else {
						groupBuffer.append(temName).append(".").append(string);
						groupBuffer.append(",");
					}
				}

			}
			groupBuffer = groupBuffer.delete(groupBuffer.length() - 1,groupBuffer.length());

		}
	}
	
	@SuppressWarnings("unchecked")
	public T find(String id){
		return (T) getSession().get(entityClass, id);
	}

	/**
	 * @param propertyName
	 * @param value
	 * @return  找到多条只返回第一条　没有找倒返回空
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);		
		List<T> lists =  createCriteria(criterion).list();		
		if (!lists.isEmpty())
		{
			return lists.get(0);
		}
		
		return null;			
	}
	
	private Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	protected Integer countSqlResult(final String sql, final List<Object> parameter) {
		String countSql = prepareCount(sql);
		try {
			BigInteger count = findUniqueBySql(countSql, parameter);
			
			return count.intValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, sql is:"
					+ countSql, e);
		}
	}
	
	protected Long countResult(final String hql, final List<Object> parameter) {
		String countHql = prepareCount(hql);
		try {
			Long count = findUnique(countHql, parameter);
			
			return count;
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, sql is:"
					+ countHql, e);
		}
	}
	
	protected <X> X findUnique(final String hql, final List<Object> parameter) {
		return (X) createQuery(hql, parameter).uniqueResult();
	}
	
	protected <X> X findUniqueBySql(final String sql, final List<Object> parameter) {
		return (X) createSqlQuery(sql, parameter).uniqueResult();
	}
	
	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @param hql
	 * @param list
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final List<Object> parameter) {
		return createQuery(hql, parameter).executeUpdate();
	}
	
	
	private String prepareCount(String query) {
		// select子句与order by子句会影响count查询,进行简单的排除.
		query = "from " + StringUtils.substringAfter(query, "from");
		query = StringUtils.substringBefore(query, "order by");

		String count = "select count(*) " + query;
		return count;
	}
	
	
	private Query createSqlQuery(final String sql, final List<Object> parameter) {
		
		Query query = getSession().createSQLQuery(sql);
		if (parameter != null) {
			for (int i = 0; i < parameter.size(); i++) {
				query.setParameter(i, parameter.get(i));
			}
		}
		return query;
	}
	

	private Query createQuery(final String hql, final List<Object> parameter) {
		
		Query query = getSession().createQuery(hql);
		if (parameter != null) {
			for (int i = 0; i < parameter.size(); i++) {
				query.setParameter(i, parameter.get(i));
			}
		}
		return query;
	}
	
	public Session getSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}
	
	private String createHqlByGroupsAll(String hql,Groups groups, List<Object> values) {
		// from段
		StringBuffer fromBuffer = new StringBuffer(" ");
		// where 段
		StringBuffer whereBufferQian = new StringBuffer(" where 1=1 ");
		StringBuffer whereBufferHou = new StringBuffer("");
		
		// 存取相同前缀
		List<String> Alias1 = new LinkedList<String>();
		Class<T> tempclass = ReflectionUtils.getSuperClassGenricType(this.getClass());
		fromBuffer.append(" from " + tempclass.getSimpleName() + " as " + tempclass.getSimpleName());		
	
		try {
			appendGroups(groups, fromBuffer, whereBufferQian, tempclass, Alias1, whereBufferHou, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		hql = fromBuffer.toString()+whereBufferQian.toString()+whereBufferHou.toString();
		
		String temp = "";
		if(groups.getOrderbys()!=null && groups.getOrderbys().length>0) {
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < groups.getOrderbys().length; i++) {// groups接受orderby数组进行多条件排序
				if(i==0){
					sBuffer.append(" order by ");
				}
				// order by 别名处理
				temp = groups.getOrderbys()[i];

				if (temp.isEmpty())
					continue;
				
				if (groups.getOrders()[i]){
					sBuffer.append(tempclass.getSimpleName()+"."+temp+" asc,");
				}else{
					sBuffer.append(tempclass.getSimpleName()+"."+temp+" desc,");
				}					
			}
			hql += sBuffer.deleteCharAt(sBuffer.length()-1).toString();
		}else if (null != groups.getOrderby()	&& !groups.getOrderby().trim().equals("")) {
			// 处理order by的别名
			temp = groups.getOrderby();			

			if (groups.isOrder()){
				hql += " order by "+tempclass.getSimpleName()+"."+temp+" asc";				
			}else{
				hql += " order by "+tempclass.getSimpleName()+"."+temp+" desc";
			}				
		}
		
		return hql;
	}
	
	private void appendGroups(Groups groups, StringBuffer fromBuffer,
			StringBuffer whereBufferQian, Class<?> tempclass, List<String> Alias1,
			StringBuffer whereBufferHou, List<Object> values){
		if (groups.getGroupList() == null) {
			System.out.println("groups的GroupList不能为空！");
		} else {
			if (groups.getChildGroups2() != null && groups.getChildGroups2().size() > 0
					|| groups.getChildrelation()!=null) {
				for (Group group : groups.getGroupList()) {
					appendGroup(group, fromBuffer, whereBufferQian,
							tempclass, Alias1, whereBufferHou, values);
				}
				for (Groups tGroup : groups.getChildGroups2()) {
					if (tGroup.getChildrelation() == MatchType.AND) {
						whereBufferHou.append(" and ( ");
						appendGroups(tGroup, fromBuffer, whereBufferQian,
								 tempclass, Alias1, whereBufferHou, values);
						whereBufferHou.append(" )");
					} else if (tGroup.getChildrelation() == MatchType.OR) {
						whereBufferHou.append(" or ( ");
						appendGroups(tGroup, fromBuffer, whereBufferQian,
								tempclass, Alias1, whereBufferHou, values);
						whereBufferHou.append(" )");
					}
				}
			}else{
				StringBuffer whereAnd = new StringBuffer();
				StringBuffer whereOr = new StringBuffer();
				List<Object> tempList = new ArrayList<Object>();
				for (Group group : groups.getGroupList()) {
					if(group.getRelation().equals(MatchType.AND)){
						appendGroup(group, fromBuffer, whereBufferQian,
								tempclass, Alias1, whereAnd, values);
					}else{
						if(whereOr.toString().equals("")){
							whereOr.append(" and ( ");
						}						
						appendGroup(group, fromBuffer, whereBufferQian,
								tempclass, Alias1, whereOr, values);
						tempList.add(group.getPropertyValue1());
						values.remove(group.getPropertyValue1());
					}
					
				}
				if(!whereOr.toString().equals("")){
					whereOr.append(" ) ");
				}
				for(Object temp : tempList){
					values.add(temp);
				}
				whereBufferHou.append(whereAnd).append(whereOr);
			}
		}
	}
	
	private void appendGroup(Group group, StringBuffer fromBuffer,
			StringBuffer whereBufferQian,Class<?> baseClass, List<String> Alias1,
			StringBuffer whereBufferHou, List<Object> values) {

		String[] strings = StringUtil.split(group.getPropertyName(), ".");
		if (strings == null || strings.length == 0) {
			System.out.println("查询参数错误，请查证！");
		}
		Class<?> temClass = baseClass;
		Field value = null;
		// 存放临时字符串
		StringBuffer temBuffer = new StringBuffer();
		// 得到基类的别名
		String temName = baseClass.getSimpleName();
		// 循环（）
		boolean isOver = false;
		List<String> tempStrs = new ArrayList<String>();
		int t = 0;
		for (String string : strings) {
			boolean isSame = false;
			// 有一样的名字
			if (tempStrs.contains(string)) {
				t++;
				isSame = true;
			}

			tempStrs.add(string);

			// 反射得到字段
			value = ReflectionUtils.getAllField(temClass, string);
			// 赋值到基类
			temClass = value.getType();
			// 加上全名
			String alisStr = "";
			if (!isSame)
				alisStr = string;
			else {
				alisStr = string + t;

			}
			temBuffer.append(alisStr + ".");
			// 如果是集合
			if (ReflectionUtils.isInherit(temClass, List.class, true)) {
				SearchAnnotation searchAnnotation = value.getAnnotation(SearchAnnotation.class);
				if (searchAnnotation != null) {// 查看别名是否存在
					
					if (!Alias1.contains(temBuffer.subSequence(0,temBuffer.length() - 1))) {
						Alias1.add(temBuffer.subSequence(0,temBuffer.length() - 1).toString());
						fromBuffer.append(" left join ").append(temName)
								.append(".").append(alisStr).append(" ").append(" as ").append(alisStr);

					}
					temClass = searchAnnotation.Class();
					temName = alisStr;
				} else {
					System.out.println("多对多关系必须要配置好注解searchAnnotation的别名");
				}
			}else if (ReflectionUtils.isInherit(temClass, BasicEntity.class, false)) {// 如果是类
				String propertyValue=group.getPropertyName().toString();
				if(temName.equals(strings[0])){
					propertyValue = propertyValue.substring(temName.length()+1);
				}
				isOver = buildCase(group, whereBufferHou, temName, propertyValue);
				
				break;
			} else {
				isOver = buildCase(group, whereBufferHou, temName, alisStr);
			}
		}//遍历结束
		
		//没有到最后（可能是 NULL/ NOT NULL）
		if (!isOver) {
			String matchCase = "";
			if(whereBufferHou.toString().trim().length()>0){
				matchCase = whereBufferHou.toString().trim().substring(
						whereBufferHou.toString().trim().length()-1, whereBufferHou.toString().trim().length());
			}
			if (group.getMatchType() == MatchType.NULL || group.getMatchType() == MatchType.NOTNULL) {
				whereBufferHou.append(" ");
				if (group.getRelation() == MatchType.AND) {
					try {						
						if (!matchCase.equals("(")) {
							whereBufferHou.append(" and ");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						if (!matchCase.equals("(")) {
							whereBufferHou.append(" or ");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				switch (group.getMatchType()) {
				case NOTIN:
					whereBufferHou.append(temName).append(" not in  ");

					break;
				case NOTNULL:
					whereBufferHou.append(temName).append(" is not null ");
					break;

				case NULL:
					whereBufferHou.append(temName).append(" is null ");
					break;
				}
			} else {
				System.out.println("拼接处的name不能以对象结尾，统一要以基本类型结尾！");
			}
		}
		
		addValues(group, whereBufferHou, values);
	}
	
	private boolean buildCase(Group group, StringBuffer whereBufferHou, String temName, String alisStr) {
		boolean isOver;
		isOver = true;
		whereBufferHou.append(" ");
		String matchCase = "";
		if(whereBufferHou.toString().trim().length()>0){
			matchCase = whereBufferHou.toString().trim().substring(
					whereBufferHou.toString().trim().length()-1, whereBufferHou.toString().trim().length());
		}
		if (group.getRelation() == MatchType.AND) {
			if (!matchCase.equals("(")) {
				whereBufferHou.append(" and ");
			}
		} else {
			if (!matchCase.equals("(")) {
				whereBufferHou.append(" or ");
			}	
		}
		
		matchCase(group, whereBufferHou, temName, alisStr);
		
		return isOver;
	}
	
	@SuppressWarnings("incomplete-switch")
	private void matchCase(Group group, StringBuffer whereBufferHou,
			String temName, String alisStr) {
		switch (group.getMatchType()) {
		case EQ:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" = ");
			break;
		case LIKE:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" like ");

			break;

		case LT:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" < ");
			break;
		case LE:
			if (group.getRelation() == MatchType.AND)
				whereBufferHou.append(temName).append(".").append(alisStr).append(" <= ");
			break;
		case GT:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" > ");

			break;
		case GE:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" >= ");

			break;
		case NE:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" <> ");
			break;

		case IN:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" in ");
			break;
		case NOTIN:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" not in  ");

			break;
		case BETWEEN:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" between  ");

			break;
		case NULL:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" is null ");
			break;
		case NOTNULL:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" is not null ");
			break;
		}
	}
	
	private String createSqlByGroupsAll(String sql,Groups groups, List<Object> values) {
		// from段
		StringBuffer fromBuffer = new StringBuffer(" ");
		// where 段
		StringBuffer whereBufferQian = new StringBuffer(" where 1=1 ");
		StringBuffer whereBufferHou = new StringBuffer("");
		
		// 存取相同前缀
		List<String> Alias1 = new LinkedList<String>();
		fromBuffer.append(sql);		
	
		try {
			appendGroups2(groups, fromBuffer, whereBufferQian, Alias1, whereBufferHou, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sql = fromBuffer.toString()+whereBufferQian.toString()+whereBufferHou.toString();
		
		if(groups.getGroupby() != null && !"".equals(groups.getGroupby()))
		{
			sql += " group by "+groups.getGroupby();
		}
		
		String temp = "";
		if(groups.getOrderbys()!=null && groups.getOrderbys().length>0) {
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < groups.getOrderbys().length; i++) {// groups接受orderby数组进行多条件排序
				if(i==0){
					sBuffer.append(" order by ");
				}
				// order by 别名处理
				temp = groups.getOrderbys()[i];

				if (temp.isEmpty())
					continue;
				
				if (groups.getOrders()[i]){
					sBuffer.append(temp+" asc,");
				}else{
					sBuffer.append(temp+" desc,");
				}					
			}
			sql += sBuffer.deleteCharAt(sBuffer.length()-1).toString();
		}else if (null != groups.getOrderby()	&& !"".equals(groups.getOrderby().trim())) {
			// 处理order by的别名
			temp = groups.getOrderby();

			if (groups.isOrder()){
				sql += " order by "+temp+" asc";
			}else{
				sql += " order by "+temp+" desc";
			}				
		}
		
		return sql;
	}
	
	private void appendGroups2(Groups groups, StringBuffer fromBuffer,
			StringBuffer whereBufferQian, List<String> Alias1,
			StringBuffer whereBufferHou, List<Object> values){
		if (groups.getGroupList() == null) {
			System.out.println("groups的GroupList不能为空！");
		} else {
			if (groups.getChildGroups2() != null && !groups.getChildGroups2().isEmpty() || groups.getChildrelation()!=null) {
				for (Group group : groups.getGroupList()) {
					appendGroup2(group, fromBuffer, whereBufferQian,Alias1, whereBufferHou, values);
				}
				for (Groups tGroup : groups.getChildGroups2()) {
					if (tGroup.getChildrelation() == MatchType.AND) {
						whereBufferHou.append(" and ( ");
						appendGroups2(tGroup, fromBuffer, whereBufferQian,Alias1, whereBufferHou, values);
						whereBufferHou.append(" )");
					} else if (tGroup.getChildrelation() == MatchType.OR) {
						whereBufferHou.append(" or ( ");
						appendGroups2(tGroup, fromBuffer, whereBufferQian,Alias1, whereBufferHou, values);
						whereBufferHou.append(" )");
					}
				}
			}else{
				StringBuffer whereAnd = new StringBuffer();
				StringBuffer whereOr = new StringBuffer();
				List<Object> tempList = new ArrayList<Object>();
				for (Group group : groups.getGroupList()) {
					if(group.getRelation().equals(MatchType.AND)){
						appendGroup2(group, fromBuffer, whereBufferQian, Alias1, whereBufferHou, values);
					}else{
						if(whereOr.toString().equals("")){
							whereOr.append(" and ( ");
						}
						tempList.add(group.getPropertyValue1());
						values.remove(group.getPropertyValue1());
					}
					
				}
				if(!whereOr.toString().equals("")){
					whereOr.append(" ) ");
				}
				for(Object temp : tempList){
					values.add(temp);
				}
				whereBufferHou.append(whereAnd).append(whereOr);
			}
		}
	}
	
	private void appendGroup2(Group group, StringBuffer fromBuffer,
			StringBuffer whereBufferQian,List<String> Alias1,
			StringBuffer whereBufferHou, List<Object> values) {
		
		String propertyName = group.getPropertyName();
		
		boolean isOver = buildCase2(group, whereBufferHou, propertyName);
		
		//没有到最后（可能是 NULL/ NOT NULL）
		if (!isOver) {
			String matchCase = "";
			if(whereBufferHou.toString().trim().length()>0){
				matchCase = whereBufferHou.toString().trim().substring(
						whereBufferHou.toString().trim().length()-1, whereBufferHou.toString().trim().length());
			}	
			if (group.getMatchType() == MatchType.NULL || group.getMatchType() == MatchType.NOTNULL) {
				whereBufferHou.append(" ");
				if (group.getRelation() == MatchType.AND) {
					try {
						if (!matchCase.equals("(")) {
							whereBufferHou.append(" and ");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						if (!matchCase.equals("(")) {
							whereBufferHou.append(" or ");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
						
				switch (group.getMatchType()) {
				case NOTIN:
					whereBufferHou.append(propertyName).append(" not in  ");							
					break;
				case NOTNULL:
					whereBufferHou.append(propertyName).append(" is not null ");
					break;
				case NULL:
					whereBufferHou.append(propertyName).append(" is null ");
					break;
				}
			} else {
				System.out.println("拼接处的name不能以对象结尾，统一要以基本类型结尾！");
			}
		}
				
		addValues(group, whereBufferHou, values);
	}

	
	private void addValues(Group group, StringBuffer whereBufferHou, List<Object> values){
		// 判断错误
		if (group.getPropertyValue1() == null && !(group.getMatchType() == MatchType.NULL
				|| group.getMatchType() == MatchType.NOTNULL)) {
			System.out.println("传入值为空，但并不是查询NULL OR NOT NULL 请查证！");
		}else if (group.getPropertyValue1() != null && !"".equals(group.getPropertyValue1())) {
			// 是in 或not in
			if (group.getMatchType() == MatchType.IN || group.getMatchType() == MatchType.NOTIN) {
				Collection<?> collection = (Collection<?>) group.getPropertyValue1();
				StringBuffer inBuffer = new StringBuffer(" ( ");
				for (Object object : collection) {
					values.add(object);
					inBuffer.append(" ? ,");
				}
				inBuffer = inBuffer.delete(inBuffer.length() - 1,inBuffer.length());
				inBuffer.append(" ) ");
				whereBufferHou.append(inBuffer);
			}else if (group.getMatchType() == MatchType.BETWEEN) {
				// 如果是bwt
				if (group.getPropertyValue2() == null) {
					System.out.println("第二个参数不能为空");
				}
				if (group.getPropertyValue1().getClass() == Date.class || group.isDate()
						|| group.getPropertyValue1().getClass() == java.sql.Timestamp.class) {
					values.add(group.getPropertyValue1());
					values.add(group.getPropertyValue2());
					whereBufferHou.append(" ? and ? ");
				} else {
					System.out.println("BETWEEN 规定只能用于时间，数字请用大于小于进行");
				}

			}else {
				if (group.getPropertyValue1().getClass() == Date.class) {
					values.add(group.getPropertyValue1());
					whereBufferHou.append(" ? ");
				} else if (group.isDate()) {
					values.add(group.getPropertyValue1());
					whereBufferHou.append(" ? ");
				} else if (group.getPropertyValue1().getClass() == java.sql.Timestamp.class) {
					whereBufferHou.append(" ? ");
				} else {
					whereBufferHou.append(" ? ");
					if (group.getMatchType() == MatchType.LIKE) {
						values.add("%"+group.getPropertyValue1()+"%");
					} else {
						values.add(group.getPropertyValue1());						
					}
				}
			}
		}//PropertyValue1()有值的情况   结束	
	}

	private boolean buildCase2(Group group, StringBuffer whereBufferHou, String alisStr) {
		boolean isOver;
		isOver = true;
		whereBufferHou.append(" ");
		String matchCase = "";
		if(whereBufferHou.toString().trim().length()>0){
			matchCase = whereBufferHou.toString().trim().substring(
					whereBufferHou.toString().trim().length()-1, whereBufferHou.toString().trim().length());
		}
		if (group.getRelation() == MatchType.AND) {
			if (!matchCase.equals("(")) {
				whereBufferHou.append(" and ");
			}
		} else {
			if (!matchCase.equals("(")) {
				whereBufferHou.append(" or ");
			}	
		}
		
		matchCase(group, whereBufferHou, alisStr);
		
		return isOver;
	}
	/**
	 * 
	 * @param group
	 * @param whereBufferHou
	 * @param temName  别名
	 * @param alisStr
	 */
	private void matchCase(Group group, StringBuffer whereBufferHou,String alisStr) {
		switch (group.getMatchType()) {
		case EQ:
			whereBufferHou.append(alisStr).append(" = ");
			break;
		case LIKE:
			whereBufferHou.append(alisStr).append(" like ");

			break;

		case LT:
			whereBufferHou.append(alisStr).append(" < ");
			break;
		case LE:
			if (group.getRelation() == MatchType.AND)
				whereBufferHou.append(alisStr).append(" <= ");
			break;
		case GT:
			whereBufferHou.append(alisStr).append(" > ");

			break;
		case GE:
			whereBufferHou.append(alisStr).append(" >= ");

			break;
		case NE:
			whereBufferHou.append(alisStr).append(" <> ");
			break;

		case IN:
			whereBufferHou.append(alisStr).append(" in ");
			break;
		case NOTIN:
			whereBufferHou.append(alisStr).append(" not in  ");

			break;
		case BETWEEN:
			whereBufferHou.append(alisStr).append(" between  ");

			break;
		case NULL:
			whereBufferHou.append(alisStr).append(" is null ");
			break;
		case NOTNULL:
			whereBufferHou.append(alisStr).append(" is not null ");
			break;
		}
	}

}
