package com.xczh.test.thirdparty;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.BaseJunit4Test;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.util.DateDistance;
import com.xczhihui.course.util.DateUtil;
import com.xczhihui.course.vo.WatchHistoryVO;

/**
 * 医馆入驻测试类
 */
public class LookWatchTest extends BaseJunit4Test {

	@Autowired
	public IWatchHistoryService watchHistoryServiceImpl;
	
	@Autowired
	public CourseMapper courseMapper;
    /**
     * 测试观看记录了
     */
	@Test
    public void LookWatch(){
    	
		Page<WatchHistoryVO> page = new Page<>();
	    page.setCurrent(1);
	    page.setSize(5);
	    
	    Page<WatchHistoryVO> records = watchHistoryServiceImpl.
	    		selectWatchHistory(page, "8eae74a4abe1430989c8005333a023a8");
		
	    for (WatchHistoryVO watchHistoryVO : records.getRecords()) {
			String watch = DateUtil.formatDate(watchHistoryVO.getWatchTime(), DateUtil.FORMAT_DAY_TIME);
			String current = DateUtil.formatDate(new Date(),DateUtil.FORMAT_DAY_TIME);
			String distance = DateDistance.getNewDistanceTime(watch,current);
			System.out.println(watch+"============="+current);
			System.out.println(distance);
			watchHistoryVO.setTimeDifference(distance);
		}
    }
	

}