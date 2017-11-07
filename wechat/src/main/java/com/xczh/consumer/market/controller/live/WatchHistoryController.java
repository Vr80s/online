package com.xczh.consumer.market.controller.live;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineWatchHistoryService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.entity.OeWatchHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bxg/history")
public class WatchHistoryController {

	@Autowired
	public OnlineWatchHistoryService onlineWatchHistoryService;

	@Autowired
	private AppBrowserService appBrowserService;

	@RequestMapping("list")
	@ResponseBody
	public ResponseObject getWatchHistoryList(HttpServletRequest req,
											  HttpServletResponse res, Map<String, String> params) {
		if(null == req.getParameter("pageNumber") || null == req.getParameter("pageSize")||null == req.getParameter("type")){
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
		if(ou==null){
		   return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		
		int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));
		String type = req.getParameter("type");
		System.err.println("pageNumber:"+pageNumber+"=============="+"pageSize:"+pageSize);
		try {
		List<OeWatchHistory> list = onlineWatchHistoryService.getOeWatchHistotyList(pageNumber,
					pageSize, ou.getId(), type);
		System.err.println("type:"+type+",list.size():"+list.size());
		  return ResponseObject.newSuccessResponseObject(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  return ResponseObject.newErrorResponseObject("查询有误");
		}
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
//	@RequestMapping("add")
//	@ResponseBody
//	public ResponseObject add(HttpServletRequest req,
//			HttpServletResponse res, Map<String, String> params) {
//		try {
//			String courseId = req.getParameter()("course_id");
//			if(null == courseId){
//				return ResponseObject.newErrorResponseObject("缺少参数");
//			}
//			OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
//			if(ou==null){
//			   return ResponseObject.newErrorResponseObject("获取用户信息异常");
//			}
//			//params.put("user_id", ou.getId());
//			onlineWatchHistoryService.saveOnlineWatchHistory(ou,courseId);
//			return ResponseObject.newSuccessResponseObject("保存成功");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return ResponseObject.newErrorResponseObject("保存失败");
//		}
//		//return null;
//	}
	
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
	public ResponseObject add1(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params) {
		try {
			String courseId = req.getParameter("course_id");
			String type = req.getParameter("type");
			if(null == courseId && null == type){
				return ResponseObject.newErrorResponseObject("缺少参数");
			}
			OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
			if(ou==null){
			   return ResponseObject.newErrorResponseObject("获取用户信息异常");
			}
			//params.put("user_id", ou.getId());
			//判断是否存在，如果存在做更新时间操作
			Integer isWatchHistory = onlineWatchHistoryService.findOnlineWatchHistory(ou, courseId);
			if(isWatchHistory>0){
				onlineWatchHistoryService.updateOnlineWatchHistory(ou,courseId);
				return ResponseObject.newSuccessResponseObject("保存成功");
			}else{
				onlineWatchHistoryService.saveOnlineWatchHistory1(ou,courseId,type);
				return ResponseObject.newSuccessResponseObject("保存成功");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("保存失败");
		}
		//return null;
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
			HttpServletResponse res, Map<String, String> params) {
		try {
			if(null == req.getParameter("type")){
				return ResponseObject.newErrorResponseObject("缺少参数");
			}
			OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
			if(ou==null){
			   return ResponseObject.newErrorResponseObject("获取用户信息异常");
			}
			String type = req.getParameter("type");
			onlineWatchHistoryService.deleteOnlineWatchHistoryByUserIdAndType(ou.getId(),type);
			return ResponseObject.newSuccessResponseObject("清空成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("清空有误");
		}
	}
	
	
}
