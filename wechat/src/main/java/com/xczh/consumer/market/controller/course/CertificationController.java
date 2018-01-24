package com.xczh.consumer.market.controller.course;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.controller.live.LiveController;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczhihui.bxg.online.api.po.LiveExamineInfo;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
/**
 * 主播认证
 * ClassName: CertificationController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月22日<br>
 */
@Controller
@RequestMapping("/xczh/certification")
public class CertificationController {
	
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CertificationController.class);
	
	@Autowired
	private OLAttachmentCenterService service;
	
	
	/**
	 * 
	 * Description：主播认证
	 * @param req
	 * @param res
	 * @param liveExamineInfo
	 * @param file
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("addLive")
	@ResponseBody
	public ResponseObject addLive(@RequestParam("liveExamineInfo")LiveExamineInfo liveExamineInfo,
			@RequestParam("file")MultipartFile [] files)
			throws Exception {

		Map<String,String> map = new HashMap<String,String>();
		String projectName="other";
		String result="1"; //图片类型了
		
//		String headImgPath = service.upload(null, //用户中心的用户ID
//				projectName, file.getOriginalFilename(),
//				file.getContentType(), 
//				file.getBytes(),fileType,null);

//		JSONObject json = JSONObject.parseObject(headImgPath);
//		LOGGER.info("文件路径——path:"+headImgPath);
//		map.put("logo", json.get("url").toString());
//
//		LOGGER.info("req.getParameterprice================"+req.getParameter("price"));
//		if("1".equals(liveExamineInfo.getSeeMode())){//收费
//			liveExamineInfo.setPrice(new BigDecimal(req.getParameter("price")));
//		}
//		if("2".equals(liveExamineInfo.getSeeMode())){//密码
//			liveExamineInfo.setPassword(req.getParameter("password"));
//		}
//		liveExamineInfo.setLogo(map.get("logo"));
//		//String id=liveExamineInfoService.add(liveExamineInfo);
//		Map<String,Object> result=new HashMap<>();
		//result.put("examineId",id);
		
		
		return ResponseObject.newSuccessResponseObject(result);
	}

}
