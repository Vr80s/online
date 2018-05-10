package com.xczhihui.user.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import com.xczhihui.common.web.UserVo;
import com.xczhihui.user.dao.UserDao;
import com.xczhihui.user.service.PermResourceService;
import com.xczhihui.utils.Groups;
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

import com.xczhihui.common.util.BeanUtil;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.UserCoin;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.common.util.PasswordUtil;
import com.xczhihui.user.service.RoleService;
import com.xczhihui.user.service.UserService;
import com.xczhihui.utils.PageVo;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.utils.CodeUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String pwd = "123456";

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermResourceService permResourceService;

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
	public Set<String> findRoles(String userId) {
		return this.roleService.findRoleCodes(userId);
	}

	@Override
	public Set<String> findPermissions(String userId) {
		return this.permResourceService.findPermissions(userId);
	}

	@Override
	public void addUser(User user) {
		if (!StringUtils.hasText(user.getPassword())) {
			user.setPassword(pwd);
		}
		user.setCreateTime(new Date());
		String pwd = PasswordUtil.encodeUserPassword(user);
		user.setPassword(pwd);
		this.userDao.save(user);
	}

	@Override
	public void deleteUser(User user) {
		this.userDao.delete(user);
		this.userDao.deleteUserRolesByUserId(user.getId());
	}

	@Override
	public Page<User> findUserPage(Boolean isDelete, String roleId,
			String searchName, int pageNumber, int pageSize) {
		Page<User> page = userDao.findUserPage(isDelete, roleId, searchName,
				pageNumber, pageSize);
		List<User> users = page.getItems();
		Set<String> userIds = new HashSet<>();
		users.forEach(u -> {
			String str = DateUtil.formatDate(u.getCreateTime(),
					DateUtil.FORMAT_DAY);
			u.setCreateTimeStr(str);
			userIds.add(u.getId());
		});
		Map<String, Set<String>> userRoles = this.roleService
				.getUserRoles(userIds);
		for (User u : users) {
			Set<String> rs = userRoles.get(u.getId());
			if (rs != null && rs.size() > 0) {
				String roles = org.apache.commons.lang.StringUtils
						.join(rs, ',');
				u.setRoleNames(roles);
			}
			User us = userDao.findOneEntitiyByProperty(User.class, "id",
					u.getId());
			if (us != null) {
				u.setDelete(us.isDelete());
			}
		}
		return page;
	}

	@Override
	public List<UserVo> findAll() {
		List<UserVo> userVos = new CopyOnWriteArrayList<UserVo>();
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(OnlineUser.class);
			dc.add(Restrictions.eq("isDelete", false));
			dc.add(Restrictions.eq("status", 0));
			dc.addOrder(Order.desc("createTime"));
			List<OnlineUser> users = this.userDao.findEntities(dc);
			if (users != null && users.size() > 0) {
				for (OnlineUser user : users) {
					UserVo userVo = new UserVo();
					BeanUtils.copyProperties(userVo, user);
					userVos.add(userVo);
				}
			}
		} catch (Exception e) {
			this.logger.warn(e.getMessage());
		}
		return userVos;
	}

	@Override
	public List<UserVo> findAllUserByGrade(String gradeIds) {
		String sql = "select DISTINCT a.user_id as id from apply_r_grade_course r,oe_apply a  where r.apply_id = a.id and r.grade_id in("
				+ gradeIds + ")";
		return this.userDao.getNamedParameterJdbcTemplate().query(sql,
				BeanPropertyRowMapper.newInstance(UserVo.class));
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
		Map<String, Set<String>> userRoles = this.roleService
				.getUserRoles(userIds);
		for (User t : items) {
			Set<String> rs = userRoles.get(t.getId());
			if (rs != null && rs.size() > 0) {
				String roles = org.apache.commons.lang.StringUtils
						.join(rs, ',');
				t.setRoleNames(roles);
			}
		}
		page.setItems(items);
		return page;
	}

	private User genUserVo(Map<String, String> resultSet) {
		User user = new User();
		user.setId(resultSet.get("id"));
		user.setLoginName(resultSet.get("login_name"));
		user.setPassword(resultSet.get("password"));
		user.setMobile(resultSet.get("mobile"));
		user.setEmail(resultSet.get("email"));
		user.setName(resultSet.get("name"));
		user.setCreatePerson(resultSet.get("create_person"));

		String createTimeStr = resultSet.get("create_time");
		Date createTime = DateUtil.parseDate(createTimeStr,
				DateUtil.FORMAT_DAY_TIME);
		user.setCreateTime(createTime);
		createTimeStr = DateUtil.formatDate(createTime, DateUtil.FORMAT_DAY);
		user.setCreateTimeStr(createTimeStr);

		String delete = resultSet.get("is_delete");
		boolean isDelete = "1".equals(delete) || "true".equals(delete);
		user.setDelete(isDelete);
		String str = resultSet.get("sex");
		user.setQq(resultSet.get("qq"));
		user.setDescription(resultSet.get("description"));
		return user;
	}

	@Override
	public void deleteUserLogic(String id) {
		userDao.markDeleted(id);
	}

	@Override
	public void deleteUser(String userId) {
		User user = this.userDao.delete(userId, User.class);
		Set<String> uids = new HashSet<>();
		uids.add(userId);
		this.roleService.updateUserRoles(uids, null);
		this.userDao.deleteUserRolesByUserId(userId);
	}

	@Override
	public void deleteUsersLogic(List<String> ids) {
		if (ids == null || ids.size() < 1) {
			return;
		}
		for (String id : ids) {
			this.userDao.markDeleted(id);
		}
	}

	@Override
	public void deleteUsers(List<String> userIds) {
		if (userIds == null || userIds.size() < 1) {
			return;
		}
		List<User> users = this.userDao.findEntities(User.class, new HashSet<>(
				userIds));
		for (User user : users) {
			this.deleteUser(user);
		}
	}

	@Override
	public User updateUser(User user) {
		User old = this.userDao.get(user.getId(), User.class);
		old.setDelete(user.isDelete());
		old.setDescription(user.getDescription());
		old.setEmail(user.getEmail());
		old.setName(user.getName());
		old.setQq(user.getQq());
		String pwd = user.getPassword();
		if (StringUtils.hasText(pwd)) {
			// 填写了新密码或修改了手机号
			user.setPassword(pwd);
			String enpwd = PasswordUtil.encodeUserPassword(user);
			old.setPassword(enpwd);
		}
		old.setMobile(user.getMobile());
		this.userDao.update(old);
		int status = old.isDelete() ? UserStatus.DISABLE.getValue()
				: UserStatus.NORMAL.getValue();
		return old;
	}

	@Override
	public void updateUserPassword(String loginName, String password) {
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
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(OnlineUser.class);
		List<OnlineUser> users = userDao.findEntities(detachedCriteria);
		for (OnlineUser onlineUser : users) {
			DetachedCriteria dc = DetachedCriteria.forClass(UserCoin.class);
			dc.add(Restrictions.eq("userId", onlineUser.getId()));
			UserCoin uc = userDao.findEntity(dc);
			if (uc == null) {
				saveUserCoin(onlineUser.getId());
			}
		}

	}

	public void saveUserCoin(String userId) {
		UserCoin userCoin = new UserCoin();
		userCoin.setUserId(userId);
		userCoin.setBalance(BigDecimal.ZERO);
		userCoin.setRmb(BigDecimal.ZERO);
		userCoin.setBalanceGive(BigDecimal.ZERO);
		userCoin.setBalanceRewardGift(BigDecimal.ZERO);
		userCoin.setDeleted(false);
		userCoin.setCreateTime(new Date());
		userCoin.setStatus(true);
		userCoin.setVersion(BeanUtil.getUUID());
		userDao.save(userCoin);
	}
}
