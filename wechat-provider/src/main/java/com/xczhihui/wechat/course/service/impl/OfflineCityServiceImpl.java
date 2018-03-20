package com.xczhihui.wechat.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.MobileBannerMapper;
import com.xczhihui.wechat.course.mapper.OfflineCityMapper;
import com.xczhihui.wechat.course.model.MobileBanner;
import com.xczhihui.wechat.course.model.OfflineCity;
import com.xczhihui.wechat.course.service.IOfflineCityService;
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
public class OfflineCityServiceImpl extends ServiceImpl<OfflineCityMapper,OfflineCity> implements IOfflineCityService {

	@Autowired
	private OfflineCityMapper iOfflineCityMapper;
	
	@Override
    public Page<OfflineCity> selectOfflineCityPage(Page<OfflineCity> page) {
		List<OfflineCity> records = iOfflineCityMapper.selectOfflineCityPage(page);
		return   page.setRecords(records);
	}

	@Override
	public Page<OfflineCity> selectOfflineRecommendedCityPage(
			Page<OfflineCity> page) {
		List<OfflineCity> records = iOfflineCityMapper.selectOfflineRecommendedCityPage(page);
		return   page.setRecords(records);
	}
}
