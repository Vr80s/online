package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.PartnerVo;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Libin Wang
 *
 * @date 2017年2月27日
 */
@Repository
public class MyShareMapper extends BasicSimpleDao {

	/***
	 * 课程详细信息
	 * @param course_id
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findOneCourse(String course_id) throws SQLException{
		StringBuffer sql = new StringBuffer("");
		sql.append(" select t1.course_id,t2.grade_name,t1.img_url,t1.share_desc,t1.work_flow ");
		sql.append(" from oe_course_share as t1,oe_course as t2                              ");
		sql.append(" where t1.course_id=t2.id and t1.course_id=?                             ");
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new MapHandler(),course_id);
	}
	
	/**
	 * 查询当前用户的佣金和上级合伙人
	 */
	public Map<String, Object> findMyBrokerage(String userId) throws SQLException {
		StringBuffer sql1 = new StringBuffer("");
		sql1.append("select sum(subsidies) as subsidies from oe_share_order where target_user_id=?");
		Map<String, Object> map1 = this.query(JdbcUtil.getCurrentConnection(), sql1.toString(),new MapHandler(),userId);
		
		StringBuffer sql2 = new StringBuffer("");
		sql2.append("select case when name is null then '' else name end as username from oe_user where id=(select parent_id from oe_user where id=? "
				+ "and STATUS=0 and is_delete=0) and STATUS=0 and is_delete=0");
		Map<String, Object> map2 = this.query(JdbcUtil.getCurrentConnection(), sql2.toString(),new MapHandler(),userId);
		if(map2==null){
			map2 = new HashMap<String, Object>();
			map2.put("username", null);
		}
		map1.putAll(map2);
		return map1;
	}
	
	/**
	 * 查询当前用户的一级合伙人列表
	 */
	public List<Map<String, Object>> findMyPartnerList(String userId) throws SQLException {
		StringBuffer sql = new StringBuffer("select id,name,mobile,create_time from oe_user where parent_id= ?");
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new MapListHandler(),userId);
	}
	
	/**
	 * 查询当前用户的一级合伙人列表
	 */
	public List<PartnerVo> findMyPartnerList1(String userId) throws SQLException {
		StringBuffer sql = new StringBuffer("select id,name,mobile,DATE_FORMAT(create_time,'%Y-%m-%d %H:%i') as create_time from oe_user where parent_id= ?");
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(PartnerVo.class),userId);
	}
	
	/**
	 * 计算一级下的二级和三级人数总和
	 */
	public Map<String,Object> getPartnerLevelCount(String parentId) throws SQLException {
		StringBuffer sql  = new StringBuffer("");
		sql.append("select count(t1.id) as count_l1,sum(t2.totCount) as count_l2 from ");
		sql.append("(select id from oe_user where parent_id= ?) as t1 ");
		sql.append("LEFT JOIN (");
		sql.append("select count(1) as totCount,parent_id from oe_user GROUP BY parent_id ");
		sql.append(") as t2 ");
		sql.append(" on t1.id=t2.parent_id ");
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new MapHandler(),parentId);
	}
	
	
	/**
	 * 查询当前用户的佣金明细列表
	 */
	public List<Map<String, Object>> findMyBrokerageList(String userId) throws SQLException {
		StringBuffer sql = new StringBuffer("");
		sql.append("select t1.*,t2.name,t2.mobile from (                                  ");
		sql.append("select id,buy_user_id,pay_time,`level`,subsidies from oe_share_order  ");
		sql.append("where target_user_id = ? order by pay_time desc) as t1        		  ");
		sql.append("LEFT JOIN oe_user as t2 on t1.buy_user_id=t2.id                       ");
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new MapListHandler(),userId);
	}
	
	/**
	 * 查询佣金详情明细
	 */
	public Map<String, Object> findBrokerageDetail(String id) throws SQLException {
		StringBuffer sql = new StringBuffer("select t1.subsidies,t1.order_no,t1.course_name,c.name as buy_user_name,c.mobile,t1.pay_time, t1.actual_pay,t1.LEVEL, p.`name` pname,pp.`name` ppname ");
		sql.append(" from (select subsidies,order_no,course_name,buy_user_id,pay_time,actual_pay,LEVEL,target_user_id from oe_share_order where id=?) as t1 ");
		sql.append(" LEFT JOIN oe_user as c on t1.buy_user_id=c.id ");
		sql.append(" LEFT JOIN oe_user as p on c.parent_id=p.id ");
		sql.append(" LEFT JOIN oe_user as pp on p.parent_id=pp.id ");
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new MapHandler(),id);
	}
}
