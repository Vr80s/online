package com.xczh.consumer.market.controller.pay;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.user.center.bean.ItcastUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.MessageService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.VersionService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.user.center.bean.ThridFalg;
import com.xczh.consumer.market.utils.VersionCompareUtil;
import com.xczh.consumer.market.vo.VersionInfoVo;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczhihui.common.util.SLEmojiFilter;
import com.xczhihui.common.util.WeihouInterfacesListUtil;
import com.xczhihui.online.api.service.CommonApiService;
import com.xczhihui.online.api.service.RechargesService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.course.service.ICourseService;

/**
 * 通用控制器 ClassName: CommonController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月12日<br>
 */
@Controller
@RequestMapping("/xczh/recharge")
public class XzRechargeController {

	@Autowired
	private RechargesService rechargesService;
	
	/**
	 * Description：获取充值列表
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="rechargeList")
	@ResponseBody
	public ResponseObject rechargeList(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return ResponseObject.newSuccessResponseObject(rechargesService.getRecharges());
    }
}
