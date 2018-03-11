package com.xczhihui.wechat.course.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
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
	public List<Map<String, Object>> selectSettlementList(Integer pageNumber,Integer pageSize,String userId) {
		return myInfoMapper.selectSettlementList(pageNumber,pageSize,userId);
	}

	@Override
	public List<Map<String, Object>> selectWithdrawalList(Integer pageNumber,Integer pageSize,String userId) {
		// TODO Auto-generated method stub
		return myInfoMapper.selectWithdrawalList(pageNumber,pageSize,userId);
	}

	@Override
	public void updateUserSetInfo(OnlineUserVO user) {
		// TODO Auto-generated method stub
		
		//验证信息长度
		if(user.getSex()!=null && !UserSex.isValid(user.getSex())){
			throw new RuntimeException("性别不合法,0 女  1男   2 未知");
		}
		
		if(StringUtils.isNotBlank(user.getName()) 
				&&(user.getName().length()>20 ||user.getName().length()<1)){
			throw new RuntimeException("用户昵称长度在1-20之间");
		}
		if(StringUtils.isNotBlank(user.getEmail()) && 
				 (user.getEmail().length()>32 || user.getEmail().length()<5)){
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

	@Override
	public List<Map<String, Object>> hostInfoRec() {
		
		return myInfoMapper.hostInfoRec();
	}

	@Override
	public List<Map<String, Object>> findUserWallet(
			Integer pageNumber,Integer pageSize, String id) {
		List<Map<String, Object>>  page1 = 	myInfoMapper.findUserWallet(pageNumber,pageSize,id);
		return page1;
	}
}
