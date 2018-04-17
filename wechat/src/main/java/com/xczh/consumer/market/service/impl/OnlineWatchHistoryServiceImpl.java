package com.xczh.consumer.market.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.dao.OnlineWatchHistoryMapper;
import com.xczh.consumer.market.service.OnlineWatchHistoryService;
import com.xczh.consumer.market.utils.DateUtil;
import com.xczh.consumer.market.wxpay.entity.OeWatchHistory;
import com.xczh.consumer.market.wxpay.util.DateDistance;

@Service
public class OnlineWatchHistoryServiceImpl implements OnlineWatchHistoryService {

	
	@Autowired
	public OnlineWatchHistoryMapper onlineWatchHistoryMapper;
	
	@Override
	public List<OeWatchHistory> getOeWatchHistotyList(int pageNumber, int pageSize, String userId,
			String type,OnlineUser ou ) throws SQLException{
		
		List<OeWatchHistory> list =new ArrayList<OeWatchHistory>();
		if(ou ==null){
			list = onlineWatchHistoryMapper.getOeWatchHistotyListAppId(pageNumber,pageSize,userId,type);
		}else{
			list = onlineWatchHistoryMapper.getOeWatchHistotyList(pageNumber,pageSize,userId,type);
		}
		/**
		 * 设置时间差
		 */
		for (OeWatchHistory oeWatchHistory : list) {
			Date startdate = oeWatchHistory.getWatchTime();
			String watch = DateUtil.formatDate(startdate, DateUtil.FORMAT_DAY_TIME);
			String current = DateUtil.formatDate(new Date(),DateUtil.FORMAT_DAY_TIME);
			String distance = DateDistance.getDistanceTime(watch,current);
			oeWatchHistory.setTimeDifference(distance);
		}
		return list;
	}

	@Override
	public void saveOnlineWatchHistory(OnlineUser ou, String courseId) throws SQLException{
		onlineWatchHistoryMapper.saveOnlineWatchHistory(ou,courseId);
	}

	@Override
	public void saveOnlineWatchHistory1(String userId, String courseId, String type) throws SQLException{
		onlineWatchHistoryMapper.saveOnlineWatchHistory1(userId,courseId,type);
	}
	
	
	@Override
	public void deleteOnlineWatchHistoryByUserIdAndType(String userId,
			String type) throws SQLException {
		onlineWatchHistoryMapper.deleteOnlineWatchHistoryByUserIdAndType(userId,type);
	}
    
	@Override
	public void updateOnlineWatchHistory(String userId, String courseId) throws SQLException {
		onlineWatchHistoryMapper.updateOnlineWatchHistory(userId,courseId);
	}
	   
	@Override
	public Integer findOnlineWatchHistory(String userId, String courseId) throws SQLException {
		return onlineWatchHistoryMapper.findOnlineWatchHistory(userId,courseId);
	}
	
}
