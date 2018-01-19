package com.xczh.consumer.market.controller.live;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.service.GiftService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
/**
 * 
 * ClassName: HostController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/bxg/host")
public class HostController {
	
	@Autowired
	private OnlineCourseService onlineCourseService;
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private FocusService focusService;
	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private GiftService giftService;
	
	@Autowired
	private OnlineWebService onlineWebService;

	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	@Value("${webdomain}")
	private String webdomain;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HostController.class);
	
	
	/**
	 * Description：用户主页    -- 讲师基本信息、是否已经关注了这个主播、关注该主播的粉丝数、主播关注的主播数、关注该主播的前六个人的头像
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("hostPageInfo")
	@ResponseBody
	public ResponseObject userHomePage(HttpServletRequest req,
									   HttpServletResponse res, Map<String,String> params)throws Exception {
		//用户主页   -- 》接口
		//关注人数         用户头像
		//直播的课程     传递一个讲师id 就ok了... 得到讲师下的所有课程，得到讲师下的所有粉丝，得到讲师的
		String lecturerId = req.getParameter("lecturerId");
		Map<String,Object> mapAll = new HashMap<String,Object>();
		/**
		 * 得到讲师   主要是房间号，缩略图的信息啦
		 */
		Map<String,Object> lecturerInfo = onlineUserService.findUserRoomNumberById(lecturerId);
		/**
		 * 粉丝总数 
		 */
		Integer fansCount  = focusService.findMyFansCount(lecturerId);
		/**
		 * 关注总数 
		 */
		Integer focusCount  = focusService.findMyFocusCount(lecturerId);
		mapAll.put("lecturerInfo", lecturerInfo);          //讲师基本信息
		mapAll.put("fansCount", fansCount);       		   //粉丝总数
		mapAll.put("focusCount", focusCount);   	  	   //关注总数
		//讲师的精彩简介  
		mapAll.put("videoId", "F89D83B02BCE744D9C33DC5901307461");  //
		//坐诊医馆
		MedicalHospitalVo   mh = new MedicalHospitalVo();
		mh.setDetailedAddress("北京市丰台区开阳路一号瀚海花园大厦一层底商 北京海淀区中关村南大街19号院");
		mh.setTel("010-68412758");
		mh.setVisitTime("周一到周五");
		mh.setCertificationType(1);
		//认证的主播 还是 医馆
		mapAll.put("hospital",mh);
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(null == lecturerId){  //讲师id
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
	    if(user==null){
	    	mapAll.put("isFours", 0); 
	    }else{
	    	/**
			 * 是否已经关注了这个主播：0 未关注  1已关注
			 */
			Integer isFours  = focusService.myIsFourslecturer(user.getId(), lecturerId);
			mapAll.put("isFours", isFours); 		  //是否关注       0 未关注  1已关注
	    }
	    return ResponseObject.newSuccessResponseObject(mapAll);
	
	    /**
		 * 关注讲师的粉丝  显示六个
		 */
		//List<FocusVo> listFans = focusService.findMyFans(lecturerId,0,6);
		/**
		 * 得到讲师下面的所有课程数  ---》如果是视频数的话客户会比较蒙
		 */
		//Integer courseAll = onlineCourseService.liveAndBunchAndAudioCount(lecturerId);
		/**
		 * 得到这个讲师的所有   礼物数
		 */
		//Integer giftAll = giftService.findByUserId(lecturerId);
		/**
         * 得到判断这个主播有没有正在直播的课程啦	
         */
		//Map<String,String> mapLiveState  =  onlineCourseService.teacherIsLive(lecturerId);
		
//		mapAll.put("lecturerInfo", lecturerInfo);          //讲师基本信息
//		//mapAll.put("mapLiveState", mapLiveState); // 1 表示有直播  null表示没直播
//		mapAll.put("fansCount", fansCount);       //粉丝总数
//		mapAll.put("focusCount", focusCount);   	  // 关注总数
		//mapAll.put("giftAll", giftAll);           // 礼物数 
		//mapAll.put("courseAll", courseAll);       // 课程数 
		//mapAll.put("listFans", listFans);   	  // 前六个的粉丝数
		//主播最近一次直播
	}
	/**
	 * Description：用户主页    -- 课程列表
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("hostPageCourse")
	@ResponseBody
	public ResponseObject userHomePageCourseList(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception {
		
		String lecturerId = req.getParameter("lecturerId");
		String pageNumberS = req.getParameter("pageNumber");
		String pageSizeS = req.getParameter("pageSize");
		//String type = req.getParameter("type"); 
		if(null == lecturerId || null == pageNumberS || null == pageSizeS){  //讲师id
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		//根据讲师id得到下面的所有课程啊，包括点播，包括直播....... 直播的在前面
		//后期音频增加上的话，还需要增加音频了
		int pageNumber =Integer.parseInt(pageNumberS);
		int pageSize = Integer.parseInt(pageSizeS);
		try {
			//这个需要写在新接口中
			List<CourseLecturVo> list = onlineCourseService.liveAndBunchAndAudio(lecturerId,pageNumber,pageSize,1);
			return ResponseObject.newSuccessResponseObject(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("后台数据异常");
		}
	}

}
