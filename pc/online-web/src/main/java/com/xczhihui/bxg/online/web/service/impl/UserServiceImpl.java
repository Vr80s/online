package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.VerificationCode;
import com.xczhihui.bxg.online.web.base.common.Constant;
import com.xczhihui.bxg.online.web.dao.UserCenterDao;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.online.web.vo.RegionVo;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.domain.SystemVariate;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentType;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.ImageUtil;
import com.xczhihui.common.util.VhallUtil;
import com.xczhihui.common.util.enums.UserOrigin;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.vo.OeUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关
 * @author Haicheng Jiang
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserCenterService userCenterService;

	@Autowired
	public UserCenterDao userCenterDao;

	private SimpleHibernateDao dao;

	@Autowired
	private AttachmentCenterService attachmentCenterService;
	
	@Value("${web.url}")
	private String weburl;
	
	//数据字典
	private Map<String, String> attrs = new HashMap<String, String>();


	@Override
	public void addPhoneRegist(HttpServletRequest req,String username, String password, String code,String nikeName) {

//		//动态码验证
//		List<VerificationCode>  codes = dao.findByHQL("from VerificationCode where phone=? and vtype='1' ", username);
//		if (!StringUtils.hasText(code) || codes == null || codes.size() <= 0
//				|| !codes.get(0).getVcode().equals(code)) {
//			throw new RuntimeException("动态码不正确！");
//		}
//		if (!codes.get(0).getPhone().equals(username)) {
//			throw new RuntimeException ("手机号与动态码不符！");
//		}
//
//		if (nikeName == null || "".equals(nikeName)) {
//			throw new RuntimeException ("请输入用户名");
//		}
//
//		//初始化字典
//		initSystemVariate();
//
//		if (System.currentTimeMillis() - codes.get(0).getCreateTime().getTime() > 1000 * 60
//				* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
//			throw new RuntimeException ("动态码超时，请重新发送！");
//		}

		userCenterService.regist(username, password, nikeName, UserOrigin.PC);
		//删除动态码
//		dao.delete(codes.get(0));
	}

	/**
	 * 重置用户密码
	 * @param username
	 * @param password
	 * @param code
	 * @return
	 */
    @Override
    public void updateUserPassword(String username,String password, String code) {
    	
    	//动态码验证
		List<VerificationCode>  codes = dao.findByHQL("from VerificationCode where phone=? and vcode=? and vtype='2' ", username, code);
		if (codes == null || codes.size() <= 0 ) {
			throw new RuntimeException ("动态码不正确！");
		}
		
		//初始化字典
		initSystemVariate();
		
		//动态码超时
		if (System.currentTimeMillis() - codes.get(0).getCreateTime().getTime() > 1000 * 60
				* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
			throw new RuntimeException ("动态码超时，请重新发送！");
		}
		
		//更新用户密码
		userCenterService.resetPassword(username,password);
		if (codes.get(0) != null) {
			dao.delete(codes.get(0));
		}
    }
    
    public void initSystemVariate(){
		//查数据字典
		List<SystemVariate> lst = dao.findByHQL("select t1 from SystemVariate t1,SystemVariate t2 "
				+ "where t1.parentId=t2.id and t2.name=?","message_provider");
		for (SystemVariate systemVariate : lst) {
			attrs.put(systemVariate.getName(), systemVariate.getValue());
		}
	}

	@Resource(name="simpleHibernateDao")
	public void setDao(SimpleHibernateDao dao) {
		this.dao = dao;
	}

	/**
	 * 获取用户资料
	 * @return
	 */
	@Override
    public UserDataVo getUserData(OnlineUser loginUser) {
		UserDataVo vo = new UserDataVo();
		if (StringUtils.hasText(loginUser.getId())) {
			vo = userCenterDao.getUserData(loginUser.getId());
	 	if (!StringUtils.hasText(vo.getNickName())) {
				vo.setNickName(Constant._NICKNAME);
			}
		    vo.setBirthdayStr(DateUtil.formatDate(vo.getBirthday(), DateUtil.FORMAT_DAY));
			//设置职位
			vo.setJob(userCenterDao.getJob("occupation"));

			// 获取用户报名信息
			Apply  app=  dao.findOneEntitiyByProperty(Apply.class, "userId", loginUser.getId());
			if(app != null) {
				vo.setUid(app.getUserId());
				vo.setQq(app.getQq());
				vo.setEmail(app.getEmail());
				vo.setMobile(app.getMobile());
				vo.setBirthdayStr(app.getBirthday() != null ? DateUtil.formatDate(app.getBirthday(), DateUtil.FORMAT_DAY) : "");
				vo.setApplyProvince(app.getProvince());
				vo.setAppCity(app.getCity());
				vo.setRealName(app.getRealName());
				vo.setSchoolId(app.getSchoolId());
				vo.setEducationId(app.getEducationId());
				vo.setMajorId(app.getMajorId());
				vo.setApplyId(app.getId());
				vo.setIdCardNo(app.getIdCardNo());

			}
		}
		return vo;
	}


	/**
	 * 检查当前用户昵称是否存在，存在返回：true   不存在返回：false
	 * @param nickName  昵称
	 * @return
	 */
    @Override
    public Boolean  checkNickName(String  nickName, OnlineUser u) {
		String sql = "select name from oe_user where name = ? ";
		List<UserDataVo> ou = dao.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
				BeanPropertyRowMapper.newInstance(UserDataVo.class), nickName);
		if (u == null && ou.size() >0) {
			return true;
		}
		if ( ou.size() >0 && u != null && !nickName.equals(u.getName())) {
			return true;
		}
		return false;

	}

	@Override
	public void updateHeadPhoto(String userId, byte[] image) throws IOException {

		OeUserVO oeUserVO = new OeUserVO();
		InputStream imageInputStream = new ByteArrayInputStream(image);
        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
        
        ImageUtil.scaleImage(imageInputStream, imageOutputStream,"png", 80, 80);
		image = imageOutputStream.toByteArray();
		
		Attachment smallattr  = this.attachmentCenterService.addAttachment(
				userId, AttachmentType.ONLINE, userId+"_small.png", image,
				StringUtils.getFilenameExtension(userId+"_small.png"));

		oeUserVO.setId(userId);
		oeUserVO.setSmallHeadPhoto(smallattr.getUrl());
		userCenterService.update(oeUserVO);

		/*更新微吼账户信息*/
		VhallUtil.updateUser(oeUserVO.getId(),oeUserVO.getName(),oeUserVO.getSmallHeadPhoto(), null);
	}
	

	@Override
	public OnlineUser isAlive(String loginName) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		OnlineUser u=dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", loginName);
		u.setIsPerfectInformation(true);
        u.setIsOldUser(0);
		if(u != null){ //查看用户真实资料是否填写完成
			paramMap.put("userId",u.getId());
			String  sql1=" select is_old_user from  oe_apply  where user_id=:userId ";
			String  sql2=" select is_old_user from  oe_apply  where user_id=:userId  and (real_name is NULL  or real_name='' or mobile is null or mobile='' or id_card_no is null or id_card_no = '' ) ";
			List<Map<String,Object>> applys1 =  dao.getNamedParameterJdbcTemplate().queryForList(sql1, paramMap);
			List<Map<String,Object>> applys2 =  dao.getNamedParameterJdbcTemplate().queryForList(sql2, paramMap);
			if(applys1.size() < 1 || applys2.size() > 0){
				u.setIsPerfectInformation(false);
			}
		}
		return u;
	}

	@Override
	public List<RegionVo> listProvinces() {
		return dao.getNamedParameterJdbcTemplate().query("select * from oe_region where parent_id='0' ",
				new BeanPropertyRowMapper<RegionVo>(RegionVo.class));
	}

	@Override
	public List<RegionVo> listCities(String provinceId) {
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("provinceId", provinceId);
		return dao.getNamedParameterJdbcTemplate().query("select * from oe_region where parent_id= :provinceId ",ps,
				new BeanPropertyRowMapper<RegionVo>(RegionVo.class));
	}

	@Override
	public OnlineUser findUserByLoginName(String loginName) {
		return dao.findByHQLOne("from OnlineUser where loginName=?", loginName);
	}
	
	@Override
	public OnlineUser findUserById(String userId) {
		return dao.findByHQLOne("from OnlineUser where id=?", userId);
	}

	@Override
	public Boolean isAnchor(String loginName) {
		StringBuffer sql = new StringBuffer();
		List<OnlineUser> users;
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName",loginName);
		sql.append( "SELECT \n" +
				"  ou.`name`," +
				" ca.status caStatus "+
				"FROM\n" +
				"  `course_anchor` ca \n" +
				"  JOIN `oe_user` ou \n" +
				"    ON ca.`user_id` = ou.id \n" +
				"WHERE ou.`login_name` = :loginName ");
		users= dao.findEntitiesByJdbc(OnlineUser.class, sql.toString(), paramMap);
		if(users.size()!=1){
			return false;
		}else{
			return users.get(0).getCaStatus();
		}
	}
}

