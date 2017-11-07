package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.service.OLCourseServiceI;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.ChapterVo;
import com.xczh.consumer.market.vo.MyCourseVo;
import com.xczh.consumer.market.vo.WxcpCourseVo;
import com.xczh.consumer.market.vo.WxcpOeMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bxg/course")
public class OLCourseController {
	
	@Autowired
	private OLCourseServiceI wxcpCourseService;
	
	/***
	 * 学科分类列表
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("category/list")
	@ResponseBody
	public ResponseObject categoryList(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		List<WxcpOeMenuVo> list = wxcpCourseService.categoryList();
		return ResponseObject.newSuccessResponseObject(list);
	}
	
	/***
	 * 学科分类下课程列表
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@ResponseBody
	public ResponseObject courseList(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		
		String menid = req.getParameter("menu_id");
		String s = req.getParameter("start_num");
		String e = req.getParameter("end_num");
		if("".equals(menid) || menid==null || "null".equals(menid)){
			return ResponseObject.newErrorResponseObject("分类ID是空的");
		}
		int number =0;
		if(!"".equals(s) && s!=null && !"null".equals(s)){
			 number = Integer.valueOf(s);
		}
		
		int pageSize=0;
		if("".equals(e) || e==null || "null".equals(e)){
			pageSize=6;
		}else{
			pageSize = Integer.valueOf(e);
		}
		int menu_id = Integer.valueOf(menid);
		List<WxcpCourseVo> list = wxcpCourseService.courseListByCategory(menu_id, number, pageSize);
		return ResponseObject.newSuccessResponseObject(list);
	}
	
	/***
	 * 课程详细信息
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@ResponseBody
	public ResponseObject courseDetail(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		String courseid = req.getParameter("course_id");
		if("".equals(courseid) || courseid==null || "null".equals(courseid)){
			return ResponseObject.newErrorResponseObject("课程ID是空的");
		}
		int course_id = Integer.valueOf(courseid);
		String user_id = req.getParameter("user_id");
		WxcpCourseVo course = wxcpCourseService.courseDetail1(course_id,user_id);
		return ResponseObject.newSuccessResponseObject(course);
	}
	
	/***
	 * 课程详细信息
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detailtabs")
	@ResponseBody
	public ResponseObject courseDetail2(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		String courseid = req.getParameter("course_id");
		if("".equals(courseid) || courseid==null || "null".equals(courseid)){
			return ResponseObject.newErrorResponseObject("课程ID是空的");
		}
		int course_id = Integer.valueOf(courseid);
		WxcpCourseVo course = wxcpCourseService.courseDetail2(course_id);
		return ResponseObject.newSuccessResponseObject(course);
	}
	
	/***
	 * 详情订单信息
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("orderDetail")
	@ResponseBody
	public ResponseObject courseOrderDetail(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		String courseid = req.getParameter("course_id");
		if("".equals(courseid) || courseid==null || "null".equals(courseid)){
			return ResponseObject.newErrorResponseObject("课程ID是空的");
		}
		int course_id = Integer.valueOf(courseid);
		WxcpCourseVo course = wxcpCourseService.courseOrderDetail(course_id);
		
		return ResponseObject.newSuccessResponseObject(course);
	}
	
	/**
	 * 课程试看 结构 知识点--视频
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("video/trailers")
	@ResponseBody
	public ResponseObject videoTrailers(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		String courseid = req.getParameter("course_id");
		if("".equals(courseid) || courseid==null || "null".equals(courseid)){
			return ResponseObject.newErrorResponseObject("课程ID是空的");
		}
		int course_id = Integer.valueOf(courseid);
		List<ChapterVo> charptersVideos = wxcpCourseService.videoTrailersList(course_id);
		return ResponseObject.newSuccessResponseObject(charptersVideos);
	}
	
	/**
	 * 我的课程
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("user/mycourse")
	@ResponseBody
	public ResponseObject myCourseList(HttpServletRequest req, HttpServletResponse res, Map<String, String> params)throws Exception{
		String user_id = req.getParameter("user_id");
		if("".equals(user_id) || user_id==null || "null".equals(user_id)){
			return ResponseObject.newErrorResponseObject("用户ID是空的");
		}
		String s = req.getParameter("start_num");
		String e = req.getParameter("end_num");
		int number = Integer.valueOf(s);
		int pageSize = Integer.valueOf(e);
		List<MyCourseVo> mycourses = wxcpCourseService.myCourseList(user_id,number,pageSize);//2c90819158afa2170158afa393090000
		
		return ResponseObject.newSuccessResponseObject(mycourses);
	}

	/**
	 * 课程结构 知识点--视频
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("video/look")
	@ResponseBody
	public ResponseObject videoLook(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		String courseid = req.getParameter("course_id");
		if("".equals(courseid) || courseid==null || "null".equals(courseid)){
			return ResponseObject.newErrorResponseObject("课程ID是空的");
		}
		int course_id = Integer.valueOf(courseid);
		List<ChapterVo> charptersVideos = wxcpCourseService.videoLookList(course_id);
		return ResponseObject.newSuccessResponseObject(charptersVideos);
	}
	
}
