/**  
* <p>Title: TherapyliveController.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年8月21日 
*/  
package com.xczh.consumer.market.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.medical.doctor.model.TreatmentAppointmentInfo;
import com.xczhihui.medical.doctor.service.IRemoteTreatmentService;

/**
* @ClassName: TherapyliveController
* @Description: 诊疗直播
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年8月21日
*
*/
@RestController
@RequestMapping("/xczh/therapy")
public class TherapyliveController {

	@Autowired
	private ICourseService courseService;

    @Autowired
    private IRemoteTreatmentService remoteTreatmentService;
	
    //审核通过
    @RequestMapping(value = "pass", method = RequestMethod.GET)
    public ResponseObject appointmentCheck(@RequestParam int id, @Account String accountId) throws Exception {
    	
    	//1、发送短信提示
    	
    	
    	
    	//2、主播工作台生成新的待直播
        //获取客户端类型
        Integer clientType = HeaderInterceptor.getClientTypeCode();
    	courseService.createTherapyLive(id,clientType);
    	
    	//3、医师动态生成新的直播动态
    	
    	//4、预约诊疗时间前10分钟，系统分别向医师及申请人发送短信提醒。
    	
        return ResponseObject.newSuccessResponseObject("");
    }
	
	
}
