package com.xczh.consumer.market.controller.course;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineWatchHistoryService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.entity.OeWatchHistory;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.wechat.course.model.WatchHistory;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.service.IWatchHistoryService;
import com.xczhihui.wechat.course.vo.CourseLecturVo;
import com.xczhihui.wechat.course.vo.WatchHistoryVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/xczh/history")
public class LookHistoryController {

	@Autowired
	public OnlineWatchHistoryService onlineWatchHistoryService;
	
	@Autowired
	public IWatchHistoryService watchHistoryServiceImpl;

	@Autowired
	private AppBrowserService appBrowserService;
	
	@Autowired
	private OnlineWebService onlineWebService;
	
	@Autowired
	private ICourseService courseServiceImpl;
	
	/**
     * Description：增加观看记录
     * @param req
     * @param res
     * @param params
     * @return
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	@RequestMapping("add")
	@ResponseBody
	public ResponseObject add(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("courseId") Integer courseId) {
		try {
			
			
			OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
			if(ou==null){
			   return ResponseObject.newSuccessResponseObject("登录失效");
			}
			CourseLecturVo course =  courseServiceImpl.selectCourseMiddleDetailsById(courseId);
			if(course == null){
		          throw new RuntimeException("课程信息有误");
		     }
			WatchHistory target = new WatchHistory();
			target.setCourseId(courseId);
			target.setUserId(ou.getId());
			target.setLecturerId(course.getUserLecturerId());
			
			if(course.getWatchState() == 1){
			  onlineWebService.saveEntryVideo(courseId, ou);
			}
			
			watchHistoryServiceImpl.addOrUpdate(target);
			
			return ResponseObject.newSuccessResponseObject("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("保存失败");
		}
	}

	@RequestMapping("list")
	@ResponseBody
	public ResponseObject list(HttpServletRequest req,
			HttpServletResponse res) {
		try {
			/*
			 * 取五条记录
			 */
			Page<WatchHistoryVO> page = new Page<>();
		    page.setCurrent(1);
		    page.setSize(5);
		    OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
			if(ou==null){
			   return ResponseObject.newErrorResponseObject("登录失效");
			}
			return ResponseObject.newSuccessResponseObject(watchHistoryServiceImpl.selectWatchHistory(page,ou.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("数据有误");
		}
	}
	
	@RequestMapping("empty")
	@ResponseBody
	public ResponseObject empty(HttpServletRequest req,HttpServletResponse res) {
		try {
			OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
			if(ou==null){
			   return ResponseObject.newErrorResponseObject("登录失效");
			}
			watchHistoryServiceImpl.deleteBatch(ou.getUserId());
			
			return ResponseObject.newSuccessResponseObject("清空成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("清空失败");
		}
	}
	
	
	
}
