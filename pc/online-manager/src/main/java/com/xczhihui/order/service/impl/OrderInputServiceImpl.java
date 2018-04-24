package com.xczhihui.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.common.util.OrderNoUtil;
import com.xczhihui.online.api.service.UserCoinService;
//import UserCoinService;
import com.xczhihui.online.api.vo.OrderVo;
import com.xczhihui.common.util.enums.Payment;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.vhall.VhallUtil;

import com.xczhihui.order.service.OrderInputService;
import com.xczhihui.order.vo.OrderInputVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.BeanUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.UserCoin;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.course.dao.CourseDao;
import com.xczhihui.utils.RandomUtil;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.user.center.bean.UserOrigin;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;

/**
 * 线下订单录入
 * 
 * @author Haicheng Jiang
 */
@Service
public class OrderInputServiceImpl extends OnlineBaseServiceImpl implements
		OrderInputService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "simpleHibernateDao")
	private SimpleHibernateDao dao;

	@Autowired
	private UserCenterAPI userCenterAPI;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	UserCoinService userCoinService;
	@Autowired
	OnlineUserService onlineUserService;

	@Override
	public Page<OrderInputVo> findOrderInputPage(OrderInputVo orderVo,
			Integer pageNumber, Integer pageSize) {
		String sql = "select t1.course_name,t1.login_name,t1.actual_pay,t1.create_time,t2.`name` as create_person "
				+ " from oe_order_input t1,`user` t2 where t1.create_person=t2.id ";
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

		String name = ""+(int) (Math.random() * 90000 + 10000);
		boolean delete = false;

		ItcastUser user = userCenterAPI.getUser(loginName);
		if (user == null) {
			// 向用户中心注册
			if (userCenterAPI.getUser(loginName) == null) {
				userCenterAPI.regist(loginName, loginName, name,
						UserSex.UNKNOWN, null, loginName, UserType.STUDENT,
						UserOrigin.ONLINE, UserStatus.NORMAL);
			}
		} else {
			name = user.getNikeName();
			delete = user.getStatus() == -1 ? true : false;
		}

		OnlineUser ou = dao.findOneEntitiyByProperty(OnlineUser.class,
				"loginName", loginName);
		if (ou == null) {

			// 保存本地库
			OnlineUser u = new OnlineUser();
			u.setLoginName(loginName);
			if (ism) {
				u.setMobile(u.getLoginName());
			}
			if (ise) {
				u.setEmail(u.getLoginName());
			}
			u.setStatus(0);
			u.setSex(UserSex.UNKNOWN.getValue());
			u.setCreateTime(new Date());
			u.setDelete(delete);
			u.setName(name);
			u.setSmallHeadPhoto("http://www.ixincheng.com/web/images/defaultHead/"
					+ (int) (Math.random() * 20 + 1) + ".png");// 时间紧张，暂时写死
			u.setVisitSum(0);
			u.setStayTime(0);
			u.setUserType(0);
			u.setMenuId(-1);
			u.setIsLecturer(0);
			u.setRoomNumber(0);
			dao.save(u);
			UserCoin userCoin = new UserCoin();
			userCoin.setUserId(u.getId());
			userCoin.setBalance(BigDecimal.ZERO);
			userCoin.setRmb(BigDecimal.ZERO);
			userCoin.setBalanceGive(BigDecimal.ZERO);
			userCoin.setBalanceRewardGift(BigDecimal.ZERO);
			userCoin.setDeleted(false);
			userCoin.setCreateTime(new Date());
			userCoin.setStatus(true);
			userCoin.setVersion(BeanUtil.getUUID());
			dao.save(userCoin);
			Thread.sleep(1000);
			if (u.getVhallId() == null) {
				String vhallPassword = RandomUtil.getCharAndNumr(6);
				String vhallId = VhallUtil.createUser(u, vhallPassword);
				u.setVhallId(vhallId);
				u.setVhallPass(vhallPassword);
				u.setVhallName(u.getId());
				dao.update(u);
			}
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
		String orderNo = createOrder(user,courseId,coursePrice, orderFrom.toString());
		//保存订单导入记录
		String orderInputId = saveOrderInput(user, courseId, coursePrice, vo.getCreate_person(), orderFrom);

		logger.info("用户" + vo.getLogin_name() + "购买" + courseId + "成功");
		// 执行支付成功方法
		addPaySuccess(orderNo, Payment.OFFLINE, orderInputId);
		return orderNo;
	}

	private String saveOrderInput(OnlineUser user, String courseId, Double coursePrice, String createPerson, Integer orderFrom) {
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

	private String createOrder(OnlineUser onlineUser,String courseId,Double coursePrice,String orderForm) {
		String orderNo = OrderNoUtil.getCourseOrderNo();
		// 写订单主表
		String id = IStringUtil.getUuid();
		String did = IStringUtil.getUuid();
		String sql = "insert into oe_order (id,create_person,order_no, actual_pay,purchaser,order_status,user_id,order_from) "
				+ " values ('"
				+ id
				+ "','"
				+ onlineUser.getLoginName()
				+ "',"
				+ "'"
				+ orderNo
				+ "',"
				+ coursePrice
				+ ",'"
				+ onlineUser.getName()
				+ "',0,'"
				+ onlineUser.getId()
				+ "',"
				+ orderForm + ")";
		dao.getNamedParameterJdbcTemplate().update(sql, new HashMap<>());

		// 写订单明细表
		sql = "insert into oe_order_detail (id,order_id,course_id,actual_pay,activity_rule_detal_id,price,class_id) "
				+ "values('"
				+ did
				+ "','"
				+ id
				+ "','"
				+ courseId
				+ "',"
				+ coursePrice
				+ ",null,"
				+ coursePrice
				+ ","
				+ null
				+ ")";
		dao.getNamedParameterJdbcTemplate().update(sql,  new HashMap<>());
		return orderNo;
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
		Double currentPrice = dao.getNamedParameterJdbcTemplate().queryForObject(sql,
				paramMap, Double.class);
		if (currentPrice == null) {
			throw new RuntimeException("根据id“" + courseId + "”找不到课程信息！");
		} else if(currentPrice == 0){
			throw new RuntimeException("id“" + courseId + "”为免费课程，请直接登录学习！");
		}
		return currentPrice;
	}

	private String getCourseName(String courseId) {
		// 查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("course_id", courseId);
		// 查询课程信息
		String sql = "select current_price currentPrice from oe_course where id=:course_id";
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
					"  AND argc.course_id = :courseId  ";
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

	public void subscribe(OrderInputVo vo) {
		Course c = courseDao.getCourseById(Integer.valueOf(vo.getCourse_id()));
		if (c.getType() != null
				&& c.getType() == 1
				&& !isSubscribe(vo.getUser_id(),
						Integer.valueOf(vo.getCourse_id()))) {
			insertSubscribe(vo.getUser_id(), vo.getLogin_name(),
					Integer.valueOf(vo.getClass_id()));
		}
	}

	/**
	 * Description：订单支付成功后业务处理 creed: Talk is cheap,show me the code
	 * 
	 * @author name：yuxin <br>
	 *         email: yuruixin@ixincheng.com
	 * @Date: 下午 10:02 2018/1/24 0024
	 **/
	public void addPaySuccess(String orderNo, Payment payment, String transactionId) {
		String sql = "";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<OrderVo> orders = getOrderByNo(orderNo);
		if (orders.size() > 0) {
			// 更新订单表
			sql = "update oe_order set order_status=1,pay_type="
					+ payment.getCode() + ",pay_time=now(),pay_account='"
					+ transactionId + "' where order_no='" + orderNo + "' ";
			dao.getNamedParameterJdbcTemplate().update(sql, paramMap);

			for (OrderVo order : orders) {
				order.setPayment(payment);
				//保存课程-用户关系表
				saveArgc(order);
			}
			// 给主播分成
			try {
				userCoinService.updateBalanceForCourses(orders);
			} catch (Exception e) {
				logger.info("订单分成失败，订单id:{}", orders.get(0).getOrderId());
			}
		}
	}

	private void saveArgc(OrderVo order) {
		int gradeId = 0;
		// 写课程中间表
		String id = IStringUtil.getUuid();
		String sql = "select (ifnull(max(cast(student_number as signed)),'0'))+1 from apply_r_grade_course where grade_id="
				+ gradeId;
		Integer no = dao.getNamedParameterJdbcTemplate()
				.queryForObject(sql, new HashMap<>(), Integer.class);
		String sno = no < 10 ? "00" + no : (no < 100 ? "0" + no : no
				.toString());
		sql = "insert into apply_r_grade_course (id,course_id,grade_id,apply_id,is_payment,create_person,user_id,create_time,cost,student_number,order_no)"
				+ " values('"
				+ id
				+ "',"
				+ order.getCourse_id()
				+ ","
				+ gradeId
				+ ",'"
				+ null
				+ "',2,'"
				+ order.getCreate_person()
				+ "','"
				+ order.getUser_id()
				+ "',now(),"
				+ order.getActual_pay()
				+ ","
				+ " '"
				+ sno
				+ "'," + "'" + order.getOrder_no() + "')";
		dao.getNamedParameterJdbcTemplate().update(sql, new HashMap<>());
	}

	private List<OrderVo> getOrderByNo(String orderNo) {
		// 查未支付的订单
		String sql = "select od.id orderDetailId ,o.id orderId ,od.actual_pay,od.course_id,o.user_id,o.create_person,od.class_id,o.`order_from` "
				+ "from oe_order o,oe_order_detail od "
				+ " where o.id = od.order_id  and  o.order_no='"
				+ orderNo
				+ "' and order_status=0 ";
		List<OrderVo> orders = dao.getNamedParameterJdbcTemplate().query(sql,
				new BeanPropertyRowMapper<OrderVo>(OrderVo.class));
		return orders;
	}

	/**
	 * 查询用户是否预约过
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public boolean isSubscribe(String userId, Integer courseId) {
		String sql = " select count(*) as allCount from oe_course_subscribe where course_id =? and user_id = ?";
		int ct = dao.queryForInt(sql.toString(), courseId, userId);
		return ct >= 1 ? true : false;
	}

	public Integer insertSubscribe(String userId, String mobile,
			Integer courseId) {
		String sql = " insert into oe_course_subscribe(course_id,user_id,phone,create_time ) values (?,?,?,?) ";
		int ct = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, courseId, userId, mobile, new Date());
		return ct;
	}
}
