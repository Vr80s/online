package com.xczh.consumer.market.controller.live;

//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.cc.APIServiceFunction;

//import java.util.UUID;

/**
 * 直播控制器
 * ClassName: LiveController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
@Controller
@RequestMapping("/bxg/ccvideo")
public class CCVideoController {

	@Autowired
	private OnlineCourseService onlineCourseService;

	/**
	 * 这个方法暂时先这样提供，能用到的就用呗
	 * 
	 * 只需要得到这个课程的状态集合,用户判断前台的页面跳转
	 * watchState 观看状态  0 免费观看  1 需要收费  2 需要密码
	 * lineState: 0 直播已结束 1 直播还未开始 2 正在直播
	 * type: 1直播  2点播 3音频 
	 * 
	 */
	@RequestMapping("commonCourseStatus")
	@ResponseBody
	public ResponseObject commonCourseStatus(HttpServletRequest req,
											 HttpServletResponse res)throws Exception {
		//http://spark.bokecc.com/api/video/playcode
		String playerwidth = req.getParameter("playerwidth");
		String playerheight = req.getParameter("playerheight");
		String videoId = req.getParameter("videoId");
		boolean autoplay = true;
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", "B5E673E55C702C42");
		paramsMap.put("videoid", videoId);
		paramsMap.put("auto_play", "true");
		paramsMap.put("player_width", playerwidth);
		paramsMap.put("player_height", playerheight);
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,"K45btKhytR527yfTAjEp6z4fb3ajgu66");
		String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/video/playcode?" + requestURL);
		System.out.println(responsestr);
		if (responsestr.contains("\"error\":")) {
			return ResponseObject.newErrorResponseObject("视频走丢了，请试试其他视频。");
		}
		return ResponseObject.newSuccessResponseObject(responsestr);
	}

	 public static void main(String[] args) {
			
//	    	APIServiceFunction api = new APIServiceFunction();
//	    	
//	    	Map<String, String> paramsMap = new HashMap<String, String>();
//			paramsMap.put("userid", "B5E673E55C702C42");
//			paramsMap.put("videoid", "A9067DA7F5AA34C39C33DC5901307461");
//			paramsMap.put("format", "json");
//			long time = System.currentTimeMillis();
//			String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,"K45btKhytR527yfTAjEp6z4fb3ajgu66");
//			/**
//			 * 获取video信息
//			 */
//			String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/video?" + requestURL);
//			
//			System.out.println(responsestr);
//			if (responsestr.contains("\"error\":")) {
//				throw new RuntimeException("该课程有视频正在做转码处理<br>请过半小时之后再操作。");
//			}
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("userid", "B5E673E55C702C42");
			paramsMap.put("videoid", "A9067DA7F5AA34C39C33DC59013074611");
			paramsMap.put("autoplay", "true");
			paramsMap.put("playerwidth", "100");
			paramsMap.put("playerheight", "120");
			paramsMap.put("format", "json");
			long time = System.currentTimeMillis();
			String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,"K45btKhytR527yfTAjEp6z4fb3ajgu66");
			System.out.println(requestURL);
			String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/video/playcode?" + requestURL);
			System.out.println(responsestr);
	 }
}
