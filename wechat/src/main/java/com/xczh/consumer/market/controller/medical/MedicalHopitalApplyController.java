package com.xczh.consumer.market.controller.medical;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.medical.hospital.model.MedicalHospitalApply;
import com.xczhihui.medical.hospital.service.IMedicalHospitalApplyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医馆控制器 ClassName: MedicalHopitalApplyController.java <br>
 * Description: <br>医馆认证
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/medical")
public class MedicalHopitalApplyController {

	@Autowired
	private IMedicalHospitalApplyService medicalHospitalApplyService;

	@Autowired
	private OLAttachmentCenterService service;

	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private ICommonService commonServiceImpl;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MedicalHopitalApplyController.class);

	/**
	 * 医馆认证
	 */
	@RequestMapping("addHospitalApply")
	@ResponseBody
	public ResponseObject addDoctorApply(HttpServletRequest req,
										 HttpServletResponse res, MedicalHospitalApply medicalHospitalApply,
										 @RequestParam("businessLicensePictureFile")MultipartFile businessLicensePictureFile
										 , @RequestParam("licenseForPharmaceuticalTradingPictureFile")MultipartFile licenseForPharmaceuticalTradingPictureFile){


			OnlineUser user = appBrowserService.getOnlineUserByReq(req);
			if(user==null){
				return ResponseObject.newErrorResponseObject("登录失效");
			}
		try {
			medicalHospitalApply.setUserId(user.getId());
			//循环获取file数组中得文件
			String projectName="other";
			String fileType="1"; //图片类型了
			//营业执照
			String businessLicensePicture = service.upload(null,
                    projectName, businessLicensePictureFile.getOriginalFilename(),businessLicensePictureFile.getContentType(), businessLicensePictureFile.getBytes(),fileType,null);
			JSONObject businessLicensePictureJson = JSONObject.parseObject(businessLicensePicture);
			medicalHospitalApply.setBusinessLicensePicture(businessLicensePictureJson.get("url").toString());
			//药品经营许可证
			String licenseForPharmaceuticalTradingPicture = service.upload(null,
                    projectName, licenseForPharmaceuticalTradingPictureFile.getOriginalFilename(),licenseForPharmaceuticalTradingPictureFile.getContentType(), licenseForPharmaceuticalTradingPictureFile.getBytes(),fileType,null);
			JSONObject licenseForPharmaceuticalTradingPictureJson = JSONObject.parseObject(licenseForPharmaceuticalTradingPicture);
			medicalHospitalApply.setLicenseForPharmaceuticalTradingPicture(licenseForPharmaceuticalTradingPictureJson.get("url").toString());

			medicalHospitalApplyService.add(medicalHospitalApply);
		} catch (IOException e) {
			return ResponseObject.newErrorResponseObject(e.getMessage());
		}
		return ResponseObject.newSuccessResponseObject("创建成功");

	}

	/**
	 * 医师入驻申请信息
	 */
	@RequestMapping("hospitalInfo")
	@ResponseBody
	public ResponseObject getLastOne(HttpServletRequest req)
			throws Exception {

		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}

		Map<String, Object> mapAll = new HashMap<String, Object>();
		MedicalHospitalApply mda = medicalHospitalApplyService.getLastOne(user.getId());
		Integer status = commonServiceImpl.isDoctorOrHospital(user.getId());
		mapAll.put("medicalHospital",mda);
		mapAll.put("status",status);
		return ResponseObject.newSuccessResponseObject(mapAll);
	}

	
}
