package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.dao.OnlineLecturerMapper;
import com.xczh.consumer.market.service.OnlineLecturerService;
import com.xczh.consumer.market.vo.LecturVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OnlineLecturerServiceImpl implements OnlineLecturerService {

	@Autowired
	public OnlineLecturerMapper onlineLecturerMapper;
	
	@Override
	public List<LecturVo> findLecturerById(Integer courseId) throws SQLException {
		return onlineLecturerMapper.findLecturerById(courseId);
	}

}
