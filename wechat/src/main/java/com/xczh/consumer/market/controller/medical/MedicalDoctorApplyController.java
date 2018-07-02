package com.xczh.consumer.market.controller.medical;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;

/**
 * 医师控制器 ClassName: MedicalDoctorApplyController.java <br>
 * Description: <br>
 * 医师认证 Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/medical")
public class MedicalDoctorApplyController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorApplyController.class);
    @Autowired
    private IMedicalDoctorApplyService medicalDoctorApplyService;
    @Autowired
    private OLAttachmentCenterService service;
    @Autowired
    private ICommonService commonServiceImpl;

    /**
     * 医师认证
     *
     * @throws IOException
     */
    @RequestMapping("addDoctorApply")
    @ResponseBody
    public ResponseObject addDoctorApply(@Account String accountId,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         MedicalDoctorApply medicalDoctorApply,
                                         @RequestParam(value = "cardPositiveFile", required = false) MultipartFile cardPositiveFile,
                                         @RequestParam(value = "cardNegativeFile", required = false) MultipartFile cardNegativeFile,
                                         @RequestParam("qualificationCertificateFile") MultipartFile qualificationCertificateFile,
                                         @RequestParam("professionalCertificateFile") MultipartFile professionalCertificateFile)
            throws Exception {

        LOGGER.info("--------------------医师认证开始"
                + professionalCertificateFile.getOriginalFilename());
        medicalDoctorApply.setUserId(accountId);
        // 循环获取file数组中得文件
        String projectName = "other";
        String fileType = "1"; // 图片类型了
        if (cardPositiveFile != null && cardNegativeFile != null) {
            // 身份证正面
            String cardPositive = service.upload(null, projectName,
                    cardPositiveFile.getOriginalFilename(),
                    cardPositiveFile.getContentType(), cardPositiveFile.getBytes(),
                    fileType, null);
            medicalDoctorApply.setCardPositive(cardPositive);
            // 身份证反面
            String cardNegative = service.upload(null, projectName,
                    cardNegativeFile.getOriginalFilename(),
                    cardNegativeFile.getContentType(), cardNegativeFile.getBytes(),
                    fileType, null);
            medicalDoctorApply.setCardNegative(cardNegative);
        }

        // 医师资格证
        String qualificationCertificate = service.upload(null, projectName,
                qualificationCertificateFile.getOriginalFilename(),
                qualificationCertificateFile.getContentType(),
                qualificationCertificateFile.getBytes(), fileType, null);
        medicalDoctorApply
                .setQualificationCertificate(qualificationCertificate);
        // 医师执业证书
        String professionalCertificate = service.upload(null, projectName,
                professionalCertificateFile.getOriginalFilename(),
                professionalCertificateFile.getContentType(),
                professionalCertificateFile.getBytes(), fileType, null);
        medicalDoctorApply.setProfessionalCertificate(professionalCertificate);

        LOGGER.info("--------------------医师认证");
        medicalDoctorApplyService.add(medicalDoctorApply);
        return ResponseObject.newSuccessResponseObject("创建成功");
    }

    /**
     * 医师认证
     */
    @RequestMapping("applyStatus")
    @ResponseBody
    public ResponseObject addDoctorApply(@Account String accountId)
            throws Exception {

        // 1：医师认证 2：医馆认证 3：医师认证中 4：医馆认证中 5:医师认证被拒 6：医馆认证被拒 7：即不是医师也不是医馆
        return ResponseObject.newSuccessResponseObject(commonServiceImpl.isDoctorOrHospital(accountId));
    }

    /**
     * 医师入驻申请信息
     */
    @RequestMapping("doctorInfo")
    @ResponseBody
    public ResponseObject getLastOne(@Account String accountId, HttpServletRequest req) throws Exception {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        MedicalDoctorApply mda = medicalDoctorApplyService.getLastOne(accountId);
        Integer status = commonServiceImpl.isDoctorOrHospital(accountId);
        mapAll.put("medicalDoctor", mda);
        mapAll.put("status", status);
        return ResponseObject.newSuccessResponseObject(mapAll);
    }
}