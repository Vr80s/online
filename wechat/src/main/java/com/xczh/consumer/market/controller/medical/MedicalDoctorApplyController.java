package com.xczh.consumer.market.controller.medical;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.HotSearchService;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorApplyVO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 医师控制器 ClassName: MedicalDoctorApplyController.java <br>
 * Description: <br>医师认证
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/medical")
public class MedicalDoctorApplyController {

	@Autowired
	private IMedicalDoctorApplyService medicalDoctorApplyService;

	@Autowired
	private OLAttachmentCenterService service;

	@Autowired
	private AppBrowserService appBrowserService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MedicalDoctorApplyController.class);

	/**
	 * 医师认证
	 */
	@RequestMapping("addDoctorApply")
	@ResponseBody
	public ResponseObject addDoctorApply(HttpServletRequest req,
									 HttpServletResponse res,MedicalDoctorApply medicalDoctorApply, @RequestParam("files")MultipartFile[] files)
			throws Exception {

		try {
			OnlineUser user = appBrowserService.getOnlineUserByReq(req);
			if(user==null){
				return ResponseObject.newErrorResponseObject("登录失效");
			}
			medicalDoctorApply.setUserId(user.getId());
			if(files!=null&&files.length>0){
				//循环获取file数组中得文件
				String projectName="other";
				String fileType="1"; //图片类型了
				//身份证正面
				String cardPositive = service.upload(null,
						projectName, files[0].getOriginalFilename(),files[0].getContentType(), files[0].getBytes(),fileType,null);
				JSONObject cardPositiveJson = JSONObject.parseObject(cardPositive);
				medicalDoctorApply.setCardPositive(cardPositiveJson.get("url").toString());
				//身份证反面
				String cardNegative = service.upload(null,
						projectName, files[1].getOriginalFilename(),files[1].getContentType(), files[1].getBytes(),fileType,null);
				JSONObject cardNegativeJson = JSONObject.parseObject(cardNegative);
				medicalDoctorApply.setCardNegative(cardNegativeJson.get("url").toString());
				//医师资格证
				String qualificationCertificate = service.upload(null,
						projectName, files[2].getOriginalFilename(),files[2].getContentType(), files[2].getBytes(),fileType,null);
				JSONObject qualificationCertificateJson = JSONObject.parseObject(qualificationCertificate);
				medicalDoctorApply.setQualificationCertificate(qualificationCertificateJson.get("url").toString());
				//医师执业证书
				String professionalCertificate = service.upload(null,
						projectName, files[3].getOriginalFilename(),files[3].getContentType(), files[3].getBytes(),fileType,null);
				JSONObject professionalCertificateJson = JSONObject.parseObject(professionalCertificate);
				medicalDoctorApply.setProfessionalCertificate(professionalCertificateJson.get("url").toString());
			}
			medicalDoctorApplyService.add(medicalDoctorApply);
			return  ResponseObject.newSuccessResponseObject("提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("提交失败");
		}
	}

	/**
	 * 医师认证
	 */
	@RequestMapping("applyStatus")
	@ResponseBody
	public ResponseObject addDoctorApply(HttpServletRequest req,
					@RequestParam("userId") String userId)
			throws Exception {

		//1：医师认证 2：医馆认证 3：医师认证中 4：医馆认证中 5:医师认证被拒 6：医馆认证被拒 7：即不是医师也不是医馆
		
		System.out.println("userId"+userId);
		
		return ResponseObject.newSuccessResponseObject(1);
	}
	
	
	
	
	
}
