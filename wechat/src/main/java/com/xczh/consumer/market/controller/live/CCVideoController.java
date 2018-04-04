package com.xczh.consumer.market.controller.live;

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

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CCVideoController.class);
	
	/**
	 * 这个方法暂时先这样提供，能用到的就用呗
	 * 
	 * 只需要得到这个课程的状态集合,用户判断前台的页面跳转
	 * watchState 观看状态  0 免费观看  1 需要付费  2 需要密码
	 * lineState: 0 直播已结束 1 直播还未开始 2 正在直播
	 * type: 1直播  2点播 3音频 
	 * multimedia_type：1 视频 2 音频
	 */
	@RequestMapping("commonCourseStatus")
	@ResponseBody
	public ResponseObject commonCourseStatus(HttpServletRequest req,
											 HttpServletResponse res)throws Exception {
		//http://spark.bokecc.com/api/video/playcode
		String playerwidth = req.getParameter("playerwidth");
		String playerheight = req.getParameter("playerheight");
		String videoId = req.getParameter("videoId");
		String multimedia_type = req.getParameter("multimedia_type");
		String smallImgPath = req.getParameter("smallImgPath");
		boolean autoplay = true;
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

		/*<script src="https://p.bokecc.com/player?vid=9FFDF6EC272558969C33DC5901307461"
		+ "&siteid=B5E673E55C702C42&autoStart=false"
		+ "&width=600&height=490&playerid=E92940E0788E2DAE"
		+ "&playertype=1" type="text/javascript"></script>*/
		
	/*	
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
		codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" 
		width="600" height="490" id="cc_F5846BF4230F06F59C33DC5901307461">
			<param name="movie" 
			value="https://p.bokecc.com/flash/single/B5E673E55C702C42_F5846BF4230F06F59C33DC5901307461_false_E92940E0788E2DAE_1/player.swf" />
			<param name="allowFullScreen" value="true" />
			<param name="allowScriptAccess" value="always" />
			<param value="transparent" name="wmode" />
			<embed src="https://p.bokecc.com/flash/single/B5E673E55C702C42_F5846BF4230F06F59C33DC5901307461_false_E92940E0788E2DAE_1/player.swf" width="600" height="490" name="cc_F5846BF4230F06F59C33DC5901307461" allowFullScreen="true" wmode="transparent" allowScriptAccess="always" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"/>
		</object>*/
		
		
	}

	 public static void main(String[] args) {
			
	    	APIServiceFunction api = new APIServiceFunction();
	    	
//	    	Map<String, String> paramsMap = new HashMap<String, String>();
//			paramsMap.put("userid", "B5E673E55C702C42");
//			paramsMap.put("videoid", "A9067DA7F5AA34C39C33DC5901307461");
//			paramsMap.put("FORMAT", "json");
//			long time = System.currentTimeMillis();
//			String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,"K45btKhytR527yfTAjEp6z4fb3ajgu66");
//			/**
//			 * 获取video信息
//			 */
//			String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/video?" + requestURL);
//			
//			LOGGER.info(responsestr);
//			if (responsestr.contains("\"error\":")) {
//				throw new RuntimeException("该课程有视频正在做转码处理<br>请过半小时之后再操作。");
//			}
	    	
//			Map<String, String> paramsMap = new HashMap<String, String>();
//			paramsMap.put("userid", "B5E673E55C702C42");
//			paramsMap.put("videoid", "070F3FC7BEAF701F9C33DC5901307461");
//			paramsMap.put("autoplay", "true");
//			paramsMap.put("playerwidth", "100");
//			paramsMap.put("playerheight", "120");
//			paramsMap.put("FORMAT", "json");
//			long time = System.currentTimeMillis();
//			String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,"K45btKhytR527yfTAjEp6z4fb3ajgu66");
//			LOGGER.info(requestURL);
//			String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/video/playcode?" + requestURL);
//			LOGGER.info(responsestr);

			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("userid", "B5E673E55C702C42");
			/*paramsMap.put("videoid", "FB0765EE9ECDBBDF9C33DC5901307461");*/
			//aaaaaaaaaa
			paramsMap.put("customid	", "aaaaaaaaaa");
			paramsMap.put("date", "2018-01-01");
			paramsMap.put("num_per_page", "10");
			paramsMap.put("page", "1");
			long time = System.currentTimeMillis();
			String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,"K45btKhytR527yfTAjEp6z4fb3ajgu66");
			LOGGER.info(requestURL);
			String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/playlog/custom/user/v2?" + requestURL);
			LOGGER.info(responsestr);
			
			
			
		 
		 
	 }
}
