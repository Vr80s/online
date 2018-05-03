package com.xczh.consumer.market.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.bean.OnlineCourse;
import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.dao.OnlineOrderMapper;
import com.xczh.consumer.market.dao.PayRecordMapper;
import com.xczh.consumer.market.service.OnlineOrderService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.utils.RandomUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.TimeUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.PayRecordVo;
import com.xczh.consumer.market.vo.WxcpCourseVo;

@Service
public class OnlineOrderServiceImpl implements OnlineOrderService {
	@Autowired
	private OnlineOrderMapper orderMapper;

	@Autowired
	private PayRecordMapper payRecordMapper;

	@Autowired
	private OnlineUserService o;
	
	@Override
	public List<OnlineOrder> getOnlineOrderList(Integer status, String userId, Integer pageNumber, Integer pageSize) throws SQLException {
		return orderMapper.getOnlineOrderList(status, userId, pageNumber,pageSize);
	}

	@Override
	public List<OnlineCourse> listLearningCenter(Integer type, String userId, Integer pageNumber, Integer pageSize) throws SQLException {
		return orderMapper.getLearningCenterList(type,userId,pageNumber,pageSize);
	}

	@Override
	public void updateOnlineOrderStatus(Integer type, String orderNo) throws SQLException {
		orderMapper.updateOnlineOrderStatus(type, orderNo);
	}

	@Override
	public OnlineOrder getOnlineOrderByOrderNo(String orderNo) throws SQLException {
		return orderMapper.getOnlineOrderByOrderNo(orderNo);
	}

	
	@Override
	public ResponseObject getNewOrderAndCourseInfoByOrderNo(String orderNo) throws SQLException {
		
		OnlineOrder order = orderMapper.getOnlineOrderByOrderNo(orderNo);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(null == order){
			return ResponseObject.newErrorResponseObject("查询不到订单信息！");
		}
		returnMap.put("orderNo", order.getOrderNo());
		List<OnlineCourse> lists = orderMapper.getNewCourseByOrderId(order.getId());
		order.setAllCourse(lists);
		return ResponseObject.newSuccessResponseObject(order);
	}
	
	
	@Override
	public void updateUserParentId(String id, String shareCode) throws Exception {
		Connection conn = JdbcUtil.getCurrentConnection();
		Map<String, Object> parentid = 
				orderMapper.query(conn,"select id from oe_user where share_code=?", new MapHandler(),shareCode);
		//判断，不是自己的shareCode
		if (!id.equals(parentid.get("id").toString())) {
			orderMapper.update(conn,
					"update oe_user set parent_id=? where id=? and parent_id is null",parentid.get("id").toString(),id);
		}
	}

