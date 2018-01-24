package com.xczhihui.wechat.course.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.CourseMapper;
import com.xczhihui.wechat.course.mapper.WatchHistoryMapper;
import com.xczhihui.wechat.course.model.Course;
import com.xczhihui.wechat.course.model.WatchHistory;
import com.xczhihui.wechat.course.service.IWatchHistoryService;
import com.xczhihui.wechat.course.util.DateDistance;
import com.xczhihui.wechat.course.util.DateUtil;
import com.xczhihui.wechat.course.vo.CourseLecturVo;
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

	@Autowired
	private WatchHistoryMapper watchHistoryMapper;
	
	@Autowired
	private CourseMapper courseMapper;

	@Override
	public Page<WatchHistoryVO> selectWatchHistory(Page<WatchHistoryVO> page,
			String userId) {
		
		List<WatchHistoryVO> records = watchHistoryMapper.selectWatchHistory(page,userId);
		for (WatchHistoryVO watchHistoryVO : records) {
			String watch = DateUtil.formatDate(new Date(), DateUtil.FORMAT_DAY_TIME);
			String current = DateUtil.formatDate(new Date(),DateUtil.FORMAT_DAY_TIME);
			String distance = DateDistance.getNewDistanceTime(watch,current);
			watchHistoryVO.setTimeDifference(distance);
		}
		return   page.setRecords(records);
	}

	@Override
	public void addOrUpdate(WatchHistory target) {
		// TODO Auto-generated method stub
	  //根据课程Id查找讲师id
	  /**
	   * 判断这个记录有没有添加进去，如果有添加进去，需要做更新操作	
	   */
	  WatchHistory watchHistory = watchHistoryMapper.findWatchHistoryByUserIdAndCourseId(target.getUserId(), target.getCourseId());
	  if(watchHistory!=null){
		  watchHistory.setCreateTime(new Date());
		  watchHistoryMapper.updateById(watchHistory);
	  }else{
		  if(target.getCourseId() == null){
	          throw new RuntimeException("请传递课程名字");
	      }
		  CourseLecturVo course =  courseMapper.selectCourseById(target.getCourseId());
		  target.setLecturerId(course.getUserLecturerId());
		  //通过课程id查找讲师id
		  watchHistoryMapper.insert(target);
	  }
	}

	@Override
	public void deleteBatch(String userId) {
		// TODO Auto-generated method stub
		List<WatchHistory> list = watchHistoryMapper.findWatchHistoryByUserId(userId);
		//System.out.println(list.get(0).getId());
		System.out.println(list.size()+"{}{}{}{}{}{");
		if(list.size()>0){
			watchHistoryMapper.deleteBatch(list);
		}
	}
	
	
}
