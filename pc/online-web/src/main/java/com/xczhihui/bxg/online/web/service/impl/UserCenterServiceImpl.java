package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.Constant;
import com.xczhihui.bxg.online.web.dao.UserCenterDao;
import com.xczhihui.bxg.online.web.service.OnlineUserCenterService;
import com.xczhihui.bxg.online.web.vo.*;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.vo.OeUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;

/**
 * 用户中心显示内容
 * @author duanqh
 *
 */
@Service("userCenterServiceImpl")
public class UserCenterServiceImpl extends OnlineBaseServiceImpl implements OnlineUserCenterService {

	@Autowired
	public UserCenterDao userCenter;
	
	@Autowired
	private UserCenterService userCenterService;
	
	@Override
	public void updateUser(OeUserVO user, HttpServletRequest request) {
		OnlineUser u= (OnlineUser) request.getSession().getAttribute(Constant.LOGINUSER);
		if (u == null){
			throw new RuntimeException("请登录!");
		}
		user.setLoginName(null);
		user.setId(u.getId());
		userCenterService.update(user);
	}

	@Override
    public Map<String,String>  updateApply(ApplyVo applyVo, HttpServletRequest request) {
		Map<String,String> mapValue=new HashMap<String,String>();
		OnlineUser user= (OnlineUser) request.getSession().getAttribute(Constant.LOGINUSER);
		if (user == null){
			throw new RuntimeException("请登录!");
		}
		Apply apply = dao.findOneEntitiyByProperty(Apply.class, "idCardNo", applyVo.getIdCardNo());
		if(apply!=null && !apply.getUserId().equals(user.getId())){
			throw new RuntimeException("该身份证号已被填写");
		}
		apply = dao.findOneEntitiyByProperty(Apply.class, "userId", user.getId());
		String card_no="";
		Integer isOldUser=0;
		if(apply==null){
			apply=new Apply();
			//apply.setId(UUID.randomUUID().toString().replace("-", ""));
			apply.setDelete(false);
			apply.setCreatePerson(user.getLoginName());
		    apply.setIsOldUser(isOldUser);
			apply.setCreateTime(new Date());
			BeanUtils.copyProperties(applyVo, apply);
			dao.save(apply);
		}else{ //如果是老学员  身份证无法修改
			card_no= apply.getIsOldUser()==0 ? applyVo.getIdCardNo() :apply.getIdCardNo();
			isOldUser=apply.getIsOldUser();
			BeanUtils.copyProperties(applyVo, apply);
			apply.setIdCardNo(card_no);
			dao.update(apply);
		}



		mapValue.put("updateState","修改成功");
		mapValue.put("isOldUser",String.valueOf(isOldUser));
		return mapValue;
	}


	@Override
	public void updateUserHeadImg(BxgUser user, String img) {
			if (user != null) {
				OnlineUser u = dao.get(user.getId(), OnlineUser.class);
					if (u != null) {
						u.setSmallHeadPhoto(img);
						dao.update(u);
					} else {
						throw new RuntimeException("用户不存在！");
					}
			}
	}
	
	@Override
	public boolean updatePassword(String userId, String pwd) {
		OnlineUser user = dao.get(userId, OnlineUser.class);
		//修改用户中心密码
		userCenterService.updatePassword(user.getLoginName(),null, pwd);
		return true;
	}

	@Override
	public UserCenterVo getUserInfo(String userId) {
		return userCenter.getUserInfo(userId);
	}
	
	@Override
	public OnlineUser getUser(String userId) {
		if (!StringUtils.hasText(userId)) {
			return null;
		}
		return userCenter.getUser(userId);
	}
	
	@Override
	public OnlineUser getUserByLoginName(String loginName) {
		return dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", loginName);
	}
	
	@Override
	public void addFeedBack(String userId, String title, String describe) {
		Message message = new Message();
		message.setContext(describe);
		message.setUserId(userId);
		message.setType(2);
		dao.save(message);
	}

	@Override
	public UserDataVo getUserData(String userId) {
		UserDataVo vo = userCenter.getUserData(userId);
		if (!StringUtils.hasText(vo.getNickName())) {
			vo.setNickName(Constant._NICKNAME);
		}
		if (!StringUtils.hasText(vo.getAutograph())) {
			vo.setAutograph(Constant._AUTOGRAPH);
		}
		vo.setBirthdayStr(DateUtil.formatDate(vo.getBirthday(), DateUtil.FORMAT_DAY));
		//设置职位
		vo.setJob(userCenter.getJob("occupation"));
		//学习目标
		vo.setStudyTarget(userCenter.getJob("target"));
		//设置工作年限
		vo.setJobyear(userCenter.getJobYear());
		
		return vo;
	}
	@Override
	public List<ProvinceVo> getAllProvince() {
		return userCenter.getAllProvince();
	}

	@Override
	public List<CityVo> getCityByProId(Integer proId) {
		return userCenter.getAllCity(proId);
	}

	@Override
	public List<Map<String, Object>> getAllProvinceCity() throws SQLException {
		 /*
		  * sql 一下字查完。然后在进行拼接，得到中国下的省份。
		  */
		String sql1 = "select cid,lin,name from ht_location where level = 3 and lin = 7";
		List<Map<String, Object>> listProven =dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql1);

		String sql2 = "select cid,lin,name from ht_location where level = 4";
		List<Map<String, Object>> listCity =dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql2);

		for (Map<String, Object> mapProven : listProven) {
			List<Map<String, Object>> listCityC = new ArrayList<Map<String,Object>>();
			String objPcid  = mapProven.get("cid").toString();
			for (Map<String, Object> mapCity : listCity) {
				String objPclin  = mapCity.get("lin").toString();
				if(objPcid.equals(objPclin)){
					listCityC.add(mapCity);
				}
			}
			mapProven.put("cityList", listCityC);
		}
		return listProven;
	}
}
