package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.service.VideoService;
import com.xczh.consumer.market.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Map;

/**
 * 
 * 客户端用户访问控制器类
 * @author Alex Wang
 **/
@Controller
@RequestMapping("/bxg/video")
public class VideoController {
	@Autowired
	private VideoService service;
//	@Autowired
//	private CacheService cacheService;
	/**
	 * 获得播放代码
	 * @param req
	 * @param video_id
	 * @param width
	 * @param height
	 * @param autoPlay
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("getVidoInfo")
	@ResponseBody
	public ResponseObject getVidoInfo(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		return ResponseObject.newSuccessResponseObject(service.getVideoInfo(req.getParameter("video_id")));
	}
	
	@RequestMapping("videoInfo")
	@ResponseBody
	public ResponseObject videoInfo(HttpServletRequest req,
                                    HttpServletResponse res, Map<String, String> params)throws Exception{
		Map<String,String> video = service.videoInfo(req.getParameter("video_id"),req);
		return ResponseObject.newSuccessResponseObject(video);
	}
	
	
    /**
     * 从
     * v.新版本要求查询课程中所有章、节、知识点下的视频列表
     * @param request
     * @param courseId
     * @return
     * @throws SQLException 
     */
    @RequestMapping("getVideos")
    @ResponseBody
    public ResponseObject getvideos(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws SQLException {
    	if(null == req.getParameter("courseId")){
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		Integer courseId = Integer.parseInt((req.getParameter("courseId")));
//		String userId = req.getParameter("userId");
//		//获取当前登录用户信息
//        OnlineUser user = cacheService.get(userId);
//        if(user ==null){
//        	return ResponseObject.newErrorResponseObject("登录失效");
//        }
        //isTryLearn 是否试学
        boolean isTryLearn= true;
        return ResponseObject.newSuccessResponseObject(service.getvideos(courseId, null, isTryLearn));
    }
	
	
}
