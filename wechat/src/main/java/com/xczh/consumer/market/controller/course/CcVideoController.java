package com.xczh.consumer.market.controller.course;

//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/xczh/ccvideo")
public class CcVideoController {


	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CcVideoController.class);
	
	/**
	 * 这个方法暂时先这样提供，能用到的就用呗
	 * 
	 * 只需要得到这个课程的状态集合,用户判断前台的页面跳转
	 * watchState 观看状态  0 免费观看  1 需要付费  2 需要密码
	 * lineState: 0 直播已结束 1 直播还未开始 2 正在直播
	 * type: 1直播  2点播 3音频 
	 * multimedia_type：1 视频 2 音频
	 */
	@RequestMapping("palyCode")
	@ResponseBody
	public ResponseObject commonCourseStatus(HttpServletRequest req,
											 HttpServletResponse res)throws Exception {
		//http://spark.bokecc.com/api/video/playcode
		String playerwidth = req.getParameter("playerwidth");
		String playerheight = req.getParameter("playerheight");
		String videoId = req.getParameter("videoId");
		String multimedia_type = req.getParameter("multimedia_type");
		String smallImgPath = req.getParameter("smallImgPath");
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", "B5E673E55C702C42");
		paramsMap.put("videoid", videoId);
		paramsMap.put("auto_play", "false");
		paramsMap.put("player_width", playerwidth);
		
		//cc_A9067DA7F5AA34C39C33DC5901307461    A9067DA7F5AA34C39C33DC5901307461
		BigDecimal decimal = new BigDecimal(playerheight);
		BigDecimal setScale = decimal.setScale(0,BigDecimal.ROUND_HALF_DOWN);
		paramsMap.put("player_height",setScale+"");
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,"K45btKhytR527yfTAjEp6z4fb3ajgu66");
		String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/video/playcode?" + requestURL);
		LOGGER.info(responsestr);
		if (responsestr.contains("\"error\":")) {
			return ResponseObject.newErrorResponseObject("视频走丢了，请试试其他视频。");
		}
		//如果是音频的话需要这样暂时替换下
		if("2".equals(multimedia_type)){
			responsestr = responsestr.replaceAll("playertype=1", "playertype=1&mediatype=2");
		}
		//背景图片
		if(StringUtils.isNotBlank(smallImgPath)){
			responsestr = responsestr.replaceAll("playertype=1", "playertype=1&img_path="+smallImgPath);
		}
		LOGGER.info(responsestr);
		return ResponseObject.newSuccessResponseObject(responsestr);


	}

}
