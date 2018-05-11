package com.xczh.consumer.market.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.dao.OnlineCourseMapper;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.LecturVo;

@Service
public class OnlineCourseServiceImpl extends BasicSimpleDao implements OnlineCourseService {

	@Autowired
	private OnlineCourseMapper courseMapper;

	/**
	 * 修改直播课程的浏览数
	 * 
	 * @param courseId
	 *            鲜花数
	 * @throws SQLException
	 */
	@Override
	public void updateBrowseSum(Integer courseId) throws SQLException {
		String sql = " update oe_course  set pv = (pv + 1)  where id = ? and is_delete = 0 and status=1 ";
		super.update(JdbcUtil.getCurrentConnection(), sql, courseId);
	}

	@Override
	public void updateLiveSourceType(Integer courseId) throws SQLException {
		String sql = " update oe_course  set live_source_type = 1  where id = ? and is_delete = 0 and status=1 ";
		super.update(JdbcUtil.getCurrentConnection(), sql, courseId);
	}

	@Override
	public CourseLecturVo courseShare(Integer courseId) throws SQLException {
		return courseMapper.courseShare(courseId);
	}

	@Override
	public LecturVo lectureShare(String lecturerId) throws SQLException {
		return courseMapper.lectureShare(lecturerId);
	}
}
