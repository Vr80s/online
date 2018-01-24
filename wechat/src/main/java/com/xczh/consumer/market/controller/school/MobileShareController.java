package com.xczh.consumer.market.controller.school;

import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.LecturVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 热门搜索控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>默认关键字与热门搜索
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/xczh/share")
public class MobileShareController {

	@Autowired
	private OnlineCourseService onlineCourseService;
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileShareController.class);

	/**
	 * 课程分享
	 */
	@RequestMapping("courseShare")
	@ResponseBody
	public ResponseObject courseShare(@RequestParam(value="courseId")Integer courseId)throws Exception{
		try {
			CourseLecturVo courseLectur = onlineCourseService.courseShare(courseId);
			if(courseLectur.getDescription()!=null){
				String description = courseLectur.getDescription();
				description = com.xczh.consumer.market.utils.StringUtils.delHTMLTag(description);
				courseLectur.setDescription(description);
			}
			courseLectur.setLink(returnOpenidUri+"/course_details.html?courseId="+courseId);

			return ResponseObject.newSuccessResponseObject(courseLectur);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请求有误");
		}
	}

	/**
	 * 主播分享
	 */
	@RequestMapping("lectureShare")
	@ResponseBody
	public ResponseObject lectureShare(@RequestParam(value="lecturerId")String lecturerId)throws Exception{
		try {
			LecturVo lectur = onlineCourseService.lectureShare(lecturerId);
			if(lectur.getDescription()!=null){
				String description = lectur.getDescription();
				description = com.xczh.consumer.market.utils.StringUtils.delHTMLTag(description);
				lectur.setDescription(description);
			}
			lectur.setLink(returnOpenidUri+"/speaker.html?lecturerId="+lecturerId);
			return ResponseObject.newSuccessResponseObject(lectur);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请求有误");
		}
	}

	
}
