package com.xczhihui.wechat.course.service.impl;

import java.util.Date;
import java.util.List;

import com.xczhihui.bxg.common.support.lock.Lock;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.CourseMapper;
import com.xczhihui.wechat.course.mapper.WatchHistoryMapper;
import com.xczhihui.wechat.course.model.WatchHistory;
import com.xczhihui.wechat.course.service.IWatchHistoryService;
import com.xczhihui.wechat.course.util.DateDistance;
import com.xczhihui.wechat.course.util.DateUtil;
import com.xczhihui.wechat.course.vo.WatchHistoryVO;

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
	private CourseMapper courseMapper;

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
		try {
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
			  watchHistoryMapper.updateById(watchHistory);
		  }else{
			  try {
				  watchHistoryMapper.insert(target);
			  } catch (Exception e) {
					e.printStackTrace();
			  }
		  }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("操作过于频繁!");
		}
	
	}

	@Override
	public void deleteBatch(String userId) {
		// TODO Auto-generated method stub
//		List<WatchHistory> list = watchHistoryMapper.findWatchHistoryByUserId(userId);
//		//System.out.println(list.get(0).getId());
//		if(list.size()>0){
//			watchHistoryMapper.deleteBatch(list);
//		}
		watchHistoryMapper.deleteWatchHistoryByUserId(userId);
	}
	
	
}
