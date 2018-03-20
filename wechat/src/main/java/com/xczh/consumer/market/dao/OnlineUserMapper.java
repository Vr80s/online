package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.SystemVariate;
import com.xczh.consumer.market.bean.VerificationCode;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczhihui.user.center.bean.UserSex;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户操作dao
 * 
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Repository
public class OnlineUserMapper extends BasicSimpleDao {
	/**
	 * 根据登录名查找用户
	 * @param loginName
	 * @return
	 */
	public OnlineUser findUserByLoginName(String loginName) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,name,login_name as loginName,password,sex,mobile,email,is_delete as isDelete, ");
		sql.append(" small_head_photo as smallHeadPhoto,big_head_photo as bigHeadPhoto,status as status, ");
		sql.append(" create_person as createPerson,create_time as createTime,last_login_date as lastLoginDate, ");
		sql.append(" last_login_ip as lastLoginIp,visit_sum as visitSum,stay_time as stayTime,info,jobyears, ");
		sql.append(" occupation,occupation_other as occupationOther,target,is_apply as isApply,full_address as fullAddress, ");
		sql.append(" menu_id as menuId,union_id as unionId,user_type as userType,ref_id as refId,parent_id as parentId, ");
		sql.append(" share_code as shareCode,change_time as changeTime,origin,type,vhall_id as vhallId,"
				+ "vhall_pass as vhallPass,vhall_name as vhallName, ");
		sql.append(" region_id as district,region_area_id as province,region_city_id as city,");
		sql.append(" province_name as provinceName,city_name as cityName,is_lecturer as isLecturer,info as info,");
		
