package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.RegionVo;
import com.xczhihui.bxg.online.web.vo.SchoolVo;
import com.xczhihui.bxg.online.web.vo.SpecialitiesVo;
import com.xczhihui.bxg.online.web.vo.UserDataVo;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	public String addPhoneRegist(HttpServletRequest req,String username, String password, String code,String nikeName);
	/**
	 * 邮箱提交注册
	 * @param username
	 * @param password
	 */
	public String addEmailRegist(HttpServletRequest req,String username,String password,String nikeName);
	/**
	 * 新注册用户邮箱验证
	 * @param vcode
	 * @return
	 */
	public String addRegistEmailValidate(String vcode);

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
	public String updateUserPassword(String username,String password, String code);
	/**
	 * 删除用户
	 * @param username
	 * @return
	 */
	public String deleteUser(String username);
	/**
	 * 邮箱重置密码，验证
	 * @param vcode
	 * @return
	 */
	public String addResetEmailValidate(String vcode);
	/**
	 * 邮箱重置密码
	 * @param vcode
	 * @param password
	 * @return
	 */
	public String addResetPasswordByEmail(String vcode, String password);


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
	/**
	 * 联动显示学校
	 * @param
	 * @return
	 */
	public List<SchoolVo> listSchools(String cityId);


	/*public List<SchoolVo> listSchools(String schoolName);*/
	/**
	 * 联动显示专业
	 * @param schoolId
	 * @return
	 */
	public List<SpecialitiesVo> listSpecialities(String schoolId);
	public OnlineUser findUserByLoginName(String loginName);
	public void addUser(OnlineUser user);

	/**
	 * qq根据授权获取的authorization_code获取AccessToken
	 * @param authorization_code
	 */
	public String getQQAccessToken(String authorization_code);
	/**
	 * qq根据access_token获取OpenId
	 * @param access_token
	 * @return
	 */
	public String getQQOpenId(String access_token);
	/**
	 * 获取qq用户信息
	 * @param access_token
	 * @return
	 */
	public Map<String,String> saveQQUserInfo(String access_token, String openId);

	/**
	 * 获取用微信用户信息
	 * @param code    
	 * @return
	 */
	public Map<String,String> saveWechatUserInfo(String code);

	/**
	 * 三方登录账号绑定手机或邮箱账号
	 * @param username
	 * @param unionid
	 */
	public ResponseObject saveBindCount(String username, String unionid);

	public String update(String username,OnlineUser u);
	
	/** 
	 * Description：若用户登录时，没有微吼账号则创建并更新用户信息中微吼相关信息
	 * @param u
	 * @return
	 * @return String
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	String updateVhallInfo(OnlineUser u);
}
