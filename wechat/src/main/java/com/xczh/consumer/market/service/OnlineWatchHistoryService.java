package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.wxpay.entity.OeWatchHistory;

import java.sql.SQLException;
import java.util.List;

public interface OnlineWatchHistoryService {

	
	List<OeWatchHistory> getOeWatchHistotyList(int pageNumber, int pageSize, String userId, String type,OnlineUser ou) throws SQLException;

	void saveOnlineWatchHistory(OnlineUser ou, String courseId)throws SQLException;

	void deleteOnlineWatchHistoryByUserIdAndType(String userId, String type)throws SQLException;

	void saveOnlineWatchHistory1(String userId, String courseId, String type)
			throws SQLException;

	void updateOnlineWatchHistory(String userId, String courseId)
			throws SQLException;

	Integer findOnlineWatchHistory(String userId, String courseId)
			throws SQLException;

}