		sql.append(" (select val from oe_common as common where common.id = occupation) as occupationText ");
		sql.append(" from oe_user where login_name = ? and status = 0  and is_delete =0  ");
		Object params[] = { loginName };
		OnlineUser o=this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new BeanHandler<>(OnlineUser.class), params);
		return o;
	}

	/**
	 * 根据id查询用户
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public OnlineUser findUserById(String id) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,name,login_name as loginName,password,sex,mobile,email,is_delete as isDelete, ");
		sql.append(" small_head_photo as smallHeadPhoto,big_head_photo as bigHeadPhoto,status as status, ");
		sql.append(" create_person as createPerson,create_time as createTime,last_login_date as lastLoginDate, ");
		sql.append(" last_login_ip as lastLoginIp,visit_sum as visitSum,stay_time as stayTime,info,jobyears, ");
		sql.append(" occupation,occupation_other as occupationOther,target,is_apply as isApply,full_address as fullAddress, ");
		sql.append(" menu_id as menuId,union_id as unionId,user_type as userType,ref_id as refId,parent_id as parentId, ");
		sql.append(" share_code as shareCode,change_time as changeTime,origin,type,room_number as roomNumber, ");
		sql.append(" region_id as district,region_area_id as province,region_city_id as city,");
		sql.append(" province_name as provinceName,city_name as cityName,is_lecturer as isLecturer,info as info, ");
		sql.append(" vhall_id as vhallId,vhall_pass as vhallPass,vhall_name as vhallName, ");
		sql.append(" county_name as countyName, ");
		sql.append(" (select val from oe_common as common where common.id = occupation) as occupationText ");
		sql.append(" from oe_user where id = ? and status = 0  and is_delete =0  ");
		Object params[] = { id };
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new BeanHandler<>(OnlineUser.class), params);
	}

	/**
	 * 根据姓名查找用户
	 * 
	 * @param name
	 * @return
	 */
	public OnlineUser findUserByName(String name) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,name,login_name as loginName,password,sex,mobile,email,is_delete as isDelete, ");
		sql.append(" small_head_photo as smallHeadPhoto,big_head_photo as bigHeadPhoto,status, ");
		sql.append(" create_person as createPerson,create_time as createTime,last_login_date as lastLoginDate, ");
		sql.append(" last_login_ip as lastLoginIp,visit_sum as visitSum,stay_time as stayTime,info,jobyears, ");
		sql.append(" occupation,occupation_other as occupationOther,target,is_apply as isApply,full_address as fullAddress, ");
		sql.append(" menu_id as menuId,union_id as unionId,user_type as userType,ref_id as refId,parent_id as parentId ");
		sql.append(" ,share_code as shareCode,change_time as changeTime,origin,type, ");
		sql.append(" region_id as district,region_area_id as province,region_city_id as city,");
		sql.append(" province_name as provinceName,city_name as cityName,is_lecturer as isLecturer,info as info,");
		sql.append(" county_name as countyName, ");
		sql.append(" (select val from oe_common as common where common.id = occupation) as occupationText ");
		sql.append(" from oe_user where name = ?  and status = 0  and is_delete =0 ");
		Object params[] = { name };
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new BeanHandler<>(OnlineUser.class), params);
	}

	/**
	 * 添加用户
	 * @param user
	 * @throws SQLException
	 */
	public void addOnlineUser(OnlineUser user) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into oe_user(id,name,login_name,mobile,is_delete,small_head_photo,create_person, ");
		sql.append(" status,visit_sum,stay_time,menu_id,user_type,origin,sex,create_time,type,parent_id, ");
		sql.append(" vhall_id,vhall_pass,vhall_name,union_id,password,region_id,region_area_id,region_city_id, ");
		sql.append(" province_name,city_name) ");
		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		super.update(JdbcUtil.getCurrentConnection(), sql.toString(),
				user.getId(), user.getName(), user.getLoginName(),
				user.getMobile(), user.isDelete(), user.getSmallHeadPhoto(),
				user.getCreatePerson(), user.getStatus(), user.getVisitSum(),
				user.getStayTime(), user.getMenuId(), user.getUserType(),
				user.getOrigin(), user.getSex(), user.getCreateTime(),
				user.getType(), user.getParentId(), user.getVhallId(),
				user.getVhallPass(), user.getVhallName(), user.getUnionId(),
				user.getPassword(), user.getDistrict(), user.getProvince(),
				user.getCity(), user.getProvinceName(), user.getCityName());
	}

	/**
	 * 获取验证码列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<VerificationCode> getListVerificationCode(String username)
			throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id ,phone,vcode,vtype,is_delete as isDelete,create_person as createPerson,create_time as createTime ");
		sql.append(" from oe_verification_code where phone=? and vtype='1' ");
		Object params[] = { username };
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new BeanListHandler<>(VerificationCode.class), params);
	}

	public List<VerificationCode> getListVerificationCode(String username,
                                                          Integer vType) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id ,phone,vcode,vtype,is_delete as isDelete,create_person as createPerson,create_time as createTime ");
		sql.append(" from oe_verification_code where phone=? and vtype=? ");
		Object params[] = { username, vType };
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new BeanListHandler<>(VerificationCode.class), params);
	}

	/**
	 * 删除验证码
	 * 
	 * @param id
	 * @throws SQLExceptin
	 */
	public void deleteVerificationCodeById(Integer id) throws SQLException {
		String sql = " delete from oe_verification_code where id = ? ";
		Object params[] = { id };
		super.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
	}

	/**
	 * 获取配置参数
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<SystemVariate> getListSystemVariate() throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t1.id,t1.create_person as createPerson,t1.create_time as createTime,t1.is_delete as isDelete, ");
		sql.append(" t1.description,t1.display_order as displayOrder,t1.name,t1.parent_id as parentId,t1.value from system_variate t1,system_variate t2 ");
		sql.append(" where t1.parent_id=t2.id and t2.name= 'message_provider' ");
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new BeanListHandler<>(SystemVariate.class));
	}

	/**
	 * 保存验证码
	 * 
	 * @throws SQLException
	 */
	public void insertVerificationCode(VerificationCode code)
			throws SQLException {
		StringBuffer sql = new StringBuffer("");
		sql.append(" insert into oe_verification_code(phone,vcode,vtype,is_delete,create_person,create_time)");
		sql.append(" VALUES(?,?,?,?,?,?) ");
		Object params[] = { code.getPhone(), code.getVcode(), code.getVtype(),
				code.isDelete(), code.getCreatePerson(), code.getCreateTime() };
		this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
	}

	/**
	 * 更新验证码
	 * 
	 * @throws SQLException
	 */
	public void updateVerificationCode(VerificationCode code)
			throws SQLException {
		StringBuffer sql = new StringBuffer(
				"update oe_verification_code set vcode = ? ,create_time = ? where id = ? ");
		Object params[] = { code.getVcode(), code.getCreateTime(), code.getId() };
		super.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
	}

	/**
	 * 更新用户share_code
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void updateOnlineUser(OnlineUser user) throws SQLException {
		String sql = " update oe_user set share_code = ? where id = ? ";
		Object[] params = { user.getShareCode(), user.getId() };
		this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
	}

	/**
	 * Description：更新用户中心提交的数据
	 * 
	 * @param user
	 * @throws SQLException
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	public void updateUserCenter(OnlineUser original, Map<String, String> map)
			throws SQLException {
		StringBuilder sb = new StringBuilder("");
		sb.append("update oe_user set ");

		if (StringUtils.hasText(map.get("info"))
				&& !map.get("info").equals(original.getName())) {
			sb.append(" info ='" + map.get("info") + "',");
		}
		if (StringUtils.hasText(map.get("nickname"))
				&& !map.get("nickname").equals(original.getName())) {
			sb.append(" name ='" + map.get("nickname") + "',");
		}
		if (StringUtils.hasText(map.get("province"))
				&& !map.get("province").equals(original.getProvince())) {
			sb.append(" region_area_id = '" + map.get("province") + "',");
		}
		if (StringUtils.hasText(map.get("city"))
				&& !map.get("city").equals(original.getCity())) {
			sb.append(" region_city_id = '" + map.get("city") + "',");
		}
		if (StringUtils.hasText(map.get("district"))
				&& !map.get("district").equals(original.getCity())) {
			sb.append(" region_id = '" + map.get("district") + "',");
		}
		if (StringUtils.hasText(map.get("provinceName"))
				&& !map.get("provinceName").equals(original.getProvince())) {
			sb.append(" province_name = '" + map.get("provinceName") + "',");
		}
		if (StringUtils.hasText(map.get("cityName"))
				&& !map.get("cityName").equals(original.getCity())) {
			sb.append(" city_name = '" + map.get("cityName") + "',");
		}
		if (StringUtils.hasText(map.get("countyName"))
				&& !map.get("countyName").equals(original.getProvince())) {
			sb.append(" county_name = '" + map.get("countyName") + "',");
		}	
		
		if (StringUtils.hasText(map.get("email"))
				&& !map.get("email").equals(original.getEmail())) {
			sb.append(" email = '" + map.get("email") + "',");
		}
		if (StringUtils.hasText(map.get("sex"))
				&& UserSex.isValid(Integer.parseInt(map.get("sex")))
				&& Integer.parseInt(map.get("sex")) != original.getSex()) {
			sb.append(" sex =" + Integer.parseInt(map.get("sex")) + ",");
		}
		if (StringUtils.hasText(map.get("smallHeadPhoto"))
				&& !map.get("smallHeadPhoto").equals(
						original.getSmallHeadPhoto())) {
			sb.append(" small_head_photo ='" + map.get("smallHeadPhoto") + "',");
		}
		if (StringUtils.hasText(map.get("occupation"))) {
			sb.append(" occupation =" + Integer.parseInt(map.get("occupation")) + ",");
		}
		if (StringUtils.hasText(map.get("occupationOther")) //身份信息
				&& !map.get("occupationOther").equals(original.getOccupationOther())) {
			sb.append(" occupation_other ='" + map.get("occupationOther") + "',");
		}
		
		String sql = sb.toString();
		if (sql.indexOf(",") != -1) {
			sql = sql.substring(0, sb.length() - 1);
			sql += " where id = ? ";
			Object[] params = { original.getId() };
			this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
		}
	}

	/**
	 * 根据登录名查找用户
	 * 
	 * @param loginName
	 * @return
	 */
	public Map<String, Object> findUserRoomNumberById(String userId)
			throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,name,room_number as roomNumber,sex,province_name as provinceName,occupation,occupation_other as occupationOther,");
		sql.append(" small_head_photo as smallHeadPhoto,city_name as cityName,info as info,is_lecturer as isLecturer,");
		sql.append(" county_name as countyName, ");
		sql.append(" (select val from oe_common as common where common.id = occupation) as occupationText ");
		sql.append(" from oe_user where id = ?  and status = 0  and is_delete =0 ");
		Object params[] = { userId };
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new MapHandler(), params);
	}

	/**
	 * 热门博主 Description：
	 * 
	 * @param queryKey
	 * @return
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 * @throws SQLException
	 *
	 */
	public List<Map<String, Object>> findHotHostByQueryKey(String queryKey)
			throws SQLException {

		
		// 如果有搜索参数的时候的话就显示3个数据，没有的话就显示4个
		Integer pageNumber = 4;
		/**
		 * 直接先检索关注表啦
		 */
		String strSql = " select count(*) as count1,of.lecturer_id as userId,ou.name as name,ou.small_head_photo as headImg "
				+ "from oe_focus as of ";
		
		strSql += ",oe_user as ou  where  of.lecturer_id = ou.id  and ou.status = 0 and ou.is_delete =0 and ou.name like '%" + queryKey + "%' ";
		if (queryKey != null && !"".equals(queryKey)
				&& !"null".equals(queryKey)) {
			pageNumber = 3;
		}
		strSql += " GROUP BY of.lecturer_id ORDER BY count1 desc limit "
				+ pageNumber;

		List<Map<String, Object>> listMap = this.query(
				JdbcUtil.getCurrentConnection(), strSql, new MapListHandler());

		String ids = "";
		if (listMap.size() > 0) {
			for (Map<String, Object> map : listMap) {
				String userId = map.get("userId").toString();
				ids += "'" + userId + "'" + ",";
			}
		}
		List<Map<String, Object>> bigMap = new ArrayList<Map<String, Object>>();
		if (listMap.size() == pageNumber) {
			return listMap;
		} else {
			bigMap.addAll(listMap);
			int pageSize = pageNumber - listMap.size();
			StringBuffer sql = new StringBuffer();
			sql.append("select id as userId,name,small_head_photo as headImg ");
			sql.append(" from oe_user where is_lecturer = 1 and status = 0 and is_delete =0 ");
			if (ids != "") {
				ids = ids.substring(0, ids.length() - 1);
				sql.append(" and id not in (" + ids + ")");
			}
			if (queryKey != null && !"".equals(queryKey)
					&& !"null".equals(queryKey)) {
				sql.append(" and (name like '%" + queryKey + "%' )");
			}
			sql.append(" limit " + pageSize);
			List<Map<String, Object>> chapters = super.query(
					JdbcUtil.getCurrentConnection(), sql.toString(),
					new MapListHandler());

			bigMap.addAll(chapters);
		}
		return bigMap;
	}

	public OnlineUser findOnlineUserByUnionid(String unionid_)
			throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,name,login_name as loginName,password,sex,mobile,email,is_delete as isDelete, ");
		sql.append(" small_head_photo as smallHeadPhoto,big_head_photo as bigHeadPhoto,status, ");
		sql.append(" create_person as createPerson,create_time as createTime,last_login_date as lastLoginDate, ");
		sql.append(" last_login_ip as lastLoginIp,visit_sum as visitSum,stay_time as stayTime,info,jobyears, ");
		sql.append(" occupation,occupation_other as occupationOther,target,is_apply as isApply,full_address as fullAddress, ");
		sql.append(" menu_id as menuId,union_id as unionId,user_type as userType,ref_id as refId,parent_id as parentId, ");
		sql.append(" share_code as shareCode,change_time as changeTime,origin,type,vhall_id as vhallId,vhall_pass as vhallPass,vhall_name as vhallName,");

		sql.append(" region_id as district,region_area_id as province,region_city_id as city,");
		sql.append(" province_name as provinceName,city_name as cityName,is_lecturer as isLecturer,info as info,");
		
		sql.append(" county_name as countyName, ");
		
		sql.append(" (select val from oe_common as common where common.id = occupation) as occupationText ");

		sql.append(" from oe_user where union_id = ? and status = 0  and is_delete =0 ");
		Object params[] = { unionid_ };
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new BeanHandler<>(OnlineUser.class), params);
	}

	/**
	 * 更新用户share_code
	 * 
	 * @throws SQLException
	 */
	public void updateVhallIdOnlineUser(String vhallId, String vhallPass,
			String vahllName, String userid) throws SQLException {
		String sql = " update oe_user set vhall_id = ?,vhall_pass =?,vhall_name =? where id = ? ";
		Object[] params = { vhallId, vhallPass, vahllName, userid };
		this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
	}

	/**
	 * 得到此用户是否是讲师信息 Description：
	 * 
	 * @param id
	 * @return
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 * @throws SQLException
	 */
	public Map<String, Object> getUserIsTeacher(String id) throws SQLException {
		String sql = " select is_lecturer,room_number from oe_user where id = ? and status = 0  and is_delete =0 ";
		Map<String, Object> map = this.query(JdbcUtil.getCurrentConnection(),
				sql, new MapHandler(), id);
		return map;
	}

	public void addLiveExamineData() throws SQLException {
		String str = "select c.id as id, c.description as description,c.grade_name as gradeName,c.smallimg_path as smallImgPath,"
				+ "c.start_time as startTime,c.course_length as courseLength,c.status as status"
				+ " from oe_course as c";
		List<CourseLecturVo> clist = this.query(
				JdbcUtil.getCurrentConnection(), str, new BeanListHandler<>(
						CourseLecturVo.class));
		for (CourseLecturVo c : clist) {
			StringBuilder sql = new StringBuilder();
			sql.append(" insert into live_examine_info(id,title,content,type,see_mode,start_time,when_long, ");
			sql.append(" user_id,examine_status,is_free,password,price,logo) ");
			sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			super.update(JdbcUtil.getCurrentConnection(), sql.toString(),
					c.getId(), c.getGradeName(), c.getDescription(),
					c.getType(), null, c.getStartTime(), c.getCourseLength(), null,
					0, c.getIsFree(),null,c.getCurrentPrice(),c.getSmallImgPath());
		}

	}

	/**
	 * 更新用户Unionid
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void updateUserUnionidByid(OnlineUser user) throws SQLException {
		String sql = " update oe_user set union_id = ? where id = ? ";
		Object[] params = { user.getUnionId(), user.getId() };
		this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
	}
	
	/**
	 * 更新用户的登录名  -- 手机号
	 * @param user
	 * @throws SQLException
	 */
	public void updateUserLoginName(OnlineUser user) throws SQLException {
		String sql = " update oe_user set login_name = ? where id = ? ";
		Object[] params = { user.getLoginName(), user.getId() };
		this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
	}

	public Map<String, Object> judgeUserIsTeacher(String userId) throws SQLException {

		String sql = " select is_lecturer from oe_user where  id =? ";
		Object[] params = { userId };
	    Map<String,Object> mapList = this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new MapHandler(), params);
		return mapList;
	}

	public void updateOnlineUserByWeixinInfo(OnlineUser ou, OnlineUser ouNew) throws SQLException {

		StringBuilder sb = new StringBuilder("");
		sb.append("update oe_user set ");

		// nickname: 昵称,sex:性别, 头像
		// provinceId:省份ID provinceName:省份名字

		if (ouNew.getSex()!=2) {
			sb.append(" sex ='" + ouNew.getSex() + "',");
		}
		if (StringUtils.hasText(ouNew.getName())) {
			sb.append(" name ='" + ouNew.getName() + "',");
		}
		if (StringUtils.hasText(ouNew.getSmallHeadPhoto())) {
			sb.append(" smallimg_path = '" + ouNew.getSmallHeadPhoto() + "',");
		}
		
		if (StringUtils.hasText(ouNew.getUnionId())) {
			sb.append(" union_id = '" + ouNew.getUnionId() + "',");
		}

		if (StringUtils.hasText(ouNew.getDistrict())) {
			sb.append(" region_id = '" + ouNew.getDistrict() + "',");
			sb.append(" region_area_id = '" + ouNew.getProvince() + "',");
			sb.append(" region_city_id = '" + ouNew.getCity()+ "',");
			
			sb.append(" province_name = '" + ouNew.getProvinceName() + "',");
			sb.append(" city_name = '" + ouNew.getCityName() + "',");
		}
		String sql = sb.toString();
		if (sql.indexOf(",") != -1) {
			sql = sql.substring(0, sb.length() - 1);
			sql += " where id = ? ";
			Object[] params = { ou.getId() };
			this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
		}
	}

	/**
	 * 查询出apple 游客登录的信息
	 * @param id
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 * @throws SQLException
	 */
	public Map<String, Object> getAppTouristRecord(String appOnlyOne) throws SQLException {
		String sql = " select user_id as userId,is_reigs as isRegis,vhall_id as vhallId,vhall_name as vhallName,vhall_pass as vhallPass,user_center_id as userCenterId from apple_tourist_record where app_only_one = ?";
		Map<String, Object> map = this.query(JdbcUtil.getCurrentConnection(),
				sql, new MapHandler(), appOnlyOne);
		return map;
	}
	/**
	 * 保存apple 游客登录的信息
	 * @param id
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 * @throws SQLException
	 */
	public void saveAppTouristRecord(OnlineUser ou,String appOnlyOne) throws SQLException {
		String sql = " insert into apple_tourist_record(app_only_one,user_id,vhall_id,vhall_name,vhall_pass,user_center_id) values(?,?,?,?,?,?) ";
		super.update(JdbcUtil.getCurrentConnection(), sql,
				appOnlyOne,ou.getId(),ou.getVhallId(),ou.getVhallName(),ou.getVhallPass(),ou.getUserCenterId());
	}

	public void updateOnlineUserAddPwdAndUserName(OnlineUser u) throws SQLException {

		StringBuilder sb = new StringBuilder("");
		sb.append("update oe_user set ");
		
		
		if (StringUtils.hasText(u.getLoginName())) {
			sb.append(" login_name ='" + u.getLoginName() + "',");
		}
		
		if (StringUtils.hasText(u.getLoginName())) {
			sb.append(" mobile ='" + u.getMobile() + "',");
		}
		
		if (StringUtils.hasText(u.getLoginName())) {
			sb.append(" name ='" + u.getName() + "',");
		}
		
		if (StringUtils.hasText(u.getLoginName())) {
			sb.append(" password ='" + u.getPassword() + "',");
		}

		if (u.getSex()!=2) {
			sb.append(" sex ='" + u.getSex() + "',");
		}
		
		if (StringUtils.hasText(u.getUnionId())) {
			sb.append(" union_id ='" + u.getUnionId() + "',");
		}
		
		if (StringUtils.hasText(u.getSmallHeadPhoto())) {
			sb.append(" small_head_photo ='" + u.getSmallHeadPhoto() + "',");
		}
		
		if (StringUtils.hasText(u.getOrigin())) {
			sb.append(" origin ='" + u.getOrigin() + "',");
		}
		

		if (StringUtils.hasText(u.getSmallHeadPhoto())) {
			sb.append(" small_head_photo ='" + u.getSmallHeadPhoto() + "',");
		}
		
		if (StringUtils.hasText(u.getOrigin())) {
			sb.append(" origin ='" + u.getOrigin() + "',");
		}
		
		if (StringUtils.hasText(u.getDistrict())) {
			sb.append(" region_id = '" + u.getDistrict() + "',");
			sb.append(" region_area_id = '" + u.getProvince() + "',");
			sb.append(" region_city_id = '" + u.getCity()+ "',");
			sb.append(" province_name = '" + u.getProvinceName() + "',");
			sb.append(" city_name = '" + u.getCityName() + "',");
		}
		
		String sql = sb.toString();
		if (sql.indexOf(",") != -1) {
			sql = sql.substring(0, sb.length() - 1);
			sql += " where id = ? ";
			
			Object[] params = { u.getId() };
			this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
		}
	}
	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public OnlineUser findUserByIdAndVhallNameInfo(String id) throws SQLException {
		StringBuffer sql = new StringBuffer(); 
		sql.append(" select id as id,name as name,small_head_photo as smallHeadPhoto,vhall_id as vhallId,vhall_pass as vhallPass,vhall_name as vhallName ");
		sql.append(" from oe_user where id = ?  and status = 0  and is_delete =0 ");
		Object params[] = { id };
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(OnlineUser.class), params);
	}

	public void updateAppleTourisrecord(String appUniqueId,Integer isReigs) throws SQLException {
		// TODO Auto-generated method stub
		
		StringBuilder sb = new StringBuilder("");
		sb.append("update apple_tourist_record set is_reigs = ? where app_only_one = ?");
		Object[] params = {isReigs,appUniqueId};
		this.update(JdbcUtil.getCurrentConnection(), sb.toString(), params);
	}
    /**
     * 
     * Description：查找主播的详情    主播详细信息和精彩致辞在course_anchor表中
     * @param lecturerId
     * @return
     * @return Map<String,Object>
     * @author name：yangxuan <br>email: 15936216273@163.com
     * @throws SQLException 
     */
	public Map<String, Object> findHostById(String lecturerId) throws SQLException {
		StringBuffer sql = new StringBuffer(); 
		sql.append("select ca.user_id,ca.name,ca.profile_photo as small_head_photo,ca.video,ca.detail,ca.type as type,"
				+ " (select md.work_time from medical_doctor md where id = (select doctor_id from medical_doctor_account as mda  "
				+ "    where mda.account_id = ?) )  as workTime  "
				+ "  from  oe_user ou inner join course_anchor ca on  ou.id = ca.user_id where ou.id = ?");
		
		Object params[] = { lecturerId,lecturerId };
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new MapHandler(), params);
	}

	public void emptyAccount(String userName) throws SQLException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("");
		sb.append("delete from  oe_user where login_name = ?");
		Object[] params = {userName};
		this.update(JdbcUtil.getCurrentConnection(), sb.toString(), params);
	}
	
}
