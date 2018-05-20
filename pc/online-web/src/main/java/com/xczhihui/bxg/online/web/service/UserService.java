package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.RegionVo;
import com.xczhihui.bxg.online.web.vo.UserDataVo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 用户
 * @author Haicheng Jiang
 */
public interface UserService {


	/**
	 * 手机提交注册
	 * @param username
	 * @param password
	 * @param code
	 */
	public void addPhoneRegist(HttpServletRequest req,String username, String password, String code,String nikeName);

	/**
	 * 检查当前用户昵称是否存在，存在返回：true   不存在返回：false
	 * @param nickName  昵称
	 * @return
	 */
	public Boolean  checkNickName(String  nickName,OnlineUser u);

	/**
	 * 重置手机注册用户密码
	 * @param username
	 * @param password
	 * @param code
	 * @return
	 */
	public void updateUserPassword(String username,String password, String code);

	/**
	 * 获取用户资料
	 * @return
	 */
	public UserDataVo getUserData(OnlineUser loginUser);
	
	/**
	 * 修改头像
	 * @param userId
	 * @param image
	 */
	public void updateHeadPhoto(String userId, byte[] image) throws IOException;
	
	/**
	 * 获取当前用户
	 * @param loginName
	 * @return
	 */
	public OnlineUser isAlive(String loginName);
	
	/**
	 * 联动显示省
	 * @return
	 */
	public List<RegionVo> listProvinces();

	/**
	 * 联动显示市
	 * @param provinceId
	 * @return
	 */
	public List<RegionVo> listCities(String provinceId);

	public OnlineUser findUserByLoginName(String loginName);

	Boolean isAnchor(String loginName);

	OnlineUser findUserById(String userId);
}
