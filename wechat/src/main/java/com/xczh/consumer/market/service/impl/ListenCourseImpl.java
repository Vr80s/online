package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.dao.ListenCourseMapper;
import com.xczh.consumer.market.service.ListenCourseService;
import com.xczh.consumer.market.vo.CourseLecturVo;
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
	public List<CourseLecturVo> listenCourseList() throws Exception {
		return listenCourseMapper.listenCourseList();
	}

}
