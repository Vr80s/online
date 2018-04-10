package com.xczhihui.operate.web;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.operate.service.MobilePartnerCourseService;
import com.xczhihui.operate.vo.MobilePartnerCourseVo;
import com.xczhihui.support.shiro.ManagerUserUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 移动合伙人课程控制层代码
 * 
 * @Author Fudong.Sun【】
 * @Date 2017/3/9 20:55
 */
@Controller
@RequestMapping(value = "/mobile/partner")
public class MobilePartnerCourseController {
	@Autowired
	private MobilePartnerCourseService mobilePartnerCourseService;
	@Autowired
	private AttachmentCenterService att;

	@Value("${share.course.id}")
	private Integer courseId;

	/**
	 * 移动合伙人课程页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("/operate/mobilePartnerCourse");
		mav.addObject("course_id", courseId);
		return mav;
	}

	/**
	 * 课程页详情数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/courseInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject courseInfo() {
		MobilePartnerCourseVo mobilePartnerCourse = mobilePartnerCourseService
				.findCourseInfoByCourseId();
		return ResponseObject.newSuccessResponseObject(mobilePartnerCourse);
	}

	/**
	 * 保存修改详情页数据
	 * 
	 * @param mobilePartnerCourseVo
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject saveOrUpdate(
			MobilePartnerCourseVo mobilePartnerCourseVo) {
		mobilePartnerCourseService.saveOrUpdate(mobilePartnerCourseVo);
		return ResponseObject.newSuccessResponseObject("保存成功");
	}

	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject uploadImg(String content) {
		String str = content.split("base64,")[1];
		byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(str);
		Attachment a = att.addAttachment(ManagerUserUtil.getId(),
				AttachmentType.ONLINE, "1.png", b, "image/png");
		if (a.getError() != 0) {
			return ResponseObject.newErrorResponseObject("上传失败！");
		}
		return ResponseObject.newSuccessResponseObject(a);
	}
}
