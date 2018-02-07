package com.xczhihui.wechat.course.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.wechat.course.mapper.MyInfoMapper;
import com.xczhihui.wechat.course.model.OnlineUser;
import com.xczhihui.wechat.course.service.IMyInfoService;
import com.xczhihui.wechat.course.vo.OnlineUserVO;

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
	public List<Map<String, Object>> selectSettlementList(String userId) {
		// TODO Auto-generated method stub
		return myInfoMapper.selectSettlementList(userId);
	}

	@Override
	public List<Map<String, Object>> selectWithdrawalList(String userId) {
		// TODO Auto-generated method stub
		return myInfoMapper.selectWithdrawalList(userId);
	}

	@Override
	public void updateUserSetInfo(OnlineUserVO user) {
		// TODO Auto-generated method stub
		
		//验证信息长度
		if(user.getSex()!=null && !UserSex.isValid(user.getSex())){
			throw new RuntimeException("性别不合法,0 女  1男   2 未知");
		}
		
		if(StringUtils.isNotBlank(user.getName()) 
				&&(user.getName().length()>20 ||user.getName().length()<4)){
			throw new RuntimeException("用户昵称长度在4-20之间");
		}
		if(StringUtils.isNotBlank(user.getEmail()) && 
				 (user.getEmail().length()>32 || user.getName().length()<5)){
			throw new RuntimeException("邮件长度在5-32之间");
        }
		
		if(StringUtils.isNotBlank(user.getEmail()) && !com.xczhihui.bxg.online.common.utils.StringUtils.checkEmail(user.getEmail())){
       	 throw new RuntimeException("邮箱格式有误");
       }
		
        myInfoMapper.updateUserSetInfo(user);
		
		
	}

	@Override
	public Integer getUserHostPermissions(String userId) {
		return myInfoMapper.getUserHostPermissions(userId);
	}
}
