package com.xczhihui.bxg.user.center.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.user.center.bean.TableVo;

/**
 * 用户相关的CRUD
 * @author Haicheng Jiang
 */
@Repository
public class ItcastUserDao {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * 添加一个用户
	 * @param user
	 * @return
	 */
	public ItcastUser addItcastUser(ItcastUser user) {
		String sql = "insert into itcast_user"
				+ "(login_name,password,salt,nike_name,sex,email,mobile,type,origin,status,last_login_date,regist_date)"
				+ " values(:loginName,:password,:salt,:nikeName,:sex,:email,:mobile,:type,:origin,:status,:lastLoginDate,:registDate)";
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		this.namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user), generatedKeyHolder);
		user.setId(generatedKeyHolder.getKey().intValue());
		return user;
	}

	/**
	 * 删除一个用户
	 * @param loginName 登录名
	 * @return
	 */
	public void delItcastUser(String loginName) {
		String sql = "delete from itcast_user where login_name=?";
		this.namedParameterJdbcTemplate.getJdbcOperations().update(sql, loginName);
	}

	/**
	 * 根据ID获取用户
	 * @param id
	 * @return
	 */
	public ItcastUser getItcastUser(int id) {
		String sql = "select * from itcast_user where id=?";
		List<ItcastUser> users = this.namedParameterJdbcTemplate.getJdbcOperations().query(sql,
				BeanPropertyRowMapper.newInstance(ItcastUser.class), id);
		ItcastUser user = null;
		if (users != null && users.size() > 0) {
			user = users.get(0);
		}
		return user;
	}

	/**
	 * 根据ID获取一批用户
	 * @param ids 用户ID集合
	 * @return
	 */
	public List<ItcastUser> getUsersByIds(Set<Integer> ids) {
		String sql = "select * from itcast_user where id in(:ids)";
		Map<String, Set<Integer>> paramMap = new HashMap<String, Set<Integer>>();
		paramMap.put("ids", ids);
		List<ItcastUser> users = this.namedParameterJdbcTemplate.query(sql, paramMap,
				BeanPropertyRowMapper.newInstance(ItcastUser.class));
		return users;
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public ItcastUser getItcastUser(String loginName) {
		String sql = "select * from itcast_user where login_name=?";
		List<ItcastUser> users = this.namedParameterJdbcTemplate.getJdbcOperations().query(sql,
				BeanPropertyRowMapper.newInstance(ItcastUser.class), loginName);
		ItcastUser user = null;
		if (users != null && users.size() > 0) {
			user = users.get(0);
		}
		return user;
	}

	/**
	 * 根据登录名获取一批用户
	 * @param loginNames 登录名集合
	 * @return
	 */
	public List<ItcastUser> getUsersByLoginNames(Set<String> loginNames) {
		String sql = "select * from itcast_user where login_name in(:loginNames)";
		Map<String, Set<String>> paramMap = new HashMap<String, Set<String>>();
		paramMap.put("loginNames", loginNames);
		List<ItcastUser> users = this.namedParameterJdbcTemplate.query(sql, paramMap,
				BeanPropertyRowMapper.newInstance(ItcastUser.class));
		return users;
	}

	/**
	 * 更新用户的nikeName,type,status,lastLoginDate,email,mobile,sex
	 * @param user
	 * @return
	 */
	public int updateItcastUser(ItcastUser user) {
		String sql = "update itcast_user set nike_name=:nikeName,email=:email,mobile=:mobile,sex=:sex,"
				+ "type=:type,status=:status where login_name=:loginName";
		return this.namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
	}
	
	public void updateStatus(String loginName,int status) {
		String sql = "update itcast_user set status=? where login_name=?";
		this.namedParameterJdbcTemplate.getJdbcOperations().update(sql, status,loginName);
	}

	/**
	 * 更新用户的密码
	 * @param id
	 * @param password
	 * @return
	 */
	public int updatePassword(int id, String password) {
		String sql = "update itcast_user set password=? where id=?";
		return this.namedParameterJdbcTemplate.getJdbcOperations().update(sql, password, id);
	}

	/**
	 * 修改登录名
	 * @param oldLoginName
	 * @param newLoginName
	 * @return
	 */
	public int updateLoginName(String oldLoginName, String newLoginName) {
		String sql = "update itcast_user set login_name=? where login_name=?";
		return this.namedParameterJdbcTemplate.getJdbcOperations().update(sql, newLoginName, oldLoginName);
	}

	/**
	 * 更新最后登录时间字段。
	 * 
	 * @param id
	 * @param date
	 */
	public void updateLastLoginDate(int id, Date date) {
		String sql = "update itcast_user set last_login_date=? where id=?";
		this.namedParameterJdbcTemplate.getJdbcOperations().update(sql, date,id);
	}
	/**
	 * 查询用户
	 * @param search 关键字
	 * @param origin 来自哪里
	 * @param status 状态
	 * @param pageNumber 当前是第几页
	 * @param pageSize 每页显示多少行
	 * @return
	 */
	public TableVo getUsers(String search,String origin,int status,int pageNumber,int pageSize){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql = "select * from itcast_user where 1=1 ";
		if (StringUtils.hasText(search)) {
			sql += (" and (login_name=:search or email=:search or mobile=:search) ");
			paramMap.put("search", search);
		}
		if (StringUtils.hasText(origin)) {
			sql += (" and origin=:origin ");
			paramMap.put("origin", origin);
		}
		if (status != 100) {
			sql += (" and status=:status ");
			paramMap.put("status", status);
		}
		
		// 统计总页数
		String totalSql = "select count(*) from ( " + sql + "  ) as total";
		int totalCount = this.namedParameterJdbcTemplate.queryForObject(totalSql, paramMap, Integer.class);
		
		int offset = (pageNumber - 1) * pageSize; // 起始下标
		offset = offset > 0 ? offset : 0;
		offset = offset < totalCount ? offset : totalCount;

		paramMap.put("offset", new Integer(offset));
		paramMap.put("pageSize", new Integer(pageSize));
		String querySql = "select * from ( select pagettttt.* from (" + sql
				+ ") pagettttt ) as total limit :offset ,:pageSize";
		
		List<ItcastUser> users = this.namedParameterJdbcTemplate.query(querySql, paramMap,
				BeanPropertyRowMapper.newInstance(ItcastUser.class));
		
		TableVo  tvo = new TableVo();
		tvo.setAaData(users);
		tvo.setiTotalRecords(totalCount);
		tvo.setiTotalDisplayRecords(totalCount);
		return tvo;
	}
}
