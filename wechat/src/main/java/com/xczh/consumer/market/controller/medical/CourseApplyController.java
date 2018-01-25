package com.xczh.consumer.market.controller.medical;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 直播课程控制器 ClassName: MedicalDoctorApplyController.java <br>
 * Description: <br>直播课程
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/medical")
public class CourseApplyController {

	@Autowired
	private ICourseApplyService courseApplyService;

	@Autowired
	private OLAttachmentCenterService service;

	@Autowired
	private OnlineUserService onlineUserService;

	@Autowired
	private AppBrowserService appBrowserService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseApplyController.class);

	/**
	 * 创建直播
	 */

	@RequestMapping("addCourseApply")
	@ResponseBody
	public ResponseObject addCourseApply(HttpServletRequest req,
										 HttpServletResponse res, CourseApplyInfo courseApplyInfo, @RequestParam("file")MultipartFile file)
			throws Exception {

		try {
			OnlineUser user = appBrowserService.getOnlineUserByReq(req);

			courseApplyInfo.setCreateTime(new Date());

			courseApplyInfo.setUserId(user.getId());
			courseApplyInfo.setLecturer(user.getName());

			Map<String,Object> lecturerInfo = onlineUserService.findHostById(user.getId());
			String detail = lecturerInfo.get("detail").toString();
			courseApplyInfo.setLecturerDescription(detail);
			courseApplyInfo.setCourseForm(CourseForm.LIVE.getCode());

			//封面图片
			String projectName="other";
			String fileType="1"; //图片类型了
			String imgPath = service.upload(null,
					projectName, file.getOriginalFilename(),file.getContentType(), file.getBytes(),fileType,null);
			JSONObject imgPathJson = JSONObject.parseObject(imgPath);
			courseApplyInfo.setImgPath(imgPathJson.get("url").toString());

			courseApplyService.saveCourseApply(courseApplyInfo);
			return  ResponseObject.newSuccessResponseObject("创建成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("创建失败");
		}
	}


}
