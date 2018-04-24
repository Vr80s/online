package com.xczhihui.bxg.online.web.controller.medical;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.MedicalDepartment;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.medical.department.service.IMedicalDepartmentService;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;

/**
 * 医师入驻申请控制层
 *
 * @author zhuwenbao
 */
@RestController
@RequestMapping(value = "/medical/doctor/apply")
public class DoctorApplyController extends AbstractController {

    @Autowired
    private IMedicalDoctorApplyService applyService;
    @Autowired
    private UserService userService;
    @Autowired
    private IMedicalHospitalBusinessService hospitalBusinessService;
    @Autowired
    private IMedicalDepartmentService medicalDepartmentService;

    /**
     * 添加医师入驻申请
     *
     * @param target 医师入驻申请信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject add(MedicalDoctorApply target, HttpServletRequest request) {
        if (target == null) {
            return ResponseObject.newErrorResponseObject("请求参数不能为空");
        }

        // 获取发起申请的医师的id
        target.setUserId(getOnlineUser(request).getId());
        applyService.add(target);

        return ResponseObject.newSuccessResponseObject("入驻申请信息提交成功");
    }

    /**
     * 根据用户id获取医师入驻申请信息
     */
    @RequestMapping(value = "getLastOne", method = RequestMethod.GET)
    public ResponseObject getLastOne(HttpServletRequest request) {

        // 获取当前用户
        OnlineUser loginUser = getOnlineUser(request);
        return ResponseObject.newSuccessResponseObject(applyService.getLastOne(userService.getUserData(loginUser).getUid()));
    }

    /**
     * 获取科室列表
     *
     * @param currentPage 当前页
     */
    @RequestMapping(value = "/listDepartment/{currentPage}", method = RequestMethod.GET)
    public ResponseObject listDepartment(@PathVariable Integer currentPage) {
        Page<MedicalDepartment> page = new Page();
        int size = 10;
        if (currentPage <= 0) {
            currentPage = 1;
            size = Integer.MAX_VALUE;
        }
        page.setCurrent(currentPage);
        page.setSize(size);
        return ResponseObject.newSuccessResponseObject(medicalDepartmentService.page(page));
    }

    /**
     * 获取医馆列表
     *
     * @param currentPage 当前页
     */
    @RequestMapping(value = "/listHospital/{currentPage}", method = RequestMethod.GET)
    public ResponseObject listHospital(@PathVariable Integer currentPage) {
        Page<MedicalHospitalVo> page = new Page<>();
        int size = 10;
        if (currentPage <= 0) {
            currentPage = 1;
            size = Integer.MAX_VALUE;
        }
        page.setCurrent(currentPage);
        page.setSize(size);
        return ResponseObject.newSuccessResponseObject(hospitalBusinessService.selectHospitalPage(page, null, null));
    }
}
