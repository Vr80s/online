package com.xczh.consumer.market.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorQuestion;
import com.xczhihui.medical.doctor.service.IMedicalDoctorQuestionService;

/**
 * Description：医师页面
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/26 0026 上午 11:59
 **/
@RestController
@RequestMapping(value = "/xczh/question")
public class DoctorQuestionController {

    @Autowired
    private IMedicalDoctorQuestionService medicalDoctorQuestionService;


    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    /**
     * Description:医师答疑的问题
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("doctorQuestion")
    public ResponseObject introduction(@RequestParam("doctorId") String doctorId,
            @RequestParam(value = "pageNumber", required = false)Integer pageNumber,
            @RequestParam(value = "pageSize", required = false)Integer pageSize)
            throws Exception {

        pageNumber = (pageNumber == null ? 1 : pageNumber);
        pageSize = (pageSize == null ? 10 : pageSize);
        
        Page<MedicalDoctorQuestion> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        
        Page<MedicalDoctorQuestion> pageMdq = medicalDoctorQuestionService.selectQuestionByDoctorId(page,doctorId);
        return ResponseObject.newSuccessResponseObject(pageMdq.getRecords());
    }

    
    /**
     * Description:添加医师问题
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("addDoctorQuestion")
    public ResponseObject introduction(@RequestParam("doctorId") String doctorId,
            @RequestParam("question") String question,@Account String accountId)
            throws Exception {
        
        medicalDoctorQuestionService.addQuestion(accountId,doctorId,question);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }
    
    
}
