package com.xczhihui.course.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xczhihui.course.model.WatchHistory;
import com.xczhihui.course.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.course.mapper.CriticizeMapper;
import com.xczhihui.course.mapper.WatchHistoryMapper;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.util.DateDistance;
import com.xczhihui.course.vo.WatchHistoryVO;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class WatchHistoryServiceImpl extends ServiceImpl<WatchHistoryMapper,WatchHistory> implements IWatchHistoryService {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WatchHistoryServiceImpl.class);
	
	@Autowired
	private WatchHistoryMapper watchHistoryMapper;
	
	@Autowired
	private CriticizeMapper criticizeMapper;

	@Override
	public Page<WatchHistoryVO> selectWatchHistory(Page<WatchHistoryVO> page,
			String userId) {
		
		List<WatchHistoryVO> records = watchHistoryMapper.selectWatchHistory(userId);
		
		for (WatchHistoryVO watchHistoryVO : records) {
			String watch = DateUtil.formatDate(watchHistoryVO.getWatchTime(), DateUtil.FORMAT_DAY_TIME);
			String current = DateUtil.formatDate(new Date(),DateUtil.FORMAT_DAY_TIME);
			String distance = DateDistance.getNewDistanceTime(watch,current);
			LOGGER.info("watch:"+watch+"========current:"+current);
			LOGGER.info("distance:"+distance);
			watchHistoryVO.setTimeDifference(distance);
		}
		return   page.setRecords(records);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Lock(lockName = "addOrUpdateLock",waitTime = 5,effectiveTime = 8)
	public void addOrUpdate(String lockId, WatchHistory target) {
		  /**
		   * 判断这个记录有没有添加进去，如果有添加进去，需要做更新操作	
		   */
		 WatchHistory watchHistory =null;	
		 if(target.getCollectionId()!=null && target.getCollectionId() !=0 ){
			 watchHistory = watchHistoryMapper.findWatchHistoryByUserIdAndCollectionId(target.getUserId(),target.getCollectionId());	
			 if(watchHistory!=null){
				 watchHistory.setCourseId(target.getCourseId());
			 }
		 }else{
			 watchHistory = watchHistoryMapper.findWatchHistoryByUserIdAndCourseId(target.getUserId(),target.getCourseId());	
		 }
		  if(watchHistory!=null){
			  watchHistory.setCreateTime(new Date());
			  
			  LOGGER.info("{}{}{}{}{}"
			  		+ "courseId:"+watchHistory.getCourseId()
			  		+ "collectionId"+watchHistory.getCollectionId());
			  watchHistoryMapper.updateById(watchHistory);
		  }else{
				  LOGGER.info("{}{}{}{}{}"
				  		+ "courseId:"+target.getCourseId()
				  		+ "collectionId"+target.getCollectionId());
				  watchHistoryMapper.insert(target);
		  }
	}

	@Override
	public void deleteBatch(String userId) {
		watchHistoryMapper.deleteWatchHistoryByUserId(userId);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Lock(lockName = "addOrUpdateLearnLock",waitTime = 5,effectiveTime = 8)
	public void addLearnRecord(String lockId, Integer courseId,String userId,String loginName) {
		Integer hasCourse = criticizeMapper.hasCourse(courseId, userId);
		if(hasCourse!=null && hasCourse == 0) {
			String id = UUID.randomUUID().toString().replace("-", "");
			watchHistoryMapper.insertApplyRGradeCourse(id,courseId,userId,loginName);
			
		}
	}
	
	
	
	
	
}
