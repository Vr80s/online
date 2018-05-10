package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.web.service.VedioService;
import com.xczhihui.bxg.online.web.vo.VedioAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

}
