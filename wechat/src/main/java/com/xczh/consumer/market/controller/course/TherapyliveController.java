/**  
* <p>Title: TherapyliveController.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年8月21日 
*/  
package com.xczh.consumer.market.controller.course;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import com.xczhihui.medical.doctor.service.IRemoteTreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

//    @Value("${weixin.course.remind.code}")
//    private String weixinTemplateMessageRemindCode;
	
	@Autowired
	private ICourseService courseService;

    @Autowired
    private IRemoteTreatmentService remoteTreatmentService;
    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;
	

    //审核通过
    @RequestMapping(value = "pass", method = RequestMethod.GET)
    public ResponseObject appointmentCheck(@RequestParam int id, @Account String accountId) throws Exception {
    	
    	//1、主播工作台生成新的待直播 、 发送短信、存放redis缓存 开播前10分钟推送消息
        Integer clientType = HeaderInterceptor.getClientTypeCode();
    	Integer courseId = courseService.createTherapyLive(id,clientType,accountId);
    	
    	//3、医师动态生成新的直播动态
        Course course = courseService.selectById(courseId);
        medicalDoctorPostsService.addDoctorPosts(accountId, course.getId(), null, course.getGradeName(), course.getSubtitle(), course.getAppointmentInfoId());
        return ResponseObject.newSuccessResponseObject("操作成功");
    }
	
    
    
	
}
