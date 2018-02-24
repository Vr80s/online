package com.xczh.consumer.market.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Repository;

import com.xczh.consumer.market.bean.OnlineCourse;
import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.DateUtil;

@Repository
public class OnlineOrderMapper extends BasicSimpleDao{
	/**
	 * 根据用户id和课程id查询订单
	 * @param courseId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public OnlineOrder getOnlineOrderByCourseId(String courseId, String userId) throws SQLException{
		String sql = " select *  from oe_order where order_status !=2 and  user_id=? and course_id=?  limit 1 ";
		Object params[] = {userId,courseId};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(OnlineOrder.class),params);
	}
	/**
	 * 根据订单号查询信息
	 * @param orderNo
	 * @return
	 * @throws SQLException
	 */
	public OnlineOrder getOnlineOrderByOrderNo(String orderNo) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select od.id,od.create_person as createPerson,od.create_time as createTime,od.is_delete as isDelete,od.sort, ");
		sql.append(" od.order_no as orderNo,");
		sql.append(" od.actual_pay as actualPay,od.pay_account as payAccount,od.purchaser as purchaser,od.pay_type as payType, ");
		sql.append(" od.pay_time as payTime,od.order_status as orderStatus,od.user_id as userId,od.order_from as orderFrom, ");
		sql.append(" od.is_count_brokerage as isCountBrokerage,od.preferenty_money as preferentyMoney from oe_order as od ");

