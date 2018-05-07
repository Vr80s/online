package com.xczhihui.bxg.online.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.MyCourseType;
import com.xczhihui.common.util.enums.UserRecordType;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.course.model.WatchHistory;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.WatchHistoryVO;

/**
 * 观看记录和学习记录控制器
 * @author yangxuan
 *
 */
@Controller
@RequestMapping("/learnWatch")
public class LearnToWatchController extends AbstractController{

	@Autowired
	public IWatchHistoryService watchHistoryServiceImpl;

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
			BxgUser ou = UserLoginUtil.getLoginUser(req);
			if(ou==null){
			   return ResponseObject.newSuccessResponseObject("登录失效");
			}
			
			String recordTypeStr = UserRecordType.getTypeText(recordType);
			if(recordTypeStr==null) {
				 throw new RuntimeException("记录类型有误："+UserRecordType.getAllToString());
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
					if(course.getWatchState() == 1 || 
					   course.getUserLecturerId().equals(ou.getId())){
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
			BxgUser ou = UserLoginUtil.getLoginUser(req);
			
			if(ou==null){
			   return ResponseObject.newSuccessResponseObject("登录失效");
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
			BxgUser ou = UserLoginUtil.getLoginUser(req);
			if(ou==null){
			   return ResponseObject.newSuccessResponseObject("登录失效");
			}
			watchHistoryServiceImpl.deleteBatch(ou.getId());
			
			return ResponseObject.newSuccessResponseObject("清空成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("清空失败");
		}
	}
	
}
