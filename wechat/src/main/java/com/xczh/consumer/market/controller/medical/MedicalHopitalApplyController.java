package com.xczh.consumer.market.controller.medical;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.medical.hospital.model.MedicalHospitalApply;
import com.xczhihui.medical.hospital.service.IMedicalHospitalApplyService;

/**
 * 医馆控制器 ClassName: MedicalHopitalApplyController.java <br>
 * Description: <br>
 * 医馆认证 Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/medical")
public class MedicalHopitalApplyController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MedicalHopitalApplyController.class);
    @Autowired
    private IMedicalHospitalApplyService medicalHospitalApplyService;
    @Autowired
    private OLAttachmentCenterService service;
    @Autowired
    private ICommonService commonServiceImpl;

    /**
     * 医馆认证
     *
     * @throws IOException
     */
    @RequestMapping("addHospitalApply")
    @ResponseBody
    public ResponseObject addDoctorApply(@Account String accountId,
                                         MedicalHospitalApply medicalHospitalApply,
                                         @RequestParam("businessLicensePictureFile") MultipartFile businessLicensePictureFile,
                                         @RequestParam("licenseForPharmaceuticalTradingPictureFile") MultipartFile licenseForPharmaceuticalTradingPictureFile)
            throws IOException {
        medicalHospitalApply.setUserId(accountId);
        // 循环获取file数组中得文件
        String projectName = "other";
        String fileType = "1"; // 图片类型了
        // 营业执照
        String businessLicensePicture = service.upload(null, projectName,
                businessLicensePictureFile.getOriginalFilename(),
                businessLicensePictureFile.getContentType(),
                businessLicensePictureFile.getBytes(), fileType, null);
        medicalHospitalApply.setBusinessLicensePicture(businessLicensePicture);
        // 药品经营许可证
        String licenseForPharmaceuticalTradingPicture = service.upload(null,
                projectName, licenseForPharmaceuticalTradingPictureFile
                        .getOriginalFilename(),
                licenseForPharmaceuticalTradingPictureFile.getContentType(),
                licenseForPharmaceuticalTradingPictureFile.getBytes(),
                fileType, null);
        medicalHospitalApply
                .setLicenseForPharmaceuticalTradingPicture(licenseForPharmaceuticalTradingPicture);

        medicalHospitalApply.setClientType(HeaderInterceptor.getClientTypeCode());
        medicalHospitalApplyService.add(medicalHospitalApply);
        return ResponseObject.newSuccessResponseObject("创建成功");
    }

    /**
     * 医师入驻申请信息
     */
    @RequestMapping("hospitalInfo")
    @ResponseBody
    public ResponseObject getLastOne(@Account String accountId) throws Exception {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        MedicalHospitalApply mda = medicalHospitalApplyService.getLastOne(accountId);
        Integer status = commonServiceImpl.isDoctorOrHospital(accountId);
        mapAll.put("medicalHospital", mda);
        mapAll.put("status", status);
        return ResponseObject.newSuccessResponseObject(mapAll);
    }
}