		sql.append(" where order_no=? limit 1  "); //去掉条件 and od.order_from=3 pc端下的单也可以在wap端付款 刘涛注释
		Object params[] = {orderNo};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(OnlineOrder.class),params);
	}
	
	/**
	 * 根据订单号查询信息
	 * @return
	 * @throws SQLException
	 */
	public OnlineOrder getOnlineOrderByOrderId(String orderId) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select od.id,od.create_person as createPerson,od.create_time as createTime,od.is_delete as isDelete,od.sort, ");
		sql.append(" od.order_no as orderNo,");
		sql.append(" od.actual_pay as actualPay,od.pay_account as payAccount,od.purchaser as purchaser,od.pay_type as payType, ");
		sql.append(" od.pay_time as payTime,od.order_status as orderStatus,od.user_id as userId,od.order_from as orderFrom, ");
		sql.append(" od.is_count_brokerage as isCountBrokerage,od.preferenty_money as preferentyMoney from oe_order as od ");
		sql.append(" where od.id=? limit 1  "); //去掉条件 and od.order_from=3 pc端下的单也可以在wap端付款 刘涛注释
		Object params[] = {orderId};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(OnlineOrder.class),params);
	}
	
	
	
	/**
	 * 取消订单或删除订单        支付状态 0:未支付 1:已支付 2:已关闭 
	 * @param type:0删除订单 1:取消订单  2:未支付->已支付   3:
	 * @param orderNo 订单号 （不是id）
	 */
	public void updateOnlineOrderStatus(Integer type,String orderNo) throws SQLException{
		String sql = "";
		if (type == 0){ //删除失效订单
			sql = " delete from oe_order where order_no=? and order_status=2";
		}else if(type == 1){  // 未支付 --》失效   
			sql = " update oe_order set order_status=2 where order_no=? and order_status=0";
		}else if(type == 2){  // 未支付 --》已支付
			sql = " update oe_order set order_status=1 where order_no=? and order_status=0 ";
		}else if(type == 3){   //失效  -- 》 未支付
			sql = " update oe_order set order_status=0 where order_no=? and order_status=2 ";
		}
		Object[] params = {orderNo};
		this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
	}
	/**
	 * 添加订单
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public void addOrder(OnlineOrder order) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("insert into oe_order 		");
		sql.append("(id,create_person,create_time,is_delete,sort,order_no, ");
		sql.append("actual_pay,pay_account,purchaser,pay_type,pay_time, ");
		sql.append("order_status,user_id,order_from,is_count_brokerage,preferenty_money) ");
		sql.append("values                              ");
		sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )  ");
		this.update(JdbcUtil.getCurrentConnection(),sql.toString(), 
			order.getId(),order.getCreatePerson(),order.getCreateTime(),order.isDelete(),
			order.getSort(),order.getOrderNo(),order.getActualPay(),order.getPayAccount(),
			order.getPurchaser(),order.getPayType(),order.getPayTime(),order.getOrderStatus(),
			order.getUserId(),order.getOrderFrom(),order.getIsCountBrokerage(),order.getPreferentyMoney()
			);				
	}
	/**
	 * 添加订单详情
	 * @param orderId
	 * @param courseId
	 */
	public void addOrderDetail(String orderId,int courseId,double actualPay) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into oe_order_detail (id,order_id,course_id,actual_pay,price) values(?,?,?,?,?) ");
		String orderDetailId=UUID.randomUUID().toString().replace("-", "");
		Object[] params = {orderDetailId,orderId,courseId,actualPay,actualPay};
		this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
	}
	
	
	/**
	 * 获取订单列表
	 * @param status
	 * @param pageNumber
	 * @return
	 * @throws SQLException
	 */
	public List<OnlineOrder> getOnlineOrderList(Integer status, String userId,
                                                Integer pageNumber, Integer pageSize) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select od.id,od.create_person as createPerson,od.create_time as createTime,od.is_delete as isDelete,od.sort, ");
		sql.append(" od.order_no as orderNo,");
		sql.append(" od.actual_pay as actualPay,od.pay_account as payAccount,od.purchaser as purchaser,od.pay_type as payType, ");
		sql.append(" od.pay_time as payTime,od.order_status as orderStatus,od.user_id as userId,od.order_from as orderFrom, ");
		sql.append(" od.is_count_brokerage as isCountBrokerage,od.preferenty_money as preferentyMoney from oe_order as od ");
		sql.append(" where od.user_id = ? ");
		Object[] params = null;
		if(status != -1){
			if(status==0){  //未支付里 也显示已关闭的订单
				sql.append(" and od.order_status = ? ");
				params = new Object[]{userId,status};
			}else {
				sql.append(" and od.order_status = ? ");
				params = new Object[]{userId,status};
			}
		}else{
			params = new Object[]{userId}; 
		}
		sql.append(" order by od.order_status asc,od.create_time desc ");
		List<OnlineOrder> lists = this.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(), pageNumber, pageSize,OnlineOrder.class, params);
		
		for (int i = 0; i < lists.size(); i++) {
			
			OnlineOrder order = lists.get(i);
			//订单支付时间
			Date d = order.getCreateTime();
			String dStr = DateUtil.dateAddYear(d);
			order.setValidity(dStr);
			
			String id = order.getId();
			StringBuffer sql2 = new StringBuffer();
			sql2.append(" select course.id as id ,course.grade_name as gradeName,course.default_student_count,de.actual_pay as actualPay, ");
			sql2.append(" course.original_cost as originalCost,course.current_price as currentPrice, ");  
			sql2.append(" IF(course.type=1,1,if(course.type = 3,4,if(course.multimedia_type=1,2,3))) as type, ");//1 直播 2 视频 3 音频  4 线下
			sql2.append(" course.live_status as lineState, ");
			sql2.append(" course.smallimg_path as smallimgPath,course.start_time as startTime,course.end_time as endTime,"
					+ "ou.name as teacherName,ou.id as userId");
			sql2.append("  from oe_order_detail as de ");
			sql2.append(" ,oe_course as course,oe_user as ou where de.course_id = course.id and course.user_lecturer_id = ou.id "
					+ "  and de.order_id = ? and course.is_delete=0 and course.status = 1 ");

			Object params2[] = {id};
			List<OnlineCourse> lists2 = this.query(JdbcUtil.getCurrentConnection(), sql2.toString(),
					new BeanListHandler<>(OnlineCourse.class),params2);

			//课程已失效
			if(lists2.size()>0){
				//课程有效期 //支付状态 0:未支付 1:已支付 2:已关闭
				order.setAllCourse(lists2);
			}else{   //如果此订单下没有课程的话，就不显示改订单了
				lists.remove(i);
			}
		}
		return lists;
	}


	/**
	 * 获取学习中心列表
	 * @param type 1点播 2 直播 3线下
	 * @param pageNumber
	 * @return
	 * @throws SQLException
	 */
	public List<OnlineCourse> getLearningCenterList(Integer type, String userId,
												Integer pageNumber, Integer pageSize) throws SQLException {
		String sql="";
		
		String dateWhere = " if(date_sub(date_format(oc.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff ";//这个用来比较啦
			if(1==type){
				//CourseVo
				sql = " select oc.end_time endTime,oc.current_price currentPrice,ou.name as teacherName,if(oc.type = 1,1,if(oc.multimedia_type=1,2,3)) as type, oc.id,oc.grade_name as courseName,oc.smallimg_path as smallImgPath," +
						" ( SELECT COUNT(id) from oe_video  where course_id=oc.id and is_delete=0 and  status=1  ) as count, " +
						" ( SELECT COUNT(id) from user_r_video  where course_id=oc.id and study_status=1  and status=1 and is_delete=0 and  user_id=? ) as learndCount" +
						" from  oe_course  oc  join  apply_r_grade_course argc  on oc.`id`=argc.`course_id` inner join oe_user as ou on oc.user_lecturer_id = ou.id  where argc.user_id=?  and oc.multimedia_type in (1,2) and oc.is_delete=0 and oc.status=1 group by oc.id  order by argc.create_time desc ";
					List<OnlineCourse> list =
					this.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(), pageNumber, pageSize,OnlineCourse.class ,userId,userId);
					return list;
			}else if(2==type){
				sql = " select  oc.current_price currentPrice, ou.name as teacherName,if(oc.type = 1,1,if(oc.multimedia_type=1,2,3)) as type,oc.live_status as lineState, oc.id,oc.grade_name as courseName,oc.smallimg_path as smallImgPath, oc.`start_time` AS startTime, oc.`end_time` AS endTime,oc.direct_id" +
						" from  oe_course  oc join `apply_r_grade_course` argc on oc.id = argc.`course_id` inner join oe_user as ou on oc.user_lecturer_id = ou.id    where oc.type = 1 and  argc.user_id=? and oc.is_delete=0 and oc.status=1 group by oc.id  order by argc.create_time desc";
				List<OnlineCourse> list =this.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(), pageNumber, pageSize,OnlineCourse.class ,userId);
				return list;
			}else if(3==type){
				 sql = " select oc.address, oc.current_price currentPrice,4 as type, ou.name as teacherName,oc.id,oc.grade_name as courseName,oc.smallimg_path as smallImgPath,"
				 		+ "oc.start_time startTime,oc.end_time endTime, " 
						+  dateWhere 
						+ " from  oe_course  oc JOIN `apply_r_grade_course` argc ON oc.`id`=argc.`course_id` inner join oe_user as ou on oc.user_lecturer_id = ou.id   where argc.user_id=?  AND oc.`type`=3 and oc.is_delete=0 and oc.status=1 group by oc.id order by argc.create_time desc ";
				List<OnlineCourse> list =this.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(), pageNumber, pageSize,OnlineCourse.class ,userId);
				return list;
				}
		return null;
	}
	/**
	 * 根据订单号查询信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<OnlineCourse> getCourseByOrderId(String id) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select course.id ,course.grade_name as gradeName,course.default_student_count,de.actual_pay as actualPay, ");
		sql.append(" course.original_cost as originalCost,course.current_price as currentPrice, ");
		sql.append(" IF(course.type = 1,1,if(course.type =3,4,if(course.multimedia_type=1,2,3))) as type, ");
		sql.append(" course.live_status as  lineState, ");
		
		sql.append(" course.start_time as startTime,course.end_time as endTime,ou.name as teacherName,ou.id as userId, ");
		sql.append(" course.smallimg_path as smallimgPath from oe_order_detail as de, ");
		sql.append(" oe_course as course,oe_user as ou  where "
				+ "  de.course_id = course.id and course.user_lecturer_id = ou.id and de.order_id = ? ");
		Object params[] = {id};
		List<OnlineCourse> list = this.query(JdbcUtil.getCurrentConnection(),
				sql.toString(),new BeanListHandler<>(OnlineCourse.class),params);
		
		return list;
	}
	
	
	/**
	 * 根据订单号查询信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<OnlineCourse> getNewCourseByOrderId(String id) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select course.id ,course.grade_name as gradeName,course.default_student_count,de.actual_pay as actualPay, ");
		sql.append(" course.collection as collection,course.current_price*10 as currentPrice, ");
		sql.append(" IF(course.type = 1,1,if(course.type =3,4,if(course.multimedia_type=1,2,3))) as type, ");
		sql.append(" course.live_status as  lineState, ");
		
		sql.append(" course.start_time as startTime,course.end_time as endTime,ou.name as teacherName,ou.id as userId, ");
		sql.append(" course.smallimg_path as smallimgPath from oe_order_detail as de, ");
		sql.append(" oe_course as course,oe_user as ou  where "
				+ "  de.course_id = course.id and course.user_lecturer_id = ou.id and de.order_id = ? ");
		Object params[] = {id};
		List<OnlineCourse> list = this.query(JdbcUtil.getCurrentConnection(),
				sql.toString(),new BeanListHandler<>(OnlineCourse.class),params);
		
		return list;
	}
	
	/**
	 * 根据商品id查询订单信息
	 * @param courseId
	 * @return
	 * @throws SQLException
	 */
	public OnlineOrder getCourseInfoByCourseId(int courseId, String userId,String orderId)throws SQLException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select od.id,od.create_person as createPerson,od.create_time as createTime,od.is_delete as isDelete,od.sort, ");
		sql.append(" od.order_no as orderNo,");
		sql.append(" od.actual_pay as actualPay,od.pay_account as payAccount,od.purchaser as purchaser,od.pay_type as payType, ");
		sql.append(" od.pay_time as payTime,od.order_status as orderStatus,od.user_id as userId,od.order_from as orderFrom, ");
		sql.append(" od.is_count_brokerage as isCountBrokerage,od.preferenty_money as preferentyMoney ");
		sql.append(" from oe_order as od left join oe_order_detail as ood ");
		sql.append(" on od.id = ood.order_id where ood.course_id = ? and od.user_id = ? and od.id = ? and od.order_status = 0 ");
		
		Object params[] = {courseId,userId,orderId};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(OnlineOrder.class),params);
	}
	/**
	 * 根据商品id查询订单信息
	 * @param courseId
	 * @return
	 * @throws SQLException
	 */
	public OnlineOrder getOrderCourseInfoByUserId(int courseId, String userId)throws SQLException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select course_id as courseId,oo.id from  oe_order as oo,oe_order_detail as ord  ");
		sql.append(" where oo.id = order_id and  ");
		sql.append(" oo.user_id = ? and oo.order_status =0 ");
		
		Object params[] = {userId};
		/**
		 * 得到这个用户下所有的订单信息（包含课程和订单id）
		 */
	    List<Map<String,Object>> AllList = 
			this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new MapListHandler(),params);
		
	  
	    List<Map<String,Object>> filterList = new ArrayList<Map<String,Object>>();
	    
	    /**
	     * 过滤所有的，让订单和课程之间称为一对一关系
	     */
	    int indexfalg = 0;
	    for (Map<String, Object> map : AllList) {
	    	if(null != map.get("courseId") && courseId == Integer.parseInt(map.get("courseId").toString())){
	    		//tmp = map;
	    		//通过这个课程得到订单id。根据订单id
	    		indexfalg ++;
	    		filterList.add(map);
	    	}
		}
	    /**
	     * 说明并没有这个课程。直接添加
	     */
	    if(indexfalg == 0){
	    	return null;
	    }
	    /**
	     * 通过两个循环得到所有的到底哪个才是真正的id
	     */
	    Map<String,Object> realMap = null;
	    for (Map<String, Object> mapf : filterList) {
		    int sign =0;
	    	for (Map<String, Object> mapa : AllList) {
	    		if(null != mapf.get("id") && null != mapa.get("id")
	    				&& mapf.get("id").equals(mapa.get("id"))){
	    			sign++;
	    		}
			}
	    	if(sign == 1){
	    		realMap = mapf;
	    		break;
	    	}
		}
	    //如果realMap等于null。那么说明没有，可以添加，如果存在的话，那么需要请求啦
	    if(null == realMap){
	    	return null;
	    }else{
			return getCourseInfoByCourseId(courseId,userId,realMap.get("id").toString());
	    }
	}
	
	
	
	
	public static void main(String[] args)
    {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
//        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.add(Calendar.YEAR, 1);
        date = calendar.getTime();
    }
	
	
}
