package com.xczh.consumer.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.dao.OnlineWatchHistoryMapper;
import com.xczh.consumer.market.service.OnlineWatchHistoryService;
import com.xczh.consumer.market.utils.DateUtil;
import com.xczh.consumer.market.wxpay.entity.OeWatchHistory;
import com.xczh.consumer.market.wxpay.util.DateDistance;
import com.xczh.consumer.market.wxpay.util.WeihouInterfacesListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class OnlineWatchHistoryServiceImpl implements OnlineWatchHistoryService {

	
	@Autowired
	public OnlineWatchHistoryMapper onlineWatchHistoryMapper;
	
	@Override
	public List<OeWatchHistory> getOeWatchHistotyList(int pageNumber, int pageSize, String userId, String type) throws SQLException{
		List<OeWatchHistory> list = onlineWatchHistoryMapper.getOeWatchHistotyList(pageNumber,pageSize,userId,type);
		
		
		
		
/*		if(type!=null && "1".equals(type)){
			for (OeWatchHistory oeWatchHistory : list) {
				Date start = oeWatchHistory.getStartTime();
				Date end = oeWatchHistory.getEndTime();
				Date now = new Date();
				// lineState 0 直播已结束 1 直播还未开始 2 正在直播
				if(end!=null && start!=null){
					if (end.getTime() < now.getTime()) {// 结束时间小于当前时间，说明已经结束
						oeWatchHistory.setLineState(0);
					} else if (start.getTime() > now.getTime()) { // 开始时间大于当前时间，说明已经还没开始
						oeWatchHistory.setLineState(1);
					} else if (start.getTime() < now.getTime()
							&& end.getTime() > now.getTime()) {//正在直播
						oeWatchHistory.setLineState(2);
						// 如果正在直播，需要获取当前观看人数
						String webinar_id = oeWatchHistory.getDirectId();
						if(webinar_id!=null){
							JSONObject obj = WeihouInterfacesListUtil.getCurrentOnlineNumber(webinar_id);
							oeWatchHistory.setJobj(obj);
						}
					}
				}
			}
		}*/
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
	public void saveOnlineWatchHistory1(OnlineUser ou, String courseId, String type) throws SQLException{
		onlineWatchHistoryMapper.saveOnlineWatchHistory1(ou,courseId,type);
	}
	
	
	@Override
	public void deleteOnlineWatchHistoryByUserIdAndType(String userId,
			String type) throws SQLException {
		onlineWatchHistoryMapper.deleteOnlineWatchHistoryByUserIdAndType(userId,type);
	}
    
	@Override
	public void updateOnlineWatchHistory(OnlineUser ou, String courseId) throws SQLException {
		onlineWatchHistoryMapper.updateOnlineWatchHistory(ou,courseId);
	}
	   
	@Override
	public Integer findOnlineWatchHistory(OnlineUser ou, String courseId) throws SQLException {
		return onlineWatchHistoryMapper.findOnlineWatchHistory(ou,courseId);
	}
	
}
