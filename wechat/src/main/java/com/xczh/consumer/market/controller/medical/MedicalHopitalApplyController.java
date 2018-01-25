package com.xczh.consumer.market.controller.medical;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.ResponseObject;
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

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MedicalHopitalApplyController.class);

	/**
	 * 医馆认证
	 */
	@RequestMapping("addHospitalApply")
	@ResponseBody
	public ResponseObject addDoctorApply(HttpServletRequest req,
										 HttpServletResponse res, MedicalHospitalApply medicalHospitalApply, @RequestParam("files")MultipartFile[] files)
			throws Exception {

		try {
			if(files!=null&&files.length>0){
				//循环获取file数组中得文件
				String projectName="other";
				String fileType="1"; //图片类型了
				//营业执照
				String businessLicensePicture = service.upload(null,
						projectName, files[0].getOriginalFilename(),files[0].getContentType(), files[0].getBytes(),fileType,null);
				JSONObject businessLicensePictureJson = JSONObject.parseObject(businessLicensePicture);
				medicalHospitalApply.setBusinessLicensePicture(businessLicensePictureJson.get("url").toString());
				//药品经营许可证
				String licenseForPharmaceuticalTradingPicture = service.upload(null,
						projectName, files[1].getOriginalFilename(),files[1].getContentType(), files[1].getBytes(),fileType,null);
				JSONObject licenseForPharmaceuticalTradingPictureJson = JSONObject.parseObject(licenseForPharmaceuticalTradingPicture);
				medicalHospitalApply.setLicenseForPharmaceuticalTradingPicture(licenseForPharmaceuticalTradingPictureJson.get("url").toString());
			}
			medicalHospitalApplyService.add(medicalHospitalApply);
			return ResponseObject.newSuccessResponseObject("提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("提交失败");
		}
	}

	
}
