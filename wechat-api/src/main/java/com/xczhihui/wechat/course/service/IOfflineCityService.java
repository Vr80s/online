package com.xczhihui.wechat.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.MobileBanner;
import com.xczhihui.wechat.course.model.OfflineCity;

public interface IOfflineCityService {
	
	  public Page<OfflineCity> selectOfflineCityPage(Page<OfflineCity> page);
}
