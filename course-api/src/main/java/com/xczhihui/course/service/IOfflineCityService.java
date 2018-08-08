package com.xczhihui.course.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.OfflineCity;

public interface IOfflineCityService {

    public Page<OfflineCity> selectOfflineCityPage(Page<OfflineCity> page);


    public Page<OfflineCity> selectOfflineRecommendedCityPage(Page<OfflineCity> page);


	public Map<String, Object> getOffLine(int clientType, Boolean onlyThread, String mobileSource);
	
	/**
	 * 
	* @Title: getOtherOffLine
	* @Description: 得到其他的城市字符串，比如："北京,上海"
	* @param @return    参数
	* @return String    返回类型
	* @author yangxuan
	* @throws
	 */
	public  String getClassIfyOffLine();
}
