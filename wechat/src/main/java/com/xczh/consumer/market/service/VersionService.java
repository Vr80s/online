package com.xczh.consumer.market.service;

import java.sql.SQLException;

import com.xczh.consumer.market.vo.VersionInfoVo;

/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/7 21:00
 */
public interface VersionService {


    VersionInfoVo getNewVersion(Integer type);

	void insertTipOff(String content, String courseId, String label,
			String teacherId, String userId, String imgStrs) throws SQLException;

	VersionInfoVo getNewVersion();




}
