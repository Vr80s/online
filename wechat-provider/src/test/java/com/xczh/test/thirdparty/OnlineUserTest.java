package com.xczh.test.thirdparty;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.mapper.OnlineUserMapper;
import com.xczhihui.wechat.course.service.IWatchHistoryService;
import com.xczhihui.wechat.course.util.DateDistance;
import com.xczhihui.wechat.course.util.DateUtil;
import com.xczhihui.wechat.course.vo.WatchHistoryVO;

import test.BaseJunit4Test;

/**
 * 医馆入驻测试类
 */
public class OnlineUserTest extends BaseJunit4Test {

	@Autowired
	public OnlineUserMapper onlineUserMapper;
	
    /**
     * 测试观看记录了
     */
	@Test
    public void queryOnlineUserInfo(){
    	
		
		
		
    }

}