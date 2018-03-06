package com.xczhihui.wechat.course.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.QQClientUserMappingMapper;
import com.xczhihui.wechat.course.mapper.WeiboClientUserMappingMapper;
import com.xczhihui.wechat.course.model.QQClientUserMapping;
import com.xczhihui.wechat.course.model.WeiboClientUserMapping;
import com.xczhihui.wechat.course.service.IThreePartiesLoginService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class ThreePartiesLoginServiceImpl extends ServiceImpl<QQClientUserMappingMapper,QQClientUserMapping> implements IThreePartiesLoginService {

	@Autowired
	private QQClientUserMappingMapper qqClientUserMappingMapper;
	
	@Autowired
	private WeiboClientUserMappingMapper weiboClientUserMappingMapper;

	@Override
	public void saveWeiboClientUserMapping(
			WeiboClientUserMapping weiboClientUserMapping) {
		// TODO Auto-generated method stub
		weiboClientUserMappingMapper.insert(weiboClientUserMapping);
	}

	@Override
	public void saveQQClientUserMapping(QQClientUserMapping qqClientUserMapping) {
		// TODO Auto-generated method stub
		qqClientUserMappingMapper.insert(qqClientUserMapping);
	}
	
	
	@Override
	public WeiboClientUserMapping selectWeiboClientUserMappingByUid(String uid) {
		// TODO Auto-generated method stub
		return weiboClientUserMappingMapper.selectWeiboClientUserMappingByUid(uid);
	}

	@Override
	public QQClientUserMapping selectQQClientUserMappingByOpenId(String openId) {
		// TODO Auto-generated method stub
		return qqClientUserMappingMapper.selectQQClientUserMappingByOpenId(openId);
	}

	@Override
	public WeiboClientUserMapping selectWeiboClientUserMappingByUserId(String userId,String uid) {
		return weiboClientUserMappingMapper.selectWeiboClientUserMappingByUserId(userId, uid);
	}

	@Override
	public QQClientUserMapping selectQQClientUserMappingByUserId(String userId,String unionId) {
		return qqClientUserMappingMapper.selectQQClientUserMappingByUserId(userId, unionId);
	}

	@Override
	public void updateQQInfoAddUserId(QQClientUserMapping qq) {
		// TODO Auto-generated method stub
		qqClientUserMappingMapper.updateById(qq);
	}

	@Override
	public void updateWeiboInfoAddUserId(WeiboClientUserMapping weibo) {
		// TODO Auto-generated method stub
		weiboClientUserMappingMapper.updateById(weibo);
	}

	@Override
	public QQClientUserMapping selectQQClientUserMappingByUnionId(String unionId) {
		// TODO Auto-generated method stub
		return qqClientUserMappingMapper.selectQQClientUserMappingByUnionId(unionId);
	}
	
	
	
	@Override
	public Map<String,Object> selectUserBindingInfo(String userId) {
		
		// TODO Auto-generated method stub
		return qqClientUserMappingMapper.selectUserBindingInfo(userId);
	}

	@Override
	public void deleteAccount(String id) {
		// TODO Auto-generated method stub
		
		qqClientUserMappingMapper.deleteAccount(id);
		weiboClientUserMappingMapper.deleteAccount(id);
	}
	
	@Override
	public QQClientUserMapping selectQQClientUserMappingByUserIdAndOpenId(String userId,String openId) {
		return qqClientUserMappingMapper.selectQQClientUserMappingByUserIdAndOpenId(userId, openId);
	}

}
