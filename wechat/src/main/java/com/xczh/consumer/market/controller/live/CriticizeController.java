package com.xczh.consumer.market.controller.live;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.service.CriticizeService;
import com.xczhihui.bxg.online.api.vo.Criticize;

@Controller
@RequestMapping("/bxg/criticize")
public class CriticizeController {

	@Autowired
	private CriticizeService criticizeService;

	@Autowired
	private AppBrowserService appBrowserService; 
	
	/**
	 * 添加评论
	 */
	@RequestMapping("saveCriticize")
	@ResponseBody
	public ResponseObject saveCriticize(HttpServletRequest req,
			HttpServletResponse res,Criticize criticize)
			throws Exception {
		OnlineUser  ou = appBrowserService.getOnlineUserByReq(req);
		criticize.setCreateTime(new Date());
		criticize.setUserId(ou.getId());
		
		if(criticize.getContent().length()>200){
			return ResponseObject.newErrorResponseObject("抱歉评论内容不能大于200字");
		}else{
			criticizeService.saveCriticize(criticize);
			return ResponseObject.newSuccessResponseObject("评论添加成功");
		}
	}
	/**
	 * 得到此视频下的说有评论
	 */
	@RequestMapping("getVideoCriticize")
	@ResponseBody
	public ResponseObject getVideoCriticize(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		int pageNumber =Integer.parseInt(req.getParameter("pageNumber"));
		int pageSize = 20;
		String videoId = req.getParameter("videoId");
		String name = req.getParameter("name");
		
		Page<Criticize> pageList  = criticizeService.getVideoCriticize(videoId, name, pageNumber, pageSize);
		return ResponseObject.newSuccessResponseObject(pageList);
	}
}
