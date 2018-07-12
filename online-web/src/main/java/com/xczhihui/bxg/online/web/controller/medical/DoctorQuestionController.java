package com.xczhihui.bxg.online.web.controller.medical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorQuestion;
import com.xczhihui.medical.doctor.service.IMedicalDoctorQuestionService;
import com.xczhihui.medical.doctor.vo.DoctorQuestionVO;

@RestController
@RequestMapping(value = "/doctor/question")
public class DoctorQuestionController extends AbstractController {

    @Autowired
    private IMedicalDoctorQuestionService medicalDoctorQuestionService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseObject getDoctorQuestion(Integer current, Integer size) {
        Page<MedicalDoctorQuestion> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        String userId = getCurrentUser().getId();
        return ResponseObject.newSuccessResponseObject(medicalDoctorQuestionService.
                selectDoctorQuestionByUserId(page,userId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseObject updateQuestion(DoctorQuestionVO doctorQuestionVO) {
        try {
            Integer  count = medicalDoctorQuestionService.updateQuestion(doctorQuestionVO);
            if(count>0){
                return ResponseObject.newSuccessResponseObject("修改成功"); 
            }else{
                return ResponseObject.newErrorResponseObject("修改失败"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("修改失败"); 
        }
    }
    
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ResponseObject details(Integer questionId) {
        try {
            MedicalDoctorQuestion mQuestion = medicalDoctorQuestionService.findQuestionDetailsById(questionId);
            return ResponseObject.newSuccessResponseObject(mQuestion);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("查询失败");
        }
    }
    
}
