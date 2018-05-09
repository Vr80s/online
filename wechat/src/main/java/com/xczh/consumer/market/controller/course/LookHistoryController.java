package com.xczh.consumer.market.controller.course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.model.WatchHistory;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.WatchHistoryVO;

@Controller
@RequestMapping("/xczh/history")
public class LookHistoryController {

	
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
			@RequestParam("courseId") Integer courseId,
			@RequestParam("recordType")Integer recordType,
			@RequestParam(required=false)Integer collectionId) {
		try {
			
			OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
			if(ou==null){
			   return ResponseObject.newSuccessResponseObject("登录失效");
			}
			CourseLecturVo course =  courseServiceImpl.selectCurrentCourseStatus(courseId);
			if(course == null){
		          throw new RuntimeException("课程信息有误");
		    }
			
			//锁id
			String lockId = ou.getId()+courseId;
			if(collectionId!=null && collectionId!=0){
				lockId  = ou.getId()+collectionId;
			}
			
			if(recordType!=null){
				if(recordType == 1){ //增加学习记录
					if(course.getWatchState() == 1){
						  watchHistoryServiceImpl.addLearnRecord(lockId, courseId, ou.getId(), ou.getLoginName());
				    }
				}
				if(recordType == 2){
					WatchHistory target = new WatchHistory();
					target.setCourseId(courseId);
					target.setUserId(ou.getId());
					target.setLecturerId(course.getUserLecturerId());
					target.setCollectionId(collectionId);
					watchHistoryServiceImpl.addOrUpdate(lockId,target);
				}
			
			}else{
				if(course.getType() == 4){
					WatchHistory target = new WatchHistory();
					target.setCourseId(courseId);
					target.setUserId(ou.getId());
					target.setLecturerId(course.getUserLecturerId());
					watchHistoryServiceImpl.addOrUpdate(lockId,target);
				}
				if(course.getWatchState() == 1){
					 watchHistoryServiceImpl.addLearnRecord(lockId, courseId, ou.getId(), ou.getLoginName());
				}
			}
			return ResponseObject.newSuccessResponseObject("保存成功");
		} catch (Exception e) {
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
