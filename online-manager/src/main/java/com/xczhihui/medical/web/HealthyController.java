package com.xczhihui.medical.web;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.medical.constitution.model.MedicalConstitutionQuestionBank;
import com.xczhihui.medical.constitution.model.MedicalConstitutionQuestionRecord;
import com.xczhihui.medical.constitution.service.IConstitutionService;
import com.xczhihui.medical.constitution.vo.AnalysisResult;
import com.xczhihui.utils.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 医师管理控制层实现类
 *
 * @author yxd
 */

@Controller
@RequestMapping("medical/healthy")
public class HealthyController extends AbstractController {
    protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";
    @Autowired
    private IConstitutionService constitutionService;

    @Value("${web.url}")
    private String weburl;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
//        List<DoctorType> doctorTypes = doctorTypeService.getDoctorTypeList();
//        request.setAttribute("doctorTypes", doctorTypes);

        return CLOUD_CLASS_PATH_PREFIX + "/healthy";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;

        com.baomidou.mybatisplus.plugins.Page<MedicalConstitutionQuestionRecord> list = constitutionService.getRecordListByPage(currentPage, pageSize);
        int total = list.getTotal();
        tableVo.setAaData(list.getRecords());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    @RequestMapping(value = "info/{recordId}/{sex}")
    public String index(HttpServletRequest request,
                        @PathVariable String recordId,
                        @PathVariable String sex) {
        request.setAttribute("recordId", recordId);
        request.setAttribute("sex", sex);
        return CLOUD_CLASS_PATH_PREFIX + "/healthyInfo";
    }

    @RequestMapping(value = "getRecordById")
    @ResponseBody
    public ResponseObject getRecordById(Integer recordId) {
        ResponseObject responseObject = new ResponseObject();
        AnalysisResult record = constitutionService.getRecordById(recordId);
        responseObject.setSuccess(true);
        responseObject.setResultObject(record);
        return responseObject;
    }

    @RequestMapping(value = "getQuestionList")
    @ResponseBody
    public ResponseObject getQuestionList() {
        ResponseObject responseObject = new ResponseObject();
        List<MedicalConstitutionQuestionBank> questionBank = constitutionService.getQuestionBank();
        responseObject.setSuccess(true);
        responseObject.setResultObject(questionBank);
        return responseObject;
    }


}
