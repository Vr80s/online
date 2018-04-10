package com.xczh.consumer.market.controller.course;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.GiftService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.common.util.enums.OrderFrom;


/**
 * Description：礼物相关控制层
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 5:19 2018/1/22 0022
 **/
@Controller
@RequestMapping(value = "/xczh/gift")
public class XzGiftController {

	
	@Autowired
	@Qualifier("giftServiceLocal")
	private GiftService localGiftService;
	
	@Autowired()
	@Qualifier("giftServiceImpl")
	private com.xczhihui.bxg.online.api.service.GiftService remoteGiftService;
	
	@Autowired
	private AppBrowserService appBrowserService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzGiftController.class);

	/**
	 * 礼物榜单（直播间）
	 * @return
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value = "/rankingList")
	public ResponseObject rankingList(@RequestParam(value="liveId")String liveId, 
			@RequestParam(value="pageNumber")Integer current,
			@RequestParam(value="pageSize")Integer size) throws SQLException {
		
		return ResponseObject.newSuccessResponseObject(localGiftService.newRankingList(liveId,current,size));
	}
	/**
	 * 礼物列表
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public ResponseObject list(HttpServletRequest req,HttpServletResponse res) throws SQLException {
		
		int pageNumber = 0;
		if(null != req.getParameter("pageNumber")){
			pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
		}
		int pageSize = 10;
		if(null != req.getParameter("pageSize")){
			pageSize = Integer.valueOf(req.getParameter("pageSize"));
		}
		pageSize = Integer.MAX_VALUE;
		return ResponseObject.newSuccessResponseObject(localGiftService.listAll(pageNumber,pageSize));
	}

	/**
	 * Description：赠送礼物接口（做礼物赠送余额扣减和检验）
	 * @return
	 * @return ResponseObject
	 * @author name：liutao <br>email: gvmtar@gmail.com
	 * @throws IOException
	 * @throws SmackException
	 * @throws XMPPException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 **/
	@ResponseBody
	@RequestMapping(value = "/sendGift")
	public ResponseObject sendGift(HttpServletRequest req,
			HttpServletResponse res) throws SQLException, XMPPException, SmackException, IOException, IllegalAccessException, InvocationTargetException {

		OnlineUser user =appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		
		LOGGER.info("====================="+user.getId());
		String giftId = req.getParameter("giftId");
		String liveId = req.getParameter("liveId");
		Integer clientType = Integer.valueOf(req.getParameter("clientType"));
		Integer count = Integer.valueOf(req.getParameter("count"));
		String receiverId = req.getParameter("receiverId");
		Map<String,Object> map=null;
		map= remoteGiftService.addGiftStatement(user.getId(),
				receiverId,giftId, 
				OrderFrom.getOrderFrom(clientType),count,liveId);

		return ResponseObject.newSuccessResponseObject(map);
	}

}
