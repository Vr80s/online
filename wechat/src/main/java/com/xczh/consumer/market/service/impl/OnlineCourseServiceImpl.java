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

	public static void main(String[] args) {

		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");

		List<String> newlist = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			String string = list.get(i);
			if ("1".equals(string) || "3".equals(string) || "5".equals(string)) {
				newlist.add(string);
				list.remove(i);
			}
		}
		newlist.addAll(list);
		for (String string : newlist) {
			System.out.println(string);
		}
	}

}
