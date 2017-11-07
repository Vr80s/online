package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.wxpay.entity.OeWatchHistory;

import java.sql.SQLException;
import java.util.List;

public interface OnlineWatchHistoryService {

	
	List<OeWatchHistory> getOeWatchHistotyList(int pageNumber, int pageSize, String userId, String type) throws SQLException;

	void saveOnlineWatchHistory(OnlineUser ou, String courseId)throws SQLException;

	void deleteOnlineWatchHistoryByUserIdAndType(String userId, String type)throws SQLException;

	void saveOnlineWatchHistory1(OnlineUser ou, String courseId, String type)
			throws SQLException;

	void updateOnlineWatchHistory(OnlineUser ou, String courseId)
			throws SQLException;

	Integer findOnlineWatchHistory(OnlineUser ou, String courseId)
			throws SQLException;

}
