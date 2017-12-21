package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/medical/doctor")
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
    public ResponseObject getDoctors(Integer current,Integer size,Integer type,String hospitalId,String name,String field){
        Page<MedicalDoctorVo> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.selectDoctorPage(page,type,hospitalId,name,field));
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

    @RequestMapping(value = "/getRecDoctors",method= RequestMethod.GET)
    public ResponseObject getRecDoctors(){
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.selectRecDoctor());
    }

    /**
     * 获取医师热门引用医疗领域
     * @return
     */
    @RequestMapping(value = "getHotField")
    @ResponseBody
    public ResponseObject getHotField(){
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.getHotField());
    }

    /**
     * 根据医师id获取相关医师报道
     * @return
     */
    @RequestMapping(value = "getNewsReports")
    @ResponseBody
    public ResponseObject getNewsReports(String doctorId){
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.getNewsReports(doctorId));
    }

    /**
     * 根据报道id获取相关医师报道详情
     * @return
     */
    @RequestMapping(value = "getNewsReportByArticleId")
    @ResponseBody
    public ResponseObject getNewsReportByArticleId(String articleId){
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.getNewsReportByArticleId(articleId));
    }


    /**
     * 根据医师id获取相关医师报道
     * @return
     */
    @RequestMapping(value = "getSpecialColumnByDoctorId")
    @ResponseBody
    public ResponseObject getSpecialColumnByDoctorId(String doctorId){
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.getSpecialColumnByDoctorId(doctorId));
    }

    /**
     * 根据专栏文章id获取文章内容详情
     * @return
     */
    @RequestMapping(value = "getSpecialColumnDetailsById")
    @ResponseBody
    public ResponseObject getSpecialColumnDetailsById(String articleId){
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.getSpecialColumnDetailsById(articleId));
    }
}
