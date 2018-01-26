package com.xczhihui.wechat.course.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.CourseMapper;
import com.xczhihui.wechat.course.mapper.FocusMapper;
import com.xczhihui.wechat.course.mapper.MyInfoMapper;
import com.xczhihui.wechat.course.model.Focus;
import com.xczhihui.wechat.course.model.OnlineUser;
import com.xczhihui.wechat.course.service.IFocusService;
import com.xczhihui.wechat.course.service.IMyInfoService;
import com.xczhihui.wechat.course.vo.FocusVo;

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


	
	
}
