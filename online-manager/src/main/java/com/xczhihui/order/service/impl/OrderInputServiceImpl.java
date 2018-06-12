package com.xczhihui.order.service.impl;

import java.util.*;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.enums.Payment;
import com.xczhihui.common.util.enums.UserOrigin;
import com.xczhihui.online.api.service.OrderPayService;
import com.xczhihui.online.api.service.UserCoinService;
import com.xczhihui.online.api.vo.OrderVo;
import com.xczhihui.order.service.OrderInputService;
import com.xczhihui.order.service.OrderService;
import com.xczhihui.order.vo.OrderInputVo;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.service.OnlineUserService;


/**
 * 线下订单录入
 * 
 * @author Haicheng Jiang
 */
@Service
public class OrderInputServiceImpl extends OnlineBaseServiceImpl implements OrderInputService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "simpleHibernateDao")
	private SimpleHibernateDao dao;

	@Autowired
	private OrderPayService orderPayServiceImpl;
	@Autowired
	private OrderService orderServiceImpl;
	@Autowired
	UserCoinService userCoinService;
	@Autowired
	OnlineUserService onlineUserService;
	@Autowired
	UserCenterService userCenterService;

	@Override
	public Page<OrderInputVo> findOrderInputPage(OrderInputVo orderVo,
			Integer pageNumber, Integer pageSize) {
		String sql = "SELECT \n" +
				"  t1.id, t1.course_name, t1.login_name, t1.actual_pay, t1.create_time, t2.`name` AS create_person , argc.`validity`,t1.order_from \n" +
				"FROM\n" +
				"  oe_order_input t1 JOIN\n" +
				"  `user` t2 \n" +
				"ON t1.create_person = t2.id \n" +
				"LEFT JOIN `oe_order` oo\n" +
				"ON t1.`id` = oo.`pay_account`\n" +
				"LEFT JOIN `oe_order_detail` ood\n" +
				"ON oo.id=ood.`order_id`\n" +
				"LEFT JOIN `apply_r_grade_course` argc\n" +
				"ON argc.`order_no` = ood.id ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (orderVo.getCreate_time_start() != null) {
			sql += " and t1.create_time >= :create_time_start ";
			paramMap.put("create_time_start", orderVo.getCreate_time_start());
		}
		if (orderVo.getCreate_time_end() != null) {
			sql += " and t1.create_time <= :create_time_end ";
			paramMap.put("create_time_end", orderVo.getCreate_time_end());
		}
		if (StringUtils.hasText(orderVo.getKey_value())) {
			if ("0".equals(orderVo.getKey_type())) {
				sql += " and t1.login_name = :login_name ";
				paramMap.put("login_name", orderVo.getKey_value());
			} else if ("1".equals(orderVo.getKey_type())) {
				sql += " and t1.course_name like :course_name ";
				paramMap.put("course_name", "%" + orderVo.getKey_value() + "%");
			} else if ("2".equals(orderVo.getKey_type())) {
				sql += " and t2.name = :create_person ";
				paramMap.put("create_person", orderVo.getKey_value());
			}
		}
		sql += " order by t1.create_time desc ";
		return dao.findPageBySQL(sql, paramMap, OrderInputVo.class, pageNumber,
				pageSize);
	}

	@Override
	public void addUser(String loginName) throws InterruptedException {

		boolean ism = Pattern.matches("^((1[0-9]))\\d{9}$", loginName);
		boolean ise = Pattern
				.matches(
						"^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
						loginName);
		if (!ism && !ise) {
			throw new RuntimeException("帐号请输入手机号或邮箱！");
		}

//		String name =  (Math.random() * 90000 + 10000)+"";

		OnlineUser ou = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", loginName);
		if (ou == null) {
			userCenterService.regist(loginName,loginName,loginName, UserOrigin.IMPORT);
			Thread.sleep(500);
		}
	}

	@Override
	public String addOrder(OrderInputVo vo) {
		OnlineUser user = onlineUserService.getUserByLoginName(vo.getLogin_name());

		// 查询条件
		String userId = user.getId();
		String courseId = vo.getCourse_id();

		Double coursePrice = getCoursePrice(courseId);
		//检验该用户是否已经购买过该课程
		hasCourse(userId, courseId,user.getLoginName());
		Integer orderFrom = vo.getOrder_from();
		String orderNo = orderServiceImpl.createOrder(user,courseId,coursePrice, orderFrom.toString());
		//保存订单导入记录
		String orderInputId = saveOrderInput(user, courseId, coursePrice, vo.getCreate_person(), orderFrom);

		// 执行支付成功方法
		orderPayServiceImpl.addPaySuccess(orderNo, Payment.OFFLINE, orderInputId);
		logger.info("用户" + vo.getLogin_name() + "购买" + courseId + "成功");
		return orderNo;
	}

	@Override
	public String saveOrderInput(OnlineUser user, String courseId, Double coursePrice, String createPerson, Integer orderFrom) {
		String courseName = getCourseName(courseId);
		// 写入记录
		String uuid = IStringUtil.getUuid();
		String sql = "insert into oe_order_input (id,login_name,course_id,course_name,actual_pay,create_person,user_id,order_from) "
				+ " values ('"
				+ uuid
				+ "','"
				+ user.getLoginName()
				+ "','"
				+ courseId
				+ "','"
				+ courseName
				+ "',"
				+ coursePrice
				+ ",'"
				+ createPerson
				+ "','"
				+ user.getId()
				+ "',"
				+ orderFrom + ") ";
		dao.getNamedParameterJdbcTemplate().update(sql, new HashMap<>());
		return uuid;
	}


	/**
	 * Description：校验课程信息是否有问题
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 2018/4/15 0015 下午 7:55
	 **/
	private Double getCoursePrice(String courseId) {
		// 查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("course_id", courseId);
		// 查询课程信息
		String sql = "select current_price currentPrice from oe_course where id=:course_id";
		List<Map<String, Object>> list = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		if (list.size()==0) {
			throw new RuntimeException("根据id“" + courseId + "”找不到课程信息！");
		} else if("0".equals(list.get(0).get("currentPrice"))){
			throw new RuntimeException("id“" + courseId + "”为免费课程，请直接登录学习！");
		}
		return (Double) list.get(0).get("currentPrice");
	}

	private String getCourseName(String courseId) {
		// 查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("course_id", courseId);
		// 查询课程信息
		String sql = "select grade_name courseName from oe_course where id=:course_id";
		String courseName = dao.getNamedParameterJdbcTemplate().queryForObject(sql,
				paramMap, String.class);
		return courseName;
	}

	/**
	 * Description：检验该用户是否购买过该课程
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 2018/4/15 0015 下午 7:55
	 **/
	public void hasCourse(String userId,String courseId,String loginName){
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		paramMap.put("courseId", courseId);
			String sql = "SELECT \n" +
					"  COUNT(1) \n" +
					"FROM\n" +
					"  apply_r_grade_course argc \n" +
					"WHERE argc.`user_id` = :userId \n" +
					"  AND argc.course_id = :courseId AND argc.`validity`>NOW()  ";
		Integer count = dao.getNamedParameterJdbcTemplate().queryForObject(sql,
				paramMap, Integer.class);
		if (count > 0) {
			throw new RuntimeException("此用户“" +loginName + "”已经购买了“" + courseId + "”课程");
		}
	}
	@Override
	public void checkOrderInput(OrderInputVo vo) {
		OnlineUser user = onlineUserService.getUserByLoginName(vo.getLogin_name());

		// 查询条件
		String userId = user.getId();
		String courseId = vo.getCourse_id();

		getCoursePrice(courseId);
		//检验该用户是否已经购买过该课程
		hasCourse(userId, courseId,user.getLoginName());
	}

	@Override
	public void addOrders(List<OrderInputVo> lv) {
		for (OrderInputVo orderInputVo : lv) {
			addOrder(orderInputVo);
		}
	}

	@Override
	public void updateValidity(String[] inputOrderIds, String days) {
		for (String inputOrderId : inputOrderIds) {
			OrderVo order = getOrderByInputOrderId(inputOrderId);
			if(order!=null){
				String sql = "update apply_r_grade_course set validity=:validity where order_no=:orderNo ";
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("validity",getValidity(days));
				paramMap.put("orderNo",order.getOrderDetailId());
				dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
			}
		}
	}

	private Date getValidity(String d){
		try {
			double days = Double.valueOf(d);
			int min = Double.valueOf(days * 24 * 60).intValue();
			Calendar now=Calendar.getInstance();
			now.add(Calendar.MINUTE,min);
			return now.getTime();
		}catch (Exception e){
			throw new RuntimeException("天数有误");
		}
	}

	private OrderVo getOrderByInputOrderId(String inputOrderId) {
		// 查未支付的订单
		String sql = "select od.id orderDetailId ,o.id orderId ,od.actual_pay,od.course_id,o.user_id,o.create_person,od.class_id,o.`order_from` "
				+ "from oe_order o,oe_order_detail od "
				+ " where o.id = od.order_id  and  o.pay_account='"
				+ inputOrderId
				+ "'";
		List<OrderVo> orders = dao.getNamedParameterJdbcTemplate().query(sql,
				new BeanPropertyRowMapper<OrderVo>(OrderVo.class));
		return orders.size()>0?orders.get(0):null;
	}

}
