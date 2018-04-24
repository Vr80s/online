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
public class LiveController {


	@Autowired
	private LiveService  service;
	@Autowired
	private Broadcast broadcast;
	/**
	 * 上传聊天图片
	 * 
	 * @param request
	 * @param response
	 * @return 图片地址
	 * @throws IOException 
	 */
	@RequestMapping(value = "/uploadMsgImg", method = RequestMethod.POST)
	public ResponseObject findChapterInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		BxgUser u = UserLoginUtil.getLoginUser(request);
		if (u == null) {
			return ResponseObject.newErrorResponseObject("请登录！");
		}
		
		// 获得文件
		MultipartFile attachmentFile = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> item = multipartRequest.getFileNames();
		while (item.hasNext()) {
			attachmentFile = multipartRequest.getFile(item.next());
		}
		if (attachmentFile == null || attachmentFile.getSize() <= 0) {
			return ResponseObject.newErrorResponseObject("请上传图片！");
		}

		// 图片允许上传的文件扩展名
		String extname = attachmentFile.getOriginalFilename();
		String lowerCase = extname.toLowerCase();
		if (!lowerCase.endsWith("image") && !lowerCase.endsWith("gif") && !lowerCase.endsWith("jpg")
				&& !lowerCase.endsWith("png") && !lowerCase.endsWith("bmp")) {
			return ResponseObject.newErrorResponseObject("不支持的图片类型：“" + lowerCase + "”！");
		}
		
		if (attachmentFile.getSize() > 1024*1024) {
			return ResponseObject.newErrorResponseObject("图片不能超过1M！");
		}
		
		String name = "/online/live/image/"+UUID.randomUUID().toString().replace("-", "")+"bxgliveimage.png";
		FileUtil.writeToFile(name, attachmentFile.getBytes());
		return ResponseObject.newSuccessResponseObject(name);
	}

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
		OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
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
		OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
		return ResponseObject.newSuccessResponseObject(service.getOpenCourseById(courseId,user));
	}


	/**
	 * 更新在线人数
	 * @param courseId  课程id
	 * @param personNumber 当前在线人数
	 */
	@RequestMapping(value = "/saveOnUserCount")
	public ResponseObject  saveOnUserCount(Integer courseId,Integer personNumber){
            service.saveOnUserCount(courseId,personNumber);
		    return  ResponseObject.newSuccessResponseObject("操作成功");
	}

	/**
	 * 检测每个用户送花时间距离上次送花时间的差距是否是30秒
	 *
	 * @param roomId  房间号
	 * @return true:可以送花，false：不可以送花
	 */
	@RequestMapping(value = "/checkTime")
	public ResponseObject checkTime(String roomId,HttpSession s){
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return  ResponseObject.newSuccessResponseObject(service.checkTime(roomId,u));
	}


	/**
	 * 获取一周的课程表数据
	 * @param currentTime 前端传过来的时间
	 */
	@RequestMapping(value = "/getCourseTimetable")
	public ResponseObject   getCourseTimetable(long currentTime){
		return  ResponseObject.newSuccessResponseObject(service.getCourseTimetable(currentTime));
	}
}
