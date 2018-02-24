package com.xczhihui.bxg.online.manager.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.util.BeanUtil;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.UserCoin;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.manager.common.util.PasswordUtil;
import com.xczhihui.bxg.online.manager.common.web.UserVo;
import com.xczhihui.bxg.online.manager.user.dao.UserDao;
import com.xczhihui.bxg.online.manager.user.service.RoleService;
import com.xczhihui.bxg.online.manager.user.service.UserService;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.PageVo;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.UserOrigin;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;
import com.xczhihui.user.center.utils.CodeUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String pwd = "123456";
	private UserDao userDao;

	private RoleService roleService;

	@Autowired
	private UserCenterAPI userCenterAPI;

	@Override
	public List<User> findUsersByRole(String roleCode) {
		return this.userDao.findUsersByRole(roleCode);
	}

	@Override
	public User getUserById(String id) {
		return this.userDao.get(id, User.class);
	}

	@Override
	public User getUserByLoginName(String loginName) {
		return this.userDao.getUserByLoginName(loginName);
	}

	@Override
	public void addUser(User user) {
		if (!StringUtils.hasText(user.getPassword())) {
			user.setPassword(pwd);
		}
		this.userCenterAPI.regist(user.getLoginName(), 
				user.getPassword(), 
				user.getName(), 
				UserSex.parse(user.getSex()), 
				user.getEmail(), 
				user.getMobile(), 
				UserType.TEACHER,
				UserOrigin.DUAL,
				UserStatus.NORMAL);
		user.setCreateTime(new Date());
		String pwd = PasswordUtil.encodeUserPassword(user);
		user.setPassword(pwd);
		this.userDao.save(user);
	}

	@Override
	public void deleteUser(User teacher) {
		this.userCenterAPI.deleteUser(teacher.getLoginName());
		this.userDao.delete(teacher);
		this.userDao.deleteUserRolesByUserId(teacher.getId());
	}

	@Override
	public Page<User> findUserPage(Boolean isDelete, String roleId,String searchName, int pageNumber, int pageSize) {
		Page<User> page = userDao.findUserPage(isDelete, roleId,searchName, pageNumber, pageSize);
		List<User> users = page.getItems();
		Set<String> userIds = new HashSet<>();
		users.forEach(u -> {
			String str = DateUtil.formatDate(u.getCreateTime(), DateUtil.FORMAT_DAY);
			u.setCreateTimeStr(str);
			userIds.add(u.getId());
		});
		Map<String, Set<String>> userRoles = this.roleService.getUserRoles(userIds);
		for (User u : users) {
			Set<String> rs = userRoles.get(u.getId());
			if (rs != null && rs.size() > 0) {
				String roles = org.apache.commons.lang.StringUtils.join(rs, ',');
				u.setRoleNames(roles);
			}
			User us = userDao.findOneEntitiyByProperty(User.class, "id", u.getId());
			if (us != null) {
				u.setDelete(us.isDelete());
			}
		}
		return page;
	}
	@Override
    public List<UserVo> findAll(){
		List<UserVo> userVos=new CopyOnWriteArrayList<UserVo>();
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(OnlineUser.class);
			dc.add(Restrictions.eq("isDelete",false));
			dc.add(Restrictions.eq("status",0));
			dc.addOrder(Order.desc("createTime"));
			List<OnlineUser> users = this.userDao.findEntities(dc);
			if (users != null && users.size() > 0) {
				for (OnlineUser user : users) {
					UserVo userVo = new UserVo();
					BeanUtils.copyProperties(userVo, user);
					userVos.add(userVo);
				}
			}
		}catch(Exception e){
			this.logger.warn(e.getMessage());
		}
		return userVos;
	}
	@Override
	public List<UserVo> findAllUserByGrade(String gradeIds){
	    String sql="select DISTINCT a.user_id as id from apply_r_grade_course r,oe_apply a  where r.apply_id = a.id and r.grade_id in("+gradeIds+")";
		return this.userDao.getNamedParameterJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(UserVo.class));
	}

	@Override
	public PageVo findPageByGroups(Groups groups, PageVo page) {
		page = userDao.findTeacherPage(groups, page);
		@SuppressWarnings("rawtypes")
		List list = page.getItems();
		List<User> items = new ArrayList<User>();
		Set<String> userIds = new HashSet<>();
		for (Object object : list) {
			@SuppressWarnings("unchecked")
			Map<String, String> objs = (Map<String, String>) object;
			User teacher = this.genUserVo(objs);
			items.add(teacher);
			userIds.add(teacher.getId());
		}
		Map<String, Set<String>> userRoles = this.roleService.getUserRoles(userIds);
		for (User t : items) {
			Set<String> rs = userRoles.get(t.getId());
			if (rs != null && rs.size() > 0) {
				String roles = org.apache.commons.lang.StringUtils.join(rs, ',');
				t.setRoleNames(roles);
			}
		}
		page.setItems(items);
		return page;
	}

	private User genUserVo(Map<String, String> resultSet) {
		// select id,login_name,password,mobile,email,name,create_person,
		// create_time,is_delete,sex,qq,education,identity,description from user
		User user = new User();
		user.setId(resultSet.get("id"));
		user.setLoginName(resultSet.get("login_name"));
		user.setPassword(resultSet.get("password"));
		user.setMobile(resultSet.get("mobile"));
		user.setEmail(resultSet.get("email"));
		user.setName(resultSet.get("name"));
		user.setCreatePerson(resultSet.get("create_person"));

		String createTimeStr = resultSet.get("create_time");
		Date createTime = DateUtil.parseDate(createTimeStr, DateUtil.FORMAT_DAY_TIME);
		user.setCreateTime(createTime);
		createTimeStr = DateUtil.formatDate(createTime, DateUtil.FORMAT_DAY);
		user.setCreateTimeStr(createTimeStr);

		String delete = resultSet.get("is_delete");
		boolean isDelete = "1".equals(delete) || "true".equals(delete);
		user.setDelete(isDelete);
		String str = resultSet.get("sex");
		user.setSex(Integer.parseInt(str));
		user.setQq(resultSet.get("qq"));
		user.setEducation(resultSet.get("education"));
		user.setIdentity(resultSet.get("identity"));
		user.setDescription(resultSet.get("description"));
		return user;
	}

	@Override
	public User deleteUserLogic(String id) {
		User teacher = this.userDao.deleteLogic(id, User.class);
		this.userCenterAPI.updateStatus(teacher.getLoginName(), UserStatus.DISABLE.getValue());
		return teacher;
	}

	@Override
	public User deleteUser(String userId) {
		User user = this.userDao.delete(userId, User.class);
		this.userCenterAPI.deleteUser(user.getLoginName());
		Set<String> uids = new HashSet<>();
		uids.add(userId);
		this.roleService.updateUserRoles(uids, null);
		this.userDao.deleteUserRolesByUserId(userId);
		return user;
	}

	@Override
	public void deleteUsersLogic(List<String> ids) {
		if (ids == null || ids.size() < 1) {
			return;
		}
		List<User> users = this.userDao.findEntities(User.class, new HashSet<>(ids));
		for (User user : users) {
			this.userDao.deleteLogic(user);
			this.userCenterAPI.updateStatus(user.getLoginName(), UserStatus.DISABLE.getValue());
		}
	}

	@Override
	public void deleteUsers(List<String> userIds) {
		if (userIds == null || userIds.size() < 1) {
			return;
		}
		List<User> users = this.userDao.findEntities(User.class, new HashSet<>(userIds));
		for (User user : users) {
//			this.userDao.deleteUserRolesByUserId(user.getId());
//			this.userCenterService.deleteFromUCenter(user.getLoginName());
			this.deleteUser(user);
		}
//		this.roleService.updateUserRoles(new HashSet<>(userIds), null);
	}

	@Override
	public User updateUser(User user) {
		User old = this.userDao.get(user.getId(), User.class);
		old.setDelete(user.isDelete());
		old.setDescription(user.getDescription());
		old.setEducation(user.getEducation());
		old.setEmail(user.getEmail());
		old.setIdentity(user.getIdentity());
		old.setName(user.getName());
		old.setQq(user.getQq());
		String pwd = user.getPassword();
		if (StringUtils.hasText(pwd)) {
			// 填写了新密码或修改了手机号
			user.setPassword(pwd);
			String enpwd = PasswordUtil.encodeUserPassword(user);
			old.setPassword(enpwd);
			this.userCenterAPI.updatePassword(user.getLoginName(),null, pwd);
		}
		old.setMobile(user.getMobile());
		this.userDao.update(old);
		int status = old.isDelete() ? UserStatus.DISABLE.getValue() : UserStatus.NORMAL.getValue();
		this.userCenterAPI.update(old.getLoginName(), 
				old.getName(), 
				old.getSex(), 
				old.getEmail(), 
				old.getMobile(), 
				UserType.TEACHER.getValue(), 
				status);
		return old;
	}

	@Override
	public void updateUserPassword(String loginName, String password) {
		this.userCenterAPI.updatePassword(loginName,null, password);
		User t = this.userDao.getUserByLoginName(loginName);
		password = CodeUtil.encodePassword(password, t.getLoginName());
		t.setPassword(password);
		this.userDao.update(t);
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public void saveUserCoin() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OnlineUser.class);
		List<OnlineUser> users = userDao.findEntities(detachedCriteria);
		for (OnlineUser onlineUser : users) {
			DetachedCriteria dc = DetachedCriteria.forClass(UserCoin.class);
			dc.add(Restrictions.eq("userId",onlineUser.getId()));
			UserCoin uc=userDao.findEntity(dc);
			if(uc==null){
				saveUserCoin(onlineUser.getId());
			}
		}
		
	}
	
	public void saveUserCoin(String userId) {
		UserCoin userCoin = new UserCoin();
		userCoin.setUserId(userId);
		userCoin.setBalance(BigDecimal.ZERO);
		userCoin.setBalanceGive(BigDecimal.ZERO);
		userCoin.setBalanceRewardGift(BigDecimal.ZERO);
		userCoin.setDeleted(false);
		userCoin.setCreateTime(new Date());
		userCoin.setStatus(true);
		userCoin.setVersion(BeanUtil.getUUID());
		userDao.save(userCoin);
	}
}
