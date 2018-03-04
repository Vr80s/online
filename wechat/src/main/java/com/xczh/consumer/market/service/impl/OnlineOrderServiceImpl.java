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
import com.xczh.consumer.market.dao.OLCourseMapper;
import com.xczh.consumer.market.dao.OnlineOrderMapper;
import com.xczh.consumer.market.dao.OnlineUserMapper;
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
	private OLCourseMapper oLCourseMapper;
	@Autowired
	private OnlineUserMapper userMapper;

	@Autowired
	private PayRecordMapper payRecordMapper;

	@Autowired
	private OnlineUserService o;
	
	@Override
	public ResponseObject regenerateOrder(int courseId,String orderId,String orderNo,String userId, Integer orderFrom) throws SQLException {
		/**
		 * 判断是否存在此课程？
		 *    from oe_course oc,course_r_lecturer ct,oe_lecturer ot,oe_course_mobile ocm  进行判断
		 * 判断是否存在此用户？
		 */
		OnlineUser user = userMapper.findUserById(userId);
		if(null == user){
			return ResponseObject.newErrorResponseObject("查询不到用户信息！");
		}
		CourseLecturVo course = oLCourseMapper.courseDetail1(courseId,userId);
		if(null == course){
			return ResponseObject.newErrorResponseObject("查询不到课程信息！");
		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		/**
		 * 原来只判断了一种情况，单个订单。如果是多个订单呢？
		 */
		createOrder2(course, user,orderFrom,orderId,orderNo);
		returnMap.put("orderNo", orderNo);
	    returnMap.put("orderId",orderId);
		return ResponseObject.newSuccessResponseObject(returnMap);
	}


	@Override
	public ResponseObject addOrder(int courseId, String userId, Integer orderFrom) throws SQLException {
		/**
		 * 判断是否存在此课程？
		 *    from oe_course oc,course_r_lecturer ct,oe_lecturer ot,oe_course_mobile ocm  进行判断
		 * 判断是否存在此用户？
		 */
		OnlineUser user = userMapper.findUserById(userId);
		if(null == user){
			return ResponseObject.newErrorResponseObject("查询不到用户信息！");
		}
		CourseLecturVo course = oLCourseMapper.courseDetail1(courseId,userId);
		if(null == course){
			return ResponseObject.newErrorResponseObject("查询不到课程信息！");
		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		/*
		 * 查找订单详情
		 *   也就是这个订单已经存在了，
		 */
		//支付状态 0:未支付 1:已支付 2:已关闭 
		/**
		 * 原来只判断了一种情况，单个订单。如果是多个订单呢？
		 */
		OnlineOrder order_query = orderMapper.getOrderCourseInfoByUserId(courseId,userId);
		String orderId =UUID.randomUUID().toString().replace("-", "");
		if(null != order_query){
	        long createTime = order_query.getCreateTime().getTime();//订单创建时间
	        long now = new Date().getTime();
	         //也就是这个未支付的订单，超过一天就失效了，就直接取消。在生成一个新订单
	        
	        if((now - createTime)/1000 > 60 * 60 * 24){  
	        	//取消这个订单
	        	orderMapper.updateOnlineOrderStatus(1, order_query.getOrderNo()); 
	        	//创建一个新订单
	        	String orderNo = createOrder1(course, user,orderFrom,orderId);
	        	returnMap.put("orderNo", orderNo);
	        	returnMap.put("orderId", orderId);
	        }else{                                      //也就是这个未支付的订单，且时间较短，就用原来的订单。

	        	returnMap.put("orderNo", order_query.getOrderNo());
	        	returnMap.put("orderId", order_query.getId());
	        }
	        returnMap.put("courseName", course.getGradeName());
	        returnMap.put("currentPrice", course.getCurrentPrice());
	        return ResponseObject.newSuccessResponseObject(returnMap);
		}
		String orderNo = createOrder1(course, user,orderFrom,orderId);
		returnMap.put("orderNo", orderNo);
		returnMap.put("courseName", course.getGradeName());
	    returnMap.put("currentPrice", course.getCurrentPrice());
	    returnMap.put("orderId",orderId);
		return ResponseObject.newSuccessResponseObject(returnMap);
	} 
	/**
	 * 创建订单
	 * @param course
	 * @param user
	 * @throws Exception
	 */
	private String createOrder2(CourseLecturVo course, OnlineUser user, Integer orderFrom,
			String orderId,String orderNo) throws SQLException{
		OnlineOrder order = new OnlineOrder();
		order.setId(orderId);
		//String orderNo= TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
		order.setOrderNo(orderNo); //订单号
		order.setPreferentyMoney(0.0); //优惠金额
		order.setActualPay(course.getCurrentPrice());//实际支付
		order.setPurchaser(user.getName());  //购买者
		order.setCreatePerson(user.getLoginName());
		order.setCreateTime(new Date());     //创建时间
		order.setUserId(user.getId());       //用户ID
		order.setOrderFrom(orderFrom);               //订单来源，0直销（本系统），1分销系统，2线下（刷数据） 3:微信分销' 4:h5网页    5：手机app
		order.setPayAccount("暂无");          //支付账号
		order.setPayTime(new Date());
		orderMapper.addOrder(order);
		orderMapper.addOrderDetail(order.getId(), course.getCourseId(),course.getCurrentPrice());
		return orderNo;
	}
	/**
	 * 创建订单
	 * @param course
	 * @param user
	 * @throws Exception
	 */
	private String createOrder1(CourseLecturVo course, OnlineUser user, Integer orderFrom, String orderId) throws SQLException{
		OnlineOrder order = new OnlineOrder();
		order.setId(orderId);
		String orderNo= TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
		order.setOrderNo(orderNo); //订单号
		order.setPreferentyMoney(0.0); //优惠金额
		order.setActualPay(course.getCurrentPrice());//实际支付 人民币
		order.setPurchaser(user.getName());  //购买者
		order.setCreatePerson(user.getLoginName());
		order.setCreateTime(new Date());     //创建时间
		order.setUserId(user.getId());       //用户ID
		order.setOrderFrom(orderFrom);       //订单来源，0直销（本系统），1分销系统，2线下（刷数据） 3:微信分销' 4:h5网页    5：手机app 
		order.setPayAccount("暂无");          //支付账号
		order.setPayTime(new Date());
		orderMapper.addOrder(order);
		orderMapper.addOrderDetail(order.getId(), course.getCourseId(),course.getCurrentPrice());
		return orderNo;
	}
	/**
	 * 创建订单
	 * @param course
	 * @param user
	 * @throws Exception
	 */
	private String createOrder(WxcpCourseVo course, OnlineUser user, Integer orderFrom) throws SQLException{
		OnlineOrder order = new OnlineOrder();
		order.setId(UUID.randomUUID().toString().replace("-", ""));
		String orderNo= TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
		order.setOrderNo(orderNo); //订单号
		order.setPreferentyMoney(0.0); //优惠金额
		order.setActualPay(course.getCurrent_price());//实际支付
		order.setPurchaser(user.getName());  //购买者
		order.setCreatePerson(user.getLoginName());
		order.setCreateTime(new Date());     //创建时间
		order.setUserId(user.getId());       //用户ID
		order.setOrderFrom(orderFrom);               //订单来源，0直销（本系统），1分销系统，2线下（刷数据） 3:微信分销' 4:h5网页    5：手机app 
		order.setPayAccount("暂无");          //支付账号
		order.setPayTime(new Date());
		
		orderMapper.addOrder(order);
		orderMapper.addOrderDetail(order.getId(), course.getCourse_id(), course.getCurrent_price());
		return orderNo;
	}
	
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
	public ResponseObject getOrderAndCourseInfoByOrderNo(String orderNo) throws SQLException {
		
		OnlineOrder order = orderMapper.getOnlineOrderByOrderNo(orderNo);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(null == order){
			return ResponseObject.newErrorResponseObject("查询不到订单信息！");
		}
		returnMap.put("orderNo", order.getOrderNo());
		List<OnlineCourse> lists = orderMapper.getCourseByOrderId(order.getId());
		order.setAllCourse(lists);
		return ResponseObject.newSuccessResponseObject(order);
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
	public ResponseObject getOnlineOrderByOrderId(String orderId) throws SQLException {
		
		OnlineOrder order = orderMapper.getOnlineOrderByOrderId(orderId);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(null == order){
			return ResponseObject.newErrorResponseObject("查询不到订单信息！");
		}
		returnMap.put("orderNo", order.getOrderNo());
		List<OnlineCourse> lists = orderMapper.getCourseByOrderId(order.getId());
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
	public ResponseObject orderIsExitCourseIsBuy(String orderId,String userId,Integer orderStatus)
			throws SQLException {
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
		sql1.append(" select course_id as courseId,oo.id from  oe_order as oo,oe_order_detail as ord ");
		sql1.append(" where oo.id = ord.order_id and  ");
		sql1.append(" oo.user_id = ? and oo.order_status ="+orderStatus);
		Object params[] = {userId};
		List<Map<String,Object>> listUser = orderMapper.query(JdbcUtil.getCurrentConnection(), sql1.toString(),new MapListHandler(),params);
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select course_id  as courseId from oe_order_detail ");
		sql.append(" where order_id = ?  ");
		Object params1[] = {orderId};
		List<Map<String,Object>> listOod = orderMapper.query(JdbcUtil.getCurrentConnection(), sql.toString(),new MapListHandler(),params1);
		
		//如果不是订单是订单号呢
		for (Map<String, Object> map : listUser) {
			if(map.get("courseId")!=null){
				for (Map<String, Object> map1 : listOod) {
					if(map1.get("courseId")!=null){
						if(map.get("courseId").equals(map1.get("courseId"))){
							return ResponseObject.newErrorResponseObject("同学，此订单中包含有您已支付的课程"); 
						}
					}
				}
			}
		}
		return  ResponseObject.newSuccessResponseObject("可以购买哈"); 
	}
	@Override
	public String getCourseIdByOrderId(String orderId)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		sql.append(" select course_id  as courseId from oe_order_detail ");
		sql.append(" where order_id = ?  ");
		Object params1[] = {orderId};
		List<Map<String,Object>> listOod = orderMapper.query(JdbcUtil.getCurrentConnection(), sql.toString(),new MapListHandler(),params1);
		String str = "";
	    for (Map<String, Object> map : listOod) {
	    	if(map.get("courseId")!=null){
	    		str+=map.get("courseId")+",";
	    	}
		}
		if(str.indexOf(",")!=-1){
			str = str.substring(0,str.length()-1);
		}
		return  str;
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

//		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
//		Map<String,String> map = new HashMap<String, String>();
//		map.put("orderNo", "1");
//		map.put("courseId", "200");
//		map.put("join", "200");
//		Map<String,String> map1 = new HashMap<String, String>();
//		map1.put("orderNo", "2");
//		map1.put("courseId", "300");
//		map1.put("join", "200");
//		Map<String,String> map2 = new HashMap<String, String>();
//		map2.put("orderNo", "3");
//		map2.put("courseId", "400");
//		map2.put("join", "200");
//		Map<String,String> map3 = new HashMap<String, String>();
//		map3.put("orderNo", "1");
//		map3.put("courseId", "500");
//		map3.put("join", "500");
//		/*Map<String,String> map4 = new HashMap<String, String>();
//		map4.put("orderNo", "1");
//		map3.put("courseId", "500");
//		map2.put("join", "500");*/
//		list.add(map);
//		list.add(map1);
//		list.add(map2);
//		list.add(map3);
//
//		for (Map<String, String> map4 : list) {
//			for (Map<String, String> map5 : list) {
//				if(map4.get("orderNo").toString().equals(map5.get("orderNo"))){
//					if(!map4.get("courseId").toString().equals(map5.get("courseId"))){
//						String courseId = map4.get("courseId");
//						courseId+=(","+map5.get("courseId"));
//						map4.put("join", courseId);
//					}
//				}
//			}
//		}

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
	
	


	@Override
	public List<PayRecordVo> findUserWallet(Integer pageNumber,
			Integer pageSize, String userId) {
		try {
			return payRecordMapper.findUserWallet(userId,pageNumber,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
