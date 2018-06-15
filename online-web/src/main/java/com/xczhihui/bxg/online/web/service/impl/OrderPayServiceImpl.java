package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.online.api.service.OrderPayService;
import com.xczhihui.online.api.service.UserCoinService;
import com.xczhihui.online.api.vo.OrderVo;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.common.util.enums.Payment;
import com.xczhihui.bxg.online.web.dao.OrderDao;
import com.xczhihui.bxg.online.web.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Description：支付成功课程业务
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 9:10 2018/1/24 0024
 **/
@Service
public class OrderPayServiceImpl extends OnlineBaseServiceImpl implements OrderPayService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderDao orderDao;

	@Autowired
	UserCoinService userCoinService;
	@Value("${web.url}")
	private String weburl;
	@Autowired
	private OrderService orderService;

   /**
    * Description：购买课程支付成功接口
    * creed: Talk is cheap,show me the code
    * @author name：yuxin <br>email: yuruixin@ixincheng.com
    * @Date: 下午 9:10 2018/1/24 0024
    **/
    @Override
    @Transactional
    public void addPaySuccess(String orderNo, Payment payment, String transactionId) {
    	if(payment==null){
    		throw new RuntimeException("支付类型不可为空");
		}
    	String sql = "";
    	String id = "";
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	
    	//查未支付的订单
    	sql = "select od.id orderDetailId ,o.id orderId ,od.actual_pay,od.course_id,o.user_id,o.create_person,od.class_id,o.`order_from` from oe_order o,oe_order_detail od "
    			+ " where o.id = od.order_id  and  o.order_no='"+orderNo+"' and order_status=0 ";
    	List<OrderVo> orders = orderDao.getNamedParameterJdbcTemplate().query(sql, new BeanPropertyRowMapper<OrderVo>(OrderVo.class));
    	if (orders.size() > 0) {
    		//更新订单表
			sql = "update oe_order set order_status=1,pay_type="+payment.getCode()+",pay_time=now(),pay_account='"+ transactionId +"' where order_no='"+orderNo+"' ";
			orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
    		
    		for (OrderVo order : orders){
				//插入支付类型
				order.setPayment(payment);
				int  gradeId = 0;

				//写用户、报名、课程中间表
				id = UUID.randomUUID().toString().replace("-", "");
				sql = "insert into  apply_r_grade_course(id,course_id,grade_id,apply_id,is_payment,create_person,user_id,create_time,cost,student_number,order_no)"
						+ " values('"+id+"',"+order.getCourse_id()+","+gradeId+",'"+0+"',2,'"+order.getCreate_person()+"','"+order.getUser_id()+"',now(),"+order.getActual_pay()+","
						+ " '"+0+"',"+"'"+order.getOrderDetailId()+"')";
				System.out.println(sql);
				orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);

				/*
				 * 将该用户所购买的课程对应的评论改为已购买
				 */
				Map<String,Object> paramMap1 = new HashMap<>();
				paramMap1.put("createPerson", order.getUser_id());
				paramMap1.put("courseId", order.getCourse_id());

				String sql1 = "update oe_criticize set is_buy = TRUE  where create_person =:createPerson and course_id=:courseId";
				orderDao.getNamedParameterJdbcTemplate().update(sql1, paramMap1);

			}

			//给主播分成
			try {
				userCoinService.updateBalanceForCourses(orders);
			}catch (Exception e){
				logger.info("订单分成失败，订单id:{}",orders.get(0).getOrderId());
//				e.printStackTrace();
			}
			//为购买用户发送购买成功的消息通知
			orderService.savePurchaseNotice(weburl, orderNo);

		}
	}

}
