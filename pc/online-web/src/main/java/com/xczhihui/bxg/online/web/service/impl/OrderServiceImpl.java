package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.MessageDao;
import com.xczhihui.bxg.online.web.dao.OrderDao;
import com.xczhihui.bxg.online.web.service.OrderService;
import com.xczhihui.bxg.online.web.vo.MessageShortVo;
import com.xczhihui.bxg.online.web.vo.OrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 订单业务层接口实现类
 *
 * @author 康荣彩
 * @create 2016-11-02 19:21
 */
@Service
public class OrderServiceImpl  extends OnlineBaseServiceImpl implements OrderService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderDao   orderDao;
	@Autowired
	private CourseDao  courseDao;
	@Autowired
	private MessageDao  messageDao;
	
    /**
     * 获取用户全部订单信息
     * @return 所有订单信息
     */
    @Override
    public Page<OrderVo> getMyAllOrder(Integer  orderStatus, Integer timeQuantum, Integer pageNumber, Integer pageSize, HttpServletRequest request){
        OnlineUser u =  (OnlineUser) request.getSession().getAttribute("_user_");
        if(u != null){
             return  orderDao.getMyAllOrder(orderStatus,timeQuantum,pageNumber, pageSize,u.getId());
        }
        return  null;
    }

	/**
	 * 为购买用户发送消息通知
	 */
	@Override
    public  void  savePurchaseNotice(String  basePath, String orderNo){
		//为购买用户发送消息通知
		List<Map<String, Object>> courses=courseDao.findNewestCourse(orderNo);
		if(courses.size() > 0) {
			for(Map<String,Object>  course : courses){
				String msg_id = UUID.randomUUID().toString().replaceAll("-", ""); //最新消息的id
				String msg_link = basePath+"/course/courses/" + course.get("course_id").toString();
				String content = "恭喜您成功报名课程<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\"><p>"+course.get("course_name")+"</p></a>";
				//"快去加入班级QQ群："+course.get("qqno")+"~";
				MessageShortVo messageShortVo = new MessageShortVo();
				messageShortVo.setUser_id(course.get("user_id").toString());
				messageShortVo.setId(msg_id);
				messageShortVo.setContext(content);
				messageShortVo.setCreate_person(course.get("user_id").toString());
				messageShortVo.setType(1);
				messageDao.saveMessage(messageShortVo);
			}
		}
		logger.info("发送课程消息通知"+orderNo);
	}
    
    @Override
    public Integer getOrderStatus(String orderNo) {
        return orderDao.getOrderStatus(orderNo);
    }

    @Override
    public Integer getOrderStatusById(String orderId) {
        return orderDao.getOrderStatusById(orderId);
    }
    
    @Override
	public Map<String, Object> checkPayInfo(String orderNo) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	String sql = "select o.actual_pay,GROUP_CONCAT(c.grade_name) as course_name,o.order_no,GROUP_CONCAT(c.id) as course_id,o.user_id from oe_order o,oe_order_detail od,oe_course c  "
    			+ "where o.id=od.order_id and od.course_id=c.id and o.order_status=0 and o.order_no=:order_no GROUP BY o.id";
    	paramMap.put("order_no", orderNo);
    	List<Map<String, Object>> lst = orderDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
    	if (lst != null && lst.size() > 0) {
			/** 在加入购物车的时候已经做了是否已经购买过课程的判断 */
    		String courseIds = (String) lst.get(0).get("course_id");
			String user_id = (String) lst.get(0).get("user_id");
			sql = "select count(t1.id) from oe_order t1,oe_order_detail t2 " +
					"where t1.id=t2.order_id and t2.course_id in ("+courseIds+") and t1.order_status=1 and t1.user_id=?";
			if (orderDao.queryForInt(sql,user_id) > 0) {
				lst.get(0).put("error_msg", "当前订单内有课程已经购买");
			}
			return lst.get(0);
		}
    	throw new RuntimeException("找不到订单"+orderNo+"信息！");
	}

	@Override
    public OrderVo findOrderByOrderId(String orderId){
		return  orderDao.findOrderByOrderId(orderId);
	}

	@Override
    public OrderVo findOrderByOrderNoAndStatus(String orderId,Integer status){
		return  orderDao.findOrderByOrderNoAndStatus(orderId,status);
	}

	@Override
	public String checkOrder(String orderNo) {
		return orderDao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList("select id from oe_order where order_no = ? and order_status = 1", orderNo)
				.size() > 0 ? "yes" : "no"; 
	}

	/**
	 * 取消订单或删除订单
	 * @param type:0删除订单 1:取消订单
	 * @param orderNo
	 */
	@Override
    public void  updateOrderStatu(Integer type, String orderNo, OnlineUser user){
		String sql="";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id",user.getId());
		paramMap.put("order_no", orderNo);
		if (type==0){ //删除失效订单
			sql ="select id from oe_order where order_no=:order_no and order_status =2 and user_id=:user_id";
			String orderId=orderDao.getNamedParameterJdbcTemplate().queryForObject(sql,paramMap,String.class);
			//删除订单表
			sql=" delete from oe_order where order_no=:order_no and user_id=:user_id and order_status=2";
			orderDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
            //删除订单详情表
			sql=" delete from oe_order_detail where order_id='"+orderId+"'";
			orderDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
		}else {
			sql=" update oe_order set order_status=2 where order_no=:order_no and user_id=:user_id and order_status=0";
			orderDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
			
			sql="update oe_fcode set status=2,used_user_id=null,used_course_id=null,use_order_no=null,use_time=null where use_order_no=:order_no ";
			orderDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
		}
	}


	/**
	 *查询目前购买的课程能参与的活动,以及每种活动下的购买课程信息
	 * @param ids 购买课程的id数组
	 * @return
	 */
	@Override
    public List<Map<String,Object>> findActivityOrder(HttpServletRequest request, String ids){
		return  orderDao.findActivityOrder(request,ids);
	}

	/**
	 * 获取用户购买课程所享受的所有活动，以及每种活动下能参与的课程
	 * @param ids
	 * @return
	 */
	@Override
    public List<Map<String,Object>>  findActivityCourse(String ids){
		return orderDao.findActivityCourse(ids);
	}

	/**
	 * 根据课程id查询课程
	 * @param idArr  课程id号
	 * @return 返回对应的课程对象
	 */
	@Override
    public  List<Map<String,Object>>   findNotActivityOrder(String  idArr, String orderNo, HttpServletRequest request){
		return  orderDao.findNotActivityOrder(idArr,orderNo,request);
	}

	@Override
	public Object consumptionList(String userId, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return orderDao.consumptionList(userId, pageNumber,pageSize);
	}

	@Override
	public void updateOrderNo(String orderNo, String orderId) {
		orderDao.updateOrderNo(orderNo,orderId);
	}
}
