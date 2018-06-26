package com.xczhihui.course.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.UserSex;
import com.xczhihui.course.exception.UserInfoException;
import com.xczhihui.course.mapper.MyInfoMapper;
import com.xczhihui.course.model.OnlineUser;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.vo.OnlineUserVO;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MyInfoServiceImpl extends ServiceImpl<MyInfoMapper,OnlineUser> implements IMyInfoService {

	@Autowired
	private MyInfoMapper myInfoMapper;
	
	@Override
	public List<BigDecimal> selectCollegeCourseXmbNumber(String userId) {
		// TODO Auto-generated method stub
		List<BigDecimal>  list= myInfoMapper.selectCollegeCourseXmbNumber(userId);
		return list; 
	}

	@Override
	public List<Map<String, Object>> selectSettlementList(Integer pageNumber,Integer pageSize,String userId) {
		return myInfoMapper.selectSettlementList(pageNumber,pageSize,userId);
	}

	@Override
	public List<Map<String, Object>> selectWithdrawalList(Integer pageNumber,Integer pageSize,String userId) {
		return myInfoMapper.selectWithdrawalList(pageNumber,pageSize,userId);
	}

	@Override
	public void updateUserSetInfo(OnlineUserVO user) {
		
		if(user.getSex()!=null && !UserSex.isValid(user.getSex())){
			throw new UserInfoException("性别不合法,0 女  1男   2 未知");
		}
		
		//过滤掉可能出现的表情字符
		if(StringUtils.isNotBlank(user.getName())) {
			String name = user.getName();
			user.setName(name);
		}
		
		if(StringUtils.isNotBlank(user.getName()) && XzStringUtils.checkNickName(user.getName())
				&&(user.getName().length()>20)){
			throw new UserInfoException("昵称最多允许输入20个字符");
		}
		
		if(StringUtils.isNotBlank(user.getEmail()) && 
				 (user.getEmail().length()>32 || user.getEmail().length()<5)){
			throw new UserInfoException("邮件长度在5-32之间");
        }
		
		if(StringUtils.isNotBlank(user.getEmail()) && 
				!XzStringUtils.checkEmail(user.getEmail())){
			throw new UserInfoException("邮箱格式有误");
		}
        myInfoMapper.updateUserSetInfo(user);
	}

	@Override
	public Integer getUserHostPermissions(String userId) {
		return myInfoMapper.getUserHostPermissions(userId);
	}

	@Override
	public List<Map<String, Object>> hostInfoRec() {
		
		return myInfoMapper.hostInfoRec();
	}

	@Override
	public Page<Map<String, Object>> findUserWallet(
			Page<Map<String, Object>> page, String id) {
		List<Map<String, Object>>  page1 = 	myInfoMapper.findUserWallet(page,id);
		return page.setRecords(page1);
	}	

	@Override
	public Map<String, Object> findHostInfoById(String userId) {
		Map<String, Object> map =  myInfoMapper.findHostInfoById(userId);
		if(map!=null && map.get("workTime")!=null) {
//			if(XzStringUtils.isContainChinese( map.get("workTime").toString())) {
//				String workTime = "每周"+map.get("workTime")+"全天";
//				map.put("workTime", workTime);
//			}
			
			XzStringUtils.workTimeScreen(map.get("workTime").toString());
			
		}
		return myInfoMapper.findHostInfoById(userId);
	}
}
