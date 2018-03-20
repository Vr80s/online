package com.xczh.consumer.market.service;

import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.MobileSearchVo;

import java.util.List;

public interface HotSearchService {
	
	public List<MobileSearchVo> SearchList(Integer searchType) throws Exception;
	

}
