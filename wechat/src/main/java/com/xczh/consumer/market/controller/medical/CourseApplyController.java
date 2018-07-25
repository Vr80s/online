package com.xczh.consumer.market.controller.medical;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.CourseForm;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.anchor.vo.CourseAnchorVO;

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

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseApplyController.class);
    @Autowired
    private ICourseApplyService courseApplyService;
    @Autowired
    private OLAttachmentCenterService service;
    @Autowired
    private IMyInfoService myInfoService;
    @Autowired
    private IAnchorInfoService anchorInfoService;

    /**
     * 创建直播
     */
    @RequestMapping(value = "addCourseApply", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addCourseApply(@Account String accountId, CourseApplyInfo courseApplyInfo, @RequestParam("file") MultipartFile file)
            throws Exception {

        courseApplyInfo.setCreateTime(new Date());

        courseApplyInfo.setUserId(accountId);
        //获取主播昵称
        CourseAnchorVO ca = anchorInfoService.detail(accountId);
        courseApplyInfo.setLecturer(ca.getName());


        Map<String, String> lecturerInfo = myInfoService.findHostInfoById(accountId);
        if (lecturerInfo.get("detail") != null && !"".equals(lecturerInfo.get("detail"))) {
            String detail = lecturerInfo.get("detail");
            courseApplyInfo.setLecturerDescription(detail);
        }
        courseApplyInfo.setCourseForm(CourseForm.LIVE.getCode());

        //封面图片
        String projectName = "other";
        String fileType = "1"; //图片类型了
        String imgPath = service.upload(null,
                projectName, file.getOriginalFilename(), file.getContentType(), file.getBytes(), fileType, null);
        courseApplyInfo.setImgPath(imgPath);
        courseApplyInfo.setClientType(HeaderInterceptor.getClientTypeCode());
        courseApplyService.saveCourseApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("创建成功");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
}
