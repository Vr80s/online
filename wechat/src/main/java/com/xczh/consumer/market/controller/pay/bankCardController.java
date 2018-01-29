package com.xczh.consumer.market.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;

import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.anchor.vo.UserBank;
import com.xczhihui.medical.anchor.service.IUserBankService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
	public ResponseObject addCourseApply(HttpServletRequest req,HttpServletResponse res,UserBank userBank,
										 @RequestParam("acctName")String acctName,@RequestParam("acctPan")String acctPan,
										 @RequestParam("certId")String certId)
			throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		UserBank i = new UserBank();
		/*if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}*/
		String userId="2c9aec35605a5bab01605a632d350000";
		userBank.setUserId(userId);
		/*UserBank ub = userBankService.selectUserBankByUserIdAndAcctPan(userId,acctPan,certId);
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
			HttpResponse response = HttpUtils.doGet(host, path, method, headers,querys);
			System.out.println(response.toString());
			System.out.println(EntityUtils.toString(response.getEntity()));
			String s = EntityUtils.toString(response.getEntity());
			JSONObject cardNegativeJson = JSONObject.parseObject(s);
			String code = cardNegativeJson.get("showapi_res_code").toString();
			if(!"0".equals(code)){
				return ResponseObject.newErrorResponseObject("银行卡信息有误");
			}
			String showapi_res_body = cardNegativeJson.get("showapi_res_body").toString();
			JSONObject srb = JSONObject.parseObject(showapi_res_body);
			String bankname = srb.get("bankName").toString();

			UserBank userBank = new UserBank();
			userBank.setUserId(userId);
			userBank.setAcctName(acctName);
			userBank.setAcctPan(acctPan);
			userBank.setCertId(certId);
			userBank.setCertType("01");
			userBank.setCreateTime(new Date());*/
			userBankService.addUserBank(userBank);
			return  ResponseObject.newSuccessResponseObject("添加成功");
		/*} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("添加失败");
		}*/
	}

	@RequestMapping(value = "userBankList")
	@ResponseBody
	public ResponseObject selectUserBankbyUserId(HttpServletRequest req) throws Exception{

		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		/*if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}*/
		String userId="2c9aec35605a5bab01605a632d350000";
		List<UserBank> userBankList = userBankService.selectUserBankByUserId(userId);
		return  ResponseObject.newSuccessResponseObject(userBankList);
	}

	public static void main(String[] args) {
		String str = "{\"showapi_res_code\":0,\"showapi_res_error\":\"\",\"showapi_res_body\":\n" +
				"{\"ret_code\":0,\"belong\":{\"area\":\"北京市 - 北京\",\"tel\":\"95599\",\"brand\":\"金穗通宝卡(银联卡)\",\"bankName\":\"中国农业银行-农业银行\",\n" +
				"\"cardType\":\"借记卡\",\"url\":\"www.abchina.com\",\"cardNum\":\"6228480018573118777\"},\"code\":0,\"msg\":\"认证通过\"}}";

		JSONObject businessLicensePictureJson = JSONObject.parseObject(str);
		String s = businessLicensePictureJson.get("showapi_res_body").toString();
		JSONObject showapi_res_body = JSONObject.parseObject(s);
		String belong = showapi_res_body.get("belong").toString();
		JSONObject b = JSONObject.parseObject(belong);
		String bankName = b.get("bankName").toString();
		System.out.println(bankName);
	}
}
