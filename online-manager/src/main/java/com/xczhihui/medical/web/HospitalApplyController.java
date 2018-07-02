package com.xczhihui.medical.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.online.common.domain.MedicalHospitalApply;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.medical.service.HospitalApplyService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 医馆入驻管理控制层
 *
 * @author zhuwenbao
 */

@Controller
@RequestMapping("medical/hospital/apply")
public class HospitalApplyController extends AbstractController {

    protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";
    @Autowired
    private HospitalApplyService hospitalApplyService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        return CLOUD_CLASS_PATH_PREFIX + "/hospitalApply";
    }

    @RequestMapping(value = "/{applyId}")
    public String MedicalDoctorDetail(HttpServletRequest request,
                                      @PathVariable String applyId) {

        MedicalHospitalApply medicalHospitalApply = hospitalApplyService
                .findById(applyId);

        request.setAttribute("medicalHospitalApply", medicalHospitalApply);

        return CLOUD_CLASS_PATH_PREFIX + "/hospitalApplyDetail";
    }

    /**
     * 获取医师入驻申请列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize;

        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group searchStatus = groups.findByName("search_status");
        Group searchHospitalName = groups.findByName("search_hospitalName");

        MedicalHospitalApply searchVo = new MedicalHospitalApply();
        if (searchStatus != null) {
            searchVo.setStatus(Integer.valueOf(searchStatus.getPropertyValue1()
                    .toString()));
        }
        if (searchHospitalName != null) {
            searchVo.setName(searchHospitalName.getPropertyValue1().toString());
        }

        Page<MedicalHospitalApply> page = hospitalApplyService.list(searchVo,
                currentPage, pageSize);

        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;

    }

    /**
     * 更新医师入驻申请状态
     */
    @RequestMapping(value = "/updateStatus")
    @ResponseBody
    public ResponseObject updateStatus(MedicalHospitalApply hospitalApply) {

        hospitalApplyService.updateStatus(hospitalApply);

        ResponseObject responseObj = new ResponseObject();
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改成功");
        return responseObj;
    }
}
