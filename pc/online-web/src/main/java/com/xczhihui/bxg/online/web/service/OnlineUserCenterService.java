package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.online.api.vo.*;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.ApplyVo;
import com.xczhihui.bxg.online.web.vo.CityVo;
import com.xczhihui.bxg.online.web.vo.ProvinceVo;
import com.xczhihui.bxg.online.web.vo.UserCenterVo;
import com.xczhihui.bxg.online.web.vo.UserDataVo;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 用户中心
 * @author duanqh
 *
 */
public interface OnlineUserCenterService {
	
	/**
	 * 获取用户资料
	 * @return
	 */
	public UserDataVo getUserData(String userId);
	
	/**
	 * 获取用户
	 * @param userId
	 * @return
	 */
	public OnlineUser getUser(String userId);
	
	/**
	 * 获取用户
	 * @param loginName
	 * @return
	 */
	public OnlineUser getUserByLoginName(String loginName);

	/**
	 * 修改用户资料
	 * @param user
	 * @return
	 */
	public boolean updateUser(UserDataVo user);

	/**
	 * 修改用户资料
	 * @param user
	 * @return
	 */
	public Map<String,String> updateApply(ApplyVo user,HttpServletRequest request);
	
	/**
	 * 修改用户头像
	 * @param user
	 * @param img
	 * @return
	 */
	public void updateUserHeadImg(BxgUser user, String img);
	/**
	 * 修改密码
	 * @param userId 用户id
	 * @param pwd 密码
	 * @return
	 */
	public boolean updatePassword(String userId, String pwd);
	
	/**
	 * 获取用户中心顶部显示信息
	 * @param userId 用户id
	 * @return
	 */
	public UserCenterVo getUserInfo(String userId);
	
	/**
	 * 意见反馈
	 * @param userId
	 * @param title 标题
	 * @param describe 描述
	 */
	public void addFeedBack(String userId, String title, String describe);
	
	/**
	 * 根据区域id获取省份
	 * @param
	 * @return
	 */
	public List<ProvinceVo> getAllProvince();
	
	/**
	 * 根据省份id获取城市
	 * @param proId
	 * @return
	 */
	public List<CityVo> getCityByProId(Integer proId);

    List<Map<String, Object>> getAllProvinceCity() throws SQLException;
}
