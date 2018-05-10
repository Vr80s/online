package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/hospital")
public class HospitalController extends AbstractController{

    @Autowired
    private IMedicalHospitalBusinessService medicalHospitalBusinessServiceImpl;
    @Autowired
    private UserService userService;

    /**
     * Description：通过医馆分页信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:09 2017/12/10 0010
     **/
    @RequestMapping(value = "/getHospitals",method= RequestMethod.GET)
    public ResponseObject getHospitals(Integer current,Integer size,String name,String field){
        Page<MedicalHospitalVo> page = new Page<>();
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
        return ResponseObject.newSuccessResponseObject(medicalHospitalBusinessServiceImpl.selectRecHospital());
    }

    /**
     * 获取医馆热门引用医疗领域
     * @return
     */
    @RequestMapping(value = "getHotField")
    @ResponseBody
    public ResponseObject getHotField(){
        return ResponseObject.newSuccessResponseObject(medicalHospitalBusinessServiceImpl.getHotField());
    }

    /**
     * 获取医疗领域（分页）
     * @param currentPage 当前页（currentPage <= 0表示不分页）
     * @return 医疗领域列表
     */
    @RequestMapping(value = "/getFields/{currentPage}")
    @ResponseBody
    public ResponseObject getFields(@PathVariable Integer currentPage){
        Page<MedicalHospitalVo> page = new Page<>();
        int size = 10;
        if(currentPage <= 0){
            currentPage = 1;
            size = Integer.MAX_VALUE;
        }
        page.setCurrent(currentPage);
        page.setSize(size);
        return ResponseObject.newSuccessResponseObject(medicalHospitalBusinessServiceImpl.getFieldsPage(page));
    }

    /**
     * 修改(完善)医馆信息
     * @author zhuwenbao
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject update(@RequestBody MedicalHospital medicalHospital, HttpServletRequest request){

        if(medicalHospital == null){
            throw new RuntimeException("请选择要修改的医馆");
        }

        // 获取当前用户
        OnlineUser loginUser = getCurrentUser();
        UserDataVo currentUser = userService.getUserData(loginUser);
        medicalHospital.setUpdatePerson(currentUser.getUid());
        medicalHospitalBusinessServiceImpl.update(medicalHospital);
        return ResponseObject.newSuccessResponseObject("修改成功");
    }

    /**
     * 获取医馆的医师列表
     * @param current 当前页
     * @param size 每页显示的条数
     * @param doctorName 医师名字
     * @author zhuwenbao
     */
    @RequestMapping(value = "/getDoctors", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getDoctors(Integer current,Integer size,String doctorName, HttpServletRequest request){

        // 获取当前用户
        OnlineUser loginUser = getCurrentUser();
        UserDataVo currentUser = userService.getUserData(loginUser);

        // 分页信息
        Page page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        return ResponseObject.newSuccessResponseObject(medicalHospitalBusinessServiceImpl.selectDoctorPage(page, doctorName, currentUser.getUid()));
    }

    /**
     * 根据用户id获取其医馆详情
     * @author zhuwenbao
     */
    @RequestMapping(value = "/getHospitalByUserId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getHospitalByUserId(Integer current,Integer size,String doctorName, HttpServletRequest request){

        // 获取当前用户
        OnlineUser loginUser = getCurrentUser();
        UserDataVo currentUser = userService.getUserData(loginUser);
        return ResponseObject.newSuccessResponseObject(medicalHospitalBusinessServiceImpl.selectHospitalByUserId(currentUser.getUid()));
    }

    /**
     * 删除医馆里面的医师
     * @param doctorId 医师id
     */
    @RequestMapping(value = "deleteDoctor", method = RequestMethod.POST)
    public ResponseObject delete(HttpServletRequest request, String doctorId){
        // 获取当前用户
        OnlineUser loginUser = getCurrentUser();
        UserDataVo currentUser = userService.getUserData(loginUser);
        medicalHospitalBusinessServiceImpl.deleteDoctor(currentUser.getUid(), doctorId);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

}
