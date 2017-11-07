package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.api.vo.*;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.CityVo;
import com.xczhihui.bxg.online.web.vo.JobVo;
import com.xczhihui.bxg.online.web.vo.JobYearVo;
import com.xczhihui.bxg.online.web.vo.ProvinceVo;
import com.xczhihui.bxg.online.web.vo.RegionVo;
import com.xczhihui.bxg.online.web.vo.UserCenterVo;
import com.xczhihui.bxg.online.web.vo.UserDataVo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户中心dao
 * @author duanqh
 *
 */
@Repository
public class UserCenterDao extends SimpleHibernateDao {
	
	/**
	 * 顶部用户展示信息
	 * @param userId
	 * @return
	 */
	public UserCenterVo getUserInfo(String userId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.img AS img,"//用户头像
					+ "ifnull(a.`name`,'') AS `name`,"//用户名字
					+ "ifnull(a.info,'') AS info "//用户签名
					+ "FROM oe_user a WHERE a.is_delete = 0 AND a.id = ? ");
		List<UserCenterVo> list = this.getNamedParameterJdbcTemplate().getJdbcOperations().query
				(sql.toString(),new Object[]{userId},BeanPropertyRowMapper.newInstance(UserCenterVo.class));
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	public OnlineUser getUser(String userId) {
		return this.get(userId, OnlineUser.class);
	}
	
	/**
	 * 获取用户资料
	 * @param userId
	 * @return
	 */
	public UserDataVo getUserData(String userId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.id AS uid," +
				"ifnull(a.small_head_photo,'') AS img," +
				"a.`name` AS nickName," +
				"a.info AS autograph," +
				"a.login_name AS loginName," +
				"a.occupation AS occupation," +
				"a.occupation_other AS occupationOther," +
//				"a.jobyears AS jobyearId," +
//				"a.full_address AS fullAddress," +
				"a.sex," +
				"a.province_name AS province," +
				"a.city_name AS city," +

				"ifnull(a.target,'') AS target," +
//				"ifnull(a.region_area_id,'') AS province," +
//				"ifnull(a.region_city_id,'') AS city," +
				"ifnull(a.region_id,'') AS district");
		sql.append(" FROM oe_user a WHERE a.is_delete = 0 AND a.status != -1  AND a.id = ? ");
		
		List<UserDataVo> list = this.getNamedParameterJdbcTemplate().getJdbcOperations().query
				(sql.toString(),new Object[]{userId}, BeanPropertyRowMapper.newInstance(UserDataVo.class));
		if(list.size() > 0){
			return list.get(0);
		}
		return new UserDataVo();
	}
	
	/**
	 * 获取职位
	 * @param
	 * @return
	 */
	public List<JobVo> getJob(String group){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.id,"//id
					+ "a.`key`,"//key
					+ "a.val AS value "//value
					+ "FROM oe_common a WHERE a.`group` = ? AND a.is_delete = 0 ORDER BY a.sort ");
		return this.getNamedParameterJdbcTemplate().getJdbcOperations().query
				(sql.toString(),new Object[]{group},BeanPropertyRowMapper.newInstance(JobVo.class));
	}

	/**
	 * 获取工作年限
	 * @param
	 * @return
	 */
	public List<JobYearVo> getJobYear(){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.id ,"//id
					+ "a.`key`,"//key
					+ "a.val AS value "//value
					+ "FROM oe_common a WHERE a.is_delete = 0 AND a.`group` = 'jobyears' ORDER BY a.sort ");
		return this.getNamedParameterJdbcTemplate().getJdbcOperations().query
				(sql.toString(), BeanPropertyRowMapper.newInstance(JobYearVo.class));
	}

	/**
	 * 获取区域
	 * @return
	 */
	public List<RegionVo> getAllRegion(){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.id AS id,a.`name` AS regionName FROM oe_region a WHERE a.parent_id = 0 AND a.type = 1 ");
		return this.getNamedParameterJdbcTemplate().getJdbcOperations().query
				(sql.toString(), BeanPropertyRowMapper.newInstance(RegionVo.class));
	}

	public  OnlineUser  getUserByLoginName(String loginName){
		return this.findOneEntitiyByProperty(OnlineUser.class, "loginName", loginName);
	}
	
	/**
	 * 根据区域id获取获取所有省份
	 * @param
	 * @return
	 */
	public List<ProvinceVo> getAllProvince(){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.id AS id,a.`name` AS name FROM oe_region a WHERE a.parent_id = 0 AND a.id <> 0 ");
		return this.getNamedParameterJdbcTemplate().getJdbcOperations().query
				(sql.toString(), BeanPropertyRowMapper.newInstance(ProvinceVo.class));
	}
	
	/**
	 * 根据省份id获取所有市
	 * @param
	 * @return
	 */
	public List<CityVo> getAllCity(Integer provincId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.id AS id,a.`name` AS name FROM oe_region a WHERE a.parent_id = ? ");
		return this.getNamedParameterJdbcTemplate().getJdbcOperations().query
				(sql.toString(),new Object[]{provincId}, BeanPropertyRowMapper.newInstance(CityVo.class));
	}

	/**
	 * 修改用户报名状态
	 * @param onlineUser
	 */
	public  void   updateUser(OnlineUser  onlineUser){
             this.update(onlineUser);
	}
	/**
	 * 更新博问答相关表
	 * @param userId
	 */
	public void updateAskUserInfo(String userId,String nikeName,String photo){
		/**
		 * 更新提问表
		 */
		this.getNamedParameterJdbcTemplate().getJdbcOperations()
			.update("update oe_ask_question set create_nick_name=?,create_head_img=? where user_id=?",
					nikeName,photo,userId);
		/**
		 * 更新回答表
		 */
		this.getNamedParameterJdbcTemplate().getJdbcOperations()
		.update("update oe_ask_answer set create_nick_name=?,create_head_img=? where user_id=?",
				nikeName,photo,userId);
		/**
		 * 更新评论表
		 */
		this.getNamedParameterJdbcTemplate().getJdbcOperations()
		.update("update oe_ask_comment set create_nick_name=? where user_id=?",
				nikeName,userId);
		this.getNamedParameterJdbcTemplate().getJdbcOperations()
		.update("update oe_ask_comment set target_nike_name=? where target_user_id=?",
				nikeName,userId);
	}

}
