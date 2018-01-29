package com.xczhihui.bxg.online.manager.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;

/**
 * 网站用户
 * 
 * @author Haicheng Jiang
 */
@Repository("onlineUserDao")
public class OnlineUserDao extends HibernateDao<OnlineUser> {
	/**
	 * 查询用户信息。
	 * 
	 * @param lastLoginIp
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param lastLoginTimeStart
	 * @param lastLoginTimeEnd
	 * @param searchName
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<OnlineUser> findUserPage(String lastLoginIp, String createTimeStart, String createTimeEnd,
			String lastLoginTimeStart, String lastLoginTimeEnd, String searchName, Integer status,Integer lstatus,int pageNumber,
			int pageSize) {
		String sql = "select u.id,u.login_name,u.name as name,if(o.sex is null,2,o.sex) as sex,u.stay_time,"
				+ "uc.balance,uc.balance_give balanceGive,vhall_id, "
				+ "o.mobile,o.qq,o.email,u.last_login_ip,u.visit_sum,u.create_time,u.last_login_date,u.menu_id as menuId,u.is_lecturer as isLecturer,m.name as menuName, "
				+ "u.status,o.id as applyId,u.room_number as roomNumber from oe_user u left join oe_apply o on u.id = o.user_id left join oe_menu m on u.menu_id = m.id "
				+ "left join user_coin uc on uc.user_id=u.id "
				+ "where 1=1 ";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.hasText(lastLoginIp)) {
			sql += (" and u.last_login_ip like :lastLoginIp ");
			paramMap.put("lastLoginIp", "%" + lastLoginIp.trim() + "%");
		}
		if (StringUtils.hasText(createTimeStart)) {
			sql += (" and u.create_time >= :createTimeStart and u.create_time <= :createTimeEnd ");
			paramMap.put("createTimeStart", createTimeStart+" 00:00:00");
			paramMap.put("createTimeEnd", createTimeStart+" 23:59:59");
		}
//		if (StringUtils.hasText(createTimeEnd)) {
//			sql += (" and u.create_time <= :createTimeEnd ");
//			paramMap.put("createTimeEnd", createTimeEnd);
//		}
		if (StringUtils.hasText(lastLoginTimeStart)) {
			sql += (" and u.last_login_date >= :lastLoginTimeStart and u.last_login_date <= :lastLoginTimeEnd ");
			paramMap.put("lastLoginTimeStart", lastLoginTimeStart+" 00:00:00");
			paramMap.put("lastLoginTimeEnd", lastLoginTimeStart+" 23:59:59");
		}
//		if (StringUtils.hasText(lastLoginTimeEnd)) {
//			sql += (" and u.last_login_date < :lastLoginTimeEnd ");
//			paramMap.put("lastLoginTimeEnd", lastLoginTimeEnd);
//		}
		if (status != null) {
			sql += (" and u.status= :status ");
			paramMap.put("status", status);
		}
		if (lstatus != null) {
			sql += (" and u.is_lecturer= :lstatus ");
			paramMap.put("lstatus", lstatus);
		}
		if (StringUtils.hasText(searchName)) {
			sql += " and (u.name like :searchName or u.login_name like :searchName or o.real_name like :searchName or o.mobile like :searchName or o.qq like :searchName or o.email like :searchName ) ";
			paramMap.put("searchName", "%" + searchName.trim() + "%");
		}
		sql += " order by u.create_time desc";
		Page<OnlineUser> pg = this.findPageBySQL(sql, paramMap, OnlineUser.class, pageNumber, pageSize);
		for (OnlineUser u : pg.getItems()) {
			if (StringUtils.hasText(u.getApplyId())) {
				sql = "select t3.name,if(t2.is_payment is null,1,t2.is_payment) as is_payment from oe_apply t1,apply_r_grade_course t2,oe_grade t3 where t1.id = t2.apply_id and t2.grade_id = t3.id and t1.user_id=?";
				List<Map<String, Object>> applys =this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, u.getId());
				String name = "";
				if (applys.size() > 0) {
					for (Map<String, Object> mp : applys) {
						String gname = mp.get("name").toString();
						String isPay = mp.get("is_payment").toString();
						isPay = "0".equals(isPay) ? "免费" : "2".equals(isPay) ? "已缴费" : "未缴费";
						name+=("<br>"+gname+"&nbsp;&nbsp;&nbsp;"+isPay);
					}
					if (name.length() > 0) {
						name = name.substring(4);
					}
				}
				u.setGradeName(name);
			}
		}
		return pg;
	}
    /**
     * 查找所有的讲师
     * Description：
     * @return
     * @return List<Map<String,Object>>
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
	public List<Map<String, Object>> getAllUserLecturer() {
		// TODO Auto-generated method stub
		String sql = "select id,name,login_name as logo from oe_user where is_lecturer = 1  and status =0 ";
//		String sql = "select id,name,ifnull(mobile,email) as logo from oe_user where is_lecturer = 1";
		List<Map<String, Object>> list =this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql);
		return list;
	}
    /**
     * Description：得到当前最大房间号,默认从10000开始
     * @return
     * @return int
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	public int getCurrent(){
		String sql = "select max(room_number) from oe_user";
		int count = super.queryForInt(sql,null);
		if(count==0){
			//说明存在
			count = 10000;
		}else{
			count = count+1;
		}
		return count;
	}
	public OnlineUser getOnlineUserByUserId(String userId) {
		return this.find(userId);
	}
	public List<Map<String, Object>> getAllCourseName() {
		String sql = "select c.id,c.grade_name as courseName,ou.name as name from oe_course as c,oe_user as ou  where c.user_lecturer_id = ou.id and c.is_delete=0 and c.status = 1  and ou.status =0";
		List<Map<String, Object>> list =this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql);
		return list;
	}
}
