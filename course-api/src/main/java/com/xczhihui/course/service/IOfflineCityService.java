package com.xczhihui.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.OfflineCity;

public interface IOfflineCityService {
	
	  public Page<OfflineCity> selectOfflineCityPage(Page<OfflineCity> page);
	  
	  
	  public Page<OfflineCity> selectOfflineRecommendedCityPage(Page<OfflineCity> page);
}
