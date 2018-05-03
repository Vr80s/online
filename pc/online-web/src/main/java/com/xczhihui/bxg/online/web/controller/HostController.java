package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.service.IFocusService;
import com.xczhihui.course.service.IMyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
 * ClassName: GiftController.java <br>
 * Description: 礼物打赏接口<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@RestController
@RequestMapping(value = "/host")
public class HostController extends AbstractController{


	@Autowired
	private  IMyInfoService myInfoService;
	
	@Autowired
	private IFocusService focusService;
	
	
	/**
	 * Description:获取主播信息接口
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/getHostInfoById")
	public ResponseObject getHostInfoById(HttpServletRequest request,String lecturerId) {
		
		Map<String,Object> mapAll = new HashMap<String,Object>();
		Map<String,Object> lecturerInfo = myInfoService.findHostInfoById(lecturerId);
		mapAll.put("lecturerInfo", lecturerInfo);          //讲师基本信息
		List<Integer> listff =   focusService.selectFocusAndFansCount(lecturerId);
		mapAll.put("fansCount", listff.get(0));       	   //粉丝总数
		mapAll.put("focusCount", listff.get(1));   	  	   //关注总数
		mapAll.put("criticizeCount", listff.get(2));   	   //总数评论总数
		/**
		 * 判断用户是否已经关注了这个主播
		 */
		BxgUser loginUser = getCurrentUser();
	    if(loginUser==null){
	    	mapAll.put("isFours", 0); 
	    }else{
			Integer isFours  = focusService.isFoursLecturer(loginUser.getId(), lecturerId);
			mapAll.put("isFours", isFours); 		  //是否关注       0 未关注  1已关注
	    }
		return ResponseObject.newSuccessResponseObject(mapAll);
	}
}