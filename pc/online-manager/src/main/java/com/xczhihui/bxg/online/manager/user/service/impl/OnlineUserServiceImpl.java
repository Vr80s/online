package com.xczhihui.bxg.online.manager.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.manager.user.dao.OnlineUserDao;
import com.xczhihui.bxg.online.manager.user.service.OnlineUserService;
import com.xczhihui.bxg.online.manager.vhall.VhallUtil;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;

@Service("onlineUserService")
public class OnlineUserServiceImpl implements OnlineUserService {

	@Autowired
	private OnlineUserDao dao;
	@Autowired
	private UserCenterAPI api;

	@Override
	public Page<OnlineUser> findUserPage(String lastLoginIp, String createTimeStart, String createTimeEnd,
			String lastLoginTimeStart, String lastLoginTimeEnd, String searchName, Integer status,Integer lstatus ,int pageNumber,
			int pageSize) {
		return dao.findUserPage(lastLoginIp, createTimeStart, createTimeEnd, lastLoginTimeStart, lastLoginTimeEnd,
				searchName, status,lstatus, pageNumber, pageSize);
	}

	@Override
	public void updateUserStatus(String loginName,int status) {
		OnlineUser u = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", loginName);
		if (u != null) {
			u.setStatus(status);
			dao.update(u);
			if (-1 == status) {
				api.deleteUserLogic(loginName);
			} else if (0 == status) {
				api.updateStatus(loginName, status);
			}
		}
	}

	@Override
	public void updateMenuForUser(OnlineUser entity) {
		OnlineUser temp=dao.findOneEntitiyByProperty(OnlineUser.class, "id", entity.getId());
		temp.setMenuId(entity.getMenuId());
		dao.update(temp);
		
	}
	
	
    //查询所有的讲师	
	@Override
	public List<Map<String,Object>> getAllUserLecturer(){
		return dao.getAllUserLecturer();
	}
    
	@Override
	public void updateUserLecturer(String userId,int isLecturer,String description) {
		OnlineUser u = dao.findOneEntitiyByProperty(OnlineUser.class, "id", userId);
//		Integer ylisLecturer = u.getIsLecturer();
		/**
		 * 获取房间号
		 */
		u.setIsLecturer(isLecturer);
		u.setDescription(description);
		Integer roomNumber=u.getRoomNumber();
		
//		if((isLecturer == 1 && ylisLecturer ==0) && (null==u.getRoomNumber() || 0==u.getRoomNumber())){//如果是取消职位或者已经存在这个职位了，房间号就不应该变了
//			u.setRoomNumber(count);
//		}
		if(isLecturer==1 && (roomNumber==null||roomNumber==0)){
			roomNumber = dao.getCurrent();
			u.setRoomNumber(roomNumber);
		}
		dao.update(u);
		VhallUtil.changeUserPower(u.getVhallId(), isLecturer+"", "0");//设置讲师权限
	}

	@Override
	public OnlineUser getOnlineUserByUserId(String userId) {
		OnlineUser ou = dao.getOnlineUserByUserId(userId);
		return ou;
	}

	@Override
	public List<Map<String, Object>> getAllCourseName() {
		return dao.getAllCourseName();
	}
}
