package com.xczh.consumer.market.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.MessageTypeEnum;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
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

    //审核通过
    @RequestMapping(value = "pass", method = RequestMethod.GET)
    public ResponseObject pass(@RequestParam int id, @Account String accountId) throws Exception {
    	
    	//1、主播工作台生成新的待直播 、 发送短信、存放redis缓存 开播前10分钟推送消息
        Integer clientType = HeaderInterceptor.getClientTypeCode();
        
    	courseService.createTherapyLive(id,clientType,accountId);
    	
    	//3、医师动态生成新的直播动态
    	
        return ResponseObject.newSuccessResponseObject("操作成功");
    }
	
    
    //审核通过
    @RequestMapping(value = "cancel", method = RequestMethod.GET)
    public ResponseObject cancel(@RequestParam int id, @Account String accountId) throws Exception {
    	
        
    	courseService.updateTherapyLive(id,accountId);
    	
    	
        return ResponseObject.newSuccessResponseObject("操作成功");
    }
    
    
	
}
