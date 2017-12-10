package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/")
public class DoctorController {

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;

    /**
     * Description：获取医师分页信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:07 2017/12/10 0010
     **/
    @RequestMapping(value = "/getDoctors",method= RequestMethod.GET)
    public ResponseObject getDoctors(Integer type){
        Page<MedicalDoctor> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.selectDoctorPage(page));
    }

    /**
     * Description：通过医师id获取详细信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:09 2017/12/10 0010
     **/
    @RequestMapping(value = "/getDoctorById",method= RequestMethod.GET)
    public ResponseObject getDoctorById(String id) {
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.selectDoctorById(id));
    }
}
