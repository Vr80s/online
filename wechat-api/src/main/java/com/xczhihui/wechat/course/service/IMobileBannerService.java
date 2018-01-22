package com.xczhihui.wechat.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.MobileBanner;

public interface IMobileBannerService {
	
	  public Page<MobileBanner> selectMobileBannerPage(Page<MobileBanner> page, Integer type);
}
