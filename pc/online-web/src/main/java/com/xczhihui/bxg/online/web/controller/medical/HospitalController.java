package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/medical/hospital")
public class HospitalController {

    @Autowired
    private IMedicalHospitalBusinessService medicalHospitalBusinessServiceImpl;

    /**
     * Description：通过医馆分页信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:09 2017/12/10 0010
     **/
    @RequestMapping(value = "/getHospitals",method= RequestMethod.GET)
    public ResponseObject getHospitals(Integer current,Integer size,String name,String field){
        Page<MedicalHospital> page = new Page<>();

        page.setCurrent(current);
        page.setSize(size);
        return ResponseObject.newSuccessResponseObject(medicalHospitalBusinessServiceImpl.selectHospitalPage(page,name,field));
    }

    /**
     * Description：通过医馆id获取详细信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:09 2017/12/10 0010
     **/
    @RequestMapping(value = "/getHospitalById",method= RequestMethod.GET)
    public ResponseObject getHospitalById(String id) {
        return ResponseObject.newSuccessResponseObject(medicalHospitalBusinessServiceImpl.selectHospitalById(id));
    }

    @RequestMapping(value = "/getRecHospitals",method= RequestMethod.GET)
    public ResponseObject getRecHospitals(){
        Page<MedicalHospital> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        return ResponseObject.newSuccessResponseObject(medicalHospitalBusinessServiceImpl.selectRecHospital());
    }

    /**
     * 获取热门引用医疗领域
     * @return
     */
    @RequestMapping(value = "getHotField")
    @ResponseBody
    public ResponseObject getHotField(){
        return ResponseObject.newSuccessResponseObject(medicalHospitalBusinessServiceImpl.getHotField());
    }

}
