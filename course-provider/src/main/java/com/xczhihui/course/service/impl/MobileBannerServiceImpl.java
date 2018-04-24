package com.xczhihui.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.course.mapper.MobileBannerMapper;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.service.IMobileBannerService;
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
public class MobileBannerServiceImpl extends ServiceImpl<MobileBannerMapper,MobileBanner> implements IMobileBannerService {

	@Autowired
	private MobileBannerMapper iMobileBannerMapper;
	
	@Override
    public Page<MobileBanner> selectMobileBannerPage(Page<MobileBanner> page, Integer type) {
		// TODO Auto-generated method stub
		List<MobileBanner> records = iMobileBannerMapper.selectMobileBannerPage(page,type);
		return   page.setRecords(records);
	}
	@Override
	public void addClickNum(String id) {
		// TODO Auto-generated method stub
		   iMobileBannerMapper.addClickNum(id);
	}
}
