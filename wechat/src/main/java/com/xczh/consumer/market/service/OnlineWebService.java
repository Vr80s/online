package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OnlineWebService {

	void saveEntryVideo(Integer courseId, OnlineUser u) throws SQLException;

	List<Map<String, Object>> getUserCourse(Integer courseId, String userId)
			throws SQLException;

	List<Map<String, Object>> getLiveUserCourse(Integer courseId, String userId)
			throws SQLException;

}
