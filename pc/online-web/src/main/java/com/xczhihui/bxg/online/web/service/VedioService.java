package com.xczhihui.bxg.online.web.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.bxg.online.web.vo.VedioAuthVo;

/**
 * 视频播放验证
 * 
 * @author Haicheng Jiang
 *
 */
public interface VedioService {
	/**
	 * 获得验证码
	 * @param userId
	 * @param vid
	 * @param id
	 * @return
	 */
	public String getCcVerificationCode(String userId,String vid,String id);
	/**
	 * 获得视频播放验证信息
	 * @param vid
	 * @param verificationcode
	 * @return
	 */
	public VedioAuthVo checkAuth(String vid,String verificationcode);
	/**
	 * 获得视频详情
	 * @param paramsMap
	 * @param req 
	 * @return
	 */
	public Map<String,Object> getVideoInfo(Map<String, String> paramsMap, HttpServletRequest req);

	public Map<String,Object> getCCVideoInfo(Map<String, String> paramsMap);
	/**
	 * 获得上传路径
	 * @param title
	 * @param description
	 * @param tag
	 * @param categoryid
	 * @return
	 */
//	public String getUploadUrl(String title, String description, String tag,String categoryid);
	/**
	 * 视频处理成功的回调
	 * @param duration
	 * @param image
	 * @param status
	 * @param videoid
	 * @param time
	 * @param hash
	 */
//	public void uploadSuccessCallback(String duration,String image,String status,String videoid,String time,String hash);
}