	@Override
	public List<PayRecordVo> listPayRecord(String userId, Integer pageNumber, Integer pageSize) {

		try {
			return payRecordMapper.findByUserId(userId,pageNumber,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public PayRecordVo listPayRecordItem(String orderNo) {
		try {
			return payRecordMapper.findByOrderNo(orderNo);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getCourseNames(String orderNo) {
		String sql="SELECT  GROUP_CONCAT(c.grade_name) AS course_name FROM oe_order o, oe_order_detail od, oe_course c WHERE o.id = od.order_id AND od.course_id = c.id AND o.order_status = 0 AND o.order_no =? GROUP BY o.id";
		String  result=null;
		try {
			result=orderMapper.query(JdbcUtil.getCurrentConnection(), sql, new Object[]{orderNo}, new ResultSetHandler<String>(){
                @Override
                public String handle(ResultSet resultSet) throws SQLException {
                	resultSet.next();
					return resultSet.getString("course_name");

                }
            });
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String updateOrderNo(String oldOrderNo) throws SQLException {
		//修改一下原来的订单号（微信不能重复下单）
		String newOrder= TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
		orderMapper.update(JdbcUtil.getCurrentConnection(),"update oe_order set order_no=? where order_no=?",newOrder,oldOrderNo);
		return newOrder;
	}
	
	@Override
	public OnlineOrder getOrderByOrderId(String orderId) throws SQLException {
		OnlineOrder order = orderMapper.getOnlineOrderByOrderId(orderId);
		return order;
	}
	@Override
	public ResponseObject orderIsExitCourseIsBuy(String courseIds,String userId)
			throws SQLException {

		ResponseObject  ro = new ResponseObject();
		/**
		 * 1、查找当前要下单的课程id
		 * 2、查看用户已经支付的课程中是否存在
		 * 3、比较，如果有相等的话，那么就判断下吧
		 *
		 * 如果是多个订单的话怎么搞呢...
		 *   现在判断的还是单个，
		 *   重新生成的订单，这个订单号中的课程是否存在与未支付的订单号中的课程是否一致。
		 */
		StringBuffer sql1 = new StringBuffer();
		sql1.append(" select oo.id,oo.order_no as orderNo,oo.order_status as orderStatus,ood.course_id as courseId  "
				+ " ,course_id as 'join' from  oe_order as oo,oe_order_detail as ood ");
		sql1.append(" where  oo.id = ood.order_id and  oo.user_id = ? and oo.order_status = 0 "); //未支付
		Object params[] = {userId};
		List<Map<String,Object>> listUser = orderMapper.query(JdbcUtil.getCurrentConnection(), sql1.toString(),new MapListHandler(),params);

		String [] array1 = courseIds.split(",");
		boolean falg = false;
		/**
		 * 把订单号相同课程id的放到一块。方便比较
		 */
		for (Map<String, Object> map : listUser) {
		    for (Map<String, Object> map1 : listUser) {
		    	if(null!=map.get("orderNo")&& null!=map1.get("orderNo") &&
		    			map.get("orderNo").toString().equals(map1.get("orderNo"))){
		    		if(!map.get("courseId").toString().equals(map1.get("courseId"))){
						String courseIdss = map.get("courseId").toString();
						courseIdss+=(","+map1.get("courseId").toString());
						map.put("join", courseIdss);
					}
		    	}
			}
		}
		/**
		 * 存放到一块的课程id。与前台传递过来的进行比较，两个数组相等，那么就说明已经存在啦
		 */
		for (Map<String, Object> map : listUser) {
		     if(map.get("join")!=null){
		    		 String str = map.get("join").toString();
		    		 String [] array2 = str.split(",");
		             Arrays.sort(array1);
		             Arrays.sort(array2);
		             if (Arrays.equals(array1, array2)) {
		            	 //System.out.println("两个数组中的元素值相同");
		            	 falg = true;
		                 break;
		             } else {
		                 //System.out.println("两个数组中的元素值不相同");
		             }
		     }
		}
		//}
		if(falg){
			ro.setSuccess(true);
			ro.setResultObject("已经存在相同的订单了");
			ro.setCode(200);
			return  ro;
		}else{
			ro.setSuccess(true);
			ro.setResultObject("不存在相同的订单啦");
			ro.setCode(201);
			return  ro;
		}
	}

	@Override
	public ResponseObject getNewOrderAndCourseInfoByOrderId(String orderId) throws SQLException {
		OnlineOrder order =  orderMapper.getOnlineOrderByOrderId(orderId);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(null == order){
			return ResponseObject.newErrorResponseObject("查询不到订单信息！");
		}
		returnMap.put("orderNo", order.getOrderNo());
		List<OnlineCourse> lists = orderMapper.getNewCourseByOrderId(order.getId());
		if(null != lists && lists.size() > 0){
			/*OnlineCourse course = lists.get(0);
			returnMap.put("courseName", course.getGradeName());
			returnMap.put("currentPrice", course.getCurrentPrice());
			returnMap.put("orderId",order.getId());
			returnMap.put("courseId",course.getId());
			returnMap.put("smallimgPath",course.getSmallImgPath());*/
		}
		order.setAllCourse(lists);
		return ResponseObject.newSuccessResponseObject(order);
	}
	

	public static void main(String[] args) {
		 String [] array1 = {"1","2","3"};
         String [] array2 = {"3","2","1"};
         Arrays.sort(array1);
         Arrays.sort(array2);
         if (Arrays.equals(array1, array2)) {
                 System.out.println("两个数组中的元素值相同");
         } else {
                 System.out.println("两个数组中的元素值不相同");
         }
		//System.out.println(list.size());
	}
}
