package com.xczhihui.bxg.online.manager.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.xczhihui.bxg.online.manager.vhall.VhallUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.BeanUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.UserCoin;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseDao;
import com.xczhihui.bxg.online.manager.order.service.OrderInputService;
import com.xczhihui.bxg.online.manager.order.vo.OrderInputVo;
import com.xczhihui.bxg.online.manager.utils.RandomUtil;
import com.xczhihui.bxg.online.manager.utils.TimeUtil;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.user.center.bean.UserOrigin;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;
/**
 * 线下订单录入
 * @author Haicheng Jiang
 */
@Service
public class OrderInputServiceImpl extends OnlineBaseServiceImpl implements OrderInputService {

	@Resource(name = "simpleHibernateDao")
	private SimpleHibernateDao dao;

	@Autowired
	private UserCenterAPI userCenterAPI;
	@Autowired
	private CourseDao courseDao;


	@Override
	public Page<OrderInputVo> findOrderInputPage(OrderInputVo orderVo, Integer pageNumber, Integer pageSize) {
		String sql = "select t1.course_name,t1.login_name,t1.actual_pay,t1.create_time,t2.`name` as create_person "
				+ " from oe_order_input t1,`user` t2 where t1.create_person=t2.id ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (orderVo.getCreate_time_start() != null) {
			sql+=" and t1.create_time >= :create_time_start ";
			paramMap.put("create_time_start", orderVo.getCreate_time_start());
		}
		if (orderVo.getCreate_time_end() != null) {
			sql+=" and t1.create_time <= :create_time_end ";
			paramMap.put("create_time_end", orderVo.getCreate_time_end());
		}
		if (StringUtils.hasText(orderVo.getKey_value())) {
			if ("0".equals(orderVo.getKey_type())) {
				sql+=" and t1.login_name = :login_name ";
				paramMap.put("login_name", orderVo.getKey_value());
			} else if ("1".equals(orderVo.getKey_type())) {
				sql+=" and t1.course_name like :course_name ";
				paramMap.put("course_name", "%"+orderVo.getKey_value()+"%");
			} else if ("2".equals(orderVo.getKey_type())) {
				sql+=" and t2.name = :create_person ";
				paramMap.put("create_person", orderVo.getKey_value());
			}
		}
		sql += " order by t1.create_time desc ";
		return dao.findPageBySQL(sql, paramMap, OrderInputVo.class, pageNumber, pageSize);
	}

	@Override
	public void addUser(String loginName) throws InterruptedException {

		boolean ism = Pattern.matches("^((1[0-9]))\\d{9}$", loginName);
		boolean ise = Pattern.matches("^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",loginName);
		if (!ism && !ise) {
			throw new RuntimeException("账号请输入手机号或邮箱！");
		}

		String name = "ixincheng_"+ (int)(Math.random()*90000+10000);
		boolean delete = false;

		ItcastUser user = userCenterAPI.getUser(loginName);
		if (user == null) {
			//向用户中心注册
			if (userCenterAPI.getUser(loginName) == null) {
				userCenterAPI.regist(loginName, loginName, name, UserSex.UNKNOWN, null,
						loginName, UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
			}
		} else {
			name = user.getNikeName();
			delete = user.getStatus() == -1 ? true : false;
		}

		OnlineUser ou = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", loginName);
		if (ou == null) {

			//保存本地库
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
			u.setSmallHeadPhoto("http://www.ixincheng.com/web/images/defaultHead/" + (int) (Math.random() * 20 + 1)+".png");//时间紧张，暂时写死
			u.setVisitSum(0);
			u.setStayTime(0);
			u.setUserType(0);
			u.setMenuId(-1);
			u.setIsLecturer(0);
			u.setRoomNumber(0);
			dao.save(u);
			UserCoin userCoin = new UserCoin();
			userCoin.setUserId(u.getId());
			userCoin.setBalance(BigDecimal.ZERO);;
			userCoin.setBalanceGive(BigDecimal.ZERO);
			userCoin.setBalanceRewardGift(BigDecimal.ZERO);
			userCoin.setDeleted(false);
			userCoin.setCreateTime(new Date());
			userCoin.setStatus(true);
			userCoin.setVersion(BeanUtil.getUUID());
			dao.save(userCoin);
			Thread.sleep(1000);
			if(u.getVhallId()==null){
				String vhallId = VhallUtil.createUser(u,"123456");
				u.setVhallId(vhallId);
				u.setVhallPass("123456");
				u.setVhallName(u.getId());
				dao.update(u);
			}
		}
	}

	@Override
	public String addOrder(OrderInputVo vo) {
//		String order_no = UUID.randomUUID().toString().replace("-", "");
		String order_no = TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
		//查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("login_name", vo.getLogin_name());
		paramMap.put("course_id", vo.getCourse_id());

		//查询课程信息
		Map<String, Object> cmap = null;
		String sql = "select grade_name,current_price,is_free,original_cost from oe_course where id=:course_id";
		List<Map<String, Object>> cmaplst = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		if (cmaplst.size() > 0) {
			cmap = cmaplst.get(0);
		} else {
			throw new RuntimeException("根据id“"+vo.getCourse_id()+"”找不到课程信息！");
		}
		if (Boolean.valueOf(cmap.get("is_free").toString())
				|| (Double.valueOf(cmap.get("original_cost").toString()) == 0
				&& Double.valueOf(cmap.get("current_price").toString()) == 0)) {
			throw new RuntimeException("id“"+vo.getCourse_id()+"”为免费课程，请直接登陆学习！");
		}

		//查询用户信息
		sql = "select * from oe_user where login_name=:login_name";
		List<Map<String, Object>> lm = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		Map<String, Object> umap = new HashMap<>();
		if(lm.size()>0){
			umap = lm.get(0);
		}
		if(!umap.isEmpty()){
			paramMap.put("user_id", umap.get("id").toString());
			vo.setUser_id((String) umap.get("id"));

			sql = "select count(1) from oe_user t1,oe_apply t2,apply_r_grade_course t3 "
					+ " where t1.id = t2.user_id and t2.id = t3.apply_id and "
					+ " t1.login_name = :login_name and t3.course_id = :course_id ";
			if (dao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class) > 0) {
				throw new RuntimeException("此用户“"+vo.getLogin_name()+"”已经购买了“"+vo.getCourse_id()+"”课程");
			}
		}
		//查询订单
		sql = "select t1.* from oe_order t1,oe_order_detail t2 "
				+ " where t1.id = t2.order_id and t1.user_id = :user_id and t2.course_id = :course_id";
		List<Map<String, Object>> lst = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);

		//如果不存在未支付的订单，就创建订单
		if (lst.size() <= 0 || Integer.valueOf(lst.get(0).get("order_status").toString()) == 2) {
			//写订单主表
			String id = UUID.randomUUID().toString().replace("-", "");
			String did = UUID.randomUUID().toString().replace("-", "");
			sql = "insert into oe_order (id,create_person,order_no,"
					+ "actual_pay,purchaser,order_status,user_id,order_from) "
					+ " values ('"+id+"','"+vo.getLogin_name()+"',"
					+ "'"+order_no+"',"+cmap.get("current_price")+",'"+umap.get("name")+"',0,'"
					+ umap.get("id")+"',"+vo.getOrder_from()+")";
			dao.getNamedParameterJdbcTemplate().update(sql, paramMap);

			//写订单明细表
			String class_id = (vo.getClass_id() != null && !"".equals(vo.getClass_id().trim()))
					? ("'"+vo.getClass_id()+"'") : "null";//班级id，可以选班
			sql = "insert into oe_order_detail (id,order_id,course_id,actual_pay,activity_rule_detal_id,price,class_id) "
					+ "values('"+did+"','"+id+"','"+vo.getCourse_id()+"',"
					+ cmap.get("current_price").toString()+",null,"+cmap.get("current_price").toString()+","+class_id+")";
			dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
		} else {
			order_no = lst.get(0).get("order_no").toString();
		}

		//写入记录
		sql = "insert into oe_order_input (id,login_name,course_id,course_name,actual_pay,create_person,user_id,order_from) "
				+ " values ('"+UUID.randomUUID().toString().replace("-", "")+"','"+vo.getLogin_name()+"','"
				+ vo.getCourse_id()+"','"+cmap.get("grade_name")+"',"+cmap.get("current_price")+",'"
				+ vo.getCreate_person()+"','"+umap.get("id")+"',"+vo.getOrder_from()+") ";
		dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
