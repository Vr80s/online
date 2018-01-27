package com.xczhihui.wechat.course.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.OfflineCityMapper;
import com.xczhihui.wechat.course.mapper.UserBankMapper;
import com.xczhihui.wechat.course.model.OfflineCity;
import com.xczhihui.wechat.course.model.UserBank;
import com.xczhihui.wechat.course.service.IUserBankService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper,UserBank> implements IUserBankService {

	@Autowired
	private UserBankMapper userBankMapper;

	@Override
	public UserBank selectUserBankByUserIdAndAcctPan(String userId, String acctPan,String certId) {
		return userBankMapper.selectUserBankByUserIdAndAcctPan(userId,acctPan,certId);
	}

	@Override
	public void addUserBank(UserBank userBank) {
		validateUserBank(userBank);
		userBankMapper.add(userBank);
	}

	@Override
	public List<UserBank> selectUserBankByUserId(String userId) {
		return userBankMapper.selectUserBankByUserId(userId);
	}


	private void validateUserBank(UserBank userBank) {
		if(StringUtils.isBlank(userBank.getUserId())){
			throw new RuntimeException("用户id不可为空");
		}
		if(StringUtils.isBlank(userBank.getAcctName())){
			throw new RuntimeException("户名不可为空");
		}
		if(StringUtils.isBlank(userBank.getAcctPan())){
			throw new RuntimeException("卡号不可为空");
		}
		if(StringUtils.isBlank(userBank.getCertId())){
			throw new RuntimeException("身份证号不可为空");
		}
	}
}
