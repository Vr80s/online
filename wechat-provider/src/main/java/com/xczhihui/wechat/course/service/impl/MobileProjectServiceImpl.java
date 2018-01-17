package com.xczhihui.wechat.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.MobileBannerMapper;
import com.xczhihui.wechat.course.mapper.MobileProjectMapper;
import com.xczhihui.wechat.course.model.MobileBanner;
import com.xczhihui.wechat.course.model.MobileProject;
import com.xczhihui.wechat.course.service.IMobileBannerService;
import com.xczhihui.wechat.course.service.IMobileProjectService;
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
public class MobileProjectServiceImpl extends ServiceImpl<MobileProjectMapper,MobileProject> implements IMobileProjectService {

	@Autowired
	private MobileProjectMapper iMobileProjectMapper;
	
	@Override
    public Page<MobileProject> selectMobileProjectPage(Page<MobileProject> page, Integer type) {
		// TODO Auto-generated method stub
		List<MobileProject> records = iMobileProjectMapper.selectMobileProjectPage(page,type);
		return   page.setRecords(records);
	}
}
