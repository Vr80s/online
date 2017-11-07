package com.xczhihui.bxg.user.center.dao;

import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.user.center.bean.LoginLimit;
import com.xczhihui.user.center.bean.TableVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;


@Repository
public class LoginLimitDao {
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
	public LoginLimit addLoginList(LoginLimit ll) {
		String sql = "insert into login_limit"
				+ "(login_name,pc_sign,app_sign,h5_sign,pc_info,app_info,h5_info,pc_last_time,app_last_time,h5_last_time)"
				+ " values(:loginName,:pcSign,:appSign,:h5Sign,:pcInfo,:appInfo,:h5Info,:pcLastTime,:appLastTime,:h5LastTime)";
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		this.namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(ll), generatedKeyHolder);
		ll.setId(generatedKeyHolder.getKey().intValue());
		return ll;
	}

	/**
	 * 根据loginName获取
	 * @param id
	 * @return
	 */
	public LoginLimit getLoginLimitByLoginName(String loginName) {
		String sql = "select * from login_limit where login_name=?";
		List<LoginLimit> lls = this.namedParameterJdbcTemplate.getJdbcOperations().query(sql,
				BeanPropertyRowMapper.newInstance(LoginLimit.class), loginName);
		LoginLimit ll = null;
		if (lls != null && lls.size() > 0) {
			ll = lls.get(0);
		}
		return ll;
	}

	/**
	 * 更新
	 * @param user
	 * @return
	 */
	public int updateLoginLimit(LoginLimit ll) {
//		+ "(pc_sign,app_sign,h5_sign,pc_info,app_info,h5_info,pc_last_time,app_last_time,h5_last_time)"
//				+ " values(:pcSign,:appSign,:h5Sign,:pcInfo,:appInfo,:h5Info,:pcLastTime,:appLastTime,:h5Last_Time)";
		String sql = "update login_limit set pc_sign=:pcSign,pc_info=:pcInfo,pc_last_time=:pcLastTime," +
				" app_sign=:appSign,app_info=:appInfo,app_last_time=:appLastTime," +
				" h5_sign=:h5Sign,h5_info=:h5Info,h5_last_time=:h5LastTime "
				+ " where id=:id";
		return this.namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(ll));
	}

}
