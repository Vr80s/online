package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.dao.HotSearchMapper;
import com.xczh.consumer.market.dao.ListenCourseMapper;
import com.xczh.consumer.market.service.HotSearchService;
import com.xczh.consumer.market.service.ListenCourseService;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.MobileSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotSearchImpl implements HotSearchService {

	@Autowired
	private HotSearchMapper hotSearchMapper;

	/***
	 * 热门搜索列表
	 */
	@Override
	public List<MobileSearchVo> SearchList(Integer searchType) throws Exception {
		return hotSearchMapper.SearchList(searchType);
	}


}
