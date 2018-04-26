package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.FileUtil;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.Broadcast;
import com.xczhihui.bxg.online.web.service.LiveService;
import com.xczhihui.bxg.online.web.vo.OpenCourseVo;

/**
 * 直播
 * 
 * @author Haicheng Jiang
 *
 */
@RestController
@RequestMapping(value = "online/live")
public class LiveController extends AbstractController{


	@Autowired
	private LiveService  service;
	@Autowired
	private Broadcast broadcast;

	/**
	 * 首页获取公开直播课
	 * @param  num:条数
	 * @return
	 */
	@RequestMapping(value = "/getOpenCourse")
	public ResponseObject getOpenCourse(Integer num){
		return ResponseObject.newSuccessResponseObject(service.getOpenCourse(num));
	}

	/**
	 * 首页获取直播预告
	 * @return
	 */
	@RequestMapping(value = "/getLiveTrailer")
	public ResponseObject getLiveTrailer(HttpServletRequest request){
		OnlineUser user = getCurrentUser();
		String userId = null;
		if(user != null) {
            userId = user.getId();
        }
		List<OpenCourseVo> openCourses = service.getOpenCourse(null,userId);
		return ResponseObject.newSuccessResponseObject(openCourses);
	}

	/**
	 * 首页获取直播
	 * @return
	 */
	@RequestMapping(value = "/getLive")
	public ResponseObject getLive(){
		return ResponseObject.newSuccessResponseObject(service.getIndexLive());
	}


	/**
	 * 修改直播课程的浏览数
	 *
	 * @param courseId 课程的封装对象
	 * @throws IOException 
	 * @throws SmackException 
	 * @throws XMPPException 
	 */
	@RequestMapping(value = "/updateBrowseSum")
	public ResponseObject updateBrowseSum(Integer courseId,Integer personNumber) throws XMPPException, SmackException, IOException {
		int count = service.updateBrowseSum(courseId);
		Map<String,Object> onlineCount = new HashMap<String,Object>();
    	onlineCount.put("messageType", 2);
    	onlineCount.put("onlineCount", count);
    	String onlineCountStr = JSONObject.toJSONString(onlineCount);
		broadcast.loginAndSend(courseId.toString(), onlineCountStr);
		return ResponseObject.newSuccessResponseObject("操作成功");
	}

	/**
	 * 获取直播课程信息，根据课程id查询课程
	 * @param courseId 课程id号
	 */
	@RequestMapping(value = "/getOpenCourseById")
	public ResponseObject getOpenCourseById(Integer courseId,HttpServletRequest request) {
		OnlineUser user = getCurrentUser();
		return ResponseObject.newSuccessResponseObject(service.getOpenCourseById(courseId,user));
	}

}
