package com.xczh.consumer.market.controller.medical;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.bxg.online.common.utils.RedissonUtil;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.anchor.vo.CourseAnchorVO;
import org.redisson.api.RLock;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
	@Autowired
	private RedissonUtil redissonUtil;

	@Autowired
	private IAnchorInfoService anchorInfoService;

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
			if(user==null){
				return ResponseObject.newErrorResponseObject("登录失效");
			}

			courseApplyInfo.setCreateTime(new Date());

			courseApplyInfo.setUserId(user.getId());
			//获取主播昵称
			CourseAnchorVO ca = anchorInfoService.detail(user.getId());
			courseApplyInfo.setLecturer(ca.getName());

			Map<String,Object> lecturerInfo = onlineUserService.findHostById(user.getId());
			if(lecturerInfo.get("detail")!=null&&!"".equals(lecturerInfo.get("detail"))){
				String detail = lecturerInfo.get("detail").toString();
				courseApplyInfo.setLecturerDescription(detail);
			}
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
			return ResponseObject.newErrorResponseObject(e.getMessage());
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {

		//转换日期
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

}
