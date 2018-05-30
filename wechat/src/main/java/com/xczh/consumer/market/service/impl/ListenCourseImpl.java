package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.dao.ListenCourseMapper;
import com.xczh.consumer.market.service.ListenCourseService;
import com.xczh.consumer.market.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListenCourseImpl implements ListenCourseService {

	@Autowired
	private ListenCourseMapper listenCourseMapper;

	/***
	 * 听课列表
	 */
	@Override
	public List<CourseVo> listenCourseList() throws Exception {
		List<CourseVo> list = listenCourseMapper.listenCourseList();
		return list;
	}

}
