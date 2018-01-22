package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/medical/hospital")
public class HospitalController {

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
     * 添加医师
     */
    @RequestMapping(value = "/addDoctor")
    @ResponseBody
    public ResponseObject addDoctor(MedicalDoctor medicalDoctor, HttpServletRequest request){
        // 获取当前用户
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录！");
        }
        UserDataVo currentUser = userService.getUserData(loginUser);
        medicalDoctor.setUserId(currentUser.getUid());
        medicalHospitalBusinessServiceImpl.addDoctor(medicalDoctor);
        return ResponseObject.newSuccessResponseObject("添加成功");
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

}
