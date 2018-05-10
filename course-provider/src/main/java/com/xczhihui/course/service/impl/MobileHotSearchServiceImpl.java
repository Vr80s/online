package com.xczhihui.course.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.course.mapper.MobileHotSearchMapper;
import com.xczhihui.course.model.MobileHotSearch;
import com.xczhihui.course.service.IMobileHotSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MobileHotSearchServiceImpl extends ServiceImpl<MobileHotSearchMapper,MobileHotSearch> implements IMobileHotSearchService {

	@Autowired
	private MobileHotSearchMapper mobileHotSearchMapper;

	@Override
	public List<MobileHotSearch> HotSearchList(Integer searchType) {
		return mobileHotSearchMapper.HotSearchList(searchType);
	}
}
