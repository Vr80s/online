package com.xczh.consumer.market.controller.live;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;
import com.xczhihui.bxg.online.api.service.CommonApiService;
import com.xczhihui.bxg.online.api.service.EnchashmentService;
import com.xczhihui.bxg.online.api.service.GiftService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.api.vo.ReceivedGift;
import com.xczhihui.bxg.online.api.vo.RechargeRecord;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;

/**
 * 用户调用这个接口，进入h5页面模式
 * ClassName: BrowserUserController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月2日<br>
 */
@Controller
@RequestMapping(value = "/bxg/oa")
public class OnlineApiController {
	@Autowired
	private UserCenterAPI userCenterAPI;
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private UserCoinService userCoinService; //充值service
	@Autowired
	private GiftService giftService;  //礼物service
	@Autowired
	private AppBrowserService appBrowserService;
	@Autowired
	private EnchashmentService enchashmentService;

	@Autowired
	private CommonApiService commonApiService;
	
	/**
	 * Description：得到用户充值列表
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="userCoinList")
	@ResponseBody
	public ResponseObject checkToken(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(null == req.getParameter("pageNumber") && null == req.getParameter("pageSize")){
			return ResponseObject.newErrorResponseObject("缺少分页参数");
		}
		int pageNumber =Integer.parseInt(req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		
		
		Page<RechargeRecord> page =  userCoinService.getUserCoinIncreaseRecord(ou.getId()
				, pageNumber, pageSize);
		System.out.println("page.getPageSize()"+page.getPageSize());
		return ResponseObject.newSuccessResponseObject(page);
    }
	
	@RequestMapping(value="giftList")
	@ResponseBody
	public ResponseObject giftList(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(null == req.getParameter("pageNumber") && null == req.getParameter("pageSize")){
			return ResponseObject.newErrorResponseObject("缺少分页参数");
		}
		int pageNumber =Integer.parseInt(req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		@SuppressWarnings("unchecked")
		Page<ReceivedGift> page =  (Page<ReceivedGift>) giftService.getReceivedGift(ou.getId()
				, pageNumber, pageSize);
		System.out.println("page.getPageSize()"+page.getPageSize());
		return ResponseObject.newSuccessResponseObject(page);
    }
	/**
	 * 
	 * Description：获取打赏集合
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="rewardList")
	@ResponseBody
	public ResponseObject rewardList(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(null == req.getParameter("pageNumber") && null == req.getParameter("pageSize")){
			return ResponseObject.newErrorResponseObject("缺少分页参数");
		}
		int pageNumber =Integer.parseInt(req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		@SuppressWarnings("unchecked")
		Page<ReceivedGift> page =  (Page<ReceivedGift>) giftService.getReceivedReward(ou.getId()
				, pageNumber, pageSize);
		System.out.println("page.getPageSize()"+page.getPageSize());
		return ResponseObject.newSuccessResponseObject(page);
    }
	/**
	 * Description：体现记录
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="cashList")
	@ResponseBody
	public ResponseObject cashList(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(null == req.getParameter("pageNumber") && null == req.getParameter("pageSize")){
			return ResponseObject.newErrorResponseObject("缺少分页参数");
		}
		int pageNumber =Integer.parseInt(req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		Page<EnchashmentApplication> page =  (Page<EnchashmentApplication>) 
				enchashmentService.enchashmentApplicationList(ou.getId()
				, pageNumber, pageSize);
		System.out.println("page.getPageSize()");
		return ResponseObject.newSuccessResponseObject(page);
    }
	
	/**
	 * Description：身份信息
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="jobVo")
	@ResponseBody
	public ResponseObject JobVo(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String group = req.getParameter("group");
		return ResponseObject.newSuccessResponseObject(commonApiService.getJob(group));
    }
	
}
