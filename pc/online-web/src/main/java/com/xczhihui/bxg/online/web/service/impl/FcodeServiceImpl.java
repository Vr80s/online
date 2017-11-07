package com.xczhihui.bxg.online.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.service.FcodeService;
import com.xczhihui.bxg.online.web.vo.UserFcodeVo;

/**
 * F码
 * @author Haicheng Jiang
 */
@Service
public class FcodeServiceImpl  extends OnlineBaseServiceImpl implements FcodeService{
	@Resource(name="simpleHibernateDao")
	private SimpleHibernateDao dao;

	@Override
	public Page<UserFcodeVo> findMyFcode(String userId,Integer status,Integer pageNumber, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String statusSql = "";
		if (status == 0) {
			statusSql = " and t2.status=2 and t4.expiry_time >= now() ";
		} else if(status == 1){
			statusSql = " and t2.status=3 and t4.expiry_time >= now() ";
		} else if(status == 2){
			statusSql = " and t4.expiry_time < now() ";
		}
		paramMap.put("user_id", userId);
		Page<UserFcodeVo> lst = dao.findPageBySQL("select t1.course_names,t4.expiry_time,t3.grade_name course_name "
				+ " from oe_fcode_user t1,oe_fcode t2 left join oe_course t3 "
					+ " on t2.used_course_id = t3.id,oe_fcode_lot t4 "
					+ " where t1.fcode = t2.fcode and t2.lot_no = t4.lot_no and "
					+ " t1.user_id=:user_id"+statusSql, paramMap, 
				UserFcodeVo.class, pageNumber, pageSize);
		return lst;
	}

	@Override
	public void addUserFcode(String userId, String fcode) {
		if(dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(
				"select count(*) from oe_fcode_user where fcode='"+fcode+"'", Integer.class) > 0){
			throw new RuntimeException("此兑换码已被领取");
		}
		//查询F码
		List<Map<String,Object>> codelst = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList("select t1.status, group_concat(t5.grade_name) course_names from oe_fcode t1,oe_fcode_lot t2,"
						+ "oe_fcode_activity t3,oe_fcode_activity_course t4,oe_course t5 "
							+ " where t1.lot_no = t2.lot_no and t2.lot_no = t3.lot_no and t3.id = t4.activity_id "
								+ " and t4.course_id = t5.id and t1.fcode=? and t1.status=0 and t2.expiry_time > now() group by t2.lot_no",fcode);
//		+ " and t4.course_id = t5.id and t1.fcode=? and t1.status=1 and t2.expiry_time > now() group by t2.lot_no",fcode);
		if (codelst.size() > 0) {
			Map<String,Object> code = codelst.get(0);
			//写入用户F码表
			String insertsql = "insert into oe_fcode_user (id,user_id,fcode,course_names,course_name) "
					+ " values ('"+UUID.randomUUID().toString().replace("-", "")
					+"','"+userId+"','"+fcode+"','"+code.get("course_names").toString()+"',null)";
			dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(insertsql);
			
			//更新为已领取状态
			dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update("update oe_fcode set status=2 where fcode='"+fcode+"' ");
		} else {
			throw new RuntimeException("兑换码无效");
		}
	}
}
