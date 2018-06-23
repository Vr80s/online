package com.xczhihui.bxg.online.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.web.service.VedioService;
import com.xczhihui.common.support.cc.util.CCUtils;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.CourseLecturVo;

/**
 * 视频播放权限验证
 * 
 * @author Haicheng Jiang
 *
 */
@RestController
@RequestMapping(value = "online/vedio")
public class VedioController extends AbstractController{

	@Autowired
	private VedioService service;

	@Autowired
	private CCUtils ccUtils;
	
	@Autowired
	private ICourseService courseServiceImpl;

	
	/**
	 * 获得播放代码
	 * 
	 * @param req
	 * @param courseId
	 * @param width
	 * @param height
	 * @param autoPlay
	 * @return
	 */
	@RequestMapping(value = "getVidoInfo", method = RequestMethod.GET)
	public ResponseObject getVidoInfo(HttpServletRequest req, String courseId, String width, String height,
			String autoPlay) {
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("courseId", courseId);

		if (width != null) {
			paramsMap.put("player_width", width);
		}
		if (height != null) {
			paramsMap.put("player_height", height);
		}
		if (autoPlay != null) {
			paramsMap.put("auto_play", autoPlay);
		}
		
		return ResponseObject.newSuccessResponseObject(service.getCCVideoInfo(paramsMap));
	}
	
	@RequestMapping(value = "getVideoPlayCodeByVideoId", method = RequestMethod.GET)
	public ResponseObject getVideoPlayCodeByVideoId(HttpServletRequest req,String videoId,  String width, String height,
			String autoPlay) {
		String str =  ccUtils.getPlayCode(videoId,"",width,height);
		return ResponseObject.newSuccessResponseObject(str);
	}

	/**
	 * 课程视频播放代码  获得播放代码
	 * 
	 * @param req
	 * @param courseId
	 * @param width
	 * @param height
	 * @param autoPlay
	 * @param collectionId 
	 *    如果播放的是专辑以及专辑下的下的子课程的话，需要验证这个专辑是否购买
	 * @return
	 */
	@RequestMapping(value = "getPlayCodeByCourseId", method = RequestMethod.GET)
	public ResponseObject getVidoInfo(HttpServletRequest req, 
			String courseId, String width, String height,
			String autoPlay,String directId,
			String multimediaType,
			@RequestParam(required=false)Integer collectionId) {
		
		//获取登录用户     -- 判断是否登录
		BxgUser loginUser = UserLoginUtil.getLoginUser();
		CourseLecturVo cv = null;
		if(collectionId!=null && collectionId!=0) {
			 cv = courseServiceImpl.selectUserCurrentCourseStatus(collectionId,loginUser.getId());
		}else {
			cv = courseServiceImpl.selectUserCurrentCourseStatus(
			Integer.parseInt(courseId),loginUser.getId());
		}
		if (cv!=null && cv.getWatchState() == 0) { 
		
			return ResponseObject.newErrorResponseObject("请先购买此课程哈!");
		}
//		if(cv.getType()==2) { //multimediaType 1 视频 2 音频
//			multimediaType = "2";
//		}
		
		String responsestr=  CCUtils.getPlayCodeRequest(directId,
				autoPlay,width,height,multimediaType,null);
		
		return ResponseObject.newSuccessResponseObject(responsestr);
	}

}
