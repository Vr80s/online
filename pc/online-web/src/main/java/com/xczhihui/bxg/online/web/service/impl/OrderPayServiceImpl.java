package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.online.api.service.OrderPayService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.api.vo.OrderVo;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.dao.OrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

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
public class OrderPayServiceImpl extends OnlineBaseServiceImpl implements OrderPayService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderDao   orderDao;

	@Value("${share.course.id:191}")
	private String shareCourseId;
	@Autowired
	UserCoinService userCoinService;

   /**
    * Description：购买课程支付成功接口
    * creed: Talk is cheap,show me the code
    * @author name：yuxin <br>email: yuruixin@ixincheng.com
    * @Date: 下午 9:10 2018/1/24 0024
    **/
    @Override
    public void addPaySuccess(String orderNo,Integer payType,String transactionId) {
    	String sql = "";
    	String id = "";
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	
    	//查未支付的订单
    	sql = "select od.actual_pay,od.course_id,o.user_id,o.create_person,od.class_id from oe_order o,oe_order_detail od "
    			+ " where o.id = od.order_id and  o.order_no='"+orderNo+"' and order_status=0 ";
    	List<OrderVo> orders = orderDao.getNamedParameterJdbcTemplate().query(sql, new BeanPropertyRowMapper<OrderVo>(OrderVo.class));
    	if (orders.size() > 0) {
    		//更新订单表
			sql = "update oe_order set order_status=1,pay_type="+payType+",pay_time=now(),pay_account='"+ transactionId +"' where order_no='"+orderNo+"' ";
			orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
    		
    		//写用户报名信息表，如果有就不写了
			String apply_id = UUID.randomUUID().toString().replace("-", "");
			sql = "select a.id from oe_apply a where  a.user_id='"+orders.get(0).getUser_id()+"' ";
			List<Map<String, Object>> applies = orderDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
			if (applies.size() > 0) {
				apply_id = applies.get(0).get("id").toString();
			} else {
				sql = "insert into oe_apply(id,user_id,create_time,is_delete,create_person) "
						+ " values ('"+apply_id+"','"+orders.get(0).getUser_id()+"',now(),0,'"+orders.get(0).getCreate_person()+"')";
				orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
			}
			
			//更新用户is_apply为true（报过课）
			sql = "update oe_user set is_apply=1 where id='"+orders.get(0).getUser_id()+"' and is_apply=0";
			orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
    		
			for (OrderVo order : orders){
				int  gradeId = 0;

				//写用户、报名、课程中间表
				id = UUID.randomUUID().toString().replace("-", "");
				sql = "select (ifnull(max(cast(student_number as signed)),'0'))+1 from apply_r_grade_course where grade_id="+gradeId;
				Integer no = orderDao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class);
				String sno = no < 10 ? "00"+no : (no < 100 ? "0"+no : no.toString());
				sql = "insert into apply_r_grade_course (id,course_id,grade_id,apply_id,is_payment,create_person,user_id,create_time,cost,student_number,order_no)"
						+ " values('"+id+"',"+order.getCourse_id()+","+gradeId+",'"+apply_id+"',2,'"+order.getCreate_person()+"','"+order.getUser_id()+"',now(),"+order.getActual_pay()+","
						+ " '"+sno+"',"+"'"+orderNo+"')";
				orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
				//给主播分成
				userCoinService.updateBalanceForCourse(order);

				/*//如果是限时免费课程，不参与分销，业务到此结束
				if(Double.valueOf(order.getActual_pay())==0){
					continue;
				}

				//如果是购买大使课，成为分享大使
				if (shareCourseId.trim().equals(order.getCourse_id().toString().trim())) {
					id = UUID.randomUUID().toString().replace("-", "");
					orderDao.getNamedParameterJdbcTemplate().update("update oe_user set share_code='"+id+"' "
							+ "where share_code is null and id='"+order.getUser_id()+"' ", paramMap);
				}

				//如果是微课参与分销，如果购买人有上级，此订单更新为分销订单
				sql = "update oe_order o set o.order_from=1 where o.order_no='"+orderNo+"' "
						+ " and o.user_id=(select id from oe_user where parent_id is not null and parent_id != '' and id = o.user_id);";
				orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);*/
			}
		}
	}

}
