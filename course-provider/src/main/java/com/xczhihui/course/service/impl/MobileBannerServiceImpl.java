package com.xczhihui.course.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.course.mapper.MobileBannerMapper;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;

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
    public List<MobileBanner> selectMobileBannerPage(Integer type) {
		List<MobileBanner> records = iMobileBannerMapper.selectMobileBannerPage(type);
		return   records;
	}
	@Override
	public void addClickNum(String id) {
		   iMobileBannerMapper.addClickNum(id);
	}
	@Override
	public List<CourseLecturVo> recommendCourseList(List<MenuVo> menuList,int pageSize) {
		
		return iMobileBannerMapper.recommendCourseList(menuList,pageSize);
	}
}
