package com.xczhihui.wechat.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.MobileBanner;
import com.xczhihui.wechat.course.vo.CourseLecturVo;

public interface IMobileBannerService {
	
	  public Page<MobileBanner> selectMobileBannerPage(Page<MobileBanner> page, Integer type);
}
