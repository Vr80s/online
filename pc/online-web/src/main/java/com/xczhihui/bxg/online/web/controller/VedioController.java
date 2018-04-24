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
public class VedioController {

	@Autowired
	private VedioService service;

	/**
	 * 获得验证码
	 * 
	 * @param req
	 * @param video_id
	 * @return
	 */
	@RequestMapping(value = "getCcVerificationCode", method = RequestMethod.POST)
	public ResponseObject getCcVerificationCode(HttpServletRequest req, String video_id,String id) {

		BxgUser loginUser = UserLoginUtil.getLoginUser(req);
		if (loginUser == null) {
			return ResponseObject.newErrorResponseObject("请登录！");
		}

		Map<String, String> mp = new HashMap<String, String>();
		mp.put("verificationcode", service.getCcVerificationCode(loginUser.getId(), video_id,id));
		return ResponseObject.newSuccessResponseObject(mp);
	}

	/**
	 * 获得视频播放验证信息
	 * 
	 * @param reg
	 * @param video_id
	 * @param verificationcode
	 * @return
	 */
	@RequestMapping(value = "checkAuth", method = RequestMethod.POST)
	public Map<String, Object> checkAuth(HttpServletRequest req, String vid, String verificationcode) {
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("response", service.checkAuth(vid, verificationcode));
		return mp;
	}

	/**
	 * 研发环境用，所有视频都可以播放
	 * 
	 * @param req
	 * @param vid
	 * @param verificationcode
	 * @return
	 */
	@RequestMapping(value = "checkAuthDev", method = RequestMethod.POST)
	public Map<String, Object> checkAuthDev(HttpServletRequest req, String vid, String verificationcode) {
		VedioAuthVo vo = new VedioAuthVo();
		vo.setEnable(1);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("response", vo);
		return mp;
	}

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

//	/**
//	 * 获得上传路径
//	 * 
//	 * @param req
//	 * @param title
//	 * @param description
//	 * @param tag
//	 * @param categoryid
//	 * @return
//	 */
////	@RequestMapping(value = "getUploadUrl", method = RequestMethod.GET)
////	public String getUploadUrl(HttpServletRequest req, String title, String description, String tag,
////			String categoryid) {
////		return service.getUploadUrl(title, description, tag, categoryid);
////	}
//
//	/**
//	 * 视频处理完成的回调
//	 * 
//	 * @param videoid
//	 * @param status
//	 * @param duration
//	 * @param image
//	 * @throws IOException
//	 */
//	@RequestMapping(value = "uploadSuccessCallback", method = RequestMethod.GET)
//	public void uploadSuccessCallback(HttpServletResponse res, String duration, String image, String status,
//			String videoid, String time, String hash) throws IOException {
//		service.uploadSuccessCallback(duration, image, status, videoid, time, hash);
//		res.setCharacterEncoding("UTF-8");
//		res.setContentType("text/xml; charset=utf-8");
//		res.getWriter().write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><video>OK</video>");
//	}

}
