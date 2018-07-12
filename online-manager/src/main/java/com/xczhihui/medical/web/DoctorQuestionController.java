package com.xczhihui.medical.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorQuestion;
import com.xczhihui.medical.service.DoctorQuestionService;
import com.xczhihui.medical.vo.DoctorQuestionVO;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 医师管理控制层实现类
 *
 * @author yxd
 */
@Controller
@RequestMapping("medical/question")
public class DoctorQuestionController extends AbstractController {
    protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";
    
    
    @Autowired
    private DoctorQuestionService questionService;
 
    @Value("${web.url}")
    private String weburl;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        return CLOUD_CLASS_PATH_PREFIX + "/question";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        
        Group doctorName = groups.findByName("doctorName");
        Group status = groups.findByName("status");
        
        DoctorQuestionVO searchVo = new DoctorQuestionVO();
//        if (doctorName != null) {
//            searchVo.setCreatePerson(doctorName.getPropertyValue1().toString());
//        }
//        if (nameGroup != null) {
//            searchVo.setName(nameGroup.getPropertyValue1().toString());
//        }
        
        Page<MedicalDoctorQuestion> page = questionService.selectQuestion(searchVo,
                currentPage, pageSize);

        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }
    /**
     * 查看
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "details", method = RequestMethod.GET)
    @ResponseBody
    public MedicalDoctorQuestion findMedicalDoctorQuestionById(Integer id) {
        return questionService.findMedicalDoctorQuestionById(id);
    }

    /**
     * 编辑
     *
     * @param MedicalDoctorQuestion
     * @return
     */
    @RequestMapping(value = "updateMedicalDoctorQuestionById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateMedicalDoctorQuestionById(MedicalDoctorQuestion medicalDoctorQuestion) {
        
        ResponseObject responseObj = new ResponseObject();
        MedicalDoctorQuestion old = questionService.
                findMedicalDoctorQuestionById(medicalDoctorQuestion.getId());

        old.setAnswer(medicalDoctorQuestion.getAnswer());
        old.setUpdateTime(new Date());
        
        questionService.updateMedicalDoctorQuestion(old);
        
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改成功");
        
        return responseObj;
    }

    /**
     * 修改状态(禁用or启用)
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(Integer id) throws IOException{
        ResponseObject responseObj = new ResponseObject();
        try {
            questionService.updateStatus(id);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteMedicalDoctorQuestionById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteMedicalDoctorQuestionById(Integer id) {
        questionService.deleteMedicalDoctorQuestionById(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException,
            IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            questionService.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功!");
        return responseObject;
    }




}
