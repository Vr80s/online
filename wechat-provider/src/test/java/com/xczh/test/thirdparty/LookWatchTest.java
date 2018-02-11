package com.xczh.test.thirdparty;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.service.IWatchHistoryService;
import com.xczhihui.wechat.course.util.DateDistance;
import com.xczhihui.wechat.course.util.DateUtil;
import com.xczhihui.wechat.course.vo.WatchHistoryVO;

import test.BaseJunit4Test;

/**
 * 医馆入驻测试类
 */
public class LookWatchTest extends BaseJunit4Test {

	@Autowired
	public IWatchHistoryService watchHistoryServiceImpl;
	
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