//		subscribe(vo);将预约注释掉
		System.out.println("用户"+vo.getLogin_name()+"购买"+vo.getCourse_id()+"加入"+vo.getClass_id()+"成功");
		return order_no;
	}

	@Override
	public void checkOrderInput(OrderInputVo vo) {
		//查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("login_name", vo.getLogin_name());
		paramMap.put("course_id", vo.getCourse_id());
		paramMap.put("class_id", vo.getClass_id());
		//查询课程信息
		Map<String, Object> cmap = null;
		String sql = "select grade_name,current_price,is_free,original_cost,type from oe_course where id=:course_id";
		List<Map<String, Object>> cmaplst = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		if (cmaplst.size() > 0) {
			cmap = cmaplst.get(0);
		} else {
			throw new RuntimeException("根据id“"+vo.getCourse_id()+"”找不到课程信息！");
		}
		if (Boolean.valueOf(cmap.get("is_free").toString())
				|| (Double.valueOf(cmap.get("original_cost").toString()) == 0
				&& Double.valueOf(cmap.get("current_price").toString()) == 0)) {
			throw new RuntimeException("id“"+vo.getCourse_id()+"”为免费课程，请直接登陆学习！");
		}
		if (cmap.get("type")==null){
			//查询班级信息
			sql = "SELECT id FROM `oe_grade` WHERE id=:class_id AND course_id=course_id";
			List<Map<String, Object>> gradelist = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
			if (gradelist.size() < 1) {
				throw new RuntimeException("找不到课程id"+vo.getCourse_id()+"对应的班级"+vo.getClass_id()+"信息！");
			}
		}

		//查询用户信息
		sql = "select * from oe_user where login_name=:login_name";
        List<Map<String, Object>> lm = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
        Map<String, Object> umap = new HashMap<>();
        if(lm.size()>0){
            umap = lm.get(0);
        }
		if(!umap.isEmpty()){
			paramMap.put("user_id", umap.get("id").toString());
			vo.setUser_id((String) umap.get("id"));

			sql = "select count(1) from oe_user t1,oe_apply t2,apply_r_grade_course t3 "
					+ " where t1.id = t2.user_id and t2.id = t3.apply_id and "
					+ " t1.login_name = :login_name and t3.course_id = :course_id ";
			if (dao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class) > 0) {
				throw new RuntimeException("此用户“"+vo.getLogin_name()+"”已经购买了“"+vo.getCourse_id()+"”课程");
			}
		}
	}

	public void subscribe(OrderInputVo vo){
		Course c = courseDao.getCourseById(Integer.valueOf(vo.getCourse_id()));
		if(c.getType()!=null&&c.getType()==1 && !isSubscribe(vo.getUser_id(),Integer.valueOf(vo.getCourse_id()))){
			insertSubscribe(vo.getUser_id(), vo.getLogin_name(), Integer.valueOf(vo.getClass_id()));
		}
	}

	/**
	 * 查询用户是否预约过
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public boolean isSubscribe(String userId,Integer courseId){
		String sql=" select count(*) as allCount from oe_course_subscribe where course_id =? and user_id = ?";
		int ct=dao.queryForInt(sql.toString(),courseId,userId);
		return ct>=1?true:false;
	}

	public Integer insertSubscribe(String userId,String mobile,Integer courseId){
		String sql= " insert into oe_course_subscribe(course_id,user_id,phone,create_time ) values (?,?,?,?) ";
		int ct=dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,courseId,userId,mobile,new Date());
		return ct;
	}
}
