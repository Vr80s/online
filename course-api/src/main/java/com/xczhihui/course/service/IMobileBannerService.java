package com.xczhihui.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.MobileBanner;

public interface IMobileBannerService {
	
	  public Page<MobileBanner> selectMobileBannerPage(Page<MobileBanner> page, Integer type);

	  void  addClickNum(String id);
}
