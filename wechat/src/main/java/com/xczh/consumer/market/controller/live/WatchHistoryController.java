package com.xczh.consumer.market.controller.live;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.service.IWatchHistoryService;

@Controller
@RequestMapping("/bxg/history")
public class WatchHistoryController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WatchHistoryController.class);
	
	@Autowired
	public IWatchHistoryService watchHistoryServiceImpl;

	@Autowired
	private AppBrowserService appBrowserService;

	@RequestMapping("list")
	@ResponseBody
	public ResponseObject getWatchHistoryList(HttpServletRequest req, HttpServletResponse res) {
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
	}
    /**
     * Description：添加观看记录
     * @param req
     * @param res
     * @param params
     * @return
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	@RequestMapping("add")
	@ResponseBody
	public ResponseObject add(HttpServletRequest req,
			HttpServletResponse res) {
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
	}
	/**
	 * Description：清空观看记录
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("empty")
	@ResponseBody
	public ResponseObject empty(HttpServletRequest req,
			HttpServletResponse res) {
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
	}
}
