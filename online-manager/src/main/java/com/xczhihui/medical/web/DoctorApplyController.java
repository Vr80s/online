package com.xczhihui.medical.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.online.common.domain.MedicalDoctorApply;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.medical.doctor.service.IMedicalDoctorSolrService;
import com.xczhihui.medical.service.DoctorApplyService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 医师入驻管理控制层
 *
 * @author zhuwenbao
 */

@Controller
@RequestMapping("medical/doctor/apply")
public class DoctorApplyController extends AbstractController {

    protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";
    @Autowired
    private DoctorApplyService doctorApplyService;
    @Autowired
    private IMedicalDoctorSolrService medicalDoctorSolrService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        return CLOUD_CLASS_PATH_PREFIX + "/doctorApply";
    }

    /**
     * 获取医师入驻申请列表
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;

        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group searchStatus = groups.findByName("search_status");
        Group searchDoctorName = groups.findByName("search_doctorName");

        MedicalDoctorApply searchVo = new MedicalDoctorApply();
        if (searchStatus != null) {
            searchVo.setStatus(Integer.valueOf(searchStatus.getPropertyValue1()
                    .toString()));
        }
        if (searchDoctorName != null) {
            searchVo.setName(searchDoctorName.getPropertyValue1().toString());
        }

        Page<MedicalDoctorApply> page = doctorApplyService.list(searchVo,
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
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(MedicalDoctorApply doctorApply) throws IOException, SolrServerException {

        ResponseObject responseObj = new ResponseObject();

        String doctorId = doctorApplyService.updateStatus(doctorApply);
        medicalDoctorSolrService.initDoctorsSolrDataById(doctorId);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改成功");
        return responseObj;
    }

    @RequestMapping(value = "info/{appId}")
    public String MedicalDoctorDetail(HttpServletRequest request,
                                      @PathVariable String appId) {

        MedicalDoctorApply medicalDoctorApply = doctorApplyService.findById(appId);
        request.setAttribute("medicalDoctorApply", medicalDoctorApply);

        return CLOUD_CLASS_PATH_PREFIX + "/doctorApplyDetail";
    }

}
