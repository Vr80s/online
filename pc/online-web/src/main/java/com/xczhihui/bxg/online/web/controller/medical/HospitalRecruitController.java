package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.medical.hospital.service.IMedicalHospitalRecruitBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/medical/hospitalRecruit")
public class HospitalRecruitController {

    @Autowired
    private IMedicalHospitalRecruitBusinessService medicalHospitalRecruitBusinessService;

    /**
     * Description：通过医馆id获取招聘列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:09 2017/12/10 0010
     **/
    @RequestMapping(value = "/getHospitalRecruitById",method= RequestMethod.GET)
    public ResponseObject getHospitalRecruitById(String hospitalId) {
        return ResponseObject.newSuccessResponseObject(medicalHospitalRecruitBusinessService.selectHospitalRecruitByHospitalId(hospitalId));
    }

    @RequestMapping(value = "/getRecHospitalRecruits",method= RequestMethod.GET)
    public ResponseObject getRecHospitalRecruits(){
        return ResponseObject.newSuccessResponseObject(medicalHospitalRecruitBusinessService.selectRecHospitalRecruit());
    }

}
