package com.xczh.consumer.market.service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface VideoService {
	/**
	 * 获得视频详情
	 * @param video_id
	 * @return
	 * @throws SQLException 
	 */
	public Map<String,Object> getVideoInfo(String video_id) throws SQLException;

	Map<String,String> videoInfo(String video_id, HttpServletRequest req) throws SQLException;

	/**
	 * 
	 * Description：获取此课程下的所有章节
	 * @param courseId
	 * @param userId
	 * @param isTryLearn
	 * @return
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	List<Map<String, Object>> getvideos(Integer courseId, String userId,
                                        Boolean isTryLearn) throws SQLException;
}
