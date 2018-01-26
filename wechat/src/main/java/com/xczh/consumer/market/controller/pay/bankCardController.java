package com.xczh.consumer.market.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;

import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.HttpUtils;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.wechat.course.model.UserBank;
import com.xczhihui.wechat.course.service.IUserBankService;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 资产控制器 ClassName: bankCardController.java <br>
 * Description:
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/medical")
public class bankCardController {

	@Autowired
	private ICourseApplyService courseApplyService;

	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private IUserBankService userBankService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(bankCardController.class);

	/**
	 * 添加银行卡
	 */

	@RequestMapping("addBankCard")
	@ResponseBody
	public ResponseObject addCourseApply(HttpServletRequest req,HttpServletResponse res,
										 @RequestParam("acctName")String acctName,@RequestParam("acctPan")String acctPan,
										 @RequestParam("certId")String certId)
			throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		String userId="2c9aec35605a5bab01605a632d350000";
		UserBank ub = userBankService.selectUserBankByUserIdAndAcctPan(userId,acctPan,certId);
		if(ub!=null){
			return ResponseObject.newErrorResponseObject("此卡已添加");
		}

		String host = "https://ali-bankcard4.showapi.com";
		String path = "/bank3";
		String method = "GET";
		String appcode = "c46df1e69afe4de199c7ce7041277534";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("acct_name", acctName);
		querys.put("acct_pan", acctPan);
		querys.put("cert_id", certId);
		querys.put("cert_type", "01");
		querys.put("needBelongArea", "true");

		try {
			/*HttpResponse response = HttpUtils.doGet(host, path, method, headers,querys);
			System.out.println(response.toString());
			System.out.println(EntityUtils.toString(response.getEntity()));*/

			UserBank userBank = new UserBank();
			userBank.setUserId(userId);
			userBank.setAcctName(acctName);
			userBank.setAcctPan(acctPan);
			userBank.setCertId(certId);
			userBank.setCertType("01");
			userBank.setCreateTime(new Date());
			userBankService.addUserBank(userBank);
			return  ResponseObject.newSuccessResponseObject("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("添加失败");
		}
	}


}
